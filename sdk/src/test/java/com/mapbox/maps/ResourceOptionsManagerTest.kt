package com.mapbox.maps

import android.content.Context
import com.mapbox.common.TileStore
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class ResourceOptionsManagerTest {

  private val context: Context = mockk(relaxUnitFun = true)
  private val tileStore: TileStore = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    CredentialsManager.default.setAccessToken("token")
    every { context.filesDir } returns File("foobar")
  }

  @After
  fun cleanUp() {
    ResourceOptionsManager.getDefault(context).resourceOptions =
      ResourceOptions.Builder().applyDefaultParams(context)
        .build()
  }

  @Test
  fun getDefaultResourceOptionsManagerTest() {
    val defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    assertEquals("token", defaultResourceOptionsManager.resourceOptions.accessToken)
    Assert.assertTrue(defaultResourceOptionsManager.resourceOptions.cachePath!!.endsWith("/.mapbox/maps/ambient_cache.db"))
    assertEquals(DEFAULT_CACHE_SIZE, defaultResourceOptionsManager.resourceOptions.cacheSize)
  }

  @Test
  fun updateDefaultResourceOptionsManagerTest() {
    val defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    defaultResourceOptionsManager.update {
      cacheSize(1000L)
      accessToken("abc")
      cachePath("cachePath")
      tileStore(tileStore)
      tileStoreUsageMode(TileStoreUsageMode.READ_AND_UPDATE)
      baseURL("baseUrl")
    }
    assertEquals("abc", ResourceOptionsManager.getDefault(context).resourceOptions.accessToken)
    assertEquals("cachePath", ResourceOptionsManager.getDefault(context).resourceOptions.cachePath)
    assertEquals(1000L, ResourceOptionsManager.getDefault(context).resourceOptions.cacheSize)
    assertEquals(tileStore, ResourceOptionsManager.getDefault(context).resourceOptions.tileStore)
    assertEquals(
      TileStoreUsageMode.READ_AND_UPDATE,
      ResourceOptionsManager.getDefault(context).resourceOptions.tileStoreUsageMode
    )
    assertEquals("baseUrl", ResourceOptionsManager.getDefault(context).resourceOptions.baseURL)
  }
}