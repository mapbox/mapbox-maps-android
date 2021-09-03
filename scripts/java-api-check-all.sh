#!/usr/bin/env bash

set -eo pipefail
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

"${CURRENT_DIR}"/java-api-check.sh "$1" "./sdk/build/outputs/aar/sdk-release.aar" "sdk"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./sdk-base/build/outputs/aar/sdk-base-release.aar" "base"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-scalebar/build/outputs/aar/plugin-scalebar-release.aar" "scalebar"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-overlay/build/outputs/aar/plugin-overlay-release.aar" "overlay"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-logo/build/outputs/aar/plugin-logo-release.aar" "logo"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-locationcomponent/build/outputs/aar/plugin-locationcomponent-release.aar" "locationcomponent"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-lifecycle/build/outputs/aar/plugin-lifecycle-release.aar" "lifecycle"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-gestures/build/outputs/aar/plugin-gestures-release.aar" "gestures"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-compass/build/outputs/aar/plugin-compass-release.aar" "compass"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-attribution/build/outputs/aar/plugin-attribution-release.aar" "attribution"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-annotation/build/outputs/aar/plugin-annotation-release.aar" "annotation"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./plugin-animation/build/outputs/aar/plugin-animation-release.aar" "animation"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./extension-style/build/outputs/aar/extension-style-release.aar" "style"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./extension-localization/build/outputs/aar/extension-localization-release.aar" "localization"
"${CURRENT_DIR}"/java-api-check.sh "$1" "./extension-androidauto/build/outputs/aar/extension-androidauto-release.aar" "androidauto"

rm -rf "${REVAPI}"
