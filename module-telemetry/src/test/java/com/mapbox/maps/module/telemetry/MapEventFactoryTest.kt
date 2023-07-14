package com.mapbox.maps.module.telemetry

import android.os.Build
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.module.telemetry.PerformanceEvent.PerformanceAttribute
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers
import java.util.*

@RunWith(RobolectricTestRunner::class)
class MapEventFactoryTest {
  private lateinit var phoneState: PhoneState
  private val gson = Gson()

  @Before
  fun setUp() {
    ReflectionHelpers.setStaticField(
      Build::class.java,
      "MODEL",
      "Foobar"
    )

    phoneState = PhoneState()
    phoneState.accessibilityFontScale = FONT_SCALE
    phoneState.batteryLevel = BATTERY_LEVEL
    phoneState.carrier = CARRIER
    phoneState.cellularNetworkType = NETWORK_TYPE
    phoneState.created = CREATED
    phoneState.orientation = PhoneState.Orientation.ORIENTATION_LANDSCAPE
    phoneState.isPluggedIn = PLUGIN
    phoneState.resolution = RESOLUTION
    phoneState.isWifi = WIFI
  }

  @Test
  fun testMapLoadEvent() {
    val mapLoadEvent = MapEventFactory.buildMapLoadEvent(phoneState)
    Assert.assertEquals(
      "Android - " + Build.VERSION.RELEASE,
      mapLoadEvent.operatingSystem
    )
    Assert.assertEquals(Build.MODEL, mapLoadEvent.model)
    Assert.assertNotNull(mapLoadEvent.userId)
    Assert.assertEquals(
      BATTERY_LEVEL.toLong(),
      mapLoadEvent.batteryLevel.toLong()
    )
    Assert.assertEquals(CARRIER, mapLoadEvent.carrier)
    Assert.assertEquals(
      NETWORK_TYPE,
      mapLoadEvent.cellularNetworkType
    )
    Assert.assertEquals(CREATED, mapLoadEvent.created)
    Assert.assertEquals("Landscape", mapLoadEvent.orientation)
    Assert.assertEquals(
      BuildConfig.MAPBOX_SDK_IDENTIFIER,
      mapLoadEvent.sdkIdentifier
    )
    Assert.assertEquals(
      BuildConfig.MAPBOX_SDK_VERSION,
      mapLoadEvent.sdkVersion
    )
    Assert.assertEquals(PLUGIN, mapLoadEvent.isPluggedIn)
    Assert.assertEquals(WIFI, mapLoadEvent.isWifi)
    Assert.assertEquals(
      FONT_SCALE,
      mapLoadEvent.accessibilityFontScale,
      0f
    )
    val json = gson.toJson(mapLoadEvent)
    val event = gson.fromJson(json, MapLoadEvent::class.java)
    Assert.assertEquals(mapLoadEvent, event)
  }

  @Test
  fun testPerformanceEvent() {
    val nameAttribute = "nameAttribute"
    val valueAttribute = "100"
    val nameCounters = "nameCounters"
    val valuesCounters = 1000.0
    val metaData = JsonObject()
    metaData.addProperty("os", "android")
    metaData.addProperty("manufacturer", Build.MANUFACTURER)
    metaData.addProperty("brand", Build.BRAND)
    metaData.addProperty("device", Build.MODEL)
    metaData.addProperty("version", Build.VERSION.RELEASE)
    metaData.addProperty("abi", Build.CPU_ABI)
    metaData.addProperty("country", Locale.getDefault().isO3Country)
    metaData.addProperty("ram", "ram")
    metaData.addProperty("screenSize", "1000")
    val data: Bundle = mockk()
    val attribtueList =
      ArrayList<PerformanceAttribute<String>>()
    val attribute =
      PerformanceAttribute(nameAttribute, valueAttribute)
    attribtueList.add(attribute)
    val countersList =
      ArrayList<PerformanceAttribute<Double>>()
    val counter =
      PerformanceAttribute(nameCounters, valuesCounters)
    countersList.add(counter)
    every { data.getString("attributes") } returns gson.toJson(attribtueList)
    every { data.getString("counters") } returns gson.toJson(countersList)
    every { data.getString("metadata") } returns metaData.toString()
    val performanceEvent = MapEventFactory.buildPerformanceEvent(
      phoneState,
      SESSION_ID, data
    )
    Assert.assertEquals(
      CREATED,
      performanceEvent.created
    )
    Assert.assertEquals(metaData.toString(), performanceEvent.metadata.toString())
    Assert.assertEquals(
      SESSION_ID,
      performanceEvent.sessionId
    )
    val attributeList = performanceEvent.attributes
    Assert.assertEquals(1, attributeList?.size)
    Assert.assertEquals(attribute, attributeList!![0])
    val counterList = performanceEvent.counters
    Assert.assertEquals(1, counterList?.size)
    Assert.assertEquals(counter, counterList!![0])
  }

  @Test
  fun testBuildConfigValues() {
    // Regression test
    assertNotEquals("version shouldn't be null", "null", BuildConfig.MAPBOX_SDK_VERSION)
    assertNotEquals("sdk identifier shouldn't be null", "Mapbox/null", BuildConfig.MAPBOX_VERSION_STRING)
    // Value test
    assertTrue("sdk identifier like should match", BuildConfig.MAPBOX_SDK_IDENTIFIER.contains("mapbox-maps-android"))
    assertTrue("version like should match", BuildConfig.MAPBOX_VERSION_STRING.contains("Mapbox/"))
  }

  companion object {
    private const val FONT_SCALE = 1f
    private const val BATTERY_LEVEL = 50
    private const val CARRIER = "carrier"
    private const val NETWORK_TYPE = "network"
    private const val CREATED = "2019-04-09"
    private const val PLUGIN = true
    private const val RESOLUTION = 1024f
    private const val WIFI = true
    private const val SESSION_ID = "001"
  }
}