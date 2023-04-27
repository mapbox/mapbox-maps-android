package com.mapbox.maps.lint.style

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*

/**
 * A detector to scan through the source code and find the lambdas that applies to the Style DSL builder.
 * And find if a layer/source/light/terrain/atmosphere/projection/layer & position pair is present but
 * not added to the style.
 *
 * And it also suggest auto fix to add it to the style by prefixing `+` operator.
 */
class UnusedStyleDslDetector : MapboxStyleDslDetector() {
  override fun visitMapboxStyleDslLambda(
    context: JavaContext,
    lambda: ULambdaExpression,
    receiverClass: String
  ) {
    checkUnusedStyleDsl(context, lambda)
  }

  private fun checkUnusedStyleDsl(context: JavaContext, lambda: ULambdaExpression) {
    // uncomment to print the expression tree within the lambda for debugging.
    // println("checkUnusedLiteralsInLambda: ${lambda.asRecursiveLogString()}")
    val blockExpression = lambda.body as? UBlockExpression
    blockExpression?.let {
      for (expression in blockExpression.expressions) {
        val type = expression.getExpressionType()?.canonicalText
        type?.let {
          if (isUnusedInStyleDsl(it)) {
            context.reportIssue(
              expression = expression,
            )
          }
        }
      }
    }
  }

  private fun JavaContext.reportIssue(
    expression: UExpression,
  ) {
    report(
      issue = ISSUE,
      scope = expression,
      location = getLocation(expression),
      message = REPORT_MESSAGE,
      quickfixData = LintFix.create()
        .name("Append with unaryPlus(+) operator")
        .replace()
        .range(getRangeLocation(from = expression, fromDelta = 0, length = 1))
        .with("+" + getFirstCharOfExpression(expression))
        .reformat(true)
        .autoFix()
        .build()
    )
  }

  private fun getFirstCharOfExpression(expression: UExpression): Char {
    val elementSource = when (expression) {
      is UCallExpression -> {
        expression.methodIdentifier?.name?.first() ?: expression.asSourceString().first()
      }
      else -> {
        expression.asSourceString().first()
      }
    }
    return elementSource
  }

  companion object {
    private const val ISSUE_ID = "UnusedBlockInStyleDsl"
    private const val BRIEF_DESCRIPTION =
      "This check detects unused block inside Style DSL."
    private const val PRIORITY = 8
    private const val REPORT_MESSAGE =
      "Unused block within Style DSL, consider adding it to the style using unaryPlus(+) operator."

    private val LAYER_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.layers\\.generated.*Layer")
    private val SOURCE_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.sources\\.generated.*Source")
    private val LIGHT_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.light\\.generated\\.Light")
    private val TERRAIN_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.terrain\\.generated\\.Terrain")
    private val PROJECTION_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.projection\\.generated\\.Projection")
    private val ATMOSPHERE_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.atmosphere\\.generated\\.Atmosphere")
    private val IMAGE_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.image\\.ImageExtensionImpl")
    private val IMAGE_NINE_PATCH_CLASS_NAME_PATTERN =
      Regex("com\\.mapbox\\.maps\\.extension\\.style\\.image\\.ImageNinePatchExtensionImpl")
    private val LAYER_POSITION_PAIR_PATTERN =
      Regex("kotlin\\.Pair<\\? extends com\\.mapbox\\.maps\\.extension\\.style\\.layers\\.Layer,\\? extends com\\.mapbox\\.maps\\.LayerPosition>")
    private val ALL_STYLE_DSL_CLASS_NAME_PATTERNS = listOf(
      LAYER_CLASS_NAME_PATTERN,
      SOURCE_CLASS_NAME_PATTERN,
      LIGHT_CLASS_NAME_PATTERN,
      TERRAIN_CLASS_NAME_PATTERN,
      ATMOSPHERE_CLASS_NAME_PATTERN,
      PROJECTION_CLASS_NAME_PATTERN,
      IMAGE_CLASS_NAME_PATTERN,
      IMAGE_NINE_PATCH_CLASS_NAME_PATTERN,
      LAYER_POSITION_PAIR_PATTERN
    )

    private fun isUnusedInStyleDsl(className: String): Boolean {
      return ALL_STYLE_DSL_CLASS_NAME_PATTERNS.any { it.matches(className) }
    }

    private val IMPLEMENTATION = Implementation(
      UnusedStyleDslDetector::class.java, Scope.JAVA_FILE_SCOPE
    )

    @JvmField
    val ISSUE = Issue.create(
      id = ISSUE_ID,
      briefDescription = BRIEF_DESCRIPTION,
      explanation = REPORT_MESSAGE,
      category = Category.CORRECTNESS,
      priority = PRIORITY,
      severity = Severity.FATAL,
      implementation = IMPLEMENTATION
    ).setAndroidSpecific(true)
  }
}