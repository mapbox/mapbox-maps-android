package com.mapbox.maps.compose.testapp.examples.model

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import com.mapbox.bindgen.Value
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineCapValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineJoinValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ModelLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ModelTypeValue
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.ModelSourceModelsValue
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.sources.generated.rememberModelSourceState
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.sources.generated.modelSourceModel
import com.mapbox.turf.TurfMeasurement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Showcase animated 3D airplane model following a flight path.
 * Demonstrates continuous animation using feature state to control model parts like propellers,
 * landing gear, and lights.
 */
public class Animated3DModelActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        Animated3DModelScreen()
      }
    }
  }
}

@OptIn(MapboxDelicateApi::class)
@Composable
private fun Animated3DModelScreen() {
  var flightRoute by remember { mutableStateOf<FlightRoute?>(null) }
  var airplane by remember { mutableStateOf(Airplane()) }
  var animationPhase by remember { mutableStateOf(0f) }
  var isStyleLoaded by remember { mutableStateOf(false) }

  val mapState = rememberMapState()

  LaunchedEffect(Unit) {
    flightRoute = withContext(Dispatchers.IO) {
      loadFlightRoute(FLIGHT_PATH_JSON_URI)
    }
  }

  // Listen for style loaded event
  LaunchedEffect(Unit) {
    mapState.styleLoadedEvents.collect {
      isStyleLoaded = true
    }
  }

  val mapViewportState = rememberMapViewportState {
    setCameraOptions {
      center(INITIAL_CENTER)
      zoom(19.0)
      bearing(0.0)
      pitch(45.0)
    }
  }

  val flightPathSourceState = rememberGeoJsonSourceState(sourceId = FLIGHT_PATH_SOURCE_ID) {
    data = GeoJSONData(FLIGHT_PATH_JSON_URI)
  }
  val modelSourceState = rememberModelSourceState(sourceId = MODEL_SOURCE_ID)

  ExampleScaffold {
    MapboxMap(
      modifier = Modifier.fillMaxSize(),
      mapViewportState = mapViewportState,
      mapState = mapState,
      style = {
        MapboxStandardStyle(
          standardStyleState = rememberStandardStyleState {
            configurationsState.apply {
              lightPreset = LightPresetValue("dusk")
              showPointOfInterestLabels = BooleanValue(false)
              showRoadLabels = BooleanValue(false)
            }
          }
        )
      }
    ) {
      // Flight path line layer
      LineLayer(flightPathSourceState, FLIGHT_PATH_LAYER_ID) {
        lineColor = ColorValue(androidx.compose.ui.graphics.Color(0xFF007CBF))
        lineWidth = DoubleValue(8.0)
        lineEmissiveStrength = DoubleValue(1.0)
        lineCap = LineCapValue.ROUND
        lineJoin = LineJoinValue.ROUND
      }

      modelSourceState.models = ModelSourceModelsValue(
        hashMapOf(
          PLANE_MODEL_KEY to modelSourceModel(PLANE_MODEL_KEY) {
            uri(AIRPLANE_MODEL_URI)
            position(listOf(airplane.position[0], airplane.position[1]))
            orientation(listOf(airplane.roll, airplane.pitch, airplane.bearing + 90.0))
            materialOverrideNames(MATERIAL_OVERRIDE_NAMES)
            nodeOverrideNames(NODE_OVERRIDE_NAMES)
          }
        )
      )

      // Airplane model layer
      ModelLayer(modelSourceState, MODEL_LAYER_ID) {
        modelType = ModelTypeValue.LOCATION_INDICATOR

        // Altitude from feature state - build expression manually
        modelTranslation = DoubleListValue(
          Value.valueOf(
            listOf(
              Value.valueOf(0.0),
              Value.valueOf(0.0),
              Value.valueOf(
                listOf(
                  Value.valueOf("feature-state"),
                  Value.valueOf("z-elevation")
                )
              )
            )
          )
        )

        // Scale model based on zoom to maintain constant viewport size
        modelScale = DoubleListValue(
          Value.valueOf(
            listOf(
              Value.valueOf("interpolate"),
              Value.valueOf(listOf(Value.valueOf("exponential"), Value.valueOf(0.5))),
              Value.valueOf(listOf(Value.valueOf("zoom"))),
              Value.valueOf(2.0),
              Value.valueOf(listOf(Value.valueOf(40000.0), Value.valueOf(40000.0), Value.valueOf(40000.0))),
              Value.valueOf(16.0),
              Value.valueOf(listOf(Value.valueOf(1.0), Value.valueOf(1.0), Value.valueOf(1.0)))
            )
          )
        )

        // Model rotation for animated parts
        modelRotation = DoubleListValue(
          match {
            get { literal("part") }
            // Gears
            literal("front_gear")
            featureState { literal("front-gear-rotation") }
            literal("rear_gears")
            featureState { literal("rear-gear-rotation") }
            // Propellers
            literal("propeller_left_outer")
            featureState { literal("propeller-rotation") }
            literal("propeller_left_inner")
            featureState { literal("propeller-rotation") }
            literal("propeller_right_outer")
            featureState { literal("propeller-rotation") }
            literal("propeller_right_inner")
            featureState { literal("propeller-rotation") }
            // Blurred propellers
            literal("propeller_left_outer_blur")
            featureState { literal("propeller-rotation-blur") }
            literal("propeller_left_inner_blur")
            featureState { literal("propeller-rotation-blur") }
            literal("propeller_right_outer_blur")
            featureState { literal("propeller-rotation-blur") }
            literal("propeller_right_inner_blur")
            featureState { literal("propeller-rotation-blur") }
            literal(listOf(0.0, 0.0, 0.0))
          }
        )

        // Emissive strength for lights
        modelEmissiveStrength = DoubleValue(
          match {
            get { literal("part") }
            literal("lights_position_white")
            featureState { literal("light-emission-strobe") }
            literal("lights_position_white_volume")
            featureState { literal("light-emission-strobe") }
            literal("lights_anti_collision_red")
            featureState { literal("light-emission-strobe") }
            literal("lights_anti_collision_red_volume")
            featureState { literal("light-emission-strobe") }
            literal("lights_position_red")
            featureState { literal("light-emission") }
            literal("lights_position_red_volume")
            featureState { literal("light-emission") }
            literal("lights_position_green")
            featureState { literal("light-emission") }
            literal("lights_position_green_volume")
            featureState { literal("light-emission") }
            literal("lights_taxi_white")
            featureState { literal("light-emission-taxi") }
            literal("lights_taxi_white_volume")
            featureState { literal("light-emission-taxi") }
            literal(0.0)
          }
        )

        // Opacity for light volumes and propeller blur
        modelOpacity = DoubleValue(
          match {
            get { literal("part") }
            literal("lights_position_white_volume")
            product {
              featureState { literal("light-emission-strobe") }
              literal(0.25)
            }
            literal("lights_anti_collision_red_volume")
            product {
              featureState { literal("light-emission-strobe") }
              literal(0.45)
            }
            literal("lights_position_green_volume")
            product {
              featureState { literal("light-emission") }
              literal(0.25)
            }
            literal("lights_position_red_volume")
            product {
              featureState { literal("light-emission") }
              literal(0.25)
            }
            literal("lights_taxi_white")
            product {
              featureState { literal("light-emission-taxi") }
              literal(0.25)
            }
            literal("lights_taxi_white_volume")
            product {
              featureState { literal("light-emission-taxi") }
              literal(0.25)
            }
            literal("propeller_blur")
            literal(0.2)
            literal(1.0)
          }
        )
      }
      // Animation loop - waits for both route and style to be loaded
      LaunchedEffect(flightRoute, isStyleLoaded) {
        val route = flightRoute ?: return@LaunchedEffect
        if (!isStyleLoaded) return@LaunchedEffect

        var lastFrameTime = 0L
        var routeElevation = 0.0

        while (true) {
          withFrameMillis { frameTime ->
            if (lastFrameTime == 0L) lastFrameTime = frameTime

            val frameDeltaTime = frameTime - lastFrameTime
            val animFade = clamp(
              (routeElevation - FLIGHT_TRAVEL_ALTITUDE_MIN) /
                (FLIGHT_TRAVEL_ALTITUDE_MAX - FLIGHT_TRAVEL_ALTITUDE_MIN)
            )

            val timelapseFactor = mix(0.001, 10.0, animFade * animFade)
            animationPhase += ((frameDeltaTime * timelapseFactor) / ANIMATION_DURATION).toFloat()
            lastFrameTime = frameTime

            if (animationPhase > 1f) {
              animationPhase = 0f
              routeElevation = 0.0
            }

            route.sample(route.totalLength * animationPhase)?.let { target ->
              routeElevation = target.altitude
              airplane = airplane.update(target, frameDeltaTime.toDouble())
            }
          }
        }
      }

      // Update camera, model source and feature state
      MapEffect(airplane) { mapView ->
        val animFade = clamp(
          (airplane.altitude - FLIGHT_TRAVEL_ALTITUDE_MIN) /
            (FLIGHT_TRAVEL_ALTITUDE_MAX - FLIGHT_TRAVEL_ALTITUDE_MIN)
        )

        // Then update camera to follow airplane using free camera API
        val cameraOffsetLng = mix(-0.003, 0.0, airplane.altitude / 200.0)
        val cameraOffsetLat = mix(0.003, 0.0, airplane.altitude / 200.0)
        val cameraAltitude = airplane.altitude + 150.0 + mix(0.0, 10000000.0, animFade)

        val camera = mapView.mapboxMap.getFreeCameraOptions()

        // Set the position and altitude of the camera
        camera.setLocation(
          Point.fromLngLat(
            airplane.position[0] + cameraOffsetLng,
            airplane.position[1] + cameraOffsetLat
          ),
          cameraAltitude
        )

        // Tell the camera to look at the airplane position
        camera.lookAtPoint(
          Point.fromLngLat(airplane.position[0], airplane.position[1]),
          airplane.altitude
        )

        mapView.mapboxMap.setCamera(camera)

        // Update feature state
        val featureState = hashMapOf(
          "z-elevation" to Value.valueOf(airplane.altitude),
          "front-gear-rotation" to Value.valueOf(
            listOf(Value.valueOf(0.0), Value.valueOf(0.0), Value.valueOf(airplane.frontGearRotation))
          ),
          "rear-gear-rotation" to Value.valueOf(
            listOf(Value.valueOf(0.0), Value.valueOf(0.0), Value.valueOf(airplane.rearGearRotation))
          ),
          "propeller-rotation" to Value.valueOf(
            listOf(
              Value.valueOf(0.0),
              Value.valueOf(0.0),
              Value.valueOf(-(airplane.animTimeS % 0.5) * 2.0 * 360.0)
            )
          ),
          "propeller-rotation-blur" to Value.valueOf(
            listOf(
              Value.valueOf(0.0),
              Value.valueOf(0.0),
              Value.valueOf((airplane.animTimeS % 0.1) * 10.0 * 360.0)
            )
          ),
          "light-emission" to Value.valueOf(airplane.lightPhase),
          "light-emission-strobe" to Value.valueOf(airplane.lightPhaseStrobe),
          "light-emission-taxi" to Value.valueOf(airplane.lightTaxiPhase)
        )

        mapView.mapboxMap.setFeatureState(MODEL_SOURCE_ID, PLANE_MODEL_KEY, Value.valueOf(featureState)) { result ->
          result.error?.let { error ->
            android.util.Log.e("3DModel", "Failed to set feature state: $error")
          }
        }
      }
    }
  }
}

// Data class to hold sampled route point
private data class RoutePoint(
  val position: List<Double>,
  val altitude: Double,
  val bearing: Double,
  val pitch: Double
)

// FlightRoute class handles loading and sampling flight path data
private class FlightRoute(
  private val coordinates: List<Point>,
  private val elevationData: List<Double>,
  private val distances: List<Double>,
  val maxElevation: Double
) {
  val totalLength: Double
    get() = distances.lastOrNull() ?: 0.0

  fun sample(currentDistance: Double): RoutePoint? {
    if (distances.isEmpty()) return null

    var segmentIndex = distances.indexOfFirst { it >= currentDistance } - 1
    if (segmentIndex < 0) segmentIndex = 0
    if (segmentIndex >= coordinates.size - 1) segmentIndex = coordinates.size - 2

    val p1 = coordinates[segmentIndex]
    val p2 = coordinates[segmentIndex + 1]
    val segmentLength = distances[segmentIndex + 1] - distances[segmentIndex]
    val segmentRatio = (currentDistance - distances[segmentIndex]) / segmentLength

    val e1 = elevationData[segmentIndex]
    val e2 = elevationData[segmentIndex + 1]
    val bearing = TurfMeasurement.bearing(p1, p2)
    val altitude = e1 + (e2 - e1) * segmentRatio
    val pitch = rad2deg(atan2(e2 - e1, segmentLength))

    return RoutePoint(
      position = listOf(
        p1.longitude() + (p2.longitude() - p1.longitude()) * segmentRatio,
        p1.latitude() + (p2.latitude() - p1.latitude()) * segmentRatio
      ),
      altitude = altitude,
      bearing = bearing,
      pitch = pitch
    )
  }
}

// Airplane state
private data class Airplane(
  val position: List<Double> = listOf(INITIAL_CENTER.longitude(), INITIAL_CENTER.latitude()),
  val altitude: Double = 0.0,
  val bearing: Double = 0.0,
  val pitch: Double = 0.0,
  val roll: Double = 0.0,
  val rearGearRotation: Double = 0.0,
  val frontGearRotation: Double = 0.0,
  val lightPhase: Double = 0.0,
  val lightPhaseStrobe: Double = 0.0,
  val lightTaxiPhase: Double = 0.0,
  val animTimeS: Double = 0.0
) {
  fun update(target: RoutePoint, dtimeMs: Double): Airplane {
    val newAnimTimeS = animTimeS + dtimeMs / 1000.0
    return copy(
      position = listOf(
        mix(position[0], target.position[0], dtimeMs * 0.05),
        mix(position[1], target.position[1], dtimeMs * 0.05)
      ),
      altitude = mix(altitude, target.altitude, dtimeMs * 0.05),
      bearing = mix(bearing, target.bearing, dtimeMs * 0.01),
      pitch = mix(pitch, target.pitch, dtimeMs * 0.01),
      frontGearRotation = mix(0.0, 90.0, altitude / 50.0),
      rearGearRotation = mix(0.0, -90.0, altitude / 50.0),
      lightPhase = animSinPhaseFromTime(newAnimTimeS, 2.0) * 0.25 + 0.75,
      lightPhaseStrobe = animSinPhaseFromTime(newAnimTimeS, 1.0),
      lightTaxiPhase = mix(1.0, 0.0, altitude / 100.0),
      roll = rad2deg(mix(0.0, sin(newAnimTimeS * PI * 0.2) * 0.1, (altitude - 50.0) / 100.0)),
      animTimeS = newAnimTimeS
    )
  }
}

// Helper functions
private fun clamp(v: Double): Double = max(0.0, min(v, 1.0))

private fun mix(a: Double, b: Double, mixFactor: Double): Double {
  val f = clamp(mixFactor)
  return a * (1 - f) + b * f
}

private fun rad2deg(angRad: Double): Double = (angRad * 180.0) / PI

private fun animSinPhaseFromTime(animTimeS: Double, phaseLen: Double): Double {
  return sin(((animTimeS % phaseLen) / phaseLen) * PI * 2.0) * 0.5 + 0.5
}

private fun loadFlightRoute(url: String): FlightRoute {
  val json = URL(url).readText()
  val featureCollection = com.mapbox.geojson.FeatureCollection.fromJson(json)
  val targetRouteFeature = featureCollection.features()?.firstOrNull()
    ?: throw IllegalStateException("No features in flight path data")

  val geometry = targetRouteFeature.geometry() as? LineString
    ?: throw IllegalStateException("Expected LineString geometry")

  val coordinates = geometry.coordinates()
  val elevationData = targetRouteFeature.getProperty("elevation")
    .asJsonArray
    .map { it.asDouble }

  if (elevationData.size != coordinates.size) {
    throw IllegalStateException("Number of elevation samples does not match coordinate data length")
  }

  val distances = mutableListOf(0.0)
  var maxElevation = elevationData[0]

  for (i in 1 until coordinates.size) {
    val segmentDistance = TurfMeasurement.distance(
      coordinates[i - 1],
      coordinates[i],
      com.mapbox.turf.TurfConstants.UNIT_KILOMETERS
    ) * 1000.0
    distances.add(distances[i - 1] + segmentDistance)
    maxElevation = max(maxElevation, elevationData[i])
  }

  return FlightRoute(coordinates, elevationData, distances, maxElevation)
}

private const val FLIGHT_PATH_JSON_URI = "https://docs.mapbox.com/mapbox-gl-js/assets/flightpath.json"
private const val AIRPLANE_MODEL_URI = "https://docs.mapbox.com/mapbox-gl-js/assets/airplane.glb"
private const val FLIGHT_PATH_SOURCE_ID = "flightpath"
private const val MODEL_SOURCE_ID = "3d-model-source"
private const val PLANE_MODEL_KEY = "plane"
private const val FLIGHT_PATH_LAYER_ID = "flight-path-line"
private const val MODEL_LAYER_ID = "3d-model-layer"

private val INITIAL_CENTER = Point.fromLngLat(-122.37204647633236, 37.619836883832306)

private const val ANIMATION_DURATION = 50000f
private const val FLIGHT_TRAVEL_ALTITUDE_MIN = 200.0
private const val FLIGHT_TRAVEL_ALTITUDE_MAX = 3000.0

private val MATERIAL_OVERRIDE_NAMES = listOf(
  "propeller_blur",
  "lights_position_white",
  "lights_position_white_volume",
  "lights_position_red",
  "lights_position_red_volume",
  "lights_position_green",
  "lights_position_green_volume",
  "lights_anti_collision_red",
  "lights_anti_collision_red_volume",
  "lights_taxi_white",
  "lights_taxi_white_volume"
)

private val NODE_OVERRIDE_NAMES = listOf(
  "front_gear",
  "rear_gears",
  "propeller_left_inner",
  "propeller_left_outer",
  "propeller_right_inner",
  "propeller_right_outer",
  "propeller_left_inner_blur",
  "propeller_left_outer_blur",
  "propeller_right_inner_blur",
  "propeller_right_outer_blur"
)