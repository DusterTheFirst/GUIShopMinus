#!/bin/bash

# Set deployment bot credentials
git config --local user.name "Deployment Bot"
git config --local user.email "deploy@travis-ci.org"

# Get environment variables
branch=$GIT_BRANCH
repo_slug=$TRAVIS_REPO_SLUG
token=$GITHUB_TOKEN

# Generate a version
version=${TRAVIS_TAG:-$(date +'%Y-%m-%d')-$(git log --format=%h -1)}

# Add the tag
git tag $version

# Create the body of a release
body="## Changes in this release
$(git log --format="- %aN - %s (%h)" $TRAVIS_COMMIT_RANGE)"

# Echo status
echo "Create release $version for repo: $repo_slug, branch: $branch"

# Generate JSON payload
payload=$(jq -n \
  --arg body "$body" \
  --arg name "Snapshot $version" \
  --arg tag_name "$version" \
  --arg target_commitish "$branch" \
  '{
    body: $body,
    name: $name,
    tag_name: $tag_name,
    target_commitish: $target_commitish,
    draft: false,
    prerelease: true
  }')

# Create the release and get the file upload URL
url=$(curl -sS -H "Authorization: token $token" --data "$payload" "https://api.github.com/repos/$repo_slug/releases" \
    | jq .upload_url \
    | sed -e "s/{?name,label}/?name=GuiShopMinus-$version-SNAPSHOT.jar/g" \
    | sed -e "s/\"//g")

# Upload the archive
curl -sS -H "Authorization: token $token" -H "Content-Type: application/java-archive" --data @target/GuiShopMinus.jar $url > /dev/null
