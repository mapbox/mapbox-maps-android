package com.mapbox.maps

import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RestrictTo
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.maps.exception.WorkerThreadException

/**
 * Validates that map APIs are called on the thread backing [looper], the Mapbox thread.
 * Thread checking executes for debug builds only and can be fully disabled with the manifest
 * configuration shown below.
 *
 * ```xml
 *  <meta-data
 *    android:name="com.mapbox.maps.ThreadChecker"
 *    android:value="false" />
 * ```
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
open class MapThreadChecker(private val looper: Looper) {

  private companion object {
    // Constants
    private const val TAG = "ThreadChecker"
    private const val DEBUG_FIELD = "DEBUG"
    private const val METADATA_KEY = "com.mapbox.maps.ThreadChecker"
    private const val DEBUG_LOOKUP_FAILED = "Unable to lookup build config of application."
    private const val METADATA_LOOKUP_FAILED = "No boolean metadata found for key $METADATA_KEY"

    // The manifest flag and BuildConfig.DEBUG can't change at runtime, so resolve them once per
    // process, as the ThreadChecker object did.
    private val checksEnabled: Boolean = resolveEnabledState() && resolveDebugState()

    /**
     * Perform manifest metadata lookup for boolean value of com.mapbox.maps.ThreadChecker/
     * Returns true by default.
     */
    private fun resolveEnabledState(): Boolean {
      var enabled = true
      try {
        val context = MapboxSDKCommon.getContext()
        val packageManager: PackageManager = context.packageManager
        @Suppress("DEPRECATION")
        val appInfo = packageManager.getApplicationInfo(
          context.packageName,
          PackageManager.GET_META_DATA
        )

        val metaData = appInfo.metaData
        if (metaData.containsKey(METADATA_KEY)) {
          enabled = metaData.getBoolean(METADATA_KEY)
        }
      } catch (metaLookupException: Exception) {
        logI(TAG, "$METADATA_LOOKUP_FAILED ${metaLookupException.localizedMessage}")
      }
      return enabled
    }

    /**
     * Perform build config look up to determine if the app is running in debug mode.
     * Returns false by default if lookup fails.
     */
    private fun resolveDebugState(): Boolean {
      var debug = false
      try {
        val packageName = MapboxSDKCommon.getContext().applicationInfo.packageName
        val buildConfigClass = Class.forName("$packageName.BuildConfig")
        debug = buildConfigClass.getField(DEBUG_FIELD).getBoolean(null)
      } catch (exception: Exception) {
        logW(TAG, "$DEBUG_LOOKUP_FAILED ${exception.localizedMessage}")
      }
      return debug
    }
  }

  /**
   * Perform main thread checking if this is enabled and application is running in debug mode.
   */
  fun throwIfNotMainThread() {
    if (!checksEnabled) {
      return
    }

    if (!isMainThread()) {
      throw WorkerThreadException()
    }
  }

  private fun isMainThread(): Boolean {
    return Thread.currentThread() == looper.thread
  }
}