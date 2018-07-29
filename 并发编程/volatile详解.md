# volatile关键字

## 作用

1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
2）禁止进行指令重排序

## 代码实例

如下代码，线程1去修改flag为true，当修改成功时，主线程就会打印字符，并跳出循环

当flag字段没有volatile关键字修饰时，程序却陷入死循环。

而引入volatile关键字后，正常退出。

原因就是每个线程工作时，会把主存的变量拷贝一份到线程中，线程1改变了flag的值，并写入到主存中，而主线程的flag在线程1写入主存中前就拷贝到主线程了。并且经过虚拟机优化后的代码，指令重新排序了。while循环是CPU没有重新从主存中获取修改后的flag的值，所以才陷入死循环。

```
package JUC.volatile实例;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/7/14 10:41
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        while(true){
            if(td.isFlag()){
                System.out.println("------------------");
                break;
            }
        }

    }

}

class ThreadDemo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
```

## 什么是指令重排？

> 引用自https://www.cnblogs.com/dolphin0520/p/3920373.html

### 概念

指令重排序是JVM为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，尽可能地提高并行度。编译器、处理器也遵循这样一个目标。注意是单线程。多线程的情况下指令重排序就会给程序员带来问题。

### 例子

如果一个操作不是原子的，就会给JVM留下重排的机会。下面看几个例子：

例子1：A线程指令重排导致B线程出错
对于在同一个线程内，这样的改变是不会对逻辑产生影响的，但是在多线程的情况下指令重排序会带来问题。看下面这个情景:

在线程A中:
context = loadContext();
inited = true;
在线程B中:
while(!inited ){ //根据线程A中对inited变量的修改决定是否使用context变量
   sleep(100);
}
doSomethingwithconfig(context);
假设线程A中发生了指令重排序:
inited = true;
context = loadContext();
那么B中很可能就会拿到一个尚未初始化或尚未初始化完成的context,从而引发程序错误。

例子2：指令重排导致单例模式失效
我们都知道一个经典的懒加载方式的双重判断单例模式：
public class Singleton {
  private static Singleton instance = null;
  private Singleton() { }
  public static Singleton getInstance() {
     if(instance == null) {
        synchronzied(Singleton.class) {
           if(instance == null) {
               <strong>instance = new Singleton();  //非原子操作
           }
        }
     }
     return instance;
   }
}
看似简单的一段赋值语句：instance= new Singleton()，但是很不幸它并不是一个原子操作，其实际上可以抽象为下面几条JVM指令：
memory =allocate();    //1：分配对象的内存空间 
ctorInstance(memory);  //2：初始化对象 
instance =memory;     //3：设置instance指向刚分配的内存地址
上面操作2依赖于操作1，但是操作3并不依赖于操作2，所以JVM是可以针对它们进行指令的优化重排序的，经过重排序后如下：
memory =allocate();    //1：分配对象的内存空间 
instance =memory;     //3：instance指向刚分配的内存地址，此时对象还未初始化
ctorInstance(memory);  //2：初始化对象
可以看到指令重排之后，instance指向分配好的内存放在了前面，而这段内存的初始化被排在了后面。

在线程A执行这段赋值语句，在初始化分配对象之前就已经将其赋值给instance引用，恰好另一个线程进入方法判断instance引用不为null，然后就将其返回使用，导致出错。

## 禁止指令重排序的意思

1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。
可能上面说的比较绕，举个简单的例子：

```
//x、y为非volatile变量
//flag为volatile变量

x = 2;        //语句1
y = 0;        //语句2
flag = true;  //语句3
x = 4;         //语句4
y = -1;       //语句5
```

 　　由于flag变量为volatile变量，那么在进行指令重排序的过程的时候，不会将语句3放到语句1、语句2前面，也不会讲语句3放到语句4、语句5后面。但是要注意语句1和语句2的顺序、语句4和语句5的顺序是不作任何保证的。

　　并且volatile关键字能保证，执行到语句3时，语句1和语句2必定是执行完毕了的，且语句1和语句2的执行结果对语句3、语句4、语句5是可见的。

## volatile实现禁止指令重排的原理

volatile关键字通过提供“内存屏障”的方式来防止指令被重排序，为了实现volatile的内存语义，编译器在生成字节码时，会在指令序列中插入内存屏障来禁止特定类型的处理器重排序。

大多数的处理器都支持内存屏障的指令。

对于编译器来说，发现一个最优布置来最小化插入屏障的总数几乎不可能，为此，Java内存模型采取保守策略。下面是基于保守策略的JMM内存屏障插入策略：

在每个volatile写操作的前面插入一个StoreStore屏障。

在每个volatile写操作的后面插入一个StoreLoad屏障。

在每个volatile读操作的后面插入一个LoadLoad屏障。

在每个volatile读操作的后面插入一个LoadStore屏障。

> 引用自http://ifeve.com/memory-barriers-or-fences/

### 内存屏障是什么？

#### 概念

内存屏障或内存栅栏，也就是让一个CPU处理单元中的内存状态对其它处理单元可见的一项技术。

硬件层的内存屏障分为两种：Load Barrier 和 Store Barrier即读屏障和写屏障。

#### 作用：

1. 阻止屏障两侧的指令重排序；
2. 强制把写缓冲区/高速缓存中的脏数据等写回主内存，让缓存中相应的数据失效。

对于Load Barrier来说，在指令前插入Load Barrier，可以让高速缓存中的数据失效，强制从新从主内存加载数据；
对于Store Barrier来说，在指令后插入Store Barrier，能让写入缓存中的最新数据更新写入主内存，让其他线程可见

> 引用自https://www.jianshu.com/p/2ab5e3d7e510

#### java内存屏障

java的内存屏障通常所谓的四种即LoadLoad,StoreStore,LoadStore,StoreLoad实际上也是上述两种的组合，完成一系列的屏障和数据同步功能。
LoadLoad屏障：对于这样的语句Load1; LoadLoad; Load2，在Load2及后续读取操作要读取的数据被访问前，保证Load1要读取的数据被读取完毕。
StoreStore屏障：对于这样的语句Store1; StoreStore; Store2，在Store2及后续写入操作执行前，保证Store1的写入操作对其它处理器可见。
LoadStore屏障：对于这样的语句Load1; LoadStore; Store2，在Store2及后续写入操作被刷出前，保证Load1要读取的数据被读取完毕。
StoreLoad屏障：对于这样的语句Store1; StoreLoad; Load2，在Load2及后续所有读取操作执行前，保证Store1的写入对所有处理器可见。它的开销是四种屏障中最大的。在大多数处理器的实现中，这个屏障是个万能屏障，兼具其它三种内存屏障的功能

## 参考文献：

https://www.cnblogs.com/dolphin0520/p/3920373.html
http://ifeve.com/memory-barriers-or-fences/
http://www.importnew.com/23535.html
https://www.jianshu.com/p/2ab5e3d7e510

