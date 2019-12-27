/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package com.dan.tse.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  @author xiezhiyong on 2019/12/18   
 */
public class MyCallable implements Callable {
    @Override
    public Integer call() throws Exception {
        String name = Thread.currentThread().getName();
        System.out.println("MyCallable-->threadName=="+name);
        return 123;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String name = Thread.currentThread().getName();
        System.out.println("main-->threadName=="+name);
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }
}
