#!/bin/bash -eo pipefail
function validate {
  echo "Validating..."
  if [[ "$*" == *"Undocumented: com.mapbox.maps"* ]];
  then
    echo 'kdoc validation failed'
    exit 1
  fi
  if [[ "$*" == *"Gradle build daemon disappeared unexpectedly"* ]];
  then
    echo 'Gradle build daemon disappeared unexpectedly'
    exit 1
  fi
}

echo Started docs validation, it may take some time...
gradle_command="./gradlew clean dokkaHtml -Pmapbox.dokkaHtmlFlavor=publicRelease"
echo $gradle_command
gradle_output="$( $gradle_command 2>&1)"
validate $gradle_output
gradle_command="./gradlew clean dokkaHtml -Pmapbox.dokkaHtmlFlavor=privateRelease"
echo $gradle_command
gradle_output="$( $gradle_command 2>&1)"
validate $gradle_output
exit 0