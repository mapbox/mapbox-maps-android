package com.mapbox.maps.testapp.examples.java;

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
import com.mapbox.maps.extension.style.StyleExtension;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.generated.CircleLayerKt;
import com.mapbox.maps.extension.style.layers.generated.RasterLayerKt;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayerKt;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSourceKt;
import com.mapbox.maps.extension.style.sources.generated.ImageSourceKt;
import com.mapbox.maps.extension.style.types.Formatted;
import com.mapbox.maps.plugin.gestures.GesturesPluginImplKt;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.testapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.concat;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.get;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.gt;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.literal;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.rgb;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.subtract;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.toNumber;
import static java.text.DateFormat.getDateTimeInstance;

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
    private static final String IMAGE_SOURCE_ID = "imag";
    private static final String CIRCLE_LAYER_ID = "earthquakeCircle";
    private static final String SYMBOL_LAYER_ID = "earthquakeText";
    private static final String RASTER_LAYER_ID = "raster";
    private static final String MAG_KEY = "mag";
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
        setContentView(R.layout.activity_simple_map);
        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyle(createStyle(), null, null);
        GesturesPluginImplKt.addOnMapClickListener(mapboxMap, this);
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
        return StyleExtension.style(Style.TRAFFIC_DAY, builder -> {
            builder.unaryPlus(
                    ImageSourceKt.imageSource(IMAGE_SOURCE_ID, imageBuilder -> {
                        imageBuilder.url(IMAGE_URL).coordinates(POINT_LIST);
                        return null;
                    }));
            builder.unaryPlus(
                    GeoJsonSourceKt.geoJsonSource(SOURCE_ID, geoJsonSourceBuilder -> {
                        geoJsonSourceBuilder.url(GEOJSON_URL).cluster(false);
                        return null;
                    }));
            builder.unaryPlus(
                    CircleLayerKt.circleLayer(CIRCLE_LAYER_ID, SOURCE_ID, circleLayerDsl -> {
                        circleLayerDsl.circleRadius(get(MAG_KEY));
                        circleLayerDsl.circleColor(rgb(255.0, 0.0, 0.0));
                        circleLayerDsl.circleOpacity(0.3);
                        circleLayerDsl.circleStrokeColor(Color.WHITE);
                        return null;
                    }));
            builder.layerAtPosition(SymbolLayerKt.symbolLayer(SYMBOL_LAYER_ID, SOURCE_ID, symbolLayerDsl -> {
                symbolLayerDsl.filter(gt(get(MAG_KEY), literal(5.0)));
                symbolLayerDsl.textField(
                        new Expression.FormatBuilder()
                                .formatSection(concat(literal(MAG_KEY), Expression.toString(get(MAG_KEY))))
                                .build());
                symbolLayerDsl.textFont(TEXT_FONT);
                symbolLayerDsl.textColor(Color.BLACK);
                symbolLayerDsl.textHaloColor(Color.WHITE);
                symbolLayerDsl.textHaloWidth(1.0);
                symbolLayerDsl.textAnchor(TextAnchor.TOP);
                symbolLayerDsl.textOffset(new ArrayList<Double>() {
                    {
                        add(0.0);
                        add(1.0);
                    }
                });
                symbolLayerDsl.textSize(10.0);
                symbolLayerDsl.textIgnorePlacement(false);
                symbolLayerDsl.symbolSortKey(subtract(toNumber(get(MAG_KEY))));
                return null;
            }), CIRCLE_LAYER_ID);
            builder.unaryPlus(RasterLayerKt.rasterLayer(RASTER_LAYER_ID, IMAGE_SOURCE_ID, rasterLayerDsl -> {
                rasterLayerDsl.rasterOpacity(0.8);
                return null;
            }));
            return null;
        });
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
