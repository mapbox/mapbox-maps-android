package com.mapbox.maps.testapp.examples.terrain3D

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.modelLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ModelType
import com.mapbox.maps.extension.style.model.addModel
import com.mapbox.maps.extension.style.model.model
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logE
import com.mapbox.turf.TurfMeasurement

/**
 * Showcase adding 3D models using model layer.
 */
@MapboxExperimental
class ModelLayerActivity : AppCompatActivity() {
  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)

    mapView.getMapboxMap().apply {
      setCamera(
        cameraOptions {
          center(TurfMeasurement.midpoint(HELSINKI, MAPBOX_HELSINKI))
          zoom(CAMERA_ZOOM)
          pitch(CAMERA_PITCH)
        }
      )
      loadStyle(
        style(Style.LIGHT) {
          +model(MODEL_ID_1) {
            uri(SAMPLE_MODEL_URI_1)
          }
          +model(MODEL_ID_2) {
            uri(SAMPLE_MODEL_URI_2)
          }
          +geoJsonSource(SOURCE_ID) {
            featureCollection(
              FeatureCollection.fromFeatures(
                listOf(
                  Feature.fromGeometry(HELSINKI).also {
                    it.addStringProperty(MODEL_ID_KEY, MODEL_ID_1)
                    it.addNumberProperty("height", 100.0)
                  },
                  Feature.fromGeometry(MAPBOX_HELSINKI).also {
                    it.addStringProperty(MODEL_ID_KEY, MODEL_ID_2)
                    it.addNumberProperty("height", 3.0)
                  }
                )
              )
            )
          }
          +modelLayer(MODEL_LAYER_ID, SOURCE_ID) {
            modelId(get(MODEL_ID_KEY))
            modelType(ModelType.COMMON_3D)
            modelTranslation(listOf(0.0, 0.0, 0.0))
            modelRotation(listOf(0.0, 0.0, 90.0))
            modelOpacity(0.7)
          }
        }
      ) { style ->
        style.setStyleLayerProperty(
          MODEL_LAYER_ID,
          "model-scale",
          Value(
            listOf(
              Value(100.0),
              Value(100.0),
              Value(listOf(
                Value("get"),
                Value("height")
              ))
            )
          )
        ).also {
          logE("testtest", it.error!!)
        }
//        style.addLayer(
//          modelLayer(MODEL_LAYER_ID, "vegetation") {
//            sourceLayer("trees")
//            modelId(
//              switchCase {
//                eq {
//                  get("type")
//                  literal("pine")
//                }
//                literal("pine-tree-model")
//                eq {
//                  get("type")
//                  literal("palm")
//                }
//                literal("palm-tree-model")
//                literal("default-tree-model")
//              }
//            )
//            modelScale(
//              array {
//                literal("number")
//                literal(3)
//                literal(1.0)
//                literal(1.0)
//                get("height")
//              }
//            )
//            modelTranslation(
//              array {
//                literal("number")
//                literal(3)
//                literal(0.0)
//                literal(0.0)
//                get("z-offset")
//              }
//            )
//            modelRotation(
//              array {
//                literal("number")
//                literal(3)
//                literal(1.0)
//                literal(1.0)
//                get("z-rotation")
//              }
//            )
//          }
//        )
      }
    }
  }

  private companion object {
    const val CAMERA_ZOOM = 14.0
    const val CAMERA_PITCH = 45.0
    const val SOURCE_ID = "source-id"
    const val MODEL_LAYER_ID = "model-layer-id"
    const val MODEL_ID_KEY = "model-id-key"
    const val MODEL_ID_1 = "model-id-1"
    const val MODEL_ID_2 = "model-id-2"
    const val SAMPLE_MODEL_URI_1 =
      "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf"
    const val SAMPLE_MODEL_URI_2 = "asset://sportcar.glb"
    val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
    val MAPBOX_HELSINKI = Point.fromLngLat(24.945389069265598, 60.17195694011002)
  }
}