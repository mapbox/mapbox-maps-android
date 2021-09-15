package com.mapbox.maps.plugin.viewannotation;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Callback to get data needed to correctly position view annotations on the screen.
 *
 * Array describing how to position visible view annotations. Order is critical in terms of correct placing views above each other.
 */
public interface ViewAnnotationsPositionCallback {
    void run(@NonNull List<ViewAnnotationPositionDescriptor> positions);
}
