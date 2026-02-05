package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.indoorselector.indoorSelector
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import java.lang.ref.WeakReference

/**
 * Example showcasing the Indoor Manager API for controlling indoor map floor display.
 */
class IndoorExampleActivity : AppCompatActivity() {
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @OptIn(MapboxExperimental::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapInitOptions = MapInitOptions(
            context = this,
            cameraOptions = cameraOptions {
                center(Point.fromLngLat(LONGITUDE, LATITUDE))
                zoom(ZOOM)
                bearing(BEARING)
                pitch(PITCH)
            },
            styleUri = STYLE_URI,
            plugins = MapInitOptions.defaultPluginList + Plugin.Mapbox(Plugin.MAPBOX_INDOOR_SELECTOR_PLUGIN_ID)
        )

        setContentView(R.layout.activity_indoor_example)
        val container = findViewById<android.view.ViewGroup>(R.id.container)
        val mapView = MapView(this, mapInitOptions)
        container.addView(mapView, 0)

        val styleInput = findViewById<android.widget.EditText>(R.id.styleInput)
        findViewById<android.view.View>(R.id.loadStyleButton).setOnClickListener {
            val style = styleInput.text.toString()
            if (style.trim().startsWith("{")) {
                mapView.mapboxMap.loadStyleJson(style)
            } else {
                mapView.mapboxMap.loadStyle(style)
            }
        }

        mapView.scalebar.enabled = true

        mapView.indoorSelector.enabled = true
        mapView.indoorSelector.marginTop = 160f

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            mapView.location.apply {
                enabled = true
                puckBearingEnabled = true
                locationPuck = createDefault2DPuck(withBearing = true)

                addOnIndicatorPositionChangedListener { point ->
                    mapView.mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(point)
                            .zoom(18.0)
                            .build()
                    )
                }
            }
        }
    }

    companion object {
        private const val STYLE_URI = "mapbox://styles/mapbox/standard"
        private const val LATITUDE = 35.55025
        private const val LONGITUDE = 139.794131
        private const val ZOOM = 16.0
        private const val BEARING = 12.0
        private const val PITCH = 60.0
    }
}