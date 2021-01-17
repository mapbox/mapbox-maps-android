package com.mapbox.maps.testapp.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ExampleSectionAdapter(
  private val context: Context,
  @field:LayoutRes
  private val sectionRes: Int,
  @field:IdRes
  private val textRes: Int,
  private val adapter: ExampleAdapter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private var sections: SparseArray<Section> = SparseArray()
  private var valid = true

  init {
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onChanged() {
        valid = adapter.itemCount > 0
        notifyDataSetChanged()
      }

      override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        valid = adapter.itemCount > 0
        notifyItemRangeChanged(positionStart, itemCount)
      }

      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        valid = adapter.itemCount > 0
        notifyItemRangeInserted(positionStart, itemCount)
      }

      override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        valid = adapter.itemCount > 0
        notifyItemRangeRemoved(positionStart, itemCount)
      }
    })
  }

  override fun getItemCount(): Int {
    return if (valid) adapter.itemCount + sections.size() else 0
  }

  class SectionViewHolder(@NonNull view: View, @IdRes textRes: Int) : RecyclerView.ViewHolder(view) {
    var title: TextView = view.findViewById<View>(textRes) as TextView
  }

  override fun onCreateViewHolder(parent: ViewGroup, typeView: Int): RecyclerView.ViewHolder {
    return if (typeView == SECTION_TYPE) {
      val view = LayoutInflater.from(context).inflate(sectionRes, parent, false)
      SectionViewHolder(view, textRes)
    } else {
      adapter.onCreateViewHolder(parent, typeView - 1)
    }
  }

  override fun onBindViewHolder(sectionViewHolder: RecyclerView.ViewHolder, position: Int) {
    if (isSectionHeaderPosition(position)) {
      val cleanTitle = sections.get(position).title.toString().replace("_", " ")
      val newSectionViewHolder = sectionViewHolder as SectionViewHolder
      newSectionViewHolder.title.text = cleanTitle
    } else {
      adapter.onBindViewHolder(sectionViewHolder as ExampleAdapter.ViewHolder, getConvertedPosition(position))
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (isSectionHeaderPosition(position))
      SECTION_TYPE
    else
      adapter.getItemViewType(getConvertedPosition(position)) + 1
  }

  class Section(internal var firstPosition: Int, title: CharSequence) {
    internal var sectionedPosition: Int = 0
    var title: CharSequence
      internal set

    init {
      this.title = title
    }
  }

  fun setSections(sections: Array<Section>) {
    this.sections.clear()

    Arrays.sort(sections) { section, section1 ->
      when {
        section.firstPosition == section1.firstPosition -> 0
        section.firstPosition < section1.firstPosition -> -1
        else -> 1
      }
    }

    for ((offset, section) in sections.withIndex()) {
      section.sectionedPosition = section.firstPosition + offset
      this.sections.append(section.sectionedPosition, section)
    }

    notifyDataSetChanged()
  }

  fun getConvertedPosition(sectionedPosition: Int): Int {
    if (isSectionHeaderPosition(sectionedPosition)) {
      return RecyclerView.NO_POSITION
    }

    var offset = 0
    for (i in 0 until sections.size()) {
      if (sections.valueAt(i).sectionedPosition > sectionedPosition) {
        break
      }
      --offset
    }
    return sectionedPosition + offset
  }

  fun isSectionHeaderPosition(position: Int): Boolean {
    return sections.get(position) != null
  }

  override fun getItemId(position: Int): Long {
    return (
      if (isSectionHeaderPosition(position))
        Integer.MAX_VALUE - sections.indexOfKey(position).toLong()
      else
        adapter.getItemId(getConvertedPosition(position))
      )
  }

  companion object {
    private const val SECTION_TYPE = 0
  }
}