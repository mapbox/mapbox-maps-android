package com.mapbox.maps.plugin.viewannotation;

/** Enum describing how to place view annotation relatively to geometry. */
public enum ViewAnnotationAnchor {
    /** The top of the view annotation is placed closest to the geometry. */
    TOP,
    /** The left side of the view annotation is placed closest to the geometry. */
    LEFT,
    /** The bottom of the view annotation is placed closest to the geometry. */
    BOTTOM,
    /** The right side of the view annotation is placed closest to the geometry. */
    RIGHT,
    /** The top-left corner of the view annotation is placed closest to the geometry. */
    TOP_LEFT,
    /** The bottom-right corner of the view annotation is placed closest to the geometry. */
    BOTTOM_RIGHT,
    /** The top-right corner of the view annotation is placed closest to the geometry. */
    TOP_RIGHT,
    /** The bottom-left corner of the view annotation is placed closest to the geometry. */
    BOTTOM_LEFT,
    /** The center of the view annotation is placed closest to the geometry. */
    CENTER
}
