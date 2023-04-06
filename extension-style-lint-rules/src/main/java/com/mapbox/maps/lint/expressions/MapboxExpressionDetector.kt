package com.mapbox.maps.lint.expressions

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULambdaExpression

/**
 * A detector to scan through the source code and find the lambdas that applies to the ExpressionBuilder.
 */
abstract class MapboxExpressionDetector : Detector(), Detector.UastScanner {

  /**
   * Called when a Mapbox Expression lambda is visited
   *
   * @param context the Context for the file being analyzed
   * @param lambda the lambda that's belonged to a Mapbox Expression builder
   * @param receiverClass the canonical class name of the Mapbox Expression builder
   */
  abstract fun visitMapboxExpressionLambda(
    context: JavaContext,
    lambda: ULambdaExpression,
    receiverClass: String
  )

  override fun getApplicableUastTypes(): List<Class<out UElement>>? {
    return listOf(
      ULambdaExpression::class.java
    )
  }

  override fun createUastHandler(context: JavaContext): UElementHandler? {
    return object : UElementHandler() {
      override fun visitLambdaExpression(node: ULambdaExpression) {
        val receiverType = node.getExpressionType()?.canonicalText
        for (name in ALL_EXPRESSION_CLASS_NAME_LIST) {
          if (receiverType?.contains(name) == true) {
            visitMapboxExpressionLambda(context, node, name)
          }
        }
      }
    }
  }

  companion object {
    const val EXPRESSION_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.ExpressionBuilder"
    const val INTERPOLATOR_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.InterpolatorBuilder"
    const val NUMBER_FORMAT_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.NumberFormatBuilder"
    const val FORMAT_SECTION_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.FormatSectionBuilder"
    const val FORMAT_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.FormatBuilder"
    const val COLLATOR_BUILDER_CLASS_NAME =
      "com.mapbox.maps.extension.style.expressions.generated.Expression.CollatorBuilder"
    private val ALL_EXPRESSION_CLASS_NAME_LIST = listOf(
      EXPRESSION_BUILDER_CLASS_NAME,
      INTERPOLATOR_BUILDER_CLASS_NAME,
      NUMBER_FORMAT_BUILDER_CLASS_NAME,
      FORMAT_SECTION_BUILDER_CLASS_NAME,
      FORMAT_BUILDER_CLASS_NAME,
      COLLATOR_BUILDER_CLASS_NAME
    )

    /**
     * Check if the class name is a generic expression class extends from
     * `com.mapbox.maps.extension.style.expressions.generated.Expression.ExpressionBuilder`.
     *
     * A generic expression contains all the expression DSL methods.
     */
    fun isGenericExpressionClass(name: String?): Boolean {
      return name == EXPRESSION_BUILDER_CLASS_NAME || name == INTERPOLATOR_BUILDER_CLASS_NAME
    }
  }
}