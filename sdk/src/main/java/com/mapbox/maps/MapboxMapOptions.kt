package com.mapbox.maps

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet

/**
 * Defines configuration [MapboxMapOptions] for a [MapboxMap]. These options can be used when adding a
 * map to your application programmatically (as opposed to via XML). If you are using a MapFragment,
 * you can pass these options in using the static factory method newInstance(MapboxMapOptions).
 * If you are using a [MapView], you can pass these options in using the constructor
 * MapView(Context, MapboxMapOptions). If you add a map using XML, then you can apply these options
 * using custom XML tags.
 *
 * @property context the context hosting the [MapView].
 * @property pixelRatio the pixel ratio for the map to be rendered on.
 * @property attrs the optional [AttributeSet] to derive configurations from.
 */
data class MapboxMapOptions constructor(
  val context: Context,
  val pixelRatio: Float = context.resources.displayMetrics.density,
  val attrs: AttributeSet? = null
) {
  private var internalResourceOptions: ResourceOptions

  /**
   * Resource options when using a MapView.
   *
   * Access token required when using a Mapbox service.
   *
   * Please see [https://www.mapbox.com/help/create-api-access-token/](https://www.mapbox.com/help/create-api-access-token/) to learn how to create one.
   *
   * More information in this guide [https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens](https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens).
   */
  var resourceOptions: ResourceOptions
    get() {
      if (TextUtils.isEmpty(internalResourceOptions.accessToken)) {
        throw MapboxConfigurationException()
      }
      return internalResourceOptions
    }
    set(value) {
      internalResourceOptions = value
    }

  /**
   * Describes the map options value when using a MapView.
   */
  var mapOptions: MapOptions

  /**
   * Camera options when using a MapView.
   */
  var cameraOptions: CameraOptions?

  /**
   * Flag indicating to use a TextureView as render surface for the MapView
   */
  var textureView: Boolean

  init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      internalResourceOptions = if (MapboxOptions.isInitialized()) {
        MapboxOptions.getDefaultResourceOptions(context)
      } else {
        ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
      }
      mapOptions = MapAttributeParser.parseMapOptions(typedArray, pixelRatio)
      cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray)
      textureView = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0) != 0
    } finally {
      typedArray.recycle()
    }

    // Store resource options as default if it wasn't initialized yet
    if (!MapboxOptions.isInitialized()) {
      MapboxOptions.setDefaultResourceOptions(internalResourceOptions)
    }
  }
}