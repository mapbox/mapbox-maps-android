package com.mapbox.maps.plugin.location;

import android.animation.TypeEvaluator;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Point;

class PointEvaluator implements TypeEvaluator<Point> {

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Point evaluate(float fraction, @NonNull Point startValue, @NonNull Point endValue) {
        return Point.fromLngLat(
                startValue.longitude() + ((endValue.longitude() - startValue.longitude()) * fraction),
                startValue.latitude() + ((endValue.latitude() - startValue.latitude()) * fraction)
        );
    }
}