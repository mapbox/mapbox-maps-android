package com.mapbox.maps.extension.style.expressions.types

import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Holds format entries used in a format expression.
 */
data class FormatSection @JvmOverloads constructor(

  /**
   * The visible text or image as an expression for this format section.
   */
  val content: Expression,

  /**
   * If set, the font-scale argument specifies a scaling factor relative to the text-size
   * specified in the root layout properties.
   *
   * "font-scale" is required to be of a resulting type number.
   */
  var fontScale: Expression? = null,

  /**
   * If set, the text-font argument overrides the font specified by the root layout properties.
   *
   * "text-font" is required to be a literal array.
   *
   * The requested font stack has to be a part of the used style.
   * For more information see [The online documentation](https://www.mapbox.com/help/define-font-stack/).
   */
  var textFont: Expression? = null,

  /**
   * If set, the text-color argument overrides the color specified by the root paint properties.
   */
  var textColor: Expression? = null
)