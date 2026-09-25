package com.mapbox.maps.extension.compose.gestures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.applySettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Create and remember a [GesturesState] with init block.
 *
 * @param init the initialization block to be applied to the [GesturesState] after created and remembered.
 *
 * @return [GesturesState]
 */
@MapboxExperimental
@Composable
public inline fun rememberGesturesState(
  key: String? = null,
  crossinline init: GesturesState.() -> Unit = {},
): GesturesState = rememberSaveable(key = key, saver = GesturesState.Saver) {
  GesturesState().apply(init)
}

/**
 * A state holder for gesture configuration and gesture event observation.
 *
 * Obtain an instance through [MapState.gesturesState][com.mapbox.maps.extension.compose.MapState.gesturesState].
 *
 * Use [gesturesSettings] to configure which gestures are enabled, and [GestureInput] to
 * observe individual gesture events in a coroutine-based, lifecycle-aware fashion.
 */
@MapboxExperimental
@Stable
public class GesturesState(initialGesturesSettings: GesturesSettings = GesturesSettings { }) {

  internal val mapboxMapFlow = MutableStateFlow<MapboxMap?>(null)

  /**
   * Gesture configuration that controls user touch interaction.
   */
  public var gesturesSettings: GesturesSettings by mutableStateOf(initialGesturesSettings)

  /**
   * Returns `true` if a gesture is currently in progress.
   */
  public fun isGestureInProgress(): Boolean = mapboxMapFlow.value?.isGestureInProgress() ?: false

  /**
   * Returns `true` if a user-initiated animation is currently in progress.
   */
  public fun isUserAnimationInProgress(): Boolean = mapboxMapFlow.value?.isUserAnimationInProgress() ?: false

  /**
   * Binds this [GesturesState] to the given [MapboxMap], applying [gesturesSettings]
   * reactively and managing the map reference lifecycle.
   */
  @Composable
  internal fun BindToMap(mapboxMap: MapboxMap) {
    LaunchedEffect(mapboxMap, gesturesSettings) {
      mapboxMap.gesturesPlugin { applySettings(gesturesSettings) }
    }
    DisposableEffect(mapboxMap) {
      mapboxMapFlow.value = mapboxMap
      onDispose { mapboxMapFlow.value = null }
    }
  }

  /**
   * Suspends until the coroutine is canceled, collecting each map attachment and
   * running [block] with a [GestureScope] receiver. When the map is replaced or detaches, the
   * current [block] invocation is canceled and restarted on the new map.
   *
   * This is the non-Composable counterpart of [GestureInput]. Prefer [GestureInput] inside
   * Composition; use this function when you need gesture observation from a plain coroutine or
   * custom [LaunchedEffect] with a lifecycle other than the default.
   *
   * @param block a suspend lambda with [GestureScope] receiver.
   */
  @MapboxExperimental
  public suspend fun gestureInput(
    block: suspend GestureScope.() -> Unit,
  ) {
    mapboxMapFlow.collectLatest { mapboxMap ->
      if (mapboxMap != null) {
        GestureScope(mapboxMap).block()
      }
    }
  }

  /**
   * Public companion object of [GesturesState].
   */
  public companion object {
    /**
     * The default [Saver] implementation for [GesturesState].
     */
    public val Saver: Saver<GesturesState, GesturesSettings> = Saver(
      save = { it.gesturesSettings },
      restore = { GesturesState(it) }
    )
  }
}