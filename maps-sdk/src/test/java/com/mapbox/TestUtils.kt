package com.mapbox

import io.mockk.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal fun verifyNo(
  ordering: Ordering = Ordering.UNORDERED,
  timeout: Long = 0,
  verifyBlock: MockKVerificationScope.() -> Unit
) = verify(
  ordering = ordering,
  exactly = 0,
  timeout = timeout,
  verifyBlock = verifyBlock,
)

internal fun verifyOnce(
  ordering: Ordering = Ordering.UNORDERED,
  timeout: Long = 0,
  verifyBlock: MockKVerificationScope.() -> Unit
) = verify(
  ordering = ordering,
  exactly = 1,
  timeout = timeout,
  verifyBlock = verifyBlock,
)

internal fun waitZeroCounter(startCounter: Int = 1, timeoutMillis: Int = 1000, runnable: CountDownLatch.() -> Unit) {
  val countDownLatch = CountDownLatch(startCounter)
  runnable(countDownLatch)
  if (!countDownLatch.await(timeoutMillis.toLong(), TimeUnit.MILLISECONDS)) {
    throw TimeoutException("Test had failed, counter is not zero but $startCounter after $timeoutMillis milliseconds!")
  }
}

internal fun CountDownLatch.countDownEvery(stubBlock: MockKMatcherScope.() -> Unit) {
  every(stubBlock).answers { countDown() }
}