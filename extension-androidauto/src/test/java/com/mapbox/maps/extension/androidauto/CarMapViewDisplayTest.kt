@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import android.app.Activity
import android.app.Presentation
import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.os.Looper
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.car.app.SurfaceContainer
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.ContextMode
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class CarMapViewDisplayTest {

  private val decorView = FrameLayout(ApplicationProvider.getApplicationContext())
  private val virtualDisplay = mockk<VirtualDisplay>(relaxed = true)
  private val mapView = mockk<MapView>(relaxed = true)
  private val presentation = mockk<Presentation>(relaxed = true) {
    every { window!!.decorView } returns this@CarMapViewDisplayTest.decorView
  }
  private val context = mockk<Context>()
  private val displayManager = mockk<DisplayManager>()
  private val surface = mockk<Surface>()
  private val surfaceContainer = mockk<SurfaceContainer> {
    every { surface } returns this@CarMapViewDisplayTest.surface
    every { width } returns 800
    every { height } returns 400
    every { dpi } returns 160
  }
  private val mapInitOptions = MapInitOptions(context, mapOptions = MapOptions.Builder().pixelRatio(1f).build())

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } returns Unit
    every { logI(any(), any()) } returns Unit
    mockkObject(MapSurfaceProvider)
    every { context.getSystemService(DisplayManager::class.java) } returns displayManager
    every { displayManager.createVirtualDisplay(any(), any(), any(), any(), any(), any()) } returns virtualDisplay
    every { MapSurfaceProvider.createPresentation(context, any()) } returns presentation
    every { presentation.context } returns decorView.context
    every { MapSurfaceProvider.createMapView(any(), any()) } returns mapView
    every { mapView.mapboxMap.isValid() } returns true
  }

  @After
  fun teardown() {
    unmockkAll()
  }

  @Test
  fun `presentation descendants inherit all three view tree owners before show`() {
    val child = View(decorView.context)
    val container = FrameLayout(decorView.context)
    decorView.addView(container)
    container.addView(child)
    every { presentation.show() } answers {
      val owner = ViewTreeLifecycleOwner.get(child)
      assertEquals(Lifecycle.State.STARTED, owner!!.lifecycle.currentState)
      assertSame(owner, child.findViewTreeSavedStateRegistryOwner())
      assertSame(owner, ViewTreeViewModelStoreOwner.get(child))
      assertNull(child.findViewTreeSavedStateRegistryOwner()!!.savedStateRegistry.consumeRestoredStateForKey("test"))
    }

    val display = CarMapViewDisplay(presentation, virtualDisplay, mapView).apply { show() }

    assertSame(display, ViewTreeLifecycleOwner.get(decorView))
    assertSame(display, decorView.findViewTreeSavedStateRegistryOwner())
    assertSame(display, ViewTreeViewModelStoreOwner.get(decorView))
    verifyOrder {
      presentation.setContentView(mapView)
      presentation.show()
    }
    display.destroy()
  }

  @Test
  fun `compose content can create a composition and disposes it when the display is destroyed`() {
    val activityController = Robolectric.buildActivity(Activity::class.java).setup().visible()
    try {
      val activity = activityController.get()
      val container = FrameLayout(activity)
      val composeView = ComposeView(activity)
      decorView.addView(container)
      container.addView(composeView)
      every { presentation.show() } answers {
        activity.setContentView(decorView)
        composeView.createComposition()
      }
      every { presentation.dismiss() } answers {
        activity.setContentView(FrameLayout(activity))
      }

      val display = CarMapViewDisplay(presentation, virtualDisplay, mapView).apply { show() }
      shadowOf(Looper.getMainLooper()).idle()

      assertTrue(composeView.hasComposition)
      display.destroy()
      shadowOf(Looper.getMainLooper()).idle()
      assertFalse(composeView.hasComposition)
    } finally {
      activityController.pause().stop().destroy()
    }
  }

  @Test
  fun `destroy dispatches lifecycle destruction and clears view models before releasing display`() {
    val display = CarMapViewDisplay(presentation, virtualDisplay, mapView).apply { show() }
    val events = mutableListOf<Lifecycle.Event>()
    display.lifecycle.addObserver(LifecycleEventObserver { _, event -> events.add(event) })
    val viewModel = ViewModelProvider(display, ViewModelProvider.NewInstanceFactory())[TestViewModel::class.java]
    every { virtualDisplay.release() } answers {
      assertEquals(Lifecycle.State.DESTROYED, display.lifecycle.currentState)
      assertTrue(viewModel.cleared)
    }

    display.destroy()

    assertEquals(
      listOf(Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_START, Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY),
      events
    )
    verifyOrder {
      presentation.dismiss()
      virtualDisplay.release()
    }
  }

  @Test
  fun `destroy cleans up a map that never attached to the window`() {
    val display = CarMapViewDisplay(presentation, virtualDisplay, mapView).apply { show() }

    display.destroy()

    verifyOrder {
      presentation.dismiss()
      mapView.onDestroy()
      virtualDisplay.release()
    }
    verify(exactly = 1) { mapView.onDestroy() }
  }

  @Test
  fun `destroy does not clean up a map twice when its lifecycle observer handles destruction`() {
    val display = CarMapViewDisplay(presentation, virtualDisplay, mapView).apply { show() }
    display.lifecycle.addObserver(
      LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
          mapView.onDestroy()
          every { mapView.mapboxMap.isValid() } returns false
        }
      }
    )

    display.destroy()

    verify(exactly = 1) { mapView.onDestroy() }
    verify(exactly = 1) { virtualDisplay.release() }
  }

  @Test
  fun `create shows the presentation without releasing its resources`() {
    val display = CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)!!

    assertSame(mapView, display.mapView)
    assertEquals(Lifecycle.State.STARTED, display.lifecycle.currentState)
    verifyOrder {
      MapSurfaceProvider.createPresentation(context, virtualDisplay.display)
      MapSurfaceProvider.createMapView(decorView.context, any())
      presentation.setContentView(mapView)
      presentation.show()
    }
    verify(exactly = 0) { presentation.dismiss() }
    verify(exactly = 0) { mapView.onDestroy() }
    verify(exactly = 0) { virtualDisplay.release() }
    display.destroy()
  }

  @Test
  fun `create and destroy log the virtual display id and the car surface`() {
    every { virtualDisplay.display.displayId } returns 42
    val display = CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)!!

    verify {
      logI(
        "CarMapViewDisplay",
        match { it.startsWith("Created virtual display id=42") && it.contains(surfaceContainer.toString()) }
      )
    }

    display.destroy()

    verifyOrder {
      logI(
        "CarMapViewDisplay",
        match { it.startsWith("Destroying virtual display id=42") && it.contains(surfaceContainer.toString()) }
      )
      virtualDisplay.release()
    }
  }

  @Test
  fun `window failure log includes the virtual display id`() {
    every { virtualDisplay.display.displayId } returns 42
    every { MapSurfaceProvider.createPresentation(context, any()) } throws
      WindowManager.InvalidDisplayException("display removed")

    assertNull(CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions))

    verify { logE("CarMapViewDisplay", match { it.contains("virtual display id=42") }) }
  }

  @Test
  fun `create matches map pixel ratio to display density without changing other options`() {
    val originalMapOptions = MapOptions.Builder()
      .pixelRatio(3f)
      .contextMode(ContextMode.SHARED)
      .build()
    val originalOptions = mapInitOptions.copy(mapOptions = originalMapOptions, mapName = "car-map")

    for (dpi in listOf(160, 640)) {
      val displayContext = decorView.context.createConfigurationContext(
        Configuration().apply { densityDpi = dpi }
      )
      val expectedPixelRatio = dpi / 160f
      assertEquals(expectedPixelRatio, displayContext.resources.displayMetrics.density)
      every { presentation.context } returns displayContext
      every { surfaceContainer.dpi } returns dpi
      val displayOptions = slot<MapInitOptions>()

      val display = CarMapViewDisplay.create(context, surfaceContainer, originalOptions)!!

      verify { MapSurfaceProvider.createMapView(displayContext, capture(displayOptions)) }
      assertEquals(expectedPixelRatio, displayOptions.captured.mapOptions.pixelRatio)
      assertEquals(
        originalOptions.copy(
          context = displayContext,
          mapOptions = originalMapOptions.toBuilder().pixelRatio(expectedPixelRatio).build()
        ),
        displayOptions.captured
      )
      assertSame(originalMapOptions, originalOptions.mapOptions)
      assertSame(context, originalOptions.context)
      assertEquals(3f, originalOptions.mapOptions.pixelRatio)
      display.destroy()
    }
  }

  @Test
  fun `presentation construction failure releases the display and preserves the exception`() {
    val failure = IllegalStateException("presentation construction failed")
    every { MapSurfaceProvider.createPresentation(context, any()) } throws failure

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    verify(exactly = 1) { virtualDisplay.release() }
    verify(exactly = 0) { MapSurfaceProvider.createMapView(any(), any()) }
  }

  @Test
  fun `map construction failure dismisses the presentation and releases the display`() {
    val failure = IllegalArgumentException("map construction failed")
    every { MapSurfaceProvider.createMapView(any(), any()) } throws failure

    assertSame(
      failure,
      assertThrows(IllegalArgumentException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    verifyOrder {
      presentation.dismiss()
      virtualDisplay.release()
    }
    verify(exactly = 0) { mapView.onDestroy() }
  }

  @Test
  fun `failure before the lifecycle starts still destroys the map and releases the display`() {
    every { presentation.window } returns null

    assertThrows(IllegalStateException::class.java) {
      CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
    }

    verifyOrder {
      presentation.dismiss()
      mapView.onDestroy()
      virtualDisplay.release()
    }
  }

  @Test
  fun `unexpected show failure clears owners and map resources and preserves cleanup failures`() {
    val failure = IllegalStateException("show failed")
    val cleanupFailure = IllegalStateException("dismiss failed")
    lateinit var owner: CarMapViewDisplay
    lateinit var viewModel: TestViewModel
    every { presentation.show() } answers {
      owner = ViewTreeLifecycleOwner.get(decorView) as CarMapViewDisplay
      viewModel = ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory())[TestViewModel::class.java]
      throw failure
    }
    every { presentation.dismiss() } throws cleanupFailure
    every { virtualDisplay.release() } answers {
      assertEquals(Lifecycle.State.DESTROYED, owner.lifecycle.currentState)
      assertTrue(viewModel.cleared)
    }

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    assertEquals(listOf(cleanupFailure), failure.suppressed.toList())
    verifyOrder {
      presentation.dismiss()
      mapView.onDestroy()
      virtualDisplay.release()
    }
  }

  @Test
  fun `cleanup does not destroy a map already destroyed by its lifecycle observer`() {
    every { presentation.show() } answers {
      val owner = ViewTreeLifecycleOwner.get(decorView)!!
      owner.lifecycle.addObserver(
        LifecycleEventObserver { _, event ->
          if (event == Lifecycle.Event.ON_DESTROY) {
            mapView.onDestroy()
            every { mapView.mapboxMap.isValid() } returns false
          }
        }
      )
      throw WindowManager.InvalidDisplayException("display removed")
    }

    assertNull(CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions))

    verify(exactly = 1) { mapView.onDestroy() }
    verify(exactly = 1) { virtualDisplay.release() }
  }

  @Test
  fun `invalid display during show is logged after releasing resources`() {
    assertExpectedWindowFailure(WindowManager.InvalidDisplayException("display removed"))
  }

  @Test
  fun `invalid window token during show is logged after releasing resources`() {
    assertExpectedWindowFailure(WindowManager.BadTokenException("window token rejected"))
  }

  @Test
  fun `invalid display during presentation construction releases the virtual display and logs the failure`() {
    every { MapSurfaceProvider.createPresentation(context, any()) } throws
      WindowManager.InvalidDisplayException("display removed")

    assertNull(CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions))

    verifyOrder {
      virtualDisplay.release()
      logE("CarMapViewDisplay", match { it.contains("display removed") })
    }
  }

  @Test
  fun `virtual display allocation failure is logged`() {
    every { displayManager.createVirtualDisplay(any(), any(), any(), any(), any(), any()) } returns null

    assertNull(CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions))

    verify { logE("CarMapViewDisplay", "Failed to allocate map view virtual display") }
    verify(exactly = 0) { MapSurfaceProvider.createPresentation(any(), any()) }
  }

  @Test
  fun `unexpected allocation exception is preserved`() {
    val failure = SecurityException("display permission denied")
    every { displayManager.createVirtualDisplay(any(), any(), any(), any(), any(), any()) } throws failure

    assertSame(
      failure,
      assertThrows(SecurityException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    verify(exactly = 0) { MapSurfaceProvider.createPresentation(any(), any()) }
    verify(exactly = 0) { virtualDisplay.release() }
  }

  @Test
  fun `display release failure does not replace the original creation exception`() {
    val failure = IllegalStateException("map construction failed")
    val cleanupFailure = IllegalStateException("display release failed")
    every { MapSurfaceProvider.createMapView(any(), any()) } throws failure
    every { virtualDisplay.release() } throws cleanupFailure

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    assertEquals(listOf(cleanupFailure), failure.suppressed.toList())
  }

  @Test
  fun `cleanup throwing the original exception does not interrupt remaining cleanup`() {
    val failure = IllegalStateException("presentation failed")
    every { presentation.show() } throws failure
    every { presentation.dismiss() } throws failure

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)
      }
    )

    assertTrue(failure.suppressed.isEmpty())
    verify(exactly = 1) { mapView.onDestroy() }
    verify(exactly = 1) { virtualDisplay.release() }
  }

  private fun assertExpectedWindowFailure(failure: RuntimeException) {
    every { presentation.show() } throws failure

    assertNull(CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions))

    verifyOrder {
      presentation.dismiss()
      mapView.onDestroy()
      virtualDisplay.release()
      logE("CarMapViewDisplay", match { it.contains(failure.message!!) })
    }
  }

  class TestViewModel : ViewModel() {
    var cleared = false
      private set

    override fun onCleared() {
      cleared = true
    }
  }
}