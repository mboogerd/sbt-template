import sbt.Keys._
import sbt._


object DependenciesConf {

  lazy val settings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.12.0",
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("iheartradio", "maven")
    ),
    libraryDependencies ++= commonDeps
  )

  def commonDeps = Seq(
    "org.scalaz" %% "scalaz-core" % "7.2.7",
    "commons-io" % "commons-io" % "2.5",
    "com.iheart" %% "ficus" % "1.3.4",
    "ch.qos.logback" % "logback-classic" % "1.1.7",

    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "org.scalacheck" %% "scalacheck" % "1.13.4" % Test,
    "org.typelevel" %% "scalaz-scalatest" % "1.1.1" % Test
  )
}