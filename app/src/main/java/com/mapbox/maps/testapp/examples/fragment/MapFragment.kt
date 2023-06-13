package com.mapbox.maps.testapp.examples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap

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
      // Use TextureView as render surface for the MapView, for smooth transitions following holding views, e.g. in a ViewPager.
      MapInitOptions(inflater.context, textureView = true)
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
}