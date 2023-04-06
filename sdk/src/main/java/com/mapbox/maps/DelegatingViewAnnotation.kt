package com.mapbox.maps

internal abstract class DelegatingViewAnnotationPositionsUpdateListener : ViewAnnotationPositionsUpdateListener {

  final override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    onDelegatingViewAnnotationPositionsUpdate(
      positions.map {
        DelegatingViewAnnotationPositionDescriptor(
          it.identifier,
          it.width,
          it.height,
          it.leftTopCoordinate
        )
      }
    )
  }

  abstract fun onDelegatingViewAnnotationPositionsUpdate(positions: List<DelegatingViewAnnotationPositionDescriptor>)
}

internal class DelegatingViewAnnotationPositionDescriptor(
  identifier: String,
  width: Int,
  height: Int,
  leftTopCoordinate: ScreenCoordinate,
) : ViewAnnotationPositionDescriptor(identifier, width, height, leftTopCoordinate)