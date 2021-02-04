package com.mapbox.maps

import android.content.Context

/**
 * Configuration object for Mapbox Maps SDK For Android.
 */
object MapboxOptions {

  private lateinit var defaultOptions: ResourceOptions

  /**
   * Get the default resource options
   */
  fun getDefaultResourceOptions(context: Context, accessToken: String? = null): ResourceOptions {
    if (!::defaultOptions.isInitialized) {
      val token = accessToken ?: context.getMapboxAccessTokenFromResources()
        ?: throw MapboxConfigurationException()
      defaultOptions = createResourceOptions(token, context.filesDir.absolutePath)
    }
    return defaultOptions
  }

  /**
   * Override the default used resource options given a context and access token.
   * This function allows you to use standard configuration, Context is used to look up
   * the app specific directories. Access token is used to authenticate with Mapbox API endpoints.
   */
  fun setDefaultResourceOptions(context: Context, accessToken: String): ResourceOptions {
    defaultOptions = createResourceOptions(accessToken, context.filesDir.absolutePath)
    return defaultOptions
  }

  /**
   * Override the default used resource options given a [ResourceOptions] configuration object.
   * This allows to have full control over the location of the database.
   */
  fun setDefaultResourceOptions(resourceOptions: ResourceOptions) {
    defaultOptions = resourceOptions
  }

  /**
   * Returns if the default resource options have been initialised.
   */
  fun isInitialized(): Boolean {
    return ::defaultOptions.isInitialized
  }

  /**
   * Create resource options
   */
  fun createResourceOptions(context: Context, accessToken: String): ResourceOptions =
    createResourceOptions(accessToken, context.filesDir.absolutePath)

  /**
   * Create resource options
   */
  fun createResourceOptions(accessToken: String, cachePath: String): ResourceOptions =
    ResourceOptions.Builder().apply {
      accessToken(accessToken)
      cachePath("$cachePath/$DATABASE_NAME")
      assetPath(cachePath)
      // https://github.com/mapbox/mapbox-maps-android/issues/939
      // tileStorePath("$cachePath/maps_tile_store/")
      cacheSize(50_000_000) // 50 mb
    }.build()

  /**
   * Utility function to get an access token from resources using [MAPBOX_ACCESS_TOKEN_RESOURCE_NAME]
   */
  internal fun Context.getMapboxAccessTokenFromResources(): String? {
    val tokenResId =
      resources.getIdentifier(
        MAPBOX_ACCESS_TOKEN_RESOURCE_NAME,
        "string",
        packageName
      )
    return if (tokenResId != 0) getString(tokenResId) else null
  }
}