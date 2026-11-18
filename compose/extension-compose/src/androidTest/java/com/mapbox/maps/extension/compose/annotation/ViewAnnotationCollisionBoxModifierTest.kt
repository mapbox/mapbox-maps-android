package com.mapbox.maps.extension.compose.annotation

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.AnnotatedLayerFeature
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Covers the modifier itself rather than the registry behind it: what the composition does to a
 * box when the caller turns the flag off, and when the marked composable leaves.
 *
 * The registry is the real one and a real layout pass produces the coordinates, as it does
 * inside a [ViewAnnotation]. Only the SDK boundary is replaced, by [RecordingManager].
 *
 * There is no map here, so this test needs no access token.
 *
 * Only the first of the two tests fails if the modifier's `DisposableEffect` is removed. See
 * the note on the second.
 */
@OptIn(MapboxExperimental::class)
internal class ViewAnnotationCollisionBoxModifierTest {

  @get:Rule
  val composeTestRule: ComposeContentTestRule = createComposeRule()

  private lateinit var host: View
  private lateinit var manager: RecordingManager
  private lateinit var registry: ViewAnnotationCollisionBoxRegistry

  @Before
  fun setUp() {
    host = View(InstrumentationRegistry.getInstrumentation().targetContext)
    manager = RecordingManager()
    registry = ViewAnnotationCollisionBoxRegistry(host, manager)
  }

  @Test
  fun turningTheFlagOffTakesTheBoxAway() {
    val marked = mutableStateOf(true)
    composeTestRule.setContent {
      AnnotationContent {
        Box(
          Modifier
            .size(20.dp)
            .mbxViewAnnotationCollisionBox(enabled = marked.value)
        )
      }
    }
    composeTestRule.waitForIdle()
    assertEquals(1, manager.handedOver.last().size)

    composeTestRule.runOnUiThread { marked.value = false }
    composeTestRule.waitForIdle()

    // Empty, not absent. An empty list is what tells the map to use the whole annotation again.
    assertEquals(emptyList<ScreenBox>(), manager.handedOver.last())
  }

  /**
   * Unlike the test above, this one does not fail when the modifier's `DisposableEffect` is
   * taken out: the registry drops a detached box on the next layout pass anyway. It is here for
   * the behaviour the modifier documents, which has two mechanisms behind it rather than one.
   */
  @Test
  fun leavingTheCompositionTakesTheBoxAway() {
    val present = mutableStateOf(true)
    composeTestRule.setContent {
      AnnotationContent {
        if (present.value) {
          Box(
            Modifier
              .size(20.dp)
              .mbxViewAnnotationCollisionBox()
          )
        }
      }
    }
    composeTestRule.waitForIdle()
    assertEquals(1, manager.handedOver.last().size)

    composeTestRule.runOnUiThread { present.value = false }
    composeTestRule.waitForIdle()

    assertEquals(emptyList<ScreenBox>(), manager.handedOver.last())
  }

  /**
   * The part of [ViewAnnotation] the modifier needs: the registry in scope, and an outer box
   * whose coordinates become the compose root the marked parts are measured against.
   *
   */
  @Composable
  private fun AnnotationContent(content: @Composable () -> Unit) {
    Box(
      Modifier.onGloballyPositioned { registry.setRootCoordinates(it) }
    ) {
      CompositionLocalProvider(
        LocalViewAnnotationCollisionBoxRegistry provides registry
      ) {
        content()
      }
    }
  }

  /**
   * Records every box list the registry hands over.
   *
   * Written out rather than mocked: mockk builds its proxies with dexmaker, which cannot
   * subclass a Kotlin interface on device. The instrumentation test next door
   * (`MapboxMapTelemetryTest`) writes its fake out for the same reason. Only
   * [setCollisionBoxes] is ever called here; the rest of the interface is not part of this test.
   */
  private class RecordingManager : ViewAnnotationManager {

    val handedOver = mutableListOf<List<ScreenBox>>()

    override fun setCollisionBoxes(view: View, boxes: List<ScreenBox>): Boolean {
      handedOver.add(boxes)
      return true
    }

    override var viewAnnotationAvoidLayers: HashSet<String>
      get() = unused()
      set(_) = unused()

    override var viewAnnotationAvoidRegions: List<ScreenBox>
      get() = unused()
      set(_) = unused()

    override val annotations: Map<View, ViewAnnotationOptions> get() = unused()

    override fun addViewAnnotation(resId: Int, options: ViewAnnotationOptions): View = unused()

    override fun addViewAnnotation(
      resId: Int,
      options: ViewAnnotationOptions,
      asyncInflater: androidx.asynclayoutinflater.view.AsyncLayoutInflater,
      asyncInflateCallback: (View) -> Unit
    ): Unit = unused()

    override fun addViewAnnotation(view: View, options: ViewAnnotationOptions): Unit = unused()

    override fun removeViewAnnotation(view: View): Boolean = unused()

    override fun removeAllViewAnnotations(): Unit = unused()

    override fun updateViewAnnotation(view: View, options: ViewAnnotationOptions): Boolean =
      unused()

    override fun getViewAnnotation(annotatedLayerFeature: AnnotatedLayerFeature): View? = unused()

    override fun getViewAnnotationOptions(
      annotatedLayerFeature: AnnotatedLayerFeature
    ): ViewAnnotationOptions? = unused()

    override fun getViewAnnotationOptions(view: View): ViewAnnotationOptions? = unused()

    override fun addOnViewAnnotationUpdatedListener(
      listener: OnViewAnnotationUpdatedListener
    ): Unit = unused()

    override fun removeOnViewAnnotationUpdatedListener(
      listener: OnViewAnnotationUpdatedListener
    ): Unit = unused()

    override fun setViewAnnotationUpdateMode(mode: ViewAnnotationUpdateMode): Unit = unused()

    override fun getViewAnnotationUpdateMode(): ViewAnnotationUpdateMode = unused()

    override fun cameraForAnnotations(
      annotations: List<View>,
      edgeInsets: EdgeInsets?,
      bearing: Double?,
      pitch: Double?
    ): CameraOptions? = unused()

    override fun cameraForAnnotations(
      annotations: List<View>,
      edgeInsets: EdgeInsets?,
      bearing: Double?,
      pitch: Double?,
      result: (CameraOptions) -> Unit
    ): Unit = unused()

    private fun unused(): Nothing = throw AssertionError("not used by this test")
  }
}