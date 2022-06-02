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
import com.mapbox.maps.extension.observable.model.DataSourceType
import com.mapbox.maps.extension.observable.model.RequestType
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
  private val tileStore: TileStore by lazy {
    TileStore.create().also {
      // Users need to make sure the custom TileStore is initialised properly with valid access token
      it.setOption(
        TileStoreOptions.MAPBOX_ACCESS_TOKEN,
        TileDataDomain.MAPS,
        Value(ResourceOptionsManager.getDefault(InstrumentationRegistry.getInstrumentation().targetContext).resourceOptions.accessToken)
      )
    }
  }
  private val resourceOptions = ResourceOptions.Builder().applyDefaultParams(
    InstrumentationRegistry.getInstrumentation().targetContext
  ).tileStore(tileStore).build()
  private val offlineManager: OfflineManager by lazy {
    OfflineManager(resourceOptions)
  }

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  fun setUp() {
    val latch = CountDownLatch(1)
    handler.post {
      offlineManager.removeStylePack(STYLE)
      tileStore.removeTileRegion(TILE_REGION_ID)
      tileStore.setOption(TileStoreOptions.DISK_QUOTA, Value(0))
      MapboxMap.clearData(resourceOptions) {
        if (!it.isError) {
          latch.countDown()
        }
      }
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
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
            logE(TAG, "Error: ${it.error}")
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
      // TODO remove -1 when issue will be fixed in gl-native, requiredResourceCount should be equal to completedResourceCount
      Assert.assertEquals(
        downloadedStyle?.requiredResourceCount,
        (downloadedStyle?.completedResourceCount ?: 0) - 1L
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
            logE(TAG, "Error: ${it.error}")
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
      tileStore.loadTileRegion(
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
          logE(TAG, "Error: ${it.error}")
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
      cancelableTileStoreTask = tileStore.loadTileRegion(
        TILE_REGION_ID,
        TileRegionLoadOptions.Builder()
          .geometry(TOKYO)
          .descriptors(
            listOf(
              offlineManager.createTilesetDescriptor(
                TilesetDescriptorOptions.Builder()
                  .styleURI(STYLE)
                  // Make the zoom range lager to make sure the download process not complete to fast.
                  .minZoom((ZOOM - 5).toInt().toByte())
                  .maxZoom((ZOOM + 5).toInt().toByte())
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
          logE(TAG, "Error: ${it.error}")
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
      Assert.assertEquals(TileRegionErrorType.CANCELED, tileRegionError?.type)
      Assert.assertNull(tileRegion)
      Assert.assertNotNull(tileRegionError)
      Assert.assertNotNull(tileRegionProgress)

      Assert.assertNotEquals(
        tileRegionProgress?.requiredResourceCount,
        tileRegionProgress?.completedResourceCount
      )
    }
  }

  @Test
  fun resourceLoadingFromTileStoreAirplaneMode() {
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(2)
    var resourceRequests = 0
    var mapLoadingErrorCount = 0
    val observer = Observer { event ->
      logE(TAG, "type ${event.type}, data ${event.data.toJson()}")
      when (event.type) {
        MapEvents.RESOURCE_REQUEST -> {
          val data = event.getResourceEventData()
          if (!data.cancelled && data.dataSource == DataSourceType.DATABASE && data.request.kind == RequestType.SOURCE) {
            resourceRequests++
          }
        }
        MapEvents.MAP_LOADING_ERROR -> {
          mapLoadingErrorCount++
        }
        else -> {
          latch.countDown()
        }
      }
    }
    switchAirplaneMode()
    verifyAirplaneModeEnabled()
    // Make the zoom lever one more larger to make sure all the tiles are downloaded.
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
      // verify resource requests came from tile store, map will be shown
      MatcherAssert.assertThat(resourceRequests, Matchers.greaterThan(0))
      // map load errors will still occur because of no internet
      MatcherAssert.assertThat(mapLoadingErrorCount, Matchers.greaterThan(0))
    } finally {
      switchAirplaneMode()
    }
  }

  @Test
  fun resourceLoadingFromTileStoreMapboxSwitch() {
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(2)
    var resourceRequests = 0
    var mapLoadingErrorCount = 0
    val observer = Observer { event ->
      logE(TAG, "type ${event.type}, data ${event.data.toJson()}")
      when (event.type) {
        MapEvents.RESOURCE_REQUEST -> {
          val data = event.getResourceEventData()
          if (!data.cancelled && data.dataSource == DataSourceType.DATABASE && data.request.kind == RequestType.TILE) {
            resourceRequests++
          }
        }
        MapEvents.MAP_LOADING_ERROR -> {
          mapLoadingErrorCount++
        }
        else -> {
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
      ),
      ZOOM + 1
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
    tileStore.removeTileRegion(TILE_REGION_ID)
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

  // It's still discussable whether we should trigger MAP_IDLE event
  // if there's no internet and Mapbox stack is set to True.
  // Currently it works as designed - we do not trigger MAP_IDLE if any
  // errors occurred during style load.
  @Test
  fun idleEventTileStoreAirplaneMode() {
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(1)
    var idleEventCount = 0
    var mapLoadingErrorCount = 0
    val observer = Observer { event ->
      logE(TAG, "type ${event.type}, data ${event.data.toJson()}")
      if (event.type == MapEvents.MAP_IDLE) {
        idleEventCount++
      }
      if (event.type == MapEvents.MAP_LOADING_ERROR) {
        mapLoadingErrorCount++
      }
    }
    switchAirplaneMode()
    verifyAirplaneModeEnabled()
    // Show map with the zoom level larger than download range to trigger loading error
    prepareMapView(
      observer,
      listOf(MapEvents.MAP_IDLE, MapEvents.MAP_LOADING_ERROR),
      ZOOM + 3
    )
    try {
      // we do not throw timeout exception and verify we have exactly 1 IDLE event after timeout
      latch.await(10, TimeUnit.SECONDS)
      MatcherAssert.assertThat(mapLoadingErrorCount, Matchers.greaterThan(0))
      Assert.assertEquals(0, idleEventCount)
    } finally {
      switchAirplaneMode()
    }
  }

  @Test
  fun idleEventTileStoreMapboxSwitch() {
    loadTileStoreWithStylePack()
    val latch = CountDownLatch(1)
    var idleEventCount = 0
    var mapLoadingErrorCount = 0
    val observer = Observer { event ->
      logE(TAG, "type ${event.type}, data ${event.data.toJson()}")
      if (event.type == MapEvents.MAP_IDLE) {
        idleEventCount++
      }
      if (event.type == MapEvents.MAP_LOADING_ERROR) {
        mapLoadingErrorCount++
      }
    }
    OfflineSwitch.getInstance().isMapboxStackConnected = false
    // Set zoom 1 larger to make sure all tiles are download.
    prepareMapView(
      observer,
      listOf(MapEvents.MAP_IDLE),
      ZOOM + 1
    )
    try {
      // we wait for whole time and verify we hit exactly one IDLE event
      latch.await(10, TimeUnit.SECONDS)
      Assert.assertEquals(0, mapLoadingErrorCount)
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
    tileStore.getAllTileRegions { expected ->
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

  private fun loadTileStoreWithStylePack() {
    val latch = CountDownLatch(1)
    handler.post {
      tileStore.loadTileRegion(
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
                  // set min and max zoom in range [zoom-2, zoom+2] to make sure tile in loaded map view
                  // will be fully visible and not somewhere on tile edge
                  .minZoom((ZOOM - 2).toInt().toByte())
                  .maxZoom((ZOOM + 2).toInt().toByte())
                  .build()
              )
            )
          )
          .metadata(Value(TILE_REGION_METADATA))
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build(),
        { progress ->
          logI(TAG, "completedResourceCount: ${progress.completedResourceCount}, requiredResourceCount: ${progress.requiredResourceCount}")
        }
      ) {
        if (it.isValue) {
          it.value?.let { latch.countDown() }
        } else {
          logE(TAG, "Error when loading tile: ${it.error?.toString()}")
        }
      }
    }
    if (!latch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  private fun prepareMapView(observer: Observer, eventList: List<String>, zoom: Double = ZOOM) {
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
              .zoom(zoom)
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
  }
}