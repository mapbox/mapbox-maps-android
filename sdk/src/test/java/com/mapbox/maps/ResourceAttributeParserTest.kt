package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.io.File

class ResourceAttributeParserTest {

  private val typedArray = mockk<TypedArray>()
  private val context = mockk<Context>()
  private val resources = mockk<Resources>()

  @Before
  fun setUp() {
    every { context.resources } returns resources
    every { context.packageName } returns "foobar"
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns -1
    every { context.getString(-1) } returns "pk.foobar"
    every { context.filesDir } returns File("/sdcard/data")
    every { typedArray.getString(any()) } returns null
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getFloat(any(), any()) } returns 99.0f
  }

  @Test
  fun default() {
    val resourceOptions = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("pk.foobar", resourceOptions.accessToken)
    assertEquals("/sdcard/data", resourceOptions.assetPath)
    assertEquals(null, resourceOptions.baseURL)
    assertEquals("/sdcard/data/mbx.db", resourceOptions.cachePath)
    assertEquals(99L, resourceOptions.cacheSize)
  }

  @Test
  fun noAccessTokenResource() {
    every { context.getString(-1) } returns null
    val options = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("", options.accessToken)
  }

  @Test
  fun assetPath() {
    every { context.filesDir } returns File("/foobar")
    val resourceOptions = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("/foobar", resourceOptions.assetPath)
  }

  @Test
  fun cachePath() {
    every { context.filesDir } returns File("/foobar")
    val resourceOptions = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("/foobar/mbx.db", resourceOptions.cachePath)
  }

  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/939")
  @Test
  fun tileStorePath() {
    every { context.filesDir } returns File("/foobar")
    val resourceOptions = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("/foobar/maps_tile_store/", resourceOptions.tileStorePath)
  }

  @Test
  fun cacheSize() {
    every { typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_resourcesCacheSize, any()) } returns 1234F
    assertEquals(1234L, ResourcesAttributeParser.parseResourcesOptions(context, typedArray).cacheSize)
  }

  @Test
  fun baseUrl() {
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl) } returns "mapbox.be"
    assertEquals("mapbox.be", ResourcesAttributeParser.parseResourcesOptions(context, typedArray).baseURL)
  }
}