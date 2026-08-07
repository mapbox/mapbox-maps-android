package com.mapbox.maps.renderer

import android.opengl.EGL14
import android.opengl.EGLContext
import android.opengl.EGLSurface
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.egl.EGLCore
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EGLCoreTest {

  private lateinit var eglCore: EGLCore
  private val eglContext = mockk<EGLContext>()
  private val boundSurface = mockk<EGLSurface>()
  private val newSurface = mockk<EGLSurface>()

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
    every { logD(any(), any<String>()) } just Runs
    EGL14.EGL_NO_CONTEXT = mockk<EGLContext>()
    EGL14.EGL_NO_SURFACE = mockk<EGLSurface>()
    EGL14.EGL_NO_DISPLAY = mockk()
    mockkStatic("android.opengl.EGL14")
    eglCore = EGLCore(
      translucentSurface = false,
      antialiasingSampleCount = 1,
      mapName = "",
    )
    eglCore.eglContext = eglContext
  }

  @After
  fun cleanUp() {
    unmockkStatic("android.opengl.EGL14")
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun makeCurrentRebindsWhenContextCurrentWithDifferentSurface() {
    every { EGL14.eglGetCurrentContext() } returns eglContext
    every { EGL14.eglGetCurrentSurface(EGL14.EGL_DRAW) } returns boundSurface
    every { EGL14.eglMakeCurrent(any(), any(), any(), any()) } returns true

    assertTrue(eglCore.makeCurrent(newSurface))

    verify(exactly = 1) {
      EGL14.eglMakeCurrent(any(), newSurface, newSurface, eglContext)
    }
  }

  @Test
  fun makeCurrentSkipsRebindWhenSameContextAndSurfaceAlreadyCurrent() {
    every { EGL14.eglGetCurrentContext() } returns eglContext
    every { EGL14.eglGetCurrentSurface(EGL14.EGL_DRAW) } returns boundSurface

    assertTrue(eglCore.makeCurrent(boundSurface))

    verify(exactly = 0) {
      EGL14.eglMakeCurrent(any(), any(), any(), any())
    }
  }

  @Test
  fun makeCurrentRebindsWhenAnotherContextIsCurrent() {
    every { EGL14.eglGetCurrentContext() } returns mockk<EGLContext>()
    every { EGL14.eglMakeCurrent(any(), any(), any(), any()) } returns true

    assertTrue(eglCore.makeCurrent(newSurface))

    verify(exactly = 1) {
      EGL14.eglMakeCurrent(any(), newSurface, newSurface, eglContext)
    }
  }
}