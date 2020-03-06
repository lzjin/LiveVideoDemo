package com.danqiu.online.live.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.rtmp.TXLiveBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.e("test", "--------liteav sdk version is : " + sdkver);
    }
}
