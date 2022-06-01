## Mapbox Maps Animation Plugin for Android

![Camera Animation Plugin](https://user-images.githubusercontent.com/15800566/95355920-c49f6e00-08ce-11eb-9f79-4d2cbbfc2855.gif)

## Overview

The Mapbox Maps Animation Plugin for Android is an public library for animating camera for `MapboxMap`. Following animations types are currently supported: center (camera center represented as lng-lat point), zoom, bearing, pitch, anchor (screen coordinate relative to the camera movement), padding. Plugin uses Android `ValueAnimator`s that extend `CameraAnimator`. There is a possibility to receive camera updates callbacks during animation transition by adding animation listeners.

**Note**: If animation is about to start while an animation of the same type is already running -- the latter one will be canceled.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

## Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Animation Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the Animation plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-animation:10.6.0-rc.1'
}
```

## Examples

Customizing Mapbox Maps Animation Plugin for Android could be done in two ways: by either providing your own version of the Animation plugin or by using existing API. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with three logical API levels.

### High-level API

A set of ready-to-use functions for animating map camera transitions. It includes such functions as `easeTo`, `moveBy`, `flyTo`, `pitchBy`, `rotateBy`, `scaleBy`. Those functions could be easily called with `MapboxMap` object. Depending on function type different parameters will be used but all of those functions support customizing animation duration and interpolator. It is possible to add `Animator.AnimatorListener` object to listen to animation start / cancel / end.

**Note**: If animation is about to start while any other high-level API animation is already running -- the latter one will be canceled.

```kotlin
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions

mapboxMap.flyTo(
    CameraOptions.Builder().center(geoPoint).build(),
    mapAnimationOptions { duration = 7000L }
)
```

### Medium-level API

Any number of animators extending `CameraAnimator` could be created and started sequentially or in parallel. In that case user should not take care of registering and unregistering those animators.

```kotlin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions

mapView.camera.apply {
    val bearing = createBearingAnimator(cameraAnimatorOptions(18.0, 20.0) { startValue = 15.0 }) {
          duration = 8500
          interpolator = AnticipateOvershootInterpolator()
    }
    val pitch = createPitchAnimator(cameraAnimatorOptions(30.0) { startValue = 15.0 }) {
          duration = 2000
    }
    playAnimatorsTogether(bearing, pitch)
}
```

### Low-level API

Any number of animators extending `CameraAnimator` could be created but user should also register them in Mapbox Maps Animation Plugin in order for map camera to work properly and unregister them when they will not be needed anymore. Animation lifecycle (start, end, cancel) is driven directly by user in this case.

```kotlin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions

val plugin = mapView.camera
val bearing = plugin.createBearingAnimator(cameraAnimatorOptions(160.0) { startValue = 0.0 }) {
      duration = 8500
      interpolator = AnticipateOvershootInterpolator()
}
plugin.registerAnimators(bearing)
bearing.apply {
    addListener(
        object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
              (animation as? CameraAnimator<*>)?.let {
                plugin.unregisterAnimators(it)
              }
            }

            override fun onAnimationCancel(animation: Animator?) {
              (animation as? CameraAnimator<*>)?.let {
                plugin.unregisterAnimators(it)
              }
            }

            override fun onAnimationRepeat(animation: Animator?) {}
        }
    )
    start()
}
```

More concrete examples of the Animation plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
