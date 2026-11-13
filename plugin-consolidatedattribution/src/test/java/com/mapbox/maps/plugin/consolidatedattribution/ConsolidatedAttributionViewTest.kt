package com.mapbox.maps.plugin.consolidatedattribution

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.RippleDrawable
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import java.time.Duration

/**
 * Unit tests for [ConsolidatedAttributionView].
 *
 * Mirrors the iOS ConsolidatedAttributionViewTests, translated to Android/Robolectric.
 * Each test starts from an empty in-memory intro flag, set up in [setUp].
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ConsolidatedAttributionViewTest {

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    ConsolidatedAttributionView.introFlagStore = InMemoryIntroFlagStore()
    ConsolidatedAttributionView.hasShownExpandedInProcess = false
  }

  // ── helpers ───────────────────────────────────────────────────────────────────────────────

  /**
   * Creates a [ConsolidatedAttributionView] attached to a [FrameLayout] so that
   * [FrameLayout.LayoutParams] is set before any tests inspect `layoutParams.width`.
   */
  private fun makeView(
    collapsedStyle: ConsolidatedAttributionCollapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY,
    material: ConsolidatedAttributionMaterial = ConsolidatedAttributionMaterial.SOLID,
    tintColor: Int = Color.TRANSPARENT,
  ): ConsolidatedAttributionView {
    val parent = FrameLayout(context)
    val view = ConsolidatedAttributionView(context).apply {
      applyCollapsedStyle(collapsedStyle)
      applyMaterial(material, tintColor)
    }
    parent.addView(view)
    return view
  }

  private fun makeExpandedView(): ConsolidatedAttributionView {
    val view = makeView()
    view.setAttributionText("© Mapbox • OSM")
    assertTrue("Precondition: view must be expanded", view.isExpanded)
    return view
  }

  /**
   * The pill is a plain clickable view, so a tap is the framework calling [View.performClick];
   * MotionEvents would need a laid-out view to pass the platform's in-bounds check.
   */
  private fun tap(view: ConsolidatedAttributionView) {
    view.performClick()
  }

  // ── initial visibility ────────────────────────────────────────────────────────────────────

  /** Mirrors iOS: the pill is on screen in its collapsed form before any text arrives. */
  @Test
  fun startsVisibleAndCollapsedBeforeAttributionTextArrives() {
    val view = makeView()
    assertEquals(View.VISIBLE, view.visibility)
    assertEquals(1f, view.alpha)
    assertFalse(view.isExpanded)
  }

  @Test
  fun firstAttributionTextIsRecorded() {
    val view = makeView()
    view.setAttributionText("© Mapbox")
    assertTrue("hasReceivedAttributionText should be true after first assignment", view.hasReceivedAttributionText)
  }

  // ── session state — mirrors iOS testFirstViewInSessionStartsExpanded / testSecondViewInSessionStartsCollapsed

  @Test
  fun firstViewInFreshSessionStartsExpanded() {
    val view = makeView()
    view.setAttributionText("© OpenStreetMap")
    assertTrue(
      "First view in a fresh session should be expanded",
      view.isExpanded,
    )
  }

  @Test
  fun secondViewInSameSessionStartsCollapsed() {
    // First view triggers the session flag.
    val first = makeView()
    first.setAttributionText("© OpenStreetMap")
    assertTrue("Precondition: first view must be expanded", first.isExpanded)

    // Second view without resetting session state.
    val second = makeView()
    second.setAttributionText("© OpenStreetMap")
    assertFalse(
      "Second view in the same session should start collapsed",
      second.isExpanded,
    )
  }

  @Test
  fun emptyAttributionTextDoesNotBurnTheIntro() {
    val view = makeView()
    view.setAttributionText("")
    assertFalse("An empty string is nothing to disclose, so it must not expand", view.isExpanded)

    view.setAttributionText("© OpenStreetMap")
    assertTrue("The intro is still owed once real attribution arrives", view.isExpanded)
  }

  @Test
  fun everyLaunchModeReplaysIntroAfterRelaunch() {
    val first = makeView().apply { introExpansion = ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH }
    first.setAttributionText("© OpenStreetMap")
    assertTrue("Precondition: first view must be expanded", first.isExpanded)

    ConsolidatedAttributionView.hasShownExpandedInProcess = false
    val second = makeView().apply { introExpansion = ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH }
    second.setAttributionText("© OpenStreetMap")
    assertTrue("EVERY_LAUNCH ignores the saved flag", second.isExpanded)
  }

  @Test
  fun everyLaunchIntroIsSavedForOnce() {
    val first = makeView().apply { introExpansion = ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH }
    first.setAttributionText("© OpenStreetMap")
    assertTrue("Precondition: first view must be expanded", first.isExpanded)

    ConsolidatedAttributionView.hasShownExpandedInProcess = false
    val second = makeView()
    second.setAttributionText("© OpenStreetMap")
    assertFalse("ONCE reads the flag saved by EVERY_LAUNCH", second.isExpanded)
  }

  // ── collapse triggers — mirrors iOS testMapInteractionCollapsesExpandedControl /
  //                                    testMapInteractionIgnoredWhenCollapsed

  @Test
  fun collapseImmediatelyCollapsesExpandedControl() {
    val view = makeExpandedView()
    view.collapseImmediately()
    assertFalse(
      "collapseImmediately() should collapse an expanded control",
      view.isExpanded,
    )
  }

  @Test
  fun collapseImmediatelyIsNoOpWhenAlreadyCollapsed() {
    val view = makeView()
    // No attribution text yet → never expanded.
    assertFalse("Precondition: view should start collapsed", view.isExpanded)
    view.collapseImmediately()
    assertFalse(
      "collapseImmediately() should be a no-op on an already-collapsed control",
      view.isExpanded,
    )
  }

  // ── tap opens the sheet — mirrors iOS testTappingControlOpensAttribution ─────────────────

  @Test
  fun tapInvokesTapListenerAndDoesNotExpand() {
    val view = makeView()
    view.setAttributionText("© OpenStreetMap")
    view.collapseImmediately()
    var callCount = 0
    view.setOnTapListener { callCount++ }

    tap(view)

    assertEquals("Tap should invoke the tap listener once", 1, callCount)
    assertFalse("Tap should never expand the pill", view.isExpanded)
  }

  @Test
  fun tapWhileExpandedCollapsesTheControl() {
    val view = makeExpandedView()
    var callCount = 0
    view.setOnTapListener { callCount++ }

    tap(view)

    assertEquals("Tap should invoke the tap listener once", 1, callCount)
    assertFalse("Tapping the expanded pill should settle it", view.isExpanded)
  }

  @Test
  fun detachDuringExpandCollapsesImmediately() {
    val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
    val parent = FrameLayout(activity)
    activity.setContentView(parent)
    val view = ConsolidatedAttributionView(activity)
    parent.addView(view)
    view.setAttributionText("© OpenStreetMap")
    assertTrue("Precondition: view must be expanding", view.isExpanded)
    val changes = mutableListOf<Boolean>()
    view.onExpandedChanged = { changes.add(it) }

    parent.removeView(view)

    assertFalse("Detaching should collapse the pill", view.isExpanded)
    assertEquals(listOf(false), changes)

    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(10))
    parent.addView(view)

    assertFalse("The pill should stay collapsed after reattaching", view.isExpanded)
    assertEquals("The cancelled timer must not fire", listOf(false), changes)
  }

  @Test
  fun detachMidCollapseSettlesCollapsed() {
    val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
    val parent = FrameLayout(activity)
    activity.setContentView(parent)
    val view = ConsolidatedAttributionView(activity).apply {
      applyCollapsedStyle(ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    }
    parent.addView(view)
    view.setAttributionText("© OpenStreetMap")
    val looper = Shadows.shadowOf(Looper.getMainLooper())
    looper.idleFor(Duration.ofMillis(500))
    view.collapseOnInteraction()
    assertTrue("Precondition: collapse has not finished", view.getChildAt(2).alpha > 0f)

    parent.removeView(view)
    parent.addView(view)

    assertFalse(view.isExpanded)
    for (index in 1 until view.childCount) {
      assertEquals("Content should not freeze mid-animation", 0f, view.getChildAt(index).alpha)
    }
  }

  @Test
  fun collapseTimerStartsWithTheExpand() {
    val view = makeExpandedView()

    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(5_000))

    assertFalse(view.isExpanded)
  }

  @Test
  fun contentFadesOutWhenCollapsedIconOnly() {
    val view = makeExpandedView()
    view.collapseImmediately()

    for (index in 1 until view.childCount) {
      assertEquals(0f, view.getChildAt(index).alpha)
    }
  }

  @Test
  fun wordmarkStaysVisibleWhenCollapsedIconAndWordmark() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
    view.setAttributionText("© OpenStreetMap")
    view.collapseImmediately()

    assertEquals(1f, view.getChildAt(1).alpha)
    assertEquals(0f, view.getChildAt(2).alpha)
  }

  // ── expanded width bound — mirrors the iOS free-edge limit constraint ────────────────────

  /**
   * A pill bound to a map [parentWidthDp] wide. The width is handed over the way the host does it
   * - [ConsolidatedAttributionView.mapViewWidth] - rather than being read off the parent, so the
   * pill measures the same whether the MapView or a Compose holder is its parent.
   */
  private fun makeViewInParent(
    parentWidthDp: Int,
    horizontalMarginPx: Int = 0,
    layOutParent: Boolean = true,
  ): ConsolidatedAttributionView {
    val density = context.resources.displayMetrics.density
    val parentWidthPx = (parentWidthDp * density).toInt()
    val parent = FrameLayout(context)
    val view = ConsolidatedAttributionView(context)
    parent.addView(
      view,
      FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
      ),
    )
    view.setPillMargins(horizontalMarginPx, 0, horizontalMarginPx, 0)
    if (layOutParent) {
      view.mapViewWidth = parentWidthPx
    }
    return view
  }

  @Test
  fun maxPillWidthIsTheMapWidth() {
    val density = context.resources.displayMetrics.density
    val view = makeViewInParent(parentWidthDp = 200)

    assertEquals(
      "Max pill width should be the map's own width when the pill has no margins",
      (200 * density).toInt(),
      view.maxPillWidthPx(),
    )
  }

  @Test
  fun maxPillWidthSubtractsHorizontalMargins() {
    val density = context.resources.displayMetrics.density
    val marginPx = (8 * density).toInt()
    val view = makeViewInParent(parentWidthDp = 200, horizontalMarginPx = marginPx)

    assertEquals(
      "Max pill width should be the map width less the pill's margins on both sides",
      (200 * density).toInt() - 2 * marginPx,
      view.maxPillWidthPx(),
    )
  }

  @Test
  fun maxPillWidthGrowsWithAWiderMap() {
    assertTrue(
      "A wider map should raise the bound instead of a fixed cap applying to both",
      makeViewInParent(parentWidthDp = 800).maxPillWidthPx() >
        makeViewInParent(parentWidthDp = 200).maxPillWidthPx(),
    )
  }

  /** Before the map has been laid out its width is unknown, so the fallback cap applies. */
  @Test
  fun maxPillWidthFallsBackBeforeTheMapIsLaidOut() {
    val density = context.resources.displayMetrics.density
    val view = makeViewInParent(parentWidthDp = 200, layOutParent = false)

    assertEquals(
      "An unmeasured map should yield the fallback cap",
      (FALLBACK_MAX_WIDTH_DP * density).toInt(),
      view.maxPillWidthPx(),
    )
  }

  // ── collapsed width — mirrors iOS testCollapseWidthIconOnly / testCollapseWidthIconAndWordmark ──

  @Test
  fun collapsedWidthIconOnlyEqualsCircleDiameter() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    view.setAttributionText("© Mapbox • OSM") // expand first, then collapse
    view.collapseImmediately()

    val density = context.resources.displayMetrics.density
    val expectedWidth = (PILL_HEIGHT_DP * density).toInt()
    assertEquals(
      "ICON_ONLY collapsed width should equal pill height (perfect circle)",
      expectedWidth,
      view.layoutParams.width - 2 * view.touchInsetX,
    )
  }

  // ── touch target ──────────────────────────────────────────────────────────────────────────

  private fun measure(view: ConsolidatedAttributionView) {
    val unspecified = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    view.measure(
      View.MeasureSpec.makeMeasureSpec(view.layoutParams.width, View.MeasureSpec.EXACTLY),
      unspecified,
    )
  }

  @Test
  fun touchTargetIsAtLeast48dpInEveryCollapsedStyle() {
    val minPx = (TOUCH_TARGET_DP * context.resources.displayMetrics.density).toInt()
    ConsolidatedAttributionCollapsedStyle.values().forEach { style ->
      val view = makeView(collapsedStyle = style)
      measure(view)
      assertTrue("$style height ${view.measuredHeight} < $minPx", view.measuredHeight >= minPx)
      assertTrue("$style width ${view.measuredWidth} < $minPx", view.measuredWidth >= minPx)
    }
  }

  @Test
  fun iconAndWordmarkHasNoHorizontalTouchInset() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
    assertEquals(0, view.touchInsetX)
    assertTrue(view.touchInsetY > 0)
  }

  /** The visible pill keeps the requested margins; the view's own margins absorb the inset. */
  @Test
  fun pillMarginsAreReducedByTheTouchInset() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    view.setPillMargins(8, 8, 8, 8)
    val lp = view.layoutParams as FrameLayout.LayoutParams
    assertEquals(8 - view.touchInsetX, lp.leftMargin)
    assertEquals(8 - view.touchInsetY, lp.topMargin)
    assertEquals(8 - view.touchInsetX, lp.rightMargin)
    assertEquals(8 - view.touchInsetY, lp.bottomMargin)
    assertTrue("Default margins should go negative", lp.bottomMargin < 0)

    view.applyCollapsedStyle(ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
    assertEquals("A style change re-applies the margins", 8, lp.leftMargin)
  }

  /** The pill background and ripple are drawn inside the touch inset. */
  @Test
  fun pillIsDrawnInsideTheTouchInset() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    measure(view)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    val ripple = view.background as RippleDrawable
    ripple.bounds = Rect(0, 0, view.width, view.height)
    val pill = ripple.getDrawable(0).bounds
    assertEquals(view.touchInsetX, pill.left)
    assertEquals(view.touchInsetY, pill.top)
    assertEquals(view.width - view.touchInsetX, pill.right)
    assertEquals(view.height - view.touchInsetY, pill.bottom)
  }

  @Test
  fun collapsedWidthWordmarkIsWiderThanIconOnly() {
    val viewIconOnly = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    viewIconOnly.setAttributionText("© Mapbox")
    viewIconOnly.collapseImmediately()
    val iconOnlyWidth = viewIconOnly.layoutParams.width

    val viewWordmark = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
    // Second view in same session — starts collapsed, but width set to new collapsed style.
    viewWordmark.setAttributionText("© Mapbox")
    viewWordmark.collapseImmediately()
    val wordmarkWidth = viewWordmark.layoutParams.width

    assertTrue(
      "ICON_AND_WORDMARK collapsed width ($wordmarkWidth) should exceed ICON_ONLY width ($iconOnlyWidth)",
      wordmarkWidth > iconOnlyWidth,
    )
  }

  // ── applyCollapsedStyle in-place ──────────────────────────────────────────────────────────

  @Test
  fun applyCollapsedStyleUpdatesWidthImmediatelyWhenCollapsed() {
    val view = makeView(collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_ONLY)
    view.setAttributionText("© Mapbox")
    view.collapseImmediately()

    val widthBefore = view.layoutParams.width
    view.applyCollapsedStyle(ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
    val widthAfter = view.layoutParams.width

    assertTrue(
      "applyCollapsedStyle(ICON_AND_WORDMARK) should increase the collapsed width immediately",
      widthAfter > widthBefore,
    )
  }

  // ── material ──────────────────────────────────────────────────────────────────────────────

  @Test
  fun applySolidMaterialSetsBackground() {
    val view = makeView()
    view.applyMaterial(ConsolidatedAttributionMaterial.SOLID, Color.TRANSPARENT)
    // Background drawable is a GradientDrawable — check it is non-null (visual correctness
    // is validated by rendering; here we just guard against NPE/misconfiguration).
    assertTrue(
      "Solid material should set a non-null GradientDrawable background",
      view.background != null,
    )
    assertEquals(ConsolidatedAttributionMaterial.SOLID, view.material)
  }

  @Test
  fun applyTranslucentMaterialSetsBackground() {
    val view = makeView()
    view.applyMaterial(ConsolidatedAttributionMaterial.TRANSLUCENT, Color.TRANSPARENT)
    assertTrue(
      "Translucent material should set a non-null GradientDrawable background",
      view.background != null,
    )
    assertEquals(ConsolidatedAttributionMaterial.TRANSLUCENT, view.material)
  }

  @Test
  fun tintColorIsRetained() {
    val view = makeView(tintColor = Color.RED)
    assertEquals(Color.RED, view.tintColor)
  }

  // ── attribution text ─────────────────────────────────────────────────────────────────────

  @Test
  fun attributionTextIsDisplayedInLabel() {
    val view = makeView()
    val text = "© Mapbox • © OpenStreetMap"
    view.setAttributionText(text)

    // attributionLabel is the third child (index 2) of the LinearLayout (this view).
    val label = view.getChildAt(2) as? TextView
    assertEquals(
      "Attribution label should display the text passed to setAttributionText",
      text,
      label?.text?.toString(),
    )
  }

  @Test
  fun subsequentSetAttributionTextUpdatesLabelWithoutReExpanding() {
    val view = makeView()
    view.setAttributionText("© Mapbox")
    view.collapseImmediately()
    assertFalse("Precondition: should be collapsed", view.isExpanded)

    view.setAttributionText("© Mapbox • © OpenStreetMap")

    assertFalse("Second setAttributionText call should not re-expand the pill", view.isExpanded)
    val label = view.getChildAt(2) as? TextView
    assertEquals(
      "Label should show the updated text",
      "© Mapbox • © OpenStreetMap",
      label?.text?.toString(),
    )
  }

  // ── reduce motion — mirrors iOS testReduceMotionSkipsAnimation ───────────────────────────

  @Test
  fun reduceMotionSkipsAnimationAndJumpsToFinalState() {
    // Set animator duration scale to 0 (reduce-motion equivalent).
    Settings.Global.putFloat(
      context.contentResolver,
      Settings.Global.ANIMATOR_DURATION_SCALE,
      0f,
    )

    val view = makeView()
    view.setAttributionText("© Mapbox")

    // With reduce motion, the view should already be in the correct expanded state without
    // waiting for an animation to finish.
    assertTrue(
      "With ANIMATOR_DURATION_SCALE=0, view should be in expanded state immediately",
      view.isExpanded,
    )
  }

  // ── all materials construct without crash — mirrors iOS testAllPositionsCreateWithoutCrash ──

  @Test
  fun allMaterialsApplyWithoutCrash() {
    val view = makeView()
    for (material in ConsolidatedAttributionMaterial.values()) {
      view.applyMaterial(material, Color.TRANSPARENT)
    }
  }

  @Test
  fun allCollapsedStylesApplyWithoutCrash() {
    val view = makeView()
    for (style in ConsolidatedAttributionCollapsedStyle.values()) {
      view.applyCollapsedStyle(style)
    }
  }

  @Test
  fun pillKeepsLeftToRightOrderInRtlLayout() {
    context.applicationInfo.flags = context.applicationInfo.flags or ApplicationInfo.FLAG_SUPPORTS_RTL
    val parent = FrameLayout(context).apply { layoutDirection = View.LAYOUT_DIRECTION_RTL }
    val view = ConsolidatedAttributionView(context)
    parent.addView(view)
    parent.measure(
      View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY),
    )
    assertEquals(View.LAYOUT_DIRECTION_RTL, parent.layoutDirection)
    assertEquals(View.LAYOUT_DIRECTION_LTR, view.layoutDirection)
  }

  // ── accessibility — mirrors the iOS single-button pill ──────────────────────────────────────

  @Test
  fun pillIsOneAccessibleButtonWithLabelAndHint() {
    val view = makeView()
    val info = AccessibilityNodeInfoCompat.obtain()
    ViewCompat.onInitializeAccessibilityNodeInfo(view, info)

    assertTrue(view.isClickable)
    assertEquals(
      context.getString(R.string.mapbox_consolidated_attr_icon_a11y_label),
      view.contentDescription,
    )
    assertEquals(Button::class.java.name, info.className)
    assertEquals(
      context.getString(R.string.mapbox_consolidated_attr_icon_a11y_hint),
      info.hintText,
    )
  }

  @Test
  fun pillContentDescriptionIncludesDataCredits() {
    val view = makeView()
    view.setAttributionText("© OpenStreetMap")
    assertEquals(
      context.getString(R.string.mapbox_consolidated_attr_icon_a11y_label) + ", © OpenStreetMap",
      view.contentDescription,
    )
  }

  @Test
  fun pillChildrenAreNotAccessibilityElements() {
    val view = makeView()
    for (index in 0 until view.childCount) {
      assertEquals(
        View.IMPORTANT_FOR_ACCESSIBILITY_NO,
        view.getChildAt(index).importantForAccessibility,
      )
    }
  }

  @Test
  fun accessibilityClickActionOpensTheAttributionSheet() {
    val view = makeView()
    var taps = 0
    view.setOnTapListener { taps++ }

    val info = AccessibilityNodeInfoCompat.obtain()
    ViewCompat.onInitializeAccessibilityNodeInfo(view, info)
    // What TalkBack's double-tap dispatches; the node must advertise it for that to reach us.
    assertTrue(
      ViewCompat.performAccessibilityAction(
        view,
        AccessibilityNodeInfoCompat.ACTION_CLICK,
        null,
      )
    )

    assertEquals(1, taps)
  }

  private companion object {
    // Must match the private constants in ConsolidatedAttributionView.
    private const val PILL_HEIGHT_DP = 28f
    private const val FALLBACK_MAX_WIDTH_DP = 220f
    private const val TOUCH_TARGET_DP = 48f
  }
}