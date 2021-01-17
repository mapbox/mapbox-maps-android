package com.mapbox.maps.testapp.utils

import android.content.Context
import com.mapbox.core.utils.TextUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

class Assets {
  companion object {
    fun loadStringFromAssets(context: Context, fileName: String): String {
      if (TextUtils.isEmpty(fileName)) {
        throw NullPointerException("No GeoJSON File Name passed in.")
      }
      val `is` = context.assets.open(fileName)
      val rd = BufferedReader(InputStreamReader(`is`, Charset.forName("UTF-8")))
      return readAll(rd)
    }

    private fun readAll(rd: Reader): String {
      val sb = StringBuilder()
      rd.forEachLine {
        sb.append(it)
      }
      return sb.toString()
    }
  }
}