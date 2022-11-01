package com.mapbox.maps.testapp.auto.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.mapbox.maps.testapp.auto.CarAppPreferences
import com.mapbox.maps.testapp.auto.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val switchButton: SwitchCompat = findViewById(R.id.switchButton)

    val carAppPreferences = CarAppPreferences(applicationContext)
    switchButton.isChecked = carAppPreferences.isOnClickEnabled()
    switchButton.setOnCheckedChangeListener { _, isChecked ->
      if (carAppPreferences.isOnClickEnabled() != isChecked) {
        Toast.makeText(
          this,
          "Custom setting changed, reconnect Android Auto to ensure a restart",
          Toast.LENGTH_LONG
        ).show()
        carAppPreferences.enableOnClick(isChecked)
      }
    }
  }
}