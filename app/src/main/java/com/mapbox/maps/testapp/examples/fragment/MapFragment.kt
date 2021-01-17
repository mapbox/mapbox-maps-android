package com.mapbox.maps.testapp.examples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxMapOptions

class MapFragment : Fragment() {

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var onMapReady: (MapboxMap) -> Unit

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mapView = MapView(
      inflater.context,
      MapboxMapOptions(inflater.context)
    )
    return mapView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mapboxMap = mapView.getMapboxMap()
    if (::onMapReady.isInitialized) {
      onMapReady.invoke(mapboxMap)
    }
  }

  fun getMapAsync(callback: (MapboxMap) -> Unit) = if (::mapboxMap.isInitialized) {
    callback.invoke(mapboxMap)
  } else this.onMapReady = callback

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mapView.onDestroy()
  }

//  // Used for test
//  override fun onDestroy() {
//    super.onDestroy()
//    mapView.onDestroy()
//  }

  fun getMapView(): MapView? {
    return mapView
  }
}