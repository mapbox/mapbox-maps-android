# Changelog for Mapbox Maps SDK Carbon for Android

Mapbox welcomes participation and contributions from everyone.

## 10.0.0-beta.11 - January 13, 2021
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.10...android-v10.0.0-beta.11) since [Mapbox Maps SDK for Android 10.0.0-beta.10](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.10)

## Features ✨ and improvements 🏁
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#936](https://github.com/mapbox/mapbox-maps-android/pull/936))
* Improved the experience of using the constructor by better ordering and default argument for pixel density ([#895](https://github.com/mapbox/mapbox-maps-android/pull/895))
* [animation] hide animators from public API ([#887](https://github.com/mapbox/mapbox-maps-android/pull/887))
* Provide a default tile store path as part of map initialisation ([#908](https://github.com/mapbox/mapbox-maps-android/pull/908))
* delay testCancelAnimatorWithDelayed test more to make sure the animation has finished. ([#916](https://github.com/mapbox/mapbox-maps-android/pull/916))
* Make MapView a thin client, refactor plugin dependencies, add MapSurface test example, migrate to using Surface as implementation hook for MapSurface. ([#915](https://github.com/mapbox/mapbox-maps-android/pull/915))
* Make ViewPlugin and ContextBinder not mutually exclusive. ([#918](https://github.com/mapbox/mapbox-maps-android/pull/918))
* [style-extension] Add convenient static methods to build rgb and rgba expression. ([#891](https://github.com/mapbox/mapbox-maps-android/pull/891))
* Add convenience expressions to make migration from v9.0.0 more easy and make the expression api less typed. ([#919](https://github.com/mapbox/mapbox-maps-android/pull/919))
* Update the default icon colour of Attribution plugin. ([#921](https://github.com/mapbox/mapbox-maps-android/pull/921))
* Add annotation plugin ([#899](https://github.com/mapbox/mapbox-maps-android/pull/899))
* [renderer] fix memory leak SurfaceHolder ([#926](https://github.com/mapbox/mapbox-maps-android/pull/926))
* Add MapStyleStateDelegate to check if style has been fully loaded. ([#928](https://github.com/mapbox/mapbox-maps-android/pull/928))
* Make target for annotation code generation + CI check. ([#930](https://github.com/mapbox/mapbox-maps-android/pull/930))
* Use animator options builder to simplify animator creation ([#920](https://github.com/mapbox/mapbox-maps-android/pull/920))
* Refactor attribution plugin. ([#888](https://github.com/mapbox/mapbox-maps-android/pull/888))
* Add Owner option, add API to listen to camera animation lifecycle ([#935](https://github.com/mapbox/mapbox-maps-android/pull/935))
* Add annotation data-driven properties support ([#933](https://github.com/mapbox/mapbox-maps-android/pull/933))
* [location] depend on style loaded boolean from style.kt, add test for updating location while style loads ([#941](https://github.com/mapbox/mapbox-maps-android/pull/941))
* Disable tile store path ([#940](https://github.com/mapbox/mapbox-maps-android/pull/940))
### Tests
* Add annotation instrumentation tests. ([#931](https://github.com/mapbox/mapbox-maps-android/pull/931))
### Examples
* Render to a Surface, wallpaper example ([#907](https://github.com/mapbox/mapbox-maps-android/pull/907))
* Add example integration for mapView snapshot, Fix native crash with reusing PixelReader ([#917](https://github.com/mapbox/mapbox-maps-android/pull/917))
### Dependencies
* update codebase to match v10.0.0-beta.11 release. ([#932](https://github.com/mapbox/mapbox-maps-android/pull/932))

## 10.0.0-beta.10 - December 15, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.9...android-v10.0.0-beta.10) since [Mapbox Maps SDK for Android 10.0.0-beta.9](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.9)

## Features ✨ and improvements 🏁
* Change default cachesize of database from 50kb to 50mb. ([#866](https://github.com/mapbox/mapbox-maps-android/pull/866))
* Remove not working fontcache ([#865](https://github.com/mapbox/mapbox-maps-android/pull/865))
* Optional plugin interfaces functions with default empty implementation ([#864](https://github.com/mapbox/mapbox-maps-android/pull/864))
* Remove factories. ([#861](https://github.com/mapbox/mapbox-maps-android/pull/861))
* Fix leak in TextureViewAnimateActivity ([#879](https://github.com/mapbox/mapbox-maps-android/pull/879))
* Port force location update using LocationUpdate model class. ([#880](https://github.com/mapbox/mapbox-maps-android/pull/880))
* Add build-from-source-clean make command ([#884](https://github.com/mapbox/mapbox-maps-android/pull/884))
* Make ViewPlugin get the real width and height of MapView. ([#876](https://github.com/mapbox/mapbox-maps-android/pull/876))
## Tests
* Fix flaky android test ([#852](https://github.com/mapbox/mapbox-maps-android/pull/852))
* Add robo tests for rendering ([#878](https://github.com/mapbox/mapbox-maps-android/pull/878))

## 10.0.0-beta.9 - December 3, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.8...android-v10.0.0-beta.9) since [Mapbox Maps SDK for Android 10.0.0-beta.8](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.8)

## Features ✨ and improvements 🏁
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#789](https://github.com/mapbox/mapbox-maps-android/pull/789), [#800](https://github.com/mapbox/mapbox-maps-android/pull/800))
* IdeographsRasterizedLocally the default mode for glyphs rasterization mode ([#799](https://github.com/mapbox/mapbox-maps-android/pull/799))
* [camera] Hide most of animation plugin itself, rework public API ([#798](https://github.com/mapbox/mapbox-maps-android/pull/798))
* [camera] Move StartValue to separate file, update animation README.md ([#809](https://github.com/mapbox/mapbox-maps-android/pull/809))
* Create cameraOptionsBuilder for every parseCameraOptions to avoid caching properties ([#817](https://github.com/mapbox/mapbox-maps-android/pull/817))
* [sky] Add gesture logic for high pitch values ([#822](https://github.com/mapbox/mapbox-maps-android/pull/822))
* [location] fix icon size of location puck ([#803](https://github.com/mapbox/mapbox-maps-android/pull/803))
* Add lambda support for loadStyle. ([#823](https://github.com/mapbox/mapbox-maps-android/pull/823))
* Add MapOverlay plugin ([#740](https://github.com/mapbox/mapbox-maps-android/pull/740))
* [docs] update documentation on MapboxMap ([#826](https://github.com/mapbox/mapbox-maps-android/pull/826))
* Add translucent flag for texture view, add transparent background example ([#833](https://github.com/mapbox/mapbox-maps-android/pull/833))
* Check access token while using resourceOptions ([#832](https://github.com/mapbox/mapbox-maps-android/pull/832))
* Update reframe interface ([#834](https://github.com/mapbox/mapbox-maps-android/pull/834))
* [style] Add default scale for image extension ([#844](https://github.com/mapbox/mapbox-maps-android/pull/844))
* [map] by default constrain height only ([#846](https://github.com/mapbox/mapbox-maps-android/pull/846))
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#847](https://github.com/mapbox/mapbox-maps-android/pull/847))
* [sdk] default font fallback ([#841](https://github.com/mapbox/mapbox-maps-android/pull/841))
* [snapshotter] fix context memory leak ([#848](https://github.com/mapbox/mapbox-maps-android/pull/848))
* Use ClassNotFoundException when attempting to create plugins ([#854](https://github.com/mapbox/mapbox-maps-android/pull/854))
* Change Map.queryRenderedFeatures, querySourceFeatures, getFeatureState, queryFeatureExtension API to be async. ([#811](https://github.com/mapbox/mapbox-maps-android/pull/811))
* Remove ProjectionMode and Annotations API and Annotation plugin. ([#811](https://github.com/mapbox/mapbox-maps-android/pull/811))
* Remove OfflineManager ([#850](https://github.com/mapbox/mapbox-maps-android/pull/850))
* Add missing 'Style' keywords to StyleManager API function names ([#850](https://github.com/mapbox/mapbox-maps-android/pull/850))
* Improve Projection API by using proper data types ([#850](https://github.com/mapbox/mapbox-maps-android/pull/850))
### Tests
* Fix flaky scrollSurfaceRecyclerView test ([#821](https://github.com/mapbox/mapbox-maps-android/pull/821))
* Increate test coverage for scalebar plugin ([#830](https://github.com/mapbox/mapbox-maps-android/pull/830))
* Increate test coverage for observable extension ([#831](https://github.com/mapbox/mapbox-maps-android/pull/831))
### Examples
* Add example for junction view prototype ([#796](https://github.com/mapbox/mapbox-maps-android/pull/796))
* [app] add example on tinting fillLayer pattern ([#805](https://github.com/mapbox/mapbox-maps-android/pull/805))
* Add custom layer example ([#777](https://github.com/mapbox/mapbox-maps-android/pull/777))
* Add custom layer using C++ and manual JNI binding ([#789](https://github.com/mapbox/mapbox-maps-android/pull/789))
* Add triangle custom layer example with projection ([#800](https://github.com/mapbox/mapbox-maps-android/pull/800))
### Dependencies
* Update to gl-native release to v10.0.0-beta.10, bump common to 10.0.0-beta.4 ([#850](https://github.com/mapbox/mapbox-maps-android/pull/850))

## 10.0.0-beta.8 - November 18, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.7...android-v10.0.0-beta.8) since [Mapbox Maps SDK for Android 10.0.0-beta.7](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.7)

## Features ✨ and improvements 🏁
* Remove {@inheritdoc} from documentation ([#742](https://github.com/mapbox/mapbox-maps-android/pull/742))
* [sdk] increase code coverage of sdk module ([#738](https://github.com/mapbox/mapbox-maps-android/pull/738))
* [tests] Re-enable ignored source property getter instrumentation tests. ([#746](https://github.com/mapbox/mapbox-maps-android/pull/746))
* [snapshot] expose style API on snapshotter ([#749](https://github.com/mapbox/mapbox-maps-android/pull/749))
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#767](https://github.com/mapbox/mapbox-maps-android/pull/767))
* Enable snapshot builds ([#774](https://github.com/mapbox/mapbox-maps-android/pull/774))
* Replace text with suggested changelog entry for this PR or add the 'skip changelog' label. ([#750](https://github.com/mapbox/mapbox-maps-android/pull/750))
* [docs] update changelog for release v10.0.0-beta.7 ([#727](https://github.com/mapbox/mapbox-maps-android/pull/727))
* Revisit cleanup and remove enabled from MapPlugin interface. ([#726](https://github.com/mapbox/mapbox-maps-android/pull/726))
* Add listener parameter to camera high-level API ([#728](https://github.com/mapbox/mapbox-maps-android/pull/728))
* Set 30 min timeout for building instrument apk. ([#736](https://github.com/mapbox/mapbox-maps-android/pull/736))
* Throw exception while logo plugin and attribution plugin are removed. ([#734](https://github.com/mapbox/mapbox-maps-android/pull/734))
* [ci] add job to validate metrics build ([#739](https://github.com/mapbox/mapbox-maps-android/pull/739))
* [tests] move instrumented style tests to their own app module.  ([#724](https://github.com/mapbox/mapbox-maps-android/pull/724))
* Rework public API for animation plugin ([#745](https://github.com/mapbox/mapbox-maps-android/pull/745))
* [gl-native] update to v10.0.0-beta.8 ([#751](https://github.com/mapbox/mapbox-maps-android/pull/751))
* [style] rename terrain setters/getters exposed on MapStyleDelegate ([#754](https://github.com/mapbox/mapbox-maps-android/pull/754))
* [snapshotter] address renaming of style terrain setter/getter ([#761](https://github.com/mapbox/mapbox-maps-android/pull/761))
* [ci] rework CI execution ([#762](https://github.com/mapbox/mapbox-maps-android/pull/762))
* Unlock sky layer, set max possible pitch to 85 ([#764](https://github.com/mapbox/mapbox-maps-android/pull/764))
* [snapshotter] optimize public API for style loaded usage ([#758](https://github.com/mapbox/mapbox-maps-android/pull/758))
* Don't show copyright icon when there isn't any source attribution ([#772](https://github.com/mapbox/mapbox-maps-android/pull/772))
* Add sky layer test activity ([#773](https://github.com/mapbox/mapbox-maps-android/pull/773))
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#777](https://github.com/mapbox/mapbox-maps-android/pull/777))
* [build] remove remaining bintray setup ([#775](https://github.com/mapbox/mapbox-maps-android/pull/775))
* [build] use override when publishing a snapshot build ([#782](https://github.com/mapbox/mapbox-maps-android/pull/782))
* [style] remove MapStyleDelegate and replace with StyleManagerInterface ([#771](https://github.com/mapbox/mapbox-maps-android/pull/771))

## 10.0.0-beta.7 - October 30, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.6...android-v10.0.0-beta.7) since [Mapbox Maps SDK for Android 10.0.0-beta.6](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.6)
## Features ✨ and improvements 🏁
* Integrate ResourcesAttributeParser and MapAttributeParser ([#719](https://github.com/mapbox/mapbox-maps-android/pull/719))
* Rename GeojsonSource to GeoJsonSource ([#720](https://github.com/mapbox/mapbox-maps-android/pull/720))
* Update generated classes using latest spec ([#717](https://github.com/mapbox/mapbox-maps-android/pull/717))
* Update gl-native to v10.0.0-beta.7 ([#704](https://github.com/mapbox/mapbox-maps-android/pull/704))
* Using generated settings in logo plugin ([#705](https://github.com/mapbox/mapbox-maps-android/pull/705))
* Using generated settings in attribution plugin ([#707](https://github.com/mapbox/mapbox-maps-android/pull/707))
* Using generated settings in scalebar plugin ([#701](https://github.com/mapbox/mapbox-maps-android/pull/701))
* Using generated settings in compass plugin ([#702](https://github.com/mapbox/mapbox-maps-android/pull/702))
* Migrate Camera Locations animations to Camera API ([#671](https://github.com/mapbox/mapbox-maps-android/pull/671))
* Fix position default value ([#703](https://github.com/mapbox/mapbox-maps-android/pull/703))
* Fix flaky instrumentation tests ([#698](https://github.com/mapbox/mapbox-maps-android/pull/698))
* Rework generated settings contract ([#695](https://github.com/mapbox/mapbox-maps-android/pull/695))
* Move style from plugin to extension ([#693](https://github.com/mapbox/mapbox-maps-android/pull/693))
* Fix gesture options tests ([#694](https://github.com/mapbox/mapbox-maps-android/pull/694))
* Add synchronous getStyle API ([#689](https://github.com/mapbox/mapbox-maps-android/pull/689))
* Extension library for observable ([#695](https://github.com/mapbox/mapbox-maps-android/pull/665))
* Adding listener parameter to high-level animation API ([#664](https://github.com/mapbox/mapbox-maps-android/pull/664))
* Add generated plugin setting abstract classes ([#690](https://github.com/mapbox/mapbox-maps-android/pull/690) & [#695](https://github.com/mapbox/mapbox-maps-android/pull/695))

## 10.0.0-beta.6 - October 14, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.5...android-v10.0.0-beta.6) since [Mapbox Maps SDK for Android 10.0.0-beta.5](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.5)
## Features ✨ and improvements 🏁
* Enabled reusing objects with `Projection.getVisibleCoordinateBounds`. ([#662](https://github.com/mapbox/mapbox-maps-android/pull/662))
* Fix defaultModel does not have a uri error ([#652](https://github.com/mapbox/mapbox-maps-android/pull/652))
* Add README.md for scale bar plugin. ([#649](https://github.com/mapbox/mapbox-maps-android/pull/649))
* Update CHANGELOG and README for v10.0.0-beta.5. ([#657](https://github.com/mapbox/mapbox-maps-android/pull/657))
* Fix CI scripts for uploading to sdk registry. ([#656](https://github.com/mapbox/mapbox-maps-android/pull/656))
* Convert LocationAnimationCoordinator to kotlin ([#655](https://github.com/mapbox/mapbox-maps-android/pull/655))
* Fix memory leak caused by observers. ([#659](https://github.com/mapbox/mapbox-maps-android/pull/659))
* Add README.md for annotation plugin. ([#650](https://github.com/mapbox/mapbox-maps-android/pull/650))
* Fixes required for mapbox-common 9.0.0 and maps-v10.0.0-beta.6 releases ([#653](https://github.com/mapbox/mapbox-maps-android/pull/653))

## 10.0.0-beta.5 - October 9, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.4...android-v10.0.0-beta.5) since [Mapbox Maps SDK for Android 10.0.0-beta.4](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.4)
### Improvements and bug fixes
* Rework plugins and contracts, inner plugin dependencies ([#587](https://github.com/mapbox/mapbox-maps-android/pull/587))
* How-to use low level animator API ([#560](https://github.com/mapbox/mapbox-maps-android/pull/560))
* Add setMaxFps method ([#602](https://github.com/mapbox/mapbox-maps-android/pull/602))
* Make plugin-scale depends on sdk-base ([#614](https://github.com/mapbox/mapbox-maps-android/pull/614))
* Extend MapboxMap API to work with gestures listeners ([#617](https://github.com/mapbox/mapbox-maps-android/pull/617))
* Add Choreographer to render thread ([#627](https://github.com/mapbox/mapbox-maps-android/pull/627))
* Fix memory leak in OfflineServiceActivity.kt ([#624](https://github.com/mapbox/mapbox-maps-android/pull/624))
* Add choreographer to camera animation engine ([#629](https://github.com/mapbox/mapbox-maps-android/pull/629))
* Address feedback on plugin reloading and removal of plugins  ([#632](https://github.com/mapbox/mapbox-maps-android/pull/632))
* Allow MapboxMapOptions for non-xml attributes configuration ([#639](https://github.com/mapbox/mapbox-maps-android/pull/639))
* Make LocationPlugin independent from StylePlugin ([#641](https://github.com/mapbox/mapbox-maps-android/pull/641))
* Change gesture plugin's artifact id to maps-gestures. ([#648](https://github.com/mapbox/mapbox-maps-android/pull/648))
* Fix defaultModel does not have a uri error ([#652](https://github.com/mapbox/mapbox-maps-android/pull/652))
### Examples
* Lines/Polygons - Draw a GeoJson line. ([#584](https://github.com/mapbox/mapbox-maps-android/pull/584))
### Dependencies
* Update to gl-native release v10.0.0-alpha.5 ([#613](https://github.com/mapbox/mapbox-maps-android/pull/613))
* Bump core to v3.1.0 and telemetry to v6.2.0 ([#647](https://github.com/mapbox/mapbox-maps-android/pull/647))

## 10.0.0-beta.4 - September 25, 2020
[Changes](https://github.com/mapbox/mapbox-maps-android/compare/android-v10.0.0-beta.3...android-v10.0.0-beta.4) since [Mapbox Maps SDK for Android 10.0.0-beta.3](https://github.com/mapbox/mapbox-maps-android/releases/tag/android-v10.0.0-beta.3)
### Improvements and bug fixes
* Rename plugin-animation artifact id: maps-camera-animation -> maps-animation. ([#524](https://github.com/mapbox/mapbox-maps-android/pull/524))
* Improve CameraAnimatorsFactory; add tests ([#520](https://github.com/mapbox/mapbox-maps-android/pull/520))
* Remove duplicated source extension functions. ([#526](https://github.com/mapbox/mapbox-maps-android/pull/526))
* Add Annotation plugin. ([#368](https://github.com/mapbox/mapbox-maps-android/pull/368))
* Move all generated files to its own folders ([#531](https://github.com/mapbox/mapbox-maps-android/pull/531))
* Add mutex for EGL context creation ([#537](https://github.com/mapbox/mapbox-maps-android/pull/537))
* Remove redundant MapTransformDelegate.cancelTranitions() call ([#538](https://github.com/mapbox/mapbox-maps-android/pull/538))
* Remove default model for ModelLocationRender ([#535](https://github.com/mapbox/mapbox-maps-android/pull/535))
* Set animation progress inside Camera Animations engine ([#546](https://github.com/mapbox/mapbox-maps-android/pull/546))
* Ignore animations turn on\turn off for IDE tests run ([#549](https://github.com/mapbox/mapbox-maps-android/pull/549))
* Add automatic casting for layer and source getters. ([#562](https://github.com/mapbox/mapbox-maps-android/pull/562))
* Rename function to be lowercase ([#571](https://github.com/mapbox/mapbox-maps-android/pull/571))
* Add helpers to run CameraAnimators ([#573](https://github.com/mapbox/mapbox-maps-android/pull/573))
* Add image plugin. ([#567](https://github.com/mapbox/mapbox-maps-android/pull/567))
* Add snapshotter attribution. ([#576](https://github.com/mapbox/mapbox-maps-android/pull/576))
* Package plugins directly in SDK ([#579](https://github.com/mapbox/mapbox-maps-android/pull/579))
### Examples
* Add circle clustering through dynamic styling ([#570](https://github.com/mapbox/mapbox-maps-android/pull/570))
* Fix up restrict bounds activity, avoid null pointer exception ([#530](https://github.com/mapbox/mapbox-maps-android/pull/530))
* Add vector tile source example ([#539](https://github.com/mapbox/mapbox-maps-android/pull/539))
* External WMS source example ([#545](https://github.com/mapbox/mapbox-maps-android/pull/545))
* Simplify simple mapview example ([#550](https://github.com/mapbox/mapbox-maps-android/pull/550))
* Add MovingIconWithTrailingLineActivity. ([#551](https://github.com/mapbox/mapbox-maps-android/pull/551))
* Add animated marker example ([#548](https://github.com/mapbox/mapbox-maps-android/pull/548))
* Add SnakingDirectionsRouteActivity. ([#559](https://github.com/mapbox/mapbox-maps-android/pull/559))
* Show / hide layers ([#569](https://github.com/mapbox/mapbox-maps-android/pull/569))
* Example - marker change icon size ([#575](https://github.com/mapbox/mapbox-maps-android/pull/575))
* Add symbols to map ([#582](https://github.com/mapbox/mapbox-maps-android/pull/582))
* Add ImageSource example ([#581](https://github.com/mapbox/mapbox-maps-android/pull/581))
* Re-enable test + add offline example ([#448](https://github.com/mapbox/mapbox-maps-android/pull/448))
* Add offline service demo ([#586](https://github.com/mapbox/mapbox-maps-android/pull/586))
### Dependencies
* Update gl-native-internal to v10.0.0-beta.4 ([#543](https://github.com/mapbox/mapbox-maps-android/pull/543))
* Update gradle to 6.5 ([#547](https://github.com/mapbox/mapbox-maps-android/pull/547))
* Update Dokka to 1.4 ([#553](https://github.com/mapbox/mapbox-maps-android/pull/553))


## 10.0.0-beta.1 - August 14, 2020

- Initial Carbon beta release.
