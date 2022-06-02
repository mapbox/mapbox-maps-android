## Mapbox Maps MapOverlay Plugin for Android

### Overview

The Mapbox MapOverlay Plugin for Android is an public library for adding overlays on top of MapView. By registering overlays into MapOverlay Plugin and providing the EdgeInsets(margins) of these overlay views, MapOverlay Plugin can adjust the camera center location and zoom level to make sure all the important POIs(provided by `MapOverlayCoordinatesProvider`) are not covered by these overlays.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

![MapOverlay-plugin](https://user-images.githubusercontent.com/8577318/98535135-8f68b000-22c0-11eb-9b09-c4f4f8664745.gif)

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox MapOverlay Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the MapOverlay plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-overlay:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox MapOverlay Plugin for Android could be done in two ways: by either providing your own version of the MapOverlay plugin or by using the existing apis of the MapOverlay plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
private val provider = object : MapOverlayCoordinatesProvider {
    override fun getShownCoordinates(): List<Point> {
        // Return the points that will not be covered by overlays
    }
}

mapOverlayPlugin = mapView.mapboxOverlay
// Set displaying area margins if needed
mapOverlayPlugin.setDisplayingAreaMargins(50, 50, 50, 50)

mapOverlayPlugin.registerMapOverlayCoordinatesProvider(provider)
// Register a view as a overlays on map
mapOverlayPlugin.registerOverlay(overlay)
// Register other overlays if needed

// Reframe MapView when needed.
mapOverlayPlugin.reframe()

// Reframe MapView with animation when needed.
mapOverlayPlugin.getReframeCameraOption()?.let {
    mapboxMap.flyTo(it, 1000)
}
```

More concrete examples of the MapOverlay plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
