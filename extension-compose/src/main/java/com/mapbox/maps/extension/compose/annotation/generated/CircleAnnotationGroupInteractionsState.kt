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
import com.mapbox.maps.plugin.annotation.ClusterFeature
import com.mapbox.maps.plugin.annotation.OnClusterClickListener
import com.mapbox.maps.plugin.annotation.OnClusterLongClickListener
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationLongClickListener

/**
 * The state holder for Interactions of [CircleAnnotationGroup].
 */
@Stable
public class CircleAnnotationGroupInteractionsState {

  private val onClickedListener: OnCircleAnnotationClickListener = OnCircleAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnCircleAnnotationLongClickListener =
    OnCircleAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onClusterClickedListener: OnClusterClickListener = OnClusterClickListener {
    onClusterClicked?.invoke(it) ?: false
  }

  private val onClusterLongClickedListener: OnClusterLongClickListener = OnClusterLongClickListener {
    onClusterLongClicked?.invoke(it) ?: false
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
  private var onClusterClicked: ((ClusterFeature) -> Boolean)? by mutableStateOf(null)
  private var onClusterLongClicked: ((ClusterFeature) -> Boolean)? by mutableStateOf(null)

  /**
   * Set whether CircleAnnotation holding this CircleAnnotationGroupInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when CircleAnnotation is also part of the cluster, then once CircleAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate CircleAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (CircleAnnotation) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [CircleAnnotation] is long clicked. The long clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (CircleAnnotation) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   * Set onClusterClick Callback to be invoked when one of the clusters is clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClusterClicked(onClusterClick: (ClusterFeature) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onClusterClicked = onClusterClick
    }

  /**
   * Set onClusterLongClick Callback to be invoked when one of the clusters is long clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClusterLongClicked(onClusterLongClick: (ClusterFeature) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onClusterLongClicked = onClusterLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [CircleAnnotation] dragging has started. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (CircleAnnotation) -> Unit): CircleAnnotationGroupInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [CircleAnnotation] dragging is in progress. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (CircleAnnotation) -> Unit): CircleAnnotationGroupInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [CircleAnnotation] dragging has finished. The dragged [CircleAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (CircleAnnotation) -> Unit): CircleAnnotationGroupInteractionsState =
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
    DisposableEffect(key1 = onClusterClicked) {
      onClusterClicked.let {
        annotationManager.addClusterClickListener(onClusterClickedListener)
      }
      onDispose {
          annotationManager.removeClusterClickListener(onClusterClickedListener)
      }
    }
    DisposableEffect(key1 = onClusterLongClicked) {
      onClusterLongClicked.let {
        annotationManager.addClusterLongClickListener(onClusterLongClickedListener)
      }
      onDispose {
        annotationManager.removeClusterLongClickListener(onClusterLongClickedListener)
      }
    }
  }
}

/**
 * Create and remember a [CircleAnnotationGroupInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [CircleAnnotationGroupInteractionsState] after created and remembered.
 *
 * @return a [CircleAnnotationGroupInteractionsState]
 */
@Composable
public inline fun rememberCircleAnnotationGroupInteractionsState(crossinline init: CircleAnnotationGroupInteractionsState.() -> Unit = {}): CircleAnnotationGroupInteractionsState {
  return remember {
    CircleAnnotationGroupInteractionsState()
  }.apply(init)
}

// End of generated file