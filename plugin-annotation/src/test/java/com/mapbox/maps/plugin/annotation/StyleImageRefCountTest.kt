package com.mapbox.maps.plugin.annotation

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation.Companion.iconImageId
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapInteractionDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class StyleImageRefCountTest {

  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: MapboxStyleManager = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: SymbolLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: SymbolLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()

  private lateinit var manager: PointAnnotationManager

  private val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
  private val bitmap2 = Bitmap.createBitmap(60, 60, Bitmap.Config.ARGB_8888)

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { delegateProvider.mapStyleManagerDelegate } returns style
    every { style.addSource(any()) } just Runs
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addPersistentLayer(any(), any()) } just Runs
    every { style.setStyleLayerProperty(any(), any(), any()) } returns mockk()
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    every { style.removeStyleLayer(any()) } returns mockk()
    every { style.removeStyleSource(any()) } returns mockk()
    every { style.pixelRatio } returns 1.0f
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(any()) } returns gesturesPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapInteractionDelegate } returns mapInteractionDelegate
    every { delegateProvider.mapFeatureQueryDelegate } returns mockk()
    every { mapInteractionDelegate.addInteraction(any()) } returns Cancelable { }
    every { gesturesPlugin.getGesturesManager().moveGestureDetector } returns mockk()
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(1.0, 1.0)
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any()) } answers { source }
    every { dragLayer.layerId } returns "draglayer0"
    every { dragSource.sourceId } returns "dragsource0"
    every { dragSource.featureCollection(any()) } answers { dragSource }
    val expected = mockk<Expected<String, None>>(relaxed = true)
    every { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { style.removeStyleImage(any()) } returns mockk()
    every { layer.iconImage(any<Expression>()) } answers { layer }
    every { dragLayer.iconImage(any<Expression>()) } answers { dragLayer }

    manager = PointAnnotationManager(delegateProvider)
    manager.layer = layer
    manager.source = source
    manager.dragLayer = dragLayer
    manager.dragSource = dragSource
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun `delete annotation with unique bitmap removes style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotation = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true

      manager.delete(annotation)

      verify(exactly = 1) { style.removeStyleImage(imageId) }
    }
  }

  @Test
  fun `deleteAll removes all unique style images`() {
    val imageIdA = imageIdFor(bitmap)
    val imageIdB = imageIdFor(bitmap2)
    withBitmapStatics {
      every { style.hasStyleImage(imageIdA) } returns false
      every { style.hasStyleImage(imageIdB) } returns false
      createWithBitmap(bitmap)
      createWithBitmap(bitmap2)
      every { style.hasStyleImage(imageIdA) } returns true
      every { style.hasStyleImage(imageIdB) } returns true

      manager.deleteAll()

      verify(exactly = 1) { style.removeStyleImage(imageIdA) }
      verify(exactly = 1) { style.removeStyleImage(imageIdB) }
    }
  }

  /**
   * Two annotations share the same bitmap (same imageId). Deleting the first must
   * NOT remove the style image because the second annotation still references it.
   */
  @Test
  fun `deleting first of two shared-bitmap annotations preserves style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotationA = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true
      createWithBitmap(bitmap)

      manager.delete(annotationA)

      verify(exactly = 0) { style.removeStyleImage(imageId) }
    }
  }

  /**
   * After deleting both shared-bitmap annotations the style image must be removed.
   */
  @Test
  fun `deleting both shared-bitmap annotations removes style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotationA = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true
      val annotationB = createWithBitmap(bitmap)

      manager.delete(annotationA)
      manager.delete(annotationB)

      verify(exactly = 1) { style.removeStyleImage(imageId) }
    }
  }

  /**
   * Annotation B is created using A's iconImage id (no bitmap) while A is still
   * alive. Deleting A must NOT remove the style image because B holds the same id.
   */
  @Test
  fun `deleting source annotation preserves style image when cached-id annotation is alive`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotationA = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true
      // B references only the id string — no bitmap
      val annotationB = createWithIconImageString(imageId)

      manager.delete(annotationA)

      verify(exactly = 0) { style.removeStyleImage(imageId) }

      // Once B is also deleted, the image should be released.
      manager.delete(annotationB)
      verify(exactly = 1) { style.removeStyleImage(imageId) }
    }
  }

  /**
   * Setting manager.iconImageBitmap registers the image via addStyleImage() with
   * ref count = 1. Annotations created without an explicit iconImage don't
   * contribute to the ref count (their _iconImage is null). Deleting such an
   * annotation must NOT remove the manager-level style image.
   */
  @Test
  fun `deleting individual annotation does not remove manager-level style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false

      // Triggers addStyleImage → styleImages.put(imageId), ref count = 1
      manager.iconImageBitmap = bitmap
      every { style.hasStyleImage(imageId) } returns true

      // Annotation has no explicit iconImage → styleImages.put(annotation) is a no-op
      val annotationA = manager.create(
        PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )

      manager.delete(annotationA)

      verify(exactly = 0) { style.removeStyleImage(imageId) }
    }
  }

  /**
   * deleteAll must release the manager-level style image via styleImages.clear().
   */
  @Test
  fun `deleteAll removes manager-level style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      manager.iconImageBitmap = bitmap
      manager.create(PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
      every { style.hasStyleImage(imageId) } returns true

      manager.deleteAll()

      verify(exactly = 1) { style.removeStyleImage(imageId) }
    }
  }

  /**
   *   1. Create A with a bitmap → image uploaded, cached ID = A._iconImage.
   *   2. Delete A → ref count → 0, image removed from style.
   *   3. Create B using the cached ID string (no bitmap) → ref count back to 1,
   *      but the image is NOT re-uploaded because B has no bitmap to supply.
   *
   * This documents a known limitation: re-using a string ID after its originating
   * annotation was deleted results in B having no icon in the style.
   */
  @Test
  fun `creating string-id annotation after source annotation deleted does not re-add style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotationA = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true

      // Cache the ID and delete A - image removed from style
      val cachedId = annotationA.iconImageInternal!!
      manager.delete(annotationA)
      every { style.hasStyleImage(imageId) } returns false
      verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }

      // Create B with the cached string ID only (no bitmap)
      val annotationB = manager.create(
        PointAnnotationOptions()
          .withIconImage(cachedId)
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )

      // Image was uploaded exactly once (for A). B has no bitmap to re-upload it.
      verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }
      // B holds the image ID but the image is absent from the style → B will not be shown on the map.
      Assert.assertEquals(cachedId, annotationB.iconImageInternal)
      Assert.assertFalse(style.hasStyleImage(cachedId))
    }
  }

  /**
   * Two different PointAnnotationManagers use the same bitmap. Each manager now
   * produces a unique imageId (includes the manager's hashCode), so deleteAll on
   * one manager only removes its own style image, leaving the other manager's
   * image intact.
   */
  @Test
  fun `deleteAll on one manager does not remove style image used by another manager`() {
    // Set up a second manager with its own layer/source mocks
    val layer2: SymbolLayer = mockk()
    val source2: GeoJsonSource = mockk()
    val dragLayer2: SymbolLayer = mockk()
    val dragSource2: GeoJsonSource = mockk()
    every { layer2.layerId } returns "layer1"
    every { source2.sourceId } returns "source1"
    every { source2.featureCollection(any()) } answers { source2 }
    every { dragLayer2.layerId } returns "draglayer1"
    every { dragSource2.sourceId } returns "dragsource1"
    every { dragSource2.featureCollection(any()) } answers { dragSource2 }
    every { layer2.iconImage(any<Expression>()) } answers { layer2 }
    every { dragLayer2.iconImage(any<Expression>()) } answers { dragLayer2 }

    val manager2 = PointAnnotationManager(delegateProvider)
    manager2.layer = layer2
    manager2.source = source2
    manager2.dragLayer = dragLayer2
    manager2.dragSource = dragSource2

    val imageId1 = imageIdFor(bitmap, manager)
    val imageId2 = imageIdFor(bitmap, manager2)

    withBitmapStatics {
      every { style.hasStyleImage(imageId1) } returns false
      every { style.hasStyleImage(imageId2) } returns false

      // Manager 1 creates an annotation with the bitmap
      createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId1) } returns true

      // Manager 2 creates an annotation with the same bitmap — gets its own imageId
      manager2.create(
        PointAnnotationOptions()
          .withIconImage(bitmap)
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )
      every { style.hasStyleImage(imageId2) } returns true

      // Manager 1 deletes all — removes only its own style image
      manager.deleteAll()

      verify(exactly = 1) { style.removeStyleImage(imageId1) }
      // Manager 2's style image must NOT be removed
      verify(exactly = 0) { style.removeStyleImage(imageId2) }
    }
  }

  /**
   * After deleting an annotation whose image was removed from the style, creating
   * a new annotation with the same bitmap must re-upload the image to the style.
   */
  @Test
  fun `creating annotation with previously deleted image re-adds style image`() {
    val imageId = imageIdFor(bitmap)
    withBitmapStatics {
      every { style.hasStyleImage(imageId) } returns false
      val annotationA = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true

      // Delete A → image removed (ref count → 0, removeStyleImage called)
      manager.delete(annotationA)
      every { style.hasStyleImage(imageId) } returns false

      // Create B with the same bitmap → image must be re-uploaded
      val annotationB = createWithBitmap(bitmap)
      every { style.hasStyleImage(imageId) } returns true

      // Two addStyleImage calls: one for A, one for B
      verify(exactly = 2) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }

      // Delete B → second removal
      manager.delete(annotationB)
      verify(exactly = 2) { style.removeStyleImage(imageId) }
    }
  }

  private fun imageIdFor(b: Bitmap, mgr: PointAnnotationManager = manager) =
    iconImageId(mgr, b)

  private fun createWithBitmap(b: Bitmap) = manager.create(
    PointAnnotationOptions()
      .withIconImage(b)
      .withPoint(Point.fromLngLat(0.0, 0.0))
  )

  private fun createWithIconImageString(imageId: String) = manager.create(
    PointAnnotationOptions()
      .withIconImage(imageId)
      .withPoint(Point.fromLngLat(0.0, 0.0))
  )

  /** Wraps a test body that involves bitmap operations requiring DataRef mocking. */
  private fun withBitmapStatics(block: () -> Unit) {
    mockkStatic(DataRef::class)
    every { DataRef.allocateNative(any()) } returns mockk(relaxed = true)
    try {
      block()
    } finally {
      unmockkStatic(DataRef::class)
    }
  }
}