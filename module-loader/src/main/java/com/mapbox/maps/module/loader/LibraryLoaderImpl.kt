package com.mapbox.maps.module.loader

import com.mapbox.common.module.LibraryLoader

/**
 * Concrete implementation of LibraryLoader that loads native libraries with System.loadLibrary().
 */
class LibraryLoaderImpl : LibraryLoader {

  /**
   * Load native library from a library name.
   */
  override fun load(libraryName: String) {
    System.loadLibrary(libraryName)
  }
}