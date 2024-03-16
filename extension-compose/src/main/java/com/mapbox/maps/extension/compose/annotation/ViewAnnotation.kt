package com.mapbox.maps.extension.compose.annotation

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager

internal class ViewAnnotationNode(
  val viewAnnotationManager: ViewAnnotationManager,
  val view: View,
  var updatedListener: OnViewAnnotationUpdatedListener?,
) : MapNode() {
  override fun onAttached(parent: MapNode) {
    viewAnnotationManager.apply {
      updatedListener?.let {
        addOnViewAnnotationUpdatedListener(it)
      }
    }
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    viewAnnotationManager.apply {
      updatedListener?.let {
        removeOnViewAnnotationUpdatedListener(it)
      }
    }
    viewAnnotationManager.removeViewAnnotation(view = view)
  }
}

/**
 * Composable function to add a [ViewAnnotation] to the Map.
 *
 * @param options Various options needed for displaying view annotation.
 * @param modifier Modifier that will be applied to the [ComposeView] where the view annotation is created.
 * @param onUpdatedListener A listener to be invoked when the [ViewAnnotation] is updated.
 * @param content The content to draw in the [ViewAnnotation]
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun ViewAnnotation(
  options: ViewAnnotationOptions,
  modifier: Modifier = Modifier,
  onUpdatedListener: OnViewAnnotationUpdatedListener? = null,
  layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
    FrameLayout.LayoutParams.WRAP_CONTENT,
    FrameLayout.LayoutParams.WRAP_CONTENT
  ),
  content: @Composable () -> Unit,
) {
  val mapApplier = currentComposer.applier as? MapApplier
  val currentOptions = rememberUpdatedState(options)

  ComposeNode<ViewAnnotationNode, MapApplier>(
    factory = {
      val viewAnnotationManager =
        mapApplier?.mapView?.viewAnnotationManager ?: error("Error adding view annotation")

      val composeView = ComposeView(mapApplier.mapView.context).apply {
        this.layoutParams = layoutParams
      }

      composeView.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
          Box(
            modifier.onGloballyPositioned { coordinates ->
              viewAnnotationManager.updateViewAnnotation(
                this,
                with(currentOptions.value) {
                  toBuilder()
                    .height(height ?: coordinates.size.height.toDouble())
                    .width(width ?: coordinates.size.width.toDouble())
                    .build()
                }
              )
            }
          ) {
            content()
          }
        }
      }

      // need set a tag to avoid crashing, more details in [ViewAnnotationManagerImpl.measureView]
      composeView.setTag(R.id.composeView, "")
      viewAnnotationManager.addViewAnnotation(composeView, options)

      ViewAnnotationNode(
        viewAnnotationManager,
        composeView,
        onUpdatedListener,
      )
    },
    update = {
      update(options) {
        viewAnnotationManager.updateViewAnnotation(view, options)
      }
      update(content) {
        throw IllegalStateException(
          """
          Mutating content during composition is not allowed.
          """.trimIndent()
        )
      }
      update(onUpdatedListener) { onUpdatedListener ->
        this.updatedListener = onUpdatedListener
      }
    }
  )
}