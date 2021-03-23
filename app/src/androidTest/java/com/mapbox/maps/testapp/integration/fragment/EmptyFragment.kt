package com.mapbox.maps.testapp.integration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class EmptyFragment : Fragment() {

  var onViewCreated: OnFragmentResumed? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = FrameLayout(inflater.context)
    view.setBackgroundColor(android.R.color.white)
    return view
  }

  override fun onResume() {
    super.onResume()
    onViewCreated?.onResume()
  }

  fun interface OnFragmentResumed {
    fun onResume()
  }
}