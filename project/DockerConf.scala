import sbt.Keys._
import sbt._


object DockerConf {
  // docker settings //
//  lazy val settings: Seq[Setting[_]] = Seq(
//    mainClass in Compile := Some("com.github.mboogerd.sbt-template.App"),
//    dockerCommands := dockerCommands.value.filterNot {
//      // ExecCmd is a case class, and args is a varargs variable, so you need to bind it with @
//      case Cmd("USER", args@_*) => true
//      // dont filter the rest
//      case cmd => false
//    },
//
//    dockerExposedPorts := Seq(8080),
//    dockerUpdateLatest := false,
//    dockerBaseImage := "java:8",
//    packageName in Docker := "sbt-template",
//    // configure sbt-native-package to use config
//    bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
//  )
}