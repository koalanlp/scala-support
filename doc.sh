#!/usr/bin/env bash

JAR_VER=$(cat build.sbt | grep "version := " | cut -d$'"' -f2 | cut -d- -f1)
DATE=`date +%D`

function run_sbt(){
  java -jar ~/.IdeaIC2018.2/config/plugins/Scala/launcher/sbt-launch.jar $@
}

run_sbt doc
git rm -r docs/api
mv target/scala-2.12/api docs/api
git add docs/api
git commit -a -m "Documentation of v$JAR_VER at $DATE"