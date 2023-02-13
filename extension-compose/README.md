# Mapbox Maps Compose Extension

## Overview

The Mapbox Maps Compose Extension is a public library to extend the Mapbox Map to work together with Jetpack Compose UI framework.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

Working examples of the Compose extension can be found in our [compose test application](https://github.com/mapbox/mapbox-maps-android/tree/master/compose-app/).

## Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) or building an app that uses the Mapbox Maps Compose Extension. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the compose extension to your project, you configure its dependency in your `build.gradle` files.

```groovy
// In the root build.gradle file
// The Mapbox access token needs to a scope set to DOWNLOADS:READ
allprojects {
    repositories {
        maven {
            url 'https://api.mapbox.com/downloads/v2/releases/maven'
            authentication {
                basic(BasicAuthentication)
            }
            credentials {
                username = "mapbox"
                password = "INSERT_MAPBOX_ACCESS_TOKEN_HERE"
            }
        }
    }
}

// In your build.gradle, add the extension with your other dependencies.
dependencies {
  implementation 'com.mapbox.extension:maps-compose:0.1.0'

  // Pick your versions of Android Mapbox Map SDK
  // Note that Compose extension is compatible with Maps SDK v10.0+, however some Compose features like requires later versions of Maps SDK.
  // See the detailed feature compatibility table below
  implementation 'com.mapbox.maps:android:10.10.0'
}
```

You should also become familiar with [Google's documentation for Jetpack Compose](https://developer.android.com/jetpack/compose/setup).

To start using Compose, you need to first add some build configurations to your project. Add the following definition to your app’s build.gradle file:
```groovy
android {
    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}
```

## Example

Here's a simple example on using Compose extension to insert a Mapbox map to your app:
```kotlin
Box(Modifier.fillMaxSize()) {
 MapboxMap(
   modifier = Modifier.matchParentSize(),
   mapInitOptions = MapInitOptions(
     context = LocalContext.current,
     styleUri = Style.MAPBOX_STREETS,
     cameraOptions = cameraOptions {
       center(Point.fromLngLat(LONGITUDE, LATITUDE))
       zoom(12.0)
     },
   ),
 ) { }
}
```

## Compatibility with Maps SDK v10
The Compose extension is released separately from the Android Maps SDK v10 and has a compileOnly dependency. When using the Compose extension you need to include a compatible Maps SDK. The feature compatibility checklist can be found below.

Below is the full feature compatibility table:

Features  | Supported? | Compatible Maps SDK version
------------- | ------------- | -------------
Basic Map rendering | ✅ | v10.0.0+

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.
