package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.SymbolLayerDsl
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityGlitchIssueBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class SimpleMapActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGlitchIssueBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGlitchIssueBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    putFragment()
  }


  private fun putFragment() {
    supportFragmentManager.commitNow {
      val glitchDemoMapFragment = GlitchDemoMapFragment()
      this.add(binding.mapContainer.id, glitchDemoMapFragment, "map")
    }
  }
}

class GlitchDemoMapFragment : Fragment() {

  private val mapView
    get() = requireView() as MapView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = MapView(
    requireContext(),
    MapInitOptions(
      requireContext(),
      textureView = true,
      plugins = listOf(
        Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_GESTURES_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_COMPASS_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_LOGO_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID)
      ),
      resourceOptions = MapInitOptions.getDefaultResourceOptions(requireContext())
        .toBuilder()
        .accessToken(resources.getString(R.string.mapbox_access_token))
        .build(),
      mapOptions = MapInitOptions.getDefaultMapOptions(requireContext()).toBuilder().apply {
        this.optimizeForTerrain(false)
      }.build()
    )
  ).apply {
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT
    )
  }

  private val lazyFeatures by lazy {
    FeatureCollection.fromFeatures((0 until 50).map {
      val nextLat = Random.nextDouble(-90.0, 90.0)
      val nextLng = Random.nextDouble(-180.0, 180.0)
      Feature.fromGeometry(Point.fromLngLat(nextLng, nextLat), JsonObject().apply {
        this.addProperty("image", "some_image_$it")
        this.addProperty("selected", false)
        this.addProperty("order", Random.nextInt())
      }, "id_$it")
    })
  }

  private val placeholderImage by lazy {
    foreground()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    //TODO: the issue only appears, if a style with vector backgrounds is loaded.
    // It will not glitch when using Style.SATELLITE
    mapView.getMapboxMap().loadStyle(style(
      Style.MAPBOX_STREETS
    ) {
      // we add a layer for all symbols that do not have the state "selected=true"
      +symbolLayer("symbol-layer", "symbol-source") {
        filter(Expression.eq(Expression.get("selected"), Expression.literal(false)))
        image()
      }

      // we add a layer for a red background for all symbols that have the state "selected=true"
      +symbolLayer("symbol-layer-selected-background", "symbol-source") {
        filter(Expression.eq(Expression.get("selected"), Expression.literal(true)))
        selection()
      }

      // we add a layer for the selected symbol foreground
      +symbolLayer("symbol-layer-selected-foreground", "symbol-source") {
        filter(Expression.eq(Expression.get("selected"), Expression.literal(true)))
        image()
      }
      // we statically provide the background symbol
      +image("background") {
        this.bitmap(background())
      }

      +geoJsonSource("symbol-source")
    })

    mapView.getMapboxMap().addOnStyleImageMissingListener { eventData ->
      // we first add a placeholder image (required or else later the map doesn't invalidate)
      mapView.getMapboxMap().getStyle()?.addImage(eventData.id, bitmap = placeholderImage)
      lifecycleScope.launch {
        // we simulate a remotely loaded image
        delay(Random.nextLong(100, 500))
        // we replace the placeholder with the actual image
        mapView.getMapboxMap().getStyle()?.addImage(eventData.id, bitmap = foreground())
      }
    }


    lifecycleScope.launch(Dispatchers.IO) {
      while (isActive) {
        delay(2000L)
        withContext(Dispatchers.Main) {
          mapView.getMapboxMap().getStyle()?.let {
            var pointToFocus: Point? = null

            // we update the source every 2 seconds and select + frame a different symbol
            it.getSourceAs<GeoJsonSource>("symbol-source")?.let { source ->
              source.featureCollection(lazyFeatures.apply {
                val randomFeature = Random.nextInt(0, this.features()!!.size - 1)
                this.features()!!.forEachIndexed { index, feature ->
                  val isSelected = index == randomFeature
                  feature.addBooleanProperty("selected", isSelected)
                  if (isSelected) {
                    pointToFocus = feature.geometry() as Point
                  }
                }
              })
            }

            // we animate to the newly selected symbol
            pointToFocus?.let { point ->
              val nextZoom = Random.nextDouble(0.0, 4.0)
              mapView.getMapboxMap().flyTo(
                CameraOptions.Builder().center(point).zoom(nextZoom).build(),
                animationOptions = MapAnimationOptions.mapAnimationOptions {
                  duration(2000L)
                })
            }
          }
        }
      }
    }
  }

  private fun foreground() =
    ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)?.apply {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }?.toBitmap()!!

  private fun background() =
    ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_foreground_other_color)
      ?.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
      }?.toBitmap()!!

  private fun SymbolLayerDsl.image() {
    this.iconImage(
      Expression.get("image")
    )
    this.iconOpacityTransition {
      duration(0)
      delay(0)
    }
    this.iconAllowOverlap(true)
  }

  private fun SymbolLayerDsl.selection() {
    this.iconImage(
      "background"
    )
    this.iconOpacityTransition {
      duration(0)
      delay(0)
    }
    this.iconAllowOverlap(true)
  }


}