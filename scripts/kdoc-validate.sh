#!/bin/bash -eo pipefail
./gradlew clean
echo Started docs validation, it may take some time...
./gradlew dokkaHtml | grep -i 'Undocumented: com.mapbox.maps' && { echo 'kdoc validation failed'; exit 1; }
exit 0