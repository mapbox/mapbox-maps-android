## Mapbox Maps Android Auto Extension for Android

### Overview

The Mapbox Maps Android Auto Extension for Android is an public library for initialising the MapSurface from a [car app session](https://developer.android.com/reference/androidx/car/app/Session), and handling scroll and scale gestures.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Android Auto Extension for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the android auto extension to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.extension:maps-androidauto:10.0.0-rc.7'
}
```

### Example

Using the Mapbox Maps Android Auto Extension for Android could be done using:

```kotlin
class MapSession : Session() {
  private lateinit var mapSurface: MapSurface

  override fun onCreateScreen(intent: Intent): Screen {
    val mapScreen = MapScreen(carContext)
    initMapSurface(scrollListener = carCameraController) { surface ->
      // Interact with the created MapSurface
      mapSurface = surface
      surface.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
        // Interact with the style
        ...
      }
    }

    return if (carContext.checkSelfPermission(ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
      carContext.getCarService(ScreenManager::class.java).push(mapScreen)
      RequestPermissionScreen(carContext)
    } else mapScreen
  }
```

More concrete examples of the Android Auto extension can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/android-auto-app/src/main/java/com/mapbox/maps/testapp/auto).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.
