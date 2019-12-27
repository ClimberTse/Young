package com.dan.tse.design;

import java.lang.reflect.Constructor;

/**
 * 设计模式---单例设计模式
 */
public class SingletonPattern {

    public static void main(String[] args) {
        //使用反射打破单例
        try {
            //获得构造器
            Constructor<SingleTon> con = SingleTon.class.getDeclaredConstructor();
            //设置为可访问
            con.setAccessible(true);
            //构造两个不同的对象
            SingleTon singleton1 = (SingleTon)con.newInstance();
            SingleTon singleton2 = (SingleTon)con.newInstance();
            //验证是否是不同对象
            System.out.println(singleton1.equals(singleton2));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
class SingleTon{
    private SingleTon(){

    }
    private static volatile SingleTon instance=null;
    //双重检测
    public static SingleTon getInstance(){

            // 如果两个线程同时进行判断，instance构建了两次，所以要加锁。
            if(instance==null){
                synchronized (SingleTon.class){
                    if(instance==null)
                        instance=new SingleTon();
                }
            }
        return instance;
    }
}

//静态内部类式
class SingleTon2{
    private  SingleTon2(){
    }
    public static class SingleHolder{
        public static final SingleTon2 INSTANCE=new SingleTon2();
    }
    public static SingleTon2 getInstance(){
        return SingleHolder.INSTANCE;
    }
}

enum EnumSingleton{
    INSTANCE;
    public EnumSingleton getInstance(){
        return INSTANCE;
    }
}