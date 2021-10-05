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
	./gradlew sdk-base:assembleRelease;
	./gradlew sdk:assembleRelease;
	./gradlew extension-style:assembleRelease;
	./gradlew extension-localization:assembleRelease;
	./gradlew extension-androidauto:assembleRelease;
	./gradlew module-telemetry:assembleRelease;
	./gradlew plugin-animation:assembleRelease;
	./gradlew plugin-annotation:assembleRelease;
	./gradlew plugin-attribution:assembleRelease;	
	./gradlew plugin-compass:assembleRelease;
	./gradlew plugin-gestures:assembleRelease;
	./gradlew plugin-locationcomponent:assembleRelease;
	./gradlew plugin-logo:assembleRelease;
	./gradlew plugin-overlay:assembleRelease;
	./gradlew plugin-scalebar:assembleRelease;
	./gradlew plugin-lifecycle:assembleRelease;

# Use `make checkApi TAG=ReleaseTag` to check the release tag or `make checkApi` to check current branch while running locally.
.PHONY: checkApi
checkApi:
	./scripts/java-api-check-all.sh "$(TAG)"

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

# Use `make generate-changelog TAG=LastReleaseTag` while running locally.
.PHONY: generate-changelog
generate-changelog:
	unset GITHUB_TOKEN; \
	export GITHUB_TOKEN=$(shell mbx-ci github reader token); \
	changelog-draft -b main -p $(TAG) -o CHANGELOG.md

# Use `make update-android-docs TAG=YourReleaseTag` while running locally.
# Run `make prepare-release-doc` first in the internal repository,
.PHONY: update-android-docs
update-android-docs:
	unset GITHUB_TOKEN; \
	mbx-ci github writer opt-in mapbox-maps-android; \
	mbx-ci github writer opt-in android-docs; \
	echo "$(shell mbx-ci github reader token)" > gh_token.txt;\
	sh scripts/update-android-docs.sh -s $(TAG)

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

# Metalava: check API
.PHONY: check-api
check-api:
	./gradlew checkApi

# Metalava: update API
.PHONY: update-api
update-api:
	./gradlew updateApi

# Metalava: update metalava version
.PHONY: update-metalava
update-metalava:
	sh ./metalava/update.sh

.PHONY: start-android-auto-dhu
start-android-auto-dhu:
	adb forward tcp:5277 tcp:5277;
	$(ANDROID_HOME)/extras/google/auto/desktop-head-unit;