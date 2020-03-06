package com.danqiu.online.live.demo.config;

import android.app.Application;

import com.tencent.rtmp.TXLiveBase;

/**
 * Created by lzj on 2020/3/6
 * Describe ：注释
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/2f9db61d6881a232b207135a6b48bb36/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "dbfba9965ac3e77c960d56697d73be77"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
    }
}
