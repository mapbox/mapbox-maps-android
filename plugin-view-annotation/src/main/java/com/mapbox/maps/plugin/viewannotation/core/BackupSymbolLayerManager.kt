package com.mapbox.maps.plugin.viewannotation.core

import android.os.Handler
import android.os.Looper
import android.view.View
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions

internal class BackupSymbolLayerManager(
  private val coreAnnotations: LinkedHashMap<String, EnhancedViewAnnotationOptions>,
  mapDelegateProvider: MapDelegateProvider
) {
  private val backupAnnotations = hashMapOf<String, ViewAnnotationOptions>()
  private val workHandler = Handler(Looper.getMainLooper())
  private var detectorRunning = false

  private val mapFeatureQueryDelegate = mapDelegateProvider.mapFeatureQueryDelegate
  private val mapTransformDelegate = mapDelegateProvider.mapTransformDelegate
  private val annotationPlugin: AnnotationPlugin

  init {
    @Suppress("UNCHECKED_CAST")
    this.annotationPlugin = mapDelegateProvider.mapPluginProviderDelegate.getPlugin(
      Plugin.MAPBOX_ANNOTATION_PLUGIN_ID
    ) ?: throw InvalidPluginConfigurationException(
      "Can't look up an instance of plugin, " +
        "is it available on the clazz path and loaded through the map?"
    )
  }

  fun createAnnotationManager(view: View) {

  }

  fun updateBackupSymbol(id: String, options: ViewAnnotationOptions) {
    backupAnnotations[id] = options
    if (detectorRunning) {
      return
    }
    detectorRunning = true
    runCollisionDetectorLoop()
  }

  fun removeBackupSymbol(id: String) {
    backupAnnotations.remove(id)
    if (backupAnnotations.isEmpty()) {
      stopCollisionDetectorLoop()
    }
  }

  private fun stopCollisionDetectorLoop() {
    detectorRunning = false
    workHandler.removeCallbacksAndMessages(null)
  }

  private fun runCollisionDetectorLoop() {
    workHandler.postDelayed(
      {
        updateVisibilityBasedOnCollisions()
        runCollisionDetectorLoop()
      },
      DETECTOR_FREQUENCY_MS
    )
  }

  private fun updateVisibilityBasedOnCollisions() {
    val viewportSize = mapTransformDelegate.getSize()
    mapFeatureQueryDelegate.queryRenderedFeatures(
      listOf(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(viewportSize.width.toDouble(), viewportSize.height.toDouble())
      ),
      RenderedQueryOptions(listOf("layer_id"), Value.nullValue())
    ) {
      Logger.e("KIRYLDD", "QRF")
      if (it.isValue && it.value?.size!! > 0) {
        val list = it.value!!
        Logger.e("KIRYLDD", list.map { qf -> qf.feature.id() }.joinToString(", "))
      }
    }
  }

  companion object {
    private const val SOURCE_ID = "backup_callout_view_source"
    private const val LAYER_ID = "backup_callout_view_layer"
    private const val DETECTOR_FREQUENCY_MS = 500L
  }
}