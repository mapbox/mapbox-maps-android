package com.mapbox.maps.lint

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.TextFormat
import org.junit.Test

class LifecycleMethodDetectorTest {
  val mapViewFile = TestFiles.java(
    """
              package com.mapbox.maps;
              public class MapView {
                  void onStart() {
                  }
                  void onStop() {
                  }
                  void onDestroy() {
                  }
                  void onLowMemory() {
                  }
              }
              """
  ).indented()

  val fooFile = TestFiles.java(
    """
              package com.foo;
              public class Foo {
                  void onStart() {
                  }
                  void onStop() {
                  }
                  void onDestroy() {
                  }
                  void onLowMemory() {
                  }
              }
              """
  ).indented()

  @Test
  fun mapViewOnStart() {
    TestLintTask.lint().files(
      mapViewFile,
      TestFiles.java(
        """
                  package com.foo;
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
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/com/foo/TestClass.java:6: Warning: ${LifecycleMethodDetector.ISSUE.getExplanation(TextFormat.RAW)} [Lifecycle]
         |        mapView.onStart();
         |        ~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
      .expectWarningCount(1)
  }

  @Test
  fun mapViewOnStop() {
    TestLintTask.lint().files(
      mapViewFile,
      TestFiles.java(
        """
                  package com.foo;
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
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/com/foo/TestClass.java:6: Warning: ${LifecycleMethodDetector.ISSUE.getExplanation(TextFormat.RAW)} [Lifecycle]
         |        mapView.onStop();
         |        ~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
      .expectWarningCount(1)
  }

  @Test
  fun mapViewOnDestroy() {
    TestLintTask.lint().files(
      mapViewFile,
      TestFiles.java(
        """
                  package com.foo;
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
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/com/foo/TestClass.java:6: Warning: ${LifecycleMethodDetector.ISSUE.getExplanation(TextFormat.RAW)} [Lifecycle]
         |        mapView.onDestroy();
         |        ~~~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
      .expectWarningCount(1)
  }

  @Test
  fun mapViewOnLowMemory() {
    TestLintTask.lint().files(
      mapViewFile,
      TestFiles.java(
        """
                  package com.foo;
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
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expect(
        """
         |src/com/foo/TestClass.java:6: Warning: ${LifecycleMethodDetector.ISSUE.getExplanation(TextFormat.RAW)} [Lifecycle]
         |        mapView.onLowMemory();
         |        ~~~~~~~~~~~~~~~~~~~~~
         |0 errors, 1 warnings
        """.trimMargin()
      )
      .expectWarningCount(1)
  }

  @Test
  fun normalOnStart() {
    TestLintTask.lint().files(
      fooFile,
      TestFiles.java(
        """
                  package com.foo;
                  public class TestClass {
                      Foo foo = new Foo();
                      void onStart() {
                          foo.onStart();
                      }
                  }
                  """
      ).indented()
    )
      .issues(
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expectClean()
  }

  @Test
  fun normalOnStop() {
    TestLintTask.lint().files(
      fooFile,
      TestFiles.java(
        """
                  package com.foo;
                  public class TestClass {
                      Foo foo = new Foo();
                      void onStop() {
                          foo.onStop();
                      }
                  }
                  """
      ).indented()
    )
      .issues(
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expectClean()
  }

  @Test
  fun normalOnDestroy() {
    TestLintTask.lint().files(
      fooFile,
      TestFiles.java(
        """
                  package com.foo;
                  public class TestClass {
                      Foo foo = new Foo();
                      void onDestroy() {
                          foo.onDestroy();
                      }
                  }
                  """
      ).indented()
    )
      .issues(
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expectClean()
  }

  @Test
  fun normalOnLowMemory() {
    TestLintTask.lint().files(
      fooFile,
      TestFiles.java(
        """
                  package com.foo;
                  public class TestClass {
                      Foo foo = new Foo();
                      void onLowMemory() {
                          foo.onLowMemory();
                      }
                  }
                  """
      ).indented()
    )
      .issues(
        LifecycleMethodDetector.ISSUE
      )
      .run()
      .expectClean()
  }
}