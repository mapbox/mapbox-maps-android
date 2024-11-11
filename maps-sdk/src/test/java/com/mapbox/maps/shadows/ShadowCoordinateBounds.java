package com.mapbox.maps.shadows;

import com.mapbox.maps.CoordinateBounds;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of CoordinateBounds, this shadow class
 * will be used for the Robolectric unit tests.
 */
@Implements(CoordinateBounds.class)
public class ShadowCoordinateBounds {
    @Implementation
    public static double north() {
        return 20.0;
    }
    @Implementation
    public static double east() {
        return 20.0;
    }
    @Implementation
    public static double south() {
        return 10.0;
    }
    @Implementation
    public static double west() {
        return 10.0;
    }
}
