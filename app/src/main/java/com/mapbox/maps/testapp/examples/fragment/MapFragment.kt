package com.mapbox.maps.testapp.examples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.common.Logger
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
  ): View {
    Logger.e("FragmentTest", "fragment onCreateView, parent activity ${activity.hashCode()}")
    mapView = MapView(
      inflater.context,
      MapboxMapOptions(inflater.context)
    )
    return mapView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    Logger.e("FragmentTest", "fragment onViewCreated, parent activity ${activity.hashCode()}")
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
    Logger.e("FragmentTest", "fragment onStart, parent activity ${activity.hashCode()}")
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    Logger.e("FragmentTest", "fragment onStop, parent activity ${activity.hashCode()}")
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    Logger.e("FragmentTest", "fragment onLowMemory, parent activity ${activity.hashCode()}")
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroyView() {
    Logger.e("FragmentTest", "fragment onDestroyView, parent activity ${activity.hashCode()}")
    super.onDestroyView()
    mapView.onDestroy()
  }

//  // Used for test
//  override fun onDestroy() {
//    super.onDestroy()
//    mapView.onDestroy()
//  }

  fun getMapView(): MapView {
    return mapView
  }
}