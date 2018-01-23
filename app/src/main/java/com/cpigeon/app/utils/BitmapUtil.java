package com.cpigeon.app.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class BitmapUtil {


    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bmp, float degrees){
        Matrix matrix=new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bmp,0,0,bmp.getWidth(), bmp.getHeight(),matrix,true);
    }


    public static SoftReference<Bitmap> getCropBitmapFromAssets(Context context, String src,
                                                                int width, int height) {
        if (src == null) {
            return null;
        }
        if (src.equals("")) {
            return null;
        }
        AssetManager am = context.getResources().getAssets();
        SoftReference<Bitmap> bitmap = null;
        BitmapFactory.Options opts = null;
        opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        InputStream is = null;
        try {
            is = am.open(src);
            BitmapFactory.decodeStream(is, null, opts);
        } catch (IOException e) {
            return null;
        }
        int maxth = Math.max(width, height);
        int maxth1 = Math.max(opts.outWidth, opts.outHeight);
        if (maxth > maxth1) {
            try {
                is = am.open(src);
            } catch (IOException e) {
                return null;
            }
            opts.inJustDecodeBounds = false;
            bitmap = new SoftReference<Bitmap>(BitmapFactory.decodeStream(is,
                    null, opts));
        } else {
            final int minSideLength = Math.min(width, height);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, width
                    * height);
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            try {
                is = am.open(src);
                bitmap = new SoftReference<Bitmap>(BitmapFactory.decodeStream(is, null, opts));
            } catch (IOException e) {
                return null;
            } catch (OutOfMemoryError e) {
            }
        }
        return bitmap;
    }


    public static SoftReference<Bitmap> getCropBitmapFromFile(String src,
                                                              int width, int height) {
        if (src == null) {
            return null;
        }
        if (src.equals("")) {
            return null;
        }
        SoftReference<Bitmap> bitmap = null;
        BitmapFactory.Options opts = null;
        opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, opts);
        BufferedInputStream in = null;
        int maxth = Math.max(width, height);
        int maxth1 = Math.max(opts.outWidth, opts.outHeight);
        if (maxth > maxth1) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(src)));
            } catch (FileNotFoundException e) {
                return null;
            }
            opts.inJustDecodeBounds = false;
            bitmap = new SoftReference<Bitmap>(BitmapFactory.decodeStream(in,
                    null, opts));
        } else {
            final int minSideLength = Math.min(width, height);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, width
                    * height);
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            try {
                bitmap = new SoftReference<Bitmap>(BitmapFactory.decodeFile(
                        src, opts));
            } catch (OutOfMemoryError e) {
            }
        }
        return bitmap;
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 图片合成 左下角
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createBitmapLowerLeft(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }

        double b = (double) (watermark.getWidth()) / watermark.getHeight();

        src = Bitmap.createScaledBitmap(src, 768, 1028, true);
//        src = Bitmap.createScaledBitmap(src, 1080, 1920, true);
        watermark = Bitmap.createScaledBitmap(watermark, (int) (150 * b) + 135, 163, true);
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
//        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图

        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
//        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        cv.drawBitmap(watermark, 5, h - wh + 5, null);// 在src的左下角下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
//        return Bitmap.createScaledBitmap(newb, 960, 1028, true);
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param path
     */
    public static boolean saveJPGE_After(Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void saveJPGE_After(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);

            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateResources(Context context, String path) {
        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }


    public static void makeDir(File file) {
        File tempPath = new File(file.getParent());
        if (!tempPath.exists()) {
            tempPath.mkdirs();
        }
    }

    /**
     * 图片合成 中心
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createBitmapCenter(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();

        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
//        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        cv.drawBitmap(watermark, w / 2 - ww / 2, h / 2 - wh / 2, null);// 在src的左下角下角画入水印
        // save all clip
//        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.save(Canvas.CLIP_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * 把被系统旋转了的图片，转正
     *
     * @param angle 旋转角度
     * @return bitmap 图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


}
