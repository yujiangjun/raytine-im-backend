package com.yujiangjun.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLock {

    private static final String LOCK_TITLE="redisLock";

    public static boolean acquire(String lockName){
        String key=LOCK_TITLE+lockName;
        RedissonClient redissonClient = SpringContextUtil.getBean(RedissonClient.class);
        RLock lock = redissonClient.getLock(key);
        lock.lock(2, TimeUnit.SECONDS);
        return true;
    }

    public static void release(String lockName){
        String key = LOCK_TITLE+lockName;
        RedissonClient redissonClient = SpringContextUtil.getBean(RedissonClient.class);
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
