package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Point
import com.mapbox.maps.test.R
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class LegacyOfflineManagerTest {

  private lateinit var offlineManager: OfflineRegionManagerInterface

  @Test
  fun sanity() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      offlineManager = OfflineRegionManager(
        ResourceOptions.Builder()
          .accessToken(context.getString(R.string.mapbox_access_token))
          .cachePath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/test.mbgl")
          .assetPath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath)
          .cacheSize(50000L)
          .build()
      )
      offlineManager.createOfflineRegion(
        OfflineRegionGeometryDefinition.Builder()
          .geometry(Point.fromLngLat(0.0, 0.0))
          .minZoom(12.0)
          .maxZoom(12.0)
          .pixelRatio(1.0f)
          .glyphsRasterizationMode(GlyphsRasterizationMode.NO_GLYPHS_RASTERIZED_LOCALLY)
          .styleURL(Style.MAPBOX_STREETS)
          .build()
      ) {
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}