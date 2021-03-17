package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.plugin.delegates.listeners.eventdata.RenderMode

/**
 *The data class for Map Loading Error event data in Observer
 */
data class RenderFrameFinishedEventData(
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