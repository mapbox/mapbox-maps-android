package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.test.R
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class CacheManagerTest {

  private lateinit var cacheManager: CacheManagerInterface

  @Test
  fun clearAmbientCache() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      cacheManager = CacheManager(
        ResourceOptions.Builder()
          .accessToken(context.getString(R.string.mapbox_access_token))
          .cachePath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/mbx.db")
          .assetPath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath)
          .cacheSize(50000L)
          .build()
      )
      cacheManager.clearAmbientCache {
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun invalidateAmbientCache() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      cacheManager = CacheManager(
        ResourceOptions.Builder()
          .accessToken(context.getString(R.string.mapbox_access_token))
          .cachePath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/mbx.db")
          .assetPath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath)
          .cacheSize(50000L)
          .build()
      )
      cacheManager.invalidateAmbientCache {
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun setMaximumAmbientCacheSize() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      cacheManager = CacheManager(
        ResourceOptions.Builder()
          .accessToken(context.getString(R.string.mapbox_access_token))
          .cachePath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/mbx.db")
          .assetPath(InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath)
          .cacheSize(50000L)
          .build()
      )
      cacheManager.setMaximumAmbientCacheSize(50000) {
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}