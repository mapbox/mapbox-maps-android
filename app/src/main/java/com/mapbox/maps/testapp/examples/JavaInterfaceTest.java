package com.mapbox.maps.testapp.examples;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.division;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.literal;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.pi;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.rgba;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Surface;

import com.mapbox.android.gestures.AndroidGesturesManager;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.android.gestures.RotateGestureDetector;
import com.mapbox.android.gestures.ShoveGestureDetector;
import com.mapbox.android.gestures.StandardScaleGestureDetector;
import com.mapbox.bindgen.Value;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.CameraState;
import com.mapbox.maps.ExtensionUtils;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapOptions;
import com.mapbox.maps.MapSnapshotOptions;
import com.mapbox.maps.MapSurface;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.QueryFeatureStateCallback;
import com.mapbox.maps.ResourceOptions;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.SnapshotOverlayOptions;
import com.mapbox.maps.Snapshotter;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.StyleContract;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.types.Formatted;
import com.mapbox.maps.extension.style.types.FormattedSection;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.LocationPuck3D;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.gestures.OnRotateListener;
import com.mapbox.maps.plugin.gestures.OnScaleListener;
import com.mapbox.maps.plugin.gestures.OnShoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils;
import com.mapbox.maps.plugin.logo.LogoPlugin;
import com.mapbox.maps.plugin.logo.LogoUtils;
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin;
import com.mapbox.maps.plugin.overlay.MapOverlayUtils;
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin;
import com.mapbox.maps.plugin.scalebar.ScaleBarUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavaInterfaceTest {

    private void featureState(MapboxMap mapboxMap, QueryFeatureStateCallback callback, Value state, String sourceId, String featureId, String sourceLayerId) {
        mapboxMap.getFeatureState(sourceId, featureId, callback);
        mapboxMap.getFeatureState(sourceId, sourceLayerId, featureId, callback);
        mapboxMap.setFeatureState(sourceId, featureId, state);
        mapboxMap.setFeatureState(sourceId, sourceLayerId, featureId, state);
        mapboxMap.removeFeatureState(sourceId, featureId);
        mapboxMap.removeFeatureState(sourceId, sourceLayerId, featureId);
        mapboxMap.removeFeatureState(sourceId, sourceLayerId, featureId, "stateKey");
    }

    private void mapSurface(Context context, Surface surface, MapInitOptions mapInitOptions) {
        MapSurface mapSurface = new MapSurface(context, surface);
        mapSurface = new MapSurface(context, surface, mapInitOptions);
    }

    private void locationPuck(Drawable image, List<Float> floatList) {
        LocationPuck2D locationPuck2D = new LocationPuck2D();
        locationPuck2D = new LocationPuck2D(image);
        locationPuck2D = new LocationPuck2D(image, image);
        locationPuck2D = new LocationPuck2D(image, image, image);
        locationPuck2D = new LocationPuck2D(image, image, image, "scale");

        LocationPuck3D locationPuck3D = new LocationPuck3D("uri");
        locationPuck3D = new LocationPuck3D("uri", floatList);
        locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f);
        locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList);
        locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale");
        locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale", floatList);
        locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale", floatList, floatList);
    }

    private void mapboxMapOverLoad(MapView mapView, StyleContract.StyleExtension styleExtension, Style.OnStyleLoaded onStyleLoaded,
                                   OnMapLoadErrorListener onMapLoadErrorListener) {
        final MapboxMap mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS);
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, onStyleLoaded);
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, onStyleLoaded, onMapLoadErrorListener);
        mapboxMap.loadStyleJson("json");
        mapboxMap.loadStyleJson("json", onStyleLoaded);
        mapboxMap.loadStyleJson("json", onStyleLoaded, onMapLoadErrorListener);
        mapboxMap.loadStyle(styleExtension);
        mapboxMap.loadStyle(styleExtension, onStyleLoaded);
        mapboxMap.loadStyle(styleExtension, onStyleLoaded, onMapLoadErrorListener);
    }

    private void mapInitOptionsOverloads(Context context,
                                         ResourceOptions resourceOptions,
                                         MapOptions mapOptions, List<Plugin> plugins,
                                         CameraOptions cameraOptions,
                                         AttributeSet attrs) {
        MapInitOptions mapInitOptions = new MapInitOptions(context);
        mapInitOptions = new MapInitOptions(context, resourceOptions);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins, cameraOptions);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins, cameraOptions, false);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins, cameraOptions, false, Style.MAPBOX_STREETS);
        mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins, cameraOptions, false, Style.MAPBOX_STREETS, attrs);
    }

    private void snapshotter(Context context, MapSnapshotOptions options) {
        SnapshotOverlayOptions overlayOptions = new SnapshotOverlayOptions();
        overlayOptions = new SnapshotOverlayOptions(false);
        overlayOptions = new SnapshotOverlayOptions(false, false);
        Snapshotter snapshotter = new Snapshotter(context, options);
        snapshotter = new Snapshotter(context, options, overlayOptions);

    }

    private void cameraState(CameraState state, ScreenCoordinate screenCoordinate) {
        CameraOptions options = ExtensionUtils.toCameraOptions(state);
        options = ExtensionUtils.toCameraOptions(state, screenCoordinate);
    }

    private void addSymbolLayer(Style style) {
        Formatted formatted = new Formatted();
        formatted.add(new FormattedSection("test", 0.7, new ArrayList<String>(), "#1000"));
        SymbolLayer symbolLayer = new SymbolLayer("test", "test")
                .iconAnchor(IconAnchor.BOTTOM)
                .textField(formatted)
                .iconOpacity(literal(0.9));
        symbolLayer.bindTo(style);

        Expression expression = division(
                literal(2.0),
                pi());
        symbolLayer.iconOpacity(expression);
        symbolLayer.iconColor(rgba(255.0, 0.0, 0.0, 1.0));
    }

    private void cameraAnimations(MapView mapView) {
        final MapboxMap mapboxMap = mapView.getMapboxMap();
        final CameraOptions cameraOptions = new CameraOptions.Builder().build();
        final MapAnimationOptions mapAnimationOptions = new MapAnimationOptions.Builder().build();

        // plugin itself
        final CameraAnimationsPlugin plugin = CameraAnimationsUtils.getCamera(mapView);
        plugin.easeTo(cameraOptions, mapAnimationOptions);
        plugin.flyTo(cameraOptions, mapAnimationOptions);
        plugin.moveBy(new ScreenCoordinate(0.0, 1.1), mapAnimationOptions);
        plugin.pitchBy(1, mapAnimationOptions);
        plugin.scaleBy(1, new ScreenCoordinate(0.0, 1.1), mapAnimationOptions);
        plugin.rotateBy(new ScreenCoordinate(0.0, 1.1), new ScreenCoordinate(1.1, 0.0), mapAnimationOptions);

        // animation utils
        CameraAnimationsUtils.easeTo(mapboxMap, cameraOptions, mapAnimationOptions);
        CameraAnimationsUtils.flyTo(mapboxMap, cameraOptions, mapAnimationOptions);
        CameraAnimationsUtils.moveBy(mapboxMap, new ScreenCoordinate(0.0, 1.1), mapAnimationOptions);
        CameraAnimationsUtils.pitchBy(mapboxMap, 1, mapAnimationOptions);
        CameraAnimationsUtils.scaleBy(mapboxMap, 1, new ScreenCoordinate(0.0, 1.1), mapAnimationOptions);
        CameraAnimationsUtils.rotateBy(mapboxMap, new ScreenCoordinate(0.0, 1.1), new ScreenCoordinate(1.1, 0.0), mapAnimationOptions);
    }

    private void gesture(MapView mapView) {
        final MapboxMap mapboxMap = mapView.getMapboxMap();
        final GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(mapView);
        GesturesUtils.addOnFlingListener(mapboxMap, () -> {
        });
        GesturesUtils.removeOnFlingListener(mapboxMap, () -> {
        });
        GesturesUtils.addOnMapClickListener(mapboxMap, point -> false);
        GesturesUtils.removeOnMapClickListener(mapboxMap, point -> false);
        GesturesUtils.addOnMapLongClickListener(mapboxMap, point -> false);
        GesturesUtils.removeOnMapLongClickListener(mapboxMap, point -> false);
        OnMoveListener onMoveListener = new OnMoveListener() {
            @Override
            public void onMoveBegin(@NotNull MoveGestureDetector detector) {

            }

            @Override
            public boolean onMove(@NotNull MoveGestureDetector detector) {
                return false;
            }

            @Override
            public void onMoveEnd(@NotNull MoveGestureDetector detector) {

            }
        };
        GesturesUtils.addOnMoveListener(mapboxMap, onMoveListener);
        GesturesUtils.removeOnMoveListener(mapboxMap, onMoveListener);
        OnRotateListener onRotateListener = new OnRotateListener() {
            @Override
            public void onRotateBegin(@NotNull RotateGestureDetector detector) {

            }

            @Override
            public void onRotate(@NotNull RotateGestureDetector detector) {

            }

            @Override
            public void onRotateEnd(@NotNull RotateGestureDetector detector) {

            }
        };
        GesturesUtils.addOnRotateListener(mapboxMap, onRotateListener);
        GesturesUtils.removeOnRotateListener(mapboxMap, onRotateListener);
        OnScaleListener onScaleListener = new OnScaleListener() {
            @Override
            public void onScaleBegin(@NotNull StandardScaleGestureDetector detector) {

            }

            @Override
            public void onScale(@NotNull StandardScaleGestureDetector detector) {

            }

            @Override
            public void onScaleEnd(@NotNull StandardScaleGestureDetector detector) {

            }
        };
        GesturesUtils.addOnScaleListener(mapboxMap, onScaleListener);
        GesturesUtils.removeOnScaleListener(mapboxMap, onScaleListener);
        OnShoveListener onShoveListener = new OnShoveListener() {
            @Override
            public void onShoveBegin(@NotNull ShoveGestureDetector detector) {

            }

            @Override
            public void onShove(@NotNull ShoveGestureDetector detector) {

            }

            @Override
            public void onShoveEnd(@NotNull ShoveGestureDetector detector) {

            }
        };
        GesturesUtils.addOnShoveListener(mapboxMap, onShoveListener);
        GesturesUtils.removeOnShoveListener(mapboxMap, onShoveListener);
        AndroidGesturesManager gesturesManager = GesturesUtils.getGesturesManager(mapboxMap);
        GesturesUtils.setGesturesManager(mapboxMap, gesturesManager, false, false);
        GesturesUtils.getGesturesSettings(mapboxMap);
    }

    private void locationComponent(MapView mapView) {
        LocationComponentPlugin locationComponent = LocationComponentUtils.getLocationComponent(mapView);
    }

    private void logo(MapView mapView) {
        LogoPlugin logo = LogoUtils.getLogo(mapView);
    }

    private void overlay(MapView mapView) {
        MapOverlayPlugin overlay = MapOverlayUtils.getOverlay(mapView);
    }

    private void scaleBar(MapView mapView) {
        ScaleBarPlugin scaleBar = ScaleBarUtils.getScaleBar(mapView);
    }
}