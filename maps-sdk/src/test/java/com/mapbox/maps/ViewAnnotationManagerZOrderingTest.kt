 package com.mapbox.maps

 import com.mapbox.geojson.Point
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

 @RunWith(Parameterized::class)
 internal class ViewAnnotationManagerZOrderingTest(
  private val oldDescriptors: List<DelegatingViewAnnotationPositionDescriptor>,
  private val newDescriptors: List<DelegatingViewAnnotationPositionDescriptor>,
  private val shouldChangeZOrder: Boolean,
 ) {

  @Test
  fun calculatesCorrectZOrder() {
    assertEquals(
      shouldChangeZOrder,
      ViewAnnotationManagerImpl.needToReorderZ(
        oldDescriptors, newDescriptors
      ),
    )
  }

  companion object {
    private fun descriptors(vararg identifiers: String) = identifiers.map {
      DelegatingViewAnnotationPositionDescriptor(
        it,
        0.0,
        0.0,
        // different identifiers should have different screen coordinates
        ScreenCoordinate(
          (identifiers.hashCode() % 500).toDouble(),
          (identifiers.hashCode() % 300).toDouble()
        ),
        anchorCoordinate = Point.fromLngLat(0.0, 0.0),
        anchorConfig = ViewAnnotationAnchorConfig.Builder().anchor(ViewAnnotationAnchor.CENTER).build(),
      )
    }

    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        /* old descriptors */
        descriptors(),
        /* new descriptors */
        descriptors(),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors("1"),
        /* new descriptors */
        descriptors("1"),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors("1", "2", "3"),
        /* new descriptors */
        descriptors("1", "2", "3"),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "2",
          "3",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
        ),
        /* new descriptors */
        descriptors(
          "2",
          "4",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "4",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "3",
          "2",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
        ),
        /* new descriptors */
        listOf<String>(),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(),
        /* new descriptors */
        descriptors(
          "1",
          "3",
          "2",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "3",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "2",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "2",
          "3",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "2",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "3",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "2",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "2",
          "7",
          "8",
          "9",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "7",
          "8",
          "9",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "5",
          "7",
          "8",
          "9",
        ),
        /* change z order */
        false,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "5",
          "3",
          "8",
          "9",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "5",
          "8",
          "3",
          "9",
        ),
        /* change z order */
        true,
      ),
      arrayOf(
        /* old descriptors */
        descriptors(
          "1",
          "2",
          "3",
          "4",
          "5",
        ),
        /* new descriptors */
        descriptors(
          "1",
          "3",
          "5",
          "8",
          "9",
        ),
        /* change z order */
        false,
      ),
    )
  }
 }