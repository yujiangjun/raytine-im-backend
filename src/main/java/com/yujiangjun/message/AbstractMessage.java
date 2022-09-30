package com.yujiangjun.message;

import com.yujiangjun.constants.CoreEnum;
import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractMessage implements Message{
    private String msgId;
    private CoreEnum.MessageCat cat;
    private CoreEnum.MessageType type;
    private Date sendTime;
}
