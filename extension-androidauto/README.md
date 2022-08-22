# Mapbox Maps Android Auto Extension

## Overview

The Mapbox Maps Android Auto Extension is a public library for initialising the Mapbox `MapSurface` from a [Session](https://developer.android.com/reference/androidx/car/app/Session). The extension provides a simple framework for adding Mapbox to Android Auto head units.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

Working examples of the Android Auto extension can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/android-auto-app/src/main/java/com/mapbox/maps/testapp/auto). To see examples of an integration with the Mapbox Navigation and Search SDKs, you can also try the [navigation examples](https://github.com/mapbox/mapbox-navigation-android-examples).

## Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) or building an app that uses the Mapbox Maps Android Auto Extension. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the android auto extension to your project, you configure its dependency in your `build.gradle` files.

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

// In your build.gradle, add the extension with your other dependencies.
dependencies {
  implementation 'com.mapbox.extension:maps-androidauto:0.1.0'

  // Pick your versions of Android Mapbox Map SDK
  // Note that Android Auto extenison is compatibile with Maps SDK v10.0+, however some Android Auto features like Widgets requires Map SDK v10.4.0+
  // See the detailed feature compatibility table below
  implementation 'com.mapbox.maps:android:10.4.0'
}
```

## AndroidManifest and permissions

You should become familiar with [Google's documentation for building a navigation app](https://developer.android.com/training/cars/apps/navigation). You will need to declare your own [CarAppService](https://developer.android.com/reference/androidx/car/app/CarAppService), your own `minCarApiLevel`, create your own `xml/automotive_app_desc`, and make your own car app theme.

The Mapbox Android Auto extension includes the `androidx.car.app.ACCESS_SURFACE` permission, you do not need to add this permission to your manifest. You will need to add the `androidx.car.app.NAVIGATION_TEMPLATES` permission, along with any other permission your app requires.

``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.mapbox.maps.testapp.auto">

    <!-- You must include this in order to use the NavigationTemplate -->
    <uses-permission android:name="androidx.car.app.NAVIGATION_TEMPLATES" />

    <application
        <!-- snip -->
        >

        <!-- You must add xml/automotive_app_desc -->
        <meta-data
            android:name="com.google.android.gms.car.application"
            android:resource="@xml/automotive_app_desc" />

        <!-- Create a theme for your app -->
        <meta-data
            android:name="androidx.car.app.theme"
            android:resource= "@style/CarAppTheme" />

        <!-- You choose your minCarApiLevel -->
        <meta-data
            android:name="androidx.car.app.minCarApiLevel"
            android:value="3" />

        <!-- Link to your implementation of CarAppService -->
        <service
            android:name=".car.YourCarAppService"
            android:exported="true"
            tools:ignore="ExportedService">

            <intent-filter>
                <action android:name="androidx.car.app.CarAppService" />
                <category android:name="androidx.car.app.category.NAVIGATION" />
            </intent-filter>
        </service>
    </application>
</manifest>
```

## Example Session and MapScreen

In this example, the Session manages an instance of `MapboxCarMap` and then each `Screen` can register and unregister observers. Each screen can control map features. Your implementations of `MapboxCarMapObserver` give you control of the map.

```kotlin
class MySession : Session() {

  private val mapboxCarMap = mapboxMapInstaller()
      .onCreated(CarMapWidgets())
      .install { carContext ->
        // Callback is triggered when the Session calls onCreate. This allows you to specify
        // custom MapInitOptions.
        MapInitOptions(carContext)
      }

  override fun onCreateScreen(intent: Intent): Screen = MapScreen(mapboxCarMap)
}

class MyMapScreen(
  mapboxCarMap: MapboxCarMap
) : Screen(mapboxCarMap.carContext) {

  private val carCameraController = CarCameraController()
  private val myCustomExperience = MyCustomMapExperience()

  init {
    mapboxMapInstaller(mapboxCarMap)
        .onCreated(carCameraController)
        .gestureHandler(carCameraController.gestureHandler)
        .onResumed(myCustomExperience)
        .install()
  }

  override fun onGetTemplate(): Template {
    val builder = NavigationTemplate.Builder()
      --snip--

class MyCustomMapExperience : MapboxCarMapObserver {
  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    val mapboxMap = mapboxCarMapSurface?.mapSurface?.getMapboxMap()!!
    mapboxMap.loadStyle(
      --snip--
}
```

## Compatibility with Maps SDK v10
The Android Auto extension is released separately from the Android Maps SDK v10 and has a compileOnly dependency. When using the Android Auto extension you need to include a compatible Maps SDK. The feature compatibility checklist can be found below.

Below is the full feature compatibility table:

Features  | Supported? | Compatible Maps SDK version
------------- | ------------- | -------------
Map rendering  | ✅ | v10.0+
Runtime styling  | ✅ | v10.0+
Camera animation | ✅ | v10.0+
Viewport plugin | ✅ | v10.0+
Location Component plugin | ✅ | v10.0+
Gestures(scroll and scale) | ✅ | v10.0+
Annotation plugin | ✅ | v10.0+
Logo Widget | ✅ | v10.4+
Compass Widget | ✅ | v10.4+
View annotation plugin | ❌ | -
Scale bar plugin | ❌ | -
Attribution | ❌ | -

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.
