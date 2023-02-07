package com.mapbox.maps.extension.compose.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.location2
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.scalebar

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
private class MapboxMapNode(
  val controller: MapView,
  var clickListener: OnMapClickListener?,
  var longClickListener: OnMapLongClickListener?
) : MapNode {
  override fun onAttached() {
    controller.gestures.apply {
      clickListener?.let {
        addOnMapClickListener(it)
      }
      longClickListener?.let {
        addOnMapLongClickListener(it)
      }
    }
  }

  override fun onRemoved() {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    controller.gestures.apply {
      clickListener?.let {
        removeOnMapClickListener(it)
      }
      longClickListener?.let {
        removeOnMapLongClickListener(it)
      }
    }
  }
}

@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
  mapInitOptionsFactory: (Context) -> MapInitOptions,
  attributionSettings: AttributionSettings,
  compassSettings: CompassSettings,
  gesturesSettings: GesturesSettings,
  locationComponentSettings: LocationComponentSettings,
  locationComponentSettings2: LocationComponentSettings2,
  logoSettings: LogoSettings,
  scaleBarSettings: ScaleBarSettings,
  onMapClickListener: OnMapClickListener?,
  onMapLongClickListener: OnMapLongClickListener?,
) {
  val mapApplier = currentComposer.applier as MapApplier
  ComposeNode<MapboxMapNode, MapApplier>(
    factory = {
      MapboxMapNode(
        mapApplier.mapView,
        onMapClickListener,
        onMapLongClickListener
      )
    },
    update = {
      // input arguments updater
      update(mapInitOptionsFactory) {
        throw IllegalStateException(
          """
          Mutating MapInitOptions during composition is not allowed.
          """.trimIndent()
        )
      }
      set(attributionSettings) {
        this.controller.attribution.applySettings(it)
      }
      set(compassSettings) {
        this.controller.compass.applySettings(it)
      }
      set(gesturesSettings) {
        this.controller.gestures.applySettings(it)
      }
      set(locationComponentSettings) {
        this.controller.location.applySettings(it)
      }
      set(locationComponentSettings2) {
        this.controller.location2.applySettings(it)
      }
      set(logoSettings) {
        this.controller.logo.applySettings(it)
      }
      set(scaleBarSettings) {
        this.controller.scalebar.applySettings(it)
      }
      update(onMapClickListener) { listener ->
        val previousListener = this.clickListener
        controller.gestures.apply {
          previousListener?.let {
            removeOnMapClickListener(it)
          }
          listener?.let {
            addOnMapClickListener(it)
          }
        }
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        val previousListener = this.longClickListener
        controller.gestures.apply {
          previousListener?.let {
            removeOnMapLongClickListener(it)
          }
          listener?.let {
            addOnMapLongClickListener(it)
          }
        }
        this.longClickListener = listener
      }
    }
  )
}