## Mapbox Maps Localization Plugin for Android

### Overview

The Mapbox Localization Plugin for Android is an public library for adding overlays on top of MapView. By registering overlays into Localization Plugin and providing the EdgeInsets(margins) of these overlay views, Localization Plugin can adjust the camera center location and zoom level to make sure all the important POIs(provided by `MapOverlayCoordinatesProvider`) are not covered by these overlays.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Localization Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the Localization plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-localization:10.0.0-beta.20'
}
```

### Example

Customizing Mapbox Localization Plugin for Android could be done in two ways: by either providing your own version of the Localization plugin or by using the existing apis of the Localization plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin

```

More concrete examples of the Localization plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
