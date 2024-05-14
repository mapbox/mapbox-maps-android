package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.extension.compose.style.internal.DispatchQueue
import com.mapbox.maps.extension.compose.style.internal.PausingDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * The abstract [StateDispatcher] is a node that holds a [PausingDispatcher], where implementations can
 * dispatch within the pausing queue and decide on when the node is ready for dispatching.
 */
internal class StateDispatcher {
  /**
   * [PausingDispatcher] used to enqueue jobs ([dispatch]) to be run once the node is ready ([resumeDispatching]).
   * Initially is paused.
   */
  private val dispatcher = PausingDispatcher().also { it.dispatchQueue.pause() }

  /**
   * Subclasses must call this method once the node is in the right state to run any pending job
   */
  internal fun resumeDispatching() {
    dispatcher.dispatchQueue.resume()
  }

  /**
   * Subclasses must call this method once the node transitions to a state where [PausingDispatcher] can't be run
   * with the [DispatchQueue].
   *
   * After this point of time [dispatch] block will be invoked immediately on [Dispatchers.Main].
   *
   * By default it will be called when [MapNode.onRemoved] is invoked.
   */
  internal fun finishDispatching() {
    dispatcher.dispatchQueue.finish()
  }

  /**
   * Pause the [PausingDispatcher.dispatchQueue], so that [dispatch] block will be enqueued until
   * [resumeDispatching] is invoked.
   */
  internal fun pauseDispatching() {
    dispatcher.dispatchQueue.pause()
  }

  // Execute code only when the dispatcher is resumed.
  internal suspend fun <T> dispatch(
    context: CoroutineContext = Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> T
  ): T = withContext(context) {
    val job = coroutineContext[Job] ?: error("dispatch method should have a parent job")
    if (!job.isActive) {
      job.cancel()
      dispatcher.dispatchQueue.finish()
    }
    // Following line will suspend until the dispatcher is ready
    withContext(dispatcher, block)
  }
}