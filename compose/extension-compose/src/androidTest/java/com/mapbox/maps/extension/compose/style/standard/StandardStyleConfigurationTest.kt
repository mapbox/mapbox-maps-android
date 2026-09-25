package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.mapbox.maps.Style
import com.mapbox.maps.coroutine.awaitStyle
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.StringValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

public class StandardStyleConfigurationTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testComprehensiveMapboxStandardStyleWithAllParameters() {
    val configVerificationLatch = CountDownLatch(1)

    createMapAndVerifyStyle(
      styleContent = {
        val standardStyleState = rememberStandardStyleState()
        standardStyleState.configurationsState.apply {
          // ALL Enum parameters from schema
          theme = ThemeValue("faded")
          lightPreset = LightPresetValue.NIGHT
          backgroundPointOfInterestLabels = BackgroundPointOfInterestLabelsValue.NONE
          colorModePointOfInterestLabels = ColorModePointOfInterestLabelsValue.SINGLE

          // ALL String parameters from schema
          font = StringValue("Montserrat")
          themeData = StringValue("custom-theme")

          // ALL Boolean parameters from schema
          showPointOfInterestLabels = BooleanValue(false)
          showTransitLabels = BooleanValue(false)
          showPlaceLabels = BooleanValue(true)
          showRoadLabels = BooleanValue(true)
          showPedestrianRoads = BooleanValue(false)
          show3dObjects = BooleanValue(false)
          showAdminBoundaries = BooleanValue(false)
          showLandmarkIconLabels = BooleanValue(false)
          showLandmarkIcons = BooleanValue(true)

          // ALL Color parameters from schema
          colorAdminBoundaries = ColorValue(Color.Red)
          colorBuildingHighlight = ColorValue(Color.Green)
          colorBuildingSelect = ColorValue(Color.Blue)
          colorGreenspace = ColorValue(Color(0xFF228B22))
          colorMotorways = ColorValue(Color.Yellow)
          colorPlaceLabelHighlight = ColorValue(Color.Magenta)
          colorPlaceLabels = ColorValue(Color.Black)
          colorPlaceLabelSelect = ColorValue(Color.Cyan)
          colorPointOfInterestLabels = ColorValue(Color.Gray)
          colorRoadLabels = ColorValue(Color.White)
          colorRoads = ColorValue(Color(0xFF696969))
          colorTrunks = ColorValue(Color(0xFFA0A0A0))
          colorWater = ColorValue(Color(0xFF87CEEB))

          // ALL Number parameters from schema
          densityPointOfInterestLabels = DoubleValue(5.0)
          roadsBrightness = DoubleValue(0.8)
        }
        MapboxStandardStyle(standardStyleState = standardStyleState)
      },
      verification = { style ->
        // Get ALL configuration properties at once
        val allPropertiesResult = style.getStyleImportConfigProperties("basemap")
        Assert.assertTrue("Should be able to get config properties", allPropertiesResult.isValue)

        val allProperties = allPropertiesResult.value!! as Map<String, *>

        // Define expected properties with their expected values
        val expectedProperties = mapOf(
          // Enum values
          "theme" to "faded",
          "lightPreset" to "night",
          "backgroundPointOfInterestLabels" to "none",
          "colorModePointOfInterestLabels" to "single",

          // String values
          "font" to "Montserrat",
          "theme-data" to "custom-theme",

          // Boolean values
          "showPointOfInterestLabels" to false,
          "showTransitLabels" to false,
          "showPlaceLabels" to true,
          "showRoadLabels" to true,
          "showPedestrianRoads" to false,
          "show3dObjects" to false,
          "showAdminBoundaries" to false,
          "showLandmarkIconLabels" to false,
          "showLandmarkIcons" to true,

          // Number values
          "densityPointOfInterestLabels" to 5.0,
          "roadsBrightness" to 0.8
        )

        // Color properties with their expected RGBA string values
        val expectedColorProperties = mapOf(
          "colorAdminBoundaries" to "rgba(255, 0, 0, 1)", // Color.Red
          "colorBuildingHighlight" to "rgba(0, 255, 0, 1)", // Color.Green
          "colorBuildingSelect" to "rgba(0, 0, 255, 1)", // Color.Blue
          "colorGreenspace" to "rgba(34, 139, 34, 1)", // Color(0xFF228B22) - Forest Green
          "colorMotorways" to "rgba(255, 255, 0, 1)", // Color.Yellow
          "colorPlaceLabelHighlight" to "rgba(255, 0, 255, 1)", // Color.Magenta
          "colorPlaceLabels" to "rgba(0, 0, 0, 1)", // Color.Black
          "colorPlaceLabelSelect" to "rgba(0, 255, 255, 1)", // Color.Cyan
          "colorPointOfInterestLabels" to "rgba(136, 136, 136, 1)", // Color.Gray
          "colorRoadLabels" to "rgba(255, 255, 255, 1)", // Color.White
          "colorRoads" to "rgba(105, 105, 105, 1)", // Color(0xFF696969) - Dim Gray
          "colorTrunks" to "rgba(160, 160, 160, 1)", // Color(0xFFA0A0A0) - Dark Gray
          "colorWater" to "rgba(135, 206, 235, 1)" // Color(0xFF87CEEB) - Sky Blue
        )

        // Verify all expected properties are set with correct values
        expectedProperties.forEach { (key, expectedValue) ->
          Assert.assertTrue("Property '$key' should be present", allProperties.containsKey(key))
          val stylePropertyValue = allProperties[key] as com.mapbox.maps.StylePropertyValue
          val actualValue = stylePropertyValue.value.contents
          Assert.assertEquals("Property '$key' should have correct value", expectedValue, actualValue)
        }

        // Verify all color properties are present and have correct RGBA string values
        expectedColorProperties.forEach { (colorProperty, expectedRgba) ->
          Assert.assertTrue("Color property '$colorProperty' should be present", allProperties.containsKey(colorProperty))
          val stylePropertyValue = allProperties[colorProperty] as com.mapbox.maps.StylePropertyValue
          val actualColorString = stylePropertyValue.value.contents as String
          Assert.assertEquals(
            "Color '$colorProperty' should have correct RGBA value",
                             expectedRgba, actualColorString
          )
        }

        // Verify we have all expected properties (should be 26 total for Standard style)
        val totalExpectedProperties = expectedProperties.size + expectedColorProperties.size
        Assert.assertEquals(
          "Should have all $totalExpectedProperties configuration properties",
                           totalExpectedProperties, allProperties.size
        )

        configVerificationLatch.countDown()
      }
    )

    Assert.assertTrue("Configuration verification should complete", configVerificationLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
  }

  @Test
  public fun testComprehensiveMapboxStandardSatelliteStyleWithAllParameters() {
    val configVerificationLatch = CountDownLatch(1)

    createMapAndVerifyStyle(
      styleContent = {
        val standardSatelliteStyleState = rememberStandardSatelliteStyleState()
        standardSatelliteStyleState.configurationsState.apply {
          // ALL StandardSatellite Enum parameters from schema
          lightPreset = LightPresetValue.DAWN
          backgroundPointOfInterestLabels = BackgroundPointOfInterestLabelsValue.CIRCLE
          colorModePointOfInterestLabels = ColorModePointOfInterestLabelsValue.DEFAULT_COLOR

          // ALL StandardSatellite String parameters from schema
          font = StringValue("Roboto")

          // ALL StandardSatellite Boolean parameters from schema (including satellite-specific ones)
          showPointOfInterestLabels = BooleanValue(false)
          showTransitLabels = BooleanValue(false)
          showPlaceLabels = BooleanValue(true)
          showRoadLabels = BooleanValue(true)
          showRoadsAndTransit = BooleanValue(false) // This is satellite-specific
          showPedestrianRoads = BooleanValue(false)
          showAdminBoundaries = BooleanValue(true)

          // ALL StandardSatellite Color parameters from schema
          colorAdminBoundaries = ColorValue(Color.Cyan)
          colorMotorways = ColorValue(Color.Magenta)
          colorPlaceLabelHighlight = ColorValue(Color.Yellow)
          colorPlaceLabels = ColorValue(Color.Black)
          colorPlaceLabelSelect = ColorValue(Color.Red)
          colorPointOfInterestLabels = ColorValue(Color.Gray)
          colorRoadLabels = ColorValue(Color.White)
          colorRoads = ColorValue(Color(0xFF808080))
          colorTrunks = ColorValue(Color(0xFF90EE90))

          // ALL StandardSatellite Number parameters from schema
          densityPointOfInterestLabels = DoubleValue(2.0)
          roadsBrightness = DoubleValue(0.5)
        }
        MapboxStandardSatelliteStyle(standardSatelliteStyleState = standardSatelliteStyleState)
      },
      verification = { style ->
        // Get ALL configuration properties at once
        val allPropertiesResult = style.getStyleImportConfigProperties("basemap")
        Assert.assertTrue("Should be able to get config properties", allPropertiesResult.isValue)

        val allProperties = allPropertiesResult.value!! as Map<String, *>

        // Define expected properties with their expected values for StandardSatellite
        val expectedProperties = mapOf(
          // Enum values
          "lightPreset" to "dawn",
          "backgroundPointOfInterestLabels" to "circle",
          "colorModePointOfInterestLabels" to "default",

          // String values
          "font" to "Roboto",

          // Boolean values (including satellite-specific ones)
          "showPointOfInterestLabels" to false,
          "showTransitLabels" to false,
          "showPlaceLabels" to true,
          "showRoadLabels" to true,
          "showRoadsAndTransit" to false, // This is satellite-specific
          "showPedestrianRoads" to false,
          "showAdminBoundaries" to true,

          // Number values
          "densityPointOfInterestLabels" to 2.0,
          "roadsBrightness" to 0.5
        )

        // Color properties for StandardSatellite with their expected RGBA string values
        val expectedColorProperties = mapOf(
          "colorAdminBoundaries" to "rgba(0, 255, 255, 1)", // Color.Cyan
          "colorMotorways" to "rgba(255, 0, 255, 1)", // Color.Magenta
          "colorPlaceLabelHighlight" to "rgba(255, 255, 0, 1)", // Color.Yellow
          "colorPlaceLabels" to "rgba(0, 0, 0, 1)", // Color.Black
          "colorPlaceLabelSelect" to "rgba(255, 0, 0, 1)", // Color.Red
          "colorPointOfInterestLabels" to "rgba(136, 136, 136, 1)", // Color.Gray
          "colorRoadLabels" to "rgba(255, 255, 255, 1)", // Color.White
          "colorRoads" to "rgba(128, 128, 128, 1)", // Color(0xFF808080) - Gray
          "colorTrunks" to "rgba(144, 238, 144, 1)" // Color(0xFF90EE90) - Light Green
        )

        // Verify all expected properties are set with correct values
        expectedProperties.forEach { (key, expectedValue) ->
          Assert.assertTrue("Property '$key' should be present", allProperties.containsKey(key))
          val stylePropertyValue = allProperties[key] as com.mapbox.maps.StylePropertyValue
          val actualValue = stylePropertyValue.value.contents
          Assert.assertEquals("Property '$key' should have correct value", expectedValue, actualValue)
        }

        // Verify all color properties are present and have correct RGBA string values
        expectedColorProperties.forEach { (colorProperty, expectedRgba) ->
          Assert.assertTrue("Color property '$colorProperty' should be present", allProperties.containsKey(colorProperty))
          val stylePropertyValue = allProperties[colorProperty] as com.mapbox.maps.StylePropertyValue
          val actualColorString = stylePropertyValue.value.contents as String
          Assert.assertEquals(
            "Color '$colorProperty' should have correct RGBA value",
                             expectedRgba, actualColorString
          )
        }

        // Verify we have all expected properties (should be 22 total for StandardSatellite style)
        val totalExpectedProperties = expectedProperties.size + expectedColorProperties.size
        Assert.assertEquals(
          "Should have all $totalExpectedProperties configuration properties",
                           totalExpectedProperties, allProperties.size
        )

        configVerificationLatch.countDown()
      }
    )

    Assert.assertTrue("Configuration verification should complete", configVerificationLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
  }

  private fun createMapAndVerifyStyle(
    styleContent: @Composable () -> Unit,
    verification: (Style) -> Unit
  ) {
    val mapLoadLatch = CountDownLatch(1)

    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          Modifier
            .fillMaxSize()
            .testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(HELSINKI)
            }
          },
          mapState = rememberMapState(),
          style = styleContent
        ) {
          MapEffect(Unit) {
            it.mapboxMap.apply {
              awaitStyle()
              // Verify configuration after style is loaded
              verification(style!!)
              mapLoadLatch.countDown()
            }
          }
        }
      }
    }

    composeTestRule.waitForIdle()
    Assert.assertTrue("Map should load within timeout", mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
  }

  private companion object {
    private const val ZOOM: Double = 9.0
    private const val MAP_TEST_TAG = "map_tag"
    private const val TEST_TIMEOUT_MS = 10000L
  }
}