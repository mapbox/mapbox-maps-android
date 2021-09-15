package com.mapbox.maps.plugin.viewannotation;

import androidx.annotation.NonNull;
import com.mapbox.bindgen.RecordUtils;
import com.mapbox.maps.ScreenCoordinate;

import java.util.Objects;
import java.io.Serializable;

/** Data needed to correctly position the single view annotation on screen. */
public final class ViewAnnotationPositionDescriptor implements Serializable {

    @NonNull
    private final String identifier;
    @NonNull
    private final ScreenCoordinate leftTopCoordinate;

    public ViewAnnotationPositionDescriptor(@NonNull String identifier,
                                            @NonNull ScreenCoordinate leftTopCoordinate) {
        this.identifier = identifier;
        this.leftTopCoordinate = leftTopCoordinate;
    }

    /** Unique id in order to lookup an actual view in platform SDKs. */
    @NonNull
    public String getIdentifier() {
        return identifier;
    }

    /** Left-top screen coordinate in px for view annotation. */
    @NonNull
    public ScreenCoordinate getLeftTopCoordinate() {
        return leftTopCoordinate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ViewAnnotationPositionDescriptor other = (ViewAnnotationPositionDescriptor) object;

        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (!Objects.equals(this.leftTopCoordinate, other.leftTopCoordinate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.identifier,
                this.leftTopCoordinate);
    }

    @Override
    public String toString() {
        return "[" + "identifier: " + RecordUtils.fieldToString(identifier) + ", " + "leftTopCoordinate: " + RecordUtils.fieldToString(leftTopCoordinate) + "]";
    }

}
