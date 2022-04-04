package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StyleTest {

  private lateinit var style: Style
  private val nativeMap: MapInterface = mockk(relaxed = true)

  @Before
  fun setUp() {
    style = Style(nativeMap as StyleManagerInterface, 1.0f)
  }

  @Test
  fun isValid() {
    assertTrue(style.isValid())
    style.markInvalid()
    assertFalse(style.isValid())
  }

  @Test
  fun addImage() {
    val image: Image = mockk()
    style.addImage("foobar", image)
    verify { nativeMap.addStyleImage("foobar", 1.0f, image, false, listOf(), listOf(), null) }
  }

  @Test
  fun addImageSdf() {
    val image: Image = mockk()
    style.addImage("foobar", image, true)
    verify { nativeMap.addStyleImage("foobar", 1.0f, image, true, listOf(), listOf(), null) }
  }

  @Test
  fun addImageFull() {
    val image: Image = mockk()
    val stretchesLeft = listOf(ImageStretches(1.0f, 2.0f))
    val stretchesRight = listOf(ImageStretches(3.0f, 4.0f))
    val imageContent = ImageContent(1.0f, 2.0f, 3.0f, 4.0f)
    style.addStyleImage("foobar", 2.0f, image, true, stretchesLeft, stretchesRight, imageContent)
    verify {
      nativeMap.addStyleImage(
        "foobar",
        2.0f,
        image,
        true,
        stretchesLeft,
        stretchesRight,
        imageContent
      )
    }
  }

  @Test
  fun removeImage() {
    style.removeStyleImage("id")
    verify { nativeMap.removeStyleImage("id") }
  }

  @Test
  fun hasImage() {
    style.hasStyleImage("id")
    verify { nativeMap.hasStyleImage("id") }
  }

  @Test
  fun addBitmap() {
    val bitmap: Bitmap = mockk(relaxed = true)
    style.addImage("foobar", bitmap)
    verify { bitmap.height }
    verify { bitmap.width }
    verify { bitmap.byteCount }
    verify { nativeMap.addStyleImage("foobar", 1.0f, any(), false, listOf(), listOf(), null) }
  }

  @Test
  fun addBitmapSdf() {
    val bitmap: Bitmap = mockk(relaxed = true)
    style.addImage("foobar", bitmap, true)
    verify { bitmap.height }
    verify { bitmap.width }
    verify { bitmap.byteCount }
    verify { nativeMap.addStyleImage("foobar", 1.0f, any(), true, listOf(), listOf(), null) }
  }

  @Test
  fun addLayer() {
    val value = mockk<Value>()
    val position = mockk<LayerPosition>()
    style.addStyleLayer(value, position)
    verify { nativeMap.addStyleLayer(value, position) }
  }

  @Test
  fun getLayers() {
    style.styleLayers
    verify { nativeMap.styleLayers }
  }

  @Test
  fun removeLayer() {
    style.removeStyleLayer("id")
    verify { nativeMap.removeStyleLayer("id") }
  }

  @Test
  fun layerExist() {
    style.styleLayerExists("id")
    verify { nativeMap.styleLayerExists("id") }
  }

  @Test
  fun setLayerProperty() {
    val value = mockk<Value>()
    style.setStyleLayerProperty("id", "foobar", value)
    verify { nativeMap.setStyleLayerProperty("id", "foobar", value) }
  }

  @Test
  fun getLayerProperty() {
    style.getStyleLayerProperty("id", "foobar")
    verify { nativeMap.getStyleLayerProperty("id", "foobar") }
  }

  @Test
  fun setLayerProperties() {
    val properties = mockk<Value>()
    style.setStyleLayerProperties("id", properties)
    verify { nativeMap.setStyleLayerProperties("id", properties) }
  }

  @Test
  fun getLayerProperties() {
    style.getStyleLayerProperties("id")
    verify { nativeMap.getStyleLayerProperties("id") }
  }

  @Test
  fun addSource() {
    val properties = mockk<Value>()
    style.addStyleSource("id", properties)
    verify { nativeMap.addStyleSource("id", properties) }
  }

  @Test
  fun getSources() {
    style.styleSources
    verify { nativeMap.styleSources }
  }

  @Test
  fun removeSource() {
    style.removeStyleSource("id")
    verify { style.removeStyleSource("id") }
  }

  @Test
  fun sourceExist() {
    style.styleSourceExists("id")
    verify { nativeMap.styleSourceExists("id") }
  }

  @Test
  fun setSourceProperties() {
    val value = mockk<Value>()
    style.setStyleSourceProperties("id", value)
    verify { nativeMap.setStyleSourceProperties("id", value) }
  }

  @Test
  fun getSourceProperties() {
    style.getStyleSourceProperties("id")
    verify { nativeMap.getStyleSourceProperties("id") }
  }

  @Test
  fun setSourceProperty() {
    val value = mockk<Value>()
    style.setStyleSourceProperty("id", "foobar", value)
    verify { nativeMap.setStyleSourceProperty("id", "foobar", value) }
  }

  @Test
  fun getSourceProperty() {
    style.getStyleSourceProperty("id", "foobar")
    verify { nativeMap.getStyleSourceProperty("id", "foobar") }
  }

  @Test
  fun getSourcesAttribution() {
    val source = mockk<StyleObjectInfo>()
    every { source.id } returns "id"
    every { nativeMap.styleSources } returns listOf(source)
    val valueMap = HashMap<String, Value>()
    valueMap.put("attribution", mockk())
    every { nativeMap.getStyleSourceProperties(any()) } returns ExpectedFactory.createValue(
      Value.valueOf(
        valueMap
      )
    )
    style.getStyleSourcesAttribution()
    verify { nativeMap.getStyleSourceProperties("id") }
  }

  @Test
  fun updateImageSourceImage() {
    val image = mockk<Image>()
    style.updateStyleImageSourceImage("id", image)
    verify { nativeMap.updateStyleImageSourceImage("id", image) }
  }

  @Test
  fun addCustomLayer() {
    val layerHost = mockk<CustomLayerHost>()
    val layerPosition = mockk<LayerPosition>()
    style.addStyleCustomLayer("id", layerHost, layerPosition)
  }

  @Test
  fun setCustomGeometrySourceTileData() {
    val canonicalTileID = mockk<CanonicalTileID>()
    val features = mockk<MutableList<Feature>>()
    style.setStyleCustomGeometrySourceTileData("id", canonicalTileID, features)
  }

  @Test
  fun addCustomGeometrySource() {
    val options = mockk<CustomGeometrySourceOptions>()
    style.addStyleCustomGeometrySource("id", options)
    verify { nativeMap.addStyleCustomGeometrySource("id", options) }
  }

  @Test
  fun invalidateCustomGeometrySourceBounds() {
    val bounds = mockk<CoordinateBounds>()
    style.invalidateStyleCustomGeometrySourceRegion("id", bounds)
    verify { nativeMap.invalidateStyleCustomGeometrySourceRegion("id", bounds) }
  }

  @Test
  fun invalidateCustomGeometrySourceId() {
    val tileId = mockk<CanonicalTileID>()
    style.invalidateStyleCustomGeometrySourceTile("id", tileId)
    verify { nativeMap.invalidateStyleCustomGeometrySourceTile("id", tileId) }
  }

  @Test
  fun addPersistentStyleLayer() {
    val value = mockk<Value>()
    val position = mockk<LayerPosition>()
    style.addPersistentStyleLayer(value, position)
    verify { nativeMap.addPersistentStyleLayer(value, position) }
  }

  @Test
  fun addPersistentStyleCustomLayer() {
    val layerHost = mockk<CustomLayerHost>()
    val layerPosition = mockk<LayerPosition>()
    style.addPersistentStyleCustomLayer("id", layerHost, layerPosition)
    verify { nativeMap.addPersistentStyleCustomLayer("id", layerHost, layerPosition) }
  }

  @Test
  fun isStyleLayerPersistent() {
    style.isStyleLayerPersistent("id")
    verify { nativeMap.isStyleLayerPersistent("id") }
  }

  @Test
  fun setJson() {
    style.styleJSON = "foobar"
    verify { nativeMap.styleJSON = "foobar" }
  }

  @Test
  fun getJson() {
    style.styleJSON
    verify { nativeMap.styleJSON }
  }

  @Test
  fun setUri() {
    style.styleURI = "foobar"
    verify { nativeMap.styleURI = "foobar" }
  }

  @Test
  fun getUri() {
    style.styleURI
    verify { nativeMap.styleURI }
  }

  @Test
  fun setTransition() {
    val transition = mockk<TransitionOptions>()
    style.styleTransition = transition
    verify { nativeMap.styleTransition = transition }
  }

  @Test
  fun getTransition() {
    style.styleTransition
    verify { nativeMap.styleTransition }
  }

  @Test
  fun getDefaultCamera() {
    style.styleDefaultCamera
    verify { nativeMap.styleDefaultCamera }
  }

  @Test
  fun getImage() {
    style.getStyleImage("foobar")
    verify { nativeMap.getStyleImage("foobar") }
  }

  @Test
  fun setLight() {
    val value = mockk<Value>()
    style.setStyleLight(value)
    verify { nativeMap.setStyleLight(value) }
  }

  @Test
  fun getLight() {
    style.getStyleLightProperty("id")
    verify { nativeMap.getStyleLightProperty("id") }
  }

  @Test
  fun setLightProperty() {
    val value = mockk<Value>()
    style.setStyleLightProperty("id", value)
    verify { nativeMap.setStyleLightProperty("id", value) }
  }

  @Test
  fun setStyleTerrain() {
    val value = mockk<Value>()
    style.setStyleTerrain(value)
    verify { nativeMap.setStyleTerrain(value) }
  }

  @Test
  fun getStyleTerrainProperty() {
    style.getStyleTerrainProperty("id")
    verify { nativeMap.getStyleTerrainProperty("id") }
  }

  @Test
  fun setStyleTerrainProperty() {
    val value = mockk<Value>()
    style.setStyleTerrainProperty("id", value)
    verify { nativeMap.setStyleTerrainProperty("id", value) }
  }

  @Test
  fun setStyleProjection() {
    val value = mockk<Value>()
    style.setStyleProjection(value)
    verify { nativeMap.setStyleProjection(value) }
  }

  @Test
  fun getStyleProjectionProperty() {
    style.getStyleProjectionProperty("foo")
    verify { nativeMap.getStyleProjectionProperty("foo") }
  }

  @Test
  fun setStyleProjectionProperty() {
    val value = mockk<Value>()
    style.setStyleProjectionProperty("foo", value)
    verify { nativeMap.setStyleProjectionProperty("foo", value) }
  }
}