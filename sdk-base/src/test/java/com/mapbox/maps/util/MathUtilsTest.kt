package com.mapbox.maps.util

import com.mapbox.maps.util.MathUtils.prepareOptimalBearingPath
import com.mapbox.maps.util.MathUtils.shortestRotation
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

private const val EPS = 0.0001

@RunWith(Parameterized::class)
class PrepareOptimalBearingPathTest(
  private val inputArray: DoubleArray,
  private val expectedArray: DoubleArray,
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        doubleArrayOf(-0.0, -10.0, 10.0, 270.0, 560.0),
        doubleArrayOf(0.0, -10.0, 10.0, -90.0, -160.0)
      ),
      arrayOf(doubleArrayOf(0.0, 90.0, 270.0), doubleArrayOf(0.0, 90.0, 270.0)),
      arrayOf(doubleArrayOf(20.0, 720.0, 0.0), doubleArrayOf(20.0, 0.0, 0.0)),
    )
  }

  @Test
  fun checkPrepareOptimalBearingPath() {
    val actualArray = prepareOptimalBearingPath(inputArray)
    assertArrayEquals(expectedArray, actualArray, EPS)
  }
}

@RunWith(Parameterized::class)
class ShortestRotationTest(private val actual: Double, private val expected: Double) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(shortestRotation(0.0, 181.0), 360.0),
      arrayOf(shortestRotation(0.0, 179.0), 0.0),
      arrayOf(shortestRotation(0.0, 180.0), 0.0),

      arrayOf(shortestRotation(50.0, 231.0), 410.0),
      arrayOf(shortestRotation(50.0, 229.0), 50.0),
      arrayOf(shortestRotation(50.0, 180.0), 50.0),
    )
  }

  @Test
  fun doesReturnValueDistance() {
    assertEquals(expected, actual, EPS)
  }
}