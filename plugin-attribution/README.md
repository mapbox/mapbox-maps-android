## Mapbox Maps Attribution Plugin for Android

### Overview

The Mapbox Maps Attribution Plugin for Android is an public library for displaying map attribution on top of a MapView. By default the plugin will show an 'i'-icon on top of the map in bottom-left corner. When clicked, an attribution dialog will be shown that lists all of the attributions for the currently loaded map style as well as links to `Improve this map` and `Telemetry settings` items. Please visit our [documentation](https://docs.mapbox.com/help/how-mapbox-works/attribution/) for more information on displaying source attribution.


A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

![image](https://user-images.githubusercontent.com/2151639/94667028-8c1bf500-030e-11eb-9922-0a913c715940.gif)

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Attribution Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the attribution plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-attribution:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox Maps Attribution Plugin for Android could be done in two ways: by either providing your own version of the attribution plugin or by providing a custom AttributionDialogManager. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val plugin = mapView.attribution
plugin.setCustomAttributionDialogManager(object : AttributionDialogManager {
  override fun showAttribution(attributionModel: AttributionModel) {
    // called when the attribution dialog should be shown using the AttributionModel data
  }

  override fun onStop() {
    // called when the hosting activity is stopped
  }
})
```

More concrete examples of the attribution plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
