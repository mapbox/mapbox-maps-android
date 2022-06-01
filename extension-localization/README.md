## Mapbox Maps Localization Extension for Android

### Overview

The Mapbox Localization Extension for Android is a public library for localizing labels for a MapView.
This extension library can localize supported languages in [mapbox-streets-v8](https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v8/#common-fields) and [mapbox-streets-v7](https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v7/#name-fields)

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/api-reference/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Localization Extension for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the Localization Extension to your project, you configure its dependency in your `build.gradle` files.

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
    implementation 'com.mapbox.extension:maps-localization:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox Localization Extension for Android could be done in two ways: by either providing your own version of the Localization Extension or by using the existing Localization Extension API. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
mapboxMap.loadStyleUri(styleUri) {
    it.localizeLabels(selectedLocale)
}
```

More concrete examples of the Localization Extension can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.
