package com.mapbox.maps;

import androidx.annotation.NonNull;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Map.class)
public class ShadowMap {
    @Implementation
    public static void clearData(@NonNull ResourceOptions resourceOptions, @NonNull AsyncOperationResultCallback callback) {
        // no-ops
    }
}