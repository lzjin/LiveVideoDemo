package com.danqiu.online.live.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.danqiu.online.live.demo.ui.LivePullActivity;
import com.danqiu.online.live.demo.ui.LivePushActivity;
import com.danqiu.online.live.demo.ui.TxPlayerActivity;
import com.danqiu.online.live.demo.utils.IntentUtil;
import com.danqiu.online.live.demo.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 腾讯 直播、点播sdk测试
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.bt_push)
    Button btPush;
    @BindView(R.id.bt_pull)
    Button btPull;
    @BindView(R.id.bt_player)
    Button btPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermission();
    }

    @OnClick({R.id.bt_push, R.id.bt_pull, R.id.bt_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_push:
                IntentUtil.IntenToActivity(this,LivePushActivity.class);
                break;
            case R.id.bt_pull:
                IntentUtil.IntenToActivity(this, LivePullActivity.class);
                break;
            case R.id.bt_player:
                IntentUtil.IntenToActivity(this, TxPlayerActivity.class);
                break;
        }
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
