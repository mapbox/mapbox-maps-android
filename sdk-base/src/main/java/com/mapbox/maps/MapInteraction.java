package com.mapbox.maps;

import androidx.annotation.NonNull;

/**
 * Base class for interactions. Should not be used directly.
 */
@MapboxExperimental
public abstract class MapInteraction {

    /**
     * Actually initialized in constructor so should never be nullable.
     */
    @NonNull
    Interaction coreInteraction;
}
