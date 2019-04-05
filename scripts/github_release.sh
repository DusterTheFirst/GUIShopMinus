#!/bin/bash

# # Get the previous hash for the archive
# latest_release=$(curl -sS -H "Authorization: token $GITHUB_TOKEN" "https://api.github.com/repos/$TRAVIS_REPO_SLUG/releases/latest" \
#     | jq ".assets[] | .name")

# echo $latest_release
# exit 0

# Create the body of a release
body="## Changes in this release
$(git log --format="- %aN - %s (%h)" $TRAVIS_COMMIT_RANGE)
> Thank you for your support"

# Echo status
echo "Create release $version for repo: $TRAVIS_REPO_SLUG, branch: $TRAVIS_BRANCH"

# Generate JSON payload
payload=$(jq -n --arg body "$body" --arg name "Snapshot $version" --arg tag_name "$version" --arg target_commitish "$TRAVIS_BRANCH" \
    '{ body: $body, name: $name, tag_name: $tag_name, target_commitish: $target_commitish, draft: false, prerelease: true }')

# Create the release and get the file upload URL
url=$(curl -sS -H "Authorization: token $GITHUB_TOKEN" --data "$payload" "https://api.github.com/repos/$TRAVIS_REPO_SLUG/releases" \
    | jq .upload_url | sed -e "s/{?name,label}//g" | sed -e "s/\"//g")

# Upload the archive
curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Content-Type: application/java-archive" --data @target/GuiShopMinus.jar "$url?name=GuiShopMinus-$version-SNAPSHOT.jar" > /dev/null

# Generate hash for the archive and upload
shasum -a 256 target/GuiShopMinus.jar \
    | curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Content-Type: text/plain" --data @- "$url?name=checksum.sha256" > /dev/null
