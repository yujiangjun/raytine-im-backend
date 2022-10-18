package com.yujiangjun.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends AbstractMessage implements Serializable {
    private String content;
    private String sendUserId;
    /**
     * 当是单聊，表示接收者id，群聊时表示
     */
    private String targetId;

}
