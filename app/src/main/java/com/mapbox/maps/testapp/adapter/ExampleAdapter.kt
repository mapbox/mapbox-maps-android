package com.mapbox.maps.testapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.model.SpecificExample

/**
 * Adapter used for the ExampleOverviewActivity.
 *
 * Adapts a SpecificExample to a visual representation to be shown in
 * a RecyclerView [ExampleSectionAdapter.Section].
 */
class ExampleAdapter(private val specificExampleList: List<SpecificExample>) :
  RecyclerView.Adapter<ExampleAdapter.ViewHolder>() {

  open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var labelView: TextView = view.findViewById(R.id.nameView) as TextView
    var descriptionView: TextView = view.findViewById(R.id.descriptionView) as TextView
  }

  override fun getItemCount(): Int {
    return specificExampleList.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.item_single_example, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.labelView.text = specificExampleList[position].getLabel()
    holder.descriptionView.text = specificExampleList[position].getDescription()
  }
}