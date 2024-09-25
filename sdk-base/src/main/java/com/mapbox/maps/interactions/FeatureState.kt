package com.mapbox.maps.interactions

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureState.Builder
import com.mapbox.maps.logW
import java.util.Objects

/**
 * Feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`.
 */
@MapboxExperimental
open class FeatureState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  open val internalState: Value
) {
  /**
   * Returns the state value for [stateName] if it exists
   * and is a [Boolean] or can be coerced to a [Boolean], or NULL otherwise.
   */
  @Suppress("UNCHECKED_CAST")
  fun getBooleanState(stateName: String): Boolean? {
    return (internalState.contents as HashMap<String, Value>)[stateName]?.contents as? Boolean?
  }

  /**
   * Returns the state value for [stateName] if it exists
   * and is a [String] or can be coerced to a [String], or NULL otherwise.
   */
  @Suppress("UNCHECKED_CAST")
  fun getStringState(stateName: String): String? {
    return (internalState.contents as HashMap<String, Value>)[stateName]?.contents as? String?
  }

  /**
   * Returns the state value for [stateName] if it exists
   * and is a [Long] or can be coerced to a [Long], or NULL otherwise.
   */
  @Suppress("UNCHECKED_CAST")
  fun getLongState(stateName: String): Long? {
    return (internalState.contents as HashMap<String, Value>)[stateName]?.contents as? Long?
  }

  /**
   * Returns the state value for [stateName] if it exists
   * and is a [Double] or can be coerced to a [Double], or NULL otherwise.
   */
  @Suppress("UNCHECKED_CAST")
  fun getDoubleState(stateName: String): Double? {
    return (internalState.contents as HashMap<String, Value>)[stateName]?.contents as? Double?
  }

  /**
   * Return state as a JSON string.
   */
  fun asJsonString(): String = internalState.toJson()

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FeatureState

    return internalState == other.internalState
  }

  /**
   * Override hashCode method.
   */
  override fun hashCode() = Objects.hash(internalState)

  /**
   * Override toString method.
   */
  override fun toString() = "FeatureStateValue(state=${internalState.toJson()})"

  /**
   * Builder class to build [FeatureState].
   */
  open class Builder {

    protected val rawStateMap: HashMap<String, Value> = hashMapOf()

    /**
     * Add a boolean state for property [stateName].
     */
    fun addBooleanState(stateName: String, value: Boolean): Builder {
      rawStateMap[stateName] = Value.valueOf(value)
      return this
    }

    /**
     * Add a string state for property [stateName].
     */
    fun addStringState(stateName: String, value: String): Builder {
      rawStateMap[stateName] = Value.valueOf(value)
      return this
    }

    /**
     * Add a long state for property [stateName].
     */
    fun addLongState(stateName: String, value: Long): Builder {
      rawStateMap[stateName] = Value.valueOf(value)
      return this
    }

    /**
     * Add a double state for property [stateName].
     */
    fun addDoubleState(stateName: String, value: Double): Builder {
      rawStateMap[stateName] = Value.valueOf(value)
      return this
    }

    /**
     * Build an instance of [FeatureState].
     */
    open fun build(): FeatureState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty FeatureState. Is this intended?")
      }
      return FeatureState(Value(rawStateMap))
    }
  }

  /**
   * Static variable and functions.
   */
  internal companion object {
    internal const val TAG = "FeatureState"
  }
}

/**
 * Builder function to create [FeatureState].
 */
@MapboxExperimental
@JvmSynthetic
fun FeatureState(init: Builder.() -> Unit): FeatureState =
  Builder().apply(init).build()