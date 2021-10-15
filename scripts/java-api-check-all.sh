#!/usr/bin/env bash

set -Eeuxo pipefail
# Usage:
#   ./java-api-check-all.sh <current release tag (empty for branches)>
#
CURRENT_DIR=$(dirname "$0")
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

RELEATE_TAG=${1-""}
echo "Release tag: $RELEATE_TAG"
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
    echo "Checking module: $i, module aar name: $MODULE_NAME"
    "${CURRENT_DIR}"/java-api-check.sh "$RELEATE_TAG" "./$i/build/outputs/aar/$i-release.aar" "$MODULE_NAME"
  fi
done

rm -rf "${REVAPI}"
