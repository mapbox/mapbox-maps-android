package com.mapbox.maps.testapp.style.sources

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Manually written test for RasterArraySource.
 */
@RunWith(AndroidJUnit4::class)
class RasterArraySourceTest : BaseStyleTest() {
  @Test
  fun rasterLayersTest() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyle(
          style = """
        {
          "version": 8,
          "name": "Temperature",
          "sources": {
            "mapbox": {
                "type": "raster-array",
                "tiles": ["mapbox://tiles/example/{z}/{x}/{y}.mrt"],
                "scheme": "xyz",
                "minzoom": 1,
                "maxzoom": 4,
                "attribution": "mapbox",
                "volatile": true,
                "raster_layers": [
                    {
                        "fields": {
                            "bands": [
                                "1659898800", "1659902400", "1659906000", "1659909600", "1659913200", "1659916800"
                            ],
                            "buffer": 1,
                            "units": "degrees"
                        },
                        "id": "temperature",
                        "maxzoom": 3,
                        "minzoom": 0
                    },
                    {
                        "fields": {
                            "bands": [
                                "1659898800", "1659902400", "1659906000", "1659909600", "1659913200", "1659916800"
                            ],
                            "buffer": 1,
                            "units": "percent"
                        },
                        "id": "humidity",
                        "maxzoom": 3,
                        "minzoom": 0
                    }
                ]
            }
          },
          "layers": [
            {
            "id": "temperature",
            "type": "raster",
            "source": "mapbox",
            "source-layer": "temperature",
            "paint": {
                "raster-color": [
                  "interpolate",
                  [
                    "linear"
                  ],
                  [
                    "raster-value"
                  ],
                  -5,
                  "rgba(94, 79, 162, 0.8)",
                  0,
                  "rgba(75, 160, 177, 0.8)",
                  5,
                  "rgba(160, 217, 163, 0.8)",
                  10,
                  "rgba(235, 247, 166, 0.8)",
                  15,
                  "rgba(254, 232, 154, 0.8)",
                  20,
                  "rgba(251, 163, 94, 0.8)",
                  25,
                  "rgba(225, 82, 74, 0.8)",
                  30,
                  "rgba(158, 1, 66, 0.8)"
                ],
                "raster-color-range": [-5, 30]
            }
          }]
        }
      """.trimIndent()
        ) { style ->
          val source = style.getSourceAs<RasterArraySource>("mapbox")
          assertEquals(
            /* expected = */ "[RasterDataLayer(layerId=temperature, bands:[1659898800, 1659902400, 1659906000, 1659909600, 1659913200, 1659916800]), RasterDataLayer(layerId=humidity, bands:[1659898800, 1659902400, 1659906000, 1659909600, 1659913200, 1659916800])]",
            /* actual = */ source?.rasterLayers?.toString()
          )
          latch.countDown()
        }
      }
    }

    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }
}