package com.ccq.bangdream.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.CropTransformation;

public class MyCropTransformation extends CropTransformation {


    public enum CropType {
        PRE,
        RANDOM,
        NEXT
    }

    public MyCropTransformation(int width, int height) {
        super(width, height);
    }

    public MyCropTransformation(int width, int height, CropTransformation.CropType cropType) {
        super(width, height, cropType);
    }

    private int width;
    private int height;

    private MyCropTransformation.CropType cropType = MyCropTransformation.CropType.RANDOM;


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
        float left = (width - scaledWidth) / 2;
        float top = getTop(scaledHeight);
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

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
            case PRE:
                return 0;
            case RANDOM:
                return (height - scaledHeight) / 2;
            case NEXT:
                return height - scaledHeight;
            default:
                return 0;
        }
    }
}
