package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.graphics.Color
import com.mapbox.common.Logger
import com.mapbox.maps.plugin.PresetPuckStyle
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.locationcomponent.utils.BitmapUtils
import java.lang.ref.WeakReference

internal class PuckPresetProvider(context: Context) {
  private val contextWeakReference = WeakReference(context)

  private fun getPrecisePresetPuck(): TwoDLocationPuck {
    // Normal mode as in old impl
    contextWeakReference.get()?.let {
      return TwoDLocationPuck(
        topImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_icon)!!,
        bearingImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_stroke_icon)!!,
        shadowImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_icon_shadow)!!,
        topStaleTintColor = STALE_TINT_COLOR
      )
    }
    Logger.e(TAG, "context is not available any more.")
    return TwoDLocationPuck()
  }

  private fun getApproximatePresetPuck(): TwoDLocationPuck {
    // GPS mode as in old impl
    contextWeakReference.get()?.let {
      return TwoDLocationPuck(
        bearingImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_puck_icon)!!,
        shadowImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_stroke_icon)!!
      )
    }
    Logger.e(TAG, "context is not available any more.")
    return TwoDLocationPuck()
  }

  private fun getHeadingArrowPresetPuck(): TwoDLocationPuck {
    // Compass mode as in old impl
    contextWeakReference.get()?.let {
      return TwoDLocationPuck(
        topImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_icon)!!,
        bearingImage = BitmapUtils.getDrawableFromRes(
          it,
          R.drawable.mapbox_user_bearing_icon
        )!!,
        shadowImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_stroke_icon)!!,
        topStaleTintColor = STALE_TINT_COLOR,
        bearingStaleTintColor = STALE_TINT_COLOR
      )
    }
    Logger.e(TAG, "context is not available any more.")
    return TwoDLocationPuck()
  }

  private fun getHeadingBeamPresetPuck(): TwoDLocationPuck {
    TODO()
  }

  private fun getArrowPresetPuck(): TwoDLocationPuck {
    contextWeakReference.get()?.let {
      return TwoDLocationPuck(
        topImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_puck_icon)!!,
        bearingImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_puck_icon)!!,
        shadowImage = BitmapUtils.getDrawableFromRes(it, R.drawable.mapbox_user_puck_icon)!!
      )
    }
    Logger.e(TAG, "context is not available any more.")
    return TwoDLocationPuck()
  }

  fun getPresetPuck(preset: PresetPuckStyle) =
    when (preset) {
      PresetPuckStyle.PRECISE -> getPrecisePresetPuck()
      PresetPuckStyle.APPROXIMATE -> getApproximatePresetPuck()
      PresetPuckStyle.HEADING_ARROW -> getHeadingArrowPresetPuck()
      PresetPuckStyle.HEADING_BEAM -> getHeadingBeamPresetPuck()
      PresetPuckStyle.ARROW -> getArrowPresetPuck()
    }

  companion object {
    private val STALE_TINT_COLOR = Color.parseColor("#A1B0C0")
    private const val TAG = "PuckPresetProvider"
  }
}