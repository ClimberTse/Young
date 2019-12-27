# 1.单例设计模式

- 饿汉式
- 懒汉式
- 静态内部类实现

```java
class SingleTon{
    private  SingleTon(){
    }
    public static class SingleHolder{
        public static final SingleTon INSTANCE=new SingleTon();
    }
    public static SingleTon getInstance(){
        return SingleHolder.INSTANCE;
    }
}
```

- 双重校验锁

```java
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
```
使用反射打破单例
```java
//获得构造器
Constructor<SingleTon> con = SingleTon.class.getDeclaredConstructor();
//设置为可访问
con.setAccessible(true);
//构造两个不同的对象
SingleTon singleton1 = (SingleTon)con.newInstance();
SingleTon singleton2 = (SingleTon)con.newInstance();
//验证是否是不同对象
System.out.println(singleton1.equals(singleton2));
```

- 枚举（JVM会阻止反射获取枚举类的私有构造方法）

```java
enum EnumSingleton{
    INSTANCE;
    public EnumSingleton getInstance(){
        return INSTANCE;
    }
}
```

# 2. 工厂模式

- 简单工厂

  定义：一个工厂方法，依据传入的参数，生成对应的产品对象。

  角色： 1、抽象产品   2、具体产品   3、具体工厂   4、产品使用者

  缺点：如果要新增一个产品，必须修改工厂类，违反了开放封闭原则。一般只适合产品量少，需求固定的情况。 

  ```java
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
  
  /**
   * 创建一个接口（抽象产品类）
   */
  interface Tea{
      //具体功能方法的定义
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
  ```

- 工厂模式

  定义：将工厂提取成一个接口或抽象类，具体生产什么产品由子类决定；

  角色：1、抽象产品类   2、具体产品类   3、抽象工厂类   4、具体工厂类

  缺点：如果有很多产品，则需要创建非常多的工厂

  ```java
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
  ```

- 抽象工厂

  定义：为创建一组相关或者是相互依赖的对象提供的一个接口，而不需要指定它们的具体类。 在一个工厂里聚合多个同类产品，工厂方法是生产一个具体的产品，而抽象工厂可以用来生产一组相同，有联系的产品。

  ```java
  //cpu接口和实现类
  public interface Cpu {
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
  public interface Screen {
  
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
  public interface PhoneFactory {
      Cpu getCpu();//使用的cpu
      Screen getScreen();//使用的屏幕
  }
  
  //具体工厂类：小米手机工厂
  public class XiaoMiFactory implements PhoneFactory {
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
  public class HongMiFactory implements PhoneFactory {
      @Override
      public Cpu getCpu() {
          return new Cpu.Cpu650();//高效处理器
      }
  
      @Override
      public Screen getScreen() {
          return new Screen.Screen5();//小屏手机
      }
  }
  ```

- 模板模式

  角色：父类模板类（规定要执行的方法和顺序，只关心方法的定义及顺序，不关心方法实现），子类实现类（实现父类规定要执行的方法，只关心方法实现，不关心调用顺序）

  ```java
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
  ```

- 策略模式

  ```java
  /**
   * 设计模式---策略模式
   */
  public class StrategyPattern {
      public static void main(String[] args){
          Context context = new Context(new OperationAdd());
          System.out.println("10 + 5 = " + context.executeStrategy(10, 5));
  
          context = new Context(new OperationSubstract());
          System.out.println("10 - 5 = " + context.executeStrategy(10, 5));
  
          context = new Context(new OperationMultiply());
          System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
      }
  }
  //策略模式接口
  interface Strategy {
      public int doOperation(int num1, int num2);
  }
  
  //具体策略类A
  class OperationAdd implements Strategy{
      @Override
      public int doOperation(int num1, int num2) {
          return num1 + num2;
      }
  }
  //具体策略类B
  class OperationSubstract implements Strategy{
      @Override
      public int doOperation(int num1, int num2) {
          return num1 - num2;
      }
  }
  //具体策略类C
  class OperationMultiply implements Strategy{
      @Override
      public int doOperation(int num1, int num2) {
          return num1 * num2;
      }
  }
  //策略模式上下文
  class Context {
      private Strategy strategy;
  
      public Context(Strategy strategy){
          this.strategy = strategy;
      }
  
      public int executeStrategy(int num1, int num2){
          return strategy.doOperation(num1, num2);
      }
  }
  ```

  # Java并发
  
  ## 1. 进程和线程的区别
  
  - 进程：进程是程序的一次执行过程，是资源分配的最小单位 。
  
  - 线程：线程是进程的一个执行单元，是程序任务调度和执行的最小单位 。
  
  ## 2. 线程的状态
  
  - 新建状态：新创建了一个对象
  - 就绪状态（可执行状态）：调用线程的 start() 方法后进入可执行状态。
  - 运行状态：线程获得 CPU 执行权，从就绪状态变为运行状态。
  - 阻塞状态：线程因为某种原因放弃CPU使用权，暂时停止运行。
  - 死亡转态：线程执行完成或因为异常退出了执行。
  
  <img src="/580846-20170225204223851-1795574074.jpg" width="600"/>
  
  ##3. wait() 、sleep() 与 yield() 的区别
  
  - sleep() 和 yield() 方法定义在 Thread 类中，而 wait() 方法定义在 Object 类中。
  - wait() 与 sleep() 的区别在于：执行 wait() 方法的线程会释放所持有的锁，然后进入等待对列，需要其他线程调用其 notify() 或 notifyAll() 方法对其进行唤醒。而执行 sleep() 方法不会释放当前线程所持有的锁。
  -  yield() 和 sleep() 都会让出 CPU 执行权，但是 yield() 只会将 CPU 执行权让给和它线程优先级相同的线程，而 sleep() 不考虑线程优先级。sleep()方法声明抛出InterruptedException异常，而yield()方法没有声明抛出任何异常 。线程执行 sleep() 方法后转为阻塞状态，而执行 yield() 方法后转为就绪状态。
  
  ## 4. 守护线程
  
  ​	线程分为用户线程和守护线程，守护线程为用户线程服务，当用户线程结束后，守护线程也会退出，比如垃圾回收线程就是一个守护线程 。将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。 设置为守护线程必须在线程启动前，否则会抛IllegalThreadStateException 异常
  
  ## 5. 什么是 ThreadLocal
  
  ​	ThreadLocal 是每个线程独自拥有的一个变量副本，每个线程都可以独立地改变自己的变量副本，而不会影响其他线	  				     								程 ThreadLocal 中的内容。ThreadLocal 本身并不存储值，存储值的是 ThreadLocal 的内部类 ThreadLocalMap，以当前ThreadLcoal 对象为key，value 则是当前线程的变量副本。
  
  ​	[Java并发编程，深入剖析ThreadLocal](https://www.cnblogs.com/dolphin0520/p/3920407.html)
  
  ​	[ThreadLocal-面试必问深度解析](https://www.jianshu.com/p/98b68c97df9b)
  
  ## 6. synchronized 原理
  
  ​	[《【死磕 Java 并发】—– 深入分析 synchronized 的实现原理》](http://www.iocoder.cn/JUC/sike/synchronized/?vip) 
  
  ​	synchronized: 具有原子性，有序性和可见性； volatile：具有有序性和可见性。synchronized 的实现原理是，同一时刻只能有一个线程获取对象的监视器，
  
  ## 7. volatile 原理
  
  ​	[【死磕 Java 并发】—– 深入分析 volatile 的实现原理](http://www.iocoder.cn/JUC/sike/volatile/)
  
  ​	[聊聊并发（一）——深入分析Volatile的实现原理](https://www.infoq.cn/article/ftf-java-volatile)
  
