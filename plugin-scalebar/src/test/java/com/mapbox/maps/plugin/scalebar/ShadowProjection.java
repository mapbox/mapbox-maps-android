package com.mapbox.maps.plugin.scalebar;

import com.mapbox.maps.Projection;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of Projection, this shadow Projection
 * will be used for the Robolectric unit tests.
 */
@Implements(Projection.class)
public class ShadowProjection {
    @Implementation
    public static double getMetersPerPixelAtLatitude(double latitude, double zoom) {
        return 0.0;
    }
}
