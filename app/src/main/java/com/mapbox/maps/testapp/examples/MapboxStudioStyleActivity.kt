package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.testapp.databinding.ActivityStyleMapboxStudioBinding

/**
 * Example of displaying a custom Mapbox-hosted style, the default style uri is set in layout file.
 */
class MapboxStudioStyleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityStyleMapboxStudioBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}