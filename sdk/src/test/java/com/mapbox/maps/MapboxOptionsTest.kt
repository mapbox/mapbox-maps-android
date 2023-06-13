package com.mapbox.maps

import com.mapbox.common.MapboxOptions
import com.mapbox.common.TileStore
import com.mapbox.maps.shadows.ShadowMapsResourceOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowMapsResourceOptions::class])
class MapboxOptionsTest {
  private val mockedTileStore = mockk<TileStore>()

  @Before
  fun setUp() {
    mockkStatic(MapsResourceOptions::class)
    every { MapsResourceOptions.getBaseURL() } returns "https://api.mapbox.com"
    every { MapsResourceOptions.getDataPath() } returns "/tmp/data/"
    every { MapsResourceOptions.getTileStoreUsageMode() } returns TileStoreUsageMode.READ_ONLY
    every { MapsResourceOptions.getTileStore() } returns mockedTileStore
  }

  @After
  fun cleanUp() {
    unmockkStatic(MapsResourceOptions::class)
  }

  @Test
  fun baseUrl() {
    val baseUrl = MapboxOptions.mapsOptions.baseUrl
    assertEquals("https://api.mapbox.com", baseUrl)
    verify(exactly = 1) { MapsResourceOptions.getBaseURL() }
    MapboxOptions.mapsOptions.baseUrl = "https://test.com"
    verify(exactly = 1) { MapsResourceOptions.setBaseURL("https://test.com") }
  }

  @Test
  fun dataPath() {
    val dataPath = MapboxOptions.mapsOptions.dataPath
    assertEquals("/tmp/data/", dataPath)
    verify(exactly = 1) { MapsResourceOptions.getDataPath() }
    MapboxOptions.mapsOptions.dataPath = "/var/data"
    verify(exactly = 1) { MapsResourceOptions.setDataPath("/var/data") }
  }

  @Test
  fun tileStoreUsageMode() {
    val tileStoreUsageMode = MapboxOptions.mapsOptions.tileStoreUsageMode
    assertEquals(TileStoreUsageMode.READ_ONLY, tileStoreUsageMode)
    verify(exactly = 1) { MapsResourceOptions.getTileStoreUsageMode() }
    MapboxOptions.mapsOptions.tileStoreUsageMode = TileStoreUsageMode.DISABLED
    verify(exactly = 1) { MapsResourceOptions.setTileStoreUsageMode(TileStoreUsageMode.DISABLED) }
  }

  @Test
  fun tileStore() {
    val tileStore = MapboxOptions.mapsOptions.tileStore
    assertEquals(mockedTileStore, tileStore)
    verify(exactly = 1) { MapsResourceOptions.getTileStore() }
    val tileStore2 = mockk<TileStore>()
    MapboxOptions.mapsOptions.tileStore = tileStore2
    verify(exactly = 1) { MapsResourceOptions.setTileStore(tileStore2) }
    MapboxOptions.mapsOptions.tileStore = null
    verify(exactly = 1) { MapsResourceOptions.setTileStore(null) }
  }
}