package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.geojson.Point
import java.nio.ByteBuffer

/**
 * An image snapshot of a map rendered by MapSnapshotter.
 */
internal class Snapshot(snapshot: MapSnapshot) : MapSnapshotInterface {
  private var _bitmap: Bitmap

  /**
   * [Bitmap] representing a rendered map.
   */
  val bitmap: Bitmap get() = _bitmap

  private val coreSnapshot: MapSnapshot = snapshot

  init {
    val configBmp: Bitmap.Config = Bitmap.Config.ARGB_8888
    _bitmap = Bitmap.createBitmap(coreSnapshot.image().width, coreSnapshot.image().height, configBmp)
    val buffer: ByteBuffer = ByteBuffer.wrap(coreSnapshot.image().data)
    _bitmap.copyPixelsFromBuffer(buffer)
  }

  /**
   * Get list of attributions for the sources in this snapshot.
   *
   * @return list of attributions for the sources in this snapshot.
   */
  override fun attributions(): MutableList<String> {
    return coreSnapshot.attributions()
  }

  /**
   * Calculate geographical coordinates from a point on the snapshot.
   *
   * @param screenCoordinate Screen coordinates on the snapshot in \link MapOptions#size platform pixels \endlink
   * @return Geographical coordinates for point on the snapshot.
   */
  override fun coordinate(screenCoordinate: ScreenCoordinate): Point {
    return coreSnapshot.coordinate(screenCoordinate)
  }

  /**
   * Calculate screen coordinate on the snapshot from geographical coordinate.
   *
   * @param coordinate Geographical coordinate.
   * @return screen coordinate in \link MapOptions#size platform pixels \endlink on the snapshot for geographical coordinate.
   */
  override fun screenCoordinate(coordinate: Point): ScreenCoordinate {
    return coreSnapshot.screenCoordinate(coordinate)
  }

  /**
   * Get the rendered snapshot image data.
   *
   * @return rendered snapshot image data.
   */
  override fun image(): Image {
    val buffer: ByteBuffer = ByteBuffer.allocate(_bitmap.byteCount)
    _bitmap.copyPixelsToBuffer(buffer)
    return Image(_bitmap.width, _bitmap.height, buffer.array())
  }
}