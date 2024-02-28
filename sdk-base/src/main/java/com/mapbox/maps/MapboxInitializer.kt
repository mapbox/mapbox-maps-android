package com.mapbox.maps

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.startup.Initializer
import com.mapbox.common.MapboxSDKCommon

/**
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class MapboxInitializer : Initializer<Boolean> {

  /**
   * @suppress
   */
  override fun create(context: Context): Boolean {
    MapboxSDKCommon(context)
    return true
  }

  /**
   * @suppress
   */
  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}