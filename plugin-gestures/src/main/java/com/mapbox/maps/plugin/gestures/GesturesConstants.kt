package com.mapbox.maps.plugin.gestures

internal const val SCHEDULED_ANIMATION_TIMEOUT = 150L

internal const val VELOCITY_THRESHOLD_IGNORE_FLING: Long = 1000

/**
 * Last scale span delta to XY velocity ratio required to execute scale velocity animation.
 */
internal const val SCALE_VELOCITY_RATIO_THRESHOLD = 4 * 1e-3

/**
 * Last rotation delta to XY velocity ratio required to execute rotation velocity animation.
 */
internal const val ROTATE_VELOCITY_RATIO_THRESHOLD = 2.2 * 1e-4

/**
 * Maximum absolute zoom change for multi-pointer scale velocity animation
 */
internal const val MAX_ABSOLUTE_SCALE_VELOCITY_CHANGE = 2.5

/**
 * Scale velocity animation duration multiplier.
 */
internal const val SCALE_VELOCITY_ANIMATION_DURATION_MULTIPLIER = 150.0

/**
 * Maximum possible zoom change during the quick zoom gesture executed across the whole screen
 */
internal const val QUICK_ZOOM_MAX_ZOOM_CHANGE = 4.0

/**
 * Zoom value multiplier for scale gestures.
 */
internal const val ZOOM_RATE = 0.65f

/**
 * Maximum angular velocity for rotation animation
 */
internal const val MAXIMUM_ANGULAR_VELOCITY = 30f

/**
 * Factor to calculate pitch change based on pixel change during shove gesture.
 */
internal const val SHOVE_PIXEL_CHANGE_FACTOR = 0.1f

/**
 * The currently supported minimum pitch value.
 */
internal const val MINIMUM_PITCH = 0.0

/**
 * Maximum locked pitch value.
 */
internal const val NORMAL_MAX_PITCH = 60.0

/**
 * The currently supported maximum unlocked pitch value.
 */
internal const val MAXIMUM_PITCH = 85.0

/**
 * Default animation time
 */
internal const val ANIMATION_DURATION = 300

/**
 * Default short animation time
 */
internal const val ANIMATION_DURATION_SHORT = 150

/**
 * Animation time of a fling gesture
 */
internal const val ANIMATION_DURATION_FLING_BASE = ANIMATION_DURATION_SHORT.toLong()