package com.mapbox.maps.extension.compose.annotation

import android.view.ViewPropertyAnimator
import androidx.compose.ui.platform.ComposeView
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.ShadowLogConfiguration
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(shadows = [ShadowLogConfiguration::class])
@RunWith(RobolectricTestRunner::class)
internal class ViewAnnotationNodeTest {

  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var composeView: ComposeView
  private lateinit var parentNode: MapNode

  @Before
  fun setup() {
    viewAnnotationManager = mockk(relaxed = true)
    composeView = spyk(ComposeView(ApplicationProvider.getApplicationContext()))
    parentNode = mockk(relaxed = true)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `onRemoved disposes composition and removes view annotation`() {
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onRemoved(parentNode)

    verify { composeView.disposeComposition() }
    verify { viewAnnotationManager.removeViewAnnotation(view = composeView) }
  }

  @Test
  fun `onRemoved nulls updatedListener`() {
    val listener = mockk<OnViewAnnotationUpdatedListener>()
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = listener,
    )

    node.onAttached(parentNode)
    node.onRemoved(parentNode)

    assertNull(node.updatedListener)
  }

  @Test
  fun `onRemoved removes internal update listener when observing`() {
    val listener = mockk<OnViewAnnotationUpdatedListener>()
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = listener,
    )

    node.onAttached(parentNode)
    node.onRemoved(parentNode)

    verify { viewAnnotationManager.removeOnViewAnnotationUpdatedListener(any()) }
  }

  @Test
  fun `onRemoved skips removing internal update listener when not observing`() {
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onRemoved(parentNode)

    verify(exactly = 0) { viewAnnotationManager.removeOnViewAnnotationUpdatedListener(any()) }
  }

  @Test
  fun `onRemoved sets view composition strategy before disposing composition`() {
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onRemoved(parentNode)

    verifyOrder {
      composeView.setViewCompositionStrategy(any())
      composeView.disposeComposition()
    }
  }

  @Test
  fun `onClear sets view composition strategy before disposing composition`() {
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onClear()

    verifyOrder {
      composeView.setViewCompositionStrategy(any())
      composeView.disposeComposition()
    }
  }

  @Test
  fun `onClear disposes composition and removes view annotation`() {
    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onClear()

    verify { composeView.disposeComposition() }
    verify { viewAnnotationManager.removeViewAnnotation(view = composeView) }
  }

  @Test
  fun `onRemoved with disappear animation defers cleanup`() {
    setupDisappearAnimation()

    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onRemoved(parentNode)

    verify(exactly = 0) { viewAnnotationManager.removeViewAnnotation(view = composeView) }
    verify(exactly = 0) { composeView.disposeComposition() }
  }

  @Test
  fun `onRemoved with disappear animation cleans up after animation ends`() {
    val endActionSlot = slot<Runnable>()
    setupDisappearAnimation(endActionSlot)

    val node = ViewAnnotationNode(
      viewAnnotationManager = viewAnnotationManager,
      view = composeView,
      updatedListener = null,
    )

    node.onRemoved(parentNode)
    endActionSlot.captured.run()

    verify { viewAnnotationManager.removeViewAnnotation(view = composeView) }
    verify { composeView.disposeComposition() }
  }

  private fun setupDisappearAnimation(
    endActionSlot: CapturingSlot<Runnable>? = null,
  ): ViewPropertyAnimator {
    val animationConfig = MarkerAnimationConfig(
      listOf(MarkerAnimationEffect.Effect.Fade(from = 1f, to = 0f))
    )
    composeView.setTag(R.id.markerDisappearAnimation, animationConfig)

    val animator = mockk<ViewPropertyAnimator>(relaxed = true)
    every { composeView.animate() } returns animator
    every { animator.alpha(any()) } returns animator
    every { animator.setDuration(any()) } returns animator
    if (endActionSlot != null) {
      every { animator.withEndAction(capture(endActionSlot)) } returns animator
    }
    return animator
  }
}