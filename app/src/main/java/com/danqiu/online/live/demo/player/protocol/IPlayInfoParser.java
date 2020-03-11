package com.danqiu.online.live.demo.player.protocol;



import com.danqiu.online.live.demo.player.bean.TCPlayImageSpriteInfo;
import com.danqiu.online.live.demo.player.bean.TCPlayKeyFrameDescInfo;
import com.danqiu.online.live.demo.player.bean.TCVideoQuality;

import java.util.List;

/**
 * 视频信息协议解析接口
 */
public interface IPlayInfoParser {
    /**
     * 获取视频播放url
     *
     * @return url字符串
     */
    String getUrl();

    /**
     * 获取视频名称
     *
     * @return 视频名称字符串
     */
    String getName();

    /**
     * 获取略缩图信息
     *
     * @return 雪略缩信息对象
     */
    TCPlayImageSpriteInfo getImageSpriteInfo();

    /**
     * 获取关键帧信息
     *
     * @return 关键帧信息数组
     */
    List<TCPlayKeyFrameDescInfo> getKeyFrameDescInfo();

    /**
     * 获取画质信息
     *
     * @return 画质信息数组
     */
    List<TCVideoQuality> getVideoQualityList();

    /**
     * 获取默认画质信息
     *
     * @return 默认画质信息对象
     */
    TCVideoQuality getDefaultVideoQuality();

}
