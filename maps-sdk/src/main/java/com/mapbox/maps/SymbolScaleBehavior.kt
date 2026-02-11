package com.mapbox.maps

import java.util.Objects

/**
 * Controls how map symbols scale in response to system font size settings.
 *
 * Use factory methods to create instances:
 * - [system] - Automatic scaling with default or custom mapping
 * - [fixed] - Fixed scale factor, no dynamic updates
 */
@MapboxExperimental
class SymbolScaleBehavior private constructor(
  private val type: String,
  internal val scaleFactor: Float?,
  internal val mapping: ((Float) -> Float)?
) {

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * Note: Mapping functions are compared by reference identity.
   * Two System instances are equal only if they share the same mapping function reference.
   */
  override fun equals(other: Any?) = other is SymbolScaleBehavior &&
    type == other.type &&
    scaleFactor == other.scaleFactor &&
    mapping === other.mapping

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = Objects.hash(type, scaleFactor, mapping)

  /**
   * Returns a String for the object.
   */
  override fun toString() = when (type) {
    TYPE_FIXED -> "SymbolScaleBehavior.Fixed(scaleFactor=$scaleFactor)"
    TYPE_SYSTEM -> "SymbolScaleBehavior.System(mapping=${if (mapping == defaultMapping) "default" else "custom"})"
    else -> "SymbolScaleBehavior(type=$type)"
  }

  /**
   * Returns true if this is a System mode (responds to system font scale changes).
   */
  internal fun isSystem(): Boolean = type == TYPE_SYSTEM

  /**
   * Returns true if this is a Fixed mode (fixed scale factor).
   */
  internal fun isFixed(): Boolean = type == TYPE_FIXED

  companion object {
    private const val TYPE_SYSTEM = "SYSTEM"
    private const val TYPE_FIXED = "FIXED"

    /**
     * Automatic scaling based on system font size.
     * Scales symbols from 0.8x (small fonts) to 2.0x (accessibility sizes).
     */
    @JvmStatic
    val system: SymbolScaleBehavior
      get() = SymbolScaleBehavior(TYPE_SYSTEM, null, defaultMapping)

    /**
     * Automatic scaling with custom mapping function.
     *
     * @param mapping Transforms system font scale (Float) to symbol scale (Float).
     *
     * Example: `SymbolScaleBehavior.system { scale -> min(scale * 1.2f, 1.5f) }`
     */
    @JvmStatic
    fun system(mapping: (Float) -> Float): SymbolScaleBehavior =
      SymbolScaleBehavior(TYPE_SYSTEM, null, mapping)

    /**
     * Fixed scale factor (no system font scaling).
     *
     * @param scaleFactor Scale value (recommended: 0.8-2.0)
     */
    @JvmStatic
    fun fixed(scaleFactor: Float): SymbolScaleBehavior =
      SymbolScaleBehavior(TYPE_FIXED, scaleFactor, null)

    /**
     * Default mapping function that converts system font scale to map symbol scale.
     */
    internal val defaultMapping: (Float) -> Float = { systemFontScale ->
      when {
        systemFontScale < 0.85f -> 0.8f
        systemFontScale > 2.0f -> 2.0f
        systemFontScale < 1.0f -> {
          // Linear interpolation: [0.85, 1.0] → [0.8, 1.0]
          0.8f + (systemFontScale - 0.85f) / 0.15f * 0.2f
        }
        else -> systemFontScale // Already in [1.0, 2.0]
      }
    }
  }
}