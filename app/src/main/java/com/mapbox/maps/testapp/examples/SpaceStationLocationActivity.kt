package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.model.IssModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Display the space station's real-time location
 */
class SpaceStationLocationActivity : AppCompatActivity() {

  private val handler = Handler(Looper.getMainLooper())
  private var runnable: (() -> Unit) = {}
  private var call: Call<IssModel>? = null
  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.SATELLITE_STREETS
    ) {
      initSpaceStationSymbolLayer(it)
      callApi()
      showHintToast()
    }

    mapboxMap.setCamera(CameraOptions.Builder().zoom(2.0).build())
  }

  private fun showHintToast() = Toast.makeText(
    this@SpaceStationLocationActivity,
    R.string.space_station_toast,
    Toast.LENGTH_LONG
  ).show()

  private fun callApi() {

    // Build our client, The API we are using is very basic only returning a handful of
    // information, mainly, the current latitude and longitude of the International Space Station.
    val client = Retrofit.Builder()
      .baseUrl("http://api.open-notify.org/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    val service = client.create(IssApiService::class.java)

    // A handler is needed to called the API every x amount of seconds.
    runnable = {
      call = service.loadLocation()
      call?.enqueue(object : Callback<IssModel?> {

        override fun onResponse(
          call: Call<IssModel?>,
          response: Response<IssModel?>
        ) {

          // We only need the latitude and longitude from the API.
          val latitude = response.body()?.issPosition?.latitude
          val longitude = response.body()?.issPosition?.longitude
          if (latitude != null && longitude != null) {
            updateMarkerPosition(longitude = longitude, latitude = latitude)
          } else {
            Logger.w(TAG, "Wrong response position")
          }
        }

        override fun onFailure(
          call: Call<IssModel?>,
          throwable: Throwable
        ) {
          // If retrofit fails or the API was unreachable, an error will be called.
          // to check if throwable is null, then give a custom message.
          if (throwable.message == null) {
            Logger.e(TAG, "Http connection failed")
          } else {
            Logger.e(TAG, "onFailure: ${throwable.message}")
          }
        }
      })

      // Schedule the next execution time for this runnable.
      handler.postDelayed(runnable, apiCallTime.toLong())
    }

    // The first time this runs we don't need a delay so we immediately post.
    handler.post(runnable)
  }

  private fun initSpaceStationSymbolLayer(style: Style) {
    style.addImage(
      "space-station-icon-id",
      BitmapFactory.decodeResource(
        this.resources, R.drawable.iss
      )
    )

    style.addSource(
      geoJsonSource("source-id") {
        feature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
      }
    )

    style.addLayer(
      symbolLayer("layer-id", "source-id") {
        iconImage("space-station-icon-id")
        iconIgnorePlacement(true)
        iconAllowOverlap(true)
        iconSize(.7)
      }
    )
  }

  private fun updateMarkerPosition(longitude: Double, latitude: Double) {
    // This method is where we update the marker position once we have new coordinates. First we
    // check if this is the first time we are executing this handler, the best way to do this is
    // check if marker is null;

    val geoPoint = Point.fromLngLat(longitude, latitude)

    mapboxMap.getStyle {
      val spaceStationSource = it.getSource("source-id") as? GeoJsonSource
      spaceStationSource?.featureCollection(
        FeatureCollection.fromFeature(
          Feature.fromGeometry(
            geoPoint
          )
        )
      )
    }

    // Lastly, animate the camera to the new position so the user
    // wont have to search for the marker and then return.
    mapboxMap.flyTo(
      CameraOptions.Builder().center(geoPoint).build(),
      mapAnimationOptions { duration(300) }
    )
  }

  // Interface used for Retrofit.
  fun interface IssApiService {
    @GET("iss-now")
    fun loadLocation(): Call<IssModel>?
  }

  override fun onStart() {
    super.onStart()
    // When the user returns to the activity we want to resume the API calling.
    handler.post(runnable)
  }

  override fun onStop() {
    super.onStop()
    // When the user leaves the activity, there is no need in calling the API since the map
    // isn't in view.
    handler.removeCallbacksAndMessages(null)
    call?.cancel()
  }

  companion object {
    private const val TAG = "SpaceStationActivity"

    // apiCallTime is the time interval when we call the API in milliseconds, by default this is set
    // to 2000 and you should only increase the value, reducing the interval will only cause server
    // traffic, the latitude and longitude values aren't updated that frequently.
    private const val apiCallTime = 2000
  }
}