package com.mapbox.maps.renderer;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = Thread.class)
public class ShadowThread {

    boolean isAlive = false;

    @Implementation
    public final boolean isAlive() {
        return isAlive;
    }
}
