## Mapbox Maps Consolidated Attribution Plugin for Android

### Overview

The Mapbox Maps Consolidated Attribution Plugin for Android shows the Mapbox logo and the map's data attributions in a single pill on top of the MapView. On first launch the pill expands to show the attributions, then collapses after a few seconds or when the user interacts with the map. Tapping the pill opens the attribution sheet. While the plugin is enabled, the separate logo and attribution ornaments are hidden.

This plugin is experimental.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

To add the Consolidated Attribution plugin to your project, configure its dependency in your `build.gradle` files.

```groovy
// In the root build.gradle file
allprojects {
    repositories {
        maven {
            url 'https://api.mapbox.com/downloads/v2/releases/maven'
        }
    }
}

// In the app build.gradle file
dependencies {
  implementation 'com.mapbox.plugin:maps-consolidatedattribution:11.36.0'
}
```

### Enabling the Plugin

The plugin ships with the Maps SDK, but it is **not included in the default plugin list**. Add it when creating your MapView, then enable it:

```kotlin
val mapView = MapView(
  this,
  MapInitOptions(
    context = this,
    plugins = MapInitOptions.defaultPluginList + Plugin.Mapbox(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID)
  )
)
mapView.consolidatedAttribution.updateSettings {
  enabled = true
}
```

In Jetpack Compose, put the `ConsolidatedAttribution` composable in the `attribution` slot and leave the `logo` slot empty:

```kotlin
MapboxMap(
  attribution = { ConsolidatedAttribution() },
  logo = {},
)
```

More examples can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/main/app/src/main/java/com/mapbox/maps/testapp).
