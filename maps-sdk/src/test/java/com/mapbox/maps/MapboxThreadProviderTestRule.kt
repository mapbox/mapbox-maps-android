package com.mapbox.maps

import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.MainMapboxThreadProvider
import com.mapbox.common.MapboxThreadProvider
import com.mapbox.common.mapboxThreadProvider
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Initializes [mapboxThreadProvider] with [provider] (a [MainMapboxThreadProvider] by default)
 * before the test runs.
 *
 * In production, [mapboxThreadProvider] is initialized as a side effect of `androidx.startup`
 * running `CoreInitializer` (which touches the `mapboxThread` lazy singleton) before any app code
 * can construct a `MapView`. Unit tests construct SDK classes (`MapboxMap`, `MapSurface`,
 * `Snapshotter`, ...) directly, bypassing that startup chain, so `mapboxThreadProvider` is never
 * set. Apply this rule to any test whose setup calls `mapboxThreadProvider.getOrCreate()` (either
 * directly or via the class under test), or it fails with
 * `UninitializedPropertyAccessException`. Pass a different [provider] (e.g. [com.mapbox.common.OneMapboxThreadProvider])
 * to exercise the class under test against a non-main [com.mapbox.common.MapboxThread].
 */
@OptIn(MapboxExperimental::class)
class MapboxThreadProviderTestRule(
  private val provider: MapboxThreadProvider = MainMapboxThreadProvider()
) : TestRule {
  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        mapboxThreadProvider = provider
        base.evaluate()
      }
    }
  }
}