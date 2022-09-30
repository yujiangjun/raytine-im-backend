package com.yujiangjun.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AbstractMessage implements Message{
    private String msgId;
    private Integer cat;
    private Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendTime;
    private Integer messageType;
}
