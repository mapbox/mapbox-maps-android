package com.mapbox.maps.extension.style;

import androidx.annotation.NonNull;

import com.mapbox.bindgen.Value;
import com.mapbox.maps.StyleManager;
import com.mapbox.maps.StylePropertyValue;
import com.mapbox.maps.StylePropertyValueKind;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of StyleManager, this shadow StyleManager
 * will be used for the Robolectric unit tests.
 */
@Implements(StyleManager.class)
public class ShadowStyleManager {

  /**
   * @param layerType Style layer type.
   * @param property  Style layer property name.
   * @return The default value of \p property for the layers with type \p layerType.
   * @brief Gets the default value of style layer \p property.
   */
  @Implementation
  public static @NonNull
  StylePropertyValue getStyleLayerPropertyDefaultValue(@NonNull String layerType, @NonNull String property) {
    return new StylePropertyValue(Value.valueOf(0), StylePropertyValueKind.CONSTANT);
  }


  /**
   * @param sourceType Style source type.
   * @param property   Style source property name.
   * @return The default value of \p property for the sources with type \p sourceType.
   * @brief Gets the default value of style source \p property.
   */
  @Implementation
  public static @NonNull
  StylePropertyValue getStyleSourcePropertyDefaultValue(@NonNull String sourceType, @NonNull String property) {
    return new StylePropertyValue(Value.valueOf(0), StylePropertyValueKind.CONSTANT);
  }
}
