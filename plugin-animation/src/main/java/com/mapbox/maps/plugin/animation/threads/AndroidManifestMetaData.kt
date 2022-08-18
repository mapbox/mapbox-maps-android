package com.mapbox.maps.plugin.animation.threads

import android.content.pm.PackageManager
import android.os.Bundle
import com.mapbox.common.MapboxSDKCommon

/**
 * The [PackageManager] has issues being mocked. This class makes it so usages of the package
 * meta data can be tested without mocking the application info metadata.
 *
 * https://github.com/mockk/mockk/issues/182
 */
internal class AndroidManifestMetaData {

  private fun metaData(): Bundle {
    val context = MapboxSDKCommon.getContext()
    val packageManager: PackageManager = context.packageManager
    return packageManager.getApplicationInfo(
      context.packageName, PackageManager.GET_META_DATA
    ).metaData
  }

  /**
   * Perform manifest metadata lookup for boolean value of
   * com.mapbox.maps.BackgroundAnimatorThread. Returns false by default.
   */
  fun backgroundAnimatorThreadEnabled(): Boolean {
    return metaData().getBoolean(BACKGROUND_ANIMATOR_THREAD_KEY, false)
  }

  private companion object {
    private const val BACKGROUND_ANIMATOR_THREAD_KEY = "com.mapbox.maps.BackgroundAnimatorThread"
  }
}