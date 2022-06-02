## Mapbox Maps Location Component Plugin for Android

### Overview

The Mapbox Maps Location Component Plugin for Android is an public library for displaying a 2D or 3D location puck at user's location. By default the plugin provides a default 2D location puck. Users has the flexibility to customise location puck.

**Note**:
With v10, the puck and camera updates have been decoupled, the location component plugin will only handle the location puck updates. Camera updates can be manually handled and synced. And the [Mapbox Viewport Plugin](https://github.com/mapbox/mapbox-maps-android/tree/main/plugin-viewport) is introduced as the successor of the location tracking modes of v9.

A full overview of classes and interfaces can be found in our [User location guide](https://docs.mapbox.com/android/maps/guides/user-location) and [API documentation](https://docs.mapbox.com/android/maps/api-reference/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Location Component Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the location component plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-locationcomponent:10.6.0-rc.1'
}
```

#### Using Google's Fused Location Provider

By default, the Maps SDK uses the Android Location Provider to obtain raw location updates. And with Android 11, the raw location updates might suffer from precision issue.

The Maps SDK also comes pre-compiled with support for the [Google's Fused Location Provider](https://developers.google.com/location-context/fused-location-provider) if that dependency is available. This means, that if your target devices support Google Play Services, [we recommend adding the Google Play Location Services dependency to your project](https://developers.google.com/android/guides/setup).
```groovy
implementation("com.google.android.gms:play-services-location:18.0.0")
```

If that dependency is available in your app build, the Maps SDK will automatically use the Google's Fused Location Provider.

### Example

Customizing Mapbox Maps Location Component Plugin for Android could be done in two ways: by either providing your own version of the location component plugin or by updating the settings on the location component plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val plugin = mapView.location
// Enable location component plugin
plugin.enabled = true
```

#### Customize 2D puck appearance

```kotlin
// Customise a 2D location puck:
plugin.locationPuck = LocationPuck2D(
 topImage = ... ,
 bearingImage = ... ,
 shadowImage =  ... ,
 // Scale expression defines the expression that will be applied to the image sizes.
 // This example provides an linear interpolate expression to scale the image size according to the zoom level.
 scaleExpression = interpolate {
   linear()
   zoom()
   stop {
     literal(0.0)
     literal(0.6)
   }
   stop {
     literal(20.0)
     literal(1.0)
   }
 }.toJson()
)
```

#### Customize 3D puck appearance

```kotlin
// Customise a 3D location puck:
plugin.locationPuck = LocationPuck3D(
 modelUri = ... ,
 // Model scale is used to set the initial scale of the model when the map is at maximum zoom level.
 // By default, Mapbox provides a default expression to keep the model size constant during zoom changes,
 // however, it could also be overwritten by the setting the modelScaleExpression property.
 modelScale = listOf(0.1f, 0.1f, 0.1f)
)
```

#### Provide your own LocationProvider with custom puck animators

```kotlin
// Provide your own Location Updates:
// Create your own Location Provider implementation that implements the LocationProvider interface.
class MyLocationProvider : LocationProvider {
  // Your logic here to send location updates to the location consumer asynchronously.
  // Location and bearing updates to the location consumer can be customised with flexible animation options.
  ...
}
val myLocationProvider = MyLocationProvider()
// Set your own location provider, it will replace the default implementation.
plugin.setLocationProvider(myLocationProvider)
```

If using own LocationProvider there is a possibility to customize puck animation to upcoming bearing and location updates.

```kotlin
private inner class CustomLocationProvider : LocationProvider {
  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    // set default animation options that will be used for all upcoming location updates.
    locationConsumer.onPuckLocationAnimatorDefaultOptionsUpdated() {
      duration = 2000
      interpolator = FastOutSlowInInterpolator()
    }
    locationConsumer.onLocationUpdated(geoPointOne)
    locationConsumer.onLocationUpdated(geoPointTwo)
    // set custom animator options for next update only, then default animator options will apply
    locationConsumer.onLocationUpdated(geoPointThree) {
      duration = 1000
    }
  }
  ...
}
```

#### Interface location2
Inorder to avoid breaking api changes, interface location2 is introduced for updating `puckBearingSource`, `puckBearingEnabled` and `showAccuracyRing` properties.
```
// Change the puck bearing source.
mapView.location2.puckBearingSource = PuckBearingSource.HEADING
mapView.location2.puckBearingSource = PuckBearingSource.COURSE
// Change the visibility of accuracy ring.
mapView.location2.showAccuracyRing = true
mapView.location2.showAccuracyRing = false
// Change the puck bearing enabled.
mapView.location2.puckBearingEnabled = true
mapView.location2.puckBearingEnabled = false
```

More concrete examples of the location component plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
