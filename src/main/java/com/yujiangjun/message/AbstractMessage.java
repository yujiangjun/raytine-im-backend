package com.yujiangjun.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AbstractMessage implements Message{
    private String msgId;
    /**
     * 消息分类 用户消息，系统消息，消息回执
     */
    private Integer cat;
    /**
     * 消息类型 文本 音频 视频
     */
    private Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendTime;
    /**
     * 单聊 群聊
     */
    private Integer messageType;
}
