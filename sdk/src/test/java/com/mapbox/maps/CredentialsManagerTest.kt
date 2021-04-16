package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CredentialsManagerTest {

  private val context: Context = mockk(relaxUnitFun = true)
  private val resources: Resources = mockk(relaxed = true)

  @Before
  fun setUp() {
    every { context.resources } returns resources
    every { context.packageName } returns "com.mapbox.maps"
    every { resources.getIdentifier(any(), any(), any()) } returns 0
  }

  @Test(expected = MapboxConfigurationException::class)
  fun noToken() {
    val credentialsManager = CredentialsManager()
    assertEquals("", credentialsManager.getAccessToken(context))
  }

  @Test(expected = MapboxConfigurationException::class)
  fun noTokenInDefault() {
    assertEquals("", CredentialsManager.default.getAccessToken(context))
  }

  @Test
  fun setToken() {
    val credentialsManager = CredentialsManager("token")
    assertEquals("token", credentialsManager.getAccessToken(context))
  }

  @Test
  fun getTokenFromXml() {
    every { resources.getIdentifier(any(), any(), any()) } returns 1
    every { context.getString(any()) } returns "token"
    val credentialsManager = CredentialsManager()
    assertEquals("token", credentialsManager.getAccessToken(context))
  }
}