package com.mapbox.maps.lint.expressions

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.ULiteralExpression

/**
 * A detector to scan through the source code and find the lambdas that applies to the ExpressionBuilder,
 * then loop through direct expressions under the lambda and analyse the expression's return type,
 * if the return type is one of supported literal types, we suggest user to wrap it inside the literal().
 *
 * We also suggest user to convert the int to long to match literal expression's signature.
 */
class UnusedLiteralDetector : MapboxExpressionDetector() {
  override fun visitMapboxExpressionLambda(
    context: JavaContext,
    lambda: ULambdaExpression,
    receiverClass: String
  ) {
    // We only care about ExpressionBuilder and InterpolatorBuilder here, as literal is only available
    // under ExpressionBuilder, and InterpolatorBuilder extends ExpressionBuilder.
    if (isGenericExpressionClass(receiverClass)) {
      checkUnusedLiteralsInLambda(context, lambda)
    }
  }

  private fun checkUnusedLiteralsInLambda(context: JavaContext, lambda: ULambdaExpression) {
    // uncomment to print the expression tree within the lambda for debugging.
    //    println("checkUnusedLiteralsInLambda: ${lambda.asRecursiveLogString()}")
    val blockExpression = lambda.body as? UBlockExpression
    blockExpression?.let {
      for (expression in blockExpression.expressions) {
        when (val type = expression.getExpressionType()?.canonicalText) {
          "int" -> {
            // For int return type, we need to convert it to Long(supported literal expression)
            val replaceWith = if (expression is ULiteralExpression) {
              "literal(${expression.asSourceString()})"
            } else {
              "literal((${expression.asSourceString()}).toLong())"
            }
            context.reportIssue(
              expression = expression,
              replaceWith = replaceWith
            )
          }
          "float" -> {
            // For float return type, we need to convert it to Double(supported literal expression)
            val replaceWith = if (expression is ULiteralExpression) {
              "literal(${expression.asSourceString()})"
            } else {
              "literal((${expression.asSourceString()}).toDouble())"
            }
            context.reportIssue(
              expression = expression,
              replaceWith = replaceWith
            )
          }
          "long", "double", "java.lang.String", "boolean" -> {
            // For string literal expression like "red", context.getLocation(expression) will return
            // the location of red without "", to bypass this issue, we need to extend the range by
            // extending the range and offset it by 1.
            val location = if (expression is ULiteralExpression && expression.isString) {
              context.getRangeLocation(expression, -1, expression.asSourceString().length)
            } else {
              context.getLocation(expression)
            }
            context.reportIssue(
              expression = expression,
              location = location
            )
          }
          "kotlin.Unit" -> {
            // no-ops, as the expression returns nothing
          }
          else -> {
            if (type?.startsWith("java.util.List") == true) {
              context.reportIssue(
                expression = expression
              )
            }
          }
        }
      }
    }
  }

  private fun JavaContext.reportIssue(
    expression: UExpression,
    location: Location = getLocation(expression),
    replaceWith: String = "literal(${expression.asSourceString()})"
  ) {
    report(
      issue = ISSUE,
      scope = expression,
      location = location,
      message = REPORT_MESSAGE,
      quickfixData = LintFix.create()
        .name("Wrap unused literal with literal()")
        .replace()
        .range(location)
        .with(replaceWith)
        .reformat(true)
        .autoFix()
        .build()
    )
  }

  companion object {
    private const val ISSUE_ID = "IllegalExpressionLiteralUsage"
    private const val BRIEF_DESCRIPTION =
      "This check detects unused literal expressions within a code block that's applied to a Expression.ExpressionBuilder class."
    private const val PRIORITY = 8
    private const val REPORT_MESSAGE =
      "Unused literal expression within ExpressionBuilder, wrap it within literal()."

    private val IMPLEMENTATION = Implementation(
      UnusedLiteralDetector::class.java, Scope.JAVA_FILE_SCOPE
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