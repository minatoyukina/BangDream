package com.ccq.bangdream.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.CropTransformation;


public class MyCropTransformation extends CropTransformation {

    private int position;
    private Bitmap bgBitmap;

    MyCropTransformation(int width, int height, int position, Bitmap bgBitmap) {
        super(width, height);
        this.width = width;
        this.height = height;
        this.position = position;
        this.bgBitmap = bgBitmap;
    }

    private int width;
    private int height;



    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        width = width == 0 ? toTransform.getWidth() : width;
        height = height == 0 ? toTransform.getHeight() : height;

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        bitmap.setHasAlpha(true);

        float realWidth = toTransform.getWidth();

        float left = (float) (width - width * position) / 2;

        RectF targetRect = new RectF(left, 0, left + realWidth, height);

        Log.d("left", String.valueOf(left));
        Log.d("realWidth", String.valueOf(realWidth));


        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bgBitmap, null, targetRect, null);
        canvas.drawBitmap(toTransform, null, targetRect, null);

        return bitmap;
    }

    @Override
    public String key() {
        return "CropTransformation(width=" + width + ", height=" + height + ")";
    }

}
