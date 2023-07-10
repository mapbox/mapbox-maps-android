# Changelog for Mapbox Maps Compose Extension

Mapbox welcomes participation and contributions from everyone.

# main

* Add `MapboxMap` composable function as the entry point to inject a MapboxMap to the app.
* Add `MapEffect` to expose the raw `MapView` controller, so that user can use it to access the full
  Maps SDK API surface.
* Add plugin settings classes(i.e. `AttributionSettings`, `CompassSettings`, `GesturesSettings`
  , `LocationComponentSettings`, `LogoSettings`, `ScaleBarSettings`) as the input state
  of `MapboxMap` composable function.
* Add `PointAnnotation`, `CircleAnnotation`, `PolylineAnnotation` and `PolygonAnnotation` composable
  functions to add an annotation to the map.
* Add `PointAnnotationGroup`, `CircleAnnotationGroup`, `PolylineAnnotationGroup`and
  `PolygonAnnotationGroup` composable functions to add an annotation group to the
  map; `PointAnnotationGroup` and `CircleAnnotationGroup` can be clustered based on configuration.
* Add `ViewAnnotation` composable function to add a view annotation to the map.
* Add `MapViewportState` API hat can be hoisted to control and observe the map's camera state.