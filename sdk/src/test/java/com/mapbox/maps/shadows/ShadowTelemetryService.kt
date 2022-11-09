package com.mapbox.maps.shadows

import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.FlushOperationResultCallback
import com.mapbox.common.TelemetryService
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(TelemetryService::class)
class ShadowTelemetryService {
  @Implementation
  fun flush(callback: FlushOperationResultCallback?) {
    TODO("mocked implementation")
  }

  companion object {
    @Implementation
    fun getOrCreate(options: EventsServerOptions): EventsService {
      TODO("mocked implementation")
    }
  }
}