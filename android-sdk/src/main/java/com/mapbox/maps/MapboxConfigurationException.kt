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
      Provide the token by either:
        1. Initialising the MapView programmatically using 'MapInitOptions' and configure the token using 'MapInitOptions.resourceOptions.accessToken'.
        2. Or by passing it using MapView's 'mapbox_resourcesAccessToken' XML attribute.
        3. Or by creating a $MAPBOX_ACCESS_TOKEN_RESOURCE_NAME string resource.
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