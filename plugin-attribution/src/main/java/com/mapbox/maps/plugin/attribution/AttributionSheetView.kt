package com.mapbox.maps.plugin.attribution

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.common.geofencing.GeofencingUtilsUserConsentResponseCallback
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.logW
import com.mapbox.maps.module.MapTelemetry

/**
 * The attribution sheet: a bottom-attached panel listing the style's data providers, the telemetry
 * and geofencing consent switches, and the privacy policy.
 *
 */
internal class AttributionSheetView(context: Context) : FrameLayout(context) {

  private val itemsContainer: LinearLayout

  /** What the sheet is currently showing; set by [bind]. */
  private var payload: AttributionSheetPayload? = null
  private var onDismissRequest: () -> Unit = {}

  init {
    LayoutInflater.from(context).inflate(R.layout.mapbox_attribution_bottom_sheet, this, true)
    itemsContainer = findViewById(R.id.mapbox_attribution_items_container)
    findViewById<View>(R.id.mapbox_attribution_header_divider).setBackgroundColor(dividerColor())
    // The scrim behind the sheet dismisses on tap, and the rows do not cover the whole panel, so
    // the sheet has to consume touches on its own background - the title, the gaps between rows,
    // the non-link part of the telemetry text.
    isClickable = true

    val content = getChildAt(0)
    val bottomInset = findViewById<View>(R.id.mapbox_attribution_bottom_inset)
    val minBottomInset = bottomInset.layoutParams.height
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
      val bars = insets.getInsets(
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
      )
      content.setPadding(bars.left, 0, bars.right, 0)
      bottomInset.layoutParams.height = minBottomInset + bars.bottom
      bottomInset.requestLayout()
      insets
    }
  }

  /**
   * Fill the sheet in from [payload].
   *
   * @param onDismissRequest invoked by the close button and before a link row opens its page.
   */
  fun bind(
    payload: AttributionSheetPayload,
    onDismissRequest: () -> Unit,
  ) {
    this.payload = payload
    this.onDismissRequest = onDismissRequest
    findViewById<View>(R.id.mapbox_attribution_close_button)
      .setOnClickListener { onDismissRequest() }

    itemsContainer.removeAllViews()
    val rows = AttributionSheetRows.from(payload.attributionList)

    val geofencingConsent = payload.geofencingConsent?.takeIf { it.shouldShowConsent() }
    val hasConsentRows = rows.feedback != null || payload.telemetry != null ||
      geofencingConsent != null || rows.privacy != null

    rows.dataLinks.forEach { addLinkRow(it, isFeedback = false) }
    // The divider separates the data links from the rows below them, so it is only earned when
    // there are rows on both sides of it.
    if (rows.dataLinks.isNotEmpty() && hasConsentRows) {
      addDivider()
    }
    rows.feedback?.let { addLinkRow(it, isFeedback = true) }

    payload.telemetry?.let { addTelemetryRows(it) }
    geofencingConsent?.let { addGeofencingRow(it) }

    rows.privacy?.let { addLinkRow(it, isFeedback = false) }
  }

  private fun addLinkRow(attribution: Attribution, isFeedback: Boolean) {
    val hasUrl = attribution.url.isNotEmpty()
    itemsContainer.addView(
      TextView(context).apply {
        text = attribution.title
        setTextColor(
          if (hasUrl) ContextCompat.getColor(context, R.color.mapbox_blue) else Color.GRAY
        )
        gravity = Gravity.CENTER_VERTICAL
        applyRowLayout()
        if (hasUrl) {
          background = selectableItemBackground()
          setOnClickListener {
            val url = resolveUrl(attribution, isFeedback)
            onDismissRequest()
            context.openAttributionUrl(url)
          }
        }
      }
    )
  }

  private fun resolveUrl(attribution: Attribution, isFeedback: Boolean): String {
    if (!isFeedback) {
      return attribution.url
    }
    return payload?.mapAttributionDelegate?.buildMapBoxFeedbackUrl(context) ?: attribution.url
  }

  private fun addTelemetryRows(telemetry: MapTelemetry) {
    val switch = addSwitchRow(
      context.getString(R.string.mapbox_attribution_telemetry_switch_label),
      telemetry.userTelemetryRequestState,
    )

    val description = makeDescription(telemetryMessage(telemetry.userTelemetryRequestState)).apply {
      movementMethod = LinkMovementMethod.getInstance()
    }
    itemsContainer.addView(description)

    // The message explains what the current state means, so it has to follow the switch.
    switch.setOnCheckedChangeListener { _, checked ->
      telemetry.userTelemetryRequestState = checked
      description.text = telemetryMessage(checked)
    }
  }

  private fun addGeofencingRow(geofencingConsent: MapGeofencingConsent) {
    val callback = GeofencingUtilsUserConsentResponseCallback { result ->
      result.error?.let { logW(TAG, "Geofencing consent error: ${it.type}") }
    }
    addSwitchRow(
      context.getString(R.string.mapbox_attribution_geofencing_switch_label),
      geofencingConsent.getUserConsent(),
    ).setOnCheckedChangeListener { _, checked ->
      geofencingConsent.setUserConsent(checked, callback)
    }
    itemsContainer.addView(
      makeDescription(context.getString(R.string.mapbox_attributionGeofencingMessage))
    )
  }

  private fun makeDescription(text: CharSequence): TextView = TextView(context).apply {
    this.text = text
    setTextColor(themeColor(android.R.attr.textColorSecondary, Color.GRAY))
    setTextSize(
      TypedValue.COMPLEX_UNIT_PX,
      resources.getDimension(R.dimen.mapbox_attribution_sheet_description_text_size)
    )
    layoutParams = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT,
    )
    setPadding(
      sidePadding,
      resources.getDimensionPixelSize(R.dimen.mapbox_attribution_sheet_description_padding_top),
      sidePadding,
      resources.getDimensionPixelSize(R.dimen.mapbox_attribution_sheet_description_padding_bottom),
    )
  }

  private fun addDivider() {
    itemsContainer.addView(
      View(context).apply {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
        setBackgroundColor(dividerColor())
      }
    )
  }

  private fun telemetryMessage(enabled: Boolean): CharSequence {
    val message = context.getString(
      if (enabled) {
        R.string.mapbox_attribution_telemetry_message_enabled
      } else {
        R.string.mapbox_attribution_telemetry_message_disabled
      }
    )
    val learnMore = context.getString(R.string.mapbox_attribution_telemetry_learn_more)
    val full = "$message $learnMore"
    return SpannableString(full).apply {
      setSpan(
        object : ClickableSpan() {
          override fun onClick(widget: View) {
            context.openAttributionUrl(context.getString(R.string.mapbox_telemetryLink))
          }
        },
        full.length - learnMore.length,
        full.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,
      )
    }
  }

  /** Adds a row with [label] and a switch, and returns the switch. */
  private fun addSwitchRow(label: String, checked: Boolean): Switch {
    val switch = Switch(context).apply {
      id = View.generateViewId()
      isChecked = checked
    }
    val row = LinearLayout(context).apply {
      orientation = LinearLayout.HORIZONTAL
      gravity = Gravity.CENTER_VERTICAL
      applyRowLayout()
    }
    row.addView(
      TextView(context).apply {
        text = label
        labelFor = switch.id
        setTextColor(themeColor(android.R.attr.textColorPrimary, Color.BLACK))
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
      }
    )
    row.addView(switch)
    itemsContainer.addView(row)
    return switch
  }

  /** Rows grow with their text, so large font scales and wrapped titles are not clipped. */
  private fun View.applyRowLayout() {
    layoutParams = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT,
    )
    minimumHeight = resources.getDimensionPixelSize(R.dimen.mapbox_attribution_sheet_row_min_height)
    val verticalPadding =
      resources.getDimensionPixelSize(R.dimen.mapbox_attribution_sheet_row_vertical_padding)
    setPadding(sidePadding, verticalPadding, sidePadding, verticalPadding)
  }

  private val sidePadding: Int
    get() = resources.getDimensionPixelSize(R.dimen.mapbox_attribution_sheet_side_padding)

  /** The primary text color at 12% alpha, so it shows on light and dark themes. */
  private fun dividerColor(): Int =
    ColorUtils.setAlphaComponent(themeColor(android.R.attr.textColorPrimary, Color.BLACK), 0x1F)

  private fun themeColor(attr: Int, fallback: Int): Int {
    val typedArray = context.obtainStyledAttributes(intArrayOf(attr))
    val color = typedArray.getColor(0, fallback)
    typedArray.recycle()
    return color
  }

  private fun selectableItemBackground(): Drawable? {
    val typedArray =
      context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackground))
    val drawable = typedArray.getDrawable(0)
    typedArray.recycle()
    return drawable
  }

  private companion object {
    private const val TAG = "AttributionSheetView"
  }
}