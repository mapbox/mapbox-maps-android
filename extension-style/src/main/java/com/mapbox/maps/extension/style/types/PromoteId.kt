package com.mapbox.maps.extension.style.types

import androidx.annotation.Keep
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.unwrapToExpression

/**
 * Holds a property type to promote a specific feature for feature state API.
 *
 * @param propertyName promote id value.
 * @param sourceId source layer in [VectorSource] to which the feature ID override applies.
 *
 * If not provided the override will be applied to all layers of the source.
 *
 * For more information see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/types/#promoteId).
 */
@Keep
data class PromoteId @JvmOverloads constructor(
  /*
   * Feature property name.
   */
  val propertyName: String,

  /*
   * The source layer in [VectorSource] to which the feature ID override applies.
   *
   * If not provided the override will be applied to all layers of the source.
   */
  val sourceId: String? = null
) {

  /**
   * Expression to promote.
   */
  val expression: Expression? = try {
    Value.fromJson(propertyName).value?.unwrapToExpression()
  } catch (_: Throwable) {
    null
  }

  /**
   * Construct a [PromoteId] object from an [Expression].
   *
   * @param expression expression to promote.
   * @param sourceId source layer in [VectorSource] to which the feature ID override applies.
   *
   * If not provided the override will be applied to all layers of the source.
   */
  @JvmOverloads
  constructor(expression: Expression, sourceId: String? = null) : this(
    expression.toJson(),
    sourceId
  )

  internal fun toValue(): Value {
    val promoteIdValue = Value.fromJson(propertyName).getValueOrElse { Value.valueOf(propertyName) }
    sourceId?.let {
      return Value(hashMapOf(sourceId to promoteIdValue))
    }
    return promoteIdValue
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Construct a [PromoteId] object from a Property returned from the core.
     *
     * @param propertyName feature property name.
     * Can be either of type [String] or [HashMap] of <String,String>
     * Throws [RuntimeException] exception if couldn't construct to [PromoteId].
     */
    internal fun fromProperty(propertyName: Any) = when (propertyName) {
      is String -> PromoteId(propertyName)
      is List<*> -> PromoteId(TypeUtils.wrapToValue(propertyName).unwrapToExpression())
      is HashMap<*, *> -> {
        @Suppress("UNCHECKED_CAST")
        try {
          val propertyMap = propertyName as HashMap<String, *>
          if (propertyMap.keys.isNotEmpty()) {
            val key = propertyMap.keys.iterator().next()
            when (val value = propertyMap[key]) {
              is String? -> PromoteId(value ?: "", key)
              is List<*> -> PromoteId(TypeUtils.wrapToValue(value).unwrapToExpression(), key)
              else -> throw RuntimeException("PromoteId value should be either String or Expression")
            }
          } else {
            PromoteId("")
          }
        } catch (e: RuntimeException) {
          throw IllegalArgumentException("$propertyName must be in the format HashMap<String,String> or HashMap<String,Expression>")
        }
      }

      else -> throw UnsupportedOperationException("Wrapping ${propertyName::class.java.simpleName} to PromoteId is not supported.")
    }
  }
}