package com.mapbox.maps.testapp.examples

import android.app.Presentation
import android.content.Context
import android.content.DialogInterface
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.maps.MapView
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivitySecondaryDisplayPresentationBinding
import com.mapbox.maps.testapp.databinding.ItemDisplayInfoBinding

private const val TAG = "SecondaryDisplayPresent"

private const val DISPLAY_INFO_TAG = "DisplayInfo"

class SecondaryDisplayPresentationActivity : AppCompatActivity() {
  private var currentPresentation: Presentation? = null
  private lateinit var binding: ActivitySecondaryDisplayPresentationBinding
  private val onDismissListener = DialogInterface.OnDismissListener { dialog ->
    if (dialog == currentPresentation) {
      Log.i(TAG, "Presentation was dismissed.")
      currentPresentation = null
      updateDisplaysInfo()
    }
  }

  override fun onResume() {
    super.onResume()
    binding = ActivitySecondaryDisplayPresentationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    updateDisplaysInfo()
  }

  private fun updateDisplaysInfo() {
    clearDisplaysInfo()
    val displayManager = getSystemService(DisplayManager::class.java)
    val thisActivityDisplay = windowManager.defaultDisplay
    val displays = displayManager.displays
    val secondaryDisplays = displays.filterNot { it.displayId == thisActivityDisplay.displayId }
    if (secondaryDisplays.isEmpty()) {
      binding.displayNone.visibility = View.VISIBLE
    } else {
      secondaryDisplays.forEach { display ->
        Log.d(TAG, "updatePresentation: Found display $display")
        val displayInfoBinding =
          ItemDisplayInfoBinding.inflate(
            LayoutInflater.from(binding.root.context),
            binding.root,
            true
          )
        displayInfoBinding.root.tag = DISPLAY_INFO_TAG
        displayInfoBinding.displayInfo.text = "${display.name} [${display.displayId}]"
        displayInfoBinding.displayBt.isEnabled = true
        displayInfoBinding.displayBt.setOnClickListener {
          updatePresentation(display)
          updateDisplaysInfo()
        }
        currentPresentation?.let {
          if (it.display == display) {
            displayInfoBinding.displayBt.isEnabled = false
          }
        }
      }
    }
  }

  private fun clearDisplaysInfo() {
    binding.root.children.filter { it.tag == DISPLAY_INFO_TAG }
      .toList() // Accumulate all the views to remove
      .forEach {
        Log.d(TAG, "clearDisplaysInfo: removing $it")
        binding.root.removeView(it)
      }
  }

  private fun updatePresentation(display: Display) {
    currentPresentation?.let { presentation ->
      // Dismiss the current presentation if the display has changed.
      if (presentation.display !== display) {
        currentPresentation = null
        Log.i(TAG, "Dismissing old presentation. A new display will be used.")
        presentation.dismiss()
      }
    }

    // Show a new presentation if needed.
    if (currentPresentation == null) {
      Log.i(TAG, "Showing presentation on display: $display")
      val presentation = DemoPresentation(this, display)
      presentation.setOnDismissListener(onDismissListener)
      currentPresentation = try {
        presentation.show()
        presentation
      } catch (ex: WindowManager.InvalidDisplayException) {
        Log.w(TAG, "Couldn't show presentation!  Display was removed in " + "the meantime.", ex)
        null
      }
    }
  }

  /**
   * The presentation to show on the secondary display.
   *
   *
   * Note that this display may have different metrics from the display on which
   * the main activity is showing so we must be careful to use the presentation's
   * own [Context] whenever we load resources.
   *
   */
  private class DemoPresentation(context: Context?, display: Display?) :
    Presentation(context, display), LifecycleOwner {
    private lateinit var lifecycleRegistry: LifecycleRegistry
    override fun onCreate(savedInstanceState: Bundle?) {
      // Be sure to call the super class.
      super.onCreate(savedInstanceState)

      lifecycleRegistry = LifecycleRegistry(this)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
      context.setTheme(R.style.AppTheme)

      // Notice that we are getting the context of the presentation.
      val mapView = MapView(context)
      setContentView(mapView)
    }

    public override fun onStart() {
      super.onStart()
      // Only after we show does the dialog window actually return a decor view.
      val decorView: View = window!!.decorView
      ViewTreeLifecycleOwner.set(decorView, this)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
//      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onStop() {
//      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      super.onStop()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
  }
}