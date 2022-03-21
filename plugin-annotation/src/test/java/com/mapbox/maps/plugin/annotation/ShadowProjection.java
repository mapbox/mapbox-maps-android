package com.mapbox.maps.plugin.annotation;

import com.mapbox.geojson.Point;
import com.mapbox.maps.MercatorCoordinate;
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
    public static MercatorCoordinate project(Point coordinate, double zoomScale) {
        return new MercatorCoordinate(0.0,0.0);
    }

    @Implementation
    public  static Point unproject(MercatorCoordinate coordinate, double zoomScale){
        return Point.fromLngLat(0.0,0.0);
    }
}
