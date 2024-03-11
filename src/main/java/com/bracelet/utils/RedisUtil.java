package com.bracelet.utils;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

@Component
public class RedisUtil {


    private static JedisPool jedisPool ;
    //未使用
    @Autowired
    public void setJedisPool(JedisPool jedisPool){
        RedisUtil.jedisPool=jedisPool;
    }

//    private static String host;
//
//    private static int port;
//
//    private static int timeout;
//
//    private static int maxIdle;
//
//    private static long maxWaitMillis;
//
//    private static String password;
//
//    private static boolean  blockWhenExhausted;
//
//    @Value("${spring.redis.host}")
//    public void setHost(String host) {
//        RedisUtils.host = host;
//    }
//    @Value("${spring.redis.port}")
//    public void setPort(int port) {
//        RedisUtils.port = port;
//    }
//    @Value("${spring.redis.timeout}")
//    public void setTimeout(int timeout) {
//        RedisUtils.timeout = timeout;
//    }
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    public void setMaxIdle(int maxIdle) {
//        RedisUtils.maxIdle = maxIdle;
//    }
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    public void setMaxWaitMillis(long maxWaitMillis) {
//        RedisUtils.maxWaitMillis = maxWaitMillis;
//    }
//    @Value("${spring.redis.password}")
//    public void setPassword(String password) {
//        RedisUtils.password = password;
//    }
//    @Value("${spring.redis.block-when-exhausted}")
//    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
//        RedisUtils.blockWhenExhausted = blockWhenExhausted;
//    }


    /**
     * Jedis实例获取返回码
     *
     * @author jqlin
     *
     */
    public static class JedisStatus{
        /**Jedis实例获取失败*/
        public static final long FAIL_LONG = -5L;
        /**Jedis实例获取失败*/
        public static final int FAIL_INT = -5;
        /**Jedis实例获取失败*/
        public static final String FAIL_STRING = "-5";
    }

//
//    public static void  initialPool() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
//        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
//        // 是否启用pool的jmx管理功能, 默认true
//        jedisPoolConfig.setJmxEnabled(true);
//        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
//    }
//
//
//    /**
//     * 初始化Redis连接池
//     */
//    static {
//        initialPool();
//    }
//
//    /**
//     * 在多线程环境同步初始化
//     */
//    private static synchronized void poolInit() {
//        if (jedisPool == null) {
//            initialPool();
//        }
//    }


    private static String SPLIT = ":";


    private static String LIKE = "LIKE";
    //点赞
    public static String getLikeKey(int entityType ,int entityId){
        return LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    private static String HOT = "HOT";
    //热评优先队列
    public static String getHotKey(int entityType,int entityId){
        return HOT + SPLIT + entityType + SPLIT + entityId;
    }

    private static String FOLLOW = "FOLLOW";
    private static String FANS = "FANS";
    //关注集合
    public static String getFollowKey(int entityType, int entityId){
        return FOLLOW + SPLIT + entityType +SPLIT + entityId;
    }
    //粉丝集合
    public static String getFansKey(int entityType,int entityId){
        return FANS + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 获取jedis
     * @return
     */
    public static Jedis getJedis() {
//        if (jedisPool == null) {
//            jedisPool =getJedisPool();
//        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }else{
                System.out.println("jedisPool null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null /*&& jedisPool != null*/) {
            jedis.close();
        }

    }
    public static void returnBrokenResource(final Jedis jedis) {
        if (jedis != null /*&& jedisPool != null*/) {
            jedis.close();
        }
//        System.out.println("出异常了");
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @return -5：Jedis实例获取失败<br/>OK：操作成功<br/>null：操作失败
     * @author jqlin
     */
    public static String set(String key, String value) {
        Jedis jedis = getJedis();
        if(jedis == null){
            return JedisStatus.FAIL_STRING;
        }

        String result = null;
        try {
            //synchronized (RedisUtil.class){
                result = jedis.set(key, value);


        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @param expire 过期时间，单位：秒
     * @return -5：Jedis实例获取失败<br/>OK：操作成功<br/>null：操作失败
     * @author jqlin
     */
    public static String set(String key, String value, int expire) {
        Jedis jedis = getJedis();
        if(jedis == null){
            return JedisStatus.FAIL_STRING;
        }

        String result = null;
        try {
            result = jedis.set(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     * @author jqlin
     */
    public static String get(String key) {
        Jedis jedis = getJedis();
        if(jedis == null){
            System.out.println("jedis null");
            return JedisStatus.FAIL_STRING;
        }

        String result = null;
        try {
            //synchronized (RedisUtil.class){
                result = jedis.get(key);
            //}
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 添加元素到集合中
     * @param key
     * @param value
     * @return
     */

    public static long sadd(String key,String value){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return -1;
        }
        try {
            result = jedis.sadd(key,value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除集合中的元素
     * @param key
     * @param value
     * @return
     */
    public static long srem(String key, String value){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return -1;
        }
        try {
            result = jedis.srem(key,value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 是否为set的成员
     * @param key
     * @param value
     * @return
     */
    public static boolean sismember(String key,String value){
        Jedis jedis = getJedis();
        boolean result = false;
        if(jedis == null){
            return false;
        }
        try {
            result = jedis.sismember(key,value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取集合中的数量
     * @param key
     * @return
     */
    public static long scard (String key){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return -1;
        }
        try {
            result = jedis.scard(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 在key中添加member
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static long zadd(String key,double score,String member){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zadd(key,score,member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 使member + increment
     * @param key
     * @param increment
     * @param member
     * @return
     */
    public static double zincrby(String key,double increment,String member){
        Jedis jedis = getJedis();
        double result = -1;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zincrby(key,increment,member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取member的rank
     * @param key
     * @param member
     * @return
     */
    public static long zrank(String key,String member){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zrank(key,member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取member的score
     * @param key
     * @param member
     * @return
     */
    public static double zscore(String key,String member){
        Jedis jedis = getJedis();
        double result = -1;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zscore(key,member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取集合中的数量
     * @param key
     * @return
     */
    public static long zcard (String key){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return -1;
        }
        try {
            result = jedis.zcard(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }


    /**
     * 获取score从大到小排序的集合
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrevrange(String key, long start, long end){
        Jedis jedis = getJedis();
        Set<String> result = null;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zrevrange(key,start,end);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取score从小到大排序的集合
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrange(String key, long start, long end){
        Jedis jedis = getJedis();
        Set<String> result = null;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zrange(key,start,end);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除member
     * @param key
     * @param member
     * @return
     */
    public static long zrem(String key,String member){
        Jedis jedis = getJedis();
        long result = -1;
        if(jedis == null){
            return result;
        }
        try {
            result = jedis.zrem(key,member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param value -5：Jedis实例获取失败，1：成功，0：失败
     * @return
     * @author jqlin
     */
    public static long expire(String key, int seconds) {
        Jedis jedis = getJedis();
        if(jedis == null){
            return JedisStatus.FAIL_LONG;
        }

        long result = 1;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
//            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     * @author jqlin
     */
    public static boolean exists(String key) {
        Jedis jedis = getJedis();
        if(jedis == null){
            return false;
        }

        boolean result = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
//            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 删除key
     *
     * @param keys
     * @return -5：Jedis实例获取失败，1：成功，0：失败
     * @author jqlin
     */
    public static long del(String... keys) {
        Jedis jedis = getJedis();
        if(jedis == null){
            return JedisStatus.FAIL_LONG;
        }

        long result = JedisStatus.FAIL_LONG;
        try {
            //synchronized (RedisUtil.class){
                result = jedis.del(keys);
            //}
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    public static String setObject(String key,Object obj){
        Jedis jedis = getJedis();
        if(jedis == null){
            return JedisStatus.FAIL_STRING;
        }

        String result = null;
        try {
            String data= JsonUtil.toJson(obj);
            result = jedis.set(key, data);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }


    public static <T> T getObject(String key,Class<T> classT){
        Jedis jedis = getJedis();
        if(jedis == null){
            return null;
        }

        T result = null;
        try {
            String data = jedis.get(key);
            if(data != null ){
                result= JsonUtil.toObject(data,classT);
            }
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    public static <T> T getCollection(String key,Class<?> collectionClass,Class<?>... elementClasses){
        Jedis jedis = getJedis();
        if(jedis == null){
            return null;
        }

        T result = null;
        try {
            String data = jedis.get(key);
            if(data != null ){
                result= JsonUtil.string2Obj(data,collectionClass,elementClasses);
            }
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }
}