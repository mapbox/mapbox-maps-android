package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.databinding.ActivityViewpagerBinding
import com.mapbox.maps.testapp.examples.fragment.MapFragment

/**
 * Test activity showcasing using the Android SDK ViewPager API to show MapFragments.
 */
class ViewPagerActivity : AppCompatActivity() {

  private lateinit var binding: ActivityViewpagerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityViewpagerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.viewPager.adapter = MapFragmentAdapter(supportFragmentManager)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    val currentPosition = binding.viewPager.currentItem
    val offscreenLimit = binding.viewPager.offscreenPageLimit
    for (i in currentPosition - offscreenLimit..currentPosition + offscreenLimit) {
      if (i < 0 || i > binding.viewPager.adapter?.count ?: 0) {
        continue
      }
      val mapFragment = binding.viewPager.adapter?.instantiateItem(binding.viewPager, i) as MapFragment
      mapFragment.getMapAsync(i)
    }
  }

  internal class MapFragmentAdapter(
    fragmentManager: FragmentManager
  ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
      return NUM_ITEMS
    }

    override fun getItem(position: Int): Fragment {
      val fragment = MapFragment()
      fragment.getMapAsync(position)
      return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
      return "Page $position"
    }

    companion object {
      private const val NUM_ITEMS = 5
    }
  }
}

fun MapFragment.getMapAsync(index: Int) {
  this.getMapAsync {
    it.loadStyleUri(getStyleFromIndex(index))
  }
}

fun getStyleFromIndex(index: Int): String {
  return when (index) {
    0 -> Style.MAPBOX_STREETS
    1 -> Style.DARK
    2 -> Style.SATELLITE
    3 -> Style.LIGHT
    4 -> Style.TRAFFIC_NIGHT
    else -> Style.MAPBOX_STREETS
  }
}