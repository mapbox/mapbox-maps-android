package com.mapbox.maps.extension.androidauto;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.car.app.SurfaceCallback;

import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapboxExperimental;

/**
 * Many downstream services will not work until the surface has been created and the map has
 * loaded. This interface allows you to create custom Mapbox experiences for the car.
 */
@MapboxExperimental
public interface MapboxCarMapObserver {

  /**
   * Called when a {@link MapboxCarMapSurface} has been loaded.
   * You can assume there will only be a single surface at a time.
   *
   * @see MapboxCarMap#registerObserver
   *
   * @param mapboxCarMapSurface loaded and ready to use car map surface
   */
  default void onAttached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {
    // No op by default
  }

  /**
   * Called when a {@link MapboxCarMapSurface} has been detached from this observer. Some examples that
   * can cause this to detach are:
   * - {@link MapboxCarMap} lifecycle is destroyed
   * - This observer has been unregistered with {@link MapboxCarMap#unregisterObserver}
   *
   * You can assume that there was a corresponding call to {@link #onAttached} with the same
   * [MapboxCarMapObserver] instance.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   */
  default void onDetached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {
    // No op by default
  }

  /**
   * Called when the car library updates the visible regions for the surface. For example, this
   * is triggered when the action buttons come in and out of visibility.
   * You can assume this will be called after {@link #onAttached}.
   *
   * @see SurfaceCallback#onVisibleAreaChanged(Rect) for more details.
   *
   * @param visibleArea the visible area provided by the host
   * @param edgeInsets distance from each side of the screen that creates the visibleArea
   */
  default void onVisibleAreaChanged(@NonNull Rect visibleArea, @NonNull EdgeInsets edgeInsets) {
    // No op by default
  }

  /**
   * Called when the car library updates the stable region for the surface. This area will remain
   * constant while the visible area changes when views come in and out of view.
   *
   * @see SurfaceCallback#onStableAreaChanged(Rect) for more details.
   *
   * @param stableArea the stable area provided by the host
   * @param edgeInsets distance from each side of the screen that creates the stableArea
   */
  default void onStableAreaChanged(@NonNull Rect stableArea, @NonNull EdgeInsets edgeInsets) {
    // No op by default
  }
}
