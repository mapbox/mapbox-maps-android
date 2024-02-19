package com.mapbox.maps.extension.compose.ornaments.scalebar

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.ornaments.scalebar.internal.ScaleBarComposePlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

/**
 * A [MapScaleBarScope] provides a scope for adding [ScaleBar] ornament.
 */
@MapboxMapScopeMarker
@Immutable
@MapboxExperimental
public class MapScaleBarScope internal constructor(
  private val mapView: MapView,
  private val boxScope: BoxScope
) {
  /**
   * Add a [ScaleBar] ornament to the map, the scale bar will update accordingly when the map's zoom
   * level or latitude changes.
   *
   * By default, the [ScaleBar] will be placed to the [Alignment.TopStart] of the map with padding of 4dp.
   *
   * @param modifier Modifier to be applied to the [ScaleBar].
   * @param alignment The alignment of the [ScaleBar] within the Map.
   * @param textColor Defines text color of the scale bar.
   * @param primaryColor Defines primary color of the scale bar.
   * @param secondaryColor Defines secondary color of the scale bar.
   * @param borderWidth Defines width of the border for the scale bar.
   * @param height Defines height of the scale bar.
   * @param textBarMargin Defines margin of the text bar of the scale bar.
   * @param textBorderWidth Defines text border width of the scale bar.
   * @param textSize Defines text size of the scale bar.
   * @param isMetricUnit Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   * @param refreshInterval Configures minimum refresh interval, in millisecond, default is 15 milliseconds.
   * @param showTextBorder Configures whether to show the text border or not, default is true.
   * @param ratio Configures ratio of scale bar max width compared with MapView width, default is 0.5.
   * @param useContinuousRendering If set to True scale bar will be triggering onDraw depending on
   *     [refreshInterval] even if actual data did not change. If set to False scale bar
   *     will redraw only on demand. Defaults to False and should not be changed explicitly in most cases.
   *     Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  @Composable
  @MapboxExperimental
  public fun ScaleBar(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    textColor: Color = Color.Black,
    primaryColor: Color = Color.Black,
    secondaryColor: Color = Color.White,
    borderWidth: Dp = 2.dp,
    height: Dp = 2.dp,
    textBarMargin: Dp = 8.dp,
    textBorderWidth: Dp = 2.dp,
    textSize: TextUnit = 8.sp,
    isMetricUnit: Boolean = true,
    refreshInterval: Duration = 15.milliseconds,
    showTextBorder: Boolean = true,
    ratio: Float = 0.5f,
    useContinuousRendering: Boolean = false,
  ) {
    val pluginId = remember {
      ScaleBarComposePlugin.getNextId()
    }
    val density = LocalDensity.current
    AndroidView(
      modifier = with(boxScope) {
        if (modifier === Modifier) {
          Modifier
            .padding(4.dp)
        } else {
          modifier
        }.align(alignment)
      },
      factory = { context ->
        ScaleBarImpl(context).also { scaleBar ->
          mapView.createPlugin(
            Plugin.Custom(
              pluginId,
              ScaleBarComposePlugin(scaleBar)
            )
          )
        }
      }
    ) {
      with(density) {
        it.settings = it.settings.toBuilder()
          .setTextColor(textColor.toArgb())
          .setPrimaryColor(primaryColor.toArgb())
          .setSecondaryColor(secondaryColor.toArgb())
          .setBorderWidth(borderWidth.toPx())
          .setHeight(height.toPx())
          .setTextBarMargin(textBarMargin.toPx())
          .setTextBorderWidth(textBorderWidth.toPx())
          .setTextSize(textSize.toPx())
          .setIsMetricUnits(isMetricUnit)
          .setRefreshInterval(refreshInterval.toLong(DurationUnit.MILLISECONDS))
          .setShowTextBorder(showTextBorder)
          .setRatio(ratio)
          .setUseContinuousRendering(useContinuousRendering)
          .build()
      }
    }
    DisposableEffect(Unit) {
      onDispose {
        mapView.removePlugin(pluginId)
      }
    }
  }
}