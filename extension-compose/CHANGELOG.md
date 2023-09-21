# Changelog for Mapbox Maps Compose Extension

Mapbox welcomes participation and contributions from everyone.

# main
## Features ✨ and improvements 🏁
* Add experimental `MapEvents` to handle all events emitted by `MapboxMap`.
* Expose `PointAnnotationGroup.symbolZElevate` property. 

## Bug fixes 🐞
* Fix the bug where wrong pixel ratio could be used in a dual display setup.

# 0.1.0 August 10, 2023
## Features ✨ and improvements 🏁
* Add experimental `MapboxMap` composable function as the entry point to inject a MapboxMap to the app.
* Add experimental `MapEffect` to expose the raw `MapView` controller, so that user can use it to access the full
  Maps SDK API surface.
* Add experimental plugin settings classes(i.e. `AttributionSettings`, `CompassSettings`, `GesturesSettings`
  , `LocationComponentSettings`, `LogoSettings`, `ScaleBarSettings`) as the input state
  of `MapboxMap` composable function.
* Add experimental `PointAnnotation`, `CircleAnnotation`, `PolylineAnnotation` and `PolygonAnnotation` composable
  functions to add an annotation to the map.
* Add experimental `PointAnnotationGroup`, `CircleAnnotationGroup`, `PolylineAnnotationGroup`and
  `PolygonAnnotationGroup` composable functions to add an annotation group to the
  map; `PointAnnotationGroup` and `CircleAnnotationGroup` can be clustered based on configuration.
* Add experimental `ViewAnnotation` composable function to add a view annotation to the map.
* Add experimental `MapViewportState` API hat can be hoisted to control and observe the map's camera state.