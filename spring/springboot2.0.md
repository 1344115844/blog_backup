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