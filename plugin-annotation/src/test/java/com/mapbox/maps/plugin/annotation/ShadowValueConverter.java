package com.mapbox.maps.plugin.annotation;

import androidx.annotation.NonNull;

import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.ExpectedFactory;
import com.mapbox.bindgen.Value;
import com.mapbox.common.ValueConverter;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of ValueConverter, this shadow ValueConverter
 * will be used for the Robolectric unit tests.
 */
@Implements(ValueConverter.class)
public class ShadowValueConverter {

    @Implementation
    @NonNull
    public static Expected<Value, String> fromJson(@NonNull String var0) {
        return ExpectedFactory.createValue();
    }

    @Implementation
    @NonNull
    public static String toJson(@NonNull Value var0) {
        return "";
    }
}
