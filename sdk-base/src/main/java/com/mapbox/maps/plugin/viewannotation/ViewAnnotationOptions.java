package com.mapbox.maps.plugin.viewannotation;

import androidx.annotation.Nullable;
import com.mapbox.bindgen.RecordUtils;
import com.mapbox.geojson.Geometry;

import java.util.Objects;
import java.io.Serializable;

/** Various options needed for displaying view annotation. */
public final class ViewAnnotationOptions implements Serializable {

    @Nullable
    private final Geometry geometry;
    private final int width;
    private final int height;
    private final boolean allowViewAnnotationsCollision;
    @Nullable
    private final ViewAnnotationAnchor anchor;
    private final int offsetX;
    private final int offsetY;
    private final boolean selected;
    @Nullable
    private final String featureIdentifier;

    private ViewAnnotationOptions(@Nullable Geometry geometry,
                                  int width,
                                  int height,
                                  boolean allowViewAnnotationsCollision,
                                  @Nullable ViewAnnotationAnchor anchor,
                                  int offsetX,
                                  int offsetY,
                                  boolean selected,
                                  @Nullable String featureIdentifier) {
        this.geometry = geometry;
        this.width = width;
        this.height = height;
        this.allowViewAnnotationsCollision = allowViewAnnotationsCollision;
        this.anchor = anchor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.selected = selected;
        this.featureIdentifier = featureIdentifier;
    }

    private ViewAnnotationOptions(@Nullable Geometry geometry,
                                  @Nullable ViewAnnotationAnchor anchor,
                                  @Nullable String featureIdentifier) {
        this.geometry = geometry;
        this.anchor = anchor;
        this.featureIdentifier = featureIdentifier;
        this.width = 0;
        this.height = 0;
        this.allowViewAnnotationsCollision = true;
        this.offsetX = 0;
        this.offsetY = 0;
        this.selected = false;
    }

    /** Geometry the view annotation is bound to. */
    @Nullable
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * View annotation width in pixels.
     * If not set will be taken from the view layout the user has provided.
     */
    public int getWidth() {
        return width;
    }

    /**
     * View annotation height in pixels.
     * If not set will be taken from the view layout the user has provided.
     */
    public int getHeight() {
        return height;
    }

    /**
     * If View annotations are allowed to be drawn above each other or
     * the newest only should be drawn.
     * Defaults to true.
     */
    public boolean getAllowViewAnnotationsCollision() {
        return allowViewAnnotationsCollision;
    }

    /**
     * Anchor describing where the view annotation will be located relatively to given geometry.
     * Defaults to CENTER.
     */
    @Nullable
    public ViewAnnotationAnchor getAnchor() {
        return anchor;
    }

    /**
     * Extra X offset in pixels.
     * Providing positive value moves view annotation to the right while negative moves it to the left.
     * Defaults to 0.
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * Extra Y offset in pixels.
     * Providing positive value moves view annotation to the top while negative moves it to the bottom.
     * Defaults to 0.
     */
    public int getOffsetY() {
        return offsetY;
    }

    /** Specifies if this view annotation is selected meaning it should be placed on top of others. */
    public boolean getSelected() {
        return selected;
    }

    /**
     * Optional style icon id connected to given view annotation.
     *
     * View annotation's visibility behaviour becomes tied to feature visibility where feature could represent icon / text etc.
     * E.g. if feature is not visible view annotation also becomes not visible.
     */
    @Nullable
    public String getFeatureIdentifier() {
        return featureIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ViewAnnotationOptions other = (ViewAnnotationOptions) object;

        if (!Objects.equals(this.geometry, other.geometry)) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.allowViewAnnotationsCollision != other.allowViewAnnotationsCollision) {
            return false;
        }
        if (!Objects.equals(this.anchor, other.anchor)) {
            return false;
        }
        if (this.offsetX != other.offsetX) {
            return false;
        }
        if (this.offsetY != other.offsetY) {
            return false;
        }
        if (this.selected != other.selected) {
            return false;
        }
        if (!Objects.equals(this.featureIdentifier, other.featureIdentifier)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.geometry,
                Integer.valueOf(this.width),
                Integer.valueOf(this.height),
                Boolean.valueOf(this.allowViewAnnotationsCollision),
                this.anchor,
                Integer.valueOf(this.offsetX),
                Integer.valueOf(this.offsetY),
                Boolean.valueOf(this.selected),
                this.featureIdentifier);
    }

    public static final class Builder {

        @Nullable
        private Geometry geometry;
        private int width = 0;
        private int height = 0;
        private boolean allowViewAnnotationsCollision = true;
        @Nullable
        private ViewAnnotationAnchor anchor;
        private int offsetX = 0;
        private int offsetY = 0;
        private boolean selected = false;
        @Nullable
        private String featureIdentifier;

        public Builder() {
        }

        /** Geometry the view annotation is bound to. */
        public ViewAnnotationOptions.Builder geometry(@Nullable Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        /**
         * View annotation width in pixels.
         * If not set will be taken from the view layout the user has provided.
         */
        public ViewAnnotationOptions.Builder width(int width) {
            this.width = width;
            return this;
        }

        /**
         * View annotation height in pixels.
         * If not set will be taken from the view layout the user has provided.
         */
        public ViewAnnotationOptions.Builder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * If View annotations are allowed to be drawn above each other or
         * the newest only should be drawn.
         * Defaults to true.
         */
        public ViewAnnotationOptions.Builder allowViewAnnotationsCollision(boolean allowViewAnnotationsCollision) {
            this.allowViewAnnotationsCollision = allowViewAnnotationsCollision;
            return this;
        }

        /**
         * Anchor describing where the view annotation will be located relatively to given geometry.
         * Defaults to CENTER.
         */
        public ViewAnnotationOptions.Builder anchor(@Nullable ViewAnnotationAnchor anchor) {
            this.anchor = anchor;
            return this;
        }

        /**
         * Extra X offset in pixels.
         * Providing positive value moves view annotation to the right while negative moves it to the left.
         * Defaults to 0.
         */
        public ViewAnnotationOptions.Builder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        /**
         * Extra Y offset in pixels.
         * Providing positive value moves view annotation to the top while negative moves it to the bottom.
         * Defaults to 0.
         */
        public ViewAnnotationOptions.Builder offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        /** Specifies if this view annotation is selected meaning it should be placed on top of others. */
        public ViewAnnotationOptions.Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        /**
         * Optional style icon id connected to given view annotation.
         *
         * View annotation's visibility behaviour becomes tied to feature visibility where feature could represent icon / text etc.
         * E.g. if feature is not visible view annotation also becomes not visible.
         */
        public ViewAnnotationOptions.Builder featureIdentifier(@Nullable String featureIdentifier) {
            this.featureIdentifier = featureIdentifier;
            return this;
        }

        public ViewAnnotationOptions build() {

            return new ViewAnnotationOptions(geometry, width, height, allowViewAnnotationsCollision, anchor, offsetX, offsetY, selected, featureIdentifier);
        }

    }

    public ViewAnnotationOptions.Builder toBuilder() {
        return new ViewAnnotationOptions.Builder()
                .geometry(geometry)
                .width(width)
                .height(height)
                .allowViewAnnotationsCollision(allowViewAnnotationsCollision)
                .anchor(anchor)
                .offsetX(offsetX)
                .offsetY(offsetY)
                .selected(selected)
                .featureIdentifier(featureIdentifier);
    }

    @Override
    public String toString() {
        return "[" + "geometry: " + RecordUtils.fieldToString(geometry) + ", " + "width: " + RecordUtils.fieldToString(width) + ", " + "height: " + RecordUtils.fieldToString(height) + ", " + "allowViewAnnotationsCollision: " + RecordUtils.fieldToString(allowViewAnnotationsCollision) + ", " + "anchor: " + RecordUtils.fieldToString(anchor) + ", " + "offsetX: " + RecordUtils.fieldToString(offsetX) + ", " + "offsetY: " + RecordUtils.fieldToString(offsetY) + ", " + "selected: " + RecordUtils.fieldToString(selected) + ", " + "featureIdentifier: " + RecordUtils.fieldToString(featureIdentifier) + "]";
    }

}
