// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import android.content.Context
import android.util.AttributeSet
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.gestures.R

/**
 * Utility class for parsing [AttributeSet] to [GesturesSettings].
 */
internal object GesturesAttributeParser {
  /**
   * Parse [AttributeSet] to [GesturesSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseGesturesSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): GesturesSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return GesturesSettings {
        rotateEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesRotateEnabled, true)
        pinchToZoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesPinchToZoomEnabled, true)
        scrollEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesScrollEnabled, true)
        simultaneousRotateAndPinchToZoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesSimultaneousRotateAndPinchToZoomEnabled, true)
        pitchEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesPitchEnabled, true)
        scrollMode = ScrollMode.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_gesturesScrollMode, ScrollMode.HORIZONTAL_AND_VERTICAL.ordinal)]
        doubleTapToZoomInEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesDoubleTapToZoomInEnabled, true)
        doubleTouchToZoomOutEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesDoubleTouchToZoomOutEnabled, true)
        quickZoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesQuickZoomEnabled, true)
        focalPoint = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointX) && typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointY)) {
          ScreenCoordinate(
            typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointX, 0.0f).toDouble(),
            typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointY, 0.0f).toDouble()
          )
        } else {
          null
        }
        pinchToZoomDecelerationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesPinchToZoomDecelerationEnabled, true)
        rotateDecelerationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesRotateDecelerationEnabled, true)
        scrollDecelerationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesScrollDecelerationEnabled, true)
        increaseRotateThresholdWhenPinchingToZoom = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesIncreaseRotateThresholdWhenPinchingToZoom, true)
        increasePinchToZoomThresholdWhenRotating = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesIncreasePinchToZoomThresholdWhenRotating, true)
        zoomAnimationAmount = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesZoomAnimationAmount, 1f)
        pinchScrollEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesPinchScrollEnabled, true)
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.