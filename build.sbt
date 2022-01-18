ThisBuild / scalaVersion     := "2.13.7"

lazy val ce2 = (project in file("ce2")).settings(name := "ce2", libraryDependencies ++= Seq("org.typelevel" %% "cats-effect" % "2.5.2", "co.fs2" %% "fs2-core" % "2.5.9"))
lazy val ce3 = (project in file("ce3")).settings(name := "ce3", libraryDependencies ++= Seq("org.typelevel" %% "cats-effect" % "3.3.4", "co.fs2" %% "fs2-core" % "3.2.4"))

lazy val root = (project in file("."))
  .settings(
    name := "queue-order-repro",
  ).aggregate(ce2, ce3)

