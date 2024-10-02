// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

/**
 * The state holder for Interactions of [PolygonAnnotationGroup].
 */
@Stable
public class PolygonAnnotationGroupInteractionsState {

  private var onClickedListener: OnPolygonAnnotationClickListener? by mutableStateOf(null)

  private var onLongClickedListener: OnPolygonAnnotationLongClickListener? by mutableStateOf(null)

  /**
   * Set onClick Callback to be invoked when the [PolygonAnnotation] is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
   * Returns reference to [PolygonAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onClicked(onClick: (PolygonAnnotation) -> Boolean): PolygonAnnotationGroupInteractionsState =
    apply {
      onClickedListener = OnPolygonAnnotationClickListener {
        onClick.invoke(it)
      }
    }

  /**
   * Set onLongClick Callback to be invoked when one of the [PolygonAnnotation] is long clicked. The long clicked [PolygonAnnotation] will be passed as parameter.
   * Returns reference to [PolygonAnnotationGroupInteractionsState] for making chained calls.
   */
  public fun onLongClicked(onLongClick: (PolygonAnnotation) -> Boolean): PolygonAnnotationGroupInteractionsState =
    apply {
      onLongClickedListener = OnPolygonAnnotationLongClickListener {
        onLongClick.invoke(it)
      }
    }

  @Composable
  internal fun BindTo(annotationManager: PolygonAnnotationManager) {
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
 * Create and remember a [PolygonAnnotationGroupInteractionsState] with init block.
 *
 * @param init the initialization block to be applied to the [PolygonAnnotationGroupInteractionsState] after created and remembered.
 *
 * @return a [PolygonAnnotationGroupInteractionsState]
 */
@Composable
public inline fun rememberPolygonAnnotationGroupInteractionsState(crossinline init: PolygonAnnotationGroupInteractionsState.() -> Unit = {}): PolygonAnnotationGroupInteractionsState {
  return remember {
    PolygonAnnotationGroupInteractionsState()
  }.apply(init)
}

// End of generated file