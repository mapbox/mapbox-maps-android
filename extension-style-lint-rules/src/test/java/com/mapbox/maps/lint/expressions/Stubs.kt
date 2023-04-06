package com.mapbox.maps.lint.expressions

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile

object Stubs {
  val expressionClassFile: TestFile = kotlin(
    """
      package com.mapbox.maps.extension.style.expressions.generated

      class Expression {
        open class ExpressionBuilder(operator: String) {
            /**
             * Provides a literal array or object value.
             */
            fun literal(value: Double) {
            }

            /**
             * Provides a literal array or object value.
             */
            fun literal(value: Long) {
            }

            /**
             * Provides a literal array or object value.
             */
            fun literal(value: Boolean) {
            }

            /**
             * Provides a literal array or object value.
             */
            fun literal(value: String) {
            }

            /**
             * Provides a literal array or object value.
             */
            fun literal(value: HashMap<String, Any>) {
            }

            /**
             * Provides a literal array or object value.
             */
            fun literal(value: List<Any>) {
            }
        }

        class InterpolatorBuilder(operator: String) : ExpressionBuilder(operator) {
        
        }

        companion object {
            fun concat(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Types https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#types
            fun array(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun image(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun toBoolean(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun toString(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun typeofExpression(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Feature data https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#feature-data
            fun featureState(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Lookup https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#lookup
            fun at(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun get(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun has(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun inExpression(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun indexOf(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun length(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun slice(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Decision https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#decision
            fun not(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun neq(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun less(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun lt(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun lte(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun eq(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun gt(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun gte(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun switchCase(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun within(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Ramps, scales, curves https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#ramps-scales-curves
            fun interpolate(block: InterpolatorBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun step(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Variable binding https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#variable-binding
            fun varExpression(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // String https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#string
            fun downcase(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun isSupportedScript(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun resolvedLocale(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun upcase(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Color https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#color
            fun rgb(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun rgba(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun toRgba(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // Math https://docs.mapbox.com/mapbox-gl-js/style-spec/expressions/#math
            fun subtract(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun division(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun mod(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun pow(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun abs(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun acos(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun asin(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun atan(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun ceil(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun cos(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun distance(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun floor(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun ln(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun log10(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun log2(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun round(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun sin(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun sqrt(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            fun tan(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
            // stop expression that groups 2 argument as a pair
            fun stop(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
        }
      }

    """.trimIndent()
  )
}