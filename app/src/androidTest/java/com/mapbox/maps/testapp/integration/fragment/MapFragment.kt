package com.mapbox.maps.testapp.integration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

class MapFragment : Fragment() {

  private var viewCreated: OnViewCreated? = null
  lateinit var mapView: MapView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    mapView = MapView(inflater.context)
    return mapView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewCreated?.onViewCreated()
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  fun getViewAsync(onViewCreated: OnViewCreated) {
    this.viewCreated = onViewCreated
  }

  fun loadMap(idleListener: OnMapIdle) {
    val map = mapView.mapboxMap
    map.loadStyle(Style.MAPBOX_STREETS)
    map.subscribeMapIdle {
      idleListener.onIdle()
    }
  }

  fun interface OnViewCreated {
    fun onViewCreated()
  }

  fun interface OnMapIdle {
    fun onIdle()
  }
}