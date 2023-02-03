package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.internal.MapApplier
import kotlinx.coroutines.CoroutineScope

/**
 * When MapEffect enters the composition it will launch [block] into the composition's CoroutineContext.
 * The coroutine will be cancelled and re-launched when [MapEffect] is recomposed with a different [key1].
 *
 * The coroutine will be cancelled when the [MapEffect] leaves the composition.
 *
 * This function should not be used to (re-)launch ongoing tasks in response to callback events by
 * way of storing callback data in MutableState passed to key. Instead, see rememberCoroutineScope
 * to obtain a CoroutineScope that may be used to launch ongoing jobs scoped to the composition in
 * response to event callbacks.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun MapEffect(key1: Any?, block: suspend CoroutineScope.(MapView) -> Unit) {
  val map = (currentComposer.applier as MapApplier).mapView
  LaunchedEffect(key1 = key1) {
    block(map)
  }
}

/**
 * When MapEffect enters the composition it will launch [block] into the composition's CoroutineContext.
 * The coroutine will be cancelled and re-launched when [MapEffect] is recomposed with a different [key1]
 * or [key2].
 *
 * The coroutine will be cancelled when the [MapEffect] leaves the composition.
 *
 * This function should not be used to (re-)launch ongoing tasks in response to callback events by
 * way of storing callback data in MutableState passed to key. Instead, see rememberCoroutineScope
 * to obtain a CoroutineScope that may be used to launch ongoing jobs scoped to the composition in
 * response to event callbacks.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun MapEffect(key1: Any?, key2: Any?, block: suspend CoroutineScope.(MapView) -> Unit) {
  val map = (currentComposer.applier as MapApplier).mapView
  LaunchedEffect(key1 = key1, key2 = key2) {
    block(map)
  }
}

/**
 * When MapEffect enters the composition it will launch [block] into the composition's CoroutineContext.
 * The coroutine will be cancelled and re-launched when [MapEffect] is recomposed with a different [key1],
 * [key2] or [key3].
 *
 * The coroutine will be cancelled when the [MapEffect] leaves the composition.
 *
 * This function should not be used to (re-)launch ongoing tasks in response to callback events by
 * way of storing callback data in MutableState passed to key. Instead, see rememberCoroutineScope
 * to obtain a CoroutineScope that may be used to launch ongoing jobs scoped to the composition in
 * response to event callbacks.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun MapEffect(
  key1: Any?,
  key2: Any?,
  key3: Any?,
  block: suspend CoroutineScope.(MapView) -> Unit
) {
  val map = (currentComposer.applier as MapApplier).mapView
  LaunchedEffect(key1 = key1, key2 = key2, key3 = key3) {
    block(map)
  }
}

/**
 * When MapEffect enters the composition it will launch [block] into the composition's CoroutineContext.
 * The coroutine will be cancelled and re-launched when [MapEffect] is recomposed with any different [keys].
 *
 * The coroutine will be cancelled when the [MapEffect] leaves the composition.
 *
 * This function should not be used to (re-)launch ongoing tasks in response to callback events by
 * way of storing callback data in MutableState passed to key. Instead, see rememberCoroutineScope
 * to obtain a CoroutineScope that may be used to launch ongoing jobs scoped to the composition in
 * response to event callbacks.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun MapEffect(vararg keys: Any?, block: suspend CoroutineScope.(MapView) -> Unit) {
  val map = (currentComposer.applier as MapApplier).mapView
  LaunchedEffect(keys = keys) {
    block(map)
  }
}