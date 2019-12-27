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
 *  @author xiezhiyong on 2019/12/19   
 */
public class SnowflakeIdGeneratorTest {

    private static SnowflakeIdGeneratorTest generatorInstance;

    private SnowflakeIdGeneratorTest(){
        super();
    }

    public static SnowflakeIdGeneratorTest getIdGenerator(long workerId, long dataCenterId){
        if (generatorInstance==null){
            generatorInstance = new SnowflakeIdGeneratorTest(workerId,dataCenterId);
        }
        return generatorInstance;
    }


    //================================================Algorithm's Parameter=============================================

    // 系统开始时间截 (UTC 2017-06-28 00:00:00)
    private final long startTime = 0L;
    // 机器id所占的位数
    private final long workerIdBits = 5L;
    // 数据标识id所占的位数
    private final long dataCenterIdBits = 5L;
    // 支持的最大机器id(十进制)，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    // -1L 左移 5位 (worker id 所占位数) 即 5位二进制所能获得的最大十进制数 - 31
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 支持的最大数据标识id —— 31
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    // 序列在id中占的位数
    private final long sequenceBits = 12L;
    // 机器ID 左移位数 —— 12 (即末 sequence 所占用的位数)
    private final long workerIdMoveBits = sequenceBits;

    // 数据标识id 左移位数 —— 17(12+5)
    private final long dataCenterIdMoveBits = sequenceBits + workerIdBits;
    // 时间截向 左移位数 —— 22(5+5+12)
    private final long timestampMoveBits = sequenceBits + workerIdBits + dataCenterIdBits;
    // 生成序列的掩码(12位所对应的最大整数值)，这里为4095 (0b111111111111=0xfff=4095)
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    //=================================================Works's Parameter================================================
    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;
    //===============================================Constructors=======================================================
    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    private SnowflakeIdGeneratorTest(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("DataCenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }
    // ==================================================Methods========================================================
    // 线程安全的获得下一个 ID 的方法
    public synchronized long nextId() {
        long timestamp = currentTime();

        //如果当前时间小于上一次ID生成的时间戳: 说明系统时钟回退过 - 这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出 即 序列 > 4095
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
                | (dataCenterId << dataCenterIdMoveBits)
                | (workerId << workerIdMoveBits)
                | sequence;
    }
    // 阻塞到下一个毫秒 即 直到获得新的时间戳
    protected long blockTillNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }
    // 获得以毫秒为单位的当前时间
    protected long currentTime() {
        return System.currentTimeMillis();
    }
    //====================================================Test Case=====================================================

    /*public static void main(String[] args) {
        *//*SnowflakeIdGeneratorTest idWorker = new SnowflakeIdGeneratorTest(0, 0);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
        String s = "11111111111111111111111111111111111111111111111111111111111";
        System.out.println(s.length());*//*
        long i= -1L ^ (-1L << 5L);
        System.out.println(i);
    }*/

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

    public static void main(String[] args) {

        final Map<Long, Integer> map = new ConcurrentHashMap();
        for (int i =0;i<400;i++){
            SnowflakeIdGeneratorTest idWorker = getIdGenerator(6, 6);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int j=0;j<10000;j++){
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
    }



}
