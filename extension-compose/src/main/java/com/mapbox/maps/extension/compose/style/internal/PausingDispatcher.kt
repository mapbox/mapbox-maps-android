package com.mapbox.maps.extension.compose.style.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A [CoroutineDispatcher] implementation that maintains a dispatch queue to be able to pause
 * execution of coroutines.
 *
 * Originally ported from [androidx.lifecycle.PausingDispatcher], this class helps to dispatch style
 * related operations in an async manner.
 *
 * @see [DispatchQueue] for details.
 */
internal class PausingDispatcher : CoroutineDispatcher() {
  /**
   * helper class to maintain state and enqueued continuations.
   */
  @JvmField
  internal val dispatchQueue = DispatchQueue()

  override fun isDispatchNeeded(context: CoroutineContext): Boolean {
    if (Dispatchers.Main.immediate.isDispatchNeeded(context)) {
      return true
    }
    // It's safe to call dispatchQueue.canRun() here because
    // Dispatchers.Main.immediate.isDispatchNeeded returns true if we're not on the main thread
    // If the queue is paused right now we need to dispatch so that the block is added to the
    // the queue
    return !dispatchQueue.canRun()
  }

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatchQueue.dispatchAndEnqueue(context, block)
  }
}