package com.mapbox

import io.mockk.MockKVerificationScope
import io.mockk.Ordering
import io.mockk.verify

internal fun verifyNo(
  ordering: Ordering = Ordering.UNORDERED,
  inverse: Boolean = false,
  timeout: Long = 0,
  verifyBlock: MockKVerificationScope.() -> Unit
) = verify(
  ordering,
  inverse,
  1,
  Int.MAX_VALUE,
  exactly = 0,
  timeout,
  verifyBlock
)

internal fun verifyOnce(
  ordering: Ordering = Ordering.UNORDERED,
  inverse: Boolean = false,
  timeout: Long = 0,
  verifyBlock: MockKVerificationScope.() -> Unit
) = verify(
  ordering,
  inverse,
  1,
  Int.MAX_VALUE,
  exactly = 1,
  timeout,
  verifyBlock
)
