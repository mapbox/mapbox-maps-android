package com.mapbox.maps.renderer;

import android.os.HandlerThread;

import org.robolectric.annotation.Implements;

@Implements(value = HandlerThread.class)
public class ShadowHandlerThread extends ShadowThread {
}
