name := "android-yaml-view"
version := "0.01"
scalaVersion := "2.11.7"

libraryDependencies ++= {
  Seq(
    "net.jcazevedo" %% "moultingyaml" % "0.4.0",
    "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6",
    "org.apache.commons" % "commons-io" % "1.3.2",
    "org.scalactic" %% "scalactic" % "3.0.1" % "test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}

