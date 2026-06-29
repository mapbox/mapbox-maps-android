package com.mapbox.maps

import com.mapbox.verifyOnce
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SafeBinderCallTest {

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = UnconfinedTestDispatcher(testScheduler)

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
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

  @Test
  fun `safeBinderCall should return operation result when successful`() = runTest {
    val result = safeBinderCall(fallback = 0, logTag = "Test", dispatcher = testDispatcher) { 42 }
    assertEquals(42, result)
  }

  @Test
  fun `safeBinderCall should return fallback when operation throws`() = runTest {
    val result = safeBinderCall(fallback = 100, logTag = "Test", dispatcher = testDispatcher) {
      throw RuntimeException("Test exception")
    }
    assertEquals(100, result)
    verifyOnce { logE("Test", "System call failed: Test exception, using fallback") }
  }

  @Test
  fun `safeBinderCall should rethrow CancellationException`() = runTest {
    var reached = false
    assertThrows(CancellationException::class.java) {
      kotlinx.coroutines.runBlocking {
        safeBinderCall(fallback = 0, logTag = "Test", dispatcher = testDispatcher) {
          throw CancellationException("scope cancelled")
        }
        reached = true
      }
    }
    assertEquals(false, reached)
  }

  @Test
  fun `safeBinderCall should handle null fallback`() = runTest {
    val result = safeBinderCall<String?>(fallback = null, logTag = "Test", dispatcher = testDispatcher) { null }
    assertEquals(null, result)
  }
}