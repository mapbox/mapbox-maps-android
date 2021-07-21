package com.mapbox.maps.lint

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class MapboxObsoleteDetectorTest {
  private val noWarnings = "No warnings."

  @Test
  fun mapViewOnStart() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    import com.mapbox.maps.MapView;
                    public class TestClass {
                        MapView mapView = new MapView();
                        void onStart() {
                            mapView.onStart();
                        }
                    }
                    """
      ).indented()
    )

      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/test/pkg/TestClass.java:6: Warning: No need to invoke onStart/onStop/onDestroy/onLowMemory with Mapbox Lifecycle Plugin. [Lifecycle]
         |        mapView.onStart();
         |        ~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
  }

  @Test
  fun mapViewOnStop() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    import com.mapbox.maps.MapView;
                    public class TestClass {
                        MapView mapView = new MapView();
                        void onStop() {
                            mapView.onStop();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/test/pkg/TestClass.java:6: Warning: No need to invoke onStart/onStop/onDestroy/onLowMemory with Mapbox Lifecycle Plugin. [Lifecycle]
         |        mapView.onStop();
         |        ~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
  }

  @Test
  fun mapViewOnDestroy() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    import com.mapbox.maps.MapView;
                    public class TestClass {
                        MapView mapView = new MapView();
                        void onDestroy() {
                            mapView.onDestroy();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/test/pkg/TestClass.java:6: Warning: No need to invoke onStart/onStop/onDestroy/onLowMemory with Mapbox Lifecycle Plugin. [Lifecycle]
         |        mapView.onDestroy();
         |        ~~~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
  }

  @Test
  fun mapViewOnLowMemory() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    import com.mapbox.maps.MapView;
                    public class TestClass {
                        MapView mapView = new MapView();
                        void onLowMemory() {
                            mapView.onLowMemory();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/test/pkg/TestClass.java:6: Warning: No need to invoke onStart/onStop/onDestroy/onLowMemory with Mapbox Lifecycle Plugin. [Lifecycle]
         |        mapView.onLowMemory();
         |        ~~~~~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
  }

  @Test
  fun normalOnStart() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    public class TestClass {
                        void onStart() {
                            foo.onStart();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        noWarnings
      )
  }

  @Test
  fun normalOnStop() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    public class TestClass {
                        void onStop() {
                            foo.onStop();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        noWarnings
      )
  }

  @Test
  fun normalOnDestroy() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    public class TestClass {
                        void onDestroy() {
                            foo.onDestroy();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        noWarnings
      )
  }

  @Test
  fun normalOnLowMemory() {
    TestLintTask.lint().files(
      TestFiles.java(
        """
                    package test.pkg;
                    public class TestClass {
                        void onLowMemory() {
                            foo.onLowMemory();
                        }
                    }
                    """
      ).indented()
    )
      .issues(
        MapboxObsoleteDetector.ISSUE
      )
      .run()
      .expect(
        noWarnings
      )
  }
}