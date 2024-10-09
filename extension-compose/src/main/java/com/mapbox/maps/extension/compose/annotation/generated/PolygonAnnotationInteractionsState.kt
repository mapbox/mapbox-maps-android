// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.plugin.annotation.Annotation
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

/**
 * The state holder for Interactions of [PolygonAnnotation].
 */
@Stable
public class PolygonAnnotationInteractionsState {

  private val onClickedListener: OnPolygonAnnotationClickListener = OnPolygonAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnPolygonAnnotationLongClickListener =
    OnPolygonAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onDragListener: OnPolygonAnnotationDragListener =
      object : OnPolygonAnnotationDragListener {
        override fun onAnnotationDragStarted(annotation: Annotation<*>) {
          (annotation as? PolygonAnnotation)?.let {
            onDragStarted?.invoke(it)
          }
        }

        override fun onAnnotationDrag(annotation: Annotation<*>) {
          (annotation as? PolygonAnnotation)?.let {
            onDragged?.invoke(it)
          }
        }

        override fun onAnnotationDragFinished(annotation: Annotation<*>) {
          (annotation as? PolygonAnnotation)?.let {
            onDragFinished?.invoke(it)
          }
        }
      }

  private var onClicked: ((PolygonAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onLongClicked: ((PolygonAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onDragStarted: ((PolygonAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragged: ((PolygonAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragFinished: ((PolygonAnnotation) -> Unit)? by mutableStateOf(null)

  /**
   * Set whether PolygonAnnotation holding this PolygonAnnotationInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when PolygonAnnotation is also part of the cluster, then once PolygonAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate PolygonAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [PolygonAnnotation] is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
   * Returns reference to [PolygonAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PolygonAnnotation) -> Boolean): PolygonAnnotationInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PolygonAnnotation] is long clicked. The long clicked [PolygonAnnotation] will be passed as parameter.
   * Returns reference to [PolygonAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PolygonAnnotation) -> Boolean): PolygonAnnotationInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [PolygonAnnotation] dragging has started. The dragged [PolygonAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (PolygonAnnotation) -> Unit): PolygonAnnotationInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [PolygonAnnotation] dragging is in progress. The dragged [PolygonAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (PolygonAnnotation) -> Unit): PolygonAnnotationInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [PolygonAnnotation] dragging has finished. The dragged [PolygonAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (PolygonAnnotation) -> Unit): PolygonAnnotationInteractionsState =
    apply {
      onDragFinished = onDragFinish
    }

  @Composable
  internal fun BindTo(annotationManager: PolygonAnnotationManager) {
    DisposableEffect(key1 = onClicked) {
      onClicked?.let {
        annotationManager.addClickListener(onClickedListener)
      }
      onDispose {
        annotationManager.removeClickListener(onClickedListener)
      }
    }
    DisposableEffect(key1 = onLongClicked) {
      onLongClicked?.let {
        annotationManager.addLongClickListener(onLongClickedListener)
      }
      onDispose {
        annotationManager.removeLongClickListener(onLongClickedListener)
      }
    }
    DisposableEffect(key1 = onDragStarted, onDragged, onDragFinished) {
      if (onDragStarted != null || onDragged != null || onDragFinished != null) {
        annotationManager.addDragListener(onDragListener)
      }
      onDispose {
        annotationManager.removeDragListener(onDragListener)
      }
    }
  }
}

/**
 * Create and remember a [PolygonAnnotationInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [PolygonAnnotationInteractionsState] after created and remembered.
 *
 * @return a [PolygonAnnotationInteractionsState]
 */
@Composable
public inline fun rememberPolygonAnnotationInteractionsState(crossinline init: PolygonAnnotationInteractionsState.() -> Unit = {}): PolygonAnnotationInteractionsState {
  return remember {
    PolygonAnnotationInteractionsState()
  }.apply(init)
}

// End of generated file