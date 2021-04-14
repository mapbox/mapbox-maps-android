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

DOKKA_OUTPUT_DIR="./build/dokka/htmlCollector"

readonly ANDROID_DOCS_DIRECTORY="android-docs-repo"
readonly CONSTANTS_FILE="./src/constants.json"
readonly MAP_VERSION_NUMBERS_FILE="./src/data/map-version-numbers.json"
readonly BRANCH_WITH_DOCUMENTATION="publisher-production"
readonly ANDROID_DOCS_REPO="git@github.com:mapbox/android-docs.git"

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

function generate_docs() {
  make dokka-html || dokka_result=1

  if [ ${dokka_result:-0} -eq 1 ]; then
    echo "dokka failed, ignoring."
  fi

  if [ ! -d $DOKKA_OUTPUT_DIR ]; then
    echo "Directory with docs doesn't exist."
    exit 1
  fi
}

function prepare_branch_with_documentation() {
  INTERIM_BRANCH_WITH_DOCUMENTATION="${BRANCH_WITH_DOCUMENTATION}_${1}"
  git checkout -f $BRANCH_WITH_DOCUMENTATION
  mkdir -p $1
  cp -r $DOKKA_OUTPUT_DIR/* $1
  git add $1
  git commit -m "Add $1 API documentation."
  git checkout -b $INTERIM_BRANCH_WITH_DOCUMENTATION
  git push --set-upstream origin $INTERIM_BRANCH_WITH_DOCUMENTATION
}

function clone_android_docs_repo() {
  git clone $ANDROID_DOCS_REPO $ANDROID_DOCS_DIRECTORY
}

function update_constants_and_map_version_numbers() {
  cd $ANDROID_DOCS_DIRECTORY
  CONSTANTS_FILE_TMP="${CONSTANTS_FILE}.tmp"
  jq --arg version $1 '.MAP_SDK_v10_VERSION=$version' $CONSTANTS_FILE >$CONSTANTS_FILE_TMP
  mv $CONSTANTS_FILE_TMP $CONSTANTS_FILE

  MAP_VERSION_NUMBERS_FILE_TMP="${MAP_VERSION_NUMBERS_FILE}.tmp"
  if [[ $1 == *beta* ]] || [[ $1 == *alpha* ]]; then
    jq --arg version $1 '.[-1] |= $version' $MAP_VERSION_NUMBERS_FILE >$MAP_VERSION_NUMBERS_FILE_TMP
  else
    jq --arg version $1 '. += [$version]' $MAP_VERSION_NUMBERS_FILE >$MAP_VERSION_NUMBERS_FILE_TMP
  fi
  mv $MAP_VERSION_NUMBERS_FILE_TMP $MAP_VERSION_NUMBERS_FILE
  cd -
}

function prepare_android_docs_branch() {
  cd $ANDROID_DOCS_DIRECTORY
  BRANCH_NAME="maps_android_sdk_v$1"
  git checkout -b $BRANCH_NAME
  git add -A
  git commit -m "Carbon Maps SDK bump to $1"
  git push --set-upstream origin $BRANCH_NAME
  cd -
}

function create_pull_request() {
  CMD="gh pr create --title \"${1}\" --body \"cc: @mapbox/maps-android\""

  if [ ! -z "$REVIEWERS" ]; then
    CMD+=" --reviewer ${REVIEWERS}"
  fi

  if [ ! -z "${2:-}" ]; then
    CMD+=" --base ${BRANCH_WITH_DOCUMENTATION}"
  fi
  eval $CMD
}

gh auth login --with-token < gh_token.txt

# Generate docs, create branch and make PR with API documentation in the SDK repo.
generate_docs
prepare_branch_with_documentation $MAPS_SDK_VERSION
create_pull_request "Add ${MAPS_SDK_VERSION} API documentation." $BRANCH_WITH_DOCUMENTATION

## Update config files in Android Docs Repo.
clone_android_docs_repo
update_constants_and_map_version_numbers $MAPS_SDK_VERSION
prepare_android_docs_branch $MAPS_SDK_VERSION
cd $ANDROID_DOCS_DIRECTORY
create_pull_request "Carbon Maps SDK bump to ${MAPS_SDK_VERSION}" $BRANCH_WITH_DOCUMENTATION
cd -
