lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % "test,it"
lazy val junit = "junit" % "junit" % "4.12" % "test,it"
lazy val junitInterface = "com.novocode" % "junit-interface" % "0.11" % "test,it"

scalacOptions := Seq("-target:jvm-1.8")

lazy val root = (project in file(".")).
  configs(IntegrationTest).
  settings(
    inThisBuild(List(
      organization := "com.norbjd",
      scalaVersion := "2.12.4",
      version := "1.0.0-SNAPSHOT"
    )),
    name := "maven-vs-sbt-integration-tests",
    Defaults.itSettings,
    libraryDependencies += scalaTest,
    libraryDependencies += junit,
    libraryDependencies += junitInterface
  )

testOptions += Tests.Argument(TestFrameworks.JUnit)