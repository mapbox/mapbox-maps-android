package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental

/**
 * The default maximum duration of the generated transitions set for the state transition options,
 * including delays between animators and their respective durations.
 */
const val DEFAULT_TRANSITION_MAX_DURATION_MS = 3500L

/**
 * The default duration of the generated transitions set for the frame transition options.
 */
const val DEFAULT_STATE_ANIMATION_DURATION_MS = 1000L

/**
 * The default pitch value for the follow puck viewport state.
 */
const val DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH = 45.0

/**
 * The default zoom value for the follow puck viewport state.
 */
const val DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM = 16.35

/**
 * The default vertical fov value for the follow puck viewport state.
 */
@MapboxExperimental
const val DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_VERTICAL_FOV = 36.87

/**
 * The default vertical fov value for the overview viewport state.
 */
@MapboxExperimental
const val DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV = 36.87