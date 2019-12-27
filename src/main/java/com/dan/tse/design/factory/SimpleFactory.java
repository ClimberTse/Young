package com.dan.tse.design.factory;

/**
 * 简单工厂
 */
public class SimpleFactory {
    public static void main(String[] args) {
        Tea tea1=TeaFactory.getTea(1);
        Tea tea2=TeaFactory.getTea(2);
        tea1.describe();
        tea2.describe();
    }
}

//创建一个接口
interface Tea{
    /**
     * 具体功能方法的定义
     */
    public void describe();
}

/**
 * 具体实现类1
 */
class LongjingTea implements Tea{
    @Override
    public void describe() {
        System.out.println("一杯龙井茶");
    }
}

/**
 * 具体实现类2
 */
class OolongTea implements Tea{
    @Override
    public void describe() {
        System.out.println("一杯乌龙茶");
    }
}

/**
 * 工厂类，用来获取对象
 */
class TeaFactory{
    public static Tea getTea(int condition){
        Tea tea=null;
        if(condition==1){
            tea=new LongjingTea();
        }
        if(condition==2){
            tea=new OolongTea();
        }
        return tea;
    }
}