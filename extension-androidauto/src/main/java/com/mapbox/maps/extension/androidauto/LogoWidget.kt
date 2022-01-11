package com.mapbox.maps.extension.androidauto

import android.content.Context
import android.graphics.BitmapFactory
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition

class LogoWidget(context: Context) : BitmapWidget(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
  position = WidgetPosition(
    horizontal = WidgetPosition.Horizontal.LEFT,
    vertical = WidgetPosition.Vertical.BOTTOM,
  ),
  marginX = 20f,
  marginY = 20f,
)