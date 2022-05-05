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
  view: View,
  localLifecycleOwner: LifecycleOwner,
  hostingLifecycleOwner: LifecycleOwner,
) : LifecycleRegistry(localLifecycleOwner) {

  private var isAttached = view.isAttachedToWindow

  private val hostingLifecycleObserver = LifecycleEventObserver { _, event ->
    val isAtLeastCreated = currentState.isAtLeast(State.CREATED)
    if (isAttached || (isAtLeastCreated && event == Event.ON_DESTROY)) {
      handleLifecycleEvent(event)
    }
  }

  private val attachStateChangeListener = object : View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(p0: View?) {
      currentState = hostingLifecycleOwner.lifecycle.currentState
      isAttached = true
    }

    override fun onViewDetachedFromWindow(p0: View?) {
      isAttached = false
      if (hostingLifecycleOwner.lifecycle.currentState.isAtLeast(State.STARTED)) {
        currentState = State.CREATED
      }
    }
  }

  init {
    hostingLifecycleOwner.lifecycle.addObserver(hostingLifecycleObserver)
    view.addOnAttachStateChangeListener(attachStateChangeListener)
  }
}