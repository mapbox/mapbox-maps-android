package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.extension.observable.eventdata.MapIdleEventData
import com.mapbox.maps.extension.observable.eventdata.SourceDataLoadedEventData
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.utils.toValue
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnMapIdleListener
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.examples.featurestate.FeatureStateActivity

/**
 * Example of passing large geojson to verify it does not block UI thread
 * for parsing before passed to core.
 */
class LargeGeojsonPerformanceActivity : AppCompatActivity() , OnMapClickListener, OnCameraChangeListener, OnMapIdleListener {

  private lateinit var routePoints: FeatureCollection
  private lateinit var alter1: FeatureCollection
  private lateinit var alter2: FeatureCollection
  private lateinit var alter1Str: String
  private lateinit var alter2Str: String
  private var featureIds0: List<QueriedFeature> = listOf<QueriedFeature>()
  private var featureIds1: List<QueriedFeature> = listOf<QueriedFeature>()
  private lateinit var mapboxMap: MapboxMap
  private var primaryIs1: Boolean = false
  private var featureQueried: Boolean = false
  private var canQuery: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    mapboxMap = mapView.getMapboxMap()
    setContentView(mapView)

    routePoints = FeatureCollection.fromFeature(
      Feature.fromGeometry(
        LineString.fromPolyline(
          DirectionsResponse.fromJson(
            AnnotationUtils.loadStringFromAssets(
              this@LargeGeojsonPerformanceActivity, LARGE_GEOJSON_ASSET_NAME
            )
          ).routes()[0].geometry()!!,
          Constants.PRECISION_6
        )
      )
    )


    alter1 = FeatureCollection.fromJson(AnnotationUtils.loadStringFromAssets(
      this@LargeGeojsonPerformanceActivity,
      "alter1.json"
    )!!)
    alter1Str = alter1.toJson()

    alter2 = FeatureCollection.fromJson(AnnotationUtils.loadStringFromAssets(
      this@LargeGeojsonPerformanceActivity,
      "alter2.json"
    )!!)

    alter2Str = alter2.toJson()

   mapboxMap
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(START_ZOOM)
            .build()
        )
        // start an animation that uses UI thread to update map camera
        flyTo(
          CameraOptions.Builder()
            .zoom(END_ZOOM)
            .build(),
          MapAnimationOptions.mapAnimationOptions {
            duration(ANIMATION_DURATION_MS)
          }
        )
        loadStyleJson(
          """
      {
        "version": 8,
        "sources": {},
        "layers": [
          {
            "id": "background",
            "type": "background",
            "paint": {
              "background-color": "white"
            }
          }
        ]
      }
      """.trimIndent()
        ) {
          val style = getStyle()
          if (style != null) {
            loadAdditionalGeoJsonAfter(style)
          }

        }
      }
    mapboxMap.addOnMapClickListener(this@LargeGeojsonPerformanceActivity)
    mapboxMap.addOnCameraChangeListener(this)
    mapboxMap.addOnMapIdleListener(this)
  }

  private fun loadAdditionalGeoJsonAfter(style: Style) {
    style.addSource(
      geoJsonSource("source_0") {
        featureCollection(alter1)
        generateId(true)
      }
    )
    style.addSource(
      geoJsonSource("source_1") {
        featureCollection(alter2)
        generateId(true)
      }
    )
    style.addLayer(
      lineLayer("layer_1", "source_1") {
        lineColor(
          switchCase {
            boolean {
              featureState {
                literal("primary")
              }
              literal(false)
            }
            literal("blue")
            literal("gray")
          }
        )
        lineWidth(5.0)
      }
    )
    style.addLayer(
      lineLayer("layer_0", "source_0") {
        lineColor(
          switchCase {
            boolean {
              featureState {
                literal("primary")
              }
              literal(false)
            }
            literal("blue")
            literal("gray")
          }
        )
        lineWidth(5.0)
      })


    Log.e("Testing", "Initial primary route is layer_0")


  }

  private fun setFeatureState(featureId: String, sourceId: String, primary: Boolean) {
    mapboxMap.setFeatureState(
      sourceId = sourceId,
      featureId = featureId,
      state = Value(
        hashMapOf(
          "primary" to Value(primary)
        )
      )
    )
  }

  override fun onMapClick(point: Point): Boolean {
    if (!featureQueried) {
      canQuery = true
      return true
    }
    if (!primaryIs1) {
      for (feature in featureIds0) {
        setFeatureState(feature.feature.id()!!, "source_0", false)
      }
      for (feature in featureIds1) {
        setFeatureState(feature.feature.id()!!, "source_1", true)
      }
      mapboxMap.getStyle()?.let {
        it.moveStyleLayer("layer_1", LayerPosition("layer_0", null,null))
      }
      Log.e("Testing", "primary route is layer_1")
    } else {
      for (feature in featureIds0) {
        setFeatureState(feature.feature.id()!!, "source_0", true)
      }
      for (feature in featureIds1) {
        setFeatureState(feature.feature.id()!!, "source_1", false)
      }
//      mapboxMap.getStyle()?.let {
//        it.moveStyleLayer("layer_0",  LayerPosition("layer_1", null,null))
//      }
      Log.e("Testing", "primary route is layer_0")
    }
    primaryIs1 = !primaryIs1

//    toggleRoute()
    return true
  }

  override fun onCameraChanged(eventData: CameraChangedEventData) {
    featureQueried = false
  }

  override fun onMapIdle(eventData: MapIdleEventData) {
    if (!featureQueried && canQuery) {
      mapboxMap.querySourceFeatures( "source_0", SourceQueryOptions(null, literal(true))
      ) { expected: Expected<String, List<QueriedFeature>> ->
        expected.value?.takeIf { it.isNotEmpty() }?.let {
          featureIds0 = it
        }
      }
      mapboxMap.querySourceFeatures( "source_1", SourceQueryOptions(null, literal(true) )
      ) { expected: Expected<String, List<QueriedFeature>> ->
        expected.value?.takeIf { it.isNotEmpty() }?.let {
          featureIds1 = it
        }
      }
      featureQueried = true
    }
  }

  private fun toggleRoute() {

    mapboxMap.getStyle()?.let{
      it.setStyleLayerProperty("layer_0", "line-color", literal("blue"))
    }
    if (!primaryIs1) {
      mapboxMap.getStyle()?.let{
        it.getSource("source_1")?.let{
          (it as GeoJsonSource).data(alter1Str)
//          (it as GeoJsonSource).featureCollection(alter1)
        }
      }
      mapboxMap.getStyle()?.let{
        it.getSource("source_0")?.let{
          (it as GeoJsonSource).data(alter2Str)
//          (it as GeoJsonSource).featureCollection(alter2)
        }
      }


    } else {
      mapboxMap.getStyle()?.let{
        it.getSource("source_1")?.let{
          (it as GeoJsonSource).data(alter2Str)
//          (it as GeoJsonSource).featureCollection(alter2)
        }
      }
      mapboxMap.getStyle()?.let{
        it.getSource("source_0")?.let{
          (it as GeoJsonSource).data(alter1Str)
//          (it as GeoJsonSource).featureCollection(alter1)
        }
      }

    }
    primaryIs1 = !primaryIs1
  }

  override fun onDestroy() {
    super.onDestroy()
    mapboxMap.removeOnMapClickListener(this)
  }
  companion object {
    private const val LARGE_GEOJSON_ASSET_NAME = "long_route.json"
    private const val ANIMATION_DURATION_MS = 10_000L
    private const val SOURCE = "source"
    private const val LAYER = "layer"
    private const val LARGE_SOURCE_COUNT = 5
    private const val LATITUDE = 51.1079
    private const val LONGITUDE = 17.0385
    private const val START_ZOOM = 6.0
    private const val END_ZOOM = 2.0
  }
}