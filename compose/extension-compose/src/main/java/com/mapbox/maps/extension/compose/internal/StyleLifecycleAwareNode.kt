package com.mapbox.maps.extension.compose.internal

import androidx.annotation.CallSuper
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.internal.StyleAwareNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * Nodes inheriting from this class can use [dispatchWhenReady] to apply actions that require style
 * to be ready before applying the action.
 */
internal abstract class StyleLifecycleAwareNode(
    private val mapStyleManager: MapboxStyleManager,
    private val coroutineScope: CoroutineScope
) : MapNode() {
  private val styleManagerFlow: MutableStateFlow<MapboxStyleManager?> = MutableStateFlow(null)

  @CallSuper
  override fun onAttached(parent: MapNode) {
    super.onAttached(parent)
    updateStyleManagerState(parent)
  }

  private fun updateStyleManagerState(parent: MapNode) {
    when (parent) {
      is StyleAwareNode -> coroutineScope.launch {
        parent.mapStyleNode.styleDataLoaded.firstOrNull()?.let {
          styleManagerFlow.value = mapStyleManager
        }
      }

      else -> styleManagerFlow.value = mapStyleManager
    }
  }

  protected fun dispatchWhenReady(action: (MapboxStyleManager) -> Unit) {
    val mapboxStyleManager = styleManagerFlow.value
    // If we have the style manager, we can apply the action immediately
    if (mapboxStyleManager != null) {
      action(mapboxStyleManager)
    } else {
      // Otherwise, we need to wait for the style manager to be available
      coroutineScope.launch {
        styleManagerFlow.filterNotNull().first().apply(action)
      }
    }
  }
}