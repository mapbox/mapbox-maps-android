package com.mapbox.maps.extension.observable

import com.mapbox.maps.CameraChanged
import com.mapbox.maps.CanonicalTileID
import com.mapbox.maps.MapIdle
import com.mapbox.maps.MapLoaded
import com.mapbox.maps.MapLoadingError
import com.mapbox.maps.RenderFrameFinished
import com.mapbox.maps.RenderFrameStarted
import com.mapbox.maps.SourceAdded
import com.mapbox.maps.SourceDataLoaded
import com.mapbox.maps.SourceRemoved
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleImageMissing
import com.mapbox.maps.StyleImageRemoveUnused
import com.mapbox.maps.StyleLoaded
import com.mapbox.maps.extension.observable.eventdata.*
import com.mapbox.maps.extension.observable.model.*
import java.util.*

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [MapLoadedEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun MapLoaded.toMapLoadedEventData() = MapLoadedEventData(
  begin = this.timeInterval.begin.time,
  end = this.timeInterval.end.time
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [MapLoadingErrorEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun MapLoadingError.toMapLoadingErrorEventData() = MapLoadingErrorEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time,
  type = MapLoadErrorType.valueOf(this.type.name),
  message = this.message,
  sourceId = this.sourceId,
  tileId = this.tileId?.toTileId()
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [MapIdleEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun MapIdle.toMapIdleEventData() = MapIdleEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [StyleDataLoadedEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun StyleDataLoaded.toStyleDataLoadedEventData() = StyleDataLoadedEventData(
  begin = this.timeInterval.begin.time,
  end = this.timeInterval.end.time,
  type = StyleDataType.valueOf(this.type.name)
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [StyleLoadedEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun StyleLoaded.toStyleLoadedEventData() = StyleLoadedEventData(
  begin = this.timeInterval.begin.time,
  end = this.timeInterval.end.time,
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [SourceDataLoadedEventData] object.
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun SourceDataLoaded.toSourceDataLoadedEventData() = SourceDataLoadedEventData(
  begin = this.timeInterval.begin.time,
  end = this.timeInterval.end.time,
  id = this.sourceId,
  type = SourceDataType.valueOf(this.type.name),
  loaded = this.loaded,
  tileID = this.tileId?.toTileId()
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [StyleImageMissingEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun StyleImageMissing.toStyleImageMissingEventData() = StyleImageMissingEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time,
  id = this.imageId
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [StyleImageUnusedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun StyleImageRemoveUnused.toStyleImageUnusedEventData() = StyleImageUnusedEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time,
  id = this.imageId
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [SourceAddedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun SourceAdded.toSourceAddedEventData() = SourceAddedEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time,
  id = this.sourceId
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [SourceRemovedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun SourceRemoved.toSourceRemovedEventData() = SourceRemovedEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time,
  id = this.sourceId
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [RenderFrameStartedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
@Suppress("UNCHECKED_CAST")
fun RenderFrameStarted.toRenderFrameStartedEventData() = RenderFrameStartedEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [RenderFrameFinishedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun RenderFrameFinished.toRenderFrameFinishedEventData() = RenderFrameFinishedEventData(
  begin = this.timeInterval.begin.time,
  end = this.timeInterval.end.time,
  renderMode = RenderMode.valueOf(this.renderMode.name),
  needsRepaint = this.needsRepaint,
  placementChanged = this.placementChanged
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return a parsed [CameraChangedEventData].
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun CameraChanged.toCameraChangedEventData() = CameraChangedEventData(
  begin = this.timestamp.time,
  end = this.timestamp.time
)

/**
 * Extension function to map the newly introduced event to the legacy one.
 * @return [TileId]
 */
@Deprecated(
  message = "This extension function is deprecated, and will be removed in next major release.",
  level = DeprecationLevel.WARNING
)
fun CanonicalTileID.toTileId() = TileID(this.z.toLong(), this.x.toLong(), this.y.toLong())