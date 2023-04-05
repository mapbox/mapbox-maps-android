package com.mapbox.maps.lint.expressions

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile

object Stubs {
  val expressionClassFile: TestFile = kotlin(
    """
      package com.mapbox.maps.extension.style.expressions.generated

      class Expression {
        class ExpressionBuilder(operator: String) {
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

        companion object {
            fun concat(block: ExpressionBuilder.() -> Unit): Expression {
              return Expression()
            }
        }
      }

    """.trimIndent()
  )
}