package com.mapbox.maps.compose.testapp.examples.annotation

import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.GeometryCollection
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationAnchorConfig
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.examples.utils.SimulateRouteLocationProvider
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.coroutine.mapLoadedEvents
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.layers.generated.FillLayer
import com.mapbox.maps.extension.compose.style.layers.generated.LineCapValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJsonSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.annotationAnchors
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlinx.coroutines.flow.firstOrNull

/**
 * Example how to use dynamic view annotations on line layers and fixed positions.
 *
 * The example adds following 5 view annotations to the map:
 *
 * - ETA view annotation
 *    - Attached to the default route line geometry, configured to fit into the camera
 *    padding([ViewAnnotationOptions.ignoreCameraPadding]=false) and not overlapping with other view
 *    annotations or location puck([ViewAnnotationOptions.allowOverlap]=false and
 *    [ViewAnnotationOptions.allowOverlapWithPuck]=false).
 *
 * - Alternative ETA view annotation
 *    - Attached to the alternative routine line geometry, configured to fit into the camera
 *    padding([ViewAnnotationOptions.ignoreCameraPadding]=false) and not overlapping with other view
 *    annotations or location puck([ViewAnnotationOptions.allowOverlap]=false and
 *    [ViewAnnotationOptions.allowOverlapWithPuck]=false).
 *
 * - 2 parking view annotations
 *    - Attached to polygon geometries, configured to ignore the camera
 *    padding([ViewAnnotationOptions.ignoreCameraPadding]=true) and allow overlapping with other view
 *    annotations([ViewAnnotationOptions.allowOverlap]=true), so they would be visible on a further
 *    distance in the follow puck mode, where the padding top is set to 500.
 *    The parking view annotations are not allowed to overlap with the location puck ([ViewAnnotationOptions.allowOverlapWithPuck]=false).
 *
 * - Construction site view annotation
 *    - Attached to point geometry, configured to ignore the camera
 *    padding([ViewAnnotationOptions.ignoreCameraPadding]=true), allow overlapping with other view
 *    annotations([ViewAnnotationOptions.allowOverlap]=true), and allow overlapping with the location
 *    puck([ViewAnnotationOptions.allowOverlapWithPuck]=true). This is useful for high priority labels
 *    that shouldn't be covered by anything.
 */
public class DynamicViewAnnotationActivity : ComponentActivity() {
  private val featureRouteMain: Feature by lazy { getFeatureFromAsset(ROUTE_MAIN_GEOJSON) }
  private val featureRouteAlt: Feature by lazy { getFeatureFromAsset(ROUTE_ALT_GEOJSON) }
  private val featureCollectionParkings: FeatureCollection by lazy {
    getFeatureCollectionFromAsset(
      PARKINGS_GEOJSON
    )
  }
  private val featureCollectionConstructionSite: FeatureCollection by lazy {
    getFeatureCollectionFromAsset(
      CONSTRUCTION_GEOJSON
    )
  }
  private val overviewViewportStateOptions: OverviewViewportStateOptions by lazy {
    OverviewViewportStateOptions.Builder()
      .geometry(
        GeometryCollection.fromGeometries(
          listOf(
            featureRouteMain.geometry(),
            featureRouteAlt.geometry(),
          )
        )
      )
      .padding(
        EdgeInsets(
          /* top = */ 100.0,
          /* left = */ 100.0,
          /* bottom = */ 100.0,
          /* right = */ 100.0
        )
      )
      .build()
  }
  private val followPuckViewportStateOption = FollowPuckViewportStateOptions.Builder()
    .pitch(70.0)
    .zoom(18.0)
    .padding(
      EdgeInsets(
        /* top = */ 500.0,
        /* left = */ 100.0,
        /* bottom = */ 100.0,
        /* right = */ 100.0
      )
    )
    .build()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val viewportState = rememberMapViewportState {
        setCameraOptions {
          center(CityLocations.HELSINKI)
          bearing(0.0)
          pitch(0.0)
          zoom(ZOOM)
        }
      }

      var showDynamicViewAnnotations by remember {
        mutableStateOf(false)
      }

      var isMainActive by remember {
        mutableStateOf(true)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              onClick = {
                if ((viewportState.mapViewportStatus as? ViewportStatus.State)?.state is FollowPuckViewportState) {
                  viewportState.transitionToOverviewState(overviewViewportStateOptions)
                } else {
                  viewportState.transitionToFollowPuckState(followPuckViewportStateOption)
                }
              },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text(
                modifier = Modifier.padding(10.dp),
                text = if ((viewportState.mapViewportStatus as? ViewportStatus.State)?.state is FollowPuckViewportState) "Overview" else "Follow puck"
              )
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = viewportState,
            style = {
              NavigationStyle(isMainActive)
            }
          ) {
            MapEffect(Unit) { mapView ->
              mapView.location.apply {
                setLocationProvider(
                  SimulateRouteLocationProvider(
                    featureRouteMain.geometry() as LineString
                  )
                )
                locationPuck = LocationPuck2D(
                  bearingImage = ImageHolder.from(R.drawable.mapbox_user_puck_icon),
                )
                enabled = true
                puckBearingEnabled = true
                puckBearing = PuckBearing.COURSE
              }
              // Only show view annotation and adjust location puck position after all the runtime layers
              // are added.
              mapView.mapboxMap.mapLoadedEvents.firstOrNull()?.let {
                mapView.location.layerAbove = LAYER_CONSTRUCTION
                showDynamicViewAnnotations = true
                viewportState.transitionToOverviewState(overviewViewportStateOptions)
              }
            }

            if (showDynamicViewAnnotations) {
              DynamicViewAnnotations(isMainActive) {
                isMainActive = !isMainActive
              }
            }
          }
        }
      }
    }
  }

  @Composable
  @MapboxStyleComposable
  private fun NavigationStyle(isMainActive: Boolean) {
    val mainRouteSourceState = remember { GeoJsonSourceState() }
    mainRouteSourceState.data = GeoJSONData(if (isMainActive) featureRouteMain else featureRouteAlt)

    val altRouteSourceState = remember { GeoJsonSourceState() }
    altRouteSourceState.data = GeoJSONData(if (isMainActive) featureRouteAlt else featureRouteMain)

    val parkingSourceState = remember {
      GeoJsonSourceState().apply {
        data = GeoJSONData(featureCollectionParkings.features()!!)
      }
    }
    val constructionSourceState = remember {
      GeoJsonSourceState().apply {
        data = GeoJSONData(featureCollectionConstructionSite.features()!!)
      }
    }

    MapboxStandardStyle(
      topSlot = {
        LineLayer(
          sourceState = altRouteSourceState,
          layerId = LAYER_ALT_ID,
        ) {
          lineColor = ColorValue(
            Color(0xFF999999)
          )
          lineWidth = DoubleValue(12.0)
          lineBorderWidth = DoubleValue(2.0)
          lineBorderColor = ColorValue(Color(0xFF333333))
        }
        LineLayer(
          sourceState = mainRouteSourceState,
          layerId = LAYER_MAIN_ID
        ) {
          lineColor = ColorValue(Color(0xFF57A9FB))
          lineWidth = DoubleValue(12.0)
          lineCap = LineCapValue.ROUND
          lineBorderWidth = DoubleValue(2.0)
          lineBorderColor = ColorValue(Color(0xFF327AC2))
        }
        FillLayer(
          sourceState = parkingSourceState,
          layerId = LAYER_PARKING
        ) {
          fillColor = ColorValue(Color(0xff0080ff))
          fillOpacity = DoubleValue(0.5)
        }
        CircleLayer(
          sourceState = constructionSourceState,
          layerId = LAYER_CONSTRUCTION
        ) {
          circleColor = ColorValue(Color.Transparent)
        }
      }
    )
  }

  @Composable
  @MapboxMapComposable
  private fun DynamicViewAnnotations(isMainActive: Boolean, toggleActiveRoute: () -> Unit) {
    var etaViewAnnotationAnchor by remember {
      mutableStateOf(ViewAnnotationAnchor.BOTTOM_LEFT)
    }

    var alternativeEtaViewAnnotationAnchor by remember {
      mutableStateOf(ViewAnnotationAnchor.BOTTOM_LEFT)
    }
    // eta view
    ViewAnnotation(
      options = viewAnnotationOptions {
        annotatedLayerFeature(LAYER_MAIN_ID)
        annotationAnchors(
          {
            anchor(ViewAnnotationAnchor.TOP_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_LEFT)
          },
        )
      },
      onUpdatedListener = object : OnViewAnnotationUpdatedListener {
        override fun onViewAnnotationAnchorUpdated(view: View, anchor: ViewAnnotationAnchorConfig) {
          etaViewAnnotationAnchor = anchor.anchor
        }
      }
    ) {
      MainDVAContent(isInitial = isMainActive, etaViewAnnotationAnchor = etaViewAnnotationAnchor)
    }

    // alternative eta view
    ViewAnnotation(
      options = viewAnnotationOptions {
        annotatedLayerFeature(LAYER_ALT_ID)
        annotationAnchors(
          {
            anchor(ViewAnnotationAnchor.TOP_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_RIGHT)
          },
          {
            anchor(ViewAnnotationAnchor.BOTTOM_LEFT)
          },
        )
      },
      onUpdatedListener = object : OnViewAnnotationUpdatedListener {
        override fun onViewAnnotationAnchorUpdated(view: View, anchor: ViewAnnotationAnchorConfig) {
          alternativeEtaViewAnnotationAnchor = anchor.anchor
        }
      }
    ) {
      AlternativeDVAContent(
        isInitial = !isMainActive,
        alternativeEtaViewAnnotationAnchor = alternativeEtaViewAnnotationAnchor,
        onClick = toggleActiveRoute
      )
    }

    // parking view annotation 1
    ViewAnnotation(
      options = viewAnnotationOptions {
        allowOverlap(true)
        ignoreCameraPadding(true)
        annotatedLayerFeature(LAYER_PARKING) {
          featureId(PARKING_FEATURE_ID_1)
        }
      }
    ) {
      ParkingDVAContent("$4/h")
    }

    // parking view annotation 2
    ViewAnnotation(
      options = viewAnnotationOptions {
        allowOverlap(true)
        ignoreCameraPadding(true)
        annotatedLayerFeature(LAYER_PARKING) {
          featureId(PARKING_FEATURE_ID_2)
        }
      }
    ) {
      ParkingDVAContent("$6/h")
    }

    // construction view annotation
    ViewAnnotation(
      options = viewAnnotationOptions {
        allowOverlap(true)
        ignoreCameraPadding(true)
        allowOverlapWithPuck(true)
        annotatedLayerFeature(LAYER_CONSTRUCTION) {
          featureId(CONSTRUCTION_FEATURE_ID_1)
        }
      }
    ) {
      ConstructionDVAContent()
    }
  }

  @Preview
  @Composable
  @UiComposable
  public fun MainDVAContent(
    isInitial: Boolean = true,
    etaViewAnnotationAnchor: ViewAnnotationAnchor = ViewAnnotationAnchor.BOTTOM_LEFT,
    onClick: () -> Unit = {}
  ) {
    // etaViewAnnotationAnchor change did not trigger recomposition of shadow, use key composable
    // here to trigger recomposition of the box everytime the anchor changes.
    key(etaViewAnnotationAnchor) {
      Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .shadow(
                elevation = 8.dp,
                CalloutShape(etaViewAnnotationAnchor)
            )
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(unbounded = true)
      ) {
        Column(
          modifier = Modifier
              .align(Alignment.Center)
              .padding(30.dp)
        ) {
          Text(
            text = "25 min",
            fontSize = 25.sp,
            color = Color.White
          )
          if (isInitial) {
            Text(
              text = "This is the initially suggested route",
              fontSize = 12.sp,
              color = Color.White
            )
          }
        }
      }
    }
  }

  @Preview
  @Composable
  @UiComposable
  public fun AlternativeDVAContent(
    isInitial: Boolean = true,
    alternativeEtaViewAnnotationAnchor: ViewAnnotationAnchor = ViewAnnotationAnchor.BOTTOM_LEFT,
    onClick: () -> Unit = {}
  ) {
    // alternativeEtaViewAnnotationAnchor change did not trigger recomposition of shadow, use key composable
    // here to trigger recomposition of the box everytime the anchor changes.
    key(alternativeEtaViewAnnotationAnchor) {
      Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .shadow(
                elevation = 8.dp,
                CalloutShape(alternativeEtaViewAnnotationAnchor)
            )
            .background(Color.White)
            .wrapContentSize(unbounded = true)
      ) {
        Row(
          modifier = Modifier
            .align(Alignment.Center)
            .padding(30.dp)
        ) {
          Image(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .padding(5.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.arrow_straight),
            contentDescription = "arrow",
            colorFilter = ColorFilter.tint(Color(0xFF09AA74))
          )
          Column {
            Text(
              text = "Save 10 min",
              fontSize = 18.sp,
              color = Color.Black
            )
            Text(
              text = "Avoid traffic",
              fontSize = 12.sp,
              color = Color.Black
            )
            if (isInitial) {
              Text(
                text = "This is the initially suggested route",
                fontSize = 12.sp,
                color = Color.Black
              )
            }
          }
        }
      }
    }
  }

  @Preview
  @Composable
  @UiComposable
  public fun ParkingDVAContent(price: String = "$4/h") {
    Box(
      modifier = Modifier
          .width(110.dp)
          .height(40.dp)
          .background(Color.White, shape = CircleShape)
    ) {
      Row(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .padding(2.dp)
      ) {
        Text(
          modifier = Modifier
              .size(36.dp)
              .align(Alignment.CenterVertically)
              .background(MaterialTheme.colors.primary, shape = CircleShape),
          text = "P",
          fontSize = 25.sp,
          textAlign = TextAlign.Center,
          color = Color.White,
        )
        Text(
          modifier = Modifier
            .padding(start = 8.dp),
          text = price,
          textAlign = TextAlign.Center,
          fontSize = 25.sp,
          color = Color.Black,
        )
      }
    }
  }

  @Preview
  @Composable
  @UiComposable
  public fun ConstructionDVAContent() {
    Text(
      modifier = Modifier
          .size(40.dp)
          .padding(4.dp),
      text = "🚧",
      fontSize = 20.sp
    )
  }

  private fun getFeatureFromAsset(featureGeojson: String) =
    Feature.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this, featureGeojson
      )!!
    )

  private fun getFeatureCollectionFromAsset(featureGeojson: String) =
    FeatureCollection.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this, featureGeojson
      )!!
    )

  private companion object {
    const val ZOOM: Double = 2.0
    const val LAYER_MAIN_ID = "layer-main"
    const val LAYER_ALT_ID = "layer-alt"
    const val LAYER_PARKING = "layer-parking"
    const val LAYER_CONSTRUCTION = "layer-construction"

    const val PARKING_FEATURE_ID_1 = "parking-1"
    const val PARKING_FEATURE_ID_2 = "parking-2"
    const val CONSTRUCTION_FEATURE_ID_1 = "construction-1"

    const val ROUTE_MAIN_GEOJSON = "dva-sf-route-main.geojson"
    const val ROUTE_ALT_GEOJSON = "dva-sf-route-alternative.geojson"
    const val PARKINGS_GEOJSON = "dva-sf-parkings.geojson"
    const val CONSTRUCTION_GEOJSON = "dva-sf-construction.geojson"
  }
}

/**
 * Utility class to define the callout shape.
 */
private data class CalloutShape(private val anchor: ViewAnnotationAnchor?) : Shape {

  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    val path = Path()
    val scaledSize = size / density.density
    path.addArrow(anchor, scaledSize)
    path.addRoundRect(RoundRect(scaledSize.toRect().deflate(delta = 12f), CornerRadius(x = 16f)))
    val matrix = Matrix()
    matrix.setScale(density.density, density.density)
    path.asAndroidPath().transform(matrix)
    return Outline.Generic(path)
  }
}

private fun Path.addArrow(anchor: ViewAnnotationAnchor?, size: Size) {
  when (anchor) {
    ViewAnnotationAnchor.TOP_LEFT -> addPath(
      Path().apply {
        moveTo(x = 32f, y = 12f)
        cubicTo(x1 = 26f, y1 = 12f, x2 = 24.7f, y2 = 11.5f, x3 = 21.1f, y3 = 9.7f)
        lineTo(x = 1.4f, y = 0.1f)
        cubicTo(x1 = 0.6f, y1 = -0.3f, x2 = -0.3f, y2 = 0.6f, x3 = 0.1f, y3 = 1.5f)
        lineTo(x = 9.8f, y = 21f)
        cubicTo(x1 = 11.5f, y1 = 24.7f, x2 = 12f, y2 = 26f, x3 = 12f, y3 = 32f)
      },
    )

    ViewAnnotationAnchor.TOP_RIGHT -> addPath(
      Path().apply {
        moveTo(x = 20f, y = 32f)
        cubicTo(x1 = 20f, y1 = 26f, x2 = 20.5f, y2 = 24.7f, x3 = 22.2f, y3 = 21f)
        lineTo(x = 31.9f, y = 1.5f)
        cubicTo(x1 = 32.3f, y1 = 0.6f, x2 = 31.4f, y2 = -0.3f, x3 = 30.6f, y3 = 0.1f)
        lineTo(x = 10.9f, y = 9.7f)
        cubicTo(x1 = 7.3f, y1 = 11.5f, x2 = 6f, y2 = 12f, x3 = 0f, y3 = 12f)
      },
      Offset(x = size.width - 32f, y = 0f),
    )

    ViewAnnotationAnchor.BOTTOM_RIGHT -> addPath(
      Path().apply {
        moveTo(x = 0f, y = 20f)
        cubicTo(x1 = 6f, y1 = 20f, x2 = 7.3f, y2 = 20.5f, x3 = 10.9f, y3 = 22.3f)
        lineTo(x = 30.6f, y = 31.9f)
        cubicTo(x1 = 31.4f, y1 = 32.3f, x2 = 32.3f, y2 = 31.4f, x3 = 31.9f, y3 = 30.5f)
        lineTo(x = 22.2f, y = 11f)
        cubicTo(x1 = 20.5f, y1 = 7.3f, x2 = 20f, y2 = 6f, x3 = 20f, y3 = 0f)
      },
      Offset(x = size.width - 32f, y = size.height - 32f),
    )

    ViewAnnotationAnchor.BOTTOM_LEFT -> addPath(
      Path().apply {
        moveTo(x = 12f, y = 0f)
        cubicTo(x1 = 12f, y1 = 6f, x2 = 11.5f, y2 = 7.3f, x3 = 9.8f, y3 = 11f)
        lineTo(x = 0.1f, y = 30.5f)
        cubicTo(x1 = -0.3f, y1 = 31.4f, x2 = 0.6f, y2 = 32.3f, x3 = 1.4f, y3 = 31.9f)
        lineTo(x = 21.1f, y = 22.3f)
        cubicTo(x1 = 24.7f, y1 = 20.5f, x2 = 26f, y2 = 20f, x3 = 32f, y3 = 20f)
      },
      Offset(x = 0f, y = size.height - 32f),
    )

    else -> return
  }
}