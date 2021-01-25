package com.mapbox.maps.testapp.examples.annotation

import android.content.Context
import com.mapbox.core.utils.TextUtils
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import java.util.*

/**
 * Useful utilities used throughout the testapp.
 */
object Utils {

  private val STYLES =
    arrayOf(Style.MAPBOX_STREETS, Style.OUTDOORS, Style.LIGHT, Style.DARK, Style.SATELLITE_STREETS)

  private var index: Int = 0

  /**
   * Utility to cycle through map styles. Useful to test if runtime styling source and layers transfer over to new
   * style.
   *
   * @return a string ID representing the map style
   */
  val nextStyle: String
    get() {
      index++
      if (index == STYLES.size) {
        index = 0
      }
      return STYLES[index]
    }

  /**
   * Utility for getting a list of random points.
   *
   * @return a a list of random points
   */
  fun createRandomPoints(): List<Point> {
    val random = Random()
    val points: MutableList<Point> = ArrayList<Point>()
    for (i in 0 until random.nextInt(10)) {
      points.add(
        Point.fromLngLat(
          random.nextDouble() * -360.0 + 180.0,
          random.nextDouble() * -180.0 + 90.0
        )
      )
    }
    return points
  }

  /**
   * Utility for getting a list of lists of random points
   *
   * @return a list of lists of random points
   */
  fun createRandomPointsList(): List<List<Point>> {
    val random = Random()
    val points = mutableListOf<Point>()
    val firstLast = Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
    points.add(firstLast)
    for (i in 0 until random.nextInt(10)) {
      points.add(
        Point.fromLngLat(
          random.nextDouble() * -360.0 + 180.0,
          random.nextDouble() * -180.0 + 90.0
        )
      )
    }
    points.add(firstLast)
    return listOf(points)
  }

  /**
   * Utility for getting a random point
   *
   * @return a random point
   */
  fun createRandomPoint(): Point {
    val random = Random()
    return Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
  }

  @Throws(IOException::class)
  fun loadStringFromAssets(context: Context, fileName: String): String {
    if (TextUtils.isEmpty(fileName)) {
      throw NullPointerException("No GeoJSON File Name passed in.")
    }
    val `is` = context.assets.open(fileName)
    val rd = BufferedReader(InputStreamReader(`is`, Charset.forName("UTF-8")))
    return readAll(rd)
  }

  @Throws(IOException::class)
  private fun readAll(rd: Reader): String {
    val sb = StringBuilder()
    rd.forEachLine {
      sb.append(it)
    }
    return sb.toString()
  }
}