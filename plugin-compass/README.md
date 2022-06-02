## Mapbox Maps Compass Plugin for Android

### Overview

The Mapbox Maps Compass Plugin for Android is an public library for displaying compass on top of MapView. By default the plugin will show a compass on top of the map at the top-right corner. The compass will appear and point to north direction when the map is rotated. When clicked, the map will be reset to face north and disappear. Please visit our [documentation](https://docs.mapbox.com/android/maps/guides/ui-settings/#compass) for more information on displaying compass.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

![compass-plugin](https://user-images.githubusercontent.com/2764714/94573517-d784c380-027a-11eb-9a1f-2d22a1bd2525.gif)

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Compass Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the compass plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-compass:10.6.0-rc.1'
  // Mapbox Maps Compass Plugin depends on the Mapbox Maps Animation Plugin
  implementation 'com.mapbox.plugin:maps-animation:10.6.0-rc.1'
}
```

Please note that Mapbox Maps Compass Plugin depends on the Mapbox Maps Animation Plugin, failing to provide the Mapbox Maps Animation Plugin dependency will result in a RuntimeException.

### Example

Customizing Mapbox Maps Compass Plugin for Android could be done in two ways: by either providing your own version of the compass plugin or by setting the CompassSettings to the compass plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val plugin = mapView.compass
plugin.compassSettings = CompassSettings(...)
```

More concrete examples of the compass plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
