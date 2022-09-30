package com.yujiangjun.message;

import lombok.Data;

@Data
public class TextMessage extends AbstractMessage{
    private String content;
    private String sendUserId;
    private String receiveUserId;

}
