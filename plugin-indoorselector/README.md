## Mapbox Maps Indoor Selector Plugin for Android

### Overview

The Mapbox Maps Indoor Selector Plugin for Android is a public library for displaying an indoor floor selector on top of a MapView. When enabled, the plugin will show a floor selector on the top-right corner of the map when indoor map data is available. The selector displays available floors and allows users to navigate between them. The plugin automatically shows and hides based on whether indoor data is present in the current map view.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Indoor Selector Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the indoor selector plugin to your project, you configure its dependency in your `build.gradle` files.

### Enabling the Plugin

⚠️ **Important**: The indoor selector plugin is **not included in the default plugin list**. You must explicitly add it when creating your MapView.

**Recommendation**: Only add this plugin to your application if you specifically need indoor mapping functionality and have access to indoor map data with a supported style.

```kotlin
// In your Activity or Fragment
val mapView = MapView(
  this,
  MapInitOptions(
    context = this,
    plugins = MapInitOptions.defaultPluginList + Plugin.Mapbox(Plugin.MAPBOX_INDOOR_SELECTOR_PLUGIN_ID)
  )
)
```

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
  implementation 'com.mapbox.plugin:maps-indoorselector:11.17.0'
}
```

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
