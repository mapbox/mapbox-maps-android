## Mapbox Maps Logo Plugin for Android

### Overview

The Mapbox Maps Logo Plugin for Android is an public library for displaying logo on top of MapView. By default the plugin will show the Mapbox logo on top of the map at the bottom-left corner. Please visit our [documentation](https://docs.mapbox.com/android/maps/guides/ui-settings/#logo-and-attribution) for more information on displaying the Mapbox Logo.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Logo Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the logo plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-logo:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox Maps Logo Plugin for Android could be done in two ways: by setting the LogoSettings to the logo plugin. This can be achieved with:

```kotlin
val plugin = mapView.logo
plugin.logoSettings = LogoSettings(...)
```

More concrete examples of the logo plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
