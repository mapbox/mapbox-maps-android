package com.mapbox.maps.plugin.location;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.annotation.Nullable;

import com.mapbox.maps.plugin.location.listeneres.CancelableCallback;

class MapboxAnimatorListener extends AnimatorListenerAdapter {

    @Nullable
    private final CancelableCallback cancelableCallback;

    MapboxAnimatorListener(@Nullable CancelableCallback cancelableCallback) {
        this.cancelableCallback = cancelableCallback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnimationCancel(Animator animation) {
        if (cancelableCallback != null) {
            cancelableCallback.onCancel();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnimationEnd(Animator animation) {
        if (cancelableCallback != null) {
            cancelableCallback.onFinish();
        }
    }
}