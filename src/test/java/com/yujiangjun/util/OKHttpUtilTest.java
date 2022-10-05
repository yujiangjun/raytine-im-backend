package com.yujiangjun.util;

import com.yujiangjun.constants.Constant;
import org.junit.jupiter.api.Test;

class OKHttpUtilTest {

    @Test
    void get() {
        String url= Constant.getDistributedIdUrl;
        String s = OKHttpUtil.get(url, String.class);
        System.out.println(s);
    }
}