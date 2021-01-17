package com.mapbox.maps.testapp.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.maps.testapp.R

class ItemClickSupport private constructor(private val recyclerView: RecyclerView) {
  private var onItemClickListener: OnItemClickListener? = null
  private var onItemLongClickListener: OnItemLongClickListener? = null
  private val onClickListener = View.OnClickListener { view ->
    if (onItemClickListener != null) {
      val holder = recyclerView.getChildViewHolder(view)
      onItemClickListener!!.onItemClicked(recyclerView, holder.adapterPosition, view)
    }
  }
  private val onLongClickListener = View.OnLongClickListener { view ->
    if (onItemLongClickListener != null) {
      val holder = recyclerView.getChildViewHolder(view)
      return@OnLongClickListener onItemLongClickListener!!.onItemLongClicked(
        recyclerView,
        holder.adapterPosition,
        view
      )
    }
    false
  }
  private val attachListener = object : RecyclerView.OnChildAttachStateChangeListener {
    override fun onChildViewAttachedToWindow(view: View) {
      if (onItemClickListener != null) {
        view.setOnClickListener(onClickListener)
      }
      if (onItemLongClickListener != null) {
        view.setOnLongClickListener(onLongClickListener)
      }
    }

    override fun onChildViewDetachedFromWindow(view: View) {
      // Empty on purpose. Not used.
    }
  }

  init {
    this.recyclerView.setTag(R.id.item_click_support, this)
    this.recyclerView.addOnChildAttachStateChangeListener(attachListener)
  }

  fun setOnItemClickListener(listener: OnItemClickListener): ItemClickSupport {
    onItemClickListener = listener
    return this
  }

  fun setOnItemLongClickListener(listener: OnItemLongClickListener): ItemClickSupport {
    onItemLongClickListener = listener
    return this
  }

  private fun detach(view: RecyclerView) {
    view.removeOnChildAttachStateChangeListener(attachListener)
    view.setTag(R.id.item_click_support, null)
  }

  fun interface OnItemClickListener {

    fun onItemClicked(recyclerView: RecyclerView, position: Int, view: View)
  }

  fun interface OnItemLongClickListener {

    fun onItemLongClicked(recyclerView: RecyclerView, position: Int, view: View): Boolean
  }

  companion object {

    fun addTo(view: RecyclerView): ItemClickSupport {
      var support: ItemClickSupport? = view.getTag(R.id.item_click_support) as? ItemClickSupport
      if (support == null) {
        support = ItemClickSupport(view)
      }
      return support
    }

    fun removeFrom(view: RecyclerView): ItemClickSupport? {
      val support = view.getTag(R.id.item_click_support) as ItemClickSupport
      support.detach(view)
      return support
    }
  }
}