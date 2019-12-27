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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author xiezhiyong on 2019/12/19   
 */
public class IdGenerator {

    public static void main(String[] args) {
        final Map<String, Integer> map = new ConcurrentHashMap();
        for (int i =0;i<400;i++){
            new Thread(new Runnable() {
                IdGenerator idGenerator = new IdGenerator();
                @Override
                public void run() {
                    for (int j=0;j<1000;j++){
                        String s = idGenerator.generateId();
                        Integer put = map.put(s, 1);
                        if (put!=null){
                            System.out.println("****************id重复===="+s+"**************");
                        }
                    }
                }
            }).start();
        }
    }


    public String generateId() {
        long currentTime = currentTime();
        String id = currentTime+createRandomCharData(5);
        return id;
    }

    /**
     * 当前毫秒数.
     *
     * @return
     */
    protected long currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 生成指定长度的大小写加数字的随机数.
     *
     */
    public  String createRandomCharData(int length){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        Random dataRand=new Random();
        int data;
        for(int i=0;i<length;i++){
            int index=rand.nextInt(3);
            /**
             * 随机选择生成数字,大小写字母.
             */
            switch(index){
                /**
                 * 生成随机数字.
                 */
                case 0:
                    data=dataRand.nextInt(10);
                    sb.append(data);
                    break;
                /**
                 * 生成随机大写字母.
                 */
                case 1:
                    data=dataRand.nextInt(26)+65;
                    sb.append((char)data);
                    break;
                /**
                 * 生成随机小写字母.
                 */
                case 2:
                    data=dataRand.nextInt(26)+97;
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        return result;
    }
}
