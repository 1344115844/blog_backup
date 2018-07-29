# java8新特性

# 1.       lambda表达式

## 1.1.         为什么引入

 

lambda表达式是一个可传递的代码块，可以在以后执行一次或者多次；

优化冗余代码的方式：

\1. 设计模式，java23种设计 模式’

\2. 匿名内部类

\3. lambda表达式

## 1.2.         基础语法

 

java8中引入了一个新的操作符“->”，该操作符被称为箭头操作符，或者lambda操作符，lambda操作符把lambda表达式拆分成两部分

左侧为lambda表达式的参数列表

右侧为lambda表达式中所需的功能，就是lambda体

需要函数式接口的支持

函数式接口的定义：对于只有一个抽象方法的接口，需要这种接口的对象时，就可以提供一个lambda表达式。函数式接口可以使用@FunctionalInterface修饰一下，检查是否

\1. 语法格式1：无参数，无返回值,比如runnable接口

`()-> System.out.println("hello");`

\2. 语法格式2：有一个参数，无返回值。比如`Consumer<T>.accept(T t)`

`Consumer<String> con=(x) -> System.out.println(x);`

\3. 语法格式3：只有一个参数（小括号可以不写），无返回值

`Consumer<String> con=x -> System.out.println(x);`

\4. 语法格式4：两个以上 的参数，有返回值，并且lambda体中有多条语句，比如

```
Comparator<T>

(x,y)->{

System.out.println("hello");

return Integer.compare(x,y);

};
```

\5. 语法格式5,：若lambda体中只有一条语句，return 和大括号都可以省略不写

\6. 语法格式6： lambda表达式的参数列表的数据类型可以省略不写，为什么？因为JVM编译器通过上下文推断出，数据类型 ，“类型推断”

## 1.3.         重要限制

 

\1. 在lambda表达式中，只能引用 ，值不会改变的变量

为什么？

并发不安全

\2. lambda表达式中捕获的变量必须实际上是最终变量。最终变量是指，这个变量初始化之后就不会再为他赋新值

## 1.4.         比较器和监听器使用lambda

```
import java.util.Arrays;

import java.util.Date;

import javax.swing.*;

 /**

 * @author Veng Su 1344114844@qq.com

 * @date 2018/7/9 10:52

 */

public class LambdaTest {

    public static void main(String[] args) {

        String[] plants =new String[]{"hello","ahi","whateeee","whenw"};

        System.out.println(Arrays.toString(plants));

        System.out.println("sorted");

        Arrays.sort(plants);

        System.out.println(Arrays.toString(plants));

        System.out.println("sort by length");

        Arrays.sort(plants,(first,second)->first.length()-second.length());

        System.out.println(Arrays.toString(plants));

 

        Timer t=new Timer(2000,event-> System.out.println("the time is "+new Date()));

        t.start();

 

        JOptionPane.showMessageDialog(null,"quit?");

        System.exit(0);

   }

}
```

 

## 1.5.         方法引用

 

可能已经有现成的方法可以完成你想要传递到其他代码的某个动作

\1. `object::instance method`

   

```
  //对象的引用 :: 实例方法名

     @Test

     public void test2(){

              Employee emp = new Employee(101, "张三", 18, 9999.99);

              

              Supplier<String> sup = () -> emp.getName();

              System.out.println(sup.get());

              

              System.out.println("----------------------------------");

              

              Supplier<String> sup2 = emp::getName;

              System.out.println(sup2.get());

     }
```

 

\2. `class::staticMethod`

​     

```
//类名 :: 静态方法名

     @Test

     public void test4(){

              Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

              

              System.out.println("-------------------------------------");

              

              Comparator<Integer> com2 = Integer::compare;

     }

     

     @Test

     public void test3(){

              BiFunction<Double, Double, Double> fun = (x, y) -> Math.max(x, y);

              System.out.println(fun.apply(1.5, 22.2));

              

              System.out.println("--------------------------------------------------");

              

              BiFunction<Double, Double, Double> fun2 = Math::max;

              System.out.println(fun2.apply(1.2, 1.5));

     }

\3. class::instanceMethod

//类名 :: 实例方法名

     @Test

     public void test5(){

              BiPredicate<String, String> bp = (x, y) -> x.equals(y);

              System.out.println(bp.test("abcde", "abcde"));

              

              System.out.println("-----------------------------------------");

              

              BiPredicate<String, String> bp2 = String::equals;

              System.out.println(bp2.test("abc", "abc"));

              

              System.out.println("-----------------------------------------");

              

              

              Function<Employee, String> fun = (e) -> e.show();

              System.out.println(fun.apply(new Employee()));

              

              System.out.println("-----------------------------------------");
             

              Function<Employee, String> fun2 = Employee::show;

              System.out.println(fun2.apply(new Employee()));

              

     }
```

注意:

\1. 使用这种形式，前提是函数式接口的参数列表以及返回值类型要与lambda表达式中调用的实例方法的参数列表和返回值一致

\2. 若lambda参数列表的第一个参数是实例方法的调用者，二第二个参数是实例方法的参数时，可以使用Class::instanceMethod

 

## 1.6.         构造器引用

 

`class::new`

```
//构造器引用
	@Test
	public void test7(){
		Function<String, Employee> fun = Employee::new;
		
		BiFunction<String, Integer, Employee> fun2 = Employee::new;
	}
	
	@Test
	public void test6(){
		Supplier<Employee> sup = () -> new Employee();
		System.out.println(sup.get());
		
		System.out.println("------------------------------------");
		
		Supplier<Employee> sup2 = Employee::new;
		System.out.println(sup2.get());
	}
```

注意:

\1. 使用这种形式，前提是函数式接口的参数列表以及返回值类型要与lambda表达式中调用的构造方法的参数列表和返回值一致

## 1.7.         数组引用

 

`type[]::new`

```
//数组引用
	@Test
	public void test8(){
		Function<Integer, String[]> fun = (args) -> new String[args];
		String[] strs = fun.apply(10);
		System.out.println(strs.length);
		
		System.out.println("--------------------------");
		
		Function<Integer, Employee[]> fun2 = Employee[] :: new;
		Employee[] emps = fun2.apply(20);
		System.out.println(emps.length);
	}
```



## 1.8.         Java8内置的四大核心函数式接口

### 1.8.1.          `consumer<T>`

 

消费型接口：`void accept(T t);`

```
	//Consumer<T> 消费型接口 :
	@Test
	public void test1(){
		happy(10000, (m) -> System.out.println("你们刚哥喜欢大宝剑，每次消费：" + m + "元"));
	} 
	
	public void happy(double money, Consumer<Double> con){
		con.accept(money);
	}
```



### 1.8.2.          `supplier<T>`

 

供给型接口：`T get();`

 

```
	//Supplier<T> 供给型接口 :
	@Test
	public void test2(){
		List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));
		
		for (Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//需求：产生指定个数的整数，并放入集合中
	public List<Integer> getNumList(int num, Supplier<Integer> sup){
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			Integer n = sup.get();
			list.add(n);
		}
		
		return list;
	}
```



### 1.8.3.          `function<T,R>`

 

函数型接口：`R apply(T t);`

```
	//Function<T, R> 函数型接口：
	@Test
	public void test3(){
		String newStr = strHandler("\t\t\t 我大尚硅谷威武   ", (str) -> str.trim());
		System.out.println(newStr);
		
		String subStr = strHandler("我大尚硅谷威武", (str) -> str.substring(2, 5));
		System.out.println(subStr);
	}
	
	//需求：用于处理字符串
	public String strHandler(String str, Function<String, String> fun){
		return fun.apply(str);
	}
```

### 1.8.4.          `predicate<T>`

 

断言型接口：`boolean test(T t);`

 

```
//Predicate<T> 断言型接口：
	@Test
	public void test4(){
		List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "www", "ok");
		List<String> strList = filterStr(list, (s) -> s.length() > 3);
		
		for (String str : strList) {
			System.out.println(str);
		}
	}

	//需求：将满足条件的字符串，放入集合中
	public List<String> filterStr(List<String> list, Predicate<String> pre){
		List<String> strList = new ArrayList<>();
		
		for (String str : list) {
			if(pre.test(str)){
				strList.add(str);
			}
		}
		
		return strList;
	}
```



# 2.       StreamAPI（流）

## 2.1.         是什么？

 

Stream是数据渠道，用于操作数据源所生成的元素序列

注意：

\1. Stream 自己不会存储元素

\2. Stream 不会改变原对象，相反，他们会返回一个持有结果的新Stream

\3. Stream 操作是延迟执行的，这意味着他们会等到需要结果的时候才执行

 

## 2.2.         Stream的三个操作步骤

 

\1. 创建 Stream

​     1. 可以通过 Collection 系列集合提供的 stream()或 parallelStream()

​              `List<String> list = new ArrayList<>();`

​              `Stream<String> stream = list.stream(); //获取一个顺序流`

​              `Stream<String> parallelStream = list.parallelStream(); //获取一个并行流`

​     2. 通过 Arrays 中的静态方法 stream() 获取数组流

​              `Integer[] nums = new Integer[10];`

​              `Stream<Integer> stream1 = Arrays.stream(nums);`

​     3. 通过 Stream 类的 静态方法 of()

​              `Stream<Integer> stream2 = Stream.of(1,2,3,4,5,6);`

​     4. 创建无限流

​              1.迭代 Stream.iterate(seed,lambda) 

​              `Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);`

​              `stream3.forEach(System.out::println);`

​                                                    

​              2.生成 Sream.generate(Supplier<T>s)

​              `Stream<Double> stream4 = Stream.generate(Math::random).limit(2);`

​              `stream4.forEach(System.out::println);`

\2. 中间操作 

​     1.        筛选与切片

​              filter——接收 Lambda ， 从流中排除某些元素。

​              limit——截断流，使其元素不超过给定数量。

​              skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补

​              distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素

​     2. 映射

​              map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。

​              flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流

​     3.排序

​              sorted()——自然排序

​              sorted(Comparator com)——定制排序

注意：

​     1. 当执行终止操作才执行中间操作。这叫做惰性求值

​     2. 内部迭代：迭代操作由stream api 完成

​     3. 外部迭代：  自己写iterator 迭代

 

\3. 终止操作

​              1. allMatch——检查是否匹配所有元素

​              anyMatch——检查是否至少匹配一个元素

​              noneMatch——检查是否没有匹配的元素

​              findFirst——返回第一个元素

​              findAny——返回当前流中的任意元素

​              count——返回流中元素的总个数

​              max——返回流中最大值

​              min——返回流中最小值

```
List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);
@Test
	public void test1(){
			boolean bl = emps.stream()
				.allMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl);
			
			boolean bl1 = emps.stream()
				.anyMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl1);
			
			boolean bl2 = emps.stream()
				.noneMatch((e) -> e.getStatus().equals(Status.BUSY));
			
			System.out.println(bl2);
	}
	
	@Test
	public void test2(){
		Optional<Employee> op = emps.stream()
			.sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
			.findFirst();
		
		System.out.println(op.get());
		
		System.out.println("--------------------------------");
		
		Optional<Employee> op2 = emps.parallelStream()
			.filter((e) -> e.getStatus().equals(Status.FREE))
			.findAny();
		
		System.out.println(op2.get());
	}
	
	@Test
	public void test3(){
		long count = emps.stream()
						 .filter((e) -> e.getStatus().equals(Status.FREE))
						 .count();
		
		System.out.println(count);
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.max(Double::compare);
		
		System.out.println(op.get());
		
		Optional<Employee> op2 = emps.stream()
			.min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		
		System.out.println(op2.get());
	}
```

​              2. 归约

​              reduce(T identity, BinaryOperator)                  reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。

​     

```
@Test
	public void test1(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
			.reduce(0, (x, y) -> x + y);
		
		System.out.println(sum);
		
		System.out.println("----------------------------------------");
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.reduce(Double::sum);
		
		System.out.println(op.get());
	}
```

​              3. collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法

 

# 3.       并行流

## 3.1.         Fork/Join框架

 

ForkJoinPool是JDK7引入的线程池，核心思想是将大的任务拆分成多个小任务（即fork），然后在将多个小任务处理汇总到一个结果上（即join），非常像MapReduce处理原理。同时，它提供基本的线程池功能，支持设置最大并发线程数，支持任务排队，支持线程池停止，支持线程池使用情况监控，也是AbstractExecutorService的子类，主要引入了“工作窃取”机制，在多CPU计算机上处理性能更佳。 

![img](C:\Users\13441\Desktop\md\Java\java8新特性.assets/clip_image001.jpg)

 work-stealing（工作窃取算法）

​     work-stealing（工作窃取），ForkJoinPool提供了一个更有效的利用线程的机制，当ThreadPoolExecutor还在用单个队列存放任务时，ForkJoinPool已经分配了与线程数相等的队列，当有任务加入线程池时，会被平均分配到对应的队列上，各线程进行正常工作，当有线程提前完成时，会从队列的末端“窃取”其他线程未执行完的任务，当任务量特别大时，CPU多的计算机会表现出更好的性能。

## 3.2.         实例代码

 

```
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class TestForkJoin {
	//旧的forkjoin框架写法
	@Test
	public void test1(){
		long start = System.currentTimeMillis();
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);
		
		long sum = pool.invoke(task);
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //112-1953-1988-2654-2647-20663-113808
	}
	//单线程写法
	@Test
	public void test2(){
		long start = System.currentTimeMillis();
		
		long sum = 0L;
		
		for (long i = 0L; i <= 10000000000L; i++) {
			sum += i;
		}
		
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //34-3174-3132-4227-4223-31583
	}
	//java8改进后的写法
	@Test
	public void test3(){
		long start = System.currentTimeMillis();
		
		Long sum = LongStream.rangeClosed(0L, 10000000000L)
							 .parallel()
							 .sum();
		
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费的时间为: " + (end - start)); //2061-2053-2086-18926
	}

}


import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 13475679780L;
	
	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000L; //临界值
	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long length = end - start;
		
		if(length <= THRESHOLD){
			long sum = 0;
			
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			
			return sum;
		}else{
			long middle = (start + end) / 2;
			
			ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
			left.fork(); //拆分，并将该子任务压入线程队列
			
			ForkJoinCalculate right = new ForkJoinCalculate(middle+1, end);
			right.fork();
			
			return left.join() + right.join();
		}
		
	}

}
```



# 4.       Optional类

 

```
前言
工作中经常会有这样的一个经历:调用一个方法获得的返回值可能为空，需要进行null判断，然后再做一些相应的业务处理或者直接抛出NullPointerException。为了减少这样的null值判断，java官方借鉴google guava类库的Optional类，在java8 中引入了一个同样名字的Optional类,官方javadoc描述如下：

A container object which may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value.

本文会逐个介绍Optional类包含的方法,并通过几个示例展示如何使用:

of
为非Null值创建一个Optional

of方法通过工厂方法创建Optional实例，需要注意的是传入的参数不能为null，否则抛出NullPointerException。

// 给与一个非空值
Optional<String> username = Optional.of("cwl");
// 传入参数为null，抛出NullPointerException.
Optional<String> nullValue = Optional.of(null);
ofNullable
为指定的值创建一个Optional,如果指定的值为null，则返回一个空的Optional。可为空的Optional

// 下面创建了一个不包含任何值的Optional实例
// 输出Optional.empty
Optional empty = Optional.ofNullable(null);
isPresent
如果值存在返回true,否则返回false
类似下面的代码:

// isPresent方法用来检查Optional实例中是否包含值
if (username.isPresent()) {
    //在Optional实例内调用get()返回已存在的值
    System.out.println(username.get());      //输出cwl
}
get
如果Optional有值则将其返回，否则抛出NoSuchElementException。

// 执行下面的代码抛出NoSuchElementException
try {
     // 在空的Optional实例上调用get()
     System.out.println(empty.get());
 } catch (NoSuchElementException ex) {
     System.out.println(ex.getMessage());         // 输出：No value present
}
ifPresent
如果Optional实例有值则为其调用consumer ,否则不做处理。
要理解ifPresent方法，首先需要了解Consumer类。简答地说，Consumer类包含一个抽象方法。该抽象方法对传入的值进行处理，但没有返回值。Java8支持不用接口直接通过lambda表达式传入参数。
如果Optional实例有值，调用ifPresent()可以接受接口段或lambda表达式。类似下面的代码：

// ifPresent方法接受lambda表达式作为参数。
// lambda表达式对Optional的值调用consumer进行处理。
username.ifPresent((value) -> {
     System.out.println("The length of the value is: " + value.length());
});
orElse
如果有值则将其返回，否则返回指定的其它值。
如果Optional实例有值则将其返回，否则返回orElse方法传入的参数。示例如下：

// 如果值不为null，orElse方法返回Optional实例的值,否则返回传入的消息
System.out.println(empty.orElse("There is no value present!"));// 输出：There is no value present!
System.out.println(username.orElse("There is some value!"));  // 输出：cwl
orElseGet
orElseGet与orElse方法类似，区别在于得到的默认值。orElse方法将传入的字符串作为默认值，orElseGet方法可以接受Supplier接口的实现用来生成默认值。示例如下：

// orElseGet与orElse方法类似，区别在于orElse传入的是默认值，
// orElseGet可以接受一个lambda表达式生成默认值。
// 输出：Default Value
System.out.println(empty.orElseGet(() -> "Default Value"));
// 输出：cwl
System.out.println(username.orElseGet(() -> "Default Value"));
orElseThrow
如果有值则将其返回，否则抛出supplier接口创建的异常。
在orElseGet方法中，我们传入一个Supplier接口。然而，在orElseThrow中我们可以传入一个lambda表达式或方法，如果值不存在来抛出异常。示例如下：

try {
    // orElseThrow与orElse方法类似。与返回默认值不同，
    // orElseThrow会抛出lambda表达式或方法生成的异常
    empty.orElseThrow(ValueAbsentException::new);
} catch (Throwable ex) {
    //输出: No value present in the Optional instance
    System.out.println(ex.getMessage());
}
ValueAbsentException定义如下:

class ValueAbsentException extends Throwable {
     public ValueAbsentException() {
        super();
    }
    public ValueAbsentException(String msg) {
        super(msg);
    }
    @Override
    public String getMessage() {
       return "No value present in the Optional instance";
    }
}
map
如果有值，则对其执行调用mapping函数得到返回值。如果返回值不为null，则创建包含mapping返回值的Optional作为map方法返回值，否则返回空Optional。
map方法用来对Optional实例的值执行一系列操作。通过一组实现了Function接口的lambda表达式传入操作。如果你不熟悉Function接口，可以参考这篇博客。map方法示例如下：

// map方法执行传入的lambda表达式参数对Optional实例的值进行修改。
// 为lambda表达式的返回值创建新的Optional实例作为map方法的返回值。
Optional<String> upperName = username.map((value) -> value.toUpperCase());  
System.out.println(upperName.orElse("No value found"));      //输出: CWL
flatMap
如果有值，为其执行mapping函数返回Optional类型返回值，否则返回空Optional。flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回值必须是Optional。调用结束时，flatMap不会对结果用Optional封装。
参照map函数，使用flatMap重写的示例如下：

// flatMap与map（Function）非常类似，区别在于传入方法的lambda表达式的返回类型。
// map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。 
// 但flatMap方法中的lambda表达式返回值必须是Optionl实例。 
 upperName = username.flatMap((value) -> Optional.of(value.toUpperCase()));
 System.out.println(upperName.orElse("No value found"));//输出 CWL
filter
filter个方法通过传入限定条件对Optional实例的值进行过滤。文档描述如下：
如果有值并且满足断言条件返回包含该值的Optional，否则返回空Optional。
读到这里，可能你已经知道如何为filter方法传入一段代码。是的，这里可以传入一个lambda表达式。对于filter函数我们应该传入实现了Predicate接口的lambda表达式。如果你不熟悉Predicate接口，可以参考这篇文章。
现在我来看看filter的各种用法，下面的示例介绍了满足限定条件和不满足两种情况：

// filter方法检查给定的Option值是否满足某些条件。
// 如果满足则返回同一个Option实例，否则返回空Optional。
Optional<String> longName = username.filter((value) -> value.length() > 2);
System.out.println(longName.orElse("The name is less than 2 characters"));//cwl

// 另一个例子是Optional值不满足filter指定的条件。
Optional<String> anotherName = Optional.of("y");
Optional<String> shortName = anotherName.filter((value) -> value.length() > 2);
// 输出：The name is less than 2 characters
System.out.println(shortName.orElse("The name is less than 2 characters"));
以上就是Optional的各个方法api的用法介绍。

```

 

# 5.       接口的默认方法

 

接口中除了静态变量和抽象方法，还提供默认实现方法。

```
public interface MyFun {
	
	default String getName(){
		return "哈哈哈";
	}

}
```

注意：

\1. 当一个类继承父类的同时，又实现一个接口，并且这个父类和接口都有相同的方法。这时会采取”类优先“原则，就是会继承父类的方法而不实现接口的方法。

\2. 当实现多个接口的时候，A接口和B接口同时有相同的方法，那么就需要自己重写接口方法，指明接口名。

```
public class SubClass implements MyFun, MyInterface{

	@Override
	public String getName() {
		return MyInterface.super.getName();
	}

}
```

 

# 6.       接口的静态方法

 

java8中接口可以使用默认方法，同时也可以使用静态方法。具体实现如下

```
public interface MyInterface {

	public static void show(){
		System.out.println("接口中的静态方法");
	}

}

public class TestDefaultInterface {
	
	public static void main(String[] args) {
		MyInterface.show();
	}

}
```



# 7.       新的时间日期API

 

以前的时间api不是线程安全的，会被改变，一般用锁保证线程安全

现在的java.time包下的都是线程安全的，因为每一次改变都会产生新的实例，所以原来的时间也就是不可变的。

java.time 操作日期和时间

java.time.chrono 时间矫正器，特殊时间格式

java.time.format 日期时间格式化

 

```
public class DateFormatThreadLocal {
	
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
		
		protected DateFormat initialValue(){
			return new SimpleDateFormat("yyyyMMdd");
		}
		
	};
	
	public static final Date convert(String source) throws ParseException{
		return df.get().parse(source);
	}

}



import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSimpleDateFormat {
	
	public static void main(String[] args) throws Exception {
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		
//		Callable<Date> task = new Callable<Date>() {
//
//			@Override
//			public Date call() throws Exception {
//				return sdf.parse("20180121");
//			}
//			
//		};
//
//		ExecutorService pool = Executors.newFixedThreadPool(10);
//		
//		List<Future<Date>> results = new ArrayList<>();
//		
//		for (int i = 0; i < 10; i++) {
//			results.add(pool.submit(task));
//		}
//		
//		for (Future<Date> future : results) {
//			System.out.println(future.get());
//		}
//		
//		pool.shutdown();
		
		//解决多线程安全问题
//		Callable<Date> task = new Callable<Date>() {
//
//			@Override
//			public Date call() throws Exception {
//				return DateFormatThreadLocal.convert("20180121");
//			}
//
//		};
//
//		ExecutorService pool = Executors.newFixedThreadPool(10);
//
//		List<Future<Date>> results = new ArrayList<>();
//
//		for (int i = 0; i < 10; i++) {
//			results.add(pool.submit(task));
//		}
//
//		for (Future<Date> future : results) {
//			System.out.println(future.get());
//		}
//
//		pool.shutdown();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

		Callable<LocalDate> task = new Callable<LocalDate>() {

			@Override
			public LocalDate call() throws Exception {
				LocalDate ld = LocalDate.parse("20180121", dtf);
				return ld;
			}

		};

		ExecutorService pool = Executors.newFixedThreadPool(10);

		List<Future<LocalDate>> results = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}

		for (Future<LocalDate> future : results) {
			System.out.println(future.get());
		}

		pool.shutdown();
	}

}
```

 

## 7.1.         LocalDate,LocalTime,LocalDateTime

 

​    

```
	//1. LocalDate、LocalTime、LocalDateTime
	@Test
	public void test1(){
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		
		LocalDateTime ld2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
		System.out.println(ld2);
		
		LocalDateTime ldt3 = ld2.plusYears(20);
		System.out.println(ldt3);
		
		LocalDateTime ldt4 = ld2.minusMonths(2);
		System.out.println(ldt4);
		
		System.out.println(ldt.getYear());
		System.out.println(ldt.getMonthValue());
		System.out.println(ldt.getDayOfMonth());
		System.out.println(ldt.getHour());
		System.out.println(ldt.getMinute());
		System.out.println(ldt.getSecond());
	}
```



## 7.2.         Instant

 

```
//2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
	@Test
	public void test2(){
		Instant ins = Instant.now();  //默认使用 UTC 时区
		System.out.println(ins);
		
		OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
		System.out.println(odt);
		
		System.out.println(ins.getNano());
		
		Instant ins2 = Instant.ofEpochSecond(5);
		System.out.println(ins2);
	}
```



## 7.3.         时间间隔

 

```
	//Duration : 用于计算两个“时间”间隔
	//Period : 用于计算两个“日期”间隔
	@Test
	public void test3(){
		Instant ins1 = Instant.now();
		
		System.out.println("--------------------");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		Instant ins2 = Instant.now();
		
		System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));
		
		System.out.println("----------------------------------");
		
		LocalDate ld1 = LocalDate.now();
		LocalDate ld2 = LocalDate.of(2011, 1, 1);
		
		Period pe = Period.between(ld2, ld1);
		System.out.println(pe.getYears());
		System.out.println(pe.getMonths());
		System.out.println(pe.getDays());
	}
```

​    

## 7.4.         时间校正器

 

```
//4. TemporalAdjuster : 时间校正器
	@Test
	public void test4(){
	LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		
		LocalDateTime ldt2 = ldt.withDayOfMonth(10);
		System.out.println(ldt2);
		
		LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		System.out.println(ldt3);
		
		//自定义：下一个工作日
		LocalDateTime ldt5 = ldt.with((l) -> {
			LocalDateTime ldt4 = (LocalDateTime) l;
			
			DayOfWeek dow = ldt4.getDayOfWeek();
			
			if(dow.equals(DayOfWeek.FRIDAY)){
				return ldt4.plusDays(3);
			}else if(dow.equals(DayOfWeek.SATURDAY)){
				return ldt4.plusDays(2);
			}else{
				return ldt4.plusDays(1);
			}
		});
		
		System.out.println(ldt5);
		
	}
```



## 7.5.         格式化时间

 

​    

```
	//5. DateTimeFormatter : 解析和格式化日期或时间
	@Test
	public void test5(){
//		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
		
		LocalDateTime ldt = LocalDateTime.now();
		String strDate = ldt.format(dtf);
		
		System.out.println(strDate);
		
		LocalDateTime newLdt = ldt.parse(strDate, dtf);
		System.out.println(newLdt);
	}
```



## 7.6.         带时区时间

 

```
	//6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
	@Test
	public void test7(){
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
		System.out.println(ldt);
		
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
		System.out.println(zdt);
	}
	
	@Test
	public void test6(){
		Set<String> set = ZoneId.getAvailableZoneIds();
		set.forEach(System.out::println);
	}
```

​     

# 8.       重复注解

## 8.1.         定义

 

可以重复使用的注解。

## 8.2.         实例代码

 

说明：重复注解需要一个容器去装这个重复注解。还需要在重复注解那里打上@Repeatable(容器类)的注解

 

 

```
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/7/13 13:11
 */
//这是重复注解
@Repeatable(MyAnnotations.class)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation
{
    String value() default "author is Veng Su ";
}




import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/7/13 13:12
 */
//这是容器
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotations {
    MyAnnotation[] value();
}


import java.lang.reflect.Method;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/7/13 13:14
 */
public class Test {
    @org.junit.Test
    public void test() throws NoSuchMethodException {
        Class<Test> testClass=Test.class;
        Method method=testClass.getMethod("show");
        MyAnnotation[] myAnnotations=method.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation : myAnnotations) {
            System.out.println(myAnnotation.value());
        }
    }
    @MyAnnotation("Veng Su")
    @MyAnnotation("苏文广")
    public void show(){

    }
}
```

 

# 9.       类型注解

 ![20180713132937](C:\Users\13441\Desktop\md\Java\java8新特性.assets/20180713132937.jpg)

 ![20180713133005](C:\Users\13441\Desktop\md\Java\java8新特性.assets/20180713133005.jpg)

# 10.  参考文献

 

**https://www.jianshu.com/p/a785e65eddd6**

 