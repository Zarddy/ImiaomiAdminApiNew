package cn.imiaomi.admin.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

import java.util.concurrent.Callable;

/**
 * redis缓存处理类
 */
public class RedisCache implements Cache {

    private RedisTemplate<String, Object> redisTemplate;
    private String name;

    public RedisCache(String name, RedisTemplate<String, Object> redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return redisTemplate;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    @Override
    public ValueWrapper get(final Object key) {
        String keyf = String.valueOf(key);

        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = redisConnection.get(key);
                return SerializationUtils.deserialize(value);
            }
        });
        return new SimpleValueWrapper(object);
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    @Override
    public void put(final Object key, final Object value) {
        final String keyf = String.valueOf(key);
        final long liveTime = 86400;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = SerializationUtils.serialize(keyb);
                redisConnection.set(keyb, valueb);
                if (liveTime > 0) {
                    redisConnection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    /**
     * 删除缓存
     * @param key
     */
    @Override
    public void evict(final Object key) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.del(String.valueOf(key).getBytes());
            }
        });
    }

    /**
     * 清理缓存
     */
    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return "ok";
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }
}
