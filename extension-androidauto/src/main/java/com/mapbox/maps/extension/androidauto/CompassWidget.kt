package com.mapbox.maps.extension.androidauto

import android.content.Context
import android.graphics.BitmapFactory
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition

class CompassWidget(context: Context) : BitmapWidget(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_compass_icon),
  position = WidgetPosition(
    horizontal = WidgetPosition.Horizontal.LEFT,
    vertical = WidgetPosition.Vertical.CENTER,
  ),
  marginX = 20f,
  marginY = 20f
)