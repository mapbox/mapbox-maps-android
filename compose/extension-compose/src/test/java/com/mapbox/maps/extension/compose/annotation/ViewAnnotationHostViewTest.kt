package com.mapbox.maps.extension.compose.annotation

import android.content.Context
import android.view.View
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ViewAnnotationHostViewTest {

  private val host =
    ViewAnnotationHostView(ApplicationProvider.getApplicationContext<Context>())

  @Test
  fun anAnnotationTheMapCouldNotPlaceLeavesTheAccessibilityTree() {
    host.alpha = 0f

    // Making a view transparent does not take it out of the accessibility tree on its own, so a
    // screen reader could still reach and click an annotation that is not on screen.
    assertEquals(
      View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS,
      host.importantForAccessibility
    )
  }

  @Test
  fun anAnnotationThatIsPlacedAgainComesBackToTheAccessibilityTree() {
    host.alpha = 0f

    host.alpha = 1f

    assertEquals(
      View.IMPORTANT_FOR_ACCESSIBILITY_AUTO,
      host.importantForAccessibility
    )
  }

  @Test
  fun anAnnotationStartsInTheAccessibilityTree() {
    assertEquals(
      View.IMPORTANT_FOR_ACCESSIBILITY_AUTO,
      host.importantForAccessibility
    )
  }
}