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
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for Interactions of [PointAnnotation].
 */
@Stable
public class PointAnnotationInteractionsState {

  private val onClickedListener: OnPointAnnotationClickListener = OnPointAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnPointAnnotationLongClickListener =
    OnPointAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onDragListener: OnPointAnnotationDragListener =
      object : OnPointAnnotationDragListener {
        override fun onAnnotationDragStarted(annotation: Annotation<*>) {
          (annotation as? PointAnnotation)?.let {
            onDragStarted?.invoke(it)
          }
        }

        override fun onAnnotationDrag(annotation: Annotation<*>) {
          (annotation as? PointAnnotation)?.let {
            onDragged?.invoke(it)
          }
        }

        override fun onAnnotationDragFinished(annotation: Annotation<*>) {
          (annotation as? PointAnnotation)?.let {
            onDragFinished?.invoke(it)
          }
        }
      }

  private var onClicked: ((PointAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onLongClicked: ((PointAnnotation) -> Boolean)? by mutableStateOf(null)
  private var onDragStarted: ((PointAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragged: ((PointAnnotation) -> Unit)? by mutableStateOf(null)
  private var onDragFinished: ((PointAnnotation) -> Unit)? by mutableStateOf(null)

  /**
   * Set whether PointAnnotation holding this PointAnnotationInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when PointAnnotation is also part of the cluster, then once PointAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate PointAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PointAnnotation) -> Boolean): PointAnnotationInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PointAnnotation] is long clicked. The long clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PointAnnotation) -> Boolean): PointAnnotationInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [PointAnnotation] dragging has started. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (PointAnnotation) -> Unit): PointAnnotationInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [PointAnnotation] dragging is in progress. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (PointAnnotation) -> Unit): PointAnnotationInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [PointAnnotation] dragging has finished. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (PointAnnotation) -> Unit): PointAnnotationInteractionsState =
    apply {
      onDragFinished = onDragFinish
    }

  @Composable
  internal fun BindTo(annotationManager: PointAnnotationManager) {
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
 * Create and remember a [PointAnnotationInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [PointAnnotationInteractionsState] after created and remembered.
 *
 * @return a [PointAnnotationInteractionsState]
 */
@Composable
public inline fun rememberPointAnnotationInteractionsState(crossinline init: PointAnnotationInteractionsState.() -> Unit = {}): PointAnnotationInteractionsState {
  return remember {
    PointAnnotationInteractionsState()
  }.apply(init)
}

// End of generated file