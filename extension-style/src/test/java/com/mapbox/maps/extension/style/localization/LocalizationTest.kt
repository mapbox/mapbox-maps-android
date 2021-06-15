package com.mapbox.maps.extension.style.localization

import com.mapbox.maps.StyleObjectInfo
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.sources.getSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [LocalizationShadowValueConverter::class])
class LocalizationTest {
  val style: StyleInterface = mockk(relaxed = true)
  val source: StyleObjectInfo = mockk(relaxed = true)
  val layer: StyleObjectInfo = mockk(relaxed = true)
  val symbolLayer: SymbolLayer = mockk(relaxed = true)
  val vectorSource: VectorSource = mockk(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerKt")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceKt")
    every { vectorSource.url } returns "mapbox.mapbox-streets-v8"
    every { style.styleSources } returns listOf(source)
    every { style.getLayer(any()) } returns symbolLayer
    every { style.getSource(any()) } returns vectorSource
    every { style.styleLayers } returns listOf(layer)
    every { layer.type } returns "symbol"
    every { source.type } returns "vector"
    every { symbolLayer.textFieldAsExpression } returns Expression.get("name_en")
  }

  @Test
  fun setMapLanguage() {
    setMapLanguage(Locale.SIMPLIFIED_CHINESE, style)
    verify { symbolLayer.textField(Expression.get("name_zh-Hans")) }
  }
}