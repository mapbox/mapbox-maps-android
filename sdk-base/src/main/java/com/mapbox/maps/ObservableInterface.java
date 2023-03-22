package com.mapbox.maps;

import androidx.annotation.NonNull;
import java.util.List;

/**
 * The `observable` interface provides basic Publish&Subscribe functionality. Classes that extend
 * this functionality and capable of generating events, have to specify event types and
 * corresponding data format for an event.
 */
@MapboxInternal
public interface ObservableInterface {
    /**
     * Subscribes an `observer` to a provided array of event types.
     * The `observable` will hold a strong reference to an `observer` instance, therefore,
     * in order to stop receiving notifications, caller must call `unsubscribe` with an
     * `observer` instance used for an initial subscription.
     *
     * @param observer An `observer` that will be subscribed to a given events.
     * @param events An array of event types to be subscribed to.
     */
    void subscribe(@NonNull Observer observer, @NonNull List<String> events);
    /**
     * Unsubscribes an `observer` from a provided array of event types.
     *
     * @param observer An `observer` that will be unsubscribed from a given events.
     * @param events An array of event types to be unsubscribed from.
     */
    void unsubscribe(@NonNull Observer observer, @NonNull List<String> events);
    /**
     * Unsubscribes an `observer` from all events.
     *
     * @param observer An `observer` that will be unsubscribed from all previously subscribed events.
     */
    void unsubscribe(@NonNull Observer observer);
}
