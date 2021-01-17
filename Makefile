.PHONY: check
check:
	./gradlew ktlint lint;
	python scripts/license-validate.py;
	sh scripts/kdoc-validate.sh;
	sh scripts/stylegen-validate.sh;

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

.PHONY: generate-style-code
generate-style-code:
	cd vendor/mapbox-maps-stylegen && npm install && node generators/kotlin/generate-style-code

.PHONY: generate-config-code
generate-config-code:
	 cd vendor/mapbox-maps-stylegen && npm install && node map-serialization-generator/generators/kotlin/generate-configurations-code.js

.PHONY: ndk-stack-maps
ndk-stack-maps:
	 adb logcat | ndk-stack -sym vendor/mapbox-gl-native-internal/internal/platform/android/sdk/build/intermediates/cmake/debug/obj/arm64-v8a

.PHONY: build-from-source-clean
build-from-source-clean:
	sh scripts/clean.sh

.PHONY: generate-annotation-code
generate-annotation-code:
	cd vendor/mapbox-maps-stylegen/annotation-generator && npm install && node code-gen

# Generates Activity sanity tests
.PHONY: generate-sanity-test
generate-sanity-test:
	node scripts/sanity-test/generate-sanity-test.js
