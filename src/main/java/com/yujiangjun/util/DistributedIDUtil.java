package com.yujiangjun.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.setting.dialect.Props;
import com.yujiangjun.constants.Constant;
import org.springframework.core.env.Environment;

import java.util.Objects;

public class DistributedIDUtil {
    private DistributedIDUtil(){}

    public static String getId(){
        Environment environment = SpringContextUtil.getBean(Environment.class);
        String[] activeProfiles = environment.getActiveProfiles();
        String url;
        if (ArrayUtil.isEmpty(activeProfiles) || !Objects.equals("prod",activeProfiles[0])){
            url= new Props("leaf.properties").get("left.host") +Constant.getDistributedIdUrl;
        }else {
            url = new Props(String.format("leaf-%s.properties",environment.getActiveProfiles()[0])).get("left.host")+Constant.getDistributedIdUrl;
        }
        return OKHttpUtil.get(url, String.class);
    }
}
