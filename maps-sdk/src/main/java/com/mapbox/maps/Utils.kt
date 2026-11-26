package com.mapbox.maps

import android.content.Context
import android.os.Looper
import android.os.SystemClock
import android.util.TypedValue
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.mapbox.bindgen.Expected
import com.mapbox.common.Cancelable
import com.mapbox.common.MapboxThread
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
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
 * Executes a potentially blocking system call off the main thread, preventing ANRs.
 *
 * The call runs on [dispatcher] (default [Dispatchers.IO]). Note: if the underlying
 * operation is a blocking Binder IPC (e.g. [android.view.Display.getRefreshRate]), its
 * duration cannot be reliably bounded — the native Binder layer ignores [Thread.interrupt],
 * so the dispatcher thread may stay blocked until the system server replies. The main thread
 * is never affected.
 *
 * @param fallback Value returned if the operation throws.
 * @param logTag Tag for error logging.
 * @param dispatcher Dispatcher for the blocking operation (default: [Dispatchers.IO]).
 * @param operation The blocking operation to execute.
 * @return The result of [operation], or [fallback] if it throws.
 */
internal suspend fun <T> safeBinderCall(
  fallback: T,
  logTag: String = "SystemCall",
  dispatcher: CoroutineDispatcher = Dispatchers.IO,
  operation: suspend () -> T,
): T = try {
  withContext(dispatcher + CoroutineName("safeBinderCall")) { operation() }
} catch (e: CancellationException) {
  throw e
} catch (e: Exception) {
  logE(logTag, "System call failed: ${e.message}, using fallback")
  fallback
}

/**
 * Runs [block] on this [MapboxThread] and returns its result once it has finished.
 *
 * When called on this thread, e.g. from the main thread with the default main-thread provider, it
 * runs [block] inline. Otherwise, it dispatches [block] to this thread and blocks the caller until
 * it completes. Running inline matters with the default provider: when a test replaces
 * `Dispatchers.Main` with a queueing test dispatcher such as `StandardTestDispatcher`, a dispatch
 * would wait for a scheduler the blocked test can't advance.
 *
 * The Mapbox thread may be shared by several maps, so [block] can be queued behind other work.
 * A warning is logged when [block] starts more than [SLOW_MAPBOX_THREAD_WAIT_MS] after it was
 * dispatched.
 */
@OptIn(com.mapbox.annotation.MapboxExperimental::class)
internal inline fun <T> MapboxThread.runAndWait(crossinline block: () -> T): T =
  if (Looper.myLooper() == handler.looper) {
    block()
  } else {
    val dispatchedAt = SystemClock.uptimeMillis()
    runBlocking(dispatcher.immediate) {
      warnIfSlowMapboxThreadWait(SystemClock.uptimeMillis() - dispatchedAt)
      block()
    }
  }

@VisibleForTesting(otherwise = PRIVATE)
internal const val MAPBOX_THREAD_TAG = "MapboxThread"

@VisibleForTesting(otherwise = PRIVATE)
internal const val SLOW_MAPBOX_THREAD_WAIT_MS = 100L

internal fun warnIfSlowMapboxThreadWait(waitedMs: Long) {
  if (waitedMs > SLOW_MAPBOX_THREAD_WAIT_MS) {
    logW(
      MAPBOX_THREAD_TAG,
      "Waited $waitedMs ms for the Mapbox thread before running blocking map work. " +
        "Other work queued on the Mapbox thread delays map creation and destruction."
    )
  }
}