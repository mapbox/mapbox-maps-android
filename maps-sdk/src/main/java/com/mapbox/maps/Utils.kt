package com.mapbox.maps

import android.content.Context
import android.util.TypedValue
import com.mapbox.bindgen.Expected
import com.mapbox.common.Cancelable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
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

@Suppress("UNCHECKED_CAST")
internal fun <T : Number> T.toDP(context: Context): T {
  return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
  ) as T
}

/**
 * Executes a potentially blocking system call off the main thread with timeout and error handling.
 * This utility helps prevent ANRs caused by blocking IPC calls to system services.
 *
 * Uses [Dispatchers.Default] as system calls are CPU-bound operations involving IPC to system services.
 *
 * @param timeoutMs Timeout in milliseconds for the operation (default: 5000ms)
 * @param fallback Fallback value to return if the operation fails or times out
 * @param logTag Tag for logging errors (default: "SystemCall")
 * @param dispatcher The dispatcher to use for the operation (default: Dispatchers.Default)
 * @param operation The blocking operation to execute
 * @return The result of the operation or the fallback value
 */
internal suspend fun <T> safeSystemCall(
  timeoutMs: Long = 5000L,
  fallback: T,
  logTag: String = "SystemCall",
  dispatcher: CoroutineDispatcher = Dispatchers.Default,
  operation: suspend () -> T
): T {
  return try {
    withTimeoutOrNull(timeoutMs) {
      withContext(dispatcher) {
        operation()
      }
    } ?: run {
      logW(logTag, "System call timed out after ${timeoutMs}ms, using fallback")
      fallback
    }
  } catch (e: Exception) {
    logE(logTag, "System call failed: ${e.message}, using fallback")
    fallback
  }
}

/**
 * Executes a potentially blocking system call off the main thread and posts the result back to the main thread.
 * This is useful for updating UI components after retrieving system information.
 *
 * @param timeoutMs Timeout in milliseconds for the operation (default: 5000ms)
 * @param fallback Fallback value to return if the operation fails or times out
 * @param logTag Tag for logging errors (default: "SystemCall")
 * @param dispatcher The dispatcher to use for the operation (default: Dispatchers.Default)
 * @param mainDispatcher The dispatcher to use for the callback (default: Dispatchers.Main)
 * @param operation The blocking operation to execute
 * @param onResult Callback to handle the result on the main thread
 */
internal suspend fun <T> safeSystemCallWithCallback(
  timeoutMs: Long = 5000L,
  fallback: T,
  logTag: String = "SystemCall",
  dispatcher: CoroutineDispatcher = Dispatchers.Default,
  mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
  operation: suspend () -> T,
  onResult: (T) -> Unit
) {
  val result = safeSystemCall(timeoutMs, fallback, logTag, dispatcher, operation)
  withContext(mainDispatcher) {
    onResult(result)
  }
}