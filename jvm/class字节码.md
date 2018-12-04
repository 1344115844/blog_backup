# class文件简介和发展历史

1. class文件以0xCAFEBABE开头的二进制文件
2. class变化非常小

#  class文件结构

- class文件是一组以8位字节位基础单位的二进制流,各个数据项目严格按照顺序紧凑的排列在class文件之中,中间没有添加任何分隔符,整个class文件钟存储的内容几乎全部是程序运行的必要数据,没有空隙存在
- 当遇到8为字节以上的空间的数据项时,则会按照高位在前的方式分割成若干个8位字节进行存储
- class文件钟有两种数据类型,分别是五符号数和表
- 高字节存在低地址成为大端,低字节存在高地址成为小端

## 魔数 0xCAFEBABE

1. 没有这个魔数,编译就会报一个异常,magic value
2. class文件版本魔数每个版本都有一个不同数字

## 常量池

1. javap -verbose XXX.class使用这条命令可以打印出class的一些常量池以及其他的信息
2. 主要存放两大类常量
   1. 字面量
   2. 符号引用,属于编译原理方面的概念
      1. 类和接口的全限定名
      2. 字段的名称和描述符
      3. 方法的名称和描述符

## 访问标志(access_flags)

1. 比如public , private,final这些访问标志,在class字节码中是以

1. 类索引,父类索引,接口索引集合

   这三项数据确定这个类的继承关系

## 字段表集合

1. 字段的作用域(public private protected ),实例变量还是类变量(static),可变性(final),并发可见性(volatile),能否被序列化(transient),class文件中用标志位表示'
2. 字段的数据类型,字段名称.这些属性则引用常量池中的常量来描述
3. 另外字段表集合不会列出从父类或者父接口中继承而来的字段

## 方法表集合

1. 访问标志(access_flags),名称索引(name_index),描述符索引(descriptor_index),属性表集合(attributes)
2. 而方法里面的代码则统一放到code属性里面

## 属性表集合

1. code java的程序方法体
2. exceptions 作用是列举出方法中可能抛出的受查异常,也就是方法描述是在throws关键字后面列举的异常
3. LineNumberTable用于描述Java源码行号于字节码行号之间的对应关系,非必须
4. LocalVariableTable 用于描述栈帧中局部变量表中的变量于Java源码定义的变量之间关系
5. sourceFile是用于记录生成这个class文件的源码文件名称
6. constantValue 是用于通知虚拟机自动为静态变量赋值
7. InnerClasses 用于记录内部类于宿主类之间的关联
8. Deprecated及Syncthetic 
   1. Deprecated 用于表示某个类,字段或者方法已经被作者定义为不再推荐使用
   2. Syncthetic 代表次字段或者方法不是有java源码直接产生的,而是有编译器自行添加的
9. StackMapTable 一个复杂的变长属性,位于code属性的属性表
10. signature 用于记录泛型签名信息
11. BootstrapMethods用于保存invokedDynamic指令引用的引导方法限定符

## 字节码指令简介

java虚拟机的指令由一个字节长度的,代表这某种特定操作含义的数字(操作码),以及跟随其后的零至多个代表此操作所需的参数(操作数)构成

## 字节码与数据类型

java虚拟机中大多数指令都包含了其操作所对应的数据类型信息

## 





