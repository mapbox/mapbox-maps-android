package com.mapbox.maps.testapp.examples.markersandcallouts;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.*;
import static java.lang.Double.parseDouble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolZOrder;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.plugin.annotation.Annotation;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationsUtils;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener;
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationDragListener;
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationInteractionListener;
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationLongClickListener;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.testapp.R;
import com.mapbox.maps.testapp.databinding.ActivityAnnotationJavaBinding;
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils;
import com.mapbox.maps.testapp.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example showing how to add Symbol annotations
 */
public class PointAnnotationJavaActivity extends AppCompatActivity {
    private static final String ID_ICON_AIRPORT = "airport";
    private static final String MAKI_ICON_CAR = "car-15";
    private static final String MAKI_ICON_CAFE = "cafe-15";
    private static final double AIRPORT_LONGITUDE = 0.381457;
    private static final double AIRPORT_LATITUDE = 6.687337;
    private static final double NEARBY_LONGITUDE = 0.367099;
    private static final double NEARBY_LATITUDE = 6.526384;
    private Bitmap blueBitmap = null;

    private AnnotationPlugin annotationPlugin = null;
    private PointAnnotationManager pointAnnotationManager = null;
    private CircleAnnotationManager circleAnnotationManager = null;
    private PointAnnotation pointAnnotation = null;
    private CircleAnnotation circleAnnotation = null;
    private ArrayList<ValueAnimator> animators = new ArrayList<>();
    private int styleIndex = 0;
    private int slotIndex = 0;
    private boolean consumeClickEvent = false;
    private String getNextStyle() {
        int index = styleIndex++ % AnnotationUtils.INSTANCE.getSTYLES().length;
        return AnnotationUtils.INSTANCE.getSTYLES()[index];
    }
    private String getNextSlot() {
        int index = slotIndex++ % AnnotationUtils.INSTANCE.getSLOTS().length;
        return AnnotationUtils.INSTANCE.getSLOTS()[index];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAnnotationJavaBinding binding = ActivityAnnotationJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mapView.getMapboxMap().setCamera(
                new CameraOptions.Builder()
                        .center(
                                Point.fromLngLat(
                                        AIRPORT_LONGITUDE,
                                        AIRPORT_LATITUDE
                                )
                        )
                        .pitch(45.0)
                        .zoom(10.5)
                        .bearing(-17.6)
                        .build()
        );
        binding.mapView.getMapboxMap().loadStyle(getNextStyle(), (style -> {
            annotationPlugin = AnnotationsUtils.getAnnotations(binding.mapView);;

            circleAnnotationManager = CircleAnnotationManagerKt.createCircleAnnotationManager(annotationPlugin, null);

            CircleAnnotationOptions circleAnnotationOptions = new CircleAnnotationOptions()
                    .withPoint(Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE))
                    .withCircleColor(Color.YELLOW)
                    .withCircleRadius(12.0)
                    .withDraggable(false);
            circleAnnotation = circleAnnotationManager.create(circleAnnotationOptions);

            pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, null);
            pointAnnotationManager.setTextFont(
                    Arrays.asList("Arial Unicode MS Bold", "Open Sans Regular")
            );
            pointAnnotationManager.addDragListener(new OnPointAnnotationDragListener() {
                @Override
                public void onAnnotationDragStarted(@NonNull Annotation<?> annotation) {}

                @Override
                public void onAnnotationDrag(@NonNull Annotation<?> annotation) {
                    if (circleAnnotationManager != null && circleAnnotation != null) {
                        circleAnnotation.setPoint(((PointAnnotation) annotation).getPoint());
                        circleAnnotationManager.update(circleAnnotation);
                    }
                }

                @Override
                public void onAnnotationDragFinished(@NonNull Annotation<?> annotation) {}
            });

            pointAnnotationManager.addClickListener(new OnPointAnnotationClickListener() {
                @Override
                public boolean onAnnotationClick(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "Click1: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return consumeClickEvent;
                }
            });

            pointAnnotationManager.addClickListener(new OnPointAnnotationClickListener() {
                @Override
                public boolean onAnnotationClick(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "Click2: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return consumeClickEvent;
                }
            });

            pointAnnotationManager.addLongClickListener(new OnPointAnnotationLongClickListener() {
                @Override
                public boolean onAnnotationLongClick(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "LongClick1: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return consumeClickEvent;
                }
            });

            pointAnnotationManager.addLongClickListener(new OnPointAnnotationLongClickListener() {
                @Override
                public boolean onAnnotationLongClick(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "LongClick2: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return consumeClickEvent;
                }
            });

            pointAnnotationManager.addInteractionListener(new OnPointAnnotationInteractionListener() {
                @Override
                public void onSelectAnnotation(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "onSelectAnnotation: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                }

                @Override
                public void onDeselectAnnotation(@NonNull PointAnnotation annotation) {
                    Toast.makeText(
                            PointAnnotationJavaActivity.this,
                            "onDeselectAnnotation: " + annotation.getId(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });

            Bitmap airplaneModeBitmap = BitmapUtils.INSTANCE.bitmapFromDrawableRes(
                    this,
                    R.drawable.ic_airplanemode_active_black_24dp
            );
            if (airplaneModeBitmap != null) {
                PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE))
                        .withIconImage(airplaneModeBitmap)
                        .withTextField(ID_ICON_AIRPORT)
                        .withTextOffset(Arrays.asList(0.0, -2.0))
                        .withTextColor(Color.RED)
                        .withIconSize(1.3)
                        .withIconOffset(Arrays.asList(0.0, -2.0))
                        .withSymbolSortKey(10.0)
                        .withDraggable(true);

                pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions);

                // add random symbols across the globe
                ArrayList<PointAnnotationOptions> pointAnnotationOptionsList = new ArrayList();
                for (int i = 0; i < 5; i += 1) {
                    pointAnnotationOptionsList.add(
                            new PointAnnotationOptions()
                                    .withPoint(AnnotationUtils.INSTANCE.createRandomPoint())
                                    .withIconImage(airplaneModeBitmap)
                                    .withDraggable(true)
                    );
                }
                pointAnnotationManager.create(pointAnnotationOptionsList);
            }

            Bitmap mapboxUserIconBitmap = BitmapUtils.INSTANCE.bitmapFromDrawableRes(
                    this,
                    R.drawable.mapbox_user_icon
            );
            if (mapboxUserIconBitmap != null) {
                blueBitmap = mapboxUserIconBitmap;
                PointAnnotationOptions nearbyOptions = new PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(NEARBY_LONGITUDE, NEARBY_LATITUDE))
                        .withIconImage(mapboxUserIconBitmap)
                        .withIconSize(2.5)
                        .withTextField(ID_ICON_AIRPORT)
                        .withSymbolSortKey(5.0)
                        .withDraggable(true);
                pointAnnotationManager.create(nearbyOptions);
            }
            // add random symbols across the globe
            ArrayList<PointAnnotationOptions> pointAnnotationOptionsList = new ArrayList();
            for (int i = 0; i < 20; i += 1) {
                pointAnnotationOptionsList.add(
                        new PointAnnotationOptions()
                                .withPoint(AnnotationUtils.INSTANCE.createRandomPoint())
                                .withIconImage(MAKI_ICON_CAR)
                                .withDraggable(true)
                );
            }
            pointAnnotationManager.create(pointAnnotationOptionsList);

            String json = AnnotationUtils.INSTANCE.loadStringFromAssets(this, "annotations.json");
            if (json != null) {
                pointAnnotationManager.create(FeatureCollection.fromJson(json));
            }

            GesturesUtils.addOnMapClickListener(binding.mapView.getMapboxMap(), ( point -> {
                Toast.makeText(this, "OnMapClick", Toast.LENGTH_SHORT).show();
                return true;
            }));
        }));

        binding.deleteAll.setOnClickListener(( view -> {
            if (pointAnnotationManager != null) {
                annotationPlugin.removeAnnotationManager(pointAnnotationManager);
                pointAnnotationManager = null;
            }
        }));

        binding.changeStyle.setOnClickListener(( view -> {
            binding.mapView.getMapboxMap().loadStyle(getNextStyle());
        }));

        binding.changeSlot.setOnClickListener(( view -> {
            if (pointAnnotationManager != null) {
                String slot = getNextSlot();
                Toast.makeText(this, slot, Toast.LENGTH_SHORT).show();
                pointAnnotationManager.setSlot(slot);
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_symbol, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (pointAnnotationManager == null || pointAnnotation == null) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_action_draggable:
                for (PointAnnotation annotation : pointAnnotationManager.getAnnotations()) {
                    annotation.setDraggable(!annotation.isDraggable());
                }
                break;
            case R.id.menu_action_consume_click:
                consumeClickEvent = !consumeClickEvent;
                String message = "Click listener will consume event: " + consumeClickEvent;
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_action_filter:
                String idKey = pointAnnotationManager.getAnnotationIdKey();
                Expression expression = eq(
                        toNumber(get(idKey)),
                        literal(parseDouble(pointAnnotation.getId()))
                );
                Expression filter = pointAnnotationManager.getLayerFilter();
                if (filter != null && filter == expression) {
                    pointAnnotationManager.setLayerFilter(
                            not(eq(toNumber(get(idKey)), literal(-1)))
                    );
                } else {
                    pointAnnotationManager.setLayerFilter(expression);
                }
                break;
            case R.id.menu_action_icon:
                pointAnnotation.setIconImage(MAKI_ICON_CAFE);
                break;
            case R.id.menu_action_bitmap_blue:
                pointAnnotation.setIconImageBitmap(blueBitmap);
                break;
            case R.id.menu_action_rotation:
                pointAnnotation.setIconRotate(45.0);
                break;
            case R.id.menu_action_text:
                pointAnnotation.setTextField("Hello world!");
                break;
            case R.id.menu_action_anchor:
                pointAnnotation.setIconAnchor(IconAnchor.BOTTOM);
                break;
            case R.id.menu_action_opacity:
                pointAnnotation.setIconOpacity(0.5);
                break;
            case R.id.menu_action_offset:
                pointAnnotation.setIconOffset(Arrays.asList(10.0, 20.0));
                break;
            case R.id.menu_action_text_anchor:
                pointAnnotation.setTextAnchor(TextAnchor.TOP);
                break;
            case R.id.menu_action_text_color:
                pointAnnotation.setTextColorInt(Color.WHITE);
                break;
            case R.id.menu_action_text_size:
                pointAnnotation.setTextSize(22.0);
                break;
            case R.id.menu_action_z_index:
                pointAnnotation.setSymbolSortKey(0.0);
                break;
            case R.id.menu_action_halo:
                pointAnnotation.setIconHaloWidth(5.0);
                pointAnnotation.setIconHaloColorInt(Color.RED);
                pointAnnotation.setIconHaloBlur(1.0);
                break;
            case R.id.menu_action_animate:
                resetSymbol();
                easeSymbol(pointAnnotation, Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE));
                return true;
            case R.id.z_order_auto:
                item.setChecked(true);
                pointAnnotationManager.setSymbolZOrder(SymbolZOrder.AUTO);
                break;
            case R.id.z_order_viewport_y:
                item.setChecked(true);
                pointAnnotationManager.setSymbolZOrder(SymbolZOrder.VIEWPORT_Y);
                break;
            case R.id.z_order_source:
                item.setChecked(true);
                pointAnnotationManager.setSymbolZOrder(SymbolZOrder.SOURCE);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        pointAnnotationManager.update(pointAnnotation);
        return true;
    }

    private void resetSymbol() {
        if (pointAnnotationManager != null && pointAnnotation != null) {
            pointAnnotation.setIconRotate(0.0);
            pointAnnotation.setGeometry(Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE));
            pointAnnotationManager.update(pointAnnotation);
        }
    }

    private void easeSymbol(PointAnnotation pointAnnotation, Point location, Double rotation) {
        Point originalPosition = pointAnnotation.getPoint();
        Double originalRotation = pointAnnotation.getIconRotate();
        if (originalPosition == location || originalRotation == rotation) {
            return;
        }
        ValueAnimator moveSymbol = ValueAnimator.ofFloat(0f, 1f).setDuration(5000);
        moveSymbol.setInterpolator(new LinearInterpolator());
        moveSymbol.addUpdateListener(( valueAnimator -> {
            if (pointAnnotationManager != null) {
                if (!pointAnnotationManager.getAnnotations().contains(pointAnnotation)) {
                    return;
                }
                Float fraction = (Float) valueAnimator.getAnimatedValue();
                if (originalPosition != location) {
                    Double lat = (location.latitude() - originalPosition.latitude())* fraction + originalPosition.latitude();
                    Double lng = (location.longitude() - originalPosition.longitude()) * fraction + originalPosition.longitude();
                    pointAnnotation.setGeometry(Point.fromLngLat(lng, lat));
                }
                if (originalRotation != null && originalRotation != rotation) {
                    pointAnnotation.setIconRotate((rotation - originalRotation) * fraction + originalRotation);
                }
                pointAnnotationManager.update(pointAnnotation);
            }
        }));
        moveSymbol.start();
        animators.add(moveSymbol);
    }
    private void easeSymbol(PointAnnotation pointAnnotation, Point location) {
        easeSymbol(pointAnnotation, location, 180.0);
    }
}