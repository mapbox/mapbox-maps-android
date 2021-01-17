package com.mapbox.maps.plugin.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.mapbox.maps.plugin.location.utils.BitmapUtils;

import static com.mapbox.maps.plugin.location.Utils.generateShadow;

class LayerBitmapProvider {

  private final Context context;

  LayerBitmapProvider(Context context) {
    this.context = context;
  }

  @NonNull
  Bitmap generateBitmap(@DrawableRes int drawableRes, @ColorInt Integer tintColor) {
    Drawable drawable = BitmapUtils.getDrawableFromRes(context, drawableRes, tintColor);
    return BitmapUtils.getBitmapFromDrawable(drawable);
  }

  @NonNull
  Bitmap generateShadowBitmap(@NonNull LocationComponentOptions options) {
    Drawable shadowDrawable = BitmapUtils.getDrawableFromRes(context, R.drawable.mapbox_user_icon_shadow);
    return generateShadow(shadowDrawable, options.elevation());
  }
}