package com.mapbox.maps

import android.content.Context

/**
 * Convenience class that holds the default ResourceOptions.
 * `ResourceOptionsManager` could be created per `MapView` instance if given view should use some specific resource options.
 * Most common use-case when all `MapView`s should use same resource options could be handled by using `ResourceOptionsManager.getDefault(context: Context)` static object.
 *
 * @property resourceOptions the initial resource options.
 */
data class ResourceOptionsManager(
  var resourceOptions: ResourceOptions
) {

  /**
   * Update specific resource options in a closure.
   */
  fun update(block: ResourceOptions.Builder.() -> Unit) {
    resourceOptions = resourceOptions.toBuilder().apply(block).build()
  }

  /**
   * Reset all the properties of the inside resourceOptions to default
   *
   * @param context the application context
   * @param defaultToken the token will applied as default during reset
   */
  fun reset(context: Context, defaultToken: String? = null) {
    resourceOptions = ResourceOptions.Builder().applyDefaultParams(context, defaultToken).build()
  }

  /**
   * Static variables and methods.
   */
  companion object {
    internal lateinit var default: ResourceOptionsManager

    /**
     * The default shared instance with default resource options.
     *
     * @param context the application context
     * @defaultToken the default token will be set to the default [ResourceOptionsManager] instance.
     * If set to null, will reuse the previously set one or search for an access token in the application resources for the first time of init.
     */
    @Synchronized
    fun getDefault(context: Context, defaultToken: String? = null): ResourceOptionsManager {
      if (!this::default.isInitialized) {
        default = ResourceOptionsManager(
          ResourceOptions.Builder().applyDefaultParams(context, defaultToken)
            .build()
        )
      }
      // A new default token is applied, update it.
      if (defaultToken != null) {
        default.update { accessToken(defaultToken) }
      }
      return default
    }
  }
}