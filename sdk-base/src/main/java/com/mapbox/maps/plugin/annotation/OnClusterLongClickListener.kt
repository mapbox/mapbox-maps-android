package com.mapbox.maps.plugin.annotation

/**
 * Interface definition of a callback to be invoked when cluster with annotations has been long clicked.
 **/
fun interface OnClusterLongClickListener {
  /**
   * Called when an cluster has been long clicked.
   *
   * @param cluster the long clicked cluster represented by [ClusterFeature].
   * @return True if this click should be consumed and not passed further to other listeners
   * registered afterwards, false otherwise.
   */
  fun onClusterLongClick(cluster: ClusterFeature): Boolean
}