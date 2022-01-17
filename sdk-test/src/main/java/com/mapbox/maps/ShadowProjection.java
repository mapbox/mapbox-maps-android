package com.mapbox.maps;

import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of Projection, this shadow class
 * will be used for the Robolectric unit tests.
 */
@Implements(Projection.class)
public class ShadowProjection {
}
