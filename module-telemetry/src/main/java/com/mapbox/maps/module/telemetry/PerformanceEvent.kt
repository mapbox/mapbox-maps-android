package com.mapbox.maps.module.telemetry

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Generic Performance Event that can be used for performance measurements.
 * Customer measurements can be added to the bundle.
 *
 * Bundle is expected to have following properties:
 * "attributes", "counters", and "metadata" with String values.
 *
 * Attributes: a string representing an array of name/string value pair objects.
 * Counters: a string representing an array of name/number value pair objects.
 * Metadata is a string representation of a JsonObject with string values.
 *
 * Here is an example of a Performance event bundle data:
 *
 * "attributes": [{ "name": "style_id", "value": "mapbox://styles/mapbox/streets-v10"}]
 *
 * "counters": [{"name": "fps_average", "value": 90.7655486547093},
 * {"name": "fps_deviation", "value": 29.301809631465574}]
 * “metadata”: {
 * “version”: “9”,
 * “screenSize”: “1080x1794”,
 * “country”: “US”,
 * “device”: “Pixel 2”,
 * “abi”: “arm64-v8a”,
 * “brand”: “google”,
 * “ram”: “3834167296”,
 * “os”: “android”,
 * “gpu”: “Qualcomm, Adreno (TM) 540, OpenGL ES 3.2 V@313.0 (GIT@7bf2852, Ie32bfa6f6f)“,
 * “manufacturer”: “Google”
 * }
 *
 */
@SuppressLint("ParcelCreator")
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PerformanceEvent(
  phoneState: PhoneState,
  @field:SerializedName("sessionId") val sessionId: String?,
  bundle: Bundle
) : MapBaseEvent(phoneState) {

  @SerializedName("attributes")
  val attributes: List<PerformanceAttribute<String>>?

  @SerializedName("counters")
  val counters: List<PerformanceAttribute<Double>>?

  @SerializedName("metadata")
  val metadata: JsonObject?

  init {
    attributes = initList(
      bundle.getString("attributes"),
      object :
        TypeToken<ArrayList<PerformanceAttribute<String?>?>?>() {}
    )
    counters = initList(
      bundle.getString("counters"),
      object :
        TypeToken<ArrayList<PerformanceAttribute<Double?>?>?>() {}
    )
    metadata = initMetaData(bundle.getString("metadata"))
  }

  private fun <T> initList(
    fromString: String?,
    typeToken: TypeToken<*>
  ): ArrayList<PerformanceAttribute<T>> {
    return if (fromString == null || fromString.isEmpty()) {
      ArrayList()
    } else Gson().fromJson(fromString, typeToken.type)
  }

  private fun initMetaData(fromString: String?): JsonObject {
    return if (fromString == null) {
      JsonObject()
    } else {
      Gson()
        .fromJson(fromString, JsonObject::class.java)
    }
  }

  override fun getEventName(): String {
    return PERFORMANCE_TRACE
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val that = other as PerformanceEvent
    if (event != that.event) {
      return false
    }
    if (created != that.created) {
      return false
    }
    if (sessionId != that.sessionId) {
      return false
    }
    if (attributes != that.attributes) {
      return false
    }
    return if (counters != that.counters) {
      false
    } else metadata == that.metadata
  }

  override fun hashCode(): Int {
    var result = sessionId?.hashCode() ?: 0
    result = 31 * result + event.hashCode()
    result = 31 * result + created.hashCode()
    result = 31 * result + (attributes?.hashCode() ?: 0)
    result = 31 * result + (counters?.hashCode() ?: 0)
    result = 31 * result + (metadata?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return (
      "PerformanceEvent{" +
        "sessionId='" + sessionId + '\'' +
        ", attributes=" + attributes +
        ", counters=" + counters +
        ", metadata=" + metadata +
        '}'
      )
  }

  internal class PerformanceAttribute<T>(private val name: String?, value: T) {
    private val value: T?
    override fun equals(other: Any?): Boolean {
      if (this === other) {
        return true
      }
      if (other == null || javaClass != other.javaClass) {
        return false
      }
      val that = other as PerformanceAttribute<*>
      return if (name != that.name) {
        false
      } else value == that.value
    }

    override fun hashCode(): Int {
      var result = name?.hashCode() ?: 0
      result = 31 * result + (value?.hashCode() ?: 0)
      return result
    }

    init {
      this.value = value
    }
  }

  companion object {
    private const val PERFORMANCE_TRACE = "mobile.performance_trace"
  }
}