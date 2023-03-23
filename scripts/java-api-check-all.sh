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

REVAPI_VERSION="0.9.7"
CURRENT_DIR=$(dirname "$0")
MAJOR_CHANGE_FILE=${CURRENT_DIR}/../api_compat_report/major.txt
[ -e $MAJOR_CHANGE_FILE ] && rm $MAJOR_CHANGE_FILE
REVAPI=`mktemp -d`
mkdir -p "${REVAPI}"
REVAPI_PATH="$REVAPI/revapi-${REVAPI_VERSION}"

if [[ -f "$REVAPI_PATH/revapi.sh" ]]; then
  echo "RevAPI already installed in $REVAPI_PATH"
fi
if ! [ -x "$(command -v revapi.sh)" ]; then
    echo "RevAPI is not installed. Installing RevAPI ${REVAPI_VERSION}"
    pushd "${REVAPI}" > /dev/null
    wget https://search.maven.org/remotecontent?filepath=org/revapi/revapi-standalone/${REVAPI_VERSION}/revapi-standalone-${REVAPI_VERSION}-standalone.zip -O revapi.zip \
    && unzip revapi.zip
    popd > /dev/null
fi
export PATH=$REVAPI_PATH:$PATH

RELEASE_TAG=${1-""}

CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
TARGET_BRANCH=$(gh pr view "$CURRENT_BRANCH" --json baseRefName --jq '.baseRefName')
echo "CURRENT_BRANCH: $CURRENT_BRANCH"
echo "TARGET_BRANCH: $TARGET_BRANCH"
VARIANTS=( private public )

# If "main" then we're targeting v10 releases
if [[ "$TARGET_BRANCH" = "main" ]]; then
  STABLE_RELEASE_TAG_PATTERN="v10\.[0-9]*\.0$"
  # use the latest stable minor release tag for main branch
  LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep "$STABLE_RELEASE_TAG_PATTERN" | head -n 1)
# If "develop" then we're targeting v11 releases
elif [[ "$TARGET_BRANCH" = "develop" ]]; then
  STABLE_RELEASE_TAG_PATTERN="v11\.[0-9]*\.0$"
  # use the latest stable minor release tag for develop branch if available or empty if not
  LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep "$STABLE_RELEASE_TAG_PATTERN" | head -n 1 || true)
  if [ -z "$LAST_STABLE_RELEASE_TAG" ]; then
    VARIANTS=( public )
    echo "========================  No v11 stable release for $TARGET_BRANCH found. Comparing against most recent one. ===================================="
    STABLE_RELEASE_TAG_PATTERN="v11\.[0-9]*\.[0-9]*.*$"
    # use the latest release tag for develop branch if available (e.g. [v11.0.0-alpha.1])
    LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep "$STABLE_RELEASE_TAG_PATTERN" | head -n 1)
  fi
else
  LATEST_MINOR_THIS_BRANCH=$(git tag --sort=-creatordate | { grep "^$TARGET_BRANCH\.0$" || test $? = 1; } | head -n 1) # grep sets error if result is empty, so `test` is needed
  if [ -z "$LATEST_MINOR_THIS_BRANCH" ]; then
    # no minor releases found in this release branch - means we're in the beta/rc stage
    # then compare with the previous stable minor release
    readonly MINOR_VERSION=${TARGET_BRANCH:4}
    readonly PREVIOUS_MINOR=$(($MINOR_VERSION - 1))
    readonly PREVIOUS_VERSION="v10\.${PREVIOUS_MINOR}\.0$"
    # use minor release from the previous branch
    # during rc/beta release compare with the latest previous minor release
    LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep ${PREVIOUS_VERSION} | head -n 1)
  else
    # during patch release compare with the previous release in this branch
    LAST_STABLE_RELEASE_TAG=$LATEST_MINOR_THIS_BRANCH
  fi
fi
echo "LAST_STABLE_RELEASE_TAG: $LAST_STABLE_RELEASE_TAG"
echo "Release tag: $RELEASE_TAG"
echo "Variants: $VARIANTS"
MODULES=$(sed -e '/include(/,/)/!d' ./settings.gradle.kts | tr -d '\n' | sed 's/.*(\([^]]*\)).*/\1/g;s/,\n/\n/g' | tr , \\n | sed 's/.*":\([^]]*\)".*/\1/g')
for i in $MODULES; do
  for variant in "${VARIANTS[@]}"; do
    echo "========================  Checking module: $i (variant $variant) ===================================="
    AAR_PATH="./$i/build/outputs/aar/$i-$variant-release.aar"
    if [ -f $AAR_PATH ]; then
      MODULE_NAME=$i
      if [[ $i == plugin* ]]; then
        MODULE_NAME=${MODULE_NAME: 7}
      elif [[ $i == extension* ]]; then
        MODULE_NAME=${MODULE_NAME: 10}
      elif [[ $i == *telemetry* ]]; then
        continue
      fi
      echo "========================  module aar name: $MODULE_NAME  ===================================="
      "${CURRENT_DIR}"/java-api-check.sh "$RELEASE_TAG" "$AAR_PATH" "$MODULE_NAME" "$LAST_STABLE_RELEASE_TAG"
    else
      echo "======================== WARNING: no release aar file found: $AAR_PATH ===================================="
    fi
  done
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
