package com.mapbox.maps.renderer

import android.opengl.EGL14
import android.opengl.EGLConfig
import com.mapbox.maps.*
import com.mapbox.maps.renderer.egl.EGLConfigChooser
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

internal data class EglSettings(
  val redSize: Int,
  val greenSize: Int,
  val blueSize: Int,
  val alphaSize: Int,
  val depthSize: Int,
  val stencilSize: Int,
  val samples: Int,
  val sampleBuffers: Int,
  val caveat: Int,
  val conformant: Int,
  val bufferSize: Int,
  val eglConfig: EGLConfig,
)

@RunWith(Parameterized::class)
internal class EglConfigChooserTest(
  private val eglSettings: List<EglSettings>,
  private val expectedEglSettingsIndex: Int,
  private val antialiasingSampleCount: Int,
  private val translucentSurface: Boolean,
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data(): List<Any?> {
      return listOf<Array<Any?>>(
        arrayOf(
          /* eglSettings */ emptyList<EglSettings>(),
          /* expectedEglSettingsIndex */ -1,
          /* antialiasingSampleCount */ 4,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ emptyList<EglSettings>(),
          /* expectedEglSettingsIndex */ -1,
          /* antialiasingSampleCount */ 0,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ listOf(
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
          ),
          /* expectedEglSettingsIndex */ 0,
          /* antialiasingSampleCount */ 4,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ listOf(
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 24,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
          ),
          /* expectedEglSettingsIndex */ 0,
          /* antialiasingSampleCount */ 4,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ listOf(
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 0,
              sampleBuffers = 0,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 16,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 0,
              sampleBuffers = 0,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 24,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 16,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
          ),
          /* expectedEglSettingsIndex */ 1,
          /* antialiasingSampleCount */ 4,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ listOf(
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 0,
              sampleBuffers = 0,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 16,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 0,
              sampleBuffers = 0,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 24,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 16,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
          ),
          /* expectedEglSettingsIndex */ 0,
          /* antialiasingSampleCount */ 0,
          /* translucentSurface */ false,
        ),
        arrayOf(
          /* eglSettings */ listOf(
            EglSettings(
              bufferSize = 32,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 8,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 24,
              redSize = 8,
              greenSize = 8,
              blueSize = 8,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
            EglSettings(
              bufferSize = 16,
              redSize = 5,
              greenSize = 6,
              blueSize = 5,
              alphaSize = 0,
              depthSize = 24,
              stencilSize = 8,
              samples = 4,
              sampleBuffers = 1,
              caveat = EGL14.EGL_NONE,
              conformant = EGL14.EGL_OPENGL_ES2_BIT,
              eglConfig = mockk(relaxed = true),
            ),
          ),
          /* expectedEglSettingsIndex */ 0,
          /* antialiasingSampleCount */ 4,
          /* translucentSurface */ false,
        ),
      )
    }
  }

  private lateinit var eglConfigChooser: EGLConfigChooser

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logD(any(), any()) } just Runs
    mockkObject(EGLConfigChooser.Companion)
    every { EGLConfigChooser.inEmulator() } returns false

    mockkStatic("android.opengl.EGL14")
    every { EGL14.eglGetError() } returns EGL14.EGL_BAD_DISPLAY
    eglConfigChooser = EGLConfigChooser(translucentSurface, antialiasingSampleCount)
  }

  @After
  fun cleanUp() {
    unmockkStatic("android.opengl.EGL14")
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkObject(EGLConfigChooser.Companion)
  }

  private fun mockChooseConfig(configs: Array<EGLConfig>) {
    val numConfigsSlot = slot<IntArray>()
    val configsSlot = slot<Array<EGLConfig>>()

    every {
      EGL14.eglChooseConfig(
        any(),
        any(),
        any(),
        capture(configsSlot),
        any(),
        any(),
        capture(numConfigsSlot),
        any(),
      )
    } answers {
      numConfigsSlot.captured[0] = configs.size
      configs.copyInto(configsSlot.captured)
      true
    }
  }

  private fun mockEglConfigAttributes(
    eglSettings: EglSettings?
  ) {
    val attributeSlot = slot<Int>()
    val valueSlot = slot<IntArray>()
    every {
      EGL14.eglGetConfigAttrib(
        any(),
        eglSettings?.eglConfig,
        capture(attributeSlot),
        capture(valueSlot),
        any(),
      )
    } answers {
      val value = when (attributeSlot.captured) {
        EGL14.EGL_CONFIG_CAVEAT -> eglSettings?.caveat
        EGL14.EGL_CONFORMANT -> eglSettings?.conformant
        EGL14.EGL_BUFFER_SIZE -> eglSettings?.bufferSize
        EGL14.EGL_RED_SIZE -> eglSettings?.redSize
        EGL14.EGL_GREEN_SIZE -> eglSettings?.greenSize
        EGL14.EGL_BLUE_SIZE -> eglSettings?.blueSize
        EGL14.EGL_ALPHA_SIZE -> eglSettings?.alphaSize
        EGL14.EGL_DEPTH_SIZE -> eglSettings?.depthSize
        EGL14.EGL_STENCIL_SIZE -> eglSettings?.stencilSize
        EGL14.EGL_SAMPLE_BUFFERS -> eglSettings?.sampleBuffers
        EGL14.EGL_SAMPLES -> eglSettings?.samples
        else -> throw java.lang.IllegalStateException("Unexpected attribute ${attributeSlot.captured}")
      }

      value?.let { valueSlot.captured[0] = it }

      value != null
    }
  }

  @Test
  fun supportedConfigsTest() {
    mockChooseConfig(eglSettings.map { it.eglConfig }.toTypedArray())
    eglSettings.forEach(::mockEglConfigAttributes)

    assertEquals(eglSettings.getOrNull(expectedEglSettingsIndex)?.eglConfig, eglConfigChooser.chooseConfig(mockk()))
  }
}