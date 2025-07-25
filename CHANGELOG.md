# Changelog for Mapbox Maps SDK for Android

Mapbox welcomes participation and contributions from everyone.

# main

## Bug fixes 🐞
* Fix potential ANR (Application Not Responding) issue when retrieving display refresh rate during map initialization by offloading the system call to a background thread with proper timeout and fallback handling.


# 11.14.0
## Features ✨ and improvements 🏁
* Added experimental `MapView.scheduleThreadServiceTypeReset()` to reset the renderer thread service type to Interactive. This experimental API is intended for edge cases involving custom lifecycle management or specific scenarios where the default lifecycle behavior is insufficient.

## Bug fixes 🐞
* [compose] Avoid excessive debug logging on SourceState, which can result in Out Of Memory in extreme cases. Avoid appending geojson data in `GeoJsonSourceState.toString()` override.


# 11.14.0-rc.1 July 16, 2025
## Features ✨ and improvements 🏁
* Added `setContentDescription()` method to `AttributionPlugin` and `AttributionView` interfaces to programmatically set accessibility content description for the attribution button.
* Added `MapView.onResume()` which should be called in `onResume()` of the host activity or fragment to resume the map view if `plugin-lifecycle` is not used.
* Improved zoom animation performance by preloading target tiles and reducing unnecessary intermediate tile processing, resulting in smoother camera transitions and reduced frame rate drops.

## Bug fixes 🐞
* Fix exception when accessing enum properties in annotations.
* Fix an issue where instant animation might not have been executed in case of launching multiple animations within a single frame.
* Fix potential data race in Mapbox token initialization
* Minor fixes in `TileStore` service
* Fixed an issue where View Annotations would disappear when rotating and tilting the `MapView` with `allowOverlapWithPuck=false` due to incorrect intersection calculations with off-screen location pucks.

## Dependencies
* Update gl-native to v11.14.0-rc.1 and common to 24.14.0-rc.1.

# 11.14.0-beta.1 July 02, 2025

## Features ✨ and improvements 🏁
* Added new `FillLayer.fillPatternCrossFade`, `FillExtrusionLayer.fillExtrusionPatternCrossFade`, `LineLayer.fillExtrusionPatternCrossFade` properties.
* Provide depth range used for rendering 3D content to custom layers.
* Added new `split` expression, which returns an array of substrings from a string, split by a delimiter parameter.

## Bug fixes 🐞
* Apply obfuscation rules for generated `@MapboxModule` with `enableConfiguration = true`
* Handle getSharedPreferences exceptions in SettingsServiceHelper

## Dependencies
* Update gl-native to v11.14.0-beta.1, common to 24.14.0-beta.1.
* Update Mapbox Base Android library to [v0.12.0](https://github.com/mapbox/mapbox-base-android/releases/tag/v0.12.0).
* Update Mapbox Gestures Android library to [v0.9.1](https://github.com/mapbox/mapbox-gestures-android/releases/tag/v0.9.1).


# 11.13.1 June 18, 2025

## Bug fixes 🐞
* Apply a more robust policy for retrying failed network requests.

## Dependencies
* Update gl-native to v11.13.1, common to 24.13.1.

# 11.13.0
## Features ✨ and improvements 🏁
* Expose `RenderThreadStatsRecorder` as experimental API.
* Expose new experimental properties: `CircleLayer.circleElevationReference`, `FillLayer.fillConstructBridgeGuardRail`, `FillLayer.fillBridgeGuardRailColor`, `FillLayer.fillTunnelStructureColor`.
* Promote `MapInteraction` APIs to stable, remove experimental `Style.STANDARD_EXPERIMENTAL` constant, as `Style.STANDARD` supports featuresets and map interactions by default.
  * Add common `MapInteraction` APIs to `StandardStyleInteractionsState`: `.onFeaturesetClicked`, `.onLayerClicked`, `.onFeaturesetLongClicked`, `.onLayerLongClicked`, `.onMapClicked`, `.onMapLongClicked`.
  * Add `StyleInteractionsState` to `MapboxStandardSatelliteStyle` so interactions can be used with the satellite style.
* [compose] Rename experimental `ExperimentalStandardStyleState` to `StandardStyleState`, rename experimental `MapboxStandardStyleExperimental` to `MapboxStandardStyle(styleImportsContent, topSlot, middleSlot, bottomSlot, standardStyleState)`, and deprecate previous `MapboxStandardStyle` composable functions.
* Expose new experimental camera event `MapboxMap.subscribeCameraChangedCoalesced`, `MapboxMap.cameraChangedCoalescedEvents`.
* [compose] Expose new camera event `MapState.cameraChangedCoalescedEvents`
* Introduce custom layer matrices API `CustomLayerMapProjection` in `CustomLayerRenderParameters` for globe projection.
* Introduce `minZoom` and `maxZoom` properties for `CircleAnnotationManager`, `PointAnnotationManager`, `PolygonAnnotationManager`, `PolylineAnnotationManager`.
* Introduce experimental `worldview` expression.
* [compose] Introduce `minZoom` and `maxZoom` properties for `CircleAnnotationGroupState`, `PointAnnotationGroupState`, `PolygonAnnotationGroupState`, `PolylineAnnotationGroupState`.
* [compose] Add config option `showLandmarkIcons` to `MapboxStandardStyle`.
* Improve the performance of `MapboxMap.cameraForCoordinates(...)` for large amounts of points.

## Bug fixes 🐞
* Fix elevated lines rendering regression.
* Do not write errors to logs if event was not send because events are disabled.
* Fixed incorrect placement for symbols with symbol-z-elevate on Globe projection.
* Elevated symbol heights now change as smooth as building heighs.
* Fixing unstable placement for elevated symbols.
* Respect config expressions in filter.
* Fix symbol order with symbol-z-elevate true and symbol-z-order viewport-y.
* Fix issue that promoteId content is missing after vector source serialization.
* Fixed disappearing volatile tiles on camera changes in offline mode when `maximumStaleInterval` is set.
* Resolved frequent DVA repositioning issues.
* Fixed fill patterns for elevated roads.
* Tiles are now properly reloaded when the language setting is removed.
* Limited the collision grid size for view annotations to improve performance.
* Fix location indicator bearing animation update being missing in some occasions.
* Fix a bug where `cameraForCoordiantes` with screen box oscillates when initial zoom is close to set max zoom.
* Skip unneeded tiles relayout when landmark icons get enabled.
* Fix a crash in disk cache code when fetching a high-zoom (z > 16) tile.

## Dependencies
* Update gl-native to v11.13.0, common to 24.13.0.

# 11.12.4 June 12, 2025
## Bug fixes 🐞
* Fix location indicator bearing animation update being missing in some occasions.

## Dependencies
* Update gl-native to v11.12.4 and common to v24.12.4.

# 11.12.3 June 05, 2025
## Features ✨ and improvements 🏁
* Improve the performance of `MapboxMap.cameraForCoordinates(...)` for large amounts of points.

## Bug fixes 🐞
* Fix a crash in disk cache code when fetching a high-zoom (z > 16) tile.
* Fix a bug where cameraForCoordiantes with screen box oscillates when initial zoom is close to set max zoom.
## Dependencies
* Update gl-native to v11.12.3 and common to v24.12.3.

# 11.13.0-rc.1 June 03, 2025
## Features ✨ and improvements 🏁
* Introduce custom layer matrices API `CustomLayerMapProjection` in `CustomLayerRenderParameters` for globe projection.
* Introduce `minZoom` and `maxZoom` properties for `CircleAnnotationManager`, `PointAnnotationManager`, `PolygonAnnotationManager`, `PolylineAnnotationManager`.
* [compose] Introduce `minZoom` and `maxZoom` properties for `CircleAnnotationGroupState`, `PointAnnotationGroupState`, `PolygonAnnotationGroupState`, `PolylineAnnotationGroupState`.
* [compose] Add config option `showLandmarkIcons` to `MapboxStandardStyle`.

## Bug fixes 🐞
* Fixed disappearing volatile tiles on camera changes in offline mode when `maximumStaleInterval` is set.
* Resolved frequent DVA repositioning issues.
* Fixed fill patterns for elevated roads.
* Tiles are now properly reloaded when the language setting is removed.
* Limited the collision grid size for view annotations to improve performance.

## Dependencies
* Update gl-native to v11.13.0-rc.1, common to 24.13.0-rc.1.

# 11.13.0-beta.1 May 19, 2025
## Breaking changes ⚠️
* `PointAnnotation.iconImageCrossFade` has been deprecated and setting value to it will not have any impact. Use `PointAnnotationManager.iconImageCrossFadeTransition` instead.

## Features ✨ and improvements 🏁
* Expose `RenderThreadStatsRecorder` as experimental API.
* Expose new experimental properties: `CircleLayer.circleElevationReference`, `FillLayer.fillConstructBridgeGuardRail`, `FillLayer.fillBridgeGuardRailColor`, `FillLayer.fillTunnelStructureColor`.
* Promote `MapInteraction` APIs to stable, remove experimental `Style.STANDARD_EXPERIMENTAL` constant, as `Style.STANDARD` supports featuresets and map interactions by default.
  * Add common `MapInteraction` APIs to `StandardStyleInteractionsState`: `.onFeaturesetClicked`, `.onLayerClicked`, `.onFeaturesetLongClicked`, `.onLayerLongClicked`, `.onMapClicked`, `.onMapLongClicked`.
  * Add `StyleInteractionsState` to `MapboxStandardSatelliteStyle` so interactions can be used with the satellite style.
* [compose] Rename experimental `ExperimentalStandardStyleState` to `StandardStyleState`, rename experimental `MapboxStandardStyleExperimental` to `MapboxStandardStyle(styleImportsContent, topSlot, middleSlot, bottomSlot, standardStyleState)`, and deprecate previous `MapboxStandardStyle` composable functions.
* Expose new experimental camera event `MapboxMap.subscribeCameraChangedCoalesced`, `MapboxMap.cameraChangedCoalescedEvents`.
* [compose] Expose new camera event `MapState.cameraChangedCoalescedEvents`

## Bug fixes 🐞
* Fix elevated lines rendering regression.
* Do not write errors to logs if event was not send because events are disabled.
* Fixed incorrect placement for symbols with symbol-z-elevate on Globe projection.
* Elevated symbol heights now change as smooth as building heighs.
* Fixing unstable placement for elevated symbols.
* Respect config expressions in filter.
* Fix symbol order with symbol-z-elevate true and symbol-z-order viewport-y.
* Fix issue that promoteId content is missing after vector source serialization.

## Dependencies
* Update gl-native to v11.13.0-beta.1, common to 24.13.0-beta.1.


# 11.11.1 May 16, 2025
## Bug fixes 🐞
* Fixed a crash that occurred when a runtime-added unused image was removed and later reused.

## Dependencies
* Update gl-native to v11.11.4.

# 11.12.0 May 7, 2025
## Features ✨ and improvements 🏁
* Mapbox Geofencing is now available in public preview. Follow the documentation and code examples for [Android](https://docs.mapbox.com/android/maps/guides/geofencing/) and [iOS](https://docs.mapbox.com/ios/maps/guides/geofencing/) to get started.
* Avoid fetching pixelRatio from gl-native while rendering scalebar to improve CPU usage.
* Promote `MapView.attribution.getMapAttributionDelegate().extraAttributions` to stable.
* Expose an experimental API to define a non-rectangular screen culling shape(`MapboxMap.get/setScreenCullingShape`)
* Introduce experimental `colorUseTheme` API for `AmbientLight`, `DirectionalLight`, and `FlatLight` to override color theme of light.
* [compose] Introduce experimental `colorUseTheme` API for `AmbientLightState`, `DirectionalLightState`, and `FlatLightState` to override color theme of light.
* [compose] Introduce experimental `vignetteColorUseTheme` and `colorUseTheme` for `RainState` and `SnowState` which allows overriding color theme of precipitations.
* [compose] Annotate `rememberGeoJsonSourceState` as delicate API due to performance implications when used with large GeoJsonData.
* Avoid dynamic view annotation overlapping given symbol layers.
* Vector icons can now also be retrieved via `getStyleImage` API.
* Revert changes to `at` expression and add new `at-interpolated` expression.
* Enable tile pack v2 format by default.

## Bug fixes 🐞
* Fixed a crash that occurred when a runtime-added unused image was removed and later reused.
* `*-sort-key` properties now uses double, fixing incorrect sorting with big key values
* Fix registering camera change listener every time `mapView.scalebar.enabled` is called with value `true` and it was already enabled.
* Fix map flickering caused by the fill-extrusion pan tiles and missing stencil in 3D layers.
* Fix semi transparent landmark icons.
* Return null for config expression if requested config option is missing.
* Fix clipPath and mask rendering for vector icon rasterization.
* Fix dotted lines on tile borders.
* Fix pattern not found when using imports.
* Fix line layer not rendering if using `line-pattern` inside an imported style.
* Fix ground effect gradient not working with multiple polygons.
* Add simple bounds check to avoid crash during centroid computation.
* Fix invisible line when interpolating line-width from 0 to 1 using line-progress.
* Tile loading speedup.
* Fix URL migration to DB v10.

## Dependencies
* Update gl-native to v11.12.0 and common to v24.12.0.

# 11.12.0-rc.1
## Features ✨ and improvements 🏁
* Avoid fetching pixelRatio from gl-native while rendering scalebar to improve CPU usage.
* Promote `MapView.attribution.getMapAttributionDelegate().extraAttributions` to stable.


# 11.12.0-beta.1 April 09, 2025

## Features ✨ and improvements 🏁
* Introduce experimental `colorUseTheme` API for `AmbientLight`, `DirectionalLight`, and `FlatLight` to override color theme of light.
* [compose] Introduce experimental `colorUseTheme` API for `AmbientLightState`, `DirectionalLightState`, and `FlatLightState` to override color theme of light.
* [compose] Introduce experimental `vignetteColorUseTheme` and `colorUseTheme` for `RainState` and `SnowState` which allows overriding color theme of precipitations.
* [compose] Annotate `rememberGeoJsonSourceState` as delicate API due to performance implications when used with large GeoJsonData.
* Avoid dynamic view annotation overlapping given symbol layers.
* Vector icons can now also be retrieved via `getStyleImage` API.
* Revert changes to `at` expression and add new `at-interpolated` expression.
* Enable tile pack v2 format by default.

## Bug fixes 🐞
* Fix semi transparent landmark icons.
* Return null for config expression if requested config option is missing.
* Fix clipPath and mask rendering for vector icon rasterization.
* Fix dotted lines on tile borders.
* Fix pattern not found when using imports.
* Fix line layer not rendering if using `line-pattern` inside an imported style.
* Fix ground effect gradient not working with multiple polygons.
* Add simple bounds check to avoid crash during centroid computation.
* Fix invisible line when interpolating line-width from 0 to 1 using line-progress.
* Tile loading speedup.
* Fix URL migration to DB v10.

## Dependencies
* Update gl-native to v11.12.0-beta.1 and common to v24.12.0-beta.1.


# 11.11.0 March 26, 2025
## Features ✨ and improvements 🏁
* Support for landmark icons. Landmark icons are stylized, uniquely designed POI icons that indicate the most popular and recognizable landmarks on the map.
* Support expression input for `PromoteId`.
* Add a setting that allows geofencing users with Android <= 28 to access background locations. Add `mapbox-location-config.xml` file with the following content:
```xml
<resources>
  <bool name="com.mapbox.common.location.sdk28_use_background_permissions">true</bool>
</resources>
```
* Add `Expression` support for `*UseTheme` style properties to override color theme for particular color properties in all layers.
* Add experimental `*UseTheme` support for annotations and `LocationPuck3D`.
* Introduce experimental `Style.setImportColorTheme`, which allows changing the color theme of the style import.
* [compose] Introduce experimental `StyleImportState.styleColorTheme` which allows changing the color theme of the style import.
* Conflate `MapboxMap.mapLoadedEvents`, `MapboxMap.mapLoadingErrorEvents`, `MapboxMap.styleLoadedEvents`, `MapboxMap.styleDataLoadedEvents`, `MapboxMap.cameraChangedEvents`, `MapboxMap.mapIdleEvents`, `MapboxMap.sourceAddedEvents`, `MapboxMap.sourceRemovedEvents`, `MapboxMap.sourceDataLoadedEvents`, `MapboxMap.styleImageMissingEvents`, `MapboxMap.styleImageRemoveUnusedEvents`, `MapboxMap.renderFrameStartedEvents`, `MapboxMap.renderFrameFinishedEvents`, `MapboxMap.resourceRequestEvents` by default to avoid blocking main thread due to slow collectors.

## Bug fixes 🐞
* Fix annotation drag being triggered when multi-finger gesture is in progress.
* Fix missing vector images after style change.
* Ensure background color is correctly set.
* Fix background layer not being updated if raster image was updated in-place or if color theme changed.
* Trigger DVA replacement if zoom diff is big.
* Fix line placement symbol disappearing issue when in zoom level 5.
* Place view annotation away from camera when pitch > 45.
* Fix dark shades of gradient effect in night preset.
* Fix Custom Raster Source behavior on re-creation.
* Change unreachable connection retries to use exponential backoff.
* Fixed incorrect rgba to hsla conversion for white color.
* Fix vector images rasterization.
* Make katakana and CJK symbol rendered correctly in vertical writing mode.
* Fix a bug that may cause style packs to be either inaccessible or incorrect when updating an older tile store database created with Maps SDK 11.9 or earlier versions. **In systems that have already used Maps SDK 11.10.0 through 11.10.2, a re-download of style packs may be necessary.**
* Fix a map start time regression by speed up tile loading.

## Dependencies
* Update gl-native to v11.11.0 and common to v24.11.0.



# 11.10.3 March 19, 2025
## Bug fixes 🐞
* Fix a bug that may cause style packs to be either inaccessible or incorrect when updating an older tile store database created with Maps SDK 11.9 or earlier versions. **In systems that have already used Maps SDK 11.10.0 through 11.10.2, a re-download of style packs may be necessary.**
* Fix a map start time regression by speed up tile loading.

## Dependencies
* Update gl-native to v11.10.3, common to v24.10.1.


# 11.11.0-rc.1 March 12, 2025
## Features ✨ and improvements 🏁
* Add `Expression` support for `*UseTheme` style properties to override color theme for particular color properties in all layers.
* Add experimental `*UseTheme` support for annotations and `LocationPuck3D`.
* Introduce experimental `Style.setImportColorTheme`, which allows changing the color theme of the style import.
* [compose] Introduce experimental `StyleImportState.styleColorTheme` which allows changing the color theme of the style import.
* Conflate `MapboxMap.mapLoadedEvents`, `MapboxMap.mapLoadingErrorEvents`, `MapboxMap.styleLoadedEvents`, `MapboxMap.styleDataLoadedEvents`, `MapboxMap.cameraChangedEvents`, `MapboxMap.mapIdleEvents`, `MapboxMap.sourceAddedEvents`, `MapboxMap.sourceRemovedEvents`, `MapboxMap.sourceDataLoadedEvents`, `MapboxMap.styleImageMissingEvents`, `MapboxMap.styleImageRemoveUnusedEvents`, `MapboxMap.renderFrameStartedEvents`, `MapboxMap.renderFrameFinishedEvents`, `MapboxMap.resourceRequestEvents` by default to avoid blocking main thread due to slow collectors.

## Bug fixes 🐞
* Fixed incorrect rgba to hsla conversion for white color.
* Fix vector images rasterization.
* Make katakana and CJK symbol rendered correctly in vertical writing mode.

## Dependencies
* Update gl-native to v11.11.0-rc.1 and common to v24.11.0-rc.1.


# 11.11.0-beta.1 March 03, 2025
## Features ✨ and improvements 🏁
* Support expression input for `PromoteId`.
* Add a setting that allows geofencing users with Android <= 28 to access background locations. Add `mapbox-location-config.xml` file with the following content:
```xml
<resources>
  <bool name="com.mapbox.common.location.sdk28_use_background_permissions">true</bool>
</resources>
```

## Bug fixes 🐞
* Fix missing vector images after style change.
* Ensure background color is correctly set.
* Fix background layer not being updated if raster image was updated in-place or if color theme changed.
* Trigger DVA replacement if zoom diff is big.
* Fix line placement symbol disappearing issue when in zoom level 5.
* Place view annotation away from camera when pitch > 45.
* Fix dark shades of gradient effect in night preset.
* Fix Custom Raster Source behavior on re-creation.
* Change unreachable connection retries to use exponential backoff.

## Dependencies
* Update gl-native to v11.11.0-beta.1 and common to v24.11.0-beta.1.


# 11.10.2 February 25, 2025
## Bug fixes 🐞
* Fixing missing vector images after style change

## Dependencies
* Update gl-native to v11.10.2.


# 11.10.1 February 24, 2025
## Features ✨ and improvements 🏁
* Small performance improvements in the map initialization call chain by avoiding IPC calls on main.

## Bug fixes 🐞
* Fix LUT not being applied to in-place updated images.
* Fix in-place updates for SDF images.
* Fix background layer not being updated if raster image was updated in-place or if color theme changed.
* Vector icons supported in location indicator layer.
* Fix mipmaps for images updated inplace.
* Ensure background color is correctly set.

## Dependencies
* Update gl-native to v11.10.1.

# 11.10.0 February 13, 2025
## Features ✨ and improvements 🏁
* Localize geofencing attribution dialog.
* Introduce `ViewAnnotationOptions.priority`, deprecate `ViewAnnotationOptions.selected`. Use this property to define view annotation sort order.
* Introduce `ViewAnnotationOptions.minZoom` and `ViewAnnotationOptions.maxZoom`. Use these properties to configure zoom-level specific view annotations.
* Introduce `SymbolLayer.iconSizeScaleRange`, `SymbolLayer.textSizeScaleRange`, `FillLayer.fillElevationReference`, `LineLayer.lineCrossSlope`, `LineLayer.lineWidthUnit`, `LineLayer.lineCrossSlope`, `LineLayer.lineElevationReference`, `DirectionalLight.shadowQuality`, `Rain.distortionStrength`, `Rain.distortionStrengthTransition`, `Rain.dropletSize`, `Rain.dropletSizeTransition`, `Rain.vignetteColor`, `Rain.vignetteColorTransition`, `Snow.flakeSize`, `Snow.flakeSizeTransition`, `Snow.vignetteColor`, `Snow.vignetteColorTransition`.
* Introduce experimental Color Theme API, which allows changing the style of the map using `Style.setStyleColorTheme`.
* Introduce experimental `ModelLayer.modelElevationReference` property.
* Introduce experimental `LocationPuck3D.modelElevationReference` property.
* Add experimental `ViewAnnotationManager.setViewAnnotationAvoidLayers` for specifying layers that view annotations should avoid. The API currently only supports line layers.
* Add `*UseTheme` String style properties to override color theme for particular color properties in all layers and their Compose counterparts.
* Add support for the `maxOverscaleFactorForParentTiles` property in `CustomRasterSource` and `CustomGeometrySource`, allowing greater control over tile overscaling behavior when rendering custom raster tiles.
* Extend tilecover for elevated roads avoiding missing road segments.
* Change default value of experimental `Rain.opacity` to 0.19, default `Rain.vignette` to 0.3, `Snow.opacity` to 0.19, default `Snow.vignette` to 0.3.
* Update the default value of experimental properties: default `Snow.density` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.85]`; default `Snow.opacity` is updated to `1.0`; default `Snow.vignette` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.3]`; default `Snow.centerThinning` is updated to `0.4`, default `Snow.direction` is updated to `listOf(0.0, 50.0)`; default `Snow.flakeSize` is updated to `0.71`; default `Rain.density` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.85]`; default `Rain.color` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,"#03113d",0.3,"#a8adbc"]`; default `Rain.opacity` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,0.88,1,0.7]`;  default `Rain.vignette` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.3]`; default `Rain.vignetteColor` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,"#001736",0.3,"#464646"]`; default `Rain.centerThinning` is updated to `0.57`; default `Rain.dropletSize` is updated to `listOf(2.6, 18.2)`; default `Rain.distortionStrength` is updated to `0.7`.
* Remove `@MapboxExperimental` from `ClipLayer.clipLayerTypes` and `ClipLayer.clipLayerScope`.
* Remove `<profileable android:shell=true/>` flag in release manifest.
* Remove `libandroid-tests-support-code.so` from release AAR.
* Remove experimental `ShadowQuality` properties from direct light layer.
* [compose] Introduce experimental `StyleState.colorTheme` state for setting `ColorTheme` for the style.
* [compose] Introduce experimental `SymbolLayerState.iconSizeScaleRange`, `SymbolLayerState.textSizeScaleRange`, `FillLayerState.fillElevationReference`, `LineLayerState.lineCrossSlope`, `LineLayerState.lineWidthUnit`, `LineLayerState.lineCrossSlope`, `LineLayerState.lineElevationReference`, `DirectionalLightState.shadowQuality`, `RainState.distortionStrength`, `RainState.distortionStrengthTransition`, `RainState.dropletSize`, `RainState.dropletSizeTransition`, `RainState.vignetteColor`, `RainState.vignetteColorTransition`, `SnowState.flakeSize`, `SnowState.flakeSizeTransition`, `SnowState.vignetteColor`, `SnowState.vignetteColorTransition`.

## Bug fixes 🐞
* [compose] Fix wrong layer order when using `aboveLayer(..)` composable function.
* [compose] Fix annotation composable functions not working inside `slot(...)` composable function.
* [compose] Fix annotation composable functions rendered in wrong order.
* Prefer last used anchor for Dynamic View Annotation placement.
* Fix out of bound issue of gradient effect.
* Fix hidden elements caused by flood lighting.
* Fix parsing raster sprite images with float stretch/content coordinates.
* Fix glyph loading issue when incorrect glyph url is used.
* Speed up start with big old offline database.
* Fix disappearing tiles in some rare conditions.
* Fix rasterization of images with positive mask coordinates which caused vector icons not being rasterized correctly.
* Place Dynamic View Annotation further away from camera when pitch > 45. This allows Dynamic View Annotation to be placed further away from the puck so it is not repositioned frequently.
* Fix visual artifacts from `FillExtrusionLayer` for night presets.
* Fix precision issues with 3D puck and orthographic projection when zoom is more than 17. The fix prevents puck-shadow from disappearing in lower zoom scales in 2D-views.
* Fix road shadows issues when far away and low zoom levels.
* Fix app crash when autoMaxZoom=true.
* Fix an unhandled exception occurring during config expression parsing
* Mark `BackgroundLayer.backgroundPitchAlignment` as experimental.
* Skip any map scroll (panning) if shove gesture is already in progress preventing camera flying away.
* Fix crash on style reload if a config referenced in the expression was missing.
* Fix high cpu usage when map goes to background.
* Fix missing on style loaded call if sprite is absent in cache and there is no network connection.
* Fix background layers which used images from a mapbox-hosted style.
* Fix images being displayed incorrectly in some cases with line patterns and `LineJoin.NONE`.
* Fix too early sources loaded event.
* Fix to calculate image size for max text size, not for the next zoom level text size.
* Fix line rendering for layers with elevated and non-elevated buckets

## Dependencies
* Update gl-native to v11.10.0 and common to v24.10.0.



# 11.9.2 February 05, 2025
## Bug fixes 🐞
* Fix exception while parsing the config expression.
* Fix app crash when autoMaxZoom=true.
* Fixing parsing raster sprite images with float stretch/content coordinates.
* Fix color theme data decoding.
* Fix glyph loading issue when incorrect glyph url is used.

## Dependencies
* Update gl-native to v11.9.3.


# 11.10.0-rc.1 January 31, 2025
## Features ✨ and improvements 🏁
* Add experimental `ViewAnnotationManager.setViewAnnotationAvoidLayers` for specifying layers that view annotations should avoid. The API currently only supports line layers.
* Add support for the `maxOverscaleFactorForParentTiles` property in `CustomRasterSource` and `CustomGeometrySource`, allowing greater control over tile overscaling behavior when rendering custom raster tiles.
* Add `*UseTheme` String style properties to override color theme for particular color properties in all layers and their Compose counterparts.
* Remove experimental `ShadowQuality` properties from direct light layer.
* Extend tilecover for elevated roads avoiding missing road segments.
* Introduce experimental Color Theme API, which allows changing the style of the map using `Style.setStyleColorTheme`.
* [compose] Introduce experimental `StyleState.colorTheme` state for setting `ColorTheme` for the style.

## Bug fixes 🐞
* Prefer last used anchor for Dynamic View Annotation placement.
* Fix out of bound issue of gradient effect.
* Fix hidden elements caused by flood lighting.
* Fix parsing raster sprite images with float stretch/content coordinates.
* Fix glyph loading issue when incorrect glyph url is used.
* Fix color theme data decoding.
* Speed up start with big old offline database.
* Fix disappearing tiles in some rare conditions.
* Fix road shadows issues when far away and low zoom levels.

## Dependencies
* Update gl-native to v11.10.0-rc.1 and common to v24.10.0-rc.1.


# 11.10.0-beta.1 January 20, 2025
## Features ✨ and improvements 🏁
* Localize geofencing attribution dialog.
* Introduce `ViewAnnotationOptions.priority`, deprecate `ViewAnnotationOptions.selected`. Use this property to define view annotation sort order.
* Introduce `ViewAnnotationOptions.minZoom` and `ViewAnnotationOptions.maxZoom`. Use these properties to configure zoom-level specific view annotations.
* Introduce `SymbolLayer.iconSizeScaleRange`, `SymbolLayer.textSizeScaleRange`, `FillLayer.fillElevationReference`, `LineLayer.lineCrossSlope`, `LineLayer.lineWidthUnit`, `LineLayer.lineCrossSlope`, `LineLayer.lineElevationReference`, `DirectionalLight.shadowQuality`, `Rain.distortionStrength`, `Rain.distortionStrengthTransition`, `Rain.dropletSize`, `Rain.dropletSizeTransition`, `Rain.vignetteColor`, `Rain.vignetteColorTransition`, `Snow.flakeSize`, `Snow.flakeSizeTransition`, `Snow.vignetteColor`, `Snow.vignetteColorTransition`.
* Change default value of experimental `Rain.opacity` to 0.19, default `Rain.vignette` to 0.3, `Snow.opacity` to 0.19, default `Snow.vignette` to 0.3.
* [compose] Introduce experimental `SymbolLayerState.iconSizeScaleRange`, `SymbolLayerState.textSizeScaleRange`, `FillLayerState.fillElevationReference`, `LineLayerState.lineCrossSlope`, `LineLayerState.lineWidthUnit`, `LineLayerState.lineCrossSlope`, `LineLayerState.lineElevationReference`, `DirectionalLightState.shadowQuality`, `RainState.distortionStrength`, `RainState.distortionStrengthTransition`, `RainState.dropletSize`, `RainState.dropletSizeTransition`, `RainState.vignetteColor`, `RainState.vignetteColorTransition`, `SnowState.flakeSize`, `SnowState.flakeSizeTransition`, `SnowState.vignetteColor`, `SnowState.vignetteColorTransition`.
* Remove `@MapboxExperimental` from `ClipLayer.clipLayerTypes` and `ClipLayer.clipLayerScope`.
* Update the default value of experimental properties: default `Snow.density` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.85]`; default `Snow.opacity` is updated to `1.0`; default `Snow.vignette` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.3]`; default `Snow.centerThinning` is updated to `0.4`, default `Snow.direction` is updated to `listOf(0.0, 50.0)`; default `Snow.flakeSize` is updated to `0.71`; default `Rain.density` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.85]`; default `Rain.color` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,"#03113d",0.3,"#a8adbc"]`; default `Rain.opacity` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,0.88,1,0.7]`;  default `Rain.vignette` is updated to `["interpolate",["linear"],["zoom"],11,0,13,0.3]`; default `Rain.vignetteColor` is updated to `["interpolate",["linear"],["measure-light","brightness"],0,"#001736",0.3,"#464646"]`; default `Rain.centerThinning` is updated to `0.57`; default `Rain.dropletSize` is updated to `listOf(2.6, 18.2)`; default `Rain.distortionStrength` is updated to `0.7`.
* Introduce experimental `ModelLayer.modelElevationReference` property.
* Introduce experimental `LocationPuck3D.modelElevationReference` property.
* Remove `<profileable android:shell=true/>` flag in release manifest.
* Remove `libandroid-tests-support-code.so` from release AAR.

## Bug fixes 🐞
* Mark `BackgroundLayer.backgroundPitchAlignment` as experimental.
* Skip any map scroll (panning) if shove gesture is already in progress preventing camera flying away.
* Fix crash on style reload if a config referenced in the expression was missing.
* Fix high cpu usage when map goes to background.
* Fix missing on style loaded call if sprite is absent in cache and there is no network connection.
* Fix background layers which used images from a mapbox-hosted style.
* Fix images being displayed incorrectly in some cases with line patterns and `LineJoin.NONE`.
* Fix too early sources loaded event.

## Known issues ⚠️
* Disappearing tiles in some rare conditions. Fix will be available in the next release.

## Dependencies
* Update gl-native to v11.10.0-beta.2 and common to v24.10.0-beta.2.


# 11.9.1 January 20, 2025
## Bug fixes 🐞
* Add missing experimental annotation to `backgroundPitchAlignment` property.
* Fix crash on style reload if a config referenced in the expression was missing.
* Fix high cpu usage when map goes to background.
* Fix missing on style loaded call if sprite is absent in cache and there is no network connection.
* Fix background layers which used images from a mapbox-hosted style.
* Fix disappearing tiles in some rare conditions.

## Dependencies
* Update gl-native to v11.9.2.

# 11.9.0 December 18, 2024
## Known issues 🛑
We do not recommend using this version. Please use [11.9.1](https://github.com/mapbox/mapbox-maps-android/releases/tag/v11.9.1) or newer.

* When a map is currently visible and user press home button the [MapboxRenderThread](maps-sdk/src/main/java/com/mapbox/maps/renderer/RenderHandlerThread.kt) consumes CPU (potentially keeping **one** CPU busy).

## Breaking changes ⚠️
* Expose experimental Geofencing with `com.mapbox.annotation.MapboxExperimental`.
* Move experimental geofencing classes to `com.mapbox.common.geofencing` package from `com.mapbox.common.experimental.geofencing`.
* Make constructor of experimental `GeofencingOptions`, `GeofencingEvent` and other geofencing classes holding data private. Associated `Builder()` classes should be used instead.
* Remove experimental `MapboxMap.queryRenderedFeatures` and `MapboxMap.querySourceFeatures` that used `FeaturesetQueryTarget` as an argument.

## Features ✨ and improvements 🏁
* Introduce experimental `FillExtrusionLayer.fillExtrusionBaseAlignment` and `FillExtrusionLayer.fillExtrusionHeightAlignment` APIs to control the behavior of fill extrusion base over terrain and the behavior of fill extrusion height over terrain respectively.
* Introduce experimental `FillLayer.fillZOffset` and `FillLayer.fillZOffsetTransition` APIs to specify a uniform elevation in meters and define the transition of `fillZOffset` respectively.
* Adds support for `fillZOffset` in `PolygonAnnotation`, `PolygonAnnotationManager`, `PolygonAnnotationOptions`.
* Introduce `BackgroundLayer.backgroundPitchAlignment` API to configure the orientation of background layer.
* Introduce `LocationIndicatorLayer.emphasisCircleGlowRange` and `LocationIndicatorLayer.emphasisCircleGlowRangeTransition` APIs to control the glow effect of the emphasis circle from the solid start to the fully transparent end and to set the transition options for the `emphasisCircleGlowRange` property, respectively.
* Introduce `radius` parameter for `ClickInteraction` and `LongClickInteraction` to support an extra area around the interaction.
* Add a way to specify options for `Expression.image()`.
* Introduce experimental `AnimatableModel`, `ModelMaterialPart`, `ModelNodePart` APIs to style the 3D location puck's overridable parts. [Implementation example](app/src/main/java/com/mapbox/maps/testapp/examples/LocationComponentModelAnimationActivity.kt).
* Introduce `modelRotationExpression`, `modelColor`, `modelColorExpression`, `modelColorMixIntensity`, `modelColorMixIntensityExpression`, `modelOpacityExpression` on `LocationPuck3D`.
* Introduce experimental `LocationPuck3D.materialOverrides` and `LocationPuck3D.nodeOverrides` API to allow model parts overrides.
* Add vector icons support: SDK will now download vector icons and rasterize them locally, which will results in better icon quality for icons resized via icon-size. Changeable colors support: icon expression can now have optional parameter to change named colors described in SVG icons metadata.
* Add support for shadows from elevated structures.
* Add `toHsla` expression.
* Introduce experimental `Snow` and `Rain` APIs to show the snow or rain effect on the map.
* Expose experimental `getFeaturesets` for `MapboxMap` and `Style`.
* Remove`MapboxExperimental` from `ClipLayer`.
* [compose] Introduce `LocationIndicatorLayerState.emphasisCircleGlowRange` and `LocationIndicatorLayerState.emphasisCircleGlowRangeTransition` properties.
* [compose] Introduce `FillLayerState.fillZOffset` and `FillLayerState.fillZOffsetTransition` properties.
* [compose] Introduce `FillExtrusionLayerState.fillExtrusionBaseAlignment` and `FillExtrusionLayerState.fillExtrusionHeightAlignment` properties.
* [compose] Introduce `BackgroundLayerState.backgroundPitchAlignment` property.
* [compose] Adds support for `fillZOffset` in `PolygonAnnotationState`, `PolygonAnnotationGroupState`.
* [compose] Expose `MapViewportState.cameraForCoordinates` method.
* [compose] Introduce `radius` parameter for all relevant compose functions for interactions to support an extra area around the interaction.
* [compose] Introduce experimental `SnowState` and `RainState` APIs to show the snow or rain effect on the map.

## Bug fixes 🐞
* Fix dark shades caused by corner case light directions when `FillLayer.fillExtrusionEmissiveStrength` is set to high values (closer to 1).
* Fix rendering of interleaved SDF and non-SDF icons in the same layer.
* Fix `LineLayer.lineEmissiveStrength` not being applied to patterned lines.
* Fix map flickering on some Mali and PowerVR GPUs.
* Fix shader fog computation being incorrectly enabled for landmarks.
* Handle empty payloads for offline resources.
* Encapsulate config expression in assertion expression when expected return type is known.
* Fixes a bug which caused icon shifts in some cases.
* Fix feature state update if layer contains data driven `measureLight` expression.
* Fix the owning thread log error prints on legacy `OfflineRegion` creation.
* Fix a crash in interpolate expression.
* Resolve usage of `GeoJsonSource.autoMaxZoom` for single feature.
* Resolve dotted line issue with very long lines.
* Fix texture gather for shadows not being selected correctly by default.
* Performance improvements for runtime-added images.
* Fix `AndroidDeviceLocationProvider` reporting an error when location permissions are not granted.
* Improve exception handling inside Cronet providers when Cronet library failed to load.
* Improve character spacing for text offsets.
* Fixed crash on Android API level < 26.
* Do not load vector icons for client-provided sprites.
* Fall back to the feature's original ID when promoteId is an object and the source layer is not specified as a key in the object.
* Fixed crash caused by a repeated command buffer commit call.
* Fixed invalid processing of icon-size 0, using biggest of two image sizes with interpolated icon-size during vector icons rasterization.

## Dependencies
* Update gl-native to v11.9.0 and common to v24.9.0.



# 11.9.0-rc.1 December 10, 2024
## Features ✨ and improvements 🏁
* Add `toHsla` expression.
* Introduce experimental `Snow` and `Rain` APIs to show the snow or rain effect on the map.
* [compose] Introduce experimental `SnowState` and `RainState` APIs to show the snow or rain effect on the map.

## Bug fixes 🐞
* Improve character spacing for text offsets.
* Fixed crash on Android API level < 26.
* Do not load vector icons for client-provided sprites.
* Fall back to the feature's original ID when promoteId is an object and the source layer is not specified as a key in the object.
* Fixed crash caused by a repeated command buffer commit call.

## Dependencies
* Update gl-native to v11.9.0-rc.1 and common to v24.9.0-rc.1.


# 11.8.1 December 03, 2024
## Bug fixes 🐞
* Fix map flickering on some Mali and PowerVR GPUs.

## Dependencies
* Update gl-native to v11.8.1.


# 11.9.0-beta.1 November 28, 2024
## Breaking changes ⚠️
* Expose experimental Geofencing with `com.mapbox.annotation.MapboxExperimental`.
* Move experimental geofencing classes to `com.mapbox.common.geofencing` package from `com.mapbox.common.experimental.geofencing`.
* Make constructor of experimental `GeofencingOptions`, `GeofencingEvent` and other geofencing classes holding data private. Associated `Builder()` classes should be used instead.
* Remove experimental `MapboxMap.queryRenderedFeatures` and `MapboxMap.querySourceFeatures` that used `FeaturesetQueryTarget` as an argument.

## Features ✨ and improvements 🏁
* Introduce experimental `FillExtrusionLayer.fillExtrusionBaseAlignment` and `FillExtrusionLayer.fillExtrusionHeightAlignment` APIs to control the behavior of fill extrusion base over terrain and the behavior of fill extrusion height over terrain respectively.
* Introduce experimental `FillLayer.fillZOffset` and `FillLayer.fillZOffsetTransition` APIs to specify a uniform elevation in meters and define the transition of `fillZOffset` respectively.
* Adds support for `fillZOffset` in `PolygonAnnotation`, `PolygonAnnotationManager`, `PolygonAnnotationOptions`.
* Introduce `BackgroundLayer.backgroundPitchAlignment` API to configure the orientation of background layer.
* Introduce `LocationIndicatorLayer.emphasisCircleGlowRange` and `LocationIndicatorLayer.emphasisCircleGlowRangeTransition` APIs to control the glow effect of the emphasis circle from the solid start to the fully transparent end and to set the transition options for the `emphasisCircleGlowRange` property, respectively.
* Introduce `radius` parameter for `ClickInteraction` and `LongClickInteraction` to support an extra area around the interaction.
* Add a way to specify options for `Expression.image()`.
* Introduce experimental `AnimatableModel`, `ModelMaterialPart`, `ModelNodePart` APIs to style the 3D location puck's overridable parts. [Implementation example](app/src/main/java/com/mapbox/maps/testapp/examples/LocationComponentModelAnimationActivity.kt).
* Introduce `modelRotationExpression`, `modelColor`, `modelColorExpression`, `modelColorMixIntensity`, `modelColorMixIntensityExpression`, `modelOpacityExpression` on `LocationPuck3D`.
* Introduce experimental `LocationPuck3D.materialOverrides` and `LocationPuck3D.nodeOverrides` API to allow model parts overrides.
* Add vector icons support: SDK will now download vector icons and rasterize them locally, which will results in better icon quality for icons resized via icon-size. Changeable colors support: icon expression can now have optional parameter to change named colors described in SVG icons metadata.
* Add support for shadows from elevated structures.
* [compose] Introduce `LocationIndicatorLayerState.emphasisCircleGlowRange` and `LocationIndicatorLayerState.emphasisCircleGlowRangeTransition` properties.
* [compose] Introduce `FillLayerState.fillZOffset` and `FillLayerState.fillZOffsetTransition` properties.
* [compose] Introduce `FillExtrusionLayerState.fillExtrusionBaseAlignment` and `FillExtrusionLayerState.fillExtrusionHeightAlignment` properties.
* [compose] Introduce `BackgroundLayerState.backgroundPitchAlignment` property.
* [compose] Adds support for `fillZOffset` in `PolygonAnnotationState`, `PolygonAnnotationGroupState`.
* [compose] Expose `MapViewportState.cameraForCoordinates` method.
* [compose] Introduce `radius` parameter for all relevant compose functions for interactions to support an extra area around the interaction.

## Bug fixes 🐞
* Fix dark shades caused by corner case light directions when `FillLayer.fillExtrusionEmissiveStrength` is set to high values (closer to 1).
* Fix rendering of interleaved SDF and non-SDF icons in the same layer.
* Fix `LineLayer.lineEmissiveStrength` not being applied to patterned lines.
* Fix map flickering on some Mali and PowerVR GPUs.
* Fix shader fog computation being incorrectly enabled for landmarks.
* Handle empty payloads for offline resources.
* Encapsulate config expression in assertion expression when expected return type is known.
* Fixes a bug which caused icon shifts in some cases.
* Fix feature state update if layer contains data driven `measureLight` expression.
* Fix the owning thread log error prints on legacy `OfflineRegion` creation.
* Fix a crash in interpolate expression.
* Resolve usage of `GeoJsonSource.autoMaxZoom` for single feature.
* Resolve dotted line issue with very long lines.
* Fix texture gather for shadows not being selected correctly by default.
* Performance improvements for runtime-added images.
* Fix `AndroidDeviceLocationProvider` reporting an error when location permissions are not granted.
* Improve exception handling inside Cronet providers when Cronet library failed to load.

## Dependencies
* Update gl-native to v11.9.0-beta.1 and common to v24.9.0-beta.1.

## Known issues
* Custom (non Mapbox-hosted) sprites could fail to load in some scenarios.


# 11.7.3 November 19, 2024
## Bug fixes 🐞
* Fix map flickering on some Mali and PowerVR GPUs.

## Dependencies
* Update gl-native to v11.7.3.


# 11.8.0 November 11, 2024
## Breaking changes ⚠️
* Change the signature of experimental `MapboxMap.queryRenderedFeatures(RenderedQueryGeometry, TypedFeaturesetDescriptor, Value?, QueryRenderedFeaturesetFeaturesCallback)` to `MapboxMap.queryRenderedFeatures(TypedFeaturesetDescriptor, RenderedQueryGeometry?, Value?, QueryRenderedFeaturesetFeaturesCallback)`. `RenderedQueryGeometry` being NULL is equivalent to passing a bounding box encompassing the entire map viewport.
* [compose] Change the signature of experimental `MapState.queryRenderedFeatures(RenderedQueryGeometry, TypedFeaturesetDescriptor, Expression?): List` to `MapState.queryRenderedFeatures(TypedFeaturesetDescriptor, RenderedQueryGeometry?, Expression?): List`. `RenderedQueryGeometry` being NULL is equivalent to passing a bounding box encompassing the entire map viewport.

## Features ✨ and improvements 🏁
* Introduce `Style.STANDARD_EXPERIMENTAL` style supporting featuresets and map interactions. **Important: this style should not be used in production as the style definition on backend is a subject to change after v11.8.0 stable release!**
* Introduce fully typed map click and long click interactions working with `Style.STANDARD_EXPERIMENTAL`: `standardPoi`, `standardPlaceLabels`, `standardBuildings`.
* Use [Cronet](https://developer.android.com/develop/connectivity/cronet) as the default network stack. If Cronet is not available, network stack defaults to [OkHttp](https://square.github.io/okhttp/) used in previous versions. More information available [here](https://docs.mapbox.com/android/maps/guides/install/#managing-google-play).
* Introduce `OnClusterClickListener` and `OnClusterLongClickListener` for `CircleAnnotationManager` and `PointAnnotationManager`. These callbacks receive the clicked cluster represented by a `ClusterFeature`.
* Introduce experimental `getStyleGlyphURL` / `setStyleGlyphURL` functions for `MapboxMap` and `Style`.
* Make `fill-extrusion-emissive-strength` property data-driven.
* Dispatch view annotations update before rendering, so that view annotations and map layers are rendered simultaneously and thus decreasing the view annotations latency when using `ViewAnnotationUpdateMode.MAP_FIXED_DELAY` mode.
* Overscale composited tile components in offline.
* Skip rendering landmarks when the camera is inside them.
* Introduce experimental Geofencing API. Implementation example: [SimpleGeofencingActivity.kt](app/src/main/java/com/mapbox/maps/testapp/examples/geofence/SimpleGeofencingActivity.kt) and [ExtendedGeofencingActivity.kt](app/src/main/java/com/mapbox/maps/testapp/examples/geofence/ExtendedGeofencingActivity.kt).
* Introduce experimental `MapView.attribution.getMapAttributionDelegate().extraAttributions` to add custom attributions to the attribution dialog.
* [compose] Deprecate all `Annotation` and `AnnotationGroup` composables that take `onClick` parameter. Now all annotation interactions could be set with appropriate `AnnotationInteractionsState` or `AnnotationGroupInteractionsState` stored in `AnnotationGroupState`.
* [compose] Introduce `AnnotationInteractionsState` and `AnnotationGroupInteractionsState` states that allow to set callbacks for annotation interactions via `onClicked()` and `onLongClicked()`.`PointAnnotationGroupInteractionsState` and `CircleAnnotationGroupInteractionsState` also provide ability to set callbacks for interactions with clusters via `onClusterClicked` and `onClusterLongClicked`.
* [compose] Introduce `remember` (e.g. `rememberPolylineAnnotationGroupInteractionsState` and `rememberPolylineAnnotationInteractionsState`) composable functions to create, init and remember all types of `AnnotationInteractionsState` and `AnnotationGroupInteractionsState`.
* [compose] Introduce `<AnnotationType>InteractionsState.isDraggable` / `<AnnotationType>GroupInteractionsState.isDraggable` API for all annotation types allowing to drag annotations. Callbacks `onDragStarted()`, `onDragged()`,`onDragFinished()` are added as well.
* [compose] Introduce experimental `Attribution(..., geofencingDialog)` compose function to customize Geofencing consent dialog.
* Annotate `Bitmap.toMapboxImage()` and related as delicate API due to its native memory allocation.
* Use fallback engine if cronet fails to load.

## Bug fixes 🐞
* Fix terrain related snapshotter crash.
* Handle empty payloads for offline resources.
* Improve zooming performance on dynamic Standard terrain and optimize terrain re-rendering performance on e.g routeline `line-trim-offset` change.
* Respect polygons with holes on querying rendered features.
* Fix self-overlap of line corners when large `line-width` is used.
* Adjust conflation intersection test padding to fix disappearing `fill-extrusion`.
* Fix TileCover bug with polygon horizontal edges.
* Fix a bug with image dependent paint properties not getting a correct value after image become available.
* Clear tile pyramid on color theme change before the tiles are updated.
* Fix missing images notifications for images within coalesce expression when other images in coalesce are present. Image expressions with two arguments are no longer being considered present if only second image is present.
* Return operation error for featurestate related API in case the featureset is not valid.
* Fix crash on style pack load when no access token is set.
* Fix crash in TerrainRenderer when using snapshotter.
* Fix crash on re-creation of a custom raster source when different options are provided.
* Return parsing errors if runtime added style import JSON is not valid.
* Fix missing models in rendering result if `reduceMemoryUse` is called before taking snapshot.
* Fix the incorrect behaviour when using `symbol-z-oder` property.
* Fix `raster-particle` trail discontinuity at the antimeridian.
* Fix an Android 12 specific bug where location puck custom animator options lambda without explicit `ValueAnimator.duration` resulted in `duration = 0`.
* Fix a rare `android.content.res.Resources$NotFoundException` happening when creating a `MapView`.
* Fix a rare `NullPointerException` happening when fling gesture event is recognized.
* Fix `StandardPoiFeature.geometry` to have a concrete `Point` type instead of `Geometry` interface.
* Fix a bug in `GeoJsonSource.autoMaxZoom` leading to rendering artifacts with long dotted line layers.
* Fix a crash if Cronet failed to init on device by fallbacking to OkHttp.
* Disable false-positive lint error "Incorrect number of expressions".
* Fix possible out of memory in native heap during annotation manager annotation updates (`AnnotationManager.update(...)`).
* Fix CronetProviderInstaller proguard missing rule.
* Fix rare crash due to native library not loaded when receiving background locations.
* Do not modify file description structure when reading resource files.
* Fix lifecycle calculation in case activity destruction is in progress.

## Dependencies
* Update gl-native to v11.8.0 and common to v24.8.0.



# 11.7.2 November 05, 2024
## Bug fixes 🐞
* Fix terrain related snapshotter crash.
* Handle empty payloads for offline resources.
* Do not modify file description structure when reading resource files.

## Dependencies
* Update gl-native to v11.7.2 and common to v24.7.2.



# 11.8.0-rc.1 October 23, 2024
## Bug fixes 🐞
* Fix an Android 12 specific bug where location puck custom animator options lambda without explicit `ValueAnimator.duration` resulted in `duration = 0`.
* Fix a rare `android.content.res.Resources$NotFoundException` happening when creating a `MapView`.
* Fix a rare `NullPointerException` happening when fling gesture event is recognized.
* Fix `StandardPoiFeature.geometry` to have a concrete `Point` type instead of `Geometry` interface.
* Fix a bug in `GeoJsonSource.autoMaxZoom` leading to rendering artifacts with long dotted line layers.
* Fix a crash if Cronet failed to init on device by fallbacking to OkHttp.

## Dependencies
* Update gl-native to v11.8.0-rc.1 and common to v24.8.0-rc.1.


# 11.8.0-beta.1 October 14, 2024
## Features ✨ and improvements 🏁
* Introduce `Style.STANDARD_EXPERIMENTAL` style supporting featuresets and map interactions. **Important: this style should not be used in production as the style definition on backend is a subject to change after v11.8.0 stable release!**
* Introduce fully typed map click and long click interactions working with `Style.STANDARD_EXPERIMENTAL`: `standardPoi`, `standardPlaceLabels`, `standardBuildings`.
* Use [Cronet](https://developer.android.com/develop/connectivity/cronet) as the default network stack. If Cronet is not available, network stack defaults to [OkHttp](https://square.github.io/okhttp/) used in previous versions. More information available [here](https://docs.mapbox.com/android/maps/guides/install/#managing-google-play).
* Introduce `OnClusterClickListener` and `OnClusterLongClickListener` for `CircleAnnotationManager` and `PointAnnotationManager`. These callbacks receive the clicked cluster represented by a `ClusterFeature`.
* Introduce experimental `getStyleGlyphURL` / `setStyleGlyphURL` functions for `MapboxMap` and `Style`.
* Make `fill-extrusion-emissive-strength` property data-driven.
* Dispatch view annotations update before rendering, so that view annotations and map layers are rendered simultaneously and thus decreasing the view annotations latency when using `ViewAnnotationUpdateMode.MAP_FIXED_DELAY` mode.
* Overscale composited tile components in offline.
* Skip rendering landmarks when the camera is inside them.
* Introduce experimental Geofencing API. Implementation example: [GeofencingActivity.kt](app/src/main/java/com/mapbox/maps/testapp/examples/geofence/GeofencingActivity.kt)
* Introduce experimental `MapView.attribution.getMapAttributionDelegate().extraAttributions` to add custom attributions to the attribution dialog.
* [compose] Deprecate all `Annotation` and `AnnotationGroup` composables that take `onClick` parameter. Now all annotation interactions could be set with appropriate `AnnotationInteractionsState` or `AnnotationGroupInteractionsState` stored in `AnnotationGroupState`.
* [compose] Introduce `AnnotationInteractionsState` and `AnnotationGroupInteractionsState` states that allow to set callbacks for annotation interactions via `onClicked()` and `onLongClicked()`.`PointAnnotationGroupInteractionsState` and `CircleAnnotationGroupInteractionsState` also provide ability to set callbacks for interactions with clusters via `onClusterClicked` and `onClusterLongClicked`.
* [compose] Introduce `remember` (e.g. `rememberPolylineAnnotationGroupInteractionsState` and `rememberPolylineAnnotationInteractionsState`) composable functions to create, init and remember all types of `AnnotationInteractionsState` and `AnnotationGroupInteractionsState`.
* [compose] Introduce `<AnnotationType>InteractionsState.isDraggable` / `<AnnotationType>GroupInteractionsState.isDraggable` API for all annotation types allowing to drag annotations. Callbacks `onDragStarted()`, `onDragged()`,`onDragFinished()` are added as well.
* [compose] Introduce experimental `Attribution(..., geofencingDialog)` compose function to customize Geofencing consent dialog.

## Bug fixes 🐞
* Improve zooming performance on dynamic Standard terrain and optimize terrain re-rendering performance on e.g routeline `line-trim-offset` change.
* Respect polygons with holes on querying rendered features.
* Fix self-overlap of line corners when large `line-width` is used.
* Adjust conflation intersection test padding to fix disappearing `fill-extrusion`.
* Fix TileCover bug with polygon horizontal edges.
* Fix a bug with image dependent paint properties not getting a correct value after image become available.
* Clear tile pyramid on color theme change before the tiles are updated.
* Fix missing images notifications for images within coalesce expression when other images in coalesce are present. Image expressions with two arguments are no longer being considered present if only second image is present.
* Return operation error for featurestate related API in case the featureset is not valid.
* Fix crash on style pack load when no access token is set.
* Fix crash in TerrainRenderer when using snapshotter.
* Fix crash on re-creation of a custom raster source when different options are provided.
* Return parsing errors if runtime added style import JSON is not valid.
* Fix missing models in rendering result if `reduceMemoryUse` is called before taking snapshot.
* Fix the incorrect behaviour when using `symbol-z-oder` property.
* Fix `raster-particle` trail discontinuity at the antimeridian.

## Dependencies
* Update gl-native to v11.8.0-beta.1 and common to v24.8.0-beta.1.

# 11.7.1 October 10, 2024
## Bug fixes 🐞
* Respect polygons with holes on querying rendered features.

## Dependencies
* Update gl-native to v11.7.1 and common to v24.7.1.


# 11.7.0 September 26, 2024
## Features ✨ and improvements 🏁
* [compose] Introduce `PointAnnotationState.iconOcclusionOpacity`, `PointAnnotationState.textOcclusionOpacity` to control occlusion opacity for individual point annotation.
* [compose] Remove `MapboxExperimental` from `PolylineAnnotationGroupState.lineOcclusionOpacity`, `PointAnnotationGroupState.iconOcclusionOpacity`, `PointAnnotationGroupState.textOcclusionOpacity`.
* [compose] Expose data-driven properties on `AnnotationGroupState`s. Now it's possible to set data-driven properties globally on `AnnotationGroupState` and specify per-annotation overrides in `AnnotationState`. Setting global property value on `AnnotationGroupState` could introduce performance improvements when the amount of annotations is large.
* [compose] Remove experimental `ModelLayerState.modelFrontCutoff`.
* [compose] Introduce experimental `GeoJsonSourceState.autoMaxZoom` property to resolve rendering artifacts for features that use wide blur (e.g. fill extrusion ground flood light or circle layer).
* [compose] Introduce experimental `ClipLayerState.clipLayerScope` API to remove data from certain style fragments only.
* [compose] Introduce experimental `PointAnnotationState.symbolZOffset` property.
* [compose] Introduce experimental `PointAnnotationGroupState.symbolElevationReference` property.
* [compose] Introduce experimental `SymbolLayerState.symbolElevationReference` and `SymbolLayerState.symbolZOffset` properties.
* [compose] Introduce experimental `SymbolLayerState.symbolZOffsetTransition` API.
* [compose] Introduce experimental `StyleInteractionsState`, `StyleImportsInteractionsState`, `LayerInteractionsState` to handle interactions to the style, style imports and layers.
* [compose] Introduce experimental `StyleImport` composable functions to accept `StyleImportsInteractionsState` as an parameter.
* [compose] Introduce experimental `LayerInteractionsState` as part of `*LayerState` of layers that's driven by a source.
* [compose] Introduce experimental `MapState.getFeatureState`, `MapState.setFeatureState`, `MapState.removeFeatureState` and `MapState.resetFeatureState` APIs.
* [compose] Introduce `StyleState` that groups `StyleImportsConfig`, `StyleInteractionsState`, `Projection`, `AtmosphereState`, `TerrainState`, `LightsState`, `styleTransition`.
* [compose] Introduce `GenericStyle` composable function that uses `StyleState` as parameter and deprecate `GenericStyle` that takes individual light/terrain/projection states.
* [compose] Introduce `MapStyle` composable function that uses `StyleState` as parameter and deprecate `MapStyle` that takes individual light/terrain/projection states.
* [compose] Introduce `StyleImportState` that groups `ImportConfigs` and `StyleImportInteractionsState`.
* [compose] Introduce `StyleImport` composable function that uses `StyleImportState` as parameter and deprecate `StyleImport` that takes `ImportConfigs`.
* Publish Mapbox Maps Android artifacts using NDK 27 and [support for 16 KB page sizes](https://developer.android.com/guide/practices/page-sizes).
  * The new artifacts are available by appending `-ndk27` to the artifact ID (for example, `com.mapbox.maps:android-ndk27:11.7.0`).
* Introduce experimental interactive feature elements and related APIs. Those APIs provide the convenient way to add the click / long click listener to layer / featureset / map itself with `MapboxMap.addInteraction`.
* Expose `lineTrimColor` and `lineTrimFadeRange` on `LineLayer` which allow to set custom color for trimmed line and fade effect for trim. Update navigation example to use those properties.
* Introduce `PointAnnotation.iconOcclusionOpacity`, `PointAnnotation.textOcclusionOpacity` to control occlusion opacity for individual point annotation.
* Remove `MapboxExperimental` from `SymbolLayer.iconOcclusionOpacity`, `SymbolLayer.textOcclusionOpacity` and these properties are now supported on global zoom levels with default value changed to `0`.
* Remove `MapboxExperimental` from `PolylineAnnotationManager.lineOcclusionOpacity`, `PointAnnotationManager.iconOcclusionOpacity`, `PointAnnotationManager.textOcclusionOpacity`.
* Expose data-driven properties on `AnnotationManager`s. Now it's possible to set data-driven properties globally on `AnnotationManager` and specify per-annotation overrides. Setting global property value on `AnnotationManager` could introduce performance improvements when the amount of annotations is large.
* Remove experimental `ModelLayer.modelFrontCutoff`.
* Introduce experimental `GeoJsonSource.autoMaxZoom` property to resolve rendering artifacts for features that use wide blur (e.g. fill extrusion ground flood light or circle layer).
* Introduce static `HttpServiceFactory.setCancellationCallback` API.
* Reduce style parsing time.
* Enable multiple meta tiling schemes for composited sources.
* Expose experimental `ClipLayer.clipLayerScope` API to remove data from certain style fragments only.
* Expose experimental `PointAnnotation.symbolZOffset` property.
* Expose experimental `PointAnnotationManager.symbolElevationReference` property.
* Expose experimental `SymbolLayer.symbolElevationReference` and `SymbolLayer.symbolZOffset` properties.
* Expose experimental `SymbolLayer.symbolZOffsetTransition` API.
* Introduce experimental `MapboxMap.queryRenderedFeature` allowing to get an `InteractiveFeature` for given geometry, `FeaturesetHolder` and optional filter.
* Introduce experimental `FillExtrusionLayer.fillExtrusionLineWidth` and `FillExtrusionLayerState.fillExtrusionLineWidth` in Compose allowing to switch fill extrusion rendering into wall rendering mode. Use this property to render the feature with the given width over the outlines of the geometry.
* Add missing experimental annotation to `PointAnnotationManager.symbolZOffset` and `PolylineAnnotationManager.lineZOffset`
* Deprecate `PolylineAnnotationManager.lineTrimColor` in favour of `PolylineAnnotationManager.lineTrimColorString`/`PolylineAnnotationManager.lineTrimColorInt`.

## Bug fixes 🐞
* [compose] Fix `ViewAnnotation` size not being updated when content layout changes.
* [compose] Fix `UnsatisfiedLinkError` issue when rendering preview.
* Return parsing errors if runtime added style import JSON is not valid.
* Fix color theme change before the tiles are updated.
* Fix annotation issues with click / long click callback ordering and dragging. Now the order of triggering the callbacks / dragging is determined: from top-level rendered annotation to bottom-level one.
* Fix `OnAnnotationInteractionListener` not triggered if any `OnAnnotationClickListener.onAnnotationClick` returns true.
* Fix incorrect layer updates when using `ClipLayer.clipLayerTypes` or `ClipLayerState.clipLayerTypes` in Compose.
* Fix shadow rendering issues when using `FillExtrusionLayer.fillExtrusionCutoffFadeRange` or `FillExtrusionLayerState.fillExtrusionCutoffFadeRange`.
* Significantly reduce the rate at which raster-particle trails cross each other.
* Fix normal offset to improve shadow accuracy.
* Fix data synchronization between renderer and the main threads on map resize.
* Regenerate location indicator mipmap on image change.
* Fix landmark visibility issues near tile borders.
* Fix elevated line depth occlusion issue in 2D mode.
* Fix Dynamic View Annotation position update for annotated geojson feature with `allowZElevate` to be true.
* Fix race condition between `setFeatureState` and `removeFeatureState` / `resetFeatureStates` resulting in feature state not being applied or removed.
* Fix a crash when calling `CameraAnimationsPlugin.easeTo()` with empty camera options or `CameraAnimationsPlugin.playAnimatorsSequentially()` / `CameraAnimationsPlugin.playAnimatorsTogether()` with an empty array of animators.
* Fix ongoing animations being canceled when `CameraAnimationsPlugin.flyTo()` with empty camera options is called.
* Fix simultaneous scale and rotation gestures not working when the first registered rotation is a scale one.
* Fix crash on Android tilestore where null data was returned.
* Fix the incorrect behaviour when using `SymbolLayer.symbolZOrder` property.
* Fix retrieval of tilesets for 3d tiles in offline mode.
* Fix rendering errors of patterns on high zoom levels.
* Fix a bug where style changes weren't reflected after the source layer of a layer was changed.

## Dependencies
* Update gl-native to v11.7.0 and common to v24.7.0.

# 11.7.0-rc.1 September 16, 2024
## Breaking changes ⚠️
* Experimental interactive features API changes:
    * `FeatureStateValue` was removed in favour of generic `FeatureState`.
    * `MapboxMap.setFeatureState` takes a single `FeatureState` class instead of vararg `FeatureStateValue`.
    * `BaseInteractiveFeature` was removed in favour of `InteractiveFeature`.
    * `InteractiveFeature.state` is now a generic of `FeatureState`.
    * `MapboxMap.getFeatureState` signature changed to be generic and return typed `FeatureState` in the new `FeatureStateCallback`.

## Features ✨ and improvements 🏁
* Introduce experimental `MapboxMap.queryRenderedFeature` allowing to get an `InteractiveFeature` for given geometry, `FeaturesetHolder` and optional filter.
* Introduce experimental `FillExtrusionLayer.fillExtrusionLineWidth` and `FillExtrusionLayerState.fillExtrusionLineWidth` in Compose allowing to switch fill extrusion rendering into wall rendering mode. Use this property to render the feature with the given width over the outlines of the geometry.
* Add missing experimental annotation to `PointAnnotationManager.symbolZOffset` and `PolylineAnnotationManager.lineZOffset`
* Deprecate `PolylineAnnotationManager.lineTrimColor` in favour of `PolylineAnnotationManager.lineTrimColorString`/`PolylineAnnotationManager.lineTrimColorInt`.
* [compose] Introduce experimental `StyleInteractionsState`, `StyleImportsInteractionsState`, `LayerInteractionsState` to handle interactions to the style, style imports and layers.
* [compose] Introduce experimental `StyleImport` composable functions to accept `StyleImportsInteractionsState` as an parameter.
* [compose] Introduce experimental `LayerInteractionsState` as part of `*LayerState` of layers that's driven by a source.
* [compose] Introduce experimental `MapState.getFeatureState`, `MapState.setFeatureState`, `MapState.removeFeatureState` and `MapState.resetFeatureState` APIs.
* [compose] Introduce `StyleState` that groups `StyleImportsConfig`, `StyleInteractionsState`, `Projection`, `AtmosphereState`, `TerrainState`, `LightsState`, `styleTransition`.
* [compose] Introduce `GenericStyle` composable function that uses `StyleState` as parameter and deprecate `GenericStyle` that takes individual light/terrain/projection states.
* [compose] Introduce `MapStyle` composable function that uses `StyleState` as parameter and deprecate `MapStyle` that takes individual light/terrain/projection states.
* [compose] Introduce `StyleImportState` that groups `ImportConfigs` and `StyleImportInteractionsState`.
* [compose] Introduce `StyleImport` composable function that uses `StyleImportState` as parameter and deprecate `StyleImport` that takes `ImportConfigs`.

## Bug fixes 🐞
* Fix a crash when calling `CameraAnimationsPlugin.easeTo()` with empty camera options or `CameraAnimationsPlugin.playAnimatorsSequentially()` / `CameraAnimationsPlugin.playAnimatorsTogether()` with an empty array of animators.
* Fix ongoing animations being canceled when `CameraAnimationsPlugin.flyTo()` with empty camera options is called.
* Fix simultaneous scale and rotation gestures not working when the first registered rotation is a scale one.
* Fix crash on Android tilestore where null data was returned.
* Fix the incorrect behaviour when using `SymbolLayer.symbolZOrder` property.
* Fix retrieval of tilesets for 3d tiles in offline mode.
* Fix rendering errors of patterns on high zoom levels.
* Fix a bug where style changes weren't reflected after the source layer of a layer was changed.

# 11.6.1 September 10, 2024
## Bug fixes 🐞
* Fix for offline retrieval of 3D tiles for `Style.STANDARD`.

## Dependencies
* Update gl-native to v11.6.1 and common to v24.6.1.

# 11.7.0-beta.1 September 03, 2024
## Features ✨ and improvements 🏁
* [compose] Introduce `PointAnnotationState.iconOcclusionOpacity`, `PointAnnotationState.textOcclusionOpacity` to control occlusion opacity for individual point annotation.
* [compose] Remove `MapboxExperimental` from `PolylineAnnotationGroupState.lineOcclusionOpacity`, `PointAnnotationGroupState.iconOcclusionOpacity`, `PointAnnotationGroupState.textOcclusionOpacity`.
* [compose] Expose data-driven properties on `AnnotationGroupState`s. Now it's possible to set data-driven properties globally on `AnnotationGroupState` and specify per-annotation overrides in `AnnotationState`. Setting global property value on `AnnotationGroupState` could introduce performance improvements when the amount of annotations is large.
* [compose] Remove experimental `ModelLayerState.modelFrontCutoff`.
* [compose] Introduce experimental `GeoJsonSourceState.autoMaxZoom` property to resolve rendering artifacts for features that use wide blur (e.g. fill extrusion ground flood light or circle layer).
* [compose] Introduce experimental `ClipLayerState.clipLayerScope` API to remove data from certain style fragments only.
* [compose] Introduce experimental `PointAnnotationState.symbolZOffset` property.
* [compose] Introduce experimental `PointAnnotationGroupState.symbolElevationReference` property.
* [compose] Introduce experimental `SymbolLayerState.symbolElevationReference` and `SymbolLayerState.symbolZOffset` properties.
* [compose] Introduce experimental `SymbolLayerState.symbolZOffsetTransition` API.
* Introduce experimental interactive feature elements and related APIs. Those APIs provide the convenient way to add the click / long click listener to layer / featureset / map itself with `MapboxMap.addInteraction`.
* Expose `lineTrimColor` and `lineTrimFadeRange` on `LineLayer` which allow to set custom color for trimmed line and fade effect for trim. Update navigation example to use those properties.
* Introduce `PointAnnotation.iconOcclusionOpacity`, `PointAnnotation.textOcclusionOpacity` to control occlusion opacity for individual point annotation.
* Remove `MapboxExperimental` from `SymbolLayer.iconOcclusionOpacity`, `SymbolLayer.textOcclusionOpacity` and these properties are now supported on global zoom levels with default value changed to `0`.
* Remove `MapboxExperimental` from `PolylineAnnotationManager.lineOcclusionOpacity`, `PointAnnotationManager.iconOcclusionOpacity`, `PointAnnotationManager.textOcclusionOpacity`.
* Expose data-driven properties on `AnnotationManager`s. Now it's possible to set data-driven properties globally on `AnnotationManager` and specify per-annotation overrides. Setting global property value on `AnnotationManager` could introduce performance improvements when the amount of annotations is large.
* Remove experimental `ModelLayer.modelFrontCutoff`.
* Introduce experimental `GeoJsonSource.autoMaxZoom` property to resolve rendering artifacts for features that use wide blur (e.g. fill extrusion ground flood light or circle layer).
* Introduce static `HttpServiceFactory.setCancellationCallback` API.
* Reduce style parsing time.
* Enable multiple meta tiling schemes for composited sources.
* Expose experimental `ClipLayer.clipLayerScope` API to remove data from certain style fragments only.
* Expose experimental `PointAnnotation.symbolZOffset` property.
* Expose experimental `PointAnnotationManager.symbolElevationReference` property.
* Expose experimental `SymbolLayer.symbolElevationReference` and `SymbolLayer.symbolZOffset` properties.
* Expose experimental `SymbolLayer.symbolZOffsetTransition` API.

## Bug fixes 🐞
* [compose] Fix `UnsatisfiedLinkError` issue when rendering preview.
* Fix annotation issues with click / long click callback ordering and dragging. Now the order of triggering the callbacks / dragging is determined: from top-level rendered annotation to bottom-level one.
* Fix `OnAnnotationInteractionListener` not triggered if any `OnAnnotationClickListener.onAnnotationClick` returns true.
* Fix incorrect layer updates when using `ClipLayer.clipLayerTypes` or `ClipLayerState.clipLayerTypes` in Compose.
* Fix shadow rendering issues when using `FillExtrusionLayer.fillExtrusionCutoffFadeRange` or `FillExtrusionLayerState.fillExtrusionCutoffFadeRange`.
* Significantly reduce the rate at which raster-particle trails cross each other.
* Fix normal offset to improve shadow accuracy.
* Fix data synchronization between renderer and the main threads on map resize.
* Regenerate location indicator mipmap on image change.
* Fix landmark visibility issues near tile borders.
* Fix elevated line depth occlusion issue in 2D mode.
* Fix Dynamic View Annotation position update for annotated geojson feature with `allowZElevate` to be true.
* Fix race condition between `setFeatureState` and `removeFeatureState` / `resetFeatureStates` resulting in feature state not being applied or removed.

## Dependencies
* Update gl-native to v11.7.0-beta.2 and common to v24.7.0-beta.2.

## Known issues
* Experimental `GeoJsonSource.autoMaxZoom` and `GeoJsonSourceState.autoMaxZoom` will return `null` value even after it's set.

# 11.6.0 August 16, 2024
## Breaking changes ⚠️
* [compose] Remove `StyleImage` constructor with `BitmapImage`, use `rememberStyleImage` composable function to build it instead.
* [compose] Move layer properties within `*Layer` composable functions to `*LayerState` classes, use the convenient method `*Layer(sourceState, layerId, init)` with trailing lambda for easier migration.
* [compose] Move `onClick` listener from last parameter to the second last parameter of `Annotation` and `AnnotationGroup` composable functions.
* [compose] Move properties within `*Annotation` and `*AnnotationGroup` composable functions to the new `*AnnotationState` and `*AnnotationGroupState` classes, use the convenient method `*Annotation(point, onClick, init)` and `*AnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda for easier migration.
* [compose] Replace color int and color string property types in `*Annotation` with compose `Color` type.
* [compose] Replace `PointAnnotation.iconImageBitmap` and `PointAnnotation.iconImage` with `PointAnnotationState.iconImage` with `IconImage` type, `IconImage` can be constructed with either a image id `String` or a `Bitmap`, and introduced `rememberIconImage` to build and remember a bitmap from `Painter` or from a drawable resource id.
* [compose] Introduce `StandardStyleConfigurationState` class to hold the configurations of `MapboxStandardStyle`, and move `lightPreset` config from `MapboxStandardStyle` to `StandardStyleConfigurationState`; consider using overload method `MapboxStandardStyle` with trailing init lambda for easier migration.
* Experimental `CustomRasterSourceOptions.Builder` now accepts `CustomRasterSourceClient` containing `CustomRasterSourceTileStatusChangedCallback` instead of `CustomRasterSourceTileStatusChangedCallback` interface directly.

## Features ✨ and improvements 🏁
* **[compose] Promote Compose Extension to stable.**
* [compose] Mark `MapState`, `MapViewportState`, `TerrainState`, light states and source states as `Stable` as they are internally backed by `MutableState`.
* [compose] Add more config options including `showPlaceLabels`, `showRoadLabels`, `showPointOfInterestLabels`, `showTransitLabels` and `font` to `StandardStyleConfigurationState`.
* [compose] Introduce `StyleImage` constructor with `Image` type, and add `rememberStyleImage` composable functions to create and remember `StyleImage`.
* [compose] Add extension function to `*AnnotationOptions` to handle compose `Color`, use it in `*AnnotationGroup` composable functions for convenience.
* [compose] Add style transition parameter in `GenericStyle`, `MapStyle` and `MapboxStandardStyle`.
* [compose] Extend `StandardStyleConfigurationState` with `theme: ThemeValue` and `show3dObjects: BooleanValue` properties.
* [compose] Introduce composable function `MapboxStandardSatelliteStyle` with dedicated `StandardSatelliteStyleConfigurationState`.
* Add new `Style.STANDARD_SATELLITE` style that combines updated satellite imagery and vector layers.
* Introduce new import configuration properties for `Style.STANDARD`: `theme`, `show3dObjects` which could be set with `Style.setStyleImportConfigProperty`.
* Modify `awaitCameraForCoordinates` extension function to use `MapCameraManagerDelegate` as receiver type.
* Modify `queryRenderedFeatures` and `querySourceFeatures` extension functions to use `MapFeatureQueryDelegate` as receiver type.
* Introduce asynchronous overloaded method `ViewAnnotationManager.cameraForAnnotations` better covering some corner cases.
* Make use of asynchronous `MapboxMap.cameraForCoordinates` in Mapbox overlay plugin better covering some corner cases.
* Mark synchronous methods `MapboxMap.cameraForCoordinates` and `ViewAnnotationManager.cameraForAnnotations` with `@MapboxDelicateApi`. Consider using asynchronous overloaded methods instead.
* Deprecate `MapboxMap.getDebug()`/`MapboxMap.setDebug()` in favour of `MapView.debugOptions` taking new enhanced enum `MapViewDebugOptions` as an argument.
* Introduce `MapViewDebugOptions.CAMERA` and `MapViewDebugOptions.PADDING` debug options to track current camera state and visualize camera paddings.
* Expose experimental `ClipLayer` to remove 3D data (fill extrusions, landmarks, instanced trees) and symbols.
* Enable r8 optimisations for all the Mapbox extensions and plugins in consumer proguard file, the optimisation will apply when minify is enabled in app's build settings.
* Enable direct rendering into `CustomRasterSource` tiles.
* Introduce a new `allowZElevate` option in `ViewAnnotationOptions`. When set to true, the annotation will be positioned on the rooftops of buildings, including both fill extrusions and models.
* Support negative values for `CircleLayer.circleBlur` to render inner glow effect.
* Support for model-uris as properties in geojson model layers.
* Allow usage of the tile leveling schemes with maximum zoom exceeding 16.

## Bug fixes 🐞
* [compose] Fix minZoom/maxZoom not working for layers.
* [compose] Fix `java.lang.UnsupportedOperationException` when setting `textWritingMode` and `textVariableAnchor`.
* Fix compass view ignoring `enabled` option when it is set in `updateSettings()`.
* Fix background locations not received when unregistering other providers.
* Improve `linePattern` precision.
* Fix local glyph rasterization to ensure the correct Typeface is used.
* Fix `CustomRasterSource` rendering when camera shows antimeridian or multiple world copies.
* Fix elevated line occlusion issue in 2D mode.
* Fix blinking of layer as you are panning across the antimeridian.
* Fix `modelFrontCutoff` property for meshopt models.
* Align location provider default displacement for Android and Google providers.

## Dependencies
* Update gl-native to v11.6.0 and common to v24.6.0.


# 11.6.0-rc.1 August 02, 2024
## Features ✨ and improvements 🏁
* Support negative values for `CircleLayer.circleBlur` to render inner glow effect.
* Add new `Style.STANDARD_SATELLITE` style that combines updated satellite imagery and vector layers.
* Introduce new import configuration properties for `Style.STANDARD`: `theme`, `show3dObjects` which could be set with `Style.setStyleImportConfigProperty`.
* [compose] Extend `StandardStyleConfigurationState` with `theme: ThemeValue` and `show3dObjects: BooleanValue` properties.
* [compose] Introduce composable function `MapboxStandardSatelliteStyle` with dedicated `StandardSatelliteStyleConfigurationState`.

## Bug fixes 🐞
* Fix elevated line occlusion issue in 2D mode.

## Dependencies
* Update gl-native to v11.6.0-rc.1 and common to v24.6.0-rc.1.

## Known issues
* Experimental `ClipLayer`'s property `clipLayerTypes` is not updated in runtime. The fix is expected to land in stable 11.6.0.


# 11.5.1 July 25, 2024
## Bug fixes 🐞
* Fix local glyph rasterization to ensure the correct Typeface is used.
* Fix map freezing and huge memory consumption issue when using 3D models.
* Fix `CustomRasterSource` rendering when camera shows antimeridian or multiple world copies.
* Fix shadow rendering issues with `FillExtrusionLayer.fillExtrusionCutoffFadeRange`.

## Dependencies
* Update gl-native to v11.5.2.


# 11.6.0-beta.1 July 19, 2024
## Breaking changes ⚠️
* [compose] Remove `StyleImage` constructor with `BitmapImage`, use `rememberStyleImage` composable function to build it instead.
* [compose] Move layer properties within `*Layer` composable functions to `*LayerState` classes, use the convenient method `*Layer(sourceState, layerId, init)` with trailing lambda for easier migration.
* [compose] Move `onClick` listener from last parameter to the second last parameter of `Annotation` and `AnnotationGroup` composable functions.
* [compose] Move properties within `*Annotation` and `*AnnotationGroup` composable functions to the new `*AnnotationState` and `*AnnotationGroupState` classes, use the convenient method `*Annotation(point, onClick, init)` and `*AnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda for easier migration.
* [compose] Replace color int and color string property types in `*Annotation` with compose `Color` type.
* [compose] Replace `PointAnnotation.iconImageBitmap` and `PointAnnotation.iconImage` with `PointAnnotationState.iconImage` with `IconImage` type, `IconImage` can be constructed with either a image id `String` or a `Bitmap`, and introduced `rememberIconImage` to build and remember a bitmap from `Painter` or from a drawable resource id.
* [compose] Introduce `StandardStyleConfigurationState` class to hold the configurations of `MapboxStandardStyle`, and move `lightPreset` config from `MapboxStandardStyle` to `StandardStyleConfigurationState`; consider using overload method `MapboxStandardStyle` with trailing init lambda for easier migration.
* Experimental `CustomRasterSourceOptions.Builder` now accepts `CustomRasterSourceClient` containing `CustomRasterSourceTileStatusChangedCallback` instead of `CustomRasterSourceTileStatusChangedCallback` interface directly.

## Features ✨ and improvements 🏁
* **[compose] Promote Compose Extension to stable.**
* [compose] Mark `MapState`, `MapViewportState`, `TerrainState`, light states and source states as `Stable` as they are internally backed by `MutableState`.
* [compose] Add more config options including `showPlaceLabels`, `showRoadLabels`, `showPointOfInterestLabels`, `showTransitLabels` and `font` to `StandardStyleConfigurationState`.
* [compose] Introduce `StyleImage` constructor with `Image` type, and add `rememberStyleImage` composable functions to create and remember `StyleImage`.
* [compose] Add extension function to `*AnnotationOptions` to handle compose `Color`, use it in `*AnnotationGroup` composable functions for convenience.
* [compose] Add style transition parameter in `GenericStyle`, `MapStyle` and `MapboxStandardStyle`.
* Modify `awaitCameraForCoordinates` extension function to use `MapCameraManagerDelegate` as receiver type.
* Modify `queryRenderedFeatures` and `querySourceFeatures` extension functions to use `MapFeatureQueryDelegate` as receiver type.
* Introduce asynchronous overloaded method `ViewAnnotationManager.cameraForAnnotations` better covering some corner cases.
* Make use of asynchronous `MapboxMap.cameraForCoordinates` in Mapbox overlay plugin better covering some corner cases.
* Mark synchronous methods `MapboxMap.cameraForCoordinates` and `ViewAnnotationManager.cameraForAnnotations` with `@MapboxDelicateApi`. Consider using asynchronous overloaded methods instead.
* Deprecate `MapboxMap.getDebug()`/`MapboxMap.setDebug()` in favour of `MapView.debugOptions` taking new enhanced enum `MapViewDebugOptions` as an argument.
* Introduce `MapViewDebugOptions.CAMERA` and `MapViewDebugOptions.PADDING` debug options to track current camera state and visualize camera paddings.
* Expose experimental `ClipLayer` to remove 3D data (fill extrusions, landmarks, instanced trees) and symbols.
* Enable r8 optimisations for all the Mapbox extensions and plugins in consumer proguard file, the optimisation will apply when minify is enabled in app's build settings.
* Align default displacement for Android and Google location providers.
* Enable direct rendering into `CustomRasterSource` tiles.
* Introduce a new `allowZElevate` option in `ViewAnnotationOptions`. When set to true, the annotation will be positioned on the rooftops of buildings, including both fill extrusions and models.

## Bug fixes 🐞
* [compose] Fix minZoom/maxZoom not working for layers.
* [compose] Fix `java.lang.UnsupportedOperationException` when setting `textWritingMode` and `textVariableAnchor`.
* Fix compass view ignoring `enabled` option when it is set in `updateSettings()`.
* Fix background locations not received when unregistering other providers.
* Improve `linePattern` precision.
* Fix local glyph rasterization to ensure the correct Typeface is used.
* Fix `CustomRasterSource` rendering when camera shows antimeridian or multiple world copies.

## Known issues
* `ClipLayer.clipLayerTypes` could not be updated with a layer setter. Layer has to be re-added to update it. This is expected to be fixed in v11.6.0-rc.1.

## Dependencies
* Update gl-native to v11.6.0-beta.1 and common to v24.6.0-beta.1.


# 11.4.2 July 17, 2024
## Bug fixes 🐞
* Fix local glyph rasterization to ensure the correct Typeface is used.

## Dependencies
* Update gl-native to v11.4.1.


# 11.5.0 July 05, 2024
## Breaking changes ⚠️
* [compose] Make `MapboxMap.onMapClickListener` and `MapboxMap.onMapLongClickListener` nullable and default to `null`.
* [compose] Rename `ImportConfig` to `ImportConfigs`.
* [compose] Move `MapboxMap.mapEvents` to events flows in `MapState`.
* [compose] Move `MapboxMap.gesturesSettings` to `MapState`.
* [compose] Move `Projection` to `generated` package and rename `default` values to `DEFAULT`.
* [compose] Rename `LightPreset` to `LightPresetValue`.
* [compose] Rename `TerrainState.disabled` to `TerrainState.DISABLED`.
* [compose] Replace terrain property `Exaggeration` with `DoubleValue`.
* [compose] Replace concrete `AtmosphereState` properties (e.g. `HighColor`, `HorizonBlend`, `SpaceColor`, etc) with generic ones: `ColorValue`, `DoubleValue`, `DoubleRangeValue`.
* [compose] Replace concrete Layer properties(e.g. `CircleColor`, `CircleOpacity`, `IconImage` etc) with generic ones: `ColorValue`, `DoubleValue`, `ImageValue` etc.
* [compose] Replace concrete `GeoJsonSourceState`, `ImageSourceState`, `RasterArraySourceState`, `RasterDemSourceState`, `RasterSourceState`, `SourceProperties`, `VectorSourceState` properties with generic ones (e.g. `BooleanValue`, `StringValue`, `LongValue`...).
* [compose] Move `GeoJSONData` outside of `generated` package.
* Remove experimental `CustomRasterSource.invalidateRegion` and `CustomRasterSource.invalidateTile` methods and change signature of `CustomRasterSource.setTileData`.
* Remove experimental `CustomRasterSource.tileCacheBudget` getter and setter. If needed, caching should be implemented on user's side.
* Remove experimental `MapboxMap` and `Style` methods: `invalidateStyleCustomRasterSourceTile`, `invalidateStyleCustomRasterSourceRegion`; change signature of `setStyleCustomRasterSourceTileData` method in `MapboxMap` and `Style`.

## Features ✨ and improvements 🏁
* [compose] Enable r8 optimisations of compose extension in consumer proguard file, the optimisation will apply when minify is enabled in app's build settings.
* [compose] Introduce `StyleImport` composable API to be used in the `GenericStyle`, `MapStyle` and `MapboxStandardStyle`.
* [compose] Introduce `MapState` that can be hoisted to interact with map states, such as query rendered features, subscribe to map events and configure gestures settings.
* [compose] Expose `TerrainState` and `AtmosphereState` properties as `MutableState`.
* [compose] Introduce `AmbientLightState`, `DirectionalLightState`, `FlatLightState` as separate states; `LightsState` can be constructed by combination of `DirectionalLightState` and `AmbientLightState` or with `FlatLightState` to be set to the style.
* [compose] Avoid recreation of objects during recomposition of `GenericStyle`.
* Expose `LineJoin.NONE` which in conjunction with e.g. `linePattern` image allows to display repeated series of images along a line (e.g. dotted route line).
* Expose new function `DefaultLocationProvider.locationAnimatorOptions` to allow changing the value animator properties for puck position animation.
* Deprecate `MapboxMap.cameraForCoordinates` suspending extension function in favour of suspend `MapboxMap.awaitCameraForCoordinates`.
* Add min/max/default values to the docs for the generated properties.
* Add asynchronous `TileStore.create().clearAmbientCache()` API that can be used for clearing all ambient cache data.
* Expose experimental `lineZOffset` and `lineOcclusionOpacity` for `LineLayer`.
* Expose experimental `modelFrontCutoff` for `ModelLayer`.
* Expose experimental `iconOcclusionOpacity` and `textOcclusionOpacity` for `SymbolLayer` and `PointAnnotationManager`.
* Expose experimental `lineOcclusionOpacity` for `PolylineAnnotationManager`.
* Expose experimental `lineZOffset` for `PolylineAnnotation` and `PolylineAnnotationOptions`.
* Expose `clusterMinPoints` property for `GeoJSONSource` and for annotation's `ClusterOptions`.
* Remove explicit main thread locking when using `CircleAnnotationManager`, `PointAnnotationManager`, `PolygonAnnotationManager`, `PolylineAnnotationManager` and dragging the map that could lead to an ANR.
* Use dedicated thread for the tile store to increase performance.
* [compose] Expose `TerrainState` and `AtmosphereState` properties as `MutableState`.

## Bug fixes 🐞
* [compose] Fix the layer and annotation ordering by moving the annotations/layers according to the relative position in the node tree.
* [compose] Fix `No enum constant com.mapbox.maps.GeoJSONSourceData` crash when restoring app from background.
* Fix transitioning to `OverviewViewportState` in corner cases when the map is not yet ready for rendering(e.g. immediately after `MapView` is created).
* Set default minimum displacement between location updates to 0.1 meters in `DefaultLocationProvider`. Now this value is the same regardless of application using Google Play Services location library or not.
* Fix `PointAnnotationManager` and `CircleAnnotationManager` cluster layer id collision issue, so that multiple clusters can work at the same time.
* Fix `RasterParticleLayer.rasterParticleCount` and `RasterParticleLayer.defaultRasterParticleCount` returning `null`.
* Fix an issue allowing view annotation to be added even if its associated layer does not exist. Now, view annotation will function correctly once the layer is added.
* Fix feature queries for symbols above the horizon.
* Fix the rotated icon position during the globe transition.
* Fix Dynamic View Annotation (DVA) placement to place DVA in the center of the line geometry point, and try to avoid placing DVA near the lines' intersection point.
* Reduce the max raster-particle animation speed. It prevents particles from moving too fast, causing a visible clipping artifact at tile boundaries.
* `Snapshotter` methods throw `SnapshotterDestroyedException` if `destroy` was already called.
* Fix precision issues in `ColorUtils` methods.
* Fix NPE when parsing `rgb(...)` strings with `ColorUtils` methods.
* Fix `ScaleBar.useContinuousRendering` not being in sync with `ScaleBar.settings.useContinuousRendering`.
* Fix accuracy ring related location settings updates not being rendered immediately.
* Fix a crash for Draco compressed 3D models whose geometry share indices.
* Fix tile rendering errors when the composited source tile components are overscaled.
* Fix transparent areas in overlapped polygons of MultiPolygon feature.
* Fix crash on multiple style pack loading operations.

## Dependencies
* Update gl-native to v11.5.1 and common to v24.5.0.


# 11.5.0-rc.1 June 20, 2024
## Breaking changes ⚠️
* [compose] Make `MapboxMap.onMapClickListener` and `MapboxMap.onMapLongClickListener` nullable and default to `null`.
* [compose] Rename `ImportConfig` to `ImportConfigs`.
* [compose] Move `MapboxMap.mapEvents` to events flows in `MapState`.
* [compose] Move `MapboxMap.gesturesSettings` to `MapState`.
* Remove experimental `CustomRasterSource.invalidateRegion` and `CustomRasterSource.invalidateTile` methods and change signature of `CustomRasterSource.setTileData`.
* Remove experimental `CustomRasterSource.tileCacheBudget` getter and setter. If needed, caching should be implemented on user's side.
* Remove experimental `MapboxMap` and `Style` methods: `invalidateStyleCustomRasterSourceTile`, `invalidateStyleCustomRasterSourceRegion`; change signature of `setStyleCustomRasterSourceTileData` method in `MapboxMap` and `Style`.

## Features ✨ and improvements 🏁
* [compose] Introduce `StyleImport` composable API to be used in the `GenericStyle`, `MapStyle` and `MapboxStandardStyle`.
* [compose] Introduce `MapState` that can be hoisted to interact with map states, such as query rendered features, subscribe to map events and configure gestures settings.
* Deprecate `MapboxMap.cameraForCoordinates` suspending extension function in favour of suspend `MapboxMap.awaitCameraForCoordinates`.
* Add min/max/default values to the docs for the generated properties.
* Add asynchronous `TileStore.create().clearAmbientCache()` API that can be used for clearing all ambient cache data.
* Expose experimental `lineZOffset` and `lineOcclusionOpacity` for `LineLayer`.
* Expose experimental `modelFrontCutoff` for `ModelLayer`.
* Expose experimental `iconOcclusionOpacity` and `textOcclusionOpacity` for `SymbolLayer` and `PointAnnotationManager`.
* Expose experimental `lineOcclusionOpacity` for `PolylineAnnotationManager`.
* Expose experimental `lineZOffset` for `PolylineAnnotation` and `PolylineAnnotationOptions`.

## Bug fixes 🐞
* Fix `RasterParticleLayer.rasterParticleCount` and `RasterParticleLayer.defaultRasterParticleCount` returning `null`.
* Fix the rotated icon position during the globe transition.
* Fix Dynamic View Annotation (DVA) placement to place DVA in the center of the line geometry point, and try to avoid placing DVA near the lines' intersection point.
* Reduce the max raster-particle animation speed. It prevents particles from moving too fast, causing a visible clipping artifact at tile boundaries.

## Dependencies
* Update gl-native to v11.5.0-rc.1 and common to v24.5.0-rc.1.


# 11.5.0-beta.1 June 11, 2024
## Breaking changes ⚠️
* [compose] Move `Projection` to `generated` package and rename `default` values to `DEFAULT`.
* [compose] Rename `LightPreset` to `LightPresetValue`.
* [compose] Rename `TerrainState.disabled` to `TerrainState.DISABLED`.
* [compose] Replace terrain property `Exaggeration` with `DoubleValue`.
* [compose] Replace concrete `AtmosphereState` properties (e.g. `HighColor`, `HorizonBlend`, `SpaceColor`, etc) with generic ones: `ColorValue`, `DoubleValue`, `DoubleRangeValue`.
* [compose] Replace concrete Layer properties(e.g. `CircleColor`, `CircleOpacity`, `IconImage` etc) with generic ones: `ColorValue`, `DoubleValue`, `ImageValue` etc.
* [compose] Replace concrete `GeoJsonSourceState`, `ImageSourceState`, `RasterArraySourceState`, `RasterDemSourceState`, `RasterSourceState`, `SourceProperties`, `VectorSourceState` properties with generic ones (e.g. `BooleanValue`, `StringValue`, `LongValue`...).
* [compose] Move `GeoJSONData` outside of `generated` package.

## Features ✨ and improvements 🏁
* Expose `clusterMinPoints` property for `GeoJSONSource` and for annotation's `ClusterOptions`.
* Remove explicit main thread locking when using `CircleAnnotationManager`, `PointAnnotationManager`, `PolygonAnnotationManager`, `PolylineAnnotationManager` and dragging the map that could lead to an ANR.
* Use dedicated thread for the tile store to increase performance.
* [compose] Expose `TerrainState` and `AtmosphereState` properties as `MutableState`.
* [compose] Introduce `AmbientLightState`, `DirectionalLightState`, `FlatLightState` as separate states; `LightsState` can be constructed by combination of `DirectionalLightState` and `AmbientLightState` or with `FlatLightState` to be set to the style.
* [compose] Avoid recreation of objects during recomposition of `GenericStyle`.
* Expose `TerrainState` and `AtmosphereState` properties as `MutableState`.

## Bug fixes 🐞
* [compose] Fix `No enum constant com.mapbox.maps.GeoJSONSourceData` crash when restoring app from background.
* `Snapshotter` methods throw `SnapshotterDestroyedException` if `destroy` was already called.
* Fix precision issues in `ColorUtils` methods.
* Fix NPE when parsing `rgb(...)` strings with `ColorUtils` methods.
* Fix `ScaleBar.useContinuousRendering` not being in sync with `ScaleBar.settings.useContinuousRendering`.
* Fix accuracy ring related location settings updates not being rendered immediately.
* Fix a crash for Draco compressed 3D models whose geometry share indices.
* Fix tile rendering errors when the composited source tile components are overscaled.
* Fix transparent areas in overlapped polygons of MultiPolygon feature.
* Fix crash on multiple style pack loading operations.

## Dependencies
* Update gl-native to v11.5.0-beta.1 and common to v24.5.0-beta.4.


# 11.4.1 June 03, 2024
## Bug fixes 🐞
* Fix an issue that `getLight` API always returns null.


# 11.4.0 May 22, 2024
## Breaking changes ⚠️
* [compose] Remove `locationComponentSettings` from `MapboxMap` composable function, `MapEffect` with location component API should be used instead. More compose-friendly location component API will be introduced in future releases.
* [compose] Remove `TileCacheBudget(com.mapbox.maps.TileCacheBudget)` constructor and introduce `TileCacheBudget(TileCacheBudgetInMegabytes)` and `TileCacheBudget(TileCacheBudgetInTiles)` constructor instead.
* [compose] Remove `layoutParams` from `ViewAnnotation` composable function, the internal `ComposeView` wrapping the `ViewAnnotation.content` will always use `WRAP_CONTENT`; In case of tests where the assertion happens before the measure, user can force the content size using `ViewAnnotationOptions.width/height` APIs.
* [compose] Constructor in `PromoteId` data class from compose now takes `PropertyName` and optional `SourceId` instead of itself.
* [compose] Use new `SlotsContent` instead of generic `Map` to handle the style content for slots. Introduced `slotsContent` builder function.
* [compose] Use new `LayerPositionedContent` instead of generic `Map` to handle the layer positioned style content. Introduced `layerPositionedContent` builder function.
* [compose] Use new `StyleImportsConfig` instead of generic `Map` to handle the style import configurations. Introduced `styleImportsConfig` builder function.
* [compose] Move `MapboxStandardStyle` to a different package and introduce `LightPreset` with available presets as constants.
* [compose] `MapViewportState` properties `cameraState`, `mapViewportStatusChangedReason` and `mapViewportStatus` are null when the state is not attached to a map.
* [compose] `MapViewportState` constructor parameter has been renamed to `initialCameraState`.

## Features ✨ and improvements 🏁
* [compose] Add `AtmosphereState` parameter to `GenericStyle` composable function.
* [compose] Introduce `Projection` and `AtmosphereState` API on `MapStyle` and `MapboxStandardStyle`.
* [compose] Add `StyleImage` to construct following image layer properties: `IconImage`, `FillPattern`, `LinePattern`, `BearingImage`, `ShadowImage`, `TopImage`.
* [compose] Add `ModelId` constructor to add model id and uri.
* [compose] Add `TerrainState` parameter to `GenericStyle`, `MapStyle` and `MapboxStandardStyle` composable functions.
* Introduce `addStyleImportFromJSON`, `addStyleImportFromURI`, `updateStyleImportWithJSON`, `updateStyleImportWithURI`, `moveStyleImport` APIs to `MapboxMap` and `Style`.
* Handle updating geo-json data exceptions and propagate them to `MapboxMap.subscribeMapLoadingError(mapLoadingErrorCallback)`.
* Introduce `SlotLayer` in Style DSL.
* Add statistics for graphics pipeline program creation.
* Enable `raster-elevation` for tiled raster sources.
* Improve tile processing performance by filtering out tiny polygon holes.
* Reduce number of evaluations of step expression in `line-gradient` properties.
* Add support for `line-trim-offset` with `line-pattern`.
* Enable two dimensional data handling in Mapbox Raster tiles.
* Trim zoom ranges for the style at tileset descriptor resolving.
* Extend `SymbolLayer.iconColorSaturation` range from [0, 1] to [-1, 1] and change default value to 0.
* Reduce time spent on model layer re-evaluation during light change.
* Expose experimental `Style.styleSlots` allowing to get the ordered list of slots.
* Deprecate `MapboxMap.cameraForCoordinateBounds`, `MapboxMap.cameraForGeometry` and some synchronous overloaded `MapboxMap.cameraForCoordinates` in favour of single synchronous, asynchronous and suspend `MapboxMap.cameraForCoordinates`. Synchronous `MapboxMap.cameraForCoordinates` returns empty camera (could be checked with `CameraOptions.isEmpty`) if the map's size is not yet calculated.
* Add feature metrics collection. Mapbox Maps SDK collects anonymous data about which of its features are used. Mapbox uses this data to understand how our software is being used and prioritize plans to improve it. These metrics tell us whether a feature has been used ("flyTo was called"), but not how ("flyTo was called with this position"). No user-level metrics or identifiers are collected as part of this initiative.
* Avoid locking main thread when it is not needed on map destroy.
* Add experimental `MapView.setSnapshotLegacyMode` function to help avoiding `MapView.snapshot` native crash on some Samsung devices running Android 14.
* Add experimental `RasterParticleLayer` in Style DSL and Compose.
* Add `mapView.location.slot` API to assign a slot for the location indicator.

## Bug fixes 🐞
* [compose] Fix an issue with `rememberGeoJsonSourceState`, where the `Value` and `GeoJsonData` can not be serialised.
* [compose] Remember default `ComposeMapInitOptions` and `GesturesSettings` so that we don't reconstruct these classes when `MapboxMap` recomposes.
* [compose] Filter relevant events for `ViewAnnotation.onUpdatedListener` and skip events from other view annotations.
* [compose] Do not consume tap event for `Compass`, so that user set `clickable` can be processed.
* [compose] Fix slots and layerposition content not being cleaned up during recomposition.
* [compose] Propagate onRemoved and onClear to children nodes of MapStyleNode to do proper clean up.
* [compose] Fix lost style import config during style switch by waiting for style load event.
* [compose] Make the initial compass visibility to be false, so the compass wouldn't show and hide initially if the user is facing north.
* [compose] Fix `java.io.NotSerializableException: com.mapbox.bindgen.Value` for SourceState.
* [compose] Queue viewport operations when the `MapViewportState` is no yet attached to the map, to avoid losing events.
* Fix `Snapshotter.cameraForCoordinates` arguments `padding`, `bearing` and `pitch` to be nullable.
* Fix config with format expression that contains text property overrides.
* Make non-vector tile parsing cancellable.
* Move cutoff opacity calculation to CPU side.
* Fix icon/pattern missing issue if the missing image is only added after map gets rendered.
* Introduce a dedicated thread for 3d landmarks parsing.
* Fix crash on start when no free disk space left.
* Fix TilePrefetch for GeoJSON sources.
* Fix snapshotter latency when 3d tiles involved.
* Fix renderer destruction being blocked by 3d models parsing completion.
* Fix memory leak when camera animations are skipped.
* Fix Mapbox attribution and telemetry links not opening in a browser.
* Fix incorrect size of the tile memory budget for vector tiles when the budget is set in megabytes.
* Fix `LogoView.logoEnabled` not being in sync with `MapView.logo.enabled` state.
* Fix raster-particle not being visible on some Android devices.
* Fixed invalid circle order while using `circle-sort-key`.
* Fixed duplicate circles in static viewport mode.
* Fixed a crash during style change.

## Dependencies
* Update gl-native to v11.4.0 and common to v24.4.0.


# 11.4.0-rc.2 May 15, 2024
## Breaking changes ⚠️
* [compose] `MapViewportState` properties `cameraState`, `mapViewportStatusChangedReason` and `mapViewportStatus` are null when the state is not attached to a map.
* [compose] `MapViewportState` constructor parameter has been renamed to `initialCameraState`.

## Bug fixes 🐞
* [compose] Queue viewport operations when the `MapViewportState` is no yet attached to the map, to avoid losing events.
* Fix `LogoView.logoEnabled` not being in sync with `MapView.logo.enabled` state.
* Fix raster-particle not being visible on some Android devices.

## Dependencies
* Update gl-native to v11.4.0-rc.2 and common to v24.4.0-rc.2.


# 11.4.0-rc.1 May 08, 2024
## Features ✨ and improvements 🏁
* [compose] Add `StyleImage` to construct following image layer properties: `IconImage`, `FillPattern`, `LinePattern`, `BearingImage`, `ShadowImage`, `TopImage`.
* [compose] Add `ModelId` constructor to add model id and uri.
* [compose] Add `TerrainState` parameter to `GenericStyle`, `MapStyle` and `MapboxStandardStyle` composable functions.
* Add experimental `RasterParticleLayer` in Style DSL and Compose.
* Add `mapView.location.slot` API to assign a slot for the location indicator.

## Bug fixes 🐞
* Fix memory leak when camera animations are skipped.
* Fix Mapbox attribution and telemetry links not opening in a browser.
* Fix incorrect size of the tile memory budget for vector tiles when the budget is set in megabytes.
* Fix known issue from 11.4.0-beta.1 where setting a RasterLayer’s rasterColor property with an expression will block the layer from rendering.

## Dependencies
* Update gl-native to v11.4.0-rc.1 and common to v24.4.0-rc.1.


# 11.4.0-beta.3 May 06, 2024
## Features ✨ and improvements 🏁
* Add experimental `MapView.setSnapshotLegacyMode` function to help avoiding `MapView.snapshot` native crash on some Samsung devices running Android 14.

## Bug fixes 🐞
* [compose] Make the initial compass visibility to be false, so the compass wouldn't show and hide initially if the user is facing north.
* [compose] Fix `java.io.NotSerializableException: com.mapbox.bindgen.Value` for SourceState.

## Dependencies
* Update common to v24.4.0-beta.3.


# 11.4.0-beta.2 April 30, 2024
## Features ✨ and improvements 🏁
* Avoid locking main thread when it is not needed on map destroy.

## Bug fixes 🐞
* Fix TilePrefetch for GeoJSON sources.
* Fix snapshotter latency when 3d tiles involved.
* Fix renderer destruction being blocked by 3d models parsing completion.

## Dependencies
* Update gl-native to v11.4.0-beta.2 and common to v24.4.0-beta.2.


# 11.4.0-beta.1 April 29, 2024
## Breaking changes ⚠️
* [compose] Remove `locationComponentSettings` from `MapboxMap` composable function, `MapEffect` with location component API should be used instead. More compose-friendly location component API will be introduced in future releases.
* [compose] Remove `TileCacheBudget(com.mapbox.maps.TileCacheBudget)` constructor and introduce `TileCacheBudget(TileCacheBudgetInMegabytes)` and `TileCacheBudget(TileCacheBudgetInTiles)` constructor instead.
* [compose] Remove `layoutParams` from `ViewAnnotation` composable function, the internal `ComposeView` wrapping the `ViewAnnotation.content` will always use `WRAP_CONTENT`; In case of tests where the assertion happens before the measure, user can force the content size using `ViewAnnotationOptions.width/height` APIs.
* [compose] Constructor in `PromoteId` data class from compose now takes `PropertyName` and optional `SourceId` instead of itself.
* [compose] Use new `SlotsContent` instead of generic `Map` to handle the style content for slots. Introduced `slotsContent` builder function.
* [compose] Use new `LayerPositionedContent` instead of generic `Map` to handle the layer positioned style content. Introduced `layerPositionedContent` builder function.
* [compose] Use new `StyleImportsConfig` instead of generic `Map` to handle the style import configurations. Introduced `styleImportsConfig` builder function.
* [compose] Move `MapboxStandardStyle` to a different package and introduce `LightPreset` with available presets as constants.

## Features ✨ and improvements 🏁
* [compose] Add `AtmosphereState` parameter to `GenericStyle` composable function.
* [compose] Introduce `Projection` and `AtmosphereState` API on `MapStyle` and `MapboxStandardStyle`.
* Introduce `addStyleImportFromJSON`, `addStyleImportFromURI`, `updateStyleImportWithJSON`, `updateStyleImportWithURI`, `moveStyleImport` APIs to `MapboxMap` and `Style`.
* Handle updating geo-json data exceptions and propagate them to `MapboxMap.subscribeMapLoadingError(mapLoadingErrorCallback)`.
* Introduce `SlotLayer` in Style DSL.
* Add statistics for graphics pipeline program creation.
* Enable `raster-elevation` for tiled raster sources.
* Improve tile processing performance by filtering out tiny polygon holes.
* Reduce number of evaluations of step expression in `line-gradient` properties.
* Add support for `line-trim-offset` with `line-pattern`.
* Enable two dimensional data handling in Mapbox Raster tiles.
* Trim zoom ranges for the style at tileset descriptor resolving.
* Extend `SymbolLayer.iconColorSaturation` range from [0, 1] to [-1, 1] and change default value to 0.
* Reduce time spent on model layer re-evaluation during light change.
* Expose experimental `Style.styleSlots` allowing to get the ordered list of slots.
* Deprecate `MapboxMap.cameraForCoordinateBounds`, `MapboxMap.cameraForGeometry` and some synchronous overloaded `MapboxMap.cameraForCoordinates` in favour of single synchronous, asynchronous and suspend `MapboxMap.cameraForCoordinates`. Synchronous `MapboxMap.cameraForCoordinates` returns empty camera (could be checked with `CameraOptions.isEmpty`) if the map's size is not yet calculated.

## Bug fixes 🐞
* [compose] Fix an issue with `rememberGeoJsonSourceState`, where the `Value` and `GeoJsonData` can not be serialised.
* [compose] Remember default `ComposeMapInitOptions` and `GesturesSettings` so that we don't reconstruct these classes when `MapboxMap` recomposes.
* [compose] Filter relevant events for `ViewAnnotation.onUpdatedListener` and skip events from other view annotations.
* [compose] Do not consume tap event for `Compass`, so that user set `clickable` can be processed.
* [compose] Fix slots and layerposition content not being cleaned up during recomposition.
* [compose] Propagate onRemoved and onClear to children nodes of MapStyleNode to do proper clean up.
* [compose] Fix lost style import config during style switch by waiting for style load event.
* Fix `Snapshotter.cameraForCoordinates` arguments `padding`, `bearing` and `pitch` to be nullable.
* Fix config with format expression that contains text property overrides.
* Make non-vector tile parsing cancellable.
* Move cutoff opacity calculation to CPU side.
* Fix icon/pattern missing issue if the missing image is only added after map gets rendered.
* Introduce a dedicated thread for 3d landmarks parsing.
* Fix crash on start when no free disk space left.

## Dependencies
* Update gl-native to v11.4.0-beta.1 and common to v24.4.0-beta.1.

## Known issues
* In v11.4.0-beta.1, setting a RasterLayer’s rasterColor property with an expression will block the layer from rendering. This issue will be resolved in v11.4.0-rc.1.
* In v11.4.0-beta.1, the map destroy might block main thread for short amount of time and cause UI to freeze. This issue will be resolved in v11.4.0-beta.2.


# 11.3.1 April 26, 2024
## Features ✨ and improvements 🏁
* Reduce time spent on model layer re-evaluation during light change.

## Bug fixes 🐞
* Make non-vector tile parsing cancellable.
* Introduce a dedicated thread for 3d landmarks parsing.
* Fix TilePrefetch for GeoJSON sources.

## Dependencies
* Update gl-native to v11.3.2.


# 11.3.0 April 11, 2024
## Breaking changes ⚠️
* [compose] Introduce experimental `ComposeMapInitOptions` and remove `mapInitOptionsFactory`.
* [compose] Replace experimental `MapboxMap.compassSettings`, `MapboxMap.scaleBarSettings`, `MapboxMap.logoSettings`, `MapboxMap.attributionSettings` with composable functions in dedicated scopes: `MapCompassScope.Compass()`, `MapScaleBarScope.ScaleBar()`, `MapLogoScope.Logo()`, `MapAttributionScope.Attribution()`.

## Features ✨ and improvements 🏁
* [compose] Add layerPosition support in `GenericStyle` composable function.
* [compose] Add layer transition properties.
* [compose] Add `contentPadding` to map ornament composable functions(e.g. `Compass`, `Logo`, `Attribution`, `ScaleBar`).
* [compose] Introduce experimental `MapStyle`, `MapboxStandardStyle`, `GenericStyle` composable functions to work with the map style.
* [compose] Introduce experimental layer composable functions to insert layers to the map.
* [compose] Introduce experimental source states to work with layer composable functions.
* [compose] Add map projection support in `GenericStyle` composable function.
* Expose `MapInitOptions.mapName` (`mapbox_mapName` in XML) property allowing to set the custom name which will be appended to map render related logs.
* Add Attribution and Telemetry pop-up dialogs and compass view content description translations for Arabic, Bulgarian, Catalan, Chinese Simplified, Chinese Traditional, Czech, Danish, Dutch, French, Galician, German, Hebrew, Italian, Japanese, Korean, Lithuanian, Norwegian, Polish, Belarusian, Russian, Spanish, Swedish, Ukranian and Vietnamese.
* Perform faster landmark parsing by switching tinygltf in favor of cgltf.
* Use mipmap with pattern images.
* Add `SdkInformationQuery` to expose sdk version information.
* Enable `TileStore` delta updates by default for Maps domain.
* Add `TileStore.estimateTileRegion` API for estimating Tile Region downloads and storage size.

## Bug fixes 🐞
* [compose] Fix a bug introduced in `11.3.0-beta.1` where AnnotationGroup items updates were skipped.
* [compose] Fix `ViewAnnotation` not cleared when it leaves composition.
* [compose] Fix the `IndexOutOfBoundsException` because of `RootNode` of `MapboxMap` node tree being shared across multiple maps.
* Resolve the data race by ensuring that when terrain is enabled, the transform state is updated with the correct elevation instance.
* Fix offline composited tiles fetching when the request tile zoom level is above maximum zoom for one of the composed tile packs but below maximum zoom level for another one.
* Fix override of line-gradient textures when fill-extrusion effects are used on terrain.
* Return `ViewAnnotationOptions.Builder` when calling `ViewAnnotationOptions.Builder.annotationAnchor` extension function.
* Immediately add annotations and location component to the map instead of waiting for style load events.
* Fix a bug where specifying a large negative value for padding in `MapboxMap.camera*()` methods resulted in the returned zoom value being NaN.
* Fix location indicator models rendering issue with globe projection.
* Offline: composite higher level tiles from their parents, when a non-standard [tile pack zoom ranges scheme](https://docs.mapbox.com/android/maps/guides/offline/#tile-count-granularity) is used.
* Use bigger http buffers to avoid reference table overflow.
* Fix attribution links not opening in some scenarios.
* Fix attribution and telemetry dialogs not respecting current theme.
* Fix map being pixelated on some devices when `ContextMode.SHARED` is used (e.g. in AndroidAuto extension).
* Fix map being black on some zoom levels when `ContextMode.SHARED` is used (e.g. in AndroidAuto extension).
* Fix incorrect widget position and scale when resizing the drawing surface.
* Fix layer paint property update with feature state changes, especially if the paint property value data-driven by brightness or zoom.
* Fix snapshotter race conditions to ensure new request could effectively trigger map rendering.
* Fix raster array band updates glitches during the camera zoom animation.
* Reload vector source tiles when language or worldview setting is changed.
* Apply config expression to atmosphere properties.
* Fix map freezing when using `queryRenderedFeatures` with 3d models in mercator projection.
* Reduce time spent on line gradient updates on the render thread.
* Fix network reachability status getting stuck with ReachableViaWWAN status if HTTP requests completed at the same time as network reported being disconnected.
* Fix wrong network reachability statuses.
* Fix double `LocationServiceObserver.onAvailabilityChanged` callback trigger.
* Avoid bringing Kotlin 1.8 as transitive dependency, Maps SDK should use Kotlin 1.7.20.

## Dependencies
* Update gl-native to v11.3.0 and common to v24.3.1.
* Update Mapbox Base Android library to [v0.11.0](https://github.com/mapbox/mapbox-base-android/releases/tag/v0.11.0).

# 11.3.0-rc.1 March 28, 2024
##  Known Issues ⚠️
* The tiles fetching from the offline database is malfunctioning for the composited sources. Setting `com.mapbox.maps.experimental.offline_vt_compositing` runtime flag to `false` resolves this issue:
```Kotlin
val settings = SettingsServiceFactory.getInstance(SettingsServiceStorageType.NON_PERSISTENT)
settings.set("com.mapbox.maps.experimental.offline_vt_compositing", Value.valueOf(false))
```

## Features ✨ and improvements 🏁
* [compose] Add layerPosition support in `GenericStyle` composable function.
* Expose `MapInitOptions.mapName` (`mapbox_mapName` in XML) property allowing to set the custom name which will be appended to map render related logs.
* [compose] Add layer transition properties.
* [compose] Add `contentPadding` to map ornament composable functions(e.g. `Compass`, `Logo`, `Attribution`, `ScaleBar`).

## Bug fixes 🐞
* Return `ViewAnnotationOptions.Builder` when calling `ViewAnnotationOptions.Builder.annotationAnchor` extension function.
* [compose] Fix the `IndexOutOfBoundsException` because of `RootNode` of `MapboxMap` node tree being shared across multiple maps.
* Immediately add annotations and location component to the map instead of waiting for style load events.
* Fix a bug where specifying a large negative value for padding in `MapboxMap.camera*()` methods resulted in the returned zoom value being NaN.
* Fix location indicator models rendering issue with globe projection.
* Offline: composite higher level tiles from their parents, when a non-standard [tile pack zoom ranges scheme](https://docs.mapbox.com/android/maps/guides/offline/#tile-count-granularity) is used.
* Use bigger http buffers to avoid reference table overflow.
* Fix a crash in `MapView.snapshot` happening on specific devices.

## Dependencies
* Update gl-native to v11.3.0-rc.1 and common to v24.3.0-rc.1.


# 11.2.2 March 27, 2024
## Features ✨ and improvements 🏁
* Expose `MapInitOptions.mapName` (`mapbox_mapName` in XML) property allowing to set the custom name which will be appended to map render related logs.

## Dependencies
* Update gl-native to v11.2.2 and common to v24.2.3.

# 11.2.1 March 15, 2024
## Bug fixes 🐞
* Apply config expression to atmosphere properties.
* Fix map freezing when using `queryRenderedFeatures` with 3d models in mercator projection.

## Dependencies
* Update gl-native to v11.2.1.

# 11.3.0-beta.1 March 14, 2024
## Breaking changes ⚠️
* [compose] Replace experimental `MapboxMap.compassSettings`, `MapboxMap.scaleBarSettings`, `MapboxMap.logoSettings`, `MapboxMap.attributionSettings` with composable functions in dedicated scopes: `MapCompassScope.Compass()`, `MapScaleBarScope.ScaleBar()`, `MapLogoScope.Logo()`, `MapAttributionScope.Attribution()`.

## Features ✨ and improvements 🏁
* Added Attribution and Telemetry pop-up dialogs and compass view content description translations for Arabic, Bulgarian, Catalan, Chinese Simplified, Chinese Traditional, Czech, Danish, Dutch, French, Galician, German, Hebrew, Italian, Japanese, Korean, Lithuanian, Norwegian, Polish, Belarusian, Russian, Spanish, Swedish, Ukranian and Vietnamese.
* Faster landmark parsing by switching tinygltf in favor of cgltf.
* Use mipmap with pattern images .
* Add `SdkInformationQuery` to expose sdk version information.
* Enable `TileStore` delta updates by default for Maps domain.
* Add `TileStore.estimateTileRegion` API for estimating Tile Region downloads and storage size.
* [compose] Introduce experimental `MapStyle`, `MapboxStandardStyle`, `GenericStyle` composable functions to work with the map style.
* [compose] Added experimental layer and source composable functions to insert layer/sources to the map.
* [compose] Improve annotation group update speed.

## Bug fixes 🐞
* [compose] Fix `ViewAnnotation` not cleared when it leaves composition.
* Fix attribution links not opening in some scenarios.
* Fix attribution and telemetry dialogs not respecting current theme.
* Fix map being pixelated on some devices when `ContextMode.SHARED` is used (e.g. in AndroidAuto extension).
* Fix incorrect widget position and scale when resizing the drawing surface.
* Fix layer paint property update with feature state changes, especially if the paint property value data-driven by brightness or zoom.
* Fix snapshotter race conditions to ensure new request could effectively trigger map rendering.
* Fix raster array band updates glitches during the camera zoom animation.
* Reload vector source tiles when language or worldview setting is changed.
* Apply config expression to atmosphere properties.
* Fix map freezing when using `queryRenderedFeatures` with 3d models in mercator projection.
* Reduce time spent on line gradient updates on the render thread.
* Fix network reachability status getting stuck with ReachableViaWWAN status if HTTP requests completed at the same time as network reported being disconnected.
* Fix wrong network reachability statuses.
* Fix double `LocationServiceObserver.onAvailabilityChanged` callback trigger.
* Avoid bringing Kotlin 1.8 as transitive dependency, Maps SDK should use Kotlin 1.7.20.

## Dependencies
* Update gl-native to v11.3.0-beta.1 and common to v24.3.0-beta.1.


# 11.2.0 February 29, 2024
## Features ✨ and improvements 🏁
* Introduce better way of SDK initialization to avoid `java.lang.UnsatisfiedLinkError` exception on process startup. If the native library is still not found when actual Mapbox API is called, meaningful `MapboxInitializerException` is thrown and could be caught and processed on user's side.
* Introduce `MapboxMap.getCenterAltitudeMode` API.
* Add `useShortestPath` option to `CameraAnimationPlugin.createCenterAnimator`, when enabled, shortest path will be applied when the start and end camera  center is across the antimeridian, for example from -170 to 170 longitude. Defaults to true.
* Introduce `SymbolLayer.iconColorSaturation` API.
* Introduce experimental `RasterLayer.rasterElevation` API.
* Introduce experimental `MapboxMap.startPerformanceStatisticsCollection` / `MapboxMap.stopPerformanceStatisticsCollection` APIs allowing to start / stop collecting map rendering performance statistics.
* Introduce `GeoJsonSource.tileCacheBudget`, `RasterSource.tileCacheBudget`, `RasterDemSource.tileCacheBudget`, `RasterArraySource.tileCacheBudget`, `VectorSource.tileCacheBudget`, `CustomGeometrySource.tileCacheBudget`, `CustomRasterSource.tileCacheBudget`.
* Skip unneeded layers properties re-evaluation on zoom change.
* Add the possibility to use constant expressions for `model-emissive-strength` when rendering 3D model layers using 2D sources.
* Introduce static `HttpServiceFactory.setMaxRequestsPerHost` API.
* `TileStoreOptions.DiskQuota` is now an abort threshold for tilestore size. When we have more than this amount of bytes stored, new downloads will fail. `Tilestore` starts to evict tiles with closest expiration date 50Mb (or 10% of DiskQuota, whatever is smaller) before this abort threshold is reached.
* Speedup preparing tiled sources for rendering.
* Uploading model resources to GPU in continuous map mode is now limited by fixed time per frame.
* Modify `FillExtrusionLayer.fillExtrusionCutoffFadeRange` to scale down and remove buildings in a staggered fashion, instead of fading opacity.
* [compose] Introduce `DisposableMapEffect` API.
* [compose] Add default value for `MapViewportState.transitionToFollowPuckState.followPuckViewportStateOptions`.

## Bug fixes 🐞
* Retain previous `CenterAltitudeMode` after gestures are finished.
* Avoid marking whole `LayerDsl` as experimental when only a part of the layer properties are experimental.
* Fix R8 error due to missing class `com.tobrun.datacompat.annotation.Default`.
* `EaseTo` and `MoveBy` camera animation and `DefaultViewportTransition` now will use shortest path when the start and end camera center is across the antimeridian, for example from -170 to 170 longitude.
* Remove extra image padding from text shaping offset.
* Address crashes on certain Android devices by disabling the texture pool.
* Fix elevated rasters with coordinates not aligned to the longitude/latitude grid.
* Fix a bug that was causing absence of `MapLoaded` event and never ending background task processing.
* Fix a bug that heatmap layer wasn't updating visuals after feature state change.
* Fix a bug where scientific notation is not supported when parse JSON numbers to `Value`.
* Fix a crash occurring when clicking on the "Telemetry settings" option in the attribution dialog when not using the `AppCompat` theme.
* Fix `ModelLayer.modelCutoffFadeRange` calculation on low zoom levels.
* Fix `RasterArray` rendering on Android.
* Fix rare null pointer dereference crash.
* Fix a bug with disappearing models during light changes.
* Fix rendering artifacts on long fill outlines on pitched map view.
* Fix style parsing when the style and import's urls are both empty.
* Fix config expression evaluation if the expected type is formatted but the actual value is string.
* Fix not taking `line-trim-offset` into account for Dynamic View Annotation placement.
* Fix issue that View Annotation stays visible when the associated layer's visibility is none.
* Fix camera framing on globe with padding.
* [compose] Fix a warning that using UI composable where Mapbox Map composable is expected.
* [compose] Fix losing some location component settings during wrapping.

## Dependencies
* Update gl-native to v11.2.0 and common to v24.2.0.
* Upgrade to [Kotlin Data compat v0.8.0](https://github.com/tobrun/kotlin-data-compat/releases/tag/v0.8.0).


# 11.2.0-rc.1 February 15, 2024
## Features ✨ and improvements 🏁
* [compose] Introduce `DisposableMapEffect` API.
* Speedup preparing tiled sources for rendering.
* Uploading model resources to GPU in continuous map mode is now limited by fixed time per frame.
* Modify `FillExtrusionLayer.fillExtrusionCutoffFadeRange` to scale down and remove buildings in a staggered fashion, instead of fading opacity.

## Bug fixes 🐞
* [compose] Fix a warning that using UI composable where Mapbox Map composable is expected.
* Fix a crash occurring when clicking on the "Telemetry settings" option in the attribution dialog when not using the `AppCompat` theme.
* Fix `ModelLayer.modelCutoffFadeRange` calculation on low zoom levels.
* Fix `RasterArray` rendering on Android.
* Fix rare null pointer dereference crash.

## Dependencies
* Update gl-native to v11.2.0-rc.1 and common to v24.2.0-rc.2.


# 11.2.0-beta.1 February 01, 2024
## Features ✨ and improvements 🏁
* Introduce better way of SDK initialization to avoid `java.lang.UnsatisfiedLinkError` exception on process startup. If the native library is still not found when actual Mapbox API is called, meaningful `MapboxInitializerException` is thrown and could be caught and processed on user's side.
* Introduce `MapboxMap.getCenterAltitudeMode` API.
* Add `useShortestPath` option to `CameraAnimationPlugin.createCenterAnimator`, when enabled, shortest path will be applied when the start and end camera  center is across the antimeridian, for example from -170 to 170 longitude. Defaults to true.
* Introduce `SymbolLayer.iconColorSaturation` API.
* Introduce experimental `RasterLayer.rasterElevation` API.
* Introduce experimental `MapboxMap.startPerformanceStatisticsCollection` / `MapboxMap.stopPerformanceStatisticsCollection` APIs allowing to start / stop collecting map rendering performance statistics.
* Introduce `GeoJsonSource.tileCacheBudget`, `RasterSource.tileCacheBudget`, `RasterDemSource.tileCacheBudget`, `RasterArraySource.tileCacheBudget`, `VectorSource.tileCacheBudget`, `CustomGeometrySource.tileCacheBudget`, `CustomRasterSource.tileCacheBudget`.
* Skip unneeded layers properties re-evaluation on zoom change.
* Add the possibility to use constant expressions for `model-emissive-strength` when rendering 3D model layers using 2D sources.
* Introduce static `HttpServiceFactory.setMaxRequestsPerHost` API.
* `TileStoreOptions.DiskQuota` is now an abort threshold for tilestore size. When we have more than this amount of bytes stored, new downloads will fail. `Tilestore` starts to evict tiles with closest expiration date 50Mb (or 10% of DiskQuota, whatever is smaller) before this abort threshold is reached.

## Bug fixes 🐞
* Retain previous `CenterAltitudeMode` after gestures are finished.
* Avoid marking whole `LayerDsl` as experimental when only a part of the layer properties are experimental.
* Fix R8 error due to missing class `com.tobrun.datacompat.annotation.Default`.
* `EaseTo` and `MoveBy` camera animation and `DefaultViewportTransition` now will use shortest path when the start and end camera center is across the antimeridian, for example from -170 to 170 longitude.
* Remove extra image padding from text shaping offset.
* Address crashes on certain Android devices by disabling the texture pool.
* Fix elevated rasters with coordinates not aligned to the longitude/latitude grid.
* Fix a bug that was causing absence of `MapLoaded` event and never ending background task processing.
* Fix a bug that heatmap layer wasn't updating visuals after feature state change.
* Fix a bug where scientific notation is not supported when parse JSON numbers to `Value`.

## Dependencies
* Upgrade to [Kotlin Data compat v0.8.0](https://github.com/tobrun/kotlin-data-compat/releases/tag/v0.8.0).
* Update gl-native to v11.2.0-beta.1 and common to v24.2.0-beta.1.

## Known issues
With the new way of Mapbox SDK initialization, your application __unit tests__ may start failing when interacting with Mapbox APIs, even if mocked.
The error may look like:
```
Exception java.lang.NoClassDefFoundError: Could not initialize class com.mapbox.common.location.Location
```
or
```
Caused by: java.lang.RuntimeException: Method w in android.util.Log not mocked. See https://developer.android.com/r/studio-ui/build/not-mocked for details.
  at android.util.Log.w(Log.java)
  at com.mapbox.common.BaseMapboxInitializer$Companion.init(BaseMapboxInitializer.kt:116)
  at com.mapbox.common.BaseMapboxInitializer.init(BaseMapboxInitializer.kt)
  at com.mapbox.common.location.Location.<clinit>(Location.java:442)
```

To fix above errors, `BaseMapboxInitializer` should be properly mocked before accessing the failing Mapbox class. For example, when using Kotlin [MockK](https://mockk.io/) you should add the following code:
```kotlin
mockkObject(BaseMapboxInitializer)
every { BaseMapboxInitializer.init<Any>(any()) } just Runs
// your test code
unmockkObject(BaseMapboxInitializer)
```
Alternative solution is to apply [`unitTests.returnDefaultValues`](https://developer.android.com/training/testing/local-tests#error).


# 11.1.0 January 17, 2024
## Features ✨ and improvements 🏁
* From v11.1.0, Mapbox Android Auto Extension for Android is released as a separate module following the same release cadence with the main SDK, please refer to [this guide](extension-androidauto/README.md) to get started with Android Auto, and [v0.5.0 changelog](extension-androidauto/CHANGELOG-v0.5.0.md) for previous changelogs.
* Define minCompileSdkVersion=31 for Maps SDK libraries.
* Introduce `FillExtrusionLayer.fillExtrusionEmissiveStrength`, `HillshadeLayer.hillshadeEmissiveStrength` and `RasterLayer.rasterEmissiveStrength` properties.
* Introduce `RasterLayer.rasterArrayBand` property.
* Improve performance of `StyleInterface.localizeLabels` method.
* Introduce `RasterArraySource` with read only property `RasterArraySource.rasterLayers`, which can be used in combination with `RasterLayer.sourceLayer` and `RasterLayer.rasterArrayBand`.
* Improve error handling for `UnknownNativeException`, more specific error message will be thrown instead.
* Support landmark tilesets with compressed textures for improved tile load performance.
* Improve performance of changing layers' properties.

## Bug fixes 🐞
* Fix `MapSurface` rendering issue when widgets are used.
* Fix `coordinateBoundsForCameraUnwrapped` returning wrapped coordinates.
* Fix inconsistent behavior in fill-extrusion color when using directional and ambient lights.
* Downloaded but corrupted style is now invalidated and will be downloaded again on the next load.
* Fixed missing tiles in the bottom part of the screen when looking from the mountain down to the valley.
* Do not emit slot missing warnings if style imports are not fully loaded.
* Fixed wrong dem tile selection from elevation snapshots in rare cases.
* Fixed tile flickering with enabled terrain.
* Add missing properties, i.e. `array`, `values`, `maxValue`, `minValue`, `stepValue`, `metadata` when using `getStyleImportSchema` API, if they are present in the original schema.
* Exclude duplicated `tileID` in `MapboxMap.tileCover` querying results.
* Fix race condition on repeated style transitions, when the transition fails for some layers.
* Allow style schema to control imported fragment configs.
* Fix possible rendering artifacts on startup when `ContextMode.SHARED` is used.
* Fix wrong camera positions while using `setBounds`.
* Fix missing IDs of flat light types when configured through the `setLights` API.
* Reload image dependent tiles when sprites are loaded.
* Fix an issue where memory use would grow continuously with Z-offset enabled symbol layers.
* Fix crash when using Dynamic View Annotation with location indicator enabled but visibility is turned from visible to none.
* Fix `getStyleSourceProperties()` API for `RasterArray` source.
* Fix unreliable position update of View Annotations.
* Fix GeoJSON partial updates when there is an error during update followed by multiple update calls.

## Dependencies
* Update gl-native to v11.1.0 and common to v24.1.0.

# 11.1.0-rc.1 January 04, 2024
## Bug fixes 🐞
* Fix possible rendering artifacts on startup when `ContextMode.SHARED` is used.
* Fix regression introduced in v11.1.0-beta.1 where only last added widget was rendered.
* Fix wrong camera positions while using `setBounds`.
* Fix missing IDs of flat light types when configured through the `setLights` API.
* Reload image dependent tiles when sprites are loaded.
* Fix an issue where memory use would grow continuously with Z-offset enabled symbol layers.
* Fix crash when using Dynamic View Annotation with location indicator enabled but visibility is turned from visible to none.
* Fix `getStyleSourceProperties()` API for `RasterArray` source.
* Fix unreliable position update of View Annotations.

## Dependencies
* Update gl-native to v11.1.0-rc.1 and common to v24.1.0-rc.1.


# 11.1.0-beta.1 December 19, 2023
## Features ✨ and improvements 🏁
* Define minCompileSdkVersion=31 for Maps SDK libraries.
* Introduce `FillExtrusionLayer.fillExtrusionEmissiveStrength`, `HillshadeLayer.hillshadeEmissiveStrength` and `RasterLayer.rasterEmissiveStrength` properties.
* Introduce `RasterLayer.rasterArrayBand` property.
* Improve performance of `StyleInterface.localizeLabels` method.
* Introduce `RasterArraySource` with read only property `RasterArraySource.rasterLayers`, which can be used in combination with `RasterLayer.sourceLayer` and `RasterLayer.rasterArrayBand`.
* Improve error handling for `UnknownNativeException`, more specific error message will be thrown instead.
* Support landmark tilesets with compressed textures for improved tile load performance.

## Bug fixes 🐞
* Fix a `MapSurface` rendering issue when widgets are used.
* Fix `coordinateBoundsForCameraUnwrapped` returning wrapped coordinates.
* Fix inconsistent behavior in fill-extrusion color when using directional and ambient lights.
* Downloaded but corrupted style is now invalidated and will be downloaded again on the next load.
* Fixed missing tiles in the bottom part of the screen when looking from the mountain down to the valley.
* Do not emit slot missing warnings if style imports are not fully loaded.
* Fixed wrong dem tile selection from elevation snapshots in rare cases.
* Fixed tile flickering with enabled terrain.
* Add missing properties, i.e. `array`, `values`, `maxValue`, `minValue`, `stepValue`, `metadata` when using `getStyleImportSchema` API, if they are present in the original schema.
* Exclude duplicated tileID in tileCover querying results.
* Fix race condition on repeated style transitions, when the transition fails for some layers.
* Allow style schema to control imported fragment configs.

## Dependencies
* Update gl-native to v11.1.0-beta.1 and common to v24.1.0-beta.2.


# 11.0.0 November 29, 2023
## Breaking changes ⚠️
* **Minimum OpenGL ES version is now 3.0**.
* **Update SDK's `targetSdkVersion` and `compileSdkVersion` to 33**.
* Remove deprecated `GeoJsonSource` public constructor, builder should be used instead.
* Remove deprecated `MapboxMap.queryRenderedFeatures` methods.
* Remove `Snapshotter.setTileMode`, `Snapshotter.isInTileMode` methods.
* Remove deprecated `MapStyleStateDelegate` and `isFullyLoaded` method.
* Remove experimental `setRenderCacheOptions`, `getRenderCacheOptions` apis.
* Add `callback` argument to the `MapboxMap` methods `getFeatureState`, `setFeatureState`, `removeFeatureState`.
* Use different callback types for the `MapboxMap.queryRenderedFeatures` and the `MapboxMap.querySourceFeatures` methods.
* Return `cancelable` from the `MapboxMap` methods : `getFeatureState`, `setFeatureState`, `removeFeatureState`, `querySourceFeatures`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Remove the deprecated `MapboxMap.queryFeatureExtensions` method.
* Remove `MapAnimationOptions.animatorListener` property. In order to subscribe to animations, provide `Animator.animatorListener` with `flyTo`, `easeTo`, `pitchBy`, `scaleBy`, `moveBy`, `rotateBy` apis.
* Replace `LocationEngine` use with `LocationService` in `DefaultProvider`.
* Add new `LocationConsumer.onError` method to allow consumers handle location errors.
* Remove `MapView.location2` related interfaces and move `showAccuracyRing`, `accuracyRingColor`, `accuracyRingBorderColor`, `puckBearingEnabled` and `puckBearingSource` to `MapView.location`.
* Make `AttributionSettings`, `CompassSettings`, `GesturesSettings`, `LocationComponentSettings`, `LogoSettings`, `ScaleBarSettings` not Kotlin `data class`, better binary compatible and implementing `Parcelable`.
* Change `CompassSettings.image`, `LocationPuck2D.topImage`, `LocationPuck2D.bearingImage`, `LocationPuck2D.shadowImage` to `ImageHolder` allowing to pass either drawable id or `Bitmap`.
* Remove deprecated `backgroundPatternTransition`, `lineDasharrayTransition`, `linePatternTransition`, `fillPatternTransition` properties.
* Replace `MapSnapshotInterface` interface with `MapSnapshotResult` abstract class and remove `image()` method, `bitmap()` should be used instead.
* Change `Annotation.id` from monotonically increasing `Long` to UUID represented as `String`.
* Remove `Annotation.featureIdentifier` used to connect with View Annotations, now `Annotation.id` should be used instead.
* Rename `PuckBearingSource` to `PuckBearing` in location component plugin.
* Remove deprecated overloaded `Style.setStyleGeoJSONSourceData(sourceId: String, data: GeoJSONSourceData)` method.
* Rename `MapboxMap.setMemoryBudget` to `MapboxMap.setTileCacheBudget` and make it non-experimental.
* Remove `ResourceOptions` and `ResourceOptionsManager`. Introduce `MapboxOptions` and `MapboxMapsOptions` to handle application-level access token and other generic options.
* Removed XML attributes `mapbox_resourcesAccessToken` and `mapbox_resourcesBaseUrl`.
* Update Mapbox styles to latest versions:

| Style             | Before                                       | After                                        |
|-------------------|----------------------------------------------|----------------------------------------------|
| MAPBOX_STREETS    | mapbox://styles/mapbox/streets-v11           | mapbox://styles/mapbox/streets-v12           |
| SATELLITE_STREETS | mapbox://styles/mapbox/satellite-streets-v11 | mapbox://styles/mapbox/satellite-streets-v12 |
| OUTDOORS          | mapbox://styles/mapbox/outdoors-v11          | mapbox://styles/mapbox/outdoors-v12          |
| LIGHT             | mapbox://styles/mapbox/light-v10             | mapbox://styles/mapbox/light-v11             |
| DARK              | mapbox://styles/mapbox/dark-v10              | mapbox://styles/mapbox/dark-v11              |


* Remove native interfaces `StyleManagerInterface`, `StyleInterface`, `CameraManagerInterface`, `MapInterface`, `ObservableInterface` and use only `Map` object to access native methods.
* Make map events typed-safe, events are now have their own subscription methods.
  Following events are added as typed-safe, `CameraChanged`, `MapIdle`, `MapLoadingError`, `MapLoaded`, `StyleDataLoaded`, `StyleLoaded`, `StyleImageMissing`, `StyleImageRemovedUnunsed`,
  `RenderFrameStarted`, `RenderFrameFinished`, `SourceAdded`, `SourceDataLoaded`, `SourceRemoved`, `ReourceRequest`.
  All `subscribe` methods return `Cancelable` object, which users could store and call `cancel` when subscription is no longer needed.
  `MapboxMap.unsubscribe` methods were removed.
* Rename `LocationConsumer.onAccuracyRadiusUpdated` to `onHorizontalAccuracyRadiusUpdated`.
* Deprecate `MapboxMap.loadStyleUri`, `MapboxMap.loadStyleJson` and `MapboxMap.loadStyle` methods and introduce one `MapboxMap.loadStyle` accepting either URI / JSON or Style DSL block.
* Replace `com.mapbox.maps.plugin.animation.Cancelable` with `com.mapbox.common.Cancelable`.
* Remove `TileStoreOptions.MAPBOX_ACCESS_TOKEN` used e.g. in `TileStore.setOption(TileStoreOptions.MAPBOX_ACCESS_TOKEN, someDomain, someValue)` as it has become redundant.
* Introduce `MapboxTracing` object allowing to enable Android traces to measure performance of the SDK. More details and instructions could be found in `Working with traces` section in `DEVELOPING.md`.
* Remove Mapbox plugin implementations from public API surface.
* Remove `HttpServiceFactory.getInstance`, `HttpServiceFactory.reset`, `HttpServiceFactory.setUserDefined` methods.
* Replace `HttpServiceFactory.getInstance().setInterceptor` with `HttpServiceFactory.setHttpServiceInterceptor`.
* Argument `dataId` of the `GeoJson.feature`, `GeoJson.featureCollection`, `GeoJson.geometry`, `GeoJson.url`, `GeoJson.data` became non-nullable.
* Remove `Style.getStyleSourcesAttribution`. `MapboxMap.getAttributions` should be used instead.
* (Kotlin only) Remove `Style.getStyleSources()` / `Style.getStyleLayers()`. Properties `Style.styleSources` / `Style.styleLayers` should be used instead.
* Replace style related enum classes with regular classes.
* Migrate from `android.app.AlertDialog` to `androidx.appcompat.app.AlertDialog` for attribution plugin.
* Rename `MapboxMap.subscribeStyleImageUnused` to `MapboxMap.subscribeStyleImageRemoveUnused`.
* The `MapCameraPlugin`'s `onCameraMove` method now uses `Point` for camera center and `EdgeInsets` for padding.
* Remove `MapInitOptions.optimizeForTerrain` as whenever terrain is present layer order is automatically adjusted for better performance.
* Replace `MapboxMap` and `MapCameraManagerDelegate` APIs `dragStart`, `dragEnd`, `getDragCameraOptions` with `cameraForDrag`, `setCenterAltitudeMode`.
* Remove setter functions for `Style.styleURI` and `Style.styleJSON` as loading the style should happen only with `MapboxMap.loadStyle`.
* Rename `StyleImageMissing.getStyleImageMissingEventData` to `StyleImageMissing.toStyleImageMissingEventData`.
* Rename `MapCameraManagerDelegate` properties for methods `cameraForCoordinateBounds`, `cameraForCoordinates` and `cameraForGeometry` to align them with `MapboxMap` methods.
* Consolidate `FetchTileFunctionCallback` and `CancelTileFunctionCallback` by single type `TileFunctionCallback`.
* Make `Image` parameter nullable in `setStyleCustomRasterSourceTileData()` method.
* Extension function `Style.getProjection()` return type changed from `Projection` to `Projection?`.
* Extension function `LocationComponentPlugin.createDefault2DPuck` in `LocationComponentUtils.kt` is now stand-alone `createDefault2DPuck`.
* Extension function `LocationComponentPlugin.createDefault2DPuck` in `LocationComponentUtils` is now stand-alone `createDefault2DPuck`.
* Function `createDefault2DPuck` does not require a `context` parameter.
* Increase minimum location puck bearing threshold needed to trigger an animation to 1 degree (previously 0.01 degrees) to reduce the CPU usage.
* Location component puck bearing enabled property (`MapView.location.puckBearingEanbled`) has been changed to `false` by default.
* `ViewAnnotationManager.getViewAnnotationByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotation`,
* `ViewAnnotationManager.getViewAnnotationByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotation`.
* `ViewAnnotationManager.getViewAnnotationOptionsByView` is renamed to `ViewAnnotationManager.getViewAnnotationOptions`.
* `ViewAnnotationManager.getViewAnnotationOptionsByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotationOptions`.
* `ViewAnnotationAnchorConfig` fields `offsetX`/`offsetY` are now of type Double instead of Int.
* `ViewAnnotationOptions` accepts list of anchors `variableAnchors` instead of `anchor`/`offsetX`/`offsetY`.
* `ViewAnnotationOptions` fields `width`/`height` are now of type Double instead of Int.
* `OnViewAnnotationUpdatedListener.onViewAnnotationPositionUpdated` arguments `width`/`height` are now of type Double instead of Int.
* Add `getName` method to `DeviceLocationProvider` interface.
* Add boolean parameter `allowUserDefined` to `getDeviceLocationProvider` method in `LocationService` interface.
* Add `LocationService.getDeviceLocationProvider` method that accepts `ExtendedLocationProviderParameters` to `LocationService` interface.

## Features ✨ and improvements 🏁
* Introduce new Mapbox 3D style `Style.STANDARD` and make it default.
* Add dynamic view annotations that can be attached to complex feature geometries and reposition itself based on the current camera viewport. To create dynamic view annotation use `AnnotatedFeature` of type `ANNOTATED_LAYER_FEATURE`. Multiple dynamic view annotations can be attached to the same feature. Also additional options `ViewAnnotationOptions.allowOverlapWithPuck` and `ViewAnnotationOptions.ignoreCameraPadding` introduced to configure the dynamic view annotation's behaviour.
* The Map Overlay plugin and related APIs have been promoted to stable.
* From v11.0.0-rc.1, Mapbox Compose Extension for Android is released as a separate module following the same release cadence with the main SDK, please refer to [this guide](extension-compose/README.md) to get started with Jetpack Compose, and [v0.1.0 changelog](extension-compose/CHANGELOG-v0.1.0.md) for previous changelogs.
* Avoid creating unnecessary objects during animation under some conditions.
* Improve map camera and gestures when terrain is used to fix camera bumpiness and map flickering.
* Use a fallback glyph URL if a style does not define one. This enables the addition of Symbol layers to an empty style or to the style that doesn't use Symbol layers.
* Use ETC2 compression for raster tiles to support transparency.
* Introduce experimental `MapboxMapRecorder` allowing to record and replay custom scenarios.
* Add Mapbox Privacy Policy to attribution links.
* Add clustering support for `CircleAnnotationManager`.
* Improve ergonomics of `Snapshotter.start` method to align with iOS and allow to re-use `Canvas` for user drawing.
* Add `MapboxMap.coordinateBoundsForRect` returning `CoordinateBounds` for given `RectF` of screen coordinates.
* Add optional `maxZoom` and `offset` parameters to `MapboxMap.cameraForCoordinateBounds`.
* Mark `GeoJsonSource.url` / `GeoJsonSource.Builder.url` methods as deprecated, `GeoJsonSource.data` / `GeoJsonSource.Builder.data` should be used instead.
* Refactor style Light APIs: introduce `AmbientLight`, `DirectionalLight`, `FlatLight` and methods to set them to style.
* Expose new APIs to import and configure styles: `getStyleImports`, `removeStyleImport`, `getStyleImportSchema`, `getStyleImportConfigProperties`, `setStyleImportConfigProperties`, `getStyleImportConfigProperty`, `setStyleImportConfigProperty`
* Expose `slot` property for all `Layer`s to link layers from imported styles.
* Add expression support for visibility layer property.
* Add the `MapboxMap.resetFeatureState` method.
* Make padding optional for `MapboxMap.cameraForCoordinateBounds`, `MapboxMap.cameraForCoordinates`, `MapboxMap.cameraForGeometry` methods.
* Add `FreeCameraOptions.getLocation` and `FreeCameraOptions.getAltitude` methods.
* Add `MapboxMap.coordinatesForRect(rectF: RectF)` to support rectangle parameters.
* Add `suspend` variants for the async `MapboxMap` functions : `queryRenderedFeatures`, `querySourceFeatures`, `setFeatureState`, `getFeatureState`, `removeFeatureState`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Add `MapboxMap.mapLoadedEvents`, `MapboxMap.mapLoadingErrorEvents`, `MapboxMap.styleLoadedEvents`, `MapboxMap.styleDataLoadedEvents`, `MapboxMap.cameraChangedEvents`, `MapboxMap.mapIdleEvents`, `MapboxMap.sourceAddedEvents`, `MapboxMap.sourceRemovedEvents`, `MapboxMap.sourceDataLoadedEvents`, `MapboxMap.styleImageMissingEvents`, `MapboxMap.styleImageRemoveUnusedEvents`, `MapboxMap.renderFrameStartedEvents`, `MapboxMap.renderFrameFinishedEvents`, `MapboxMap.resourceRequestEvents` returning Flow of events.
* Introduce custom lint rules to check illegal usage of literals in Expression DSL and suggest auto fix.
* Introduce custom lint rules to check illegal number of arguments within given Expression DSL.
* Introduce custom lint rules to check unused layer/source/light/terrain/atmosphere/projection objects in the Style DSL, and suggest auto fix to add it to the style using unaryPlus(+) operator.
* Improve performance for `Snapshotter` when obtaining the bitmap.
* Add `ImageSource.updateImage(Bitmap)` method.
* Introduce Expression overload functions `linearInterpolator`, `exponentialInterpolator`, `cubicBezierInterpolator`, `step`, `match` and `switchCase` to construct these expressions with strongly typed parameters.
* Introduce `ImageExtensionImpl.Builder(imageId, image)`, `ImageExtensionImpl.Builder(imageId, image)` constructors and deprecated `ImageExtensionImpl.Builder(imageId)`, `ImageExtensionImpl.Builder.image(image)`, `ImageExtensionImpl.Builder.bitmap(bitmap)`, as image/bitmap is required for `ImageExtensionImpl.Builder`; DSL functions are updated to reflect these changes as well.
* Deprecate `PointAnnotationManager.iconTextFit` and `PointAnnotationManager.iconTextFitPadding` in favor of `PointAnnotation.iconTextFit` and `PointAnnotation.iconTextFitPadding`.
* Introduce `LineLayer.lineDepthOcclusionFactor`, `PolylineAnnotationManager.lineDepthOcclusionFactor` API.
* Introduce experimental `Expression.activeAnchor` API.
* Introduce experimental `ModelLayer` API to render 3D models on the map.
* Introduce experimental `MapboxMap.addStyleModel`, `MapboxMap.removeStyleModel`, `MapboxMap.hasStyleModel` APIs.
* Introduce experimental `LocationPuck3D.modelCastShadows`, `LocationPuck3D.modelReceiveShadows`, `LocationPuck3D.modelScaleMode` APIs. Note: `LocationPuck3D.modelScaleMode` API brings behavioral changes for LocationPuck3d scale and `model-scale` property needs to be adjusted to correctly render the puck.
* Introduce `Expression.hsl`, `Expression.hsla` color expression.
* Introduce `Expression.measureLight` lights configuration property.
* Introduce `LineLayer.lineBorderColor`, `LineLayer.lineBorderWidth` APIs.
* Introduce `BackgroundLayer.backgroundEmissiveStrength`, `CircleLayer.circleEmissiveStrength`, `FillLayer.fillEmissiveStrength`, `LineLayer.lineEmissiveStrength`, `SymbolLayer.iconImageCrossFade`, `SymbolLayer.iconEmissiveStrength`, `SymbolLayer.textEmissiveStrength` APIs.
* Introduce Annotation plugin APIs: `CircleAnnotationManager.circleEmissiveStrength`, `PolygonAnnotationManager.fillEmissiveStrength`, `PolylineAnnotationManager.lineDepthOcclusionFactor`, `PolylineAnnotationManager.lineEmissiveStrength`.
* Introduce experimental `FillExtrusionLayer.fillExtrusionRoundedRoof`, `FillExtrusionLayer.fillExtrusionEdgeRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionWallRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundAttenuation`, `FillExtrusionLayer.fillExtrusionFloodLightColor`, `FillExtrusionLayer.fillExtrusionFloodLightIntensity`, `FillExtrusionLayer.fillExtrusionFloodLightWallRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundAttenuation`, `FillExtrusionLayer.fillExtrusionVerticalScale` APIs.
* Introduce GeoJSONSource partial update APIs `GeoJsonSource.addGeoJSONSourceFeatures`, `GeoJsonSource.updateGeoJSONSourceFeatures`, `GeoJsonSource.removeGeoJSONSourceFeatures`.
* Introduce raster colorization via `raster-color` expression and `RasterLayer.rasterColor`, `RasterLayer.rasterColorMix`, `RasterLayer.rasterColorRange` layer properties.
* Make both `Style` and `MapboxMap` implement `MapboxStyleManager` exposing all style related methods for the map and providing better alignment with iOS and GL-JS.
* Add `FillExtrusionLayer.fillExtrusionCutoffFadeRange` and `ModelLayer.modelCutoffFadeRange` to control fade out of the layers when pitch is used.
* Improve atmosphere rendering performance.
* Improve fill layer and model layer rendering performance on high pitch views.
* Improve GPU performance by drawing opaque 3D geometry without blending.
* Introduce `SymbolLayer.symbolZElevate` property to elevate symbols along with the buildings that are in the same place. The feature is supported for symbols with point placement only.
* Add `Atmosphere.verticalRange` property.
* Add `GeoJsonSource.data(..)` overloads to allow Java to call with and without `dataId`.
* Add a new layer type `CustomLayer` and style DSL to create it. `CustomLayer` allows to use custom OpenGL ES rendering through `CustomLayerHost`. `CustomLayer` contains the `slot` property allowing using it with the Standard style.
* Improve the caching model for the custom raster source.
* Optimize custom raster source data update.
* Increase rendering performance of shadows.
* Print warning log message for unknown style layer properties instead of causing fatal errors.
* Optimise memory usage in the `FillExtrusionLayer`.
* Improve the rendering performance of a `SymbolLayer` that uses `SymbolLayer.symbolSortKey` property.
* Reduce memory usage in fill-extrusion flood light and ground ambient occlusion.
* (Kotlin only) Deprecated `MapboxMap.getStyle()` function. Please use property `MapboxMap.style`.
* (Kotlin only) Deprecated `MapView.getMapboxMap()` function. Please use property `MapView.mapboxMap`.
* (Kotlin only) Deprecated `MapSurface.getMapboxMap()` function. Please use property `MapSurface.mapboxMap`.
* Handle zero duration map camera animators more efficiently resulting in performance improvements for gestures / viewport / locationcomponent.
* Function `DefaultLocationProvider.updatePuckBearing` now accepts null to stop listening for heading/course.
* Add `keep-legacy-style-pack` style pack load extra option that prevents the style package removal from the legacy storage.
* Enable rendering of fill extrusion flood lights on the ground with fully transparent fill extrusions.
* Add `cameraForCoordinates` overload so that the padding for map and geometry can be specified separately.
* Disable terrain when zoom-dependent exaggeration expression evaluates to zero.
* Add support for `glb` 3D tiles.
* Align hill shade illumination direction with 3D lights.
* [compose] Add experimental `MapEvents` to handle all events emitted by `MapboxMap`.
* [compose] Expose `PointAnnotationGroup.symbolZElevate` property.
* [compose] Add `ViewAnnotation.layoutParams` property.
* Introduce `ExtendedLocationProviderParameters` to specify `DeviceLocationProvider` parameters.
* Add `coordinatesPadding`, `maxZoom` and `offset` parameters to `OverviewViewportStateOptions` to allow more granular control of `OverviewViewportState`.
* Add `CircleAnnotationManager.slot`, `PointAnnotationManager.slot`, `PolygonAnnotationManager.slot`, `PolylineAnnotationManager.slot` to place the associated layer of the `AnnotationManager` to the correct position of the map.
* Introduce experimental APIs to work with Custom Raster Sources: `MapboxStyleManager.addStyleCustomRasterSource`, `MapboxStyleManager.setStyleCustomRasterSourceTileData`, `MapboxStyleManager.invalidateStyleCustomRasterSourceTile`, `MapboxStyleManager.invalidateStyleCustomRasterSourceRegion`.
* Expose experimental `CustomRasterSource` and non-experimental `CustomGeometrySource` as regular `Source`'s providing a better way to work with them and also allowing using them in Style DSL.
* Deprecate `CustomGeometrySource.invalidTile` and `CustomGeometrySource.invalidRegion`; `CustomGeometrySource.invalidateTile` and `CustomGeometrySource.invalidateRegion` should be used instead.
* Add `LocationPuck3D.modelEmissiveStrength` and `LocationPuck3D.modelEmissiveStrengthExpression` properties to LocationComponent plugin to control the strength of the light emission.

## Bug fixes 🐞
* Fix scale bar receives camera changes after being disabled the first time.
* Fix camera flying away on pitch gesture after some other animations.
* Fix map not being rendered when EGL exception happens but Android surface is still valid.
* Fix terrain rendering for the Terrarium-encoded tiles.
* Cancel pending style url loading request when loading a new style json.
* Fixes an issue that causes view annotations to be placed on the sky when high pitch and mercator projection is used.
* Fix the CustomGeometrySource `fetchTileFunction` and `cancelTileFunction` callbacks being invoked on the wrong thread.
* Fix a rendering error that broke symbol layers on Samsung S22 devices.
* Cache featureState for newly added source in case it is not available in renderer.
* Fix issue with model layer blending with geometry tile source.
* Fix incorrectly set begin timestamp for the `RenderFrameFinished` event.
* Hide methods and properties from Java in `MapView`, `MapboxMap` and `Snapshotter` that are not supposed to be public.
* Fix flood light not working by changing default EGL config to RGBA_8888.
* Fix a bug where the map would not zoom above a certain threshold on high-pitched views.
* Fix crashes if 3D layers are used alone on terrain or globe without any other layer types.
* Fix `LineLayer` leaking if placed behind the satellite layer.
* Fix line and raster layers interop for draped mode.
* Fix a crash when consecutive snapshot requests were made.
* Fix erroneous shadow map sampling of distant landmarks.
* Fix incorrect level-of-detail model being chosen for trees in some scenarios.
* Fix the style layer minimum and maximum zoom default values from infinity to actual ones.
* Fix widgets not showing on some zoom levels.
* Fix widgets flickering due to race condition if they are animated.
* Avoid listening for heading/course when location component puck bearing is disabled.
* Do not overwrite style URL when setting invalid style JSON.
* Do not store SDK version in TileStore.
* Fixed an issue where the camera padding is not calculated properly for `OverviewViewportState`.
* [compose] Fix the bug where wrong pixel ratio could be used in a dual display setup.
* [compose] Fix a lifecycle handling issue for `MapboxMap` which results in potential memory leak.
* Fix rendering of large/complex fill extrusion features.
* Fix a crash because of non-exported runtime-registered broadcasts receivers for apps targeting SDK 34.
* Fix a StackOverflow crash when `MultiGeometry` is used for `OverviewViewportStateOptions`.
* Fix flickering and wrong positions in elevated symbols.
* Update style layer's source should trigger repaint.
* Fix failure to load a 3D model when it uses a 32-bit index buffer.
* Fix rendering of the updated content on the map after a feature state change if terrain or globe were enabled.
* Fix a bug where snapshot is rendered without 3D content.

## Dependencies
* Update gl-native to v11.0.0 and common to v24.0.0.
* Update dependencies :

| Dependency                                       | Before       | After        |
|--------------------------------------------------|--------------|--------------|
| Android Gradle Plugin                            | 7.0.4        | 7.4.2        |
| Kotlin                                           | 1.5.31       | 1.7.20       |
| NDK                                              | 21.4.7075529 | 23.2.8568313 |
| EGL                                              | 1.0          | 1.4          |
| org.jetbrains.kotlin:kotlin-stdlib-jdk7          | 1.5.31       |              |
| org.jetbrains.kotlin:kotlin-stdlib-jdk8          |              | 1.7.20       |
| Dokka plugin                                     | 1.5.31       | 1.7.20       |
| androidx.core:core-ktx                           | 1.7.0        | 1.9.0        |
| androidx.appcompat:appcompat                     | 1.3.0        | 1.6.1        |
| androidx.test:rules                              | 1.4.0        | 1.5.0        |
| androidx.test:core                               | 1.4.0        | 1.5.0        |
| androidx.test:runner                             | 1.4.0        | 1.5.2        |
| androidx.test:orchestrator                       | 1.4.0        | 1.4.2        |
| androidx.test:monitor                            | 1.4.0        | 1.6.1        |
| androidx.test.espresso:espresso-core             | 3.4.0        | 3.5.1        |
| androidx.test.ext:junit                          | 1.1.3        | 1.1.5        |
| org.jetbrains.kotlinx:kotlinx-coroutines-core    | 1.3.9        | 1.6.1        |
| org.jetbrains.kotlinx:kotlinx-coroutines-test    | 1.3.9        | 1.6.1        |
| io.mockk:mockk                                   | 1.12.3       | 1.13.4       |
| io.mockk:mockk-agent-api                         | 1.12.3       | 1.13.4       |
| io.mockk:mockk-agent-jvm                         | 1.12.3       | 1.13.4       |
| org.robolectric:robolectric                      | 4.8.1        | 4.9.2        |
| com.android.tools.lint:lint-api                  | 30.0.4       | 30.4.2       |
| com.android.tools.lint:lint-checks               | 30.0.4       | 30.4.2       |
| com.android.tools.lint:lint                      | 30.0.4       | 30.4.2       |
| com.android.tools.lint:lint-tests                | 30.0.4       | 30.4.2       |
| com.android.tools:testutils                      | 30.0.4       | 30.4.2       |
| nl.jqno.equalsverifier:equalsverifier            | 3.10.1       | 3.14         |
| io.gitlab.arturbosch.detekt:detekt-formatting    | 1.20.0       | 1.22.0       |
| composeOptions -> kotlinCompilerExtensionVersion | 1.1.0-beta03 | 1.4.3        |
| androidx.compose:compose-bom                     |              | 2023.01.00   |
| androidx.compose.compiler:compiler               |              | 1.3.2        |
| com.pinterest:ktlint                             | 0.39.0       | 0.48.2       |


# 11.0.0-rc.2 November 17, 2023
## Breaking changes ⚠️
* Minimum OpenGL ES version is now 3.0

## Features ✨ and improvements 🏁
* Promote [Mapbox Standard](https://www.mapbox.com/blog/standard-core-style) style to stable.
* Introduce `ViewAnnotationOptions.allowOverlapWithPuck` and `ViewAnnotationOptions.ignoreCameraPadding` to configure the dynamic view annotation's behaviour.
* Avoid placing view annotation on overlapped line geometries, if the current bound layer is below other annotated line layers.

## Bug fixes 🐞
* [compose] Fix a lifecycle handling issue for `MapboxMap` which results in potential memory leak.
* Fix a StackOverflow crash when `MultiGeometry` is used for `OverviewViewportStateOptions`.
* Fix flickering and wrong positions in elevated symbols.
* Update style layer's source should trigger repaint.
* Fix failure to load a 3D model when it uses a 32-bit index buffer.
* Fix rendering of the updated content on the map after a feature state change if terrain or globe were enabled.
* Fix view annotation disappearing on the upper parts of the screen when terrain is enabled.
* Trigger view annotation replacement if the associated layer/source gets changed.
* Fix a bug where snapshot is rendered without 3D content.

## Dependencies
* Update gl-native to v11.0.0-rc.2 and common to v24.0.0-rc.3.

## Known issues
* When adding a view annotation with `ViewAnnotationOptions.allowOverlapWithPuck` or `ViewAnnotationOptions.ignoreCameraPadding` set to true, the same option for all the view annotations added previously will be overwritten to true. The fix will land in the next release.


# 11.0.0-rc.1 November 03, 2023

## Breaking changes ⚠️
* Add `LocationService.getDeviceLocationProvider` method that accepts `ExtendedLocationProviderParameters` to `LocationService` interface.

## Features ✨ and improvements 🏁
* From v11.0.0-rc.1, Mapbox Compose Extension for Android is released as a separate module following the same release cadence with the main SDK, please refer to [this guide](extension-compose/README.md) to get started with Jetpack Compose, and [v0.1.0 changelog](extension-compose/CHANGELOG-v0.1.0.md) for previous changelogs.
* [compose] Add experimental `MapEvents` to handle all events emitted by `MapboxMap`.
* [compose] Expose `PointAnnotationGroup.symbolZElevate` property.
* [compose] Add `ViewAnnotation.layoutParams` property.
* Introduce `ExtendedLocationProviderParameters` to specify `DeviceLocationProvider` parameters.
* Add `coordinatesPadding`, `maxZoom` and `offset` parameters to `OverviewViewportStateOptions` to allow more granular control of `OverviewViewportState`.
* Add `CircleAnnotationManager.slot`, `PointAnnotationManager.slot`, `PolygonAnnotationManager.slot`, `PolylineAnnotationManager.slot` to place the associated layer of the `AnnotationManager` to the correct position of the map.
* Expose experimental `CustomRasterSource` and non-experimental `CustomGeometrySource` as regular `Source`'s providing a better way to work with them and also allowing using them in Style DSL.
* Deprecate `CustomGeometrySource.invalidTile` and `CustomGeometrySource.invalidRegion`; `CustomGeometrySource.invalidateTile` and `CustomGeometrySource.invalidateRegion` should be used instead.
* Add `LocationPuck3D.modelEmissiveStrength` and `LocationPuck3D.modelEmissiveStrengthExpression` properties to LocationComponent plugin to control the strength of the light emission.

## Bug fixes 🐞
* Fixed an issue where the camera padding is not calculated properly for `OverviewViewportState`.
* [compose] Fix the bug where wrong pixel ratio could be used in a dual display setup.
* Fix the line label flickering issue by eliminating the anchor rounding error.
* Fix rendering of large/complex fill extrusion features.
* Fix a crash because of non-exported runtime-registered broadcasts receivers for apps targeting SDK 34.

## Dependencies
* Update gl-native to v11.0.0-rc.1 and common to v24.0.0-rc.2.


# 11.0.0-beta.6 October 24, 2023
## Breaking changes ⚠️
* Extension function `Style.getProjection()` return type changed from `Projection` to `Projection?`.
* Extension function `LocationComponentPlugin.createDefault2DPuck` in `LocationComponentUtils.kt` is now stand-alone `createDefault2DPuck`.
* Extension function `LocationComponentPlugin.createDefault2DPuck` in `LocationComponentUtils` is now stand-alone `createDefault2DPuck`.
* `createDefault2DPuck` does not require a `context` parameter.
* Increase minimum location puck bearing threshold needed to trigger an animation to 1 degree (previously 0.01 degrees) to reduce the CPU usage.
* Location component puck bearing enabled property (`MapView.location.puckBearingEanbled`) has been changed to `false` by default.
* `ViewAnnotationManager.getViewAnnotationByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotation`,
* `ViewAnnotationManager.getViewAnnotationByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotation`.
* `ViewAnnotationManager.getViewAnnotationOptionsByView` is renamed to `ViewAnnotationManager.getViewAnnotationOptions`.
* `ViewAnnotationManager.getViewAnnotationOptionsByFeatureId` is renamed to `ViewAnnotationManager.getViewAnnotationOptions`.
* `ViewAnnotationAnchorConfig` fields `offsetX`/`offsetY` are now of type Double instead of Int.
* `ViewAnnotationOptions` accepts list of anchors `variableAnchors` instead of `anchor`/`offsetX`/`offsetY`.
* `ViewAnnotationOptions` fields `width`/`height` are now of type Double instead of Int.
* `OnViewAnnotationUpdatedListener.onViewAnnotationPositionUpdated` arguments `width`/`height` are now of type Double instead of Int.
* Add `getName` method to `DeviceLocationProvider` interface.
* Add boolean parameter `allowUserDefined` to `getDeviceLocationProvider` method in `LocationService` interface.

## Features ✨ and improvements 🏁
* Add dynamic view annotations that can be attached to complex feature geometries and reposition itself based on the current camera viewport. To create dynamic view annotation use `AnnotatedFeature` of type `ANNOTATED_LAYER_FEATURE`. Multiple dynamic view annotations can be attached to the same feature.
* The following APIs have been promoted to stable:
  - `LineLayer.lineDepthOcclusionFactor`, `LineLayer.lineDepthOcclusionFactorTransition`, `LineLayer.lineEmissiveStrength` and `LineLayer.lineEmissiveStrengthTransition`
  - `SymbolLayer.iconImageCrossFade`, `SymbolLayer.iconImageCrossFadeTransition`, `SymbolLayer.iconEmissiveStrength`, `SymbolLayer.iconEmissiveStrengthTransition`, `SymbolLayer.textEmissiveStrength` and `SymbolLayer.textEmissiveStrengthTransition`
  - `BackgroundLayer.backgroundEmissiveStrength` and `BackgroundLayer.backgroundEmissiveStrengthTransition`
  - `CircleLayer.circleEmissiveStrength` and `CircleLayer.circleEmissiveStrengthTransition`
  - `FillLayer.fillEmissiveStrength` and `FillLayer.fillEmissiveStrengthTransition`
  - `FlatLight`, `AmbientLight`, `DirectionalLight` and related APIs
  - `slot` for all the layers
  - Style Import APIs: `getStyleImports`, `removeStyleImport`, `getStyleImportSchema`, `getStyleImportConfigProperties`, `getStyleImportConfigProperty`, `setStyleImportConfigProperties`, `setStyleImportConfigProperty`
  - Annotation plugin APIs: `CircleAnnotationManager.circleEmissiveStrength`, `PolygonAnnotationManager.fillEmissiveStrength`, `PolylineAnnotationManager.lineDepthOcclusionFactor`, `PolylineAnnotationManager.lineEmissiveStrength`
  - Map Overlay plugin and related APIs
* (Kotlin only) Deprecated `MapboxMap.getStyle()` function. Please use property `MapboxMap.style`.
* (Kotlin only) Deprecated `MapView.getMapboxMap()` function. Please use property `MapView.mapboxMap`.
* (Kotlin only) Deprecated `MapSurface.getMapboxMap()` function. Please use property `MapSurface.mapboxMap`.
* Handle zero duration map camera animators more efficiently resulting in performance improvements for gestures / viewport / locationcomponent.
* `DefaultLocationProvider.updatePuckBearing` now accepts null to stop listening for heading/course.
* Add `keep-legacy-style-pack` style pack load extra option that prevents the style package removal from the legacy storage.
* Enable rendering of fill extrusion flood lights on the ground with fully transparent fill extrusions.
* Add `cameraForCoordinates` overload so that the padding for map and geometry can be specified separately.
* Disable terrain when zoom-dependent exaggeration expression evaluates to zero.
* Add support for `glb` 3D tiles.
* Align hillshade illumination direction with 3D lights.

## Bug fixes 🐞
* Fix widgets not showing on some zoom levels.
* Fix widgets flickering due to race condition if they are animated.
* Fix crash when setting `mapbox:mapbox_locationComponentLocationPuck = "location_puck_2_d"` in map view XML without specifying all images.
* Fix changing location bearing from `HEADING` to `COURSE` not working.
* Avoid listening for heading/course when location component puck bearing is disabled.
* Fix the crash clicking on attribution when not using the `AppCompat` theme.
* Do not overwrite style URL when setting invalid style JSON.
* Do not store SDK version in TileStore.

## Dependencies
* Update gl-native to v11.0.0-beta.7 and common to v24.0.0-beta.7.



# 11.0.0-beta.5 October 09, 2023
## Breaking changes ⚠️
* Rename `MapCameraManagerDelegate` properties for methods `cameraForCoordinateBounds`, `cameraForCoordinates` and `cameraForGeometry` to align them with `MapboxMap` methods.
* Consolidate `FetchTileFunctionCallback` and `CancelTileFunctionCallback` by single type `TileFunctionCallback`.
* Make `Image` parameter nullable in `setStyleCustomRasterSourceTileData()` method.

## Features ✨ and improvements 🏁
* Add `GeoJsonSource.data(..)` overloads to allow Java to call with and without `dataId`.
* Add a new layer type `CustomLayer` and style DSL to create it. `CustomLayer` allows to use custom OpenGL ES rendering through `CustomLayerHost`. `CustomLayer` contains the `slot` property allowing using it with the Standard style.
* Improve the caching model for the custom raster source.
* Optimize custom raster source data update.
* Increase rendering performance of shadows.
* Print warning log message for unknown style layer properties instead of causing fatal errors.
* Optimise memory usage in the `FillExtrusionLayer`.
* Improve the rendering performance of a `SymbolLayer` that uses `SymbolLayer.symbolSortKey` property.
* Reduce memory usage in fill-extrusion flood light and ground ambient occlusion.

## Bug fixes 🐞
* Hide methods and properties from Java in `MapView`, `MapboxMap` and `Snapshotter` that are not supposed to be public.
* Fix `Style` docs for methods `setStyleImportConfigProperties`, `setStyleImportConfigProperty`, `removeGeoJSONSourceFeatures`.
* Fix flood light not working by changing default EGL config to RGBA_8888.
* Fix a bug where the map would not zoom above a certain threshold on high-pitched views.
* Fix crashes if 3D layers are used alone on terrain or globe without any other layer types.
* Fix `LineLayer` leaking if placed behind the satellite layer.
* Fix line and raster layers interop for draped mode.
* Fix a crash when consecutive snapshot requests were made.
* Fix erroneous shadow map sampling of distant landmarks.
* Fix incorrect level-of-detail model being chosen for trees in some scenarios.
* Fix the style layer minimum and maximum zoom default values from infinity to actual ones.

## Dependencies
* Update gl-native to v11.0.0-beta.6 and common to v24.0.0-beta.6.


# 11.0.0-beta.4 September 21, 2023
## Breaking changes ⚠️
* Remove setter functions for `Style.styleURI` and `Style.styleJSON` as loading the style should happen only with `MapboxMap.loadStyle`.
* Rename `StyleImageMissing.getStyleImageMissingEventData` to `StyleImageMissing.toStyleImageMissingEventData`.

## Features ✨ and improvements 🏁
* Introduce experimental APIs to work with Custom Raster Sources: `MapboxMap.addCustomRasterSource`, `MapboxMap.setCustomRasterSourceTileData`, `MapboxMap.invalidateCustomRasterSourceTile`, `MapboxMap.invalidateCustomRasterSourceRegion`.
* Copy relevant `Style` methods to `MapboxMap` providing better alignment with iOS and GL-JS.
* Add `FillExtrusionLayer.fillExtrusionCutoffFadeRange` and `ModelLayer.modelCutoffFadeRange` to control fade out of the layers when pitch is used.
* Improve atmosphere rendering performance.
* Improve fill layer and model layer rendering performance on high pitch views.
* Improve GPU performance by drawing opaque 3D geometry without blending.
* Introduce experimental `SymbolLayer.symbolZElevate` property to elevate symbols along with the buildings that are in the same place. The feature is supported for symbols with point placement only.
* Add `Atmosphere.verticalRange` property.

## Bug fixes 🐞
* Free resources immediately after map event subscription is canceled.
* Fix map event listeners not being removed when `MapboxMap.remove*Listener` is called.
* Fix issue where the terrain didn't work if defined within a style fragment.
* The Custom Geometry Source `fetchTileFunction` and `cancelTileFunction` callbacks are invoked on the client thread.
* Fixes a rendering error that broke symbol layers on Samsung S22 devices.
* Cache featureState for newly added source in case it is not available in renderer.
* Fix issue with model layer blending with geometry tile source.
* Fix incorrectly set begin timestamp for the RenderFrameFinished event.

## Dependencies
* Update gl-native to v11.0.0-beta.5 and common to v24.0.0-beta.5.



# 11.0.0-beta.3 September 08, 2023
## Breaking changes ⚠️
* The `MapCameraPlugin`'s `onCameraMove` method now uses `Point` for camera center and `EdgeInsets` for padding.
* Remove `MapInitOptions.optimizeForTerrain` as whenever terrain is present layer order is automatically adjusted for better performance.
* Replace `MapboxMap` and `MapCameraManagerDelegate` APIs `dragStart`, `dragEnd`, `getDragCameraOptions` with `cameraForDrag`, `setCenterAltitudeMode`.

## Features ✨ and improvements 🏁
* Avoid creating unnecessary objects during animation under some conditions.
* Improve map camera and gestures when terrain is used to fix camera bumpiness and map flickering.
* Use a fallback glyph URL if a style does not define one. This enables the addition of Symbol layers to an empty style or to the style that doesn't use Symbol layers.
* Use ETC2 compression for raster tiles to support transparency.

## Bug fixes 🐞
* Fix scale bar receives camera changes after being disabled the first time.
* Fix camera flying away on pitch gesture after some other animations.
* Fix map not being rendered when EGL exception happens but Android surface is still valid.
* Fix fallback rules for the style's transition property. The change fixes style transitions when style imports are modified for the Standard style.
* Fix terrain rendering for the Terrarium-encoded tiles.
* Cancel pending style url loading request when loading a new style json.
* Fixes an issue that causes view annotations to be placed on the sky when high pitch and mercator projection is used.

## Dependencies
* Update gl-native to v11.0.0-beta.4 and common to v24.0.0-beta.4.


# 11.0.0-beta.2 August 24, 2023
## Breaking changes ⚠️
* Replace style related enum classes with regular classes.
* Migrate from `android.app.AlertDialog` to `androidx.appcompat.app.AlertDialog` for attribution plugin.
* Rename `MapboxMap.subscribeStyleImageUnused` to `MapboxMap.subscribeStyleImageRemoveUnused`.

## Features ✨ and improvements 🏁
* Introduce experimental `MapboxMapRecorder` allowing to record and replay custom scenarios.
* New compose example `MulitDisplayActiviy` ported from XML test app.
* Add Mapbox Privacy Policy to attribution links.

## Bug fixes 🐞
* Fix the bug when anchor was not reset after gestures leading to an unexpected map camera animation result with incorrect `CameraState.center`.
* Fix a rounding error when point lies at the edge of the screen by using `rountToInt` for limiting `MapboxMap.pixelsForCoordinates` to the bounds of MapView.
* Fix the bug when `MapboxMap.getStyle` returned NULL if `MapboxMap.subscribeStyleLoaded` called before `MapboxMap.loadStyle`.
* Fix NPE when animating edge insets types (e.g. map padding).
* Reduce segment overlap in flood lighting to improve rendering performance.
* Enable offline support for the Standard style.
* Add wireframe rendering debug feature `MapDebugOptions.LAYERS3_DWIREFRAME` and `MapDebugOptions.LAYERS2_DWIREFRAME`.

## Dependencies
Update dependencies:

| Dependency | Before        | After         |
|------------|---------------|---------------|
| NDK        | 21.4.7075529  | 23.2.8568313  |
| gl-native  | 11.0.0-beta.2 | 11.0.0-beta.3 |
| common     | 24.0.0-beta.2 | 24.0.0-beta.3 |


# 11.0.0-beta.1 August 2, 2023
## Breaking changes ⚠️
* Remove deprecated `GeoJsonSource` public constructor, builder should be used instead.
* Remove deprecated `MapboxMap.queryRenderedFeatures` methods.
* Remove `Snapshotter.setTileMode`, `Snapshotter.isInTileMode` methods.
* Remove deprecated `MapStyleStateDelegate` and `isFullyLoaded` method.
* Remove experimental `setRenderCacheOptions`, `getRenderCacheOptions` apis.
* Update SDK's `targetSdkVersion` and `compileSdkVersion` to 33.
* Add `callback` argument to the `MapboxMap` methods `getFeatureState`, `setFeatureState`, `removeFeatureState`.
* Use different callback types for the `MapboxMap.queryRenderedFeatures` and the `MapboxMap.querySourceFeatures` methods.
* Return `cancelable` from the `MapboxMap` methods : `getFeatureState`, `setFeatureState`, `removeFeatureState`, `querySourceFeatures`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Remove the deprecated `MapboxMap.queryFeatureExtensions` method.
* Remove `MapAnimationOptions.animatorListener` property. In order to subscribe to animations, provide `Animator.animatorListener` with `flyTo`, `easeTo`, `pitchBy`, `scaleBy`, `moveBy`, `rotateBy` apis.
* Replace `LocationEngine` use with `LocationService` in `DefaultProvider`.
* Add new `LocationConsumer.onError` method to allow consumers handle location errors.
* Remove `MapView#location2` related interfaces and move `showAccuracyRing`, `accuracyRingColor`, `accuracyRingBorderColor`, `puckBearingEnabled` and `puckBearingSource` to `MapView#location`.
* Make `AttributionSettings`, `CompassSettings`, `GesturesSettings`, `LocationComponentSettings`, `LogoSettings`, `ScaleBarSettings` not Kotlin `data class`, better binary compatible and implementing `Parcelable`.
* Change `CompassSettings#image`, `LocationPuck2D#topImage`, `LocationPuck2D#bearingImage`, `LocationPuck2D#shadowImage` to `ImageHolder` allowing to pass either drawable id or `Bitmap`.
* Remove deprecated `backgroundPatternTransition`, `lineDasharrayTransition`, `linePatternTransition`, `fillPatternTransition` properties.
* Replace `MapSnapshotInterface` interface with `MapSnapshotResult` abstract class and remove `image()` method, `bitmap()` should be used instead.
* Change `Annotation.id` from monotonically increasing `Long` to UUID represented as `String`.
* Remove `Annotation.featureIdentifier` used to connect with View Annotations, now `Annotation.id` should be used instead.
* Rename `PuckBearingSource` to `PuckBearing` in location component plugin.
* Remove deprecated overloaded `Style.setStyleGeoJSONSourceData(sourceId: String, data: GeoJSONSourceData)` method.
* Rename `MapboxMap.setMemoryBudget` to `MapboxMap.setTileCacheBudget` and make it non-experimental.
* Update Mapbox styles to latest versions.
* Remove `ResourceOptions` and `ResourceOptionsManager`. Introduce `MapboxOptions` and `MapboxMapsOptions` to handle application-level access token and other generic options.
* Removed XML attributes `mapbox_resourcesAccessToken` and `mapbox_resourcesBaseUrl`.

| Style             | Before                                       | After                                        |
|-------------------|----------------------------------------------|----------------------------------------------|
| MAPBOX_STREETS    | mapbox://styles/mapbox/streets-v11           | mapbox://styles/mapbox/streets-v12           |
| SATELLITE_STREETS | mapbox://styles/mapbox/satellite-streets-v11 | mapbox://styles/mapbox/satellite-streets-v12 |
| OUTDOORS          | mapbox://styles/mapbox/outdoors-v11          | mapbox://styles/mapbox/outdoors-v12          |
| LIGHT             | mapbox://styles/mapbox/light-v10             | mapbox://styles/mapbox/light-v11             |
| DARK              | mapbox://styles/mapbox/dark-v10              | mapbox://styles/mapbox/dark-v11              |


* Remove native interfaces `StyleManagerInterface`, `StyleInterface`, `CameraManagerInterface`, `MapInterface`, `ObservableInterface` and use only `Map` object to access native methods.
* Make map events typed-safe, events are now have their own subscription methods.
  Following events are added as typed-safe, `CameraChanged`, `MapIdle`, `MapLoadingError`, `MapLoaded`, `StyleDataLoaded`, `StyleLoaded`, `StyleImageMissing`, `StyleImageRemovedUnunsed`,
  `RenderFrameStarted`, `RenderFrameFinished`, `SourceAdded`, `SourceDataLoaded`, `SourceRemoved`, `ReourceRequest`.
  All `subscribe` methods return `Cancelable` object, which users could store and call `cancel` when subscription is no longer needed.
  `MapboxMap.unsubscribe` methods were removed.
* Rename `LocationConsumer.onAccuracyRadiusUpdated` to `onHorizontalAccuracyRadiusUpdated`.
* Deprecate `MapboxMap.loadStyleUri`, `MapboxMap.loadStyleJson` and `MapboxMap.loadStyle` methods and introduce one `MapboxMap.loadStyle` accepting either URI / JSON or Style DSL block.
* Replace `com.mapbox.maps.plugin.animation.Cancelable` with `com.mapbox.common.Cancelable`.
* Remove `TileStoreOptions.MAPBOX_ACCESS_TOKEN` used e.g. in `TileStore.setOption(TileStoreOptions.MAPBOX_ACCESS_TOKEN, someDomain, someValue)` as it has become redundant.
* Introduce `MapboxTracing` object allowing to enable Android traces to measure performance of the SDK. More details and instructions could be found in `Working with traces` section in `DEVELOPING.md`.
* Remove Mapbox plugin implementations from public API surface.
* Remove `HttpServiceFactory.getInstance`, `HttpServiceFactory.reset`, `HttpServiceFactory.setUserDefined` methods.
* Replace `HttpServiceFactory.getInstance().setInterceptor` with `HttpServiceFactory.setHttpServiceInterceptor`.
* Argument `dataId` of the `GeoJson.feature`, `GeoJson.featureCollection`, `GeoJson.geometry`, `GeoJson.url`, `GeoJson.data` became non-nullable.
* Remove `Style.getStyleSourcesAttribution`. `MapboxMap.getAttributions` should be used instead.

## Features ✨ and improvements 🏁
* Add clustering support for CircleAnnotationManager.
* Update Kotlin version to v1.7.20 and compose compiler version to 1.3.2.
* Improve ergonomics of `Snapshotter.start` method to align with iOS and allow to re-use `Canvas` for user drawing.
* Add `MapboxMap.coordinateBoundsForRect` returning `CoordinateBounds` for given `RectF` of screen coordinates.
* Add optional `maxZoom` and `offset` parameters to `MapboxMap.cameraForCoordinateBounds`.
* Mark `GeoJsonSource.url` / `GeoJsonSource.Builder.url` methods as deprecated, `GeoJsonSource.data` / `GeoJsonSource.Builder.data` should be used instead.
* Refactor style Light APIs: introduce `AmbientLight`, `DirectionalLight`, `FlatLight` and methods to set them to style.
* Expose new APIs to import and configure styles: `getStyleImports`, `removeStyleImport`, `getStyleImportSchema`, `getStyleImportConfigProperties`, `setStyleImportConfigProperties`, `getStyleImportConfigProperty`, `setStyleImportConfigProperty`
* Expose `slot` property for all `Layer`s to link layers from imported styles.
* Add expression support for visibility layer property.
* Add the `MapboxMap.resetFeatureState` method.
* Make padding optional for `MapboxMap.cameraForCoordinateBounds`, `MapboxMap.cameraForCoordinates`, `MapboxMap.cameraForGeometry` methods.
* Add `FreeCameraOptions.getLocation` and `FreeCameraOptions.getAltitude` methods.
* Add `MapboxMap.coordinatesForRect(rectF: RectF)` to support rectangle parameters.
* Add `suspend` variants for the async `MapboxMap` functions : `queryRenderedFeatures`, `querySourceFeatures`, `setFeatureState`, `getFeatureState`, `removeFeatureState`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Add `MapboxMap.mapLoadedEvents`, `MapboxMap.mapLoadingErrorEvents`, `MapboxMap.styleLoadedEvents`, `MapboxMap.styleDataLoadedEvents`, `MapboxMap.cameraChangedEvents`, `MapboxMap.mapIdleEvents`, `MapboxMap.sourceAddedEvents`, `MapboxMap.sourceRemovedEvents`, `MapboxMap.sourceDataLoadedEvents`, `MapboxMap.styleImageMissingEvents`, `MapboxMap.styleImageRemoveUnusedEvents`, `MapboxMap.renderFrameStartedEvents`, `MapboxMap.renderFrameFinishedEvents`, `MapboxMap.resourceRequestEvents` returning Flow of events.
* Introduce custom lint rules to check illegal usage of literals in Expression DSL and suggest auto fix.
* Introduce custom lint rules to check illegal number of arguments within given Expression DSL.
* Introduce custom lint rules to check unused layer/source/light/terrain/atmosphere/projection objects in the Style DSL, and suggest auto fix to add it to the style using unaryPlus(+) operator.
* Improve performance for `Snapshotter` when obtaining the bitmap.
* Add `ImageSource.updateImage(Bitmap)` method.
* Introduce Expression overload functions `linearInterpolator`, `exponentialInterpolator`, `cubicBezierInterpolator`, `step`, `match` and `switchCase` to construct these expressions with strongly typed parameters.
* Introduce `ImageExtensionImpl.Builder(imageId, image)`, `ImageExtensionImpl.Builder(imageId, image)` constructors and deprecated `ImageExtensionImpl.Builder(imageId)`, `ImageExtensionImpl.Builder.image(image)`, `ImageExtensionImpl.Builder.bitmap(bitmap)`, as image/bitmap is required for `ImageExtensionImpl.Builder`; DSL functions are updated to reflect these changes as well.
* Deprecate `PointAnnotationManager.iconTextFit` and `PointAnnotationManager.iconTextFitPadding` in favor of `PointAnnotation.iconTextFit` and `PointAnnotation.iconTextFitPadding`.
* Introduce experimental lights API to enable a uniform 3D lighting across the map. Use `Style.addLights3D` or `Style.setup3DLights` to enable `Ambient` and `Directional` light.
* Introduce `LineLayer.lineDepthOcclusionFactor`, `PolylineAnnotationManager.lineDepthOcclusionFactor` API.
* Introduce experimental `Expression.activeAnchor` API.
* Introduce experimental `ModelLayer` API to render 3D models on the map.
* Introduce experimental `MapboxMap.addStyleModel`, `MapboxMap.removeStyleModel`, `MapboxMap.hasStyleModel` APIs.
* Introduce experimental `LocationPuck3D.modelCastShadows`, `LocationPuck3D.modelReceiveShadows`, `LocationPuck3D.modelScaleMode` APIs.
  Note: `LocationPuck3D.modelScaleMode` API brings behavioral changes for LocationPuck3d scale and `model-scale` property needs to be adjusted to correctly render the puck.
* Introduce `Expression.hsl`, `Expression.hsla` color expression.
* Introduce `Expression.measureLight` lights configuration property.
* Introduce `LineLayer.lineBorderColor`, `LineLayer.lineBorderWidth` APIs.
* Introduce experimental `BackgroundLayer.backgroundEmissiveStrength`, `CircleLayer.circleEmissiveStrength`, `FillLayer.fillEmissiveStrength`, `LineLayer.lineEmissiveStrength`, `SymbolLayer.iconEmissiveStrength`, `SymbolLayer.textEmissiveStrength` APIs.
* Introduce experimental `FillExtrusionLayer.fillExtrusionRoundedRoof`, `FillExtrusionLayer.fillExtrusionEdgeRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionWallRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundAttenuation`, `FillExtrusionLayer.fillExtrusionFloodLightColor`, `FillExtrusionLayer.fillExtrusionFloodLightIntensity`, `FillExtrusionLayer.fillExtrusionFloodLightWallRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundAttenuation`, `FillExtrusionLayer.fillExtrusionVerticalScale` APIs.
* Introduce new Mapbox 3D style `Style.STANDARD` and make it default.
* Introduce GeoJSONSource partial update APIs `GeoJsonSource.addGeoJSONSourceFeatures`, `GeoJsonSource.updateGeoJSONSourceFeatures`, `GeoJsonSource.removeGeoJSONSourceFeatures`.
* Introduce raster colorization via `raster-color` expression and `RasterLayer.rasterColor`, `RasterLayer.rasterColorMix`, `RasterLayer.rasterColorRange` layer properties.

## Dependencies
* Update gl-native to v11.0.0-beta.2 and common to v24.0.0-beta.2.
* Instantiate OpenGL ES context 3.0, if available, otherwise fallback to 2.0.
* Use EGL 1.4 instead of EGL 1.0.
* Update dependencies :

| Dependency                                       | Before       | After      |
|--------------------------------------------------|--------------|------------|
| Android Gradle Plugin                            | 7.0.4        | 7.4.2      |
| Kotlin                                           | 1.5.31       | 1.7.20     |
| org.jetbrains.kotlin:kotlin-stdlib-jdk7          | 1.5.31       |            |
| org.jetbrains.kotlin:kotlin-stdlib-jdk8          |              | 1.7.20     |
| Dokka plugin                                     | 1.5.31       | 1.7.20     |
| androidx.core:core-ktx                           | 1.7.0        | 1.9.0      |
| androidx.appcompat:appcompat                     | 1.3.0        | 1.6.1      |
| androidx.test:rules                              | 1.4.0        | 1.5.0      |
| androidx.test:core                               | 1.4.0        | 1.5.0      |
| androidx.test:runner                             | 1.4.0        | 1.5.2      |
| androidx.test:orchestrator                       | 1.4.0        | 1.4.2      |
| androidx.test:monitor                            | 1.4.0        | 1.6.1      |
| androidx.test.espresso:espresso-core             | 3.4.0        | 3.5.1      |
| androidx.test.ext:junit                          | 1.1.3        | 1.1.5      |
| org.jetbrains.kotlinx:kotlinx-coroutines-core    | 1.3.9        | 1.6.1      |
| org.jetbrains.kotlinx:kotlinx-coroutines-test    | 1.3.9        | 1.6.1      |
| io.mockk:mockk                                   | 1.12.3       | 1.13.4     |
| io.mockk:mockk-agent-api                         | 1.12.3       | 1.13.4     |
| io.mockk:mockk-agent-jvm                         | 1.12.3       | 1.13.4     |
| org.robolectric:robolectric                      | 4.8.1        | 4.9.2      |
| com.android.tools.lint:lint-api                  | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint-checks               | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint                      | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint-tests                | 30.0.4       | 30.4.2     |
| com.android.tools:testutils                      | 30.0.4       | 30.4.2     |
| nl.jqno.equalsverifier:equalsverifier            | 3.10.1       | 3.14       |
| io.gitlab.arturbosch.detekt:detekt-formatting    | 1.20.0       | 1.22.0     |
| composeOptions -> kotlinCompilerExtensionVersion | 1.1.0-beta03 | 1.4.3      |
| androidx.compose:compose-bom                     |              | 2023.01.00 |
| androidx.compose.compiler:compiler               |              | 1.3.2      |
| com.pinterest:ktlint                             | 0.39.0       | 0.48.2     |

# 11.0.0-alpha.2 June 27, 2023
## Breaking changes ⚠️
* Remove deprecated `MapboxMap.queryRenderedFeatures` methods.
* Remove `Snapshotter.setTileMode`, `Snapshotter.isInTileMode` methods.
* Remove deprecated `MapStyleStateDelegate` and `isFullyLoaded` method.
* Remove experimental `setRenderCacheOptions`, `getRenderCacheOptions` apis.
* Update SDK's `targetSdkVersion` and `compileSdkVersion` to 33.
* Add `callback` argument to the `MapboxMap` methods `getFeatureState`, `setFeatureState`, `removeFeatureState`.
* Use different callback types for the `MapboxMap.queryRenderedFeatures` and the `MapboxMap.querySourceFeatures` methods.
* Return `cancelable` from the `MapboxMap` methods : `getFeatureState`, `setFeatureState`, `removeFeatureState`, `querySourceFeatures`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Remove the deprecated `MapboxMap.queryFeatureExtensions` method.
* Remove `MapAnimationOptions.animatorListener` property. In order to subscribe to animations, provide `Animator.animatorListener` with `flyTo`, `easeTo`, `pitchBy`, `scaleBy`, `moveBy`, `rotateBy` apis.
* Replace `LocationEngine` use with `LocationService` in `DefaultProvider`.
* Add new `LocationConsumer.onError` method to allow consumers handle location errors.
* Remove `MapView#location2` related interfaces and move `showAccuracyRing`, `accuracyRingColor`, `accuracyRingBorderColor`, `puckBearingEnabled` and `puckBearingSource` to `MapView#location`.
* Make `AttributionSettings`, `CompassSettings`, `GesturesSettings`, `LocationComponentSettings`, `LogoSettings`, `ScaleBarSettings` not Kotlin `data class`, better binary compatible and implementing `Parcelable`.
* Change `CompassSettings#image`, `LocationPuck2D#topImage`, `LocationPuck2D#bearingImage`, `LocationPuck2D#shadowImage` to `ImageHolder` allowing to pass either drawable id or `Bitmap`.
* Remove deprecated `backgroundPatternTransition`, `lineDasharrayTransition`, `linePatternTransition`, `fillPatternTransition` properties.
* Replace `MapSnapshotInterface` interface with `MapSnapshotResult` abstract class and remove `image()` method, `bitmap()` should be used instead.
* Change `Annotation.id` from monotonically increasing `Long` to UUID represented as `String`.
* Remove `Annotation.featureIdentifier` used to connect with View Annotations, now `Annotation.id` should be used instead.
* Rename `PuckBearingSource` to `PuckBearing` in location component plugin.
* Remove deprecated overloaded `Style.setStyleGeoJSONSourceData(sourceId: String, data: GeoJSONSourceData)` method.
* Rename `MapboxMap.setMemoryBudget` to `MapboxMap.setTileCacheBudget` and make it non-experimental.
* Update Mapbox styles to latest versions.
* Remove `ResourceOptions` and `ResourceOptionsManager`. Introduce `MapboxOptions` and `MapboxMapsOptions` to handle application-level access token and other generic options.
* Removed XML attributes `mapbox_resourcesAccessToken` and `mapbox_resourcesBaseUrl`.

| Style             | Before                                       | After                                        |
|-------------------|----------------------------------------------|----------------------------------------------|
| MAPBOX_STREETS    | mapbox://styles/mapbox/streets-v11           | mapbox://styles/mapbox/streets-v12           |
| SATELLITE_STREETS | mapbox://styles/mapbox/satellite-streets-v11 | mapbox://styles/mapbox/satellite-streets-v12 |
| OUTDOORS          | mapbox://styles/mapbox/outdoors-v11          | mapbox://styles/mapbox/outdoors-v12          |
| LIGHT             | mapbox://styles/mapbox/light-v10             | mapbox://styles/mapbox/light-v11             |
| DARK              | mapbox://styles/mapbox/dark-v10              | mapbox://styles/mapbox/dark-v11              |


* Remove native interfaces `StyleManagerInterface`, `StyleInterface`, `CameraManagerInterface`, `MapInterface`, `ObservableInterface` and use only `Map` object to access native methods.
* Make map events typed-safe, events are now have their own subscription methods.
  Following events are added as typed-safe, `CameraChanged`, `MapIdle`, `MapLoadingError`, `MapLoaded`, `StyleDataLoaded`, `StyleLoaded`, `StyleImageMissing`, `StyleImageRemovedUnunsed`,
  `RenderFrameStarted`, `RenderFrameFinished`, `SourceAdded`, `SourceDataLoaded`, `SourceRemoved`, `ReourceRequest`.
  All `subscribe` methods return `Cancelable` object, which users could store and call `cancel` when subscription is no longer needed.
  `MapboxMap.unsubscribe` methods were removed.
* Rename `LocationConsumer.onAccuracyRadiusUpdated` to `onHorizontalAccuracyRadiusUpdated`.
* Deprecate `MapboxMap.loadStyleUri`, `MapboxMap.loadStyleJson` and `MapboxMap.loadStyle` methods and introduce one `MapboxMap.loadStyle` accepting either URI / JSON or Style DSL block.
* Replace `com.mapbox.maps.plugin.animation.Cancelable` with `com.mapbox.common.Cancelable`.
* Remove `TileStoreOptions.MAPBOX_ACCESS_TOKEN` used e.g. in `TileStore.setOption(TileStoreOptions.MAPBOX_ACCESS_TOKEN, someDomain, someValue)` as it has become redundant.
* Introduce `MapboxTracing` object allowing to enable Android traces to measure performance of the SDK. More details and instructions could be found in `Working with traces` section in `DEVELOPING.md`.
* Remove Mapbox plugin implementations from public API surface.
* Remove `HttpServiceFactory.getInstance`, `HttpServiceFactory.reset`, `HttpServiceFactory.setUserDefined` methods.
* Replace `HttpServiceFactory.getInstance().setInterceptor` with `HttpServiceFactory.setHttpServiceInterceptor`.
* Argument `dataId` of the `GeoJson.feature`, `GeoJson.featureCollection`, `GeoJson.geometry`, `GeoJson.url`, `GeoJson.data` became non-nullable.

## Features ✨ and improvements 🏁
* Add the `MapboxMap.resetFeatureState` method.
* Make padding optional for `MapboxMap.cameraForCoordinateBounds`, `MapboxMap.cameraForCoordinates`, `MapboxMap.cameraForGeometry` methods.
* Add `FreeCameraOptions.getLocation` and `FreeCameraOptions.getAltitude` methods.
* Add `MapboxMap.coordinatesForRect(rectF: RectF)` to support rectangle parameters.
* Add `suspend` variants for the async `MapboxMap` functions : `queryRenderedFeatures`, `querySourceFeatures`, `setFeatureState`, `getFeatureState`, `removeFeatureState`, `getGeoJsonClusterLeaves`, `getGeoJsonClusterChildren`, `getGeoJsonClusterExpansionZoom`.
* Add `MapboxMap.mapLoadedEvents`, `MapboxMap.mapLoadingErrorEvents`, `MapboxMap.styleLoadedEvents`, `MapboxMap.styleDataLoadedEvents`, `MapboxMap.cameraChangedEvents`, `MapboxMap.mapIdleEvents`, `MapboxMap.sourceAddedEvents`, `MapboxMap.sourceRemovedEvents`, `MapboxMap.sourceDataLoadedEvents`, `MapboxMap.styleImageMissingEvents`, `MapboxMap.styleImageRemoveUnusedEvents`, `MapboxMap.renderFrameStartedEvents`, `MapboxMap.renderFrameFinishedEvents`, `MapboxMap.resourceRequestEvents` returning Flow of events.
* Introduce custom lint rules to check illegal usage of literals in Expression DSL and suggest auto fix.
* Introduce custom lint rules to check illegal number of arguments within given Expression DSL.
* Introduce custom lint rules to check unused layer/source/light/terrain/atmosphere/projection objects in the Style DSL, and suggest auto fix to add it to the style using unaryPlus(+) operator.
* Improve performance for `Snapshotter` when obtaining the bitmap.
* Add `ImageSource.updateImage(Bitmap)` method.
* Introduce Expression overload functions `linearInterpolator`, `exponentialInterpolator`, `cubicBezierInterpolator`, `step`, `match` and `switchCase` to construct these expressions with strongly typed parameters.
* Introduce `ImageExtensionImpl.Builder(imageId, image)`, `ImageExtensionImpl.Builder(imageId, image)` constructors and deprecated `ImageExtensionImpl.Builder(imageId)`, `ImageExtensionImpl.Builder.image(image)`, `ImageExtensionImpl.Builder.bitmap(bitmap)`, as image/bitmap is required for `ImageExtensionImpl.Builder`; DSL functions are updated to reflect these changes as well.
* Deprecate `PointAnnotationManager.iconTextFit` and `PointAnnotationManager.iconTextFitPadding` in favor of `PointAnnotation.iconTextFit` and `PointAnnotation.iconTextFitPadding`.
* Introduce experimental lights API to enable a uniform 3D lighting across the map. Use `Style.addLights3D` or `Style.setup3DLights` to enable `Ambient` and `Directional` light.
* Introduce `LineLayer.lineDepthOcclusionFactor`, `PolylineAnnotationManager.lineDepthOcclusionFactor` API.
* Introduce experimental `Expression.activeAnchor` API.
* Introduce experimental `ModelLayer` API to render 3D models on the map.
* Introduce experimental `MapboxMap.addStyleModel`, `MapboxMap.removeStyleModel`, `MapboxMap.hasStyleModel` APIs.
* Introduce experimental `LocationPuck3D.modelCastShadows`, `LocationPuck3D.modelReceiveShadows`, `LocationPuck3D.modelScaleMode` APIs.
  Note: `LocationPuck3D.modelScaleMode` API brings behavioral changes for LocationPuck3d scale and `model-scale` property needs to be adjusted to correctly render the puck.
* Introduce `Expression.hsl`, `Expression.hsla` color expression.
* Introduce `Expression.measureLight` lights configuration property.
* Introduce `LineLayer.lineBorderColor`, `LineLayer.lineBorderWidth` APIs.
* Introduce experimental `BackgroundLayer.backgroundEmissiveStrength`, `CircleLayer.circleEmissiveStrength`, `FillLayer.fillEmissiveStrength`, `LineLayer.lineEmissiveStrength`, `SymbolLayer.iconEmissiveStrength`, `SymbolLayer.textEmissiveStrength` APIs.
* Introduce experimental `FillExtrusionLayer.fillExtrusionRoundedRoof`, `FillExtrusionLayer.fillExtrusionEdgeRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionWallRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundRadius`, `FillExtrusionLayer.fillExtrusionAmbientOcclusionGroundAttenuation`, `FillExtrusionLayer.fillExtrusionFloodLightColor`, `FillExtrusionLayer.fillExtrusionFloodLightIntensity`, `FillExtrusionLayer.fillExtrusionFloodLightWallRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundRadius`, `FillExtrusionLayer.fillExtrusionFloodLightGroundAttenuation`, `FillExtrusionLayer.fillExtrusionVerticalScale` APIs.
* Introduce new Mapbox 3D style `Style.STANDARD` and make it default.
* Introduce GeoJSONSource partial update APIs `GeoJsonSource.addGeoJSONSourceFeatures`, `GeoJsonSource.updateGeoJSONSourceFeatures`, `GeoJsonSource.removeGeoJSONSourceFeatures`.

## Bug fixes 🐞
* Fix 3d location layer properties `model-scale-transition` and `model-rotation-transition`, made them non-transitionable.
* Fix crash when running animations (e.g. gestures) on Android 14 beta.
* Fix view port not following puck if viewport `bearing` option was set to `null` even though new locations were available.

## Dependencies
* Update gl-native to v11.0.0-SNAPSHOT.0608T0508Z.a85336d and common to v24.0.0-SNAPSHOT.0616T0900Z.e0319e0.
* Instantiate OpenGL ES context 3.0, if available, otherwise fallback to 2.0.
* Use EGL 1.4 instead of EGL 1.0.
* Update dependencies :

| Dependency                                       | Before       | After      |
|--------------------------------------------------|--------------|------------|
| Android Gradle Plugin                            | 7.0.4        | 7.4.2      |
| Kotlin                                           | 1.5.31       | 1.7.20     |
| org.jetbrains.kotlin:kotlin-stdlib-jdk7          | 1.5.31       |            |
| org.jetbrains.kotlin:kotlin-stdlib-jdk8          |              | 1.7.20     |
| Dokka plugin                                     | 1.5.31       | 1.7.20     |
| androidx.core:core-ktx                           | 1.7.0        | 1.9.0      |
| androidx.appcompat:appcompat                     | 1.3.0        | 1.6.1      |
| androidx.test:rules                              | 1.4.0        | 1.5.0      |
| androidx.test:core                               | 1.4.0        | 1.5.0      |
| androidx.test:runner                             | 1.4.0        | 1.5.2      |
| androidx.test:orchestrator                       | 1.4.0        | 1.4.2      |
| androidx.test:monitor                            | 1.4.0        | 1.6.1      |
| androidx.test.espresso:espresso-core             | 3.4.0        | 3.5.1      |
| androidx.test.ext:junit                          | 1.1.3        | 1.1.5      |
| org.jetbrains.kotlinx:kotlinx-coroutines-core    | 1.3.9        | 1.6.1      |
| org.jetbrains.kotlinx:kotlinx-coroutines-test    | 1.3.9        | 1.6.1      |
| io.mockk:mockk                                   | 1.12.3       | 1.13.4     |
| io.mockk:mockk-agent-api                         | 1.12.3       | 1.13.4     |
| io.mockk:mockk-agent-jvm                         | 1.12.3       | 1.13.4     |
| org.robolectric:robolectric                      | 4.8.1        | 4.9.2      |
| com.android.tools.lint:lint-api                  | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint-checks               | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint                      | 30.0.4       | 30.4.2     |
| com.android.tools.lint:lint-tests                | 30.0.4       | 30.4.2     |
| com.android.tools:testutils                      | 30.0.4       | 30.4.2     |
| nl.jqno.equalsverifier:equalsverifier            | 3.10.1       | 3.14       |
| io.gitlab.arturbosch.detekt:detekt-formatting    | 1.20.0       | 1.22.0     |
| composeOptions -> kotlinCompilerExtensionVersion | 1.1.0-beta03 | 1.4.3      |
| androidx.compose:compose-bom                     |              | 2023.01.00 |
| androidx.compose.compiler:compiler               |              | 1.3.2      |
| com.pinterest:ktlint                             | 0.39.0       | 0.48.2     |

# 11.0.0-alpha.1 March 2, 2023
## Features ✨ and improvements 🏁
* Enable raster colorization via `raster-color` expression and `RasterLayer.rasterColor`, `RasterLayer.rasterColorMix`, `RasterLayer.rasterColorRange` layer properties.

# 10.15.0 July 27, 2023
## Bug fixes 🐞
* Fix a security exception when fine location permission is not granted when using `DefaultLocationProvider`.
* Fix camera animations jitter noticeable on high zoom levels using location puck following mode.
* Fix view port not following puck if viewport `bearing` option was set to `null` even though new locations were available.
* Fix the issue of tiles displaying redundant images during a style switch, when both styles include similarly named sprites.
* Fix crash in offline download when no-content responses are received.
* Fix the issue of rendering sky layers behind the globe when the atmosphere is disabled.
* Resolve the issue with tile rendering when the tile pack's levelling scheme has a maximum zoom level of less than 16, and the camera zoom surpasses the maximum zoom value defined by the tile pack's levelling scheme.
* Fix the HTTP resources expiration time being reset when the Expires header defined the expiration time and the Cache-Control header was present but did not define the expiration time.
* Fix the latency during the style switch of fill extrusion layers that have data-driven paint properties.
* Fix a rounding error when point lies at the edge of the screen by using `rountToInt` for limiting `MapboxMap#pixelForCoordinate` to the bounds of MapView.

## Dependencies
* Update gl-native to v10.15.0 and common to v23.7.0.


# 10.15.0-rc.1 July 13, 2023
## Bug fixes 🐞
* Fix view port not following puck if viewport `bearing` option was set to `null` even though new locations were available.
* Fix the issue of tiles displaying redundant images during a style switch, when both styles include similarly named sprites.
* Fix crash in offline download when no-content responses are received.
* Fix the issue of rendering sky layers behind the globe when the atmosphere is disabled.

## Dependencies
* Update gl-native to v10.15.0-rc.1 and common to v23.7.0-rc.1.


# 10.15.0-beta.1 June 29, 2023
## Bug fixes 🐞
* Fix a security exception when fine location permission is not granted when using DefaultLocationProvider.
* Fix camera animations jitter noticeable on high zoom levels using location puck following mode.
* Resolve the missing tile rendering issue when the tile pack's leveling scheme has a maximum zoom level of less than 16.
* Fix the issue that HTTP resources expiration time being incorrectly reset, which caused excessive network requests.
* Fix the style change latency for fill extrusion layers(in case the layers have data-driven paint properties) during the style switches.

## Dependencies
* Update gl-native to v10.15.0-beta.1 and common to v23.7.0-beta.1.



# 10.14.1 June 22, 2023
## Bug fixes 🐞
* Fix the latency during the style switch of fill extrusion layers that have data-driven paint properties.

## Dependencies
* Update gl-native to v10.14.1.


# 10.14.0 June 14, 2023
## Features ✨ and improvements 🏁
* Improve performance of setting puck style properties by removing redundant check if layer / source exists.
* Improve performance of symbol layers with identical or no text.
* Hide line labels with too large an angle between two neighboring glyphs.
* Introduce `MapboxMap.tileCover` and `Snapshotter.tileCover` experimental API to get the tileIDs that cover current map camera.

## Bug fixes 🐞
* Fix layer zoom range check so that the layer will be hidden when the zoom equals to layer's max zoom.
* Fixes occasional rendering errors caused by long line layers and vertex data overflow.
* Fix crash when running animations (e.g. gestures) on Android 14 beta.
* Fix image and zoom dependent expression evaluation errors during style switching.
* Avoid re-use of raw icon atlas buffers when images point to the different location in the atlas.
* Fix flickering of symbols on high zoom level.
* Fix ineffective `tilesize` setting in `CustomGeometrySourceOptions`. Now the generated tiles accurately reflect the specified `tilesize` setting.

## Dependencies
* Update gl-native to v10.14.0 and common to v23.6.0.


# 10.14.0-rc.1 May 31, 2023
## Features ✨ and improvements 🏁
* Introduce `MapboxMap.tileCover` experimental API to get the tileIDs that cover current map camera.

## Bug fixes 🐞
* Fix crash when running animations (e.g. gestures) on Android 14 beta.
* Fix image and zoom dependent expression evaluation errors during style switching.
* Avoid re-use of raw icon atlas buffers when images point to the different location in the atlas.

## Dependencies
* Update gl-native to v10.14.0-rc.1 and common to v23.6.0-rc.1.


# 10.14.0-beta.1 May 17, 2023
## Features ✨ and improvements 🏁
* Improve performance of setting puck style properties by removing redundant check if layer / source exists.
* Improve performance of symbol layers with identical or no text.
* Hide line labels with too large an angle between two neighboring glyphs.

## Bug fixes 🐞
* Fix layer zoom range check so that the layer will be hidden when the zoom equals to layer's max zoom.
* Fixes occasional rendering errors caused by long line layers and vertex data overflow.

## Dependencies
* Update gl-native to v10.14.0-beta.1 and common to v23.6.0-beta.1.


# 10.13.0 May 05, 2023
## Features ✨ and improvements 🏁
* Add overloaded methods to `CameraAnimatorsFactory` allowing to set camera animator owner.
* Improve startup performance by calculating the style expressions dependencies lazily.
* Introduce a new APIs `coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo` and `coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo>` which will return record(s) containing both `coordinate` and `isOnSurface` info.
* Deprecate `Snapshotter.setTileMode` and `Snapshotter.isInTileMode`.
* Deprecate `Style.setStyleGeoJSONSourceData(sourceId, data)`.
* Deprecate `isMapLoaded` method.
* Share similar image and glyph atlases across tiles and thus avoid unnecessary textures creation.
* Render single color gradient as solid line.
* Use flat screen coordinate conversion functions with zero exaggeration terrain.
* Deprecate qRF APIs that use specific geometry types, which also are not cancelable.

## Bug fixes 🐞
* Fix rare issue in renderer which could freeze the device when bringing the `MapView` back to front.
* Fix artefacts caused by a race condition when style layers got updated during pending tiles layout.
* Fix missing return unexpected result in model_loader processing, so the client could be aware of the error.
* Fix text flickering while symbol layer update if text-field contains text-color property.
* Fix 3d location layer properties `model-scale-transition` and `model-rotation-transition`, made them non-transitionable.
* Fix raw expression parsing for list literal.
* Fix text flickering while symbol layer update if `text-field` contains `text-color` property inside the format expression.
* Fix a crash when a hillshade bucket was created with disabled terrain, but the terrain got enabled afterwards.
* Fix the crash when identifying if device is connected to WiFi.
* Fix jumpy gestures when external `AndroidGestureManager` is added with `setGesturesManager`.
* Fix a bug that accidentally cleared icon images when `setMemoryBudget` was used with megabyte values.
* Fix handling of Unicode characters in `slice`, `index-of`, `in` and `length` expressions.

## Dependencies
* Update gl-native to v10.13.1 and common to v23.5.0.




# 10.13.0-rc.1 April 20, 2023
## Features ✨ and improvements 🏁
* Add overloaded methods to `CameraAnimatorsFactory` allowing to set camera animator owner.
* Improve startup performance by calculating the style expressions dependencies lazily.
* Introduce a new APIs `coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo` and `coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo>` which will return record(s) containing both `coordinate` and `isOnSurface` info.

## Bug fixes 🐞
* Fix 3d location layer properties `model-scale-transition` and `model-rotation-transition`, made them non-transitionable.
* Fix raw expression parsing for list literal.
* Fix text flickering while symbol layer update if `text-field` contains `text-color` property inside the format expression.

## Dependencies
* Update gl-native to v10.13.0-rc.1 and common to v23.5.0-rc.1.



# 10.13.0-beta.1 April 05, 2023
## Features ✨ and improvements 🏁
* Deprecate `Snapshotter.setTileMode` and `Snapshotter.isInTileMode`.
* Deprecate `Style.setStyleGeoJSONSourceData(sourceId, data)`.
* Use flat screen coordinate conversion functions with zero exaggeration terrain.
* Share similar image and glyph atlases across tiles and thus avoid unnecessary textures creation.
* Render single color gradient as solid line.

## Bug fixes 🐞
* Fix the crash when identifying if device is connected to WiFi.
* Fix a crash when a hillshade bucket was created with disabled terrain, but the terrain got enabled afterwards.

## Dependencies
* Update gl-native to v10.13.0-beta.1 and common to v23.5.0-beta.1.



# 10.12.1 March 28, 2023
## Bug fixes 🐞
* Fix missing data id in `source-data-loaded` event for empty GeoJSON data.

## Dependencies
* Update gl-native to v10.12.1.


# 10.12.0 March 23, 2023
## Features ✨ and improvements 🏁
* Add `data-id` argument to `GeoJsonSource` data update methods. `data-id` is later attached to the `SourceDataLoadedEventData` event and allows to track the specific `GeoJsonSource` update.
* Reduce line gradient texture size if there is no color change.

## Bug fixes 🐞
* Fix regression from `v10.11.0` when applying geojson from loaded style to the new style could cause the crash or no data applied.
* Fix regression from `v10.11.0` when applying geojson data was not working when no style was available.
* Do not fail on parsing vector tile when there are duplicate keys encoded in the tile data.
* Fix a bug where camera change event is not emitted when using free camera options to set camera.
* Fix network usage for the case when multiple access tokens are used.
* Fix rendering glitches for symbols when animating the map caused by image atlas interfering.
* Fix set geojson source data with null value.
* Interrupt blocking disk cache database operations on application exit, so that the application does not hang.
* Fix a bug where continuously dragging and changing zoom would lead to either very slow or very fast map dragging.
* Avoid generation of the unneeded glyph textures.
* Fix rendering errors when the closing point is missing in GeoJSON polygon features.
* Fix a bug where taking consecutive snapshots had missing tiles.
* Fix a bug where fill extrusions would flicker when crossing a certain zoom threshold.

## Dependencies
* Update gl-native to v10.12.0 and common to v23.4.0.


# 10.11.2 March 10, 2023
## Bug fixes 🐞
* Fix missing terrain on some GPUs (e.g. Mali). ([2038](https://github.com/mapbox/mapbox-maps-android/pull/2038))

## Dependencies
* Update gl-native to v10.11.2. ([2038](https://github.com/mapbox/mapbox-maps-android/pull/2038))


# 10.12.0-rc.1 March 09, 2023
## Bug fixes 🐞
* Fix regression from `v10.11.0` when applying geojson data was not working when no style was available.
* Interrupt blocking disk cache database operations on application exit so that the application does not hang.
* Fix a bug where continuously dragging and changing zoom would lead to either very slow or fast map dragging.
* Fix missing terrain on some GPUs (e.g. Mali).

## Dependencies
* Update gl-native to v10.12.0-rc.1 and common to v23.4.0-rc.1.


# 10.12.0-beta.1 February 22, 2023
## Features ✨ and improvements 🏁
* Add `data-id` argument to `GeoJsonSource` data update methods. `data-id` is later attached to the
`SourceDataLoadedEventData` event and allows to track the specific `GeoJsonSource` update. ([1991](https://github.com/mapbox/mapbox-maps-android/pull/1991))
* Reduce line gradient texture size if there is no color change.

## Bug fixes 🐞
* Avoid generation of the unneeded glyph textures.
* Fixes rendering errors when the closing point is missing in GeoJSON polygon features.
* Fix a bug where taking consecutive snapshots had missing tiles.
* Fix a bug where fill extrusions would flicker when crossing a certain zoom threshold.

## Dependencies
* Update gl-native to v10.12.0-beta.1 and common to v23.4.0-beta.1.


# 10.10.2 February 16, 2023
## Bug fixes 🐞
* [tile store] Remove token from TileStore Logs. ([2011](https://github.com/mapbox/mapbox-maps-android/pull/2011))

## Dependencies
* Update common to v23.2.3. ([2011](https://github.com/mapbox/mapbox-maps-android/pull/2011))

# 10.11.0 February 09, 2023
## Features ✨ and improvements 🏁
* Skip redundant `MapboxMap.setCamera` updates in `CameraAnimationsPlugin`. ([1909](https://github.com/mapbox/mapbox-maps-android/pull/1909))
* Improve performance by setting geojson data directly. ([1920](https://github.com/mapbox/mapbox-maps-android/pull/1920))
* Fix viewport hang when transition to `FollowPuckViewportState` and no new location update is available. ([1929](https://github.com/mapbox/mapbox-maps-android/pull/1929))
* Avoid unneeded tiles relayout on style change. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Enable the usage of expressions in array values during style parsing, where the member expressions in the array is evaluated to the same type. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Slightly improve quality and performance of the terrain. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Improve performance for style switch use cases by avoiding unneeded tiles re-layout. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))

## Bug fixes 🐞
* Fix a bug where `flyTo` animation request invalid tiles from map engine. ([1949](https://github.com/mapbox/mapbox-maps-android/pull/1949))
* Deprecate `pattern` and `dash` transition properties for layer (e.g. `BackgroundLayer.backgroundPatternTransition`, `FillExtrusionLayer.fillExtrusionPatternTransition`, `FillLayer.fillPatternTransition`, `LineLayer.lineDasharrayTransition`, `LineLayer.linePatternTransition`, ...).  ([1941](https://github.com/mapbox/mapbox-maps-android/pull/1941))
* Fix terrain tiles missing issue when running in the emulator and some android devices. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))
* Fix wrong `onLongTouch` event detected on any map gesture after clicks on ViewAnnotation. ([1954](https://github.com/mapbox/mapbox-maps-android/pull/1954))
* Fix a known issue where `NullPointerException` was thrown when last location was not available. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix flickering issues for the symbols that allow overlap (have text(icon)-allow-overlap: true) with skipping fade-in animation for them. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix data queueing issue when calling API 'setStyleGeoJSONSourceData'. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix flickering terrain on high pitched views. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Clamp inputs in DEMData::get() to prevent OOB Access. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix asset file source threading model - do not use legacy RunLoop, thus do not use ALooper and get rid of an extra thread. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix text visualization when in orthographic mode. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix terrain elevation when a padded dem source is used. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fixes visible tile borders when msaa enabled. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix distance-to-center filtering of symbols when terrain is enabled. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix color transitions in model ligthing. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix terrain placement for model layer when model scale is set to zero. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix geometry tile model layer paint property transition. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix camera bumpiness at the beginning of a drag operation when terrain is enabled. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix disappearing tiles when terrain with a high exaggeration is enabled. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix line-border-color when used with line-trim-offset. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fixes an issue when allow-overlap, ignore-placement , and map rotation-alignment of icon breaks the rendering of symbols on the globe. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Mitigate symbol flickering on source data change during camera animation. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Mitigate OOM caused by Snapshotter API usage. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fixes rare crashes during render feature queries, if the features are located close to each other. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix an issue where the camera would start flickering during subsequent calls to `Map::jumpTo` / `Map::easeTo` with terrain enabled.. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix redundant snapshot capturing that caused excessive memory usage. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Fix incorrect resource type being specified map loading error event data. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))
* Original gesture settings should be maintained after map operations (such as panning the map) complete. ([1989](https://github.com/mapbox/mapbox-maps-android/pull/1989))

## Dependencies
* Update gl-native to v10.11.1, common to v23.3.1. ([1984](https://github.com/mapbox/mapbox-maps-android/pull/1984))


# 10.11.0-rc.1 January 26, 2023
## Features ✨ and improvements 🏁
* Improve performance for style switch use cases by avoiding unneeded tiles re-layout. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))

## Bug fixes 🐞
* Fix a bug where `flyTo` animation request invalid tiles from map engine. ([1949](https://github.com/mapbox/mapbox-maps-android/pull/1949))
* Deprecate `pattern` and `dash` transition properties for layer (e.g. `BackgroundLayer.backgroundPatternTransition`, `FillExtrusionLayer.fillExtrusionPatternTransition`, `FillLayer.fillPatternTransition`, `LineLayer.lineDasharrayTransition`, `LineLayer.linePatternTransition`, ...).  ([1941](https://github.com/mapbox/mapbox-maps-android/pull/1941))
* Fix terrain tiles missing issue when running in the emulator and some android devices. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))
* Fix wrong `onLongTouch` event detected on any map gesture after clicks on ViewAnnotation. ([1954](https://github.com/mapbox/mapbox-maps-android/pull/1954))

## Dependencies
* Update gl-native to v10.11.0-rc.1 and common to v23.3.0-rc.1. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))

## Known issues
* If last location is not available, `LocationEngine` will throw a `NullPointerException`.


# 10.11.0-beta.1 January 11, 2023
## Features ✨ and improvements 🏁
* Skip redundant `MapboxMap.setCamera` updates in `CameraAnimationsPlugin`. ([1909](https://github.com/mapbox/mapbox-maps-android/pull/1909))
* Improve performance by setting geojson data directly. ([1920](https://github.com/mapbox/mapbox-maps-android/pull/1920))
* Update license to reflect latest version of the Mapbox TOS. ([1927](https://github.com/mapbox/mapbox-maps-android/pull/1927))
* Fix viewport hang when transition to `FollowPuckViewportState` and no new location update is available. ([1929](https://github.com/mapbox/mapbox-maps-android/pull/1929))
* Optimize shadow rendering memory use and performance. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Enable the usage of expressions in array values during style parsing, where the member expressions in the array is evaluated to the same type. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Slightly improve quality and performance of the terrain. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))

## Bug fixes 🐞
* Fix crash due to invalid distance when panning the map. ([1906](https://github.com/mapbox/mapbox-maps-android/pull/1906))
* Fix asset file source threading model. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix terrain elevation when a padded dem source is used. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix distance-to-center filtering of symbols when terrain is enabled. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix camera bumpiness at the beginning of a drag operation when terrain is enabled. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix disappearing tiles when terrain with a high exaggeration is enabled. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Mitigate symbol flickering on source data change during camera animation. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fixes rare crashes during render feature queries, if the features are located close to each other. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix redundant snapshot capturing that caused excessive memory usage. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fixes an issue when allow-overlap, ignore-placement , and map rotation-alignment of icon breaks the rendering of symbols on the globe. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Mitigate symbol flickering on source data change during camera animation. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Mitigate OOM caused by Snapshotter API usage. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))
* Fix an issue where the camera would start flickering during subsequent calls to `Map::jumpTo` / `Map::easeTo` with terrain enabled. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))

## Dependencies
* Update gl-native to v10.11.0-beta.1 and common to v23.3.0-beta.1. ([1934](https://github.com/mapbox/mapbox-maps-android/pull/1934))

## Known issues
* Using `pattern` and `dash` transition properties for layer (e.g. `BackgroundLayer.backgroundPatternTransition`, `FillExtrusionLayer.fillExtrusionPatternTransition`, `FillLayer.fillPatternTransition`, `LineLayer.lineDasharrayTransition`, `LineLayer.linePatternTransition`, ...) will result in an invalid layer.


# 10.10.1 January 25, 2023
## Features ✨ and improvements 🏁
* Add custom header x-mapbox-app-info to requests. ([1957](https://github.com/mapbox/mapbox-maps-android/pull/1957))

## Bug fixes 🐞
* Fix a bug where taking consecutive snapshots had missing tiles. ([1957](https://github.com/mapbox/mapbox-maps-android/pull/1957))

## Dependencies
* Update gl-native to v10.10.1 and common to v23.2.2. ([1957](https://github.com/mapbox/mapbox-maps-android/pull/1957))



# 10.10.0 December 07, 2022
## Features ✨ and improvements 🏁
* Introduce view annotation `ViewAnnotationManager.annotations` API to access list of added view annotations. ([1751](https://github.com/mapbox/mapbox-maps-android/pull/1751))
* Introduce view annotation `ViewAnnotationManager.cameraForAnnotations` API to get camera options for given view annotations list. ([1753](https://github.com/mapbox/mapbox-maps-android/pull/1753))
* Unify the `margin`/`translation` `Widget` APIs into the `WidgetPosition.offset`, rename `WidgetPosition.horizontal`/`WidgetPosition.vertical` to `WidgetPosition.horizontalAlignment`/`WidgetPosition.verticalAlignment`; Deprecate the original constructors and `setTranslation` APIs. ([1782](https://github.com/mapbox/mapbox-maps-android/pull/1782))
* Add API for removing atmosphere. ([1841](https://github.com/mapbox/mapbox-maps-android/pull/1841))
* Introduce `MapTelemetry.getUserTelemetryRequestState` API. ([1877](https://github.com/mapbox/mapbox-maps-android/pull/1877))
* Eliminate tiles re-creation and re-layout on zooming map with globe, when the camera is trespassing the zoom projection border. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Avoid tiles re-layout on enabling terrain with zero exaggeration. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Added API to enable/disable rendering of world copies in mercator mode. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Improve symbol filtering performance when `distance-from-camera` and `pitch` expressions are used. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Reduce number of operations when terrain is used with zero exaggeration. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))

## Bug fixes 🐞
* Fix an issue when touch events didn't pass through clickable view annotations. ([1745](https://github.com/mapbox/mapbox-maps-android/pull/1745))
* Trigger repaint after `BitmapWidget` is updated. ([1797](https://github.com/mapbox/mapbox-maps-android/pull/1797))
* Fix a crash after removing the view annotation if view has an attached animation or transition. ([1831](https://github.com/mapbox/mapbox-maps-android/pull/1831))
* Emit the last indicator state when new listeners are added to the location component. ([1827](https://github.com/mapbox/mapbox-maps-android/pull/1827))
* Fix an issue where queried symbol features did not contain the associated feature state. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Throw understandable exception when using widgets with the `MapView` or `MapSurface` and not specifying `MapInitOptions.MapOptions.contextMode = ContextMode.SHARED` preventing hard-catching runtime crashes or artifacts. ([1834](https://github.com/mapbox/mapbox-maps-android/pull/1834))
* Fix an issue where location updates were not provided on correct thread/looper. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Dispatch location updates as a location event when the app is moving to background. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Fix `LocationEngine` to support multiple concurrent clients. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Fix immediate camera animation on API level 23 or below. ([1842](https://github.com/mapbox/mapbox-maps-android/pull/1842))
* Fix loss of `Widget` during background/foreground transition. ([1864](https://github.com/mapbox/mapbox-maps-android/pull/1864))
* Fix displaying MapView in Android Studio Layout Preview. ([1881](https://github.com/mapbox/mapbox-maps-android/pull/1881))
* Fix a bug where `ViewAnnotationManager.cameraForAnnotations` API doesn't return correct camera bounds. ([1861](https://github.com/mapbox/mapbox-maps-android/pull/1861))
* Handle OOB when getting DEM Data ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fix a rare precision issue with symbol and circle layer occlusion when terrain is enabled. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fixes a rare crash caused by a race condition during gesture handling. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Disable location indicator occlusion testing with the terrain when exaggeration is set to 0. This fixes occasional location indicator disappearance. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fix an issue where queried symbol features did not contain associated feature state. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Clear geojson tiles after the source is updated with empty features in order to eliminate "phantom tile" artefacts and to obviate extra work for keeping empty tiles. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Eliminate duplicated tile async upload requests - fix a race condition causing a tile rendering the previous layout result. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Truncate long single line text in symbol layers to prevent rendering artifact. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fixes an issue when the mixed usage of patterns and icons caused bleeding of textures. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fixes visible tile borders when MSAA enabled. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fix LOD and prevent from flickering on enabling terrain with zero exaggeration. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Setting empty value for a style layer filter now clears the filter. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fixes rendering artifacts near tile borders when using terrain with zero exaggeration. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fixes fog rendering when terrain is used with zero exaggeration. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fix `distance-to-center` filtering of symbols when terrain is enabled. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Improved `cameraForCoordinate` result quality with pitch and with terrain. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891))
* Fix a crash on Android 10 and below, when Google Play Location Service is not present. ([1898](https://github.com/mapbox/mapbox-maps-android/pull/1898))

## Dependencies
* Update gl-native to v10.10.0 and common to v23.2.1. ([1891](https://github.com/mapbox/mapbox-maps-android/pull/1891) [1898](https://github.com/mapbox/mapbox-maps-android/pull/1898))
* Remove `mapbox-android-core` dependency, it is now part of Mapbox Common library.
    **NOTE:**: You need to remove any explicit dependency declaration to `com.mapbox.mapboxsdk:mapbox-android-core:<version>` from the project to avoid duplicated class definition errors related to location APIs. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))


# 10.10.0-rc.1 November 18, 2022
## Features ✨ and improvements 🏁
* Improve symbol filtering performance when distance-from-camera and pitch expressions are used. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Add API for removing atmosphere. ([1841](https://github.com/mapbox/mapbox-maps-android/pull/1841))

## Bug fixes 🐞
* Trigger repaint after `BitmapWidget` is updated. ([1797](https://github.com/mapbox/mapbox-maps-android/pull/1797))
* Fix a crash after removing the view annotation if view has an attached animation or transition. ([1831](https://github.com/mapbox/mapbox-maps-android/pull/1831))
* Emit the last indicator state when new listeners are added to the location component. ([1827](https://github.com/mapbox/mapbox-maps-android/pull/1827))
* Fix an issue where queried symbol features did not contain the associated feature state. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Clear geojson tiles after the source is updated with empty features in order to eliminate "phantom tile" artifacts and to obviate extra work for keeping empty tiles. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Fix a regression from v10.10.0-beta.1 release, the BitmapWidget.setTranslation API should offset relative to its original position. ([1833](https://github.com/mapbox/mapbox-maps-android/pull/1833))
* Throw understandable exception when using widgets with the `MapView` or `MapSurface` and not specifying `MapInitOptions.MapOptions.contextMode = ContextMode.SHARED` preventing hard-catching runtime crashes or artifacts. ([1834](https://github.com/mapbox/mapbox-maps-android/pull/1834))
* Fix an issue where location updates were not provided on correct thread/looper. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* [telemetry] Dispatch location updates as a location event when the app is moving to background. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Fix `LocationEngine` to support multiple concurrent clients. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Fix immediate camera animation on API level 23 or below. ([1842](https://github.com/mapbox/mapbox-maps-android/pull/1842))

## Dependencies
* Update gl-native to v10.10.0-rc.1 and common to v23.2.0-rc.3. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))
* Remove `mapbox-android-core` dependency, it is now part of Mapbox Common library.
  **NOTE:**: You need to remove any explicit dependency declaration to `com.mapbox.mapboxsdk:mapbox-android-core:<version>` from the project to avoid duplicated class definition errors related to location APIs. ([1836](https://github.com/mapbox/mapbox-maps-android/pull/1836))



# 10.9.1 November 7, 2022

## Bug fixes 🐞
* Handle OOB when getting DEM Data. ([#1808](https://github.com/mapbox/mapbox-maps-android/pull/1808))

# 10.10.0-beta.1 November 03, 2022
## Features ✨ and improvements 🏁
* Introduce view annotation `ViewAnnotationManager.annotations` API to access list of added view annotations. ([1751](https://github.com/mapbox/mapbox-maps-android/pull/1751))
* Introduce view annotation `ViewAnnotationManager.cameraForAnnotations` API to get camera options for given view annotations list. ([1753](https://github.com/mapbox/mapbox-maps-android/pull/1753))
* Eliminate tiles re-creation and re-layout on zooming map with globe, when the camera is trespassing the zoom projection border. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Avoid tiles re-layout on enabling terrain with zero exaggeration. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Unify the `margin`/`translation` `Widget` APIs into the `WidgetPosition.offset`, rename `WidgetPosition.horizontal`/`WidgetPosition.vertical` to `WidgetPosition.horizontalAlignment`/`WidgetPosition.verticalAlignment`; Deprecate the original constructors and `setTranslation` APIs. ([1782](https://github.com/mapbox/mapbox-maps-android/pull/1782))
* Add APIs to enable/disable rendering of world copies. ([1794](https://github.com/mapbox/mapbox-maps-android/pull/1794), [1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))

## Bug fixes 🐞
* Fix an issue when touch events didn't pass through clickable view annotations. ([1745](https://github.com/mapbox/mapbox-maps-android/pull/1745))
* Handle OOB when getting DEM Data. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Fix a rare precision issue with symbol and circle layer occlusion when terrain is enabled. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Fixes a rare crash caused by a race condition during gesture handling. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Disable location indicator occlusion testing with the terrain when exaggeration is set to 0. This fixes occasional location indicator disappearance. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))
* Exclude map disk cache files from cloud backups. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))

## Dependencies
* Update gl-native to v10.10.0-beta.1 and common to v23.2.0-beta.1. ([1791](https://github.com/mapbox/mapbox-maps-android/pull/1791))



# 10.9.0 October 21, 2022

## Features ✨ and improvements 🏁
* Rendering performance improvements for fast paced camera changes. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Eliminate tiles re-creation and re-layout on zooming map with globe, when the camera is trespassing the zoom projection border. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Deprecate gesture settings `increaseRotateThresholdWhenPinchingToZoom` property. ([1632](https://github.com/mapbox/mapbox-maps-android/pull/1632))
* Enable asynchronous tile uploader by default to improve animation performance. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Vector tiles without symbols are not hold for fade-out animation so that less amount of vector tiles are managed at a time. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Add support to set location puck opacity. ([1659](https://github.com/mapbox/mapbox-maps-android/pull/1659))
* Support `pitch` and `distanceFromCenter` filters in symbol layers. ([1662](https://github.com/mapbox/mapbox-maps-android/pull/1662))
* Migrate telemetry APIs from [mobile-events-android](https://github.com/mapbox/mapbox-events-android) to common SDK implementation. ([1672](https://github.com/mapbox/mapbox-maps-android/pull/1672))

## Bug fixes 🐞
* Fix frequent layout invalidation caused by view annotations calling `View.bringToFront()`. ([1744](https://github.com/mapbox/mapbox-maps-android/pull/1744))
* Fix flickering of vertically shifted line labels on style change, caused by the wrong initial placement. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Fix location indicator rendering as a rectangle on low zoom levels. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Fix crash caused by using of invalid paint property binders. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Fix "phantom tiles" artefacts after GeoJSON source update when asynchronous tile loading is enabled. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Fix a bug in cameraForGeometry when called on a map with padding set. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))
* Fix scale bar text being cut. ([1716](https://github.com/mapbox/mapbox-maps-android/pull/1716))
* Fix possible crash when adding terrain using Style DSL after consecutive style changes. ([1717](https://github.com/mapbox/mapbox-maps-android/pull/1717))
* Fix scale bar text overlapping. ([1728](https://github.com/mapbox/mapbox-maps-android/pull/1728))
* Fix issue where `Widget` is not immediately rendered when added to the `MapView`. ([1708](https://github.com/mapbox/mapbox-maps-android/pull/1708))
* Mitigate symbol flickering on zooming out globe map. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Fix random crash on tiles update when asynchronous uploading is enabled. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Make telemetry a single instance. Avoids module recreation when coming from background. ([#1695](https://github.com/mapbox/mapbox-maps-android/pull/1695))
* Fix scale bar truncated at high zoom levels near the poles. ([1620](https://github.com/mapbox/mapbox-maps-android/pull/1620))
* Fix broken view annotation positioning when marking them `View.INVISIBLE` and then making `View.VISIBLE`. ([1616](https://github.com/mapbox/mapbox-maps-android/pull/1616))
* Snap puck to north if `puckBearingEnabled` is set to false. ([1635](https://github.com/mapbox/mapbox-maps-android/pull/1635))
* Preserve cached properties if applied to the layer before during the `Style#getLayer` call. ([1622](https://github.com/mapbox/mapbox-maps-android/pull/1622))
* Fix a `NullPointerException` in `StandardGestureListener`, where `MotionEvent` should be nullable. ([1645](https://github.com/mapbox/mapbox-maps-android/pull/1645)), ([1677](https://github.com/mapbox/mapbox-maps-android/pull/1677))
* Fix incorrect `MapView` dimensions after background orientation change. ([1658](https://github.com/mapbox/mapbox-maps-android/pull/1658))
* Fix wrong `BitmapWidget` position when using `WidgetPosition.Horizontal.CENTER` or `WidgetPosition.Vertical.CENTER` property. ([1651](https://github.com/mapbox/mapbox-maps-android/pull/1651))
* Fix pixel flickering between tiles on darker styles in globe view. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix incorrect shading of rounded corners in fill extrusions when ambient occlusion and/or shadow is enabled. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix shadows when the light radial coordinate is zero. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Location indicator layer is occluded by terrain to align rendering behavior with other location indicator rendering types. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Symbol flickering after runtime styling caused by using outdated symbol placement on updated symbol buckets. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Allow Light intensity and Light shadow intensity to accept expressions when using setStyleLight API call. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix a bug in `cameraForGeometry` when called on a map with padding set. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Throw an exception with the meaningful stack trace when constructing `Value` from an infinite or NaN number. ([1681](https://github.com/mapbox/mapbox-maps-android/pull/1681))

## Dependencies
* Update mapbox-gestures-android dependency to [v0.8.0](https://github.com/mapbox/mapbox-gestures-android/releases/tag/v0.8.0). ([1645](https://github.com/mapbox/mapbox-maps-android/pull/1645))
* Update gl-native to v10.9.0 and common to v23.1.1. ([1760](https://github.com/mapbox/mapbox-maps-android/pull/1760))



# 10.9.0-rc.1 October 07, 2022

## Features ✨ and improvements 🏁
* Eliminate tiles re-creation and re-layout on zooming map with globe, when the camera is trespassing the zoom projection border. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))

## Bug fixes 🐞
* Fix scale bar text being cut. ([1716](https://github.com/mapbox/mapbox-maps-android/pull/1716))
* Fix possible crash when adding terrain using Style DSL after consecutive style changes. ([1717](https://github.com/mapbox/mapbox-maps-android/pull/1717))
* Fix scale bar text overlapping. ([1728](https://github.com/mapbox/mapbox-maps-android/pull/1728))
* Fix issue where `Widget` is not immediately rendered when added to the `MapView`. ([1708](https://github.com/mapbox/mapbox-maps-android/pull/1708))
* Fix "phantom tiles" artefacts after GeoJSON source update when asynchronous tile loading is enabled. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Mitigate symbol flickering on zooming out globe map. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Fix random crash on tiles update when asynchronous uploading is enabled. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Fix a crash when Google location service is started in background but stopped in foreground. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))
* Fix an issue where mock field is missing from the LocationEngine updates. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))

## Dependencies
* Update gl-native to v10.9.0-rc.1 and common to v23.1.0-rc.2. ([1729](https://github.com/mapbox/mapbox-maps-android/pull/1729))


# 10.8.1 September 30, 2022

## Bug fixes 🐞

* Fix incorrect `MapView` dimensions after background orientation change. ([1658](https://github.com/mapbox/mapbox-maps-android/pull/1658))
* Throw an exception with the meaningful stacktrace when constructing `Value` from an infinite or NaN number. ([1681](https://github.com/mapbox/mapbox-maps-android/pull/1681))
* Make telemetry a single instance. Avoids module recreation when coming from background. ([1695](https://github.com/mapbox/mapbox-maps-android/pull/1695))
* Fix a potential style load error when non-ASCII characters are used in the version name, by escaping non US ASCII characters in user-agent header. ([1709](https://github.com/mapbox/mapbox-maps-android/pull/1709))

## Dependencies
* Bump common to v23.0.1. ([1709](https://github.com/mapbox/mapbox-maps-android/pull/1709))

# 10.9.0-beta.2 September 28, 2022

## Bug fixes 🐞
* Make telemetry a single instance. Avoids module recreation when coming from background. ([#1695](https://github.com/mapbox/mapbox-maps-android/pull/1695))
* Fix an issue where location engine doesn't produce bearing/accuracy/speed information with the location updates. ([1696](https://github.com/mapbox/mapbox-maps-android/pull/1696))

## Dependencies
* Bump common sdk to v23.1.0-beta.2. ([1696](https://github.com/mapbox/mapbox-maps-android/pull/1696))


# 10.9.0-beta.1 September 22, 2022

## Features ✨ and improvements 🏁
* Deprecated gesture settings `increaseRotateThresholdWhenPinchingToZoom` property. ([1632](https://github.com/mapbox/mapbox-maps-android/pull/1632))
* Enable asynchronous tile uploader by default to improve animation performance. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Vector tiles without symbols are not hold for fade-out animation so that less amount of vector tiles are managed at a time. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Add support to set location puck opacity. ([1659](https://github.com/mapbox/mapbox-maps-android/pull/1659))
* Support `pitch` and `distanceFromCenter` filters in symbol layers. ([1662](https://github.com/mapbox/mapbox-maps-android/pull/1662))
* Migrated telemetry APIs from [mobile-events-android](https://github.com/mapbox/mapbox-events-android) to common SDK implementation. ([1672](https://github.com/mapbox/mapbox-maps-android/pull/1672))

## Bug fixes 🐞
* Fix scale bar truncated at high zoom levels near the poles. ([1620](https://github.com/mapbox/mapbox-maps-android/pull/1620))
* Fix broken view annotation positioning when marking them `View.INVISIBLE` and then making `View.VISIBLE`. ([1616](https://github.com/mapbox/mapbox-maps-android/pull/1616))
* Snap puck to north if `puckBearingEnabled` is set to false. ([1635](https://github.com/mapbox/mapbox-maps-android/pull/1635))
* Preserve cached properties if applied to the layer before during the `Style#getLayer` call. ([1622](https://github.com/mapbox/mapbox-maps-android/pull/1622))
* Fix a `NullPointerException` in `StandardGestureListener`, where `MotionEvent` should be nullable. ([1645](https://github.com/mapbox/mapbox-maps-android/pull/1645)), ([1677](https://github.com/mapbox/mapbox-maps-android/pull/1677))
* Fix incorrect `MapView` dimensions after background orientation change. ([1658](https://github.com/mapbox/mapbox-maps-android/pull/1658))
* Fix wrong `BitmapWidget` position when using `WidgetPosition.Horizontal.CENTER` or `WidgetPosition.Vertical.CENTER` property. ([1651](https://github.com/mapbox/mapbox-maps-android/pull/1651))
* Fix pixel flickering between tiles on darker styles in globe view. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix incorrect shading of rounded corners in fill extrusions when ambient occlusion and/or shadow is enabled. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix shadows when the light radial coordinate is zero. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Location indicator layer is occluded by terrain to align rendering behavior with other location indicator rendering types. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Symbol flickering after runtime styling caused by using outdated symbol placement on updated symbol buckets. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Allow Light intensity and Light shadow intensity to accept expressions when using setStyleLight API call. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Fix a bug in `cameraForGeometry` when called on a map with padding set. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))
* Throw an exception with the meaningful stack trace when constructing `Value` from an infinite or NaN number. ([1681](https://github.com/mapbox/mapbox-maps-android/pull/1681))

## Dependencies
* Update mapbox-gestures-android dependency to [v0.8.0](https://github.com/mapbox/mapbox-gestures-android/releases/tag/v0.8.0). ([1645](https://github.com/mapbox/mapbox-maps-android/pull/1645))
* Update gl-native to v10.9.0-beta.1 and common to v23.1.0-beta.1. ([1679](https://github.com/mapbox/mapbox-maps-android/pull/1679))



# 10.8.0 September 7, 2022
## Features ✨ and improvements 🏁
* Introduce a callback to be invoked when the device compass sensors need to be re-calibrated. ([1513](https://github.com/mapbox/mapbox-maps-android/pull/1513))
* Add support for `LocationComponentSettingsInterface.pulsingMaxRadius` to follow location's accuracy radius. ([1561](https://github.com/mapbox/mapbox-maps-android/pull/1561))
* Avoid map content disappearing on the sides of the screen when LOD is enabled. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))

## Bug fixes 🐞
* Support altitude interpolation in location component, and pass through GPS altitude information from the DefaultLocationProvider. ([1478](https://github.com/mapbox/mapbox-maps-android/pull/1478))
* Fix edge cases for renderer that could result in map not rendered. ([1538](https://github.com/mapbox/mapbox-maps-android/pull/1538))
* Fix onAnnotationDragStarted event is still fired when annotation is not draggable. ([1552](https://github.com/mapbox/mapbox-maps-android/pull/1552))
* Fix camera flying away when pitching. ([1560](https://github.com/mapbox/mapbox-maps-android/pull/1560))
* Deliver style to the plugin registry on map start if a new one was loaded after map stop. ([1558](https://github.com/mapbox/mapbox-maps-android/pull/1558))
* Fix default viewport bearing transition doesn't follow shortest path. ([1541](https://github.com/mapbox/mapbox-maps-android/pull/1541))
* Fix `OnFpsChangedListener` listener to count number of frames rendered over the last second instead of immediate time for render call. ([1477](https://github.com/mapbox/mapbox-maps-android/pull/1477))
* Fix `MapView.setMaximumFps` method to apply exact FPS value for rendering the map. ([1477](https://github.com/mapbox/mapbox-maps-android/pull/1477))
* Fix Android memory leak when destroying platform view annotation manager. ([1568](https://github.com/mapbox/mapbox-maps-android/pull/1568))
* Fix style getters for terrain, light and atmosphere resetting properties. ([1573](https://github.com/mapbox/mapbox-maps-android/pull/1573))
* Fix possible ANR when destroying renderer. ([1567](https://github.com/mapbox/mapbox-maps-android/pull/1567))
* Fix `MapSurface#surfaceChanged` to update dimensions for plugins. ([1575](https://github.com/mapbox/mapbox-maps-android/pull/1575))
* Try recreate EGL surface when it throws exception. ([1589](https://github.com/mapbox/mapbox-maps-android/pull/1589))
* Fix `MapboxMap` extension plugin functions throwing exceptions. ([1591](https://github.com/mapbox/mapbox-maps-android/pull/1591))
* Fix concurrent modification exception when using widgets. ([1597](https://github.com/mapbox/mapbox-maps-android/pull/1597))
* User-specified minimum and maximum zoom now correctly adjusted for map size. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Avoid placement of line labels with overlapping glyphs. Fix collision algorithm for the line labels with vertical shift. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix flickering when a vector layer is added on top of a raster layer. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix a rare case when black rectangles appear instead of the images of symbol layers. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix tiles disappearing when high pitch is used and atmosphere is turned on and off. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fixes an issue which prevents the usage of tile cache when changing zoom levels. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Relax LOD requirements for maps with insets and zero terrain exaggeration. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Make CameraManager.setCamera method exception free. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix black holes in the globe view when edge insets are used. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix elevation of pole geometry when exaggerated terrain is used. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))
* Fix a bug in cameraForGeometry returning incorrect camera options when pitch  > 0. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))

## Dependencies

* Bump gl-native to v10.8.0, common to v23.0.0. ([1650](https://github.com/mapbox/mapbox-maps-android/pull/1650))


# 10.8.0-rc.1 August 24, 2022
## Bug fixes 🐞
* Try recreate EGL surface when it throws exception. ([1589](https://github.com/mapbox/mapbox-maps-android/pull/1589))
* Fix `MapboxMap` extension plugin functions throwing exceptions. ([1591](https://github.com/mapbox/mapbox-maps-android/pull/1591))
* Fix concurrent modification exception when using widgets. ([1597](https://github.com/mapbox/mapbox-maps-android/pull/1597))
* Relax LOD requirements for maps with insets and zero terrain exaggeration, leading to more content shown in easily readable map areas. ([1623](https://github.com/mapbox/mapbox-maps-android/pull/1623))
* Make CameraManager.setCamera method exception free. In cases when incorrect CameraOptions are provided, error would be logged. ([1623](https://github.com/mapbox/mapbox-maps-android/pull/1623))
* Fix black holes in the globe view when edge insets are used. ([1623](https://github.com/mapbox/mapbox-maps-android/pull/1623))

## Dependencies
Bump gl-native to v10.8.0-rc.1 and common to v23.0.0-rc.2. ([1623](https://github.com/mapbox/mapbox-maps-android/pull/1623))

# 10.8.0-beta.1 August 11, 2022
## Features ✨ and improvements 🏁
* Introduce a callback to be invoked when the device compass sensors need to be re-calibrated. ([1513](https://github.com/mapbox/mapbox-maps-android/pull/1513))
* Add support for `LocationComponentSettingsInterface.pulsingMaxRadius` to follow location's accuracy radius. ([1561](https://github.com/mapbox/mapbox-maps-android/pull/1561))

## Bug fixes 🐞
* Support altitude interpolation in location component, and pass through GPS altitude information from the DefaultLocationProvider. ([1478](https://github.com/mapbox/mapbox-maps-android/pull/1478))
* Fix edge cases for renderer that could result in map not rendered. ([1538](https://github.com/mapbox/mapbox-maps-android/pull/1538))
* Fix onAnnotationDragStarted event is still fired when annotation is not draggable. ([1552](https://github.com/mapbox/mapbox-maps-android/pull/1552))
* Fix camera flying away when pitching. ([1560](https://github.com/mapbox/mapbox-maps-android/pull/1560))
* Deliver style to the plugin registry on map start if a new one was loaded after map stop. ([1558](https://github.com/mapbox/mapbox-maps-android/pull/1558))
* Fix default viewport bearing transition doesn't follow shortest path. ([1541](https://github.com/mapbox/mapbox-maps-android/pull/1541))
* Fix `OnFpsChangedListener` listener to count number of frames rendered over the last second instead of immediate time for render call. ([1477](https://github.com/mapbox/mapbox-maps-android/pull/1477))
* Fix `MapView.setMaximumFps` method to apply exact FPS value for rendering the map. ([1477](https://github.com/mapbox/mapbox-maps-android/pull/1477))
* Fix a bug in cameraForGeometry returning incorrect camera options when pitch > 0. ([1568](https://github.com/mapbox/mapbox-maps-android/pull/1568))
* Fix Android memory leak when destroying platform view annotation manager. ([1568](https://github.com/mapbox/mapbox-maps-android/pull/1568))
* Fix style getters for terrain, light and atmosphere resetting properties. ([1573](https://github.com/mapbox/mapbox-maps-android/pull/1573))
* Fix possible ANR when destroying renderer. ([1567](https://github.com/mapbox/mapbox-maps-android/pull/1567))
* Fix elevation of pole geometry when exaggerated terrain is used. ([1574](https://github.com/mapbox/mapbox-maps-android/pull/1574))
* Fix `MapSurface#surfaceChanged` to update dimensions for plugins. ([1575](https://github.com/mapbox/mapbox-maps-android/pull/1575))

## Dependencies
Bump gl-native to v10.8.0-beta.2 and common to v23.0.0-beta.1. ([1574](https://github.com/mapbox/mapbox-maps-android/pull/1574), [1568](https://github.com/mapbox/mapbox-maps-android/pull/1568))

# 10.7.0 July 29, 2022
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.6.0...android-v10.7.0) since [Mapbox Maps SDK for Android 10.6.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.6.0)
## Breaking changes ⚠️
* Remove deprecated `FollowPuckViewportStateOptions.animationDurationMs` from experimental viewport plugin. ([1421](https://github.com/mapbox/mapbox-maps-android/pull/1421))
* Remove experimental ModelLayer APIs. ([1545](https://github.com/mapbox/mapbox-maps-android/pull/1545))

# Features ✨ and improvements 🏁
* Optimise the bearing update frequency for the location puck animator. ([1398](https://github.com/mapbox/mapbox-maps-android/pull/1398))
* Use `orientation` model source property to update the 3D puck's bearing, as it is more efficient than updating the `model-rotation` layer property. ([1407](https://github.com/mapbox/mapbox-maps-android/pull/1407))
* Optimize `MapboxMap.loadStyle()` to apply images and models earlier. [1378](https://github.com/mapbox/mapbox-maps-android/pull/1378)
* Remove `MapboxExperimental` annotation from viewport plugin and promote viewport plugin as stable API. ([1425](https://github.com/mapbox/mapbox-maps-android/pull/1425))
* Introduce `addRendererSetupErrorListener`/`removeRendererSetupErrorListener` methods for `MapView` and `MapSurface` to listen to renderer setup errors and give opportunity to control some edge cases. ([1427](https://github.com/mapbox/mapbox-maps-android/pull/1427))
* Introduce transition properties for atmosphere and terrain. ([1451](https://github.com/mapbox/mapbox-maps-android/pull/1451))
* Enable main thread checking on the map/style object when running applications in debug build. This utility class will crash the application if these objects are accessed from a worked thread. It's required to call these object functions on the main thread, otherwise you can hit edge case crashes. This configurations is advised but can be opted out with a Manifest metadata entry of `com.mapbox.maps.ThreadChecker` and corresponding false value. ([1316](https://github.com/mapbox/mapbox-maps-android/pull/1316)).
* Introduce view annotation `ViewAnnotationManager.setViewAnnotationUpdateMode` / `ViewAnnotationManager.getViewAnnotationUpdateMode` API with following synchronization modes: MAP_SYNCHRONIZED (used by default) and MAP_FIXED_DELAY. ([1415](https://github.com/mapbox/mapbox-maps-android/pull/1415))
* Introduce `FillExtrusionLayer.fillExtrusionAmbientOcclusionIntensity` and `FillExtrusionLayer.fillExtrusionAmbientOcclusionRadius` properties for FillExtrusionLayer. ([1458](https://github.com/mapbox/mapbox-maps-android/pull/1458))
* Introduce `PointAnnotation.textLineHeight` and deprecated `PointAnnotationManager.textLineHeight`, as `text-line-height` is data-driven property now. ([1458](https://github.com/mapbox/mapbox-maps-android/pull/1458))
* Method `MapboxMap.cameraForCoordinates` now allows to ignore edges of framing box dynamically depending on the position of the principal point of the camera. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Synchronize volatile data (like traffic tiles) in multi-map environment. Decrease network traffic. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Make uploading of large graphics data asynchronous to improve rendering speed in particular on zooming in/out. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Reuse single index buffer in symbol layer rendering. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Use shared index buffers per tile to reduce the time spent in the upload pass. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Reduce geometry on globe tile to increase rendering performance. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Improve rendering performance with deleting layer render data on a worker thread. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* The deprecated Settings API is using the same non-persistent storage as the SettingsService API from Common SDK so that all settings consolidated in a single place. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Support using `line-trim-offset` property with pure line color. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Render cache and terrain can now have mipmapping enabled to reduce aliasing. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fast ambient occlusion support for fill extrusion layer. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Add API to create tileset descriptor from a tilesets list. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Add API `OfflineManager::createTilesetDescriptor(TileDescriptorOptionsForTilesets)` to create tileset descriptor from a tilesets list. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))

## Bug fixes 🐞
* Fix lifecycle edge cases not being handled properly by introducing internal `ViewLifecycleOwner` to have granular control over MapView's lifecycle. ([1330](https://github.com/mapbox/mapbox-maps-android/pull/1330))
* Fix an issue when `literal` array expression is used as output inside the `match` expression. ([1444](https://github.com/mapbox/mapbox-maps-android/pull/1444))
* Fix skipping gesture updates resulting in slower gestures on low-end devices. ([#1440](https://github.com/mapbox/mapbox-maps-android/pull/1440))
* Fix excessive logs appearing sometimes after `onStop` lifecycle event. ([1527](https://github.com/mapbox/mapbox-maps-android/pull/1527))
* Fix `com.mapbox.maps.MapboxMapException` crash on style load. ([1532](https://github.com/mapbox/mapbox-maps-android/pull/1532))
* Avoid NaN when converting screen coordinates to geographical coordinates executed as part of gesture. [1491](https://github.com/mapbox/mapbox-maps-android/pull/1491)
* Remove android.permission.WAKE_LOCK permission from the SDK. ([1494](https://github.com/mapbox/mapbox-maps-android/pull/1494))
* Fixes a bug when map aligned symbol layers are placed on top of other layers if draping is active. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix tile flickering with globe on rapid zooming in/out. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fixed `cameraForCoordinateBounds` method returning different values for the same input. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix setting `exaggeration-transition` property via `setStyleTerrain` API. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix SDK fragment format in turnstile user agent. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix view annotation occlusion issue when terrain enabled. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix symbol flickering issue when  `textAllowOverlap` or `iconAllowOverlap` is true. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fixes rendering issues with the globe on unsupported hardware by falling back to mercator projection. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fixed feature states not being applied on new tiles when zoom doesn't change. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Disable MapBuffer OpenGL extension on PowerVR SGX 544MP GPUs to fix incorrect usage of unimplemented methods. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix incorrect image source rendering with terrain enabled. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix possible crash bug in image processing. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix some cpu-updated symbols being invisible in globe view. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix zoom constraining issue when the input `maxZoom` is smaller than the current `minZoom` value. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix crash on calling Query Rendered Features API from renderer thread before initialising the renderer. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix tile pre-fetching for the globe map projection. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Relayout tiles after recovering from Metal rendering errors. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))
* Fix a bug where changing size of the map would lead map center getting changed as well. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))

## Dependencies
* Bump telemetry to [v8.1.5](https://github.com/mapbox/mapbox-events-android/releases/tag/telem-8.1.5-core-5.0.2). ([1494](https://github.com/mapbox/mapbox-maps-android/pull/1494))
  Also bumps [WorkManager 2.7.1](https://developer.android.com/jetpack/androidx/releases/work#2.7.1) that enforces compileSdk 31 or newer.
* Bump gl-native to v10.7.0, common to 22.1.0. ([1543](https://github.com/mapbox/mapbox-maps-android/pull/1543))

# 10.7.0-rc.1 July 14, 2022
## Features ✨ and improvements 🏁
* Reuse single index buffer in symbol layer rendering. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Use shared index buffers per tile to reduce the time spent in the upload pass. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))

## Bug fixes 🐞
* Remove android.permission.WAKE_LOCK permission from the SDK. ([1494](https://github.com/mapbox/mapbox-maps-android/pull/1494))
* Fix setting 'exaggeration-transition' property via 'setStyleTerrain' API. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Fix SDK fragment format in turnstile useragent. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Fix view annotation occlusion issue when Terrain enabled. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Fix symbol flickering issue when 'textAllowOverlap' or 'iconAllowOverlap' is true. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Fixes rendering issues with the globe on unsupported hardware by falling back to mercator projection. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Fixed feature states not being applied on new tiles when zoom doesn't change. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Disable MapBuffer OpenGL extension on PowerVR SGX 544MP GPUs to fix incorrect usage of unimplemented methods. ([1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))
* Avoid NaN when converting screen coordinates to geographical coordinates executed as part of gesture. [#1491](https://github.com/mapbox/mapbox-maps-android/pull/1491)

## Dependencies
* Bump telemetry to [v8.1.5](https://github.com/mapbox/mapbox-events-android/releases/tag/telem-8.1.5-core-5.0.2). ([#1494](https://github.com/mapbox/mapbox-maps-android/pull/1494))
  Also bumps [WorkManager 2.7.1](https://developer.android.com/jetpack/androidx/releases/work#2.7.1) that enforces compileSdk 31 or newer.
* Bump gl-native to v10.7.0-rc.1, common to 22.1.0-rc.1. ([#1497](https://github.com/mapbox/mapbox-maps-android/pull/1497))

# 10.7.0-beta.1 June 29, 2022
## Breaking changes ⚠️
* Remove deprecated `FollowPuckViewportStateOptions.animationDurationMs` from experimental viewport plugin. ([1421](https://github.com/mapbox/mapbox-maps-android/pull/1421))

## Features ✨ and improvements 🏁
* Optimise the bearing update frequency for the location puck animator. ([1398](https://github.com/mapbox/mapbox-maps-android/pull/1398))
* Use `orientation` model source property to update the 3D puck's bearing, as it is more efficient than updating the `model-rotation` layer property. ([1407](https://github.com/mapbox/mapbox-maps-android/pull/1407))
* Optimize `MapboxMap.loadStyle()` to apply images and models earlier. [#1378](https://github.com/mapbox/mapbox-maps-android/pull/1378)
* Remove `MapboxExperimental` annotation from viewport plugin and promote viewport plugin as stable API. ([1425](https://github.com/mapbox/mapbox-maps-android/pull/1425))
* Introduce `addRendererSetupErrorListener`/`removeRendererSetupErrorListener` methods for `MapView` and `MapSurface` to listen to renderer setup errors and give opportunity to control some edge cases. ([1427](https://github.com/mapbox/mapbox-maps-android/pull/1427))
* Introduce transition properties for atmosphere and terrain. ([1451](https://github.com/mapbox/mapbox-maps-android/pull/1451))
* Enable main thread checking on the map/style object when running applications in debug build. This utility class will crash the application if these objects are accessed from a worked thread. It's required to call these object functions on the main thread, otherwise you can hit edge case crashes. This configurations is advised but can be opted out with a Manifest metadata entry of `com.mapbox.maps.ThreadChecker` and corresponding false value. ([#1316](https://github.com/mapbox/mapbox-maps-android/pull/1316)).
* Introduce view annotation `ViewAnnotationManager.setViewAnnotationUpdateMode` / `ViewAnnotationManager.getViewAnnotationUpdateMode` API with following synchronization modes: MAP_SYNCHRONIZED (used by default) and MAP_FIXED_DELAY. ([1415](https://github.com/mapbox/mapbox-maps-android/pull/1415))
* Reduce geometry on globe tile to increase rendering performance. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Improve rendering performance with deleting layer render data on a worker thread. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Support using `line-trim-offset` property with pure line color. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Render cache and Terrain can now have mipmapping enabled to reduce aliasing. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fast ambient occlusion support for fill extrusion layer. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Refactor view annotation implementation to align map and annotation movement better when camera changes. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Add API `OfflineManager::createTilesetDescriptor(TileDescriptorOptionsForTilesets)` to create tileset descriptor from a tilesets list. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Introduce `FillExtrusionLayer.fillExtrusionAmbientOcclusionIntensity` and `FillExtrusionLayer.fillExtrusionAmbientOcclusionRadius` properties for FillExtrusionLayer. ([1458](https://github.com/mapbox/mapbox-maps-android/pull/1458))
* Introduce `PointAnnotation.textLineHeight` and deprecated `PointAnnotationManager.textLineHeight`, as `text-line-height` is data-driven property now. ([1458](https://github.com/mapbox/mapbox-maps-android/pull/1458))

## Bug fixes 🐞
* Fix lifecycle edge cases not being handled properly by introducing internal `ViewLifecycleOwner` to have granular control over MapView's lifecycle. ([1330](https://github.com/mapbox/mapbox-maps-android/pull/1330))
* Fix an issue when `literal` array expression is used as output inside the `match` expression. ([1444](https://github.com/mapbox/mapbox-maps-android/pull/1444))
* Fix skipping gesture updates resulting in slower gestures on low-end devices. ([#1440](https://github.com/mapbox/mapbox-maps-android/pull/1440))
* Fix incorrect image source rendering with terrain enabled. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix possible crash bug in image processing. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix some cpu-updated symbols being invisible in globe view. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Add support for terrain transition properties. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix zoom constraining issue when the input 'maxZoom' is smaller than the current 'minZoom' value. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix crash on calling Query Rendered Features API from renderer thread before initialising the renderer. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix tile pre-fetching for the globe map projection. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))
* Fix a bug where changing size of the map would lead map center getting changed as well. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))

## Dependencies
* Bump gl-native to v10.7.0-beta.1, common to 22.1.0-beta.1. ([#1462](https://github.com/mapbox/mapbox-maps-android/pull/1462))

# 10.6.0 - June 16, 2022
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.5.0...android-v10.6.0) since [Mapbox Maps SDK for Android 10.5.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.5.0)
## Breaking changes ⚠️
* Remove deprecated (since `v10.5.0`) experimental methods `MapboxMap.setMapProjection`/`MapboxMap.getMapProjection`. Those methods should be replaced with setting projection as part of Style DSL to achieve the same behavior. ([1420](https://github.com/mapbox/mapbox-maps-android/pull/1420))

## Features ✨ and improvements 🏁
* Enable support for incremental annotation processing. ([#1323](https://github.com/mapbox/mapbox-maps-android/pull/1323))
* Make use of non-deprecated common Mapbox logger. ([#1327](https://github.com/mapbox/mapbox-maps-android/pull/1327))
* Expose factory methods for high-level camera animators. ([#1338](https://github.com/mapbox/mapbox-maps-android/pull/1338))
* Introduce map atmosphere and fog. Setting atmosphere and fog supports Style DSL as well. ([#1344](https://github.com/mapbox/mapbox-maps-android/pull/1344))
* Introduce experimental 3D model support. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Use a single shared buffer across all globe tiles to increase globe rendering performance. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Re-introduce partial tile loading feature that decreases map load times. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* The `TilesetDescriptorOptions.pixelRatio` parameter is now passed to the TileStore and considered for the raster tile pack loading. This enables loading of a raster tilepacks for retina displays. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Introduce pinchScrollEnabled configuration to enable/disable 2-finger map panning, default to true. ([#1343](https://github.com/mapbox/mapbox-maps-android/pull/1343))
* Re-throw native exceptions `jni::PendingJavaException` as readable Java exceptions with detailed exception text. ([#1363](https://github.com/mapbox/mapbox-maps-android/pull/1363))
* Add static `MapView.isTerrainRenderingSupported()` method to validate if 3D terrain rendering is supported on given device. ([1368](https://github.com/mapbox/mapbox-maps-android/pull/1368))
* Optimize `MapboxMap.loadStyle()` to apply styling properties earlier. [#1362](https://github.com/mapbox/mapbox-maps-android/pull/1362)
* Update SDK name in attribution action sheet. ([1375](https://github.com/mapbox/mapbox-maps-android/pull/1375))
* Introduce experimental ModelLayer API to render 3D models on the map. ([#1369](https://github.com/mapbox/mapbox-maps-android/pull/1369))
* Minimise tiles relayout on camera zooming with globe view. Improve the overall performance and reduce latency of the cached tiles appearance. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Add minimum and maximum range check for sky layer property `sky-atmosphere-sun` and `sky-gradient-center`. If the input is invalid, the default property value will be used. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Share render data between vector render tiles referring to the same logical tile. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Reduce geometry on globe tile to increase rendering performance. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))

## Bug fixes 🐞
* Enable two finger pan gesture. ([#1280](https://github.com/mapbox/mapbox-maps-android/pull/1280))
* Fix a bug that scale bar is shorter than it should be; trigger `invalidateScaleBar` while updating settings to make scale keep the latest status. ([#1336](https://github.com/mapbox/mapbox-maps-android/pull/1336))
* Keep the original animator owner for `CameraAnimationsPlugin.playAnimatorsTogether` and `CameraAnimationsPlugin.playAnimatorsSequentially`. ([#1345](https://github.com/mapbox/mapbox-maps-android/pull/1345))
* Fix for momentary appearing of a lower zoom level tile labels during camera movement.  ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix for location indicator not being rendered at the horizon when terrain is enabled. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix for loading gltf models with interleaved buffers. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix a bug that `line-trim-offset` input may lose precision via shader calculation. ([#1359](https://github.com/mapbox/mapbox-maps-android/pull/1359))
* Add mercator scale factor to 3D location puck, so that the 3D puck size won't increase as latitude increases. ([#1350](https://github.com/mapbox/mapbox-maps-android/pull/1350)
* Fix a crash due to invalid focal point when panning the map. ([#1364](https://github.com/mapbox/mapbox-maps-android/pull/1364))
* Fix compass is not showing in "edge-to-edge" mode. ([1391](https://github.com/mapbox/mapbox-maps-android/pull/1391))
* Fix an unexpected request update delay for tiles taken from in-memory cache when minimumTileUpdateInterval is set. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix a rare bug where some tiles would not show up correctly on globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix a bug where features could be queried without cursor intersecting the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix view annotations disappearing on the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix symbols ignoring both collision and placement on the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Add anti-aliasing on the globe on low zoom levels. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix globe controls when map orientation is something else than "north". ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix circle and heatmap layers not being aligned with globe's surface. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Optimise the frequency to update location layer's visibility. ([1399](https://github.com/mapbox/mapbox-maps-android/pull/1399))
* Fix the delay in the first appearance of the location puck. ([1403](https://github.com/mapbox/mapbox-maps-android/pull/1403))
* Remove `android.permission.GET_TASKS` permission from the SDK. ([1430](https://github.com/mapbox/mapbox-maps-android/pull/1430))
* Fix lag during night/day style switching when globe is enabled. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))
* Fix crash on globe symbol placement that happens during style change. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))
* Fix incorrect image source rendering with terrain enabled. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))
* Fix in-memory tile cache for non-geometry tiles when the map projection changes. Before, the tiles cached while the previous projection was active could not be used with the new projection active. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))

## Dependencies
* Bump Mapbox Android base library to v0.8.0. ([#1323](https://github.com/mapbox/mapbox-maps-android/pull/1323))
* Bump gl-native to v10.6.0, common to 22.0.0. ([#1432](https://github.com/mapbox/mapbox-maps-android/pull/1432))
* Bump telemetry to [v8.1.3](https://github.com/mapbox/mapbox-events-android/releases/tag/telem-8.1.3-core-5.0.2), android core to [5.0.2](https://github.com/mapbox/mapbox-events-android/releases/tag/core-5.0.2). ([1430](https://github.com/mapbox/mapbox-maps-android/pull/1430))

# 10.6.0-rc.1 June 2, 2022

## Features ✨ and improvements 🏁
* Minimise tiles relayout on camera zooming with globe view. Improve the overall performance and reduce latency of the cached tiles appearance. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Add minimum and maximum range check for sky layer property `sky-atmosphere-sun` and `sky-gradient-center`. If the input is invalid, the default property value will be used. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Share render data between vector render tiles referring to the same logical tile. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))

## Bug fixes 🐞
* Fix compass is not showing in "edge-to-edge" mode. ([1391](https://github.com/mapbox/mapbox-maps-android/pull/1391))
* Fix an unexpected request update delay for tiles taken from in-memory cache when minimumTileUpdateInterval is set. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix a rare bug where some tiles would not show up correctly on globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix a bug where features could be queried without cursor intersecting the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix view annotations disappearing on the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix symbols ignoring both collision and placement on the globe. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Add anti-aliasing on the globe on low zoom levels. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix globe controls when map orientation is something else than "north". ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))
* Fix circle and heatmap layers not being aligned with globe's surface. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))

## Dependencies
* Bump gl-native to v10.6.0-rc.1, common to 22.0.0-rc.2. ([#1396](https://github.com/mapbox/mapbox-maps-android/pull/1396))

# 10.6.0-beta.2 May 25, 2022

## Features ✨ and improvements 🏁
* Update SDK name in attribution action sheet. ([1375](https://github.com/mapbox/mapbox-maps-android/pull/1375))
* Introduce experimental ModelLayer API to render 3D models on the map. ([#1369](https://github.com/mapbox/mapbox-maps-android/pull/1369))

# 10.6.0-beta.1 May 19, 2022

## Features ✨ and improvements 🏁
* Enable support for incremental annotation processing. ([#1323](https://github.com/mapbox/mapbox-maps-android/pull/1323))
* Make use of non-deprecated common Mapbox logger. ([#1327](https://github.com/mapbox/mapbox-maps-android/pull/1327))
* Expose factory methods for high-level camera animators. ([#1338](https://github.com/mapbox/mapbox-maps-android/pull/1338))
* Introduce map atmosphere and fog. Setting atmosphere and fog supports Style DSL as well. ([#1344](https://github.com/mapbox/mapbox-maps-android/pull/1344))
* Introduce experimental 3D model support. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Use a single shared buffer across all globe tiles to increase globe rendering performance. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Re-introduce partial tile loading feature that decreases map load times. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* The `TilesetDescriptorOptions.pixelRatio` parameter is now passed to the TileStore and considered for the raster tile pack loading. This enables loading of a raster tilepacks for retina displays. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Introduce pinchScrollEnabled configuration to enable/disable 2-finger map panning, default to true. ([#1343](https://github.com/mapbox/mapbox-maps-android/pull/1343))
* Re-throw native exceptions `jni::PendingJavaException` as readable Java exceptions with detailed exception text. ([#1363](https://github.com/mapbox/mapbox-maps-android/pull/1363))
* Add static `MapView.isTerrainRenderingSupported()` method to validate if 3D terrain rendering is supported on given device. ([1368](https://github.com/mapbox/mapbox-maps-android/pull/1368))
* Optimize `MapboxMap.loadStyle()` to apply styling properties earlier. [#1362](https://github.com/mapbox/mapbox-maps-android/pull/1362)

## Bug fixes 🐞
* Enable two finger pan gesture. ([#1280](https://github.com/mapbox/mapbox-maps-android/pull/1280))
* Fix a bug that scale bar is shorter than it should be; trigger `invalidateScaleBar` while updating settings to make scale keep the latest status. ([#1336](https://github.com/mapbox/mapbox-maps-android/pull/1336))
* Keep the original animator owner for `CameraAnimationsPlugin.playAnimatorsTogether` and `CameraAnimationsPlugin.playAnimatorsSequentially`. ([#1345](https://github.com/mapbox/mapbox-maps-android/pull/1345))
* Fix for momentary appearing of a lower zoom level tile labels during camera movement.  ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix for location indicator not being rendered at the horizon when terrain is enabled. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix for loading gltf models with interleaved buffers. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351))
* Fix a bug that `line-trim-offset` input may lose precision via shader calculation. ([#1359](https://github.com/mapbox/mapbox-maps-android/pull/1359))
* Add mercator scale factor to 3D location puck, so that the 3D puck size won't increase as latitude increases. ([#1350](https://github.com/mapbox/mapbox-maps-android/pull/1350)
* Fix a crash due to invalid focal point when panning the map. ([#1364](https://github.com/mapbox/mapbox-maps-android/pull/1364))

## Dependencies
* Bump Mapbox Android base library to v0.8.0. ([#1323](https://github.com/mapbox/mapbox-maps-android/pull/1323))
* Bump gl-native to v10.6.0-beta.3, common to 22.0.0-beta.1. ([#1351](https://github.com/mapbox/mapbox-maps-android/pull/1351), [#1354](https://github.com/mapbox/mapbox-maps-android/pull/1354), [#1359](https://github.com/mapbox/mapbox-maps-android/pull/1359))

# 10.4.4 May 12, 2022

### Bug fixes 🐞
* Fix NaN latitude native crash rarely happening during `MapboxMap#flyTo`. ([#1271](https://github.com/mapbox/mapbox-maps-android/pull/1271))

# 10.5.0 May 4, 2022

## Features ✨ and improvements 🏁
* Make map projection part of the style-spec and introduce new methods `StyleInterface.setProjection` / `StyleInterface.getProjection`. Setting projection supports Style DSL as well. ([#1255](https://github.com/mapbox/mapbox-maps-android/pull/1255), [#1314](https://github.com/mapbox/mapbox-maps-android/pull/1314))
* Automatic transition between the globe and mercator projection updated to appear visually more subtle. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* Avoid repeated tile loading from network (or repeated tile decompression when the tile is fetched from the cache database) and repeated vector tile data allocation and parsing when loading render tiles referring to the same logical tile. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* Switch to use shader to calculate the 'line-trim-offset' property update. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* Layer properties transitions performance improved if the layer is transitioning to the same constant value or if transitioning from/to data-driven property. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* New line layer paint property introduced: '{"line-trim-offset", [trim-start, trim-end]}', to take the line trim-off percentage range based on the whole line range [0.0, 1.0]. The property will only be effective when 'line-gradient' property is set. The line part between [trim-start, trim-end] will be marked as transparent to make a line gradient a vanishing effect. If either 'trim-start' or 'trim-end' offset is out of valid range, the default range [0.0, 0.0] will be set. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* Globe view controls revamped for more intuitive interaction with touch controls. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* OfflineRegion::getStatus() API added to get the completion status and the local size of the existing legacy offline regions. ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315))
* Refactor all Mapbox logs so that Logcat tag will always be 'Mapbox' allowing easier filtering. Previous log tag will become part of the log message now. ([#1276](https://github.com/mapbox/mapbox-maps-android/pull/1276))
* Optimize how plugins handle settings changes. Call `applySettings` only when settings value changes. ([#1189](https://github.com/mapbox/mapbox-maps-android/pull/1189))
* Add `MapboxMap.isValid()` and `Style.isValid()` methods. `MapboxMap` becomes invalid when `MapView.onDestroy()` is called. `Style` becomes invalid when `MapView.onDestroy()` is called or new style has been loaded. Accessing any method on invalid object will print an error log. Also unsubscribe map observers automatically when `MapboxMap.onDestroy()` is invoked. ([1193](https://github.com/mapbox/mapbox-maps-android/pull/1193)) ([1202](https://github.com/mapbox/mapbox-maps-android/pull/1202) ([1230](https://github.com/mapbox/mapbox-maps-android/pull/1230)) ([1241](https://github.com/mapbox/mapbox-maps-android/pull/1241)))
* Add `MapboxMap.coordinateBoundsForCameraUnwrapped` method for API consistency. ([1222](https://github.com/mapbox/mapbox-maps-android/pull/1222))
* Add `LocationIndicatorLayer.bearingTransition` API to control transition of bearing property. ([1207](https://github.com/mapbox/mapbox-maps-android/pull/1207))
* Add `MapboxConcurrentGeometryModificationException` with detailed information instead of `ConcurrentModificationException` that is thrown when GeoJson data is mutated. ([1248](https://github.com/mapbox/mapbox-maps-android/pull/1248))
* Introduce `line-trim-offset` property for LineLayer. ([1252](https://github.com/mapbox/mapbox-maps-android/pull/1252))
* Deprecate `FollowPuckViewportStateOptions.animationDurationMs`, the initial transition will be handled properly by the Viewport plugin internally. ([1256](https://github.com/mapbox/mapbox-maps-android/pull/1256), [1261](https://github.com/mapbox/mapbox-maps-android/pull/1261), [1262](https://github.com/mapbox/mapbox-maps-android/pull/1262))
* Mark `MapView.viewAnnotationManager` as non-experimental meaning View Annotation API will not have breaking changes in upcoming minor releases. ([1260](https://github.com/mapbox/mapbox-maps-android/pull/1260))

## Bug fixes 🐞
* Fix geojson missing updates with persistent layer after style change. ([#1324](https://github.com/mapbox/mapbox-maps-android/pull/1324))
* Fix render tasks being skipped when creating the map that could lead to missing tiles. ([#1304](https://github.com/mapbox/mapbox-maps-android/pull/1304))
* The legacy offline region observer instance is not unnecessarily retained inside the engine. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fix a bug of querying rendered feature for circle layer with map-pitch-alignment when the pitch is zero. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fix a bug where zooming was not possible with terrain enabled and exaggeration 0. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fix an issue where internal hsla() function was converted to an invalid rgba expression. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fix a bug that 'line-trim-offset' calculation did not property cover 'round' or 'square' line cap in line ends. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Dispatched in-flight events will not be delivered if 'unsubscribe' is called before an event is delivered. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fix an issue where some of the visible tiles could be erroneously culled during transition between globe and mercator projection. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fixes issues where camera appears under terrain, or map gets bumpy repositioning after exaggeration change. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Disable terrain rendering if GPU does not support Vertex Texture Fetch. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fixed a bug that occasionally prevents symbols from loading. [#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315)
* Fixed a bug that causes line layers to flicker. [#1325](https://github.com/mapbox/mapbox-maps-android/pull/1325)
* Fix NaN latitude native crash rarely happening during `MapboxMap.flyTo`. ([#1271](https://github.com/mapbox/mapbox-maps-android/pull/1271))
* Limit `MapboxMap.pixelForCoordinate` to the bounds of MapView. ([#1226](https://github.com/mapbox/mapbox-maps-android/pull/1226))
* Fix PolygonAnnotation and PolylineAnnotation being distorted while dragging with 3D terrain. ([#1223](https://github.com/mapbox/mapbox-maps-android/pull/1223))

## Dependencies
* Bump gl-native to v10.5.1, mapbox-common to v21.3.1 ([#1315](https://github.com/mapbox/mapbox-maps-android/pull/1315), [#1325](https://github.com/mapbox/mapbox-maps-android/pull/1325))

# 10.4.3 April 27, 2022

### Bug fixes 🐞
* Fix render tasks being skipped when creating the map that could lead to missing tiles. ([#1304](https://github.com/mapbox/mapbox-maps-android/pull/1304))

# 10.5.0-rc.1 April 20, 2022
## Features ✨ and improvements 🏁
* Refactor all Mapbox logs so that Logcat tag will always be 'Mapbox' allowing easier filtering. Previous log tag will become part of the log message now. ([#1276](https://github.com/mapbox/mapbox-maps-android/pull/1276))
* Avoid repeated tile loading from network (or repeated tile decompression when the tile is fetched from the cache database) and repeated vector tile data allocation and parsing when loading render tiles referring to the same logical tile. ([#1282](https://github.com/mapbox/mapbox-maps-android/pull/1282))
* Switch to use shader to calculate the 'line-trim-offset' property update. ([#1282](https://github.com/mapbox/mapbox-maps-android/pull/1282))

## Bug fixes 🐞
* Fix issue where internal hsla() function was converted to an invalid rgba expression. ([#1282](https://github.com/mapbox/mapbox-maps-android/pull/1282))
* Fix a bug that 'line-trim-offset' calculation did not property cover 'round' or 'square' line cap in line ends. ([#1282](https://github.com/mapbox/mapbox-maps-android/pull/1282))
* Fix NaN latitude native crash rarely happening during `MapboxMap#flyTo`. ([#1271](https://github.com/mapbox/mapbox-maps-android/pull/1271))
* Limit `MapboxMap#pixelForCoordinate` to the bounds of MapView. ([#1226](https://github.com/mapbox/mapbox-maps-android/pull/1226))

## Dependencies
* Bump gl-native to v10.5.0-rc.1, mapbox-common to v21.3.0-rc.2. ([#1282](https://github.com/mapbox/mapbox-maps-android/pull/1282))

# 10.4.2 April 13, 2022
### Bug fixes 🐞
* [tile store] Correctly decode compressed content if loaded from the cache. ([#1279](https://github.com/mapbox/mapbox-maps-android/pull/1279))
* [tile store] Fixed issue that prevented data blobs larger than 1 MB to be transferred via the service. ([#1279](https://github.com/mapbox/mapbox-maps-android/pull/1279))

# 10.5.0-beta.1 April 7, 2022
## Breaking changes ⚠️
* Experimental methods `MapboxMap#setMapProjection` / `MapboxMap#getMapProjection` are removed and should be replaced with `StyleInterface#setProjection` / `StyleInterface#getProjection`. Setting projection supports Style DSL as well. ([#1255](https://github.com/mapbox/mapbox-maps-android/pull/1255))

## Features ✨ and improvements 🏁
* Optimize how plugins handle settings changes. Call `applySettings` only when settings value changes. ([#1189](https://github.com/mapbox/mapbox-maps-android/pull/1189))
* Add `MapboxMap.isValid()` and `Style.isValid()` methods. `MapboxMap` becomes invalid when `MapView.onDestroy()` is called. `Style` becomes invalid when `MapView.onDestroy()` is called or new style has been loaded. Accessing any method on invalid object will print an error log. Also unsubscribe map observers automatically when `MapboxMap.onDestroy()` is invoked. ([1193](https://github.com/mapbox/mapbox-maps-android/pull/1193)) ([1202](https://github.com/mapbox/mapbox-maps-android/pull/1202) ([1230](https://github.com/mapbox/mapbox-maps-android/pull/1230)) ([1241](https://github.com/mapbox/mapbox-maps-android/pull/1241)))
* Add `MapboxMap#coordinateBoundsForCameraUnwrapped` method for API consistency. ([1222](https://github.com/mapbox/mapbox-maps-android/pull/1222))
* Add `LocationIndicatorLayer.bearingTransition` API to control transition of bearing property. ([1207](https://github.com/mapbox/mapbox-maps-android/pull/1207))
* Add `MapboxConcurrentGeometryModificationException` with detailed information instead of `ConcurrentModificationException` that is thrown when GeoJson data is mutated. ([1248](https://github.com/mapbox/mapbox-maps-android/pull/1248))
* Introduce `line-trim-offset` property for LineLayer. ([1252](https://github.com/mapbox/mapbox-maps-android/pull/1252))
* Deprecate `FollowPuckViewportStateOptions.animationDurationMs`, the initial transition will be handled properly by the Viewport plugin internally. ([1256](https://github.com/mapbox/mapbox-maps-android/pull/1256), [1261](https://github.com/mapbox/mapbox-maps-android/pull/1261), [1262](https://github.com/mapbox/mapbox-maps-android/pull/1262))
* Mark `MapView#viewAnnotationManager` as non-experimental meaning View Annotation API will not have breaking changes in upcoming minor releases. ([1260](https://github.com/mapbox/mapbox-maps-android/pull/1260))
* Map render call optimized further by further reducing computational overhead. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Layer properties transitions performance improved if the layer is transitioning to the same constant value or if transitioning from/to data-driven property. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* New line layer paint property introduced: '{"line-trim-offset", [trim-start, trim-end]}', to take the line trim-off percentage range based on the whole line range [0.0, 1.0]. The property will only be effective when 'line-gradient' property is set. The line part between [trim-start, trim-end] will be marked as transparent to make a line gradient a vanishing effect. If either 'trim-start' or 'trim-end' offset is out of valid range, the default range [0.0, 0.0] will be set. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Globe view controls revamped for more intuitive interaction with touch controls. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* OfflineRegion::getStatus() API added to get the completion status and the local size of the existing legacy offline regions. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Automatic transition between the globe and mercator projection updated to appear visually more subtle. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))

## Bug fixes 🐞
* Dispatched in-flight events will not be delivered if 'unsubscribe' is called before an event is delivered. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Transitions between globe and mercator projection do not cull tiles incorrectly anymore. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Map LOD disabled for camera pitch less than 30 degrees to avoid map content missing on maps with insets. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Terrain-related camera issues fixed, previously making it appear under terrain, or incorrectly repositioned after exaggeration change. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Terrain rendering disabled on GPUs not supporting Vertex Texture Fetch. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Fixed a bug that occasionally prevented symbols from loading. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))
* Fix PolygonAnnotation and PolylineAnnotation being distorted while dragging with 3D terrain. ([#1223](https://github.com/mapbox/mapbox-maps-android/pull/1223))

## Dependencies
* Bump gl-native to v10.5.0-beta.1, mapbox-common to v21.3.0-beta.2. ([#1244](https://github.com/mapbox/mapbox-maps-android/pull/1244))

# 10.4.1 April 7, 2022
## Bug fixes 🐞
* Re-introduce the API to get the status of the existing offline regions, so that the clients can get the completion status and the local size of the existing legacy offline regions. ([#1263](https://github.com/mapbox/mapbox-maps-android/pull/1263))
* Fix a bug that occasionally prevents symbols from loading. ([#1263](https://github.com/mapbox/mapbox-maps-android/pull/1263))

## Dependencies
* Bump gl-native to v10.4.2 ([#1263](https://github.com/mapbox/mapbox-maps-android/pull/1263))

# 10.4.0 March 23, 2022
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.3.0...android-v10.4.0) since [Mapbox Maps SDK for Android 10.3.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.3.0)
## Features ✨ and improvements 🏁
* Refactor scheduling logic for render thread improving rendering performance. ([#1068](https://github.com/mapbox/mapbox-maps-android/pull/1068))
* Add LocationCompassEngine and accuracy radius support for location component plugin. ([#1016](https://github.com/mapbox/mapbox-maps-android/pull/1016)) ([#1131](https://github.com/mapbox/mapbox-maps-android/pull/1131))
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
* Add support for custom widgets rendered on top of the map. ([#1036](https://github.com/mapbox/mapbox-maps-android/pull/1036))
* Expose DefaultLocationProvider as public class. ([#1168](https://github.com/mapbox/mapbox-maps-android/pull/1168))
* Add new methods to View Annotation API: `ViewAnnotationManager.removeAllViewAnnotations()` and `ViewAnnotationManager.addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)` / `ViewAnnotationManager.removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)`. ([#1165](https://github.com/mapbox/mapbox-maps-android/pull/1165))
* Add optional `TransitionOptions` parameter to `MapboxMap.loadStyleUri`, `MapboxMap.loadStyleJson`, `MapboxMap.loadStyle` to apply transition to style being loaded. ([#1174](https://github.com/mapbox/mapbox-maps-android/pull/1174))
* Rendering performance improvements. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Add support for 3D terrain tilepacks. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Add `Style#hasStyleImage` method that checks whether an image is in the style. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))

## Bug fixes 🐞
* Fix skipping / crashing user events scheduled on a render thread with `MapView#queueEvent`. ([#1068](https://github.com/mapbox/mapbox-maps-android/pull/1068))
* Fix location puck not being shown if map is created without initial style (e.g. MapInitOptions.styleUri == null) and then loaded asynchronously. ([#1114](https://github.com/mapbox/mapbox-maps-android/pull/1114))
* Fix crash within location plugin that happens when style is reloaded simultaneously with location plugin updates. ([#1112](https://github.com/mapbox/mapbox-maps-android/pull/1112))
* Fix memory leak in location component. ([#1093](https://github.com/mapbox/mapbox-maps-android/pull/1093), [#1172](https://github.com/mapbox/mapbox-maps-android/pull/1172))
* Fix bearing of the puck reseted on settings change. ([#1144](https://github.com/mapbox/mapbox-maps-android/pull/1144))
* Fix an issue when user subscribe sdk listeners multiple times, by changing CopyOnWriteArrayList to CopyOnWriteArraySet in the sdk to hold listeners. ([1183](https://github.com/mapbox/mapbox-maps-android/pull/1183))
* Fix an issue when label list is used within the match expression DSL. ([1204](https://github.com/mapbox/mapbox-maps-android/pull/1204))
* Fix too small bounds returned by `Map#latLngBoundsZoomForCamera` with tilted view and `Map#latLngBoundsZoomForCameraUnwrapped` when viewing >360° longitude. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Fix screen coordinate queries when using zero pitch and high zoom values. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Fix crash for the case when a map event is handled by an Observer of a destructed Map. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Fix shimmering artifact when pitched raster tiles with compressed textures are rendered. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Avoid possible crash at program exit caused by dummy tracer accessed after the destruction. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Clearing diffuse shaded flag for 3D puck when set by batched 3D rendering. ([1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))

## Dependencies
* Bump gl-native to v10.4.1, mapbox-common to v21.2.0 ([#1215](https://github.com/mapbox/mapbox-maps-android/pull/1215))
* Update android gradle plugin to v7.0.4, gradle version to v7.0.2, Gradle licence plugin to 0.8.80, Kotlin gradle plugin to 1.5.31, Jacoco to 0.8.7. ([#1118](https://github.com/mapbox/mapbox-maps-android/pull/1118))

# 10.4.0-rc.1 March 9, 2022

## Bug fixes 🐞
* Fix an issue when user subscribe sdk listeners multiple times, by changing CopyOnWriteArrayList to CopyOnWriteArraySet in the sdk to hold listeners. ([1183](https://github.com/mapbox/mapbox-maps-android/pull/1183))
* Fix an issue when label list is used within the match expression DSL. ([1204](https://github.com/mapbox/mapbox-maps-android/pull/1204))
* Fixed an issue where small bounds returned by Map::latLngBoundsZoomForCamera with tilted view and Map::latLngBoundsZoomForCameraUnwrapped when viewing >360° longitude. ([#1208](https://github.com/mapbox/mapbox-maps-android/pull/1208))

## Dependencies
* Bump gl-native to 10.4.0-rc.1, mapbox-common to v21.2.0-rc.1 ([#1208](https://github.com/mapbox/mapbox-maps-android/pull/1208))

# 10.4.0-beta.1 February 24, 2022

## Features ✨ and improvements 🏁
* Refactor scheduling logic for render thread improving rendering performance. ([#1068](https://github.com/mapbox/mapbox-maps-android/pull/1068))
* Add LocationCompassEngine and accuracy radius support for location component plugin. ([#1016](https://github.com/mapbox/mapbox-maps-android/pull/1016)) ([#1131](https://github.com/mapbox/mapbox-maps-android/pull/1131))
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
* Add support for custom widgets rendered on top of the map. ([#1036](https://github.com/mapbox/mapbox-maps-android/pull/1036))
* Expose DefaultLocationProvider as public class. ([#1168](https://github.com/mapbox/mapbox-maps-android/pull/1168))
* Add new methods to View Annotation API: `ViewAnnotationManager.removeAllViewAnnotations()` and `ViewAnnotationManager.addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)` / `ViewAnnotationManager.removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)`. ([#1165](https://github.com/mapbox/mapbox-maps-android/pull/1165))
* Improve rendering performance by coalescing map updates when possible. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Add `StyleManager::hasStyleImage` API that checks whether an image is in the style or not. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Improve Snapshotter performance by using a lightweight scheduler instead of platform runloop. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Map now waits on sprite sheet loading before rendering. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Improve map rendering performance by avoiding calculations for all the non-transitional style layer paint properties. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Enable using of tile pack scheme from TileJSON. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Improve map rendering performance by decreasing de/allocations in map placement code. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Avoid style layer properties transition calculation when `TransitionOptions::duration` is set to 0. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Enable tile packs for DEM terrain tiles, it includes both Offline API and `TileStoreUsageMode::ReadAndUpdate` resource option. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Render tiles with partial content while the glyph dependencies are loading. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Canonicalize URLs and enable Offline API usage for the 3dtiles/v1 tiles. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Add optional `TransitionOptions` parameter to `MapboxMap.loadStyleUri`, `MapboxMap.loadStyleJson`, `MapboxMap.loadStyle` to apply transition to style being loaded. ([#1174](https://github.com/mapbox/mapbox-maps-android/pull/1174))

## Bug fixes 🐞
* Fix skipping / crashing user events scheduled on a render thread with `MapView#queueEvent`. ([#1068](https://github.com/mapbox/mapbox-maps-android/pull/1068))
* Fix location puck not being shown if map is created without initial style (e.g. MapInitOptions.styleUri == null) and then loaded asynchronously. ([#1114](https://github.com/mapbox/mapbox-maps-android/pull/1114))
* Fix crash within location plugin that happens when style is reloaded simultaneously with location plugin updates. ([#1112](https://github.com/mapbox/mapbox-maps-android/pull/1112))
* Fix memory leak in location component. ([#1093](https://github.com/mapbox/mapbox-maps-android/pull/1093), [#1172](https://github.com/mapbox/mapbox-maps-android/pull/1172))
* Fix screen coordinate queries when using zero pitch and high zoom values. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Avoid possible crash at program exit caused by dummy tracer accessed after the destruction. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Fix crash for the case when a map event is handled by an Observer of a destructed map. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Fix shimmering artifact when pitched raster tiles with compressed textures are rendered. ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160))
* Fix bearing of the puck reseted on settings change. ([#1144](https://github.com/mapbox/mapbox-maps-android/pull/1144))

## Dependencies
* Bump gl-native to 10.4.0-beta.2, mapbox-common to v21.2.0-beta.1 ([#1160](https://github.com/mapbox/mapbox-maps-android/pull/1160), [#1175](https://github.com/mapbox/mapbox-maps-android/pull/1175))
* Update android gradle plugin to v7.0.4, gradle version to v7.0.2, Gradle licence plugin to 0.8.80, Kotlin gradle plugin to 1.5.31, Jacoco to 0.8.7. ([#1118](https://github.com/mapbox/mapbox-maps-android/pull/1118))

# 10.3.0 February 10, 2022
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.2.0...android-v10.3.0) since [Mapbox Maps SDK for Android 10.2.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.2.0)
## Features ✨ and improvements 🏁
* Improve performance for symbol layout rendering in continuous mode. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Introduce metadata setter API for the legacy offline region. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Optimize zooming on terrain and globe. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Thin out repeated line labels at overscaled tiles in order to avoid excessive memory usage. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Remove experimental designation from persistent layer APIs. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Avoid re-creation of the available sprites set. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Limit the delayed network request maximum time in the scheduler task queue, and thus avoid excessive memory usage. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Fill extrusion layer support for globe view. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Increase priority of a renderer thread. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Introduce viewport plugin. ([#1033](https://github.com/mapbox/mapbox-maps-android/pull/1033), [#1069](https://github.com/mapbox/mapbox-maps-android/pull/1069))
* Add `Style#removeTerrain` method. ([#906](https://github.com/mapbox/mapbox-maps-android/pull/906))
* Introduce ability to specify `startDelay` property as part of `mapAnimationOptions` for high-level animations. ([#932](https://github.com/mapbox/mapbox-maps-android/pull/932))
* Deprecate map extension function to get gesture settings. ([#952](https://github.com/mapbox/mapbox-maps-android/pull/952))
* Introduce Mapbox exceptions instead of regular runtime exceptions allowing more precise control of catching them from user's end. ([#964](https://github.com/mapbox/mapbox-maps-android/pull/964))
* Add `tile-requests-delay` and `tile-network-requests-delay` source properties for tile requests delay. ([#960](https://github.com/mapbox/mapbox-maps-android/pull/960))
* Expose unique annotation feature identifier that could be used to link view annotation to any annotation. ([#994](https://github.com/mapbox/mapbox-maps-android/pull/994))
* Remove json serialization in observable extension improving overall performance by saving CPU cycles. ([#1001](https://github.com/mapbox/mapbox-maps-android/pull/1001))
* Add wrap content dimension support for view annotations. ([#1021](https://github.com/mapbox/mapbox-maps-android/pull/1021))
* Add extension function for location component to support show / hide arrow bearing image. ([#1012](https://github.com/mapbox/mapbox-maps-android/pull/1012))


## Bug fixes 🐞
* Include geometry data buffer size when calculating total size of a tile. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Fix screen coordinate queries when using zero pitch and high zoom values. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* View Annotation API: move internal Java files to the corresponding package. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Reduces drag sensitivity around and above horizon. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Erase corrupt tiles from TileStore. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Add perspective correction for non-rectangular images. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Fix rendering artifacts when compressed and un-compresed raster tiles are rendered. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Avoid creating new symbol instances if the feature is outside of tile boundaries to avoid incorrect symbol cross tile indexing. In the meanwhile, disable draping for this layer otherwise symbol will only be shown on the tile that has the symbol instance created. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105))
* Avoid possible crash at program exit caused by dummy tracer accessed after destruction. ([#1116](https://github.com/mapbox/mapbox-maps-android/pull/1116))
* Fix crash for the case when a map event is handled by an Observer of a destructed map. ([#1116](https://github.com/mapbox/mapbox-maps-android/pull/1116))
* Fix an issue where 3D puck used to scale when changing coordinate bounds. ([#1067](https://github.com/mapbox/mapbox-maps-android/pull/1067))
* Fix map not rendering on emulators when MSAA is enabled. ([#1077](https://github.com/mapbox/mapbox-maps-android/pull/1077))
* Fix issue with map rendering when limiting max FPS. ([#1100](https://github.com/mapbox/mapbox-maps-android/pull/1100))
* Revert "Add LocationCompassEngine for location component (#970)" that was introduced in v10.3.0-beta.1, as it accidentally introduced an API breaking change. ([#1115](https://github.com/mapbox/mapbox-maps-android/pull/1115))
* Fix an issue where source attribution was not populated in attribution dialog. ([#1087](https://github.com/mapbox/mapbox-maps-android/pull/1087))
* Fix an issue that causes transition to following viewport state not being fired when the bearing is set to constant. ([#1064](https://github.com/mapbox/mapbox-maps-android/pull/1064))
* Fix default viewport transition's anchor point. ([#1070](https://github.com/mapbox/mapbox-maps-android/pull/1070))
* Fix crash on destruction with ongoing tile-store downloads. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))
* Fix not allowing loading empty style uri. ([#904](https://github.com/mapbox/mapbox-maps-android/pull/904))
* Fix black `MapView#snapshot` returned on some devices (e.g. Huawei). ([#966](https://github.com/mapbox/mapbox-maps-android/pull/966))
* Enable drag annotation while changing the annotation to draggable on long click. ([#990](https://github.com/mapbox/mapbox-maps-android/pull/990))
* Send turnstile events for snapshotter usage. ([#920](https://github.com/mapbox/mapbox-maps-android/pull/920))
* Allow localizing non-mapbox street sources, align localization logic with iOS implementation. ([#968](https://github.com/mapbox/mapbox-maps-android/pull/968))
* Remove observer after it's not needed anymore from map renderer resulting in slightly better CPU consumption. ([#1014](https://github.com/mapbox/mapbox-maps-android/pull/1014))
* Fix memory leak in view annotations caused by not removing properly global layout listener properly. ([#1037](https://github.com/mapbox/mapbox-maps-android/pull/1037))
* Update vector-tile to v1.0.4, fixing an end of buffer exception. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Fix terrain occluding 3D location indicator. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Fix location indicator layer rendering when SwiftShader is used. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))

## Dependencies
* Update gl-native to v10.3.2, common to v21.1.0. ([#1105](https://github.com/mapbox/mapbox-maps-android/pull/1105), [#1116](https://github.com/mapbox/mapbox-maps-android/pull/1116), [#1134](https://github.com/mapbox/mapbox-maps-android/pull/1134))
* Add sdk versions plugin v1.1.3. ([#1123](https://github.com/mapbox/mapbox-maps-android/pull/1123))

# 10.3.0-rc.1 January 26, 2022

## Features ✨ and improvements 🏁
* Refine viewport plugin's public APIs. ([#1069](https://github.com/mapbox/mapbox-maps-android/pull/1069))
* Reintroduce missing `OfflineRegion#setMetadata` API for parity with `OfflineRegion#updateMetadata` in v9. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))
* Improved frame rate when zooming in and out over terrain and globe. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))
* Thin out repeated line labels at overscaled tiles in order to avoid excessive memory usage. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))

## Bug fixes 🐞
* Fix an issue that causes transition to following viewport state not being fired when the bearing is set to constant. ([#1064](https://github.com/mapbox/mapbox-maps-android/pull/1064))
* Fix an issue where 3D puck used to scale when changing coordinate bounds. ([#1067](https://github.com/mapbox/mapbox-maps-android/pull/1067))
* Fix default viewport transition's anchor point. ([#1070](https://github.com/mapbox/mapbox-maps-android/pull/1070))
* Fix screen coordinate queries when using zero pitch and high zoom values. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))
* Fix crash on destruction with ongoing tile-store downloads. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))
* Fix map not rendering on emulators when MSAA is enabled. ([#1077](https://github.com/mapbox/mapbox-maps-android/pull/1077))

## Dependencies
* Bump gl-native to v10.3.0-rc.1, common to v21.1.0-rc.1. ([#1071](https://github.com/mapbox/mapbox-maps-android/pull/1071))

# 10.3.0-beta.1 January 12, 2022

## Features ✨ and improvements 🏁
* Introduce viewport plugin. ([#1033](https://github.com/mapbox/mapbox-maps-android/pull/1033))
* Promote persistent style layer APIs to be production-ready. ([#879](https://github.com/mapbox/mapbox-maps-android/pull/879))
* Add `Style#removeTerrain` method. ([#906](https://github.com/mapbox/mapbox-maps-android/pull/906))
* Introduce ability to specify `startDelay` property as part of `mapAnimationOptions` for high-level animations. ([#932](https://github.com/mapbox/mapbox-maps-android/pull/932))
* Deprecate map extension function to get gesture settings. ([#952](https://github.com/mapbox/mapbox-maps-android/pull/952))
* Introduce Mapbox exceptions instead of regular runtime exceptions allowing more precise control of catching them from user's end. ([#964](https://github.com/mapbox/mapbox-maps-android/pull/964))
* Add `tile-requests-delay` and `tile-network-requests-delay` source properties for tile requests delay. ([#960](https://github.com/mapbox/mapbox-maps-android/pull/960))
* Expose unique annotation feature identifier that could be used to link view annotation to any annotation. ([#994](https://github.com/mapbox/mapbox-maps-android/pull/994))
* Add `LocationComponentSettings.puckBearingSource` property to control location puck bearing to be either GPS or compass. ([#970](https://github.com/mapbox/mapbox-maps-android/pull/970))
* Remove json serialization in observable extension improving overall performance by saving CPU cycles. ([#1001](https://github.com/mapbox/mapbox-maps-android/pull/1001))
* Add wrap content dimension support for view annotations. ([#1021](https://github.com/mapbox/mapbox-maps-android/pull/1021))
* Add extension function for location component to support show / hide arrow bearing image. ([#1012](https://github.com/mapbox/mapbox-maps-android/pull/1012))
* Fill extrusion layer support for globe view. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))

## Bug fixes 🐞
* Fix not allowing loading empty style uri. ([#904](https://github.com/mapbox/mapbox-maps-android/pull/904))
* Fix black `MapView#snapshot` returned on some devices (e.g. Huawei). ([#966](https://github.com/mapbox/mapbox-maps-android/pull/966))
* Enable drag annotation while changing the annotation to draggable on long click. ([#990](https://github.com/mapbox/mapbox-maps-android/pull/990))
* Send turnstile events for snapshotter usage. ([#920](https://github.com/mapbox/mapbox-maps-android/pull/920))
* Allow localizing non-mapbox street sources, align localization logic with iOS implementation. ([#968](https://github.com/mapbox/mapbox-maps-android/pull/968))
* Remove observer after it's not needed anymore from map renderer resulting in slightly better CPU consumption. ([#1014](https://github.com/mapbox/mapbox-maps-android/pull/1014))
* Fix memory leak in view annotations caused by not removing properly global layout listener properly. ([#1037](https://github.com/mapbox/mapbox-maps-android/pull/1037))
* Update vector-tile to v1.0.4, fixing an end of buffer exception. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Erase corrupt tiles from TileStore. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Fix rendering artifacts when compressed and un-compressed raster tiles are rendered. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Fix terrain occluding 3D location indicator. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))
* Fix location indicator layer rendering when SwiftShader is used. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))

## Dependencies
* Bump gl-native to v10.3.0-beta.1, common to v21.1.0-beta.1. ([#1035](https://github.com/mapbox/mapbox-maps-android/pull/1035))

# 10.2.0 December 15, 2021
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.1.0...android-v10.2.0) since [Mapbox Maps SDK for Android 10.1.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.1.0)
## Features ✨ and improvements 🏁
* Introduce view annotation support which allows adding Android views on top of the `MapView` anchored to geo-point. ([#834](https://github.com/mapbox/mapbox-maps-android/pull/834))
* Remove `MapView` argument when constructing `AnnotationManager`. Constructor taking `MapView` as parameter is marked as deprecated. ([#766](https://github.com/mapbox/mapbox-maps-android/pull/766))
* Implement cluster API on top of `MapboxMap.queryFeatureExtensions` making it easier to use and providing better alignment with Mapbox Maps v9. ([#773](https://github.com/mapbox/mapbox-maps-android/pull/773))
* Add heatmap and circle layer support to globe view. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Add cancelable Query Rendered Features API to `MapboxMap`. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Improve `MapboxMap.queryRenderedFeatures` performance especially when querying large number of features. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Cache layer layout key inside layer, so that it is not re-evaluated at every parse of the every tile improving rendering performance. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Core renderer prints its version on initialization. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Introduce experimental `MapboxMap.setMemoryBudget` method for setting memory budget for the map and runtime "resource-budget" property for data sources. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Improve performance by avoiding re-layout of invisible fading tiles. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Add utility methods to `CoordinateBounds`. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))

## Bug fixes 🐞
* Fix black screen issue on some devices caused by incorrect EGL config. ([#980](https://github.com/mapbox/mapbox-maps-android/pull/980))
* Fix `replaceWith` template to replace deprecated `queryRenderFeatures` overloaded methods. ([#878](https://github.com/mapbox/mapbox-maps-android/pull/878))
* Do not allow to use one `associatedFeatureId` with multiple view annotations. ([#896](https://github.com/mapbox/mapbox-maps-android/pull/896))
* Fix an issue where shove gesture was not detected when angle between touch points are not horizontal. ([#875](https://github.com/mapbox/mapbox-maps-android/pull/875))
* Fix gestures at high camera pitch near horizon line. ([#927](https://github.com/mapbox/mapbox-maps-android/pull/927), [#925](https://github.com/mapbox/mapbox-maps-android/pull/925))
* Fix fading tiles layout visibility issue on globe view projection zooming in/out. ([#925](https://github.com/mapbox/mapbox-maps-android/pull/925))
* Allow simultaneous zoom and rotate gesture by default and add `GesturesSettings#simultaneousRotateAndPinchToZoomEnabled` config option. ([#885](https://github.com/mapbox/mapbox-maps-android/pull/885))
* Fix `MapView.onLowMemory` not being called on low resources. ([#780](https://github.com/mapbox/mapbox-maps-android/pull/780))
* Fix scale bar ratio setting not applied correctly. ([#827](https://github.com/mapbox/mapbox-maps-android/pull/827))
* Fix scale bar text missing for Android API 23. ([#839](https://github.com/mapbox/mapbox-maps-android/pull/839))
* Fix scale bar text being overlapped and clipped. ([#856](https://github.com/mapbox/mapbox-maps-android/pull/856))
* Fix puck jump to nullisland when location plugin settings are changed. ([#846](https://github.com/mapbox/mapbox-maps-android/pull/846))
* Fix scale listener events not being called for quick zoom doubleTap and doubleTouch gestures. ([#858](https://github.com/mapbox/mapbox-maps-android/pull/858))
* Release all unused resources when `MapboxMap.reduceMemoryUse` is invoked. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix crash for the case when an empty fill extrusion bucket is tried to be rendered. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix transparency issues with value < 0.5 for 3D puck models. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix regression where setting the same geojson source URL did not refresh the data. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix symbol layers with variable anchor placement not being placed correctly on globe view. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix crash in symbol reprojection code caused by division by zero. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix issue with bounds constraining behavior when terrain is enabled. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))

## Dependencies
* Bump common to v21.0.1. ([#937](https://github.com/mapbox/mapbox-maps-android/pull/937))
* Bump gl-native to v10.2.0. ([#989](https://github.com/mapbox/mapbox-maps-android/pull/989))

# 10.1.2 December 13, 2021

## Bug fixes 🐞
* Fix billing issue when upgrading Mapbox Maps SDK from v9 to v10.

## Dependencies
* Bump common to 20.1.2. ([#979](https://github.com/mapbox/mapbox-maps-android/pull/979))

# 10.0.2 December 13, 2021

## Bug fixes 🐞
* Fix billing issue when upgrading Mapbox Maps SDK from v9 to v10.

## Dependencies
* Bump common to v20.0.3.([#978](https://github.com/mapbox/mapbox-maps-android/pull/978))

# 10.2.0-rc.1 December 2, 2021

## Bug fixes 🐞
* Fix `replaceWith` template to replace deprecated `queryRenderFeatures` overloaded methods. ([#878](https://github.com/mapbox/mapbox-maps-android/pull/878))
* Do not allow to use one `associatedFeatureId` with multiple view annotations. ([#896](https://github.com/mapbox/mapbox-maps-android/pull/896))
* Fix an issue where shove gesture was not detected when angle between touch points are not horizontal. ([#875](https://github.com/mapbox/mapbox-maps-android/pull/875))
* Fix gestures at high camera pitch near horizon line. ([#927](https://github.com/mapbox/mapbox-maps-android/pull/927), [#925](https://github.com/mapbox/mapbox-maps-android/pull/925))
* Fix fading tiles layout visibility issue on globe view projection zooming in/out. ([#925](https://github.com/mapbox/mapbox-maps-android/pull/925))
* Allow simultaneous zoom and rotate gesture by default and add `GesturesSettings#simultaneousRotateAndPinchToZoomEnabled` config option. ([#885](https://github.com/mapbox/mapbox-maps-android/pull/885))

## Dependencies
* Bump gl-native to v10.2.0-rc.1, common to v21.0.0-rc.2. ([#925](https://github.com/mapbox/mapbox-maps-android/pull/925))

# 10.1.1 December 1, 2021

**NOTE:** As of December 3, 2021, this release is no longer available due to a new bug that was introduced while fixing the billing issue. A new patch will be issued shortly.

## Bug fixes 🐞
* Fix billing issue when upgrading Mapbox Maps SDK from v9 to v10.

## Dependencies
* Bump gl-native to 10.1.1, common to 20.1.1.

# 10.0.1 November 26, 2021

**NOTE:** As of December 3, 2021, this release is no longer available due to a new bug that was introduced while fixing the billing issue. A new patch will be issued shortly.

## Bug fixes 🐞
* Fix billing issue when upgrading Mapbox Maps SDK from v9 to v10.

## Dependencies
* Bump common to v20.0.2.

# 10.2.0-beta.1 November 18, 2021

## Features ✨ and improvements 🏁
* Introduce view annotation support which allows adding Android views on top of the `MapView` anchored to geo-point. ([#834](https://github.com/mapbox/mapbox-maps-android/pull/834))
* Remove `MapView` argument when constructing `AnnotationManager`. Constructor taking `MapView` as parameter is marked as deprecated. ([#766](https://github.com/mapbox/mapbox-maps-android/pull/766))
* Implement cluster API on top of `MapboxMap.queryFeatureExtensions` making it easier to use and providing better alignment with Mapbox Maps v9. ([#773](https://github.com/mapbox/mapbox-maps-android/pull/773))
* Add heatmap and circle layer support to globe view. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Add cancelable Query Rendered Features API to `MapboxMap`. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Improve `MapboxMap.queryRenderedFeatures` performance especially when querying large number of features. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Cache layer layout key inside layer, so that it is not re-evaluated at every parse of the every tile improving rendering performance. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Core renderer prints its version on initialization. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Introduce experimental `MapboxMap.setMemoryBudget` method for setting memory budget for the map and runtime "resource-budget" property for data sources. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Improve performance by avoiding re-layout of invisible fading tiles. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Add utility methods to `CoordinateBounds`. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))

## Bug fixes 🐞
* Fix `MapView.onLowMemory` not being called on low resources. ([#780](https://github.com/mapbox/mapbox-maps-android/pull/780))
* Fix scale bar ratio setting not applied correctly. ([#827](https://github.com/mapbox/mapbox-maps-android/pull/827))
* Fix scale bar text missing for Android API 23. ([#839](https://github.com/mapbox/mapbox-maps-android/pull/839))
* Fix scale bar text being overlapped and clipped. ([#856](https://github.com/mapbox/mapbox-maps-android/pull/856))
* Fix puck jump to nullisland when location plugin settings are changed. ([#846](https://github.com/mapbox/mapbox-maps-android/pull/846))
* Fix scale listener events not being called for quick zoom doubleTap and doubleTouch gestures. ([#858](https://github.com/mapbox/mapbox-maps-android/pull/858))
* Release all unused resources when `MapboxMap.reduceMemoryUse` is invoked. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix crash for the case when an empty fill extrusion bucket is tried to be rendered. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix transparency issues with value < 0.5 for 3D puck models. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix regression where setting the same geojson source URL did not refresh the data. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix symbol layers with variable anchor placement not being placed correctly on globe view. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix crash in symbol reprojection code caused by division by zero. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Fix issue with bounds constraining behavior when terrain is enabled. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))

## Dependencies
* Bump gl-native to v10.2.0-beta.2 and common to v21.0.0-rc.1. ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))

# 10.1.0 November 4, 2021
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0...android-v10.1.0) since [Mapbox Maps SDK for Android 10.0.0](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0)
## Features ✨ and improvements 🏁
* Set thread priorities and set thread CPU affinity based on the thread's priority to improve overall map performance.([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Introduce option to enable multisample anti-aliasing (MSAA) for map rendering. ([#741](https://github.com/mapbox/mapbox-maps-android/pull/741))
* Add convenience methods for `stop` expression in Style DSL. ([#698](https://github.com/mapbox/mapbox-maps-android/pull/698), [#764](https://github.com/mapbox/mapbox-maps-android/pull/764))

## Bug fixes 🐞
* Avoid spawning extra AssetManagerFileSource threads when multiple instances of a MapView is created during application lifecycle. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix rendering artifact when some of the model layer models may have wrong placement when globe view projection is used. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix rare heatmap flickering when zooming the map. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix an issue where an Observable event could be dispatched on a thread, different from the subscription thread. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix an issue where promoteId parameter for VectorSource was overwritten when source tilejson is loaded ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix android glyph drawing issue when 'high contrast' text accessibility feature is turned on. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))
* Fix unexpectedly rapid map panning at high pitch level. ([#775](https://github.com/mapbox/mapbox-maps-android/pull/775))
* Fix map move using faster map offsetting after zoom-in or zoom-out gesture. ([#738](https://github.com/mapbox/mapbox-maps-android/pull/738))
* Fix annotation flickering and disappearing during dragging. ([#732](https://github.com/mapbox/mapbox-maps-android/pull/732))
* Fix logo and attribution margin update. ([#744](https://github.com/mapbox/mapbox-maps-android/pull/744))
* Fix `NullPointerException` while querying annotations. ([#746](https://github.com/mapbox/mapbox-maps-android/pull/746))
* Limit fast fling gesture in a downwards direction when map is highly pitched. ([#754](https://github.com/mapbox/mapbox-maps-android/pull/754))
* Fix an issue that caused annotations not being updated when style is loading other resources. ([#753](https://github.com/mapbox/mapbox-maps-android/pull/753))
* Restore the fling factor for slightly pitched maps. ([#762](https://github.com/mapbox/mapbox-maps-android/pull/762))

## Dependencies
* Bump gl-native to 10.1.0, common to 20.1.0. ([#810](https://github.com/mapbox/mapbox-maps-android/pull/810))

# 10.1.0-rc.1 October 28, 2021

## Bug fixes 🐞
* Fix unexpectedly rapid map panning at high pitch level. ([#775](https://github.com/mapbox/mapbox-maps-android/pull/775))
* Improve rendering performance by setting thread priorities and set thread CPU affinity based on the thread's priority. ([#774](https://github.com/mapbox/mapbox-maps-android/pull/774))

## Dependencies
* Bump gl-native to v10.1.0-rc, and common to v20.1.0-rc.2 ([#774](https://github.com/mapbox/mapbox-maps-android/pull/774))

# 10.1.0-beta.1 October 21, 2021

## Features ✨ and improvements 🏁
* Introduce option to enable Multisample anti-aliasing (MSAA) for map rendering. ([#741](https://github.com/mapbox/mapbox-maps-android/pull/741))
* Add convenience methods for stop expression. ([#698](https://github.com/mapbox/mapbox-maps-android/pull/698), [#764](https://github.com/mapbox/mapbox-maps-android/pull/764))

## Bug fixes 🐞
* Fix map move using faster map offsetting after zoom-in or zoom-out gesture. ([#738](https://github.com/mapbox/mapbox-maps-android/pull/738))
* Fix annotation flickering and disappearing during dragging. ([#732](https://github.com/mapbox/mapbox-maps-android/pull/732))
* Fix logo and attribution margin update. ([#744](https://github.com/mapbox/mapbox-maps-android/pull/744))
* Fix `NullPointerException` while querying annotations. ([#746](https://github.com/mapbox/mapbox-maps-android/pull/746))
* Limit fast fling gesture in a downwards direction when map is highly pitched. ([#754](https://github.com/mapbox/mapbox-maps-android/pull/754))
* Fixed an issue that caused annotations not being updated in some cases.. ([#753](https://github.com/mapbox/mapbox-maps-android/pull/753))
* Fix glyph drawing issue when the 'high contrast' text feature is turned on. ([#752](https://github.com/mapbox/mapbox-maps-android/pull/752))
* Fix `promoteId` parameter for VectorSource overwritten when source tilejson is loaded. ([#752](https://github.com/mapbox/mapbox-maps-android/pull/752))
* Avoid spawning extra AssetManagerFileSource threads. ([#752](https://github.com/mapbox/mapbox-maps-android/pull/752))
* Fix `NullPointerException` in `HttpResponseCallback`, when get an error message from the Exception. ([#752](https://github.com/mapbox/mapbox-maps-android/pull/752))
* Restore the fling factor for slightly pitched maps. ([#762](https://github.com/mapbox/mapbox-maps-android/pull/762))

## Dependencies
* Bump gl-native to v10.1.0-beta, and common to v20.1.0-rc.1 ([#752](https://github.com/mapbox/mapbox-maps-android/pull/752))


# 10.0.0 October 6, 2021

## Breaking changes ⚠️
* Add `@JvmOverloads` where applicable to provide better experience for Java users. ([#656](https://github.com/mapbox/mapbox-maps-android/pull/656))
* Refactor gestures configuration options to be aligned better across platforms. ([#672](https://github.com/mapbox/mapbox-maps-android/pull/672))
* Apply geojson data (using `data`, `url`, `feature`, `featureCollection`, `geometry` functions) is fully async now. ([#699](https://github.com/mapbox/mapbox-maps-android/pull/699))
* Update `getLayerAs` function to return nullable `Layer` type. ([#673](https://github.com/mapbox/mapbox-maps-android/pull/673))
* Update map events data models. ([#712](https://github.com/mapbox/mapbox-maps-android/pull/712))
* Refactor MapEvents listeners, so that each listener will include one event data property. ([#718](https://github.com/mapbox/mapbox-maps-android/pull/718))
* Abstract classes `CustomLayerHost`, `ElevationData`, `MapClient`, `Observer`, `OfflineRegionObserver`, `HttpServiceInterceptorInterface`, `HttpServiceInterface`, `LogWriterBackend`, `OfflineSwitchObserver`, `ReachabilityInterface`, `TileStoreObserver` have become interfaces. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))

## Features ✨ and improvements 🏁
* Introduce 3D globe (experimental). ([#667](https://github.com/mapbox/mapbox-maps-android/pull/667))
* Append gl-native and common API reference documentation to the output of Dokka documentation generation. ([#711](https://github.com/mapbox/mapbox-maps-android/pull/711))
* Set `Process.THREAD_PRIORITY_DISPLAY` as render thread priority to improve overall performance. ([#701](https://github.com/mapbox/mapbox-maps-android/pull/701))
* Add `HttpServiceFactory.reset()` to release the HTTP service implementation. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))

## Bug fixes 🐞
* Throw exception when gestures plugin functionality is used but plugin was not created. ([#653](https://github.com/mapbox/mapbox-maps-android/pull/653))
* Throw exception when camera plugin functionality is used but plugin was not created. ([#668](https://github.com/mapbox/mapbox-maps-android/pull/668))
* Fix black screen when resuming activity with `MapView` on x86 emulator, Android API <= 23. ([#671](https://github.com/mapbox/mapbox-maps-android/pull/671))
* Fix map render deadlock on Android 8 on power on button. ([#688](https://github.com/mapbox/mapbox-maps-android/pull/688))
* Fix context leak in `LocationProviderImpl`. ([#690](https://github.com/mapbox/mapbox-maps-android/pull/690))
* Fix native memory leak by explicitly nulling map reference from renderer. ([#687](https://github.com/mapbox/mapbox-maps-android/pull/687))
* Fix wrong attribute reference in runtime exception text when token is missing. ([#708](https://github.com/mapbox/mapbox-maps-android/pull/708))
* Fix applying position property to scale bar plugin. ([#677](https://github.com/mapbox/mapbox-maps-android/pull/677))
* Fix initialisation location puck when no style loaded from code by changing `Plugin#onStart()` call after style loaded started. ([#680](https://github.com/mapbox/mapbox-maps-android/pull/680))
* Fix attribution/logo jumble when RTL layout is configured. ([#674](https://github.com/mapbox/mapbox-maps-android/pull/674))
* Fix rendering artifacts for a model layer when `model-opacity` property is used. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))
* Improve rendering performance by avoiding unnecessary re-layout for cached tiles. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))
* Fix `onResponse` callback for `HttpInterceptor` never being called. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))

## Dependencies
* Bump gl-native to v10.0.0, common to v20.0.0. ([#697](https://github.com/mapbox/mapbox-maps-android/pull/697))

# 10.0.0-rc.9 September 22, 2021

## Features ✨ and improvements 🏁
* Fix documentation for `OnMapIdleListener` and `CameraChangeListeners`. ([#645](https://github.com/mapbox/mapbox-maps-android/pull/645))
* Add support for `SymbolZOrder` property in PointAnnotationManager. ([#638](https://github.com/mapbox/mapbox-maps-android/pull/638))
* Add support for `PromoteId` to be used with Feature State API. ([#636](https://github.com/mapbox/mapbox-maps-android/pull/636))
* Expose `optimizeForTerrain` flag (default to true) that could be applied to the `MapView` in xml. When optimizeForTerrain is enabled, layers could get reordered to achieve the best performance. ([#654](https://github.com/mapbox/mapbox-maps-android/pull/654))
* Enable instant transitions for data driven symbol layer properties. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))

## Bug fixes 🐞
* `OnStyleLoaded` / `OnMapLoaded` callbacks are invoked even if hosting fragment/activity is in stopped state. ([#629](https://github.com/mapbox/mapbox-maps-android/pull/629))
* Fix drag annotation blink when drag ends. ([#639](https://github.com/mapbox/mapbox-maps-android/pull/639))
* Apply annotation manager properties to the drag layer to keep annotations the same while dragging. ([#640](https://github.com/mapbox/mapbox-maps-android/pull/640))
* Fix point annotation updating all same content bitmaps instead of one particular. ([#633](https://github.com/mapbox/mapbox-maps-android/pull/633))
* Fix `MapboxMap#getStyle` returning null after adding a new source when style was loaded before. ([#643](https://github.com/mapbox/mapbox-maps-android/pull/643))
* Allow setting null explicitly to annotation nullable properties. ([#650](https://github.com/mapbox/mapbox-maps-android/pull/650))
* Fix `std::exception` happing rarely when `MapboxMap#setCamera()` is called inside animation plugin. ([#652](https://github.com/mapbox/mapbox-maps-android/pull/652))
* Fix memory leak in renderer destroy. ([#657](https://github.com/mapbox/mapbox-maps-android/pull/657))
* Fix transition between layers with all constant properties. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))
* Fix rendering artifact for a line layer, when its `line-gradient` property is set at runtime. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))
* Don't draw SDF images in `text-field` and issue warning for it. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))
* Fix incorrect return from StyleManager#getStyleLayerPropertyDefaultValue for `text-field`, now the default value is set to `["format", "" , {}]`. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))

## Dependencies
* Bump gl-native to 10.0.0-rc.9, common to 19.0.0. ([#646](https://github.com/mapbox/mapbox-maps-android/pull/646))

# 10.0.0-rc.8 September 8, 2021

## Breaking changes ⚠️
* In offline mode (set by either mapbox::common::OfflineSwitch API or on platform side), the error notifications are send if the required resources are not present locally. The volatile tiles are not considered to be required in offline.([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Adapt setBounds to gl-js behavior: constraining of coordinates and zoom level is now stricter to prevent out of bounds map area to be visible in the viewport.([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Add HTTP interceptor API - for anyone who is using HttpServiceInterface; there is a new method called setInterceptor that should be overridden([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))

## Features ✨ and improvements 🏁
* Make 3D puck always over (in front of) 3D layers (buildings, landmarks, custom layer) but behind hill (terrain). ([#601](https://github.com/mapbox/mapbox-maps-android/pull/601))
* Integrate value marshalling performance improvement ([#606](https://github.com/mapbox/mapbox-maps-android/pull/606))
* Introduce drag layer/source for annotation plugin to improve drag performance. ([#582](https://github.com/mapbox/mapbox-maps-android/pull/582))
* Update prefetch zoom delta documentation to match actual behavior ([#609](https://github.com/mapbox/mapbox-maps-android/pull/609))
* Add support for the index-of and slice expressions ([#616](https://github.com/mapbox/mapbox-maps-android/pull/616))
* Improve collision detection by using runtime calculated sizes for collision boxes. Previously collision boxes' sizes are constant, they are calculated during symbol layout time by using constant zoom level([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Improve collision detection by using runtime calculated pixelated sizes for collision circles. Previously collision circles' sizes are constant, they are calculated during symbol layout time by using constant zoom level([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Implement 'promoteId' feature for geojson and vector sources. The feature allows to promote feature's property to a feature id, so that promoted id can be used with FeatureState API.([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Enable instant transitions for data driven paint layer properties([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))

## Bug fixes 🐞
* Use touch focal point to calculate the correct scroll displacement when the map is pitched. ([#593](https://github.com/mapbox/mapbox-maps-android/pull/593))
* Use touch focal point to calculate the correct fling displacement. ([#599](https://github.com/mapbox/mapbox-maps-android/pull/599))
* Allow geojson source to initialise with empty data. ([#602](https://github.com/mapbox/mapbox-maps-android/pull/602))
* Preserve EGL setup on renderer stop. This fixes full map reloading when map is brought out from background. ([#598](https://github.com/mapbox/mapbox-maps-android/pull/598))
* Fix issue with camera animators ordering on Android 6 and lower by revisiting overall approach of applying accumulated camera changes. ([#597](https://github.com/mapbox/mapbox-maps-android/pull/597))
* Enable update bitmap for annotations  ([#615](https://github.com/mapbox/mapbox-maps-android/pull/615))
* Fix volatile tiles disappearing on "not modified" response([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Prioritize addition of a persistent layer whose id is used for other persistent layer positions([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Only do line breaking process for point placement labels. And if text-max-width is 0, still do general ideographic beaking checks for point labels.([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Fix collision box's 'dynamicVerticesExt' updating in placement stage([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))
* Trigger map redraw when feature state changes ([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))

## Dependencies
* Bump gl-native to 10.0.0-rc.8, common to 18.0.0 ([#604](https://github.com/mapbox/mapbox-maps-android/pull/604))

# 10.0.0-rc.7 August 25, 2021

## Breaking changes ⚠️
* Remove the expression getter/setters for source properties. ([#568](https://github.com/mapbox/mapbox-maps-android/pull/568))

## Features ✨ and improvements 🏁
* Add generateId property for GeoJsonSource. ([#538](https://github.com/mapbox/mapbox-maps-android/pull/538))
* Add default value to improve usability of FeatureState API. ([#588](https://github.com/mapbox/mapbox-maps-android/pull/588))
* Add Style#moveStyleLayer(layerId: String, layerPosition: LayerPosition?): Expected<String, None> API ([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))
* Allow using combination of line-dasharray and line-gradient for line layer. ([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))

## Bug fixes 🐞
* Remove strong ref dependency in snapshotter that was leading to a memory leak if Snapshotter#destroy was not called explicitly. ([#571](https://github.com/mapbox/mapbox-maps-android/pull/571))
* Fix get annotation enum property crash ([#579](https://github.com/mapbox/mapbox-maps-android/pull/579))
* Fix rendering issue for round line-join in line gradients ([#565](https://github.com/mapbox/mapbox-maps-android/pull/565))
* A fix of the layer paint property evaluation while transitioning from a data-driven value. It snaps immediately to the new value thus preventing of drawing stale data during the animation.([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))
* Reduced memory consumption when using raster layers by deleting CPU side tile bitmap copy after uploading to GPU texture.([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))
* Fix crash on Android when using tile requests delay API([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))

## Dependencies
* Bump gl-native to v10.0.0-rc.7, common to v17.0.0 ([#563](https://github.com/mapbox/mapbox-maps-android/pull/563))
* Bump gl-native to v10.0.0-rc.7.1. ([#565](https://github.com/mapbox/mapbox-maps-android/pull/565))
* Bump gl-native to v10.0.0-rc.7.2, common to 17.1.0 ([#575](https://github.com/mapbox/mapbox-maps-android/pull/575))
* Bump targetSDKVersion and compileSDKVersion to 30, robolectric version to 4.6.1. ([#514](https://github.com/mapbox/mapbox-maps-android/pull/514))

# 10.0.0-rc.6 August 11, 2021

## Breaking changes ⚠️
* Update extension function signatures making them easier to use from Java. ([#539](https://github.com/mapbox/mapbox-maps-android/pull/539))
* Rename `mapView#overlay()` to `mapView#mapboxOverlay`. ([#539](https://github.com/mapbox/mapbox-maps-android/pull/539))

## Features ✨ and improvements 🏁
* Support adding 9-patch images to the style. ([#536](https://github.com/mapbox/mapbox-maps-android/pull/536))
* Outdated data for volatile sources gets hidden if cannot be updated due to no Internet connection. ([#543](https://github.com/mapbox/mapbox-maps-android/pull/543))

## Bug fixes 🐞
* Fix several memory leaks: clean up OnFpsChangeListener on render thread destroy / introduce Snapshotter#destroy method that must be called in Activity#onDestroy ([#546](https://github.com/mapbox/mapbox-maps-android/pull/546))
* Add layer and source check when creating annotations and init them if not initiated before which creates `AnnotationManager` before loading style. ([#549](https://github.com/mapbox/mapbox-maps-android/pull/549))
* Fix error messages returned by `Style#removeStyleSource` method. ([#543](https://github.com/mapbox/mapbox-maps-android/pull/543))
* Store persistent layer's LayerPosition, so that layer can be re-added to correct position if LayerPosition.above or LayerPosition.at is used. ([#543](https://github.com/mapbox/mapbox-maps-android/pull/543))

## Dependencies
* Update gl-native to v10.0.0-rc.6 and common to v16.2.0. ([#543](https://github.com/mapbox/mapbox-maps-android/pull/543))
* Remove turf dependency of location component plugin. ([#551](https://github.com/mapbox/mapbox-maps-android/pull/551))

# 10.0.0-rc.5 July 28, 2021

## Breaking changes ⚠️
* Improve camera API consumption from java programming language by adding `CameraAnimationsUtils` and `getCamera` JvmName annotations. ([#495](https://github.com/mapbox/mapbox-maps-android/pull/495))
* Rename `AttributionView#setOnClickListener` to `setViewOnClickListener` to avoid overloading the Android SDK method. Results in compilation on Android P and above. Adjust codebase to changes in enforced nullability of Android SDK code. ([#497](https://github.com/mapbox/mapbox-maps-android/pull/497))
* Get rid of using reflection when creating plugins which should decrease `MapView` startup time if plugins are enabled. ([#519](https://github.com/mapbox/mapbox-maps-android/pull/519))

## Features ✨ and improvements 🏁
* Add `showLogo` and `showAttributes` config for snapshotter which are defaulted to true. User can now hide logo and attributions in a snapshotter by changing this config ([#496](https://github.com/mapbox/mapbox-maps-android/pull/496))
* Add lifecycle plugin so there is no need to call `onStart`/`onStop`/`onDestroy`/`onLowMemory` methods explicitly, if the appcompact 1.3.0+ is used. ([#485](https://github.com/mapbox/mapbox-maps-android/pull/485))
* Add a minimum Android Auto test app and an optional Android Auto extension that provide convenient extension function to initialise the MapSurface from a Car App Session. ([#488](https://github.com/mapbox/mapbox-maps-android/pull/488))
* Add lint check for lifecycle methods ([#516](https://github.com/mapbox/mapbox-maps-android/pull/516))

## Bug fixes 🐞
* Fix issues with MapView#snapshot methods that could cause black snapshot or ANR in some cases. ([#508](https://github.com/mapbox/mapbox-maps-android/pull/508))

# 10.0.0-rc.4 July 14, 2021

**The Mapbox Maps SDK for Android has moved to release candidate status and is now ready for production use.**

## Features ✨ and improvements 🏁
* Add new param to allow users localize selected layers. ([#461](https://github.com/mapbox/mapbox-maps-android/pull/461))
* Add API to control logging for animation plugin and disable debug logs by default. ([#474](https://github.com/mapbox/mapbox-maps-android/pull/474))
* Introduce option to use continuous rendering for scale bar. Continuous render mode will fix gfxinfo profiling. ([#458](https://github.com/mapbox/mapbox-maps-android/pull/458))
* Add shortest bearing path option for animators. ([#473](https://github.com/mapbox/mapbox-maps-android/pull/473))
* Add modelTranslation support for LocationPuck3D ([#493](https://github.com/mapbox/mapbox-maps-android/pull/493))
* Add default parameters to coordinate conversion functions of MapCameraManagerDelegate#cameraForCoordinates, MapCameraManagerDelegate#cameraForCoordinateBounds and MapCameraManagerDelegate#cameraForGeometry. This overloads the functions to have a more simple API surface for developers to hook into. ([#491](https://github.com/mapbox/mapbox-maps-android/pull/491))
* Support text-writing-mode property for line symbol-placement text labels (#1766)
  Note: This change will bring following changes for CJK text block:
  - For vertical CJK text, all the characters including Latin and Numbers will be vertically placed now. Previously, Latin and Numbers are horizontally placed.
  - For horizontal CJK text, it may have a slight horizontal shift due to the anchor shift.
* Session SKU generation is now available
* Add getSKUTokenIfValid to get a SKU token for a SKU identifier if it exists and is not expired, return empty string if not.
* Allow filtering of log messages by categories.
* Expose isFiltered for checking logging category settings

## Bug fixes 🐞
* Fix flyTo crash when using single-pixel paddings. ([#478](https://github.com/mapbox/mapbox-maps-android/pull/478))
* Fixed regression in map gestures on devices with Android 6 and lower. ([#484](https://github.com/mapbox/mapbox-maps-android/pull/484))
* Fix overwriting sync geojson data with getSourceAs by async. ([#482](https://github.com/mapbox/mapbox-maps-android/pull/482))
* Clean up network listener after http file source gets out of scope
* Fix line-center anchor calculation when the anchor is very near to the line geometry point
* Fix crash when a Feature State API is used with dedicated rendering thread
* Fix threading issues in HTTP file source
* Fix volatile tilesets handling

## Dependencies
* Update gl-native to v10.0.0-rc.5 and common to v16.0.0. ([#487](https://github.com/mapbox/mapbox-maps-android/pull/487))

# 10.0.0-rc.3 June 30, 2021

**The Mapbox Maps SDK for Android has moved to release candidate status and is now ready for production use.**

## Breaking changes ⚠️
* Perform annotation click synchronously and change AnnotationManagerImpl#queryMapForFeatures function to be synchronous. ([#455](https://github.com/mapbox/mapbox-maps-android/pull/455))

## Features ✨ and improvements 🏁
* Introduce static MapboxMap.clearData(resourceOptions: ResourceOptions, callback: AsyncOperationResultCallback) API and MapboxMap#clearData(callback: AsyncOperationResultCallback), Snapshotter#clearData(callback: AsyncOperationResultCallback) APIs. ([#442](https://github.com/mapbox/mapbox-maps-android/pull/442))
* Optimise the Style#getLayer and Style#getSource APIs' performance. ([#449](https://github.com/mapbox/mapbox-maps-android/pull/449))
* MapEvents#MAP_LOADING_ERROR events now include source and tile information where appropriate. New fields would allow developers to understand what source or tile has failed to load and the reason for a failure. ([#457](https://github.com/mapbox/mapbox-maps-android/pull/457))

## Bug fixes 🐞
* Fix dropping annotation source updates if those were emitted rapidly without handler. ([#441](https://github.com/mapbox/mapbox-maps-android/pull/441))
* Fix raster/v1 terrain tiles fetch failures caused by appending pixel ratio to the URLs when tile size is equal to 512. ([#457](https://github.com/mapbox/mapbox-maps-android/pull/457))
* Fixed an issue that the LayerPosition is not persisted across the style change, when using persistent layer based annotation plugin and location component plugin. ([#457](https://github.com/mapbox/mapbox-maps-android/pull/457))
* Disable MapboxTelemetryInitProvider if the telemetry is disabled via app's manifest reducing startup time. ([#442](https://github.com/mapbox/mapbox-maps-android/pull/442))

## Dependencies
* Bump gl-native to v10.0.0-rc.3, common to v14.2.0. ([#442](https://github.com/mapbox/mapbox-maps-android/pull/442))
* Bump telemetry to 8.1.0. ([#457](https://github.com/mapbox/mapbox-maps-android/pull/457))

# 10.0.0-rc.2 June 23, 2021

## Features ✨ and improvements 🏁
* Introduce experimental `Style#addPersistentLayer`, `Layer#isPersistent`, `Style#addPersistentStyleLayer`, `Style#addPersistentStyleCustomLayer` and `Style#isStyleLayerPersistent` APIs, so that the tagged layer and its associated resources would remain when a style is reloaded. This improves performance of Annotation and Location Component Plugin during the style change. ([#368](https://github.com/mapbox/mapbox-maps-android/pull/368), ([#422](https://github.com/mapbox/mapbox-maps-android/pull/422)))
* Add Localization API to apply languages to the style by provided locale. ([#379](https://github.com/mapbox/mapbox-maps-android/pull/379))
* Reduce unnecessary render cache texture updates by introducing a small delay after zoom has changed.
* Save and read application state on a background thread, to avoid delays (~3-5ms) on the main thread.

## Bug fixes 🐞
* Introduce size check for render cache. ([#425](https://github.com/mapbox/mapbox-maps-android/pull/425))
* Fix memory leak on render destroy. ([#426](https://github.com/mapbox/mapbox-maps-android/pull/426))
* Changes the visibility of jsonObject in annotation to protected, fix ConcurrentModificationException ([#427](https://github.com/mapbox/mapbox-maps-android/pull/427))
* Fix camera deadlock use-case. ([#439](https://github.com/mapbox/mapbox-maps-android/pull/439))
* Tileset descriptor resolving fixes:
  - Operation completes even if the offline manager instance gets out of scope
  - Fixes leaking TilesetResolverObserver instance
  - Fixes possible crash on cancellation of pending style pack download operation
* Fix text rendering when both 'text-rotate' and 'text-offset' are set.
* Fix Android 12 compatibility to support [pending intents mutability](https://developer.android.com/about/versions/12/behavior-changes-12#pending-intent-mutability).

## Dependencies
* Bump gl-native to v10.0.0-rc.2 ([#422](https://github.com/mapbox/mapbox-maps-android/pull/422))
* Bump telemetry to v8.0.0, android core to v5.0.0 ([#423](https://github.com/mapbox/mapbox-maps-android/pull/423))

# 10.0.0-rc.1 June 10, 2021

## Breaking changes ⚠️
* Rename setter for `Light` object from `add` to `set`. This matches API from GL-JS and clarifies there is only 1 Light object. ([#387](https://github.com/mapbox/mapbox-maps-android/pull/387))
* Rename setter for `Terrain` object from `add` to `set`. ([#391](https://github.com/mapbox/mapbox-maps-android/pull/391))
* Remove `CacheManager`. In the following releases, an API to control temporary map data may be provided. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Remove `ResourceOptions::cacheSize` and `DefaultAmbientCacheSize` constant. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Replace `ResourceOptions::cachePath` with `ResourceOptions::dataPath` that accepts a folder in which the map stores offline style packages and temporary map data. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Rename `TileStore::getInstance()` to `TileStore::create()`. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Remove the `MapView#setRenderCache` and `MapSurface#setRenderCache` API and replaced them with experimental `MapboxMap#setRenderCacheOptions` and `MapboxMap#getRenderCacheOptions` APIs. ([#401](https://github.com/mapbox/mapbox-maps-android/pull/401))
* Change the default `ResourceOptions#dataPath` to `${context.filesDir.absolutePath}/.mapbox/map_data/` and the database name from `ambient_cache.db` to `map_data.db`. ([#403](https://github.com/mapbox/mapbox-maps-android/pull/403))

## Features ✨ and improvements 🏁
* The amount of the unique maps tile packs used in the offline regions is capped by the maximum amount equal to 750. The tile region loading is not be performed if it would cause exceeding of the tile pack limit. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))

## Bug fixes 🐞
* Fix a typo in `MapboxMapUtils` jvm name. ([#396](https://github.com/mapbox/mapbox-maps-android/pull/396))
* Fix an issue that vertical text was not positioned correctly if the `text-offset` property was used. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Emit `MapLoadingError` when an empty token is provided for accessing Mapbox data sources. Before the fix, the application may crash if an empty token was provided and map tries to load data from Mapbox data source. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Create folder structure for provided `ResourceOptions#dataPath` when a provided folder doesn't exist. Before the fix, map expected the folder to exist, and in case it didn't, it was difficult to report an error to the application. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Do not emit `MapLoadingError` when an empty URL is set to GeoJSON source. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Avoid packaging `gms-play-services-location` by default as part of the Android SDK. ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))
* Fix an issue that causes public resource definitions not generated in public.txt file. ([#404](https://github.com/mapbox/mapbox-maps-android/pull/404))

## Dependencies
* Bump gl-native to v10.0.0-rc.1, common to v14.0.1 ([#399](https://github.com/mapbox/mapbox-maps-android/pull/399))

# 10.0.0-beta.21- June 3, 2021
## Breaking changes ⚠️
* Align load style functions for MapboxMap and Snapshotter. ([#371](https://github.com/mapbox/mapbox-maps-android/pull/371))
* Change the default ambient cache path to `.mapbox/maps/ambient_cache.db` ([#373](https://github.com/mapbox/mapbox-maps-android/pull/373))
* Move text-font property from PointAnnotation to PointAnnotationManager ([#375](https://github.com/mapbox/mapbox-maps-android/pull/375))
* Remove CredentialsManager in favour of ResourceOptionsManager ([#365](https://github.com/mapbox/mapbox-maps-android/pull/365))
* Introduce separate minZoom/maxZoom fields into CustomGeometrySourceOptions API instead of the formerly used "zoomRange"

## Features ✨ and improvements 🏁
* Rework setPrefetchZoomDelta to reduce loading of expensive tiles and optimize zoom use-case (#1850)
* Send billing event when Map is loaded

## Bug fixes 🐞
* Fixed an issue that causes OnStyleLoaded callback not fired when there's a sprite loading error. ([#358](https://github.com/mapbox/mapbox-maps-android/pull/358))
* Update map camera on first animator update. ([#352](https://github.com/mapbox/mapbox-maps-android/pull/352))
* Fix crash due to missing access token ([#365](https://github.com/mapbox/mapbox-maps-android/pull/365))
* Call style loaded callback if data set directly to geojson. ([#377](https://github.com/mapbox/mapbox-maps-android/pull/377))
* Geojson async data parsing: fixes and improvements. ([#380](https://github.com/mapbox/mapbox-maps-android/pull/380))
* Fix terrain transparency issue when a sky layer is not used
* Make style pack resources immutable protecting the style pack from getting out of sync in case the style is updated remotely

## Dependencies
* Bump glNative to 10.0.0-beta.23, common to 13.0.0 ([#362](https://github.com/mapbox/mapbox-maps-android/pull/362))
* Bump mapbox-events-android to latest releases telem-7.0.3 and core-4.0.2 ([#370](https://github.com/mapbox/mapbox-maps-android/pull/370))

# 10.0.0-beta.20 - May 20, 2021
## Breaking changes ⚠️
* Introduce ResourceOptionsManager to configure the default resource options, and removed the xml configuration options for cache path and tile store path. ([#339](https://github.com/mapbox/mapbox-maps-android/pull/339))
* Rename default ambient cache database to mapbox/maps/ambient_cache.db  ([#314](https://github.com/mapbox/mapbox-maps-android/pull/314))
* Remove the usage of asset path from the codebase, as it is not useful in Android Maps SDK. ([#334](https://github.com/mapbox/mapbox-maps-android/pull/334))
* Rename `NetworkConnectivity` to `OfflineSwitch`.
* Remove `TileLoadOptions` from `TileRegionLoadOptions`. `networkRestriction` and `acceptExpired` can now be specified directly in `TileRegionLoadOptions`.
* Add `totalBytes` and `transferredBytes` to TileStoreLoadResult.
* `MapboxMap.setBounds` return type changed from Void to Expected.
* Expose tileset version for sources that use TileJSON metadata.
* ResourceRequest `offline-data` boolean field is replaced with the `source` string field, which whether the response came from network, cache or tile store.
* Remove `Style.getStyleGeoJSONSourceClusterLeaves`, `Style.getStyleGeoJSONSourceClusterExpansionZoom`, `Style.getStyleGeoJSONSourceClusterChildren`. All those can be fully replaced by `MapboxMap.queryFeatureExtensions`.
* Parsing geojson on a worker thread. Using DSL GeoJsonSource builders with the following functions `GeoJsonSource.Builder#feature`, `GeoJsonSource.Builder#featureCollection`, `GeoJsonSource.Builder#geometry` will immediately returns GeoJsonSource with no data set and starts preparing actual data using a worker thread. The data will be set to the GeoJsonSource once parsed. ([#327](https://github.com/mapbox/mapbox-maps-android/pull/327))

## Features ✨ and improvements 🏁
* Add a `cameraOptions(cameraState, builderBlock)` inline method that helps mutate an existing `CameraState` object. ([#317](https://github.com/mapbox/mapbox-maps-android/pull/317))
* Add selected state handling to annotation plugin ([#316](https://github.com/mapbox/mapbox-maps-android/pull/316))
* Add API for disabling vertical/horizontal scroll gestures ([#319](https://github.com/mapbox/mapbox-maps-android/pull/319))
* Introduce API to enable render cache feature that could bring up rendering performance improvement. ([#326](https://github.com/mapbox/mapbox-maps-android/pull/326))
* Add `removeAnnotationManager` API. ([#330](https://github.com/mapbox/mapbox-maps-android/pull/330))
* Improve terrain's rendering performance
* Set `begin` and `end` timestamps for StyleLoaded and MapLoaded events, so that developers could check how much time it takes to load style and map, respectively
* Added `tile-requests-delay` and `tile-network-requests-delay` runtime source properties - an API for tile requests delay
* Introduce MapOptions.optimizeForTerrain option that allow style rendering optimizations for terrain rendering
* The `text-line-height` is now data-driven property
* MapLoaded, StyleLoaded and StyleDataLoaded events now contain begin and end timestamps reflecting the effective duration timespan
* When line lablels are inside the flip state retaining range (+/- 5 degrees around the vertical direction), the lables' flip state will be kept the same
* Improve rendering quality of fill outlines when using render cache

## Bug fixes 🐞
* Fix scalebar doesn't refresh issue. ([#331](https://github.com/mapbox/mapbox-maps-android/pull/331))
* Trigger nested high-level animator listener correctly. ([#335](https://github.com/mapbox/mapbox-maps-android/pull/335))
* Make compass visible when camera was mutated while compass was disabled. ([#322](https://github.com/mapbox/mapbox-maps-android/pull/322))
* Enable LocationComponent automatically when style loaded; fix null island location puck ([#333](https://github.com/mapbox/mapbox-maps-android/pull/333))
* Fix crash if the belowLayerId doesn't exist on the current style ([#330](https://github.com/mapbox/mapbox-maps-android/pull/330))
* Fixed an issue that style pack download cancels pending tileset descriptor resolving, now tile region loading and style pack loading can work in parallel.
* Fixed the excessive network usage during map browsing caused by losing of the expiration date and the etag for the cached files
* Fix excessive network usage for delayed tile requests
* On style pack update we reset only glyphs and only when the updated options require less glyphs than currently available and we make sure ambient cache size limit is never exceeded
* Emit `StyleDataLoaded` and `SourceDataLoaded` synchronously if possible, so that developers could modify style and sources before map starts rendering style
* Fix occasional Adreno 640 and 620 driver warnings and deadlock when terrain is used
* Fix rendering order of transparent terrain proxy tiles

## Dependencies
* Update telemetry (v7.0.1) and core (v4.0.1) dependencies to latest major version releases ([#337](https://github.com/mapbox/mapbox-maps-android/pull/337))
* Bump gl-native to v10.0.0-beta.22 and common to v12.0.0. ([#338](https://github.com/mapbox/mapbox-maps-android/pull/338))

# 10.0.0-beta.19 - May 5, 2021
## Breaking changes ⚠️
* Remove temporary CustomMapInterface used for testing, obsolete with having interface inheritance from upstream. ([#296](https://github.com/mapbox/mapbox-maps-android/pull/296))
* Align MapCameraManagerDelegate with MapCameraManagerInterface ([#293](https://github.com/mapbox/mapbox-maps-android/pull/293))
* Refactor CameraOptions and change `MapboxMap.getCameraState` method to return non-nullable CameraState record
* Remove `MapboxMap.getMinZoom`, `MapboxMap.getMaxZoom` and `MapboxMap.getScale` methods that are duplicate of functionality provided by `MapboxMap.getBounds`. `MapboxMap.getBounds` returns new CameraBounds type with non-nullable fields.
* Remove `MapboxMap.setDefaultFramebufferObject`.
* Remove `MapboxMap.dumpDebugLog`.
* Remove `isPanning`, `isRotating`, `isScaling` and `cancelTransitions` methods from MapboxMap. Controlling map animations should be done with camera animation plugin.
* Remove following methods from MapCameraManagerDelegate (formerly MapCameraDelegate): getLan(), getLon(), getPitch(), getBearing(), getPadding(), setBearing(double). Those properties could be accessed now from MapCameraManagerDelegate.cameraState directly.

### OfflineManager and CacheManager
* Introduce TileStoreUsageMode enum and use it in resource options. New enum allows to set tile store usage mode in an non ambiguous way.
* Cache manager asynchronous  calls complete even after the cache manager instance gets out of scope on the client side. Fix possible crash on setDatabasePath() call
* CacheManager::prefetchAmbientCache() semantics is updated


## Features ✨ and improvements 🏁
* Add styleUri property in MapInitOptions ([#287](https://github.com/mapbox/mapbox-maps-android/pull/287))
* Refactored plugin system to have more granular control over which plugins are loaded when creating a MapView programmatically. ([#231](https://github.com/mapbox/mapbox-maps-android/pull/231))
* Instrument tests for offline ([#290](https://github.com/mapbox/mapbox-maps-android/pull/290))
* Cleanup kdoc documentation, remove html tags ([#305](https://github.com/mapbox/mapbox-maps-android/pull/305))
* Reduce GPU memory usage by reusing depth stencil buffer for terrain rendering


## Bug fixes 🐞
* Request layout when updating ornaments margins, making updates immediate ([#292](https://github.com/mapbox/mapbox-maps-android/pull/292))
* Remove runtime plugin dependency for legacy location plugin. ([#295](https://github.com/mapbox/mapbox-maps-android/pull/295))
* Fix an issue that causes the extension functions not discoverable from downstream projects. ([#299](https://github.com/mapbox/mapbox-maps-android/pull/299))
* Style and map error loading listeners are only called for the style that was associated to the listeners when style loading started. This avoid calling the wrong listeners with multiple style loads. Renamed Style#isStyleLoadInited to Style#isStyleLoadInitiated. ([#300](https://github.com/mapbox/mapbox-maps-android/pull/300))
* Fix crash if doing setCamera during map loading ([#310](https://github.com/mapbox/mapbox-maps-android/pull/310))
* Fix map rendering issue when `text-field`'s inline images used with complex case expressions
* Fix erroneous font eviction when `text-field`'s formatted sections have `text-font` overrides
* Fix Adreno specific crash that happens when terrain is enabled
* Fix OfflineManager network errors handling
* Fix map rendering issue when feature-state and terrain features are enabled
* The ResourceRequest event response.offline-data field now indicates whether or not the response came from tile store


## Dependencies
* Bump to gl-native v10.0.0-beta.21, update common v11.0.2. ([#304](https://github.com/mapbox/mapbox-maps-android/pull/304))

# 10.0.0-beta.18 - April 22, 2021
## Breaking changes ⚠️
* Rename MapView plugin extension functions. ([#272](https://github.com/mapbox/mapbox-maps-android/pull/272))
  - mapView.getAnnotationPlugin() -> mapView.annotations
  - mapView.getGesturesPlugin() -> mapView.gestures
  - mapView.getOverlayPlugin() -> mapView.overlay() // using function here because of experimental annotation
  - mapView.getLocationComponentPlugin() -> mapView.location
  - mapView.getCameraAnimationsPlugin() -> mapView.camera
  - mapView.getAttributionPlugin() -> mapView.attribution
  - mapView.getCompassPlugin() -> mapView.compass
  - mapView.getLogoPlugin() -> mapView.logo
  - mapView.getScaleBarPlugin() -> mapView.scalebar
* Remove deprecated location plugin ([#276](https://github.com/mapbox/mapbox-maps-android/pull/276))
* Add feature sdk initialisation ([#269](https://github.com/mapbox/mapbox-maps-android/pull/269))
  - Load the Mapbox Street style by default if user doesn't load any style before the onStart lifecycle event.
  - Introduce `CredentialsManager` to manage mapbox access token, when all `MapView`s should use same token could be handled by using `CredentialsManager.shared` static object.
  - Introduce `MapInitOptions` to replace MapboxMapOptions.
## Features ✨ and improvements 🏁
* High-level animations return cancelable interface ([#262](https://github.com/mapbox/mapbox-maps-android/pull/262))
* Introduce OfflineManager API that manages style packs and produces tileset descriptors for the tile store.
  - By default, users may download up to 250MB of data for offline use without incurring additional charges. This limit is subject to change during the beta.
  - The new API replaces the deprecated OfflineRegionManager API. The OfflineManager API can be used to create offline style packs that contain style data, such as: style definition, sprites, fonts and other resources. Tileset descriptors created by the OfflineManager API are used to create tile packs via TileStore API. Mobile maps SDKs use tile packs for rendering map content.
* Add offline activity example. ([#259](https://github.com/mapbox/mapbox-maps-android/pull/259))
* Load the Mapbox Street style by default if user doesn't load any style before the onStart lifecycle event([#248](https://github.com/mapbox/mapbox-maps-android/pull/248))

## Bug fixes 🐞
* Keep CompassPlugin enabled/disabled state after other properties update ([#252](https://github.com/mapbox/mapbox-maps-android/pull/252))
* Fix disabling logo in xml. ([#273](https://github.com/mapbox/mapbox-maps-android/pull/273))
* Introduce StyleInterface that include the current display's pixel ratio, and fix Style#addImage to take the correct pixel ratio from display.  ([#228](https://github.com/mapbox/mapbox-maps-android/pull/228))
* Properly reset anchor after some gestures。 ([#279](https://github.com/mapbox/mapbox-maps-android/pull/279))
* Remove animator cancel listeners logic duplicating end listeners logic. ([#280](https://github.com/mapbox/mapbox-maps-android/pull/280))

## Dependencies
* Bump gl-native to v10.0.0-beta.20, common to v11.0.1 ([#261](https://github.com/mapbox/mapbox-maps-android/pull/261))

# 10.0.0-beta.17 - April 14, 2021
## Breaking changes ⚠️
* [Annotation plugin] Rename annotation classes, the rules are as follows and applied for Annotations/Options/Managers ([#227](https://github.com/mapbox/mapbox-maps-android/pull/227))
    - Symbol -> PointAnnotation
    - Circle -> CircleAnnotation
    - Line -> PolylineAnnotation
    - Fill -> PolygonAnnotation
* mapboxMap.queryRenderedFeatures will return a new data class QueriedFeature which will contain additional properties ([#247](https://github.com/mapbox/mapbox-maps-android/pull/247)):
    - source (id of the source)
    - sourceLayer (id of the source's layer)
    - state (feature's state)
* Rename Style#isStyleFullyLoaded to Style#isStyleLoaded
* Remove old map#drag API and the AnimationOptions API
* Don't emit MapIdle event when there is gesture and / or user animation in progress
* Make overlay plugin experimental ([#233](https://github.com/mapbox/mapbox-maps-android/pull/233))

## Features ✨ and improvements 🏁
* Introducing NetworkConnectivity API (offline switch). If setting setMapboxStackConnected(false), the Reachability API will report NotReachable, http requests are being blocked and if running, cancelled.
* Added new CameraManager.cameraForCoordinates overload
* Added support for query rendered features for Circle Layer on Terrain
* Enable identical code folding and -lto-O3 optimizations to reduce binary size of native map shared library

## Bug fixes 🐞
* Fix runtime crash if logo / attribution not enabled ([#240](https://github.com/mapbox/mapbox-maps-android/pull/240))
* Fixed a bug that causes map not loading when connected through ethernet.
* Fix distance expression parsing for geometries containing over 20k points
* Fixed holes in the ground for terrain with negative elevation
* Make StyleImageMissing callback a no-op after ImageManager destruction
* Reset unprocessed event queue for destructed renderer
* Fix clipping of fill-extrusions on near plane
* Set 'clusterMaxZoom' to be 'maxZoom-1' when it is not provided
* Fix crash for the case when MapSnapshotter object is destructed from within user provided callback
* Terrain render cache now disabled during property transitions
* Fix missing terrain tiles when camera is on mountain peak
* Black screen not used during loading anymore, prefering platform background
* Emit CameraChanged, SourceAdded(Removed) and StyleImageMissing events synchronously

## Dependencies
* [deps] Bump gl-native to v10.0.0-beta.19, common to v11.0.0 ([#247](https://github.com/mapbox/mapbox-maps-android/pull/247))

# 10.0.0-beta.16 - March 25, 2021

## Breaking changes ⚠️
* Remove ModelLayer and ModelSource API ([#128](https://github.com/mapbox/mapbox-maps-android/pull/128))
* Refactor Event API into new Observers. ([#166](https://github.com/mapbox/mapbox-maps-android/pull/166))
* Bump minSdkVersion of the SDK to 21, and bumped okhttp dependency to v4.9.0. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Rename jumpTo(options: CameraOptions) to setCamera(options: CameraOptions), rename setFreeCameraOptions (options: FreeCameraOptions) to setCamera(options: FreeCameraOptions). ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Rename OnMapLoadingFinishedListener to OnMapLoadedListener. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Introduce OnStyleDataLoadedListener to replace OnStyleLoadingFinishedListener, and introduce OnSourceDataLoadedListener to replace OnSourceChangedListener. So that developers have granular control of style/source loading status. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Introduce coordinateBoundsForCamera() API to replace the getRegion() API. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Remove MapObserver from MapSurface's constructor. ([#200](https://github.com/mapbox/mapbox-maps-android/pull/200))

## Features ✨ and improvements 🏁
* [Annotation plugin] Add symbol cluster support ([#122](https://github.com/mapbox/mapbox-maps-android/pull/122))
* [map] Make public API entry points as MapView and Snapshotter ([#149](https://github.com/mapbox/mapbox-maps-android/pull/149))
* [plugins] Remove PluginRequirementException ([#158](https://github.com/mapbox/mapbox-maps-android/pull/158))
* Use String protocol for passing GeoJSON data  ([#162](https://github.com/mapbox/mapbox-maps-android/pull/162))
* [Annotation plugin] Set default values for annotation option properties to null. ([#173](https://github.com/mapbox/mapbox-maps-android/pull/173))
* [rendering] Schedule non-rendering tasks on Android's scheduler to improve render performance. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* [rendering] Query rendered features now work for fill-extrusions when terrain is enabled. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* [rendering] Improved terrain rendering performance due to reduction of loaded tiles. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* [doc] Change http link to markdown style in doc  ([#187](https://github.com/mapbox/mapbox-maps-android/pull/187))
* [rendering] Differentiate render tasks and non-render tasks ([#192](https://github.com/mapbox/mapbox-maps-android/pull/192))
* [gestures] Introduce platform-driven drag API to move a map ([#201](https://github.com/mapbox/mapbox-maps-android/pull/201))

## Bug fixes 🐞
* [Annotation plugin] Implement MapStyleObserverPlugin to listen style load event to reload layer and source ([#161](https://github.com/mapbox/mapbox-maps-android/pull/161))
* [gestures] Fix crash if zooming for SDK less than 23 ([#171](https://github.com/mapbox/mapbox-maps-android/pull/171))
* Fix an issue that will result in map not rendering on a device with Ethernet connection. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Fix the crash when running maps on the emulator. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Patch scroll gesture with a pitched camera ([#184](https://github.com/mapbox/mapbox-maps-android/pull/184))
* [locationcomponent] Fix jitter animations if interrupting animations ([#185](https://github.com/mapbox/mapbox-maps-android/pull/185))
* [animation] Fix zero duration animators, fix medium-level animators to use only CameraAnimators ([#198](https://github.com/mapbox/mapbox-maps-android/pull/198))
* [animations] Fix interpolation for flyTo ([#202](https://github.com/mapbox/mapbox-maps-android/pull/202))

## Dependencies
* Update minSdkVersion of the SDK to 21, and bumped okhttp dependency to v4.9.0. ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))
* Update gl-native to v10.0.0-beta.17, common to v10.0.2 ([#176](https://github.com/mapbox/mapbox-maps-android/pull/176))

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
