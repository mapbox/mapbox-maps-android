// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationLongClickListener

/**
 * The state holder for Interactions of [CircleAnnotation].
 */
@Stable
public class CircleAnnotationInteractionsState {

  private var onClickedListener: OnCircleAnnotationClickListener? by mutableStateOf(null)

  private var onLongClickedListener: OnCircleAnnotationLongClickListener? by mutableStateOf(null)

  /**
   * Set onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (CircleAnnotation) -> Boolean): CircleAnnotationInteractionsState =
    apply {
      onClickedListener = OnCircleAnnotationClickListener {
        onClick.invoke(it)
      }
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [CircleAnnotation] is long clicked. The long clicked [CircleAnnotation] will be passed as parameter.
   * Returns reference to [CircleAnnotationInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (CircleAnnotation) -> Boolean): CircleAnnotationInteractionsState =
    apply {
      onLongClickedListener = OnCircleAnnotationLongClickListener {
        onLongClick.invoke(it)
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