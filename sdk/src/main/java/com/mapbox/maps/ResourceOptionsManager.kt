package com.mapbox.maps

import android.content.Context

/**
 * Convenience class that manages a global `ResourceOptions`
 * It's possible to create `ResourceOptionsManager` instances as you need them,
 * however it's convenient to use the default object (`default`).
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
   * Static variables and methods.
   */
  companion object {
    private var default: ResourceOptionsManager? = null
    internal fun getTokenResId(context: Context): Int = context.resources.getIdentifier(
      MAPBOX_ACCESS_TOKEN_RESOURCE_NAME,
      "string",
      context.packageName
    )

    /**
     * Convenience function to remove the default instance. Calling `getDefault` again will re-create the default instance.
     */
    fun destroyDefault() {
      default = null
    }

    /**
     * The default shared instance with default resource options.
     *
     * @param context the application context
     * @defaultToken the default token will be set to the default [ResourceOptionsManager] instance.
     * If set to null, will reuse the previously set one or search for an access token in the application resources for the first time of init.
     * It will throw [MapboxConfigurationException] if there's no token found.
     */
    @Synchronized
    fun getDefault(context: Context, defaultToken: String? = null): ResourceOptionsManager {
      // Apply the user setting token as the default token.
      defaultToken?.let { token ->
        default?.let {
          // Reuse the config from previous default and only update the token
          it.update { accessToken(token) }
          return it
        }
        // Build a new ResourceOptionsManager with the provided token.
        val builder = ResourceOptions.Builder().applyDefaultParams(context).accessToken(token)
        return ResourceOptionsManager(builder.build()).also { default = it }
      }

      // Have a default instance created before.
      default?.let {
        return it
      }

      // No defaultToken provided, search in the resources to init default instance.
      getTokenResId(context).also {
        // Throw exception as no token could be found as default token.
        if (it == 0) throw MapboxConfigurationException()
      }

      // Build a new ResourceOptions with token from the resources.
      val builder = ResourceOptions.Builder().applyDefaultParams(context)
      return ResourceOptionsManager(builder.build()).also { default = it }
    }
  }
}