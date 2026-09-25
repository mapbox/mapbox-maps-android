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
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager

/**
 * The state holder for Interactions of [PolylineAnnotation].
 */
@Stable
public class PolylineAnnotationInteractionsState {

  private val onClickedListener: OnPolylineAnnotationClickListener = OnPolylineAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnPolylineAnnotationLongClickListener =
    OnPolylineAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onDragListener: OnPolylineAnnotationDragListener =
      object : OnPolylineAnnotationDragListener {
        override fun onAnnotationDragStarted(annotation: Annotation<*>) {
          (annotation as? PolylineAnnotation)?.let {
            onDragStarted?.invoke(it)
          }
        }

        override fun onAnnotationDrag(annotation: Annotation<*>) {
          (annotation as? PolylineAnnotation)?.let {
            onDragged?.invoke(it)
          }
        }

        override fun onAnnotationDragFinished(annotation: Annotation<*>) {
          (annotation as? PolylineAnnotation)?.let {
            onDragFinished?.invoke(it)
          }
        }
      }

  private var onClicked: ((PolylineAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onLongClicked: ((PolylineAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onDragStarted: ((PolylineAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragged: ((PolylineAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragFinished: ((PolylineAnnotation) -> Unit)? by mutableStateOf(null)

  /**
   * Set whether PolylineAnnotation holding this PolylineAnnotationInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when PolylineAnnotation is also part of the cluster, then once PolylineAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate PolylineAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [PolylineAnnotation] is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
   * Returns reference to [PolylineAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PolylineAnnotation) -> Boolean): PolylineAnnotationInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PolylineAnnotation] is long clicked. The long clicked [PolylineAnnotation] will be passed as parameter.
   * Returns reference to [PolylineAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PolylineAnnotation) -> Boolean): PolylineAnnotationInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [PolylineAnnotation] dragging has started. The dragged [PolylineAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (PolylineAnnotation) -> Unit): PolylineAnnotationInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [PolylineAnnotation] dragging is in progress. The dragged [PolylineAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (PolylineAnnotation) -> Unit): PolylineAnnotationInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [PolylineAnnotation] dragging has finished. The dragged [PolylineAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (PolylineAnnotation) -> Unit): PolylineAnnotationInteractionsState =
    apply {
      onDragFinished = onDragFinish
    }

  @Composable
  internal fun BindTo(annotationManager: PolylineAnnotationManager) {
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
 * Create and remember a [PolylineAnnotationInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [PolylineAnnotationInteractionsState] after created and remembered.
 *
 * @return a [PolylineAnnotationInteractionsState]
 */
@Composable
public inline fun rememberPolylineAnnotationInteractionsState(crossinline init: PolylineAnnotationInteractionsState.() -> Unit = {}): PolylineAnnotationInteractionsState {
  return remember {
    PolylineAnnotationInteractionsState()
  }.apply(init)
}

// End of generated file