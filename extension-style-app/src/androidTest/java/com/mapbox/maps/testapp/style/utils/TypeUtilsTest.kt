package com.mapbox.maps.testapp.style.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.extension.style.utils.toValue
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for TypeUtils.
 */
@RunWith(AndroidJUnit4::class)
class TypeUtilsTest : BaseStyleTest() {

  @Test
  fun feature_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "bbox": [100.0, 0.0, -100.0, 105.0, 1.0, 0.0],
        "geometry": {
          "type": "Point",
          "coordinates": [125.6, 10.1]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{geometry={coordinates=[125.6, 10.1], type=Point}, type=Feature, properties={name=Dinagat Islands}, bbox=[100.0, 0.0, -100.0, 105.0, 1.0, 0.0]}",
      feature.toValue().toString()
    )
  }

  @Test
  fun featureCollection_toValue() {
    val feature = FeatureCollection.fromJson(
      """
      {
         "type": "FeatureCollection",
         "bbox": [100.0, 0.0, -100.0, 105.0, 1.0, 0.0],
         "features": [
             {
                 "type": "Feature",
                 "id": "id0",
                 "geometry": {
                     "type": "LineString",
                     "coordinates": [
                         [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]
                     ]
                 },
                 "properties": {
                     "prop0": "value0",
                     "prop1": "value1"
                 }
             },
             {
                 "type": "Feature",
                 "id": "id1",
                 "geometry": {
                     "type": "Polygon",
                     "coordinates": [
                         [
                             [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]
                         ]
                     ]
                 },
                 "properties": {
                     "prop0": "value0",
                     "prop1": "value1"
                 }
             }
         ]
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=FeatureCollection, features=[{geometry={coordinates=[[102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]], type=LineString}, id=id0, type=Feature, properties={prop1=value1, prop0=value0}}, {geometry={coordinates=[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]], type=Polygon}, id=id1, type=Feature, properties={prop1=value1, prop0=value0}}], bbox=[100.0, 0.0, -100.0, 105.0, 1.0, 0.0]}",
      feature.toValue().toString()
    )
  }

  @Test
  fun geometry_Point_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "Point",
          "coordinates": [125.6, 10.1]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=Point, coordinates=[125.6, 10.1]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_LineString_toValue() {
    val feature = Feature.fromJson(
      """
        {
          "type": "Feature",
          "geometry": {
          "type": "LineString",
          "coordinates": [
           [100.0, 0.0], [101.0, 1.0]
            ]
          },
          "properties": {
          "name": "Dinagat Islands"
          }
        }
      """.trimIndent()
    )
    assertEquals(
      "{type=LineString, coordinates=[[100.0, 0.0], [101.0, 1.0]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_Polygon_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "Polygon",
          "coordinates": [
             [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ]
          ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=Polygon, coordinates=[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_PolygonWithHoles_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "Polygon",
          "coordinates": [
           [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ],
           [ [100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2] ]
          ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=Polygon, coordinates=[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]], [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_MultiPoint_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "MultiPoint",
          "coordinates": [
           [100.0, 0.0], [101.0, 1.0]
          ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=MultiPoint, coordinates=[[100.0, 0.0], [101.0, 1.0]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_MultiLineString_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "MultiLineString",
          "coordinates": [
            [ [100.0, 0.0], [101.0, 1.0] ],
            [ [102.0, 2.0], [103.0, 3.0] ]
           ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=MultiLineString, coordinates=[[[100.0, 0.0], [101.0, 1.0]], [[102.0, 2.0], [103.0, 3.0]]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_MultiPolygon_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "MultiPolygon",
          "coordinates": [
        [
            [ [102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0] ]
        ],
        [
            [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ],
            [ [100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2] ]
        ]
    ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=MultiPolygon, coordinates=[[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]], [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]], [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_GeometryCollection_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "GeometryCollection",
          "geometries": [
              {
                  "type": "Point",
                  "coordinates": [100.0, 0.0]
              },
              {
                  "type": "LineString",
                  "coordinates": [
                      [101.0, 0.0], [102.0, 1.0]
                  ]
              }
          ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=GeometryCollection, geometries=[{type=Point, coordinates=[100.0, 0.0]}, {type=LineString, coordinates=[[101.0, 0.0], [102.0, 1.0]]}]}",
      feature.geometry()!!.toValue().toString()
    )
  }

  @Test
  fun geometry_NestedGeometryCollection_toValue() {
    val feature = Feature.fromJson(
      """
      {
        "type": "Feature",
        "geometry": {
          "type": "GeometryCollection",
          "geometries": [
              {
                  "type": "Point",
                  "coordinates": [100.0, 0.0]
              },
              {
                  "type": "LineString",
                  "coordinates": [
                      [101.0, 0.0], [102.0, 1.0]
                  ]
              },
              {
                  "type": "GeometryCollection",
                  "geometries": [
                      {
                          "type": "Point",
                          "coordinates": [100.0, 0.0]
                      },
                      {
                          "type": "LineString",
                          "coordinates": [
                              [101.0, 0.0], [102.0, 1.0]
                          ]
                      }
                  ]
                }
          ]
        },
        "properties": {
          "name": "Dinagat Islands"
        }
      }
      """.trimIndent()
    )
    assertEquals(
      "{type=GeometryCollection, geometries=[{type=Point, coordinates=[100.0, 0.0]}, {type=LineString, coordinates=[[101.0, 0.0], [102.0, 1.0]]}, {type=GeometryCollection, geometries=[{type=Point, coordinates=[100.0, 0.0]}, {type=LineString, coordinates=[[101.0, 0.0], [102.0, 1.0]]}]}]}",
      feature.geometry()!!.toValue().toString()
    )
  }
}