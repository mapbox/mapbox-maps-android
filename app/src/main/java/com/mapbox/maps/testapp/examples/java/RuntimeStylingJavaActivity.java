package com.mapbox.maps.testapp.examples.java;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;

import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.None;
import com.mapbox.bindgen.Value;
import com.mapbox.common.Logger;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.maps.Image;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.image.ImageUtils;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.FillExtrusionLayer;
import com.mapbox.maps.extension.style.layers.generated.FillLayer;
import com.mapbox.maps.extension.style.layers.generated.RasterLayer;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility;
import com.mapbox.maps.extension.style.light.generated.Light;
import com.mapbox.maps.extension.style.light.generated.LightUtils;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.extension.style.sources.generated.ImageSource;
import com.mapbox.maps.extension.style.sources.generated.VectorSource;
import com.mapbox.maps.testapp.R;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.eq;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.get;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.image;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.literal;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.subtract;

/**
 * Example showcasing usage of creating runtime style with java codes.
 */
public class RuntimeStylingJavaActivity extends AppCompatActivity {
    private static String TAG = "RuntimeStylingJavaActivity";
    private static final String POLYGON_SOURCE_ID = "polygon";
    private static final String FILL_EXTRUSION_LAYER_ID = "fillextrusion";
    private static final String RASTER_LAYER_ID = "raster";
    private static final String IMAGE_SOURCE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png";
    private static final String GEOJSON_SOURCE_ID = "geojsonSource";
    private static final String SYMBOL_LAYER_ID = "symbolLayer";
    private static final String IMAGE_ID = "image";
    private static final String IMAGE_SOURCE_ID = "imageSource";
    private static final ArrayList<String> TEXT_FONT = new ArrayList<String>() {
        {
            add("Open Sans Regular");
            add("Arial Unicode MS Regular");
        }
    };
    private static final ArrayList<ArrayList<Double>> POINT_LIST = new ArrayList<ArrayList<Double>>() {
        {
            add(new ArrayList<Double>() {
                {
                    add(-35.859375);
                    add(58.44773280389084);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-16.171875);
                    add(58.44773280389084);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-16.171875);
                    add(54.7246201949245);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-35.859375);
                    add(54.7246201949245);
                }
            });
        }
    };
    private static String SYMBOL_SOURCE_FEATURECOLLECTION = "{\n" +
            "\"type\": \"FeatureCollection\",\n" +
            "\"features\": [\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "      \"count\": 0\n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Point\",\n" +
            "      \"coordinates\": [\n" +
            "-42.978515625,\n" +
            "22.024545601240337\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "      \"count\": 0\n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Point\",\n" +
            "      \"coordinates\": [\n" +
            "-29.355468750000004,\n" +
            "25.64152637306577\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "      \"count\": 1\n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Point\",\n" +
            "      \"coordinates\": [\n" +
            "-3.69140625,\n" +
            "-4.214943141390639\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "      \"count\": 1              \n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Point\",\n" +
            "      \"coordinates\": [\n" +
            "-27.861328125,\n" +
            "3.337953961416485\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "      \"count\": 1\n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Point\",\n" +
            "      \"coordinates\": [\n" +
            "-27.773437499999996,\n" +
            "-17.644022027872712\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "]\n" +
            "}";
    private static final String FILL_FEATURE_COLLECTION = "{\n" +
            "\"type\": \"FeatureCollection\",\n" +
            "\"features\": [\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {},\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Polygon\",\n" +
            "      \"coordinates\": [\n" +
            "        [\n" +
            "          [\n" +
            "            -366.85546875,\n" +
            "            18.145851771694467\n" +
            "          ],\n" +
            "          [\n" +
            "            -373.27148437499994,\n" +
            "            12.726084296948196\n" +
            "          ],\n" +
            "          [\n" +
            "            -364.39453125,\n" +
            "            6.577303118123887\n" +
            "          ],\n" +
            "          [\n" +
            "            -366.85546875,\n" +
            "            18.145851771694467\n" +
            "          ]\n" +
            "        ]\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "]\n" +
            "      }";
    private MapboxMap mapboxMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_map);
        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, this::setUpStyle);
    }

    private void setUpStyle(Style style) {
        addImage(style);
        addSymbolSource(style);
        addSymbolLayer(style);

        addFillSource(style);
        setFillLayer(style);
        addFillExtrusionLayer(style);
        addFillExtrusionLight(style);

        addImageSource(style);
        addRasterLayer(style);

        addLayerWithoutStyleExtension(style);

        final VectorSource source = (VectorSource) SourceUtils.getSource(style, "composite");
        Logger.e(TAG, "getSource: " + source);
    }

    private void addImage(Style style) {
        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.android_symbol);
        final Bitmap bitmap = DrawableKt.toBitmap(drawable, 64, 64, null);
        ImageUtils.addImage(style, delegate -> delegate.addImage(IMAGE_ID, bitmap));
    }

    private void addSymbolSource(Style style) {
        final FeatureCollection featureCollection = FeatureCollection.fromJson(SYMBOL_SOURCE_FEATURECOLLECTION);
        final GeoJsonSource geoJsonSource = new GeoJsonSource.Builder(GEOJSON_SOURCE_ID, source -> {
        }).featureCollection(featureCollection)
                .cluster(true)
                .prefetchZoomDelta(1)
                .build();
        Logger.i(TAG, geoJsonSource.toString());
        SourceUtils.addSource(style, geoJsonSource);
        Logger.i(TAG, "prefetchZoomDelta :" + geoJsonSource.getPrefetchZoomDelta());
    }

    private void addSymbolLayer(Style style) {
        final Expression textField = new Expression.FormatBuilder()
                .formatSection("London", formatSectionBuilder -> {
                    formatSectionBuilder.fontScale(1.0);
                    formatSectionBuilder.textFont(TEXT_FONT);
                    formatSectionBuilder.textColor(Color.RED);
                    return null;
                }).formatSection(image(literal("london-underground")), formatSectionBuilder -> {
                    formatSectionBuilder.fontScale(0.9);
                    return null;
                }).formatSection("underground", formatSectionBuilder -> {
                    formatSectionBuilder.fontScale(0.8);
                    formatSectionBuilder.textFont(TEXT_FONT);
                    formatSectionBuilder.textColor(Color.WHITE);
                    return null;
                }).build();

        SymbolLayer symbolLayer = new SymbolLayer(SYMBOL_LAYER_ID, GEOJSON_SOURCE_ID);
        symbolLayer.filter(eq(get("count"), literal(0)));
        symbolLayer.iconImage(literal(IMAGE_ID));
        symbolLayer.iconOpacity(subtract(literal(1.0), literal(0.6)));
        symbolLayer.textField(textField);
        symbolLayer.iconColor(Color.GREEN);
        symbolLayer.textAnchor(TextAnchor.CENTER);
        symbolLayer.iconAnchor(IconAnchor.BOTTOM);
        symbolLayer.textIgnorePlacement(false);
        symbolLayer.iconIgnorePlacement(false);
        LayerUtils.addLayer(style, symbolLayer);
        Logger.i(TAG, symbolLayer.getIconOpacityAsExpression().toString());
    }

    private void addFillSource(Style style) {
        final GeoJsonSource polygon = new GeoJsonSource.Builder(POLYGON_SOURCE_ID, source -> {
        }).featureCollection(FeatureCollection.fromJson(FILL_FEATURE_COLLECTION)).build();
        Logger.i(TAG, polygon.toString());
        SourceUtils.addSource(style, polygon);
    }

    private void setFillLayer(Style style) {
        final FillLayer fillLayer = (FillLayer) LayerUtils.getLayer(style, "water");
        final Expression.InterpolatorBuilder interpolateBuilder = new Expression.InterpolatorBuilder("interpolate");
        interpolateBuilder.exponential(builder -> {
            builder.literal(0.5);
            return null;
        });
        interpolateBuilder.zoom();
        interpolateBuilder.stop(builder -> {
            builder.literal(1.0);
            builder.color(Color.RED);
            return null;
        });
        interpolateBuilder.stop(builder -> {
            builder.literal(5.0);
            builder.color(Color.BLUE);
            return null;
        });
        interpolateBuilder.stop(builder -> {
            builder.literal(10.0);
            builder.color(Color.GREEN);
            return null;
        });
        fillLayer.fillColor(interpolateBuilder.build());
        fillLayer.visibility(Visibility.VISIBLE);
        Logger.i(TAG, fillLayer.getFillColorAsExpression().toString());
    }


    private void addFillExtrusionLayer(Style style) {
        final FillExtrusionLayer fillExtrusionLayer = new FillExtrusionLayer(FILL_EXTRUSION_LAYER_ID, POLYGON_SOURCE_ID);
        fillExtrusionLayer.fillExtrusionHeight(1000000.0);
        fillExtrusionLayer.fillExtrusionColor(Color.GRAY);
        fillExtrusionLayer.fillExtrusionOpacity(1.0);
        LayerUtils.addLayer(style, fillExtrusionLayer);
    }

    private void addFillExtrusionLight(Style style) {
        final Light light = new Light();
        light.anchor(Anchor.MAP);
        light.color(Color.YELLOW);
        light.position(10.0, 40.0, 50.0);

        LightUtils.setLight(style, light);
    }

    private void addImageSource(Style style) {
        final ImageSource imageSource = new ImageSource.Builder(IMAGE_SOURCE_ID).build();
        imageSource.url(IMAGE_SOURCE_URL);
        imageSource.coordinates(POINT_LIST);
        SourceUtils.addSource(style, imageSource);
    }

    private void addRasterLayer(Style style) {
        final RasterLayer raster = new RasterLayer(RASTER_LAYER_ID, IMAGE_SOURCE_ID);
        LayerUtils.addLayer(style, raster);
    }

    private void addLayerWithoutStyleExtension(Style style) {
        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.android_symbol);
        final Bitmap bitmap = DrawableKt.toBitmap(drawable, 64, 64, null);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        final Expected<String, None> expected = style.addStyleImage(
                IMAGE_ID,
                1f,
                new Image(64, 64, byteBuffer.array()),
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                null
        );
        if (expected.isError()) {
            Logger.e(TAG, expected.getError());
        }
        if (expected.isValue()) {
            Logger.d(TAG, expected.getValue().toString());
        }

        final HashMap<String, Value> sourceParams = new HashMap<>();
        sourceParams.put("type", new Value("geojson"));
        final HashMap<String, Value> data = new HashMap<>();
        data.put("type", new Value("Feature"));
        final HashMap<String, Value> geometry = new HashMap<>();
        geometry.put("type", new Value("Point"));
        geometry.put("coordinates", new Value(new ArrayList<Value>() {
            {
                add(new Value(24.9384));
                add(new Value(60.1699));
            }
        }));
        data.put("geometry", new Value(geometry));
        sourceParams.put("data", new Value(data));
        style.addStyleSource("source", new Value(sourceParams));

        final HashMap<String, Value> layerParams = new HashMap<>();
        layerParams.put("id", new Value("layer"));
        layerParams.put("type", new Value("symbol"));
        layerParams.put("source", new Value("source"));
        style.addStyleLayer(new Value(layerParams), null);
        style.setStyleLayerProperty("layer", "icon-image", new Value(IMAGE_ID));
        style.setStyleLayerProperty("layer", "icon-opacity", new Value(1.0));
        style.setStyleLayerProperty("layer", "icon-size", new Value(5.0));
        style.setStyleLayerProperty("layer", "icon-color", new Value("white"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
