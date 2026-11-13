package com.mapbox.maps.plugin.consolidatedattribution

import com.mapbox.bindgen.Value
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceStorageType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SettingsServiceIntroFlagStoreTest {

  private val settings = SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT)

  @Before
  fun setUp() {
    settings.erase(SettingsServiceIntroFlagStore.KEY)
  }

  @After
  fun tearDown() {
    settings.erase(SettingsServiceIntroFlagStore.KEY)
  }

  @Test
  fun flagIsUnsetByDefault() {
    assertFalse(SettingsServiceIntroFlagStore.isSet)
  }

  @Test
  fun setFlagIsSavedInPersistentSettings() {
    SettingsServiceIntroFlagStore.isSet = true

    assertTrue(SettingsServiceIntroFlagStore.isSet)
    assertEquals(Value(true), settings.get(SettingsServiceIntroFlagStore.KEY).value)
  }

  @Test
  fun clearingFlagErasesKey() {
    SettingsServiceIntroFlagStore.isSet = true

    SettingsServiceIntroFlagStore.isSet = false

    assertFalse(SettingsServiceIntroFlagStore.isSet)
    assertFalse(settings.has(SettingsServiceIntroFlagStore.KEY).value!!)
  }

  @Test
  fun nonBooleanValueReadsAsUnset() {
    settings.set(SettingsServiceIntroFlagStore.KEY, Value("true"))

    assertFalse(SettingsServiceIntroFlagStore.isSet)
  }
}