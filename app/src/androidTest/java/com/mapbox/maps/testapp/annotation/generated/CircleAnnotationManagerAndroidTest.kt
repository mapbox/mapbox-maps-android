// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Basic smoke tests for CircleAnnotationManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CircleAnnotationManagerAndroidTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_attribution)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testCircleElevationReference() {
    rule.runOnUiThread {
      val expectedValue = CircleElevationReference.NONE
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleElevationReference = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleElevationReference)
      circleAnnotationManager.circleElevationReference = null
      val expectedDefaultValue = CircleElevationReference.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-elevation-reference").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, circleAnnotationManager.circleElevationReference)
    }
  }

  @Test
  fun testCircleSortKey() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleSortKey = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleSortKey)
      circleAnnotationManager.circleSortKey = null
      assertEquals(null, circleAnnotationManager.circleSortKey)
    }
  }

  @Test
  fun testCircleBlur() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleBlur = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleBlur)
      circleAnnotationManager.circleBlur = null
      assertEquals(null, circleAnnotationManager.circleBlur)
    }
  }

  @Test
  fun testCircleColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleColorString = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleColorString)
      circleAnnotationManager.circleColorString = null
      assertEquals(
        null,
        circleAnnotationManager.circleColorString
      )
    }
  }

  @Test
  fun testCircleEmissiveStrength() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleEmissiveStrength = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleEmissiveStrength)
      circleAnnotationManager.circleEmissiveStrength = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-emissive-strength").silentUnwrap(), circleAnnotationManager.circleEmissiveStrength)
    }
  }

  @Test
  fun testCircleOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleOpacity = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleOpacity)
      circleAnnotationManager.circleOpacity = null
      assertEquals(null, circleAnnotationManager.circleOpacity)
    }
  }

  @Test
  fun testCirclePitchAlignment() {
    rule.runOnUiThread {
      val expectedValue = CirclePitchAlignment.MAP
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circlePitchAlignment = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circlePitchAlignment)
      circleAnnotationManager.circlePitchAlignment = null
      val expectedDefaultValue = CirclePitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, circleAnnotationManager.circlePitchAlignment)
    }
  }

  @Test
  fun testCirclePitchScale() {
    rule.runOnUiThread {
      val expectedValue = CirclePitchScale.MAP
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circlePitchScale = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circlePitchScale)
      circleAnnotationManager.circlePitchScale = null
      val expectedDefaultValue = CirclePitchScale.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, circleAnnotationManager.circlePitchScale)
    }
  }

  @Test
  fun testCircleRadius() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleRadius = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleRadius)
      circleAnnotationManager.circleRadius = null
      assertEquals(null, circleAnnotationManager.circleRadius)
    }
  }

  @Test
  fun testCircleStrokeColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleStrokeColorString = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleStrokeColorString)
      circleAnnotationManager.circleStrokeColorString = null
      assertEquals(
        null,
        circleAnnotationManager.circleStrokeColorString
      )
    }
  }

  @Test
  fun testCircleStrokeOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleStrokeOpacity = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleStrokeOpacity)
      circleAnnotationManager.circleStrokeOpacity = null
      assertEquals(null, circleAnnotationManager.circleStrokeOpacity)
    }
  }

  @Test
  fun testCircleStrokeWidth() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleStrokeWidth = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleStrokeWidth)
      circleAnnotationManager.circleStrokeWidth = null
      assertEquals(null, circleAnnotationManager.circleStrokeWidth)
    }
  }

  @Test
  fun testCircleTranslate() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleTranslate = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleTranslate)
      circleAnnotationManager.circleTranslate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").silentUnwrap(), circleAnnotationManager.circleTranslate)
    }
  }

  @Test
  fun testCircleTranslateAnchor() {
    rule.runOnUiThread {
      val expectedValue = CircleTranslateAnchor.MAP
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleTranslateAnchor = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleTranslateAnchor)
      circleAnnotationManager.circleTranslateAnchor = null
      val expectedDefaultValue = CircleTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, circleAnnotationManager.circleTranslateAnchor)
    }
  }

  @Test
  fun testSlot() {
    rule.runOnUiThread {
      val expectedValue = "abc"
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.slot = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.slot)
      circleAnnotationManager.slot = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "slot").silentUnwrap(), circleAnnotationManager.slot)
    }
  }

  @Test
  fun testMaxZoom() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.maxZoom = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.maxZoom)
      circleAnnotationManager.maxZoom = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "maxzoom").silentUnwrap(), circleAnnotationManager.maxZoom)
    }
  }

  @Test
  fun testMinZoom() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.minZoom = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.minZoom)
      circleAnnotationManager.minZoom = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "minzoom").silentUnwrap(), circleAnnotationManager.minZoom)
    }
  }

  @Test
  fun testCircleColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleColorUseTheme = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleColorUseTheme)
      circleAnnotationManager.circleColorUseTheme = null
      assertEquals(null, circleAnnotationManager.circleColorUseTheme)
    }
  }

  @Test
  fun testCircleStrokeColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      circleAnnotationManager.circleStrokeColorUseTheme = expectedValue
      assertEquals(expectedValue, circleAnnotationManager.circleStrokeColorUseTheme)
      circleAnnotationManager.circleStrokeColorUseTheme = null
      assertEquals(null, circleAnnotationManager.circleStrokeColorUseTheme)
    }
  }

  @Test
  fun create() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val annotation = circleAnnotationManager.create(
        CircleAnnotationOptions()
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )
      assertEquals(annotation, circleAnnotationManager.annotations[0])
    }
  }

  @Test
  fun createFromFeature() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val featureCollection =
        FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
      val annotations = circleAnnotationManager.create(featureCollection.toJson())
      assertEquals(annotations.first(), circleAnnotationManager.annotations[0])
      val annotations1 = circleAnnotationManager.create(featureCollection)
      assertEquals(annotations1.first(), circleAnnotationManager.annotations[1])
    }
  }

  @Test
  fun createList() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val list = listOf(
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )
      val annotations = circleAnnotationManager.create(list)
      assertEquals(annotations[0], circleAnnotationManager.annotations[0])
      assertEquals(annotations[1], circleAnnotationManager.annotations[1])
    }
  }

  @Test
  fun update() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val annotation = circleAnnotationManager.create(CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
      assertEquals(annotation, circleAnnotationManager.annotations[0])
      annotation.point = Point.fromLngLat(1.0, 1.0)
      circleAnnotationManager.update(annotation)
      assertEquals(annotation, circleAnnotationManager.annotations[0])
    }
  }

  @Test
  fun updateList() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val list = listOf(
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )
      val annotations = circleAnnotationManager.create(list)
      assertEquals(annotations[0], circleAnnotationManager.annotations[0])
      assertEquals(annotations[1], circleAnnotationManager.annotations[1])
      annotations[0].point = Point.fromLngLat(1.0, 1.0)
      annotations[1].point = Point.fromLngLat(1.0, 1.0)
      circleAnnotationManager.update(annotations)
      assertEquals(annotations[0], circleAnnotationManager.annotations[0])
      assertEquals(annotations[1], circleAnnotationManager.annotations[1])
    }
  }

  @Test
  fun delete() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val annotation = circleAnnotationManager.create(
        CircleAnnotationOptions()
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )
      assertEquals(annotation, circleAnnotationManager.annotations[0])
      circleAnnotationManager.delete(annotation)
      assertTrue(circleAnnotationManager.annotations.isEmpty())
    }
  }

  @Test
  fun deleteList() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val list = listOf(
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )
      val annotations = circleAnnotationManager.create(list)
      assertEquals(annotations[0], circleAnnotationManager.annotations[0])
      assertEquals(annotations[1], circleAnnotationManager.annotations[1])

      circleAnnotationManager.delete(annotations)
      assertTrue(circleAnnotationManager.annotations.isEmpty())
    }
  }

  @Test
  fun deleteAll() {
    rule.runOnUiThread {
      val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
      val list = listOf(
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
        CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )
      val annotations = circleAnnotationManager.create(list)
      assertEquals(annotations[0], circleAnnotationManager.annotations[0])
      assertEquals(annotations[1], circleAnnotationManager.annotations[1])

      circleAnnotationManager.deleteAll()
      assertTrue(circleAnnotationManager.annotations.isEmpty())
    }
  }

  fun ActivityScenarioRule<*>.runOnUiThread(block: () -> Unit) {
    this.scenario.onActivity {
      it.runOnUiThread(block)
    }
  }
}