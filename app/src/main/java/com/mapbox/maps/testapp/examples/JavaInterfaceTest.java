package com.mapbox.maps.testapp.examples;

import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.types.Formatted;
import com.mapbox.maps.extension.style.types.FormattedSection;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;

import java.util.ArrayList;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.*;

public class JavaInterfaceTest {

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
}