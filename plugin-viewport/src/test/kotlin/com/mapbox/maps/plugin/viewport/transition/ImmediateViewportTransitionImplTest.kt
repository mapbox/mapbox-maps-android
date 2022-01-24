package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPluginImpl
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImmediateViewportTransitionImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>(relaxed = true)
  private val cameraPlugin = mockk<CameraAnimationsPlugin>(relaxed = true)
  private val targetState = mockk<ViewportState>()
  private val completionListener = mockk<CompletionListener>()
  private val dataObserverSlot = slot<ViewportStateDataObserver>()
  private val cancelable = mockk<Cancelable>()
  private val cameraOptions = mockk<CameraOptions>(relaxed = true)
  private lateinit var immediateTransition: ImmediateViewportTransition

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { cancelable.cancel() } just runs
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    immediateTransition = ImmediateViewportTransition(delegateProvider)
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkAll()
  }

  @Test
  fun testRun() {
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    immediateTransition.run(targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state only get the first data point and returned false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))

    verify {
      cameraPlugin.easeTo(
        cameraOptions,
        MapAnimationOptions.mapAnimationOptions {
          duration(0)
          owner(ViewportPluginImpl.VIEWPORT_CAMERA_OWNER)
        }
      )
    }
    verify { completionListener.onComplete(true) }
  }
}