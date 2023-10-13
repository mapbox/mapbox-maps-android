package com.mapbox.maps.renderer.egl

import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLDisplay
import android.os.Build
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.MAPBOX_LOCALE
import com.mapbox.maps.MapView.Companion.DEFAULT_ANTIALIASING_SAMPLE_COUNT
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.RenderThread
import java.lang.Boolean.compare
import java.lang.Integer.compare

@RestrictTo(RestrictTo.Scope.LIBRARY)
@RenderThread
internal class EGLConfigChooser constructor(
  private val translucentSurface: Boolean,
  private var antialiasingSampleCount: Int,
) {
  private val antialiasingEnabled get() = antialiasingSampleCount > DEFAULT_ANTIALIASING_SAMPLE_COUNT

  // Get all configs at least RGB 565 with 16 depth and 8 stencil.
  // For all features to work we absolutely prefer RGBA_8888 with 24-bit depth,
  // this should be supported on almost all devices
  private val configAttributes: IntArray
    get() {
      val emulator = inEmulator()
      logI(TAG, "In emulator: $emulator")
      return intArrayOf(
        EGL14.EGL_CONFIG_CAVEAT, EGL14.EGL_NONE,
        EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
        EGL14.EGL_BUFFER_SIZE, 16,
        EGL14.EGL_RED_SIZE, 5,
        EGL14.EGL_GREEN_SIZE, 6,
        EGL14.EGL_BLUE_SIZE, 5,
        EGL14.EGL_ALPHA_SIZE, if (translucentSurface) 8 else 0,
        EGL14.EGL_DEPTH_SIZE, 16,
        EGL14.EGL_STENCIL_SIZE, STENCIL_SIZE,
        EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT
      ).plus(
        if (antialiasingEnabled) {
          intArrayOf(
            EGL14.EGL_SAMPLE_BUFFERS, 1,
            EGL14.EGL_SAMPLES, antialiasingSampleCount
          )
        } else {
          intArrayOf()
        }
      ).plus(
        if (emulator) {
          intArrayOf(
            EGL14.EGL_CONFORMANT, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_COLOR_BUFFER_TYPE, EGL14.EGL_RGB_BUFFER,
          )
        } else {
          intArrayOf()
        }
      ).plus(EGL14.EGL_NONE)
    }

  fun chooseConfig(display: EGLDisplay): EGLConfig? {
    val allConfigs = getConfigs(display)
    val config = chooseBestMatchConfig(display, allConfigs)
    if (config == null) {
      logE(TAG, "No EGL config found, see log above for concrete error.")
    }
    return config
  }

  private fun getConfigs(display: EGLDisplay): List<EGLConfig> {
    val numConfigs = IntArray(1)
    val configs = arrayOfNulls<EGLConfig>(100)
    val initialSampleCount = antialiasingSampleCount
    var suitableConfigsFound = false
    while (!suitableConfigsFound) {
      val success = EGL14.eglChooseConfig(
        /* dpy */ display,
        /* attrib_list */ configAttributes,
        /* attrib_listOffset */ 0,
        /* configs */ configs,
        /* configsOffset */ 0,
        /* config_size */ configs.size,
        /* num_config */ numConfigs,
        /* num_configOffset */ 0,
      )
      if (!success || numConfigs[0] < 1) {
        if (antialiasingSampleCount > 1) {
          // try again with halved antialiasingSampleCount
          logW(TAG, "Reducing sample count in 2 times for MSAA as EGL_SAMPLES=$antialiasingSampleCount is not supported")
          antialiasingSampleCount /= 2
        } else {
          // no config found even without anti-aliasing, return error
          logE(TAG, "No suitable EGL configs were found, eglChooseConfig returned error ${EGL14.eglGetError()}.")
          return emptyList()
        }
      } else {
        suitableConfigsFound = true
      }
    }

    if (initialSampleCount != antialiasingSampleCount) {
        logW(
          TAG,
          if (antialiasingSampleCount == 1) "Found EGL configs only with MSAA disabled."
          else "Found EGL configs with MSAA enabled, EGL_SAMPLES=$antialiasingSampleCount."
        )
    }

    return configs.take(numConfigs[0]).requireNoNulls()
  }

  // Quality
  internal enum class BufferFormat(var value: Int) {
    Format32BitAlpha(0),
    Format32BitNoAlpha(1),
    Format24Bit(2),
    Format16Bit(3),
    Unknown(4),
  }

  internal enum class DepthStencilFormat(var value: Int) {
    Format24Depth8Stencil(0),
    Format16Depth8Stencil(1),
  }

  private fun chooseBestMatchConfig(
    display: EGLDisplay,
    configs: List<EGLConfig>
  ): EGLConfig? {

    class Config(
      val bufferFormat: BufferFormat,
      val depthStencilFormat: DepthStencilFormat,
      val isNotConformant: Boolean,
      val isCaveat: Boolean,
      val index: Int,
      val config: EGLConfig,
      val samples: Int,
    ) : Comparable<Config> {

      override fun compareTo(other: Config): Int {
        var i = compare(bufferFormat.value, other.bufferFormat.value)
        if (i != 0) {
          return i
        }

        i = compare(depthStencilFormat.value, other.depthStencilFormat.value)
        if (i != 0) {
          return i
        }

        i = compare(isNotConformant, other.isNotConformant)
        if (i != 0) {
          return i
        }

        i = compare(isCaveat, other.isCaveat)
        if (i != 0) {
          return i
        }

        i = compare(index, other.index)
        return if (i != 0) i else 0
      }
    }

    val matches = ArrayList<Config>()

    var i = 0
    for (config in configs) {
      i++

      fun getConfigAttr(
        attributeName: Int
      ): Int? {
        val attributeValue = IntArray(1)
        if (!EGL14.eglGetConfigAttrib(
            /* dpy = */ display,
            /* config = */ config,
            /* attribute = */ attributeName,
            /* value = */ attributeValue,
            /* offset = */ 0
          )
        ) {
          logE(
            TAG,
            String.format(
              MAPBOX_LOCALE,
              "eglGetConfigAttrib(%d) returned error %d",
              attributeName,
              EGL14.eglGetError()
            )
          )
          return null
        }
        return attributeValue[0]
      }

      val caveat = getConfigAttr(EGL14.EGL_CONFIG_CAVEAT) ?: return null
      val conformant = getConfigAttr(EGL14.EGL_CONFORMANT) ?: return null
      val bits = getConfigAttr(EGL14.EGL_BUFFER_SIZE) ?: return null
      val red = getConfigAttr(EGL14.EGL_RED_SIZE) ?: return null
      val green = getConfigAttr(EGL14.EGL_GREEN_SIZE) ?: return null
      val blue = getConfigAttr(EGL14.EGL_BLUE_SIZE) ?: return null
      val alpha = getConfigAttr(EGL14.EGL_ALPHA_SIZE) ?: return null
      val depth = getConfigAttr(EGL14.EGL_DEPTH_SIZE) ?: return null
      val stencil = getConfigAttr(EGL14.EGL_STENCIL_SIZE) ?: return null
      val sampleBuffers = getConfigAttr(EGL14.EGL_SAMPLE_BUFFERS) ?: return null
      val samples = getConfigAttr(EGL14.EGL_SAMPLES) ?: return null

      var configOk = depth == 24 || depth == 16
      configOk = configOk and (stencil == 8)
      if (antialiasingEnabled) {
        configOk = configOk and (sampleBuffers >= 1)
        configOk = configOk and (samples >= antialiasingSampleCount)
      } else {
        configOk = configOk and (sampleBuffers == 0)
        configOk = configOk and (samples == 0)
      }

      // Filter our configs first for depth, stencil and anti-aliasing
      if (configOk) {
        val bufferFormat: BufferFormat = if (bits == 16 && red == 5 && green == 6 && blue == 5 && alpha == 0) {
          BufferFormat.Format16Bit
        } else if (bits == 32 && red == 8 && green == 8 && blue == 8 && alpha == 0) {
          BufferFormat.Format32BitNoAlpha
        } else if (bits == 32 && red == 8 && green == 8 && blue == 8 && alpha == 8) {
          BufferFormat.Format32BitAlpha
        } else if (bits == 24 && red == 8 && green == 8 && blue == 8 && alpha == 0) {
          BufferFormat.Format24Bit
        } else {
          BufferFormat.Unknown
        }

        // Ignore formats we don't recognise
        if (bufferFormat != BufferFormat.Unknown) {
          // Work out the config's depth stencil format
          val depthStencilFormat = if (depth == 16 && stencil == 8) {
            DepthStencilFormat.Format16Depth8Stencil
          } else {
            DepthStencilFormat.Format24Depth8Stencil
          }

          val isNotConformant = (conformant and EGL14.EGL_OPENGL_ES2_BIT) != EGL14.EGL_OPENGL_ES2_BIT
          val isCaveat = caveat != EGL14.EGL_NONE

          matches.add(
            Config(
              bufferFormat = bufferFormat,
              depthStencilFormat = depthStencilFormat,
              isNotConformant = isNotConformant,
              isCaveat = isCaveat,
              index = i,
              config = config,
              samples = samples,
            )
          )
        }
      }
    }

    if (matches.size == 0) {
      logE(TAG, "No matching configurations after filtering")
      return null
    }

    // Sort and choose the best one
    matches.sort()
    val bestMatch = matches[0]
    if (bestMatch.isCaveat) {
      logW(TAG, "Chosen config has a caveat.")
    }
    if (bestMatch.isNotConformant) {
      logW(TAG, "Chosen config is not conformant.")
    }
    if (antialiasingEnabled && bestMatch.samples != antialiasingSampleCount) {
      logW(
        TAG,
        "MSAA x$antialiasingSampleCount requested, but closest supported x${bestMatch.samples} applied"
      )
    }
    return bestMatch.config
  }

  companion object {

    private const val TAG = "Mbgl-EGLConfigChooser"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var STENCIL_SIZE = 8

    /**
     * Detect if we are in emulator.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun inEmulator(): Boolean {
      return Build.FINGERPRINT.startsWith("generic") ||
        Build.FINGERPRINT.startsWith("unknown") ||
        Build.MODEL.contains("google_sdk") ||
        Build.MODEL.contains("Emulator") ||
        Build.MODEL.contains("Android SDK built for x86") ||
        Build.BRAND.startsWith("generic") ||
        Build.DEVICE.startsWith("generic") ||
        Build.PRODUCT == "google_sdk" ||
        Build.MANUFACTURER.contains("Genymotion") ||
        System.getProperty("ro.kernel.qemu") != null
    }
  }
}