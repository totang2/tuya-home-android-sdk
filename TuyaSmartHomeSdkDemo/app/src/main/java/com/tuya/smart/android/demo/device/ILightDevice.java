package com.tuya.smart.android.demo.device;

import com.tuya.smart.sdk.centralcontrol.api.constants.LightMode;
import com.tuya.smart.sdk.centralcontrol.api.constants.LightScene;

public interface ILightDevice {
    public void initLightController(String devId);
    public void lightOn(Boolean on);
    public void setScene(LightScene scene);
    public void setMode(LightMode mode);
    public void setBrightness(int brightness);
    public void setColor(int r, int g, int b);
}
