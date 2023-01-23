package com.mapbox.maps.extension.compose.annotation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapApplier
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*

internal class CircleAnnotationNode(
  val circleAnnotationManager: CircleAnnotationManager,
  val circleAnnotation: CircleAnnotation,
  var onCircleAnnotationClicked: (CircleAnnotation) -> Unit
) : AnnotationNode {
  override fun onCleared() {
    circleAnnotationManager.delete(circleAnnotation)
  }

  override fun onRemoved() {
    circleAnnotationManager.delete(circleAnnotation)
  }
}

@Composable
@MapboxMapComposable
public fun CircleAnnotation(
  point: Point,
  circleRadius: Double = 10.0,
  onClick: (CircleAnnotation) -> Unit = {},
) {
  val mapApplier = currentComposer.applier as? MapApplier
  ComposeNode<CircleAnnotationNode, MapApplier>(
    factory = {
      logE("compose", "CircleAnnotation ComposeNode factory")
      val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
        .withPoint(point)
        .withCircleRadius(circleRadius)
      val circleAnnotationManager =
        mapApplier?.mapPluginProvider?.annotations?.createCircleAnnotationManager()
          ?: error("Error adding circle annotation")
      val circleAnnotation = circleAnnotationManager.create(circleAnnotationOptions)
      circleAnnotationManager.addClickListener(
        OnCircleAnnotationClickListener {
          onClick.invoke(it)
          true
        }
      )
      CircleAnnotationNode(circleAnnotationManager, circleAnnotation, onClick)
    },
    update = {
      update(onClick) {
        this.onCircleAnnotationClicked = it
      }
      set(point) {
        logE("compose", "CircleAnnotation point set")
        this.circleAnnotation.point = it
        circleAnnotationManager.update(circleAnnotation)
      }
      set(circleRadius) {
        logE("compose", "CircleAnnotation circleRadius set")
        this.circleAnnotation.circleRadius = it
        circleAnnotationManager.update(circleAnnotation)
      }
    }
  )
}