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
# By default we start with v11 as major version
MAJOR_VERSION="11"

if [[ "$TARGET_BRANCH" = "main" ]]; then
  STABLE_RELEASE_TAG_PATTERN="v${MAJOR_VERSION}\.[0-9]*\.0$"
  # use the latest stable minor release tag for main branch if available or empty if not
  LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep "$STABLE_RELEASE_TAG_PATTERN" | head -n 1 || true)
  if [ -z "$LAST_STABLE_RELEASE_TAG" ]; then
    echo "========================  No v${MAJOR_VERSION} stable release for $TARGET_BRANCH found. Comparing against most recent one. ===================================="
    STABLE_RELEASE_TAG_PATTERN="v${MAJOR_VERSION}\.[0-9]*\.[0-9]*.*$"
    # use the latest release tag for main branch if available (e.g. [v11.0.0-alpha.1])
    LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep "$STABLE_RELEASE_TAG_PATTERN" | head -n 1)
  fi
else
  # Regardless of target branch being "v10.15" or "release/v10.15" we extract everything after the right-most 'v'
  readonly MAJOR_MINOR_VERSION="${TARGET_BRANCH##*v}"
  MAJOR_VERSION=$(cut -d "." -f 1 <<< "$MAJOR_MINOR_VERSION")
  readonly MINOR_VERSION=$(cut -d "." -f 2 <<< "$MAJOR_MINOR_VERSION")
  echo "MAJOR_VERSION: $MAJOR_VERSION"
  echo "MINOR_VERSION: $MINOR_VERSION"

  # Check if there's at least the tag for the first minor (e.g. v10.16.0)
  LATEST_MINOR_THIS_BRANCH=$(git tag --sort=-creatordate | { grep "^v$MAJOR_MINOR_VERSION\.0$" || test $? = 1; } | head -n 1) # grep sets error if result is empty, so `test` is needed
  if [ -z "$LATEST_MINOR_THIS_BRANCH" ]; then
    # no minor releases found in this release branch - means we're in the beta/rc stage
    echo "First minor v${MAJOR_MINOR_VERSION}.0 not found ($LATEST_MINOR_THIS_BRANCH)"
    # Check if this is the very first minor for this major (i.e. "v11.0")
    if [ "$MINOR_VERSION" -eq 0 ]; then
      # Then we use a regex to get the latest created tag for v11.0 which could be v11.0.0-rc.1
      readonly PREVIOUS_TAG_VERSION="v${MAJOR_VERSION}\.${MINOR_VERSION}\.0.*"
    else
      # then compare with the previous stable minor release
      readonly PREVIOUS_MINOR=$(($MINOR_VERSION - 1))
      readonly PREVIOUS_TAG_VERSION="v${MAJOR_VERSION}\.${PREVIOUS_MINOR}\.0$"
    fi
    echo "PREVIOUS_TAG_VERSION=$PREVIOUS_TAG_VERSION"
    # use minor release from the previous branch
    # during rc/beta release compare with the latest previous minor release
    LAST_STABLE_RELEASE_TAG=$(git tag --list --sort=-creatordate | grep ${PREVIOUS_TAG_VERSION} | head -n 1)
  else
    # during patch release compare with the previous release in this branch
    LAST_STABLE_RELEASE_TAG=$LATEST_MINOR_THIS_BRANCH
  fi
fi
echo "LAST_STABLE_RELEASE_TAG: $LAST_STABLE_RELEASE_TAG"
echo "Release tag: $RELEASE_TAG"

# Only v10 releases have private and public flavour
if [ "$MAJOR_VERSION" -eq 10 ]; then
  VARIANTS=( private public )
fi

function check_module() {
  local module=$1
  local variant=${2:-}
  if [ ! -z "$variant" ]; then
    variant="-$variant"
  fi
  echo "========================  Checking module: $module (variant $variant) ===================================="

  if [[ $module == *telemetry* ]] || [[ $module == *app ]] || [[ $module == *-lint-rules ]]; then
    # Skip telemetry, app and lint-rules modules
    echo "Skipping  module name: $module"
    return
  fi

  # Check if the AAR file exists
  local AAR_PATH="./$module/build/outputs/aar/$module$variant-release.aar"
  if [ -f $AAR_PATH ]; then
    # Get the module name
    local MODULE_NAME=$module
    if [[ $module == plugin* ]]; then
      MODULE_NAME=${MODULE_NAME: 7}
    elif [[ $module == extension* ]]; then
      MODULE_NAME=${MODULE_NAME: 10}
    fi

    # Print the module name
    echo "========================  module aar name: $MODULE_NAME at $AAR_PATH  ===================================="

    # Check the Java API
    "${CURRENT_DIR}"/java-api-check.sh "$RELEASE_TAG" "$AAR_PATH" "$MODULE_NAME" "$LAST_STABLE_RELEASE_TAG"
  else
    echo "======================== ERROR: no release aar file found: $AAR_PATH ===================================="
    exit 1
  fi
}


echo "Variants: ${VARIANTS[*]}"
MODULES=$(sed -e '/include(/,/)/!d' ./settings.gradle.kts | tr -d '\n' | sed 's/.*(\([^]]*\)).*/\1/g;s/,\n/\n/g' | tr , \\n | sed 's/.*":\([^]]*\)".*/\1/g')
echo "Modules found:"
echo $MODULES
for i in $MODULES; do
  if [ -z "${VARIANTS[@]}" ]; then
    check_module $i
  else
    for variant in "${VARIANTS[@]}"; do
      check_module $i $variant
    done
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
