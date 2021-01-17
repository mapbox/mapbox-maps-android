## Mapbox Maps Location Plugin for Android (Deprecated)

### Overview

The Mapbox Maps Location Plugin for Android is an public library that provides location awareness to your mobile application. This plugin is a direct port of the Location component from Mapbox Maps SDK v9. It is deprecated in favor of the decoupled Location Component Plugin(handles only the location puck rendering) and a new plugin that handles the camera updates(working in progress).

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Location Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the location plugin to your project, you configure its dependency in your `build.gradle` files.

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
// In the app build.gradle file
dependencies {
  implementation 'com.mapbox.plugin:maps-location:10.0.0-beta.12'
}
```

### Example

Initialisation and customizing Mapbox Maps Location Plugin for Android can be done as follows:

```kotlin
val locationPlugin = mapView.getLocationPlugin()
// Activate with a built LocationComponentActivationOptions object
locationPlugin?.let {
  it.activateLocationComponent(
    LocationComponentActivationOptions
      .builder(context, style)
      .useDefaultLocationEngine(true)
      .locationEngineRequest(
        LocationEngineRequest.Builder(750)
          .setFastestInterval(750)
          .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
          .build()
      )
      .build()
  )
  it.enabled = true
  it.cameraMode = cameraMode
}
```

More concrete examples of the location plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
