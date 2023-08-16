package com.mapbox.maps

import com.mapbox.bindgen.DataRef
import com.mapbox.verifyOnce
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.nio.ByteBuffer

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowMapRecorder::class])
class MapboxMapRecorderTest {

  private val coreRecorder = mockk<MapRecorder>(relaxed = true)
  private val mapboxMapRecorder = MapboxMapRecorder(coreRecorder)

  @Test
  fun startRecording() {
    val options = mapRecorderOptions { }
    mapboxMapRecorder.startRecording(options)
    verifyOnce { coreRecorder.startRecording(options) }
  }

  @Test
  fun stopRecording() {
    val buffer = ByteBuffer.allocateDirect(5)
    val dataRef = mockk<DataRef>()
    every { dataRef.buffer } returns buffer
    every { coreRecorder.stopRecording() } returns dataRef
    val result = mapboxMapRecorder.stopRecording()
    verifyOnce { coreRecorder.stopRecording() }
    assert(result == buffer.rewind())
  }

  @Test
  fun replay() {
    mockkStatic(DataRef::class)
    val buffer = ByteBuffer.allocateDirect(5)
    val dataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(any()) } returns dataRef
    val options = mapPlayerOptions { }
    mapboxMapRecorder.replay(buffer, options) { }
    verifyOnce {
      coreRecorder.replay(dataRef, options, any())
    }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun togglePauseReplay() {
    mapboxMapRecorder.togglePauseReplay()
    verifyOnce { coreRecorder.togglePauseReplay() }
  }

  @Test
  fun getPlaybackState() {
    every { coreRecorder.playbackState } returns "state"
    val result = mapboxMapRecorder.getPlaybackState()
    verifyOnce { coreRecorder.playbackState }
    assert(result == "state")
  }
}