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

.PHONY: checkApi
checkApi:
	sh ./scripts/java-api-check.sh "$(TAG)" "./sdk/build/outputs/aar/sdk-release.aar" "sdk"
	sh ./scripts/java-api-check.sh "$(TAG)" "./sdk-base/build/outputs/aar/sdk-base-release.aar" "base"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-scalebar/build/outputs/aar/plugin-scalebar-release.aar" "scalebar"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-overlay/build/outputs/aar/plugin-overlay-release.aar" "overlay"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-logo/build/outputs/aar/plugin-logo-release.aar" "logo"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-locationcomponent/build/outputs/aar/plugin-locationcomponent-release.aar" "locationcomponent"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-lifecycle/build/outputs/aar/plugin-lifecycle-release.aar" "lifecycle"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-gestures/build/outputs/aar/plugin-gestures-release.aar" "gestures"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-compass/build/outputs/aar/plugin-compass-release.aar" "compass"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-attribution/build/outputs/aar/plugin-attribution-release.aar" "attribution"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-annotation/build/outputs/aar/plugin-annotation-release.aar" "annotation"
	sh ./scripts/java-api-check.sh "$(TAG)" "./plugin-animation/build/outputs/aar/plugin-animation-release.aar" "animation"
	sh ./scripts/java-api-check.sh "$(TAG)" "./extension-style/build/outputs/aar/extension-style-release.aar" "style"
	sh ./scripts/java-api-check.sh "$(TAG)" "./extension-localization/build/outputs/aar/extension-localization-release.aar" "localization"
	sh ./scripts/java-api-check.sh "$(TAG)" "./extension-androidauto/build/outputs/aar/extension-androidauto-release.aar" "androidauto"

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

# Use `make generate-changelog TAG=LastReleaseTag` while running locally.
.PHONY: generate-changelog
generate-changelog:
	unset GITHUB_TOKEN; \
	export GITHUB_TOKEN=$(shell mbx-ci github reader token); \
	changelog-draft -b main -p $(TAG) -o CHANGELOG.md

# Use `make update-android-docs TAG=YourReleaseTag` while running locally.
.PHONY: update-android-docs
update-android-docs:
	unset GITHUB_TOKEN; \
	mbx-ci github writer opt-in mapbox-maps-android; \
	mbx-ci github writer opt-in android-docs; \
	echo "$(shell mbx-ci github reader token)" > gh_token.txt;\
	sh scripts/update-android-docs.sh -s $(TAG)

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