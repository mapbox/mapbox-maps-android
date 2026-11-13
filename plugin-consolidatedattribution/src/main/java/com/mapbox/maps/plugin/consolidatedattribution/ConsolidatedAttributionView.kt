package com.mapbox.maps.plugin.consolidatedattribution

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.core.graphics.ColorUtils
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import kotlin.math.ceil

/**
 * Pill-shaped consolidated attribution control combining the Mapbox logo icon, wordmark,
 * attribution text, and a disclosure chevron into a single dismissible surface.
 *
 * ## State machine
 * ```
 * COLLAPSED ──(first attribution text)──► EXPANDED ──(5 s timer / map interaction / tap)──► COLLAPSED
 * ```
 * A tap on the pill opens the attribution sheet; it never expands the pill back.
 * How often the intro expansion plays is set by [introExpansion].
 *
 * Light and dark colors follow the night mode; [ConsolidatedAttributionMaterial] only sets the
 * surface opacity.
 *
 * @suppress
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class ConsolidatedAttributionView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

  // ── sub-views ────────────────────────────────────────────────────────────────────────────
  private val iconView: LogoIconView
  private val wordmarkView: LogoWordmarkView
  private val attributionLabel: TextView
  private val chevronView: ChevronView

  // ── pill background ───────────────────────────────────────────────────────────────────────
  private val pillBackground = GradientDrawable()

  /** Mask constraining the press ripple to the pill's rounded silhouette. */
  private val rippleMask = GradientDrawable().apply { setColor(Color.WHITE) }
  private val rippleDrawable = RippleDrawable(
    ColorStateList.valueOf(Color.TRANSPARENT),
    pillBackground,
    rippleMask,
  )

  /** Space between the view's edge and the pill, which grows the touch target to [TOUCH_TARGET_DP]. */
  var touchInsetX: Int = 0
    private set
  var touchInsetY: Int = 0
    private set

  /** Distance from the map's edges to the visible pill, set by [setPillMargins]. */
  private val pillMargins = Rect()

  // ── state ─────────────────────────────────────────────────────────────────────────────────
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var isExpanded = false
    private set(value) {
      if (field == value) return
      field = value
      onExpandedChanged?.invoke(value)
    }

  /** Called when the pill expands or collapses. */
  internal var onExpandedChanged: ((Boolean) -> Unit)? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var hasReceivedAttributionText = false
    private set

  /** Width of the full attribution string, measured when the text is set. */
  private var textWidthPx = 0

  /** Expanded width for the current text and map width; refreshed by [updateExpandedMetrics]. */
  private var expandedWidthPx = 0

  /**
   * 0 is collapsed, 1 is expanded. The width is derived from it on every frame, so a map width
   * that changes mid-animation still applies.
   */
  private var expandFraction = 0f

  /**
   * Width available to the pill, set by the host. In Compose the parent is sized to the pill, so
   * the parent's width can't be used. Until it is set, [FALLBACK_MAX_WIDTH_DP] is used.
   */
  var mapViewWidth: Int = 0
    set(value) {
      if (field == value) return
      field = value
      updateExpandedMetrics()
      if (expandFraction > 0f) applyExpandFraction()
    }
  private var onTapListener: OnClickListener? = null

  /** Current collapsed style; update via [applyCollapsedStyle]. */
  var collapsedStyle: ConsolidatedAttributionCollapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK
    private set

  /** Current material; update via [applyMaterial]. */
  var material: ConsolidatedAttributionMaterial = ConsolidatedAttributionMaterial.SOLID
    private set

  /** Current tint color; [Color.TRANSPARENT] applies no tint. */
  @ColorInt
  var tintColor: Int = Color.TRANSPARENT
    private set

  /** How often the intro expansion plays. */
  var introExpansion: ConsolidatedAttributionIntroExpansion =
    ConsolidatedAttributionIntroExpansion.ONCE

  // ── animation ────────────────────────────────────────────────────────────────────────────
  private val handler = Handler(Looper.getMainLooper())
  private var collapseRunnable: Runnable? = null
  private var currentAnimator: Animator? = null

  // ── cached px dimensions ──────────────────────────────────────────────────────────────────
  private val density = resources.displayMetrics.density
  private val pillHeightPx = (PILL_HEIGHT_DP * density).toInt()
  private val iconSizePx = (ICON_SIZE_DP * density).toInt()
  private val chevronWidthPx = (ChevronView.INTRINSIC_WIDTH_DP * density).toInt()
  private val chevronHeightPx = (ChevronView.INTRINSIC_HEIGHT_DP * density).toInt()
  private val horizontalPaddingPx = (HORIZONTAL_PADDING_DP * density).toInt()
  private val innerSpacingPx = (INNER_SPACING_DP * density).toInt()
  private val trailingInsetPx = (TRAILING_INSET_DP * density).toInt()
  private val wordmarkWidthPx = (LogoWordmarkView.INTRINSIC_WIDTH_DP * density).toInt()
  private val wordmarkMarginStartPx = (WORDMARK_MARGIN_START_DP * density).toInt()
  private val fallbackMaxWidthPx = (FALLBACK_MAX_WIDTH_DP * density).toInt()
  private val touchTargetPx = (TOUCH_TARGET_DP * density).toInt()

  /** Everything the expanded pill occupies apart from the label itself. */
  private val expandedChromeWidthPx = 2 * horizontalPaddingPx +
    iconSizePx + wordmarkMarginStartPx +
    wordmarkWidthPx + innerSpacingPx +
    innerSpacingPx + chevronWidthPx + trailingInsetPx

  init {
    orientation = HORIZONTAL
    // The logo reads left to right in every locale.
    layoutDirection = LAYOUT_DIRECTION_LTR
    gravity = Gravity.CENTER_VERTICAL
    clipChildren = true
    clipToPadding = true
    pillBackground.cornerRadius = PILL_HEIGHT_DP / 2f * density
    rippleMask.cornerRadius = pillBackground.cornerRadius
    background = rippleDrawable
    isClickable = true

    // One button for accessibility; the children are hidden from it.
    contentDescription = context.getString(R.string.mapbox_consolidated_attr_icon_a11y_label)
    ViewCompat.setAccessibilityDelegate(
      this,
      object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
          host: View,
          info: AccessibilityNodeInfoCompat,
        ) {
          super.onInitializeAccessibilityNodeInfo(host, info)
          info.className = Button::class.java.name
          info.hintText = context.getString(R.string.mapbox_consolidated_attr_icon_a11y_hint)
        }
      },
    )

    iconView = LogoIconView(context).apply {
      showBackground = false
      importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
      layoutParams = LayoutParams(iconSizePx, iconSizePx).apply {
        gravity = Gravity.CENTER_VERTICAL
      }
    }

    wordmarkView = LogoWordmarkView(context).apply {
      importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
      layoutParams = LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
      ).apply {
        gravity = Gravity.CENTER_VERTICAL
        marginStart = (WORDMARK_MARGIN_START_DP * density).toInt()
      }
    }

    attributionLabel = TextView(context).apply {
      alpha = 0f
      textSize = ATTRIBUTION_TEXT_SIZE_SP
      maxLines = 1
      ellipsize = TextUtils.TruncateAt.END
      importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
      layoutParams = LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
      ).apply {
        gravity = Gravity.CENTER_VERTICAL
        marginStart = innerSpacingPx
      }
    }

    chevronView = ChevronView(context).apply {
      importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
      alpha = 0f
      layoutParams = LayoutParams(chevronWidthPx, chevronHeightPx).apply {
        gravity = Gravity.CENTER_VERTICAL
        marginStart = innerSpacingPx
      }
    }

    addView(iconView)
    addView(wordmarkView)
    addView(attributionLabel)
    addView(chevronView)

    applyMaterial(ConsolidatedAttributionMaterial.SOLID, Color.TRANSPARENT)
    updateTouchInsets()
    updatePaddingForState()
  }

  // ── public API ────────────────────────────────────────────────────────────────────────────

  /**
   * Set the label text. The first non-empty text plays the intro expansion, if it is still owed.
   */
  fun setAttributionText(text: String) {
    attributionLabel.text = text
    val a11yLabel = context.getString(R.string.mapbox_consolidated_attr_icon_a11y_label)
    contentDescription = if (text.isEmpty()) a11yLabel else "$a11yLabel, $text"
    // Rounded up like TextView does, or a label that fits gets ellipsized.
    textWidthPx = ceil(attributionLabel.paint.measureText(text)).toInt()
    updateExpandedMetrics()
    applyExpandFraction()
    // Empty text doesn't use up the intro.
    if (text.isEmpty() || hasReceivedAttributionText) {
      return
    }
    hasReceivedAttributionText = true
    if (!sessionHasExpanded()) {
      markSessionExpanded()
      expandAnimated()
    }
  }

  /** Apply a new material and tint. */
  fun applyMaterial(material: ConsolidatedAttributionMaterial, @ColorInt tintColor: Int) {
    this.material = material
    this.tintColor = tintColor
    val colors = materialColors(material, tintColor)
    pillBackground.setColor(colors.pillColor)
    rippleDrawable.setColor(
      ColorStateList.valueOf(
        ColorUtils.setAlphaComponent(colors.contentColor, RIPPLE_ALPHA)
      )
    )
    iconView.applyColors(contentColor = colors.contentColor, detailColor = colors.detailColor)
    wordmarkView.applyColors(contentColor = colors.contentColor, detailColor = colors.detailColor)
    attributionLabel.setTextColor(colors.textColor)
    chevronView.applyColors(contentColor = colors.contentColor, detailColor = colors.detailColor)
  }

  /** Re-reads the night mode for hosts that handle `uiMode` changes themselves. */
  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    applyMaterial(material, tintColor)
  }

  /** Apply a new collapsed style. A collapsed pill resizes right away. */
  fun applyCollapsedStyle(style: ConsolidatedAttributionCollapsedStyle) {
    collapsedStyle = style
    updateTouchInsets()
    if (!isExpanded) {
      applyExpandFraction()
    }
  }

  /** Collapse the pill with an animation, when the user interacts with the map. */
  fun collapseOnInteraction() {
    if (isExpanded) collapseAnimated()
  }

  /** Collapse the pill without animation. */
  fun collapseImmediately() {
    cancelCollapseTimer()
    cancelCurrentAnimation()
    isExpanded = false
    expandFraction = 0f
    chevronView.alpha = 0f
    applyExpandFraction()
  }

  /**
   * Places the visible pill [left]..[bottom] px from the map's edges. The view's own margins are
   * smaller by the touch inset, and may be negative.
   */
  fun setPillMargins(left: Int, top: Int, right: Int, bottom: Int) {
    pillMargins.set(left, top, right, bottom)
    applyPillMargins()
    updateExpandedMetrics()
    applyExpandFraction()
  }

  private fun applyPillMargins() {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(
      pillMargins.left - touchInsetX,
      pillMargins.top - touchInsetY,
      pillMargins.right - touchInsetX,
      pillMargins.bottom - touchInsetY,
    )
  }

  // ── touch ─────────────────────────────────────────────────────────────────────────────────

  /** Set the listener called when the pill is tapped. */
  fun setOnTapListener(listener: OnClickListener) {
    onTapListener = listener
  }

  override fun performClick(): Boolean {
    super.performClick()
    // A tap never expands the pill; the expanded form is only the intro.
    onTapListener?.onClick(this)
    if (isExpanded) collapseAnimated()
    return true
  }

  // ── animation ────────────────────────────────────────────────────────────────────────────

  private fun expandAnimated() {
    cancelCollapseTimer()
    cancelCurrentAnimation()
    isExpanded = true
    updateExpandedMetrics()

    scheduleCollapse()
    animateTo(1f)
  }

  private fun collapseAnimated() {
    cancelCollapseTimer()
    cancelCurrentAnimation()
    isExpanded = false
    animateTo(0f)
  }

  /**
   * Drives [expandFraction] to [target]. The chevron fades on a shorter curve, so it is fully
   * shown before the pill's edge reaches it and gone before the edge clips it.
   */
  private fun animateTo(target: Float) {
    if (reduceMotion()) {
      expandFraction = target
      applyExpandFraction()
      chevronView.alpha = target
      return
    }
    currentAnimator = ValueAnimator.ofFloat(expandFraction, target).apply {
      duration = ANIMATION_DURATION_MS
      interpolator = AccelerateDecelerateInterpolator()
      addUpdateListener { anim ->
        expandFraction = anim.animatedValue as Float
        applyExpandFraction()
      }
      start()
    }
    chevronView.animate()
      .alpha(target)
      .setDuration(CHEVRON_FADE_DURATION_MS)
      .setInterpolator(DecelerateInterpolator())
      .start()
  }

  private fun scheduleCollapse() {
    Runnable { collapseAnimated() }.also { runnable ->
      collapseRunnable = runnable
      handler.postDelayed(runnable, EXPAND_LINGER_MS)
    }
  }

  private fun cancelCollapseTimer() {
    collapseRunnable?.let { handler.removeCallbacks(it) }
    collapseRunnable = null
  }

  private fun cancelCurrentAnimation() {
    currentAnimator?.cancel()
    currentAnimator = null
    chevronView.animate().cancel()
  }

  /** Applies the width and content alpha for the current [expandFraction]. */
  private fun applyExpandFraction() {
    updatePaddingForState()
    attributionLabel.alpha = expandFraction
    // The ICON_ONLY wordmark fades, or its first letter could show at the clip edge.
    wordmarkView.alpha =
      if (collapsedStyle == ConsolidatedAttributionCollapsedStyle.ICON_ONLY) expandFraction else 1f
    layoutParams?.let {
      it.width = widthForFraction(expandFraction) + 2 * touchInsetX
      requestLayout()
    }
  }

  /** Pads the pill out to [TOUCH_TARGET_DP] on each axis where it is smaller. */
  private fun updateTouchInsets() {
    touchInsetX = ((touchTargetPx - collapsedWidthPx()) / 2).coerceAtLeast(0)
    touchInsetY = ((touchTargetPx - pillHeightPx) / 2).coerceAtLeast(0)
    for (layer in 0 until rippleDrawable.numberOfLayers) {
      rippleDrawable.setLayerInset(layer, touchInsetX, touchInsetY, touchInsetX, touchInsetY)
    }
    // Same bounds are a no-op, so reset them to lay out the layers with the new insets.
    val bounds = Rect(rippleDrawable.bounds)
    rippleDrawable.setBounds(0, 0, 0, 0)
    rippleDrawable.bounds = bounds
    applyPillMargins()
  }

  /** Centers the icon in the circle of a collapsed ICON_ONLY pill. */
  private fun updatePaddingForState() {
    val pad = if (!isExpanded && collapsedStyle == ConsolidatedAttributionCollapsedStyle.ICON_ONLY) {
      (pillHeightPx - iconSizePx) / 2
    } else {
      horizontalPaddingPx
    }
    setPadding(pad + touchInsetX, touchInsetY, pad + touchInsetX, touchInsetY)
  }

  // ── width calculation ─────────────────────────────────────────────────────────────────────

  private fun collapsedWidthPx(): Int = when (collapsedStyle) {
    ConsolidatedAttributionCollapsedStyle.ICON_ONLY -> pillHeightPx
    ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK ->
      2 * horizontalPaddingPx + iconSizePx + wordmarkMarginStartPx +
        wordmarkWidthPx + trailingInsetPx
    else -> throw IllegalStateException("Unsupported collapsed style: $collapsedStyle")
  }

  /** The map width less the pill's horizontal margins. Compose pads the map width instead. */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun maxPillWidthPx(): Int {
    if (mapViewWidth <= 0) {
      return fallbackMaxWidthPx
    }
    return (mapViewWidth - pillMargins.left - pillMargins.right).coerceAtLeast(collapsedWidthPx())
  }

  /**
   * Caps the label so it truncates on a narrow map instead of pushing the chevron out, and
   * recomputes [expandedWidthPx]. Called when the text or map width changes, not per frame.
   */
  private fun updateExpandedMetrics() {
    attributionLabel.maxWidth = textWidthPx
      .coerceAtMost((maxPillWidthPx() - expandedChromeWidthPx).coerceAtLeast(0))
    expandedWidthPx = expandedChromeWidthPx + attributionLabel.maxWidth
  }

  /** Pill width at animation position [fraction], between the collapsed and expanded widths. */
  private fun widthForFraction(fraction: Float): Int {
    val collapsed = collapsedWidthPx()
    if (fraction <= 0f) {
      return collapsed
    }
    return (collapsed + (expandedWidthPx - collapsed) * fraction).toInt()
  }

  // ── color helpers ─────────────────────────────────────────────────────────────────────────

  private data class SchemeColors(
    @ColorInt val pillColor: Int,
    @ColorInt val contentColor: Int,
    @ColorInt val detailColor: Int,
    @ColorInt val textColor: Int,
  )

  private fun materialColors(
    material: ConsolidatedAttributionMaterial,
    @ColorInt tintColor: Int,
  ): SchemeColors {
    val night = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
      Configuration.UI_MODE_NIGHT_YES
    val translucent = material == ConsolidatedAttributionMaterial.TRANSLUCENT
    val alpha = if (translucent) {
      TRANSLUCENT_ALPHA
    } else {
      if (night) DARK_SOLID_ALPHA else LIGHT_SOLID_ALPHA
    }
    val surface = if (night) DARK_SURFACE else LIGHT_SURFACE
    val content = if (night) DARK_CONTENT else LIGHT_CONTENT
    val detail = if (night) DARK_DETAIL else LIGHT_DETAIL
    return SchemeColors(
      // Dark mode ignores the tint.
      pillColor = if (night) {
        ColorUtils.setAlphaComponent(surface, (alpha * 255).toInt())
      } else {
        tintedSurface(surface, tintColor, translucent, alpha)
      },
      contentColor = content,
      detailColor = detail,
      textColor = content,
    )
  }

  /** Takes the hue of [tintColor] with clamped saturation, keeping the surface's brightness. */
  @ColorInt
  private fun tintedSurface(
    @ColorInt surface: Int,
    @ColorInt tintColor: Int,
    translucent: Boolean,
    alpha: Float,
  ): Int {
    val opaqueAlpha = (alpha * 255).toInt()
    if (Color.alpha(tintColor) == 0) {
      return ColorUtils.setAlphaComponent(surface, opaqueAlpha)
    }
    val tintHsv = FloatArray(3)
    Color.colorToHSV(tintColor, tintHsv)
    val surfaceHsv = FloatArray(3)
    Color.colorToHSV(surface, surfaceHsv)
    val maxChroma = if (translucent) TRANSLUCENT_TINT_CHROMA else SOLID_TINT_CHROMA
    surfaceHsv[0] = tintHsv[0]
    surfaceHsv[1] = tintHsv[1].coerceAtMost(maxChroma)
    return ColorUtils.setAlphaComponent(Color.HSVToColor(surfaceHsv), opaqueAlpha)
  }

  // ── session state ─────────────────────────────────────────────────────────────────────────

  private fun sessionHasExpanded(): Boolean {
    if (introExpansion == ConsolidatedAttributionIntroExpansion.ONCE && introFlagStore.isSet) {
      return true
    }
    // At most once per process, so a second map doesn't replay it.
    return hasShownExpandedInProcess
  }

  private fun markSessionExpanded() {
    hasShownExpandedInProcess = true
    // Saved for every mode, as on iOS; only ONCE reads it.
    introFlagStore.isSet = true
  }

  // ── reduce motion ─────────────────────────────────────────────────────────────────────────

  private fun reduceMotion(): Boolean {
    val scale = Settings.Global.getFloat(
      context.contentResolver,
      Settings.Global.ANIMATOR_DURATION_SCALE,
      1f,
    )
    return scale == 0f
  }

  // ── measure ───────────────────────────────────────────────────────────────────────────────

  /** The height is fixed; the width comes from [layoutParams]. */
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    setMeasuredDimension(measuredWidth, pillHeightPx + 2 * touchInsetY)
  }

  // ── lifecycle ─────────────────────────────────────────────────────────────────────────────

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    collapseImmediately()
  }

  // ── companion ─────────────────────────────────────────────────────────────────────────────

  companion object {
    // Dimensions (dp / sp)
    private const val PILL_HEIGHT_DP = 28f
    private const val ICON_SIZE_DP = 21f
    private const val HORIZONTAL_PADDING_DP = 4f
    private const val INNER_SPACING_DP = 4f
    // The pin glyph already has inner padding, so no gap is needed.
    private const val WORDMARK_MARGIN_START_DP = 0f

    /** Extra padding after the last element, so the pill looks balanced. */
    private const val TRAILING_INSET_DP = 4f
    private const val ATTRIBUTION_TEXT_SIZE_SP = 10f

    /** Cap used only while the map's width is still unknown. */
    private const val FALLBACK_MAX_WIDTH_DP = 220f

    /** Android's minimum touch target. */
    private const val TOUCH_TARGET_DP = 48f

    // Colors
    private const val LIGHT_SURFACE = 0xFFFFFFFF.toInt()
    private const val DARK_SURFACE = 0xFF1A1A1A.toInt()
    private const val LIGHT_CONTENT = 0xB3000000.toInt()
    private const val DARK_CONTENT = 0xE6FFFFFF.toInt()

    // Fills the letter counters, outline halo and the pin's star.
    private const val LIGHT_DETAIL = 0xFFFFFFFF.toInt()
    private const val DARK_DETAIL = 0xFF1A1A1A.toInt()

    // Surface opacity.
    private const val LIGHT_SOLID_ALPHA = 0.9f
    private const val DARK_SOLID_ALPHA = 0.85f
    private const val TRANSLUCENT_ALPHA = 0.5f

    // Maximum saturation retained from the tint color.
    private const val SOLID_TINT_CHROMA = 0.14f
    private const val TRANSLUCENT_TINT_CHROMA = 0.22f

    // Alpha applied to the content color for the press ripple.
    private const val RIPPLE_ALPHA = 40

    // Timing (ms)
    private const val ANIMATION_DURATION_MS = 400L
    private const val CHEVRON_FADE_DURATION_MS = 160L
    private const val EXPAND_LINGER_MS = 5_000L

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var introFlagStore: IntroFlagStore = SettingsServiceIntroFlagStore

    /** Set once the intro has played in this process. */
    @Volatile
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var hasShownExpandedInProcess = false
  }
}