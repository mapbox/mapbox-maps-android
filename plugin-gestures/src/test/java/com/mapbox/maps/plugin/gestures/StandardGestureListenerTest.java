package com.mapbox.maps.plugin.gestures;

import static com.mapbox.maps.plugin.Plugin.MAPBOX_CAMERA_PLUGIN_ID;

import static io.mockk.MockKKt.every;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.delegates.MapDelegateProvider;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;
import com.mapbox.maps.plugin.gestures.GesturesPluginImpl.StandardGestureListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import io.mockk.MockKAnnotations;
import io.mockk.impl.annotations.RelaxedMockK;

@RunWith(RobolectricTestRunner.class)
public class StandardGestureListenerTest {
  @RelaxedMockK
  MapDelegateProvider delegateProvider;
  @RelaxedMockK
  MapPluginProviderDelegate providerDelegate;
  @RelaxedMockK
  CameraAnimationsPlugin cameraAnimationsPlugin;
  GesturesPluginImpl plugin;
  StandardGestureListener listener;

  @Before
  public void setup() {
    MockKAnnotations.INSTANCE.init(new Object[]{this}, true, true, true);
    every((matcher) -> delegateProvider.getMapPluginProviderDelegate())
        .returns(providerDelegate);
    every((matcher) -> providerDelegate.getPlugin(MAPBOX_CAMERA_PLUGIN_ID))
        .returns(cameraAnimationsPlugin);
    plugin = new GesturesPluginImpl(RuntimeEnvironment.getApplication(), 1.f);
    listener = plugin.new StandardGestureListener(1.f);
    plugin.onDelegateProvider(delegateProvider);
  }

  @After
  public void tearDown() {
    delegateProvider = null;
    providerDelegate = null;
    cameraAnimationsPlugin = null;
    plugin = null;
    listener = null;
  }

  @Test
  public void testOnDown() {
    listener.onDown(null);
  }

  @Test
  public void testOnSingleTapUp() {
    listener.onSingleTapUp(null);
  }

  @Test
  public void testOnSingleTapConfirmed() {
    listener.onSingleTapConfirmed(null);
  }

  @Test
  public void testOnDoubleTap() {
    listener.onDoubleTap(null);
  }

  @Test
  public void testOnShowPress() {
    listener.onShowPress(null);
  }

  @Test
  public void testOnDoubleTapEvent() {
    listener.onDoubleTapEvent(null);
  }

  @Test
  public void testOnLongPress() {
    listener.onLongPress(null);
  }

  @Test
  public void testOnFlingNull() {
    listener.onFling(null, null, 0.f, 0.f);
  }

  @Test
  public void testOnScroll() {
    listener.onScroll(null, null, 0.f, 0.f);
  }
}
