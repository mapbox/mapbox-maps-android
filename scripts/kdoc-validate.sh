#!/bin/bash -eo pipefail
./gradlew clean
echo Started docs validation, it may take some time...
gradle_output="$(./gradlew dokkaHtml)"
if [[ "$gradle_output" == *"Undocumented: com.mapbox.maps"* ]];
then
  echo 'kdoc validation failed'
  exit 1
fi
if [[ "$gradle_output" == *"Gradle build daemon disappeared unexpectedly"* ]];
then
  echo 'Gradle build daemon disappeared unexpectedly'
  exit 1
fi
exit 0