package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
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
    every { context.filesDir } returns File("/foobar")
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns -1
    every { context.getString(-1) } returns "pk.foobar"
    every { typedArray.getString(any()) } returns null
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getFloat(any(), any()) } returns 99.0f
    every { typedArray.hasValue(any()) } returns true
  }

  @After
  fun cleanUp() {
    ResourceOptionsManager.destroyDefault()
  }

  @Test
  fun default() {
    val resourceOptions =
      ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("pk.foobar", resourceOptions.accessToken)
    assertEquals(null, resourceOptions.baseURL)
    assertEquals("/foobar/.mapbox/maps/ambient_cache.db", resourceOptions.cachePath)
    assertEquals(99L, resourceOptions.cacheSize)
  }

  @Test
  fun noAccessTokenResourceHasTokenFromAttr() {
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns 0
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken) } returns "token"
    val options = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("token", options.accessToken)
  }

  @Test(expected = MapboxConfigurationException::class)
  fun noAccessTokenResourceNoTokenFromAttr() {
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns 0
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken) } returns null
    val options = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("token", options.accessToken)
  }

  @Test
  fun hasAccessTokenResourceHasTokenFromAttr() {
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns -1
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken) } returns "token"
    val options = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("token", options.accessToken)
  }

  @Test
  fun hasAccessTokenResourceNoTokenFromAttr() {
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns -1
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesAccessToken) } returns null
    val options = ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("pk.foobar", options.accessToken)
  }

  @Test
  fun cachePath() {
    val resourceOptions =
      ResourcesAttributeParser.parseResourcesOptions(context, typedArray)
    assertEquals("/foobar/.mapbox/maps/ambient_cache.db", resourceOptions.cachePath)
  }

  @Test
  fun cacheSize() {
    every {
      typedArray.getFloat(
        R.styleable.mapbox_MapView_mapbox_resourcesCacheSize,
        any()
      )
    } returns 1234F
    assertEquals(
      1234L,
      ResourcesAttributeParser.parseResourcesOptions(
        context,
        typedArray
      ).cacheSize
    )
  }

  @Test
  fun baseUrl() {
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_resourcesBaseUrl) } returns "mapbox.be"
    assertEquals(
      "mapbox.be",
      ResourcesAttributeParser.parseResourcesOptions(
        context,
        typedArray
      ).baseURL
    )
  }
}