package com.mapbox.maps;

class NativeMapProvider {
  static MapInterface getNativeMap(MapView mapView) {
    return MapProvider.INSTANCE.getNativeMap(mapView);
  }
}
