package com.danqiu.online.live.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.danqiu.online.live.demo.R;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推送直播流 测试完成
 */
public class LivePushActivity extends AppCompatActivity {
    @BindView(R.id.pusher_tx_cloud_view)
    TXCloudVideoView pusherTxCloudView;
    TXLivePusher mLivePusher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_live);

        ButterKnife.bind(this);

        //String sdkver = TXLiveBase.getSDKVersionStr();
        initTXLivePusher();
    }

    /**
     * 初始化 TXLivePusher 组件
     */
    private void initTXLivePusher() {
        TXLivePushConfig mLivePushConfig  = new TXLivePushConfig();
        mLivePusher = new TXLivePusher(this);
        // 一般情况下不需要修改 config 的默认配置// 一般情况下不需要
        mLivePusher.setConfig(mLivePushConfig);

        //启动本地摄像头预览
        TXCloudVideoView mPusherView = (TXCloudVideoView) findViewById(R.id.pusher_tx_cloud_view);
        mLivePusher.startCameraPreview(mPusherView);

        //启动推流
        String rtmpURL = "rtmp://83748.livepush.myqcloud.com/live/lzj?txSecret=d2d516bdb6b4a8af3de08f26681bf2f1&txTime=5E63C4FF"; //此处填写您的 rtmp 推流地址
        int ret = mLivePusher.startPusher(rtmpURL.trim());
        if (ret == -5) {
            Log.e("test", "-----------startRTMPPush: license 校验失败");
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mLivePusher.stopPusher();
        mLivePusher.stopCameraPreview(true); //如果已经启动了摄像头预览，请在结束推流时将其关闭
    }
}
