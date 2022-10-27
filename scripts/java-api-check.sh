#!/usr/bin/env bash

set -Eeuo pipefail

# Usage:
#   ./java-api-check.sh
#     <current release tag (empty for branches)>
#     <path to current aar>
#     <module name>
#     <last stable release>
#     <optional, path to previously released aar>
#
echo "\$1:$1"
echo "\$2:$2"
echo "\$3:$3"
echo "\$4:$4"

if [[ ! -f $2 ]]
then
  # Assembled aar located under moduleName/build/outputs/aar/moduleName-release.aar
  echo "$2 does not exists, please provide path to the current sdk-release.aar file, current dir: $(dirname "$0")"
  exit 1
fi

AAR_PATH=$2
MODULE_NAME=$3

if [ -z "$MODULE_NAME" ]
then
  echo "Please provide the module name"
  exit 1
fi

CURRENT_DIR=$(dirname "$0")
TMPDIR=`mktemp -d`
REPORT_DIR=${CURRENT_DIR}/../api_compat_report/$MODULE_NAME
MAJOR_CHANGE_FILE=${CURRENT_DIR}/../api_compat_report/major.txt
mkdir -p "${REPORT_DIR}"
mkdir -p "${TMPDIR}"

if ! [ -x "$(command -v revapi.sh)" ]; then
    echo "Revapi is not installed. Please install revapi before running this script"
    exit 1
fi
# Fetched by parent script from circle-ci CIRCLE_TAG env variable,
# if CIRCLE_TAG is not set, TAGGED_RELEASE_VERSION would be empty (branch build).
TAGGED_RELEASE_VERSION=$1
TAGGED_RELEASE_VERSION=${TAGGED_RELEASE_VERSION:8}

mkdir "${TMPDIR}"/current "${TMPDIR}"/previous
CURRENT_RELEASE=${TMPDIR}/current/sdk-release.aar
cp "$2" "${CURRENT_RELEASE}"
PREVIOUS_RELEASE=${TMPDIR}/previous/sdk-release.aar
CURRENT_RELEASE_DIR=$(dirname "${CURRENT_RELEASE}")
PREVIOUS_RELEASE_DIR=$(dirname "${PREVIOUS_RELEASE}")

LAST_STABLE_VERSION_TAG=$4 #v10.3.0
LAST_STABLE_VERSION_TAG_ARRAY=($LAST_STABLE_VERSION_TAG)
LAST_STABLE_VERSION=${LAST_STABLE_VERSION_TAG_ARRAY[0]:1}

RELEASE_TAG=${5-""}
if [[ -z $RELEASE_TAG ]]; then
  echo "Using the ${LAST_STABLE_VERSION} release from the SDK Registry to check api compatibility with revapi."

  AAR_GROUP_NAME=""
  AAR_MODULE_NAME=""
  if [[ $MODULE_NAME == sdk ]]; then
    AAR_MODULE_NAME="android"
    AAR_GROUP_NAME="maps"
  elif [[ $MODULE_NAME == sdk-base ]]; then
    AAR_MODULE_NAME="base"
    AAR_GROUP_NAME="maps"
  elif [[ $MODULE_NAME == androidauto ]]; then
    echo "Android auto module is not yet released, skip the check."
    exit 0
  elif [[ $AAR_PATH == *"extension"* ]]; then
    AAR_GROUP_NAME="extension"
    AAR_MODULE_NAME="maps-$MODULE_NAME"
  elif [[ $AAR_PATH == *"plugin"* ]]; then
    AAR_GROUP_NAME="plugin"
    AAR_MODULE_NAME="maps-$MODULE_NAME"
  fi

  if [[ -z $AAR_GROUP_NAME ]]; then
    echo "Invalid aar group name for the module $MODULE_NAME"
    exit 1;
  fi

  if [[ -z $AAR_MODULE_NAME ]]; then
    echo "Invalid aar module name for the module $MODULE_NAME"
    exit 1;
  fi

  AAR_PATH="https://api.mapbox.com/downloads/v2/releases/maven/com/mapbox/${AAR_GROUP_NAME}/"${AAR_MODULE_NAME}"/"${LAST_STABLE_VERSION}"/"${AAR_MODULE_NAME}"-"${LAST_STABLE_VERSION}".aar"

  # -fL0 will fail if aar is not present on the sdk registry
  curl -fL0 --user mapbox:${SDK_REGISTRY_TOKEN} ${AAR_PATH} --output "${PREVIOUS_RELEASE}"
else
  echo "Using the prebuilt ${RELEASE_TAG} to check api compatibility with revapi."
  cp "$RELEASE_TAG" "$PREVIOUS_RELEASE"
fi

extract_classess_jar() {
    aar=$1

    tmpdir=$(mktemp -d)
    unzip -qq -d "${tmpdir}" "${aar}" classes.jar

    output=$(mktemp)
    mv "${tmpdir}/classes.jar" "${output}"
    rm -rf "${tmpdir}"

    echo "${output}"
}

parse_json_report() {
    node - <<'EOF' "$1"
const fs = require('fs');

const data = fs.readFileSync(process.argv[2]).toString();
const report = JSON.parse(data);

const issues = report.reduce((acc, r) => {
    const binary = r.classification.find(e => e.compatibility === "BINARY");
    const source = r.classification.find(e => e.compatibility === "SOURCE");
    const semantic = r.classification.find(e => e.compatibility === "SEMANTIC");

    if (binary && binary.severity === "BREAKING") {
        acc.major++;
    } else if (source) {
        if (source.severity === "BREAKING" && semantic && semantic.severity === "BREAKING") {
            acc.major++;
        } else if (source.severity === "BREAKING" && semantic && semantic.severity === "POTENTIALLY_BREAKING") {
            acc.minor++;
        }
    }

    return acc;
}, {minor: 0, major: 0});

console.log(`MAJOR_PROBLEMS=${issues.major}`);
console.log(`MINOR_PROBLEMS=${issues.minor}`);
EOF
}

compare_aars() {
    aar1=$1
    aar2=$2

    if [ -z "$aar1" ] || [ -z "$aar2" ]; then
        echo "Invalid argument. usage: ${FUNCNAME[0]} <old aar> <new aar>"
        exit 1
    fi

    jar1=$(extract_classess_jar "${aar1}")
    jar2=$(extract_classess_jar "${aar2}")
    json_report=$(mktemp)

    revapi.sh \
        --extensions=org.revapi:revapi-java:0.21.0,org.revapi:revapi-reporter-json:0.1.0,org.revapi:revapi-reporter-text:0.11.0 \
        -o "${jar1}" -n "${jar2}" \
        -D revapi.java.missing-classes.behavior=report \
        -D revapi.java.missing-classes.ignoreMissingAnnotations=false \
        -D revapi.java.non-public-part-of-api.report-unchanged=false \
        -D revapi.reporter.json.minSeverity=EQUIVALENT \
        -D revapi.reporter.json.output="${json_report}" \
        -D revapi.reporter.text.minSeverity=BREAKING \
        -D revapi.reporter.text.output="${REPORT_DIR}/api_compat.txt" \
        -c ${CURRENT_DIR}/java-api-check.json \
        >&2

    eval "$(parse_json_report "${json_report}")"

    if ((MAJOR_PROBLEMS > 0)); then
        echo major
    elif ((MINOR_PROBLEMS > 0)); then
        echo minor
    else
        echo patch
    fi

    rm "${jar1}" "${jar2}" "${json_report}"
}

api_compat=$(compare_aars "${PREVIOUS_RELEASE}" "${CURRENT_RELEASE}")
rm -rf "${TMPDIR}"
echo "Compare result: $api_compat"

if [[ $api_compat == major ]]; then
  echo "======================== ${MODULE_NAME} ========================" >> "${MAJOR_CHANGE_FILE}"
  cat ${REPORT_DIR}/api_compat.txt >> "${MAJOR_CHANGE_FILE}"
fi

"${CURRENT_DIR}"/semver-check.sh "${TAGGED_RELEASE_VERSION}" "${LAST_STABLE_VERSION}" "${api_compat}"