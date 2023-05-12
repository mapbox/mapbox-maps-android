package com.mapbox.maps.exception

import java.lang.RuntimeException

/**
 * Exception thrown when a worker thread calls in a main thread only class.
 */
class WorkerThreadException : RuntimeException(
  """
  The exception that is thrown when an application attempts to 
  perform a map operation on a worker thread.
  """.trimIndent()
)