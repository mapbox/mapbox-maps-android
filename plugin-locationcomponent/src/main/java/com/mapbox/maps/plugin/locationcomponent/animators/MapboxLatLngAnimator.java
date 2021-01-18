package com.mapbox.maps.plugin.locationcomponent.animators;

import android.animation.TypeEvaluator;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Point;

class MapboxLatLngAnimator extends MapboxAnimator<Point> {

  MapboxLatLngAnimator(@NonNull Point[] values, @NonNull AnimationsValueChangeListener updateListener,
                       int maxAnimationFps) {
    super(values, updateListener, maxAnimationFps);
  }

  @NonNull
  @Override
  TypeEvaluator provideEvaluator() {
    return new PointEvaluator();
  }
}
