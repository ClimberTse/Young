package com.dan.tse.design.factory;

public class AbstractFactoryPattern {

}
//cpu接口和实现类
interface Cpu {
    void run();

    class Cpu650 implements Cpu {
        @Override
        public void run() {
            //625 也厉害
        }
    }

    class Cpu825 implements Cpu {
        @Override
        public void run() {
            //825 处理更强劲
        }
    }
}

//屏幕接口和实现类
interface Screen {

    void size();

    class Screen5 implements Screen {
        @Override
        public void size() {
            //5寸
        }
    }

    class Screen6 implements Screen {
        @Override
        public void size() {
            //6寸
        }
    }
}

//工厂接口
interface PhoneFactory {
    Cpu getCpu();//使用的cpu
    Screen getScreen();//使用的屏幕
}

//具体工厂类：小米手机工厂
class XiaoMiFactory implements PhoneFactory {
    @Override
    public Cpu getCpu() {
        return new Cpu.Cpu825();//高性能处理器
    }

    @Override
    public Screen getScreen() {
        return new Screen.Screen6();//6寸大屏
    }
}

//红米手机工厂
class HongMiFactory implements PhoneFactory {
    @Override
    public Cpu getCpu() {
        return new Cpu.Cpu650();//高效处理器
    }

    @Override
    public Screen getScreen() {
        return new Screen.Screen5();//小屏手机
    }
}