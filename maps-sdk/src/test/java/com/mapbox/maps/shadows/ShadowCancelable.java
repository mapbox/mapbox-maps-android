package com.mapbox.maps.shadows;


import com.mapbox.common.Cancelable;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Cancelable.class)
public class ShadowCancelable {
    @Implementation
    public void cancel() {

    }
}
