import sbt.librarymanagement

organization := "kr.bydelta"
name := "koalanlp-scala"
version := "2.0.3-SNAPSHOT"

scalaVersion := "2.12.0"
crossScalaVersions := Seq("2.11.0", "2.12.0", "2.13.0")
autoAPIMappings := true

scalacOptions ++= Seq("-deprecation", "-unchecked", "-language:implicitConversions")
scalacOptions in Test ++= Seq("-Yrangepos")
resolvers += Resolver.JCenterRepository
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "kr.bydelta" % "koalanlp-core" % "2.0.2",
  "kr.bydelta" % "koalanlp-hnn" % "2.0.2" % "test" classifier "assembly",
  "kr.bydelta" % "koalanlp-kmr" % "2.0.2" % "test",
  "kr.bydelta" % "koalanlp-etri" % "2.0.2" % "test",
  "org.specs2" %% "specs2-core" % "3.9.5" % "test"
)

publishArtifact in Test := false

apiURL := Some(url("https://koalanlp.github.io/scala-support/api/"))
homepage := Some(url("http://koalanlp.github.io/koalanlp"))

publishTo := version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}.value

licenses := Seq("MIT" -> url("https://tldrlegal.com/license/mit-license"))

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra :=
  <scm>
    <url>git@github.com:koalanlp/scala-support.git</url>
    <connection>scm:git:git@github.com:koalanlp/scala-support.git</connection>
  </scm>
    <developers>
      <developer>
        <id>nearbydelta</id>
        <name>Bugeun Kim</name>
        <url>http://github.com/nearbydelta</url>
      </developer>
    </developers>
