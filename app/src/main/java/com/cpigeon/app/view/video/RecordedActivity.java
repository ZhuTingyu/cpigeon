package com.cpigeon.app.view.video;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.circle.LocationManager;
import com.cpigeon.app.utils.BitmapUtils;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.DateUtils;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ScreenTool;
import com.cpigeon.app.utils.cache.CacheManager;
import com.cpigeon.app.utils.http.CommonUitls;
import com.cpigeon.app.view.video.camera.SensorControler;
import com.cpigeon.app.view.video.widget.CameraView;
import com.cpigeon.app.view.video.widget.CircularProgressView;
import com.cpigeon.app.view.video.widget.FocusImageView;


import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;


/**
 * Created by cj on 2017/7/25.
 * desc 视频录制
 * 主要包括 音视频录制、断点续录、对焦等功能
 */

public class RecordedActivity extends Activity implements View.OnClickListener, View.OnTouchListener, SensorControler.CameraFocusListener {

    @BindView(R.id.watermark_gezhu)
    TextView watermarkGeZhu;//鸽主名字
    @BindView(R.id.watermark_time)
    TextView watermarkTime;//水印时间
    @BindView(R.id.watermark_dz)
    TextView watermarkDz;//水印地址

    @BindView(R.id.watermark_llz)
    LinearLayout watermarkLlz;//水印总的布局

    @BindView(R.id.watermark_center_img)
    ImageView waterCenImg;//图片中间水印

    private CameraView mCameraView;
    private CircularProgressView mCapture;
    private FocusImageView mFocus;
    //    private ImageView mBeautyBtn;
//    private ImageView mFilterBtn;
    private ImageView mCameraChange;
    private static final int maxTime = 11000;//最长录制11s
    private boolean pausing = false;
    private boolean recordFlag = false;//是否正在录制

    private int WIDTH = 720, HEIGHT = 1280;

    private long timeStep = 50;//进度条刷新的时间
    long timeCount = 0;//用于记录录制时间
    private boolean autoPausing = false;
    ExecutorService executorService;
    private SensorControler mSensorControler;
    LocationManager locationManager;

    private Unbinder mUnbinder;

    private ImageButton mVideoWc;
    private String savePath;//视频保存路径
    private String type;

    public static final String TYPE_VIDEO = "video";

    View water;
    TextView waterTime;
    TextView waterLocation;
    Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorde_layout);
        mUnbinder = ButterKnife.bind(this);

        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        locationManager = new LocationManager(getBaseContext());
        initView();
    }

    private void initView() {

        initWater();

        //初始化定位
        mLocationClient = new AMapLocationClient(MyApp.getInstance().getBaseContext());
        //初始化定位相关数据

        mCameraView = (CameraView) findViewById(R.id.camera_view);
        mCapture = (CircularProgressView) findViewById(R.id.mCapture);
        mFocus = (FocusImageView) findViewById(R.id.focusImageView);


        mCameraView.setOnTouchListener(this);

        type = getIntent().getStringExtra(IntentBuilder.KEY_TYPE);

        if (type.equals("sgt")) {
            watermarkGeZhu.setVisibility(View.VISIBLE);
        }

        if (type.equals("photo") || type.equals("sgt")) {
            photoOperation();//拍照
        } else if (type.equals("video")) {
            videoOperation();//拍摄视频
        }


        //开启线程，持续传递Bitmap,显示水印
        /*mThread = new Thread(mRunnable);
        mThread.start();*/

        disposable = RxUtils.rollPoling(1, 1000, aLong -> {

            waterTime.setText(DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_DATETIME));

            mCameraView.mCameraDrawer.setBitmap(BitmapUtils.getViewBitmap(water));
        });

        locationManager.setLocationListener(aMapLocation -> {
            waterLocation.setText(aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet());
        }).star();
    }

    private void initWater() {
        water = findViewById(R.id.water_layout);
        waterTime  = water.findViewById(R.id.time);
        waterLocation =water.findViewById( R.id.location);
    }


//    @Override
//    public void onBackPressed() {
//        if (recordFlag) {
//            recordFlag = false;
//        } else {
//            super.onBackPressed();
//        }
//    }

    //-----------------------------------------------------生命周期（不动）------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
        if (recordFlag && autoPausing) {
            mCameraView.resume(true);
            autoPausing = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (recordFlag && !pausing) {
            mCameraView.pause(true);
            autoPausing = true;
        }
        mCameraView.onPause();
    }

    private boolean isStop = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isStop = true;


        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }

        /*if (mThread != null && mThread.isAlive()) {//如果现在正在执行
            mThread.interrupt();//线程结束
//            mThread.stop();
            Log.d(TAG, "run: 页面销毁");
//            mThread.destroy();
        }*/

//        if (mCameraView.mCamera.mCamera != null) {
//            mCameraView.mCamera.mCamera.release();
//        }

        if (mCameraView.mCamera != null) {
            mCameraView.mCamera.close();
        }

        mCameraView.destroyDrawingCache();

        mUnbinder.unbind();//解除奶油刀绑定
        disposable.dispose();
    }


    //-----------------------------------------------------事件处理（操作）------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera_switch://翻转摄像头
                mCameraView.switchCamera();
                if (mCameraView.getCameraId() == 1) {
                    //前置摄像头 使用美颜
//                    mCameraView.changeBeautyLevel(3);
                    mCameraView.changeBeautyLevel(0);
                } else {
                    //后置摄像头不使用美颜
                    mCameraView.changeBeautyLevel(0);
                }
                break;
//            case R.id.mCapture://点击录制视频
//                if (!recordFlag) {//是否正在录制
//                    executorService.execute(recordRunnable);
//                } else if (!pausing) {
//                    mCameraView.pause(false);//暂停
//                    pausing = true;
//                } else {
//                    mCameraView.resume(false);
//                    pausing = false;
//                }
//                break;

            case R.id.video_wc://点击录制完成按钮
//                if (recordFlag) {
//                    recordFlag = false;
//                }

//                mCameraView.resume(false);
//                pausing = false;
                recordFlag = false;
                break;
        }
    }


    //视频录制成功返回
    private void recordComplete(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCapture.setProcess(0);

//                Intent intent = new Intent();
//                intent.putExtra("video_path", path);
//                setResult(0x00232, intent);
//
//                RecordedActivity.this.finish();
//                Toast.makeText(RecordedActivity.this, "文件保存路径：" + path, Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("video_path", path);
                                setResult(0x00232, intent);

                                RecordedActivity.this.finish();
                            }
                        });
                    }
                }).start();
            }
        });
    }

//-----------------------------------------------------线程相关------------------------------------------------------------------------

    /**
     * 视频录制相关线程
     */
    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            recordFlag = true;
            pausing = false;
            autoPausing = false;
            timeCount = 0;
            long time = System.currentTimeMillis();
           // savePath = Constants.getPath("record/", time + ".mp4");
            savePath =getCacheDir().getAbsolutePath() +"/" +time + ".mp4";
            try {
                mCameraView.setSavePath(savePath);
                mCameraView.startRecord();
                while (timeCount <= maxTime && recordFlag) {
                    if (pausing || autoPausing) {
                        continue;
                    }
                    mCapture.setProcess((int) timeCount);
                    Thread.sleep(timeStep);
                    timeCount += timeStep;
                }
                recordFlag = false;
                mCameraView.stopRecord();
                if (timeCount < 2000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecordedActivity.this, "录像时间太短", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    recordComplete(savePath);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 水印bitmap传递到相机线程
     */
   /* private Thread mThread;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            for (; ; ) {

                if (isStop) {
                    break;
                }
                if (RecordedActivity.this.isDestroyed()) {
                    if (mThread != null && mThread.isAlive()) {//如果现在正在执行
                        mThread.interrupt();//线程结束
                    }
                }
                try {
                    if (mThread.isAlive()) {
                        Thread.sleep(1000);
                    } else {
                        mThread.interrupt();//线程结束
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "run: 线程执行");

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (RecordedActivity.this.isDestroyed()) {
                            if (mThread != null && mThread.isAlive()) {//如果现在正在执行
                                mThread.interrupt();//线程结束
                            }
                        } else if (watermarkTime != null && watermarkLlz != null) {
//                            watermarkTime.setText(getStringDate());
//                            //时间
//                            watermarkTime.setText(DateUtils.sdf.format(new Date()) + "   " + weatherlive.getWeather() + "  " + weatherlive.getTemperature() + "℃" + "  " + weatherlive.getWindDirection() + "风");



                            //时间
                            waterTime.setText(DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_DATETIME));
                           *//* Log.d(TAG, (DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_DATETIME)));
                            *//**//*String img_path = MyApp.getInstance().getBaseContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "video_watermark" + ".jpeg";
//                            Bitmap bitmap = BitmapFactory.decodeFile(img_path);*//**//*
                            Log.d(TAG, waterTime.getText().toString());*//*
//                        Bitmap viewWatermarkBitmap1 = BitmapUtils.getViewBitmap(ac_time);//控件转化成有水印的bipmap
                            mCameraView.mCameraDrawer.setBitmap(BitmapUtils.getViewBitmap(water));
                        }
//                       BitmapUtils.saveJPGE_After(RecordedActivity.this, viewWatermarkBitmap1, img_path, 100);//将有水印的bipmap保存
                    }
                });
            }
        }
    };*/


    //-----------------------------------------------------定位相关（不动）------------------------------------------------------------------------
    private String TAG = "RecordedActivity";
    private String TAGA = "RecordedActivitys";
    //初始化定位
    AMapLocationClient mLocationClient;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            Log.d(TAGA, "onLocationChanged: 正在定位");
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    aMapLocationSucceed(aMapLocation);
                }
            }
        }
    };
    /**
     * 初始化天气搜索相关
     */
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;

    /**
     * 天气查询
     *
     * @param city 城市
     */
    public void initWeatherSearch(String city) {

        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(RecordedActivity.this);

        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == 1000) {
                    if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                        weatherlive = weatherLiveResult.getLiveResult();
                        weatherSearchSucceed(weatherlive);
                    } else {
                        Log.d(TAG, "操作2: ");
//                        ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
                    }
                } else {
                    Log.d(TAG, "操作3: ");
//                    ToastUtil.showerror(WeatherSearchActivity.this, rCode);
                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {
            }
        });
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

//-----------------------------------------------------定位，天气查询成功回调（操作）------------------------------------------------------------------------

    private void aMapLocationSucceed(AMapLocation aMapLocation) {
        //定位获取数据成功
        Log.d(TAG, "onLocationChanged: " + aMapLocation.getLatitude() + "/" + aMapLocation.getLongitude());

        //地址
        /*watermarkDz.setText(aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet() + aMapLocation.getStreetNum());
        //设置经纬度
        watermarkLo.setText(("" + GPSFormatUtils.LoLatoD(aMapLocation.getLongitude()) + "°" + GPSFormatUtils.LoLatoM(aMapLocation.getLongitude()) + "\'" + GPSFormatUtils.LoLatoS(aMapLocation.getLongitude()) + "\"").trim() + "E");
        watermarkLa.setText(("" + GPSFormatUtils.LoLatoD(aMapLocation.getLatitude()) + "°" + GPSFormatUtils.LoLatoM(aMapLocation.getLatitude()) + "\'" + GPSFormatUtils.LoLatoS(aMapLocation.getLatitude()) + "\"").trim() + "N");


        //获取海拔高度
        LocationManager GpsManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = GpsManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
//            watermark_weather.setText(we + "  " + t + "度" + "  " + wd + "风" + "  海拔：" + new DecimalFormat("#.00").format(hb));
//            watermark_weather.setText(we + "  " + t + "度" + "  " + wd + "风");
            watermarkHb.setText("海拔：" + CommonUitls.strTwo(location.getAltitude()) + "米");
        } else {
//            watermark_weather.setText(we + "  " + t + "度" + "  " + wd + "风" + "  海拔：无");
//            watermark_weather.setText(we + "  " + t + "度" + "  " + wd + "风");
            watermarkHb.setText("海拔：无");
        }
        lo = CommonUitls.GPS2AjLocation(aMapLocation.getLongitude());//经度
        la = CommonUitls.GPS2AjLocation(aMapLocation.getLatitude());//纬度
        initWeatherSearch(aMapLocation.getCity());//天气查询*/
    }


    private String lo, la, we, t, wp, wd;//we：天气名称 t：温度   wp：风力 wd：风向


    /**
     * 天气查询成功后执行
     *
     * @param weatherlive
     */
    private void weatherSearchSucceed(LocalWeatherLive weatherlive) {
        //we：天气名称 t：温度   wp：风力 wd：风向
        we = weatherlive.getWeather();
        t = weatherlive.getTemperature();
        wd = weatherlive.getWindDirection();
        wp = weatherlive.getWindPower();
    }
//-----------------------------------------------------视频相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍摄视频
     */
    private void videoOperation() {
//        mCameraView.get

//        mBeautyBtn = (ImageView) findViewById(R.id.btn_camera_beauty);
//        mFilterBtn = (ImageView) findViewById(R.id.btn_camera_filter);
        mCameraChange = (ImageView) findViewById(R.id.btn_camera_switch);//翻转镜头
        mVideoWc = (ImageButton) findViewById(R.id.video_wc);//视频录制完成

//        mBeautyBtn.setOnClickListener(this);

        mCameraChange.setOnClickListener(this);
        mVideoWc.setOnClickListener(this);
        mCapture.setTotal(maxTime);
//        mCapture.setOnClickListener(this);

        mCapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                if (!recordFlag) {//是否正在录制
//                    executorService.execute(recordRunnable);
//                } else if (!pausing) {
//                    mCameraView.pause(false);//暂停
//                    pausing = true;
//                } else {
//                    mCameraView.resume(false);
//                    pausing = false;
//                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        mVideoWc.setVisibility(View.GONE);//显示录制完成按钮
                        mCameraChange.setVisibility(View.GONE);//隐藏翻转相机

//                        executorService.execute(recordRunnable);

                        if (!recordFlag) {//是否正在录制
                            executorService.execute(recordRunnable);
                        } else {
                            mCameraView.resume(false);
                            pausing = false;
                        }
                        break;
                    case MotionEvent.ACTION_UP://离开
                        mVideoWc.setVisibility(View.VISIBLE);//显示录制完成按钮
                        mCameraChange.setVisibility(View.GONE);//隐藏翻转相机

//                        mCameraView.resume(false);
//                        pausing = false;

                        if (!pausing) {
                            mCameraView.pause(false);//暂停
                            pausing = true;
                        }
                        break;

                }
//                int action = event.getAction();
//
//                if (action == MotionEvent.ACTION_DOWN) {
//                    if (mShortVideoRecorder.beginSection()) {
//                        updateRecordingBtns(true);
//                    } else {
//                        ToastUtils.s(VideoRecordActivity.this, "无法开始视频段录制");
//                    }
//                } else if (action == MotionEvent.ACTION_UP) {
//                    mShortVideoRecorder.endSection();
//                    updateRecordingBtns(false);
//                }

                return false;
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mCameraView.getCameraId() == 1) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * ScreenTool.getScreenWidth(getBaseContext()) / ScreenTool.getScreenHeight(getBaseContext());
                float temp = sRawX;
                float rawX = rawY;
                rawY = (ScreenTool.getScreenWidth(getBaseContext()) - temp) * ScreenTool.getScreenHeight(getBaseContext()) / ScreenTool.getScreenWidth(getBaseContext());

                Point point = new Point((int) rawX, (int) rawY);
                mCameraView.onFocus(point, callback);
                mFocus.startFocus(new Point((int) sRawX, (int) sRawY));
        }
        return true;
    }

    Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            Log.e("hero", "----onAutoFocus====" + success);
            if (success) {
                mFocus.onFocusSuccess();
            } else {
                //聚焦失败显示的图片
                mFocus.onFocusFailed();
            }
        }
    };

    @Override
    public void onFocus() {
        if (mCameraView.getCameraId() == 1) {
            return;
        }
        Point point = new Point(ScreenTool.getScreenWidth(getBaseContext()) / 2, ScreenTool.getScreenHeight(getBaseContext()) / 2);
        mCameraView.onFocus(point, callback);
    }


//-----------------------------------------------------图片相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍照
     */
    private void photoOperation() {
//        Button button1 = (Button) findViewById(R.id.ac_btnb);
//        ImageView imageView = (ImageView) findViewById(R.id.ac_imgv);
//        imageView.setVisibility(View.VISIBLE);
//        watermarkLlz.setVisibility(View.VISIBLE);
        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageView.setImageBitmap(mCameraView.getDrawingCache());
//
//                if (mCameraView.mCamera.mCamera == null) {
//                    CommonUitls.showToast(RecordedActivity.this, "请查看是否允许权限");
//                    return;
//                }

                mCameraView.mCamera.mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                        //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                        //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解

//                        Bitmap bitmap = BitmapUtils.createBitmapLowerLeft(BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)), BitmapUtils.convertViewToBitmap(watermarkLlz));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Bitmap saveBitmap = BitmapUtils.createBitmapLowerLeft(BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)), BitmapUtils.convertViewToBitmap(watermarkLlz));

//                                BitmapUtils.convertViewToBitmap(waterCenImg);//中间水印图片

                                //图片打水印返回
//                                Bitmap bitmap1 = BitmapUtils.createBitmapCenter(BitmapUtils.createBitmapLowerLeft(BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)), BitmapUtils.convertViewToBitmap(watermarkLlz)), BitmapUtils.convertViewToBitmap(waterCenImg));

                                String img_path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + System.currentTimeMillis() + ".jpeg";

                                //图片保存
                                BitmapUtils.saveJPGE_After(RecordedActivity.this, BitmapUtils.createBitmapCenter(BitmapUtils.createBitmapLowerLeft(BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)), BitmapUtils.convertViewToBitmap(watermarkLlz)), BitmapUtils.convertViewToBitmap(waterCenImg)), img_path, 100);
                               if (type.equals("photo")) {
                                }

//                                imageView.setImageBitmap(saveBitmap);
//                                mCameraView.open(0);
//
//                                if (!saveBitmap.isRecycled()) {
//                                    saveBitmap.recycle();
//                                }

                                RecordedActivity.this.finish();
//                                if (mCameraView.mCamera.mCamera != null) {
////                                    mCameraView.mCamera.mCamera.setPreviewCallback(null);
////                                    mCameraView.mCamera.mCamera.stopPreview();
//                                    mCameraView.mCamera.mCamera.release();
//                                }
                            }
                        });
                    }
                });
            }
        });
    }

}
