package com.mapbox.maps.testapp.overlay

import android.view.View
import android.widget.FrameLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.overlay.MapOverlayCoordinatesProvider
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.overlay.getMapOverlayPlugin
import com.mapbox.maps.testapp.BaseMapTest
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MapOverlayPluginTest : BaseMapTest() {

  private lateinit var mapOverlayPlugin: MapOverlayPlugin

  @Before
  fun setUp() {
    super.before()
    mapOverlayPlugin = mapView.getMapOverlayPlugin()
  }

  @Test
  fun setMargins() {
    mapOverlayPlugin.setDisplayingAreaMargins(10, 20, 30, 40)
    val edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(
      EdgeInsets(10.0, 20.0, 30.0, 40.0),
      edgeInsets
    )
  }

  @Test
  fun registerOverlay() {
    var edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      edgeInsets
    )

    val leftTop = View(context)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.addView(leftTop)
        val leftTopParams = FrameLayout.LayoutParams(leftTop.layoutParams)
        leftTopParams.setMargins(0, 0, 300, (mapView.height - 50.0).toInt())
        leftTop.layoutParams = leftTopParams
      }
    }

    val rightTop = View(context)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.addView(rightTop)
        val rightTopParams = FrameLayout.LayoutParams(rightTop.layoutParams)
        rightTopParams.setMargins(300, 0, 0, (mapView.height - 50.0).toInt())
        rightTop.layoutParams = rightTopParams
      }
    }

    mapOverlayPlugin.registerOverlay(leftTop)
    mapOverlayPlugin.registerOverlay(rightTop)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(
      EdgeInsets(50.0, 0.0, 0.0, 0.0),
      edgeInsets
    )

    val leftBottom = View(context)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.addView(leftBottom)
        val leftBottomParams = FrameLayout.LayoutParams(leftBottom.layoutParams)
        leftBottomParams.setMargins(300, (mapView.height - 50.0).toInt(), 300, 0)
        leftBottom.layoutParams = leftBottomParams
      }
    }

    val rightBottom = View(context)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.addView(rightBottom)
        val rightBottomParams = FrameLayout.LayoutParams(rightBottom.layoutParams)
        rightBottomParams.setMargins(300, (mapView.height - 50.0).toInt(), 0, 0)
        rightBottom.layoutParams = rightBottomParams
      }
    }

    mapOverlayPlugin.registerOverlay(leftBottom)
    mapOverlayPlugin.registerOverlay(rightBottom)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(
      EdgeInsets(50.0, 0.0, 50.0, 0.0),
      edgeInsets
    )

    mapOverlayPlugin.unregisterOverlay(leftTop)
    mapOverlayPlugin.unregisterOverlay(leftBottom)
    mapOverlayPlugin.unregisterOverlay(rightTop)
    mapOverlayPlugin.unregisterOverlay(rightBottom)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      edgeInsets
    )
  }

  @Test
  fun getReframeCameraOption() {
    mapOverlayPlugin.reframe {
      assertNull(it)
    }
    mapOverlayPlugin.registerMapOverlayCoordinatesProvider(object : MapOverlayCoordinatesProvider {
      override fun getShownCoordinates(): List<Point> {
        return listOf(
          Point.fromLngLat(0.0, 0.0),
          Point.fromLngLat(10.0, 20.0)
        )
      }
    })

    mapOverlayPlugin.reframe {
      assertNotNull(it)
    }
  }

  @Test
  fun reframe() {
    mapOverlayPlugin.registerMapOverlayCoordinatesProvider(object : MapOverlayCoordinatesProvider {
      override fun getShownCoordinates(): List<Point> {
        return listOf(
          Point.fromLngLat(0.0, 0.0),
          Point.fromLngLat(10.0, 20.0)
        )
      }
    })
    val leftTop = View(context)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.addView(leftTop)
        val leftTopParams = FrameLayout.LayoutParams(leftTop.layoutParams)
        leftTopParams.setMargins(0, 0, 300, (mapView.height - 50.0).toInt())
        leftTop.layoutParams = leftTopParams
      }
    }

    mapOverlayPlugin.reframe {
      assertNotNull(it)
      mapboxMap.jumpTo(it!!)
      val currentCameraOptions = mapboxMap.getCameraOptions(EdgeInsets(0.0, 0.0, 0.0, 0.0))
      assertEquals(currentCameraOptions.center!!.latitude(), it.center!!.latitude(), 0.01)
      assertEquals(currentCameraOptions.center!!.longitude(), it.center!!.longitude(), 0.01)
    }
  }
}