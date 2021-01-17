# Changelog for Mapbox Maps SDK v10 for Android

Mapbox welcomes participation and contributions from everyone.

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

