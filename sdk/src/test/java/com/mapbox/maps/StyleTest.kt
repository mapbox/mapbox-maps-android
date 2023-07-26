package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(
  shadows = [
    ShadowStyleManager::class,
  ]
)
@RunWith(RobolectricTestRunner::class)
class StyleTest {

  private lateinit var style: Style
  private val styleManager: StyleManager = mockk(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    style = Style(styleManager, 1.0f)
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
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
    verify {
      styleManager.addStyleImage(
        "foobar",
        1.0f,
        image,
        false,
        mutableListOf(),
        mutableListOf(),
        null
      )
    }
  }

  @Test
  fun addImageSdf() {
    val image: Image = mockk()
    style.addImage("foobar", image, true)
    verify {
      styleManager.addStyleImage(
        "foobar",
        1.0f,
        image,
        true,
        mutableListOf(),
        mutableListOf(),
        null
      )
    }
  }

  @Test
  fun addImageFull() {
    val image: Image = mockk()
    val stretchesLeft = listOf(ImageStretches(1.0f, 2.0f))
    val stretchesRight = listOf(ImageStretches(3.0f, 4.0f))
    val imageContent = ImageContent(1.0f, 2.0f, 3.0f, 4.0f)
    style.addStyleImage("foobar", 2.0f, image, true, stretchesLeft, stretchesRight, imageContent)
    verify {
      styleManager.addStyleImage(
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
    verify { styleManager.removeStyleImage("id") }
  }

  @Test
  fun hasImage() {
    style.hasStyleImage("id")
    verify { styleManager.hasStyleImage("id") }
  }

  @Test
  fun addBitmap() {
    val bitmapWidth = 1
    val bitmapHeight = 2
    val bitmap = spyk(Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888))
    mockkStatic(DataRef::class)
    val nativeDataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(any()) } returns nativeDataRef
    style.addImage("foobar", bitmap)
    verify { bitmap.height }
    verify { bitmap.width }
    verify { bitmap.byteCount }
    verify(exactly = 1) {
      styleManager.addStyleImage(
        "foobar",
        1.0f,
        Image(bitmapWidth, bitmapHeight, nativeDataRef),
        false,
        mutableListOf(),
        mutableListOf(),
        null
      )
    }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun addBitmapSdf() {
    val bitmapWidth = 1
    val bitmapHeight = 2
    val bitmap = spyk(Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888))
    mockkStatic(DataRef::class)
    val nativeDataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(any()) } returns nativeDataRef
    style.addImage("foobar", bitmap, true)
    verify { bitmap.height }
    verify { bitmap.width }
    verify { bitmap.byteCount }
    verify(exactly = 1) {
      styleManager.addStyleImage(
        "foobar",
        1.0f,
        Image(bitmapWidth, bitmapHeight, nativeDataRef),
        true,
        mutableListOf(),
        mutableListOf(),
        null
      )
    }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun addLayer() {
    val value = mockk<Value>()
    val position = mockk<LayerPosition>()
    style.addStyleLayer(value, position)
    verify { styleManager.addStyleLayer(value, position) }
  }

  @Test
  fun getLayers() {
    style.styleLayers
    verify { styleManager.styleLayers }
  }

  @Test
  fun removeLayer() {
    style.removeStyleLayer("id")
    verify { styleManager.removeStyleLayer("id") }
  }

  @Test
  fun layerExist() {
    style.styleLayerExists("id")
    verify { styleManager.styleLayerExists("id") }
  }

  @Test
  fun setLayerProperty() {
    val value = mockk<Value>()
    style.setStyleLayerProperty("id", "foobar", value)
    verify { styleManager.setStyleLayerProperty("id", "foobar", value) }
  }

  @Test
  fun getLayerProperty() {
    style.getStyleLayerProperty("id", "foobar")
    verify { styleManager.getStyleLayerProperty("id", "foobar") }
  }

  @Test
  fun setLayerProperties() {
    val properties = mockk<Value>()
    style.setStyleLayerProperties("id", properties)
    verify { styleManager.setStyleLayerProperties("id", properties) }
  }

  @Test
  fun getLayerProperties() {
    style.getStyleLayerProperties("id")
    verify { styleManager.getStyleLayerProperties("id") }
  }

  @Test
  fun addSource() {
    val properties = mockk<Value>()
    style.addStyleSource("id", properties)
    verify { styleManager.addStyleSource("id", properties) }
  }

  @Test
  fun getSources() {
    style.styleSources
    verify { styleManager.styleSources }
  }

  @Test
  fun removeSource() {
    style.removeStyleSource("id")
    verify { style.removeStyleSource("id") }
  }

  @Test
  fun sourceExist() {
    style.styleSourceExists("id")
    verify { styleManager.styleSourceExists("id") }
  }

  @Test
  fun setSourceProperties() {
    val value = mockk<Value>()
    style.setStyleSourceProperties("id", value)
    verify { styleManager.setStyleSourceProperties("id", value) }
  }

  @Test
  fun getSourceProperties() {
    style.getStyleSourceProperties("id")
    verify { styleManager.getStyleSourceProperties("id") }
  }

  @Test
  fun setSourceProperty() {
    val value = mockk<Value>()
    style.setStyleSourceProperty("id", "foobar", value)
    verify { styleManager.setStyleSourceProperty("id", "foobar", value) }
  }

  @Test
  fun getSourceProperty() {
    style.getStyleSourceProperty("id", "foobar")
    verify { styleManager.getStyleSourceProperty("id", "foobar") }
  }

  @Test
  fun getSourcesAttribution() {
    val source = mockk<StyleObjectInfo>()
    every { source.id } returns "id"
    every { styleManager.styleSources } returns listOf(source)
    val valueMap = HashMap<String, Value>()
    valueMap.put("attribution", mockk())
    every { styleManager.getStyleSourceProperties(any()) } returns ExpectedFactory.createValue(
      Value.valueOf(
        valueMap
      )
    )
    style.getStyleSourcesAttribution()
    verify { styleManager.getStyleSourceProperties("id") }
  }

  @Test
  fun updateImageSourceImage() {
    val image = mockk<Image>()
    style.updateStyleImageSourceImage("id", image)
    verify { styleManager.updateStyleImageSourceImage("id", image) }
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
    verify { styleManager.addStyleCustomGeometrySource("id", options) }
  }

  @Test
  fun invalidateCustomGeometrySourceBounds() {
    val bounds = mockk<CoordinateBounds>()
    style.invalidateStyleCustomGeometrySourceRegion("id", bounds)
    verify { styleManager.invalidateStyleCustomGeometrySourceRegion("id", bounds) }
  }

  @Test
  fun invalidateCustomGeometrySourceId() {
    val tileId = mockk<CanonicalTileID>()
    style.invalidateStyleCustomGeometrySourceTile("id", tileId)
    verify { styleManager.invalidateStyleCustomGeometrySourceTile("id", tileId) }
  }

  @Test
  fun addPersistentStyleLayer() {
    val value = mockk<Value>()
    val position = mockk<LayerPosition>()
    style.addPersistentStyleLayer(value, position)
    verify { styleManager.addPersistentStyleLayer(value, position) }
  }

  @Test
  fun addPersistentStyleCustomLayer() {
    val layerHost = mockk<CustomLayerHost>()
    val layerPosition = mockk<LayerPosition>()
    style.addPersistentStyleCustomLayer("id", layerHost, layerPosition)
    verify { styleManager.addPersistentStyleCustomLayer("id", layerHost, layerPosition) }
  }

  @Test
  fun isStyleLayerPersistent() {
    style.isStyleLayerPersistent("id")
    verify { styleManager.isStyleLayerPersistent("id") }
  }

  @Test
  fun setJson() {
    style.styleJSON = "foobar"
    verify { styleManager.styleJSON = "foobar" }
  }

  @Test
  fun getJson() {
    style.styleJSON
    verify { styleManager.styleJSON }
  }

  @Test
  fun setUri() {
    style.styleURI = "foobar"
    verify { styleManager.styleURI = "foobar" }
  }

  @Test
  fun getUri() {
    style.styleURI
    verify { styleManager.styleURI }
  }

  @Test
  fun setTransition() {
    val transition = mockk<TransitionOptions>()
    style.setStyleTransition(transition)
    verify { styleManager.styleTransition = transition }
  }

  @Test
  fun getTransition() {
    style.getStyleTransition()
    verify { styleManager.styleTransition }
  }

  @Test
  fun getDefaultCamera() {
    style.styleDefaultCamera
    verify { styleManager.styleDefaultCamera }
  }

  @Test
  fun getImage() {
    style.getStyleImage("foobar")
    verify { styleManager.getStyleImage("foobar") }
  }

  @Test
  fun setLight() {
    val value = mockk<Value>()
    style.setStyleLights(value)
    verify { styleManager.setStyleLights(value) }
  }

  @Test
  fun getLight() {
    style.getStyleLightProperty("id", "prop")
    verify { styleManager.getStyleLightProperty("id", "prop") }
  }

  @Test
  fun setLightProperty() {
    val value = mockk<Value>()
    style.setStyleLightProperty("id", "prop", value)
    verify { styleManager.setStyleLightProperty("id", "prop", value) }
  }

  @Test
  fun setStyleTerrain() {
    val value = mockk<Value>()
    style.setStyleTerrain(value)
    verify { styleManager.setStyleTerrain(value) }
  }

  @Test
  fun getStyleTerrainProperty() {
    style.getStyleTerrainProperty("id")
    verify { styleManager.getStyleTerrainProperty("id") }
  }

  @Test
  fun setStyleTerrainProperty() {
    val value = mockk<Value>()
    style.setStyleTerrainProperty("id", value)
    verify { styleManager.setStyleTerrainProperty("id", value) }
  }

  @Test
  fun setStyleProjection() {
    val value = mockk<Value>()
    style.setStyleProjection(value)
    verify { styleManager.setStyleProjection(value) }
  }

  @Test
  fun getStyleProjectionProperty() {
    style.getStyleProjectionProperty("foo")
    verify { styleManager.getStyleProjectionProperty("foo") }
  }

  @Test
  fun setStyleProjectionProperty() {
    val value = mockk<Value>()
    style.setStyleProjectionProperty("foo", value)
    verify { styleManager.setStyleProjectionProperty("foo", value) }
  }

  @Test
  fun addStyleModel() {
    val modelId = "modelId"
    val modelUri = "modelUri"
    style.addStyleModel(modelId, modelUri)
    verify { styleManager.addStyleModel(modelId, modelUri) }
  }

  @Test
  fun removeStyleModel() {
    val modelId = "modelId"
    style.removeStyleModel(modelId)
    verify { styleManager.removeStyleModel(modelId) }
  }

  @Test
  fun hasStyleModel() {
    val modelId = "modelId"
    style.hasStyleModel(modelId)
    verify { styleManager.hasStyleModel(modelId) }
  }

  @Test
  fun setStyleAtmosphere() {
    val atmosphereValue = mockk<Value>()
    style.setStyleAtmosphere(atmosphereValue)
    verify { styleManager.setStyleAtmosphere(atmosphereValue) }
  }

  @Test
  fun setStyleAtmosphereProperty() {
    val property = "property"
    val atmosphereValueProperty = mockk<Value>()
    style.setStyleAtmosphereProperty(property, atmosphereValueProperty)
    verify { styleManager.setStyleAtmosphereProperty(property, atmosphereValueProperty) }
  }

  @Test
  fun getStyleAtmosphereProperty() {
    val property = "property"
    style.getStyleAtmosphereProperty(property)
    verify { styleManager.getStyleAtmosphereProperty(property) }
  }

  @Test
  fun addGeoJSONSourceFeatures() {
    val feature = mockk<Feature>()
    style.addGeoJSONSourceFeatures("id", "dataId", listOf(feature))
    verify { styleManager.addGeoJSONSourceFeatures("id", "dataId", listOf(feature)) }
  }

  @Test
  fun updateGeoJSONSourceFeatures() {
    val feature = mockk<Feature>()
    style.updateGeoJSONSourceFeatures("id", "dataId", listOf(feature))
    verify { styleManager.updateGeoJSONSourceFeatures("id", "dataId", listOf(feature)) }
  }

  @Test
  fun removeGeoJSONSourceFeatures() {
    val featureId = "feature_id"
    style.removeGeoJSONSourceFeatures("id", "dataId", listOf(featureId))
    verify { styleManager.removeGeoJSONSourceFeatures("id", "dataId", listOf(featureId)) }
  }
}