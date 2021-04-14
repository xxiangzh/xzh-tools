package com.xzh.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 *
 * @author 向振华
 * @date 2020/06/12 10:24
 */
public class ThreadPoolFactory {

    /**
     * 生成固定大小的线程池
     *
     * @param threadName 线程名称
     * @return 线程池
     */
    public static ExecutorService createFixedThreadPool(String threadName) {
        AtomicInteger threadNumber = new AtomicInteger(0);
        return new ThreadPoolExecutor(
                // 核心线程数
                desiredThreadNum(),
                // 最大线程数
                desiredThreadNum(),
                // 空闲线程存活时间
                60L,
                // 空闲线程存活时间单位
                TimeUnit.SECONDS,
                // 工作队列
                new ArrayBlockingQueue<>(1024),
                // 线程工厂
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, threadName + "-" + threadNumber.getAndIncrement());
                    }
                },
                // 拒绝策略
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        if (!executor.isShutdown()) {
                            try {
                                //尝试阻塞式加入任务队列
                                executor.getQueue().put(r);
                            } catch (Exception e) {
                                //保持线程的中断状态
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                });
    }

    /**
     * 理想的线程数，使用 2倍cpu核心数
     */
    public static int desiredThreadNum() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }
}