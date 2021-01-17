## Mapbox Maps SDK for Android

[![codecov](https://codecov.io/gh/mapbox/mapbox-maps-android/branch/master/graph/badge.svg?token=pTqOjF4KNO)](https://codecov.io/gh/mapbox/mapbox-maps-android)

![](https://user-images.githubusercontent.com/4394910/66942701-7ed30100-effe-11e9-9948-14012d4c3289.png)

The Mapbox Maps SDK for Android is an open source library for displaying interactive, thoroughly customizable maps in native Android. It is powered by vector tiles and OpenGL.

## Getting Started

This README is intended for developers who are interesting in contributing to the Mapbox Maps SDK for Android. Please visit [https://docs.mapbox.com/android/maps/overview](https://docs.mapbox.com/android/maps/overview/) for general information and instructions on using the Maps SDK in your Android application.

### Setup environment

Prerequisites:

- Install the latest stable version of Android Studio.

### Accessing the the Maps SDK's source

Clone the git repository:

```
git clone git@github.com:mapbox/mapbox-maps-android.git && cd mapbox-maps-android
```

### Opening the Maps SDK

This repository is a regular Android Studio project. Open Android Studio and import this repository’s root `mapbox-maps-android` folder.

### Setup Mapbox Access token for dependency download

Add a Mapbox access token with scope set to `DOWNLOADS:READ` in the root `build.gradle.kts`.

**For current private beta releases, make sure to enable the `mobile_maps_private_beta` feature flag for the download token associated account.**

```kotlin
maven {
  url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
  credentials {
    username = "mapbox"
    password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
  }
  authentication {
    create<BasicAuthentication>("basic")
  }
}
```

### Configuring the Maps SDK

You must provide a Mapbox access token to display Mapbox-hosted maps in this repository’s test application.

This project will generate a `developer_config.xml` file containing a placeholder for your token’s value. If this file is missing, or you prefer to create it manually, add the following XML under `/app/src/main/res/values`:

```
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <string name="mapbox_access_token" translatable="false" tools:ignore="UnusedResources">YOUR_ACCESS_TOKEN</string>
</resources>
```

You may obtain an access token by signing up for a free [Mapbox account](https://www.mapbox.com/studio/account/tokens/). For your security, the `developer_config.xml` file is included in this repository's `.gitignore` file to prevent accidental commits of access tokens.

See [the Mapbox Help guide on keeping access tokens private in open-source Android apps](https://docs.mapbox.com/help/troubleshooting/private-access-token-android-and-ios/#android).

### Running the Maps SDK

The SDK includes a test app in the project. Use Android Studio’s Run button to launch the app on an emulator or physical device.

### Building from source
1. Download `gl-native-internal` submodule by running `git submodule update --init --recursive`.
2. Run `cd vendor/mapbox-gl-native-internal` and make sure to checkout correct branch of `gl-native-internal`.
3. Run `git submodule sync && git submodule update` in `gl-native-internal`.
3. Add `buildFromSource=true` into `local.properties` file in project root directory for building Maps core from source.
4. Add `buildCommonFromSource=true` into `local.properties` file in project root directory for building Common from source additionally to Maps. Note: building Common from source is **only** possible if building Maps from source.
5. Run `make build-from-source-clean` to make sure all previous build files are cleaned up.
6. Sync project with Gradle files.

### Generating documentation
- For generating documentation as Javadocs run `make dokka-javadoc` from project root. Docs will be located in `build/dokka/javadocCollector`. Entry point to view them via browser is `build/dokka/javadocCollector/index.html`.
- For generating documentation as Kotlin HTML run `make dokka-html` from project root. Docs will be located in `build/dokka/htmlCollector`. Entry point to view them via browser is `build/dokka/htmlCollector/project/index.html`.