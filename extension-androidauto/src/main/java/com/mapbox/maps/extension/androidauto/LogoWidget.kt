package com.mapbox.maps.extension.androidauto

import android.content.Context
import android.graphics.BitmapFactory

class LogoWidget(context: Context) : BaseWidgetRenderer(
  bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
  position = WidgetPosition.BOTTOM_LEFT,
  marginBottom = 20f,
  marginLeft = 20f,
  marginRight = 20f,
  marginTop = 20f
)