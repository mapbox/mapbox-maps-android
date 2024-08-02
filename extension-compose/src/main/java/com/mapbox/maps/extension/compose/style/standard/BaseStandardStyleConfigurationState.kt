package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue.Companion.equals
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Define the lightPreset style import config for [MapboxStandardStyle].
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class LightPresetValue(public val value: Value) {
  /**
   * Construct the [LightPresetValue] with [String].
   */
  public constructor(value: String) : this(Value(value))

  /**
   * Construct the [LightPresetValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Get the name of the [LightPresetValue] as [String], or null if it's not a constant(e.g. an expression).
   */
  public val presetNameOrNull: String?
    get() = value.contents as? String

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LightPresetValue =
      LightPresetValue(Value.valueOf("LightPresetValue.INITIAL"))

    /**
     * Default value for [LightPresetValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: LightPresetValue = LightPresetValue(Value.nullValue())

    /**
     * The light preset for "day".
     */
    @JvmField
    public val DAY: LightPresetValue = LightPresetValue("day")

    /**
     * The light preset for "night".
     */
    @JvmField
    public val NIGHT: LightPresetValue = LightPresetValue("night")

    /**
     * The light preset for "dusk".
     */
    @JvmField
    public val DUSK: LightPresetValue = LightPresetValue("dusk")

    /**
     * The light preset for "dawn".
     */
    @JvmField
    public val DAWN: LightPresetValue = LightPresetValue("dawn")
  }
}

/**
 * The state holder for the base [MapboxStandardStyle]'s & [MapboxStandardSatelliteStyle]'s configurations.
 */
@Stable
public abstract class BaseStyleConfigurationState protected constructor(
  initialShowPlaceLabels: BooleanValue = BooleanValue.INITIAL,
  initialShowRoadLabels: BooleanValue = BooleanValue.INITIAL,
  initialShowPointOfInterestLabels: BooleanValue = BooleanValue.INITIAL,
  initialShowTransitLabels: BooleanValue = BooleanValue.INITIAL,
  initialLightPreset: LightPresetValue = LightPresetValue.INITIAL,
  initialFont: StringValue = StringValue.INITIAL,
) {

  /**
   * Whether or not to show the place labels, default to true.
   */
  public var showPlaceLabels: BooleanValue by mutableStateOf(initialShowPlaceLabels)

  /**
   * Whether or not to show the road labels, default to true.
   */
  public var showRoadLabels: BooleanValue by mutableStateOf(initialShowRoadLabels)

  /**
   * Whether or not to show the point of interest labels, default to true.
   */
  public var showPointOfInterestLabels: BooleanValue by mutableStateOf(
    initialShowPointOfInterestLabels
  )

  /**
   * Whether or not to show the transit labels, default to true.
   */
  public var showTransitLabels: BooleanValue by mutableStateOf(initialShowTransitLabels)

  /**
   * The [LightPresetValue] settings of the Mapbox Standard Style, available lightPresets including "day", "night", "dawn", "dusk", defaults to "day".
   */
  public var lightPreset: LightPresetValue by mutableStateOf(initialLightPreset)

  /**
   * The font to be used with the standard style.
   */
  public var font: StringValue by mutableStateOf(initialFont)

  internal companion object {
    internal const val CONFIG_SHOW_PLACE_LABELS = "showPlaceLabels"
    internal const val CONFIG_SHOW_ROAD_LABELS = "showRoadLabels"
    internal const val CONFIG_SHOW_POINT_OF_INTEREST_LABELS = "showPointOfInterestLabels"
    internal const val CONFIG_SHOW_TRANSIT_LABELS = "showTransitLabels"
    internal const val CONFIG_LIGHT_PRESET = "lightPreset"
    internal const val CONFIG_FONT = "font"
  }
}