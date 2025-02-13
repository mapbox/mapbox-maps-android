package com.mapbox.maps.testapp.examples.style

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.atmosphere.generated.getAtmosphere
import com.mapbox.maps.extension.style.color.colorTheme
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils.showShortToast
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes

/**
 * Example showcasing the usage of Color Theme API.
 * To create custom LUT, you can follow [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
 */
@OptIn(MapboxExperimental::class)
class ColorThemeActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private var atmosphereUseTheme = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MapView(
      this,
      MapInitOptions(
        this, cameraOptions = START_CAMERA_POSITION
      )
    ).also { mapView ->
      mapboxMap = mapView.mapboxMap
      setContentView(mapView)
      mapboxMap.loadStyle(
        style(Style.MAPBOX_STREETS) {
          +colorTheme(base64 = BASE64_ENCODED_RED_THEME)
          +atmosphere {
            color(COLOR_GREEN)
          }
        }
      )
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_color_theme, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_action_color_theme_image -> {
        mapboxMap.setStyleColorTheme(bitmap = bitmapFromDrawableRes(R.drawable.monochrome_lut))
      }

      R.id.menu_action_color_theme_base64 -> {
        mapboxMap.setStyleColorTheme(base64 = BASE64_ENCODED_RED_THEME)
      }

      R.id.menu_action_color_theme_toggle_atmosphere -> {
        atmosphereUseTheme = !atmosphereUseTheme
        showShortToast("Color theme will ${"not ".takeUnless { atmosphereUseTheme } ?: ""}affect atmosphere.")
        mapboxMap.getStyle {
          it.getAtmosphere().colorUseTheme(
            if (atmosphereUseTheme) COLOR_USE_THEME_DEFAULT else COLOR_USE_THEME_NONE
          )
        }
      }

      R.id.menu_action_color_theme_reset -> {
        mapboxMap.setStyleColorTheme(colorTheme = null)
        atmosphereUseTheme = true
        mapboxMap.getStyle {
          it.getAtmosphere().colorUseTheme(COLOR_USE_THEME_DEFAULT)
        }
      }

      else -> return super.onOptionsItemSelected(item)
    }
    return true
  }

  companion object {
    /**
     * Base64 encoded version of a custom LUT (Look-Up Table) image.
     * LUT image can be created by following [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
     *
     * To convert your LUT image to a Base64 string, you can use an online tool or a script.
     * For example, you can use the following command in a terminal:
     *
     * ```sh
     * base64 -i path/to/your/lut-image.png -o output.txt
     * ```
     */
    private const val BASE64_ENCODED_RED_THEME =
      "iVBORw0KGgoAAAANSUhEUgAABAAAAAAgCAYAAACM/gqmAAAAAXNSR0IArs4c6QAABSFJREFUeF7t3cFO40AQAFHnBv//wSAEEgmJPeUDsid5h9VqtcMiZsfdPdXVzmVZlo+3ZVm+fr3//L7257Lm778x+prL1ff0/b//H+z/4/M4OkuP/n70Nc7f+nnb+yzb//sY6vxt5xXPn+dP/aH+GsXJekb25izxR/ypZ6ucUefv9g4z2jPP3/HPHwAAgABAABgACIACkAAsAL1SD4yKWQAUAHUBdAG8buKNYoYL8PEX4FcHQAAAAAAAAAAAAAAAAAAAAAAA8LAeGF1mABAABAABQACQbZP7+hk5AwACAAAAAAAAAAAAAAAAAAAAAAAA4EE9AICMx4QBAAAAAAAANgvJsxGQV1dA/PxmMEtxU9YoABQACoC5CgDxX/wvsb2sEf/Ff/Ff/N96l5n73+/5YAB4CeBqx2VvMqXgUfD2npkzBCAXEBeQcrkoa5x/FxAXEBcQF5A2Wy3/t32qNYr8I//Mln+MABgBMAJgBMAIgBEAIwBGAIwAGAEwAmAE4K4eAGCNQIw+qQ0AmQ+AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB/6gEABAB5RgACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN/UAAPKcAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgEFNODICRtDkDO/gAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOhvlPUWem+h9xKQ+V4CUt9wO6KZnn/Pv+ff8z/bW5DFP59CUnJbWSP+iX/iX78znqED/urxnwHAAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADoNMcHUAdQAQcAUfAe8xEwH0O86t3IPz8OvClu17WqD/UH+oP9cf1Gdia01d/LQsDgAHAAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABkCnSQwABgACj8Aj8D1mItAMAB1wHfDS3S5r5F/5V/6Vf3XAW12h/mIArHY89iZTAAQA2XtmBKAWqOslyf4rgBXACmAFcIur8k/bJ/mnQTr5V/6Vf+fKv0YAjAAYATACYATACIARACMARgCMABgBMAJgBMAIgBEAIwCdZuiA64AjwAgwAtxjpg6cDlztLlLA7/Pr1gueyr56/jx/5ZzUNeof9Y/6R/0zk4HGAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADgAHQaQ4DgAGAgCPgCHiPmTqQOpC1u8gAYACMjAf5V/6Vf+XfmTrQ8l97v8Z/5X8GAAOAAcAAYAAwABgADAAGAAOAAcAAYAAwABgADIBO0xgADAAdCB0IHYgeMxkADAAdkGM7IPbf/pfuWlmj/lH/qH/UPzMZGAwABgADgAHAAGAAMAAYAAwABgADgAHAAGAAMAAYAJ3mMAAYAAg4Ao6A95jJAGAA6EDrQJfuclkj/8q/8q/8O1MHWv47Nv8xABgADAAGAAOAAcAAYAAwABgADAAGAAOAAcAAYAB0msYAYADoQOhA6ED0mMkAYADogBzbAbH/9r/YFWWN+kf9o/5R/8xkYDAAGAAMAAYAA4ABwABgADAAGAAMAAYAA4ABwABgAHSawwBgACDgCDgC3mMmA4ABoAOtA126y2WN/Cv/yr/y70wdaPnv2PzHAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADgAHQaRoDgAGgA6EDoQPRYyYDgAGgA3JsB8T+2/9iV5Q16h/1j/pH/TOTgcEAYAAwABgADAAGAAOAAcAAYAAwABgADAAGAAPgyQ2AT4NBIB3ew5dkAAAAAElFTkSuQmCC"
    private const val COLOR_USE_THEME_DEFAULT = "default"
    private const val COLOR_USE_THEME_NONE = "none"
    private const val COLOR_GREEN = "#00ff00"
    private const val LATITUDE = 40.72
    private const val LONGITUDE = -73.99
    private val CENTER = Point.fromLngLat(LONGITUDE, LATITUDE)
    private val START_CAMERA_POSITION = cameraOptions {
      center(CENTER)
      zoom(2.0)
      pitch(45.0)
    }
  }
}