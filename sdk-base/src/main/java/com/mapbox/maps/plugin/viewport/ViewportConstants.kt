package com.mapbox.maps.plugin.viewport

/**
 * The default maximum duration of the generated transitions set for the state transition options,
 * including delays between animators and their respective durations.
 */
const val DEFAULT_STATE_TRANSITION_MAX_DURATION_MS = 3500L

/**
 * The default maximum duration of the generated transitions set for the frame transition options,
 * including delays between animators and their respective durations.
 */
const val DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS = 1000L

/**
 * Indicates that the [ViewportStatus] is changed due to programmatic reason.
 *
 * Used in the reason field of [ViewportStatusObserver]
 */
const val VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC = "PROGRAMMATIC"

/**
 * Indicates that the [ViewportStatus] is changed due to user interaction reason.
 *
 * Used in the reason field of [ViewportStatusObserver]
 */
const val VIEWPORT_STATUS_OBSERVER_REASON_USER_INTERACTION = "USER_INTERACTION"