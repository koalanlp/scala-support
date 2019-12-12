#!/usr/bin/env bash

SCALA_VERS=$(cat build.sbt | grep crossScalaVersions | cut -d\" -f2,4,6 --output-delim=$' ')

function run_sbt(){
  IDEA_PATH=$(ls -dt1 ~/.IntelliJIdea* | head -n 1)
  SCALA_VERSION=$1
  if [[ "${SCALA_VERSION:2:4}" =~ 2.13.* ]]
  then
      JAVA_RUNTIME=java
  else
      JAVA_RUNTIME=/usr/lib/jvm/java-8-openjdk-amd64/bin/java
  fi

  echo Launch sbt with $($JAVA_RUNTIME -version 2>&1 | head -n 1) for $SCALA_VERSION
  $JAVA_RUNTIME -jar $IDEA_PATH/config/plugins/Scala/launcher/sbt-launch.jar $@
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

build_doc()
{
    LAST_SCALA_VER=$(cat build.sbt | grep scalaVersion | cut -d\" -f2 --output-delim=$' ')
    SCALA_MINOR_VER=$(echo $LAST_SCALA_VER | cut -d. -f-2)
    DATE=`date +%D`

    run_sbt ++$LAST_SCALA_VER clean doc
    git rm -r docs/api
    mv target/scala-$SCALA_MINOR_VER/api docs
    touch docs/api/.nojekyll
    git add docs/api
    git commit -a -m "Documentation of v$JAR_VER_CURRENT for $SCALA_MINOR_VER at $DATE"
}

case $1 in
    all)
        extract_version

        # reset version code
        echo BUILD $JAR_VER_CURRENT
        ask_proceed "SET VERSION"
        if [ "${YN,,}" != "p" ]; then
            set_version $JAR_VER_CURRENT
        fi

        # Upload core and close, because of the dependencies
        ask_proceed "TEST"
        if [ "${YN,,}" != "p" ]; then
            for MODULE in $SCALA_VERS
            do
                run_sbt ++$MODULE test
            done
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
            run_sbt "sonatypeReleaseAll"
        fi

        ask_proceed "SET NEXT"
        if [ "${YN,,}" != "p" ]; then
            build_doc
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
    *)
        echo ./release.sh "[help|all]"
        ;;
esac