package com.mapbox.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * To avoid calling native method of Projection, this shadow class
 * will be used for the Robolectric unit tests.
 */
@Implements(Logger.class)
public class ShadowLogger {
  /**
   * @brief Send a Debug log message.
   *
   * @param tag An optional string to identify the source or catgeory of the log message.
   */
  @Implementation
  public static void d(@Nullable String tag, @NonNull String message) {
  }

  /**
   * @brief Send an Info log message.
   *
   * @param tag An optional string to identify the source or catgeory of the log message.
   */
  @Implementation
  public static void i(@Nullable String tag, @NonNull String message) {
  }

  /**
   * @brief Send a Warning log message.
   *
   * @param tag An optional string to identify the source or catgeory of the log message.
   */
  @Implementation
  public static void w(@Nullable String tag, @NonNull String message) {
  }

  /**
   * @brief Send a Error log message.
   *
   * @param tag An optional string to identify the source or catgeory of the log message.
   */
  @Implementation
  public static void e(@Nullable String tag, @NonNull String message) {
  }

  @Implementation
  public static void addCategoryFilter(@NonNull String message) {
  }

  @Implementation
  public static void removeCategoryFilter(@NonNull String message) {
  }
}