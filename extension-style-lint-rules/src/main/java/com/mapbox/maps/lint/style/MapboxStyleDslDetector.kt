package com.mapbox.maps.lint.style

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULambdaExpression

/**
 * A detector to scan through the source code and find the lambdas that applies to the Style DSL builder.
 */
abstract class MapboxStyleDslDetector : Detector(), Detector.UastScanner {

  /**
   * Called when a Mapbox Style DSL lambda is visited
   *
   * @param context the Context for the file being analyzed
   * @param lambda the lambda that's belonged to a Mapbox Style DSL builder
   * @param receiverClass the canonical class name of the Mapbox Style DSL builder
   */
  abstract fun visitMapboxStyleDslLambda(
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
        if (receiverType?.contains(STYLE_DSL_RECEIVER_NAME) == true) {
          visitMapboxStyleDslLambda(
            context, node, STYLE_DSL_RECEIVER_NAME
          )
        }
      }
    }
  }

  companion object {
    private const val STYLE_DSL_RECEIVER_NAME =
      "com.mapbox.maps.extension.style.StyleExtensionImpl.Builder"
  }
}