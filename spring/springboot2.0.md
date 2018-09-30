# SpringBoot2.0 核心技术

## web 应用

### 传统servlet应用

#### 依赖

```properties
<!--添加web组件-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--end-->
```

#### servlet组件

- Servlet

  - 实现

    - `@WebServlet` 
    - 继承 `httpservlet`
    - 重写 `doget()`

  - URL映射

    - `@WebServlet` 上注册一个`@WebServlet(urlPatterns = "/my/servlet")`

  - 注册

    - `@ServletComponentScan(basePackages = "my.suveng.diveinspringboot.web.servlet")`

- Filter

- Listener

#### servlet注册

##### servlet注解

- `@ServletComponentScan`+
  - `@WebServlet`
  - `@WebFilter`
  - `@WebListener`

##### 异步servlet

##### 非阻塞servlet

### Spring Web MVC 应用

#### Web MVC 视图

-  `ViewResolver`
- `View`

##### 模板引擎

- Thymeleaf
- Freemarker
- JSP

##### 内容协商

- `ContentNegotiationConfigurer`
- `ContentNegotiationStrategy`
- `ContentNegotiatingViewResolver`

##### 异常处理

- `@ExceptionHandler`
- `HandlerExceptionResolver`
  - `ExceptionHandlerExceptionResolver`
- `BasicErrorController`(Spring Boot)

#### Web MVC REST

##### 资源服务

- `@RequestMapping`
  - `@GetMapping`
  - `@PostMapping`
- `@RequestBody`
- `@ResponseBody`

##### 资源跨域

传统解决方案：

- `IFrame`
- `JSONP`

现在流行的

- `CrossOrigin` 注解
- `WebMvcConfigurer#addCorsMappings` 接口

##### 服务发现

- HATEOS

#### Web MVC 核心 

##### 核心架构

##### 处理流程

##### 核心组件

- `DispatcherServlet`
- `HandlerMapping`
- `HadlerAdapter`
- `ViewResolver`

### web flux 应用

#### Reactor 基础

##### Java lambda

##### mono

##### flux

#### web flux 核心 

##### web mvc 注解兼容

- `@Controller`
- `@RequestMapping`
- `@RequestBody`
- `@ResponseBody`

##### 函数式声明

`RouterFunction`

##### 异步非阻塞

- `Servlet 3.1+`
- `Netty reactor`

#### 使用场景

##### 页面渲染



##### REST应用

##### 性能测试

### web server 应用

#### 切换web server

##### 切换其他servlet容器

- tomcat -> jetty

  ```java
  		<dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
              <exclusions>
                  <!-- Exclude the Tomcat dependency -->
                  <exclusion>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-starter-tomcat</artifactId>
                  </exclusion>
              </exclusions>
          </dependency>
  
          <!--Use Jetty instead 为什么web要排除掉tomcat的jar，因为tomcat的优先级高于jetty，所以即使并列，springboot也会默认使用tomcat-->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-jetty</artifactId>
          </dependency>
  ```

#### 替换servlet容器

- web flux

  ```java
          <!--替换servlet容器 替换成web flux 需要注释掉传统的servlet-->
          <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-webflux</artifactId>
          </dependency>
  ```

#### 自定义web servlet server

- `WebServerFactoryCustomizer`

#### 自定义reactive web server

- `ReactiveWebServerFactoryCustomizer`



## 数据相关

### 关系型数据

#### jdbc

##### 依赖

```java
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

##### 数据源

- `javax.sql.DataSource`
- `JDBCTemplate`

##### 自动装配

- `DataSoureAutoConfiguration`

#### JPA

##### 依赖

```java
        <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```
##### 实体映射关系

- `@javax.persistence.OneToOne`
- `@javax.persistence.OneToMany`
- `@javax.persistence.ManyToOne`
- `@javax.persistence.ManyToMany`

##### 实体操作

- `javax.persistence.EntityManager`

##### 自动装配

- `HibernateJpaAutoConfiguration`

#### 事务（Transaction）

##### 依赖

```java
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-tx</artifactId>
</dependency>
```

##### spring事务抽象

- `PlatformTransactionManager`

##### jdbc事务处理

- `DataSourceTransactionManager`

##### 自动装配

- `TransactionAutoConfiguration`

## 功能扩展

### spring boot 应用

`SpringApplication`

#### 失败分析

- `FailureAnalysisReporter`

#### 应用特性

- `SpringApplication` FluentAPI

#### springboot 配置

- 外部化配置
  - `ConfigurationProperty`
- `@Profile`

- 配置属性
  - `PropertySource`

#### spring boot starter



## 运维管理

### springboot Actuator

#### 依赖

```java
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
```
#### 端点（endpoints）

##### web endpoints

##### jmx endpoints

#### 健康检查

`Health`

`HealthIndicator`

#### 指标





