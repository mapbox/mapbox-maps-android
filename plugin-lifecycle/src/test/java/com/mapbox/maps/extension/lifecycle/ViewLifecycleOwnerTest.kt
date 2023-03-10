package com.mapbox.maps.extension.lifecycle

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import com.mapbox.maps.plugin.lifecycle.ViewLifecycleOwner
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewLifecycleOwnerTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var hostingLifecycleOwner: TestLifecycleOwner
  private var view = mockk<View>()
  private val attachStateSlot = slot<View.OnAttachStateChangeListener>()
  private val mainThreadSurrogate = newSingleThreadContext("UI thread")

  @Before
  fun setup() {
    // Needed as we don't have access to Dispatchers.Main in unit testing
    Dispatchers.setMain(mainThreadSurrogate)
    hostingLifecycleOwner = generateHostingLifecycleOwner()
    mockkStatic(ViewTreeLifecycleOwner::class)
    every { ViewTreeLifecycleOwner.get(view) } returns hostingLifecycleOwner
    every { view.addOnAttachStateChangeListener(capture(attachStateSlot)) } just Runs
    every { view.removeOnAttachStateChangeListener(any()) } just Runs
    every { view.isAttachedToWindow } returns false
  }

  @Test
  fun `mirrors hosting lifecycles state when attached`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    attach()
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    detach()

    verifyOrder {
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_CREATE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_START)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_RESUME)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_PAUSE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_STOP)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_DESTROY)
    }
  }

  @Test
  fun `stops when detached and hosting lifecycle resumed`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    attach()
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    detach()

    verifyOrder {
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_CREATE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_START)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_RESUME)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_PAUSE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_STOP)
    }
  }

  @Test
  fun `stopped when detached and hosting lifecycle stopped`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    attach()
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    detach()

    verifyOrder {
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_CREATE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_START)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_RESUME)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_PAUSE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_STOP)
    }
  }

  @Test
  fun `hosting lifecycle destroyed but view never attached, remain initialized only`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val viewLifecycleRegistry = localLifecycleOwner.viewLifecycleRegistry
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    viewLifecycleRegistry.addObserver(lifecycleObserver)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    verify(exactly = 0) {
      lifecycleObserver.onStateChanged(any(), any())
    }
    Assert.assertTrue(localLifecycleOwner.lifecycle.currentState == Lifecycle.State.INITIALIZED)
  }

  @Test
  fun `destroyed state delivered even if view is detached`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    attach()
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    detach()
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    verifyOrder {
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_CREATE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_START)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_RESUME)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_PAUSE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_STOP)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_DESTROY)
    }
  }

  @Test
  fun `detaching view when hosting lifecycle is only initialized has no effect`() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    attach()
    detach()

    verify(exactly = 0) {
      lifecycleObserver.onStateChanged(any(), any())
    }
    Assert.assertTrue(localLifecycleOwner.lifecycle.currentState == Lifecycle.State.INITIALIZED)
  }

  @Test
  fun `view is already attached on lifecycle creation, handle state correctly`() {
    every { view.isAttachedToWindow } returns true
    val localLifecycleOwner = ViewLifecycleOwner(view)
    val lifecycleObserver = mockk<LifecycleEventObserver>(relaxUnitFun = true)

    localLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    hostingLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    verifyOrder {
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_CREATE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_START)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_RESUME)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_PAUSE)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_STOP)
      lifecycleObserver.onStateChanged(localLifecycleOwner, Lifecycle.Event.ON_DESTROY)
    }
  }

  @Test
  fun testCleaUp() {
    val localLifecycleOwner = ViewLifecycleOwner(view)
    attach()
    assertEquals(1, hostingLifecycleOwner.observerCount)
    localLifecycleOwner.cleanUp()
    assertEquals(0, hostingLifecycleOwner.observerCount)
    verify { view.removeOnAttachStateChangeListener(attachStateSlot.captured) }
  }

  @Test(expected = IllegalStateException::class)
  fun testNullLifecycleOwner() {
    every { ViewTreeLifecycleOwner.get(view) } returns null
    every { view.isAttachedToWindow } returns true
    ViewLifecycleOwner(view)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    mainThreadSurrogate.close()
    unmockkStatic(ViewTreeLifecycleOwner::class)
    unmockkAll()
  }

  private fun generateHostingLifecycleOwner() = TestLifecycleOwner(
    initialState = Lifecycle.State.INITIALIZED
  )

  private fun attach() {
    attachStateSlot.captured.onViewAttachedToWindow(view)
    every { view.isAttachedToWindow } returns true
  }

  private fun detach() {
    attachStateSlot.captured.onViewDetachedFromWindow(view)
    every { view.isAttachedToWindow } returns false
  }
}