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
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for Interactions of [PointAnnotationGroup].
 */
@Stable
public class PointAnnotationGroupInteractionsState {

  private var onClickedListener: OnPointAnnotationClickListener? by mutableStateOf(null)

  private var onLongClickedListener: OnPointAnnotationLongClickListener? by mutableStateOf(null)

  private var onClusterClickedListener: OnClusterClickListener? by mutableStateOf(null)

  private var onClusterLongClickedListener: OnClusterLongClickListener? by mutableStateOf(null)

  /**
   * Set onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PointAnnotation) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onClickedListener = OnPointAnnotationClickListener {
        onClick.invoke(it)
      }
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PointAnnotation] is long clicked. The long clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PointAnnotation) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      onLongClickedListener = OnPointAnnotationLongClickListener {
        onLongClick.invoke(it)
      }
    }

  /**
   * Set onClusterClick Callback to be invoked when one of the clusters is clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [camelize(type) GroupInteractionsState] for making chained calls.
   */
  public fun onClusterClicked(onClusterClick: (ClusterFeature) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      this.onClusterClickedListener = OnClusterClickListener {
        onClusterClick.invoke(it)
      }
    }

  /**
   * Set onClusterLongClick Callback to be invoked when one of the clusters is long clicked. The clicked cluster represented by a [Feature] wrapped with [ClusterFeature] with additional properties.
   * Returns reference to [camelize(type) GroupInteractionsState] for making chained calls.
   */
  public fun onClusterLongClicked(onClusterLongClick: (ClusterFeature) -> Boolean): PointAnnotationGroupInteractionsState =
    apply {
      this.onClusterLongClickedListener = OnClusterLongClickListener {
        onClusterLongClick.invoke(it)
      }
    }

  @Composable
  internal fun BindTo(annotationManager: PointAnnotationManager) {
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