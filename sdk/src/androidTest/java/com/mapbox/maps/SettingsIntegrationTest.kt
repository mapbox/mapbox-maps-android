package com.mapbox.maps

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.bindgen.Value
import com.mapbox.common.SettingsServiceInterface
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.MapboxCommonSettings.LANGUAGE
import com.mapbox.common.MapboxCommonSettings.WORLDVIEW
import org.junit.*
import org.junit.Assert.*
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * SettingsService stores settings (eg. language and worldview) as {key:value} pair,
 * there are no validation on keys and values on Settings interface, we perform tests
 * based on set values to get the output from the data store used by settings service.
 */
@RunWith(Enclosed::class)
@Suite.SuiteClasses(
  SettingsIntegrationTest.SettingsInterfaceTest::class,
  SettingsIntegrationTest.ParameterizedSettingsTest::class
)
class SettingsIntegrationTest {

  @RunWith(AndroidJUnit4::class)
  class SettingsInterfaceTest {
    private lateinit var settingsServiceInterface: SettingsServiceInterface

    @get:Rule
    var rule = ActivityScenarioRule(EmptyActivity::class.java)

    @Before
    fun setUp() {
      settingsServiceInterface =
        SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT)
    }

    @Test
    fun testSetSettings() {
      val latch = CountDownLatch(1)
      val expected = settingsServiceInterface.set(LANGUAGE, SETTING_VALUE)
      assertTrue(expected.isValue)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    @Test
    fun testGetSettings() {
      val latch = CountDownLatch(1)
      settingsServiceInterface.set(LANGUAGE, SETTING_VALUE)
      val expected = settingsServiceInterface.get(LANGUAGE)
      assertTrue(expected.isValue)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    @Test
    fun testErasedSettings() {
      val latch = CountDownLatch(1)
      // erase the setting
      settingsServiceInterface.set(LANGUAGE, SETTING_VALUE)
      settingsServiceInterface.erase(LANGUAGE)
      // check erased key
      val expected = settingsServiceInterface.has(LANGUAGE)
      assertTrue(expected.isValue)
      assertFalse(expected.value!!)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    @Test
    fun testErasedSettingsGetValue() {
      val latch = CountDownLatch(1)
      // erase the setting
      settingsServiceInterface.set(LANGUAGE, SETTING_VALUE)
      settingsServiceInterface.erase(LANGUAGE)

      val expected = settingsServiceInterface.get(LANGUAGE)
      assertTrue(expected.isError)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    @Test
    fun testGetWithoutStoredValue() {
      val expected = settingsServiceInterface.get(LANGAUGE_KEY_RANDOM)
      assertTrue(expected.isError)
    }

    private companion object {
      val SETTING_VALUE = Value("fi")
      const val LANGAUGE_KEY_RANDOM = "com.mapbox.language"
    }
  }

  // Parameterized tests for language and worldview settings
  @RunWith(Parameterized::class)
  class ParameterizedSettingsTest(private val setting: Value) {
    private lateinit var settingsServiceInterface: SettingsServiceInterface

    @Before
    fun setUp() {
      settingsServiceInterface =
        SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT)
    }

    @Test
    fun testLanguageSetting() {
      val latch = CountDownLatch(1)
      settingsServiceInterface.set(LANGUAGE, setting)
      val expected = settingsServiceInterface.get(LANGUAGE)
      assertTrue(expected.isValue)
      assertEquals(expected.value, setting)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    @Test
    fun testWorldviewSetting() {
      val latch = CountDownLatch(1)
      val expectedSet = settingsServiceInterface.set(WORLDVIEW, setting)
      assertTrue(expectedSet.isValue)
      val expected = settingsServiceInterface.get(WORLDVIEW)
      assertEquals(expected.value, setting)
      latch.countDown()

      if (!latch.await(30, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
    }

    private companion object {
      @JvmStatic
      @Parameterized.Parameters
      fun data() = listOf(
        Value.nullValue(),
        Value("null"),
        Value("fi"),
        Value("fi-FI"),
        Value("fi-Latn-FI"),
        Value(false),
        Value(1.0),
        Value(1)
      )
    }
  }
}