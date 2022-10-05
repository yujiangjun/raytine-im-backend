package com.yujiangjun.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class OKHttpUtil {

    public static  <T> T get(String url,Class<T> clazz){
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = new OkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            return JsonUtil.readObject(Objects.requireNonNull(response.body()).string(),clazz);
        } catch (IOException e) {
            log.error("http调用错误:",e);
        }
        return null;
    }
}
