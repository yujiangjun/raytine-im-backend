package com.yujiangjun.util;

import com.yujiangjun.dao.mongo.TextMessageDao;
import com.yujiangjun.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HisMessageUtil {

    private HisMessageUtil() {}

    public static void saveMessage(TextMessage message){
        TextMessageDao messageDao = SpringContextUtil.getBean(TextMessageDao.class);
        messageDao.insert(message);
    }
}
