package java.util.concurrent.atomic;
import java.util.function.IntUnaryOperator;
import java.util.function.IntBinaryOperator;
import sun.misc.Unsafe;


public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;

    // 設置為使用Unsafe.compareAndSwapInt進行更新
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;//value值偏移量




	/**
     * Unsafe类是用来在任意内存地址位置处读写数据，可见，对于普通用户来说，使用起来还是比较危险的。
     * public native long objectFieldOffset(Field var1);方法用于获取某个字段相对Java对象的“起始地址”的偏移量，方法返回值和参数如下
     * AtomicInteger.class.getDeclaredField("value")是拿到atomicInteger的value字段的field对象
     * valueoffset是拿到value的相对于AtomicInteger对象的地址偏移量
     * 
     */
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }



	/*
	*这里的int value就是atomicInteger 的值下面的操作都是围绕value展开
	*/
    private volatile int value;

    /**构造方法
     * 使用給定的初始值創建新的AtomicInteger。即value = initialValue;
     *
     * @param 传入初始值
     */
    public AtomicInteger(int initialValue) {
        value = initialValue;
    }




    /**
     * 創建一個初始值為0的新AtomicInteger实例。即value=0;
     */
    public AtomicInteger() {
    }





    /**
     * 獲取當前值。
     *
     * @return 當前的價值
     */
    public final int get() {
        return value;
    }




    /**
     * 設置為給定值。
     *
     * @param newValue 新值
     */
    public final void set(int newValue) {
        value = newValue;
    }




    /**
     * 最終設置為給定值。
     *
     * @param newValue the new value
     * @since 1.6
     */
    public final void lazySet(int newValue) {
        unsafe.putOrderedInt(this, valueOffset, newValue);
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
     * 原子级设置为给定值并返回旧值。
     *
     * @param newValue 新值
     * @return 之前的值
     */
    public final int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }
    /**
     * unsafe.getAndSetInt(this, valueOffset, newValue);的方法代码如下
     * public final int getAndSetInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);//getIntVolatile方法获取对象中offset偏移地址对应的整型field的值,支持volatile load语义。
        } while(!this.compareAndSwapInt(var1, var2, var5, var4));//var1 是传进来的对象，long var2 是字段偏移值，int var5 是旧值，var4 是新值，var1和var2相当于当前内存的值当内存的值与旧值相等时，函数才能返回true，cas算法实现这个无锁操作

        return var5;//var5 是传进来的旧值，所以上面的函数说设置定值并返回旧值
    }
     */
    



    /**
     * 以原子方式将值设置为给定的更新值
     * 如果当前值@code ==的预期值。
     *
     * @param expect 预期值
     * @param update 新值
     * @return 成功返回true 返回false表明实际值不等于预期值，设置失败
     */
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * 以原子方式将值设置为给定的更新值
     * 如果当前值@code ==的预期值。
     *
     * <p><a href="package-summary.html#weakCompareAndSet"可能会失败
     * 虚假并且不提供订购保证</a>, 也是
     * 很少是一个合适的替代品 {@code compareAndSet}.
     * 该方法可能可能虚假的失败并且不会提供一个排序的保证，所以它在极少的情况下用于代替compareAndSet方法。
     *
     * @param expect 期望值
     * @param update 新值
     * @return {@code true} if successful
     */
    public final boolean weakCompareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }
    /**
     * 一个巨大的问题：weakCompareAndSet 和 compareAndSet 之间有什么区别？
     * 看源码的doc是这样子解释的： 该方法可能可能虚假的失败并且不会提供一个排序的保证，所以它在极少的情况下用于代替compareAndSet方法。
     * 从jdk 8 的官方文档的java.util.concurrent.atomic上找到这么二段话
     * （原文）The atomic classes also support method weakCompareAndSet, which has limited applicability. 
     * On some platforms, the weak version may be more efficient than compareAndSet in the normal case, but differs in that any given invocation of the weakCompareAndSet method may return false spuriously (that is, for no apparent reason). 
     * A false return means only that the operation may be retried if desired, relying on the guarantee that repeated invocation when the variable holds expectedValue and no other thread is also attempting to set the variable will eventually succeed.
     *  (Such spurious failures may for example be due to memory contention effects that are unrelated to whether the expected and current values are equal.) Additionally weakCompareAndSet does not provide ordering guarantees that are usually needed for synchronization control. 
     *  However, the method may be useful for updating counters and statistics when such updates are unrelated to the other happens-before orderings of a program. When a thread sees an update to an atomic variable caused by a weakCompareAndSet, it does not necessarily see updates to any other variables that occurred before the weakCompareAndSet. 
     *  This may be acceptable when, for example, updating performance statistics, but rarely otherwise.
     *  （译文）一个原子类也支持weakCompareAndSet方法，该方法有适用性的限制。在一些平台上，在正常情况下weak版本比compareAndSet更高效，
     *  但是不同的是任何给定的weakCompareAndSet方法的调用都可能会返回一个虚假的失败( 无任何明显的原因 )。一个失败的返回意味着，操作将会重新执行如果需要的话，
     *  重复操作依赖的保证是当变量持有expectedValue的值并且没有其他的线程也尝试设置这个值将最终操作成功。( 一个虚假的失败可能是由于内存冲突的影响，而和预期值(expectedValue)和当前的值是否相等无关 )。
     *  此外weakCompareAndSet并不会提供排序的保证，即通常需要用于同步控制的排序保证。然而，这个方法可能在修改计数器或者统计，这种修改无关于其他happens-before的程序中非常有用。
     *  当一个线程看到一个通过weakCompareAndSet修改的原子变量时，它不被要求看到其他变量的修改，即便该变量的修改在weakCompareAndSet操作之前。
     *  （原文）weakCompareAndSet atomically reads and conditionally writes a variable but does not create any happens-before orderings, so provides no guarantees with respect to previous or subsequent reads and writes of any variables other than the target of the weakCompareAndSet.
     *  （译文）weakCompareAndSet实现了一个变量原子的读操作和有条件的原子写操作，但是它不会创建任何happen-before排序，
     *  所以该方法不提供对weakCompareAndSet操作的目标变量以外的变量的在之前或在之后的读或写操作的保证。
     *  
     */




    /**
     * 原子上增加一个当前值。
     *
     * @return 之前的值
     */
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }
    /**getAndAddInt函数的方法体，传进来的var4是1，每调用一次增加1.compareAndSwapInt前面解释过了
     *public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

        return var5;
    }
 
     */

    /**
     * 原子地将当前值减1。
     *
     * @return 之前的值
     */
    public final int getAndDecrement() {
        return unsafe.getAndAddInt(this, valueOffset, -1);
    }
    //和上面一样，传进去的var4是-1，所以每次调用减1

    /**
     * 原子上将给定值添加到当前值。
     *
     * @param delta 添加值
     * @return 旧值
     */
    public final int getAndAdd(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta);
    }
    //和上面一样，但是这里传进去的var4是delta而已




    /**
     * 原子上当前值加1。
     *
     * @return 新值
     */
    public final int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }
    //前面说getAndAddInt返回的是之前的值，调用这个函数把这个值+1，返回+1前的值之后+1,就等于现在的值


    /**
     * 原子地将当前值减1。
     *
     * @return 新值
     */
    public final int decrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
    }
    //还是一样。换成-1.变成减而已



    /**
     * 原子上将给定值添加到当前值。
     *
     * @param delta 添加值
     * @return 新值
     */
    public final int addAndGet(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta) + delta;
    }
    //还是一样。换成delta值




    /**
     * 用应用给定函数的结果原子地更新当前值，返回以前的值。 该函数应该是无副作用的，因为当尝试的更新由于线程之间的争用而失败时，它可能会被重新应用。
     *
     * @param updateFunction 无副作用的功能
     * @return 旧值
     * @since 1.8新特性诶
     */
    public final int getAndUpdate(IntUnaryOperator updateFunction) {
        int prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsInt(prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }
    /**
     * updateFunction.applyAsInt(prev);中使用了函数式编程
     * 通过函数式编程，可以灵活的实现更加复杂的原子操作。
     * # 清单5 IntUnaryOperator接口
            int applyAsInt(int operand);

            default IntUnaryOperator compose(IntUnaryOperator before) {
                Objects.requireNonNull(before);
                return (int v) -> applyAsInt(before.applyAsInt(v));
            }

            default IntUnaryOperator andThen(IntUnaryOperator after) {
                Objects.requireNonNull(after);
                return (int t) -> after.applyAsInt(applyAsInt(t));
            }
        该接口定义了一个待实现方法和两个默认方法，通过compose和andThen即可实现多个IntUnaryOperator的组合调用。
     */
    



    /**
     *用应用给定函数的结果原子地更新当前值，返回以前的值。 该函数应该是无副作用的，因为当尝试的更新由于线程之间的争用而失败时，它可能会被重新应用。
     *
     * @param updateFunction a side-effect-free function
     * @return the updated value
     * @since 1.8
     */
    public final int updateAndGet(IntUnaryOperator updateFunction) {
        int prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsInt(prev);
        } while (!compareAndSet(prev, next));
        return next;
    }
    /**
     * 老问题，updateandget和getandupdate的区别
     * 看return GetAndUpdate返回旧值，updateAndGet返回新值，
     */




    /**
     * Atomically updates the current value with the results of
     * applying the given function to the current and given values,
     * returning the previous value. The function should be
     * side-effect-free, since it may be re-applied when attempted
     * updates fail due to contention among threads.  The function
     * is applied with the current value as its first argument,
     * and the given update as the second argument.
     *
     * @param x the update value
     * @param accumulatorFunction a side-effect-free function of two arguments
     * @return the previous value
     * @since 1.8
     */
    public final int getAndAccumulate(int x,
                                      IntBinaryOperator accumulatorFunction) {
        int prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsInt(prev, x);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    /**
     * 将给定函数应用于当前值和给定值的结果以原始方式更新当前值，并返回以前的值。 该函数应该是无副作用的，因为当尝试的更新由于线程之间的争用而失败时，它可能会被重新应用。 该函数以当前值作为第一个参数，给定更新作为第二个参数应用。
     *
     * @param x the update value
     * @param accumulatorFunction a side-effect-free function of two arguments
     * @return the updated value
     * @since 1.8
     */
    public final int accumulateAndGet(int x,
                                      IntBinaryOperator accumulatorFunction) {
        int prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsInt(prev, x);
        } while (!compareAndSet(prev, next));
        return next;
    }



    //下面的这些没什么好说的get（） set（）之类的

    /**
     * Returns the String representation of the current value.
     * @return the String representation of the current value
     */
    public String toString() {
        return Integer.toString(get());
    }

    /**
     * Returns the value of this {@code AtomicInteger} as an {@code int}.
     */
    public int intValue() {
        return get();
    }

    /**
     * Returns the value of this {@code AtomicInteger} as a {@code long}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public long longValue() {
        return (long)get();
    }

    /**
     * Returns the value of this {@code AtomicInteger} as a {@code float}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public float floatValue() {
        return (float)get();
    }

    /**
     * Returns the value of this {@code AtomicInteger} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public double doubleValue() {
        return (double)get();
    }

}
