package com.mapbox.maps.plugin.annotation

/**
 * Interface for annotationManager that supports clustering
 */
interface ClusterAnnotationManager {

  /**
   * The Added clusterClickListeners. To interact with the list you can use [addClusterClickListener] and [removeClusterClickListener] methods.
   */
   val clusterClickListeners: MutableList<OnClusterClickListener>

  /**
   * The Added clusterLongClickListeners. To interact with the list you can use [addClusterLongClickListener] and [removeClusterLongClickListener] methods.
   */
  val clusterLongClickListeners: MutableList<OnClusterLongClickListener>

  /**
   * Add a callback to be invoked when a cluster with annotations has been clicked.
   *
   * @param clusterClickListener the callback to be invoked when a cluster is clicked
   */
  fun addClusterClickListener(clusterClickListener: OnClusterClickListener) = clusterClickListeners.add(clusterClickListener)

  /**
   * Remove a previously added callback that was to be invoked when cluster with annotations has been clicked.
   *
   * @param clusterClickListener - the callback to be removed
   */
  fun removeClusterClickListener(clusterClickListener: OnClusterClickListener) = clusterClickListeners.remove(clusterClickListener)

  /**
   * Add a callback to be invoked when a cluster with annotations has been long clicked.
   *
   * @param onClusterLongClickListener the callback to be invoked when a cluster is long clicked
   */
  fun addClusterLongClickListener(onClusterLongClickListener: OnClusterLongClickListener) = clusterLongClickListeners.add(onClusterLongClickListener)

  /**
   * Remove a previously added callback that was to be invoked when cluster with annotations has been long clicked.
   *
   * @param onClusterLongClickListener - the callback to be removed
   */
  fun removeClusterLongClickListener(onClusterLongClickListener: OnClusterLongClickListener) = clusterLongClickListeners.remove(onClusterLongClickListener)
}