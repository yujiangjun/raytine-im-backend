package com.yujiangjun.service;

import com.yujiangjun.constants.Constant;
import com.yujiangjun.util.DistributedRedisLock;
import com.yujiangjun.util.SpringContextUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UnReadMesService {

    public void undoMesCountUpdate(@NonNull String userId,@NonNull String targetId){
        DistributedRedisLock.acquire(Constant.unReadMsgCount+userId);
        RedisTemplate redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
        redisTemplate.boundValueOps(Constant.unReadMsgCount+userId+"_"+targetId).increment();
        DistributedRedisLock.release(Constant.unReadMsgCount+userId);
    }

    public void cleanUndoMsgCount(@NonNull String userId,@NonNull String targetId){
        DistributedRedisLock.acquire(Constant.unReadMsgCount+userId);
        RedisTemplate redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
        redisTemplate.delete(Constant.unReadMsgCount+userId+"_"+targetId);
//        redisTemplate.boundValueOps(Constant.unReadMsgCount+userId+"_"+targetId);
        log.info("重置后数量,value:{}",redisTemplate.boundValueOps(Constant.unReadMsgCount+userId+"_"+targetId).get());
        DistributedRedisLock.release(Constant.unReadMsgCount+userId);
    }
}
