package com.mapbox.maps.plugin.viewannotation.core

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.QueriedFeature
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions

internal class BackupSymbolLayerManager(
  private val collisionsCallback: CollisionDetectorCallback,
  mapDelegateProvider: MapDelegateProvider
) {
  private val backupSymbolIcons = hashMapOf<String, BackupSymbol>()
  private val workHandler = Handler(Looper.getMainLooper())
  private var detectorRunning = false

  private val mapFeatureQueryDelegate = mapDelegateProvider.mapFeatureQueryDelegate
  private val mapTransformDelegate = mapDelegateProvider.mapTransformDelegate
  private val annotationPlugin: AnnotationPlugin

  private var pointAnnotationManager: PointAnnotationManager? = null
  private val pixelRatio = mapDelegateProvider.mapTransformDelegate.getMapOptions().pixelRatio

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
    pointAnnotationManager = annotationPlugin.createPointAnnotationManager(view)
  }

  fun createBackupSymbol(id: String, options: ViewAnnotationOptions) {
    val bitmap = Bitmap.createBitmap(
      options.width,
      options.height,
      Bitmap.Config.ARGB_8888
    )
    bitmap.eraseColor(Color.TRANSPARENT)
    val pointAnnotationOptions = PointAnnotationOptions()
      .withPoint(options.geometry as Point)
      .withIconAnchor(options.anchor!!.mapToIconAnchor())
      .withIconImage(bitmap)
      .withIconOffset(
        listOf(
          options.offsetX.toDouble() / pixelRatio,
          -options.offsetY.toDouble() / pixelRatio
        )
      )
    backupSymbolIcons[id] = BackupSymbol(options, pointAnnotationManager?.create(pointAnnotationOptions))
    if (detectorRunning) {
      return
    }
    detectorRunning = true
    runCollisionDetectorLoop()
  }

  fun updateBackupSymbol(id: String, options: ViewAnnotationOptions) {
    backupSymbolIcons[id]?.let {
      val bitmap = Bitmap.createBitmap(
        options.width,
        options.height,
        Bitmap.Config.ARGB_8888
      )
      bitmap.eraseColor(Color.TRANSPARENT)
      it.pointAnnotation?.let { annotation ->
        annotation.iconAnchor = options.anchor!!.mapToIconAnchor()
        annotation.point = options.geometry as Point
        annotation.iconImageBitmap = bitmap
        annotation.iconOffset = listOf(
          options.offsetX.toDouble() / pixelRatio,
          -options.offsetY.toDouble() / pixelRatio
        )
        pointAnnotationManager?.update(annotation)
      }
    }
  }

  fun removeBackupSymbol(id: String) {
    backupSymbolIcons.remove(id)
    if (backupSymbolIcons.isEmpty()) {
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
      // TODO refactor filter to use ids only and remove hardcoded `layer_id`
      RenderedQueryOptions(listOf("layer_id", pointAnnotationManager?.layerId), Value.nullValue())
    ) {
      if (it.isValue && it.value?.size!! > 0) {
        val visibleViewsAfterCollisionDetection = calculateCollisionVisibility(it.value!!)
        val visibleViewsAfterFeatureCollisionDetection = calculateFeatureVisibility(it.value!!)
        Logger.e("KIRYLDD", "1: " + visibleViewsAfterCollisionDetection.joinToString(", "))
        Logger.e("KIRYLDD", "2: " + visibleViewsAfterFeatureCollisionDetection.joinToString(", "))
        collisionsCallback.onCollisionDetected(
          visibleViewsAfterCollisionDetection,
          visibleViewsAfterFeatureCollisionDetection
        )
      }
    }
  }

  private fun calculateCollisionVisibility(qfList: List<QueriedFeature>): List<String> {
    val visibleViewIds = mutableListOf<String>()
    qfList.forEach { qf ->
      val backupSymbolId = qf.feature.properties()?.getAsJsonPrimitive(BACKUP_SYMBOL_LAYER_ID)?.asString
      backupSymbolIcons.forEach { backupIcon ->
        if (backupIcon.value.pointAnnotation?.id.toString() == backupSymbolId) {
          visibleViewIds.add(backupIcon.key)
        }
      }
    }
    return visibleViewIds
  }

  private fun calculateFeatureVisibility(qfList: List<QueriedFeature>): List<String> {
    val visibleViewIds = mutableListOf<String>()
    val visibleFeatureList = qfList
      .filter { it.feature.id() != null }
      .map { it.feature.id() }
    backupSymbolIcons.forEach { entry ->
      entry.value.viewAnnotationOptions.featureIdentifier?.let {
        if (visibleFeatureList.contains(it)) {
          visibleViewIds.add(entry.key)
        }
      }
    }
    return visibleViewIds
  }

  companion object {
    private const val DETECTOR_FREQUENCY_MS = 500L
    private const val BACKUP_SYMBOL_LAYER_ID = "PointAnnotation"
  }
}