package com.mapbox.maps.testapp.integration.surface

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.testapp.examples.SurfaceActivity
import com.mapbox.maps.testapp.integration.BaseReuseIntegrationTest
import org.junit.runner.RunWith

/**
 * Regression test that validates reopening an Activity with a surface using ContextMode.SHARED
 */
@RunWith(AndroidJUnit4::class)
class SurfaceTest : BaseReuseIntegrationTest(SurfaceActivity::class.java)