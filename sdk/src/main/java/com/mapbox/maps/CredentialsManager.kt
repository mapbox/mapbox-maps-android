package com.mapbox.maps

import android.content.Context

/**
 * Convenience class that holds MapboxMaps related token.
 * It's possible to create `CredentialsManager` instances as you need them,
 * however it's convenient to use the default `CredentialsManager.shared`.
 *
 * @property accessToken the token will be applied.
 */
data class CredentialsManager(val accessToken: String? = null) {
  /**
   * Get the access token.
   * If the supplied token is nil (which is the case for the `shared`),
   * then we will search for an access token in the application resource.
   */
  fun getAccessToken(context: Context): String {
    accessToken?.let {
      // User have set the token, use this one.
      return it
    }

    // Check in the resources
    val tokenResId = context.resources.getIdentifier(
      MAPBOX_ACCESS_TOKEN_RESOURCE_NAME,
      "string",
      context.packageName
    )

    return if (tokenResId != 0) context.getString(tokenResId) else ""
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * The default shared instance with empty assess token.
     */
    var shared: CredentialsManager = CredentialsManager()
  }
}