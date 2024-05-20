package com.mapbox.maps

/**
 * Runtime exception thrown when the [Snapshotter] has already been destroyed.
 */
class SnapshotterDestroyedException : IllegalStateException("This snapshotter was already destroyed.")