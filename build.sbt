name := "sbt-template"

organization := "com.github.mboogerd"

version := "0.1"

lazy val root = project.in(file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings : _*)
  .settings(IntegrationConf.settings(core))
  .settings(DependenciesConf.common)
  .settings(ReleaseConf.publishSettings)
  .aggregate(common, core)
  .dependsOn(common % "compile->compile;test->test", core)

lazy val common = project.in(file("common"))
  .settings(DependenciesConf.common)
  .settings(ReleaseConf.noPublishSettings)

lazy val core = project.in(file("core"))
  .dependsOn(common % "compile->compile;test->test")
  .settings(GenericConf.settings())
  .settings(DependenciesConf.common)
  .settings(LicenseConf.settings)
  .settings(DockerConf.settings)
  .settings(ReleaseConf.noPublishSettings)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(AutomateHeaderPlugin)
