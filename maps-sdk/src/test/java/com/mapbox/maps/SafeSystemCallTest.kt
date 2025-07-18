package com.mapbox.maps

import com.mapbox.verifyOnce
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SafeSystemCallTest {

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
  private val mainTestDispatcher = UnconfinedTestDispatcher(testScheduler, "MainTestDispatcher")

  @Before
  fun setUp() {
    Dispatchers.setMain(mainTestDispatcher)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    Dispatchers.resetMain()
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  // Core functionality tests that should run quickly

  @Test
  fun `safeSystemCall should return operation result when successful`() = runTest {
    val expected = 42
    val result = safeSystemCall(
      fallback = 0,
      logTag = "Test",
      dispatcher = testDispatcher
    ) {
      expected
    }
    assertEquals(expected, result)
  }

  @Test
  fun `safeSystemCall should return fallback when operation throws exception`() = runTest {
    val fallback = 100
    val result = safeSystemCall(
      fallback = fallback,
      logTag = "Test",
      dispatcher = testDispatcher
    ) {
      throw RuntimeException("Test exception")
    }
    assertEquals(fallback, result)
    verifyOnce { logE("Test", "System call failed: Test exception, using fallback") }
  }

  @Test
  fun `safeSystemCall should return fallback when operation times out`() = runTest {
    val fallback = 200
    val result = safeSystemCall(
      timeoutMs = 10L,
      fallback = fallback,
      logTag = "Test",
      dispatcher = testDispatcher
    ) {
      delay(100) // Simulate slow operation with coroutine delay
      42
    }
    assertEquals(fallback, result)
    verifyOnce { logW("Test", "System call timed out after 10ms, using fallback") }
  }

  @Test
  fun `safeSystemCall should use default log tag when not specified`() = runTest {
    safeSystemCall(
      fallback = 0,
      dispatcher = testDispatcher
    ) {
      throw RuntimeException("Test exception")
    }
    verifyOnce { logE("SystemCall", "System call failed: Test exception, using fallback") }
  }

  @Test
  fun `safeSystemCall should handle null values properly`() = runTest {
    val result = safeSystemCall(
      fallback = null,
      logTag = "Test",
      dispatcher = testDispatcher
    ) {
      null
    }
    assertEquals(null, result)
  }

  @Test
  fun `safeSystemCall should handle string values properly`() = runTest {
    val expected = "test_result"
    val result = safeSystemCall(
      fallback = "fallback",
      logTag = "Test",
      dispatcher = testDispatcher
    ) {
      expected
    }
    assertEquals(expected, result)
  }

  @Test
  fun `safeSystemCallWithCallback should execute callback with result on success`() = runTest {
    val expected = 42
    var callbackResult = -1
    safeSystemCallWithCallback(
      fallback = 0,
      logTag = "Test",
      dispatcher = testDispatcher,
      mainDispatcher = mainTestDispatcher,
      operation = { expected }
    ) { result ->
      callbackResult = result
    }
    assertEquals(expected, callbackResult)
  }

  @Test
  fun `safeSystemCallWithCallback should execute callback with fallback on exception`() = runTest {
    val fallback = 100
    var callbackResult = -1
    safeSystemCallWithCallback(
      fallback = fallback,
      logTag = "Test",
      dispatcher = testDispatcher,
      mainDispatcher = mainTestDispatcher,
      operation = { throw RuntimeException("Test exception") }
    ) { result ->
      callbackResult = result
    }
    assertEquals(fallback, callbackResult)
    verifyOnce { logE("Test", "System call failed: Test exception, using fallback") }
  }

  @Test
  fun `safeSystemCallWithCallback should execute callback with fallback on timeout`() = runTest {
    val fallback = 200
    var callbackResult = -1
    safeSystemCallWithCallback(
      timeoutMs = 10L,
      fallback = fallback,
      logTag = "Test",
      dispatcher = testDispatcher,
      mainDispatcher = mainTestDispatcher,
      operation = {
        delay(100) // Simulate slow operation with coroutine delay
        42
      }
    ) { result ->
      callbackResult = result
    }
    assertEquals(fallback, callbackResult)
    verifyOnce { logW("Test", "System call timed out after 10ms, using fallback") }
  }

  @Test
  fun `safeSystemCallWithCallback should handle null values properly`() = runTest {
    var callbackResult: String? = "initial"
    safeSystemCallWithCallback(
      fallback = null,
      logTag = "Test",
      dispatcher = testDispatcher,
      mainDispatcher = mainTestDispatcher,
      operation = { null }
    ) { result ->
      callbackResult = result
    }
    assertEquals(null, callbackResult)
  }

  @Test
  fun `safeSystemCallWithCallback should handle string values properly`() = runTest {
    val expected = "test_result"
    var callbackResult = "initial"
    safeSystemCallWithCallback(
      fallback = "fallback",
      logTag = "Test",
      dispatcher = testDispatcher,
      mainDispatcher = mainTestDispatcher,
      operation = { expected }
    ) { result ->
      callbackResult = result
    }
    assertEquals(expected, callbackResult)
  }
}