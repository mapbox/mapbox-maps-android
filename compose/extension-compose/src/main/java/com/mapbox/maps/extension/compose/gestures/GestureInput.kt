package com.mapbox.maps.extension.compose.gestures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mapbox.maps.MapboxExperimental

/**
 * Runs [block] with a [GestureScope] receiver in a coroutine scoped to the map attachment
 * lifecycle. The coroutine is canceled and restarted whenever the map reattaches.
 *
 * @param block a suspend lambda with [GestureScope] receiver.
 */
@MapboxExperimental
@Composable
public fun GesturesState.GestureInput(
  block: suspend GestureScope.() -> Unit,
) {
  LaunchedEffect(this) {
    gestureInput(block)
  }
}

/**
 * Runs [block] with a [GestureScope] receiver in a coroutine scoped to the map attachment
 * lifecycle. The coroutine is canceled and restarted when [key1] or the map changes.
 *
 * @param key1 restarts the effect when it changes.
 * @param block a suspend lambda with [GestureScope] receiver.
 */
@MapboxExperimental
@Composable
public fun GesturesState.GestureInput(
  key1: Any?,
  block: suspend GestureScope.() -> Unit,
) {
  LaunchedEffect(key1, this) {
    gestureInput(block)
  }
}

/**
 * Runs [block] with a [GestureScope] receiver in a coroutine scoped to the map attachment
 * lifecycle. The coroutine is canceled and restarted when [key1], [key2], or the map changes.
 *
 * @param key1 restarts the effect when it changes.
 * @param key2 restarts the effect when it changes.
 * @param block a suspend lambda with [GestureScope] receiver.
 */
@MapboxExperimental
@Composable
public fun GesturesState.GestureInput(
  key1: Any?,
  key2: Any?,
  block: suspend GestureScope.() -> Unit,
) {
  LaunchedEffect(key1, key2, this) {
    gestureInput(block)
  }
}

/**
 * Runs [block] with a [GestureScope] receiver in a coroutine scoped to the map attachment
 * lifecycle. The coroutine is canceled and restarted when [key1], [key2], [key3], or the map
 * changes.
 *
 * @param key1 restarts the effect when it changes.
 * @param key2 restarts the effect when it changes.
 * @param key3 restarts the effect when it changes.
 * @param block a suspend lambda with [GestureScope] receiver.
 */
@MapboxExperimental
@Composable
public fun GesturesState.GestureInput(
  key1: Any?,
  key2: Any?,
  key3: Any?,
  block: suspend GestureScope.() -> Unit,
) {
  LaunchedEffect(key1, key2, key3, this) {
    gestureInput(block)
  }
}

/**
 * Runs [block] with a [GestureScope] receiver in a coroutine scoped to the map attachment
 * lifecycle. The coroutine is canceled and restarted when any value in [keys] or the map changes.
 *
 * @param keys restarts the effect when any element changes.
 * @param block a suspend lambda with [GestureScope] receiver.
 */
@MapboxExperimental
@Composable
public fun GesturesState.GestureInput(
  vararg keys: Any?,
  block: suspend GestureScope.() -> Unit,
) {
  LaunchedEffect(*keys, this) {
    gestureInput(block)
  }
}