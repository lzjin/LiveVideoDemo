package com.danqiu.online.live.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.danqiu.online.live.demo.utils.ToastUtil;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.pusher_tx_cloud_view)
    TXCloudVideoView pusherTxCloudView;
    TXLivePusher mLivePusher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //String sdkver = TXLiveBase.getSDKVersionStr();
        requestPermission();
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
        String rtmpURL = "rtmp://test.com/live/xxxxxx"; //此处填写您的 rtmp 推流地址
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


    private void requestPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
             Log.e("test", "--------------------已有权限，版本检测");
            //checkUpdate();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "为了更好的用户体验需要获取以下权限", 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case 1:
               // checkUpdate();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtil.showShort(this, "您已拒绝相关权限，可到设置里自行开启");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
