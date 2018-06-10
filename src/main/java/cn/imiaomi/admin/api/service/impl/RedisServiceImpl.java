package cn.imiaomi.admin.api.service.impl;

import cn.imiaomi.admin.api.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        return set(key, value, 0);
    }

    @Override
    public boolean set(@NotNull String key, Object value, long expireTime) {
        try {
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            } else {

                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean expire(@NotNull String key, long expireTime) {
        try {
            if (expireTime > 0) {
                return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getExpire(@NotNull String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(@NotNull String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void del(@NotNull String... key) {
        if (null != key && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    @Override
    public long increment(@NotNull String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public double increment(@NotNull String key, double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public Object getHash(@NotNull String key, @NotNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> getHashMap(@NotNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void setHashMap(@NotNull String key, @NotNull Map<String, Object> map) {
        setHashMap(key, map, 0);
    }

    @Override
    public boolean setHashMap(@NotNull String key, @NotNull Map<String, Object> map, long expireTime) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setHash(String key, String item, Object value) {
        setHash(key, item, value, 0);
    }

    @Override
    public boolean setHash(String key, String item, Object value, long expireTime) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
