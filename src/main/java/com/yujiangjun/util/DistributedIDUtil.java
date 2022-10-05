package com.yujiangjun.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.setting.dialect.Props;
import com.yujiangjun.constants.Constant;
import org.springframework.core.env.Environment;

public class DistributedIDUtil {
    private DistributedIDUtil(){}

    public static String getId(){
//        Environment environment = SpringContextUtil.getBean(Environment.class);
//        String[] activeProfiles = environment.getActiveProfiles();
        String url;
//        if (ArrayUtil.isEmpty(activeProfiles)){
//            url=String.valueOf(new Props("leaf.properties").get("leaf.host"))+Constant.getDistributedIdUrl;
//        }else {
//            url = new Props(environment.getActiveProfiles()[0]+"-leaf.properties").get("leaf.host")+Constant.getDistributedIdUrl;
//        }
        Props props = new Props("leaf.properties");
        url = props.get("left.host")+Constant.getDistributedIdUrl;
        return OKHttpUtil.get(url, String.class);
    }
}
