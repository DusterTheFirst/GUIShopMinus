#!/bin/bash

branch=$GIT_BRANCH
repo_slug=$TRAVIS_REPO_SLUG
token=$GITHUB_TOKEN
version=$TRAVIS_TAG

body="## Changes in this release\n$(git log --format="- %aN - %s (%h)" $TRAVIS_COMMIT_RANGE)"

echo "Create release $version for repo: $repo_slug, branch: $branch"

URL=$(jq -n \
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
    }' \
    | curl -H "Authorization: token $token" --data @- "https://api.github.com/repos/$repo_slug/releases" \
    | jq ".upload_url")

curl -H "Authorization: token $token" --data @target/GuiShopMinus-$TRAVIS_TAG-SNAPSHOT.jar $URL
