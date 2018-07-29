# Spring Cloud全家桶主要组件及简要介绍

2017年09月01日 17:30:47

阅读数：16147

## 一、微服务简介

微服务是最近的一两年的时间里是很火的一个概念。感觉不学习一下都快跟不上时代的步伐了，下边做一下简单的总结和介绍。

何为微服务？简而言之，微服务架构风格这种开发方法，是以开发一组小型服务的方式来开发一个独立的应用系统的。其中每个小型服务都运行在自己的进程中，并经常采用HTTP资源API这样轻量的机制来相互通信。这些服务围绕业务功能进行构建，并能通过全自动的部署机制来进行独立部署。这些微服务可以使用不同的语言来编写，并且可以使用不同的数据存储技术。对这些微服务我们仅做最低限度的集中管理。

一个微服务一般完成某个特定的功能，比如下单管理、客户管理等等。每一个微服务都是微型六角形应用，都有自己的业务逻辑和适配器。一些微服务还会发布API给其它微服务和应用客户端使用。其它微服务完成一个Web UI，运行时，每一个实例可能是一个云VM或者是Docker容器。

比如，一个前面描述系统可能的分解如下：

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901172818327.png)

总的来说，微服务的主旨是将一个原本独立的系统拆分成多个小型服务，这些小型服务都在各自独立的进程中运行，服务之间通过基于HTTP的RESTful API进行通信协作，并且每个服务都维护着自身的数据存储、业务开发、自动化测试以及独立部署机制。

## 二、微服务的特征

1、每个微服务可独立运行在自己的进程里；

2、一系列独立运行的微服务共同构建起了整个系统；

3、每个服务为独立的业务开发，一个微服务一般完成某个特定的功能，比如：订单管理、用户管理等；

4、微服务之间通过一些轻量的通信机制进行通信，例如通过REST API或者RPC的方式进行调用。

扩展阅读：[深度剖析微服务架构的九大特征](http://developer.51cto.com/art/201608/516401.htm): <http://developer.51cto.com/art/201608/516401.htm>

## 三、微服务的优缺点

1、易于开发和维护 
2、启动较快 
3、局部修改容易部署 
4、技术栈不受限 
5、按需伸缩 
6、DevOps

扩展阅读：[微服务架构的优势与不足 ](http://dockone.io/article/394)：<http://dockone.io/article/394>

## 四、常见微服务框架

**1、服务治理框架**

（1）Dubbo（<http://dubbo.io/>）、Dubbox（当当网对Dubbo的扩展）

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901155605686-1530871127590.png)

扩展阅读：[Dubbo详细介绍与安装使用过程: http://blog.csdn.net/xlgen157387/article/details/51865289](http://blog.csdn.net/xlgen157387/article/details/51865289)

最近的好消息是Dubbo已近重新开始进行运维啦！

（2）Netflix的Eureka、Apache的Consul等。

Spring Cloud Eureka是对Netflix的Eureka的进一步封装。

**2、分布式配置管理**

（1）百度的Disconf

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901160629440.jpg)

（2）360的QConf

（3）Spring Cloud组件中的Config

（3）淘宝的Diamond

**3、批量任务框架**

（1）Spring Cloud组件中的Task 
（2）LTS

**4、服务追踪框架**

。。。

## 五、Spring Cloud全家桶组件

在介绍Spring Cloud 全家桶之前，首先要介绍一下Netflix ，Netflix 是一个很伟大的公司，在Spring Cloud项目中占着重要的作用，Netflix 公司提供了包括Eureka、Hystrix、Zuul、Archaius等在内的很多组件，在微服务架构中至关重要，Spring在Netflix 的基础上，封装了一系列的组件，命名为：Spring Cloud Eureka、Spring Cloud Hystrix、Spring Cloud Zuul等，下边对各个组件进行分别得介绍：

**（1）Spring Cloud Eureka**

我们使用微服务，微服务的本质还是各种API接口的调用，那么我们怎么产生这些接口、产生了这些接口之后如何进行调用那？如何进行管理哪？

答案就是Spring Cloud Eureka，我们可以将自己定义的API 接口注册到Spring Cloud Eureka上，Eureka负责服务的注册于发现，如果学习过Zookeeper的话，就可以很好的理解，Eureka的角色和 Zookeeper的角色差不多，都是服务的注册和发现，构成Eureka体系的包括：服务注册中心、服务提供者、服务消费者。

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901162900847.png)

上图中描述了（图片来源于网络）：

1、两台Eureka服务注册中心构成的服务注册中心的主从复制集群； 
2、然后**服务提供者**向注册中心进行注册、续约、下线服务等； 
3、**服务消费者**向**Eureka注册中心**拉去服务列表并维护在本地（这也是**客户端发现模式**的机制体现！）； 
4、然后**服务消费者**根据从**Eureka服务注册中心**获取的服务列表选取一个服务提供者进行消费服务。

**（2）Spring Cloud Ribbon**

在上Spring Cloud Eureka描述了服务如何进行注册，注册到哪里，服务消费者如何获取服务生产者的服务信息，但是Eureka只是维护了服务生产者、注册中心、服务消费者三者之间的关系，真正的服务消费者调用服务生产者提供的数据是通过Spring Cloud Ribbon来实现的。

在（1）中提到了服务消费者是将服务从注册中心获取服务生产者的服务列表并维护在本地的，这种客户端发现模式的方式是服务消费者选择合适的节点进行访问服务生产者提供的数据，这种选择合适节点的过程就是Spring Cloud Ribbon完成的。

Spring Cloud Ribbon客户端负载均衡器由此而来。

**（3）Spring Cloud Feign**

上述（1）、（2）中我们已经使用最简单的方式实现了服务的注册发现和服务的调用操作，如果具体的使用Ribbon调用服务的话，你就可以感受到使用Ribbon的方式还是有一些复杂，因此Spring Cloud Feign应运而生。

Spring Cloud Feign 是一个声明web服务客户端，这使得编写Web服务客户端更容易，使用Feign 创建一个接口并对它进行注解，它具有可插拔的注解支持包括Feign注解与JAX-RS注解，Feign还支持可插拔的编码器与解码器，Spring Cloud 增加了对 Spring MVC的注解，Spring Web 默认使用了HttpMessageConverters, Spring Cloud 集成 Ribbon 和 Eureka 提供的负载均衡的HTTP客户端 Feign。

简单的可以理解为：Spring Cloud Feign 的出现使得Eureka和Ribbon的使用更为简单。

**（4）Spring Cloud Hystrix**

我们在（1）、（2）、（3）中知道了使用Eureka进行服务的注册和发现，使用Ribbon实现服务的负载均衡调用，还知道了使用Feign可以简化我们的编码。但是，这些还不足以实现一个高可用的微服务架构。

例如：当有一个服务出现了故障，而服务的调用方不知道服务出现故障，若此时调用放的请求不断的增加，最后就会等待出现故障的依赖方 相应形成任务的积压，最终导致自身服务的瘫痪。

Spring Cloud Hystrix正是为了解决这种情况的，防止对某一故障服务持续进行访问。Hystrix的含义是：断路器，断路器本身是一种开关装置，用于我们家庭的电路保护，防止电流的过载，当线路中有电器发生短路的时候，断路器能够及时切换故障的电器，防止发生过载、发热甚至起火等严重后果。

**（5）Spring Cloud Config**

对于微服务还不是很多的时候，各种服务的配置管理起来还相对简单，但是当成百上千的微服务节点起来的时候，服务配置的管理变得会复杂起来。

分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件Spring Cloud Config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在Cpring Cloud Config 组件中，分两个角色，一是Config Server，二是Config Client。

Config Server用于配置属性的存储，存储的位置可以为Git仓库、SVN仓库、本地文件等，Config Client用于服务属性的读取。

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901170906386.png)

**（6）Spring Cloud Zuul**

我们使用Spring Cloud Netflix中的Eureka实现了服务注册中心以及服务注册与发现；而服务间通过Ribbon或Feign实现服务的消费以及均衡负载；通过Spring Cloud Config实现了应用多环境的外部化配置以及版本管理。为了使得服务集群更为健壮，使用Hystrix的融断机制来避免在微服务架构中个别服务出现异常时引起的故障蔓延。

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901171033171-1530871558279.png)

先来说说这样架构需要做的一些事儿以及存在的不足：

1、首先，破坏了服务无状态特点。为了保证对外服务的安全性，我们需要实现对服务访问的权限控制，而开放服务的权限控制机制将会贯穿并污染整个开放服务的业务逻辑，这会带来的最直接问题是，破坏了服务集群中REST API无状态的特点。从具体开发和测试的角度来说，在工作中除了要考虑实际的业务逻辑之外，还需要额外可续对接口访问的控制处理。

2、其次，无法直接复用既有接口。当我们需要对一个即有的集群内访问接口，实现外部服务访问时，我们不得不通过在原有接口上增加校验逻辑，或增加一个代理调用来实现权限控制，无法直接复用原有的接口。 
面对类似上面的问题，我们要如何解决呢？下面进入本文的正题：服务网关！

为了解决上面这些问题，我们需要将权限控制这样的东西从我们的服务单元中抽离出去，而最适合这些逻辑的地方就是处于对外访问最前端的地方，我们需要一个更强大一些的均衡负载器，它就是本文将来介绍的：服务网关。

服务网关是微服务架构中一个不可或缺的部分。通过服务网关统一向外系统提供REST API的过程中，除了具备服务路由、均衡负载功能之外，它还具备了权限控制等功能。Spring Cloud Netflix中的Zuul就担任了这样的一个角色，为微服务架构提供了前门保护的作用，同时将权限控制这些较重的非业务逻辑内容迁移到服务路由层面，使得服务集群主体能够具备更高的可复用性和可测试性。

**（7）Spring Cloud Bus**

在（5）Spring Cloud Config中，我们知道的配置文件可以通过Config Server存储到Git等地方，通过Config Client进行读取，但是我们的配置文件不可能是一直不变的，当我们的配置文件放生变化的时候如何进行更新哪？

一种最简单的方式重新一下Config Client进行重新获取，但Spring Cloud绝对不会让你这样做的，Spring Cloud Bus正是提供一种操作使得我们在不关闭服务的情况下更新我们的配置。

Spring Cloud Bus官方意义：消息总线。

当然动态更新服务配置只是消息总线的一个用处，还有很多其他用处。

![](C:\Users\13441\Desktop\md\Java\Spring Cloud全家桶主要组件及简要介绍.assets/20170901172344140.png)

## 六、总结

Spring Cloud 的组件还不止这些，通过上边的口水话的介绍，应该可以大致有一定的了解，但是每一个组件的功能远不止上述介绍的那些，每一个组件还有很多其他的功能点，这里的介绍希望能够带大家入个门，不要对微服务这个这么大的概念有所畏惧。

另外，附上最近学习的时候写的代码，希望对大家有帮助！

<http://git.oschina.net/xuliugen/springcloud-demo>

------

参考文章：

1、<http://dockone.io/article/394> 
2、<http://blog.didispace.com/springcloud5/>