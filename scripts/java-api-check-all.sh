#!/usr/bin/env bash

set -Eeuo pipefail
# Usage:
#   ./java-api-check-all.sh <current release tag (empty for branches)>
#
# Note: if run locally, execute `mbx env` and `./gradlew assembleRelease` first.
#
if ! command -v gh &> /dev/null
then
    echo "gh (github cli tool) is not found, install it first"
    exit
fi
gh auth login --with-token < ./gh_token.txt

CURRENT_DIR=$(dirname "$0")
MAJOR_CHANGE_FILE=${CURRENT_DIR}/../api_compat_report/major.txt
[ -e $MAJOR_CHANGE_FILE ] && rm $MAJOR_CHANGE_FILE
REVAPI=`mktemp -d`
mkdir -p "${REVAPI}"

if [[ -f "$REVAPI/revapi-0.9.7/revapi.sh" ]]; then
  echo "Revapi has installed in $REVAPI/revapi-0.9.7/revapi.sh"
  export PATH=${REVAPI}/revapi-0.9.7:$PATH
fi
if ! [ -x "$(command -v revapi.sh)" ]; then
    echo "Revapi is not installed. Installing revapi"
    pushd "${REVAPI}" > /dev/null
    wget https://search.maven.org/remotecontent?filepath=org/revapi/revapi-standalone/0.9.7/revapi-standalone-0.9.7-standalone.zip -O revapi.zip \
    && unzip revapi.zip \
    && export PATH=${REVAPI}/revapi-0.9.7:$PATH
    popd > /dev/null
fi

RELEASE_TAG=${1-""}

CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
TARGET_BRANCH=$(gh pr view "$CURRENT_BRANCH" --json baseRefName --jq '.baseRefName')
echo "CURRENT_BRANCH: $CURRENT_BRANCH"
echo "TARGET_BRANCH: $TARGET_BRANCH"

if ! [ "$TARGET_BRANCH" = "main" ]; then # Release branches
  LATEST_RELEASE_TAG_IN_THIS_BRANCH=$(git tag --merged "$CURRENT_BRANCH" --sort=-creatordate | grep "android-v"| head -n 1)
  if [ "$LATEST_RELEASE_TAG_IN_THIS_BRANCH" = "*beta*" ] || [ "$LATEST_RELEASE_TAG_IN_THIS_BRANCH" = "*rc*" ]; then
    # Use the latest stable minor release tag during rc and beta release
    LAST_STABLE_RELEASE_TAG=$(git tag --list 'android-v*.0' --sort=-creatordate | head -n 1)
  else
    # Use the latest stable minor release tag in this branch
    LAST_STABLE_RELEASE_TAG=$(git tag --merged "$CURRENT_BRANCH" --sort=-creatordate | grep "android-v.*0$"| head -n 1)
  fi
else
  # Use the latest stable minor release tag for main branch
  LAST_STABLE_RELEASE_TAG=$(git tag --list 'android-v*.0' --sort=-creatordate | head -n 1)
fi
echo "LAST_STABLE_RELEASE_TAG: $LAST_STABLE_RELEASE_TAG"
echo "Release tag: $RELEASE_TAG"
MODULES=$(sed -e '/include(/,/)/!d' ./settings.gradle.kts | tr -d '\n' | sed 's/.*(\([^]]*\)).*/\1/g;s/,\n/\n/g' | tr , \\n | sed 's/.*":\([^]]*\)".*/\1/g')
for i in $MODULES; do
  if [ -f "./$i/build/outputs/aar/$i-release.aar" ]; then
    MODULE_NAME=$i
    if [[ $i == plugin* ]]; then
      MODULE_NAME=${MODULE_NAME: 7}
    elif [[ $i == extension* ]]; then
      MODULE_NAME=${MODULE_NAME: 10}
    elif [[ $i == *telemetry* ]]; then
      continue
    fi
    echo "========================  Checking module: $i, module aar name: $MODULE_NAME  ===================================="
    "${CURRENT_DIR}"/java-api-check.sh "$RELEASE_TAG" "./$i/build/outputs/aar/$i-release.aar" "$MODULE_NAME" "$LAST_STABLE_RELEASE_TAG"
  fi
done

rm -rf "${REVAPI}"

# Fail the job when there is major changes.
if [[ -f "$MAJOR_CHANGE_FILE" ]]; then
  echo ""
  echo "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***"
  echo "*** *** *** ***         Potential major level breaking change found         *** *** *** ***"
  echo "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***"
  echo ""
  cat "$MAJOR_CHANGE_FILE"
  exit 1
fi