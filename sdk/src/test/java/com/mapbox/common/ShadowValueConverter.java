package com.mapbox.common;

import androidx.annotation.NonNull;

import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.ExpectedFactory;
import com.mapbox.bindgen.Value;

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
    public static Expected<String, Value> fromJson(@NonNull String var0) {
        return ExpectedFactory.createValue(new Value(var0));
    }

    @Implementation
    @NonNull
    public static String toJson(@NonNull Value value) {
        return "{\"data-source\":\"network\",\"request\":{\"loading-method\":[\"network\"],\"url\":\"https://api.mapbox.com\",\"kind\":\"tile\",\"priority\":\"regular\"},\"response\":{\"etag\":\"d8abd8d10bee6b45b4dbf5c05496587a\",\"must-revalidate\":false,\"no-content\":false,\"modified\":\"Mon, 05 Oct 2020 14:23:52 GMT\",\"source\":\"network\",\"not-modified\":false,\"expires\":\"Thu, 15 Oct 2020 14:32:23 GMT\",\"size\":181576,\"error\":{\"reason\":\"not-found\",\"message\":\"error message\"}},\"cancelled\":false}";
    }
}
