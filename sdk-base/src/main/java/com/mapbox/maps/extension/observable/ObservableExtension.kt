package com.mapbox.maps.extension.observable

import com.mapbox.bindgen.Value
import com.mapbox.maps.Event
import com.mapbox.maps.MapEvents
import com.mapbox.maps.ObservableInterface
import com.mapbox.maps.Observer
import com.mapbox.maps.extension.observable.eventdata.*
import com.mapbox.maps.extension.observable.eventdata.ResourceEventData
import com.mapbox.maps.extension.observable.model.*
import java.util.*

/**
 * Subscribes an Observer for of event type "resource-request".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeResourceRequest(observer: Observer) =
  subscribe(observer, listOf(MapEvents.RESOURCE_REQUEST))

/**
 * Unsubscribe an Observer for event types "resource-request".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeResourceRequest(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.RESOURCE_REQUEST))

// Camera events
/**
 * Subscribes an Observer for of event type "camera-changed".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeCameraChange(observer: Observer) =
  subscribe(observer, listOf(MapEvents.CAMERA_CHANGED))

/**
 * Unsubscribe an Observer for event types "camera-changed".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeCameraChange(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.CAMERA_CHANGED))

// Map events
/**
 * Subscribes an Observer for of event type "map-idle".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeMapIdle(observer: Observer) =
  subscribe(observer, listOf(MapEvents.MAP_IDLE))

/**
 * Unsubscribe an Observer for event types "map-idle".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeMapIdle(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.MAP_IDLE))

/**
 * Subscribes an Observer for of event type "map-loading-error".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeMapLoadingError(observer: Observer) =
  subscribe(observer, listOf(MapEvents.MAP_LOADING_ERROR))

/**
 * Unsubscribe an Observer for event types "map-loading-error".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeMapLoadingError(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.MAP_LOADING_ERROR))

/**
 * Subscribes an Observer for of event type "map-loaded".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeMapLoaded(observer: Observer) =
  subscribe(observer, listOf(MapEvents.MAP_LOADED))

/**
 * Unsubscribe an Observer for event types "map-loaded".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeMapLoaded(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.MAP_LOADED))

// Style events
/**
 * Subscribes an Observer for of event type "style-data-loaded".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeStyleDataLoaded(observer: Observer) =
  subscribe(observer, listOf(MapEvents.STYLE_DATA_LOADED))

/**
 * Unsubscribe an Observer for event types "style-data-loaded".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeStyleDataFinished(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.STYLE_DATA_LOADED))

/**
 * Subscribes an Observer for of event type "style-loaded".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeStyleLoaded(observer: Observer) =
  subscribe(observer, listOf(MapEvents.STYLE_LOADED))

/**
 * Unsubscribe an Observer for event types "style-loaded".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeStyleLoaded(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.STYLE_LOADED))

/**
 * Subscribes an Observer for of event type "style-image-missing".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeStyleImageMissing(observer: Observer) =
  subscribe(observer, listOf(MapEvents.STYLE_IMAGE_MISSING))

/**
 * Unsubscribe an Observer for event types "style-image-missing".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeStyleImageMissing(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.STYLE_IMAGE_MISSING))

/**
 * Subscribes an Observer for of event type "style-image-remove-unused".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeStyleImageUnused(observer: Observer) =
  subscribe(observer, listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))

/**
 * Unsubscribe an Observer for event types "style-image-remove-unused".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeStyleImageUnused(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))

// Render frame events
/**
 * Subscribes an Observer for of event type "render-frame-started".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeRenderFrameStarted(observer: Observer) =
  subscribe(observer, listOf(MapEvents.RENDER_FRAME_STARTED))

/**
 * Unsubscribe an Observer for event types "render-frame-started".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeRenderFrameStarted(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.RENDER_FRAME_STARTED))

/**
 * Subscribes an Observer for of event type "render-frame-finished".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeRenderFrameFinished(observer: Observer) =
  subscribe(observer, listOf(MapEvents.RENDER_FRAME_FINISHED))

/**
 * Unsubscribe an Observer for event types "render-frame-finished".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeRenderFrameFinished(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.RENDER_FRAME_FINISHED))

/**
 * Subscribes an Observer for of event type "source-added".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeSourceAdded(observer: Observer) =
  subscribe(observer, listOf(MapEvents.SOURCE_ADDED))

/**
 * Unsubscribe an Observer for event types "source-added".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeSourceAdded(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.SOURCE_ADDED))

/**
 * Subscribes an Observer for of event type "source-data-loaded".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeSourceDataLoaded(observer: Observer) =
  subscribe(observer, listOf(MapEvents.SOURCE_DATA_LOADED))

/**
 * Unsubscribe an Observer for event types "source-data-loaded".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeSourceDataLoaded(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.SOURCE_DATA_LOADED))

/**
 * Subscribes an Observer for of event type "source-removed".
 *
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeSourceRemoved(observer: Observer) =
  subscribe(observer, listOf(MapEvents.SOURCE_REMOVED))

/**
 * Unsubscribe an Observer for event types "source-removed".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeSourceRemoved(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.SOURCE_REMOVED))

// Parser extension functions for event data.
/**
 * Get the parsed event data for resource-request event.
 * @return a parsed ResourceEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getResourceEventData(): ResourceEventData {
  val map = data.contents as Map<String, Value>
  val requestMap = map.nonNullMap(REQUEST)
  val responseMap = map.nullableMap(RESPONSE)
  val errorMap = responseMap?.nullableMap(ERROR)
  return ResourceEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    dataSource = DataSourceType.valueOf(map.validEnumValue(DATA_SOURCE)),
    request = Request(
      loadingMethod = requestMap.nonNullList(LOADING_METHOD),
      url = requestMap.nonNullString(URL),
      kind = RequestType.valueOf(requestMap.validEnumValue(KIND)),
      priority = RequestPriority.valueOf(requestMap.validEnumValue(PRIORITY))
    ),
    response = if (responseMap == null) null else Response(
      eTag = responseMap.nullableString(E_TAG),
      mustRevalidate = responseMap.nonNullBoolean(MUST_REVALIDATE),
      noContent = responseMap.nonNullBoolean(NO_CONTENT),
      modified = responseMap.nullableString(MODIFIED),
      source = ResponseSourceType.valueOf(responseMap.validEnumValue(SOURCE)),
      notModified = responseMap.nonNullBoolean(NOT_MODIFIED),
      expires = responseMap.nullableString(EXPIRES),
      size = responseMap.nonNullInt(SIZE),
      error = if (errorMap == null) null else Error(
        reason = ResponseErrorReason.valueOf(errorMap.validEnumValue(REASON)),
        message = errorMap.nonNullString(MESSAGE)
      )
    ),
    cancelled = map.nonNullBoolean(CANCELLED)
  )
}

/**
 * Get the parsed event data for map loaded event.
 * @return a parsed MapLoadedEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getMapLoadedEventData(): MapLoadedEventData {
  val map = data.contents as Map<String, Value>
  return MapLoadedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END)
  )
}

/**
 * Get the parsed event data for map loading error event.
 * @return a parsed MapLoadingErrorEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getMapLoadingErrorEventData(): MapLoadingErrorEventData {
  val map = data.contents as Map<String, Value>
  val tileIDMap = map.nullableMap(TILE_ID)
  return MapLoadingErrorEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    type = MapLoadErrorType.valueOf(map.validEnumValue(TYPE)),
    message = map.nonNullString(MESSAGE),
    sourceId = map.nullableString(SOURCE_ID),
    tileId = if (tileIDMap == null) null else TileID(
      zoom = tileIDMap.nonNullLong(Z),
      x = tileIDMap.nonNullLong(X),
      y = tileIDMap.nonNullLong(Y)
    )
  )
}

/**
 * Get the parsed event data for map idle event.
 * @return a parsed MapIdleEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getMapIdleEventData(): MapIdleEventData {
  val map = data.contents as Map<String, Value>
  return MapIdleEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END)
  )
}

/**
 * Get the parsed event data for style data loaded event.
 * @return a parsed StyleDataLoadedEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getStyleDataLoadedEventData(): StyleDataLoadedEventData {
  val map = data.contents as Map<String, Value>
  return StyleDataLoadedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    type = StyleDataType.valueOf(map.validEnumValue(TYPE))
  )
}

/**
 * Get the parsed event data for style loaded event.
 * @return a parsed StyleLoadedEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getStyleLoadedEventData(): StyleLoadedEventData {
  val map = data.contents as Map<String, Value>
  return StyleLoadedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
  )
}

/**
 * Get the parsed event data for source data loaded event.
 * @return a parsed SourceDataLoadedEventData object.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getSourceDataLoadedEventData(): SourceDataLoadedEventData {
  val map = data.contents as Map<String, Value>
  val tileIDMap = map.nullableMap(TILE_ID)

  return SourceDataLoadedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    id = map.nonNullString(ID),
    type = SourceDataType.valueOf(map.validEnumValue(TYPE)),
    loaded = map.nullableBoolean(LOADED),
    tileID = if (tileIDMap == null) null else TileID(
      zoom = tileIDMap.nonNullLong(Z),
      x = tileIDMap.nonNullLong(X),
      y = tileIDMap.nonNullLong(Y)
    ),
    dataId = map.nullableString("data-id"),
  )
}

/**
 * Get the parsed event data for style missing event.
 * @return a parsed StyleImageMissingEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getStyleImageMissingEventData(): StyleImageMissingEventData {
  val map = data.contents as Map<String, Value>
  return StyleImageMissingEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    id = map.nonNullString(ID)
  )
}

/**
 * Get the parsed event data for style image unused event.
 * @return a parsed StyleImageUnusedEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getStyleImageUnusedEventData(): StyleImageUnusedEventData {
  val map = data.contents as Map<String, Value>
  return StyleImageUnusedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    id = map.nonNullString(ID)
  )
}

/**
 * Get the parsed event data for source added event.
 * @return a parsed IDStringEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getSourceAddedEventData(): SourceAddedEventData {
  val map = data.contents as Map<String, Value>
  return SourceAddedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    id = map.nonNullString(ID)
  )
}

/**
 * Get the parsed event data for source removed event.
 * @return a parsed SourceRemovedEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getSourceRemovedEventData(): SourceRemovedEventData {
  val map = data.contents as Map<String, Value>
  return SourceRemovedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    id = map.nonNullString(ID)
  )
}

/**
 * Get the parsed event data for render frame started event.
 * @return a parsed RenderFrameStartedEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getRenderFrameStartedEventData(): RenderFrameStartedEventData {
  val map = data.contents as Map<String, Value>
  return RenderFrameStartedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END)
  )
}

/**
 * Get the parsed event data for render frame finished event.
 * @return a parsed RenderFrameFinishedEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getRenderFrameFinishedEventData(): RenderFrameFinishedEventData {
  val map = data.contents as Map<String, Value>
  return RenderFrameFinishedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END),
    renderMode = RenderMode.valueOf(map.validEnumValue(RENDER_MODE)),
    needsRepaint = map.nonNullBoolean(NEEDS_REPAINT),
    placementChanged = map.nonNullBoolean(PLACEMENT_CHANGED)
  )
}

/**
 * Get the parsed event data for camera changed event.
 * @return a parsed CameraChangedEventData.
 */
@Suppress("UNCHECKED_CAST")
fun Event.getCameraChangedEventData(): CameraChangedEventData {
  val map = data.contents as Map<String, Value>
  return CameraChangedEventData(
    begin = map.nonNullLong(BEGIN),
    end = map.nullableLong(END)
  )
}

internal fun Map<String, Value>.nonNullLong(name: String): Long {
  return this[name]!!.contents as Long
}

internal fun Map<String, Value>.nullableLong(name: String): Long? {
  return this[name]?.contents as Long?
}

internal fun Map<String, Value>.nonNullString(name: String): String {
  return this[name]!!.contents as String
}

internal fun Map<String, Value>.validEnumValue(name: String): String {
  return nonNullString(name).toUpperCase(Locale.US).replace(DASH, UNDERLINE)
}

internal fun Map<String, Value>.nullableString(name: String): String? {
  return this[name]?.contents as String?
}

internal fun Map<String, Value>.nonNullInt(name: String): Int {
  return (this[name]!!.contents as Long).toInt()
}

internal fun Map<String, Value>.nullableInt(name: String): Int? {
  return (this[name]?.contents as Int?)?.toInt()
}

internal fun Map<String, Value>.nonNullBoolean(name: String): Boolean {
  return this[name]!!.contents as Boolean
}

internal fun Map<String, Value>.nullableBoolean(name: String): Boolean? {
  return this[name]?.contents as Boolean?
}

@Suppress("UNCHECKED_CAST")
internal fun Map<String, Value>.nonNullMap(name: String): Map<String, Value> {
  return this[name]!!.contents as Map<String, Value>
}

@Suppress("UNCHECKED_CAST")
internal fun Map<String, Value>.nullableMap(name: String): Map<String, Value>? {
  return this[name]?.contents as Map<String, Value>?
}

@Suppress("UNCHECKED_CAST")
internal fun Map<String, Value>.nonNullList(name: String): List<String> {
  return (this[name]!!.contents as List<Value>).map { it.toString() }
}

private const val UNDERLINE = "_"
private const val DASH = "-"

// Base type
private const val BEGIN = "begin"
private const val END = "end"

// MapLoadingErrorEventData
private const val TYPE = "type"
private const val MESSAGE = "message"
private const val SOURCE_ID = "source-id"
private const val TILE_ID = "tile-id"

// TileID
private const val X = "x"
private const val Y = "y"
private const val Z = "z"

// ResourceEventData
private const val DATA_SOURCE = "data-source"
private const val REQUEST = "request"
private const val RESPONSE = "response"
private const val CANCELLED = "cancelled"

// Request
private const val LOADING_METHOD = "loading-method"
private const val URL = "url"
private const val KIND = "kind"
private const val PRIORITY = "priority"

// Response
private const val E_TAG = "etag"
private const val MUST_REVALIDATE = "must-revalidate"
private const val NO_CONTENT = "no-content"
private const val MODIFIED = "modified"
private const val SOURCE = "source"
private const val NOT_MODIFIED = "not-modified"
private const val EXPIRES = "expires"
private const val SIZE = "size"
private const val ERROR = "error"

// Error
private const val REASON = "reason"

// SourceDataLoadedEventData
private const val ID = "id"
private const val LOADED = "loaded"

// RenderFrameFinishedEventData(
private const val RENDER_MODE = "render-mode"
private const val NEEDS_REPAINT = "needs-repaint"
private const val PLACEMENT_CHANGED = "placement-changed"