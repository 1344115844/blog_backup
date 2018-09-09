# shiro

## 简介

Apache Shiro是一个强大而灵活的开源安全框架，它能够干净利落地处理身份认证，授权，企业会话管理和加密

1. 验证用户
2. 对用户执行访问控制，如： 判断用户是否拥有角色admin。判断用户是否拥有访问的权限
3. 在任何环境下使用 Session API。例如CS程序。
4. 可以使用多个用户数据源。例如一个是oracle用户库，另外一个是mysql用户库。
5. 单点登录（SSO）功能。 
6. “Remember Me”服务 ，类似购物车的功能，shiro官方建议开启

## 组件

![img](shiro简介和使用.assets/ShiroFeatures_Authentication.png)

Shiro的4大部分——身份验证，授权，会话管理和加密 

1. Authentication：身份验证，简称“登录”。
2. Authorization：授权，给用户分配角色或 者权限资源
3. Session Management：用户session管理器，可以让CS程序也使用session来控制权限
4. Cryptography：把JDK中复杂的密码加密方式进行封装。

![img](https://shiro.apache.org/assets/images/ShiroAuthenticationSequence.png)

## 流程

![1536490740652](shiro简介和使用.assets/1536490740652.png)

### 概念

- Subject
  Subject 是与程序进行交互的对象，可以是人也可以是服务或者其他，通常就理解为用户。
  所有Subject 实例都必须绑定到一个SecurityManager上。我们与一个 Subject 交互，运行时shiro会自动转化为与 SecurityManager交互的特定 subject的交互。
- SecurityManager
  SecurityManager 是 Shiro的核心，初始化时协调各个模块运行。然而，一旦 SecurityManager协调完毕，SecurityManager 会被单独留下，且我们只需要去操作Subject即可，无需操作SecurityManager 。 但是我们得知道，当我们正与一个 Subject 进行交互时，实质上是 SecurityManager在处理 Subject 安全操作。
- Realms
  Realms在 Shiro中作为应用程序和安全数据之间的“桥梁”或“连接器”。他获取安全数据来判断subject是否能够登录，subject拥有什么权限。他有点类似DAO。在配置realms时，需要至少一个realm。而且Shiro提供了一些常用的 Realms来连接数据源，如LDAP数据源的JndiLdapRealm，JDBC数据源的JdbcRealm，ini文件数据源的IniRealm，properties文件数据源的PropertiesRealm，等等。我们也可以插入自己的 Realm实现来代表自定义的数据源。 像其他组件一样，Realms也是由SecurityManager控制

### 实体图

![1536490852871](shiro简介和使用.assets/1536490852871.png)

1. Subject(org.apache.shiro.subject.Subject):
   简称用户
2. SecurityManager(org.apache.shiro.mgt.SecurityManager) 
   如上所述，SecurityManager是shiro的核心，协调shiro的各个组件

3. Authenticator(org.apache.shiro.authc.Authenticator)： 
   登录控制

> 注：Authentication Strategy
> (org.apache.shiro.authc.pam.AuthenticationStrategy) 
> 如果存在多个realm，则接口AuthenticationStrategy会确定什么样算是登录成功（例如，如果一个Realm成功，而其他的均失败，是否登录成功？）。 

4. Authorizer(org.apache.shiro.authz.Authorizer) ：
   决定subject能拥有什么样角色或者权限。

5. SessionManager(org.apache.shiro.session.SessionManager) ：
   创建和管理用户session。通过设置这个管理器，shiro可以在任何环境下使用session。

6. CacheManager(org.apahce.shiro.cache.CacheManager) ：
   缓存管理器，可以减少不必要的后台访问。提高应用效率，增加用户体验。

7. Cryptography(org.apache.shiro.crypto.*) :
   Shiro的api大幅度简化java api中繁琐的密码加密。

8. Realms(org.apache.shiro.realm.Realm) ：
   程序与安全数据的桥梁

## 配置

### 涉及的jar

| Jar包名称                     | 版本  |
| ----------------------------- | ----- |
| 核心包shiro-core              | 1.2.0 |
| Web相关包shiro-web            | 1.2.0 |
| 缓存包shiro-ehcache           | 1.2.0 |
| 与spring整合包shiro-spring    | 1.2.0 |
| Ehcache缓存核心包ehcache-core | 2.5.3 |
| Shiro自身日志包slf4j-jdk14    | 1.6.4 |

### 与spring整合

1. 在web.xml中配置shiro的过滤器

![1536491038635](shiro简介和使用.assets/1536491038635.png)

2. 在Spring的applicationContext.xml中添加shiro配置 
   ![1536491089617](shiro简介和使用.assets/1536491089617.png)

   securityManager：这个属性是必须的。

   loginUrl ：没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。	

   successUrl ：登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。

   unauthorizedUrl ：没有权限默认跳转的页面。

   | 过滤器简称 | 对应的java类                                                 |
   | ---------- | ------------------------------------------------------------ |
   | anon       | org.apache.shiro.web.filter.authc.AnonymousFilter            |
   | authc      | org.apache.shiro.web.filter.authc.FormAuthenticationFilter   |
   | authcBasic | org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter |
   | perms      | org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter |
   | port       | org.apache.shiro.web.filter.authz.PortFilter                 |
   | rest       | org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter |
   | roles      | org.apache.shiro.web.filter.authz.RolesAuthorizationFilter   |
   | ssl        | org.apache.shiro.web.filter.authz.SslFilter                  |
   | user       | org.apache.shiro.web.filter.authc.UserFilter                 |
   | logout     | org.apache.shiro.web.filter.authc.LogoutFilter               |

   - anon:例子/admins/**=anon 没有参数，表示可以匿名使用。

   - authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数

   - roles：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。

   - perms：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如 

     ```properties
     /admins/user/**=perms["user:add:*,user:modify:*"]
     ```

     当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。

   - rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。

   - port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到

   - schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString
     是你访问的url里的？后面的参数。

   - authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证

   - ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https

   - user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

   > 注：anon，authcBasic，auchc，user是认证过滤器，
   > perms，roles，ssl，rest，port是授权过滤器

3.  在applicationContext.xml中添加securityManagerper配置
   ![1536491336376](shiro简介和使用.assets/1536491336376.png)

4. 配置bosRealm(自定义)
   ![1536491367436](shiro简介和使用.assets/1536491367436.png)
   ![1536491381393](shiro简介和使用.assets/1536491381393.png)

5. 配置shiro注解模式
   ![1536491396258](shiro简介和使用.assets/1536491396258.png)

   @RequiresAuthentication：验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。

   @ RequiresUser：验证用户是否被记忆，user有两种含义：一种是成功登录的（subject.isAuthenticated() 结果为true）；另外一种是被记忆的（ subject.isRemembered()结果为true）。

   @ RequiresGuest：验证是否是一个guest的请求，与@ RequiresUser完全相反。换言之，RequiresUser  == ! RequiresGuest 。此时subject.getPrincipal() 结果为null.

   @ RequiresRoles：例如：

   ```java
   @RequiresRoles("aRoleName");
   	void someMethod();
   ```

   如果subject中有aRoleName角色才可以访问方法someMethod。如果没有这个权限则会抛出异常AuthorizationException。

   @RequiresPermissions:例如：

   ```java
    @RequiresPermissions( {"file:read", "write:aFile.txt"} )
    void someMethod();
   ```

   要求subject中必须同时含有file:read和write:aFile.txt的权限才能执行方法someMethod()。否则抛出异常AuthorizationException。

   6. 自定义realm

      ```xml
      <!--自定义的myRealm 继承自AuthorizingRealm，也可以选择shiro提供的 -->
      <bean id="myRealm" class="com.yada.shiro.MyReam"></bean>
      ```

      ```java
      //这是授权方法
      protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      	String userName = (String) getAvailablePrincipal(principals);
      	//TODO 通过用户名获得用户的所有资源，并把资源存入info中
      	…………………….
      	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      	info.setStringPermissions(set集合);
      	info.setRoles(set集合);
      	info.setObjectPermissions(set集合);
      	return info;
      }
      ```

      ```java
      //这是认证方法
      protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      	//token中储存着输入的用户名和密码
      	UsernamePasswordToken upToken = (UsernamePasswordToken)token;
      	//获得用户名与密码
      	String username = upToken.getUsername();
      	String password = String.valueOf(upToken.getPassword());
      	//TODO 与数据库中用户名和密码进行比对。比对成功则返回info，比对失败则抛出对应信息的异常AuthenticationException
      	…………………..
      	SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password .toCharArray(),getName());
      	return info;
      }
      ```

   7. 自定义登录

      ```java
      //创建用户名和密码的令牌
      UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassWord());
      //记录该令牌，如果不记录则类似购物车功能不能使用。
      token.setRememberMe(true);
      //subject理解成权限对象。类似user
      Subject subject = SecurityUtils.getSubject();
      try {
      subject.login(token);
      } catch (UnknownAccountException ex) {//用户名没有找到。
      } catch (IncorrectCredentialsException ex) {//用户名密码不匹配。
      }catch (AuthenticationException e) {//其他的登录错误
      }
      //验证是否成功登录的方法
      if (subject.isAuthenticated()) {
      }
      ```

   8. 自定义登出

      ```java
      Subject subject = SecurityUtils.getSubject();
      subject.logout();
      ```

   9. 基于编码的角色授权实现

      ```java
      Subject currentUser = SecurityUtils.getSubject();  
      if (currentUser.hasRole("administrator")) {  
          //拥有角色administrator
      } else {  
          //没有角色处理
      }  
      
      ```

   10. 断言方式控制

       ```java
       Subject currentUser = SecurityUtils.getSubject();  
       //如果没有角色admin，则会抛出异常，someMethod()也不会被执行
       currentUser.checkRole(“admin");  
       someMethod();  
       ```

   11. 在JSP上的TAG实现

       | 标签名称                            | 标签条件（均是显示标签内容） |
       | ----------------------------------- | ---------------------------- |
       | <shiro:authenticated>               | 登录之后                     |
       | <shiro:notAuthenticated>            | 不在登录状态时               |
       | <shiro:guest>                       | 用户在没有RememberMe时       |
       | <shiro:user>                        | 用户在RememberMe时           |
       | <shiro:hasAnyRoles name="abc,123" > | 在有abc或者123角色时         |
       | <shiro:hasRole name="abc">          | 拥有角色abc                  |
       | <shiro:lacksRole name="abc">        | 没有角色abc                  |
       | <shiro:hasPermission name="abc">    | 拥有权限资源abc              |
       | <shiro:lacksPermission name="abc">  | 没有abc权限资源              |
       | <shiro:principal>                   | 默认显示用户名称             |

