package com.cpigeon.app.message.ui.idCard;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.RelativeLayout;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.AppManager;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.utils.IdCardIdentification;
import com.cpigeon.app.utils.ScreenTool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/30.
 */

public class IdCardCameraActivity extends AppCompatActivity {

    private SurfaceView surfaceview;
    private Camera camera;
    private AppCompatImageView take;
    private AppCompatImageView frame;

    private int screenW;
    private int screenH;

    private int photoH;
    private int photoW;

    private static final double RATIO_PHOTO_W = 1.5f;
    private static final double RATIO_SCREN_W = (3f / 5f);


    private IdCardIdentification idCardIdentification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenH = ScreenTool.getScreenHeight(getApplicationContext());
        screenW = ScreenTool.getScreenWidth(getApplicationContext());

       /*photoW = ScreenTool.dip2px(400);
       photoH = ScreenTool.dip2px(260);*/

        idCardIdentification = new IdCardIdentification();

        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_id_card_camera_layout);
        initView();
    }

    public void initView() {
        take = findViewById(R.id.take);
        frame = findViewById(R.id.frame);
        frame.setBackgroundResource(R.drawable.background_blue);

        int w = (int) (screenW * RATIO_SCREN_W);
        int h = (int) (w / RATIO_PHOTO_W);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        frame.setLayoutParams(layoutParams);

        surfaceview = findViewById(R.id.surfaceview);
        SurfaceHolder holder = surfaceview.getHolder();
        holder.setFixedSize(screenW, screenH);// 设置分辨率
        holder.setKeepScreenOn(true);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // SurfaceView只有当activity显示到了前台，该控件才会被创建     因此需要监听surfaceview的创建
        holder.addCallback(new MySurfaceCallback());

        take.setOnClickListener(v -> {
            takepicture();
        });

    }

    //拍照的函数
    public void takepicture() {
        /*
         * shutter:快门被按下
         * raw:相机所捕获的原始数据
         * jpeg:相机处理的数据
         */
        camera.takePicture(null, null, new MyPictureCallback());
    }

    private final class MyPictureCallback implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            try {

                Bitmap bitmap = Bytes2Bimap(data);
                Matrix m = new Matrix();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                //m.setRotate(90);
                //将照片右旋90度
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m,
                        true);

                int wId = (int) (photoW * RATIO_SCREN_W);
                int hId = (int) (wId / RATIO_PHOTO_W);

                Log.d("TAG", "width " + width);
                Log.d("TAG", "height " + height);

                //截取透明框内照片(身份证)
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap
                        , (photoW  - wId) / 2
                        , (photoH - hId) / 2
                        , wId
                        , hId);



                data = Bitmap2Bytes(bitmap1);
                File file = new File(Environment.getExternalStorageDirectory(), "身份证" + ".jpg");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();



                idCardIdentification.IdCardOcr(file.getPath(),IdCardIdentification.TYPE_POSITIVE,idCardInfoEntity -> {

                    camera.startPreview();
                });

                // 在拍照的时候相机是被占用的,拍照之后需要重新预览
                //跳到新的页面

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /**
     * 监听surfaceview的创建
     *
     * @author Administrator
     *         Surfaceview只有当activity显示到前台，该空间才会被创建
     */
    private final class MySurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            // TODO Auto-generated method stub

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub

            try {
                // 当surfaceview创建就去打开相机
                camera = Camera.open();
                Camera.Parameters params = camera.getParameters();
                List<Camera.Size> sizeList = params.getSupportedPreviewSizes();
                Camera.Size optionSize = getOptimalPreviewSize(sizeList, surfaceview.getWidth(), surfaceview.getHeight());
                params.setPreviewSize(optionSize.width,optionSize.height);//把camera.size赋值到parameters
                // Log.i("i", params.flatten());
                params.setJpegQuality(80);  // 设置照片的质量
                photoW = optionSize.width;
                photoH = optionSize.height;
                params.setPictureSize(optionSize.width, optionSize.height);
                params.setPreviewFrameRate(5);  // 预览帧率
                camera.setParameters(params); // 将参数设置给相机
                //右旋90度，将预览调正
                //camera.setDisplayOrientation(90);
                // 设置预览显示
                camera.setPreviewDisplay(surfaceview.getHolder());
                // 开启预览
                camera.startPreview();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //对焦
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
                camera.cancelAutoFocus();
            }
        });

        return super.onTouchEvent(event);
    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

}


