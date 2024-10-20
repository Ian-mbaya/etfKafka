ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"


libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "2.8.0",
  "org.apache.kafka" % "kafka-clients" % "2.8.0",
  "org.apache.hadoop" % "hadoop-client" % "3.3.2" % "provided",  // Hadoop client, marked as provided
  "org.apache.hive" % "hive-jdbc" % "2.3.7" % "provided",        // Hive JDBC, marked as provided
  "org.apache.hive" % "hive-metastore" % "2.3.7" % "provided",  // Hive Metastore, marked as provided
  "com.typesafe" % "config" % "1.4.1"
)

mainClass in Compile := Some("app.etf.Main")

lazy val root = (project in file("."))
  .settings(
    name := "producerApp",
    idePackagePrefix := Some("app.etf")
  )
