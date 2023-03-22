package com.mapbox.maps;

import androidx.annotation.NonNull;
import com.mapbox.geojson.Point;
import java.util.List;

/** An image snapshot of a map rendered by `map snapshotter`. */
@MapboxInternal
public interface MapSnapshotInterface {
    /**
     * Calculate screen coordinate on the snapshot from geographical `coordinate`.
     *
     * @param coordinate A geographical `coordinate`.
     * @return A `screen coordinate` measured in `platform pixels` on the snapshot for geographical `coordinate`.
     */
    @NonNull
    ScreenCoordinate screenCoordinate(@NonNull Point coordinate);
    /**
     * Calculate geographical coordinates from a point on the snapshot.
     *
     * @param screenCoordinate A `screen coordinate` on the snapshot in `platform pixels`.
     * @return A geographical `coordinate` for a `screen coordinate` on the snapshot.
     */
    @NonNull
    Point coordinate(@NonNull ScreenCoordinate screenCoordinate);
    /**
     * Get list of attributions for the sources in this snapshot.
     *
     * @return A list of attributions for the sources in this snapshot.
     */
    @NonNull
    List<String> attributions();
    /**
     * Get the rendered snapshot `image`.
     *
     * @return A rendered snapshot `image`.
     */
    @NonNull
    Image image();
}
