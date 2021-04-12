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
    typedArray: TypedArray,
    credentialsManager: CredentialsManager
  ): ResourceOptions {
    val accessTokenString: String =
      typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken)
        ?: credentialsManager.getAccessToken(context)

    val cachePathString =
      typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesCachePath)
        ?: "${context.filesDir.absolutePath}/$DATABASE_NAME"

    val assetPathString =
      typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAssetPath)
        ?: context.filesDir.absolutePath

    val tileStorePathString =
      typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesTileStorePath)
        ?: "${context.filesDir.absolutePath}/maps_tile_store/"

    return ResourceOptions.Builder()
      .accessToken(accessTokenString)
      .baseURL(typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl))
      .cachePath(cachePathString)
      .assetPath(assetPathString)
      // https://github.com/mapbox/mapbox-maps-android/issues/939
      // .tileStorePath(tileStorePathString)
      .cacheSize(
        typedArray.getFloat(
          R.styleable.mapbox_MapView_mapbox_resourcesCacheSize, 50_000_000f // 50 mb
        ).toLong()
      )
      .build()
  }
}