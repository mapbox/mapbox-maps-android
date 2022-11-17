# Changelog for Mapbox Maps Android Auto Extension SDK

Mapbox welcomes participation and contributions from everyone.

# main
## Features ✨ and improvements 🏁

# 0.5.0 November 16, 2022

## Features ✨ and improvements 🏁
* Make the SDK compatible with Jetpack Car Library `v1.2.+`. ([#1828](https://github.com/mapbox/mapbox-maps-android/pull/1828))
* Always use `MapOptions.contextMode = ContextMode.SHARED` when creating the map session preventing hard-catching runtime crashes or artifacts. ([1834](https://github.com/mapbox/mapbox-maps-android/pull/1834))

## Dependencies
* Android Auto car library `androidx.car.app:app:1.2.+`
* Supports any version of [Mapbox Maps 10.5.+](https://github.com/mapbox/mapbox-maps-android/tree/main/extension-androidauto#compatibility-with-maps-sdk-v10)

# 0.4.0 November 7, 2022

## Features ✨ and improvements 🏁
* Make the SDK compatible with any version of Mapbox Maps `v10.5.+`. ([#1706](https://github.com/mapbox/mapbox-maps-android/pull/1706))
* Remove experimental annotation from `MapboxCarMap`, `MapboxCarMapObserver`, `MapboxCarMapSurface`, `MapboxCarMapGestureHandler`, and `DefaultMapboxCarMapGestureHandler`. ([#1767](https://github.com/mapbox/mapbox-maps-android/pull/1767))

## Dependencies
* Android Auto car library `androidx.car.app:app:1.1.+`
* Supports any version of [Mapbox Maps 10.5.+](https://github.com/mapbox/mapbox-maps-android/tree/main/extension-androidauto#compatibility-with-maps-sdk-v10)

# 0.3.0 September 26, 2022

## Features ✨ and improvements 🏁
* Allow `MapboxCarMap` to be initialized before the `CarContext` is created. ([#1603](https://github.com/mapbox/mapbox-maps-android/pull/1603))
* Add `MapboxCarMapSessionInstaller` and `MapboxCarMapScreenInstaller` for simpler setup. ([#1603](https://github.com/mapbox/mapbox-maps-android/pull/1603))
* Add `Session.mapboxMapInstaller` and `Screen.mapboxMapInstaller` extension functions to create the installers. ([#1603](https://github.com/mapbox/mapbox-maps-android/pull/1603))
* Change `MapboxCarMapGestureHandler` to a java interface so default methods can be added without breaking java backwards compatibility. ([#1670](https://github.com/mapbox/mapbox-maps-android/pull/1670))
* Change `MapboxCarMapObserver` to a java interface so default methods can be added without breaking java backwards compatibility. ([#1670](https://github.com/mapbox/mapbox-maps-android/pull/1648))
* Change `MapboxCarMap#getEdgeInsets()` to `mapboxCarMap.getVisibleEdgeInsets()` with the addition of `mapboxCarMap.getStableEdgeInsets()`. ([#1670](https://github.com/mapbox/mapbox-maps-android/pull/1648))
* Add `MapboxCarMapObserver#onStableAreaChanged` to support all the available functions from the SurfaceCallback. ([#1670](https://github.com/mapbox/mapbox-maps-android/pull/1648))
* Add support for intercepting the `SurfaceCallback#onClick` when using `MapboxCarMap.prepareSurfaceCallback`. ([#1683](https://github.com/mapbox/mapbox-maps-android/pull/1683))

## Dependencies
* Android Auto car library `androidx.car.app:app:1.1.+`
* Supports any version of [Mapbox Maps 10.5.+](https://github.com/mapbox/mapbox-maps-android/tree/main/extension-androidauto#compatibility-with-maps-sdk-v10)

# 0.2.0 August 9, 2022

## Bug fixes 🐞
* Destroy previous surface when new surface becomes available. This adds better support for `androidx.car.app:app-automotive`. ([#1509](https://github.com/mapbox/mapbox-maps-android/pull/1509))

## Dependencies
* Android Auto car library `androidx.car.app:app:1.1.+`
* Supports any version of Mapbox Maps 10.+

# 0.1.0 April 7, 2022

## Features ✨ and improvements 🏁
* Add `MapboxCarMap`. Constructing this object will add mapbox map to the head unit. ([#1004](https://github.com/mapbox/mapbox-maps-android/pull/1004))
* Add `MapboxCarMapObserver` and `MapboxCarMapSurface` which allows you to build customizations for the `MapboxMap`. ([#1004](https://github.com/mapbox/mapbox-maps-android/pull/1004))
* Add `MapboxCarMapGestureHandler` and `DefaultMapboxCarMapGestureHandler` for handling panning and zooming gestures. (#[1159](https://github.com/mapbox/mapbox-maps-android/pull/1159))
* Add an example to the `:android-auto-app:` module, it includes a 3D location puck, and follows the car location. ([#1004](https://github.com/mapbox/mapbox-maps-android/pull/1004))
* Show Mapbox Logo and a Compass in an example with the `CarMapWidgets`. The `BitmapWidget` is available starting with Maps [v10.4.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.4.0). ([#1004](https://github.com/mapbox/mapbox-maps-android/pull/1004))

## Dependencies
* Android Auto car library `androidx.car.app:app:1.1.+`
* Supports any version of Mapbox Maps 10.+
