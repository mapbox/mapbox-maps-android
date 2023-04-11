package com.mapbox.maps.lint.style

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestMode
import com.android.tools.lint.detector.api.TextFormat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class UnusedStyleDslDetectorTest(
  private val testInput: String,
  private val expectedAutoFix: String
) {
  @Test
  fun testUnusedStyleDslBlock() {
    val testSource = """
          package com.mapbox.maps.style.test
          
          import com.mapbox.maps.extension.style.style
          import com.mapbox.maps.extension.style.atmosphere.generated.Atmosphere
          import com.mapbox.maps.extension.style.layers.generated.*
          import com.mapbox.maps.extension.style.light.generated.Light
          import com.mapbox.maps.extension.style.projection.generated.Projection
          import com.mapbox.maps.extension.style.sources.*
          import com.mapbox.maps.extension.style.sources.generated.*
          import com.mapbox.maps.extension.style.terrain.generated.Terrain
          import com.mapbox.maps.extension.style.image.ImageExtensionImpl
          import com.mapbox.maps.extension.style.image.ImageNinePatchExtensionImpl

          val testStyle = style("style-id") {
            $testInput
          }
        """.trimIndent()
    TestLintTask
      .lint()
      .files(
        *Stubs.styleDslTestFiles,
        TestFiles.kotlin(testSource)
      )
      .issues(UnusedStyleDslDetector.ISSUE)
      .skipTestModes(TestMode.UI_INJECTION_HOST)
      .run()
      .expectErrorCount(1)
      .expect(
        """
         |src/com/mapbox/maps/style/test/test.kt:15: Error: ${
          UnusedStyleDslDetector.ISSUE.getExplanation(
            TextFormat.RAW
          )
        } [${UnusedStyleDslDetector.ISSUE.id}]
         |  $testInput
         |  ${"~".repeat(testInput.length)}
         |1 errors, 0 warnings
        """.trimMargin()
      )
      .expectFixDiffs(
        """
          Autofix for src/com/mapbox/maps/style/test/test.kt line 15: Append with unaryPlus(+) operator:
          @@ -15 +15
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
          SymbolLayer("layerId", "sourceId")
        """.trimIndent(),
        """
          +SymbolLayer("layerId", "sourceId")
        """.trimIndent()
      ),
      arrayOf(
        """
          GeoJsonSource("sourceId")
        """.trimIndent(),
        """
          +GeoJsonSource("sourceId")
        """.trimIndent()
      ),
      arrayOf(
        """
          Light()
        """.trimIndent(),
        """
          +Light()
        """.trimIndent()
      ),
      arrayOf(
        """
          Atmosphere()
        """.trimIndent(),
        """
          +Atmosphere()
        """.trimIndent()
      ),
      arrayOf(
        """
          Terrain()
        """.trimIndent(),
        """
          +Terrain()
        """.trimIndent()
      ),
      arrayOf(
        """
          Projection("GLOBE")
        """.trimIndent(),
        """
          +Projection("GLOBE")
        """.trimIndent()
      ),
      arrayOf(
        """
          ImageExtensionImpl()
        """.trimIndent(),
        """
          +ImageExtensionImpl()
        """.trimIndent()
      ),
      arrayOf(
        """
          ImageNinePatchExtensionImpl()
        """.trimIndent(),
        """
          +ImageNinePatchExtensionImpl()
        """.trimIndent()
      ),
      arrayOf(
        """
          layerAtPosition(SymbolLayer("layerId", "sourceId"), above = "aboveId")
        """.trimIndent(),
        """
          +layerAtPosition(SymbolLayer("layerId", "sourceId"), above = "aboveId")
        """.trimIndent()
      )
    )
  }
}