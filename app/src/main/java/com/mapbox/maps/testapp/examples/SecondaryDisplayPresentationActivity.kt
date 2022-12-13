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
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.maps.MapView
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivitySecondaryDisplayPresentationBinding
import com.mapbox.maps.testapp.databinding.ItemDisplayInfoBinding

/**
 * Example of displaying a map on a secondary display using [Presentation].
 *
 * To use this example, you either need to:
 * - attach a second display to your device
 * - have the simulated secondary display enabled in the developer settings
 * - in the emulator's extended controls, add a new secondary display
 */
class SecondaryDisplayPresentationActivity : AppCompatActivity() {
  private lateinit var displayManager: DisplayManager
  private var currentPresentation: Presentation? = null
  private lateinit var binding: ActivitySecondaryDisplayPresentationBinding
  private val onDismissListener = DialogInterface.OnDismissListener { dialog ->
    if (dialog == currentPresentation) {
      Log.i(TAG, "Presentation was dismissed.")
      currentPresentation = null
      updateDisplaysInfo()
    }
  }
  private val displayListener = object : DisplayManager.DisplayListener {
    override fun onDisplayAdded(displayId: Int) {
      Log.d(TAG, "Display #$displayId added.")
      updateDisplaysInfo()
    }

    override fun onDisplayRemoved(displayId: Int) {
      Log.d(TAG, "Display #$displayId removed.")
      updateDisplaysInfo()
    }

    override fun onDisplayChanged(displayId: Int) {
      Log.d(TAG, "Display #$displayId changed.")
      updateDisplaysInfo()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySecondaryDisplayPresentationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    displayManager = ContextCompat.getSystemService(this, DisplayManager::class.java)!!
  }

  override fun onResume() {
    super.onResume()
    updateDisplaysInfo()
    // Register to receive events from the display manager.
    displayManager.registerDisplayListener(displayListener, null)
  }

  override fun onPause() {
    // Unregister from the display manager.
    displayManager.unregisterDisplayListener(displayListener)
    super.onPause()
  }

  override fun onStop() {
    // Dismiss the presentation when the activity is not visible.
    currentPresentation?.let {
      Log.i(TAG, "Dismissing presentation because the activity is no longer visible.")
      it.dismiss()
      currentPresentation = null
    }
    super.onStop()
  }

  private fun updateDisplaysInfo() {
    clearDisplaysInfo()

    val thisActivityDisplay = windowManager.defaultDisplay
    val displays = displayManager.displays
    val secondaryDisplays = displays.filterNot { it.displayId == thisActivityDisplay.displayId }
    if (secondaryDisplays.isEmpty()) {
      binding.displayNone.visibility = View.VISIBLE
    } else {
      binding.displayNone.visibility = View.GONE
      secondaryDisplays.forEach { display ->
        Log.d(TAG, "updatePresentation: Found display $display")
        ItemDisplayInfoBinding.inflate(
          LayoutInflater.from(binding.root.context),
          binding.root,
          true
        ).apply {
          root.tag = DISPLAY_INFO_TAG
          displayInfo.text = "${display.name} [${display.displayId}]"
          displayBt.isEnabled = true
          displayBt.setOnClickListener {
            updatePresentation(display)
          }
          // Disable the PRESENT button for those displays that are already presenting
          currentPresentation?.let {
            if (it.display == display) {
              displayBt.isEnabled = false
            }
          }
        }
      }
    }
  }

  private fun clearDisplaysInfo() {
    // Remove all the views related to display info
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
        Log.i(TAG, "Dismissing old presentation. A new display will be used.")
        currentPresentation = null
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
        Log.e(TAG, "Couldn't show presentation!  Display was removed in the meantime.", ex)
        null
      }
    }
    updateDisplaysInfo()
  }

  private companion object {
    private const val TAG = "SecondaryDisplayPresent"

    private const val DISPLAY_INFO_TAG = "DisplayInfo"
  }

  /**
   * The presentation to show on the secondary display.
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
      super.onCreate(savedInstanceState)

      // MapView uses Lifecycle events to handle its own state.
      // Let's expose this Presentation lifecycle manually
      lifecycleRegistry = LifecycleRegistry(this)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

      // Set the application theme which inherits from `Theme.AppCompat` which is needed for some
      // of the views inside Mapview (e.g. CompassView)
      context.setTheme(R.style.AppTheme)

      // Notice that we are getting the context of the presentation.
      val mapView = MapView(context)
      setContentView(mapView)
    }

    public override fun onStart() {
      super.onStart()
      // Only after we show does the dialog window actually return a decor view.
      val decorView: View = window!!.decorView
      // Set the lifecycle owner for this dialog to be this class. This allows Mapview to get the
      // right lifecycle registry
      ViewTreeLifecycleOwner.set(decorView, this)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onStop() {
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
      lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      super.onStop()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
  }
}