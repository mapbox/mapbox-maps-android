package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.observable.getResourceEventData
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class OfflineTest {

  private val handler = Handler(Looper.getMainLooper())
  private val offlineManager: OfflineManager by lazy {
    OfflineManager(
      MapInitOptions.getDefaultResourceOptions(
        InstrumentationRegistry.getInstrumentation().targetContext
      )
    )
  }
  private val cacheManager: CacheManager by lazy {
    CacheManager(
      MapInitOptions.getDefaultResourceOptions(
        InstrumentationRegistry.getInstrumentation().targetContext
      )
    )
  }

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  fun setUp() {
    val latch = CountDownLatch(2)
    handler.post {
      offlineManager.removeStylePack(STYLE)
      TileStore.getInstance().removeTileRegion(TILE_REGION_ID)
      TileStore.getInstance().setOption(TileStoreOptions.DISK_QUOTA, Value(0))
      cacheManager.apply {
        // clear cache in a most severe way
        clearAmbientCache {
          latch.countDown()
        }
        // restore ambient cache size to some default value > 0
        setMaximumAmbientCacheSize(DEFAULT_AMBIENT_CACHE_SIZE_BYTES) {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun loadStylePackNoCancel() {
    val latch = CountDownLatch(1)
    var downloadProgress: StylePackLoadProgress? = null
    var downloadedStyle: StylePack? = null
    var downloadedStyleError: StylePackError? = null
    handler.post {
      offlineManager.loadStylePack(
        STYLE,
        StylePackLoadOptions.Builder()
          .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          .metadata(Value(STYLE_PACK_METADATA))
          .build(),
        { downloadProgress = it },
        {
          if (it.isValue) {
            it.value?.let { pack ->
              downloadedStyle = pack
            }
          }
          if (it.isError) {
            Logger.e(TAG, "Error: ${it.error}")
            it.error?.let { error ->
              downloadedStyleError = error
            }
          }
          latch.countDown()
        }
      )
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    } else {
      Assert.assertNotNull(downloadProgress)
      Assert.assertNotNull(downloadedStyle)
      Assert.assertNull(downloadedStyleError)

      Assert.assertEquals(0L, downloadProgress?.erroredResourceCount)
      Assert.assertEquals(
        downloadProgress?.requiredResourceCount,
        downloadProgress?.completedResourceCount
      )
      Assert.assertEquals(
        downloadedStyle?.requiredResourceCount,
        downloadedStyle?.completedResourceCount
      )
      Assert.assertEquals(STYLE, downloadedStyle?.styleURI)
    }
  }

  @Test
  fun loadStylePackCancel() {
    val latch = CountDownLatch(1)
    var downloadProgress: StylePackLoadProgress? = null
    var downloadedStyle: StylePack? = null
    var cancelableStylePackTask: Cancelable? = null
    var downloadedStyleError: StylePackError? = null
    handler.post {
      cancelableStylePackTask = offlineManager.loadStylePack(
        STYLE,
        StylePackLoadOptions.Builder()
          .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          .metadata(Value(STYLE_PACK_METADATA))
          .build(),
        {
          downloadProgress = it
          cancelableStylePackTask?.cancel()
        },
        {
          if (it.isValue) {
            it.value?.let { pack ->
              downloadedStyle = pack
            }
          }
          if (it.isError) {
            Logger.e(TAG, "Error: ${it.error}")
            it.error?.let { error ->
              downloadedStyleError = error
            }
          }
          latch.countDown()
        }
      )
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    } else {
      Assert.assertNotNull(downloadProgress)
      Assert.assertNull(downloadedStyle)
      Assert.assertNotNull(downloadedStyleError)

      Assert.assertEquals(StylePackErrorType.CANCELED, downloadedStyleError?.type)
    }
  }

  @Test
  fun loadTileRegionNoCancel() {
    val latch = CountDownLatch(1)
    var tileRegionProgress: TileRegionLoadProgress? = null
    var tileRegion: TileRegion? = null
    var tileRegionError: TileRegionError? = null
    handler.post {
      TileStore.getInstance().loadTileRegion(
        TILE_REGION_ID,
        TileRegionLoadOptions.Builder()
          .geometry(TOKYO)
          .descriptors(
            listOf(
              offlineManager.createTilesetDescriptor(
                TilesetDescriptorOptions.Builder()
                  .styleURI(STYLE)
                  .minZoom(ZOOM.toInt().toByte())
                  .maxZoom(ZOOM.toInt().toByte())
                  .build()
              )
            )
          )
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .metadata(Value(TILE_REGION_METADATA))
          .build(),
        { tileRegionProgress = it }
      ) {
        if (it.isValue) {
          it.value?.let { region ->
            tileRegion = region
          }
        }
        if (it.isError) {
          Logger.e(TAG, "Error: ${it.error}")
          it.error?.let { error ->
            tileRegionError = error
          }
        }
        latch.countDown()
      }
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    } else {
      Assert.assertNotNull(tileRegion)
      Assert.assertNull(tileRegionError)
      Assert.assertNotNull(tileRegionProgress)

      Assert.assertEquals(
        tileRegion?.requiredResourceCount,
        tileRegion?.completedResourceCount
      )
      Assert.assertEquals(
        tileRegionProgress?.requiredResourceCount,
        tileRegionProgress?.completedResourceCount
      )
      Assert.assertEquals(0L, tileRegionProgress?.erroredResourceCount)
    }
  }

  @Test
  fun loadTileRegionCancel() {
    val latch = CountDownLatch(1)
    var tileRegionProgress: TileRegionLoadProgress? = null
    var tileRegion: TileRegion? = null
    var tileRegionError: TileRegionError? = null
    var cancelableTileStoreTask: Cancelable? = null
    handler.post {
      cancelableTileStoreTask = TileStore.getInstance().loadTileRegion(
        TILE_REGION_ID,
        TileRegionLoadOptions.Builder()
          .geometry(TOKYO)
          .descriptors(
            listOf(
              offlineManager.createTilesetDescriptor(
                TilesetDescriptorOptions.Builder()
                  .styleURI(STYLE)
                  .minZoom(ZOOM.toInt().toByte())
                  .maxZoom(ZOOM.toInt().toByte())
                  .build()
              )
            )
          )
          .metadata(Value(TILE_REGION_METADATA))
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build(),
        {
          tileRegionProgress = it
          cancelableTileStoreTask?.cancel()
        }
      ) {
        if (it.isValue) {
          it.value?.let { region ->
            tileRegion = region
          }
        }
        if (it.isError) {
          Logger.e(TAG, "Error: ${it.error}")
          it.error?.let { error ->
            tileRegionError = error
          }
        }
        latch.countDown()
      }
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    } else {
      Assert.assertNull(tileRegion)
      Assert.assertNotNull(tileRegionError)
      Assert.assertNotNull(tileRegionProgress)

      Assert.assertNotEquals(
        tileRegionProgress?.requiredResourceCount,
        tileRegionProgress?.completedResourceCount
      )
      Assert.assertEquals(TileRegionErrorType.CANCELED, tileRegionError?.type)
    }
  }

  // TODO revisit after https://github.com/mapbox/mapbox-maps-android/issues/297
  // this test must behave same as resourceLoadingFromTileStoreMapboxSwitch
  // but for now we could check for style loaded event only to style is loaded
  @Test
  fun resourceLoadingFromTileStoreAirplaneMode() {
    disableAmbientCache()
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(2)
    var resourceRequests = 0
    var mapLoadingErrorCount = 0
    val observer = object : Observer() {
      override fun notify(event: Event) {
        Logger.e(TAG, "type ${event.type}, data ${event.data.toJson()}")
        val data = event.getResourceEventData()
        if (!data.cancelled && data.dataSource == "database" && data.request.kind == "tile") {
          resourceRequests++
        }
        if (event.type == MapEvents.MAP_LOADING_ERROR) {
          mapLoadingErrorCount++
        }
        if (event.type == MapEvents.MAP_LOADED || event.type == MapEvents.STYLE_LOADED) {
          latch.countDown()
        }
      }
    }
    switchAirplaneMode()
    verifyAirplaneModeEnabled()
    prepareMapView(
      observer,
      listOf(
        MapEvents.RESOURCE_REQUEST,
        MapEvents.MAP_LOADED,
        MapEvents.STYLE_LOADED,
        MapEvents.MAP_LOADING_ERROR
      )
    )
    try {
      // we do not throw exception here but verify request count
      latch.await(10, TimeUnit.SECONDS)
      // verify resource requests came from tile store
      MatcherAssert.assertThat(resourceRequests, Matchers.greaterThan(0))
      // verify no map loading errors occurred
      // TODO uncomment after https://github.com/mapbox/mapbox-maps-android/issues/297
//      Assert.assertEquals(0, mapLoadingErrorCount)
    } finally {
      switchAirplaneMode()
    }
  }

  @Test
  fun resourceLoadingFromTileStoreMapboxSwitch() {
    disableAmbientCache()
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(2)
    var resourceRequests = 0
    var mapLoadingErrorCount = 0
    val observer = object : Observer() {
      override fun notify(event: Event) {
        Logger.e(TAG, "type ${event.type}, data ${event.data.toJson()}")
        val data = event.getResourceEventData()
        if (!data.cancelled && data.dataSource == "database" && data.request.kind == "tile") {
          resourceRequests++
        }
        if (event.type == MapEvents.MAP_LOADING_ERROR) {
          mapLoadingErrorCount++
        }
        if (event.type == MapEvents.MAP_LOADED || event.type == MapEvents.STYLE_LOADED) {
          latch.countDown()
        }
      }
    }
    OfflineSwitch.getInstance().isMapboxStackConnected = false
    prepareMapView(
      observer,
      listOf(
        MapEvents.RESOURCE_REQUEST,
        MapEvents.MAP_LOADED,
        MapEvents.STYLE_LOADED,
        MapEvents.MAP_LOADING_ERROR
      )
    )
    try {
      // map loaded, style loaded
      if (latch.await(10, TimeUnit.SECONDS)) {
        // verify resource requests came from tile store
        MatcherAssert.assertThat(resourceRequests, Matchers.greaterThan(0))
        // verify no map loading errors occurred
        Assert.assertEquals(0, mapLoadingErrorCount)
      } else {
        throw TimeoutException()
      }
    } finally {
      OfflineSwitch.getInstance().isMapboxStackConnected = true
    }
  }

  @Test
  fun deleteTileRegion() {
    loadTileRegionNoCancel()
    val resultWithRegion = queryTileRegions()
    Assert.assertNull(resultWithRegion.second)
    Assert.assertEquals(1, resultWithRegion.first.size)
    Assert.assertEquals(TILE_REGION_ID, resultWithRegion.first[0].id)
    TileStore.getInstance().removeTileRegion(TILE_REGION_ID)
    val resultWithNoRegion = queryTileRegions()
    Assert.assertNull(resultWithNoRegion.second)
    Assert.assertEquals(0, resultWithNoRegion.first.size)
  }

  @Test
  fun deleteStylePack() {
    loadStylePackNoCancel()
    val stylePackWithStyle = queryStylePacks()
    Assert.assertNull(stylePackWithStyle.second)
    Assert.assertEquals(1, stylePackWithStyle.first.size)
    Assert.assertEquals(STYLE, stylePackWithStyle.first[0].styleURI)
    offlineManager.removeStylePack(STYLE)
    val stylePackWithNoStyle = queryStylePacks()
    Assert.assertNull(stylePackWithNoStyle.second)
    Assert.assertEquals(0, stylePackWithNoStyle.first.size)
  }

  @Ignore("TODO uncomment after https://github.com/mapbox/mapbox-maps-android/issues/297")
  @Test
  fun idleEventTileStoreAirplaneMode() {
    disableAmbientCache()
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(1)
    var idleEventCount = 0
    val observer = object : Observer() {
      override fun notify(event: Event) {
        Logger.e(TAG, "type ${event.type}, data ${event.data.toJson()}")
        if (event.type == MapEvents.MAP_IDLE) {
          idleEventCount++
        }
      }
    }
    switchAirplaneMode()
    verifyAirplaneModeEnabled()
    prepareMapView(
      observer,
      listOf(MapEvents.MAP_IDLE)
    )
    try {
      // we do not throw timeout exception and verify we have exactly 1 IDLE event after timeout
      latch.await(10, TimeUnit.SECONDS)
      Assert.assertEquals(1, idleEventCount)
    } finally {
      switchAirplaneMode()
    }
  }

  @Test
  fun idleEventTileStoreMapboxSwitch() {
    disableAmbientCache()
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(1)
    var idleEventCount = 0
    val observer = object : Observer() {
      override fun notify(event: Event) {
        Logger.e(TAG, "type ${event.type}, data ${event.data.toJson()}")
        if (event.type == MapEvents.MAP_IDLE) {
          idleEventCount++
        }
      }
    }
    OfflineSwitch.getInstance().isMapboxStackConnected = false
    prepareMapView(
      observer,
      listOf(MapEvents.MAP_IDLE)
    )
    try {
      // we do not throw timeout exception and verify we have exactly 1 IDLE event after timeout
      latch.await(10, TimeUnit.SECONDS)
      Assert.assertEquals(1, idleEventCount)
    } finally {
      OfflineSwitch.getInstance().isMapboxStackConnected = true
    }
  }

  private fun queryStylePacks(): Pair<List<StylePack>, StylePackError?> {
    var stylePackList: List<StylePack> = listOf()
    var stylePackError: StylePackError? = null
    val latch = CountDownLatch(1)
    handler.post {
      offlineManager.getAllStylePacks { expected ->
        if (expected.isValue) {
          expected.value?.let { stylePackList = it }
        }
        if (expected.isError) {
          expected.error?.let { stylePackError = it }
        }
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    return Pair(stylePackList, stylePackError)
  }

  private fun queryTileRegions(): Pair<List<TileRegion>, TileRegionError?> {
    var tileRegionList: List<TileRegion> = listOf()
    var tileRegionError: TileRegionError? = null
    val latch = CountDownLatch(1)
    TileStore.getInstance().getAllTileRegions { expected ->
      if (expected.isValue) {
        expected.value?.let { tileRegionList = it }
      }
      if (expected.isError) {
        expected.error?.let { tileRegionError = it }
      }
      latch.countDown()
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    return Pair(tileRegionList, tileRegionError)
  }

  private fun disableAmbientCache() {
    val latch = CountDownLatch(1)
    // set 0 size ambient cache to make use of only tile store + style packs
    handler.post {
      cacheManager.setMaximumAmbientCacheSize(0) {
        if (it.isValue) {
          latch.countDown()
        }
      }
    }
    if (!latch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  private fun loadTileStoreWithStylePack() {
    val latch = CountDownLatch(1)
    handler.post {
      TileStore.getInstance().loadTileRegion(
        TILE_REGION_ID,
        TileRegionLoadOptions.Builder()
          .geometry(TOKYO)
          .descriptors(
            listOf(
              offlineManager.createTilesetDescriptor(
                TilesetDescriptorOptions.Builder()
                  // load style pack here and not separately to reduce amount of code
                  .styleURI(STYLE)
                  .stylePackOptions(
                    StylePackLoadOptions.Builder()
                      .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
                      .metadata(Value(STYLE_PACK_METADATA))
                      .build()
                  )
                  // set min zoom twice less in order to make sure tile in loaded map view
                  // will be fully visible and not somewhere on tile edge
                  .minZoom((ZOOM.toInt() / 2).toByte())
                  .maxZoom(ZOOM.toInt().toByte())
                  .build()
              )
            )
          )
          .metadata(Value(TILE_REGION_METADATA))
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build(),
        {}
      ) {
        if (it.isValue) {
          it.value?.let { latch.countDown() }
        } else {
          Logger.e(TAG, "Error when loading tile: ${it.error?.toString()}")
        }
      }
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  private fun prepareMapView(observer: Observer, eventList: List<String>) {
    var mapboxMap: MapboxMap
    rule.scenario.onActivity {
      it.runOnUiThread {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // uses default resource options under the hood
        val mapView = MapView(
          context = context,
          mapInitOptions = MapInitOptions(
            context = context,
            cameraOptions = CameraOptions.Builder()
              .center(TOKYO)
              .zoom(ZOOM)
              .build()
          )
        )
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.subscribe(observer, eventList)
        mapboxMap.loadStyleUri(STYLE)
        it.setContentView(mapView)
        mapView.onStart()
      }
    }
  }

  private fun switchAirplaneMode() {
    InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("am start -a android.settings.AIRPLANE_MODE_SETTINGS")
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.waitForIdle()
    // adopted for current CI device
    device.findObject(UiSelector().textContains("Airplane")).click()
    device.waitForIdle()
    val latch = CountDownLatch(1)
    // need to wait some time for pretty slow CI device
    latch.await(5, TimeUnit.SECONDS)
    device.pressBack()
  }

  private fun verifyAirplaneModeEnabled() {
    val latch = CountDownLatch(1)
    handler.post {
      val airplaneModeEnabled = android.provider.Settings.Global.getInt(
        InstrumentationRegistry.getInstrumentation().targetContext.contentResolver,
        android.provider.Settings.Global.AIRPLANE_MODE_ON,
        0
      ) != 0
      Assert.assertEquals("Airplane mode should be enabled!", true, airplaneModeEnabled)
      latch.countDown()
    }
    if (!latch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  companion object {
    private const val TAG = "OfflineTest"
    private const val ZOOM = 12.0
    private val TOKYO: Point = Point.fromLngLat(139.769305, 35.682027)
    private const val STYLE = Style.MAPBOX_STREETS
    private const val STYLE_PACK_METADATA = "STYLE_PACK_METADATA"
    private const val TILE_REGION_ID = "TILE_REGION_ID"
    private const val TILE_REGION_METADATA = "TILE_REGION_METADATA"
    private const val DEFAULT_AMBIENT_CACHE_SIZE_BYTES = 50_000_000L
  }
}