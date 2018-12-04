# idea使用maven从0整合ssm+shiro

## 1. 搭建环境

idea2017

maven3

jdk1.8

## 2. 整合ssm

1. 首先新建一个maven工程，空的也无所谓，构建目录结构，如图1.

   ![图1](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\1526090274616.png)

   ​								图1

   构建maven工程的类型，可以在project structure里面看到，如图2所示
   ![1526090883085](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\1526090883085.png)

   ​								图2

   目录搭建好了，可以开始整合ssm了。

   首先把jar给导入，maven就是帮我们干这个事情的。除了jar包的依赖管理，还有编译的一些配置可以设置。我把我的maven的pom.xml给贴上来吧。如果不知道怎么弄maven，你可以先去看一下idea 配置maven的blog。如果是eclipse的话，请你走吧，我试过帮我同学弄，出现很多问题，比如xsd重复这些，如果你是新手eclipse和idea都不熟悉，你还是下载idea吧。然后边百度边使用idea。

   ```
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <groupId>com.su</groupId>
       <artifactId>ssm</artifactId>
       <packaging>war</packaging>
       <version>1.0-SNAPSHOT</version>
       <name>ssm Maven Webapp</name>
       <url>http://maven.apache.org</url>
       <properties>
           <spring.version>4.1.4.RELEASE</spring.version>
           <mybatis.version>3.2.8</mybatis.version>
           <slf4j.version>1.7.7</slf4j.version>
           <log4j.version>1.2.17</log4j.version>
           <quartz.version>2.2.2</quartz.version>
       </properties>
   
   
       <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-core</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <!-- 添加spring-tx包 -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-tx</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <!-- 添加spring-jdbc包 -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-jdbc</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <!-- 为了方便进行单元测试，添加spring-test包 -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-test</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <!--添加spring-web包 -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-web</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aop</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context-support</artifactId>
               <version>${spring.version}</version>
           </dependency>
           <!--添加aspectjweaver包 -->
           <dependency>
               <groupId>org.aspectj</groupId>
               <artifactId>aspectjweaver</artifactId>
               <version>1.8.5</version>
           </dependency>
           <!-- 添加mybatis的核心包 -->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>${mybatis.version}</version>
           </dependency>
           <!-- 添加mybatis与Spring整合的核心包 -->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-spring</artifactId>
               <version>1.2.2</version>
           </dependency>
           <!-- 添加servlet3.0核心包 -->
           <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>javax.servlet-api</artifactId>
               <version>3.0.1</version>
           </dependency>
           <dependency>
               <groupId>javax.servlet.jsp</groupId>
               <artifactId>javax.servlet.jsp-api</artifactId>
               <version>2.3.2-b01</version>
           </dependency>
           <!-- jstl -->
           <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>jstl</artifactId>
               <version>1.2</version>
           </dependency>
           <!-- 添加mysql驱动包 -->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>5.1.34</version>
           </dependency>
           <!--end-->
   
           <!-- 添加druid连接池包 -->
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
               <version>1.0.12</version>
           </dependency>
           <!--end-->
   
           <!-- 日志文件管理包 -->
           <!-- log start -->
           <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>${log4j.version}</version>
           </dependency>
           <!-- 格式化对象，方便输出日志 -->
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>fastjson</artifactId>
               <version>1.1.41</version>
           </dependency>
   
   
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-api</artifactId>
               <version>${slf4j.version}</version>
           </dependency>
   
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-log4j12</artifactId>
               <version>${slf4j.version}</version>
           </dependency>
           <!-- log end -->
   
           <!--apache shiro  -->
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-core</artifactId>
               <version>1.2.2</version>
           </dependency>
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-web</artifactId>
               <version>1.2.3</version>
           </dependency>
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-spring</artifactId>
               <version>1.2.3</version>
           </dependency>
           <!--end-->
   
   
           <!-- 映入JSON -->
           <dependency>
               <groupId>org.codehaus.jackson</groupId>
               <artifactId>jackson-mapper-asl</artifactId>
               <version>1.9.13</version>
           </dependency>
           <!--end-->
   
           <!-- 上传组件包 -->
           <dependency>
               <groupId>commons-fileupload</groupId>
               <artifactId>commons-fileupload</artifactId>
               <version>1.3.1</version>
           </dependency>
           <dependency>
               <groupId>commons-io</groupId>
               <artifactId>commons-io</artifactId>
               <version>2.4</version>
           </dependency>
           <dependency>
               <groupId>commons-codec</groupId>
               <artifactId>commons-codec</artifactId>
               <version>1.9</version>
           </dependency>
           <!--end-->
   
           <!--定時器任務調度框架-->
           <dependency>
               <groupId>org.quartz-scheduler</groupId>
               <artifactId>quartz</artifactId>
               <version>${quartz.version}</version>
           </dependency>
           <!--end-->
   
           <!--velocity視圖模板-->
           <dependency>
               <groupId>org.apache.velocity</groupId>
               <artifactId>velocity</artifactId>
               <version>1.7</version>
           </dependency>
           <dependency>
               <groupId>org.apache.velocity</groupId>
               <artifactId>velocity-tools</artifactId>
               <version>2.0</version>
           </dependency>
           <!--end-->
   
   
           <!--mybatis generator -->
           <dependency>
               <groupId>org.mybatis.generator</groupId>
               <artifactId>mybatis-generator-core</artifactId>
               <version>1.3.2</version>
   
           </dependency>
   
           <dependency>
               <groupId>org.mybatis.generator</groupId>
               <artifactId>mybatis-generator-core</artifactId>
               <version>RELEASE</version>
           </dependency>
           <!--end-->
   
           <!--common包-->
           <dependency>
               <groupId>org.apache.commons</groupId>
               <artifactId>commons-lang3</artifactId>
               <version>3.5</version>
           </dependency>
   
           <!-- mybatis pager 分页插件-->
           <dependency>
               <groupId>com.github.pagehelper</groupId>
               <artifactId>pagehelper</artifactId>
               <version>4.1.0</version>
           </dependency>
           <!--end-->
   
           <!--日期工具-->
           <dependency>
               <groupId>joda-time</groupId>
               <artifactId>joda-time</artifactId>
               <version>2.3</version>
           </dependency>
           <!--emd-->
   
           <!--valid工具包-->
           <dependency>
               <groupId>javax.validation</groupId>
               <artifactId>validation-api</artifactId>
               <version>1.1.0.Final</version>
           </dependency>
   
           <dependency>
               <groupId>org.hibernate</groupId>
               <artifactId>hibernate-validator</artifactId>
               <version>5.1.0.Final</version>
           </dependency>
           <!--end-->
   
       </dependencies>
   
   
       <build>
           <finalName>projectManager</finalName>
           <resources>
               <resource>
                   <directory>src/main/java</directory>
                   <includes>
                       <include>**/*.xml</include>
                   </includes>
               </resource>
               <resource>
                   <directory>src/main/resources</directory>
               </resource>
           </resources>
   
           <plugins>
               <plugin>
                   <groupId>org.mybatis.generator</groupId>
                   <artifactId>mybatis-generator-maven-plugin</artifactId>
                   <version>1.3.2</version>
                   <configuration>
                       <verbose>true</verbose>
                       <overwrite>true</overwrite>
                   </configuration>
               </plugin>
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <configuration>
                       <source>1.8</source>
                       <target>1.8</target>
                   </configuration>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

   jar包导进来了。你可能会问这么多jar包啊，我怎么知道。对不起，我也是百度过来的。有些jar包我知道，有些我不知道。抱歉。

   继续。web工程都先看WEB-INF下面的web.xml的配置，无论哪个web工程。

   那么我先把我整理过的web.xml贴上来。

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns="http://java.sun.com/xml/ns/javaee"
            xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
            version="3.0">
     <display-name>ssm</display-name>
     <context-param>
       <param-name>contextConfigLocation</param-name>
       <param-value>classpath:./spring-cfg.xml</param-value>
     </context-param>
   
     <!-- 编码过滤器 -->
     <filter>
       <filter-name>encodingFilter</filter-name>
       <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
       <async-supported>true</async-supported>
       <init-param>
         <param-name>encoding</param-name>
         <param-value>UTF-8</param-value>
       </init-param>
     </filter>
     <filter-mapping>
       <filter-name>encodingFilter</filter-name>
       <url-pattern>/*</url-pattern>
     </filter-mapping>
     <!-- Spring监听器 -->
     <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     </listener>
     <!-- Spring MVC servlet -->
     <servlet>
       <servlet-name>SpringMVC</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
       <init-param>
         <param-name>contextConfigLocation</param-name>
         <param-value>classpath:spring-mvc.xml</param-value>
       </init-param>
       <load-on-startup>1</load-on-startup>
       <async-supported>true</async-supported>
     </servlet>
     <servlet-mapping>
       <servlet-name>SpringMVC</servlet-name>
       <url-pattern>/</url-pattern>
     </servlet-mapping>
     <!--log4j日誌-->
     <context-param>
       <param-name>log4jConfigLocation</param-name>
       <param-value>classpath:log4j.properties</param-value>
     </context-param>
   </web-app>
   
   ```

   稍微来说明一下吧。`<display-name>ssm</display-name>`工程名。

   下面这个，springcontext的配置文件，很重要，一般把这个配置文件独立出去，方便管理，事实上你写到web.xml里面来也是没有什么问题的。但是我没有试过。有兴趣了解一下？然后告诉我？

   ```
     <context-param>
       <param-name>contextConfigLocation</param-name>
       <param-value>classpath:./spring-cfg.xml</param-value>
     </context-param>
   ```

   接下来就是编码过滤器。事实上就是一个filter，因为Tomcat的默认编码不是utf-8

   所以为了保证编码的一致，使用这个编码过滤器，`<filter-mapping>`代表的过滤器的生效路径。

   ```
   <filter>
       <filter-name>encodingFilter</filter-name>
       <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
       <async-supported>true</async-supported>
       <init-param>
         <param-name>encoding</param-name>
         <param-value>UTF-8</param-value>
       </init-param>
     </filter>
     <filter-mapping>
       <filter-name>encodingFilter</filter-name>
       <url-pattern>/*</url-pattern>
     </filter-mapping>
   ```

   接下来就是spring的监听器了，**ContextLoaderListener的作用就是启动Web容器时，自动装配ApplicationContext.xml的配置信息。**因为它实现了ServletContextListener这个接口，在web.xml配置这个监听器，启动容器时，就会默认执行它实现的方法。 

   也就是说，当web容器初始化的时候，spring 的配置文件ApplicationContext.xml也启用了，当然我这里spring的配置文件是spring-cfg.xml。所以监听器决定我们spring是否启动。

   https://www.cnblogs.com/wuchaodzxx/p/6038895.html

   ```
   <!-- Spring监听器 -->
     <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   ```

   ![img](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\935769-20161107210352405-689876543.png)

   继续下一个,就是servlet的配置了，servlet是web的基础，我们没有用框架的时候，就是在web.xml里面配置servlet-class和servlet-mapping的。那么这里的配置就是吧servlet交给springMVC的dispatcherServlet类。适配路径/* 全部路径。就是说所有的url都会进入到dispatchservlet类里面。但你配置  /su/*的时候，那就只能适配/su/ 后面的路径了

   这里就是实现了整合springMVC，而上面就是整合了spring，那么你会问mybatis捏，事实上mybatis是后端和数据交互的中间过程，也是说桥梁。那么我们写业务的时候才需要用到mybatis，所以mybatis是整合到spring里面来，也就是说实在spring-cfg.xml里面配置。

   

   ```
   <!-- Spring MVC servlet -->
     <servlet>
       <servlet-name>SpringMVC</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
       <init-param>
         <param-name>contextConfigLocation</param-name>
         <param-value>classpath:spring-mvc.xml</param-value>
       </init-param>
       <load-on-startup>1</load-on-startup>
       <async-supported>true</async-supported>
     </servlet>
     <servlet-mapping>
       <servlet-name>SpringMVC</servlet-name>
       <url-pattern>/</url-pattern>
     </servlet-mapping>
   ```

   那么接下来就是spring-cfg.xml了，这配置也就是开启aop，扫描注解开启，事务开启。还有整合mybatis，整合就是配置sqlSessionFactory以及扫描mapper。当然你说用xml注入bean也行，我开了注解就注入，然而用xml配置DI我觉得好麻烦，而且又长。打注解比较方便，相比之下。

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.0.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx-4.0.xsd"
   >
   
       <!--开启切面编程自动代理-->
       <aop:aspectj-autoproxy proxy-target-class="true"/>
   
       <!--   (到包中扫描类、方法、属性上是否有注解)-->
       <context:component-scan base-package="com.su" annotation-config="true"/>
   
   
       <context:property-placeholder location="classpath:jdbc.properties"/>
   
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <property name="mapperLocations" value="classpath*:**/dao/*.xml"/>
           <property name="plugins">
               <array>
                   <bean class="com.github.pagehelper.PageHelper">
                       <property name="properties">
                           <value>
                               dialect=mysql
                           </value>
                       </property>
                   </bean>
               </array>
           </property>
       </bean>
   
       <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
           <property name="basePackage" value="com.su.User.dao"/>
           <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
       </bean>
   
       <!--声明事务管理 采用注解方式-->
       <tx:annotation-driven transaction-manager="transactionManager"/>
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"/>
       </bean>
   
       <!--数据库设置-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
             destroy-method="close" init-method="init">
           <property name="driverClassName" value="${db.driverClassName}"/>
           <property name="url" value="${db.url}"/>
           <property name="username" value="${db.username}"/>
           <property name="password" value="${db.password}"/>
           <!-- 连接池启动时的初始值 -->
           <property name="initialSize" value="${db.initialSize}"/>
           <!-- 连接池的最大值 -->
           <property name="maxActive" value="${db.maxActive}"/>
   
           <!-- 最小空闲值.当空闲的连接数少于阀值时，连接池就会预申请去一些连接，以免洪峰来时来不及申请 -->
           <property name="minIdle" value="${db.minIdle}"/>
           <!-- 最大建立连接等待时间。如果超过此时间将接到异常。设为－1表示无限制 -->
           <property name="maxWait" value="${db.maxWait}"/>
           <property name="poolPreparedStatements" value="${db.poolPreparedStatements}"/>
           <property name="maxPoolPreparedStatementPerConnectionSize"
                     value="${db.maxPoolPreparedStatementPerConnectionSize}"/>
           <property name="validationQuery" value="${db.validationQuery}"/>
           <property name="testOnBorrow" value="${db.testOnBorrow}"/>
           <property name="testOnReturn" value="${db.testOnReturn}"/>
           <property name="testWhileIdle" value="${db.testWhileIdle}"/>
           <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
           <property name="timeBetweenEvictionRunsMillis" value="${db.timeBetweenEvictionRunsMillis}"/>
           <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
           <property name="minEvictableIdleTimeMillis" value="${db.minEvictableIdleTimeMillis}"/>
           <!-- 打开removeAbandoned功能 -->
           <property name="removeAbandoned" value="${db.removeAbandoned}"/>
           <!-- 1800秒，也就是30分钟 -->
           <property name="removeAbandonedTimeout" value="${db.removeAbandonedTimeout}"/>
           <!-- 关闭abanded连接时输出错误日志 -->
           <property name="logAbandoned" value="${db.logAbandoned}"/>
           <!-- 监控数据库 -->
           <!-- <property name="filters" value="stat" /> -->
           <property name="filters" value="${db.filters}"/>
       </bean>
       <!--<import resource="spring-job.xml"/>-->
   </beans>
   ```

   接下来就是spring-mvc.xml。开启注解，开启包扫描注解，包扫描component，主要是扫描controller，处理静态资源，以及配置处理器，这里我用的velocity，以前很多人用jsp。我没有导jstl的jar包，你要你自己找dependency导进来。再自己配置

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/mvc
         http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">
   
   
   
       <mvc:annotation-driven>
           <mvc:message-converters>
   
               <bean class="org.springframework.http.converter.StringHttpMessageConverter"/>
               <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                   <property name="supportedMediaTypes">
                       <list>
                           <value>text/html;charset=UTF-8</value>
                           <value>application/json;charset=UTF-8</value>
                       </list>
                   </property>
               </bean>
           </mvc:message-converters>
       </mvc:annotation-driven>
   
   
   
       <!--支持多媒体文件上传-->
       <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
           <!--<property name="maxUploadSize" value="10485760"/> &lt;!&ndash; 表示上传文件最大Size为10M &ndash;&gt;-->
           <property name="maxInMemorySize" value="4096" />
           <property name="defaultEncoding" value="UTF-8"/>
       </bean>
       <!--包扫描-->
       <context:component-scan base-package="com.su.User.controller" />
   
       <!--开启注解扫描-->
       <mvc:annotation-driven/>
       <!--处理静态资源-->
       <mvc:default-servlet-handler/>
   
   
   
   
       <bean id="velocityConfigurer" class="org.springframework.web.servlet.view.velocity.VelocityConfigurer">
           <property name="resourceLoaderPath" value="/WEB-INF/views"/>
           <property name="velocityProperties">
               <props>
                   <prop key="input.encoding">utf-8</prop>
                   <prop key="output.encoding">utf-8</prop>
                   <prop key="file.resource.loader.cache">false</prop>
                   <prop key="file.resource.loader.modificationCheckInterval">1</prop>
                   <prop key="velocimacro.library.autoreload">false</prop>
               </props>
           </property>
       </bean>
   
       <bean class="org.springframework.web.servlet.view.velocity.VelocityViewResolver">
           <property name="suffix" value=".vm"/>
           <property name="contentType" value="text/html;charset=utf-8"/>
           <property name="dateToolAttribute" value="date"/><!--日期函数名称-->
       </bean>
   
   
   
   </beans>
   ```

   到了这里还没好，扫描的那些mapper文件没有，entity也没有，dao也没有，这些没有还会会报错。继续看下去，你可以用mybatis-generator逆向，不会的自行百度。

   我这里简单说一下。

   主要是pom.xml的<bulid>那里吧mybatis-generator导进来。

   然后配置xml，再配置maven

   配置文件generatorConfig.xml为

   ```
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
           "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
   <generatorConfiguration>
   
       <!--classPathEntry:数据库的JDBC驱动 -->
       <classPathEntry
               location="D:\maven3\maven\repository\mysql\mysql-connector-java\5.1.45\mysql-connector-java-5.1.45.jar" />
   
       <context id="MysqlTables" targetRuntime="MyBatis3">
   
           <!-- 注意这里面的顺序确定的，不能随变更改 -->
           <!-- 自定义的分页插件 <plugin type="com.deppon.foss.module.helloworld.shared.PaginationPlugin"/> -->
   
           <!-- 可选的（0 or 1） -->
           <!-- 注释生成器 -->
           <commentGenerator>
               <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
               <property name="suppressAllComments" value="true" />
           </commentGenerator>
   
           <!-- 必须的（1 required） -->
           <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
           <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                           connectionURL="jdbc:mysql://localhost:3306/project-manager"
                           userId="root" password="root">
           </jdbcConnection>
   
           <!-- 可选的（0 or 1） -->
           <!-- 类型转换器或者加类型解析器 -->
           <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer true，把JDBC DECIMAL 和
               NUMERIC 类型解析为java.math.BigDecimal -->
           <javaTypeResolver>
               <property name="forceBigDecimals" value="false" />
           </javaTypeResolver>
   
   
           <!-- 必须的（1 required） -->
           <!-- java模型生成器 -->
           <!-- targetProject:自动生成代码的位置 -->
           <javaModelGenerator targetPackage="com.su.User.entity"
                               targetProject="D:\ProjectCollection\idea\ssm\src\main\java"
                                >
               <!-- TODO enableSubPackages:是否让schema作为包的后缀 -->
               <property name="enableSubPackages" value="true" />
               <!-- 从数据库返回的值被清理前后的空格 -->
               <property name="trimStrings" value="true" />
           </javaModelGenerator>
   
           <!-- 必须的（1 required） -->
           <!-- map xml 生成器 -->
           <sqlMapGenerator targetPackage="com.su.User.dao"
                            targetProject="D:\ProjectCollection\idea\ssm\src\main\java">
               <property name="enableSubPackages" value="true" />
           </sqlMapGenerator>
   
           <!-- 可选的（0 or 1） -->
           <!-- mapper 或者就是dao接口生成器 -->
           <javaClientGenerator targetPackage="com.su.User.dao"
                                targetProject="D:\ProjectCollection\idea\ssm\src\main\java"
                                type="XMLMAPPER">
               <property name="enableSubPackages" value="true" />
           </javaClientGenerator>
   
           <!-- 必须的（1...N） -->
           <!-- pojo 实体生成器 -->
           <!-- tableName:用于自动生成代码的数据库表；domainObjectName:对应于数据库表的javaBean类名 -->
           <!-- schema即为数据库名 可不写 -->
           <table  tableName="user" domainObjectName="User"
                   enableInsert="true" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false"
                   enableSelectByExample="false" selectByExampleQueryId="false">
               <!-- 忽略字段 可选的（0 or 1） -->
               <!-- <ignoreColumn column="is_use" /> -->
               <!--//无论字段是什么类型，生成的类属性都是varchar。 可选的（0 or 1） 测试无效 -->
               <!-- <columnOverride column="city_code" jdbcType="VARCHAR" /> -->
           </table>
   
   
   
       </context>
   </generatorConfiguration>
   ```

   ![1526094868389](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\1526094868389.png)

   逆向生成后，自己写一些controller测试吧。到这里已经搭建好了

   如果报错，查看报错信息，先翻译找错，不行再百度吧。

## 3.整合logback+lombok

首先是pom.xml导包，这里只是pom.xml的一部分，这是logback需要的一些包

```
 <!-- 日志文件管理包 -->
        <!-- log start -->

        <!--logback-->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.1.6</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.1.6</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-access</artifactId>
            <version>1.1.2</version>
        </dependency>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>1.10</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>org.logback-extensions</groupId>
            <artifactId>logback-ext-spring</artifactId>
            <version>0.1.4</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.6</version>
        </dependency>
        <!--end-->
```

配置logback，新建一个logback.xml,这里面有些地方需要改写自己项目的配置，比如日志存放的文件的位置，默认是Tomcat的启动的那个盘的目录下，我的配置是这样子的。logback的好处，自己去百度吧，这里是采用error级别和info级别的区分，有特定需求自己搞吧。

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="LOG_HOME" value="/logs/ssm/" />

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{H:mm} %-5level [%logger{16}] %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="normalLog"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${LOG_HOME}/web.normal.%d{yyyy-MM-dd}.log
            </FileNamePattern>
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <maxFileSize>10MB</maxFileSize>
        </triggeringPolicy>
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{16} - %msg%n
            </pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>DENY</onMatch>
            <onMismatch>ACCEPT</onMismatch>
        </filter>
    </appender>
    <appender name="errorLog"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${LOG_HOME}/web.error.%d{yyyy-MM-dd}.log
            </FileNamePattern>
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <maxFileSize>10MB</maxFileSize>
        </triggeringPolicy>
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{16} - %msg%n
            </pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>


    <logger name="com.su" level="debug" >
        <appender-ref ref="normalLog" />
        <appender-ref ref="errorLog" />
    </logger>


    <root level="info">
        <appender-ref ref="Console" />
    </root>
</configuration>
```

然后在web.xml配置logback

```
  <!--log4j日誌 由于启用了logback日志，此配置废除-->
  <!--<context-param>-->
    <!--<param-name>log4jConfigLocation</param-name>-->
    <!--<param-value>classpath:log4j.properties</param-value>-->
  <!--</context-param>-->


  <!--logback日志-->
  <context-param>
    <param-name>logbackConfigLocation</param-name>
    <param-value>classpath:logback.xml</param-value>
  </context-param>

```



## 4. 整合shiro

首先导包。

```
<!--apache shiro  -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>1.2.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-web</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!--end-->
```

配置spring-cfg.xml

```
<!--整合shiro-->
    <!--切面类-->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor"/>

    <!--自定义Realm-->
    <bean id="myRealm" class="com.su.shiro.MyRealm"/>

    <!--安全管理-->
    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="realm" ref="myRealm"/>
    </bean>

    <!--shiro 过滤器-->
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <!-- Shiro过滤器的核心安全接口，这个属性是必须的-->
        <property name="securityManager" ref="securityManager"/>
        <!--身份认证失败，则跳转到登录页面的配置-->
        <property name="loginUrl" value="/user/login"/>
        <!--权限认证失败，则跳转到指定页面-->
        <property name="unauthorizedUrl" value="/user/login"/>
        <!-- Shiro连接约束配置，即过滤链的定义-->
        <property name="filterChainDefinitions">
            <value>
                /static/**=anon
                /error/**=anon
                /lib/**=anon
                /myjs/**=anon
                /uploads/**=anon
                /user/login*=anon
                /user/logout*=logout
                /**=authc
            </value>
        </property>
    </bean>
    <!--over-->
```

配置spring-mvc.xml

```
<!--開啟shiro的註解-->
    <!-- AOP式方法级权限检查  -->
    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>
    <bean id="defaultAdvisorAutoProxyCreator" class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator" depends-on="lifecycleBeanPostProcessor">
        <!--强制使用cglib创建Action的代理对象-->
        <property name="proxyTargetClass" value="true"/>
    </bean>
    <!--over-->
```

创建一个realm，注意这里只是简单创建一个realm，其实可以创建多个realm，这个需要看需求，我把代码贴出来吧，简化过的代码，有需要可以查看别人的博客，来进行完善

```
package com.su.shiro;



import com.su.user.entity.User;
import com.su.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/4/12 9:40
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserService userServiceImpl;

    //为当前登录成功的用户授予权限和角色，已经登录成功了。
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username=(String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        //这里拿到角色名
        Set<String> roles = null;
        roles.add("user");//赋予user角色，其实你可以放到数据库里面，这里简化
        authorizationInfo.setRoles(roles);
        //这里拿到该角色的权限
        //这里拿到角色名
        Set<String> permissions = null;
        permissions.add("select");//赋予user角色权限，其实你可以放到数据库里面，这里简化
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }


    //验证当前登录的用户，获取认证信息。
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String) token.getPrincipal();//获取用户名
        User user=new User();//这里简化，本来从数据库拿的
        //如果查询不出来则返回null，不通过验证，如果查出来，则返回AuthenticationInfo
        if(user!=null){
            AuthenticationInfo authcInfo =new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),"myRealm");
            return authcInfo;
        }else{
            return null;
        }
    }
}


```

好了，万事俱备，那么就要在web.xml里面注册这个shiro，shiro是基于filter来搞得，那么就要在web.xml里面注册这个filter

```
<!--集成shiro-->
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <!-- 该值缺省为false，表示声明周期由SpringApplicationContext管理，设置为true表示ServletContainer管理 -->
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

那么到这里，shiro已经集成到spring里面了

接下来就是使用shiro来做登录验证，我去找张流程图

认证过程

![img](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\20170312125241625.png) 

授权过程

![img](C:\Users\13441\Desktop\md\spring\idea使用maven从0整合ssm+shiro.assets\20170312142941667.png) 

图片来源https://blog.csdn.net/mine_song/article/details/61616259

然后，我把构建sercurityManager的环境搭建起来吧，简单点就是用controller调用realm的doGetAuthorizationInfo授权,和doGetAuthenticationInfo验证

```
package com.su.shiro;


import com.su.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author Veng Su 1344114844@qq.com
 * @date 2018/4/12 9:40
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserService userServiceImpl;

    //为当前登录成功的用户授予权限和角色，已经登录成功了。
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username=(String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(userServiceImpl.getRoles(username));
//        authorizationInfo.setStringPermissions(userServiceImpl.getPermissions(username));
        return authorizationInfo;
    }


    //验证当前登录的用户，获取认证信息。
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String) token.getPrincipal();//获取用户名
//        User user=userServiceImpl.getByUsername(username);
//        if(user!=null){
//            AuthenticationInfo authcInfo =new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),"myRealm");
//            return authcInfo;
//        }else{
//            return null;
//        }
        return null;
    }
}
```

