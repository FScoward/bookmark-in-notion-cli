val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "bookmark-in-notion-cli",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "com.softwaremill.sttp.client3" %% "core" % "3.3.16",
      "com.typesafe" % "config" % "1.4.1",
      "com.github.scopt" %% "scopt" % "4.0.1"
    )
  )
