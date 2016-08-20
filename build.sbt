import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license.Apache2_0
//import com.typesafe.sbt.site.util.SiteHelpers._
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._


name := "sbt-template"

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.github.mboogerd"

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
  organization := "com.github.mboogerd",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "com.iheart" %% "ficus" % "1.1.3",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
    "com.storm-enroute" %% "scalameter" % "0.7" % "test",
    "org.typelevel" %% "discipline" % "0.5" % "test"
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


lazy val root = project.in(file("."))
  .settings(moduleName := "sbt-template")
  .settings(commonSettings)
  .settings(publishSettings)
  .aggregate(module)
  .dependsOn(module)

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



lazy val tagName = Def.setting{
  s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
}

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/mboogerd/sbt-template")),
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/Apache-2.0")),
  scmInfo := Some(ScmInfo(url("https://github.com/mboogerd/sbt-template"), "scm:git:git@github.com:mboogerd/sbt-template.git")),
  autoAPIMappings := true,
  pomExtra := (
    <developers>
      <developer>
        <id>merlijn</id>
        <name>Merlijn Boogerd</name>
        <url>https://github.com/mboogerd/</url>
      </developer>
    </developers>
    )
) ++ credentialSettings ++ sharedPublishSettings ++ sharedReleaseProcess

lazy val sharedPublishSettings = Seq(
  releaseCrossBuild := true,
  releaseTagName := tagName.value,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  }
)

lazy val sharedReleaseProcess = Seq(
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true),
    pushChanges)
)


lazy val credentialSettings = Seq(
  // For Travis CI - see http://www.cakesolutions.net/teamblogs/publishing-artefacts-to-oss-sonatype-nexus-using-sbt-and-travis-ci
  credentials ++= (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq
)