#!/bin/bash

echo $(curl -sS -H "Authorization: token $GITHUB_TOKEN" "https://api.github.com/rate_limit" \
    | jq -r '.resources.core | "\(.remaining) out of \(.limit) requests left until \(.reset | strftime("%Y-%m-%d %H:%M:%S")) UTC"')

# Get the previous hash for the archive
latest_release=$(curl -sS -H "Authorization: token $GITHUB_TOKEN" "https://api.github.com/repos/$TRAVIS_REPO_SLUG/releases/latest" \
    | jq -r ".assets[] | select(.name == \"checksum.sha256\") | .url")

curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Accept: application/octet-stream" -L $latest_release | shasum -a 256 -c -

if [ $? = 0 ]; then
    echo "Jarfile has not changed since last build, aborting"
    exit 0
fi

# Generate a version
version=${TRAVIS_TAG:-$(date +'%Y-%m-%d')-$(git log --format=%h -1)}

# Add the tag
git tag $version

# Create the body of a release
body="## Changes in this release
$(git log --format="- %aN - %s (%h)" $TRAVIS_COMMIT_RANGE)
> Thank you for your support"

# Echo status
echo "Create release $version for repo: $TRAVIS_REPO_SLUG, branch: $TRAVIS_BRANCH"

# Generate JSON payload
payload=$(jq -n --arg body "$body" --arg name "Snapshot $version" --arg tag_name "$version" --arg target_commitish "$TRAVIS_BRANCH" \
    '{ body: $body, name: $name, tag_name: $tag_name, target_commitish: $target_commitish, draft: false, prerelease: false }')

# Create the release and get the file upload URL
url=$(curl -sS -H "Authorization: token $GITHUB_TOKEN" --data "$payload" "https://api.github.com/repos/$TRAVIS_REPO_SLUG/releases" \
    | jq -r .upload_url | sed -e "s/{?name,label}//g")

# Upload the archive
curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Content-Type: application/java-archive" --data @target/GuiShopMinus.jar "$url?name=GuiShopMinus-$version-SNAPSHOT.jar" > /dev/null

# Generate hash for the archive and upload
shasum -a 256 target/GuiShopMinus.jar \
    | curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Content-Type: text/plain" --data @- "$url?name=checksum.sha256" > /dev/null
