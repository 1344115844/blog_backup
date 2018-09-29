# Spring 5.0.8 框架文档

> Version 5.0.8.RELEASE 

地址：https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/index.html

请阅读[**Overview**](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/overview.html#overview) 以获得快速介绍，包括简要历史，设计理念，提问问题以及入门提示。 有关“什么是新的”或“从以前的版本迁移”的信息，请查看[**Github Wiki**](https://github.com/spring-projects/spring-framework/wiki). 

参考文档分为几个部分:

| [Core](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#spring-core) | IoC container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP. |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Testing](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/testing.html#testing) | Mock objects, TestContext framework, Spring MVC Test, WebTestClient. |
| [Data Access](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/data-access.html#spring-data-tier) | Transactions, DAO support, JDBC, ORM, Marshalling XML.       |
| [Web Servlet](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#spring-web) | Spring MVC, WebSocket, SockJS, STOMP messaging.              |
| [Web Reactive](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web-reactive.html#spring-webflux) | Spring WebFlux, WebClient, WebSocket.                        |
| [Integration](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/integration.html#spring-integration) | Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Cache.    |
| [Languages](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/languages.html#languages) | Kotlin, Groovy, Dynamic languages.                           |

# Core Technologies核心技术

地址：https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#spring-core

这部分参考文档涵盖了Spring Framework绝对不可或缺的所有技术。

其中最重要的是Spring Framework的控制反转（IoC）容器。 对Spring框架的IoC容器进行彻底的处理后，我们将全面介绍Spring的面向方面编程（AOP）技术。  Spring Framework有自己的AOP框架，概念上易于理解，并且成功地解决了Java企业编程中AOP要求的80％最佳点。

还提供了Spring与AspectJ集成的覆盖范围（目前最丰富的 - 在功能方面 - 当然也是Java企业领域中最成熟的AOP实现）。

# 1. The IoC container IoC容器

## 1.1. Spring IoC容器和bean简介

​	本章介绍了Spring Framework实现的控制反转（IoC）1原理。  IoC也称为依赖注入（DI）。

​	这是一个过程，通过这个过程，对象定义它们的依赖关系，即它们使用的其他对象，只能通过构造函数参数，工厂方法的参数，或者在构造或从工厂方法返回后在对象实例上设置的属性。 然后容器在创建bean时注入这些依赖项。这个过程基本上是相反的，因此名称Inversion of Control（IoC），bean本身通过使用类的直接构造或诸如Service Locator模式之类的机制来控制其依赖关系的实例化或位置。

​	org.springframework.beans和org.springframework.context包是Spring Framework的IoC容器的基础。[`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/BeanFactory.html) 接口提供了一种能够管理任何类型对象的高级配置机制。

[`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/context/ApplicationContext.html)  是BeanFactory的子接口。它增加了与Spring的AOP功能的更容易的集成; 消息资源处理（用于国际化），事件发布， 和特定于应用程序层的上下文，例如用于Web应用程序的WebApplicationContext。

​	简而言之，BeanFactory提供配置框架和基本功能，ApplicationContext添加了更多特定于企业的功能。

ApplicationContext是BeanFactory的完整超集，在本章中专门用于Spring的IoC容器的描述。 有关使用BeanFactory而不是ApplicationContext的更多信息，请参阅 [The BeanFactory](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-beanfactory) 。

​	在Spring中，构成应用程序主干并由Spring IoC容器管理的对象称为bean。bean是一个由Spring IoC容器实例化，组装和管理的对象。 否则，bean只是应用程序中众多对象之一。  Bean及其之间的依赖关系反映在容器使用的配置元数据中。（元数据指的是xml配置，注解配置，java代码配置）

## 1.2. Container overview 容器概述

​	接口org.springframework.context.ApplicationContext表示Spring IoC容器，负责实例化，配置和组装上述bean。 	他通过读取配置元数据来获取有关要实例化，配置和组装的对象的指令。配置元数据以XML，Java注释或Java代码表示。它允许您表达组成应用程序的对象以及这些对象之间丰富的相互依赖性。

​	ApplicationContext接口的几个实现是与Spring一起提供的。在独立应用程序中，通常会创建ClassPathXmlApplicationContext或FileSystemXmlApplicationContext的实例。虽然XML是定义配置元数据的传统格式，但您可以通过提供少量XML配置来声明性地支持这些其他元数据格式，从而指示容器使用Java注释或代码作为元数据格式。

​	在大多数应用程序方案中，不需要显式用户代码来实例化Spring IoC容器的一个或多个实例。例如，在Web应用程序场景中，应用程序的web.xml文件中的简单八行（左右）样板Web描述符XML通常就足够了（请参阅[Web应用程序的便捷ApplicationContext实例化]( [Convenient ApplicationContext instantiation for web applications](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#context-create) )）。如果您使用的是[Spring工具套件]([Spring Tool Suite](https://spring.io/tools/sts)  )Eclipse驱动的开发环境，只需点击几下鼠标或按键即可轻松创建此样板文件配置。

​	下图是Spring工作原理的高级视图。您的应用程序类与配置元数据相结合，以便在创建和初始化ApplicationContext之后，您拥有一个完全配置且可执行的系统或应用程序。

(configuration metadata 元数据， your business objects pojos 你的Javabeans)

![container magic](assets/container-magic.png) 

​									图1. Spring IoC容器

### 1.2.1. Configuration metadata 配置元数据

​	如上图所示，Spring IoC容器使用一种配置元数据形式;此配置元数据表示您作为应用程序开发人员如何告诉Spring容器在应用程序中实例化，配置和组装对象。

​	传统上，配置元数据以简单直观的XML格式提供，本章大部分内容用于传达Spring IoC容器的关键概念和功能。	

> 基于XML的元数据不是唯一允许的配置元数据形式。  Spring IoC容器本身完全与实际编写此配置元数据的格式分离。 目前，许多开发人员为其Spring应用程序选择基于Java的配置。

有关在Spring容器中使用其他形式的元数据的信息，请参阅

- **[Annotation-based configuration](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-annotation-config): Spring 2.5引入了对基于注释的配置元数据的支持。**
- [Java-based configuration](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-java): 从Spring 3.0开始，Spring JavaConfig项目提供的许多功能都成为核心Spring Framework的一部分。 因此，您可以使用Java而不是XML文件在应用程序类外部定义bean。 要使用这些新功能，请参阅`@Configuration`，`@ Bean`，`@ Import`和`@ DependsOn`注释。



​	Spring配置由容器必须管理的至少一个且通常不止一个bean定义组成。 基于XML的配置元数据显示这些bean在顶级<beans />元素中配置为<bean />元素。  Java配置通常在@Configuration类中使用@Bean注释方法。

​	这些bean定义对应于构成应用程序的实际对象。通常，您定义服务层对象，数据访问对象（DAO），表示对象（如Struts Action实例），基础结构对象（如Hibernate SessionFactories，JMS队列等）。

​	通常，不会在容器中配置细粒度域对象，因为DAO和业务逻辑通常负责创建和加载域对象。 但是，您可以使用Spring与AspectJ的集成来配置在IoC容器控制之外创建的对象。可以看 [Using AspectJ to dependency-inject domain objects with Spring](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#aop-atconfigurable). 

以下示例显示了基于XML的配置元数据的基本结构：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions go here -->

</beans>
```

​	id属性是一个字符串，用于标识单个bean定义。class属性定义bean的类型并使用完全限定的classname。id属性的值指的是协作对象。 本例中未显示用于引用协作对象的XML; 有关更多信息，请参阅 [Dependencies](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-dependencies)  。

### 1.2.2. Instantiating a container 实例化容器

​	实例化Spring IoC容器非常简单。 提供给ApplicationContext构造函数的位置路径实际上是资源字符串，允许容器从各种外部资源（如本地文件系统，Java CLASSPATH等）加载配置元数据。

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

> 在了解了Spring的IoC容器之后，您可能想要了解有关Spring的资源抽象的更多信息，如 [Resources](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#resources) 参考资料中所述，它提供了一种从URI语法中定义的位置读取InputStream的便捷机制。 特别是，资源路径用于构建应用程序上下文，如[Application contexts and Resource paths](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#resources-app-ctx).中所述。

​	以下示例显示了服务层对象（services.xml）配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- services -->

    <bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
        <!-- 此bean的其他协作者和配置在这里 -->
    </bean>

    <!-- services的更多bean定义在这里 -->

</beans>
```

​	以下示例显示了数据访问对象daos.xml文件:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountDao"
        class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
        <!-- 此bean的其他协作者和配置在这里 -->
    </bean>

    <bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
        <!-- 此bean的其他协作者和配置在这里 -->
    </bean>

    <!-- 更多数据访问对象的bean定义在这里 -->

</beans>
```

​	在前面的示例中，服务层由PetStoreServiceImpl类和两个JpaAccountDao和JpaItemDao类型的数据访问对象组成（基于JPA对象/关系映射标准）。属性name元素引用JavaBean属性的名称，ref元素引用另一个bean定义的名称。

> JPA是Java Persistence API的简称，中文名Java持久层API 
>
> 用来操作实体对象，执行CRUD操作，框架在后台替代我们完成所有的事情，开发者从繁琐的JDBC和SQL代码中解脱出来。
>
> MyBatis则是一个能够灵活编写sql语句，并将sql的入参和查询结果映射成POJOs的一个持久层框架  

#### 编写基于XML的配置元数据

​	让bean定义跨越多个XML文件会很有用。 通常，每个单独的XML配置文件都代表架构中的逻辑层或模块。

​	您可以使用应用程序上下文构造函数从所有这些XML片段加载bean定义。 此构造函数采用多个Resource位置，如上一节中所示。 或者，使用一个或多个<import />元素来从另一个或多个文件加载bean定义。 例如：

```xml
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>

    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```

​	在前面的示例中，外部bean定义从三个文件加载：services.xml，messageSource.xml和themeSource.xml。 所有位置路径都与执行导入的定义文件相关，因此services.xml必须与执行导入的文件位于相同的目录或类路径位置，而messageSource.xml和themeSource.xml必须位于该位置下的资源位置 导入文件。 正如您所看到的，忽略了一个前导斜杠，但考虑到这些路径是相对的，最好不要使用斜杠。 根据Spring Schema，要导入的文件的内容（包括顶级<beans />元素）必须是有效的XML bean定义。

> 可以（但不建议）使用相对“../”路径引用父目录中的文件。 这样做会对当前应用程序之外的文件创建依赖关系。 特别是，不建议将此引用用于“classpath：”URL（例如，“classpath：../ services.xml”），其中运行时解析过程选择“最近的”类路径根，然后查看其父目录。 类路径配置更改可能导致选择不同的，不正确的目录。
>
> 您始终可以使用完全限定的资源位置而不是相对路径：例如，“file：C：/config/services.xml”或“classpath：/config/services.xml”。 但是，请注意您将应用程序的配置与特定的绝对位置耦合。 通常最好为这样的绝对位置保持间接，例如，通过在运行时针对JVM系统属性解析的“$ ...”占位符。

​	import指令是beans命名空间本身提供的功能。 除了普通bean定义之外的其他配置功能在Spring提供的一系列XML命名空间中可用，例如：  “context”和“util”命名空间。

#### Groovy Bean定义DSL

​	作为外部化配置元数据的另一个示例，bean定义也可以在Spring的Groovy Bean定义DSL中表示，如Grails框架中所知。 通常，此类配置将存在“.groovy”文件中，其结构如下所示：

```groovy
beans {
    dataSource(BasicDataSource) {
        driverClassName = "org.hsqldb.jdbcDriver"
        url = "jdbc:hsqldb:mem:grailsDB"
        username = "sa"
        password = ""
        settings = [mynew:"setting"]
    }
    sessionFactory(SessionFactory) {
        dataSource = dataSource
    }
    myService(MyService) {
        nestedBean = { AnotherBean bean ->
            dataSource = dataSource
        }
    }
}
```

​	此配置样式在很大程度上等同于XML bean定义，甚至支持Spring的XML配置命名空间。 它还允许通过“importBeans”指令导入XML bean定义文件。

### 1.2.3. Using the container 使用容器

​	ApplicationContext是高级工厂的接口，能够维护不同bean及其依赖项的注册表。 使用方法`T getBean（String name，Class <T> requiredType）`，您可以检索bean的实例。

​	ApplicationContext使您可以读取bean定义并按如下方式访问它们:

```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// use configured instance
List<String> userList = service.getUsernameList();
```

​	使用Groovy配置，bootstrapping看起来非常相似，只是一个不同的上下文实现类，它是Groovy感知的（但也理解XML bean定义）

```java
ApplicationContext context = new GenericGroovyApplicationContext("services.groovy", "daos.groovy");
```

​	最灵活的变体是GenericApplicationContext与读者代表的组合，例如， 使用XML文件的XmlBeanDefinitionReader:

```java
GenericApplicationContext context = new GenericApplicationContext();
new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
context.refresh();
```

​	或者使用GroovyBeanDefinitionReader for Groovy文件：

```groovy
GenericApplicationContext context = new GenericApplicationContext();
new GroovyBeanDefinitionReader(context).loadBeanDefinitions("services.groovy", "daos.groovy");
context.refresh();
```

​	这些读者委托可以在同一个ApplicationContext上混合和匹配，如果需要，可以从不同的配置源读取bean定义。

​	然后，您可以使用getBean来检索Bean的实例。  ApplicationContext接口有一些其他方法可以检索bean，但理想情况下，您的应用程序代码绝不应该使用它们。 实际上，您的应用程序代码根本不应该调用getBean（）方法，因此根本不依赖于Spring API。 例如，Spring与Web框架的集成为各种Web框架组件（如控制器和JSF托管bean）提供依赖注入，允许您通过元数据（例如自动装配注释）声明对特定bean的依赖性。

## 1.3. Bean overview Bean概述

​	Spring IoC容器管理一个或多个bean。 这些bean是使用您提供给容器的配置元数据创建的，例如，以XML <bean />定义的形式。

在容器本身内，这些bean定义表示为BeanDefinition对象，其中包含（以及其他信息）以下元数据:

1. 包限定的类名：通常是正在定义的bean的实际实现类。
2. Bean行为配置元素，说明bean在容器中的行为方式（范围，生命周期回调等）。
3. 协作者或依赖项,引用bean执行其工作所需的其他bean; 
4. 要在新创建的对象中设置的其他配置设置，例如，在管理连接池的Bean中使用的连接数，或池的大小限制。

此元数据转换为组成每个bean定义的一组属性。

​								表1. bean定义

| 属性                     | 解释在...... |
| ------------------------ | ------------ |
| class                    | 1.3.2        |
| name                     | 1.3.1        |
| scope                    | 1.5          |
| constructor arguments    | 1.4.1        |
| properties               | 1.4.1        |
| autowiring mode          | 1.4.5        |
| lazy-initialization mode | 1.4.4        |
| initialization method    | 1.6          |
| destruction method       | 1.6.1        |

​	除了包含有关如何创建特定bean的信息的bean定义之外，ApplicationContext实现还允许用户注册在容器外部创建的现有对象。 这是通过getBeanFactory（）方法访问ApplicationContext的BeanFactory来完成的，该方法返回BeanFactory实现DefaultListableBeanFactory。  DefaultListableBeanFactory通过方法registerSingleton（..）和registerBeanDefinition（..）支持此注册。 但是，典型应用程序仅适用于通过元数据bean定义定义的bean。

> 需要尽早注册Bean元数据和手动提供的单例实例，以便容器在自动装配和其他内省步骤期间正确推理它们。 虽然在某种程度上支持覆盖现有元数据和现有单例实例，但是在运行时注册新bean（与对工厂的实时访问同时）并未得到官方支持，并且可能导致bean容器中的并发访问异常和/或不一致状态 。



### 1.3.1. Naming beans 命名beans

​	每个bean都有一个或多个标识符。 这些标识符在托管bean的容器中必须是唯一的。  bean通常只有一个标识符，但如果它需要多个标识符，则额外的标识符可以被视为别名。

​	在基于XML的配置元数据中，您使用id和/或name属性来指定bean标识符。  id属性允许您指定一个id。 通常，这些名称是字母数字（'myBean'，'fooService'等），但也可能包含特殊字符。 如果要向bean引入其他别名，还可以在name属性中指定它们，用逗号（，），分号（;）或空格分隔。 作为历史记录，在Spring 3.1之前的版本中，id属性被定义为xsd：ID类型，它约束了可能的字符。 从3.1开始，它被定义为xsd：string类型。 请注意，bean ID唯一性仍由容器强制执行，但不再由XML解析器强制执行。

​	您不需要为bean提供名称或ID。 如果没有显式提供名称或标识，则容器会为该bean生成唯一的名称。 但是，如果要通过名称引用该bean，则必须通过使用ref元素或Service Locator样式查找来提供名称。 不提供名称的动机与使用内部bean和自动装配协作者有关。

> ​								Bean命名约定
>
> ​	惯例是在命名bean时使用标准Java约定作为实例字段名称。 也就是说，bean名称以小写字母开头，从那时起就是驼峰式的。
>
>  	这些名称的示例是（没有引号）'accountManager'，'accountService'，'userDao'，'loginController'等等。
>
> ​	命名bean始终使您的配置更易于阅读和理解，如果您使用的是Spring AOP，那么在将建议应用于与名称相关的一组bean时，它会有很大帮助。

​	通过类路径中的组件扫描，Spring按照上述规则为未命名的组件生成bean名称：实质上，采用简单的类名并将其初始字符转换为小写。 但是，在（不常见的）特殊情况下，当有多个字符并且第一个和第二个字符都是大写字母时，原始外壳将被保留。 这些规则与java.beans.Introspector.decapitalize（Spring在此处使用）定义的规则相同。

#### 在bean定义之外别名bean

​	在bean定义本身中，您可以为bean提供多个名称，方法是使用id属性指定的最多一个名称和name属性中的任意数量的其他名称。 这些名称可以是同一个bean的等效别名，并且在某些情况下很有用，例如允许应用程序中的每个组件通过使用特定于该组件本身的bean名称来引用公共依赖项。

​	但是，指定实际定义bean的所有别名并不总是足够的。 有时需要为其他地方定义的bean引入别名。 在大型系统中通常就是这种情况，其中配置在每个子系统之间分配，每个子系统具有其自己的一组对象定义。 在基于XML的配置元数据中，您可以使用<alias />元素来完成此任务。

```xml
<alias name="fromName" alias="toName"/>
```

​	在这种情况下，在使用此别名定义之后，同名容器中名为fromName的bean也可以称为toName。

​	例如，子系统A的配置元数据可以通过名称subsystemA-dataSource引用DataSource。 子系统B的配置元数据可以通过名称subsystemB-dataSource引用数据源。 在编写使用这两个子系统的主应用程序时，主应用程序通过名称myApp-dataSource引用DataSource。 要让所有三个名称引用您添加到MyApp配置元数据的同一对象，请使用以下别名定义:

```xml
<alias name="subsystemA-dataSource" alias="subsystemB-dataSource"/>
<alias name="subsystemA-dataSource" alias="myApp-dataSource" />
```

​	现在，每个组件和主应用程序都可以通过一个唯一的名称引用dataSource，并保证不与任何其他定义冲突（有效地创建命名空间），但它们引用相同的bean。

> ​								Java的配置
>
> 如果您使用的是Java配置，则@Bean注释可用于提供别名，请参阅 [Using the @Bean annotation](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-java-bean-annotation) 注释了解详细信息。

### 1.3.2. Instantiating beans 实例化bean

​	bean定义本质上是用于创建一个或多个对象的配方。 容器在被询问时查看命名bean的配置，并使用由该bean定义封装的配置元数据来创建（或获取）实际对象。

​	如果使用基于XML的配置元数据，则指定要在<bean />元素的class属性中实例化的对象的类型（或类）。 此class属性通常是必需的，它在内部是BeanDefinition实例上的Class属性。  （有关异常，请参阅 [Instantiation using an instance factory method](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-class-instance-factory-method)  和[Bean definition inheritance](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-child-bean-definitions) 。）以两种方式之一使用Class属性

1. 通常，在容器本身通过反向调用其构造函数直接创建bean的情况下指定要构造的bean类，稍微等同于使用new运算符的Java代码。
2. 要指定包含将被调用以创建对象的静态工厂方法的实际类，在不太常见的情况下，容器在类上调用静态工厂方法来创建bean。 从静态工厂方法的调用返回的对象类型可以完全是同一个类或另一个类

> 内部类名
>
> 如果要为静态嵌套类配置bean定义，则必须使用嵌套类的二进制名称。
>
> 例如，如果在com.example包中有一个名为Foo的类，并且这个Foo类有一个名为Bar的静态嵌套类，那么bean定义中'class'属性的值将是com.example.Foo$Bar 
>
> 请注意，在名称中使用$字符可以将嵌套类名与外部类名分开。

#### 使用构造函数实例化

​	当您通过构造方法创建bean时，所有普通类都可以使用并与Spring兼容。 也就是说，正在开发的类不需要实现任何特定接口或以特定方式编码。 简单地指定bean类就足够了。 但是，根据您为该特定bean使用的IoC类型，您可能需要一个默认（空）构造函数。

​	Spring IoC容器几乎可以管理您希望它管理的任何类; 它不仅限于管理真正的JavaBeans。 大多数Spring用户更喜欢实际的JavaBeans，只有一个默认（无参数）构造函数，并且在容器中的属性之后建模了适当的setter和getter。 您还可以在容器中拥有更多异国情调的非bean样式类。 例如，如果您需要使用绝对不符合JavaBean规范的旧连接池，那么Spring也可以对其进行管理。

​	使用基于XML的配置元数据，您可以按如下方式指定bean类：

```xml
<bean id="exampleBean" class="examples.ExampleBean"/>

<bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

​	有关为构造函数提供参数的机制（如果需要）以及在构造对象后设置对象实例属性的详细信息，请参阅[Injecting Dependencies](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-collaborators) 。

#### 使用静态工厂方法实例化

​	定义使用静态工厂方法创建的bean时，可以使用class属性指定包含静态工厂方法的类和名为factory-method的属性，以指定工厂方法本身的名称。 您应该能够调用此方法（使用后面描述的可选参数）并返回一个活动对象，随后将其视为通过构造函数创建的对象。 这种bean定义的一个用途是在遗留代码中调用静态工厂。

​	以下bean定义指定通过调用factory-method创建bean。 该定义未指定返回对象的类型（类），仅指定包含工厂方法的类。 在此示例中，createInstance（）方法必须是静态方法。

```xml
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```

```java
public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}

    public static ClientService createInstance() {
        return clientService;
    }
}
```

有关在从工厂返回对象后向工厂方法提供（可选）参数并设置对象实例属性的机制的详细信息，请参阅详细信息中的 [Dependencies and configuration in detail](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-properties-detailed) 。

#### 使用实例工厂方法实例化

​	与通过静态工厂方法实例化类似，使用实例工厂方法进行实例化会从容器调用现有bean的非静态方法来创建新bean。 要使用此机制，请将class属性保留为空，并在factory-bean属性中，在当前（或父/祖先）容器中指定bean的名称，该容器包含要调用以创建对象的实例方法。 使用factory-method属性设置工厂方法本身的名称。

```xml
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }
}
```

一个工厂类也可以包含多个工厂方法，如此处所示:

```xml
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>

<bean id="accountService"
    factory-bean="serviceLocator"
    factory-method="createAccountServiceInstance"/>
```

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    private static AccountService accountService = new AccountServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }

    public AccountService createAccountServiceInstance() {
        return accountService;
    }
}
```

这种方法表明可以通过依赖注入（DI）来管理和配置工厂bean本身。 请参阅[Dependencies and configuration in detail](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-properties-detailed). 

> 在Spring文档中，工厂bean指的是在Spring容器中配置的bean，它将通过[instance](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-class-instance-factory-method)  或[static](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-class-static-factory-method) factory method 创建对象。 相比之下，FactoryBean（注意大小写）是指Spring特定的FactoryBean。

## 1.4. Dependencies 依赖

​	典型的企业应用程序不包含单个对象（或Spring用法中的bean）。 即使是最简单的应用程序也有一些对象可以协同工作，以呈现最终用户所看到的连贯应用程序。 下一节将介绍如何定义多个独立的bean定义，以及对象协作实现目标的完全实现的应用程序。

### 1.4.1. 依赖注入

​	使用DI原理的代码更清晰，当对象提供其依赖项时，解耦更有效。 该对象不查找其依赖项，也不知道依赖项的位置或类。 因此，您的类变得更容易测试，特别是当依赖关系在接口或抽象基类上时，这允许在单元测试中使用stub或mock实现。

​	DI存在两个主要变体，基于构造函数的依赖注入[Constructor-based dependency injection](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-constructor-injection) 和基于Setter的依赖注入[Setter-based dependency injection](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-setter-injection).

#### 基于构造函数的依赖注入

​	基于构造函数的DI由容器调用具有多个参数的构造函数来完成，每个参数表示一个依赖项。 调用具有特定参数的静态工厂方法来构造bean几乎是等效的，本讨论同样处理构造函数和静态工厂方法的参数。 以下示例显示了一个只能通过构造函数注入进行依赖注入的类。 请注意，此类没有什么特别之处，它是一个POJO，它不依赖于容器特定的接口，基类或注释。

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on a MovieFinder
    private MovieFinder movieFinder;

    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

##### 构造函数参数解析

​	使用参数的类型进行构造函数参数解析匹配。 如果bean定义的构造函数参数中不存在潜在的歧义，那么在bean定义中定义构造函数参数的顺序是在实例化bean时将这些参数提供给适当的构造函数的顺序。 考虑以下类：

```java
package x.y;

public class Foo {

    public Foo(Bar bar, Baz baz) {
        // ...
    }
}
```

假设Bar和Baz类与继承无关，则不存在潜在的歧义。 因此，以下配置工作正常，您无需在<constructor-arg />元素中显式指定构造函数参数索引和/或类型。

```xml
<beans>
    <bean id="foo" class="x.y.Foo">
        <constructor-arg ref="bar"/>
        <constructor-arg ref="baz"/>
    </bean>

    <bean id="bar" class="x.y.Bar"/>

    <bean id="baz" class="x.y.Baz"/>
</beans>
```

当引用另一个bean时，类型是已知的，并且可以进行匹配（与前面的示例一样）。 当使用简单类型时，例如<value> true </ value>，Spring无法确定值的类型，因此无法在没有帮助的情况下按类型进行匹配。 考虑以下class：

```java
package examples;

public class ExampleBean {

    // Number of years to calculate the Ultimate Answer
    private int years;

    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;

    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

##### 构造函数参数类型匹配

在前面的场景中，如果使用type属性显式指定构造函数参数的类型，则容器可以使用与简单类型匹配的类型。 例如：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

##### 构造函数参数索引

使用index属性显式指定构造函数参数的索引。 例如：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

除了解决多个简单值的歧义之外，指定索引还可以解决构造函数具有相同类型的两个参数的歧义。 请注意，索引从0开始。

##### 构造函数参数名称

您还可以使用构造函数参数名称进行值消歧：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

请记住，要使这项工作开箱即用，必须在启用调试标志的情况下编译代码，以便Spring可以从构造函数中查找参数名称。 如果无法使用debug标志编译代码（或者不希望），则可以使用@ConstructorProperties JDK批注显式命名构造函数参数。 然后，示例类必须如下所示:

```java
package examples;

public class ExampleBean {

    // Fields omitted

    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

#### 基于Setter的依赖注入

​	基于setter的DI是在调用无参数构造函数或无参数静态工厂方法来实例化bean之后，通过容器调用bean上的setter方法来完成的。

​	以下示例显示了一个只能使用纯setter注入进行依赖注入的类。 这个类是传统的Java。 它是一个POJO，它不依赖于容器特定的接口，基类或注释。

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;

    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

​	ApplicationContext支持它管理的bean的基于构造函数和基于setter的DI。 在通过构造函数方法注入了一些依赖项之后，它还支持基于setter的DI。 您可以以BeanDefinition的形式配置依赖项，并将其与PropertyEditor实例结合使用，以将属性从一种格式转换为另一种格式。 但是，大多数Spring用户不直接使用这些类（即以编程方式），而是使用XML bean定义，带注释的组件（即使用@ Component，@ Controller等注释的类）或基于Java的@Bean方法， @Configuration类。 然后，这些源在内部转换为BeanDefinition的实例，并用于加载整个Spring IoC容器实例。

> ​									基于构造函数或基于setter的DI？
>
> 由于您可以混合基于构造函数和基于setter的DI，因此将构造函数用于强制依赖项和setter方法或可选依赖项的配置方法是一个很好的经验法则。 请注意，在setter方法上使用[@Required](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-required-annotation) 注释可用于使属性成为必需的依赖项。
>
> Spring团队通常提倡构造函数注入，因为它使应用程序组件能够实现为不可变对象，并确保所需的依赖项不为空。 此外，构造函数注入的组件始终以完全初始化的状态返回到客户端（调用）代码。 作为旁注，大量的构造函数参数是一个糟糕的代码气味，暗示该类可能有太多的责任，应该重构以更好地解决关注点的正确分离。
>
> Setter注入应主要仅用于可在类中指定合理默认值的可选依赖项。 否则，必须在代码使用依赖项的任何位置执行非空检查。  setter注入的一个好处是setter方法使该类的对象可以在以后重新配置或重新注入。 因此，通过JMX MBean进行管理是二次注入的一个引人注目的用例。
>
> 使用对特定类最有意义的DI样式。 有时，在处理您没有源的第三方类时，你来做决定。 例如，如果第三方类没有公开任何setter方法，那么构造函数注入可能是唯一可用的DI形式。

##### 依赖性解决过程

容器执行如下的bean依赖性解析

1. ApplicationContext是使用描述所有bean的配置元数据创建和初始化的。 可以通过XML，Java代码或注释指定配置元数据。
2. 对于每个bean，如果使用的是依赖于普通构造函数的，那么它的依赖关系将以属性，构造函数参数或static-factory方法的参数的形式表示。 实际创建bean时，会将这些依赖项提供给bean。
3. 每个属性或构造函数参数都是要设置的值的实际定义，或者是对容器中另一个bean的引用。
4. 作为值的每个属性或构造函数参数都从其指定格式转换为该属性或构造函数参数的实际类型。 默认情况下，Spring可以将以字符串格式提供的值转换为所有内置类型，例如int，long，String，boolean等。

Spring容器在创建容器时验证每个bean的配置。 但是，在实际创建bean之前，不会设置bean属性本身。 创建容器时会创建单例作用域并设置为预先实例化（默认值）的Bean。 范围在Bean范围中定义。 否则，仅在请求时才创建bean。 创建bean可能会导致创建bean的图形，因为bean的依赖关系及其依赖关系（依此类推）被创建和分配。 请注意，这些依赖项之间的分辨率不匹配可能会显示较晚，即首次创建受影响的bean时。

> ​							循环依赖
>
> 如果您主要使用构造函数注入，则可以创建无法解析的循环依赖关系场景。
>
> 例如：类A通过构造函数注入需要类B的实例，而类B通过构造函数注入需要类A的实例。 如果为A类和B类配置bean以便相互注入，则Spring IoC容器会在运行时检测此循环引用，并抛出BeanCurrentlyInCreationException。
>
> 一种可能的解决方案是编辑由setter而不是构造函数配置的某些类的源代码。 或者，避免构造函数注入并仅使用setter注入。 换句话说，尽管不推荐使用，但您可以使用setter注入配置循环依赖关系。
>
> 与典型情况（没有循环依赖）不同，bean A和bean B之间的循环依赖强制其中一个bean在完全初始化之前被注入另一个bean（一个经典的鸡/蛋场景）。

​	你通常可以信任Spring做正确的事。 它在容器加载时检测配置问题，例如对不存在的bean和循环依赖的引用。 当实际创建bean时，Spring会尽可能晚地设置属性并解析依赖项。 这意味着，如果在创建该对象或其依赖项之一时出现问题，则在请求对象时，正确加载的Spring容器可以在以后生成异常。 例如，bean因缺少属性或无效属性而抛出异常。 这可能会延迟一些配置问题的可见性，这就是默认情况下ApplicationContext实现预先实例化单例bean的原因。 以实际需要之前创建这些bean的一些前期时间和内存为代价，您会在创建ApplicationContext时发现配置问题，而不是更晚。 您仍然可以覆盖此默认行为，以便单例bean将延迟初始化，而不是预先实例化。

​	如果不存在循环依赖，当一个或多个协作bean被注入依赖bean时，每个协作bean在被注入依赖bean之前完全配置。 这意味着如果bean A依赖于bean B，Spring IoC容器在调用bean A上的setter方法之前完全配置bean B.换句话说，bean被实例化（如果不是预先实例化的单例）， 设置依赖项，并调用相关的生命周期方法（如[configured init method](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-lifecycle-initializingbean)  或 [InitializingBean callback method](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-lifecycle-initializingbean) ）。

##### 依赖注入的例子

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```

在前面的示例中，setter被声明为与XML文件中指定的属性匹配。 以下示例使用基于构造函数的DI:

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }
}
```

bean定义中指定的构造函数参数将用作ExampleBean的构造函数的参数。

现在考虑这个例子的变体，其中不是使用构造函数，而是告诉Spring调用静态工厂方法来返回对象的实例:

```xml
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/> 
```

```java
public class ExampleBean {

    // a private constructor
    private ExampleBean(...) {
        ...
    }

    // a static factory method; the arguments to this method can be
    // considered the dependencies of the bean that is returned,
    // regardless of how those arguments are actually used.
    public static ExampleBean createInstance (
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {

        ExampleBean eb = new ExampleBean (...);
        // some other operations...
        return eb;
    }
}
```

静态工厂方法的参数通过<constructor-arg />元素提供，与实际使用的构造函数完全相同。 工厂方法返回的类的类型不必与包含静态工厂方法的类的类型相同，尽管在此示例中它是。 实例（非静态）工厂方法将以基本相同的方式使用（除了使用factory-bean属性而不是class属性），因此这里不再讨论细节。

### 1.4.2. 依赖关系和详细配置

​	如上一节所述，您可以将bean属性和构造函数参数定义为对其他托管bean（协作者）的引用，或者作为内联定义的值。  Spring的基于XML的配置元数据为此目的支持其<property />和<constructor-arg />元素中的子元素类型。

#### 直值（基元，字符串等）

<property />元素的value属性将属性或构造函数参数指定为人类可读的字符串表示形式。  Spring的[conversion service](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#core-convert-ConversionService-API) 用于将这些值从String转换为属性或参数的实际类型。

```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <!-- results in a setDriverClassName(String) call -->
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="masterkaoli"/>
</bean>
```

以下示例使用 [p-namespace](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-p-namespace)  进行更简洁的XML配置。

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close"
        p:driverClassName="com.mysql.jdbc.Driver"
        p:url="jdbc:mysql://localhost:3306/mydb"
        p:username="root"
        p:password="masterkaoli"/>

</beans>
```

前面的XML更简洁; 但是，除非您在创建bean定义时使用支持自动属性完成的IntelliJ IDEA或Spring Tool Suite（STS）等IDE，否则会在运行时而不是设计时发现拼写错误。 强烈建议使用此类IDE帮助。

您还可以将java.util.Properties实例配置为：

```xml
<bean id="mappings"
    class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">

    <!-- typed as a java.util.Properties -->
    <property name="properties">
        <value>
            jdbc.driver.className=com.mysql.jdbc.Driver
            jdbc.url=jdbc:mysql://localhost:3306/mydb
        </value>
    </property>
</bean>
```

Spring容器通过使用JavaBeans PropertyEditor机制将<value />元素内的文本转换为java.util.Properties实例。 这是一个很好的快捷方式，并且是Spring团队支持在value属性样式上使用嵌套<value />元素的少数几个地方之一。

#### idref元素

idref元素只是一种防错方法，可以将容器中另一个bean的id（字符串值 - 而不是引用）传递给<constructor-arg />或<property />元素。

```xml
<bean id="theTargetBean" class="..."/>

<bean id="theClientBean" class="...">
    <property name="targetName">
        <idref bean="theTargetBean"/>
    </property>
</bean>
```

上面的bean定义代码段与下面的代码段完全等效（在运行时）：

```xml
<bean id="theTargetBean" class="..." />

<bean id="client" class="...">
    <property name="targetName" value="theTargetBean"/>
</bean>
```

第一种形式优于第二种形式，因为使用idref标签允许容器在部署时验证引用的命名bean实际存在。 在第二个变体中，不对传递给客户端bean的targetName属性的值执行验证。 当客户端bean实际被实例化时，才会发现错别字（最可能是致命的结果）。 如果客户端bean是原型bean，则只能在部署容器后很长时间才能发现此错误和产生的异常。

> 4.0 beans xsd不再支持idref元素的local属性，因为它不再提供常规bean引用的值。 升级到4.0架构时，只需将现有的idref本地引用更改为idref bean。

<idref />元素带来值的一个常见位置（至少在Spring 2.0之前的版本中）是在ProxyFactoryBean bean定义中的[AOP interceptors](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#aop-pfb-1) 的配置中。 指定拦截器名称时使用<idref />元素可以防止拼写错误的拦截器ID。

#### 引用其他bean（协作者）!!!!!!!!!!!!!!!!!!

ref元素是<constructor-arg />或<property />定义元素中的最后一个元素。 在这里，您可以将bean的指定属性的值设置为对容器管理的另一个bean（协作者）的引用。 引用的bean是bean的依赖项，其属性将被设置，并且在设置属性之前根据需要按需初始化。  （如果协作者是单例bean，它可能已由容器初始化。）所有引用最终都是对另一个对象的引用。 范围和验证取决于您是否通过bean，local或parent属性指定其他对象的id / name。

通过<ref />标记的bean属性指定目标bean是最常用的形式，并允许在同一容器或父容器中创建对任何bean的引用，而不管它是否在同一XML文件中。  bean属性的值可以与目标bean的id属性相同，也可以作为目标bean的name属性中的值之一。

```xml
<ref bean="someBean"/>
```

通过parent属性指定目标bean会创建对当前容器的父容器中的bean的引用。  parent属性的值可以与目标bean的id属性相同，也可以与目标bean的name属性中的某个值相同，并且目标bean必须位于当前bean的父容器中。 您主要在拥有容器层次结构并且希望将现有bean包装在父容器中并使用与父bean具有相同名称的代理时使用此Bean引用变体。

```xml
<!-- in the parent context -->
<bean id="accountService" class="com.foo.SimpleAccountService">
    <!-- insert dependencies as required as here -->
</bean>
```

```xml
<!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target">
        <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
    </property>
    <!-- insert other configuration and dependencies as required here -->
</bean>
```

> 4.0 bean xsd不再支持ref元素的local属性，因为它不再提供常规bean引用的值。 升级到4.0架构时，只需将现有的ref本地引用更改为ref bean。

#### 内部bean

<property />或<constructor-arg />元素中的<bean />元素定义了一个所谓的内部bean。

```xml
<bean id="outer" class="...">
    <!-- instead of using a reference to a target bean, simply define the target bean inline -->
    <property name="target">
        <bean class="com.example.Person"> <!-- this is the inner bean -->
            <property name="name" value="Fiona Apple"/>
            <property name="age" value="25"/>
        </bean>
    </property>
</bean>
```

内部bean定义不需要定义的id或名称; 如果指定，则容器不使用这样的值作为标识符。 容器还会在创建时忽略范围标志：内部bean始终是匿名的，并且始终使用外部bean创建它们。 不能将内部bean注入协作bean而不是封闭bean，或者独立访问它们。

作为极端情况，可以从自定义范围接收销毁回调，例如， 对于包含在单例bean中的请求范围的内部bean：内部bean实例的创建将绑定到其包含的bean，但是销毁回调允许它参与请求范围的生命周期。 这不是常见的情况; 内部bean通常只是共享其包含bean的范围。

#### Collections

在<list />，<set />，<map />和<props />元素中，分别设置Java Collection类型List，Set，Map和Properties的属性和参数。

```xml
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
```

映射键或值的值或设置值也可以是以下任何元素：

```xml
bean | ref | idref | list | set | map | props | value | null
```

##### Collection merging 

Spring容器还支持集合的合并。 应用程序开发人员可以定义父样式的<list />，<map />，<set />或<props />元素，并具有子样式<list />，<map />，<set />或 <props />元素继承并覆盖父集合中的值。 也就是说，子集合的值是合并父集合和子集合的元素的结果，子集合的元素覆盖父集合中指定的值。

关于合并的这一部分讨论了父子bean机制。 不熟悉父母和子bean定义的读者可能希望在继续之前阅读 *[relevant section](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-child-bean-definitions)*  。

以下示例演示了集合合并：

```xml
<beans>
    <bean id="parent" abstract="true" class="example.ComplexObject">
        <property name="adminEmails">
            <props>
                <prop key="administrator">administrator@example.com</prop>
                <prop key="support">support@example.com</prop>
            </props>
        </property>
    </bean>
    <bean id="child" parent="parent">
        <property name="adminEmails">
            <!-- the merge is specified on the child collection definition -->
            <props merge="true">
                <prop key="sales">sales@example.com</prop>
                <prop key="support">support@example.co.uk</prop>
            </props>
        </property>
    </bean>
<beans>
```

请注意在子bean定义的adminEmails属性的<props />元素上使用merge = true属性。 当容器解析并实例化子bean时，生成的实例具有adminEmails属性集合，该集合包含将子管理员集合与父管理员adminEmails集合合并的结果。

```
administrator=administrator@example.com
sales=sales@example.com
support=support@example.co.uk
```

子属性集合的值集继承父<props />的所有属性元素，子值的支持值覆盖父集合中的值。

此合并行为同样适用于<list />，<map />和<set />集合类型。 在<list />元素的特定情况下，保持与List集合类型相关联的语义，即有序的值集合的概念; 父级的值位于所有子级列表的值之前。 对于Map，Set和Properties集合类型，不存在排序。 因此，对于作为容器内部使用的关联Map，Set和Properties实现类型的基础的集合类型，没有排序语义有效。

##### Collection merging 的局限性

您不能合并不同的集合类型（例如Map和List），如果您尝试这样做，则会抛出相应的Exception。 必须在较低的继承子定义上指定merge属性; 在父集合定义上指定merge属性是多余的，不会导致所需的合并。

##### Strongly-typed collection 强类型集合

