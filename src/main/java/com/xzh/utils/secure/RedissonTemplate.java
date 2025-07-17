package com.xzh.utils.secure;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Supplier;

/**
 * redisson加锁模板
 * eg redissonTemplate.execute("xzhLock:" + 123, () -> demo());
 *
 * @author 向振华
 * @date 2025/07/16 17:45
 */
@Slf4j
@Service
public class RedissonTemplate {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 无返回值的加锁执行方法
     *
     * @param lockKey
     * @param businessLogic
     */
    public void execute(String lockKey, Runnable businessLogic) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock()) {
            try {
                businessLogic.run();
            } finally {
                unlockSafely(lock);
            }
        } else {
            // 未获取到锁
            throw new RuntimeException("业务处理中，请稍后重试");
        }
    }

    /**
     * 有返回值的加锁执行方法
     *
     * @param lockKey
     * @param businessLogic
     * @param <T>
     * @return
     */
    public <T> T execute(String lockKey, Supplier<T> businessLogic) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock()) {
            try {
                return businessLogic.get();
            } finally {
                unlockSafely(lock);
            }
        } else {
            // 未获取到锁
            throw new RuntimeException("业务处理中，请稍后重试");
        }
    }

    /**
     * 安全释放锁
     *
     * @param lock
     */
    private void unlockSafely(RLock lock) {
        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("成功释放锁 {}", lock.getName());
            }
        } catch (Exception e) {
            log.error("释放锁失败 {}", lock.getName(), e);
        }
    }
}
