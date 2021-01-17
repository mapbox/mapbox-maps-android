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
      Using MapView requires providing a valid access token when inflating or creating the view.
      Provide the token by creating a $MAPBOX_ACCESS_TOKEN_RESOURCE_NAME string resource or by passing it using MapView's 'mapbox_accessToken' XML attribute.
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