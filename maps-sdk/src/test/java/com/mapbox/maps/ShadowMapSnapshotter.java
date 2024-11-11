package com.mapbox.maps;

import androidx.annotation.*;

import com.mapbox.maps.*;

import org.robolectric.annotation.*;

@Implements(MapSnapshotter.class)
public class ShadowMapSnapshotter {

    @Implementation
    public void setStyleJson(){

    }

    @Implementation
    public void setCamera(@NonNull CameraOptions cameraOptions){

    }
}