package com.mapbox.maps.testapp.auto.testing;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.Session;
import androidx.car.app.SurfaceContainer;

import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapSurface;
import com.mapbox.maps.MapboxExperimental;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.extension.androidauto.DefaultMapboxCarMapGestureHandler;
import com.mapbox.maps.extension.androidauto.MapboxCarMap;
import com.mapbox.maps.extension.androidauto.MapboxCarMapEx;
import com.mapbox.maps.extension.androidauto.MapboxCarMapGestureHandler;
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver;
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface;

@MapboxExperimental
class CarJavaInterfaceChecker {

  void constructorMapboxCarMap(MapInitOptions mapInitOptions) {
    new MapboxCarMap();
  }

  void getters(MapboxCarMap mapboxCarMap) {
    CarContext carContext = mapboxCarMap.getCarContext();
    Rect visibleArea = mapboxCarMap.getVisibleArea();
    EdgeInsets edgeInsets = mapboxCarMap.getVisibleEdgeInsets();
    Rect stableArea = mapboxCarMap.getStableArea();
    EdgeInsets stableEdgeInsets = mapboxCarMap.getStableEdgeInsets();
    MapboxCarMapSurface mapboxCarMapSurface = mapboxCarMap.getCarMapSurface();
  }

  void getters(MapboxCarMapSurface mapboxCarMapSurface) {
    CarContext carContext = mapboxCarMapSurface.getCarContext();
    MapSurface mapSurface = mapboxCarMapSurface.getMapSurface();
    SurfaceContainer surfaceContainer = mapboxCarMapSurface.getSurfaceContainer();
  }

  private MapboxCarMapObserver createObserver() {
    return new MapboxCarMapObserver() {

      @Override
      public void onVisibleAreaChanged(@NonNull Rect visibleArea, @NonNull EdgeInsets edgeInsets) {

      }

      @Override
      public void onStableAreaChanged(@NonNull Rect stableArea, @NonNull EdgeInsets edgeInsets) {

      }

      @Override
      public void onDetached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {

      }

      @Override
      public void onAttached(@NonNull MapboxCarMapSurface mapboxCarMapSurface) {

      }
    };
  }

  private void observers(MapboxCarMap mapboxCarMap) {
    MapboxCarMapObserver emptyObserver = new MapboxCarMapObserver() { };
    MapboxCarMapObserver observer = createObserver();
    mapboxCarMap.registerObserver(emptyObserver);
    mapboxCarMap.registerObserver(observer);
    mapboxCarMap.unregisterObserver(observer);
    mapboxCarMap.unregisterObserver(emptyObserver);
    mapboxCarMap.clearObservers();
  }

  private void gestures(MapboxCarMap mapboxCarMap) {
    MapboxCarMapGestureHandler emptyGestures = new MapboxCarMapGestureHandler() { };
    mapboxCarMap.setGestureHandler(emptyGestures);
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

  private void sessionDefaultInstaller(Session session) {
    MapboxCarMapObserver observer1 = createObserver();
    MapboxCarMapObserver observer2 = createObserver();
    MapboxCarMapEx.mapboxMapInstaller(session)
            .onCreated(observer1, observer2)
            .onStarted(observer1, observer2)
            .onResumed(observer1, observer2)
            .install();
  }

  private void sessionInstaller(Session session) {
    MapboxCarMapObserver observer1 = createObserver();
    MapboxCarMapObserver observer2 = createObserver();
    MapboxCarMapEx.mapboxMapInstaller(session)
            .onCreated(observer1, observer2)
            .onStarted(observer1, observer2)
            .onResumed(observer1, observer2)
            .install(MapInitOptions::new);
  }

  private void screenInstaller(Screen screen, MapboxCarMap mapboxCarMap) {
    MapboxCarMapObserver observer1 = createObserver();
    MapboxCarMapObserver observer2 = createObserver();
    MapboxCarMapEx.mapboxMapInstaller(screen, mapboxCarMap)
            .onCreated(observer1, observer2)
            .onStarted(observer1, observer2)
            .onResumed(observer1, observer2)
            .install();
  }
}