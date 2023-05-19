package com.mapbox.maps.testapp.examples;

import static com.mapbox.maps.extension.localization.StyleInterfaceExtensionKt.localizeLabels;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.division;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.literal;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.pi;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.rgba;
import static com.mapbox.maps.extension.style.image.ImageUtils.image9Patch;
import static com.mapbox.maps.extension.style.image.NinePatchUtils.addImage9Patch;
import static com.mapbox.maps.extension.style.layers.LayerUtils.addPersistentLayer;
import static com.mapbox.maps.extension.style.terrain.generated.TerrainKt.terrain;
import static com.mapbox.maps.plugin.animation.CameraAnimationsUtils.easeTo;
import static com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt.createCircleAnnotationManager;
import static com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt.createPointAnnotationManager;
import static com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManagerKt.createPolygonAnnotationManager;
import static com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManagerKt.createPolylineAnnotationManager;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Surface;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.mapbox.android.gestures.AndroidGesturesManager;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.android.gestures.RotateGestureDetector;
import com.mapbox.android.gestures.ShoveGestureDetector;
import com.mapbox.android.gestures.StandardScaleGestureDetector;
import com.mapbox.bindgen.Value;
import com.mapbox.geojson.Feature;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.CameraState;
import com.mapbox.maps.ExtensionUtils;
import com.mapbox.maps.FeatureStateOperationCallback;
import com.mapbox.maps.ImageHolder;
import com.mapbox.maps.LayerPosition;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapLoadingErrorCallback;
import com.mapbox.maps.MapOptions;
import com.mapbox.maps.MapSnapshotOptions;
import com.mapbox.maps.MapSurface;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.QueryFeatureExtensionCallback;
import com.mapbox.maps.QueryFeatureStateCallback;
import com.mapbox.maps.ResourceOptions;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.SnapshotOverlayOptions;
import com.mapbox.maps.Snapshotter;
import com.mapbox.maps.Style;
import com.mapbox.maps.TransitionOptions;
import com.mapbox.maps.extension.style.StyleContract;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.expressions.types.FormatSection;
import com.mapbox.maps.extension.style.layers.Layer;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.types.Formatted;
import com.mapbox.maps.extension.style.types.FormattedSection;
import com.mapbox.maps.module.MapTelemetry;
import com.mapbox.maps.plugin.LocationPuck;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.LocationPuck3D;
import com.mapbox.maps.plugin.ScrollMode;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions;
import com.mapbox.maps.plugin.annotation.ClusterOptions;
import com.mapbox.maps.plugin.attribution.AttributionParserConfig;
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings;
import com.mapbox.maps.plugin.compass.generated.CompassSettings;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.gestures.OnRotateListener;
import com.mapbox.maps.plugin.gestures.OnScaleListener;
import com.mapbox.maps.plugin.gestures.OnShoveListener;
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils;
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings;
import com.mapbox.maps.plugin.logo.LogoPlugin;
import com.mapbox.maps.plugin.logo.LogoUtils;
import com.mapbox.maps.plugin.logo.generated.LogoSettings;
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin;
import com.mapbox.maps.plugin.overlay.MapOverlayUtils;
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin;
import com.mapbox.maps.plugin.scalebar.ScaleBarUtils;
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings;
import com.mapbox.maps.plugin.viewport.ViewportPlugin;
import com.mapbox.maps.plugin.viewport.ViewportStatus;
import com.mapbox.maps.plugin.viewport.ViewportStatusObserver;
import com.mapbox.maps.plugin.viewport.ViewportUtils;
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions;
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kotlin.Pair;

public class JavaInterfaceChecker {
  private void getClusterLeaves(MapboxMap mapboxMap, String sourcdId, Feature cluster, QueryFeatureExtensionCallback callback) {
    mapboxMap.getGeoJsonClusterLeaves(sourcdId, cluster, callback);
    mapboxMap.getGeoJsonClusterLeaves(sourcdId, cluster, 1, callback);
    mapboxMap.getGeoJsonClusterLeaves(sourcdId, cluster, 1, 2, callback);
  }

  private void scaleBarSettings() {
    ScaleBarSettings scaleBarSettings = new ScaleBarSettings.Builder()
            .setEnabled(true)
            .setPosition(1)
            .setMarginLeft(1f)
            .setMarginTop(1f)
            .setMarginRight(1f)
            .setMarginBottom(1f)
            .setPrimaryColor(Color.BLACK)
            .setSecondaryColor(Color.BLACK)
            .setTextColor(Color.BLACK)
            .setBorderWidth(1f)
            .setHeight(1f)
            .setTextBarMargin(1f)
            .setTextBorderWidth(1f)
            .setTextSize(1f)
            .setIsMetricUnits(true)
            .setRefreshInterval(1L)
            .setShowTextBorder(true)
            .setRatio(1f)
            .setUseContinuousRendering(true)
            .build();
  }

  private void logoSettings() {
    LogoSettings logoSettings = new LogoSettings.Builder()
            .setEnabled(true)
            .setPosition(1)
            .setMarginLeft(1f)
            .setMarginTop(1f)
            .setMarginRight(1f)
            .setMarginBottom(1f)
            .build();
  }

  private void locationComponentSettings(LocationPuck locationPuck) {
    LocationComponentSettings locationComponentSettings = new LocationComponentSettings.Builder(locationPuck)
            .setEnabled(true)
            .setPulsingColor(Color.BLACK)
            .setPulsingMaxRadius(1f)
            .setAccuracyRingColor(Color.BLACK)
            .setAccuracyRingBorderColor(Color.BLACK)
            .setLayerAbove("id1")
            .setLayerBelow("id2")
            .build();
  }

  private void locationComponent(Context context, MapView mapView) {
    LocationComponentPlugin locationComponent = LocationComponentUtils.getLocationComponent(mapView);
    locationComponent.setLocationPuck(LocationComponentUtils.createDefault2DPuck(locationComponent, context));
    locationComponent.setLocationPuck(LocationComponentUtils.createDefault2DPuck(locationComponent, context, true));
  }

  private void gesturesSettings(ScrollMode scrollMode, ScreenCoordinate screenCoordinate) {
    GesturesSettings gesturesSettings = new GesturesSettings.Builder()
            .setDoubleTapToZoomInEnabled(true)
            .setDoubleTouchToZoomOutEnabled(true)
            .setFocalPoint(screenCoordinate)
            .setIncreasePinchToZoomThresholdWhenRotating(true)
            .setIncreaseRotateThresholdWhenPinchingToZoom(true)
            .setPinchScrollEnabled(true)
            .setPinchToZoomDecelerationEnabled(true)
            .setPinchToZoomEnabled(true)
            .setPitchEnabled(true)
            .setQuickZoomEnabled(true)
            .setRotateDecelerationEnabled(true)
            .setRotateEnabled(true)
            .setScrollDecelerationEnabled(true)
            .setScrollEnabled(true)
            .setScrollMode(scrollMode)
            .setSimultaneousRotateAndPinchToZoomEnabled(true)
            .setZoomAnimationAmount(1f)
            .build();

  }

  private void compassSettings(Drawable drawable) {
    CompassSettings compassSettings = new CompassSettings.Builder()
            .setEnabled(true)
            .setMarginBottom(1f)
            .setClickable(true)
            .setFadeWhenFacingNorth(true)
            .setMarginLeft(1f)
            .setImage(ImageHolder.from(((BitmapDrawable)drawable).getBitmap()))
            .setMarginRight(1f)
            .setMarginTop(1f)
            .setPosition(1)
            .setOpacity(1f)
            .setVisibility(true)
            .setRotation(1f)
            .build();
  }

  private void attributionSettings() {
    AttributionSettings attributionSettings = new AttributionSettings.Builder()
            .setEnabled(true)
            .setClickable(true)
            .setMarginBottom(1f)
            .setMarginLeft(1f)
            .setIconColor(Color.BLACK)
            .setMarginRight(1f)
            .setMarginTop(1f)
            .setPosition(1)
            .setEnabled(true)
            .build();
  }

  private void annotationManager(AnnotationPlugin annotationPlugin, MapView mapView, AnnotationConfig annotationConfig) {
    createPolylineAnnotationManager(annotationPlugin, annotationConfig);
    createPolygonAnnotationManager(annotationPlugin, annotationConfig);
    createPointAnnotationManager(annotationPlugin, annotationConfig);
    createCircleAnnotationManager(annotationPlugin, annotationConfig);
  }

  private void attribution() {
    AttributionParserConfig attributionParserConfig = new AttributionParserConfig();
    attributionParserConfig = new AttributionParserConfig(true);
    attributionParserConfig = new AttributionParserConfig(true, true);
    attributionParserConfig = new AttributionParserConfig(true, true, true);
    attributionParserConfig = new AttributionParserConfig(true, true, true, true);
  }

  private void annotation(String belowLayerId, String layerId, String sourceId, Value expression, List<Pair<Integer, Integer>> colorLevels, HashMap<String, Object> clusterProperties) {
    ClusterOptions clusterOptions = new ClusterOptions();
    clusterOptions = new ClusterOptions(true);
    clusterOptions = new ClusterOptions(true, 1L);
    clusterOptions = new ClusterOptions(true, 1L, expression);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression, 1.0);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression, 1.0, expression);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression, 1.0, expression, 1L);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression, 1.0, expression, 1L, colorLevels);
    clusterOptions = new ClusterOptions(true, 1L, expression, 1.0, expression, Color.BLACK, expression, 1.0, expression, 1L, colorLevels, clusterProperties);

    AnnotationSourceOptions options = new AnnotationSourceOptions();
    options = new AnnotationSourceOptions(1L);
    options = new AnnotationSourceOptions(1L, 1L);
    options = new AnnotationSourceOptions(1L, 1L, true);
    options = new AnnotationSourceOptions(1L, 1L, true, 1.0);
    options = new AnnotationSourceOptions(1L, 1L, true, 1.0, clusterOptions);

    AnnotationConfig config = new AnnotationConfig();
    config = new AnnotationConfig(belowLayerId);
    config = new AnnotationConfig(belowLayerId, layerId);
    config = new AnnotationConfig(belowLayerId, layerId, sourceId);
    config = new AnnotationConfig(belowLayerId, layerId, sourceId, options);
  }

  private void cameraTest(MapboxMap mapboxMap, CameraOptions cameraOptions, MapAnimationOptions mapAnimationOptions, ScreenCoordinate screenCoordinate) {
    CameraAnimationsUtils.easeTo(mapboxMap, cameraOptions);
    CameraAnimationsUtils.easeTo(mapboxMap, cameraOptions, mapAnimationOptions);
    CameraAnimationsUtils.flyTo(mapboxMap, cameraOptions);
    CameraAnimationsUtils.flyTo(mapboxMap, cameraOptions, mapAnimationOptions);
    CameraAnimationsUtils.moveBy(mapboxMap, screenCoordinate, mapAnimationOptions);
    CameraAnimationsUtils.moveBy(mapboxMap, screenCoordinate);
    CameraAnimationsUtils.pitchBy(mapboxMap, 1.0, mapAnimationOptions);
    CameraAnimationsUtils.pitchBy(mapboxMap, 1.0);
    CameraAnimationsUtils.rotateBy(mapboxMap, screenCoordinate, screenCoordinate);
    CameraAnimationsUtils.rotateBy(mapboxMap, screenCoordinate, screenCoordinate, mapAnimationOptions);
  }

  private void terrainTest() {
    terrain("id");
    terrain("id", receiver -> null);
  }

  private void addLayer(Style style, Layer layer, LayerPosition position) {
    addPersistentLayer(style, layer);
    addPersistentLayer(style, layer, position);
  }

  private void image9PatchTest(Style style, Bitmap bitmap) {
    image9Patch("id", bitmap);
    image9Patch("id", bitmap, builder -> null);
    addImage9Patch(style, "id", bitmap);
    addImage9Patch(style, "id", bitmap, 1.0f);
    addImage9Patch(style, "id", bitmap, 1.0f, true);
  }

  private void formatSection(Expression expression) {
    FormatSection formatSection = new FormatSection(expression);
    formatSection = new FormatSection(expression, expression);
    formatSection = new FormatSection(expression, expression, expression);
    formatSection = new FormatSection(expression, expression, expression, expression);
  }

  private void localization(Style style, Locale locale, List<String> layerId) {
    localizeLabels(style, locale);
    localizeLabels(style, locale, layerId);
  }

  private void featureState(
      MapboxMap mapboxMap,
      QueryFeatureStateCallback getCallback,
      FeatureStateOperationCallback setCallback,
      FeatureStateOperationCallback removeCallback,
      FeatureStateOperationCallback resetCallback,
      Value state,
      String sourceId,
      String featureId,
      String sourceLayerId
  ) {
    mapboxMap.getFeatureState(sourceId, featureId, getCallback);
    mapboxMap.getFeatureState(sourceId, sourceLayerId, featureId, getCallback);
    mapboxMap.setFeatureState(sourceId, featureId, state, setCallback);
    mapboxMap.setFeatureState(sourceId, sourceLayerId, featureId, state, setCallback);
    mapboxMap.removeFeatureState(sourceId, featureId, removeCallback);
    mapboxMap.removeFeatureState(sourceId, sourceLayerId, featureId, removeCallback);
    mapboxMap.removeFeatureState(sourceId, sourceLayerId, featureId, "stateKey", removeCallback);
    mapboxMap.resetFeatureStates(sourceId, resetCallback);
    mapboxMap.resetFeatureStates(sourceId, sourceLayerId, resetCallback);
  }

  private void mapSurface(Context context, Surface surface, MapInitOptions mapInitOptions) {
    MapSurface mapSurface = new MapSurface(context, surface);
    mapSurface = new MapSurface(context, surface, mapInitOptions);
  }

  private void locationPuck(@DrawableRes int imageId, List<Float> floatList) {
    LocationPuck2D locationPuck2D = new LocationPuck2D();
    locationPuck2D = new LocationPuck2D(ImageHolder.from(imageId));
    locationPuck2D = new LocationPuck2D(ImageHolder.from(imageId), ImageHolder.from(imageId));
    locationPuck2D = new LocationPuck2D(ImageHolder.from(imageId), ImageHolder.from(imageId), ImageHolder.from(imageId));
    locationPuck2D = new LocationPuck2D(ImageHolder.from(imageId), ImageHolder.from(imageId), ImageHolder.from(imageId), "scale");

    LocationPuck3D locationPuck3D = new LocationPuck3D("uri");
    locationPuck3D = new LocationPuck3D("uri", floatList);
    locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f);
    locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList);
    locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale");
    locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale", floatList);
    locationPuck3D = new LocationPuck3D("uri", floatList, 1.0f, floatList, "scale", floatList, floatList);
  }

  private void mapboxMapOverLoad(MapView mapView, StyleContract.StyleExtension styleExtension, Style.OnStyleLoaded onStyleLoaded,
                                 MapLoadingErrorCallback onMapLoadErrorListener) {
    final MapboxMap mapboxMap = mapView.getMapboxMap();
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS);
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, onStyleLoaded);
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, onStyleLoaded, onMapLoadErrorListener);
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, new TransitionOptions.Builder().build(), onStyleLoaded, onMapLoadErrorListener);
    mapboxMap.loadStyleJson("json");
    mapboxMap.loadStyleJson("json", onStyleLoaded);
    mapboxMap.loadStyleJson("json", onStyleLoaded, onMapLoadErrorListener);
    mapboxMap.loadStyleJson("json", new TransitionOptions.Builder().build(), onStyleLoaded, onMapLoadErrorListener);
    mapboxMap.loadStyle(styleExtension);
    mapboxMap.loadStyle(styleExtension, onStyleLoaded);
    mapboxMap.loadStyle(styleExtension, onStyleLoaded, onMapLoadErrorListener);
    mapboxMap.loadStyle(styleExtension, new TransitionOptions.Builder().build(), onStyleLoaded, onMapLoadErrorListener);
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
    mapInitOptions = new MapInitOptions(context, resourceOptions, mapOptions, plugins, cameraOptions, false, Style.MAPBOX_STREETS, attrs, 4);
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
    Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {

      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    };
    // plugin itself
    final CameraAnimationsPlugin plugin = CameraAnimationsUtils.getCamera(mapView);
    plugin.easeTo(cameraOptions, mapAnimationOptions, animationListener);
    plugin.flyTo(cameraOptions, mapAnimationOptions, animationListener);
    plugin.moveBy(new ScreenCoordinate(0.0, 1.1), mapAnimationOptions, animationListener);
    plugin.pitchBy(1, mapAnimationOptions, animationListener);
    plugin.scaleBy(1, new ScreenCoordinate(0.0, 1.1), mapAnimationOptions, animationListener);
    plugin.rotateBy(new ScreenCoordinate(0.0, 1.1), new ScreenCoordinate(1.1, 0.0), mapAnimationOptions, animationListener);

    // animation utils
    easeTo(mapboxMap, cameraOptions, mapAnimationOptions);
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

  private void viewport(MapView mapView) {
    ViewportPlugin viewport = ViewportUtils.getViewport(mapView);
    viewport.addStatusObserver((from, to, reason) -> {
      if (reason == ViewportStatusChangeReason.USER_INTERACTION) {
        viewport.transitionTo(
            viewport.makeFollowPuckViewportState(new FollowPuckViewportStateOptions.Builder().build()),
            viewport.makeImmediateViewportTransition(),
            isFinished -> {
              // no-ops
            });
      }
    });
  }

  private class CustomTelemetry implements MapTelemetry {
    @Override
    public void onAppUserTurnstileEvent() {

    }

    @Override
    public void setUserTelemetryRequestState(boolean enabled) {

    }

    @Override
    public void disableTelemetrySession() {

    }

    @Override
    public void onPerformanceEvent(@Nullable Bundle data) {

    }
  }
}