# springboot2.0配置多数据源出错HikariPool-1 - jdbcUrl is required with driverClassName.

出现如下错误：

```
2018-11-19 10:07:26,001 [http-nio-8080-exec-2] ERROR com.zaxxer.hikari.HikariConfig.validate(HikariConfig.java:1058) - HikariPool-1 - jdbcUrl is required with driverClassName.
2018-11-19 10:07:26,007 [http-nio-8080-exec-2] ERROR o.a.c.c.C.[.[.[.[dispatcherServlet].log(DirectJDKLog.java:182) - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database.  Cause: java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.
### Cause: java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.] with root cause
java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.
	at com.zaxxer.hikari.HikariConfig.validate(HikariConfig.java:1059)
	at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:109)
	at org.springframework.jdbc.datasource.DataSourceUtils.fetchConnection(DataSourceUtils.java:151)
	at org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection(DataSourceUtils.java:115)
	at org.springframework.jdbc.datasource.DataSourceUtils.getConnection(DataSourceUtils.java:78)
	at org.mybatis.spring.transaction.SpringManagedTransaction.openConnection(SpringManagedTransaction.java:82)
	at org.mybatis.spring.transaction.SpringManagedTransaction.getConnection(SpringManagedTransaction.java:68)
	at org.apache.ibatis.executor.BaseExecutor.getConnection(BaseExecutor.java:338)
	at org.apache.ibatis.executor.SimpleExecutor.prepareStatement(SimpleExecutor.java:84)
	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:49)
	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:117)
	at org.apache.ibatis.executor.CachingExecutor.update(CachingExecutor.java:76)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)
	at com.sun.proxy.$Proxy105.update(Unknown Source)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:198)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:185)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)
	at com.sun.proxy.$Proxy82.insert(Unknown Source)
	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:278)
	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:58)
	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)
	at com.sun.proxy.$Proxy91.insert(Unknown Source)
	at com.TT.demo.controller.TestController.testMultidatasource(TestController.java:49)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:635)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:742)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.apache.catalina.filters.RemoteIpFilter.doFilter(RemoteIpFilter.java:882)
	at org.apache.catalina.filters.RemoteIpFilter.doFilter(RemoteIpFilter.java:913)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at com.TT.demo.WebConfiguration$MyFilter.doFilter(WebConfiguration.java:56)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:81)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.session.web.http.SessionRepositoryFilter.doFilterInternal(SessionRepositoryFilter.java:147)
	at org.springframework.session.web.http.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:81)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:496)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803)
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:790)
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1468)
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.lang.Thread.run(Thread.java:748)
```

## 解决方法：

**把application.xml的spring.datasource.《前缀》.url改成spring.datasource.《前缀》.jdbc-url即可**

问题是jdbcurl缺失driverClassName,我没有配置jdbcurl？回去看application.xml的数据源配置。发现我配置的是spring.datasource.spring.url。那么问题来了，之前单数据源的时候就可以用url配置，为什么多数据源就要用jdbc-url呢？等有空在仔细进入源码看看吧。

## 环境：

application.xml

```properties
logging.config=classpath:logging-config.xml

#这里是多数据源
spring.datasource.spring.url=jdbc:mysql://localhost:3306/spring?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.spring.username=root
spring.datasource.spring.password=root
spring.datasource.spring.driver-class-name=com.mysql.jdbc.Driver

spring.datasource.springboot.url=jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.springboot.username=root
spring.datasource.springboot.password=root
spring.datasource.springboot.driver-class-name=com.mysql.jdbc.Driver


# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=192.168.203.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=200ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=2000ms


```

多数据源配置类：

PrimaryDatasourceConfig.java(主)

```java
package com.TT.demo.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.TT.demo.dao.spring", sqlSessionTemplateRef  = "springSqlSessionTemplate")
public class PrimaryDataSourceConfig {

    @Bean(name = "springDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.spring")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "springSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("springDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/spring/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "springTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("springDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "springSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("springSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

SecondDataSourceConfig.java(副)

```java
package com.TT.demo.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.TT.demo.dao.springboot", sqlSessionTemplateRef  = "springbootSqlSessionTemplate")
public class SecondDataSourceConfig {

    @Bean(name = "springbootDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.springboot")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "springbootSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("springbootDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/springboot/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "springbootTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("springbootDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "springbootSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("springbootSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

