#!/bin/bash -eo pipefail
rm -rf ./gradle/configuration.gradle \
      ./app/build \
    	./app/.cxx \
    	./vendor/mapbox-gl-native-internal/internal/platform/android/sdk/.cxx \
    	./vendor/mapbox-gl-native-internal/internal/platform/android/sdk/build \
    	./vendor/mapbox-gl-native-internal/internal/vendor/common/platform/android/common/.cxx \
    	./vendor/mapbox-gl-native-internal/internal/vendor/common/platform/android/common/build
# Clean all build files for plugins / modules / sdk / extensions
for SUBDIR in $(ls -d */| grep -E 'plugin|extension|module|sdk')
do
  rm -rf ./$SUBDIR/build ./$SUBDIR/.cxx
done