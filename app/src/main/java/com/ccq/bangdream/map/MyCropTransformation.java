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

    private CropType cropType;

    public MyCropTransformation(int width, int height) {
        this(width, height, CropType.CENTER);
    }

    MyCropTransformation(int width, int height, CropType cropType) {
        super(width, height);
        this.cropType = cropType;
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

        float scaleX = (float) width / toTransform.getWidth();
        float scaleY = (float) height / toTransform.getHeight();
        float scale = Math.max(scaleX, scaleY);

        float scaledWidth = scale * toTransform.getWidth();
        float scaledHeight = scale * toTransform.getHeight();
        float left = 0;
        float top = getTop(scaledHeight);
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Log.d("left", String.valueOf(left));
        Log.d("top", String.valueOf(top));
        Log.d("scaledHeight", String.valueOf(scaledHeight));
        Log.d("scaledWidth", String.valueOf(scaledWidth));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(toTransform, null, targetRect, null);

        return bitmap;
    }

    @Override
    public String key() {
        return "CropTransformation(width=" + width + ", height=" + height + ", cropType=" + cropType
                + ")";
    }

    private float getTop(float scaledHeight) {
        switch (cropType) {
            case TOP:
                return 0;
            case CENTER:
                return (height - scaledHeight) / 2;
            case BOTTOM:
                return height - scaledHeight;
            default:
                return 0;
        }
    }
}
