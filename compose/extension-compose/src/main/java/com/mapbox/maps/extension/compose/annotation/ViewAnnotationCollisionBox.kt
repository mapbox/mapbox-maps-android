package com.mapbox.maps.extension.compose.annotation

import android.view.View
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.viewannotation.ViewAnnotationManager

/**
 * The registry that collects collision boxes for one view annotation.
 *
 * [ViewAnnotation] puts it in and [mbxViewAnnotationCollisionBox] reads it. It is a
 * CompositionLocal because the app's own composables sit between the two, and a parameter
 * would have to be added to every one of them to carry the registry down.
 *
 * Null when [mbxViewAnnotationCollisionBox] is used outside a [ViewAnnotation], so the modifier
 * does nothing.
 */
internal val LocalViewAnnotationCollisionBoxRegistry =
  compositionLocalOf<ViewAnnotationCollisionBoxRegistry?> { null }

/**
 * Marks this composable as a collision box for the view annotation around it.
 *
 * When at least one composable is marked, the map uses only the marked ones when it works out
 * what the annotation collides with. When none are marked, the map uses the whole annotation,
 * which is usually much larger than anything the user can see.
 *
 * The boxes always count for collision between view annotations. They count against the style's
 * own symbols only when the annotation sets `enableSymbolLayerCollision(true)`, which is what
 * lets an annotation hide a label underneath it.
 *
 * Marking is ignored when the annotation's own `ViewAnnotationOptions` carry `collisionBoxes`.
 * Those boxes are used instead, and they keep being used: once options have supplied them, this
 * annotation stops reading marked composables, even if a later update leaves them unset.
 *
 * Every marked composable reports its own box, at any depth. A box nested inside another
 * marked composable normally sits within the outer one and changes nothing. It matters only
 * when a child draws outside its parent's bounds.
 *
 * The Android View property of the same name follows a different rule: it reports the
 * outermost marked view and does not look inside it.
 *
 * Position, scale and rotation applied with [androidx.compose.ui.graphics.graphicsLayer] are
 * all reported, on the marked composable itself or on a parent. A composable moved only at draw
 * time, for example by a draw modifier that translates the canvas, is not: it keeps its
 * previously reported box until something else causes a layout pass.
 *
 * Hiding a marked composable with `alpha(0f)` leaves its layout in place, so it keeps its box
 * and still collides. Removing it from the composition withdraws its box. When the last marked
 * composable is gone the map goes back to using the whole annotation.
 *
 * A marked composable that measures zero in either direction reports no box, as the Android View
 * property also does. The map uses the whole annotation only while nothing is marked, so a box
 * enclosing nothing would take the annotation out of that fallback without replacing it with
 * anything the user can see.
 *
 * Moving a marked composable is reported even while the map is not showing the annotation, so an
 * annotation the map could not place comes back on its own once its boxes no longer collide.
 *
 * A marked composable shown in a `Popup`, `Dialog` or `DropdownMenu` is ignored, because it is
 * drawn in a window of its own rather than inside the annotation.
 *
 * Has no effect outside a [ViewAnnotation].
 */
@MapboxExperimental
public fun Modifier.mbxViewAnnotationCollisionBox(enabled: Boolean = true): Modifier =
  // Modifier.composed, and not the newer Modifier.Node: reading a CompositionLocal from a node
  // needs compose-ui 1.5, and this module is held at 1.3.3 by the Compose BOM.
  composed {
    val registry = LocalViewAnnotationCollisionBoxRegistry.current
    val key = remember { Any() }
    // Withdraw the box when this composable leaves, and when the caller turns the flag off.
    DisposableEffect(registry, key, enabled) {
      if (!enabled) registry?.remove(key)
      onDispose { registry?.remove(key) }
    }
    if (registry != null && enabled) {
      Modifier.onGloballyPositioned { registry.report(key, it) }
    } else {
      Modifier
    }
  }

/**
 * Collects the bounds of composables marked as collision boxes and stores them on [host], the
 * plain Android view that holds the annotation's compose content.
 *
 * The SDK cannot look inside compose content, so the boxes are handed over through
 * [ViewAnnotationManager.setCollisionBoxes] instead of being found by walking child views. They
 * replace the walk at its start, so everything after that runs exactly as it does for plain
 * Android views: the update to core is still skipped when the list has not changed, and the
 * collision debug overlay still draws the boxes. Neither needed a compose-specific branch.
 *
 * Handing them over is a direct call and not, say, a value left on [host] for the SDK to find,
 * because nothing would tell the SDK to look. `requestLayout()` is how the View API triggers that
 * re-read, and it does not work here: the request stops at Compose's AndroidView instead of
 * reaching the window, so the layout pass never runs.
 */
@OptIn(MapboxExperimental::class)
internal class ViewAnnotationCollisionBoxRegistry(
  private val host: View,
  private val manager: ViewAnnotationManager,
) {

  private val reports = LinkedHashMap<Any, LayoutCoordinates>()
  private var rootCoordinates: LayoutCoordinates? = null
  private var lastStored: List<ScreenBox>? = null
  private var ownsCollisionBoxes = false

  /**
   * The annotation's outer box, as the layout pass reported it.
   *
   * Box positions have to be relative to the top-left corner of [host], which is the space core
   * expects, and that corner belongs to the root of the compose tree rather than to the box
   * passed in here. The two are the same only while the content fills the host exactly. They
   * stop being the same as soon as the annotation is given an explicit width or height, because
   * `wrapContentSize` then centres the content inside the larger host and the box passed in here
   * moves away from the host's corner, while the boxes it measures do not. So the root is
   * resolved here instead of trusting the reported box to sit at the origin.
   */
  fun setRootCoordinates(coordinates: LayoutCoordinates) {
    rootCoordinates = coordinates.findRootCoordinates()
    store()
  }

  /** A marked composable reporting where it ended up. */
  fun report(key: Any, coordinates: LayoutCoordinates) {
    reports[key] = coordinates
    ownsCollisionBoxes = true
    store()
  }

  /** A marked composable that left the composition, or was turned off. */
  fun remove(key: Any) {
    if (reports.remove(key) != null) store()
  }

  private fun store() {
    // Nothing has ever been marked. No stored box list means this annotation does not use the
    // feature; an empty one would mean it does, and tells core to use the full annotation bounds.
    if (!ownsCollisionBoxes) return
    val root = rootCoordinates ?: return

    val boxes = reports.values
      // A marked composable inside a Popup, Dialog or DropdownMenu is left out. The
      // CompositionLocal still reaches those, because they are built with
      // rememberCompositionContext, but their content hangs under a compose root of its own.
      // Measuring it against this annotation's root throws, since the two share no parent, and
      // the throw would happen inside onGloballyPositioned and take the app down. Leaving it
      // out is also the right answer: a popup is drawn in its own window, so its bounds are not
      // in the annotation's coordinate space.
      .filter { it.isAttached && it.findRootCoordinates() === root }
      .map { root.localBoundingBoxOf(it, clipBounds = false) }
      // Core uses the full annotation only when this list is absent or empty, and otherwise uses
      // each box in it. A marked composable that measures nothing, because it is empty or because
      // the content it would have drawn is currently absent, would otherwise fill the list with a
      // rectangle enclosing nothing and take the annotation out of that fallback. The view based
      // collector leaves such views out for the same reason.
      .filter { it.width > 0f && it.height > 0f }
      .map { rect ->
        ScreenBox(
          ScreenCoordinate(rect.left.toDouble(), rect.top.toDouble()),
          ScreenCoordinate(rect.right.toDouble(), rect.bottom.toDouble())
        )
      }
      // Compose does not promise a stable report order and the comparison below is order
      // sensitive, so without sorting a reshuffle would look like a change and send an update.
      .sortedWith(compareBy({ it.min.y }, { it.min.x }, { it.max.y }, { it.max.x }))

    if (boxes == lastStored) return
    // The first layout can run before the annotation has been added, and the SDK cannot record
    // boxes for a view it does not know yet. Remember the list only once it was taken, so the
    // next layout hands it over again instead of the annotation keeping its full bounds.
    if (manager.setCollisionBoxes(host, boxes)) {
      lastStored = boxes
    }
  }
}