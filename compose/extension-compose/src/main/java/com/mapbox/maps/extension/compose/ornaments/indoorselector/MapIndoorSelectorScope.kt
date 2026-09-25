package com.mapbox.maps.extension.compose.ornaments.indoorselector

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.IndoorManager
import com.mapbox.maps.IndoorState
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.ornaments.indoorselector.internal.IndoorSelectorComposePlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.indoorselector.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val MAX_VISIBLE_ITEMS = 4
private const val FLOOR_LABEL_MAX_CHARS = 3
private const val SCROLL_THRESHOLD_PX = 5
private val ITEM_HEIGHT = 44.dp
private val CORNER_RADIUS = 8.dp
private val SURFACE_ELEVATION = 4.dp
private val FLOOR_LABEL_TEXT_SIZE_SP = 16.sp

/**
 * Manages the indoor floor state: subscribes to [IndoorManager] events, exposes the current
 * floor list and selected floor id, and propagates floor selections back to [IndoorManager].
 */
@OptIn(com.mapbox.annotation.MapboxExperimental::class)
@MapboxExperimental
public class IndoorSelectorState {

  private var _floors: List<IndoorFloor> by mutableStateOf(emptyList())
  private var _selectedFloorId: String? by mutableStateOf(null)
  private var plugin: IndoorSelectorComposePlugin? = null

  /** The list of floors in the currently visible indoor building, or empty if none is in view. */
  public val floors: List<IndoorFloor>
    get() = _floors

  /**
   * The id of the currently selected floor, or `null` when the building level (no
   * specific floor) is active. Setting this value propagates the selection to [IndoorManager].
   */
  public var selectedFloorId: String?
    get() = _selectedFloorId
    set(value) {
      val normalized = value?.takeUnless { it.isEmpty() }
      if (_selectedFloorId != normalized) {
        _selectedFloorId = normalized
        plugin?.indoorManager?.selectFloor(normalized)
      }
    }

  internal fun handleIndoorUpdate(indoorState: IndoorState) {
    _floors = indoorState.floors
    val selectedFloorIdUpdate = indoorState.selectedFloorId.takeUnless { it.isEmpty() }
    if (_selectedFloorId != selectedFloorIdUpdate) {
      _selectedFloorId = selectedFloorIdUpdate
    }
  }

  @Composable
  internal fun BindToMap(plugin: IndoorSelectorComposePlugin?) {
    DisposableEffect(plugin) {
      if (plugin == null) return@DisposableEffect onDispose {}
      this@IndoorSelectorState.plugin = plugin
      val indoorManager = plugin.indoorManager
      val savedSelectedFloorId = _selectedFloorId
      if (savedSelectedFloorId != null) {
        indoorManager.selectFloor(savedSelectedFloorId)
      }
      val callback = object : IndoorManager.OnIndoorUpdatedCallback {
        override fun onIndoorUpdated(onIndoorUpdated: IndoorState) {
          handleIndoorUpdate(onIndoorUpdated)
        }
      }
      val cancelable = indoorManager.subscribeOnIndoorUpdated(callback)
      onDispose {
        cancelable.cancel()
        this@IndoorSelectorState.plugin = null
        _floors = emptyList()
        _selectedFloorId = null
      }
    }
  }
}

/**
 * Creates and remembers an [IndoorSelectorState] for use with [MapIndoorSelectorScope.IndoorSelector].
 */
@MapboxExperimental
@Composable
public fun rememberIndoorSelectorState(): IndoorSelectorState = remember { IndoorSelectorState() }

/**
 * Scope for [IndoorSelector], [IndoorSelectorControl], and other indoor-related composables
 * within [MapboxMap][com.mapbox.maps.extension.compose.MapboxMap].
 *
 * Use [IndoorSelector] to display the default indoor floor-selection ornament.
 * Use [IndoorSelectorControl] when you want to manage indoor state but render your own UI.
 */
@MapboxMapScopeMarker
@Immutable
@OptIn(com.mapbox.annotation.MapboxExperimental::class)
@MapboxExperimental
public class MapIndoorSelectorScope internal constructor(
  internal val mapView: MapView,
  private val boxScope: BoxScope,
) {

  /**
   * Headless composable that attaches the indoor plugin to [state] without rendering any UI.
   *
   * Use this when you want to implement your own floor-selector UI while still driving
   * [IndoorSelectorState.floors] and [IndoorSelectorState.selectedFloorId] from the live map data.
   *
   * Example:
   * ```
   * val indoorState = rememberIndoorSelectorState()
   * MapboxMap(
   *   indoorSelector = { IndoorSelectorControl(state = indoorState) }
   * ) { ... }
   * // Use indoorState.floors / indoorState.selectedFloorId in your own UI
   * ```
   *
   * @param state [IndoorSelectorState] to wire to the map; use [rememberIndoorSelectorState] to create one.
   */
  @MapboxExperimental
  @Composable
  public fun IndoorSelectorControl(
    state: IndoorSelectorState = rememberIndoorSelectorState(),
  ) {
    var plugin: IndoorSelectorComposePlugin? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
      mapView.createPlugin(
        Plugin.Custom(
          id = PLUGIN_ID,
          instance = IndoorSelectorComposePlugin()
        )
      )
      plugin = mapView.getPlugin(PLUGIN_ID)
      onDispose {
        mapView.removePlugin(PLUGIN_ID)
        plugin = null
      }
    }

    state.BindToMap(plugin)
  }

  /**
   * Displays the indoor floor-selector ornament, anchored to the map.
   *
   * Hides itself when no indoor building is in view.
   *
   * To render a custom floor-selector UI instead, use [IndoorSelectorControl] to attach the
   * plugin to an [IndoorSelectorState] and read [IndoorSelectorState.floors] in your own composable.
   *
   * @param modifier Modifier to apply to the ornament container.
   * @param state [IndoorSelectorState] for hoisting state; use [rememberIndoorSelectorState] to create one.
   * @param contentPadding Padding between the ornament and the map edge. Note that the default value might overlap with other ornaments.
   * @param alignment Alignment of the ornament within the map bounds.
   * @param maxVisibleFloors Maximum number of floors visible at once before scrolling.
   * @param onFloorClicked Callback invoked when the user taps a floor item in the selector.
   *   Not called for external or programmatic floor changes.
   */
  @MapboxExperimental
  @Composable
  public fun IndoorSelector(
    modifier: Modifier = Modifier,
    state: IndoorSelectorState = rememberIndoorSelectorState(),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    alignment: Alignment = Alignment.TopEnd,
    maxVisibleFloors: Int = MAX_VISIBLE_ITEMS,
    control: @Composable MapIndoorSelectorScope.() -> Unit = { IndoorSelectorControl(state) },
    onFloorClicked: ((IndoorFloor) -> Unit)? = null,
  ) {
    control()
    val floors = state.floors
    if (floors.isEmpty()) return

    val selectedFloorId = state.selectedFloorId
    val isBuildingSelected = selectedFloorId.isNullOrEmpty()

    val bgColor = colorResource(R.color.mapbox_indoor_bg)
    val textColor = colorResource(R.color.mapbox_indoor_text)
    val selectedBgColor = colorResource(R.color.mapbox_indoor_selected_bg)
    val selectedTextColor = colorResource(R.color.mapbox_indoor_selected_text)
    val itemHeightPx = with(LocalDensity.current) { ITEM_HEIGHT.roundToPx() }

    val listState = remember(floors) { LazyListState() }
    val coroutineScope = rememberCoroutineScope()
    val snapFlingBehavior = remember(listState, floors.size) {
      IndoorSnapFlingBehavior(listState, itemHeightPx, floors.size)
    }

    val canScrollUp by remember(listState) {
      derivedStateOf {
        listState.firstVisibleItemIndex > 0 ||
          listState.firstVisibleItemScrollOffset > SCROLL_THRESHOLD_PX
      }
    }
    val canScrollDown by remember(listState) {
      derivedStateOf {
        val info = listState.layoutInfo
        val lastItem = info.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
        val notLast = lastItem.index < info.totalItemsCount - 1
        val lastItemBottom = lastItem.offset + lastItem.size
        val lastScrolledByThreshold = lastItemBottom - info.viewportEndOffset > SCROLL_THRESHOLD_PX
        notLast || lastScrolledByThreshold
      }
    }

    LaunchedEffect(listState.isScrollInProgress) {
      if (listState.isScrollInProgress) return@LaunchedEffect
      // Snap to the closest item when scroll is stopped
      val offset = listState.firstVisibleItemScrollOffset
      if (offset > 0) {
        val snapTo = if (offset < itemHeightPx.toFloat() / 2f) {
          listState.firstVisibleItemIndex
        } else {
          (listState.firstVisibleItemIndex + 1).coerceAtMost(floors.size - 1)
        }
        try {
          listState.animateScrollToItem(snapTo)
        } catch (_: CancellationException) {
          // Animation interrupted by a new user gesture - keep collecting.
          // If our own coroutine was cancelled, ensureActive() re-throws.
          currentCoroutineContext().ensureActive()
        }
      }
    }

    Surface(
      modifier = with(boxScope) {
        Modifier
          .padding(contentPadding)
          .then(modifier)
          .align(alignment)
      },
      shape = RoundedCornerShape(CORNER_RADIUS),
      elevation = SURFACE_ELEVATION,
      color = bgColor,
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(ITEM_HEIGHT),
      ) {
        // Building button — clears active floor selection
        Image(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(ITEM_HEIGHT)
            .background(if (isBuildingSelected) selectedBgColor else bgColor)
            .clickable {
              if (!isBuildingSelected) {
                state.selectedFloorId = null
              }
            },
          painter = painterResource(R.drawable.mapbox_indoor_selector_building),
          contentDescription = stringResource(R.string.mapbox_indoorselector_building),
          contentScale = ContentScale.Inside,
          alignment = Alignment.Center,
          colorFilter = ColorFilter.tint(if (isBuildingSelected) selectedTextColor else textColor, BlendMode.SrcIn),
        )

        Box(
          modifier = Modifier.width(ITEM_HEIGHT),
          contentAlignment = Alignment.Center,
        ) {
          // Floor list — up to maxVisibleFloors shown; snaps to items on fling
          LazyColumn(
            state = listState,
            flingBehavior = snapFlingBehavior,
            modifier = Modifier
              .heightIn(max = ITEM_HEIGHT * minOf(floors.size, maxVisibleFloors)),
          ) {
            items(floors, key = { it.id }) { floor ->
              val isSelected = !isBuildingSelected && floor.id == selectedFloorId
              Box(
                modifier = Modifier
                  .size(ITEM_HEIGHT)
                  .align(Alignment.Center)
                  .background(if (isSelected) selectedBgColor else bgColor)
                  .clickable {
                    state.selectedFloorId = floor.id
                    onFloorClicked?.invoke(floor)
                  },
                contentAlignment = Alignment.Center,
              ) {
                Text(
                  text = floor.name.take(FLOOR_LABEL_MAX_CHARS),
                  color = if (isSelected) selectedTextColor else textColor,
                  fontSize = FLOOR_LABEL_TEXT_SIZE_SP,
                  textAlign = TextAlign.Center,
                )
              }
            }
          }

          // Up arrow — visible only when floors above the current scroll position are hidden
          if (canScrollUp) {
            Arrow(Modifier.align(Alignment.TopCenter), isUp = true, textColor, bgColor) {
              coroutineScope.launch {
                val target = (listState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                listState.animateScrollToItem(target)
              }
            }
          }

          // Down arrow — visible only when floors below the current scroll position are hidden
          if (canScrollDown) {
            Arrow(Modifier.align(Alignment.BottomCenter), isUp = false, textColor, bgColor) {
              coroutineScope.launch {
                val target = (listState.firstVisibleItemIndex + 1).coerceAtMost(floors.size - 1)
                listState.animateScrollToItem(target)
              }
            }
          }
        }
      }
    }
  }

  @Composable
  private fun Arrow(
    modifier: Modifier,
    isUp: Boolean,
    textColor: Color,
    bgColor: Color,
    onClick: () -> Unit,
  ) {
    Image(
      painter = painterResource(if (isUp) R.drawable.mapbox_indoor_selector_arrow_up else R.drawable.mapbox_indoor_selector_arrow_down),
      contentDescription = stringResource(if (isUp) R.string.mapbox_indoorselector_scroll_up else R.string.mapbox_indoorselector_scroll_down),
      contentScale = ContentScale.Inside,
      alignment = Alignment.Center,
      colorFilter = ColorFilter.tint(textColor, BlendMode.SrcIn),
      modifier = Modifier
        .background(bgColor)
        .size(ITEM_HEIGHT)
        .clickable(onClick = onClick)
        .then(modifier)
    )
  }

  private companion object {
    private const val PLUGIN_ID = "MAPBOX_INDOOR_SELECTOR_COMPOSE_PLUGIN"
  }
}

private class IndoorSnapFlingBehavior(
  private val lazyListState: LazyListState,
  private val itemHeightPx: Int,
  private val totalItems: Int,
) : FlingBehavior {

  override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
    if (totalItems == 0 || itemHeightPx == 0) return 0f

    // Scale velocity to determine how many items to jump
    val itemJump = (initialVelocity / FLING_VELOCITY_SCALE).roundToInt()
    // Find which item we're currently closest to
    val currentItem = lazyListState.firstVisibleItemIndex +
      if (lazyListState.firstVisibleItemScrollOffset * 2 >= itemHeightPx) 1 else 0

    val targetItem = (currentItem + itemJump).coerceIn(0, totalItems - 1)

    val currentOffset = lazyListState.firstVisibleItemIndex.toFloat() * itemHeightPx +
      lazyListState.firstVisibleItemScrollOffset
    val layoutInfo = lazyListState.layoutInfo
    val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
    val maxScrollOffset = (totalItems * itemHeightPx - viewportSize).coerceAtLeast(0).toFloat()
    val targetOffset = (targetItem.toFloat() * itemHeightPx).coerceIn(0f, maxScrollOffset)

    var prev = currentOffset
    AnimationState(initialValue = currentOffset, initialVelocity = initialVelocity)
      .animateTo(targetValue = targetOffset, animationSpec = tween(durationMillis = 200)) {
        scrollBy(value - prev)
        prev = value
      }
    return 0f
  }

  private companion object {
    const val FLING_VELOCITY_SCALE = 1000f
  }
}