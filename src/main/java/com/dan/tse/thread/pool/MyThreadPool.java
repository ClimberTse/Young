/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package com.dan.tse.thread.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 *
 *  @author xiezhiyong on 2019/12/18   
 */
public class MyThreadPool {

    /**
     * corePoolSize ： 线程的核心线程数。
     *
     * maximumPoolSize：线程允许的最大线程数。
     *
     * keepAliveTime：当前线程池 线程总数大于核心线程数时，终止多余的空闲线程的时间。
     *
     * Unit：keepAliveTime参数的时间单位
     *
     * workQueue：队伍队列，如果线程池达到了核心线程数，并且其他线程都处于活动状态的时候，则将新任务放入此队列。
     *
     * threadFactory：定制线程的创建过程
     *
     * Handler：拒绝策略，当workQueue队满时，采取的措施。
     */

    /**
     * newSingleThreadExecutor
     *
     * newFixedThreadPool
     *
     * newScheduledThreadPool
     *
     * newCachedThreadPool
     */
    public static void main(String[] args) {

    }
}
