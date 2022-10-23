package com.yujiangjun.service.remote;

import com.yujiangjun.constants.RemoteConstants;
import com.yujiangjun.dto.Resp;
import com.yujiangjun.dto.Session;
import com.yujiangjun.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageSessionService {

    @Value("${message.host}")
    private String messageHost;
    @Value("${message.port}")
    private int messagePort;
    final String baseUrl="http://"+messageHost+":"+messagePort+"/message/";

    public void addOrUpdate(Session session){
        String url = baseUrl+ RemoteConstants.ADD_UPDATE;
        RestTemplate restTemplate = SpringContextUtil.getBean(RestTemplate.class);
        restTemplate.postForObject(url,session, Resp.class);
    }
}
