package com.mapbox.maps.lint.style.expressions

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestMode
import com.mapbox.maps.lint.style.Stubs
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class IllegalNumberOfArgumentsDetectorTest(
  private val testExpressionName: String,
  private val testNumberOfArguments: Int,
  private val expectError: Boolean,
) {
  @Test
  fun testIllegalNumberOfArgument() {
    val testContent =
      List(testNumberOfArguments) { "literal(0)" }.joinToString(separator = "\n            ")
    val testLintResult = TestLintTask
      .lint()
      .files(
        Stubs.expressionClassFile,
        TestFiles.kotlin(
          """
          package com.mapbox.maps.style.test
          
          import com.mapbox.maps.extension.style.expressions.generated.Expression

          val expression = Expression.$testExpressionName {
            $testContent
          }
        """.trimIndent()
        )
      )
      .issues(IllegalNumberOfArgumentsDetector.ISSUE)
      .skipTestModes(TestMode.UI_INJECTION_HOST, TestMode.PARENTHESIZED)
      .run()

    if (expectError) {
      testLintResult.expectErrorCount(1)
        .expect(
          """
         |src/com/mapbox/maps/style/test/test.kt:5: Error: ${
            IllegalNumberOfArgumentsDetector.getReportMessage(
              testExpressionName,
              testNumberOfArguments
            )
          } [${IllegalNumberOfArgumentsDetector.ISSUE.id}]
         |val expression = Expression.$testExpressionName {
         |                 ^
         |1 errors, 0 warnings
        """.trimMargin()
        )
    } else {
      testLintResult.expectClean()
    }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} with {1} arguments should be warned?({2})")
    fun data(): List<Array<Any>> {
      val mutableList = mutableListOf<Array<Any>>()
      // Add illegal test cases
      mutableList.addAll(
        IllegalNumberOfArgumentsDetector
          .ARGUMENT_COUNT_MAP
          .entries
          .map {
            // produce illegal number of arguments
            if (it.value.first() > 0) {
              arrayOf(it.key, it.value.last() + 1, true)
            } else if (it.value.first() == IllegalNumberOfArgumentsDetector.ODD_NUMBER) {
              arrayOf(it.key, 4, true)
            } else if (it.value.first() == IllegalNumberOfArgumentsDetector.EVEN_NUMBER) {
              arrayOf(it.key, 5, true)
            } else {
              arrayOf()
            }
          }
      )
      // Add legal test cases
      mutableList.addAll(
        IllegalNumberOfArgumentsDetector
          .ARGUMENT_COUNT_MAP
          .entries
          .map {
            // produce legal number of arguments
            if (it.value.first() > 0) {
              arrayOf(it.key, it.value.last(), false)
            } else if (it.value.first() == IllegalNumberOfArgumentsDetector.ODD_NUMBER) {
              arrayOf(it.key, 5, false)
            } else if (it.value.first() == IllegalNumberOfArgumentsDetector.EVEN_NUMBER) {
              arrayOf(it.key, 4, false)
            } else {
              arrayOf()
            }
          }
      )
      return mutableList
    }
  }
}