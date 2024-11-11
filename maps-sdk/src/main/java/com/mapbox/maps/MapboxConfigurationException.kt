package com.mapbox.maps

/**
 * A MapboxConfigurationException is thrown by MapboxMap when the SDK hasn't been properly initialised.
 */
class MapboxConfigurationException : RuntimeException {

  /**
   * Creates a Mapbox configuration exception thrown by MapboxMap when the SDK hasn't been properly initialised.
   */
  constructor() : super(
    """
      Using MapView, MapSurface, Snapshotter or other Map components requires providing a valid access token when inflating or creating the map.
      Provide the token by either:
        1. Creating a mapbox_access_token string resource.
        2. Or programmatically calling `MapboxOptions.accessToken = <your_access_token>`.
      The access token parameter is required when using a Mapbox service.
      Please see https://www.mapbox.com/help/create-api-access-token/ to learn how to create one.
      More information in this guide https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens.
    """.trimIndent()
  )

  /**
   * Creates a Mapbox configuration exception thrown by MapboxMap when the SDK hasn't been properly initialised.
   */
  constructor(message: String) : super(message)
}