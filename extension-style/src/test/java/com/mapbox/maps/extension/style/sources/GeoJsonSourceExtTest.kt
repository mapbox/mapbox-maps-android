package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import com.mapbox.geojson.Feature
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.ShadowValueConverter
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class])
class GeoJsonSourceExtTest {
  private val style = mockk<StyleManagerInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
  private val expectedByte = mockk<Expected<Byte, String>>(relaxUnitFun = true, relaxed = true)
  private val expectedFeatureList =
    mockk<Expected<MutableList<Feature>, String>>(relaxUnitFun = true, relaxed = true)

  private val feature = Feature.fromJson(
    """
        {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [102.0, 0.5]
          },
          "properties": {
                  "prop0": "value0"
                }
        }
    """.trimIndent()
  )

  @Before
  fun prepareTest() {
    mockkStatic(ValueConverter::class)
    every { ValueConverter.fromJson(any()) } returns ExpectedFactory.createValue<Value, String>(
      Value(1)
    )

    every { style.addStyleSource(any(), any()) } returns expected
    every { style.updateStyleImageSourceImage(any(), any()) } returns expected
    every { style.getStyleGeoJSONSourceClusterChildren(any(), any()) } returns expectedFeatureList
    every {
      style.getStyleGeoJSONSourceClusterLeaves(
        any(),
        any(),
        any(),
        any()
      )
    } returns expectedFeatureList
    every { style.getStyleGeoJSONSourceClusterExpansionZoom(any(), any()) } returns expectedByte
    every { expected.error } returns null
    every { expectedByte.error } returns null
    every { expectedFeatureList.error } returns null
  }

  @Test
  fun getClusterLeavesTest() {
    every { expectedFeatureList.value } returns mutableListOf(feature)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.getClusterLeaves(1, 1, 1)
    verify { style.getStyleGeoJSONSourceClusterLeaves("testId", 1, 1, 1) }
  }

  @Test
  fun getExpansionZoomTest() {
    every { expectedByte.value } returns 1.toByte()
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.getExpansionZoom(1)
    verify { style.getStyleGeoJSONSourceClusterExpansionZoom("testId", 1) }
  }

  @Test
  fun getClusterChildrenTest() {
    every { expectedFeatureList.value } returns mutableListOf(feature)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.getClusterChildren(1)
    verify { style.getStyleGeoJSONSourceClusterChildren("testId", 1) }
  }

  @Test(expected = RuntimeException::class)
  fun getClusterChildrenNotBindTest() {
    every { expectedFeatureList.value } returns mutableListOf(feature)
    val testSource = geoJsonSource("testId") {}
    testSource.getClusterChildren(1)
    verify { style.getStyleGeoJSONSourceClusterChildren("testId", 1) }
  }

  @Test(expected = RuntimeException::class)
  fun getClusterChildrenExceptionTest() {
    every { expectedFeatureList.value } returns mutableListOf(feature)
    every { expectedFeatureList.error } returns "error"
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.getClusterChildren(1)
    verify { style.getStyleGeoJSONSourceClusterChildren("testId", 1) }
  }
}