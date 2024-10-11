package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.interactions.standard.generated.StandardBuildingsState
import com.mapbox.maps.interactions.standard.generated.StandardPlaceLabelsState
import com.mapbox.maps.interactions.standard.generated.StandardPoiFeature
import com.mapbox.maps.interactions.standard.generated.StandardPoiState
import com.mapbox.maps.interactions.standard.generated.StandardPoiStateKey
import com.mapbox.maps.interactions.standard.generated.standardBuildings
import com.mapbox.maps.interactions.standard.generated.standardPlaceLabels
import com.mapbox.maps.interactions.standard.generated.standardPoi
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Interactive map elements when using experimental Mapbox Standard Style.
 */
@OptIn(MapboxExperimental::class)
class StandardStyleInteractionsActivity : AppCompatActivity() {

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(
      this,
      MapInitOptions(
        this,
        cameraOptions = cameraOptions {
          center(HELSINKI)
          zoom(15.0)
          pitch(30.0)
        }
      )
    )
    setContentView(mapView)
    val map = mapView.mapboxMap
    val selectedPoiList = mutableListOf<StandardPoiFeature>()

    map.loadStyle(Style.STANDARD_EXPERIMENTAL)

    // the most basic use-case to highlight clicked building
    map.addInteraction(
      ClickInteraction.standardBuildings { selectedBuilding, _ ->
        map.setFeatureState(
          selectedBuilding,
          StandardBuildingsState {
            highlight(true)
          }
        )
        return@standardBuildings true
      }
    )

    // a bit more advanced use-case to fade a place label and also show a pop-up
    // with clicked place label class
    map.addInteraction(
      ClickInteraction.standardPlaceLabels { selectedPlaceLabel, _ ->
        Toast.makeText(this, selectedPlaceLabel.`class` ?: "No class!", Toast.LENGTH_SHORT).show()
        map.setFeatureState(
          selectedPlaceLabel,
          StandardPlaceLabelsState {
            select(true)
          }
        )
        return@standardPlaceLabels true
      }
    )

    // the most advanced use case to:
    //  1. Hide clicked POI.
    //  2. Show the Android View Annotation instead in the place where user clicked.
    //  3. Clicking the Android View Annotation removes it and shows hidden POI again.
    //  4. Text color of the Android View Annotation is driven by POI type: RED if it's transit and BLUE otherwise.
    map.addInteraction(
      ClickInteraction.standardPoi { selectedPoi, _ ->
        map.setFeatureState(
          selectedPoi,
          StandardPoiState {
            hide(true)
          }
        ) {
          selectedPoiList.add(selectedPoi)
          mapView.viewAnnotationManager.addViewAnnotation(
            TextView(this).apply {
              text = "${selectedPoi.name}\nGroup: ${selectedPoi.group}"
              if (selectedPoi.group == "transit") {
                setTextColor(Color.RED)
              } else {
                setTextColor(Color.BLUE)
              }
              gravity = Gravity.CENTER
              layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
              // when clicking on the view annotation - remove it and bring the map POI back
              setOnClickListener { selectedPoiView ->
                // check with getter to get the most actual state
                map.getFeatureState(selectedPoi) { currentState ->
                  if (currentState.hide == true) {
                    // show POI again and remove the view annotation
                    map.setFeatureState(
                      selectedPoi,
                      StandardPoiState {
                        hide(false)
                      }
                    )
                    mapView.viewAnnotationManager.removeViewAnnotation(selectedPoiView)
                  }
                }
              }
            },
            viewAnnotationOptions {
              geometry(selectedPoi.geometry)
              annotationAnchor {
                anchor(ViewAnnotationAnchor.CENTER)
              }
            }
          )
        }
        return@standardPoi true
      }
    )

    // when clicked on the map - remove the feature state from all selected POIs
    // and show them as defined in Mapbox Standard Style
    map.addInteraction(
      ClickInteraction { _ ->
        selectedPoiList.forEach { selectedPoi ->
          map.removeFeatureState(
            selectedPoi,
            StandardPoiStateKey.HIDE
          )
        }
        selectedPoiList.clear()
        mapView.viewAnnotationManager.removeAllViewAnnotations()
        Toast.makeText(this, "Map clicked, removing selected POIs if any", Toast.LENGTH_SHORT).show()
        true
      }
    )
  }

  private companion object {
    private val HELSINKI = Point.fromLngLat(24.94180921290157, 60.171227338006844)
  }
}