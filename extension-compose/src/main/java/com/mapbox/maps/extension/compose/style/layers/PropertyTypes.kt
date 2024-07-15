package com.mapbox.maps.extension.compose.style.layers

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.StyleImage
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Defines the the filter type for Mapbox style.
 *
 * @param value the filter expression wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class Filter internal constructor(public val value: Value) {

  /**
   * Construct the [Filter] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

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
    internal val INITIAL: Filter = Filter(Value.valueOf("Filter.INITIAL"))

    /**
     * Default value for [Filter], setting default will result in restoring the default filter defined in the rendering engine.
     */
    @JvmField
    public val DEFAULT: Filter = Filter(Value.nullValue())
  }
}

/**
 * Defines the the Formatted type for Mapbox style. See [Formatted](https://docs.mapbox.com/style-spec/reference/types/#formatted).
 *
 * @param value the Formatted expression wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class FormattedValue internal constructor(public val value: Value) {
  /**
   * Construct the [FormattedValue] with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the [FormattedValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * The [String] represented by [value] or `null` if the stored [Value] is not a [String].
   */
  public val stringOrNull: String?
    get() = value.contents as? String

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
    internal val INITIAL: FormattedValue = FormattedValue(Value.valueOf("FormattedValue.INITIAL"))

    /**
     * Default value for [FormattedValue], setting default will result in restoring the default formatted defined by rendering engine.
     */
    @JvmField
    public val DEFAULT: FormattedValue = FormattedValue(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use as the top of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 * @param styleImage the optional style image that will be added to the style if available.
 */
@Immutable
public data class ImageValue internal constructor(public val value: Value, public val styleImage: StyleImage? = null) {
  /**
   * Construct the [ImageValue] with [StyleImage].
   */
  public constructor(styleImage: StyleImage) : this(Value(styleImage.imageId), styleImage)

  /**
   * Construct the [ImageValue] with image id as [String].
   */
  public constructor(imageId: String) : this(ComposeTypeUtils.wrapToValue(imageId))

  /**
   * Construct the TopImage with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [String] represented by [value] or `null` if the stored [Value] is not a [String].
   */
  public val imageIdOrNull: String?
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
     * Moreover, we set a custom String to differentiate it from [default], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: ImageValue = ImageValue(Value.valueOf("ImageValue.INITIAL"))

    /**
     * Default value for [ImageValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: ImageValue = ImageValue(Value.nullValue())
  }
}

/**
 * Model to render.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 * @param modelInfo The optional 3D model information, including the model id and model uri as a pair of [String]. It will be added to the style if available.
 */
@Immutable
@MapboxExperimental
public data class ModelIdValue internal constructor(public val value: Value, public val modelInfo: Pair<String, String>? = null) {
  /**
   * Construct the [ModelIdValue] with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the [ModelIdValue] with [modelId] and [uri].
   *
   * @param modelId the ID of the model
   * @param uri the uri of the model
   */
  public constructor(modelId: String, uri: String) : this(Value(modelId), modelId to uri)

  /**
   * Construct the [ModelIdValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [String] represented by [value] or `null` if the stored [Value] is not a [String].
   */
  public val modelIdOrNull: String?
    get() = value.contents as? String

  /**
   * The optional uri of the model if it's defined.
   */
  public val uriOrNull: String?
    get() = modelInfo?.second

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
    internal val INITIAL: ModelIdValue = ModelIdValue(Value.valueOf("ModelId.INITIAL"))

    /**
     * Default value for [ModelIdValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: ModelIdValue = ModelIdValue(Value.nullValue())
  }
}