// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.plugin.annotation.ClusterFeature
import com.mapbox.maps.plugin.annotation.OnClusterClickListener
import com.mapbox.maps.plugin.annotation.OnClusterLongClickListener
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationLongClickListener

/**
 * The state holder for Interactions of [CircleAnnotationGroup].
 */
@Stable
public class CircleAnnotationGroupInteractionsState {

  private var onClickedListener: OnCircleAnnotationClickListener? by mutableStateOf(null)

  private var onLongClickedListener: OnCircleAnnotationLongClickListener? by mutableStateOf(null)

  private var onClusterClickedListener: OnClusterClickListener? by mutableStateOf(null)

  private var onClusterLongClickedListener: OnClusterLongClickListener? by mutableStateOf(null)

  /**
   * Set onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (CircleAnnotation) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onClickedListener = OnCircleAnnotationClickListener {
        onClick.invoke(it)
      }
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [CircleAnnotation] is long clicked. The long clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (CircleAnnotation) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      onLongClickedListener = OnCircleAnnotationLongClickListener {
        onLongClick.invoke(it)
      }
    }

  /**
   * Set onClusterClick Callback to be invoked when one of the clusters is clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [camelize(type) GroupInteractionsState] for making chained calls.
   */
  public fun onClusterClicked(onClusterClick: (ClusterFeature) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      this.onClusterClickedListener = OnClusterClickListener {
        onClusterClick.invoke(it)
      }
    }

  /**
   * Set onClusterLongClick Callback to be invoked when one of the clusters is long clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [camelize(type) GroupInteractionsState] for making chained calls.
   */
  public fun onClusterLongClicked(onClusterLongClick: (ClusterFeature) -> Boolean): CircleAnnotationGroupInteractionsState =
    apply {
      this.onClusterLongClickedListener = OnClusterLongClickListener {
        onClusterLongClick.invoke(it)
      }
    }

  @Composable
  internal fun BindTo(annotationManager: CircleAnnotationManager) {
    with(onClickedListener) {
      DisposableEffect(key1 = this) {
        this@with?.let {
          annotationManager.addClickListener(it)
        }
        onDispose {
          this@with?.let {
            annotationManager.removeClickListener(it)
          }
        }
      }
    }
    with(onLongClickedListener) {
      DisposableEffect(key1 = this) {
        this@with?.let {
          annotationManager.addLongClickListener(it)
        }
        onDispose {
          this@with?.let {
            annotationManager.removeLongClickListener(it)
          }
        }
      }
     }
    with(onClusterClickedListener) {
      DisposableEffect(key1 = this) {
        this@with?.let {
          annotationManager.addClusterClickListener(it)
        }
        onDispose {
          this@with?.let {
            annotationManager.removeClusterClickListener(it)
          }
        }
      }
    }
    with(onClusterLongClickedListener) {
      DisposableEffect(key1 = this) {
        this@with?.let {
          annotationManager.addClusterLongClickListener(it)
        }
        onDispose {
          this@with?.let {
            annotationManager.removeClusterLongClickListener(it)
          }
        }
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