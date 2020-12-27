package com.tuya.smart.android.demo.device;

import android.widget.Toast;

import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.smart.sdk.api.ITuyaDevice;
import com.tuya.smart.sdk.centralcontrol.api.ILightListener;
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice;
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint;
import com.tuya.smart.sdk.centralcontrol.api.constants.LightMode;
import com.tuya.smart.sdk.centralcontrol.api.constants.LightScene;

import java.util.HashMap;

public class TuyaLightDeviceAdapter implements ILightDevice{
    private String mDeviceId;
    static String TAG = "TuyaLightDevice";
    //ITuyaLightDevice mLightDevice;
    ITuyaDevice mLightDevice;
    @Override
    public void initLightController(String devId)
    {
        mDeviceId = devId;
        L.d(TAG, "initLightController:"+mDeviceId);
        //mLightDevice = new com.tuya.smart.centralcontrol.TuyaLightDevice(devId);
        mLightDevice = TuyaHomeSdk.newDeviceInstance(devId);
        mLightDevice.registerDevListener(new IDevListener() {
            @Override
            public void onDpUpdate(String devId, String dpStr) {
                L.d(TAG, "onDpUpdate:" + devId+":" + dpStr);
            }
            @Override
            public void onRemoved(String devId) {
                L.d(TAG, "onRemoved:" + devId);
            }
            @Override
            public void onStatusChanged(String devId, boolean online) {
                L.d(TAG, "onStatusChanged:" + devId+"online:"+online);
            }
            @Override
            public void onNetworkStatusChanged(String devId, boolean status) {
                L.d(TAG, "onDpUpdate:" + status);
            }
            @Override
            public void onDevInfoUpdate(String devId) {
                L.d(TAG, "onDevInfoUpdate:" + devId);
            }
        });
        /*
        mLightDevice.registerLightListener(new ILightListener() {
            @Override
            public void onDpUpdate(LightDataPoint dataPoint) { // 返回LightDataPoint，包含灯所有功能点的值
                L.d(TAG, "onDpUpdate:" + dataPoint);
            }

            @Override
            public void onRemoved() {
                L.d(TAG, "onRemoved");
            }

            @Override
            public void onStatusChanged(boolean status) {
                L.d(TAG, "onDpUpdate:" + status);
            }

            @Override
            public void onNetworkStatusChanged(boolean status) {
                L.d(TAG, "onDpUpdate:" + status);
            }

            @Override
            public void onDevInfoUpdate() {
                L.d(TAG, "onDevInfoUpdate:");
            }
        });
*/
        //LightDataPoint dp = mLightDevice.getLightDataPoint();
        //L.d(TAG, dp.toString());
        boolean isStandard = TuyaHomeSdk.getDataInstance().isStandardProduct(devId);
        L.d(TAG, "isStandard:" + isStandard);

        setMode(LightMode.MODE_COLOUR);
    }

    @Override
    public void lightOn(Boolean on) {
        HashMap<String, Object> dpCodeMap = new HashMap<>();
        dpCodeMap.put("switch_led", on);
        mLightDevice.publishCommands(dpCodeMap, new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                //Toast.makeText(getAci, "开灯失败", Toast.LENGTH_SHORT).show();
                L.d(TAG, "开灯失败:" + code +" error: "+ error);
            }

            @Override
            public void onSuccess() {
                //Toast.makeText(mContext, "开灯成功", Toast.LENGTH_SHORT).show();
                L.d(TAG, "开灯成功" );
            }
        });
    }
    @Override
    public void setScene(LightScene scene) {
    }
    @Override
    public void setMode(LightMode mode)
    {
        String modeValue = "scene_1";
        switch (mode){
            case MODE_SCENE:
                modeValue = "scene";
                break;
            case MODE_COLOUR:
                modeValue = "colour";
                break;
            case MODE_WHITE:
                modeValue = "white";
        }
        HashMap<String, Object> dpCodeMap = new HashMap<>();
        dpCodeMap.put("work_mode", modeValue);
        mLightDevice.publishCommands(dpCodeMap, new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                L.d(TAG,  "workMode onError:" + code + "error:"+error);
            }

            @Override
            public void onSuccess() {
                L.d(TAG,  "workMode onSuccess");
            }
        });
    }
    @Override
    public void setBrightness(int brightness){
        HashMap<String, Object> dpCodeMap = new HashMap<>();
        dpCodeMap.put("bright_value", brightness); //3
        mLightDevice.publishCommands(dpCodeMap,  new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                L.d(TAG, "brightness onError:" + code + error);
            }

            @Override
            public void onSuccess() {
                L.d(TAG, "brightness onSuccess:");
            }
        });
    }
    // 设置颜色
    @Override
    public void setColor(int r, int g, int b) {
        HashMap<String, Object> dpCodeMap = new HashMap<>();
        dpCodeMap.put("code", "colour_data");
        //dpCodeMap.put("value", "colour_data");
        mLightDevice.publishCommands(dpCodeMap,  new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                L.d(TAG, "colorHSV onError:" + code + error);
            }

            @Override
            public void onSuccess() {
                L.d(TAG, "colorHSV onSuccess:");
            }
        });
    }
}
