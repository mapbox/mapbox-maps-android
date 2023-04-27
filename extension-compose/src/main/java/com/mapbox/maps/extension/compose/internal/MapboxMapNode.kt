package com.mapbox.maps.extension.compose.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.scalebar

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
private class MapboxMapNode(
  val controller: MapView,
  initialClickListener: OnMapClickListener,
  initialLongClickListener: OnMapLongClickListener,
  var onCameraStateChange: (CameraState) -> Unit
) : MapNode() {
  init {
    controller.getMapboxMap().apply {
      addOnCameraChangeListener {
        // Avoid invoke onCameraStateChange when the default instance is used.
        if (onCameraStateChange !== DefaultSettingsProvider.defaultOnCameraStateChange) {
          onCameraStateChange.invoke(cameraState)
        }
      }
    }
  }

  var clickListener: OnMapClickListener = initialClickListener
    set(value) {
      controller.gestures.apply {
        removeNonDefaultOnClickListener(field)
        addNonDefaultOnClickListener(value)
      }
      field = value
    }
  var longClickListener: OnMapLongClickListener = initialLongClickListener
    set(value) {
      controller.gestures.apply {
        removeNonDefaultOnLongClickListener(field)
        addNonDefaultOnLongClickListener(value)
      }
      field = value
    }

  override fun onAttached() {
    controller.gestures.apply {
      addNonDefaultOnClickListener(clickListener)
      addNonDefaultOnLongClickListener(longClickListener)
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
      removeNonDefaultOnClickListener(clickListener)
      removeNonDefaultOnLongClickListener(longClickListener)
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
  logoSettings: LogoSettings,
  scaleBarSettings: ScaleBarSettings,
  cameraOptions: CameraOptions,
  onCameraStateChange: (CameraState) -> Unit,
  onMapClickListener: OnMapClickListener,
  onMapLongClickListener: OnMapLongClickListener,
) {
  val mapApplier = currentComposer.applier as MapApplier
  ComposeNode<MapboxMapNode, MapApplier>(
    factory = {
      MapboxMapNode(
        mapApplier.mapView,
        onMapClickListener,
        onMapLongClickListener,
        onCameraStateChange
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
      set(logoSettings) {
        this.controller.logo.applySettings(it)
      }
      set(scaleBarSettings) {
        this.controller.scalebar.applySettings(it)
      }
      set(cameraOptions) {
        if (!it.isEmpty()) {
          this.controller.camera.easeTo(it, mapAnimationOptions { duration(0) })
        }
      }
      update(onCameraStateChange) {
        this.onCameraStateChange = it
      }
      update(onMapClickListener) { listener ->
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        this.longClickListener = listener
      }
    }
  )
}

private fun CameraOptions.isEmpty(): Boolean {
  return center == null && padding == null && anchor == null && zoom == null &&
    bearing == null && pitch == null
}

private fun GesturesPlugin.addNonDefaultOnClickListener(onMapClickListener: OnMapClickListener) {
  // Avoid addOnMapClickListener when the default instance is used.
  if (onMapClickListener !== DefaultSettingsProvider.defaultOnClickListener) {
    addOnMapClickListener(onMapClickListener)
  }
}

private fun GesturesPlugin.removeNonDefaultOnClickListener(onMapClickListener: OnMapClickListener) {
  // Avoid removeOnMapClickListener when the default instance is used.
  if (onMapClickListener !== DefaultSettingsProvider.defaultOnClickListener) {
    removeOnMapClickListener(onMapClickListener)
  }
}

private fun GesturesPlugin.addNonDefaultOnLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  // Avoid addOnMapLongClickListener when the default instance is used.
  if (onMapLongClickListener !== DefaultSettingsProvider.defaultOnLongClickListener) {
    addOnMapLongClickListener(onMapLongClickListener)
  }
}

private fun GesturesPlugin.removeNonDefaultOnLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  // Avoid removeOnMapLongClickListener when the default instance is used.
  if (onMapLongClickListener !== DefaultSettingsProvider.defaultOnLongClickListener) {
    removeOnMapLongClickListener(onMapLongClickListener)
  }
}