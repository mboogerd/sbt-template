#!/usr/bin/env bash

# Create the base image for the docker
source scripts/build_docker.sh

pushd docker
buildDockerImage java8
popd

# Build the individual application component docker-images

echo "============================"
echo "=== BUILDING APPLICATION ==="
echo "============================"
sbt docker:publishLocal -DsetLatestTag=true