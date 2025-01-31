package com.mapbox.maps.extension.style.color

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.maps.ColorTheme
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.toMapboxImage

/**
 * DSL function for creating empty [ColorTheme] instance. It can be used when removing an applied theme from the style.
 **/
@MapboxExperimental
fun colorTheme(): ColorTheme = ColorTheme.valueOf(
  StylePropertyValue(
    Value.nullValue(), StylePropertyValueKind.UNDEFINED
  )
)

/**
 * DSL function for creating [ColorTheme] instance.
 *
 * @param base64 Base64 representation of an LUT image.
 *  LUT image can be created by following [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
 *  To convert your LUT image to a Base64 string, you can use an online tool or a script.
 *  For example, you can use the following command in a terminal:
 *  ```sh
 *  base64 -i path/to/your/lut-image.png -o output.txt
 *  ```
 */
@MapboxExperimental
fun colorTheme(base64: String): ColorTheme = ColorTheme.valueOf(
  StylePropertyValue(
    Value.valueOf(base64), StylePropertyValueKind.CONSTANT
  )
)

/**
 * DSL function for creating [ColorTheme] instance.
 * To create custom LUT, you can follow [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
 * @param image [Image] data of an LUT image.
 */
@MapboxExperimental
fun colorTheme(image: Image): ColorTheme = ColorTheme.valueOf(image)

/**
 * DSL function for creating [ColorTheme] instance.
 * To create custom LUT, you can follow [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
 * @param bitmap bitmap representation of an LUT image.
 */
@OptIn(MapboxDelicateApi::class)
@MapboxExperimental
fun colorTheme(bitmap: Bitmap): ColorTheme = ColorTheme.valueOf(bitmap.toMapboxImage())