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
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationLongClickListener

/**
 * The state holder for Interactions of [CircleAnnotation].
 */
@Stable
public class CircleAnnotationInteractionsState {

  private val onClickedListener: OnCircleAnnotationClickListener = OnCircleAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnCircleAnnotationLongClickListener =
    OnCircleAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onDragListener: OnCircleAnnotationDragListener =
      object : OnCircleAnnotationDragListener {
        override fun onAnnotationDragStarted(annotation: Annotation<*>) {
          (annotation as? CircleAnnotation)?.let {
            onDragStarted?.invoke(it)
          }
        }

        override fun onAnnotationDrag(annotation: Annotation<*>) {
          (annotation as? CircleAnnotation)?.let {
            onDragged?.invoke(it)
          }
        }

        override fun onAnnotationDragFinished(annotation: Annotation<*>) {
          (annotation as? CircleAnnotation)?.let {
            onDragFinished?.invoke(it)
          }
        }
      }

  private var onClicked: ((CircleAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onLongClicked: ((CircleAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onDragStarted: ((CircleAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragged: ((CircleAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragFinished: ((CircleAnnotation) -> Unit)? by mutableStateOf(null)

  /**
   * Set whether CircleAnnotation holding this CircleAnnotationInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when CircleAnnotation is also part of the cluster, then once CircleAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate CircleAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (CircleAnnotation) -> Boolean): CircleAnnotationInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [CircleAnnotation] is long clicked. The long clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (CircleAnnotation) -> Boolean): CircleAnnotationInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [CircleAnnotation] dragging has started. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (CircleAnnotation) -> Unit): CircleAnnotationInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [CircleAnnotation] dragging is in progress. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (CircleAnnotation) -> Unit): CircleAnnotationInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [CircleAnnotation] dragging has finished. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (CircleAnnotation) -> Unit): CircleAnnotationInteractionsState =
    apply {
      onDragFinished = onDragFinish
    }

  @Composable
  internal fun BindTo(annotationManager: CircleAnnotationManager) {
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
 * Create and remember a [CircleAnnotationInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [CircleAnnotationInteractionsState] after created and remembered.
 *
 * @return a [CircleAnnotationInteractionsState]
 */
@Composable
public inline fun rememberCircleAnnotationInteractionsState(crossinline init: CircleAnnotationInteractionsState.() -> Unit = {}): CircleAnnotationInteractionsState {
  return remember {
    CircleAnnotationInteractionsState()
  }.apply(init)
}

// End of generated file