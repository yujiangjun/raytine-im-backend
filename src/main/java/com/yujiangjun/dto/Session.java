package com.yujiangjun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Session {
    private Integer id;

    private Integer sendId;

    private Integer targetId;

    private Integer chatType;

    private String msgContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sessionTime = new Date();
    private String sendUserName;
    private String sendUserAvatar;
    private String targetName;
    private String targetAvatar;
}