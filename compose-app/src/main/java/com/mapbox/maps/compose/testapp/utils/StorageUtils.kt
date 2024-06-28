package com.mapbox.maps.compose.testapp.utils

import android.content.Context
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

public class StorageUtils(context: Context) {
  private val directory: File = File(context.filesDir, LOG_DIR)
    .apply { mkdirs() }

  public fun fileExists(fileName: String): Boolean {
    val deviceInfo = File(directory, fileName)
    return deviceInfo.exists() && deviceInfo.length() > 0
  }

  public fun writeToFile(fileName: String, content: String) {
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

  public companion object {
    public const val LOG_DIR: String = "logs"
  }
}