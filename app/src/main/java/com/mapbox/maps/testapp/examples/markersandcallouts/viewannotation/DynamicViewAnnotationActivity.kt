package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationAnchorConfig
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityDynamicViewAnnotationsBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.BitmapUtils
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.annotationAnchors
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example how to use dynamic view annotations on line layers and fixed positions.
 */
class DynamicViewAnnotationActivity : AppCompatActivity() {

  private lateinit var viewAnnotationManager: ViewAnnotationManager

  private val featureRouteMain: Feature by lazy { getFeatureFromAsset(ROUTE_MAIN_GEOJSON) }
  private val featureRouteAlt: Feature by lazy { getFeatureFromAsset(ROUTE_ALT_GEOJSON) }
  private val featureCollectionParkings: FeatureCollection by lazy {
    getFeatureCollectionFromAsset(
      PARKINGS_GEOJSON
    )
  }

  // dynamic view annotations
  private lateinit var alternativeEtaView: View
  private lateinit var etaView: View
  private lateinit var parkingView: View

  private val routeSourceMain: GeoJsonSource = geoJsonSource(SOURCE_MAIN_ID)
  private val routeSourceAlt: GeoJsonSource = geoJsonSource(SOURCE_ALT_ID)
  private val parkingSource: GeoJsonSource = geoJsonSource(SOURCE_PARKING)
  private val routeLayerMain: LineLayer = LineLayer(
    layerId = LAYER_MAIN_ID,
    sourceId = SOURCE_MAIN_ID
  )
  private val routeLayerAlt: LineLayer = LineLayer(
    layerId = LAYER_ALT_ID,
    sourceId = SOURCE_ALT_ID,
  )
  private val parkingLayer = FillLayer(
    layerId = LAYER_PARKING,
    sourceId = SOURCE_PARKING
  )

  private var isMainActive = true
  private var isOverview = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityDynamicViewAnnotationsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val mapView = binding.mapView
    viewAnnotationManager = mapView.viewAnnotationManager

    binding.btnMode.setOnClickListener {
      isOverview = !isOverview
      if (isOverview) {
        overviewRoute(binding.mapView)
        refreshButton(binding.btnMode)
      } else {
        followLocationIndicator(binding.mapView)
        refreshButton(binding.btnMode)
      }
    }

    refreshButton(binding.btnMode)

    mapView.mapboxMap.apply {
      initStyleWithLayers {
        setupLocation(mapView)
        overviewRoute(mapView)

        addViewAnnotations()
        observeViewAnnotationUpdate()
      }
    }
  }

  private fun MapboxMap.initStyleWithLayers(onLoaded: (Style) -> Unit) {
    refreshRoutes()

    loadStyle(
      style(Style.STANDARD) {
        // source for displaying main route
        +routeSourceMain
        // source for displaying alt route
        +routeSourceAlt
        // layer for alternative route
        +routeLayerAlt.apply {
          lineColor(Color.parseColor("#FF999999"))
          lineWidth(12.0)
          lineBorderWidth(2.0)
          lineBorderColor(Color.parseColor("#FF333333"))
        }
        // layer for main route
        +routeLayerMain.apply {
          lineColor(Color.parseColor("#FF57A9FB"))
          lineWidth(12.0)
          lineCap(LineCap.ROUND)
          lineBorderWidth(2.0)
          lineBorderColor(Color.parseColor("#FF327AC2"))
        }
        +parkingSource.apply {
          featureCollection(featureCollectionParkings)
        }
        // layer for parkings
        +parkingLayer.apply {
          fillColor(Color.parseColor("#0080ff")).fillOpacity(0.5)
        }
      },
      onLoaded
    )
  }

  private fun setupLocation(mapView: MapView) {
    // simulates movement along the route
    val simulatedLocationProvider = SimulateRouteLocationProvider(
      featureRouteMain.geometry() as LineString
    )
    mapView.apply {
      location.setLocationProvider(simulatedLocationProvider)
      location.locationPuck = LocationPuck2D(
        bearingImage = ImageHolder.from(R.drawable.mapbox_user_puck_icon),
      )
      location.enabled = true
      location.puckBearingEnabled = true
      location.puckBearing = PuckBearing.COURSE
    }
  }

  private fun followLocationIndicator(mapView: MapView) {
    mapView.viewport.apply {
      transitionTo(
        makeFollowPuckViewportState(
          FollowPuckViewportStateOptions.Builder()
            .pitch(70.0)
            .zoom(18.0)
            .padding(EdgeInsets(500.0, 100.0, 100.0, 100.0))
            .build()
        )
      )
    }
  }

  private fun overviewRoute(mapView: MapView) {
    mapView.viewport.transitionTo(
      mapView.viewport.makeOverviewViewportState(
        OverviewViewportStateOptions.Builder()
          .geometry(featureRouteMain.geometry()!!)
          .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
          .build()
      )
    )
  }

  private fun refreshButton(btnMode: Button) {
    btnMode.text = getString(R.string.dynamic_mode, if (isOverview) "follow" else "overview")
  }

  private fun addViewAnnotations() {
    etaView = viewAnnotationManager.addViewAnnotation(
      R.layout.item_dva_eta,
      viewAnnotationOptions {
        annotatedLayerFeature(LAYER_MAIN_ID)
        annotationAnchors(
          {
            anchor(ViewAnnotationAnchor.TOP_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_LEFT)
          },
        )
      }
    )
    alternativeEtaView = viewAnnotationManager.addViewAnnotation(
      R.layout.item_dva_alt_eta,
      viewAnnotationOptions {
        annotatedLayerFeature(LAYER_ALT_ID)
        annotationAnchors(
          {
            anchor(ViewAnnotationAnchor.TOP_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_LEFT)
          },
        )
      }
    )
    alternativeEtaView.setOnClickListener {
      toggleActiveRoute()
    }

    parkingView = viewAnnotationManager.addViewAnnotation(
      R.layout.item_dva_parking,
      viewAnnotationOptions {
        annotatedLayerFeature(LAYER_PARKING) {
          featureId(PARKING_FEATURE_ID_1)
        }
      }
    )

    parkingView = viewAnnotationManager.addViewAnnotation(
      R.layout.item_dva_parking,
      viewAnnotationOptions {
        annotatedLayerFeature(LAYER_PARKING) {
          featureId(PARKING_FEATURE_ID_2)
        }
      }
    )
  }

  private fun toggleActiveRoute() {
    isMainActive = !isMainActive
    refreshRoutes()
  }

  private fun refreshRoutes() {
    if (isMainActive) {
      routeSourceMain.feature(featureRouteMain)
      routeSourceAlt.feature(featureRouteAlt)
    } else {
      routeSourceMain.feature(featureRouteAlt)
      routeSourceAlt.feature(featureRouteMain)
    }
  }

  private fun observeViewAnnotationUpdate() {
    viewAnnotationManager.addOnViewAnnotationUpdatedListener(
      object : OnViewAnnotationUpdatedListener {
        override fun onViewAnnotationAnchorUpdated(
          view: View,
          anchor: ViewAnnotationAnchorConfig
        ) {
          // set different background according to the anchor
          when (view) {
            etaView -> {
              view.background = getBackground(anchor, getColor(R.color.primary))
            }

            alternativeEtaView -> {
              view.background = getBackground(anchor, getColor(R.color.white))
            }

            else -> {
              // no-op
            }
          }
        }
      }
    )
  }

  private fun getFeatureFromAsset(featureGeojson: String) =
    Feature.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this, featureGeojson
      )!!
    )

  private fun getFeatureCollectionFromAsset(featureGeojson: String) =
    FeatureCollection.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this, featureGeojson
      )!!
    )

  private fun getBackground(
    anchorConfig: ViewAnnotationAnchorConfig,
    @ColorInt tint: Int,
  ): Drawable {
    var flipX = false
    var flipY = false

    when (anchorConfig.anchor) {
      ViewAnnotationAnchor.BOTTOM_RIGHT -> {
        flipX = true
        flipY = true
      }

      ViewAnnotationAnchor.TOP_RIGHT -> {
        flipX = true
      }

      ViewAnnotationAnchor.BOTTOM_LEFT -> {
        flipY = true
      }

      else -> {
        // no-op
      }
    }

    return BitmapDrawable(
      resources,
      BitmapUtils.drawableToBitmap(
        getDrawable(this, R.drawable.bg_dva_eta),
        flipX = flipX,
        flipY = flipY,
        tint = tint,
      )!!
    )
  }

  private companion object {
    const val LAYER_MAIN_ID = "layer-main"
    const val LAYER_ALT_ID = "layer-alt"
    const val LAYER_PARKING = "layer-parking"
    const val SOURCE_MAIN_ID = "source-main"
    const val SOURCE_ALT_ID = "source-alt"
    const val SOURCE_PARKING = "source-parking"
    const val PARKING_FEATURE_ID_1 = "parking-1"
    const val PARKING_FEATURE_ID_2 = "parking-2"

    const val ROUTE_MAIN_GEOJSON = "dva-sf-route-main.geojson"
    const val ROUTE_ALT_GEOJSON = "dva-sf-route-alternative.geojson"
    const val PARKINGS_GEOJSON = "dva-sf-parkings.geojson"
  }
}