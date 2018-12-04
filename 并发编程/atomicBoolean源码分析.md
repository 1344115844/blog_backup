# atomicBoolean源码分析

## 注意：解析都放在源码里面

## 源码

```
package java.util.concurrent.atomic;
import sun.misc.Unsafe;

/**
一个boolean值可以用原子更新。 有关原子变量属性的描述，请参阅java.util.concurrent.atomic包规范。 一个AtomicBoolean用于诸如原子更新标志的应用程序，不能用作替代Boolean 。 
从以下版本开始： 
1.5 
另请参见： 
Serialized Form 
 *
 * @since 1.5
 * @author Doug Lea
 */
public class AtomicBoolean implements java.io.Serializable {

    private static final long serialVersionUID = 4654671469794556979L;
/*serialVersionUID 有什么作用？该如何使用？
serialVersionUID 是实现 Serializable 接口而来的，而 Serializable 则是应用于Java 对象序列化/反序列化。对象的序列化主要有两种用途:
1. 把对象序列化成字节码，保存到指定介质上(如磁盘等)
2. 用于网络传输
详情看下面参考文献1  
这是链接https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/what-is-a-serialversionuid-and-why-should-i-use-it.md
 */

    // 设置为使用Unsafe.compareAndSwapInt进行更新
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    // valueOffset 是 value这个字段的相对于atomicBoolean这个对象的偏移量
    private static final long valueOffset;

//  下面这段静态代码块是用于获取valueOffset
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicBoolean.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

//  这个就是AtomicBoolean的关键值，value
    private volatile int value;



    /**构造方法
     * 用给定的初始值创建一个新的 AtomicBoolean 。 
     *
     *参数 initialValue - 初始值 
     */
    public AtomicBoolean(boolean initialValue) {
        value = initialValue ? 1 : 0;
    }

    /**构造方法
     * 创建一个新的 AtomicBoolean ，初始值为 false 。
     */
    public AtomicBoolean() {
    }



    /**
     * 返回当前值
     *
     * @return 当前值 
     */
    public final boolean get() {
        return value != 0;
    }
    /*
    这里的返回时（value！=0）的布尔值
    为什么初始值为false是？
    value的值为0，value=0，所以（value！=0）为false，所以初始值为false
     */
    


    /**
     * 如果当前值为 ==的预期值，则将该值原子设置为给定的更新值。 
     *
     * @param expect expect - 预期值 
     * @param update update - 新的价值
     * @return true如果成功 
     */
    public final boolean compareAndSet(boolean expect, boolean update) {
        int e = expect ? 1 : 0;
        int u = update ? 1 : 0;
        return unsafe.compareAndSwapInt(this, valueOffset, e, u);
    }
    /*
    这里直接用unsafe类的CAS操作改变值，关于unsafe类的说明：
     * Unsafe类是用来在任意内存地址位置处读写数据，可见，对于普通用户来说，使用起来还是比较危险的。
     * 关于CAS算法的说明：https://blog.csdn.net/qq_37933685/article/details/80871395
     */
    


    /**
     * 如果当前值为==为预期值，则将该值原子设置为给定的更新值。 可能会失败，所以只是很少适合替代compareAndSet 。 

     */
    public boolean weakCompareAndSet(boolean expect, boolean update) {
        int e = expect ? 1 : 0;
        int u = update ? 1 : 0;
        return unsafe.compareAndSwapInt(this, valueOffset, e, u);
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
     * 无条件地设置为给定的值。
     *
     * @param newValue newValue - 新的价值 
     */
    public final void set(boolean newValue) {
        value = newValue ? 1 : 0;
    }




    /**
     * 最终设定为给定值。
     *
     * @param newValue newValue - 新的价值
     * @since 1.6
     */
    public final void lazySet(boolean newValue) {
        int v = newValue ? 1 : 0;
        unsafe.putOrderedInt(this, valueOffset, v);
    }



    /**
     * 将原子设置为给定值并返回上一个值。
     *
     * @param newValue newValue - 新值 
     * @return 以前的值 
     */
    public final boolean getAndSet(boolean newValue) {
        boolean prev;
        do {
            prev = get();
        } while (!compareAndSet(prev, newValue));
        return prev;
    }

    /**
     * 返回当前值的String表示形式。 
     * 重写： toString在类别 Object 
     * @return the String representation of the current value
     */
    public String toString() {
        return Boolean.toString(get());
    }

}

```



## 参考文献

https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/what-is-a-serialversionuid-and-why-should-i-use-it.md