import sbt.Keys._
import sbt._


object DependenciesConf {

  lazy val scala: Seq[Setting[_]] = Seq(
    scalaVersion := "2.12.2",
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("iheartradio", "maven"),
      Resolver.sonatypeRepo("releases")
    )
  )

  lazy val common: Seq[Setting[_]] = scala ++ Seq(
    libraryDependencies ++= commonDeps
  )

  def commonDeps = Seq(
    "org.scalaz" %% "scalaz-core" % "7.2.12",
    "commons-io" % "commons-io" % "2.5",
    "com.iheart" %% "ficus" % "1.4.0",
    "com.github.pathikrit" %% "better-files" % "3.0.0",
    "com.beachape" %% "enumeratum" % "1.5.12",
    "com.github.nscala-time" %% "nscala-time" % "2.16.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "org.scalatest" %% "scalatest" % "3.0.3" % Test,
    "org.scalacheck" %% "scalacheck" % "1.13.5" % Test,
    "org.typelevel" %% "scalaz-scalatest" % "1.1.2" % Test
  )
}