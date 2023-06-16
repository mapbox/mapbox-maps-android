package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.RenderMode

/**
 * The data class for render-frame-finished event data in Observer
 */
@Deprecated(
  message = "This data class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("RenderFrameFinished"),
  level = DeprecationLevel.WARNING
)
data class RenderFrameFinishedEventData(
  /**
   * Representing timestamp taken at the time of an event creation, in microseconds, since the epoch.
   */
  @SerializedName("begin") val begin: Long,
  /**
   * For an interval events, an optional `end` property will be present that represents timestamp taken at the time
   * of an event completion.
   */
  @SerializedName("end") val end: Long?,
  /**
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   */
  @SerializedName("render-mode") val renderMode: RenderMode,
  /**
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   */
  @SerializedName("needs-repaint") val needsRepaint: Boolean,
  /**
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   */
  @SerializedName("placement-changed") val placementChanged: Boolean

)