name := "hic"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0-M15", //% "2.2.4" % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "2.53.0",
  "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.52.0"
)
