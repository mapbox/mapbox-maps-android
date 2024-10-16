package com.mapbox.maps.plugin.gestures;

import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.mapbox.android.gestures.StandardGestureDetector;

/**
 * Gesture listener callbacks comes from the Java library and could still be NULL.
 * This shim redeclares nullability annotations to allow overriding corresponding methods
 * with an optional type in Kotlin.
 */
class StandardGestureListenerShim extends StandardGestureDetector.SimpleStandardOnGestureListener {

  @Override
  public boolean onSingleTapConfirmed(@Nullable MotionEvent e) {
    return super.onSingleTapConfirmed(e);
  }

  @Override
  public boolean onDoubleTap(@Nullable MotionEvent e) {
    return super.onDoubleTap(e);
  }

  @Override
  public boolean onDoubleTapEvent(@Nullable MotionEvent e) {
    return super.onDoubleTapEvent(e);
  }

  @Override
  public boolean onDown(@Nullable MotionEvent e) {
    return super.onDown(e);
  }

  @Override
  public void onShowPress(@Nullable MotionEvent e) {
    super.onShowPress(e);
  }

  @Override
  public boolean onSingleTapUp(@Nullable MotionEvent e) {
    return super.onSingleTapUp(e);
  }

  @Override
  public boolean onScroll(@Nullable MotionEvent e1, @Nullable MotionEvent e2, float distanceX, float distanceY) {
    return super.onScroll(e1, e2, distanceX, distanceY);
  }

  @Override
  public void onLongPress(@Nullable MotionEvent e) {
    super.onLongPress(e);
  }

  @Override
  public boolean onFling(@Nullable MotionEvent e1, @Nullable MotionEvent e2, float velocityX, float velocityY) {
    return super.onFling(e1, e2, velocityX, velocityY);
  }
}
