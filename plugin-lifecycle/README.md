## Mapbox Maps Lifecycle Plugin for Android

### Overview

The Mapbox Maps Lifecycle Plugin for Android is an public library for handling Lifecycle events of the Mapbox SDK MapView. By default, the Lifecycle Plugin will automatically hook into the lifecycle events(onStart/onStop/onDestroy) of the host Activity or Fragment, so that developers don't need to invoke the corresponding lifecycle methods of MapView manually themselves.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Lifecycle Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the Lifecycle plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-lifecycle:10.6.0-rc.1'
  // Make sure the version of appcompat is 1.3.0+
  implementation 'androidx.appcompat:appcompat:1.3.0'
}
```
The Lifecycle Plugin will take effect automatically if added as dependency.
#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
