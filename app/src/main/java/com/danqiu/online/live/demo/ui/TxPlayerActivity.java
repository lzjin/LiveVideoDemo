package com.danqiu.online.live.demo.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.danqiu.online.live.demo.R;
import com.danqiu.online.live.demo.player.SuperPlayerConst;
import com.danqiu.online.live.demo.player.SuperPlayerGlobalConfig;
import com.danqiu.online.live.demo.player.SuperPlayerModel;
import com.danqiu.online.live.demo.player.SuperPlayerVideoId;
import com.danqiu.online.live.demo.player.SuperPlayerView;
import com.danqiu.online.live.demo.playermodel.VideoModel;
import com.danqiu.online.live.demo.utils.TCConstants;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 腾讯播放器
 */
public class TxPlayerActivity extends AppCompatActivity implements SuperPlayerView.OnSuperPlayerViewCallback {
    @BindView(R.id.superVodPlayerView)
    SuperPlayerView mSuperPlayerView;
    @BindView(R.id.layout_title)
    RelativeLayout mLayoutTitle;
    //进入默认播放的视频
    private int DEFAULT_APPID = 1252463788;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_player);
        ButterKnife.bind(this);

        initPlayer();
    }

    private void initPlayer() {

        mSuperPlayerView.setPlayerViewCallback(this);
        initSuperVodGlobalSetting();
        TXLiveBase.setAppID("1253131631");

//        String mVideoId = getIntent().getStringExtra(TCConstants.PLAYER_VIDEO_ID);
//        if (!TextUtils.isEmpty(mVideoId)) {
//            playDefaultVideo(TCConstants.VOD_APPID, mVideoId);
//        }

        playNewVideo("http://1253650823.vod2.myqcloud.com/a1b38d05vodcq1253650823/29197c9d5285890799653267382/P9yxcvYA3VAA.mp4");
    }

    /**
     * 初始化超级播放器全局配置
     */
    private void initSuperVodGlobalSetting() {
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = true;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = 810;
        rect.height = 540;
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        //需要修改为自己的时移域名
        prefs.playShiftDomain = "vcloudtimeshift.qcloud.com";
    }

    private void playDefaultVideo(int appid, String fileid) {
        VideoModel videoModel = new VideoModel();
        videoModel.appid = appid;
        videoModel.fileid = fileid;
        videoModel.title = "小视频-特效剪辑";
        if (videoModel.appid > 0) {
            TXLiveBase.setAppID("" + videoModel.appid);
        }
        playVideoModel(videoModel);
    }

    private void playNewVideo(String result) {

        VideoModel videoModel = new VideoModel();
        videoModel.title = "测试视频" ;
        videoModel.videoURL = result;
        videoModel.placeholderImage = "http://xiaozhibo-10055601.file.myqcloud.com/coverImg.jpg";
        videoModel.appid = DEFAULT_APPID;
        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("5815.liveplay.myqcloud.com")) {
            videoModel.appid = 1253131631;
            TXLiveBase.setAppID("1253131631");
            videoModel.multiVideoURLs = new ArrayList<>(3);
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL("超清", videoModel.videoURL));
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL("高清", videoModel.videoURL.replace(".flv", "_900.flv")));
            videoModel.multiVideoURLs.add(new VideoModel.VideoPlayerURL("标清", videoModel.videoURL.replace(".flv", "_550.flv")));
        }
        if (!TextUtils.isEmpty(videoModel.videoURL) && videoModel.videoURL.contains("3891.liveplay.myqcloud.com")) {
            videoModel.appid = 1252463788;
            TXLiveBase.setAppID("1252463788");
        }
        playVideoModel(videoModel);

//        if (isLivePlay(videoModel)) {
//            mLiveList.add(videoModel);
//            needRefreshList = mDataType == LIST_TYPE_LIVE;
//        } else {
//            mVodList.add(videoModel);
//            needRefreshList = mDataType == LIST_TYPE_VOD;
//        }
//        if (needRefreshList) {
//            mVodPlayerListAdapter.addSuperPlayerModel(videoModel);
//            mVodPlayerListAdapter.notifyDataSetChanged();
//        }
    }

    private void playVideoModel(VideoModel videoModel) {
        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
        superPlayerModelV3.appId = videoModel.appid;

        if (!TextUtils.isEmpty(videoModel.videoURL)) {
            superPlayerModelV3.title = videoModel.title;
            superPlayerModelV3.url = videoModel.videoURL;
            superPlayerModelV3.qualityName = "原画";

            superPlayerModelV3.multiURLs = new ArrayList<>();
            if (videoModel.multiVideoURLs != null) {
                for (VideoModel.VideoPlayerURL modelURL : videoModel.multiVideoURLs) {
                    superPlayerModelV3.multiURLs.add(new SuperPlayerModel.SuperPlayerURL(modelURL.url, modelURL.title));
                }
            }
        } else if (!TextUtils.isEmpty(videoModel.fileid)) {
            superPlayerModelV3.videoId = new SuperPlayerVideoId();
            superPlayerModelV3.videoId.fileId = videoModel.fileid;
        }

        mSuperPlayerView.playWithModel(superPlayerModelV3);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
            Log.i("zz", "onResume state :" + mSuperPlayerView.getPlayState());
            mSuperPlayerView.onResume();
            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
        if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FULLSCREEN) {
            //隐藏虚拟按键，并且全屏
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                decorView.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zz", "onPause state :" + mSuperPlayerView.getPlayState());
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.onPause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuperPlayerView.release();
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.resetPlayer();
        }
        //VideoDataMgr.getInstance().setGetVideoInfoListListener(null);
    }

    @Override
    public void onStartFullScreenPlay() {
        // 隐藏其他元素实现全屏
        mLayoutTitle.setVisibility(View.GONE);
    }

    @Override
    public void onStopFullScreenPlay() {
        // 恢复原有元素
        mLayoutTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickFloatCloseBtn() {
        // 点击悬浮窗关闭按钮，那么结束整个播放
        mSuperPlayerView.resetPlayer();
        finish();
    }

    @Override
    public void onClickSmallReturnBtn() {
        // 点击小窗模式下返回按钮，开始悬浮播放
        showFloatWindow();
    }

    @Override
    public void onStartFloatWindowPlay() {
        // 开始悬浮播放后，直接返回到桌面，进行悬浮播放
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 悬浮窗播放
     */
    private void showFloatWindow() {
        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
            mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FLOAT);
        } else {
            mSuperPlayerView.resetPlayer();
            finish();
        }
    }

    private boolean isLivePlay(VideoModel videoModel) {
        String videoURL = videoModel.videoURL;
        if (TextUtils.isEmpty(videoModel.videoURL)) {
            return false;
        }
        if (videoURL.startsWith("rtmp://")) {
            return true;
        } else if ((videoURL.startsWith("http://") || videoURL.startsWith("https://")) && videoURL.contains(".flv")) {
            return true;
        } else {
            return false;
        }
    }
}
