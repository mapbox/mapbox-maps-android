package com.mapbox.maps.extension.compose.style.internal

import androidx.annotation.CallSuper
import com.mapbox.maps.extension.compose.internal.MapNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * The abstract [PausingDispatcherNode] is a node that holds a [PausingDispatcher], where implementations can
 * dispatch within the pausing queue and decide on when the node is ready for dispatching.
 */
internal abstract class PausingDispatcherNode : MapNode() {
  /**
   * [PausingDispatcher] used to enqueue jobs ([whenNodeReady]) to be run once the node is ready ([onNodeReady]).
   * Initially is paused.
   *
   * It will automatically transition to finish when the node is removed ([onRemoved])
   */
  private val dispatcher = PausingDispatcher().also { it.dispatchQueue.pause() }

  /**
   * Subclasses must call this method once the node is in the right state to run any pending job
   */
  protected fun onNodeReady() {
    dispatcher.dispatchQueue.resume()
  }

  /**
   * Subclasses must call this method once the node transitions to a state where [PausingDispatcher] can't be run
   * with the [DispatchQueue].
   *
   * After this point of time [whenNodeReady] block will be invoked immediately on [Dispatchers.Main].
   *
   * By default it will be called when [MapNode.onRemoved] is invoked.
   */
  protected fun onNodeFinish() {
    dispatcher.dispatchQueue.finish()
  }

  /**
   * Subclasses can optionally call this method to pause the [PausingDispatcher.dispatchQueue], so that
   * [whenNodeReady] block will be enqueued until [onNodeReady] is invoked.
   */
  protected fun onNodePaused() {
    dispatcher.dispatchQueue.pause()
  }

  @CallSuper
  override fun onRemoved(parent: MapNode) {
    super.onRemoved(parent)
    onNodeFinish()
  }

  // Execute code only when the node is prepared, this is useful in case of runtime added layers and sources,
  // as the layer/source add depends on the style load event, and property setters has to happen after the
  // layer/source is added to the style.
  protected suspend fun <T> whenNodeReady(
    context: CoroutineContext = Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> T
  ): T = withContext(context) {
    val job = coroutineContext[Job] ?: error("when[State] methods should have a parent job")
    if (!job.isActive) {
      job.cancel()
      dispatcher.dispatchQueue.finish()
    }
    // Following line will suspend until the dispatcher is ready
    withContext(dispatcher, block)
  }
}