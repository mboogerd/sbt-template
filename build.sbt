import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license.Apache2_0

name := "sbt-template"

version := "1.0"

scalaVersion := "2.11.8"

organization := "org.mboogerd"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  //  "-Xlint",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  //  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
  //  "-Ylog-classpath"
)

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-Xlink:-warn-missing-interpolator", "-g:vars")

cancelable in Global := true

def javaVersion(version: String): Seq[Def.Setting[Task[Seq[String]]]] = Seq(
  javacOptions ++= Seq("-source", version, "-target", version),
  scalacOptions += s"-target:jvm-$version"
)

def licenceSettings = Seq(
  licenses +=("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php")),
  headers := Map(
    "scala" -> Apache2_0("2015", "Merlijn Boogerd"),
    "conf" -> Apache2_0("2015", "Merlijn Boogerd", "#")
  )
)

lazy val Benchmark = config("bench") extend Test

def commonSettings = Seq(
  organization := "org.mboogerd",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "com.iheart"        %% "ficus"      % "1.1.3",
    "org.scalatest"     %% "scalatest"  % "3.0.0-M7"  % "test",
    "org.scalacheck"    %% "scalacheck" % "1.12.5"    % "test",
    "com.storm-enroute" %% "scalameter" % "0.7"       % "test",
    "org.typelevel"     %% "discipline" % "0.4"       % "test"
  ),
  testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
  parallelExecution in Benchmark := false,
  parallelExecution in Test := false
)

def gatlingSettings = Seq(
  libraryDependencies ++= Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.7" % "test",
    "io.gatling"            % "gatling-test-framework"    % "2.1.7" % "test"
  )
)

lazy val module = project.in(file("module"))
  .settings(moduleName := "module")
  .settings(javaVersion("1.8"))
  .settings(commonSettings)
  .settings(licenceSettings)
  .settings{
    libraryDependencies ++= Seq(
    )
  }
  .enablePlugins(AutomateHeaderPlugin)
  .configs(Benchmark)
  .settings(inConfig(Benchmark)(Defaults.testSettings): _*)
