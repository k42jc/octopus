package com.dafy.cache.redis;

import com.dafy.cache.factory.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JedisLock {
    private static final Logger logger = LoggerFactory.getLogger(JedisLock.class);

    private volatile   boolean locked = false;
    //锁超时循环单位
    private  int TIMEOUT_UNIT_IN_MILLIS = 300;
    //锁超时时间，单位毫秒
    private  int LOCK_EXPIRY_IN_MILLIS = 10 * 1000;
    //获取锁超时时间
    private  int ACQUIRY_TIMEOUT_IN_MILLIS = 4 * 1000;

    public synchronized boolean lock(String lockKey) {
        int acquiryTimeoutInMillis = ACQUIRY_TIMEOUT_IN_MILLIS, lockExpiryInMillis = LOCK_EXPIRY_IN_MILLIS,  timeoutUnitInMillis = TIMEOUT_UNIT_IN_MILLIS;

        try {
            return lock(acquiryTimeoutInMillis, lockExpiryInMillis, timeoutUnitInMillis, lockKey);
        } catch (InterruptedException e) {
            logger.error("redis 获取锁失败", e);
            return false;
        }
    }

    public synchronized boolean lock(String lockKey, int acquiryTimeoutInMillis, int lockExpiryInMillis, int timeoutUnitInMillis) {

        try {
            return lock(acquiryTimeoutInMillis, lockExpiryInMillis, timeoutUnitInMillis, lockKey);
        } catch (InterruptedException e) {
            logger.error("redis 获取锁失败", e);
            return false;
        }
    }

    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized  boolean lock(int acquiryTimeoutInMillis, int lockExpiryInMillis, int timeoutUnitInMillis, String lockKey) throws InterruptedException {
        int timeout = acquiryTimeoutInMillis;
        while (timeout >= 0) {
        	logger.info("准备获取锁，{}",lockKey);
            long expires = System.currentTimeMillis() + lockExpiryInMillis + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (CacheFactory.setNX(lockKey, expiresStr)) {
            	logger.info("获取到了锁,{}",lockKey);
                locked = true;
                return true;
            }

            String currentValueStr = CacheFactory.getString(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的

                String oldValueStr = CacheFactory.getSetString(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                	logger.info("获取锁2，{}",lockKey);
                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    locked = true;
                    return true;
                }
            }
            timeout -= timeoutUnitInMillis;

            /*
                延迟timeoutUnit毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            logger.info("锁服务{}休眠{}",lockKey,timeoutUnitInMillis);
            Thread.sleep(timeoutUnitInMillis);

        }
        return false;
    }

    /**
     * Acqurired lock release.
     */
    public synchronized  void unlock(String lockKey) {
    	logger.info("准备释放锁：{}",lockKey);
        if (locked) {
            CacheFactory.deleteString(lockKey);
            locked = false;
            logger.info("释放锁成功：{}",lockKey);
        }
    }
}
