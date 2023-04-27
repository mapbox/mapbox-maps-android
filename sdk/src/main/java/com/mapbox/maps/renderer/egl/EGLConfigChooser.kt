package com.mapbox.maps.renderer.egl

import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLDisplay
import android.os.Build
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.MAPBOX_LOCALE
import com.mapbox.maps.MapView.Companion.DEFAULT_ANTIALIASING_SAMPLE_COUNT
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import java.lang.Boolean.compare
import java.lang.Integer.compare
import java.util.*

internal class EGLConfigChooser constructor(
  private val translucentSurface: Boolean,
  private var antialiasingSampleCount: Int,
) {
  private val antialiasingEnabled get() = antialiasingSampleCount > DEFAULT_ANTIALIASING_SAMPLE_COUNT

  // Get all configs at least RGB 565 with 16 depth and 8 stencil
  private val configAttributes: IntArray
    get() {
      val emulator = inEmulator() || inGenymotion()
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
  private var eglChooserSuccess = true

  fun chooseConfig(display: EGLDisplay): EGLConfig? {
    // Determine number of possible configurations
    val numConfigs = getNumberOfConfigurations(display)
    if (!eglChooserSuccess) {
      return null
    }
    if (numConfigs[0] < 1) {
      logE(TAG, "eglChooseConfig() returned no configs at all.")
      return null
    }
    // Get all possible configurations
    val possibleConfigurations = getPossibleConfigurations(
      display,
      numConfigs
    )
    if (!eglChooserSuccess) {
      return null
    }
    // Choose best match
    val config = chooseBestMatchConfig(display, possibleConfigurations)
    if (config == null) {
      logE(TAG, "No config chosen, see log above for concrete error.")
      return null
    }
    return config
  }

  private fun getNumberOfConfigurations(display: EGLDisplay): IntArray {
    val numConfigs = IntArray(1)
    val initialSampleCount = antialiasingSampleCount
    var suitableConfigsFound = false
    while (!suitableConfigsFound) {
      val success = EGL14.eglChooseConfig(
          /* dpy */ display,
          /* attrib_list */ configAttributes,
          /* attrib_listOffset */ 0,
          /* configs */ null,
          /* configsOffset */ 0,
          /* config_size */ 0,
          /* num_config */ numConfigs,
          /* num_configOffset */ 0,
      )
      if (!success || numConfigs[0] < 1) {
        logE(
          TAG,
          String.format(
            MAPBOX_LOCALE,
            "eglChooseConfig returned error %d",
            EGL14.eglGetError()
          )
        )
        if (antialiasingSampleCount > 1) {
          logW(TAG, "Reducing sample count in 2 times for MSAA as EGL_SAMPLES=$antialiasingSampleCount is not supported")
          antialiasingSampleCount /= 2
        } else {
          // we did all we could, return error
          logE(TAG, "No suitable EGL configs were found.")
          numConfigs[0] = 0
          eglChooserSuccess = false
          return numConfigs
        }
      } else {
        suitableConfigsFound = true
      }
    }
    if (initialSampleCount != antialiasingSampleCount) {
      if (antialiasingSampleCount == 1) {
        logW(TAG, "Found EGL configs only with MSAA disabled.")
      } else {
        logW(TAG, "Found EGL configs with MSAA enabled, EGL_SAMPLES=$antialiasingSampleCount.")
      }
    }
    eglChooserSuccess = true
    return numConfigs
  }

  private fun getPossibleConfigurations(
    display: EGLDisplay,
    numConfigs: IntArray
  ): Array<EGLConfig> {
    val configs = arrayOfNulls<EGLConfig>(numConfigs[0])

    val success = EGL14.eglChooseConfig(
      /* dpy */ display,
      /* attrib_list */ configAttributes,
      /* attrib_listOffset */ 0,
      /* configs */ configs,
      /* configsOffset */ 0,
      /* config_size */ numConfigs[0],
      /* num_config */ numConfigs,
      /* num_configOffset */ 0,
    )

    if (!success) {
      logE(
        TAG,
        String.format(
          MAPBOX_LOCALE,
          "Weird: eglChooseConfig() returned error %d although ran fine before.",
          EGL14.eglGetError()
        )
      )
      eglChooserSuccess = false
    }
    return configs.requireNoNulls()
  }

  // Quality
  internal enum class BufferFormat(var value: Int) {
    Format16Bit(3),
    Format32BitNoAlpha(1),
    Format32BitAlpha(2),
    Format24Bit(0),
    Unknown(4)
  }

  internal enum class DepthStencilFormat(var value: Int) {
    Format16Depth8Stencil(1),
    Format24Depth8Stencil(0)
  }

  private fun chooseBestMatchConfig(
    display: EGLDisplay,
    configs: Array<EGLConfig>
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

      val caveat = getConfigAttr(display, config, EGL14.EGL_CONFIG_CAVEAT)
      val conformant = getConfigAttr(display, config, EGL14.EGL_CONFORMANT)
      val bits = getConfigAttr(display, config, EGL14.EGL_BUFFER_SIZE)
      val red = getConfigAttr(display, config, EGL14.EGL_RED_SIZE)
      val green = getConfigAttr(display, config, EGL14.EGL_GREEN_SIZE)
      val blue = getConfigAttr(display, config, EGL14.EGL_BLUE_SIZE)
      val alpha = getConfigAttr(display, config, EGL14.EGL_ALPHA_SIZE)
      val depth = getConfigAttr(display, config, EGL14.EGL_DEPTH_SIZE)
      val stencil = getConfigAttr(display, config, EGL14.EGL_STENCIL_SIZE)
      val sampleBuffers = getConfigAttr(display, config, EGL14.EGL_SAMPLE_BUFFERS)
      val samples = getConfigAttr(display, config, EGL14.EGL_SAMPLES)

      // validate every attribute is set correctly
      if (!eglChooserSuccess) {
        return null
      }

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
        // Work out the config's buffer format
        val bufferFormat: BufferFormat
        if (bits == 16 && red == 5 && green == 6 && blue == 5 && alpha == 0) {
          bufferFormat = BufferFormat.Format16Bit
        } else if (bits == 32 && red == 8 && green == 8 && blue == 8 && alpha == 0) {
          bufferFormat =
            BufferFormat.Format32BitNoAlpha
        } else if (bits == 32 && red == 8 && green == 8 && blue == 8 && alpha == 8) {
          bufferFormat =
            BufferFormat.Format32BitAlpha
        } else if (bits == 24 && red == 8 && green == 8 && blue == 8 && alpha == 0) {
          bufferFormat = BufferFormat.Format24Bit
        } else {
          bufferFormat = BufferFormat.Unknown
        }

        // Work out the config's depth stencil format
        val depthStencilFormat = if (depth == 16 && stencil == 8) {
          DepthStencilFormat.Format16Depth8Stencil
        } else {
          DepthStencilFormat.Format24Depth8Stencil
        }

        val isNotConformant = (conformant and EGL14.EGL_OPENGL_ES2_BIT) != EGL14.EGL_OPENGL_ES2_BIT
        val isCaveat = caveat != EGL14.EGL_NONE

        // Ignore formats we don't recognise
        if (bufferFormat != BufferFormat.Unknown) {
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
        "Antialiasing was specified with sample count = $antialiasingSampleCount" +
          " but MSAA x${bestMatch.samples} was applied as closest one supported."
      )
    }
    return bestMatch.config
  }

  private fun getConfigAttr(
    display: EGLDisplay,
    config: EGLConfig,
    attributeName: Int
  ): Int {
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
      eglChooserSuccess = false
    }
    return attributeValue[0]
  }

  /**
   * Detect if we are in emulator.
   */
  private fun inEmulator(): Boolean {
    return (
      Build.FINGERPRINT.startsWith("generic") ||
        Build.FINGERPRINT.startsWith("unknown") ||
        Build.MODEL.contains("google_sdk") ||
        Build.MODEL.contains("Emulator") ||
        Build.MODEL.contains("Android SDK built for x86") ||
        Build.BRAND.startsWith("generic") ||
        Build.DEVICE.startsWith("generic") ||
        Build.PRODUCT == "google_sdk" ||
        System.getProperty("ro.kernel.qemu") != null
      )
  }

  /**
   * Detect if we are in genymotion
   */
  private fun inGenymotion(): Boolean {
    return Build.MANUFACTURER.contains("Genymotion")
  }

  companion object {

    private const val TAG = "Mbgl-EGLConfigChooser"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var STENCIL_SIZE = 8
  }
}