package com.mapbox.maps.testapp.style;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.StyleContract;
import com.mapbox.maps.extension.style.layers.LayerKt;
import com.mapbox.maps.extension.style.light.generated.LightKt;
import com.mapbox.maps.extension.style.sources.SourceKt;
import com.mapbox.maps.extension.style.terrain.generated.TerrainKt;
import com.mapbox.maps.extension.testapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BaseStyleJavaTest {
    private MapView mapView;
    private MapboxMap mapboxMap;
    protected Style style;

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(AppCompatActivity.class);

    @Before
    public void before() throws TimeoutException {
        CountDownLatch latch = new CountDownLatch(1);
        rule.getScenario().onActivity(activity -> activity.runOnUiThread(() -> {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            mapView = new MapView(context);
            mapView.setId(R.id.mapView);
            activity.setContentView(mapView);

            mapboxMap = mapView.getMapboxMap();
            mapboxMap.loadStyleUri("mapbox://styles/mapbox/empty-v9",
                    style -> {
                        BaseStyleJavaTest.this.style = style;
                        latch.countDown();
                    }
            );

            mapView.onStart();
        }));
        try {
            if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
                throw new TimeoutException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws TimeoutException {
        CountDownLatch latch = new CountDownLatch(1);
        rule.getScenario().onActivity(activity ->
                activity.runOnUiThread(() -> {
                    mapView.onStop();
                    mapView.onDestroy();
                    latch.countDown();
                }));
        try {
            if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
                throw new TimeoutException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void setupLayer(StyleContract.StyleLayerExtension layer) {
        LayerKt.addLayer(style, layer);
    }

    protected void setupLight(StyleContract.StyleLightExtension light) {
        LightKt.addLight(style, light);
    }

    protected void setupTerrain(StyleContract.StyleTerrainExtension terrain) {
        TerrainKt.addTerrain(style, terrain);
    }

    protected void setupSource(StyleContract.StyleSourceExtension source) {
        SourceKt.addSource(style, source);
    }
}