package com.mapbox.maps

import android.content.Context

/**
 * Convenience class that holds [MapboxMap] related token.
 * `CredentialsManager` could be created per `MapView` instance if given view should use some specific token.
 * Most common use-case when all `MapView`s should use same token could be handled by using `CredentialsManager.default` static object.
 *
 * @property accessToken the token will be applied.
 */
data class CredentialsManager(private var accessToken: String? = null) {
  /**
   * Set a token as the default one.
   */
  fun setAccessToken(token: String?) {
    accessToken = token
  }

  /**
   * Get the access token.
   * If the supplied token is null (which is the use-case for the `shared`),
   * then we will search for an access token in the application resources.
   */
  fun getAccessToken(context: Context): String {
    accessToken?.let {
      // User has set the token, use it.
      return it
    }

    // Check in the resources
    val tokenResId = context.resources.getIdentifier(
      MAPBOX_ACCESS_TOKEN_RESOURCE_NAME,
      STRING_DEF_TYPE,
      context.packageName
    )

    return if (tokenResId != 0) context.getString(tokenResId) else throw MapboxConfigurationException()
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * The default shared instance with empty assess token, will search for an access token in the application resources.
     */
    val default: CredentialsManager = CredentialsManager()
    private const val STRING_DEF_TYPE = "string"
  }
}