package com.mapbox.maps

import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point

internal abstract class DelegatingViewAnnotationPositionsUpdateListener : ViewAnnotationPositionsUpdateListener {

  final override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    onDelegatingViewAnnotationPositionsUpdate(
      positions.map {
        DelegatingViewAnnotationPositionDescriptor(
          identifier = it.identifier,
          width = it.width,
          height = it.height,
          leftTopCoordinate = it.leftTopCoordinate,
          anchorCoordinate = it.anchorCoordinate,
          anchorConfig = it.anchorConfig,
        )
      }
    )
  }

  abstract fun onDelegatingViewAnnotationPositionsUpdate(positions: List<DelegatingViewAnnotationPositionDescriptor>)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class DelegatingViewAnnotationPositionDescriptor(
  identifier: String,
  width: Double,
  height: Double,
  leftTopCoordinate: ScreenCoordinate,
  anchorCoordinate: Point,
  anchorConfig: ViewAnnotationAnchorConfig,
) : ViewAnnotationPositionDescriptor(
  /* identifier = */ identifier,
  /* width = */ width,
  /* height = */ height,
  /* leftTopCoordinate = */ leftTopCoordinate,
  /* anchorCoordinate = */ anchorCoordinate,
  /* anchorConfig = */ anchorConfig
)