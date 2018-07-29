# atomicLong源码分析详解

## atomicLong的字段和实例方法

![1530686380753](C:\Users\13441\Desktop\md\并发编程\atomicLong源码分析详解.assets\1530686380753.png)

## 源码分析

### 注意：源码分析都放在源码里面

```
package java.util.concurrent.atomic;
import java.util.function.LongUnaryOperator;
import java.util.function.LongBinaryOperator;
import sun.misc.Unsafe;

/**
一个long值可以用原子更新。 有关原子变量属性的描述，请参阅java.util.concurrent.atomic包规范。 
一个AtomicLong用于诸如原子增量的序列号的应用中，不能用作Long的替代物 。 但是，该类确实扩展了Number ，以允许使用基于数字类的工具和实用程序的统一访问。 
从以下版本开始： 
1.5 
另请参见： 
Serialized Form 
 * @since 1.5
 * @author Doug Lea
 */
public class AtomicLong extends Number implements java.io.Serializable {
/*serialVersionUID 有什么作用？该如何使用？
serialVersionUID 是实现 Serializable 接口而来的，而 Serializable 则是应用于Java 对象序列化/反序列化。对象的序列化主要有两种用途:
1. 把对象序列化成字节码，保存到指定介质上(如磁盘等)
2. 用于网络传输
详情看下面参考文献1  
这是链接https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/what-is-a-serialversionuid-and-why-should-i-use-it.md
 */    
    private static final long serialVersionUID = 1927816293512124184L;

    // 设置为使用Unsafe.compareAndSwapInt进行更新
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

/*
记录底层JVM是否支持longs的无锁compareAndSwap。 
虽然Unsafe.compareAndSwapLong方法在任何一种情况下都可以工作，
但是应该在Java级别处理一些构造以避免锁定用户可见的锁。
 */    
    static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();

/*
返回底层JVM是否支持longs的无锁CompareAndSet。 仅调用一次并缓存在VM_SUPPORTS_LONG_CAS中。
 */
    private static native boolean VMSupportsCS8();

/*  
下面这段静态代码块是用于获取valueOffset
 */
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicLong.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

//  这个就是AtomicBoolean的关键值，value
    private volatile long value;

    /**
     * 用给定的初始值创建一个新的AtomicLong
     * @param initialValue initialValue - 初始值 
     */
    public AtomicLong(long initialValue) {
        value = initialValue;
    }

    /**
     * 创建一个新的AtomicLong，初始值为 0 。 
     */
    public AtomicLong() {
    }



    /**
     * 获取当前值。 
     *
     * @return 当前值 
     */
    public final long get() {
        return value;
    }

    /**
     * 设置为给定值。
     *
     * @param newValue newValue - 新价值 
     */
    public final void set(long newValue) {
        value = newValue;
    }

    /**
     * 最终设定为给定值。 
     *
     * @param newValue newValue - 新价值 
     * @since 1.6
     */
    public final void lazySet(long newValue) {
        unsafe.putOrderedLong(this, valueOffset, newValue);
    }
/**
 * 这里抛出一个疑问？到底set（）和lazySet（）有什么区别？
 * 1.首先set()是对volatile变量的一个写操作, 我们知道volatile的write
 * 为了保证对其他线程的可见性会追加以下两个Fence(内存屏障)如下：
 *   1.StoreStore 在intel cpu 不存在[写写]重排序
 *   2.StoreLoad 这个是所有内存屏障里最耗性能的内存屏障相关参考Doug Lea大大的cookbook (http://g.oswego.edu/dl/jmm/cookbook.html)
 * Doug Lea大大又说了, lazySet()省去了StoreLoad屏障, 只留下StoreStore
 * 总结：set()和volatile具有一样的效果(能够保证内存可见性，能够避免指令重排序)，
 * 但是使用lazySet不能保证其他线程能立刻看到修改后的值(有可能发生指令重排序)。
 * 简单点理解：lazySet比set()具有性能优势，但是使用场景很有限。
 * 在网上没有找到lazySet和set的性能数据对比，
 * 而且CPU的速度很快的，应用的瓶颈往往不在CPU，
 * 而是在IO、网络、数据库等。对于并发程序要优先保证正确性，
 * 然后出现性能瓶颈的时候再去解决。因为定位并发导致的问题，往往要比定位性能问题困难很多。
 */


    /**
     * 将原子设置为给定值并返回旧值。
     * @param newValue newValue - 新的价值 
     * @return 以前的值 
     */
    public final long getAndSet(long newValue) {
        return unsafe.getAndSetLong(this, valueOffset, newValue);
    }
/*
    如下，实际unsafe.getAndSetLong(this, valueOffset, newValue),还是使用compareAndSwapLong这个本地方法。什么是本地方法 native method？
简单地讲，一个Native Method就是一个java调用非java代码的接口。一个Native Method是这样一个java的方法：该方法的实现由非java语言实现，比如C。这个特征并非java所特有，很多其它的编程语言都有这一机制，比如在C＋＋中，你可以用extern “C”告知C＋＋编译器去调用一个C的函数。 “A native method is a Java method whose implementation is provided by non-java code.” 在定义一个native method时，并不提供实现体（有些像定义一个java interface），因为其实现体是由非java语言在外面实现的。

    public final long getAndSetLong(Object var1, long var2, long var4) {
        long var6;
        do {
            var6 = this.getLongVolatile(var1, var2);
        } while(!this.compareAndSwapLong(var1, var2, var6, var4));

        return var6;
    }
CAS 
CAS算法的过程是这样：它包含3个参数CAS(V,E,N)。V表示要更新的变量，E表示预期值，N表示新值。仅当V 
值等于E值时，才会将V的值设为N，如果V值和E值不同，则说明已经有其他线程做了更新，则当前线程什么 
都不做。最后，CAS返回当前V的真实值。CAS操作是抱着乐观的态度进行的，它总是认为自己可以成功完成 
操作。当多个线程同时使用CAS操作一个变量时，只有一个会胜出，并成功更新，其余均会失败。失败的线程 
不会被挂起，仅是被告知失败，并且允许再次尝试，当然也允许失败的线程放弃操作。基于这样的原理，CAS 
操作即时没有锁，也可以发现其他线程对当前线程的干扰，并进行恰当的处理。


 */    

    /**
     * 如果当前值为 ==为预期值，则将该值原子设置为给定的更新值。 
     *
     * @param expect expect - 预期值
     * @param update update - 新值 
     * @return true如果成功 

     */
    public final boolean compareAndSet(long expect, long update) {
        return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    }
/*
这里是直接调用unsafe.compareAndSwapLong();CAS操作如上面所说
 */    

    /**
     * 如果当前值为== ，则将原值设置为给定的更新值。 May fail spuriously and does not provide ordering guarantees ，所以只是很少适合替代compareAndSet 。 
     *
     * @param expect expect - 预期值 
     * @param update update - 新值 
     * @return true如果成功 
     */
    public final boolean weakCompareAndSet(long expect, long update) {
        return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    }
    /*
    延续上面的提问，为什么可能会失败？这里给我跳转到jdk 的javadoc，我把原文发出来，里面很详细，这里简单描述一下
    这里是链接：https://blog.csdn.net/qq_37933685/article/details/80888972
    一个原子类也支持weakCompareAndSet方法，该方法有适用性的限制。在一些平台上，在正常情况下weak版本比compareAndSet更高效，
    但是不同的是任何给定的weakCompareAndSet方法的调用都可能会返回一个虚假的失败( 无任何明显的原因 )。一个失败的返回意味着，操作将会重新执行如果需要的话，
    重复操作依赖的保证是当变量持有expectedValue的值并且没有其他的线程也尝试设置这个值将最终操作成功。( 一个虚假的失败可能是由于内存冲突的影响，而和预期值(expectedValue)和当前的值是否相等无关 )。
    此外weakCompareAndSet并不会提供排序的保证，即通常需要用于同步控制的排序保证。然而，这个方法可能在修改计数器或者统计，这种修改无关于其他happens-before的程序中非常有用。
    当一个线程看到一个通过weakCompareAndSet修改的原子变量时，它不被要求看到其他变量的修改，即便该变量的修改在weakCompareAndSet操作之前。
    weakCompareAndSet实现了一个变量原子的读操作和有条件的原子写操作，但是它不会创建任何happen-before排序，
    所以该方法不提供对weakCompareAndSet操作的目标变量以外的变量的在之前或在之后的读或写操作的保证。
     */    

    /**
     * 原子上增加一个当前值。
     * @return 以前的值 
     */
    public final long getAndIncrement() {
        return unsafe.getAndAddLong(this, valueOffset, 1L);
    }
/*
public final long getAndAddLong(Object var1, long var2, long var4) {
        long var6;
        do {
            var6 = this.getLongVolatile(var1, var2);
        } while(!this.compareAndSwapLong(var1, var2, var6, var6 + var4));

        return var6;
    }
public native long getLongVolatile(Object var1, long var2);//使用本地方法，取的相对这个对象var1的偏移地址为var2的值，简单来说就是拿到value字段的值
public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6);//CAS操作保证原子性

 */


    /**
     * 原子减1当前值。
     *
     * @return 以前的值 
     */
    public final long getAndDecrement() {
        return unsafe.getAndAddLong(this, valueOffset, -1L);
    }
/*
原理和上面一样，数值变成-1
 */

    /**
     * 将给定的值原子地添加到当前值。 
     *
     * @param delta delta - 要添加的值 
     * @return 以前的值 
     */
    public final long getAndAdd(long delta) {
        return unsafe.getAndAddLong(this, valueOffset, delta);
    }
/*
原理和上面一样，数值变成delta
 */    

    /**
     * 原子上增加一个当前值。
     *
     * @return 更新的值
     */
    public final long incrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
    }

/*
原理和上面一样，数值变成1;返回时旧值+1就是新值，所以返回是新值
 */    


    /**
     * 原子减1当前值。 
     *
     * @return 更新的值 
     */
    public final long decrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffset, -1L) - 1L;
    }
/*
原理和上面一样，数值变成-1;返回时旧值-1就是新值，所以返回是新值
 */    

    /**
     * 将给定的值原子地添加到当前值。 
     *
     * @param delta delta - 要添加的值 
     * @return 更新的值 
     */
    public final long addAndGet(long delta) {
        return unsafe.getAndAddLong(this, valueOffset, delta) + delta;
    }
/*
原理和上面一样，数值变成delta;返回时旧值+delta就是新值，所以返回是新值
 */    

    /**
     * 用应用给定函数的结果原子更新当前值，返回上一个值。 
     * 该功能应该是无副作用的，因为尝试的更新由于线程之间的争用而失败时可能会被重新应用。
     *
     * @param updateFunction updateFunction - 无副作用的功能
     * @return 以前的值 
     * @since 1.8
     */
    public final long getAndUpdate(LongUnaryOperator updateFunction) {
        long prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsLong(prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }
    /**
     * 使用给定函数的结果原子更新当前值，返回更新的值。 该功能应该是无副作用的，因为尝试的更新由于线程之间的争用而失败时可能会被重新应用。
     * @param updateFunction updateFunction - 无副作用的功能 
     * @return 更新的值 
     * @since 1.8
     */
    public final long updateAndGet(LongUnaryOperator updateFunction) {
        long prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsLong(prev);
        } while (!compareAndSet(prev, next));
        return next;
    }

    /**
     * 使用给定函数应用给当前值和给定值的结果原子更新当前值，返回上一个值。
     * 该功能应该是无副作用的，因为尝试的更新由于线程之间的争用而失败时可能会被重新应用。 该函数应用当前值作为其第一个参数，给定的更新作为第二个参数。
     *
     * @param x x - 更新值 
     * @param accumulatorFunction accumulatorFunction - 两个参数的无效副作用 
     * @return 以前的值 
     * @since 1.8
     */
    public final long getAndAccumulate(long x,
                                       LongBinaryOperator accumulatorFunction) {
        long prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsLong(prev, x);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    /**
     * 使用将给定函数应用于当前值和给定值的结果原子更新当前值，返回更新后的值。 
     * 该功能应该是无副作用的，因为尝试的更新由于线程之间的争用而失败时可能会被重新应用。 该函数应用当前值作为其第一个参数，给定的更新作为第二个参数。
     *
     * @param x x - 更新值 
     * @param accumulatorFunction accumulatorFunction - 两个参数的无效副作用 
     * @return the updated value
     * @since 1.8
     */
    public final long accumulateAndGet(long x,
                                       LongBinaryOperator accumulatorFunction) {
        long prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsLong(prev, x);
        } while (!compareAndSet(prev, next));
        return next;
    }

/*
以上的getAndUpdate(LongUnaryOperator updateFunction)；updateAndGet(LongUnaryOperator updateFunction);getAndAccumulate(long x,LongBinaryOperator accumulatorFunction)；accumulateAndGet(long x,LongBinaryOperator accumulatorFunction)
四个方法都是用同样的原理；
prev = get();获取当前的value字段的值
next = updateFunction.applyAsLong(prev);updateFunction - 无副作用的功能;这是java8 的新特性。lz还不会。
总体来讲：这个方法就是去那当前的值，拿到当前值并返回当前值，但是有一点就是保证了返回的时候值还是那个值，没有被改变，试想就get()拿到了value，但是再多线程的转态可能会被改变，一旦改变了，下面的CAS操作就会失败；
只有CAS操作成功了就返回值，说明get()之后，value值没有被其他线程改变，这是乐观锁的思想
 */ 

    /**
     * Returns the String representation of the current value.
     * @return the String representation of the current value
     */
    public String toString() {
        return Long.toString(get());
    }

    /**
     * Returns the value of this {@code AtomicLong} as an {@code int}
     * after a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public int intValue() {
        return (int)get();
    }

    /**
     * Returns the value of this {@code AtomicLong} as a {@code long}.
     */
    public long longValue() {
        return get();
    }

    /**
     * Returns the value of this {@code AtomicLong} as a {@code float}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public float floatValue() {
        return (float)get();
    }

    /**
     * Returns the value of this {@code AtomicLong} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public double doubleValue() {
        return (double)get();
    }

}

```

## 总结:

1. lz的Java8新特性掌握不好，看完这个我要去看看Java8的新特性了
2. 下一步就对unsafe这个直接操作内存的类做一下分析