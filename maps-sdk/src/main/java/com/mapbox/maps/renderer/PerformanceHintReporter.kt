package com.mapbox.maps.renderer

import android.annotation.SuppressLint
import android.os.Build
import android.os.PerformanceHintManager
import android.os.Process
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.bindgen.Value
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.maps.logI
import com.mapbox.maps.logW

/**
 * Reports per-frame render-thread work duration to the Android Dynamic Performance Framework
 * (ADPF) via [PerformanceHintManager], so the OS can scale CPU frequency for the render thread
 * based on actual load. No-ops entirely below API 31 or if the platform doesn't expose the
 * service.
 *
 * Only ever called from the render thread (owned by [FpsManager], which only runs on the
 * render thread's [android.os.Handler]).
 *
 * Note: [close] is terminal -- once called, no new session will be created for the remaining
 * lifetime of this instance.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PerformanceHintReporter @VisibleForTesting constructor(
  private val manager: PerformanceHintManager,
  @Suppress("PrivatePropertyName")
  private val TAG: String,
) {
  private var session: PerformanceHintManager.Session? = null
  private var lastTargetDurationNs: Long = -1L
  private var isClosed: Boolean = false

  /**
   * Called from [FpsManager.setScreenRefreshRate] whenever the refresh period is known/changes.
   * Seeds the target duration used at session creation, or updates an existing session's target.
   */
  @SuppressLint("NewApi")
  fun updateTargetDuration(targetDurationNs: Long) {
    lastTargetDurationNs = (targetDurationNs * PERFORMANCE_HINT_TARGET_DURATION_RATIO).toLong()
    logI(TAG, "Updated performance hint target duration to $lastTargetDurationNs ns")
    try {
      session?.updateTargetWorkDuration(lastTargetDurationNs)
    } catch (e: Throwable) {
      logW(TAG, "Failed to update target work duration: ${e.message}")
      close()
    }
  }

  /**
   * Called from [FpsManager.postRender] with the already-computed frame render duration.
   * Creates the session lazily on first call once a target duration is known.
   */
  @SuppressLint("NewApi")
  fun report(actualDurationNs: Long) {
    if (session == null && lastTargetDurationNs > 0L && !isClosed) {
      try {
        session = manager.createHintSession(intArrayOf(Process.myTid()), lastTargetDurationNs)
        if (session != null) {
          logI(TAG, "Created performance hint session with target duration $lastTargetDurationNs ns")
        } else {
          logW(TAG, "Failed to create performance hint session")
          // Failed to get a session so close the reporter.
          close()
        }
      } catch (e: Throwable) {
        logW(TAG, "Failed to create performance hint session: ${e.message}")
        close()
      }
    }
    val currentSession = session ?: return
    try {
      currentSession.reportActualWorkDuration(actualDurationNs)
    } catch (e: Throwable) {
      logW(TAG, "Failed to report actual work duration: ${e.message}")
      close()
    }
  }

  /** Called from [FpsManager.destroy]. */
  @SuppressLint("NewApi")
  fun close() {
    try {
      session?.close()
    } catch (e: Throwable) {
      logW(TAG, "Failed to close performance hint session: ${e.message}")
    } finally {
      session = null
      isClosed = true
    }
  }

  internal companion object {
    private const val PERFORMANCE_HINT_TARGET_DURATION_RATIO = 0.95

    /**
     * Opt-out switch for ADPF performance-hint reporting. Not a typed Kotlin API, but the key
     * string is a de facto contract once used -- treat renaming or removing it like a public
     * API change. An integrator can set it if this feature misbehaves on their fleet:
     *
     * ```kotlin
     * SettingsServiceFactory.getInstance(SettingsServiceStorageType.NON_PERSISTENT)
     *   .set("com.mapbox.maps.android.performanceHintReportingEnabled", Value(false))
     * ```
     *
     * A missing key, a lookup error, or a non-boolean value at that key all default to enabled.
     */
    private const val PERFORMANCE_HINT_REPORTING_ENABLED_KEY =
      "com.mapbox.maps.android.performanceHintReportingEnabled"

    private fun isPerformanceHintReportingEnabled(): Boolean {
      val valueTrue = Value(true)
      return SettingsServiceFactory
        .getInstance(SettingsServiceStorageType.NON_PERSISTENT)
        .get(PERFORMANCE_HINT_REPORTING_ENABLED_KEY, valueTrue).value == valueTrue
    }

    internal fun obtainPerformanceHintReporter(mapName: String): PerformanceHintReporter? {
      val tag = "PerformanceHint" + if (mapName.isNotBlank()) "\\$mapName" else ""
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        logI(tag, "Performance hint reporting is not supported on this platform")
        return null
      }
      if (!isPerformanceHintReportingEnabled()) {
        logI(tag, "Performance hint reporting is disabled")
        return null
      }
      val performanceHintManager: PerformanceHintManager? =
        MapboxSDKCommon.getContext().getSystemService(PerformanceHintManager::class.java)
      if (performanceHintManager == null) {
        logW(tag, "Performance hint manager not available")
        return null
      }
      return PerformanceHintReporter(performanceHintManager, tag)
    }
  }
}