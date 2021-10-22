package com.mapbox.maps.testapp.utils

import android.content.Context
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class StorageUtils(context: Context) {
  private val directory: File = File(context.filesDir, LOG_DIR)
    .apply { mkdirs() }

  fun fileExists(fileName: String): Boolean {
    val deviceInfo = File(directory, fileName)
    return deviceInfo.exists() && deviceInfo.length() > 0
  }

  fun writeToFile(fileName: String, content: String) {
    val file = File(directory, fileName).apply { delete() }
    file.write(content)
  }

  private fun File.write(results: String) {
    val fw = FileWriter(this, true)
    val writer = BufferedWriter(fw)
    writer.write(results)
    writer.close()
    fw.close()
  }

  companion object {
    const val LOG_DIR = "logs"
  }
}