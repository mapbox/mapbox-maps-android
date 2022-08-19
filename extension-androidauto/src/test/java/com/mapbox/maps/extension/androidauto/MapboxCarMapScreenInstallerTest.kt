package com.mapbox.maps.extension.androidauto

import androidx.car.app.Screen
import androidx.lifecycle.Lifecycle
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.testing.TestLifecycleOwner
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class MapboxCarMapScreenInstallerTest {

  @Test
  fun `should attach created in created state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.CREATED)

    verify { mapboxCarMap.registerObserver(createdObserver) }
    verify(exactly = 0) {
      mapboxCarMap.unregisterObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
    }
  }

  @Test
  fun `should attach started in started state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.STARTED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
    }
    verify(exactly = 0) {
      mapboxCarMap.unregisterObserver(createdObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
    }
  }

  @Test
  fun `should attach resumed in resumed state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
    }
    verify(exactly = 0) {
      mapboxCarMap.unregisterObserver(createdObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
    }
  }

  @Test
  fun `should detach resumed in started state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)
    lifecycleOwner.moveToState(Lifecycle.State.STARTED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
    }
    verify(exactly = 0) {
      mapboxCarMap.unregisterObserver(createdObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
    }
  }

  @Test
  fun `should detach started in created state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)
    lifecycleOwner.moveToState(Lifecycle.State.CREATED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
    }
    verify(exactly = 0) {
      mapboxCarMap.unregisterObserver(createdObserver)
    }
  }

  @Test
  fun `should detach observers when destroyed`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)
    lifecycleOwner.moveToState(Lifecycle.State.DESTROYED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(resumedObserver)
      mapboxCarMap.unregisterObserver(startedObserver)
      mapboxCarMap.unregisterObserver(createdObserver)
    }
  }

  @Test
  fun `should set gesture handler to resumed state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val screen = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val resumedObserver = mockk<MapboxCarMapObserver>()
    val gestureHandler = mockk<MapboxCarMapGestureHandler>()
    val mapboxCarMap = MapboxCarMapScreenInstaller(screen, mockk(relaxed = true))
      .gestureHandler(gestureHandler)
      .onResumed(resumedObserver)
      .install()
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)
    lifecycleOwner.moveToState(Lifecycle.State.DESTROYED)

    verifyOrder {
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.setGestureHandler(gestureHandler)
      mapboxCarMap.unregisterObserver(resumedObserver)
      mapboxCarMap.setGestureHandler(match { it is DefaultMapboxCarMapGestureHandler })
    }
  }

  @Test
  fun `should not setup anything before install`() {
    val mapboxCarMap = mockk<MapboxCarMap>(relaxed = true)
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Screen> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    MapboxCarMapScreenInstaller(session, mapboxCarMap)
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)

    verify(exactly = 0) {
      mapboxCarMap.setup(any(), any())
      mapboxCarMap.registerObserver(any())
      mapboxCarMap.unregisterObserver(any())
      mapboxCarMap.setGestureHandler(any())
    }
  }
}