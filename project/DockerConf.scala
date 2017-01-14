import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging.autoImport._
import com.typesafe.sbt.packager.docker.Cmd
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import sbt.Keys._
import sbt._

import scala.util.Try


object DockerConf {

  // Allows supplying SBT with -DsetLatestTag=true in order to tag any built image as 'latest'.
  // FIXME: this should be substituted with 'dockerUpdateLatest in publishLocal := true' or something along those lines
  def updateLatestDockerTag(): Boolean = Try(System.getProperty("setLatestTag").toBoolean).getOrElse(false)

  lazy val settings: Seq[Setting[_]] = Seq(
    mainClass in Compile := Some("com.github.mboogerd"),
    dockerCommands := dockerCommands.value.filterNot {
      // ExecCmd is a case class, and args is a varargs variable, so you need to bind it with @
      case Cmd("USER", args@_*) => true
      // dont filter the rest
      case cmd => false
    },
    dockerExposedPorts := Seq(8080),
    dockerUpdateLatest := updateLatestDockerTag,
    dockerBaseImage := "java8-base:latest",
    packageName in Docker := name.value.toLowerCase,
    // configure sbt-native-package to use config
    bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
  )
}