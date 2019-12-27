package com.dan.tse.design;

/**
 * 设计模式---模板设计模式
 *  父类模板类（规定要执行的方法和顺序，只关心方法的定义及顺序，不关心方法实现）
 *  子类实现类（实现父类规定要执行的方法，只关心方法实现，不关心调用顺序）
 */
public class TemplatePattern {
    public static void main(String[] args){
        DodishTemplate dish = new BraisedPork();
        dish.dodish();
    }
}

abstract class DodishTemplate{
    protected void dodish(){
        this.preparation();
        this.dish();
        this.carryDishes();
    }
    //备料
    public abstract void preparation();
    //做菜
    public abstract void dish();
    //上菜
    public abstract void carryDishes();
}
//做红烧肉
class BraisedPork extends DodishTemplate{

    @Override
    public void preparation() {
        System.out.println("切好猪肉，准备好酱油等。");
    }

    @Override
    public void dish() {
        System.out.println("把猪肉爆炒，在放入作料。");
    }

    @Override
    public void carryDishes() {
        System.out.println("起过，上菜。");
    }
}