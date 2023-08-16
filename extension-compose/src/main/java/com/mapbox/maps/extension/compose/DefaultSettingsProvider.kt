package com.mapbox.maps.extension.compose

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.R
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings

/**
 * Utility that provides the default values of the Settings for each plugin.
 */
@MapboxExperimental
public object DefaultSettingsProvider {
  /**
   * Create a default [AttributionSettings].
   *
   * @param context must be the context closest to the map view. This is specially important in
   * dual display hardware. Otherwise, we might get the wrong pixel ratio.
   * @param pixelRatio the pixel ratio to be applied to the margins
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultAttributionSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): AttributionSettings {
    return AttributionSettings {
      marginLeft *= pixelRatio
      marginTop *= pixelRatio
      marginRight *= pixelRatio
      marginBottom *= pixelRatio
    }
  }

  /**
   * Create a default [CompassSettings].
   *
   * @param context must be the context closest to the map view. This is specially important in
   * dual display hardware. Otherwise, we might get the wrong pixel ratio.
   * @param pixelRatio the pixel ratio to be applied to the margins
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultCompassSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): CompassSettings {
    return CompassSettings {
      marginLeft *= pixelRatio
      marginTop *= pixelRatio
      marginRight *= pixelRatio
      marginBottom *= pixelRatio
    }
  }

  /**
   * Get the default [GesturesSettings].
   */
  @MapboxExperimental
  public val defaultGesturesSettings: GesturesSettings = GesturesSettings {}

  private fun Context.getCompatDrawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(
    this.resources,
    resId,
    null
  )

  /**
   * Create a [LocationPuck2D] instance with or without an arrow bearing image.
   *
   * @param context the context of application
   * @param withBearing if true, the location puck will show an arrow bearing image, default is false.
   */
  @JvmOverloads
  @MapboxExperimental
  public fun createDefault2DPuck(
    context: Context,
    withBearing: Boolean = false
  ): LocationPuck2D = LocationPuck2D(
    topImage = ImageHolder.from(R.drawable.mapbox_user_icon),
    bearingImage = ImageHolder.from(
      if (withBearing)
        R.drawable.mapbox_user_bearing_icon
      else
        R.drawable.mapbox_user_stroke_icon
    ),
    shadowImage = ImageHolder.from(
      if (withBearing)
        R.drawable.mapbox_user_stroke_icon
      else
        R.drawable.mapbox_user_icon_shadow
    )
  )

  /**
   * Create a default [LocationComponentSettings] with 2D location puck.
   *
   * @param context the context of the application
   * @param pixelRatio the pixel ratio to be applied to the [LocationComponentSettings.pulsingMaxRadius]
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultLocationComponentSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): LocationComponentSettings {
    return LocationComponentSettings(
      locationPuck = createDefault2DPuck(context)
    ) {
      pulsingMaxRadius *= pixelRatio
    }
  }

  /**
   * Create a default [LogoSettings].
   *
   * @param context must be the context closest to the map view. This is specially important in
   * dual display hardware. Otherwise, we might get the wrong pixel ratio.
   * @param pixelRatio the pixel ratio to be applied to the margins
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultLogoSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): LogoSettings {
    return LogoSettings {
      marginLeft *= pixelRatio
      marginTop *= pixelRatio
      marginRight *= pixelRatio
      marginBottom *= pixelRatio
    }
  }

  /**
   * Create a default [ScaleBarSettings].
   *
   * @param context must be the context closest to the map view. This is specially important in
   * dual display hardware. Otherwise, we might get the wrong pixel ratio.
   * @param pixelRatio the pixel ratio to be applied to the margins and size of the scale bar.
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultScaleBarSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): ScaleBarSettings {
    return ScaleBarSettings {
      marginLeft *= pixelRatio
      marginTop *= pixelRatio
      marginRight *= pixelRatio
      marginBottom *= pixelRatio
      borderWidth *= pixelRatio
      height *= pixelRatio
      textBarMargin *= pixelRatio
      textBorderWidth *= pixelRatio
      textSize *= pixelRatio
    }
  }

  /**
   * Get the default [OnMapClickListener] that does nothing.
   */
  @MapboxExperimental
  public val defaultOnClickListener: OnMapClickListener = OnMapClickListener { false }

  /**
   * Get the default [OnMapLongClickListener] that does nothing.
   */
  @MapboxExperimental
  public val defaultOnLongClickListener: OnMapLongClickListener = OnMapLongClickListener { false }
}