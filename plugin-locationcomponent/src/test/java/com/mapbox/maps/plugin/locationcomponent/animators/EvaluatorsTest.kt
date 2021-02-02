package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.geojson.Point
import org.junit.Assert
import org.junit.Test

class EvaluatorsTest {

  @Test
  fun doubleEvaluatorTest() {
    val evaluator = Evaluators.DOUBLE
    Assert.assertEquals(0.0, evaluator.evaluate(0.0f, START_DOUBLE, END_DOUBLE), EPS)
    Assert.assertEquals(5.0, evaluator.evaluate(0.5f, START_DOUBLE, END_DOUBLE), EPS)
    Assert.assertEquals(10.0, evaluator.evaluate(1.0f, START_DOUBLE, END_DOUBLE), EPS)
  }

  @Test
  fun pointEvaluatorTest() {
    val evaluator = Evaluators.POINT
    Assert.assertEquals(
      Point.fromLngLat(50.0, 50.0),
      evaluator.evaluate(0.0f, START_POINT, END_POINT)
    )
    Assert.assertEquals(
      Point.fromLngLat(70.0, 70.0),
      evaluator.evaluate(0.5f, START_POINT, END_POINT)
    )
    Assert.assertEquals(
      Point.fromLngLat(90.0, 90.0),
      evaluator.evaluate(1.0f, START_POINT, END_POINT)
    )
  }

  companion object {
    private const val EPS = 0.0001
    private const val START_DOUBLE = 0.0
    private const val END_DOUBLE = 10.0
    private val START_POINT = Point.fromLngLat(50.0, 50.0)
    private val END_POINT = Point.fromLngLat(90.0, 90.0)
  }
}