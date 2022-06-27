package com.mapbox.maps.testapp.examples.markersandcallouts

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.testapp.R
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode

abstract class BaseViewAnnotationActivity : AppCompatActivity() {

  protected abstract fun getViewAnnotationManager(): ViewAnnotationManager

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_view_annotation, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_view_annotation_fixed_delay -> {
        getViewAnnotationManager().setViewAnnotationUpdateMode(ViewAnnotationUpdateMode.MAP_FIXED_DELAY)
        true
      }
      R.id.action_view_annotation_map_synchronized -> {
        getViewAnnotationManager().setViewAnnotationUpdateMode(ViewAnnotationUpdateMode.MAP_SYNCHRONIZED)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}