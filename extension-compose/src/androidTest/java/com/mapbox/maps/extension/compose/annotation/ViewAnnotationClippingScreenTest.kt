package com.mapbox.maps.extension.compose.annotation

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.core.app.ActivityScenario
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.junit.After
import org.junit.Assert.fail
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * Verifies on screen that [ViewAnnotation] content is redrawn when only its shadow/clip [Shape]
 * changes.
 * Intentionally avoids ComposeTestRule and reads pixels via raw [PixelCopy],
 * test harness and `captureToImage` force extra frames that hide a stale frame from the screen.
 */
public class ViewAnnotationClippingScreenTest {

  private var anchorCutsCorner: Boolean by mutableStateOf(false)

  private val contentCoordinates = AtomicReference<LayoutCoordinates?>(null)
  private var scenario: ActivityScenario<ComponentActivity>? = null
  private var reusableBitmap: Bitmap? = null

  @After
  public fun tearDown() {
    anchorCutsCorner = false
    contentCoordinates.set(null)
    scenario?.close()
    reusableBitmap?.recycle()
    reusableBitmap = null
  }

  @Test
  public fun shadowShapeIsRedrawnOnAnchorChange() {
    val scenario = launchWithAnnotation(useKeyWorkaround = false)

    assumePixelCopySupported(scenario)
    awaitContentDrawn(scenario)
    assertCornerColor(scenario, isRed = true)

    scenario.onActivity { anchorCutsCorner = true }
    assertCornerColor(scenario, isRed = false)
  }

  @Test
  public fun shadowShapeIsRedrawnOnAnchorChangeWithKeyWorkaround() {
    val scenario = launchWithAnnotation(useKeyWorkaround = true)

    assumePixelCopySupported(scenario)
    awaitContentDrawn(scenario)
    assertCornerColor(scenario, isRed = true)

    scenario.onActivity { anchorCutsCorner = true }
    assertCornerColor(scenario, isRed = false)
  }

  private fun launchWithAnnotation(useKeyWorkaround: Boolean): ActivityScenario<ComponentActivity> {
    val scenario = ActivityScenario.launch(ComponentActivity::class.java).also { scenario = it }
    scenario.onActivity { activity ->
      val composeView = ComposeView(activity)
      composeView.setContent {
        MapboxMap(
          Modifier.fillMaxSize(),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(HELSINKI)
            }
          },
        ) {
          val density = LocalDensity.current
          ViewAnnotation(
            options = viewAnnotationOptions {
              geometry(HELSINKI)
              annotationAnchor {
                anchor(
                  if (anchorCutsCorner) {
                    ViewAnnotationAnchor.TOP_RIGHT
                  } else {
                    ViewAnnotationAnchor.BOTTOM_LEFT
                  }
                )
              }
              width(with(density) { 120.dp.toPx() }.toDouble())
              height(with(density) { 60.dp.toPx() }.toDouble())
              allowOverlap(true)
            }
          ) {
            if (useKeyWorkaround) {
              key(anchorCutsCorner) {
                CalloutContent()
              }
            } else {
              CalloutContent()
            }
          }
        }
      }
      activity.setContentView(composeView)
    }
    return scenario
  }

  @Composable
  private fun CalloutContent() {
    Box(
      Modifier
        .size(width = 120.dp, height = 60.dp)
        .background(Color.Green)
        .onGloballyPositioned { contentCoordinates.set(it) }
    ) {
      Box(
        Modifier
          .fillMaxSize()
          .shadow(elevation = 8.dp, shape = AnchorDependentShape(anchorCutsCorner))
          .background(Color.Red)
      ) {
        Column(Modifier.align(Alignment.Center)) {
          Text(text = "label", fontSize = 14.sp, color = Color.White)
          Text(text = anchorCutsCorner.toString(), fontSize = 10.sp, color = Color.White)
        }
      }
    }
  }

  private fun awaitContentDrawn(scenario: ActivityScenario<ComponentActivity>) {
    val deadline = System.currentTimeMillis() + CONTENT_APPEAR_TIMEOUT_MS
    while (System.currentTimeMillis() < deadline) {
      Thread.sleep(PIXEL_ASSERT_RETRY_DELAY_MS)
      if (sampleCornerWhenContentDrawn(scenario) != null) {
        return
      }
    }
    fail("ViewAnnotation content never appeared on screen within ${CONTENT_APPEAR_TIMEOUT_MS}ms.")
  }

  // Skips (rather than fails) the visual test when the window cannot be captured at all — the case
  // on software-GL emulators like the google_atd CI image, where PixelCopy over the map SurfaceView
  // always errors. A single successful copy proves the renderer supports it; the assertions then run
  // on any hardware-GPU device (locally, or a real-device CI lane).
  private fun assumePixelCopySupported(scenario: ActivityScenario<ComponentActivity>) {
    val deadline = System.currentTimeMillis() + PIXEL_COPY_PROBE_TIMEOUT_MS
    while (System.currentTimeMillis() < deadline) {
      if (captureWindowWithoutInvalidation(scenario) != null) {
        return
      }
      Thread.sleep(PIXEL_ASSERT_RETRY_DELAY_MS)
    }
    assumeTrue(
      "Window PixelCopy is unsupported on this renderer (software GL / ATD emulator); skipping " +
        "on-screen clip verification. Runs on a hardware-GPU device.",
      false
    )
  }

  // Content is a green box covered by a red box shaped by AnchorDependentShape: full rectangle
  // keeps the top-left corner red, the cut-corner shape reveals green there. Polls real frames
  // without invalidating anything until the corner matches, fails on timeout. Assumes the content
  // is already on screen (see [awaitContentDrawn]), so this budget covers only the redraw.
  private fun assertCornerColor(scenario: ActivityScenario<ComponentActivity>, isRed: Boolean) {
    val deadline = System.currentTimeMillis() + REDRAW_ASSERT_TIMEOUT_MS
    var lastCorner: Color? = null
    while (System.currentTimeMillis() < deadline) {
      Thread.sleep(PIXEL_ASSERT_RETRY_DELAY_MS)
      val corner = sampleCornerWhenContentDrawn(scenario) ?: continue
      lastCorner = corner
      if (corner.isRoughly(Color.Red) == isRed) {
        return
      }
    }
    fail(
      "Expected corner pixel to be ${if (isRed) "red (inside clip)" else "green (clipped away)"}" +
        " but it was $lastCorner. The clip shape was not redrawn."
    )
  }

  // Captures one real frame without invalidating anything and returns the top-left corner color,
  // but only once the content is genuinely drawn: the bottom-right quadrant is inside the clip
  // shape in both states, so a red sample there proves the content is present. Returns null on any
  // transient miss (view not yet positioned, capture failed, content not drawn) so callers retry.
  private fun sampleCornerWhenContentDrawn(
    scenario: ActivityScenario<ComponentActivity>,
  ): Color? {
    var liveBounds: Rect? = null
    scenario.onActivity {
      liveBounds = contentCoordinates.get()?.takeIf { it.isAttached }?.boundsInWindow()
    }
    val bounds = liveBounds ?: return null
    val bitmap = captureWindowWithoutInvalidation(scenario) ?: return null
    if (bounds.width == 0f || bounds.left < 0 || bounds.top < 0 ||
      bounds.right > bitmap.width || bounds.bottom > bitmap.height
    ) {
      return null
    }
    val inside = Color(
      bitmap.getPixel(
        (bounds.left + bounds.width * 3 / 4).toInt(),
        (bounds.top + bounds.height * 3 / 4).toInt()
      )
    )
    if (!inside.isRoughly(Color.Red)) {
      return null
    }
    return Color(
      bitmap.getPixel(
        bounds.left.toInt() + CORNER_OFFSET_PX,
        bounds.top.toInt() + CORNER_OFFSET_PX
      )
    )
  }

  private fun captureWindowWithoutInvalidation(
    scenario: ActivityScenario<ComponentActivity>,
  ): Bitmap? {
    var bitmap: Bitmap? = null
    val latch = CountDownLatch(1)
    scenario.onActivity { activity ->
      val decorView = activity.window.decorView
      if (decorView.width == 0 || decorView.height == 0) {
        latch.countDown()
        return@onActivity
      }
      val existing = reusableBitmap
      val target = if (existing != null &&
        existing.width == decorView.width &&
        existing.height == decorView.height
      ) {
        existing
      } else {
        existing?.recycle()
        Bitmap.createBitmap(decorView.width, decorView.height, Bitmap.Config.ARGB_8888)
          .also { reusableBitmap = it }
      }
      PixelCopy.request(
        activity.window,
        target,
        { result ->
          if (result == PixelCopy.SUCCESS) {
            bitmap = target
          }
          latch.countDown()
        },
        Handler(Looper.getMainLooper())
      )
    }
    check(latch.await(PIXEL_COPY_TIMEOUT_MS, TimeUnit.MILLISECONDS)) { "PixelCopy timed out" }
    // Null on a transient copy failure, the caller retries.
    return bitmap
  }

  private fun Color.isRoughly(expected: Color): Boolean =
    kotlin.math.abs(red - expected.red) < CHANNEL_TOLERANCE &&
      kotlin.math.abs(green - expected.green) < CHANNEL_TOLERANCE &&
      kotlin.math.abs(blue - expected.blue) < CHANNEL_TOLERANCE

  // A new, non-equal instance on every recomposition, like a typical user-defined callout shape.
  private class AnchorDependentShape(private val cutCorner: Boolean) : Shape {
    override fun createOutline(
      size: Size,
      layoutDirection: LayoutDirection,
      density: Density,
    ): Outline =
      if (cutCorner) {
        CutCornerTopLeftShape.createOutline(size, layoutDirection, density)
      } else {
        RectangleShape.createOutline(size, layoutDirection, density)
      }
  }

  private companion object {
    private const val ZOOM: Double = 9.0
    // Cold map + style init on a contended CI host (sharded emulators) can take many seconds
    // before the annotation is first placed on screen; the redraw itself is a frame or two.
    // Budget the two separately so init time is not charged against the redraw assertion.
    private const val CONTENT_APPEAR_TIMEOUT_MS = 30000L
    private const val REDRAW_ASSERT_TIMEOUT_MS = 10000L
    // Long enough for a hardware renderer's first successful window copy, short enough that a
    // software-GL device (where every copy errors) skips quickly.
    private const val PIXEL_COPY_PROBE_TIMEOUT_MS = 12000L
    private const val PIXEL_ASSERT_RETRY_DELAY_MS = 250L
    private const val PIXEL_COPY_TIMEOUT_MS = 5000L
    private const val CHANNEL_TOLERANCE = 0.35f

    /** Distance from the corner used for sampling, safely inside/outside the cut corner. */
    private const val CORNER_OFFSET_PX = 4

    /** A rectangle with the top-left quadrant cut away. */
    private val CutCornerTopLeftShape = GenericShape { size, _ ->
      moveTo(size.width / 2f, 0f)
      lineTo(size.width, 0f)
      lineTo(size.width, size.height)
      lineTo(0f, size.height)
      lineTo(0f, size.height / 2f)
      lineTo(size.width / 2f, size.height / 2f)
      close()
    }
  }
}