package com.mapbox.maps.lint.style.expressions

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestMode
import com.android.tools.lint.detector.api.TextFormat
import com.mapbox.maps.lint.style.Stubs
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class UnusedLiteralDetectorTest(
  private val testInput: String,
  private val expectedAutoFix: String
) {
  @Test
  fun testUnwrappedLiteralExpression() {
    TestLintTask
      .lint()
      .files(
        Stubs.expressionClassFile,
        TestFiles.kotlin(
          """
          package com.mapbox.maps.style.test
          
          import com.mapbox.maps.extension.style.expressions.generated.Expression

          val expression = Expression.concat {
            $testInput
          }
          val interpolateExpression = Expression.interpolate {
            $testInput
          }
        """.trimIndent()
        )
      )
      .issues(UnusedLiteralDetector.ISSUE)
      .skipTestModes(TestMode.UI_INJECTION_HOST)
      .run()
      .expectErrorCount(2)
      .expect(
        """
         |src/com/mapbox/maps/style/test/test.kt:6: Error: ${
          UnusedLiteralDetector.ISSUE.getExplanation(
            TextFormat.RAW
          )
        } [${UnusedLiteralDetector.ISSUE.id}]
         |  $testInput
         |  ${"~".repeat(testInput.length)}
         |src/com/mapbox/maps/style/test/test.kt:9: Error: ${
          UnusedLiteralDetector.ISSUE.getExplanation(
            TextFormat.RAW
          )
        } [${UnusedLiteralDetector.ISSUE.id}]
         |  $testInput
         |  ${"~".repeat(testInput.length)}
         |2 errors, 0 warnings
        """.trimMargin()
      )
      .expectFixDiffs(
        """
          Autofix for src/com/mapbox/maps/style/test/test.kt line 6: Wrap unused literal with literal():
          @@ -6 +6
          -   $testInput
          +   $expectedAutoFix
          Autofix for src/com/mapbox/maps/style/test/test.kt line 9: Wrap unused literal with literal():
          @@ -9 +9
          -   $testInput
          +   $expectedAutoFix
        """.trimIndent()
      )
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} -> {1}")
    fun data() = listOf(
      arrayOf(
        """
          "red"
        """.trimIndent(),
        """
          literal("red")
        """.trimIndent()
      ),
      arrayOf(
        """
          listOf("a", "b")
        """.trimIndent(),
        """
          literal(listOf("a", "b"))
        """.trimIndent()
      ),
      arrayOf(
        """
          "abc" + "def"
        """.trimIndent(),
        """
          literal("abc" + "def")
        """.trimIndent()
      ),
      arrayOf(
        """
          "abc".length
        """.trimIndent(),
        """
          literal(("abc".length).toLong())
        """.trimIndent()
      ),
      arrayOf(
        """
          1 + 2
        """.trimIndent(),
        """
          literal((1 + 2).toLong())
        """.trimIndent()
      ),
      arrayOf(
        """
          3+4
        """.trimIndent(),
        """
          literal((3 + 4).toLong())
        """.trimIndent()
      ),
      arrayOf(
        """
          1.0 + 2.0
        """.trimIndent(),
        """
          literal(1.0 + 2.0)
        """.trimIndent()
      ),
      arrayOf(
        """
          12
        """.trimIndent(),
        """
          literal(12)
        """.trimIndent()
      ),
      arrayOf(
        """
          1L
        """.trimIndent(),
        """
          literal(1)
        """.trimIndent()
      ),
      arrayOf(
        """
          0.1
        """.trimIndent(),
        """
          literal(0.1)
        """.trimIndent()
      ),
      arrayOf(
        """
          0.5f
        """.trimIndent(),
        """
          literal(0.5)
        """.trimIndent()
      ),
      arrayOf(
        """
          1.2.toFloat()
        """.trimIndent(),
        """
          literal((1.2.toFloat()).toDouble())
        """.trimIndent()
      ),

      arrayOf(
        """
          0.5f+123
        """.trimIndent(),
        """
          literal((0.5 + 123).toDouble())
        """.trimIndent()
      ),
      arrayOf(
        """
          false
        """.trimIndent(),
        """
          literal(false)
        """.trimIndent()
      ),
      arrayOf(
        """
          !true
        """.trimIndent(),
        """
          literal(!true)
        """.trimIndent()
      )
    )
  }
}