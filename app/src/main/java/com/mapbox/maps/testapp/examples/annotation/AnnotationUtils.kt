package com.mapbox.maps.testapp.examples.annotation

import android.content.Context
import android.widget.Toast
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.*
import java.nio.charset.Charset
import java.util.*

/**
 * Useful utilities used throughout the testapp.
 */
object AnnotationUtils {
  private const val TAG = "AnnotationUtils"

  val STYLES =
    arrayOf(Style.STANDARD, Style.OUTDOORS, Style.LIGHT, Style.DARK, Style.SATELLITE_STREETS, Style.STANDARD_SATELLITE)
  val SLOTS = arrayOf("top", "middle", "bottom")

  /**
   * Show short toast message.
   */
  internal fun Context.showShortToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
  }

  /**
   * Utility for getting a list of random points.
   *
   * @return a a list of random points
   */
  fun createRandomPoints(): List<Point> {
    val random = Random()
    val points: MutableList<Point> = ArrayList<Point>()
    for (i in 0 until random.nextInt(8) + 2) {
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
    for (i in 0 until random.nextInt(6) + 4) {
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

  /**
   * Load the string content from an assets file in the background
   *
   * @param context the context
   * @param fileName the file to load
   * @return the contents of the file
   *
   * @throws IOException if the given [fileName] can't be read
   */
  suspend fun loadStringFromAssets(context: Context, fileName: String): String =
    withContext(Dispatchers.IO) {
        context.assets.open(fileName).bufferedReader().readText()
    }

  /**
   * Load the string content from net
   *
   * @param url the url of the file to load
   */
  fun loadStringFromNet(context: Context, url: String): String? {
    val client = OkHttpClient.Builder()
      .cache(Cache(File(context.externalCacheDir.toString(), "cache"), 10 * 1024 * 1024L))
      .build()
    val request: Request = Request.Builder()
      .url(url)
      .build()

    return try {
      val response = client.newCall(request).execute()
      val inputStream = BufferedInputStream(response.body?.byteStream())
      val rd = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
      val sb = StringBuilder()
      rd.forEachLine {
        sb.append(it)
      }
      sb.toString()
    } catch (e: IOException) {
      logE(TAG, "Unable to download $url")
      null
    }
  }
}