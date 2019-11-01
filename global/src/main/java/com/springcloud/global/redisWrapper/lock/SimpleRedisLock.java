package com.springcloud.global.redisWrapper.lock;

import com.springcloud.global.redisWrapper.RedisCustConfig;
import com.springcloud.global.redisWrapper.RedisUtils;

import java.util.Map;

public class SimpleRedisLock extends RedisLock {
    public SimpleRedisLock(String lockName) throws Exception {
        super(lockName);
    }

    public SimpleRedisLock(String lockName, RedisUtils redisUtils) throws Exception {
        super(lockName, redisUtils);
    }

    public SimpleRedisLock(String lockName, RedisUtils redisUtils, RedisCustConfig redisCustConfig) throws Exception {
        super(lockName, redisUtils, redisCustConfig);
    }

    @Override
    public void syncOperation(RedisUtils redisUtils, Map<String, Object> context) {
    }
}
