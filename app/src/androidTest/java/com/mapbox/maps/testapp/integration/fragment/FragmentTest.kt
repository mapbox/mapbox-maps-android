package com.mapbox.maps.testapp.integration.fragment

import android.R
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mapbox.maps.testapp.EmptyActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Integration test for fragments
 */
class FragmentTest {

  @get:Rule
  var rule: ActivityScenarioRule<EmptyActivity> = ActivityScenarioRule(EmptyActivity::class.java)

  lateinit var container: FrameLayout
  lateinit var countDownLatch: CountDownLatch

  @Before
  fun setUp() {
    rule.scenario.onActivity {
      container = FrameLayout(it)
      container.id = R.id.content
      it.setContentView(container)
    }
  }

  @Test
  fun transactionAdd() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      val fragment = addMapFragment(it)
      fragment.getViewAsync {
        fragment.loadMap {
          countDownLatch.countDown()
        }
      }
    }
    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun transactionRemove() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      val fragment = addMapFragment(it)
      fragment.getViewAsync {
        fragment.loadMap {
          val fragmentTransaction = it.supportFragmentManager.beginTransaction()
          fragmentTransaction.remove(fragment)
          fragmentTransaction.commit()
          countDownLatch.countDown()
        }
      }
    }
    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun transactionReplace() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      val fragment = addMapFragment(it)
      fragment.getViewAsync {
        fragment.loadMap {
          val emptyFragment = EmptyFragment()
          emptyFragment.onViewCreated = EmptyFragment.OnFragmentResumed {
            countDownLatch.countDown()
          }
          val fragmentTransaction = it.supportFragmentManager.beginTransaction()
          fragmentTransaction.replace(R.id.content, emptyFragment)
          fragmentTransaction.commit()
        }
      }
    }
    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun transactionBackstack() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      val fragment = addMapFragment(it)
      fragment.getViewAsync {
        fragment.loadMap {
          val mapFragment = MapFragment()
          val fragmentTransaction: FragmentTransaction = it.supportFragmentManager.beginTransaction()
          fragmentTransaction.replace(R.id.content, mapFragment)
          fragmentTransaction.addToBackStack(FRAGMENT_TAG)
          fragmentTransaction.commit()

          mapFragment.getViewAsync {
            mapFragment.loadMap {
              it.supportFragmentManager.popBackStack(FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
              countDownLatch.countDown()
            }
          }
        }
      }
    }
    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  private fun addMapFragment(activity: AppCompatActivity): MapFragment {
    val fragment = MapFragment()
    val fragmentTransaction: FragmentTransaction =
      activity.supportFragmentManager.beginTransaction()
    fragmentTransaction.add(R.id.content, fragment)
    fragmentTransaction.commit()
    return fragment
  }

  companion object {
    const val FRAGMENT_TAG = "foo"
  }
}