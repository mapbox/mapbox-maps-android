package com.mapbox.maps.testapp.auto.car;

import static com.mapbox.maps.extension.androidauto.MapboxCarUtilsKt.initMapSurface;

import androidx.car.app.Session;

import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapboxExperimental;
import com.mapbox.maps.extension.androidauto.MapSurfaceReadyCallback;
import com.mapbox.maps.extension.androidauto.OnMapScaleListener;
import com.mapbox.maps.extension.androidauto.OnMapScrollListener;

@MapboxExperimental
public class CarJavaInterfaceChecker {

    private void carSession(Session session, MapInitOptions mapInitOptions,
                            OnMapScrollListener scrollListener,
                            OnMapScaleListener scaleListener,
                            MapSurfaceReadyCallback mapSurfaceReadyCallback) {
        initMapSurface(session, mapSurfaceReadyCallback);
        initMapSurface(session, mapInitOptions, mapSurfaceReadyCallback);
        initMapSurface(session, mapInitOptions, scrollListener, mapSurfaceReadyCallback);
        initMapSurface(session, mapInitOptions, scrollListener, scaleListener, mapSurfaceReadyCallback);
    }
}