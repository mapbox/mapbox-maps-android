package com.mapbox.maps.extension.androidauto.testing

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestLifecycleOwner : LifecycleOwner {
  private val lifecycleRegistry = LifecycleRegistry(this)
    .also { it.currentState = Lifecycle.State.INITIALIZED }

  override fun getLifecycle(): Lifecycle = lifecycleRegistry

  fun moveToState(state: Lifecycle.State) {
    lifecycleRegistry.currentState = state
  }
}