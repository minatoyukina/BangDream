package com.ccq.bangdream.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.CropTransformation;

public class MyCropTransformation extends CropTransformation {
    public MyCropTransformation(int width, int height) {
        super(width, height);
    }

    public MyCropTransformation(int width, int height, CropType cropType) {
        super(width, height, cropType);
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return super.transform(context, pool, toTransform, outWidth, outHeight);
    }

    @Override
    public String key() {
        return super.key();
    }
}
