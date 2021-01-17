package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.maps.CacheManager
import com.mapbox.maps.DATABASE_NAME
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_cache_manager.*

/**
 * Example of cache manipulation using Cache Manager API.
 */
class CacheManagementActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cache_manager)

    val cacheManager = CacheManager(
      ResourceOptions.Builder()
        .accessToken(getString(R.string.mapbox_access_token))
        .cachePath(filesDir.absolutePath + "/$DATABASE_NAME")
        .assetPath(filesDir.absolutePath)
        .cacheSize(50000L)
        .build()
    )

    clearButton.setOnClickListener {
      cacheManager.clearAmbientCache {
        setResult("Clear ambient cache", it)
      }
    }

    invalidateButton.setOnClickListener {
      cacheManager.invalidateAmbientCache {
        setResult("InvalidateAmbient cache", it)
      }
    }

    maximumCacheButton.setOnClickListener {
      cacheManager.setMaximumAmbientCacheSize(100000) {
        setResult("Set ambient cache size", it)
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun setResult(function: String, result: Expected<Void, String>) {
    if (result.isValue) {
      resultView.text = "$function: Success"
    } else {
      resultView.text = "$function: ${result.error}"
    }
  }
}