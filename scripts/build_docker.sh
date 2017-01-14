#!/usr/bin/env bash

# Builds a docker, assuming that the current directory is set to [project-root]/docker
buildDockerImage() {
    echo "===============================$1" | sed 's/./=/g'
    echo "=== BUILDING DOCKER IMAGE: $1 ==="
    echo "===============================$1" | sed 's/./=/g'
    pushd $1 > /dev/null
    ./docker-build.sh
    popd > /dev/null
}