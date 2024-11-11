package com.mapbox.maps

import android.content.res.TypedArray
import android.util.AttributeSet
import com.mapbox.geojson.Point.fromLngLat

/**
 * Utility class for parsing [AttributeSet] to [CameraOptions].
 */
internal object CameraAttributeParser {

  private var cameraOptionsBuilder: CameraOptions.Builder? = null

  /**
   * Parse [AttributeSet] to [CameraOptions].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseCameraOptions(
    attrs: TypedArray,
    @Suppress("UNUSED_PARAMETER")
    pixelRatio: Float = 1.0f
  ): CameraOptions? {
    with(attrs) {
      cameraOptionsBuilder = null
      val zoom = R.styleable.mapbox_MapView_mapbox_cameraZoom.getCameraFloatAttribute(this)
      if (zoom != 0.0) {
        cameraOptionsBuilder?.zoom(zoom)
      }
      val centerLng = R.styleable.mapbox_MapView_mapbox_cameraTargetLng.getCameraFloatAttribute(
        this
      )
      val centerLat = R.styleable.mapbox_MapView_mapbox_cameraTargetLat.getCameraFloatAttribute(
        this
      )
      if (centerLat != 0.0 || centerLng != 0.0) {
        cameraOptionsBuilder?.center(fromLngLat(centerLng, centerLat))
      }
      val bearing = R.styleable.mapbox_MapView_mapbox_cameraBearing.getCameraFloatAttribute(this)
      if (bearing != 0.0) {
        cameraOptionsBuilder?.bearing(bearing)
      }
      val pitch = R.styleable.mapbox_MapView_mapbox_cameraPitch.getCameraFloatAttribute(this)
      if (pitch != 0.0) {
        cameraOptionsBuilder?.pitch(pitch)
      }
      val anchorX = R.styleable.mapbox_MapView_mapbox_cameraAnchorX.getCameraFloatAttribute(this)
      val anchorY = R.styleable.mapbox_MapView_mapbox_cameraAnchorY.getCameraFloatAttribute(this)
      if (anchorX != 0.0 || anchorY != 0.0) {
        cameraOptionsBuilder?.anchor(ScreenCoordinate(anchorX, anchorY))
      }
      val paddingTop = R.styleable.mapbox_MapView_mapbox_cameraPaddingTop.getCameraFloatAttribute(
        this
      )
      val paddingLeft = R.styleable.mapbox_MapView_mapbox_cameraPaddingLeft.getCameraFloatAttribute(
        this
      )
      val paddingBottom =
        R.styleable.mapbox_MapView_mapbox_cameraPaddingBottom.getCameraFloatAttribute(
          this
        )
      val paddingRight = R.styleable.mapbox_MapView_mapbox_cameraPaddingRight.getCameraFloatAttribute(
        this
      )
      if (paddingTop != 0.0 || paddingLeft != 0.0 || paddingBottom != 0.0 || paddingRight != 0.0) {
        cameraOptionsBuilder?.padding(
          EdgeInsets(
            paddingTop,
            paddingLeft,
            paddingBottom,
            paddingRight
          )
        )
      }
      return cameraOptionsBuilder?.build()
    }
  }

  private fun Int.getCameraFloatAttribute(typedArray: TypedArray): Double {
    val value = typedArray.getFloat(this, 0f).toDouble()
    if (cameraOptionsBuilder == null && value != 0.0) {
      cameraOptionsBuilder = CameraOptions.Builder()
    }
    return value
  }
}