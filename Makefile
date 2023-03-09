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
	./gradlew plugin-viewport:assembleRelease;

# Use `make checkApi TAG=ReleaseTag` to check the release tag or `make checkApi` to check current branch while running locally.
# Note: if run locally, execute `mbx env` and `./gradlew assembleRelease` first.
.PHONY: checkApi
checkApi:
	unset GITHUB_TOKEN; \
	echo "$(shell mbx-ci github reader token)" > gh_token.txt;\
	sh scripts/java-api-check-all.sh "$(TAG)"

# Note: if run locally, execute `mbx env` first.
.PHONY: checkChangelog
checkChangelog:
	unset GITHUB_TOKEN; \
	echo "$(shell mbx-ci github reader token)" > gh_token.txt;\
	sh scripts/check-changelog.sh

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
	./gradlew mapboxSDKRegistryUpload -x extension-androidauto:mapboxSDKRegistryUpload --no-parallel --no-daemon;

.PHONY: sdkRegistryPublicReleaseUpload
sdkRegistryPublicReleaseUpload:
	./gradlew mapboxSDKRegistryPublicReleaseUpload -x extension-androidauto:mapboxSDKRegistryPublicReleaseUpload --no-parallel --no-daemon;

.PHONY: sdkRegistryPrivateReleaseUpload
sdkRegistryPrivateReleaseUpload:
	./gradlew mapboxSDKRegistryPrivateReleaseUpload -x extension-androidauto:mapboxSDKRegistryPrivateReleaseUpload --no-parallel --no-daemon;

.PHONY: sdkRegistryPublish
sdkRegistryPublish:
	./gradlew mapboxSDKRegistryPublishAll;

.PHONY: sdkRegistryPublicReleasePublish
sdkRegistryPublicReleasePublish:
	./gradlew mapboxSDKRegistryPublicReleasePublishAll;

.PHONY: sdkRegistryPrivateReleasePublish
sdkRegistryPrivateReleasePublish:
	./gradlew mapboxSDKRegistryPrivateReleasePublishAll;

.PHONY: sdkRegistryUploadPublicReleaseAndroidAutoExtension
sdkRegistryUploadPublicReleaseAndroidAutoExtension:
	./gradlew extension-androidauto:mapboxSDKRegistryPublicReleaseUpload;

.PHONY: sdkRegistryPublishPublicReleaseAndroidAutoExtension
sdkRegistryPublishPublicReleaseAndroidAutoExtension:
	./gradlew extension-androidauto:mapboxSDKRegistryPublicReleasePublish;

.PHONY: clean
clean:
	./gradlew clean

.PHONY: codecoverage
codecoverage:
	./gradlew sdk:jacocoTestPublicDebugUnitTestReport && google-chrome sdk/build/jacoco/jacocoHtml/index.html

# Use `make generate-changelog TAG=LastReleaseTag` while running locally.
.PHONY: generate-changelog
generate-changelog:
	unset GITHUB_TOKEN; \
	export GITHUB_TOKEN=$(shell mbx-ci github reader token); \
	changelog-draft -b main -p $(TAG) -o CHANGELOG.md

# Use `make update-android-docs TAG=YourReleaseTag` while running locally.
# Run `make prepare-public-release-doc` or `make prepare-private-release-doc` first in the internal repository,
# Note: if run locally, execute `mbx env` first.
.PHONY: update-android-docs
update-android-docs:
	unset GITHUB_TOKEN; \
	echo "$(shell mbx-ci github writer private token)" > gh_token.txt;\
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

# Metalava & kotlin binary compatibility validator: check API
.PHONY: check-api
check-api:
	# validate api surface for both metalava and kotlin binary compatibility validator
	./gradlew checkApi;

# Metalava & kotlin binary compatibility validator: update API
.PHONY: update-api
update-api:
	# generate api file for both metalava and kotlin binary compatibility validator
	./gradlew updateApi;

# Metalava: update metalava version
.PHONY: update-metalava
update-metalava:
	sh ./metalava/update.sh

.PHONY: start-android-auto-dhu
start-android-auto-dhu:
	adb forward tcp:5277 tcp:5277;
	$(ANDROID_HOME)/extras/google/auto/desktop-head-unit;


# Check permissions app module, requires app:assembleDebug first
.PHONY: check-permissions
check-permissions:
	 python3 scripts/check-permissions.py \
	 	--apk app/build/outputs/apk/public/debug/app-public-debug.apk \
		--file app/permission.json

# Update permissions app module, requires app:assembleDebug first
.PHONY: update-permissions
update-permissions:
	 python3 scripts/check-permissions.py \
	 	--apk app/build/outputs/apk/public/debug/app-public-debug.apk \
		--file app/permission.json \
		--update True
