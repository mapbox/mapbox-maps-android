.PHONY: check
check:
	./gradlew ktlint lint;
	python scripts/license-validate.py;
	sh scripts/kdoc-validate.sh;

.PHONY: test
test:
	./gradlew test -i

.PHONY: build
build:
	./gradlew assemble

.PHONY: proguard
proguard:
	./gradlew clean;
	./gradlew app:installRelease;
	adb shell am start -n com.mapbox.maps.testapp/.examples.SimpleMapActivity;
	xdg-open app/build/outputs/mapping/release/mapping.txt;

.PHONY: fix
fix:
	./gradlew ktlintFormat

.PHONY: sdkRegistryUpload
sdkRegistryUpload:
	./gradlew mapboxSDKRegistryUpload;

.PHONY: sdkRegistryPublish
sdkRegistryPublish:
	python3 -m pip install git-pull-request;
	./gradlew mapboxSDKRegistryPublishAll;

.PHONY: clean
clean:
	./gradlew clean

.PHONY: codecoverage
codecoverage:
	./gradlew sdk:jacocoTestDebugUnitTestReport && google-chrome sdk/build/jacoco/jacocoHtml/index.html

.PHONY: dokka-html
dokka-html:
	./gradlew dokkaHtmlCollector

.PHONY: dokka-javadoc
dokka-javadoc:
	./gradlew dokkaJavadocCollector

.PHONY: prepare-release-doc
prepare-release-doc: dokka-html
	mkdir -p release-docs;
	cd build/dokka && zip -r dokka-docs.zip htmlCollector && cd -;
	rm -r app/build;
	zip -r examples.zip app;
	cp examples.zip release-docs/;
	cp build/dokka/dokka-docs.zip release-docs/;
	zip -r release-docs.zip release-docs;

.PHONY: unit-tests
unit-tests:
	./gradlew test -i

.PHONY: instrumentation-tests
instrumentation-tests:
	./gradlew sdk:connectedDebugAndroidTest && ./gradlew app:connectedDebugAndroidTest

.PHONY: instrumentation-clean
instrumentation-clean:
	adb uninstall com.mapbox.maps.testapp || true;
	adb uninstall com.mapbox.maps.testapp.test || true;

# Generates Activity sanity tests
.PHONY: generate-sanity-test
generate-sanity-test:
	node scripts/sanity-test/generate-sanity-test.js

# Setup building from source with including a specific commit SHA
.PHONY: build-source-init-%
build-source-init:
	python scripts/build-from-source/builder/build-from-source.py;
	git clone git@github.com:mapbox/mapbox-gl-native-internal.git;
	cd mapbox-gl-native-internal && git checkout $* && git submodule update --init --recursive

# Refresh building from source setup without cloning upstream repository
.PHONY: build-source-update
build-source-update:
	python scripts/build-from-source/builder/build-from-source.py;

# Remove building from source setup, return to building from binaries
.PHONY: build-source-remove
build-source-remove:
	git restore sdk-base/build.gradle.kts;
	git restore sdk/build.gradle.kts;
	git restore build.gradle.kts;
	git restore settings.gradle.kts;
	rm -rf mapbox-gl-native-internal;

# Clean build folders related to building from source
.PHONY: build-source-clean
build-source-clean:
	git restore sdk-base/build.gradle.kts;
	git restore sdk/build.gradle.kts;
	git restore build.gradle.kts;
	git restore settings.gradle.kts;
	rm -rf app/build;
	rm -rf app/.cxx;
	rm -rf mapbox-gl-native-internal/internal/platform/android/sdk/.cxx;
	rm -rf mapbox-gl-native-internal/internal/platform/android/sdk/build;
	rm -rf mapbox-gl-native-internal/internal/vendor/common/platform/android/common/.cxx;
	rm -rf mapbox-gl-native-internal/internal/vendor/common/platform/android/common/build;
