package com.yujiangjun.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 包括图片，音频，视频等流媒体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BinaryMessage extends AbstractMessage{
    private String url;
    private String sendUserId;
    private String receiveUserId;

}
