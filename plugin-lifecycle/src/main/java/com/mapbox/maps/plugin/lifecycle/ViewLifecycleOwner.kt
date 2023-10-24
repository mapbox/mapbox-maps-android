package com.mapbox.maps.plugin.lifecycle

import android.view.View
import androidx.annotation.RestrictTo
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewTreeLifecycleOwner
import java.lang.ref.WeakReference

/**
 * A [LifecycleOwner] that merges the hosting lifecycle owner's [Lifecycle] (of an Activity or Fragment)
 * with the [View]'s drawing surface lifecycle.
 *
 * In general, this lifecycle reflects the hosting lifecycle owner's [Lifecycle] whenever the `View` is attached
 * but whenever the `View` is detached and we want to stop rendering the MapView, this lifecycle will transition
 * to [Lifecycle.State.CREATED] (and invoke [Lifecycle.Event.ON_STOP]) even if the hosting Activity or Fragment
 * is still running.
 *
 * This lifecycle reaches [Lifecycle.State.DESTROYED] only when the hosting lifecycle owner is destroyed.
 */
@UiThread
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewLifecycleOwner(
  view: View,
) : LifecycleOwner {
  private val viewWeakReference = WeakReference(view)
  @VisibleForTesting(otherwise = PRIVATE)
  internal val viewLifecycleRegistry = LifecycleRegistry(this)

  override fun getLifecycle() = viewLifecycleRegistry

  private var isAttached = false
  private var hostingLifecycleOwner: LifecycleOwner? = null

  private val hostingLifecycleObserver = LifecycleEventObserver { _, event ->
    val isAtLeastCreated = viewLifecycleRegistry.currentState.isAtLeast(Lifecycle.State.CREATED)
    if (isAttached || (isAtLeastCreated && event == Lifecycle.Event.ON_DESTROY)) {
      viewLifecycleRegistry.handleLifecycleEvent(event)
    }
  }

  private val attachStateChangeListener = object : View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(view: View) {
      doOnAttached(view)
    }

    override fun onViewDetachedFromWindow(view: View) {
      doOnDetached()
    }
  }

  init {
    view.addOnAttachStateChangeListener(attachStateChangeListener)
    if (view.isAttachedToWindow) {
      doOnAttached(view)
    }
  }

  private fun doOnAttached(view: View) {
    if (isAttached) return

    hostingLifecycleOwner?.lifecycle?.removeObserver(hostingLifecycleObserver)

    val hostingLifecycleOwner = ViewTreeLifecycleOwner.get(view)
      ?: throw IllegalStateException(
        "Please ensure that the hosting activity/fragment is a valid LifecycleOwner"
      )
    viewLifecycleRegistry.currentState = hostingLifecycleOwner.lifecycle.currentState
    hostingLifecycleOwner.lifecycle.addObserver(hostingLifecycleObserver)
    this@ViewLifecycleOwner.hostingLifecycleOwner = hostingLifecycleOwner
    isAttached = true
  }

  private fun doOnDetached() {
    if (!isAttached) return

    isAttached = false
    val hostingLifecycleOwner: LifecycleOwner = checkNotNull(hostingLifecycleOwner)
    if (hostingLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
      viewLifecycleRegistry.currentState = Lifecycle.State.CREATED
    }
  }

  fun cleanUp() {
    hostingLifecycleOwner?.lifecycle?.removeObserver(hostingLifecycleObserver)
    viewWeakReference.get()?.removeOnAttachStateChangeListener(attachStateChangeListener)
  }
}