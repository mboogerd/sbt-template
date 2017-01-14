import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import sbt.Keys._
import sbt.Process._
import sbt.{IntegrationTest, ProjectReference, TaskKey}

object IntegrationConf {

  val startPlatformTask: TaskKey[Unit] = TaskKey[Unit]("startPlatform", "Launch the platform (infrastructure and functional components) using docker-compose")
  val stopPlatformTask: TaskKey[Unit] = TaskKey[Unit]("stopPlatform", "Shutdown the platform components")

  /**
    * Configuration to run integration test; first build docker-images and launch an environment
    * @param buildDockerImages projects to build a docker-image for, before launching the platform and integration-test
    */
  def settings(buildDockerImages: ProjectReference*) = Seq (
    startPlatformTask := ("./launch.sh" !),
    stopPlatformTask := ("docker-compose stop" !),
    // Create a dependency between building the given projects as docker-images and the launch of the platform
    startPlatformTask <<= startPlatformTask dependsOn (buildDockerImages.map(p ⇒ publishLocal in (p, Docker)):_*),
    // Create a dependency between execution of integration-test and the starting/stopping of the required platform
    (test in IntegrationTest) <<=
      stopPlatformTask dependsOn ((test in IntegrationTest) dependsOn startPlatformTask)
  )
}