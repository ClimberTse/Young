/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package com.young.snowid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author xiezhiyong on 2019/12/20   
 */
public class SnowflakeIdGenerator {
    /**
     * 系统开始时间截.
     */
    private final long startTime = 0L;
    /**
     * 机器id所占的位数.
     */
    private final long workerIdBits = 9L;
    /**
     * 支持最大机器Id(1024).
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 序列在id中占的位数.
     */
    private final long sequenceBits = 8L;

    /**
     * 机器Id左移位数——12(即末 sequence 所占用的位数).
     */
    private final long workerIdMoveBits = sequenceBits;

    /**
     * 时间截向,左移位数—— 17(10+7).
     */
    private final long timestampMoveBits = sequenceBits + workerIdBits;

    /**
     * 生成序列的掩码(7位所对应的最大整数值),这里为256.
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~1023)
     */
    private long workerId;
    /**
     * 毫秒内序列(0~256)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static SnowflakeIdGenerator INSTANCE;

    public static SnowflakeIdGenerator getIdGeneratorInstance(long workerId){
        if (INSTANCE==null){
            INSTANCE = new SnowflakeIdGenerator(workerId);
        }

        return  INSTANCE;
    }

    /**
     * 构造函数.
     *
     * @param workerId  工作ID(0~1023).
     */
    private SnowflakeIdGenerator(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }


    /**
     * 线程安全的获得下一个ID的方法.
     *
     * @return ID.
     */
    public synchronized long nextId() {
        long timestamp = currentTime();

        /**
         * 如果当前时间小于上一次ID生成的时间戳:说明系统时钟回退过,这个时候应当抛出异常.
         */
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        /**
         * 如果是同一时间生成的,则进行毫秒内序列.
         */
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出 即 序列 > 256
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = blockTillNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampMoveBits)
                | (workerId << workerIdMoveBits)
                | sequence;
    }


    /**
     * 阻塞到下一个毫秒直到获得新的时间戳
     */
    protected long blockTillNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    /**
     * 获得当前毫秒值.
     */
    protected long currentTime() {
        return System.currentTimeMillis();
    }
    //====================================================Test Case=====================================================

   /* public static void main(String[] args) {

        final Map<Long, Integer> map = new ConcurrentHashMap();
        for (int i =0;i<400;i++){
            SnowflakeIdGenerator idWorker = getIdGeneratorInstance(235);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j=0;j<1000;j++){
                        long s = idWorker.nextId();
                        //System.out.println(s);
                        Integer put = map.put(s, 1);
                        if (put!=null){
                            System.out.println("****************id重复****************");
                        }
                    }
                }
            }).start();
        }
    }*/



    public static void main(String[] args) {
        String s = "111111111111111111111111111111111111111111111111111111111111111";
        System.out.println(s.length());
    }

    /*public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        SnowflakeIdGeneratorTest idWorker = new SnowflakeIdGeneratorTest(1, 1);
        long startTime = System.nanoTime();
        for (int i = 0; i < 50000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
        System.out.println((System.nanoTime()-startTime)/1000000+"ms");
        System.out.println(Long.MAX_VALUE);
    }*/
    //6613314949827662049
    //1323450621252210914
//   1111111111111111111111111111111111111111111111111111111111111
}
