package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import io.mockk.every
import io.mockk.mockk
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
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns -1
    every { context.getString(-1) } returns "pk.foobar"
    every { typedArray.getString(any()) } returns null
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getFloat(any(), any()) } returns 99.0f
    every { typedArray.hasValue(any()) } returns true
  }

  @Test
  fun default() {
    val resourceOptions =
      ResourcesAttributeParser.parseResourcesOptions(context, typedArray, CredentialsManager.default)
    assertEquals("pk.foobar", resourceOptions.accessToken)
    assertEquals(null, resourceOptions.baseURL)
    assertEquals(99L, resourceOptions.cacheSize)
  }

  @Test(expected = MapboxConfigurationException::class)
  fun noAccessTokenResource() {
    every { resources.getIdentifier("mapbox_access_token", "string", "foobar") } returns 0
    val options =
      ResourcesAttributeParser.parseResourcesOptions(context, typedArray, CredentialsManager.default)
    assertEquals("", options.accessToken)
  }

  @Test
  fun cachePath() {
    every { context.filesDir } returns File("/foobar")
    val resourceOptions =
      ResourcesAttributeParser.parseResourcesOptions(context, typedArray, CredentialsManager.default)
    assertEquals("/foobar/mbx.db", resourceOptions.cachePath)
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
        typedArray,
        CredentialsManager.default
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
        typedArray,
        CredentialsManager.default
      ).baseURL
    )
  }
}