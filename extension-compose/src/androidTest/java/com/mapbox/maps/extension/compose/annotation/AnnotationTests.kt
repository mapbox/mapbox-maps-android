package com.mapbox.maps.extension.compose.annotation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.coroutine.awaitStyle
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.generated.withCircleColor
import com.mapbox.maps.extension.compose.annotation.generated.withLineColor
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

public class AnnotationTests {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testCircleAnnotation() {
    var clickedAnnotation: CircleAnnotation? = null
    val testTag = "circle_annotation"

    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = SINGLE_POINT,
      testTag
    ) {
      CircleAnnotation(
        point = SINGLE_POINT,
        onClick = {
          clickedAnnotation = it
          true
        }
      ) {
        circleRadius = 20.0
        circleColor = Color.Blue
      }
    }

    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(SINGLE_POINT, clickedAnnotation!!.point)
  }

  @Test
  public fun testCircleAnnotationGroup() {
    var clickedAnnotation: CircleAnnotation? = null
    val testTag = "circle_annotation"
    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = CLUSTER_POINTS[2],
      zoom = ZOOM + 2,
      testTag = testTag
    ) {
      CircleAnnotationGroup(
        annotations = CLUSTER_POINTS.map {
          CircleAnnotationOptions()
            .withPoint(it)
            .withCircleRadius(10.0)
            .withCircleColor(Color.Red)
        },
        annotationConfig = AnnotationConfig(
          annotationSourceOptions = AnnotationSourceOptions(
            clusterOptions = ClusterOptions(
              colorLevels = listOf(
                Pair(100, Color.Red.toArgb()),
                Pair(50, Color.Blue.toArgb()),
                Pair(0, Color.Green.toArgb())
              )
            )
          )
        ),
        onClick = {
          clickedAnnotation = it
          true
        }
      )
    }
    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(CLUSTER_POINTS[2], clickedAnnotation!!.point)
    assertEquals(Color.Red.toArgb(), clickedAnnotation!!.circleColorInt)
  }

  @Test
  public fun testPointAnnotation() {
    var clickedAnnotation: PointAnnotation? = null
    val testTag = "point_annotation"

    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = SINGLE_POINT,
      testTag
    ) {
      val icon = rememberIconImage(resourceId = R.drawable.mapbox_mylocation_icon_bearing)
      PointAnnotation(
        point = SINGLE_POINT,
        onClick = {
          clickedAnnotation = it
          true
        }
      ) {
        iconImage = icon
      }
    }

    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(SINGLE_POINT, clickedAnnotation!!.point)
  }

  @Test
  public fun testPointAnnotationGroup() {
    var clickedAnnotation: PointAnnotation? = null
    val testTag = "point_annotation"
    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = CLUSTER_POINTS[2],
      zoom = ZOOM + 2,
      testTag = testTag
    ) {
      PointAnnotationGroup(
        annotations = CLUSTER_POINTS.map {
          PointAnnotationOptions()
            .withIconImage("bitmap")
            .withPoint(it)
        },
        annotationConfig = AnnotationConfig(
          annotationSourceOptions = AnnotationSourceOptions(
            clusterOptions = ClusterOptions(
              colorLevels = listOf(
                Pair(100, Color.Red.toArgb()),
                Pair(50, Color.Blue.toArgb()),
                Pair(0, Color.Green.toArgb())
              )
            )
          )
        ),
        onClick = {
          clickedAnnotation = it
          true
        }
      )

      MapEffect(Unit) { mapView ->
        mapView.mapboxMap.loadStyle(Style.STANDARD) {
          it.addImage(
            "bitmap",
            ContextCompat.getDrawable(mapView.context, R.drawable.mapbox_mylocation_icon_bearing)!!
              .toBitmap()
          )
        }
      }
    }

    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(CLUSTER_POINTS[2], clickedAnnotation!!.point)
  }

  @Test
  public fun testPolygonAnnotation() {
    var clickedAnnotation: PolygonAnnotation? = null
    val testTag = "polygon_annotation"

    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = POLYGON_POINTS.first().first(),
      testTag = testTag
    ) {
      PolygonAnnotation(
        points = POLYGON_POINTS,
        onClick = {
          clickedAnnotation = it
          true
        }
      ) {
        fillColor = Color.Red
      }
    }
    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(Color.Red.toArgb(), clickedAnnotation!!.fillColorInt)
    assertEquals(POLYGON_POINTS, clickedAnnotation!!.points)
  }

  @Test
  public fun testPolylineAnnotation() {
    var clickedAnnotation: PolylineAnnotation? = null
    val testTag = "polyline_annotation"
    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = HELSINKI,
      testTag = testTag
    ) {
      PolylineAnnotation(
        points = POLYLINE_POINTS,
        onClick = {
          clickedAnnotation = it
          true
        }
      ) {
        lineColor = Color.Red
        lineWidth = 15.0
      }
    }
    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(Color.Red.toArgb(), clickedAnnotation!!.lineColorInt)
    assertEquals(POLYLINE_POINTS, clickedAnnotation!!.points)
  }

  @Test
  public fun testPolylineAnnotationGroup() {
    var clickedAnnotation: PolylineAnnotation? = null
    val testTag = "polyline_annotation"
    setMapContent(
      cameraCenter = HELSINKI,
      annotationPoint = HELSINKI,
      testTag = testTag
    ) {
      PolylineAnnotationGroup(
        annotations = POLYLINE_GROUP_POINTS.map {
          PolylineAnnotationOptions()
            .withPoints(it)
            .withLineWidth(15.0)
            .withLineColor(Color.Blue)
        },
        annotationConfig = AnnotationConfig(),
        onClick = {
          clickedAnnotation = it
          true
        }
      )
    }
    composeTestRule.onNodeWithTag(testTag).performClick()
    composeTestRule.waitUntil { clickedAnnotation != null }
    assertEquals(Color.Blue.toArgb(), clickedAnnotation!!.lineColorInt)
    assertEquals(POLYLINE_POINTS, clickedAnnotation!!.points)
  }

  /**
   * @param cameraCenter coordinate of camera's center.
   * @param annotationPoint coordinate of annotation to be tested.
   */
  private fun setMapContent(
    cameraCenter: Point,
    annotationPoint: Point,
    testTag: String,
    zoom: Double = ZOOM,
    annotationBlock: @MapboxMapComposable @Composable () -> Unit,
  ) {
    composeTestRule.setContent {
      // screen coordinate of given annotation which can be used for adding Box with testTag
      var offset: IntOffset? by remember {
        mutableStateOf(null)
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = MapViewportState().apply {
            setCameraOptions {
              zoom(zoom)
              center(cameraCenter)
            }
          },
          style = {
            MapStyle(style = Style.DARK)
          },
        ) {
          annotationBlock()
          MapEffect(Unit) { mapView ->
            mapView.mapboxMap.awaitStyle()
            val screenCoordinate = mapView.mapboxMap.pixelForCoordinate(annotationPoint)
            offset = IntOffset(screenCoordinate.x.toInt(), screenCoordinate.y.toInt())
          }
        }
        if (offset != null) {
          Box(
            modifier = Modifier
              .size(5.dp)
              .offset { offset ?: IntOffset.Zero }
              .testTag(testTag)
          )
        }
      }
    }

    composeTestRule.waitUntil(5_000) {
      composeTestRule.onAllNodesWithTag(testTag)
        .fetchSemanticsNodes().size == 1
    }
  }

  public companion object {
    public const val MAP_TEST_TAG: String = "map_tag"
    public const val ZOOM: Double = 10.0
    private const val INTERVAL = 0.01
    public val CLUSTER_POINTS: List<Point> by lazy {
      mutableListOf<Point>().apply {
        repeat(10) { x ->
          repeat(10) { y ->
            add(
              Point.fromLngLat(
                HELSINKI.longitude() + INTERVAL * x,
                HELSINKI.latitude() + INTERVAL * y
              )
            )
          }
        }
      }
    }

    private val POLYGON_POINTS = listOf(
      listOf(
        Point.fromLngLat(24.9349, 60.1725),
        Point.fromLngLat(24.9429, 60.1725),
        Point.fromLngLat(24.9429, 60.1673),
        Point.fromLngLat(24.9349, 60.1673),
        Point.fromLngLat(24.9349, 60.1725),
      )
    )

    private val SINGLE_POINT = Point.fromLngLat(
      HELSINKI.longitude() - INTERVAL,
      HELSINKI.latitude() - INTERVAL
    )

    private val POLYLINE_POINTS = listOf(
      Point.fromLngLat(24.9439358, 60.1718468),
      HELSINKI,
      Point.fromLngLat(24.9389362, 60.1697015)
    )

    private val POLYLINE_GROUP_POINTS = listOf(
      POLYLINE_POINTS,
      listOf(
        Point.fromLngLat(24.9379887, 60.1748898),
        Point.fromLngLat(24.9364867, 60.1732249),
        Point.fromLngLat(24.9338259, 60.1730755)
      )
    )
  }
}