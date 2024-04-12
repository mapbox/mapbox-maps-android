# Mapbox Maps Compose Extension <!-- omit in toc -->

## Overview <!-- omit in toc -->

The Mapbox Maps Compose Extension is a public library to extend the Mapbox Map to work together with Jetpack Compose UI framework.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

Working examples of the Compose extension can be found in our [compose test application](https://github.com/mapbox/mapbox-maps-android/tree/master/compose-app/).

## Explore This Guide <!-- omit in toc -->

- [Getting Started With Compose Extension](#getting-started-with-compose-extension)
- [Tutorials](#tutorials)
  - [Place a Mapbox map to your app](#place-a-mapbox-map-to-your-app)
  - [Setup the map style and set the initial camera position](#setup-the-map-style-and-set-the-initial-camera-position)
  - [Use raw `MapboxMap` methods through `MapEffect` or `DisposableMapEffect`](#use-raw-mapboxmap-methods-through-mapeffect-or-disposablemapeffect)
  - [Use Camera Animation / Viewport APIs](#use-camera-animation--viewport-apis)
  - [Add Annotations to the map](#add-annotations-to-the-map)
    - [Add a single `CircleAnnotation` to the map](#add-a-single-circleannotation-to-the-map)
    - [Add multiple `CircleAnnotations` to the map in a group(more efficent for large number of annotations)](#add-multiple-circleannotations-to-the-map-in-a-groupmore-efficent-for-large-number-of-annotations)
    - [Add multiple `PointAnnotations` to the map as cluster(only supported for `PointAnnotation` and `CircleAnnotation`)](#add-multiple-pointannotations-to-the-map-as-clusteronly-supported-for-pointannotation-and-circleannotation)
    - [Add `ViewAnnotation` to the map](#add-viewannotation-to-the-map)
  - [Configure Map ornaments(Compass, ScaleBar, Attribution, Logo)](#configure-map-ornamentscompass-scalebar-attribution-logo)
  - [Gestures settings](#gestures-settings)
  - [Work with runtime styling (work in progress)](#work-with-runtime-styling-work-in-progress)
    - [Runtime styling with layers and sources](#runtime-styling-with-layers-and-sources)
- [Compatibility with Maps SDK v11](#compatibility-with-maps-sdk-v11)

## Getting Started With Compose Extension

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) or building an app that uses the Mapbox Maps Compose Extension. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Extension System. To add the compose extension to your project, you configure its dependency in your `build.gradle` files.

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

// In your build.gradle, add the compose extension with your other dependencies.
dependencies {
  implementation 'com.mapbox.extension:maps-compose:11.3.0'

  // Pick your versions of Android Mapbox Map SDK
  // Note that Compose extension is compatible with Maps SDK v11.0+.
  implementation 'com.mapbox.maps:android:11.3.0'
}
```

You should also become familiar with [Google's documentation for Jetpack Compose](https://developer.android.com/jetpack/compose/setup).

To start using Compose, you need to first add some build configurations to your project. Add the following definition to your app’s build.gradle file:
```groovy
android {
    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}
```

## Tutorials

### Place a Mapbox map to your app

Here's a simple example on using Compose extension to insert a Mapbox map to your app:

```kotlin
...
import com.mapbox.maps.extension.compose.MapboxMap

public class SimpleMapActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize())
    }
  }
  ...
}
```

### Setup the map style and set the initial camera position

You can set the initial map style with `MapStyle` composable function and the initial camera position by creating and remember `MapViewportState`.

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = remember {
          MapViewportState().apply {
            setCameraOptions {
              center(Point.fromLngLat(24.9384, 60.1699))
              zoom(9.0)
            }
          }
        },
        style = {
          MapStyle(style = Style.SATELLITE_STREETS)
        }
      )
    }
  }
```

### Use raw `MapboxMap` methods through `MapEffect` or `DisposableMapEffect`

Mapbox Compose Extension is built around the `MapView` in the base maps SDK. It's unlikely that we will be able to cover the full API surface in this wrapper, so we expose the reference to the raw `MapView` so that you can use all the API surface inside a `MapEffect`. 

Please note that using raw `MapView` APIs in `MapEffect` might introduce internal state changes that interferes with the Compose states, and might result in unexpected behaviours, please use it with caution.

The following example showcases how to turn on debug features using `MapEffect`:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize()) {
        // Get reference to the raw MapView using MapEffect
        MapEffect(Unit) { mapView ->
          // Use mapView to access all the Mapbox Maps APIs including plugins etc.
          // For example, to enable debug mode:
          mapView.mapboxMap.setDebug(
            listOf(
              MapDebugOptions.TILE_BORDERS,
              MapDebugOptions.PARSE_STATUS,
              MapDebugOptions.TIMESTAMPS,
              MapDebugOptions.COLLISION,
              MapDebugOptions.STENCIL_CLIP,
              MapDebugOptions.DEPTH_BUFFER,
              MapDebugOptions.MODEL_BOUNDS,
              MapDebugOptions.TERRAIN_WIREFRAME,
            ),
            true
          )
        }
      }
    }
  }
```

### Use Camera Animation / Viewport APIs

The camera/viewport animation of the map within Compose is exposed through `MapViewportState`, and internally it's implemented with the `ViewportPlugin` of the base maps SDK. Currently we expose high level camera animation APIs such as `setCamera`, `easeTo`, `flyTo` and the `FollowPuckViewportState` and `OverviewViewportState` with the `DefaultViewportTransition`.

The following example showcases adding a button to do a `flyTo` animation to the target camera position:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // Hold the hoisted MapViewPortState to manipulate the map camera.
      val mapViewportState = rememberMapViewportState {
        // Set the initial camera position
        setCameraOptions {
          center(Point.fromLngLat(0.0, 0.0))
          zoom(0.0)
          pitch(0.0)
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        // Add a MapboxMap with the mapViewportState
        MapboxMap(
          modifier = Modifier.fillMaxSize(),
          mapViewportState = mapViewportState
        )
        // Add a button on top of the map
        Button(
          onClick = {
            mapViewportState.flyTo(
              cameraOptions = cameraOptions {
                center(Point.fromLngLat(13.403, 52.562))
                zoom(14.0)
                pitch(45.0)
              },
              MapAnimationOptions.mapAnimationOptions { duration(5000) }
            )
          }
        ) {
          Text(text = "Animate camera with FlyTo")
        }
      }
    }
  }
```

### Add Annotations to the map

The full Annotation support is added with the initial compose extension release 0.1.0. `PointAnnotation`/`CircleAnnotation`/`PolygonAnnotation`/`PolylineAnnotation` can be added as composable functions within the content of the `MapboxMap` composable funciton.

We also exposed `AnnotationGroup` composable function to efficiently add a list of annotations to the map, and Point and Circle annotations within the same group can be configured to be clustered.

#### Add a single `CircleAnnotation` to the map

The following example showcases adding one circle annotation to the map:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize()) {
        // Add a single circle annotation at null island. 
        CircleAnnotation(
          point = Point.fromLngLat(0.0, 0.0),
          circleRadius = 20.0,
          circleColorInt = Color.BLUE,
          onClick = {
            Toast.makeText(
              this@CircleAnnotationActivity,
              "Clicked on Circle Annotation: $it",
              Toast.LENGTH_SHORT
            ).show()
            true
          }
        )
      }
    }
  }
```

#### Add multiple `CircleAnnotations` to the map in a group(more efficent for large number of annotations)

Adding multiple Annotations to the map using `AnnotationGroup` is more efficient, as they are backed by the same `AnnotationManager` and will be processed in batch and rendered in the same layer.

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize()) {
        // Add a number of circle annotations as CircleAnnotationGroup
        CircleAnnotationGroup(
          annotations = POINTS_TO_ADD.map {
            CircleAnnotationOptions()
              .withPoint(it)
              .withCircleRadius(10.0)
              .withCircleColor(Color.RED)
          },
          onClick = {
            Toast.makeText(
              this@CircleAnnotationActivity,
              "Clicked on Circle Annotation Cluster item: $it",
              Toast.LENGTH_SHORT
            ).show()
            true
          }
        )
      }
    }
  }
```

#### Add multiple `PointAnnotations` to the map as cluster(only supported for `PointAnnotation` and `CircleAnnotation`)

The following example showcases adding multiple `PointAnnotations` with clustering support:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize()) {
        PointAnnotationGroup(
          annotations = points.map {
            PointAnnotationOptions()
              .withPoint(it)
              .withIconImage(ICON_FIRE_STATION)
          },
          annotationConfig = AnnotationConfig(
            annotationSourceOptions = AnnotationSourceOptions(
              clusterOptions = ClusterOptions(
                textColorExpression = Expression.color(Color.YELLOW),
                textColor = Color.BLACK,
                textSize = 20.0,
                circleRadiusExpression = literal(25.0),
                colorLevels = listOf(
                  Pair(100, Color.RED),
                  Pair(50, Color.BLUE),
                  Pair(0, Color.GREEN)
                )
              )
            )
          ),
          onClick = {
            Toast.makeText(
              this@PointAnnotationClusterActivity,
              "Clicked on Point Annotation Cluster: $it",
              Toast.LENGTH_SHORT
            ).show()
            true
          }
        )
      }
    }
  }
```

#### Add `ViewAnnotation` to the map

With `ViewAnnotation` support for the compose extension, you are able to add a `ViewAnnotation` composable function to the content of `MapboxMap` and set its content using Android Composable functions.

The following example showcases adding `ViewAnnotation` that holds a `Button` to the map:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(modifier = Modifier.fillMaxSize()) {
        // Add a ViewAnnotation to the map
        ViewAnnotation(
          options = viewAnnotationOptions {
            // set the view annotation associated geometry
            geometry(HELSINKI)
            anchor(ViewAnnotationAnchor.BOTTOM)
            allowOverlap(false)
          }
        ) {
          // You can add the content to be drawn in the ViewAnnotation using Composable functions, e.g. to insert a button:
          Button(
            onClick = {
              Toast.makeText(applicationContext, "Click", LENGTH_SHORT).show()
            }
          ) {
            Text("Click me")
          }
        }
      }
    }
  }
```

### Configure Map ornaments(Compass, ScaleBar, Attribution, Logo) 

The Map ornaments are introduced as composable functions within dedicated scope and can be set to the MapboxMap composable function.

The following example showcases customising map ornaments:

```kotlin
MapboxMap(
  modifier = Modifier.fillMaxSize(),
  compass = {
    // Original compass
    Compass()
    // Add another compass with customised position and image
    Compass(
      modifier = Modifier.padding(scaffoldPadding),
      contentPadding = PaddingValues(0.dp),
      alignment = Alignment.TopEnd,
    ) {
      Image(
        painter = painterResource(id = R.drawable.my_compass),
        alpha = 0.9f,
        modifier = Modifier
          .height(55.dp)
          .width(55.dp),
        contentDescription = "My customised compass"
      )
    }
  },
  scaleBar = {
    // Change the ScaleBar primary color
    ScaleBar(
      modifier = Modifier.padding(scaffoldPadding),
      primaryColor = color
    )
  },
  attribution = {
    // Change the Attribution color
    Attribution(
      modifier = Modifier.padding(scaffoldPadding),
      iconColor = color
    )
  },
  logo = {
    // Change the Mapbox logo position
    Logo(modifier = Modifier.padding(scaffoldPadding), alignment = Alignment.BottomEnd)
  }
)
```

### Gestures settings

The following example showcases how to remember a `MutableState` of the `GesturesSettings` and update the settings through user interaction:

```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // Hold the hoisted gesturesSettings as mutable state to manipulate the gestures settings.
      var gesturesSettings by remember {
        mutableStateOf(DefaultSettingsProvider.defaultGesturesSettings)
      }
      Box(modifier = Modifier.fillMaxSize()) {
        // Add a MapboxMap with the gesturesSettings
        MapboxMap(
          modifier = Modifier.fillMaxSize(),
          gesturesSettings = gesturesSettings
        )
        // Add a button on top of the map
        Button(
          onClick = {
            gesturesSettings = gesturesSettings.toBuilder().setScrollEnabled(false).build()
          }
        ) {
          Text(text = "Disable scroll gesture")
        }
      }
  }
```

### Work with runtime styling (work in progress)

The map style can be set through `MapboxStyleComposable`, currently we expose following Style composable functions:

* `GenericStyle` for all the available style features as weakly typed APIs, it has full flexibility to control any style/slot/positioned layers and style import configs, but alternative strongly typed APIs should be preferred for safety and convenience to use. 
* `MapStyle` for simple loading style use cases if you don't need slots position your layer according to the layerIds defined in the style json.
* `MapboxStandardStyle` for the default Mapbox Standard Style, it exposes available slots to position runtime-added layers and style import configs as strongly typed API. 

#### Runtime styling with layers and sources

The layers can be added to the style/map as `MapboxMapComposable` functions.

* When added to the `slots` or `layerPositions` within the `MapboxStyleComposable`, the layer will be added to the style after the style is loaded.
* When added to the content of `MapboxMap` composable function, the layer will be added to the map immediately as persistent layer.

The sources are exposed as source state, which can be hoisted outside of the map and be shared with multiple layers. Please note the source state can not be shared across multiple map instances.

Please also note that the layer id and source id are automatically generated and remembered by default, so that you can reuse the layers in different places without `layerId already exist` error.
In case if you need the layer/source id later for other purpose, e.g. query rendered features, you can provide your own id and reuse later.  

The following example showcases how to work with runtime styling with composable functions:

```kotlin
// Create a geoJsonSourceState to be used later with layers.
val geoJsonSource: GeoJsonSourceState = rememberGeoJsonSourceState {
  // Set the initial geoJsonData as a Point
  data = GeoJSONData(Point.fromLngLat(0.0, 0.0))
}
MapboxMap(
  style = {
    // Load mapbox standard style
    MapboxStandardStyle(
      // Add a background layer to the 'top' slot of the standard style
      topSlot = {
        BackgroundLayer(
          backgroundColor = BackgroundColor(Color.Yellow),
          backgroundOpacity = BackgroundOpacity(0.3)
        )
      }
    )
  }
) {
  // Insert a circle layer with the given geoJsonSource, to display a background circle.
  CircleLayer(
    sourceState = geoJsonSource,
    circleColor = CircleColor(Color.Cyan),
    circleRadius = CircleRadius(50.0),
    circleRadiusTransition = Transition(duration = 1000L)
  )
  // Insert a symbol layer with the same geoJsonSource, to display a text above the circle.
  SymbolLayer(
    sourceState = geoJsonSource,
    textField = TextField("Hello"),
    textColor = TextColor(Color.Black),
    // Use expression to set the text size as data driven property
    textSize = TextSize(
      Expression.interpolate {
        linear()
        zoom()
        stop {
          literal(0.0)
          literal(10.0)
        }
        stop {
          literal(10.0)
          literal(20.0)
        }
      }
    )
  )
}
```

## Compatibility with Maps SDK v11

The Compose extension is released separately from the Android Maps SDK v11 and has a compileOnly dependency. When using the Compose extension you need to include a compatible Maps SDK. The feature compatibility checklist can be found below.

Below is the full feature compatibility table:

Features  | Supported? | Compatible Maps SDK version
------------- | ------------- | -------------
Basic Map rendering | ✅ | v11.0.0+
Annotations support | ✅ | v11.0.0+
ViewAnnotation support | ✅ | v11.0.0+
MapViewportState support | ✅ | v11.0.0+
Gestures settings support | ✅ | v11.0.0+
Access to raw MapView using MapEffect | ✅ | v11.0.0+
Map ornament(Compass, ScaleBar, Attribution, Logo) support | ✅ | v11.3.0+
Style composable function support | ✅ | v11.3.0+
Style layer/source support | ✅ | v11.3.0+
Style projection support | ✅ | v11.3.0+

View [LICENSE.md](LICENSE.md) for all dependencies used by this extension.