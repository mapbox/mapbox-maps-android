package com.mapbox.maps.testapp.auto.testing;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.SurfaceContainer;

import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapSurface;
import com.mapbox.maps.MapboxExperimental;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.extension.androidauto.MapboxCarMap;
import com.mapbox.maps.extension.androidauto.DefaultMapboxCarMapGestureHandler;
import com.mapbox.maps.extension.androidauto.MapboxCarMapGestureHandler;
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver;
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface;

@MapboxExperimental
class CarJavaInterfaceChecker {

  void constructors(MapInitOptions mapInitOptions) {
    MapboxCarMap mapboxCarMap = new MapboxCarMap(mapInitOptions);
  }

  void getters(MapboxCarMap mapboxCarMap) {
    CarContext carContext = mapboxCarMap.getCarContext();
    Rect visibleArea = mapboxCarMap.getVisibleArea();
    EdgeInsets edgeInsets = mapboxCarMap.getEdgeInsets();
    MapboxCarMapSurface mapboxCarMapSurface = mapboxCarMap.getCarMapSurface();
  }

  void getters(MapboxCarMapSurface mapboxCarMapSurface) {
    CarContext carContext = mapboxCarMapSurface.getCarContext();
    MapSurface mapSurface = mapboxCarMapSurface.getMapSurface();
    SurfaceContainer surfaceContainer = mapboxCarMapSurface.getSurfaceContainer();
  }

  private void observers(MapboxCarMap mapboxCarMap) {
    MapboxCarMapObserver observer = new MapboxCarMapObserver() {

      @Override
      public void onVisibleAreaChanged(@NonNull Rect visibleArea, @NonNull EdgeInsets edgeInsets) {

      }

      @Override
      public void onDetached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {

      }

      @Override
      public void onAttached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {

      }
    };
    mapboxCarMap.registerObserver(observer);
    mapboxCarMap.unregisterObserver(observer);
    mapboxCarMap.clearObservers();
  }

  private void gestures(MapboxCarMap mapboxCarMap) {
    MapboxCarMapGestureHandler gestures = new MapboxCarMapGestureHandler() {
      @Override
      public void onScale(@NonNull MapboxCarMapSurface mapboxCarMapSurface, float focusX, float focusY, float scaleFactor) {}

      @Override
      public void onFling(@NonNull MapboxCarMapSurface mapboxCarMapSurface, float velocityX, float velocityY) {}

      @Override
      public void onScroll(@NonNull MapboxCarMapSurface mapboxCarMapSurface, @NonNull ScreenCoordinate visibleCenter, float distanceX, float distanceY) {}
    };
    mapboxCarMap.setGestureHandler(gestures);
    mapboxCarMap.setGestureHandler(new DefaultMapboxCarMapGestureHandler());
    mapboxCarMap.setGestureHandler(null);
  }
}