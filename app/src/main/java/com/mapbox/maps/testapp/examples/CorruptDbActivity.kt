package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.insertOrThrow
import java.util.*
import kotlin.coroutines.CoroutineContext

private const val DATABASE_INSERT_DELAY_MS = 20L

private val LOG_TAG = CorruptDbActivity::class.java.simpleName

class CorruptDbActivity : AppCompatActivity(), CoroutineScope {

  override val coroutineContext: CoroutineContext = Dispatchers.Main

  private val mapInstance: MapboxMap by lazy { findViewById<MapView>(R.id.mapView).getMapboxMap() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)

    mapInstance.loadStyleUri(Style.SATELLITE)

    insertTestDataIntoDatabase()
  }

  private fun insertTestDataIntoDatabase() {
    launch(Dispatchers.IO) {
      while (true) {
        Log.d(LOG_TAG, "Inserting into database")
        dbHelper.use {
          insertOrThrow(TEST_TABLE_NAME, COLUMN_1 to UUID.randomUUID().toString())
        }
        delay(DATABASE_INSERT_DELAY_MS)
      }
    }
  }
}