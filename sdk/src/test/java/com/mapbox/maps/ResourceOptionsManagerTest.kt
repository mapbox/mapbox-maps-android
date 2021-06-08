package com.mapbox.maps

import android.content.Context
import com.mapbox.common.TileStore
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
    every { context.getString(-1) } returns "token"
    every {
      context.resources.getIdentifier(
        "mapbox_access_token",
        "string",
        "com.mapbox.maps"
      )
    } returns -1
    every { context.packageName } returns "com.mapbox.maps"
    every { context.filesDir } returns File("foobar")
  }

  @After
  fun cleanUp() {
    ResourceOptionsManager.destroyDefault()
  }

  @Test(expected = MapboxConfigurationException::class)
  fun getDefaultWithoutTokenInResource() {
    every {
      context.resources.getIdentifier(
        "mapbox_access_token",
        "string",
        "com.mapbox.maps"
      )
    } returns 0
    ResourceOptionsManager.getDefault(context)
  }

  fun getDefaultWithoutTokenInResourceWithUserToken() {
    every {
      context.resources.getIdentifier(
        "mapbox_access_token",
        "string",
        "com.mapbox.maps"
      )
    } returns 0
    var defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context, "newToken")
    assertEquals("newToken", defaultResourceOptionsManager.resourceOptions.accessToken)
  }

  @Test
  fun getDefaultTest() {
    var defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    assertEquals("token", defaultResourceOptionsManager.resourceOptions.accessToken)
    assertNull(defaultResourceOptionsManager.resourceOptions.dataPath)

    defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context, "newToken")
    assertEquals("newToken", defaultResourceOptionsManager.resourceOptions.accessToken)
  }

  @Test
  fun getDefaultWithUserTokenTest() {
    var defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context, "newToken")
    assertEquals("newToken", defaultResourceOptionsManager.resourceOptions.accessToken)
    defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    assertEquals("newToken", defaultResourceOptionsManager.resourceOptions.accessToken)
  }

  @Test
  fun updateDefaultResourceOptionsManagerTest() {
    var defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    defaultResourceOptionsManager.update {
      accessToken("abc")
      dataPath("cachePath")
      tileStore(tileStore)
      tileStoreUsageMode(TileStoreUsageMode.READ_AND_UPDATE)
      baseURL("baseUrl")
    }
    assertEquals("abc", ResourceOptionsManager.getDefault(context).resourceOptions.accessToken)
    assertEquals("cachePath", ResourceOptionsManager.getDefault(context).resourceOptions.dataPath)
    assertEquals(tileStore, ResourceOptionsManager.getDefault(context).resourceOptions.tileStore)
    assertEquals(
      TileStoreUsageMode.READ_AND_UPDATE,
      ResourceOptionsManager.getDefault(context).resourceOptions.tileStoreUsageMode
    )
    assertEquals("baseUrl", ResourceOptionsManager.getDefault(context).resourceOptions.baseURL)

    ResourceOptionsManager.destroyDefault()
    defaultResourceOptionsManager = ResourceOptionsManager.getDefault(context)
    assertEquals("token", defaultResourceOptionsManager.resourceOptions.accessToken)
    assertNull(defaultResourceOptionsManager.resourceOptions.dataPath)
  }
}