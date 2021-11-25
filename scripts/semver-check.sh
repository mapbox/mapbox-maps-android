#!/usr/bin/env bash

set -eo pipefail

#
# Usage:
#   ./scripts/semver-check.sh <current release version (empty for branches)> <previous release version> <api compat string (major|minor|patch)>
#

if [[ $# -ne 3 ]]; then
    echo "Incorrect number of parameters"
    exit 1
fi

TAGGED_RELEASE_VERSION=$1
LAST_VERSION=$2
API_COMPAT_LEVEL=$3

echo "Semver-check TAGGED_RELEASE_VERSION:$TAGGED_RELEASE_VERSION, LAST_VERSION: $LAST_VERSION, API_COMPAT_LEVEL: $API_COMPAT_LEVEL"
# Checking if tagged release has API breaks
if [[ ! -z ${TAGGED_RELEASE_VERSION} ]]; then
    SEMVER_REGEX="(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(\\-[0-9A-Za-z-]+(\\.[0-9A-Za-z-]+)*)?(\\+[0-9A-Za-z-]+(\\.[0-9A-Za-z-]+)*)?"
    if [[ ${TAGGED_RELEASE_VERSION} =~ $SEMVER_REGEX ]]; then
        newMajor=${BASH_REMATCH[1]}
        newMinor=${BASH_REMATCH[2]}
        newPatch=${BASH_REMATCH[3]}
    else
        echo "Could not parse new release version (${TAGGED_RELEASE_VERSION})" >&2
        exit 1
    fi

    if [[ ${LAST_VERSION} =~ $SEMVER_REGEX ]]; then
        oldMajor=${BASH_REMATCH[1]}
        oldMinor=${BASH_REMATCH[2]}
        oldPatch=${BASH_REMATCH[3]}
    else
        echo "Could not parse old release version (${LAST_VERSION})" >&2
        exit 1
    fi

    if [[ ${newMajor} == $((oldMajor + 1)) ]]; then
        echo "Major API breaking change."
    elif [[ ${newMajor} > $((oldMajor + 1)) ]] || [[ ${newMajor} < $((oldMajor)) ]] || [[ ${API_COMPAT_LEVEL} == "major" && ${newMajor} != $((oldMajor + 1)) ]]; then
        echo "Major version number changed incorrectly."
        exit 1
    elif [[ ${API_COMPAT_LEVEL} != "major" ]]; then
        if [[ ${newMinor} < $((oldMinor)) ]]; then
            echo "Minor version must not be smaller than the old minor version."
            exit 1
        elif [[ ${newMinor} == $((oldMinor)) && ${newPatch} < $((oldPatch)) ]]; then
            echo "Patch version number must not be smaller than old patch version."
            exit 1
        fi
    fi
fi
echo "Release tagged with correct version."
