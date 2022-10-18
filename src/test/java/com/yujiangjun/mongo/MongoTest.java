package com.yujiangjun.mongo;

import com.yujiangjun.message.TextMessage;
import com.yujiangjun.util.HisMessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MongoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void m1(){

        User user = new User();
        user.setId("1");
        user.setAge("12");
        user.setName("raytine");
        userDao.insert(user);
    }

    @Test
    void m2(){
        TextMessage message = new TextMessage();
        message.setContent("1");
        message.setTargetId("1");
        message.setMessageType(1);
        message.setCat(1);
        message.setMsgId("1");
        HisMessageUtil.saveMessage(message);
    }
}
