package com.mapbox.maps.extension.androidauto.widgets

import android.content.Context
import android.graphics.BitmapFactory
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.R
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition

/**
 * Widget shows compass. Positioned in the bottom left corner by default.
 *
 * Note: This feature is only available for Android Maps SDK v10.4.0 and above
 *
 * @since Maps SDK v10.4.0
 *
 * @param position position of logo
 * @param marginX horizontal margin in pixels
 * @param marginY vertical margin in pixels
 */
@MapboxExperimental
class LogoWidget constructor(
  context: Context,
  position: WidgetPosition = WidgetPosition(
    horizontal = WidgetPosition.Horizontal.LEFT,
    vertical = WidgetPosition.Vertical.BOTTOM,
  ),
  marginX: Float = 20f,
  marginY: Float = 20f,
) : BitmapWidget(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
  position = position,
  marginX = marginX,
  marginY = marginY,
)