# Changelog for Mapbox Maps SDK v10 for Android

Mapbox welcomes participation and contributions from everyone.

# 10.0.0-beta.15 - March 5, 2021
## Bugs
* [annotation] Fix text-font issue in annotation plugin. ([#144](https://github.com/mapbox/mapbox-maps-android/pull/144))
## Dependencies
* [gl-native] Update to v10.0.0-beta.16, common to beta.12 ([#137](https://github.com/mapbox/mapbox-maps-android/pull/137))

# 10.0.0-beta.14 - February 24, 2021

## Breaking changes
* [map] Change signature of Map#getElevation from Float to Double [#120](https://github.com/mapbox/mapbox-maps-android/pull/120)
* [map] Fixed text-field strings are now returned as formatted expressions [#120](https://github.com/mapbox/mapbox-maps-android/pull/120)
* [annotation] Rename getAnnotationManger to createAnnotationManager [#105](https://github.com/mapbox/mapbox-maps-android/pull/105)
* [style] GeoJsonSource data property can't be empty [#120](https://github.com/mapbox/mapbox-maps-android/pull/120)

## Features
* [location-component] Add isLocatedAt API to location component plugin [#99](https://github.com/mapbox/mapbox-maps-android/pull/99)
* [snapshot] Introduce interfaces for style events and snapshot result [#124](https://github.com/mapbox/mapbox-maps-android/pull/124)
* [annotation] Process anchor animators correctly [#109](https://github.com/mapbox/mapbox-maps-android/pull/109)
* [annotation] Limit adding style images multiple times for annotations [#118](https://github.com/mapbox/mapbox-maps-android/pull/118)
* [annotation] Add GeoJSONOptions configuration [#79](https://github.com/mapbox/mapbox-maps-android/pull/79)
* [annotation] Show all icons and text from annotation manager by default [#115](https://github.com/mapbox/mapbox-maps-android/pull/115)
* [animation] Add kotlin dsl builder for CameraOptions and java builders for CameraAnimatorOptions and MapAnimationOptions [#90](https://github.com/mapbox/mapbox-maps-android/pull/90)
* [map] Better use of Choreographer inside renderer resulting to smoother map animations [#107](https://github.com/mapbox/mapbox-maps-android/pull/107)
* [gestures] change default interpolator from Decelerate to LinearOutSlowIn [#103](https://github.com/mapbox/mapbox-maps-android/pull/103)

## Bugs
* [compass] Hide the compass on startup when facing north [#116](https://github.com/mapbox/mapbox-maps-android/pull/116)
* [annotation] Change default value of text font for symbols to null [#111](https://github.com/mapbox/mapbox-maps-android/pull/111)
* [map] Remove renderStill from public API [#104](https://github.com/mapbox/mapbox-maps-android/pull/104)
* [style]  Rename reference to style plugin and converting them to style extension [#123](https://github.com/mapbox/mapbox-maps-android/pull/123)
* [annotation] Expose collection of annotations as a List instead of Map [#121](https://github.com/mapbox/mapbox-maps-android/pull/121)
* [camera] Trigger map camera change updates immediately, Fix order of animation callbacks in camera animation plugin [#125](https://github.com/mapbox/mapbox-maps-android/pull/125)

## Dependencies
* [gl-native] update to v10.0.0-beta.15 [#120](https://github.com/mapbox/mapbox-maps-android/pull/120)
* [common] update to v10.0.0-beta.11 [#120](https://github.com/mapbox/mapbox-maps-android/pull/120)

# 10.0.0-beta.13 - February 10, 2021

## Features
* [map] Expose FPS listener [#80](https://github.com/mapbox/mapbox-maps-android/pull/80)
* [annotation] Add convenience color int API [#76](https://github.com/mapbox/mapbox-maps-android/pull/76)
* [annotation] Add convenience bitmap API for symbols [#67](https://github.com/mapbox/mapbox-maps-android/pull/67)
* [annotation] Make getting annotation manager configurable [#47](https://github.com/mapbox/mapbox-maps-android/pull/47)
* [location] Improve the default location provider and improve handling of location updates [#58](https://github.com/mapbox/mapbox-maps-android/pull/58)
* [location] Add OnIndicatorPositionChangedListener and OnIndicatorBearingChangedListener [#56](https://github.com/mapbox/mapbox-maps-android/pull/56)

## Bugs
* [map] allow getSourceAs returning null values, handle cast gracefully [#88](https://github.com/mapbox/mapbox-maps-android/pull/88)
* [animation] remove internal plugin singleton, support multi display maps [#70](https://github.com/mapbox/mapbox-maps-android/pull/70)
* [telemetry] correct versioning of BuildConfig [#65](https://github.com/mapbox/mapbox-maps-android/pull/65)
* [annotation] Fix offset array not working issue [#60](https://github.com/mapbox/mapbox-maps-android/pull/60)
* [plugin] make xml attribute parsers internal [#81](https://github.com/mapbox/mapbox-maps-android/pull/81)

## Dependencies
* [gl-native] update to v10.0.0-beta.14 [#87](https://github.com/mapbox/mapbox-maps-android/pull/87)
* [common] update to v10.0.0-beta.9.1 [#87](https://github.com/mapbox/mapbox-maps-android/pull/87)

# 10.0.0-beta.12 - January 27, 2021

## Announcement

V10 is the latest version of the Mapbox Maps SDK for Android. v10 brings substantial performance improvements, new features like 3D terrain and a more powerful camera, modern technical foundations, and a better developer experience.

To get started with v10, please refer to our [migration guide](https://docs.mapbox.com/android/beta/maps/guides/migrate-to-v10/).

## Known Issues

### Style

* Currently there is no compile-time validation of the Style DSL, exceptions will be thrown in runtime.

### Location

* Location component plugin is still under active development and the interfaces are subject to change.

### 3D Terrain

* 3D Terrain is in an experimental state
* 3D Terrain crashes on specific GPU hardware:
    * Qualcomm Adreno 640

### Other
* Annotation plugin is not feature complete with the old implementation
* Restricting the map to a bounds that includes the antemeridian will result in an invalid jump to the left side of the bounds.
* Known deficiencies with max and min zoom map properties
* An invalid LatLng conversion can occur and produce a native crash
* Native crash when resuming the map in specific situations
* Native crash when performing a camera transition using Map#jumpTo

