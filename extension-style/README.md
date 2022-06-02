## Mapbox Maps Style Extension for Android

### Overview

The Mapbox Maps Style Extension for Android is an public library that provides easy-to-use and type-safe Kotlin DSL(Domain Specific Language) for constructing and manipulating the map style according to the [Mapbox Style Specification](https://docs.mapbox.com/mapbox-gl-js/style-spec/).

The Style Extension consists of multiple extension functions and is built on top on the Mapbox Core APIs.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/styling-map/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Style Extension for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the style extension to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.extension:maps-style:10.6.0-rc.1'
}
```

### Example

The Mapbox Style Extension provides some easy-to-use Koltin DSL for constructing layers, sources, light and expressions. A basic use of constructing a style with one layer and one source can be found as follows:

```kotlin
mapView.getMapboxMap().loadStyle(
    style(styleUri = Style.MAPBOX_STREETS) {
        +geojsonSource(id = "earthquakes") {
            url(GEOJSON_URL)
            cluster(false)
         }
        +circleLayer(layerId = "earthquakeCircle", sourceId = "earthquakes") {
            circleRadius(get { literal("mag") })
            circleColor(Color.RED)
            circleOpacity(0.3)
            circleStrokeColor(Color.WHITE)
         }
    },
    new Style.OnStyleLoaded() {
      @Override
      public void onStyleLoaded(@NotNull Style style) {
        // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
      }
    },
    new MapboxMap.OnMapLoadErrorListener() {
      @Override
      public void onMapLoadError(@NotNull MapLoadError mapLoadError, @NotNull String s) {
        // Error occurred when loading the map, try to handle it gracefully here
      }
    });
```

More concrete examples of the style extension can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.
