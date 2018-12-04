# 注解 配置 spring AOP 实现五种通知类型

话就不多说了，首先准备环境，准备环境部分就直接看[xml配置spring AOP](https://blog.csdn.net/qq_37933685/article/details/81637432)吧。也可以直接把这篇的源码给download下来

环境使用maven搭建spring 环境，引入必要的jar包之后，我把之前的xml配置先给注释掉。

要使用注解配置切面，首先要在spring 的配置文件开启 切面自动代理

```xml
<aop:aspectj-autoproxy/>
```

要使用aop标签必须导入约束，xsi直接添加在后面已有的就好。

```xml
xmlns:aop="http://www.springframework.org/schema/aop"
xsi:schemaLocation= "http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd"
```

那么开启切面自动代理之后，我们就可以使用注解了，那我们来准备一个切面类。

AnnotationAspect.java

```java
/**
 * author: Veng Su
 * email: suveng@163.com
 * date: 2018/8/14 16:22
 */

@Component(value = "annotationAspect")
@Aspect
public class AnnotationAspect {

    //before 切
    @Before(value = "execution(* *..*.*.say())")
    public void beforeF(){
        System.out.println("我是before");
    }
    //after 切
    @After(value = "execution(* *..*.*.say())")
    public void afterF(){
        System.out.println("我是after");
    }
    //after_throwing 切
    @AfterThrowing(value = "execution(* *..*.*.say())")
    public void afterTF(){
        System.out.println("我是after-throwing");
    }
    //after-returning 切
    @AfterReturning(value = "execution(* *..*.*.say())")
    public void afterRF(){
        System.out.println("我是after-returning");
    }
    //around 切 要把joinpoint给传进来
    @Around(value = "AnnotationAspect.say()")
    public void aroundF(ProceedingJoinPoint joinPoint){
        System.out.println("前around");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("后 around");
    }
    // 如果切入表达式，大部分都是一样的话，则可考虑抽取出来，简单利用
    @Pointcut(value = "execution(* *..*.*.say())")
    public void say() {}
}
```

到这里，切面类和aop配置都已配置好了，可以测试了。这里是复用了上次xml配置spring aop的代码

测试：

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/config/beans.xml")
public class SpringTest {
    @Resource(name = "userDao")
    UserDao userDao;
    @Test
    public void testAnnotationAop(){
        userDao.say();
    }
}
```

测试结果：

![1534249336472](AOP的通知类型(注解).assets/1534249417802.png)

这就是简单的用注解方式配置aop。加油

码云代码地址：https://gitee.com/suwenguang/SpringFrameworkDemo

