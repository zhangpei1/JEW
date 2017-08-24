package com.new_jew.net;

import android.content.Context;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.new_jew.toolkit.GetAdress;

/**
 * Created by zhangpei on 2016/8/10.
 */
public class Location_service {
    public static LocationClient mLocationClient = null;
    public BDLocationListener myListener = null;
    public static String adress = "";
    private Context context;
    public static double Latitude = 0;
    public static double Longitude = 0;
    public static String location = "";
    private GetAdress getAdress;

    public Location_service(Context context, GetAdress getAdress) {

        this.context = context;
        this.getAdress = getAdress;
        mLocationClient = new LocationClient(context);


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

//        int span = 1000;
//        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
        Log.e("location", "12314454");
        mLocationClient.start();
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.e("location", "111111111111");
                Latitude = bdLocation.getLatitude();
                Log.e("location", String.valueOf(bdLocation.getLatitude()));
                Longitude = bdLocation.getLongitude();
                Log.e("Longitude", String.valueOf(Longitude));
                adress = bdLocation.getAddrStr();
                Log.e("adress", String.valueOf(adress));
                location = String.valueOf(Longitude) + "," + String.valueOf(Latitude);
                getAdress.get_address(adress, Latitude, Longitude);
//                Log.e("location", bdLocation.getLocationDescribe());


            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }


        });
    }


    public void StartLocation() {

        initLocation();//声明LocationClient类


    }

    public void StopLocation() {

        mLocationClient.stop();


    }


}
