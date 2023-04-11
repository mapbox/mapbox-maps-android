package com.mapbox.maps.lint.style.expressions

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULambdaExpression

/**
 * A detector that scans through the source code and find the lambdas that applies to the ExpressionBuilder,
 * find the lambda's parent function name as the expression name, and then loop through direct expression
 * function calls to the ExpressionBuilder under the lambda, and count the number of arguments for the parent
 * Mapbox expression. In the end, we compares it to the expected number of arguments map, and report errors
 * for the incorrect number of arguments for the given expression.
 */
class IllegalNumberOfArgumentsDetector : MapboxExpressionDslDetector() {
  override fun visitMapboxExpressionLambda(
    context: JavaContext,
    lambda: ULambdaExpression,
    receiverClass: String
  ) {
    if (isGenericExpressionClass(receiverClass)) {
      validateNumberOfArgumentsInExpression(context, lambda)
    }
  }

  private fun validateNumberOfArgumentsInExpression(
    context: JavaContext,
    lambda: ULambdaExpression
  ) {
    // uncomment to print the expression tree within the lambda for debugging.
    // println("validateNumberOfArgumentsInExpression: ${lambda.asSourceString()}")
    val parentExpression = lambda.uastParent as? UCallExpression
    val parentExpressionName = parentExpression?.methodName
    val body = lambda.body
    if (body is UBlockExpression) {
      val argumentCount = countNumberOfArguments(body)
      // println("*** Number of argument for $parentExpressionName: $argumentCount")
      if (parentExpressionName != null && !validateNumberOfArguments(
          expressionName = parentExpressionName,
          argumentCount = argumentCount
        )
      ) {
        context.report(
          issue = ISSUE,
          scope = parentExpression,
          location = context.getLocation(parentExpression),
          message = getReportMessage(parentExpressionName, argumentCount),
        )
      }
    }
  }

  private fun countNumberOfArguments(blockExpression: UBlockExpression): Int {
    return blockExpression.expressions.filterIsInstance<UCallExpression>().filter {
      isGenericExpressionClass(it.receiverType?.canonicalText)
    }.size
  }

  companion object {
    private const val ISSUE_ID = "IncorrectNumberOfArgumentsInExpression"
    private const val BRIEF_DESCRIPTION =
      "This check detects incorrect number of expressions within a code block that's applied to a given expression."
    private const val PRIORITY = 8

    private const val REPORT_MESSAGE = "Incorrect number of arguments for this Expression."

    private val IMPLEMENTATION = Implementation(
      IllegalNumberOfArgumentsDetector::class.java, Scope.JAVA_FILE_SCOPE
    )

    // A map of expression name and the expected number of arguments for this expression
    // * Use -1 to represent odd number is required
    // * Use -2 to represent even number is required
    internal const val ODD_NUMBER = -1
    internal const val EVEN_NUMBER = -2
    internal val ARGUMENT_COUNT_MAP = hashMapOf(
      // Types https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#types
      "array" to listOf(1, 2, 3),
      // Ignore collator checker here, as we provide a specific CollatorBuilder
      // "collator" to listOf(1),
      "image" to listOf(1),
      // Ignore numberFormat checker here, as we provide a specific NumberFormatBuilder
      // "numberFormat" to listOf(2),
      "toBoolean" to listOf(1),
      "toString" to listOf(1),
      "typeofExpression" to listOf(1),
      // Feature data https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#feature-data
      "featureState" to listOf(1),
      // Lookup https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#lookup
      "at" to listOf(2),
      "get" to listOf(1, 2),
      "has" to listOf(1, 2),
      "inExpression" to listOf(2),
      "indexOf" to listOf(2, 3),
      "length" to listOf(1),
      "slice" to listOf(2, 3),
      // Decision https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#decision
      "not" to listOf(1),
      "neq" to listOf(2, 3),
      "less" to listOf(2, 3),
      "lt" to listOf(2, 3),
      "lte" to listOf(2, 3),
      "eq" to listOf(2, 3),
      "gt" to listOf(2, 3),
      "gte" to listOf(2, 3),
      "switchCase" to listOf(ODD_NUMBER),
      "within" to listOf(1),
      // Ramps, scales, curves https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#ramps-scales-curves
      "interpolate" to listOf(EVEN_NUMBER),
      "step" to listOf(EVEN_NUMBER),
      // Variable binding https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#variable-binding
      "varExpression" to listOf(1),
      // String https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#string
      "downcase" to listOf(1),
      "isSupportedScript" to listOf(1),
      "resolvedLocale" to listOf(1),
      "upcase" to listOf(1),
      // Color https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#color
      "rgb" to listOf(3),
      "rgba" to listOf(4),
      "toRgba" to listOf(1),
      // Math https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#math
      "subtract" to listOf(1, 2),
      "division" to listOf(2),
      "mod" to listOf(2),
      "pow" to listOf(2),
      "abs" to listOf(1),
      "acos" to listOf(1),
      "asin" to listOf(1),
      "atan" to listOf(1),
      "ceil" to listOf(1),
      "cos" to listOf(1),
      "distance" to listOf(1),
      "floor" to listOf(1),
      "ln" to listOf(1),
      "log10" to listOf(1),
      "log2" to listOf(1),
      "round" to listOf(1),
      "sin" to listOf(1),
      "sqrt" to listOf(1),
      "tan" to listOf(1),
      // stop expression that groups 2 argument as a pair
      "stop" to listOf(2),
    )

    private fun validateNumberOfArguments(expressionName: String, argumentCount: Int): Boolean {
      val expectedNumberOfArguments: List<Int> = ARGUMENT_COUNT_MAP[expressionName] ?: return true
      return if (expectedNumberOfArguments.first() >= 0) {
        argumentCount in expectedNumberOfArguments
      } else if (expectedNumberOfArguments.first() == ODD_NUMBER) {
        argumentCount % 2 == 1
      } else if (expectedNumberOfArguments.first() == EVEN_NUMBER) {
        argumentCount % 2 == 0
      } else {
        // If the expectedNumberOfArguments is below -2, it's undefined use case, silently skip
        true
      }
    }

    internal fun getReportMessage(
      expressionName: String,
      actualNumberOfArguments: Int,
    ): String {
      val expectedNumberOfArguments = ARGUMENT_COUNT_MAP[expressionName]
      val expectedString = if (expectedNumberOfArguments?.first() == ODD_NUMBER) {
        "an odd number"
      } else if (expectedNumberOfArguments?.first() == EVEN_NUMBER) {
        "an even number"
      } else if (expectedNumberOfArguments?.isNotEmpty() == true) {
        expectedNumberOfArguments.joinToString(separator = " or ")
      } else {
        "unknown"
      }
      return "Incorrect number of expressions within $expressionName Expression: expected $expectedString, but $actualNumberOfArguments was found."
    }

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