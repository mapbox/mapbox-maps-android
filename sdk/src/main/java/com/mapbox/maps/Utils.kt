package com.mapbox.maps

import com.mapbox.bindgen.Expected
import com.mapbox.common.Cancelable
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import kotlin.coroutines.Continuation

/**
 * Checks the reference and if valid, calls the requested method.
 */
internal fun <T, R> WeakReference<T>.call(method: T.() -> R): R {
  return (this.get() ?: throw IllegalStateException()).method()
}

/**
 * Wraps the methods returning [Cancelable] with `suspendCancellableCoroutine`,
 * canceling continuation if calling coroutine's Job has been cancelled.
 */
internal suspend inline fun <reified E, reified V, reified T : Expected<E, V>> suspendMapboxCancellableCoroutine(
  crossinline block: (Continuation<T>) -> Cancelable
): T {
  return suspendCancellableCoroutine { continuation ->
    val cancelable = block(continuation)
    continuation.invokeOnCancellation { cancelable.cancel() }
  }
}