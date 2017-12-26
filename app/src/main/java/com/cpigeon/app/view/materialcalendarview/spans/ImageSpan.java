package com.cpigeon.app.view.materialcalendarview.spans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.text.style.LineBackgroundSpan;

/**
 * Created by Zhu TingYu on 2017/12/25.
 */

public class ImageSpan implements LineBackgroundSpan {

    Bitmap bitmap;
    private static final int size = 60;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ImageSpan(Context context, @DrawableRes int id) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), id);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        setScale();
        c.drawBitmap(bitmap, right - bitmap.getWidth(), -16, p);
    }

    private void setScale() {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
// 设置想要的大小
// 计算缩放比例
        float scaleWidth = ((float) size) / width;
        float scaleHeight = ((float) size) / height;
// 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
// 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
