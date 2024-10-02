// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for Interactions of [PointAnnotation].
 */
@Stable
public class PointAnnotationInteractionsState {

  private var onClickedListener: OnPointAnnotationClickListener? by mutableStateOf(null)

  private var onLongClickedListener: OnPointAnnotationLongClickListener? by mutableStateOf(null)

  /**
   * Set onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PointAnnotation) -> Boolean): PointAnnotationInteractionsState =
    apply {
      onClickedListener = OnPointAnnotationClickListener {
        onClick.invoke(it)
      }
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PointAnnotation] is long clicked. The long clicked [PointAnnotation] will be passed as parameter.
   * Returns reference to [PointAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PointAnnotation) -> Boolean): PointAnnotationInteractionsState =
    apply {
      onLongClickedListener = OnPointAnnotationLongClickListener {
        onLongClick.invoke(it)
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