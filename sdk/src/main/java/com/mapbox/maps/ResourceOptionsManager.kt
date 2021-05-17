package com.mapbox.maps

import android.content.Context

/**
 * Convenience class that holds the default ResourceOptions.
 * `ResourceOptionsManager` could be created per `MapView` instance if given view should use some specific resource options.
 * Most common use-case when all `MapView`s should use same resource options could be handled by using `ResourceOptionsManager.getDefault(context: Context)` static object.
 *
 * @property context the context .
 */
data class ResourceOptionsManager(private var context: Context) {
  var resourceOptions = ResourceOptions.Builder()
    .accessToken(CredentialsManager.default.getAccessToken(context))
    .cachePath("${context.filesDir.absolutePath}/$DATABASE_NAME")
    .cacheSize(DEFAULT_CACHE_SIZE) // 50 mb
    .build()
    private set

  /**
   * Update specific resource options in a closure.
   */
  fun update(block: ResourceOptions.Builder.() -> Unit) {
    resourceOptions = resourceOptions.toBuilder().apply(block).build()
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private lateinit var default: ResourceOptionsManager

    /**
     * The default cache size, which is 50MB
     */
    const val DEFAULT_CACHE_SIZE = 1024 * 1024 * 50L

    /**
     * The default shared instance with default resource options.
     */
    fun getDefault(context: Context): ResourceOptionsManager {
      if (!this::default.isInitialized) {
        default = ResourceOptionsManager(context)
      }
      return default
    }
  }
}