// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import android.content.Context
import android.util.AttributeSet
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.gestures.R

/**
 * Utility class for parsing [AttributeSet] to [GesturesSettings].
 */
object GesturesAttributeParser {
  /**
   * Parse [AttributeSet] to [GesturesSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseGesturesSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): GesturesSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return GesturesSettings(
        rotateEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesRotateEnabled, true),
        zoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesZoomEnabled, true),
        scrollEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesScrollEnabled, true),
        pitchEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesPitchEnabled, true),
        doubleTapToZoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesDoubleTapToZoomEnabled, true),
        quickZoomEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesQuickZoomEnabled, true),
        focalPoint = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointX) && typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointY)) {
          ScreenCoordinate(
            typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointX, 0.0f).toDouble(),
            typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesFocalPointY, 0.0f).toDouble()
          )
        } else {
          null
        },
        scaleVelocityAnimationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesScaleVelocityAnimationEnabled, true),
        rotateVelocityAnimationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesRotateVelocityAnimationEnabled, true),
        flingVelocityAnimationEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesFlingVelocityAnimationEnabled, true),
        increaseRotateThresholdWhenScaling = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesIncreaseRotateThresholdWhenScaling, true),
        disableRotateWhenScaling = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesDisableRotateWhenScaling, true),
        increaseScaleThresholdWhenRotating = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_gesturesIncreaseScaleThresholdWhenRotating, true),
        zoomRate = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesZoomRate, 1f),
        pixelRatio = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_gesturesPixelRatio, pixelRatio),
      )
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.