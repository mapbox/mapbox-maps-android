package com.mapbox.maps

import androidx.annotation.RestrictTo

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

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class DelegatingViewAnnotationPositionDescriptor(
  identifier: String,
  width: Int,
  height: Int,
  leftTopCoordinate: ScreenCoordinate,
) : ViewAnnotationPositionDescriptor(identifier, width, height, leftTopCoordinate)