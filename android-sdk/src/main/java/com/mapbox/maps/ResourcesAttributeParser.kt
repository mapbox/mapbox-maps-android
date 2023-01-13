package com.mapbox.maps

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

/**
 * Utility class for parsing [AttributeSet] to [ResourcesSettings].
 */
internal object ResourcesAttributeParser {
  /**
   * Parse [AttributeSet] to [ResourcesSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseResourcesOptions(
    context: Context,
    typedArray: TypedArray
  ): ResourceOptions {

    val token = typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken)
    val builder = ResourceOptionsManager.getDefault(context, token).resourceOptions.toBuilder()

    if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl)) {
      builder.baseURL(typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl))
    }
    return builder.build()
  }
}