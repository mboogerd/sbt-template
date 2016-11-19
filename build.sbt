name := "sbt-template"

version := "1.0"

scalaVersion := "2.12.0"

organization := "com.github.mboogerd"

lazy val root = project.in(file("."))
  .settings(ReleaseConf.publishSettings)
  .aggregate(module)
  .dependsOn(module)

lazy val module = project.in(file("module"))
  .settings(GenericConf.settings())
  .settings(DependenciesConf.settings)
  .settings(LicenseConf.settings)
  .enablePlugins(AutomateHeaderPlugin)