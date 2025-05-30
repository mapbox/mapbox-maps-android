// This file is generated.

package com.mapbox.maps.extension.compose.style.lights.generated

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.HoldsValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLightId
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.internal.ValueParceler
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.util.Objects

/**
 * Create and [rememberSaveable] a [FlatLightState] using [Saver].
 * [init] will be called when the [FlatLightState] is first created to configure its
 * initial state.
 *
 * @param key An optional key to be used as a key for the saved value. If not provided we use the
 * automatically generated by the Compose runtime which is unique for the every exact code location
 * in the composition tree.
 * @param init A function initialise this [FlatLightState].
 */
@Composable
public inline fun rememberFlatLightState(
  id: String = remember {
    generateRandomLightId("flat")
  },
  key: String? = null,
  crossinline init: FlatLightState.() -> Unit = {}
): FlatLightState = rememberSaveable(key = key, saver = FlatLightState.Saver) {
  FlatLightState(id).apply(init)
}

/**
 * A global directional light source which is only applied on 3D and hillshade layers. Using this type disables other light sources.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/light)
 */
@Stable
public class FlatLightState internal constructor(
  /**
   * The id of the [FlatLightState].
   */
  public val id: String,
  initialAnchor: AnchorValue = AnchorValue.INITIAL,
  initialColor: ColorValue = ColorValue.INITIAL,
  initialColorTransition: Transition = Transition.INITIAL,
  initialColorUseTheme: StringValue = StringValue.INITIAL,
  initialIntensity: DoubleValue = DoubleValue.INITIAL,
  initialIntensityTransition: Transition = Transition.INITIAL,
  initialPosition: DoubleListValue = DoubleListValue.INITIAL,
  initialPositionTransition: Transition = Transition.INITIAL,
) {
  public constructor(id: String = generateRandomLightId("flat")) : this(
    id = id,
    initialAnchor = AnchorValue.INITIAL,
    initialColor = ColorValue.INITIAL,
    initialColorTransition = Transition.INITIAL,
    initialColorUseTheme = StringValue.INITIAL,
    initialIntensity = DoubleValue.INITIAL,
    initialIntensityTransition = Transition.INITIAL,
    initialPosition = DoubleListValue.INITIAL,
    initialPositionTransition = Transition.INITIAL,
  )

  private val anchorState: MutableState<AnchorValue> = mutableStateOf(initialAnchor)
  private val colorState: MutableState<ColorValue> = mutableStateOf(initialColor)
  private val colorTransitionState: MutableState<Transition> = mutableStateOf(initialColorTransition)
  private val colorUseThemeState: MutableState<StringValue> = mutableStateOf(initialColorUseTheme)
  private val intensityState: MutableState<DoubleValue> = mutableStateOf(initialIntensity)
  private val intensityTransitionState: MutableState<Transition> = mutableStateOf(initialIntensityTransition)
  private val positionState: MutableState<DoubleListValue> = mutableStateOf(initialPosition)
  private val positionTransitionState: MutableState<Transition> = mutableStateOf(initialPositionTransition)

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   * Default value: "viewport".
   */
  public var anchor: AnchorValue by anchorState

  /**
   * Color tint for lighting extruded geometries.
   * Default value: "#ffffff".
   */
  public var color: ColorValue by colorState

  /**
   * Defines the transition of [color].
   * Default value: "#ffffff".
   */
  public var colorTransition: Transition by colorTransitionState

  /**
   * Overrides applying of color theme for [color] if "none" is set. To follow default theme "default"
   * should be set.
   * Default value: "default".
   */
  @MapboxExperimental
  public var colorUseTheme: StringValue by colorUseThemeState

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more
   * extreme contrast.
   * Default value: 0.5. Value range: [0, 1]
   */
  public var intensity: DoubleValue by intensityState

  /**
   * Defines the transition of [intensity].
   * Default value: 0.5. Value range: [0, 1]
   */
  public var intensityTransition: Transition by intensityTransitionState

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal
   * angle, p polar angle] where r indicates the distance from the center of the base of
   * an object to its light, a indicates the position of the light relative to 0 degree (0 degree
   * when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when
   * `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates
   * the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   * Default value: [1.15,210,30].
   */
  public var position: DoubleListValue by positionState

  /**
   * Defines the transition of [position].
   * Default value: [1.15,210,30].
   */
  public var positionTransition: Transition by positionTransitionState

  @Composable
  private fun MapboxStyleManager.UpdateLightProperty(
    state: MutableState<out HoldsValue>,
    name: String
  ) {
    val value = state.value
    if (value.isNotInitial()) {
      logD(TAG, "update Flat light property: $id, $name, $value")
      setStyleLightProperty(id, name, value.value)
        .onError {
          logE(TAG, it)
        }
    }
  }

  @OptIn(MapboxExperimental::class)
  internal fun getProperties(): HashMap<String, Value> {
    return hashMapOf(
      "id" to Value(id),
      "type" to Value("flat"),
      "properties" to Value(
        hashMapOf<String, Value>().apply {
          if (anchor.isNotInitial()) {
            this["anchor"] = anchor.value
          }
          if (color.isNotInitial()) {
            this["color"] = color.value
          }
          if (colorTransition.isNotInitial()) {
            this["color-transition"] = colorTransition.value
          }
          if (colorUseTheme.isNotInitial()) {
            this["color-use-theme"] = colorUseTheme.value
          }
          if (intensity.isNotInitial()) {
            this["intensity"] = intensity.value
          }
          if (intensityTransition.isNotInitial()) {
            this["intensity-transition"] = intensityTransition.value
          }
          if (position.isNotInitial()) {
            this["position"] = position.value
          }
          if (positionTransition.isNotInitial()) {
            this["position-transition"] = positionTransition.value
          }
        }
      )
    )
  }

  @Composable
  internal fun UpdateProperties(mapboxMap: MapboxMap) {
    mapboxMap.UpdateLightProperty(anchorState, "anchor")
    mapboxMap.UpdateLightProperty(colorState, "color")
    mapboxMap.UpdateLightProperty(colorTransitionState, "color-transition")
    mapboxMap.UpdateLightProperty(colorUseThemeState, "color-use-theme")
    mapboxMap.UpdateLightProperty(intensityState, "intensity")
    mapboxMap.UpdateLightProperty(intensityTransitionState, "intensity-transition")
    mapboxMap.UpdateLightProperty(positionState, "position")
    mapboxMap.UpdateLightProperty(positionTransitionState, "position-transition")
  }

  /**
   * Overwrite the hashcode for [FlatLightState].
   */
  @OptIn(MapboxExperimental::class)
  override fun hashCode(): Int {
    return Objects.hash(
      anchor,
      color,
      colorTransition,
      colorUseTheme,
      intensity,
      intensityTransition,
      position,
      positionTransition,
    )
  }

  /**
   * Overwrite the equals for [FlatLightState].
   */
  @OptIn(MapboxExperimental::class)
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as FlatLightState
    if (id != other.id) return false
    if (anchor != other.anchor) return false
    if (color != other.color) return false
    if (colorTransition != other.colorTransition) return false
    if (colorUseTheme != other.colorUseTheme) return false
    if (intensity != other.intensity) return false
    if (intensityTransition != other.intensityTransition) return false
    if (position != other.position) return false
    if (positionTransition != other.positionTransition) return false
    return true
  }

  /**
   * Overwrite the toString for [FlatLightState].
   */
  @OptIn(MapboxExperimental::class)
  override fun toString(): String {
    return "FlatLightState(anchor=$anchor, color=$color, colorTransition=$colorTransition, colorUseTheme=$colorUseTheme, intensity=$intensity, intensityTransition=$intensityTransition, position=$position, positionTransition=$positionTransition)"
  }

  /**
   * [FlatLightState] Holder class to be used within [Saver].
   *
   * @param savedProperties properties to be saved.
   */
  @Parcelize
  @TypeParceler<Value, ValueParceler>
  public data class Holder(
    val savedProperties: Map<String, Value>,
  ) : Parcelable

  /**
   * Public companion object.
   */
  public companion object {
    private const val TAG = "FlatLightState"

    /**
     * The default saver implementation for [FlatLightState].
     */
    public val Saver: Saver<FlatLightState, Holder> = Saver(
      save = { flatLightState ->
        Holder(flatLightState.getProperties()).also {
          logD(TAG, "save: $it")
        }
      },
      restore = { holder ->
        logD(TAG, "restore: $holder")
        val id: String = holder.savedProperties["id"]!!.contents as String
        @Suppress("UNCHECKED_CAST")
        val properties: Map<String, Value> = holder.savedProperties["properties"]!!.contents as HashMap<String, Value>
        FlatLightState(
          id = id,
          initialAnchor = properties["anchor"]?.let { AnchorValue(it) } ?: AnchorValue.INITIAL,
          initialColor = properties["color"]?.let { ColorValue(it) } ?: ColorValue.INITIAL,
          initialColorTransition = properties["color-transition"]?.let { Transition(it) } ?: Transition.INITIAL,
          initialColorUseTheme = properties["color-use-theme"]?.let { StringValue(it) } ?: StringValue.INITIAL,
          initialIntensity = properties["intensity"]?.let { DoubleValue(it) } ?: DoubleValue.INITIAL,
          initialIntensityTransition = properties["intensity-transition"]?.let { Transition(it) } ?: Transition.INITIAL,
          initialPosition = properties["position"]?.let { DoubleListValue(it) } ?: DoubleListValue.INITIAL,
          initialPositionTransition = properties["position-transition"]?.let { Transition(it) } ?: Transition.INITIAL,
        )
      }
    )
  }
}
// End of generated file.