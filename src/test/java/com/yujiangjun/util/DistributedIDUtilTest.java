package com.yujiangjun.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DistributedIDUtilTest {

    @Test
    void getId() {
        String id = DistributedIDUtil.getId();
        System.out.println(id);
    }
}