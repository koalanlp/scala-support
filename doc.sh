#!/usr/bin/env bash

JAR_VER=$(cat build.sbt | grep "version := " | cut -d= -f$'"' | cut -d- -f1)
DATE=`date +%D`

function run_sbt(){
  java -jar ~/.IntelliJIdea2017.3/system/sbt/sbt-launch.jar $@
}

run_sbt unidoc
git rm docs/api
mv target/scala-2.12/api docs/api
git add docs/api
git commit -a -m "Documentation of v$JAR_VER at $DATE"