package com.mapbox.maps.plugin.viewport.data

import com.mapbox.common.Logger
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Size

internal object ViewportDataSourceProcessor {
  private const val TAG = "MbxViewportDataSource"

  /**
   * Returns a padding value by transforming a user-provided padding to a single-pixel padding value.
   *
   * That single-pixel anchor sits on the bottom edge of the provided padding, centered horizontally.
   */
  fun getMapAnchoredPaddingFromUserPadding(mapSize: Size, padding: EdgeInsets): EdgeInsets {
    val verticalRange = 0f..mapSize.height
    val horizontalRange = 0f..mapSize.width
    padding.run {
      if (!verticalRange.contains(top) ||
        !verticalRange.contains(bottom) ||
        !horizontalRange.contains(left) ||
        !horizontalRange.contains(right) ||
        top + bottom > mapSize.height ||
        left + right > mapSize.width
      ) {
        Logger.e(
          TAG,
          """Provided following padding does fit the map size:
                        |mapSize: $mapSize
                        |padding: $padding
                    """.trimMargin()
        )
        return EdgeInsets(
          mapSize.height / 2.0,
          mapSize.width / 2.0,
          mapSize.height / 2.0,
          mapSize.width / 2.0,
        )
      }
    }

    val anchorPointX =
      ((mapSize.width - padding.left - padding.right) / 2.0) + padding.left
    val centerInsidePaddingY =
      ((mapSize.height - padding.top - padding.bottom) / 2.0) + padding.top
    val anchorPointY =
      ((mapSize.height - padding.bottom - centerInsidePaddingY)).plus(centerInsidePaddingY)

    return EdgeInsets(
      anchorPointY,
      anchorPointX,
      mapSize.height - anchorPointY,
      mapSize.width - anchorPointX
    )
  }

  fun getScreenBoxForFraming(mapSize: Size, padding: EdgeInsets): ScreenBox {
    val topLeft = ScreenCoordinate(
      padding.left,
      padding.top
    )
    val bottomRight = ScreenCoordinate(
      mapSize.width - padding.right,
      mapSize.height - padding.bottom
    )
    return ScreenBox(
      topLeft,
      bottomRight
    )
  }

  fun getSmootherBearingForMap(
    enabled: Boolean,
    currentMapCameraBearing: Double,
    vehicleBearing: Double
  ): Double {
    if (!enabled) {
      return vehicleBearing
    }
    return currentMapCameraBearing + shortestRotationDiff(vehicleBearing, currentMapCameraBearing)
  }

  private fun shortestRotationDiff(angle: Double, anchorAngle: Double): Double {
    if (angle.isNaN() || anchorAngle.isNaN()) {
      return 0.0
    }
    val rawAngleDiff = angle - anchorAngle
    return wrap(rawAngleDiff, -180.0, 180.0)
  }

  private fun wrap(angle: Double, min: Double, max: Double): Double {
    val d = max - min
    return ((((angle - min) % d) + d) % d) + min
  }
}