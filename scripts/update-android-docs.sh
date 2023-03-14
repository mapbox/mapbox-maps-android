#!/usr/bin/env bash

set -Eeuxo pipefail

#
# Requirements:
#   Save github token into gh_token.txt in root directory to access the GitHub API [https://github.com/settings/tokens].
#   Have following tools installed:
#     - git        - if you are a developer, you've heard about it [man 1 git].
#     - gh         - GitHub's official command line tool [https://github.com/cli/cli].
#     - jq         - command-line JSON processor [https://github.com/stedolan/jq].
#     - GNU getopt - cli option parser [man 1 getopt], out-of-the-box for GNU/Linux;
#

readonly ANDROID_DOCS_DIRECTORY="android-docs-repo"
readonly CONSTANTS_FILE="./src/constants.json"
readonly MAP_VERSION_NUMBERS_FILE="./src/data/map-version-numbers.json"
readonly BRANCH_WITH_DOCUMENTATION="publisher-production"

MAPS_SDK_VERSION=
REVIEWERS=

PROG=$(basename $0)
SHORT_OPTS=s:h
LONG_OPTS=sdk-version:,help

function usage() {
  message="Usage: $PROG -s SDK_VERSION \
           \nParameters:\
           \n  -s | --sdk-version       [version]                      Maps SDK version.\
           \n  -h | --help                                             Shows this help message."
  echo -e "$message"
}

if ! getopt --name "$PROG" --long $LONG_OPTS --options $SHORT_OPTS -- "$@" >/dev/null; then
  echo "$PROG: usage error (use -h or --help for help)" >&2
  exit 1
fi
ARGS=$(getopt --name "$PROG" --long $LONG_OPTS --options $SHORT_OPTS -- "$@")

while [ $# -gt 0 ]; do
  case "$1" in
      -s | --sdk-version) MAPS_SDK_VERSION="$2"; shift;;
      -r | --reviewers) REVIEWERS="$2"; shift;;
      -h | --help) usage; exit 0;;
      --) shift; break;;
  esac
  shift
done

if [ -z "$MAPS_SDK_VERSION" ]; then
  echo -e "ERROR: Map SDK version not defined.\n"
  usage
  exit 1
fi

#Split version name to remove the "v" prefix
MAPS_SDK_VERSION=${MAPS_SDK_VERSION:1}
read GITHUB_TOKEN < gh_token.txt

function prepare_branch_with_documentation() {
  INTERIM_BRANCH_WITH_DOCUMENTATION="${BRANCH_WITH_DOCUMENTATION}_${1}"
  git clone https://x-access-token:"$GITHUB_TOKEN"@github.com/mapbox/mapbox-maps-android.git -b $BRANCH_WITH_DOCUMENTATION ~/android-docs
  unzip -qq -o -d ~/android-docs/release-docs release-docs/dokka-docs.zip
  mkdir -p ~/android-docs/$1
  cp -fr ~/android-docs/release-docs/htmlCollector/* ~/android-docs/$1
  cd ~/android-docs
  # Create new working branch with version (e.g. `publisher-production_10.999.0`)
  git checkout -b $INTERIM_BRANCH_WITH_DOCUMENTATION
  git add $1
  git commit --quiet -m "Add $1 API documentation."
  git push --set-upstream origin $INTERIM_BRANCH_WITH_DOCUMENTATION --force
  cd -
}

function prepare_branch_with_empty() {
  cd ~/android-docs
  INTERIM_BRANCH_WITH_DOCUMENTATION="${BRANCH_WITH_DOCUMENTATION}_${1}_empty"
  git checkout -b $INTERIM_BRANCH_WITH_DOCUMENTATION origin/$BRANCH_WITH_DOCUMENTATION
  git commit --allow-empty -m "empty commit"
  git push --set-upstream origin $INTERIM_BRANCH_WITH_DOCUMENTATION --force
  cd -
}

function create_pull_request() {
  cd ~/android-docs
  CMD="gh pr create --title \"${1}\" --body \"cc: @mapbox/maps-android\""

  if [ ! -z "$REVIEWERS" ]; then
    CMD+=" --reviewer ${REVIEWERS}"
  fi

  if [ ! -z "${2:-}" ]; then
    CMD+=" --base ${BRANCH_WITH_DOCUMENTATION}"
  fi
  eval $CMD
  cd -
}

gh auth login --with-token < gh_token.txt

# Generate docs, create branch and make PR with API documentation in the SDK repo.
prepare_branch_with_documentation $MAPS_SDK_VERSION
create_pull_request "Add ${MAPS_SDK_VERSION} API documentation." $BRANCH_WITH_DOCUMENTATION

# Create a pr with empty commit to trigger dos deploy
prepare_branch_with_empty $MAPS_SDK_VERSION
create_pull_request "Trigger ${MAPS_SDK_VERSION} deploy." $BRANCH_WITH_DOCUMENTATION
