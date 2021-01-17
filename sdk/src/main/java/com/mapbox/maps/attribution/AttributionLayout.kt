package com.mapbox.maps.attribution

import android.graphics.Bitmap
import android.graphics.PointF

/**
 * Class representing attribution properties.
 */
data class AttributionLayout(
  /**
   * [Bitmap] logo.
   */
  val logo: Bitmap?,
  /**
   * [x, y] anchor point for placing the logo on the screen.
   */
  val anchorPoint: PointF?,
  /**
   * If set to `true` shorter text version will be used.
   */
  val isShortText: Boolean
)