package com.mapbox.maps.plugin.annotation

/**
 * Interface definition of a callback to be invoked when cluster with annotations has been clicked.
 */
fun interface OnClusterClickListener {
  /**
   * Called when an cluster has been clicked.
   *
   * @param cluster the clicked cluster represented by [ClusterFeature].
   * @return True if this click should be consumed and not passed further to other listeners
   * registered afterwards, false otherwise.
   */
  fun onClusterClick(cluster: ClusterFeature): Boolean
}