package com.mapbox.maps

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mapbox.common.TileStore

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

    val builder = ResourceOptions.Builder()
      .accessToken(accessTokenString)
      .baseURL(typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl))
      .cachePath(cachePathString)
      .assetPath(assetPathString)
      .cacheSize(
        typedArray.getFloat(
          R.styleable.mapbox_MapView_mapbox_resourcesCacheSize, 50_000_000f // 50 mb
        ).toLong()
      )
    tileStorePathString?.let {
      builder.tileStore(TileStore.getInstance(it))
    }
    return builder.build()
  }
}