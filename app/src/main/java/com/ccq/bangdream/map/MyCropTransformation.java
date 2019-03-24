package com.ccq.bangdream.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.CropTransformation;

import java.util.Random;


public class MyCropTransformation extends CropTransformation {

    private int position;
    private String songId;

    private CropType cropType;


    MyCropTransformation(int width, int height, CropType cropType, String songId) {
        super(width, height);
        this.width = width;
        this.height = height;
        this.cropType = cropType;
        this.songId = songId;
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
        float top = getTop(scaledHeight);
//        float left = (width - (float)toTransform.getWidth()) / 2;

        int pieces = Math.round((float) toTransform.getWidth() / width);
        Random random = new Random();
        int i = random.nextInt(pieces);
        float left = (float) (width - width * i) / 2;
        RectF targetRect = new RectF(left, top, left + (float) toTransform.getWidth(), top + scaledHeight);
        Log.d("width", String.valueOf(width));
        Log.d("real-width", String.valueOf(toTransform.getWidth()));
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
