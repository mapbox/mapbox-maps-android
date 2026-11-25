package com.mapbox.maps.testapp.integration.surface

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.testapp.EmptyActivity
import com.mapbox.maps.testapp.withLatch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CopyOnWriteArrayList

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 24)
class MapSurfaceFacadeTest {
  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Test
  fun facadeRendersOnTheBackingViewsSurface() {
    lateinit var mapView: MapView
    lateinit var surfaceView: SurfaceView
    lateinit var facade: MapSurface
    val rendererErrors = CopyOnWriteArrayList<String>()
    withLatch(timeoutMillis = 10000) { latch ->
      rule.scenario.onActivity { activity ->
        mapView = MapView(activity, MapInitOptions(activity, styleUri = null))
        mapView.addRendererSetupErrorListener { rendererErrors.add(it.toString()) }
        surfaceView = checkNotNull(mapView.rendererSurfaceView)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
          override fun surfaceCreated(holder: SurfaceHolder) {
            holder.removeCallback(this)
            facade = MapSurface(mapView)
            latch.countDown()
          }

          override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit

          override fun surfaceDestroyed(holder: SurfaceHolder) = Unit
        })
        activity.setContentView(mapView)
      }
    }

    lateinit var idleSubscription: Cancelable
    withLatch(timeoutMillis = 10000) { latch ->
      rule.scenario.onActivity {
        assertSame(mapView.mapboxMap, facade.mapboxMap)
        idleSubscription = facade.mapboxMap.subscribeMapIdle { latch.countDown() }
        facade.mapboxMap.loadStyle(
          """
          {
            "version": 8,
            "sources": {},
            "layers": [{
              "id": "background",
              "type": "background",
              "paint": {"background-color": "#00ff00"}
            }]
          }
          """.trimIndent()
        )
      }
    }
    rule.scenario.onActivity { idleSubscription.cancel() }

    val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    try {
      var copyResult = PixelCopy.ERROR_UNKNOWN
      withLatch(timeoutMillis = 10000) { latch ->
        rule.scenario.onActivity {
          PixelCopy.request(
            surfaceView,
            bitmap,
            {
              copyResult = it
              latch.countDown()
            },
            Handler(Looper.getMainLooper())
          )
        }
      }

      assertEquals(PixelCopy.SUCCESS, copyResult)
      assertEquals(Color.GREEN, bitmap.getPixel(0, 0))
      assertTrue("Unexpected renderer errors: $rendererErrors", rendererErrors.isEmpty())
    } finally {
      bitmap.recycle()
    }
  }
}