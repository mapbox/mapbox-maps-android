package com.mapbox.maps.testapp.annotation

import android.graphics.Color
import android.os.Handler
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.RenderMode
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.Symbol
import com.mapbox.maps.plugin.annotation.generated.SymbolManager
import com.mapbox.maps.plugin.annotation.generated.SymbolOptions
import com.mapbox.maps.plugin.annotation.generated.getSymbolManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.plugin.delegates.listeners.OnDidFinishRenderingMapListener
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test for verifying annotation updating while changing style
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class UpdateAnnotationTest : BaseMapTest(), OnDidFinishRenderingMapListener {
  private lateinit var runnable: Runnable
  private var index = 0
  private val latch = CountDownLatch(AnnotationUtils.STYLES.size * 3)
  private lateinit var symbolManager: SymbolManager
  private lateinit var symbol: Symbol

  @Test
  fun testUpdateAnnotation() {
    mapboxMap.addOnDidFinishRenderingMapListener(this)

    rule.scenario.onActivity {
      val handler = Handler(it.mainLooper)

      it.runOnUiThread {
        mapboxMap.loadStyleUri(AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]) {
          symbolManager = mapView.getAnnotationPlugin().getSymbolManager(mapView)
          symbol = symbolManager.create(
            SymbolOptions()
              .withIconColor(ColorUtils.colorToRgbaString(Color.RED))
              .withIconImage("car-15")
              .withPoint(Point.fromLngLat(0.0, 0.0))
          )
          Assert.assertEquals(symbol, symbolManager.annotations[0])
          runnable = Runnable {
            symbol.geometry = Point.fromLngLat(
              symbol.geometry.longitude() + 0.1,
              symbol.geometry.latitude()
            )
            symbolManager.update(symbol)
            handler.postDelayed(runnable, 100L)
          }
          handler.postDelayed(runnable, 100L)
        }
      }
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  override fun onDidFinishRenderingMap(mode: RenderMode) {
    latch.countDown()
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyleUri(AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size])
      }
    }
  }
}