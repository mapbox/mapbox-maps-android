package com.mapbox.maps.testapp.utils

import android.content.Context
import com.google.gson.JsonObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class StorageUtils(context: Context) {
  private val directory: File = File(context.filesDir, "logs")
    .apply { mkdirs() }

  fun fileExists(fileName: String): Boolean {
    val deviceInfo = File(directory, fileName)
    return deviceInfo.exists() && deviceInfo.length() > 0
  }

  fun writeJsonToFile(fileName: String, jsonObject: JsonObject) {
    val saveFilePath = File(directory, fileName).apply { delete() }
    saveFilePath.write(jsonObject.toString())
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
}
