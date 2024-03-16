package com.mapbox.maps.extension.compose.style.internal

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import kotlinx.coroutines.Dispatchers
import java.util.ArrayDeque
import java.util.Queue
import kotlin.coroutines.CoroutineContext

/**
 * Helper class for [PausingDispatcher] that tracks runnables which are enqueued to the dispatcher
 * and also calls back the [PausingDispatcher] when the runnable should run.
 *
 * Originally ported from [androidx.lifecycle.DispatchQueue], this class helps to dispatch style
 * related operations in an async manner.
 */
internal class DispatchQueue {
  private var paused: Boolean = true
  private var finished: Boolean = false
  private var isDraining: Boolean = false

  private val queue: Queue<Runnable> = ArrayDeque()

  @MainThread
  fun pause() {
    paused = true
  }

  @MainThread
  fun resume() {
    if (!paused) {
      return
    }
    check(!finished) {
      "Cannot resume a finished dispatcher"
    }
    paused = false
    drainQueue()
  }

  @MainThread
  fun finish() {
    finished = true
    drainQueue()
  }

  @MainThread
  fun drainQueue() {
    if (isDraining) {
      // Block re-entrant calls to avoid deep stacks
      return
    }
    try {
      isDraining = true
      while (queue.isNotEmpty()) {
        if (!canRun()) {
          break
        }
        queue.poll()?.run()
      }
    } finally {
      isDraining = false
    }
  }

  @MainThread
  fun canRun() = finished || !paused

  @AnyThread
  @SuppressLint("WrongThread") // false negative, we are checking the thread
  fun dispatchAndEnqueue(context: CoroutineContext, runnable: Runnable) {
    with(Dispatchers.Main.immediate) {
      // We would like to run synchronously if the dispatchAndEnqueue is invoked after the
      // dispatch queue is already resumed, so we avoid dispatch the runnable in the queue,
      // which could cause unexpected dispatch order.
      if (isDispatchNeeded(context) || canRun()) {
        dispatch(context) { enqueue(runnable) }
      } else {
        enqueue(runnable)
      }
    }
  }

  @MainThread
  private fun enqueue(runnable: Runnable) {
    check(queue.offer(runnable)) {
      "cannot enqueue any more runnables"
    }
    drainQueue()
  }
}