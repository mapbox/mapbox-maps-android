.PHONY: check
check:
	./gradlew ktlint lint;
	python scripts/license-validate.py;
	./gradlew clean dokkaHtml -Pmapbox.dokkaHtmlFlavor=release

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

.PHONY: clean
clean:
	./gradlew clean

.PHONY: codecoverage
codecoverage:
	./gradlew maps-sdk:jacocoTestDebugUnitTestReport && google-chrome maps-sdk/build/jacoco/jacocoHtml/index.html

.PHONY: unit-tests
unit-tests:
	./gradlew test -i

.PHONY: instrumentation-tests
instrumentation-tests:
	./gradlew maps-sdk:connectedDebugAndroidTest && ./gradlew app:connectedDebugAndroidTest

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
