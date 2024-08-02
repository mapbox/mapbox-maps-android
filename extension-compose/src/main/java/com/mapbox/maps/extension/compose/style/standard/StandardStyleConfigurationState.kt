package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue.Companion.equals
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Define the Theme style import config for [MapboxStandardStyle].
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class ThemeValue(public val value: Value) {
  /**
   * Construct the [ThemeValue] with [String].
   */
  public constructor(value: String) : this(Value(value))

  /**
   * Construct the [ThemeValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Get the name of the [ThemeValue] as [String], or null if it's not a constant(e.g. an expression).
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
    internal val INITIAL: ThemeValue =
      ThemeValue(Value.valueOf("ThemeValue.INITIAL"))

    /**
     * The theme for "default".
     */
    @JvmField
    public val DEFAULT: ThemeValue = ThemeValue("default")

    /**
     * The theme for "faded".
     */
    @JvmField
    public val FADED: ThemeValue = ThemeValue("faded")

    /**
     * The theme for "monochrome".
     */
    @JvmField
    public val MONOCHROME: ThemeValue = ThemeValue("monochrome")
  }
}

/**
 * The state holder for the [MapboxStandardStyle]'s configurations.
 */
@Stable
public class StandardStyleConfigurationState private constructor(
  initialTheme: ThemeValue,
  initialShow3dObjects: BooleanValue
) : BaseStyleConfigurationState() {

  /**
   * Public constructor for [StandardStyleConfigurationState].
   */
  public constructor() : this(
    initialTheme = ThemeValue.INITIAL,
    initialShow3dObjects = BooleanValue.INITIAL,
  )

  /**
   * The [ThemeValue] settings of the Mapbox Standard Style, available themes "default", "faded" and "monochrome".
   */
  public var theme: ThemeValue by mutableStateOf(initialTheme)

  /**
   * Whether or not to show all 3d layers (3D buildings, landmarks, trees, etc.) including shadows, ambient occlusion, and flood lights.
   */
  public var show3dObjects: BooleanValue by mutableStateOf(initialShow3dObjects)

  internal companion object {
    internal const val CONFIG_THEME = "theme"
    internal const val CONFIG_SHOW_3D_OBJECTS = "show3dObjects"
  }
}