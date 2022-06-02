## Mapbox Maps Annotation Plugin for Android

### Overview

The Mapbox Maps Annotation Plugin for Android is an public library for displaying Annotations(including Line/Circle/Symbol/Fill) on top of MapView.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Annotation Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the Annotation plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-annotation:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox Maps Annotation Plugin for Android could be done in two ways: by either providing your own version of the Annotation plugin or by setting the AnnotationOptions to the Annotation Managers. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val annotationPlugin = mapView.annotations
// Take Circle for example here
val circleManager = annotationPlugin.getCircleManager()
val circleOptions: CircleOptions = CircleOptions()
  .withPoint(Point.fromLngLat(0.381457, 6.687337))
  .withCircleColor(ColorUtils.colorToRgbaString(Color.YELLOW))
  .withCircleRadius(12.0)
  .withDraggable(true)
circleManager.create(circleOptions)
```

More concrete examples of the Annotation plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
