package com.mapbox.maps.plugin.gestures

internal const val SCHEDULED_ANIMATION_TIMEOUT = 150L

/**
 * The velocity threshold value at which a fling gesture is ignored.
 * The Android OS produces fling gestures fairly easy, this results in unwanted fling invocations.
 */
internal const val VELOCITY_THRESHOLD_IGNORE_FLING: Long = 1000

/**
 * Fling limiting factor. Velocity values produces by Android OS are not related to any pixel value.
 * We add a limiting factor to produce values that create a good user experience when flinging.
 */
internal const val FLING_LIMITING_FACTOR: Double = 10.0

/**
 * Scroll limiting factor. This limit factor is used to limit the maximum scroll speed that's generated
 * by the native drag API.
 */
internal const val SCROLL_LIMITING_FACTOR: Double = 100.0

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
 * Maximum pitch additional component for the fling gesture.
 */
internal const val MAX_FLING_PITCH_FACTOR = 300.0

/**
 * The currently supported maximum unlocked pitch value.
 */
internal const val MAXIMUM_PITCH = 85.0

/**
 * Default animation time
 */
internal const val ANIMATION_DURATION = 300