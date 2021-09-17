package com.mapbox.maps.testapp.examples.markersandcallouts

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.not
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.toNumber
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolZOrder
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.plugin.annotation.Annotation
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes
import java.util.*

/**
 * Example showing how to add Symbol annotations
 */
class PointAnnotationActivity : AppCompatActivity() {
  private var pointAnnotationManager: PointAnnotationManager? = null
  private var circleAnnotationManager: CircleAnnotationManager? = null
  private var pointAnnotation: PointAnnotation? = null
  private var circleAnnotation: CircleAnnotation? = null
  private val animators: MutableList<ValueAnimator> = mutableListOf()
  private var index: Int = 0
  private var consumeClickEvent = false
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]
    }
  private lateinit var annotationPlugin: AnnotationPlugin
  private lateinit var blueBitmap: Bitmap
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().loadStyleUri(nextStyle) {
      annotationPlugin = binding.mapView.annotations
      circleAnnotationManager = annotationPlugin.createCircleAnnotationManager(binding.mapView).apply {
        val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
          .withPoint(Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE))
          .withCircleColor(Color.YELLOW)
          .withCircleRadius(12.0)
          .withDraggable(false)
        circleAnnotation = create(circleAnnotationOptions)
      }
      pointAnnotationManager = annotationPlugin.createPointAnnotationManager(binding.mapView).apply {
        textFont = listOf("Arial Unicode MS Bold", "Open Sans Regular")

        addDragListener(object : OnPointAnnotationDragListener {
          override fun onAnnotationDragStarted(annotation: Annotation<*>) {}

          override fun onAnnotationDrag(annotation: Annotation<*>) {
            circleAnnotation?.let {
              it.point = (annotation as PointAnnotation).point
              circleAnnotationManager?.update(it)
            }
          }

          override fun onAnnotationDragFinished(annotation: Annotation<*>) {}
        })
        addClickListener(
          OnPointAnnotationClickListener {
            Toast.makeText(this@PointAnnotationActivity, "Click1: ${it.id}", Toast.LENGTH_SHORT)
              .show()
            consumeClickEvent
          }
        )

        addClickListener(
          OnPointAnnotationClickListener {
            Toast.makeText(this@PointAnnotationActivity, "Click2: ${it.id}", Toast.LENGTH_SHORT)
              .show()
            consumeClickEvent
          }
        )

        addLongClickListener(
          OnPointAnnotationLongClickListener {
            Toast.makeText(this@PointAnnotationActivity, "LongClick1: ${it.id}", Toast.LENGTH_SHORT)
              .show()
            consumeClickEvent
          }
        )

        addLongClickListener(
          OnPointAnnotationLongClickListener {
            Toast.makeText(this@PointAnnotationActivity, "LongClick2: ${it.id}", Toast.LENGTH_SHORT)
              .show()
            consumeClickEvent
          }
        )

        addInteractionListener(object : OnPointAnnotationInteractionListener {
          override fun onSelectAnnotation(annotation: PointAnnotation) {
            Toast.makeText(
              this@PointAnnotationActivity,
              "onSelectAnnotation: ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }

          override fun onDeselectAnnotation(annotation: PointAnnotation) {
            Toast.makeText(
              this@PointAnnotationActivity,
              "onDeselectAnnotation: ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }
        })

        bitmapFromDrawableRes(
          this@PointAnnotationActivity,
          R.drawable.ic_airplanemode_active_black_24dp
        )?.let {
          // create a symbol
          val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE))
            .withIconImage(it)
            .withTextField(ID_ICON_AIRPORT)
            .withTextOffset(listOf(0.0, -2.0))
            .withTextColor(Color.RED)
            .withIconSize(1.3)
            .withIconOffset(listOf(0.0, -5.0))
            .withSymbolSortKey(10.0)
            .withDraggable(true)
          pointAnnotation = create(pointAnnotationOptions)

          // random add symbols across the globe
          val pointAnnotationOptionsList: MutableList<PointAnnotationOptions> = ArrayList()
          for (i in 0..5) {
            pointAnnotationOptionsList.add(
              PointAnnotationOptions()
                .withPoint(AnnotationUtils.createRandomPoint())
                .withIconImage(it)
                .withDraggable(true)
            )
          }
          create(pointAnnotationOptionsList)
        }

        bitmapFromDrawableRes(
          this@PointAnnotationActivity,
          R.drawable.mapbox_user_icon
        )?.let {
          blueBitmap = it
          // create nearby symbols
          val nearbyOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(NEARBY_LONGITUDE, NEARBY_LATITUDE))
            .withIconImage(it)
            .withIconSize(2.5)
            .withTextField(ID_ICON_AIRPORT)
            .withSymbolSortKey(5.0)
            .withDraggable(true)
          create(nearbyOptions)
        }
        // random add symbols across the globe
        val pointAnnotationOptionsList: MutableList<PointAnnotationOptions> = ArrayList()
        for (i in 0..20) {
          pointAnnotationOptionsList.add(
            PointAnnotationOptions()
              .withPoint(AnnotationUtils.createRandomPoint())
              .withIconImage(MAKI_ICON_CAR)
              .withDraggable(true)
          )
        }
        create(pointAnnotationOptionsList)

        AnnotationUtils.loadStringFromAssets(
          this@PointAnnotationActivity,
          "annotations.json"
        )?.let {
          create(FeatureCollection.fromJson(it))
        }
      }

      binding.mapView.getMapboxMap().addOnMapClickListener {
        Toast.makeText(this@PointAnnotationActivity, "OnMapClick", Toast.LENGTH_SHORT).show()
        true
      }
    }

    binding.deleteAll.setOnClickListener {
      pointAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener {
      binding.mapView.getMapboxMap().loadStyleUri(nextStyle)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_symbol, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_action_draggable -> {
        pointAnnotationManager?.annotations?.forEach {
          it.isDraggable = !it.isDraggable
        }
      }
      R.id.menu_action_consume_click -> {
        consumeClickEvent = !consumeClickEvent
        val message = getString(R.string.consume_click_event_toast) + consumeClickEvent
        Toast.makeText(this@PointAnnotationActivity, message, Toast.LENGTH_LONG).show()
      }
      R.id.menu_action_filter -> {
        if (pointAnnotationManager != null && pointAnnotation != null) {
          val idKey = pointAnnotationManager!!.getAnnotationIdKey()
          val expression: Expression =
            eq(toNumber(get(idKey)), literal(pointAnnotation!!.id.toDouble()))
          val filter = pointAnnotationManager!!.layerFilter
          if (filter != null && filter == expression) {
            pointAnnotationManager!!.layerFilter = not(eq(toNumber(get(idKey)), literal(-1)))
          } else {
            pointAnnotationManager!!.layerFilter = expression
          }
        }
      }
      R.id.menu_action_icon -> pointAnnotation?.iconImage = MAKI_ICON_CAFE
      R.id.menu_action_bitmap_blue -> pointAnnotation?.iconImageBitmap = blueBitmap
      R.id.menu_action_rotation -> pointAnnotation?.iconRotate = 45.0
      R.id.menu_action_text -> pointAnnotation?.textField = "Hello world!"
      R.id.menu_action_anchor -> pointAnnotation?.iconAnchor = IconAnchor.BOTTOM
      R.id.menu_action_opacity -> pointAnnotation?.iconOpacity = 0.5
      R.id.menu_action_offset -> pointAnnotation?.iconOffset = listOf(10.0, 20.0)
      R.id.menu_action_text_anchor -> pointAnnotation?.textAnchor = TextAnchor.TOP
      R.id.menu_action_text_color -> pointAnnotation?.textColorInt = Color.WHITE
      R.id.menu_action_text_size -> pointAnnotation?.textSize = 22.0
      R.id.menu_action_z_index -> pointAnnotation?.symbolSortKey = 0.0
      R.id.menu_action_halo -> {
        pointAnnotation?.iconHaloWidth = 5.0
        pointAnnotation?.iconHaloColorInt = Color.RED
        pointAnnotation?.iconHaloBlur = 1.0
      }

      R.id.menu_action_animate -> {
        resetSymbol()
        pointAnnotation?.let {
          easeSymbol(
            it,
            Point.fromLngLat(AIRPORT_LATITUDE, AIRPORT_LONGITUDE)
          )
        }
        return true
      }
      R.id.z_order_auto -> {
        item.isChecked = true
        pointAnnotationManager?.symbolZOrder = SymbolZOrder.AUTO
      }
      R.id.z_order_viewport_y -> {
        item.isChecked = true
        pointAnnotationManager?.symbolZOrder = SymbolZOrder.VIEWPORT_Y
      }
      R.id.z_order_source -> {
        item.isChecked = true
        pointAnnotationManager?.symbolZOrder = SymbolZOrder.SOURCE
      }
      else -> return super.onOptionsItemSelected(item)
    }
    pointAnnotation?.let { pointAnnotationManager?.update(it) }
    return true
  }

  private fun resetSymbol() {
    pointAnnotation?.iconRotate = 0.0
    pointAnnotation?.geometry = Point.fromLngLat(AIRPORT_LONGITUDE, AIRPORT_LATITUDE)
    pointAnnotation?.let { pointAnnotationManager?.update(it) }
  }

  private fun easeSymbol(
    pointAnnotation: PointAnnotation,
    location: Point,
    rotation: Double = 180.0
  ) {
    val originalPosition: Point = pointAnnotation.point
    val originalRotation = pointAnnotation.iconRotate
    if (originalPosition == location || originalRotation == rotation) {
      return
    }
    val moveSymbol = ValueAnimator.ofFloat(0f, 1f).setDuration(5000)
    moveSymbol.interpolator = LinearInterpolator()
    moveSymbol.addUpdateListener { animation: ValueAnimator ->
      pointAnnotationManager?.let {
        if (!it.annotations.contains(pointAnnotation)) {
          return@addUpdateListener
        }
        val fraction = animation.animatedValue as Float
        if (originalPosition != location) {
          val lat =
            (location.latitude() - originalPosition.latitude()) * fraction + originalPosition.latitude()
          val lng =
            (location.longitude() - originalPosition.longitude()) * fraction + originalPosition.longitude()
          pointAnnotation.geometry = Point.fromLngLat(lng, lat)
        }
        if (originalRotation != null && originalRotation != rotation) {
          pointAnnotation.iconRotate = (rotation - originalRotation) * fraction + originalRotation
        }
        it.update(pointAnnotation)
      }
    }
    moveSymbol.start()
    animators.add(moveSymbol)
  }

  companion object {
    private const val ID_ICON_AIRPORT = "airport"
    private const val MAKI_ICON_CAR = "car-15"
    private const val MAKI_ICON_CAFE = "cafe-15"
    private const val AIRPORT_LONGITUDE = 0.381457
    private const val AIRPORT_LATITUDE = 6.687337
    private const val NEARBY_LONGITUDE = 0.367099
    private const val NEARBY_LATITUDE = 6.526384
  }
}