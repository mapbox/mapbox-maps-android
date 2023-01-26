# Changelog for Mapbox Maps SDK v10 for Android

Mapbox welcomes participation and contributions from everyone.

# main


# 10.11.0-rc.1 January 26, 2023
## Features ✨ and improvements 🏁
* Improve performance for style switch use cases by avoiding unneeded tiles re-layout. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))

## Bug fixes 🐞
* Fix a bug where `flyTo` animation request invalid tiles from map engine. ([1949](https://github.com/mapbox/mapbox-maps-android/pull/1949))
* Deprecate `pattern` and `dash` transition properties for layer (e.g. `BackgroundLayer.backgroundPatternTransition`, `FillExtrusionLayer.fillExtrusionPatternTransition`, `FillLayer.fillPatternTransition`, `LineLayer.lineDasharrayTransition`, `LineLayer.linePatternTransition`, ...).  ([1941](https://github.com/mapbox/mapbox-maps-android/pull/1941))
* Fix terrain tiles missing issue when running in the emulator and some android devices. ([1953](https://github.com/mapbox/mapbox-maps-android/pull/1953))

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
