## Mapbox Maps Gestures Plugin for Android

### Overview

The Mapbox Maps Gestures Plugin for Android is an public library for handling gesture interaction with a MapView. By default the plugin provides a default gesture interaction. Gestures supported include scrolling, scaling, rotating and shoving. Please visit our [documentation](https://docs.mapbox.com/android/maps/guides/ui-settings/#gestures) for more information on interacting with gestures.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Gestures Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the gestures plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-gestures:10.6.0-rc.1'
  // Mapbox Maps Gestures Plugin depends on the Mapbox Maps Animation Plugin
  implementation 'com.mapbox.plugin:maps-animation:10.6.0-rc.1'
}
```

Please note that Mapbox Maps Gestures Plugin uses on the Mapbox Gestures Android, for more information visit [mapbox/mapbox-gestures-android](https://github.com/mapbox/mapbox-gestures-android).

### Example

Customizing Mapbox Maps Gestures Plugin for Android could be done in two ways: by either providing your own version of the gestures plugin or by setting the GesturesSettings to the gestures plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val plugin = mapView.gestures
plugin.gesturesSettings = GesturesSettings(...)
```

More concrete examples of the gestures plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
