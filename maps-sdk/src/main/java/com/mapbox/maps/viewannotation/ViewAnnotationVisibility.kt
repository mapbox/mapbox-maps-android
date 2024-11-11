package com.mapbox.maps.viewannotation

internal enum class ViewAnnotationVisibility {
  /**
   * Initial state, track it separately for several corner cases.
   */
  INITIAL,

  /**
   * View is set to be visible based on Android view visibility obtained from OnGlobalLayoutListener
   * but view will actually be positioned later based on [VISIBLE_AND_POSITIONED].
   */
  VISIBLE_AND_NOT_POSITIONED,

  /**
   * View is totally visible for the user.
   */
  VISIBLE_AND_POSITIONED,

  /**
   * View is either removed from layout or not visible.
   */
  INVISIBLE,
}