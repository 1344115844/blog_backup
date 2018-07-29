# MySQL读写分离

![1527647433241](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527647433241.png)

分布式集群带来高并发,高容量的特性,但是数据一致性就成了问题

### 主要解决两个问题

#### 1. 应用层控制数据源

#### 2. 数据源之间的数据同步

### 多数据源

1. 先停止mysql服务
2. 把MySQL server整个目录拷过来,放到其他目录,修改my.ini
3. 去到复制的那个目录的bin下,执行mysql初始化, `mysqld -initialize -insecure -user=mysql`
4. 安装服务,mysqld -install mysql3307

注意:服务的路径应该是复制的那个目录的mysqld,如果不是,可能my.ini配置问题

### 主从复制实践windows:

假设你什么都没装,那么先从mysql官网下载maysql server 7.7.22 

我现在的时间是2018.5.30.请记住版本

#### 安装好mysql,作为主库

如下图所示.但是数据库data文件不是在这里,

![1527673630037](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527673630037.png)

数据库的存储文件在如下图所示的路径,是隐藏文件夹下的.

![1527673734789](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527673734789.png)

这些可以从my.ini看到的配置

![1527673806650](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527673806650.png)

修改主库配置文件my.ini

![1527674082010](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674082010.png)

主要修改log-bin这里是主库的log文件的位置.当产生主从复制,其文件就会存储在这个位置,记得如果是多文件夹的话,对应的位置必须要有这个文件夹.否则报错

binlog-do-db=project-manager这里配置的是需要主从复制的数据库,这个也是我的其中一个数据库.如下图所示.

![1527674554671](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674554671.png)

当然也可以配置多个,也可以配置不需要主从的数据库,但是我懒,一般也是这样子配置的,所以不做多余的说明,如果想深入了解.请看这篇https://segmentfault.com/a/1190000009922696

到这里主库配置完毕!

#### 复制主库,作为从库

![1527673905643](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527673905643.png)

把上面的文件夹复制到从库的位置,我这里选择d盘

![1527673957217](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527673957217.png)

如上图我复制了两个,可以看到我用端口命名.

![1527674292658](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674292658.png)

接下来就是修改从库的配置.可以看到3307下面并没有my.ini配置文件,那么就需要自己创建或者去主库那里复制过来.我选择复制过来.需要修改从库的port,basedir,datadir

从英文可以知道他们代表意思,端口,基本路径,数据路径.

![1527674328301](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674328301.png)

修改完毕后,就需要配置从库,记住server_id不能和主库的server_id重复

![1527674449983](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674449983.png)

这里最重要的是replicate-do-db=project-manager.

那么到这里从库配置完毕

接下来开始初始化从库

用管理员打开cmd,进入**从库**目录的bin目录下,敲这个指令

`mysqld -initialize -insecure -user=mysql`

这里会生成一些文件到datadir下面的路径,如下图所示

![1527674925337](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674925337.png)

圈出来的是生成的,其余那些是后面生成的,还没完成.

还需要吧主库的一些文件给拷过来.把下图的那些文件夹和文件都考过去把,这些文件夹都是一些数据库,每个人的数据库都不一样,只要把文件夹都复制过去就好了,肯定会提示要覆盖ibdata1文件的,直接覆盖就好.不然下面会报错

![1527674992262](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527674992262.png)

接下来敲这个安装mysql3307服务项

`mysqld -install mysql3307 `

安装成功后,启动两个数据库服务.

可以用cmd(管理员),打如下指令

`net start mysql57`这里的mysql57是我主库的服务项

`net start mysql3307`

启动成功后

 到这里从库配置王弼

#### 配置主从库通信

这里就是最后的部分了

用Navicat打开主库,新建查询,执行 `show master status`

![1527675308634](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675308634.png)

得到如下的信息,记住file 和position字段

![1527675370899](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675370899.png)

打开从库,配置主库的信息,打开从库后,新建查询,执行下面命令

`change master to master_host='127.0.0.1',master_port=3306,master_user='root',master_password='root',master_log_file='master-bin.000003',master_log_pos=154;`

执行成功后,是执行成功后

再执行 `start SLAVE`

再执行 `show slave status`查看从库状态

![1527675595556](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675595556.png)

如上图所示,哪两个字段的状态必须是yes,否则不成功,看到第一个字段,Waiting for master to send event,则表示成功

#### 最后验证主从复制

![1527675694291](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675694291.png)

打开其中一个表插入一条数据

![1527675732771](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675732771.png)

提交后从库下面的表也会有插入,保持数据一致性.到这里主从复制完成了.

![1527675793017](C:\Users\13441\Desktop\md\mama在线\MySQL读写分离.assets\1527675793017.png)



### AOP方式代码控制多数据源切换

要想控制多数据源,必须继承spring提供的一个接口类

重写里面`AbstractRoutingDataSource`的`determineCurrentLookupKey()`

这里需要返回`Map<Object, Object> targetDataSources`的 key,那么在spring配置一个bean吧,当然也可以写死,或者new 一个对象,但是spring还是推荐用配置.

注意,为什么会知道呢?也是百度和看了一下`AbstractRoutingDataSource`的源码的

那么就需要配置spring了如下,我这里配置一个主库和两个从库,

mysql怎么配置主从复制?上面一篇已经说明了

看下面配置spring-cfg.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd"
>

    <!--扫描注解生成bean-->
    <context:annotation-config/>
    <!--包扫描-->
    <context:component-scan base-package="com.*"/>

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="mapperLocations" value="classpath:com/*/**/**.xml"/>
    </bean>

    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.*.*.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

    <!--声明事务管理 采用注解方式-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <aop:aspectj-autoproxy/>

    <bean id="switchDataSourceAspect" class="com.*.common.DataSourceAspect"/>
    <aop:config>
        <aop:aspect ref="switchDataSourceAspect">
            <aop:pointcut id="tx" expression="execution(* com.*.*.service.*.*(..))"/>
            <aop:before method="before" pointcut-ref="tx"/>
        </aop:aspect>
    </aop:config>
    <!--数据库设置-->
    <bean id="masterdataSource" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url_m}"/>
        <property name="username" value="${jdbc_username}"/>
        <property name="password" value="${jdbc_password}"/>
    </bean>
    <bean id="slavedataSource_1" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url_s_1}"/>
        <property name="username" value="${jdbc_username}"/>
        <property name="password" value="${jdbc_password}"/>
    </bean>
    <bean id="slavedataSource_2" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url_s_2}"/>
        <property name="username" value="${jdbc_username}"/>
        <property name="password" value="${jdbc_password}"/>
    </bean>
    <bean id="dataSource" class="com.*.common.DynamicDataSource">
        <property name="targetDataSources">
            <map>
                <entry key="master" value-ref="masterdataSource"/>
                <entry key="slave_1" value-ref="slavedataSource_1"/>
                <entry key="slave_2" value-ref="slavedataSource_2"/>
            </map>
        </property>
        <property name="defaultTargetDataSource" ref="masterdataSource"/>
    </bean>


</beans>
```

如上面配置所示,首先三个数据源的配置分别为masterdataSource,slavedataSource_1,slavedataSource_2,其次就是配置datasource了,datasource额里面有两个属性targetdatasources 和 defaultTargetDataSource.

配置好了就可以通过`AbstractRoutingDataSource`的`determineCurrentLookupKey()`传入的key来控制用那个数据源.

源码地址https://github.com/1344115844/mysql-master-slave,clone自己看吧

