name := "markhorsearch"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq("spray repo" at "http://repo.spray.io/")

libraryDependencies ++= {
  Seq(
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "xalan" % "xalan" % "2.7.1",
  "ch.qos.logback" % "logback-classic" % "1.0.12",
  "org.scalatest" %% "scalatest" % "2.0.M7" % "test",
  "org.specs2" %% "specs2" % "1.14" % "test",
  "junit" % "junit" % "4.8.1" % "test"
  )
}

