package com.mapbox.maps.extension.style

import android.content.res.Resources
import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.maps.Image
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface
import java.nio.ByteBuffer

/**
 * Define the common interfaces for the Style component.
 */
interface StyleContract {
  /**
   * Define the interfaces for the Style Extension.
   */
  interface StyleExtension {
    /**
     * The style's Uri.
     */
    val styleUri: String

    /**
     * The sources of the style.
     */
    val sources: List<StyleSourceExtension>

    /**
     * The images of the style.
     */
    val images: List<StyleImageExtension>

    /**
     * The layers of the style.
     */
    val layers: List<Pair<StyleLayerExtension, LayerPosition>>

    /**
     * The light of the style.
     */
    val light: StyleLightExtension?

    /**
     * The 3D terrain of the style.
     */
    val terrain: StyleTerrainExtension?
  }

  /**
   * Define the interfaces for the Layer plugin.
   */
  interface StyleLayerExtension {
    /**
     * Bind the layer to the Style.
     *
     * @param delegate The style delegate
     * @param position the layer position
     */
    fun bindTo(delegate: StyleManagerInterface, position: LayerPosition? = null)
  }

  /**
   * Define the interfaces for the Light plugin.
   */
  fun interface StyleLightExtension {
    /**
     * Bind the light to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleManagerInterface)
  }

  /**
   * Define the interfaces for the Terrain plugin.
   */
  fun interface StyleTerrainExtension {
    /**
     * Bind the terrain to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleManagerInterface)
  }

  /**
   * Define the interfaces for the Light plugin.
   */
  fun interface StyleSourceExtension {
    /**
     * Add the source to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleManagerInterface)
  }

  /**
   * Define the interfaces for the Image plugin.
   */
  fun interface StyleImageExtension {
    /**
     * Add the image to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleManagerInterface)
  }
}

/**
 * Adds an image to be used in the style. This API can also be used for updating
 * an image. If the image \a id was already added, it gets replaced by the new image.
 *
 * The image can be used in `icon-image`, `fill-pattern`, and `line-pattern`.
 *
 * https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image
 * https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern
 * https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern
 *
 * @param imageId ID of the image.
 * @param bitmap Bitmap data of the image.
 *
 * @return A string describing an error if the operation was not successful, empty otherwise.
 */
fun StyleManagerInterface.addStyleImage(imageId: String, bitmap: Bitmap): Expected<Void, String> {
  val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
  bitmap.copyPixelsToBuffer(byteBuffer)
  val image = Image(bitmap.width, bitmap.height, byteBuffer.array())
  return this.addStyleImage(imageId, Resources.getSystem().displayMetrics.density, image, false, listOf(), listOf(), null)
}