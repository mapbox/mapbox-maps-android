.PHONY: check
check:
	./gradlew ktlint lint;
	python scripts/license-validate.py;
	sh scripts/kdoc-validate.sh;

.PHONY: test
test:
	./gradlew test -i

.PHONY: sdk-build
sdk-build:
	./gradlew assembleRelease -x app:assembleRelease -x android-auto-app:assembleRelease\
		-x compose-app:assembleRelease -x extension-style-app:assembleRelease

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
	./gradlew mapboxSDKRegistryUpload -x extension-androidauto:mapboxSDKRegistryUpload -x extension-compose:mapboxSDKRegistryUpload --no-parallel --no-daemon;

.PHONY: sdkRegistryReleaseUpload
sdkRegistryReleaseUpload:
	./gradlew mapboxSDKRegistryReleaseUpload -x extension-androidauto:mapboxSDKRegistryReleaseUpload -x extension-compose:mapboxSDKRegistryReleaseUpload --no-parallel --no-daemon;

.PHONY: sdkRegistryPublish
sdkRegistryPublish:
	./gradlew mapboxSDKRegistryPublishAll;

.PHONY: sdkRegistryReleasePublish
sdkRegistryReleasePublish:
	./gradlew mapboxSDKRegistryReleasePublishAll;

.PHONY: sdkRegistryUploadReleaseAndroidAutoExtension
sdkRegistryUploadReleaseAndroidAutoExtension:
	./gradlew extension-androidauto:mapboxSDKRegistryReleaseUpload;

.PHONY: sdkRegistryPublishReleaseAndroidAutoExtension
sdkRegistryPublishReleaseAndroidAutoExtension:
	./gradlew extension-androidauto:mapboxSDKRegistryReleasePublish;

.PHONY: sdkRegistryUploadReleaseComposeExtension
sdkRegistryUploadReleaseComposeExtension:
	./gradlew extension-compose:mapboxSDKRegistryReleaseUpload;

.PHONY: sdkRegistryPublishReleaseComposeExtension
sdkRegistryPublishReleaseComposeExtension:
	./gradlew extension-compose:mapboxSDKRegistryReleasePublish;

.PHONY: clean
clean:
	./gradlew clean

.PHONY: codecoverage
codecoverage:
	./gradlew sdk:jacocoTestDebugUnitTestReport && google-chrome sdk/build/jacoco/jacocoHtml/index.html

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
	 	--apk app/build/outputs/apk/release/app-release.apk \
		--file app/permission.json

# Update permissions app module, requires app:assembleDebug first
.PHONY: update-permissions
update-permissions:
	 python3 scripts/check-permissions.py \
	 	--apk app/build/outputs/apk/debug/app-debug.apk \
		--file app/permission.json \
		--update True
