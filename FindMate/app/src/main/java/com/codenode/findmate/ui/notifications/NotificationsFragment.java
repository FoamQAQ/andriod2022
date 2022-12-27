package com.codenode.findmate.ui.notifications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codenode.findmate.databinding.FragmentNotificationsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private final static String TAG = "LocationActivity";
    private TextView tv_location;
    private String mLocation = "";
    private LocationManager mLocationMgr; // 声明一个定位管理器对象
    private Criteria mCriteria = new Criteria(); // 声明一个定位准则对象
    private Handler mHandler = new Handler(); // 声明一个处理器
    private boolean isLocationEnable = false; // 定位服务是否可用
    private Button getPosition;
    private TextView longitude;
    private TextView latitude;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getPosition = binding.getPosition;
        latitude = binding.latitude;
        longitude = binding.longitude;
        getPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mRefresh); // 移除定位刷新任务
                initLocation();
                mHandler.postDelayed(mRefresh, 100);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initLocation() {
        // 从系统服务中获取定位管理器
        mLocationMgr = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Log.d(TAG,mLocationMgr.toString());
        // 设置定位精确度。Criteria.ACCURACY_COARSE表示粗略，Criteria.ACCURACY_FIN表示精细
        //mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否需要海拔信息
        //mCriteria.setAltitudeRequired(true);
        // 设置是否需要方位信息
        //Criteria.setBearingRequired(true);
        // 设置是否允许运营商收费
        //mCriteria.setCostAllowed(true);
        // 设置对电源的需求
        //mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获取定位管理器的最佳定位提供者
        String bestProvider = mLocationMgr.getBestProvider(mCriteria, true);
        bestProvider = LocationManager.NETWORK_PROVIDER;
        Log.d(TAG,bestProvider);
        if (mLocationMgr.isProviderEnabled(bestProvider)) { // 定位提供者当前可用
            //tv_location.setText("正在获取" + bestProvider + "定位对象");
            mLocation = String.format("定位类型=%s", bestProvider);
            Log.d(TAG,"定位类型=%s"+ bestProvider);
            beginLocation(bestProvider);
            isLocationEnable = true;
        } else { // 定位提供者暂不可用
            //tv_location.setText("\n" + bestProvider + "定位不可用");
            isLocationEnable = false;
        }
    }

    // 开始定位
    private void beginLocation(String method) {
        // 检查当前设备是否已经开启了定位功能
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.getContext(), "请授予定位权限并开启定位功能", Toast.LENGTH_SHORT).show();
            return;
        }
        // 设置定位管理器的位置变更监听器
        //mLocationMgr.requestLocationUpdates(method, 300, 0, mLocationListener);
        // 获取最后一次成功定位的位置信息
        Location location = mLocationMgr.getLastKnownLocation(method);
        setLocationText(location);
    }

    // 设置定位结果文本
    private void setLocationText(Location location) {
        if (location != null) {
            String desc = String.format("%s\n定位对象信息如下： " +
                            "\n\t其中时间：%s" + "\n\t其中经度：%f，纬度：%f" +
                            "\n\t其中高度：%d米，精度：%d米",
                    mLocation, getNowDateTimeFormat(),
                    location.getLongitude(), location.getLatitude(),
                    Math.round(location.getAltitude()), Math.round(location.getAccuracy()));
            latitude.setText("Latitude:"+location.getLatitude());
            longitude.setText("Longitude:"+location.getLongitude());
            Log.d(TAG, desc);
            //tv_location.setText(desc);
        } else {
            Log.d(TAG, "暂未获取到定位对象");
            //tv_location.setText(mLocation + "\n暂未获取到定位对象");
        }
    }




    // 定义一个位置变更监听器
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLocationText(location);
        }

        public void onProviderDisabled(String arg0) {}

        public void onProviderEnabled(String arg0) {}

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
    };

    // 定义一个刷新任务，若无法定位则每隔一秒就尝试定位
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            if (!isLocationEnable) {
                initLocation();
                mHandler.postDelayed(this, 1000);
            }
        }
    };


    // 获取定位功能的开关状态
    public static boolean getGpsStatus(Context ctx) {
        // 从系统服务中获取定位管理器
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // 检查定位功能是否打开，若未打开则跳到系统的定位功能设置页面
    public static void checkGpsIsOpen(Context ctx, String hint) {
        if (!getGpsStatus(ctx)) {
            Toast.makeText(ctx, hint, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            ctx.startActivity(intent);
        }
    }

    public static String getNowDateTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}