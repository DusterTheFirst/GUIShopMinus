#!/bin/bash

# Generate a version
version=${TRAVIS_TAG:-$(date +'%Y-%m-%d')-$(git log --format=%h -1)}

# Add the tag
git tag $version

export VERSION=$version