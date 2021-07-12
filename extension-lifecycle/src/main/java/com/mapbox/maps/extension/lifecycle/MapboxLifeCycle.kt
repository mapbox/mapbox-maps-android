package com.mapbox.maps.extension.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mapbox.maps.MapboxLifeCycleObserver

/**
 * Attach a [MapboxLifeCycleObserver] to a [LifecycleOwner]。 The `onStart`/`onStop`/`onDestroy` method of MapboxLifeCycleObserver will be invoked
 * if the LifecycleOwner is running through these life cycles.
 *
 * @param observer instance of [MapboxLifeCycleObserver], usually a [MapView]
 * @param owner the life owner, usually an Activity or Fragment
 */
fun attachLifeCycle(
  observer: MapboxLifeCycleObserver,
  owner: LifecycleOwner
) {
  owner.lifecycle.addObserver(object : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
      observer.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
      observer.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
      observer.onDestroy()
    }
  })
}