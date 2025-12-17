package com.mapbox.maps.plugin.indoorselector

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.MapboxExperimental
import kotlin.math.roundToInt

/**
 * Custom view for selecting indoor floor levels.
 *
 * Layout structure:
 * - FrameLayout container with rounded corners and elevation
 * - SnappingScrollView in the center containing floor items in a LinearLayout
 * - Top and bottom arrow buttons overlaid on the scroll view for navigation
 *
 * The scroll view snaps to floor items and shows/hides arrows based on scroll position.
 * Touch events are intercepted to prevent map gestures while interacting with the selector.
 */
@MapboxExperimental
@OptIn(com.mapbox.annotation.MapboxExperimental::class)
internal class IndoorSelectorViewImpl @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : IndoorSelectorView, FrameLayout(context, attrs, defStyleAttr) {

  private val itemHeightPx = resources.getDimensionPixelSize(R.dimen.mapbox_indoor_item_height)
  private val cornerRadiusPx = resources.getDimension(R.dimen.mapbox_indoor_corner_radius)

  private val containerLayout = LinearLayout(context).apply {
    orientation = LinearLayout.VERTICAL
  }

  private val scrollView = SnappingScrollView(context).apply {
    isVerticalScrollBarEnabled = false
    overScrollMode = View.OVER_SCROLL_NEVER
    isFillViewport = true
    addView(containerLayout)
  }

  private val topArrow: ImageView
  private val bottomArrow: ImageView

  private var indoorFloors: List<IndoorFloor> = emptyList()
  private var selectedFloorId: String? = null
  private lateinit var presenter: IndoorSelectorPluginImpl

  init {
    layoutParams = LayoutParams(itemHeightPx, LayoutParams.WRAP_CONTENT)
    elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics)
    clipToOutline = true

    // blocking touch events from passing through to map
    isClickable = true
    isFocusable = true

    background = GradientDrawable().apply {
      color = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.mapbox_indoor_bg))
      cornerRadius = cornerRadiusPx
    }

    addView(scrollView, LayoutParams(itemHeightPx, LayoutParams.WRAP_CONTENT))

    topArrow = createArrowButton(isUp = true)
    bottomArrow = createArrowButton(isUp = false)

    addView(topArrow)
    addView(bottomArrow)

    scrollView.viewTreeObserver.addOnScrollChangedListener { updateArrows() }
    topArrow.setOnClickListener { scrollByItem(-1) }
    bottomArrow.setOnClickListener { scrollByItem(1) }
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    // Prevent parent from intercepting scroll/pan gestures
    if (ev.action == MotionEvent.ACTION_DOWN) {
      parent?.requestDisallowInterceptTouchEvent(true)
    } else if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL) {
      parent?.requestDisallowInterceptTouchEvent(false)
    }
    return super.dispatchTouchEvent(ev)
  }

  internal fun injectPresenter(plugin: IndoorSelectorPluginImpl) {
    this.presenter = plugin
  }

  override var isIndoorSelectorVisible: Boolean
    get() = isVisible
    set(value) {
      isVisible = value && indoorFloors.isNotEmpty()
    }

  override fun setIndoorGravity(gravity: Int) {
    updateLayoutParams { it.gravity = gravity }
  }

  override fun setIndoorSelectorMargins(left: Int, top: Int, right: Int, bottom: Int) {
    updateLayoutParams { it.setMargins(left, top, right, bottom) }
  }

  override fun updateFloors(floors: List<IndoorFloor>, selectedFloorId: String?) {
    val floorsChanged = this.indoorFloors != floors
    this.indoorFloors = floors
    this.selectedFloorId = selectedFloorId

    if (floorsChanged) {
      rebuildFloorViews(floors)
    }

    updateSelectionHighlight(selectedFloorId)
    isIndoorSelectorVisible = indoorFloors.isNotEmpty()
  }

  private fun rebuildFloorViews(floors: List<IndoorFloor>) {
    containerLayout.removeAllViews()

    floors.forEach { level ->
      val view = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(itemHeightPx, itemHeightPx)
        gravity = Gravity.CENTER
        includeFontPadding = false
        text = level.name.take(FLOOR_LABEL_MAX_CHARS)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, FLOOR_LABEL_TEXT_SIZE_SP)

        setOnClickListener {
          if (::presenter.isInitialized) {
            presenter.onFloorSelected(level.id)
          }
          updateSelectionHighlight(level.id)
        }
      }
      containerLayout.addView(view)
    }

    updateContainerHeight()
    post { updateArrows() }
  }

  // Updates background and text color for all floor items based on selection
  private fun updateSelectionHighlight(activeId: String?) {
    selectedFloorId = activeId

    val selectedBg = ContextCompat.getColor(context, R.color.mapbox_indoor_selected_bg)
    val selectedTxt = ContextCompat.getColor(context, R.color.mapbox_indoor_selected_text)
    val normalTxt = ContextCompat.getColor(context, R.color.mapbox_indoor_text)

    for (i in 0 until containerLayout.childCount) {
      val view = containerLayout.getChildAt(i) as TextView
      val level = indoorFloors.getOrNull(i) ?: continue
      val isActive = level.id == activeId

      view.setBackgroundColor(if (isActive) selectedBg else Color.TRANSPARENT)
      view.setTextColor(if (isActive) selectedTxt else normalTxt)
    }
  }

  private fun updateContainerHeight() {
    val visibleCount = indoorFloors.size.coerceAtMost(MAX_VISIBLE_ITEMS)
    val targetHeight = visibleCount * itemHeightPx

    updateLayoutParams {
      it.height = targetHeight
      it.width = itemHeightPx
    }
  }

  private fun updateArrows() {
    if (indoorFloors.size <= MAX_VISIBLE_ITEMS) {
      topArrow.isVisible = false
      bottomArrow.isVisible = false
      return
    }

    val scrollY = scrollView.scrollY
    val maxScroll = (indoorFloors.size * itemHeightPx) - (MAX_VISIBLE_ITEMS * itemHeightPx)

    topArrow.isVisible = scrollY > SCROLL_THRESHOLD
    bottomArrow.isVisible = scrollY < maxScroll - SCROLL_THRESHOLD
  }

  private fun scrollByItem(direction: Int) {
    val targetY = scrollView.scrollY + (direction * itemHeightPx)
    scrollView.smoothSnapTo(targetY)
  }

  private fun createArrowButton(isUp: Boolean) = ImageView(context).apply {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    setBackgroundColor(ContextCompat.getColor(context, R.color.mapbox_indoor_bg))

    val imageResId = if (isUp) R.drawable.mapbox_indoor_selector_arrow_up else R.drawable.mapbox_indoor_selector_arrow_down
    setImageResource(imageResId)
    val contentDescriptionResId = if (isUp) R.string.mapbox_indoorselector_scroll_up else R.string.mapbox_indoorselector_scroll_down
    contentDescription = context.getString(contentDescriptionResId)

    setColorFilter(ContextCompat.getColor(context, R.color.mapbox_indoor_text), PorterDuff.Mode.SRC_IN)

    layoutParams = LayoutParams(itemHeightPx, itemHeightPx).apply {
      gravity = if (isUp) Gravity.TOP else Gravity.BOTTOM
    }
  }

  private inline fun updateLayoutParams(block: (LayoutParams) -> Unit) {
    (layoutParams as? LayoutParams)?.let {
      block(it)
      layoutParams = it
    }
  }

  private inner class SnappingScrollView(context: Context) : ScrollView(context) {

    override fun fling(velocityY: Int) {
      if (childCount == 0) return

      // Scale velocity to determine how many items to jump
      val itemJump = (velocityY / FLING_VELOCITY_SCALE).roundToInt()
      // Find which item we're currently closest to
      val currentItem = (scrollY + (itemHeightPx / 2)) / itemHeightPx
      val targetItem = (currentItem + itemJump).coerceIn(0, indoorFloors.size - 1)

      val targetY = targetItem * itemHeightPx
      smoothSnapTo(targetY)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
      val result = super.onTouchEvent(ev)
      if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL) {
        snapToNearest()
      }
      return result
    }

    fun smoothSnapTo(y: Int) {
      val maxScroll = (getChildAt(0).height - height).coerceAtLeast(0)
      smoothScrollTo(0, y.coerceIn(0, maxScroll))
    }

    private fun snapToNearest() {
      val nearestItemIndex = (scrollY.toFloat() / itemHeightPx).roundToInt()
      smoothSnapTo(nearestItemIndex * itemHeightPx)
    }
  }
  companion object {
    private const val MAX_VISIBLE_ITEMS = 4
    // Buffer to prevent arrow flicker at scroll boundaries
    private const val SCROLL_THRESHOLD = 5
    // Convert velocity to item jump distance
    private const val FLING_VELOCITY_SCALE = 1000f
    private const val FLOOR_LABEL_TEXT_SIZE_SP = 16f
    private const val FLOOR_LABEL_MAX_CHARS = 3
  }
}