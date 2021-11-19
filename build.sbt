val scala3Version = "3.1.0"
val circeVersion = "0.14.1"

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
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    // https://mvnrepository.com/artifact/com.softwaremill.sttp.client3/circe
    libraryDependencies += "com.softwaremill.sttp.client3" %% "circe" % "3.3.16"
  )
