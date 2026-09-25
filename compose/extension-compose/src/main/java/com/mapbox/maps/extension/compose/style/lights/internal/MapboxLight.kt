package com.mapbox.maps.extension.compose.style.lights.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.lights.generated.AmbientLightState
import com.mapbox.maps.extension.compose.style.lights.generated.DirectionalLightState
import com.mapbox.maps.extension.compose.style.lights.generated.FlatLightState
import com.mapbox.maps.logD
import com.mapbox.maps.logE

internal sealed class MapboxLight {
  internal class DynamicLight(
    private val directionalLightState: List<DirectionalLightState>,
    private val ambientLightState: AmbientLightState
  ) : MapboxLight() {
    @Composable
    override fun BindToMap(mapboxMap: MapboxMap) {
      LaunchedEffect(mapboxMap) {
        val lights =
          directionalLightState.map { Value(it.getProperties()) } + Value(ambientLightState.getProperties())
        mapboxMap.setStyleLights(Value(lights))
          .onError {
            logE(TAG, "setStyleLights error: $it")
          }
          .onValue {
            logD(TAG, "setStyleLights: $lights")
          }
      }
      directionalLightState.forEach {
        it.UpdateProperties(mapboxMap = mapboxMap)
      }
      ambientLightState.UpdateProperties(mapboxMap = mapboxMap)
    }
  }

  internal class FlatLight(private val flatLightState: FlatLightState) : MapboxLight() {
    @Composable
    override fun BindToMap(mapboxMap: MapboxMap) {
      LaunchedEffect(mapboxMap) {
        val lights = listOf(Value(flatLightState.getProperties()))
        mapboxMap.setStyleLights(Value(lights))
          .onError {
            logE(TAG, "setStyleLights error: $it")
          }
          .onValue {
            logD(TAG, "setStyleLights: $lights")
          }
      }
      flatLightState.UpdateProperties(mapboxMap = mapboxMap)
    }
  }

  object Default : MapboxLight() {
    @Composable
    override fun BindToMap(mapboxMap: MapboxMap) {
      LaunchedEffect(mapboxMap) {
        mapboxMap.setStyleLights(Value.nullValue())
          .onError {
            logE(TAG, "setStyleLights error: $it")
          }
          .onValue {
            logD(TAG, "setStyleLights: null")
          }
      }
    }
  }

  object NoOp : MapboxLight() {
    @Composable
    override fun BindToMap(mapboxMap: MapboxMap) {
      // no-op
    }
  }

  @Composable
  internal abstract fun BindToMap(mapboxMap: MapboxMap)

  private companion object {
    private const val TAG = "MapboxLight"
  }
}