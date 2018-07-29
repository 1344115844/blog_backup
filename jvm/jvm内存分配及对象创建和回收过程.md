# Java历史

2004.9 jdk1.5 tiger 自动装箱拆箱，泛型，，注解，枚举，变长参数，增强for循环 spring2.x spring4.x

2006 jdk1.6 javaee Javase Javame jdk6

1. 提供脚本支持
2. 提供编译api以及http服务器api

2009 jdk1.7 收购sun 74亿

2014 jdk1.8

2017 jdk1.9

2018 jdk10

# java 技术体系

Java程序设计语言

java 虚拟机

class 类文件格式

Java API

第三方Java类库

# Java8新特性

1. 接口默认方法和静态方法
2. lambda表达式和函数式编程
3. dateAPI
4. 重复注解
5. nashorn JavaScript引擎

# jvm可视化监控工具

jconsole.exe

在jdk/bin目录下，双击打开可运行，监控吗某个Java程序的状态

编写测试类观察jvm内存

JconsoleTest.java

```
package jconsole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/4/29 11:28
 */
public class JconsoleTest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        fill(1000);
    }

    private static void fill(int n) throws InterruptedException {
        List<JconsoleTest> jconsoleTests =new ArrayList<JconsoleTest>();
        for (int i=0;i<n;i++){
            Thread.sleep(200);
            jconsoleTests.add(new JconsoleTest());
        }
    }
}

```

# jvm内存溢出

### Main.java

```
import java.util.ArrayList;
import java.util.List;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/4/29 10:39
 */
public class Main {
    public static void main(String[] args) {
//        测试堆内存溢出
        List<Demo> demoList=new ArrayList<Demo>();
        while (true){
            demoList.add(new Demo());
        }
    }
}

```

### Demo.java

```
import java.util.ArrayList;
import java.util.List;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/4/29 10:39
 */
public class Main {
    public static void main(String[] args) {
//        测试堆内存溢出
        List<Demo> demoList=new ArrayList<Demo>();
        while (true){
            demoList.add(new Demo());
        }
    }
}

```

### 抛出的异常：

```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:231)
	at java.util.ArrayList.add(ArrayList.java:462)
	at Main.main(Main.java:13)
```

### jvm参数

#### 导出堆内存

```
-XX:+HeapDumpOnOutOfMemoryError -Xms20m -Xmx20m
```

# Java虚拟机的内存管理

### 运行时数据区

#### 线程共享区

##### 方法区

​			运行时常量池

##### Java堆

#### 线程独立区

##### 虚拟机栈

​			存放方法运行时所需的数据，称为栈帧

##### 本地方法栈

​			为jvm调用到的native，即本地方法服务

##### 程序计数器

​			记录当前线程执行到字节码的行号



## 程序计数器

1. 如果线程执行的是Java代码，这个计数器记录的正在执行的虚拟机字节码指令的地址，如果正在执行的native方法，这个计数器的值为undefined
2. 此区域是唯一一个在Java虚拟机规范中没有规定任何的OutOfMemoryError的情况的区域

## Java虚拟机栈

1. 这个描述的是Java方法执行的动态内存模型

2. 栈帧：每个方法执行都会创建一个栈帧，伴随方法从创建到执行完成，用于存储局部变量表，操作数栈，动态链接，方法出口等

3. 局部变量表：存放编译期已知的各种基本数据类型，引用类型，returnAddress类型
   局部变量表的内存空间在编译期完成分配，在进入一个方法时，这个方法需要在帧分配多少内存是固定的，在方法运行期间是不会改变的

4. 虚拟机栈的大小
   可能存在StackOverFlowError

   OutOfMemoryError内存不足，申请不到内存空间了

## 本地方法栈

Java虚拟机栈为虚拟机执行Java方法服务

本地方法栈为虚拟机执行native方法服务

## Java堆

存放对象实例

垃圾搜集器管理的主要区域

新生代，老年代，Eden空间

申请不到空间同样抛出outofmemoryerror

## 方法区

存储虚拟机加载的类信息，常量，静态变量，及时编译器编译后的代码等数据

​	类信息:

​		类的版本

​		字段

​		方法

​		接口 

方法区和永久代 Hotspot使用永久代实现方法区，两者不等价

垃圾回收在方法区的行为

异常的定义

申请空间失败抛出outofmemoryerror

## 运行时常量池

常量池相当于一个hashset存放这写常量，

而new 出来的实例肯定是放到堆内存中去

运行时常量和字节码常量的区别，运行时创建的常量和编译期创建的常量的区别

## 直接内存

## 对象的创建

![](C:\Users\13441\Desktop\md\jvm\jvm内存分配及对象创建和回收过程.assets/jvm1.png)

给对象分配内存的方法

1. 指针碰撞
2. 空闲列表

可能会出现线程安全性问题

如何解决

线程同步 缺点：效率低

本地分配缓冲

## 对象的结构

1. header （对象头）

   自身运行时数据（MarkWord）

   ​	哈希值 GC分代年龄 锁状态标志 线程持有的锁 偏向线程ID 偏向时间戳

   类型指针、

   ![](C:\Users\13441\Desktop\md\jvm\jvm内存分配及对象创建和回收过程.assets/jvm2.png)

   

2. instanceData

3. Padding

   占位符填充8的整数倍的作用

## 对象的访问定位

1. 使用句柄
   定位句柄池，在找到对象地址  
2. 直接指针
   直接找到对象地址 性能高 Hotspot使用直接地址定位

 其他虚拟机

Sun hotshot

Bea JRockit

IBM J9

# 虚拟机的发展

1. sun classic vm

- 世界山第一个商用虚拟机
- 只能使用纯解释器的方式执行java代码

1. exact vm

- exact memory management 准确试内存管理
- 编译器和解释器混合工作以及两级及时编译器
- 只在 Solaris平台发布

1. hotspot vm

1. kvm(kilobyte)

2. JRocket
   BEA

   世界上最快的虚拟机

   专注服务器端的应用

   优势

   ​	垃圾搜集器

   ​	MissionControll服务套件 寻找运行时的内存泄露的问题

3. J9

   IBM Technology for Java virtual  machine

4. Azul vm

5. Liquid vm

6. Dalvik vm
   不是Java虚拟机

   寄存器架构，非栈架构
   Google的

7. Microsoft jvm
   只能运行在windows平台下

8. taobaovm
   深度定制

# 垃圾回收

## 如何判定对象为垃圾对象

1. 引用计数法
   在对象中添加一个引用计数器，当有地方引用这个对象的时候，引用计数器的值就加1，当引用失效的时候，计数器的值就减1
   -verbose :gc  -XX:+PrintGCDetails
    打印垃圾回收的信息
2. 可达性分析法
   GCRoot对象
   1. 虚拟机栈
   2. 方法区类属性所引用的对象
   3. 方法区常量所引用的对象
   4. 本地方法栈所引用的对象 

## 如何回收

1. 回收策略

   1. 标记-清除算法
      效率问题

      空间问题

   2. 复制算法
      堆

      1. 新生代
         1. Eden 伊甸园
         2. survivor 存活区
         3. Tenured Gen
      2. 老年代

      虚拟机栈
      本地方法栈
      程序计数器

   3. 标记-整理-清除算法
      针对老年代

   4. 分代收集算法

2. 垃圾回收器

   1. Serial
      单线程

   2. Parnew

   3. parallel  scanvenge收集器
      -XX:MaxGFPauseMillis 垃圾收集器停顿时间1
      -XX:CGTimeRatio 吞吐量大小
      复制算法（新生代收集器）

      多线程收集器
      达到可控吞吐量
      吞吐量：CPU运行代码的时间与CPU消耗的总时间的比值

   4. CMS收集器 current Mark sweep

      1. 工作过程
         1. 初始标记
         2. 并发标记
         3. 重新标记
         4. 并发清理
      2. 优点
         1. 并发收集
         2. 低停顿
      3. 缺点
         1. 占用大量CPU资源
         2. 无法处理浮动垃圾
         3. 出现current mode failure
         4. 空间碎片

   5. G1

# 内存分配

![](C:\Users\13441\Desktop\md\jvm\jvm内存分配及对象创建和回收过程.assets\1525344679957.png)

只要电脑运行内存大于2g，CPU核心是多核， 默认是ServerVM

可以看到我们的虚拟机是HotSpot

## 内存分配策略

1. 优先分配到新生代的Eden区
   VM option 

   -verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8

2. 大对象直接进入到老年代
   指定进入老年代的对象的内存大小
   -XX:PretenureSizeThreshold

3. 长期存活的对象分配到老年代
   -XX:MaxTenuringThreshold
   具有年龄计数器。每次回收时存活，年龄加1.到达阈值就进入老年代中

4. 空间分配担保

   如果内存空间不足，向担保借；

   -XX:(+/-)HandlePromotionFailure

5. 逃逸分析和栈上分配
   通过逃逸分析，分析出没有逃逸的对象，直接在栈上分配空间。
   什么是逃逸分析？
   分析对象的作用域。如果对象只有在方法体内有效，则判定为没有逃逸。否则，为逃逸对象

6. 动态对象年龄判断

# 虚拟机工具

- JPS	
  JAVA PROCESS STATUS

  **JPS 名称:** jps - Java Virtual Machine Process Status Tool

  命令用法: jps options hostid 

  ​              options:命令选项，用来对输出格式进行控制

  ​              hostid:指定特定主机，可以是ip地址和域名, 也可以指定具体协议，端口。        

  **功能描述: j**ps是用于查看有权访问的hotspot虚拟机的进程. 当未指定hostid时，默认查看本机jvm进程，否者查看指定的hostid机器上的jvm进程，此时hostid所指机器必须开启jstatd服务。 jps可以列出jvm进程lvmid，主类类名，main函数参数, jvm参数，jar名称等信息。 

  1. 没添加option的时候，默认列出VM标示符号和简单的class或jar名称
  2. -p  :仅仅显示VM 标示，不显示jar,class, main参数等信息. 
  3. -m:输出主函数传入的参数. 下的hello 就是在执行程序时从命令行输入的参数 
  4. -l: 输出应用程序主类完整package名称或jar完整名称. 
  5. -v: 列出jvm参数, -Xms20m -Xmx50m是启动程序指定的jvm参数 
  6. -V: 输出通过.hotsportrc或-XX:Flags=<filename>指定的jvm参数 
  7. -Joption:传递参数到javac 调用的java lancher. 

- JSTAT
  jstat命令可以类装载，内存，垃圾收集，jit编译。命令的格式如下：

  ```
  jstat [-命令选项] [vmid] [间隔时间/毫秒] [查询次数]
  ```

  1. 类加载统计
     `jstat -class `

     ```
     Loaded:加载class的数量
     Bytes：所占用空间大小
     Unloaded：未加载数量
     Bytes:未加载占用空间
     Time：时间
     ```

     

  2. 编译统计
     `jstat -compiler`

     ```
     Compiled：编译数量。
     Failed：失败数量
     Invalid：不可用数量
     Time：时间
     FailedType：失败类型
     FailedMethod：失败的方法
     ```

  3. 垃圾回收统计
     `jstat -gccapacity `

     ```
     S0C：第一个幸存区的大小
     S1C：第二个幸存区的大小
     S0U：第一个幸存区的使用大小
     S1U：第二个幸存区的使用大小
     EC：伊甸园区的大小
     EU：伊甸园区的使用大小
     OC：老年代大小
     OU：老年代使用大小
     MC：方法区大小
     MU：方法区使用大小
     CCSC:压缩类空间大小
     CCSU:压缩类空间使用大小
     YGC：年轻代垃圾回收次数
     YGCT：年轻代垃圾回收消耗时间
     FGC：老年代垃圾回收次数
     FGCT：老年代垃圾回收消耗时间
     GCT：垃圾回收消耗总时间
     ```

  4. 新生代垃圾回收统计
     `jstat -gcnew`

     ```
     S0C：第一个幸存区大小
     S1C：第二个幸存区的大小
     S0U：第一个幸存区的使用大小
     S1U：第二个幸存区的使用大小
     TT:对象在新生代存活的次数
     MTT:对象在新生代存活的最大次数
     DSS:期望的幸存区大小
     EC：伊甸园区的大小
     EU：伊甸园区的使用大小
     YGC：年轻代垃圾回收次数
     YGCT：年轻代垃圾回收消耗时间
     ```

  5. 新生代内存统计
     `jstat -gcnewcapacity`

     ```
     NGCMN：新生代最小容量
     NGCMX：新生代最大容量
     NGC：当前新生代容量
     S0CMX：最大幸存1区大小
     S0C：当前幸存1区大小
     S1CMX：最大幸存2区大小
     S1C：当前幸存2区大小
     ECMX：最大伊甸园区大小
     EC：当前伊甸园区大小
     YGC：年轻代垃圾回收次数
     FGC：老年代回收次数
     ```

  6. 老年代垃圾回收统计
     `jstat -gcold `

     ```
     MC：方法区大小
     MU：方法区使用大小
     CCSC:压缩类空间大小
     CCSU:压缩类空间使用大小
     OC：老年代大小
     OU：老年代使用大小
     YGC：年轻代垃圾回收次数
     FGC：老年代垃圾回收次数
     FGCT：老年代垃圾回收消耗时间
     GCT：垃圾回收消耗总时间
     ```

  7. 老年代内存统计
     `jstat -gcoldcapacity`

     ```
     OGCMN：老年代最小容量
     OGCMX：老年代最大容量
     OGC：当前老年代大小
     OC：老年代大小
     YGC：年轻代垃圾回收次数
     FGC：老年代垃圾回收次数
     FGCT：老年代垃圾回收消耗时间
     GCT：垃圾回收消耗总时间
     ```

  8. 元数据空间统计
     `jstat -gcmetacapacity`

     ```
     MCMN:最小元数据容量
     MCMX：最大元数据容量
     MC：当前元数据空间大小
     CCSMN：最小压缩类空间大小
     CCSMX：最大压缩类空间大小
     CCSC：当前压缩类空间大小
     YGC：年轻代垃圾回收次数
     FGC：老年代垃圾回收次数
     FGCT：老年代垃圾回收消耗时间
     GCT：垃圾回收消耗总时间
     ```

  9. 总结垃圾回收统计
     `jstat -gcutil`

     ```
     S0：幸存1区当前使用比例
     S1：幸存2区当前使用比例
     E：伊甸园区使用比例
     O：老年代使用比例
     M：元数据区使用比例
     CCS：压缩使用比例
     YGC：年轻代垃圾回收次数
     FGC：老年代垃圾回收次数
     FGCT：老年代垃圾回收消耗时间
     GCT：垃圾回收消耗总时间
     ```

  10. JVM编译方法统计
      `jstat -printcompilation`

      ```
      Compiled：最近编译方法的数量
      Size：最近编译方法的字节码数量
      Type：最近编译方法的编译类型。
      Method：方法名标识。
      ```

      

- JINFO
  jinfo是jdk自带的命令，用来查看jvm的配置参数。通常会先使用jps查看java进程的id，然后使用jinfo查看指定pid的jvm信息 
  查看jvm的参数

  `jinfo -flags process_id`
  查看java系统参数
  `jinfo -sysprops process_id`

- JMAP
  JVM Memory Map命令用于生成heap dump文件，如果不使用这个命令，还可以使用-`XX:+HeapDumpOnOutOfMemoryError`参数来让虚拟机出现OOM的时候自动生成dump文件。 

  ```
  参数
  
  option：选项参数，不可同时使用多个选项参数
  
  pid：java进程id，命令ps -ef | grep java获取
  
  executable：产生核心dump的java可执行文件
  
  core：需要打印配置信息的核心文件
  
  remote-hostname-or-ip：远程调试的主机名或ip
  
  server-id：可选的唯一id，如果相同的远程主机上运行了多台调试服务器，用此选项参数标识服务器
  
  
  
  options参数
  
  heap : 显示Java堆详细信息
  histo : 显示堆中对象的统计信息
  permstat :Java堆内存的永久保存区域的类加载器的统计信息
  finalizerinfo : 显示在F-Queue队列等待Finalizer线程执行finalizer方法的对象
  dump : 生成堆转储快照
  F : 当-dump没有响应时，强制生成dump快照
  ```

  ```
  -dump
  dump堆到文件,format指定输出格式，live指明是活着的对象,file指定文件名
  ```

  ```
  -heap
  打印heap的概要信息，GC使用的算法，heap的配置及使用情况，可以用此来判断内存目前的使用情况以及垃圾回收情况
  ```

  ```
  -finalizerinfo
  
  打印等待回收的对象信息，
  ```

  ```
  -histo
  
  打印堆的对象统计，包括对象数、内存大小等等。jmap -histo:live 这个命令执行，JVM会先触发gc，然后再统计信息
  jmap -histo:live 24971 | grep com.yuhuo 查询类名包含com.yuhuo的信息
  
  jmap -histo:live 24971 | grep com.yuhuo > histo.txt 保存信息到histo.txt文件
  ```

  ```
  -permstat
  
  打印Java堆内存的永久区的类加载器的智能统计信息。对于每个类加载器而言，它的名称、活跃度、地址、父类加载器、它所加载的类的数量和大小都会被打印。此外，包含的字符串数量和大小也会被打印。
  ```

  ```
  -F
  
  强制模式。如果指定的pid没有响应，请使用jmap -dump或jmap -histo选项。此模式下，不支持live子选项。
  ```

  

- JHAT

  ```
  JVM Heap Analysis Tool命令是与jmap搭配使用，用来分析jmap生成的dump，jhat内置了一个微型的HTTP/HTML服务器，生成dump的分析结果后，可以在浏览器中查看。在此要注意，一般不会直接在服务器上进行分析，因为jhat是一个耗时并且耗费硬件资源的过程，一般把服务器生成的dump文件复制到本地或其他机器上进行分析。【内存分析】
  ```

  ```
  参数
  
  -J< flag >                 
  因为 jhat 命令实际上会启动一个JVM来执行, 通过 -J 可以在启动JVM时传入一些启动参数. 例如, -J-Xmx512m 则指定运行 jhat 的Java虚拟机使用的最大堆内存为 512 MB. 如果需要使用多个JVM启动参数,则传入多个 -Jxxxxxx.
  -stack false|true 
  关闭对象分配调用栈跟踪(tracking object allocation call stack)。 如果分配位置信息在堆转储中不可用. 则必须将此标志设置为 false. 默认值为 true.
  -refs false|true 
  关闭对象引用跟踪(tracking of references to objects)。 默认值为 true. 默认情况下, 返回的指针是指向其他特定对象的对象,如反向链接或输入引用(referrers or incoming references), 会统计/计算堆中的所有对象。
  -port port-number 
  设置 jhat HTTP server 的端口号. 默认值 7000。
  -exclude exclude-file 
  指定对象查询时需要排除的数据成员列表文件(a file that lists data members that should be excluded from the reachable objects query)。 例如, 如果文件列列出了 java.lang.String.value , 那么当从某个特定对象 Object o 计算可达的对象列表时, 引用路径涉及 java.lang.String.value 的都会被排除。
  -baseline exclude-file 
  指定一个基准堆转储(baseline heap dump)。 在两个 heap dumps 中有相同 object ID 的对象会被标记为不是新的(marked as not being new). 其他对象被标记为新的(new). 在比较两个不同的堆转储时很有用。
  -debug int 
  设置 debug 级别. 0 表示不输出调试信息。 值越大则表示输出更详细的 debug 信息。
  -version 
  启动后只显示版本信息就退出。
  ```

  

- JSTACK

- JCONSOLE

# 性能调优

1. 常用思路

   1. 优化sql

   2. 监控CPU

   3. 监控内存

      - FULL GC  垃圾收集时间过长

        - 解决方案

          - 调整堆内存大小

            

问题:

1. 不定期出现内存溢出,把堆内存加大也没用,导出内存信息没有任何信息.内存监控,也正常

处理思路:

1. 控制变量法
   1. 硬件环境
      1. CPU
      2. 内存
   2. 软件环境
      1. 操作系统
      2. Java版本
      3. 容器
      4. 代码问题







