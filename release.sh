#!/usr/bin/env bash

SCALA_VERS=$(cat build.sbt | grep crossScalaVersions | cut -d\" -f2,4 --output-delim=$' ')

function run_sbt(){
  java -jar ~/.IdeaIC2018.2/config/plugins/Scala/launcher/sbt-launch.jar $@
}

extract_version()
{
    JAR_VER=$(cat build.sbt | grep "version :=" | cut -d\" -f2 | cut -d- -f1)
    JAR_VER_MAJOR=$(echo $JAR_VER | cut -d. -f1)
    JAR_VER_MINOR=$(echo $JAR_VER | cut -d. -f2)
    JAR_VER_INCRM=$(echo $JAR_VER | cut -d. -f3)
    JAR_VER_CURRENT=$JAR_VER_MAJOR.$JAR_VER_MINOR.$JAR_VER_INCRM
}

add_incremental_ver()
{
    JAR_VER_NEXT=$JAR_VER_MAJOR.$JAR_VER_MINOR.$(($JAR_VER_INCRM + 1))
}

add_minor_ver()
{
    JAR_VER_NEXT=$JAR_VER_MAJOR.$(($JAR_VER_MINOR + 1)).0
}

set_version()
{
    cat build.sbt | sed -e 's/version\s*:=\s*".*"/version := "'$1'"/g' > build_new.sbt
    rm build.sbt
    mv build_new.sbt build.sbt
    git add build.sbt
}

ask_proceed()
{
    read -p "Proceed $1 [Y/n/p]? " YN
    if [ "${YN,,}" = "n" ]; then
        exit 0
    fi
}


case $1 in
    help)
        echo ./release.sh "[help|all]"
        ;;
    all)
        extract_version

        # reset version code
        echo BUILD $JAR_VER_CURRENT
        ask_proceed "SET VERSION"
        if [ "${YN,,}" != "p" ]; then
            set_version $JAR_VER_CURRENT
        fi

        # Upload core and close, because of the dependencies
        ask_proceed "UPLOAD"
        if [ "${YN,,}" != "p" ]; then
            for MODULE in $SCALA_VERS
            do
                run_sbt ++$MODULE publishSigned
            done
        fi

        echo UPLOAD FINISHED

        ask_proceed "RELEASE"
        if [ "${YN,,}" != "p" ]; then
            run_sbt "sonatypeReleaseAll kr.bydelta"
        fi

        ask_proceed "SET NEXT"
        if [ "${YN,,}" != "p" ]; then
            add_incremental_ver
            set_version "$JAR_VER_NEXT-SNAPSHOT"
        fi

        ask_proceed "COMMIT"
        if [ "${YN,,}" != "p" ]; then
            git tag v$JAR_VER_CURRENT
            git commit -a -m "inital commit of v$JAR_VER_NEXT"
            git push origin master
            git push --tags
        fi
        ;;
esac