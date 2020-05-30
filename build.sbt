name := "hub-tickets"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.8.0",
  
  "com.lihaoyi" %% "cask" % "0.6.0",
  "com.lihaoyi" %% "requests" % "0.5.1",
  "com.lihaoyi" %% "ujson" % "1.0.0",
  
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
