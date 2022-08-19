package com.mapbox.maps.extension.androidauto

import androidx.car.app.CarContext
import androidx.car.app.Session
import androidx.lifecycle.Lifecycle
import com.mapbox.maps.MapInitOptions
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
class MapboxCarMapSessionInstallerTest {

  @Test
  fun `install should setup MapboxCarMap and then register created observers`() {
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(observer)
      .install(mockCarInitializer())
    lifecycleOwner.moveToState(Lifecycle.State.CREATED)

    verifyOrder {
      mapboxCarMap.setup(any(), any())
      mapboxCarMap.registerObserver(observer)
    }
    verify(exactly = 0) { mapboxCarMap.unregisterObserver(observer) }
  }

  @Test
  fun `install should setup MapboxCarMap with the session CarContext`() {
    val lifecycleOwner = TestLifecycleOwner()
    val sessionCarContext = mockk<CarContext>()
    val session = mockk<Session> {
      every { carContext } returns sessionCarContext
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .install(mockCarInitializer())
    lifecycleOwner.moveToState(Lifecycle.State.CREATED)

    verify { mapboxCarMap.setup(sessionCarContext, any()) }
  }

  @Test
  fun `install should setup MapboxCarMap with the MapInitOptions`() {
    val lifecycleOwner = TestLifecycleOwner()
    val sessionCarContext = mockk<CarContext>()
    val session = mockk<Session> {
      every { carContext } returns sessionCarContext
      every { lifecycle } returns lifecycleOwner.lifecycle
    }
    val mapInitOptions: MapInitOptions = mockk()
    val mockInitializer: MapboxCarMapInitializer = mockk {
      every { onCreate(any()) } returns mapInitOptions
    }

    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .install(mockInitializer)
    lifecycleOwner.moveToState(Lifecycle.State.CREATED)

    verify { mapboxCarMap.setup(any(), mapInitOptions) }
  }

  @Test
  fun `should attach created in created state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
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
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
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
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
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
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
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
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
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
  fun `should clear observers when destroyed`() {
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(createdObserver)
      .onStarted(startedObserver)
      .onResumed(resumedObserver)
      .install(mockCarInitializer())
    lifecycleOwner.moveToState(Lifecycle.State.RESUMED)
    lifecycleOwner.moveToState(Lifecycle.State.DESTROYED)

    verifyOrder {
      mapboxCarMap.registerObserver(createdObserver)
      mapboxCarMap.registerObserver(startedObserver)
      mapboxCarMap.registerObserver(resumedObserver)
      mapboxCarMap.clearObservers()
    }
  }

  @Test
  fun `should not attach observers before the created state`() {
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val mapboxCarMap = MapboxCarMapSessionInstaller(session, mockk(relaxed = true))
      .onCreated(mockk())
      .onStarted(mockk())
      .onResumed(mockk())
      .install(mockCarInitializer())

    verify(exactly = 0) {
      mapboxCarMap.registerObserver(any())
      mapboxCarMap.unregisterObserver(any())
    }
  }

  @Test
  fun `should not setup anything before install`() {
    val mapboxCarMap = mockk<MapboxCarMap>(relaxed = true)
    val lifecycleOwner = TestLifecycleOwner()
    val session = mockk<Session> {
      every { carContext } returns mockk()
      every { lifecycle } returns lifecycleOwner.lifecycle
    }

    val createdObserver = mockk<MapboxCarMapObserver>()
    val startedObserver = mockk<MapboxCarMapObserver>()
    val resumedObserver = mockk<MapboxCarMapObserver>()
    MapboxCarMapSessionInstaller(session, mapboxCarMap)
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

  private fun mockCarInitializer(): MapboxCarMapInitializer = mockk {
    every { onCreate(any()) } answers {
      mockk { every { context } returns firstArg<CarContext>() }
    }
  }
}