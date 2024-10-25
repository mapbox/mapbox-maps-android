package com.mapbox.maps

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.DataRef
import java.nio.ByteBuffer

/**
 * MapboxMapRecorder provides functions to record and replay API calls of a [MapboxMap] instance.
 * These recordings can be used to debug issues which require multiple steps to reproduce.
 * Additionally, playbacks can be used for performance testing custom scenarios.
 *
 * Note: The raw format produced by [stopRecording] is experimental and there is no guarantee for version cross-compatibility when feeding it to [replay].
 *
 * To create the MapboxMapRecorder instance refer to [MapboxMap.createRecorder].
 */
@MapboxExperimental
class MapboxMapRecorder @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX) internal constructor(
  private val mapRecorder: MapRecorder
) {

  /**
   * Begins the recording session.
   *
   * @param options [MapRecorderOptions] to control recording.
   */
  @JvmOverloads
  fun startRecording(options: MapRecorderOptions = mapRecorderOptions { }) {
    mapRecorder.startRecording(options)
  }

  /**
   * Stops the current recording session.
   * Recorded section could be replayed with [replay] function.
   *
   * @return the [ByteBuffer] containing the recorded sequence in raw format.
   */
  fun stopRecording(): ByteBuffer {
    val data = mapRecorder.stopRecording()
    return data.buffer.apply { rewind() }
  }

  /**
   * Replay a supplied sequence.
   *
   * @param recordedSequence Sequence recorded with [stopRecording] method.
   * @param options Options to customize the behaviour of the playback.
   * @param onEnded Callback to call when the playback ends.
   */
  @JvmOverloads
  fun replay(
    recordedSequence: ByteBuffer,
    options: MapPlayerOptions = mapPlayerOptions { },
    onEnded: () -> Unit = { }
  ) {
    val dataRef = DataRef.allocateNative(recordedSequence.limit())
    dataRef.buffer.put(recordedSequence.apply { rewind() }).rewind()
    mapRecorder.replay(dataRef, options) {
      onEnded.invoke()
    }
  }

  /**
   * Temporarily pauses or resumes playback if already paused.
   */
  fun togglePauseReplay() {
    mapRecorder.togglePauseReplay()
  }

  /**
   * Returns the string description of the current state of playback.
   */
  fun getPlaybackState(): String {
    return mapRecorder.playbackState
  }
}

/**
 * DSL builder function to create [MapRecorderOptions] object.
 */
inline fun mapRecorderOptions(block: MapRecorderOptions.Builder.() -> Unit): MapRecorderOptions =
  MapRecorderOptions.Builder().apply(block).build()

/**
 * DSL builder function to create [MapPlayerOptions] object.
 */
inline fun mapPlayerOptions(block: MapPlayerOptions.Builder.() -> Unit): MapPlayerOptions =
  MapPlayerOptions.Builder().apply(block).build()