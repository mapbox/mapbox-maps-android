package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class BitmapWidgetRendererTest {

  private lateinit var renderer: BitmapWidgetRenderer
  private lateinit var bitmap: Bitmap
  private var genTexturesCallCount = 0

  @Before
  fun setUp() {
    genTexturesCallCount = 0

    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs

    mockkStatic("android.opengl.GLES20")
    mockkStatic("android.opengl.GLUtils")

    every { GLES20.glGetIntegerv(any(), any(), any()) } just Runs
    every { GLES20.glCreateShader(any()) } returns 1
    every { GLES20.glShaderSource(any(), any()) } just Runs
    every { GLES20.glCompileShader(any()) } just Runs
    every { GLES20.glGetShaderiv(any(), any(), any(), any()) } answers {
      val params = arg<IntArray>(2)
      params[0] = GLES20.GL_TRUE
    }
    every { GLES20.glCreateProgram() } returns 1
    every { GLES20.glAttachShader(any(), any()) } just Runs
    every { GLES20.glLinkProgram(any()) } just Runs
    every { GLES20.glGetUniformLocation(any(), any()) } returns 1
    every { GLES20.glGetAttribLocation(any(), any()) } returns 1
    every { GLES20.glGetError() } returns GLES20.GL_NO_ERROR
    every { GLES20.glGenTextures(any(), any<IntArray>(), any()) } answers {
      val texArray = arg<IntArray>(1)
      texArray[0] = 42 + genTexturesCallCount
      genTexturesCallCount++
    }
    every { GLES20.glBindTexture(any(), any()) } just Runs
    every { GLES20.glTexParameterf(any(), any(), any()) } just Runs
    every { GLES20.glUniformMatrix4fv(any(), any(), any(), any()) } just Runs
    every { GLES20.glUniform1i(any(), any()) } just Runs
    every { GLES20.glEnableVertexAttribArray(any()) } just Runs
    every { GLES20.glVertexAttribPointer(any(), any(), any(), any(), any(), any<java.nio.Buffer>()) } just Runs
    every { GLES20.glDrawArrays(any(), any(), any()) } just Runs
    every { GLES20.glDisableVertexAttribArray(any()) } just Runs
    every { GLES20.glBindBuffer(any(), any()) } just Runs
    every { GLES20.glUseProgram(any()) } just Runs
    every { GLES20.glDeleteTextures(any(), any<IntArray>(), any()) } just Runs
    every { GLES20.glDeleteProgram(any()) } just Runs
    every { GLES20.glDeleteShader(any()) } just Runs
    every { GLES20.glDetachShader(any(), any()) } just Runs
    every { GLES20.glEnable(any()) } just Runs
    every { GLES20.glBlendFunc(any(), any()) } just Runs
    every { GLUtils.texImage2D(any(), any(), any<Bitmap>(), any()) } just Runs

    bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    renderer = BitmapWidgetRenderer(bitmap, WidgetPosition { })
  }

  @After
  fun tearDown() {
    unmockkStatic("android.opengl.GLES20")
    unmockkStatic("android.opengl.GLUtils")
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun `release then re-render allocates fresh texture`() {
    renderer.onSurfaceChanged(800, 600)
    renderer.prepare()
    renderer.render()

    // First render should have called glGenTextures once
    verify(exactly = 1) { GLES20.glGenTextures(1, any(), 0) }

    // Release deletes the texture
    renderer.release()
    verify { GLES20.glDeleteTextures(any(), any<IntArray>(), any()) }

    // Re-render after release: render() internally calls prepare() when program == 0,
    // and should call glGenTextures again because textures[0] was reset to 0
    renderer.onSurfaceChanged(800, 600)
    renderer.render()

    // glGenTextures should have been called a second time for the new texture
    verify(exactly = 2) { GLES20.glGenTextures(1, any(), 0) }
  }

  @Test
  fun `release sets needRender to false`() {
    renderer.onSurfaceChanged(800, 600)
    renderer.prepare()
    assertTrue(renderer.needRender)
    renderer.release()
    assertFalse(renderer.needRender)
  }
}