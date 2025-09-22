package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityEmptyFabBinding
import com.mapbox.maps.testapp.examples.fragment.MapFragment

class FragmentBackStackActivity : AppCompatActivity() {

  private lateinit var mapFragment: MapFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityEmptyFabBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (savedInstanceState == null) {
      mapFragment = MapFragment()
      mapFragment.getMapAsync {
        initMap(it)
      }
      supportFragmentManager.beginTransaction().apply {
        add(R.id.container, mapFragment, FRAGMENT_TAG)
      }.commit()
    } else {
      supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)?.also { fragment ->
        if (fragment is MapFragment) {
          fragment.getMapAsync {
            initMap(it)
          }
        }
      }
    }

    binding.displayOnSecondDisplayButton.setOnClickListener { handleClick() }
    binding.fragmentButton.setOnClickListener { addNewFragment() }
  }

  private fun addNewFragment() {
    supportFragmentManager.beginTransaction().apply {
      replace(
        R.id.container,
        MapFragment().also {
          it.getMapAsync { mapboxMap -> initMap(mapboxMap) }
        }
      )
      addToBackStack("map_new_fragment_${System.currentTimeMillis()}")
    }.commit()
  }

  private fun initMap(mapboxMap: MapboxMap) {
    mapboxMap.loadStyle(Style.SATELLITE)
  }

  private fun handleClick() {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.container, EmptyFragment.newInstance())
      addToBackStack("map_empty_fragment")
    }.commit()
  }

  class EmptyFragment : androidx.fragment.app.Fragment() {

    companion object {
      fun newInstance(): EmptyFragment {
        return EmptyFragment()
      }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View {
      val textView = TextView(inflater.context)
      textView.text = "This is an empty Fragment"
      return textView
    }
  }

  companion object {
    private const val FRAGMENT_TAG = "map_fragment"
  }
}