package com.mapbox.maps.testapp

import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.sources.*
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class GeoJsonSourceMutateTest(
  private val initialFeatureCount: Int,
  private val updatedFeatureCount: Int,
  private val commandType: CommandType,
) : BaseMapTest() {

  private lateinit var initialFeatures: List<Feature>

  override fun loadMap() {
    withLatch { latch ->
      initialFeatures = generateFeatures(initialFeatureCount)

      rule.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()

        var cancelable: Cancelable? = null
        cancelable = mapboxMap.subscribeSourceDataLoaded {
          if (it.sourceId == SOURCE_ID && it.dataId == DATA_ID) {
            cancelable?.cancel()
            latch.countDown()
          }
        }

        mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
          this.style = it
          this.style.addSource(
            geoJsonSource(SOURCE_ID).featureCollection(
              FeatureCollection.fromFeatures(initialFeatures),
              DATA_ID
            )
          )
        }
      }
    }
  }

  @Test
  fun testMutateGeoJson() {
    val featureList = when (commandType) {
      CommandType.ADD_FULL -> {
        generateFeatures(updatedFeatureCount)
      }

      CommandType.ADD_PARTIAL -> {
        generateFeatures(updatedFeatureCount, namePrefix = "Partial_")
      }

      CommandType.UPDATE -> {
        generateUpdateFeatures(updatedFeatureCount)
      }

      CommandType.REMOVE -> {
        generateRemoveFeatures(updatedFeatureCount)
      }
    }

    withLatch(
      timeoutMillis = 10_000L
    ) { latch ->
      println("With latch")
      rule.runOnUiThread {
        var cancelable: Cancelable? = null
        cancelable = mapboxMap.subscribeSourceDataLoaded {
          println("Source data loaded, source id : ${it.sourceId} / data id : ${it.dataId}")
          if (it.sourceId == SOURCE_ID && it.dataId == DATA_ID) {
            println("Finish latch")
            cancelable?.cancel()
            latch.countDown()
          }
        }

        mutateGeoJson(featureList, commandType)
      }
    }
  }

  private fun mutateGeoJson(
    featureList: List<Feature>,
    commandType: CommandType
  ) {
    println("Mutate geojson (command $commandType), data id : $DATA_ID")
    val geoJsonSource = this.style.getSourceAs<GeoJsonSource>(SOURCE_ID)!!
    when (commandType) {
      CommandType.ADD_PARTIAL -> {
        geoJsonSource.addGeoJSONSourceFeatures(featureList, DATA_ID)
      }

      CommandType.UPDATE -> {
        geoJsonSource.updateGeoJSONSourceFeatures(featureList, DATA_ID)
      }

      CommandType.REMOVE -> {
        geoJsonSource.removeGeoJSONSourceFeatures(featureList.map { it.id()!! }, DATA_ID)
      }

      CommandType.ADD_FULL -> {
        geoJsonSource.featureCollection(FeatureCollection.fromFeatures(initialFeatures + featureList), DATA_ID)
      }
    }
    println("Geojson updated, wait for the source data loaded event")
  }

  companion object {

    enum class CommandType {
      ADD_FULL,
      ADD_PARTIAL,
      UPDATE,
      REMOVE
    }

    private const val SOURCE_ID = "source"
    private const val DATA_ID = "update_id"
    private val INITIAL_FEATURE_COUNT = listOf(100, 10000)
    private val UPDATE_FEATURE_COUNT = listOf(1, 100, 1000, 10000)

    private fun generateFeatures(size: Int, namePrefix: String = "") =
      (1..size).map {
        Feature.fromGeometry(
          Point.fromLngLat(0.01 * it, 0.01 * it),
          null,
          featureId(it, namePrefix)
        )
      }

    private fun generateUpdateFeatures(size: Int) =
      (1..size).map {
        Feature.fromGeometry(
          Point.fromLngLat(15.0 + 0.01 * it, 0.0 + 0.01 * it),
          null,
          featureId(it)
        )
      }

    private fun generateRemoveFeatures(size: Int) =
      (1..size).map {
        Feature.fromGeometry(
          Point.fromLngLat(0.001 * it, 0.001 * it),
          null,
          featureId(it)
        )
      }

    private fun featureId(id: Int, namePrefix: String = "") = "${namePrefix}_$id"

    @JvmStatic
    @Parameterized.Parameters(name = "{1}, {0}, {2}")
    fun data() = INITIAL_FEATURE_COUNT.flatMap { initialFeatureCount ->
      UPDATE_FEATURE_COUNT.filter { it <= initialFeatureCount }.flatMap { updatedFeatureCount ->
        listOf(
          arrayOf(
            initialFeatureCount,
            updatedFeatureCount,
            CommandType.ADD_PARTIAL,
          ),
          arrayOf(
            initialFeatureCount,
            updatedFeatureCount,
            CommandType.ADD_FULL,
          ),
          arrayOf(
            initialFeatureCount,
            updatedFeatureCount,
            CommandType.UPDATE,
          ),
          arrayOf(
            initialFeatureCount,
            updatedFeatureCount,
            CommandType.REMOVE,
          ),
        )
      }
    }
  }
}