package com.mapbox.maps.testapp.examples.java;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.QueriedFeature;
import com.mapbox.maps.RenderedQueryOptions;
import com.mapbox.maps.ScreenBox;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.StyleContract;
import com.mapbox.maps.extension.style.StyleExtensionImpl;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.image.ImageNinePatchExtensionImpl;
import com.mapbox.maps.extension.style.image.ImageUtils;
import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
import com.mapbox.maps.extension.style.layers.generated.RasterLayer;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.extension.style.sources.generated.ImageSource;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.testapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.concat;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.get;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.gt;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.literal;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.rgb;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.subtract;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.toNumber;
import static java.text.DateFormat.getDateTimeInstance;

/**
 * Example showcasing usage of creating style with java codes.
 */
public class DSLStylingJavaActivity extends AppCompatActivity implements OnMapClickListener {
    public static final ArrayList<ArrayList<Double>> POINT_LIST = new ArrayList<ArrayList<Double>>() {
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
    private static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png";
    private static final String GEOJSON_URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private static final String SOURCE_ID = "earthquakes";
    private static final String IMAGE_SOURCE_ID = "image";
    private static final String CIRCLE_LAYER_ID = "earthquakeCircle";
    private static final String SYMBOL_LAYER_ID = "earthquakeText";
    private static final String RASTER_LAYER_ID = "raster";
    private static final String IMAGE_9_PATCH_ID = "image9patch";
    private static final Expression MAG_KEY = literal("mag");
    private static final List<String> QUERY_LIST = new ArrayList() {
        {
            add(CIRCLE_LAYER_ID);
            add(SYMBOL_LAYER_ID);
        }
    };
    public static final ArrayList<String> TEXT_FONT = new ArrayList<String>() {
        {
            add("Open Sans Regular");
            add("Arial Unicode MS Regular");
        }
    };
    private MapboxMap mapboxMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);
        mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyle(createStyle(), style -> {
            Bitmap bitmap = BitmapFactory.decodeResource(DSLStylingJavaActivity.this.getResources(), R.drawable.blue_round_nine);
            ImageUtils.addImage9Patch(style, new ImageNinePatchExtensionImpl.Builder(IMAGE_9_PATCH_ID, bitmap).build());
            ImageUtils.image9Patch(IMAGE_9_PATCH_ID, bitmap, builder -> {
                builder.build();
                return null;
            });
        }, null);
        GesturesUtils.addOnMapClickListener(mapboxMap, this);
    }

    @Override
    public boolean onMapClick(@NotNull Point point) {
        ScreenCoordinate clicked = mapboxMap.pixelForCoordinate(point);
        mapboxMap.queryRenderedFeatures(
                new ScreenBox(
                        new ScreenCoordinate(clicked.getX() - 50, clicked.getY() - 50),
                        new ScreenCoordinate(clicked.getX() + 50, clicked.getY() + 50)
                ),
                new RenderedQueryOptions(QUERY_LIST, literal(true)), features -> {
                    List<QueriedFeature> featureList = features.getValue();
                    if (featureList != null && !featureList.isEmpty()) {
                        Number time = featureList.get(0).getFeature().getNumberProperty("time");
                        Toast.makeText(DSLStylingJavaActivity.this, getDateTime(time.longValue()), Toast.LENGTH_SHORT).show();
                    }
                });
        return true;
    }

    private String getDateTime(Long time) {
        String dateTime = null;
        try {
            DateFormat sdf = getDateTimeInstance();
            Date netDate = new Date(time);
            dateTime = sdf.format(netDate);
        } catch (Exception e) {
            e.toString();
        }
        return dateTime;
    }

    private StyleContract.StyleExtension createStyle() {
        StyleExtensionImpl.Builder builder = new StyleExtensionImpl.Builder(Style.TRAFFIC_DAY);

        // Add a image source
        builder.addSource(new ImageSource.Builder(IMAGE_URL).coordinates(POINT_LIST).build());
        // Add the earthquake source
        builder.addSource(new GeoJsonSource.Builder(SOURCE_ID, source -> {
        }).url(GEOJSON_URL).cluster(false).build());

        // Add circleLayer which will show the earthquake locations
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, SOURCE_ID);
        circleLayer.circleRadius(get(MAG_KEY));
        circleLayer.circleColor(rgb(255.0, 0.0, 0.0));
        circleLayer.circleOpacity(0.3);
        circleLayer.circleStrokeColor(Color.WHITE);
        builder.addLayer(circleLayer);

        // Add symbolLayer show earthquakes those greater than mag 5.0
        SymbolLayer symbolLayer = new SymbolLayer(SYMBOL_LAYER_ID, SOURCE_ID);
        symbolLayer.filter(gt(get(MAG_KEY), literal(5.0)));
        Expression concat = concat(MAG_KEY, Expression.toString(get(MAG_KEY)));
        symbolLayer.textField(concat);
        symbolLayer.textFont(TEXT_FONT);
        symbolLayer.textColor(Color.BLACK);
        symbolLayer.textHaloColor(Color.WHITE);
        symbolLayer.textHaloWidth(1.0);
        symbolLayer.textAnchor(TextAnchor.TOP);
        symbolLayer.textOffset(new ArrayList<Double>() {{
            add(0.0);
            add(1.0);
        }});
        symbolLayer.textSize(10.0);
        symbolLayer.textIgnorePlacement(false);
        symbolLayer.symbolSortKey(subtract(toNumber(get(MAG_KEY))));
        builder.addLayerAtPosition(builder.layerAtPosition(symbolLayer, CIRCLE_LAYER_ID));

        // Add a rasterLayer show the image
        RasterLayer rasterLayer = new RasterLayer(RASTER_LAYER_ID, IMAGE_SOURCE_ID);
        rasterLayer.rasterOpacity(0.8);
        builder.addLayer(rasterLayer);
        return builder.build();
    }
}