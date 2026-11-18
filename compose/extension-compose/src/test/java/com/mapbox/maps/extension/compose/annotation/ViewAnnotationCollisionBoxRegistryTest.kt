package com.mapbox.maps.extension.compose.annotation

import android.content.Context
import android.view.View
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
internal class ViewAnnotationCollisionBoxRegistryTest {

  private lateinit var host: View
  private lateinit var manager: ViewAnnotationManager

  /** Every box list the registry handed to the SDK, in order. */
  private val handedOver = mutableListOf<List<ScreenBox>>()

  @Before
  fun setUp() {
    // findRootCoordinates() is an extension function, so it is stubbed on the file class that
    // holds it. Every LayoutCoordinates handed to setRootCoordinates below must answer it.
    mockkStatic("androidx.compose.ui.layout.LayoutCoordinatesKt")
    val context = ApplicationProvider.getApplicationContext<Context>()
    host = spyk(View(context))
    handedOver.clear()
    manager = mockk(relaxed = true)
    every { manager.setCollisionBoxes(any(), capture(handedOver)) } returns true
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun boxesAreStoredInPositionOrderWhateverOrderTheyWereReported() {
    val lower = markedPart()
    val upper = markedPart()
    val root = rootWith(
      lower to Rect(10f, 80f, 40f, 100f),
      upper to Rect(10f, 20f, 40f, 40f),
    )
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)

    // Reported bottom part first. Compose does not promise a stable order.
    registry.report(key = "lower", coordinates = lower)
    registry.report(key = "upper", coordinates = upper)

    val stored = storedBoxes()
    assertEquals(2, stored.size)
    assertEquals(20.0, stored[0].min.y, 0.0)
    assertEquals(80.0, stored[1].min.y, 0.0)
  }

  @Test
  fun anUnchangedBoxSetIsNotStoredAgain() {
    val part = markedPart()
    val root = rootWith(part to Rect(0f, 0f, 10f, 10f))
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)
    registry.report(key = "a", coordinates = part)

    // The next layout pass reports the very same position again.
    registry.report(key = "a", coordinates = part)

    assertEquals(1, handedOver.size)
    verify(exactly = 1) { manager.setCollisionBoxes(host, any()) }
  }

  @Test
  fun theSdkIsToldDirectlyAndNoLayoutPassIsRequested() {
    val part = markedPart()
    val root = rootWith(part to Rect(0f, 0f, 10f, 10f))
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)

    registry.report(key = "a", coordinates = part)

    // requestLayout() would be thrown away inside Compose's AndroidView, so the SDK is asked
    // straight away instead. Verified on a device: with requestLayout() the boxes stayed stale.
    verify(exactly = 1) { manager.setCollisionBoxes(host, any()) }
    verify(exactly = 0) { host.requestLayout() }
  }

  @Test
  fun nothingIsStoredUntilAMarkedPartReports() {
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)

    registry.setRootCoordinates(rootWith())

    // Not an empty list: an annotation with no marked parts must fall through to
    // the SDK's own behaviour, not be told to use the whole annotation explicitly.
    assertTrue(handedOver.isEmpty())
    verify(exactly = 0) { manager.setCollisionBoxes(any(), any()) }
  }

  @Test
  fun removingTheLastMarkedPartStoresAnEmptyList() {
    val part = markedPart()
    val root = rootWith(part to Rect(0f, 0f, 10f, 10f))
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)
    registry.report(key = "a", coordinates = part)
    assertEquals(1, storedBoxes().size)

    registry.remove(key = "a")

    // Empty, not null. The map reads "nothing" as "no change" and would keep the old box.
    assertEquals(emptyList<ScreenBox>(), storedBoxes())
  }

  @Test
  fun aPartThatLeftTheLayoutIsNotReported() {
    val present = markedPart()
    val gone = markedPart(attached = false)
    val root = rootWith(
      present to Rect(0f, 0f, 10f, 10f),
      gone to Rect(0f, 50f, 10f, 60f),
    )
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)

    registry.report(key = "present", coordinates = present)
    registry.report(key = "gone", coordinates = gone)

    val stored = storedBoxes()
    assertEquals(1, stored.size)
    assertEquals(0.0, stored[0].min.y, 0.0)
  }

  @Test
  fun boxesAreMeasuredAgainstTheComposeRootAndNotTheBoxThatWasHandedOver() {
    val pin = markedPart()
    // An annotation created with an explicit width and height: the host is 900 x 900, the content
    // measures 495 x 495, and wrapContentSize centres it, putting its corner at (203, 203).
    // Core reads collision boxes as offsets from the host's corner, so this is where the pin is.
    val root = rootWith(pin to Rect(413f, 381f, 490f, 458f))
    // What the layout pass hands over is the centred content box, not the root.
    val centredContent = contentBoxWithin(root, pin to Rect(210f, 178f, 287f, 255f))
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(centredContent)

    registry.report(key = "pin", coordinates = pin)

    // Measured against the content box every box comes out 203 px short on both axes, and the map
    // then keeps labels out of an empty corner of the annotation while covering the pin itself.
    val stored = storedBoxes().single()
    assertEquals(413.0, stored.min.x, 0.0)
    assertEquals(381.0, stored.min.y, 0.0)
    assertEquals(490.0, stored.max.x, 0.0)
    assertEquals(458.0, stored.max.y, 0.0)
  }

  @Test
  fun boxesTheSdkWouldNotTakeAreHandedOverAgainOnTheNextLayout() {
    val part = markedPart()
    val root = rootWith(part to Rect(0f, 0f, 10f, 10f))
    // The first layout can run before the annotation has been added, and the SDK cannot record
    // boxes for a view it does not know yet, so the first attempt is refused.
    every { manager.setCollisionBoxes(any(), capture(handedOver)) } returnsMany listOf(false, true)
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)
    registry.report(key = "a", coordinates = part)

    // The next layout reports the same position. Nothing was recorded last time, so unchanged
    // boxes still have to be offered again rather than skipped.
    registry.report(key = "a", coordinates = part)

    assertEquals(2, handedOver.size)
    assertEquals(handedOver[0], handedOver[1])
  }

  @Test
  fun aMarkedPartWithNoSizeIsNotStored() {
    val drawn = markedPart()
    val empty = markedPart()
    val root = rootWith(
      drawn to Rect(0f, 0f, 10f, 10f),
      empty to Rect(20f, 20f, 20f, 20f),
    )
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)
    registry.report(key = "drawn", coordinates = drawn)

    registry.report(key = "empty", coordinates = empty)

    // The view based collector skips zero sized marked views as well. A box with no area is
    // still a box to the map, so keeping it would say this annotation collides with nothing.
    val stored = storedBoxes()
    assertEquals(1, stored.size)
    assertEquals(0.0, stored[0].min.x, 0.0)
    assertEquals(10.0, stored[0].max.x, 0.0)
  }

  @Test
  fun anAnnotationWhoseOnlyMarkedPartHasNoSizeFallsBackToItsFullBounds() {
    val empty = markedPart()
    val root = rootWith(empty to Rect(20f, 20f, 20f, 20f))
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)

    registry.report(key = "empty", coordinates = empty)

    // Empty rather than one box with no area. An empty list is what tells the map to go back to
    // using the whole annotation, which is what the modifier documents for an unmarked one.
    assertEquals(emptyList<ScreenBox>(), storedBoxes())
  }

  @Test
  fun aMarkedPartUnderItsOwnComposeRootIsNotStored() {
    val inAnnotation = markedPart()
    val root = rootWith(inAnnotation to Rect(0f, 0f, 10f, 10f))
    // A Popup, Dialog or DropdownMenu builds its content in a sub-composition with a compose
    // root of its own. The CompositionLocal still reaches it, so a marked composable in there
    // reports coordinates belonging to a tree this annotation's root knows nothing about.
    val popupRoot = mockk<LayoutCoordinates>()
    val inPopup = markedPart()
    every { inPopup.findRootCoordinates() } returns popupRoot
    val registry = ViewAnnotationCollisionBoxRegistry(host, manager)
    registry.setRootCoordinates(root)
    registry.report(key = "annotation", coordinates = inAnnotation)

    registry.report(key = "popup", coordinates = inPopup)

    // Measuring it against this annotation's root throws from findCommonAncestor, inside
    // onGloballyPositioned, which takes the app down. Only the annotation's own box is kept.
    val stored = storedBoxes()
    assertEquals(1, stored.size)
    assertEquals(10.0, stored[0].max.x, 0.0)
  }

  private fun storedBoxes(): List<ScreenBox> = handedOver.last()

  private fun markedPart(attached: Boolean = true): LayoutCoordinates =
    mockk<LayoutCoordinates>().also { every { it.isAttached } returns attached }

  private fun rootWith(vararg parts: Pair<LayoutCoordinates, Rect>): LayoutCoordinates =
    mockk<LayoutCoordinates>().also { root ->
      every { root.isAttached } returns true
      // The root of the compose tree is its own root.
      every { root.findRootCoordinates() } returns root
      parts.forEach { (part, rect) ->
        every { part.findRootCoordinates() } returns root
        every { root.localBoundingBoxOf(part, false) } returns rect
      }
    }

  /**
   * A box sitting inside [root] rather than at its corner, which is what the layout pass hands
   * over once `wrapContentSize` has centred the content inside a larger host.
   */
  private fun contentBoxWithin(
    root: LayoutCoordinates,
    vararg parts: Pair<LayoutCoordinates, Rect>
  ): LayoutCoordinates = mockk<LayoutCoordinates>().also { box ->
    every { box.isAttached } returns true
    every { box.findRootCoordinates() } returns root
    parts.forEach { (part, rect) ->
      every { box.localBoundingBoxOf(part, false) } returns rect
    }
  }
}