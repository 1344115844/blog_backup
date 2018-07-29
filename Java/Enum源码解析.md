# Enum源码解析

## 引言

枚举类型是 JDK 5 之后引进的一种非常重要的引用类型，可以用来定义一系列枚举常量。

在没有引入 enum 关键字之前，要表示可枚举的变量，只能使用 `public static final` 的方式。

```
public staic final int SPRING = 1;
public staic final int SUMMER = 2;
public staic final int AUTUMN = 3;
public staic final int WINTER = 4;
```

这种实现方式有几个弊端。首先，类型不安全。试想一下，有一个方法期待接受一个季节作为参数，那么只能将参数类型声明为 int，但是传入的值可能是 99。显然只能在运行时进行参数合理性的判断，无法在编译期间完成检查。其次，指意性不强，含义不明确。我们使用枚举，很多场合会用到该枚举的字串符表达，而上述的实现中只能得到一个数字，不能直观地表达该枚举常量的含义。当然也可用 String 常量，但是又会带来性能问题，因为比较要依赖字符串的比较操作。

使用 enum 来表示枚举可以更好地保证程序的类型安全和可读性。

enum 是类型安全的。除了预先定义的枚举常量，不能将其它的值赋给枚举变量。这和用 int 或 String 实现的枚举很不一样。

enum 有自己的名称空间，且可读性强。在创建 enum 时，编译器会自动添加一些有用的特性。每个 enum 实例都有一个名字 (name) 和一个序号 (ordinal)，可以通过 toString() 方法获取 enum 实例的字符串表示。还以通过 values() 方法获得一个由 enum 常量按顺序构成的数组。

enum 还有一个特别实用的特性，可以在 switch 语句中使用，这也是 enum 最常用的使用方式了。

### 反编译枚举类型源码



## 源码解析

### 注意：解析是放再源码里面的注释

### 环境：jdk1.8

### Enum的类图

![1530890633959](C:\Users\13441\Desktop\md\Java\Enum源码解析.assets\1530890633959.png)

```
package java.lang;

import java.io.Serializable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;

/*
声明方法的描述，请参见The Java™ Language Specification的第8.9 节 。 
请注意，当使用枚举类型作为集合的类型或映射中的键的类型时，可以使用专门且高效的set和map实现。 

从以下版本开始： 
1.5 
另请参见： 
Class.getEnumConstants() ， EnumSet ， EnumMap ， Serialized Form 
 */
public abstract class Enum<E extends Enum<E>>
        implements Comparable<E>, Serializable {
/*
枚举常量的名称，如枚举声明中声明的那样。 
大多数程序员应该使用toString方法而不是访问此字段。
返回：
      枚举常量的名称
 */            
    private final String name;

/*
返回此枚举常量的名称，与其枚举声明中声明的完全相同。 
大多数程序员应优先使用toString方法，因为toString方法可能返回一个更加用户友好的名称。 
方法主要用于特殊情况，
其中正确性取决于获取确切名称，该名称在不同版本之间不会有所不同。 这个枚举常量的名称
 */
    public final String name() {
        return name;
    }

/*
此枚举常量的序数（它在枚举声明中的位置，其中初始常量的序数为零）。 
大多数程序员都不会使用这个领域。 它设计用于复杂的基于枚举的数据结构，、
例如java.util.EnumSet和java.util.EnumMap。
 */    
    private final int ordinal;

/*
返回此枚举常量的序数（它在枚举声明中的位置，其中初始常量的序数为零）。 
大多数程序员都没有使用这种方法。 
它设计用于复杂的基于枚举的数据结构，例如java.util.EnumSet和java.util.EnumMap 
@return此枚举常量的序数
 */    
    public final int ordinal() {
        return ordinal;
    }

    /**
     * 唯一的构造函数。 程序员无法调用此构造函数。 
     * 它由编译器发出的代码用于响应枚举类型声明。
     *
     * @param name - 此枚举常量的名称，它是用于声明它的标识符。
     * @param ordinal - 此枚举常量的序数（它在枚举声明中的位置，其中初始常量的序数为零）。
     */
    protected Enum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    /**
     * 返回声明中包含的此枚举常量的名称。 可以覆盖该方法，但通常不需要或不需要。
     * 当存在更“程序员友好”的字符串形式时，枚举类型应该重写此方法。
     *
     * @return 这个枚举常量的名称
     */
    public String toString() {
        return name;
    }

    /**
     * 如果指定的对象等于此枚举常量，则返回true。
     *
     * @param other 要与此对象进行相等性比较的对象。
     * @return  如果指定的对象等于此枚举常量，则返回true。
     */
    public final boolean equals(Object other) {
        return this==other;
    }

    /**
     * 返回此枚举常量的哈希码。
     *
     * @return 此枚举常量的哈希码。
     */
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * 抛出CloneNotSupportedException。 
     * 这保证了枚举永远不会被克隆，这是保持其“单身”状态所必需的。
     *
     * @return (never returns)
     */
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * 将此枚举与指定的订单对象进行比较。 
     * 返回负整数，零或正整数，因为此对象小于，等于或大于指定对象。
     *
     * 枚举常量仅与同一枚举类型的其他枚举常量相当。 
     * 此方法实现的自然顺序是声明常量的顺序。
     */
    public final int compareTo(E o) {
        Enum<?> other = (Enum<?>)o;
        Enum<E> self = this;
        if (self.getClass() != other.getClass() && // optimization
            self.getDeclaringClass() != other.getDeclaringClass())
            throw new ClassCastException();
        return self.ordinal - other.ordinal;
    }

    /**
     * 返回与此枚举常量的枚举类型相对应的Class对象。 
     * 当且仅当e1.getDeclaringClass（）== e2.getDeclaringClass（））时，
     * 两个枚举常量e1和e2具有相同的枚举类型。 
     * （此方法返回的值可能与使用常量特定类体的枚举常数Object.getClass()方法返回的值不同） 
     *
     * @return 该类对象对应于此枚举常量的枚举类型 
     */
    @SuppressWarnings("unchecked")
    public final Class<E> getDeclaringClass() {
        Class<?> clazz = getClass();
        Class<?> zuper = clazz.getSuperclass();
        return (zuper == Enum.class) ? (Class<E>)clazz : (Class<E>)zuper;
    }

    /**
     * 返回具有指定名称的指定枚举类型的枚举常量。 
     * 该名称必须与用于声明此类型的枚举常量的标识符完全一致。 
     * （不允许使用外来空白字符。） 
     *
     * 请注意，对于特定枚举类型T ，
     * 可以使用该枚举上隐式声明的public static T valueOf(String)方法，
     * 而不是使用此方法将名称映射到相应的枚举常量。 
     * 枚举类型的所有常量可以通过调用该类型的隐式public static T[] values()方法来获得。 
     * @param <T> T - 要返回其常量的枚举类型 
     * @param enumType  类返回常量的枚举类型的 类对象 

     * @param name 常量返回的名称 
     * @return 具有指定名称的指定枚举类型的枚举常量
     * @throws IllegalArgumentException 如果指定的枚举类型没有指定名称的常量，或者指定的类对象不表示枚举类型 

     * @throws NullPointerException 如果 enumType或 name为null 

     * @since 1.5
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType,
                                                String name) {
        T result = enumType.enumConstantDirectory().get(name);
        if (result != null)
            return result;
        if (name == null)
            throw new NullPointerException("Name is null");
        throw new IllegalArgumentException(
            "No enum constant " + enumType.getCanonicalName() + "." + name);
    }
    /*
    可以看到，enumType.enumConstantDirectory().get(name);enumType这个枚举类抵用enumConstantDirectory方法
    拿到这个枚举类的map，代码如下。然后根据name拿到这个枚举的对象。
    Map<String, T> enumConstantDirectory() {
        if (enumConstantDirectory == null) {
            T[] universe = getEnumConstantsShared();
            if (universe == null)
                throw new IllegalArgumentException(
                    getName() + " is not an enum type");
            Map<String, T> m = new HashMap<>(2 * universe.length);
            for (T constant : universe)
                m.put(((Enum<?>)constant).name(), constant);
            enumConstantDirectory = m;
        }
        return enumConstantDirectory;
    }
    enumConstantDirectory()方法获取枚举常量目录，没有就继续调用getEnumConstantsShared();如果有返回值，就遍历put进enumConstantDirectory。然后返回。方法说明如下
    getEnumConstantsShared();返回此枚举类的元素，如果此Class对象不表示枚举类型，则返回null; 
    与getEnumConstants相同，但结果是由所有调用者取消克隆，缓存和共享。

     */

    /**
     * 枚举类不能有finalize方法
     */
    protected final void finalize() { }

    /**
     * 防止默认反序列化
     */
    private void readObject(ObjectInputStream in) throws IOException,
        ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize enum");
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize enum");
    }
}

```

### 枚举是如何保证线程安全的

当一个Java类第一次被真正使用到的时候静态资源被初始化、Java类的加载和初始化过程都是线程安全的。所以，**创建一个enum类型是线程安全的**。 

### 为什么用枚举实现的单例是最好的方式

**1. 枚举写法简单** 

**2. 枚举自己处理序列化** 

**3.枚举实例创建是thread-safe(线程安全的)** 

## 参考文献

http://blog.jobbole.com/94074/

http://blog.jrwang.me/2016/java-enum/