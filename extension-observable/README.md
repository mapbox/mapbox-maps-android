## Mapbox Maps Observable Extension for Android

### Overview

The Mapbox Maps Observable Extension for Android is an public library for subscribe and unsubscribe event Observers in a more convenient way. What's more, the received events will be transferred to data class objects.
The supported events and their formats are:
#### "resource-request" Event:
```
 .
 ├── data-source - String ("resource-loader" | "network" | "database" | "asset" | "file-system")
 ├── request - Object
 │   ├── url - String
 │   ├── kind - String ("unknown" | "style" | "source" | "tile" | "glyphs" | "sprite-image" | "sprite-json" | "image")
 │   ├── priority - String ("regular" | "low")
 │   └── loading-method - Array ["cache" | "network"]
 ├── response - optional Object
 │   ├── no-content - Boolean
 │   ├── not-modified - Boolean
 │   ├── must-revalidate - Boolean
 │   ├── size - Number (size in bytes)
 │   ├── modified - optional String, rfc1123 timestamp
 │   ├── expires - optional String, rfc1123 timestamp
 │   ├── etag - optional String
 │   └── error - optional Object
 │       ├── reason - String ("success" | "not-found" | "server" | "connection" | "rate-limit" | "other")
 │       └── message - String
 └── cancelled - Boolean
```

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Observable Extension for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps extension libraries. To add the Observable Extension to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.extension:maps-observable:10.0.0-beta.12'
}
```

### Example

Customizing Mapbox Maps Observable Extension for Android could be done in two ways: by either providing your own version of the Observable Extension or using existing API. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
private val observable = object : Observer() {
    override fun notify(event: Event) {
    // called when the registered event are received
    }
}
mapboxMap = mapView.getMapboxMap()
mapboxMap.subscribeResourceRequest(observable)

// Do remember unsubscribe the observable
override fun onDestroy() {
    super.onDestroy()
    mapboxMap.unsubscribeResourceRequest(observable)
    mapView.onDestroy()
}
```

More concrete examples of the Observable Extension can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
