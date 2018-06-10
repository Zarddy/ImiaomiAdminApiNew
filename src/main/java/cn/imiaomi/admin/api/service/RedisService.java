package cn.imiaomi.admin.api.service;

import javax.validation.constraints.NotNull;
import java.util.Map;

public interface RedisService {

    /**
     * 存入缓存
     * @param key 键
     * @param value 值
     */
    boolean set(@NotNull String key, Object value);

    /**
     * 存入缓存并设置失效时间
     * @param key 键
     * @param value 值
     * @param expireTime 失效时长(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    boolean set(@NotNull String key, Object value, long expireTime);

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    Object get(@NotNull String key);

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间（秒）
     * @return 是否设置成功
     */
    boolean expire(@NotNull String key, long time);

    /**
     * 根据key 获取过期时间
     * @param key 键
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(@NotNull String key);

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(@NotNull String key);

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    void del(@NotNull String... key);

    /**
     * 递增
     * @param key 键
     * @param value 递增值
     * @return
     */
    long increment(@NotNull String key, long value);

    /**
     * 递增
     * @param key 键
     * @param value 递增值
     * @return
     */
    double increment(@NotNull String key, double value);

    /**
     *
     * @param key
     * @param item
     * @return
     */
    Object getHash(@NotNull String key, @NotNull String item);

    /**
     * 获取hashkey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> getHashMap(@NotNull String key);

    void setHashMap(@NotNull String key, @NotNull Map<String, Object> map);

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param expireTime 失效时间(秒)
     * @return
     */
    boolean setHashMap(@NotNull String key, @NotNull Map<String, Object> map, long expireTime);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    void setHash(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param expireTime 失效时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return
     */
    boolean setHash(String key, String item, Object value, long expireTime);
}
