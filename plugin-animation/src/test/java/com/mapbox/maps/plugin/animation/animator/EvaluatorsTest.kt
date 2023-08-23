package com.mapbox.maps.plugin.animation.animator

import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.animator.Evaluators.EDGE_INSET
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EvaluatorsTest {
  private val zeroEdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
  }

  @Test
  fun edgeInsetEvaluatorFromZero() {
    with(EDGE_INSET.evaluate(0F, zeroEdgeInsets, zeroEdgeInsets)) {
      assertEquals(zeroEdgeInsets, this)
    }

    val endEdgeInsets = EdgeInsets(200.0, 300.0, 400.0, 500.0)
    with(EDGE_INSET.evaluate(0F, zeroEdgeInsets, endEdgeInsets)) {
      assertEquals(zeroEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(0.5F, zeroEdgeInsets, endEdgeInsets)) {
      assertEquals(
        EdgeInsets(
          endEdgeInsets.top / 2F,
          endEdgeInsets.left / 2F,
          endEdgeInsets.bottom / 2F,
          endEdgeInsets.right / 2F
        ),
        this
      )
    }

    with(EDGE_INSET.evaluate(1F, zeroEdgeInsets, endEdgeInsets)) {
      assertEquals(endEdgeInsets, this)
    }
  }

  @Test
  fun edgeInsetEvaluatorBasic() {
    val startEdgeInsets = EdgeInsets(10.0, 20.0, 30.0, 40.0)
    with(EDGE_INSET.evaluate(0F, startEdgeInsets, zeroEdgeInsets)) {
      assertEquals(startEdgeInsets, this)
    }

    val endEdgeInsets = EdgeInsets(200.0, 300.0, 400.0, 500.0)
    with(EDGE_INSET.evaluate(0F, startEdgeInsets, endEdgeInsets)) {
      assertEquals(startEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(0.5F, startEdgeInsets, endEdgeInsets)) {
      assertEquals(EdgeInsets(105.0, 160.0, 215.0, 270.0), this)
    }

    with(EDGE_INSET.evaluate(1F, startEdgeInsets, endEdgeInsets)) {
      assertEquals(endEdgeInsets, this)
    }
  }

  @Test
  fun nullEdgeInsetEvaluator() {
    with(EDGE_INSET.evaluate(0F, null, null)) {
      assertEquals(zeroEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(0.5F, null, null)) {
      assertEquals(zeroEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(1.0F, null, null)) {
      assertEquals(zeroEdgeInsets, this)
    }
  }

  @Test
  fun startNullEdgeInsetEvaluator() {
    val endEdgeInsets = EdgeInsets(200.0, 300.0, 400.0, 500.0)
    with(EDGE_INSET.evaluate(0F, null, endEdgeInsets)) {
      assertEquals(zeroEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(0.5F, null, endEdgeInsets)) {
      assertEquals(
        EdgeInsets(
          endEdgeInsets.top / 2F,
          endEdgeInsets.left / 2F,
          endEdgeInsets.bottom / 2F,
          endEdgeInsets.right / 2F
        ),
        this
      )
    }

    with(EDGE_INSET.evaluate(1F, null, endEdgeInsets)) {
      assertEquals(endEdgeInsets, this)
    }
  }

  @Test
  fun endNullEdgeInsetEvaluator() {
    val startEdgeInsets = EdgeInsets(200.0, 300.0, 400.0, 500.0)
    with(EDGE_INSET.evaluate(0F, startEdgeInsets, null)) {
      assertEquals(startEdgeInsets, this)
    }

    with(EDGE_INSET.evaluate(0.5F, startEdgeInsets, null)) {
      assertEquals(
        EdgeInsets(
          startEdgeInsets.top / 2F,
          startEdgeInsets.left / 2F,
          startEdgeInsets.bottom / 2F,
          startEdgeInsets.right / 2F
        ),
        this
      )
    }

    with(EDGE_INSET.evaluate(1F, startEdgeInsets, null)) {
      assertEquals(zeroEdgeInsets, this)
    }
  }
}