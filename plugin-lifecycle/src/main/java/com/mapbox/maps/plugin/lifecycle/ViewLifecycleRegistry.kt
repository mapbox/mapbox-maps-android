package com.mapbox.maps.plugin.lifecycle

import android.view.View
import androidx.annotation.UiThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * A [LifecycleRegistry] that merges the [hostingLifecycleOwner]'s [Lifecycle] (of an Activity or Fragment)
 * with the [View]'s drawing surface lifecycle.
 *
 * This lifecycle is useful for continuously running tasks that obtain the data and draw it in the [View].
 *
 * In general, this lifecycle reflects the [hostingLifecycleOwner]'s [Lifecycle] whenever the `View` is attached
 * but whenever the `View` is detached and we want to stop the coroutines tied to the lifecycle's scope,
 * this lifecycle will transition to [Lifecycle.State.CREATED] (and invoke [Lifecycle.Event.ON_STOP])
 * even if the hosting Activity or Fragment is still running.
 *
 * This lifecycle reaches [Lifecycle.State.DESTROYED] only when the [hostingLifecycleOwner] is destroyed.
 */
@UiThread
internal class ViewLifecycleRegistry(
  private val view: View,
  private val hostingLifecycleOwner: LifecycleOwner,
) : LifecycleOwner {

  private val viewLifecycleRegistry = LifecycleRegistry(this)
  override fun getLifecycle() = viewLifecycleRegistry

  private var isAttached = view.isAttachedToWindow

  private val hostingLifecycleObserver = LifecycleEventObserver { _, event ->
    val isAtLeastCreated = viewLifecycleRegistry.currentState.isAtLeast(Lifecycle.State.CREATED)
    if (isAttached || (isAtLeastCreated && event == Lifecycle.Event.ON_DESTROY)) {
      viewLifecycleRegistry.handleLifecycleEvent(event)
    }
  }

  private val attachStateChangeListener = object : View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(p0: View?) {
      viewLifecycleRegistry.currentState = hostingLifecycleOwner.lifecycle.currentState
      isAttached = true
    }

    override fun onViewDetachedFromWindow(p0: View?) {
      isAttached = false
      if (hostingLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        viewLifecycleRegistry.currentState = Lifecycle.State.CREATED
      }
    }
  }

  fun cleanUp() {
    hostingLifecycleOwner.lifecycle.removeObserver(hostingLifecycleObserver)
    view.removeOnAttachStateChangeListener(attachStateChangeListener)
  }

  init {
    hostingLifecycleOwner.lifecycle.addObserver(hostingLifecycleObserver)
    view.addOnAttachStateChangeListener(attachStateChangeListener)
  }
}