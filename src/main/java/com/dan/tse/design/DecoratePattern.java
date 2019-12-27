package com.dan.tse.design;

public class DecoratePattern {
    public static void main(String[] args){
        Beverage espresso1 = new Espresso(); //实例化一个espresso对象
        System.out.println(espresso1.getDescription() + ":" + espresso1.cost()); //输出只有espresso没有配料的描述与价格
        espresso1 = new Whip(espresso1); //继续用whip装饰被mocha装饰后的espresso
        System.out.println(espresso1.getDescription() + ":" + espresso1.cost()); //输出被两种配料装饰后的描述与价格
    }
}

/*headfirst设计模式 第三章节 装饰者模式，装饰者模式分为装饰者与被装饰者，它们必须共享同一个超类，或者接口，因为只有这样，才可以用
 * 装饰者替换被装饰者（被装饰者对象被装饰者装饰后变成了装饰者对象，只有它们两个对象可以相互替换才可使用装饰者模式）
 * 本章节把咖啡饮料比作被装饰者，把调料比作装饰者，用新的调料来装饰饮料，得到新的装饰者对象
 */

abstract class Beverage{ //装饰者与被装饰者共享的超类，这里是个抽象类
    String description = "Unknown Beverage"; //饮料描述，此时为未知饮料
    public String getDescription(){ //获取饮料描述的方法
        return description;
    }
    public abstract double cost(); //抽象方法，获取价格金额
}

abstract class CondimentDecorator extends Beverage{ //为装饰者的实现写的一个抽象类，所有装饰者继承该类
    public abstract String getDescription(); //装饰者内部需要重写获取描述的方法，因为每装饰一次，就添加了新的配料
}

class Espresso extends Beverage{ //具体化了一个饮料类，黑咖啡
    public Espresso(){
        description = "espresso"; //描述变更为黑咖啡
    }

    public double cost(){
        return 1.99; //返回黑咖啡价格
    }
}

class HouseBlend extends Beverage{ //具体化了一个饮料类，混合咖啡

    public HouseBlend(){
        description = "houseblend"; //描述变更为混合咖啡
    }

    public double cost() {
        return .89; //返回混合咖啡的价格
    }
}

class Whip extends CondimentDecorator{ //具体化了一个配料类，奶泡，以下代码功能与mocha配料一致
    Beverage beverage;
    public Whip(Beverage bever){
        this.beverage = bever;
    }
    public String getDescription(){
        return beverage.getDescription() + ",whip";
    }
    public double cost(){
        return beverage.cost() + .41;
    }
}


