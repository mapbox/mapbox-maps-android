package com.mapbox.maps.testapp.auto.testing;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.SurfaceContainer;

import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapSurface;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.extension.androidauto.MapboxCarMap;
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver;
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface;

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
      public boolean onScale(@NonNull MapboxCarMapSurface mapboxCarMapSurface, @NonNull ScreenCoordinate anchor, double fromZoom, double toZoom) {
        return false;
      }

      @Override
      public boolean onFling(@NonNull MapboxCarMapSurface mapboxCarMapSurface, float velocityX, float velocityY) {
        return false;
      }

      @Override
      public boolean onScroll(@NonNull MapboxCarMapSurface mapboxCarMapSurface, float distanceX, float distanceY) {
        return false;
      }

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

}
