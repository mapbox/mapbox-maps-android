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
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationDragListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for Interactions of [PointAnnotationGroup].
 */
@Stable
public class PointAnnotationGroupInteractionsState {

  private val onClickedListener: OnPointAnnotationClickListener = OnPointAnnotationClickListener {
    onClicked?.invoke(it) ?: false
  }

  private val onLongClickedListener: OnPointAnnotationLongClickListener =
    OnPointAnnotationLongClickListener {
      onLongClicked?.invoke(it) ?: false
    }

  private val onClusterClickedListener: OnClusterClickListener = OnClusterClickListener {
    onClusterClicked?.invoke(it) ?: false
  }

  private val onClusterLongClickedListener: OnClusterLongClickListener = OnClusterLongClickListener {
    onClusterLongClicked?.invoke(it) ?: false
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
  private var onClusterClicked: ((ClusterFeature) -> Boolean)? by mutableStateOf(null)
  private var onClusterLongClicked: ((ClusterFeature) -> Boolean)? by mutableStateOf(null)

  /**
   * Set whether PointAnnotation holding this PointAnnotationGroupInteractionsState should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   * Note: If this param is used when PointAnnotation is also part of the cluster, then once PointAnnotation is dragged,
   * it moves out of the cluster and can't be added back to it and is rendered as a separate PointAnnotation.
   */
  public var isDraggable: Boolean by mutableStateOf(false)

  /**
   * Set onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PointAnnotation) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onClicked = onClick
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PointAnnotation] is long clicked. The long clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PointAnnotation) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onLongClicked = onLongClick
    }

  /**
   * Set onClusterClick Callback to be invoked when one of the clusters is clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClusterClicked(onClusterClick: (ClusterFeature) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onClusterClicked = onClusterClick
    }

  /**
   * Set onClusterLongClick Callback to be invoked when one of the clusters is long clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClusterLongClicked(onClusterLongClick: (ClusterFeature) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onClusterLongClicked = onClusterLongClick
    }

  /**
   *  Set onDragStart Callback to be invoked when one of the [PointAnnotation] dragging has started. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragStarted(onDragStart: (PointAnnotation) -> Unit): PointAnnotationGroupInteractionsState =
    apply {
      onDragStarted = onDragStart
    }

  /**
   *  Set onDrag Callback to be invoked when one of the [PointAnnotation] dragging is in progress. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragged(onDrag: (PointAnnotation) -> Unit): PointAnnotationGroupInteractionsState =
    apply {
      onDragged = onDrag
    }

  /**
   *  Set onDragFinish Callback to be invoked when one of the [PointAnnotation] dragging has finished. The dragged [PointAnnotation] will be passed as parameter.
   */
  public fun onDragFinished(onDragFinish: (PointAnnotation) -> Unit): PointAnnotationGroupInteractionsState =
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
 * Create and remember a [PointAnnotationGroupInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [PointAnnotationGroupInteractionsState] after created and remembered.
 *
 * @return a [PointAnnotationGroupInteractionsState]
 */
@Composable
public inline fun rememberPointAnnotationGroupInteractionsState(crossinline init: PointAnnotationGroupInteractionsState.() -> Unit = {}): PointAnnotationGroupInteractionsState {
  return remember {
    PointAnnotationGroupInteractionsState()
  }.apply(init)
}

// End of generated file