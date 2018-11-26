#!/usr/bin/env bash

JAR_VER=$(cat build.sbt | grep "version := " | cut -d$'"' -f2 | cut -d- -f1)
DATE=`date +%D`

function run_sbt(){
  if [ -d ~/.IntelliJIdea2018.2 ]
  then
    java -jar ~/.IntelliJIdea2018.2/config/plugins/Scala/launcher/sbt-launch.jar $@
  else
    java -jar ~/.IdeaIC2018.2/config/plugins/Scala/launcher/sbt-launch.jar $@
  fi
}

run_sbt ++2.12.0 clean doc
mv docs/api/.nojekyll docs/
git rm -r docs/api
mv target/scala-2.12/api docs
git add docs/api
mv docs/.nojekyll docs/api
git commit -a -m "Documentation of v$JAR_VER at $DATE"