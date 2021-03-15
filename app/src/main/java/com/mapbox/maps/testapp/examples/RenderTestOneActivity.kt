package com.mapbox.maps.testapp.examples

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_render_one.*

class RenderTestOneActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_render_one)

    val mapFragment = MapFragment()
    mapFragment.getMapAsync {
      it.loadStyleUri(Style.SATELLITE)
    }
    supportFragmentManager.beginTransaction().apply {
      add(R.id.container, mapFragment, "TAG1")
    }.commit()

    button.setOnClickListener {
      startActivity(Intent(this, RenderTestTwoActivity::class.java))
    }
  }
}