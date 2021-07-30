package com.mapbox.maps.extension.androidauto

import android.content.Context
import android.graphics.BitmapFactory

class LogoWidget(context: Context) : BaseWidgetRenderer(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
  position = WidgetPosition.BOTTOM_LEFT,
  marginBottom = 10f,
  marginLeft = 10f,
  marginRight = 10f,
  marginTop = 10f
)