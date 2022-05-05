package com.mapbox.maps.testapp.examples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.logD

class MapFragment : Fragment() {

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var onMapReady: (MapboxMap) -> Unit

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    mapView = MapView(
      inflater.context,
      MapInitOptions(inflater.context)
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

  fun getMapView(): MapView {
    return mapView
  }

  override fun onStart() {
    logD("lifecycle", "fragment - ${mapView.hashCode()} - onStart")
    super.onStart()
  }

  override fun onStop() {
    logD("lifecycle", "fragment - ${mapView.hashCode()} - onStop")
    super.onStop()
  }
  override fun onDestroyView() {
    logD("lifecycle", "fragment - ${mapView.hashCode()} - onDestroyView")
    super.onDestroyView()
  }

  override fun onDestroy() {
    logD("lifecycle", "fragment - ${mapView.hashCode()} - onDestroy")
    super.onDestroy()
  }
}