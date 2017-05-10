name := "sbt-template"

organization := "com.github.mboogerd"

version := "0.1"

lazy val root = project.in(file("."))
  .settings(GenericConf.settings())
  .settings(DependenciesConf.common)
  .settings(TutConf.settings)
  .enablePlugins(TutPlugin)