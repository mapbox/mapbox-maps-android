package com.mapbox.maps.extension.androidauto

import android.content.Context
import android.graphics.BitmapFactory

class CompassWidget(context: Context) : BaseWidgetRenderer(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_compass_icon),
  position = WidgetPosition.TOP_RIGHT,
  marginBottom = 10f,
  marginLeft = 10f,
  marginRight = 20f,
  marginTop = 30f
)