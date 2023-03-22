package com.mapbox.maps;

class NativeMapProvider {
  static Map getNativeMap(MapView mapView) {
    return MapProvider.INSTANCE.getNativeMapCore(mapView);
  }
}
