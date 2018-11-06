organization := "kr.bydelta"
name := "koalanlp-scala"
version := "1.0.0"

crossScalaVersions := Seq("2.11.0", "2.12.0", "2.13.0")

scalacOptions += "-language:implicitConversions"

libraryDependencies ++= Seq(
  "kr.bydelta" % "koalanlp-core" % "2.0.0",
  "org.specs2" %% "specs2-core" % "3.9.5" % "test"
)