package com.dan.tse.design.factory;

/**
 * 工厂模式
 */
public class FactoryPattern {
    public static void main(String[] args) {
        Factory longjingFactory = new LongjingFactory();
        Factory OolongFactory = new OolongFactory();
        Tea longjing = longjingFactory.createTea();
        Tea oolong = OolongFactory.createTea();
        oolong.describe();
    }
}

/**
 * 工厂接口
 */
interface Factory{
    public Tea createTea();
}
/**
 * 龙井茶工厂
 */
class LongjingFactory implements Factory{
    @Override
    public Tea createTea() {
        return new LongjingTea();
    }
}
/**
 * 乌龙茶
 */
class OolongFactory implements Factory{
    @Override
    public Tea createTea() {
        return new OolongTea();
    }
}
