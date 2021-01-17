package com.mapbox.maps.module.loader

import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.module.Mapbox_ModuleProvider_LibraryLoader
import org.junit.Test

/**
 * Validates that ModuleProvider is able to create an instance of LibraryLoaderImpl
 */
class LibraryLoaderTest {

  @Test(expected = UnsatisfiedLinkError::class)
  fun load() {
    Mapbox_ModuleProvider_LibraryLoader.createLibraryLoader().load(
      InstrumentationRegistry.getInstrumentation().context, "foobar"
    )
  }
}