package com.mapbox.maps.plugin.attribution

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AttributionViewPluginImplTest {

  private lateinit var attributionPlugin: AttributionPlugin
  private val attributionView = mockk<AttributionViewImpl>(relaxUnitFun = true)
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapAttributionDelegate = mockk<MapAttributionDelegate>(relaxed = true)
  private val clickSot = slot<View.OnClickListener>()
  private val context: Context = mockk(relaxed = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)
  private val mapView = mockk<FrameLayout>()
  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    mockkStatic(Color::class)
    every { Color.parseColor(any()) } returns Color.WHITE
    every { delegateProvider.mapAttributionDelegate } returns mapAttributionDelegate
    every { mapView.context } returns context
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getColor(any(), any()) } returns Color.WHITE
    every { typedArray.getInt(any(), any()) } returns (Gravity.BOTTOM or Gravity.START)
    every { typedArray.getDimension(any(), any()) } returns 0f

    attributionPlugin = AttributionViewPlugin { attributionView }
    attributionPlugin.onDelegateProvider(delegateProvider)
    attributionPlugin.bind(mapView, attrs, 1f)
    attributionPlugin.onPluginView(attributionView)
    verify {
      attributionView.setOnClickListener(capture(clickSot))
    }
    attributionPlugin.initialize()
  }

  @After
  fun stop() {
    attributionPlugin.onStop()
  }

  @Test
  fun attributionGravity() {
    assertEquals(Gravity.BOTTOM or Gravity.START, attributionPlugin.position)
    attributionPlugin.position = Gravity.BOTTOM
    verify { attributionView.setGravity(Gravity.BOTTOM) }
  }

  @Test
  fun enabled() {
    assertEquals(true, attributionPlugin.enabled)
    attributionPlugin.enabled = false
    verify { attributionView.setEnable(false) }
    attributionPlugin.enabled = true
    verify { attributionView.setEnable(true) }
  }

  @Test
  fun bind() {
    val mapView = mockk<FrameLayout>()
    every { mapView.context } returns mockk(relaxed = true)
    assertEquals(attributionView, attributionPlugin.bind(mapView, mockk(relaxed = true), 1.0f))
  }

  @Test
  fun setAttributionMargins() {
    attributionPlugin.updateSettings {
      marginLeft = 0f; marginTop = 5f; marginRight = 0f; marginBottom = 0f
    }
    verify { attributionView.setAttributionMargins(0, 5, 0, 0) }
  }

  @Test
  fun setDialogManager() {
    val dialogManager = mockk<AttributionDialogManager>(relaxed = true)
    attributionPlugin.setCustomAttributionDialogManager(dialogManager)
    attributionPlugin.clickable = false
    clickSot.captured.onClick(attributionView)
    verify(exactly = 0) { dialogManager.showAttribution(mapAttributionDelegate) }
    attributionPlugin.clickable = true
    clickSot.captured.onClick(attributionView)
    verify(exactly = 1) { dialogManager.showAttribution(mapAttributionDelegate) }
  }

  @Test
  fun mapAttributionDelegate() {
    assertEquals(mapAttributionDelegate, attributionPlugin.getMapAttributionDelegate())
  }
}