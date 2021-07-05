package com.mapbox.common;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Logger.class)
public class ShadowLogger {

    @Implementation
    public static void e(@Nullable String tag, @NonNull String message) {
        Log.e(message, tag);
    }

    @Implementation
    public static void d(@Nullable String tag, @NonNull String message) {
        Log.d(message, tag);
    }

    @Implementation
    public static void w(@Nullable String tag, @NonNull String message) {
        Log.w(message, tag);
    }

    @Implementation
    public static void i(@Nullable String tag, @NonNull String message) {
        Log.i(message, tag);
    }
}