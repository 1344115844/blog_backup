# Web Service实战

## 什么是webservice?

webservice 有的人一看到这个，估计会认为这个是一种新技术，一种新框架。

其实不是，严格的说，webservice是一种跨平台，跨语言的规范，用于不同平台，不同语言开发的应用之间的交互。

这里具体举个例子，比如在Windows Server服务器上有个C#.Net开发的应用A，在Linux上有个Java语言开发的应用B，

B应用要调用A应用，或者是互相调用。用于查看对方的业务数据。

再举个例子，天气预报接口。无数的应用需要获取天气预报信息；这些应用可能是各种平台，各种技术实现；而气象局的项目，估计也就一两种，要对外提供天气预报信息，这个时候，如何解决呢？

webservice就是出于以上类似需求而定义出来的规范；

开发人员一般就是在具体平台开发webservice接口，以及调用webservice接口；每种开发语言都有自己的webservice实现框架。比如Java 就有 Apache Axis1、Apache Axis2、Codehaus XFire、Apache CXF、Apache Wink、Jboss  RESTEasyd等等...

![img](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\640.png)

AXIS2 也是Webservice服务开发的一个优良框架。

--------------以下AXIS2 特性说明 摘自百度百科-----------------------

Axis2是下一代 Apache Axis。Axis2 虽然由 Axis 1.x 处理程序模型提供支持，但它具有更强的灵活性并可扩展到新的[体系结构](http://baike.baidu.com/view/1188494.htm)。Axis2 基于新的体系结构进行了全新编写，而且没有采用 Axis 1.x 的常用代码。支持开发 Axis2 的动力是探寻模块化更强、灵活性更高和更有效的体系结构，这种体系结构可以很容易地插入到其他相关 Web 服务标准和协议（如 WS-Security、WS-ReliableMessaging 等）的实现中。

​        --------------以上AXIS2 特性说明 摘自百度百科-----------------------

下面就AXIS2的webService服务如何发布和开发进行实践

## 一、目标：

1）axis2 环境准备

2）服务端开发和发布

3)  客户端的开发

## 二、axis2环境准备

整个开发环境如下：

主机： 

1. windows10 
2. eclipse：jee-oxygen
3. tomcat 8.5
4. axis2 1.7.4
5. java1.8   

### 1、axis2的框架软件的获取

axis2 可以从 <http://ws.apache.org/axis2/>  这个网址获取。我们一般获取的war包（部署到tomcat下）和bin 二进制包（用到一些里面的工具）

![1525431183583](C:\Users\13441\AppData\Local\Temp\1525431183583.png)

2、下载后部署到发布服务器上

将war.zip解压后，部署到本地的tomcat 环境中webapps目录   ../apache-tomcat-6.0.48/webapps。启动tomcat后，会自动将war包解压成目录，我们只要看一下下面url是否能顺利打开，就可以知道axis2是否部署成功。



## 三、服务端的开发与部署

服务端一般有两种部署方式：

1） POJO  简单java 对象方式，这种方式要求部署的java对象，不带包名

2)    利用axis2管理平台发布，编辑service.xml进行发布（更常用，灵活度高）

  

### 1.  POJO方式部署和开发

由于不带包名，因为相关的类就放在一个地方。我们在myeclipse里面，建立一个web project工程StudyWsAxis2Student。（不选web service project 是因为默认用的是jax-ws 作为webService 框架）。工程里面，我们建几个类。 实体类：student， 实体DAO接口和实现， 实体操作类（这个作为要暴露的service服务）。

工程目录如下图所示：

![img](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\20170120165748997.png) 

StudentOpt.java 是pojo 类，需要开放的服务方法要设置为 public ，其他的为private属性。

`StudentOpt`

```

import java.util.ArrayList;  
import java.util.List;  
  
import com.study.dao.StudentDAO;  
import com.study.dao.impl.StudentDAOImpl;  
import com.study.entity.Student;  
  
public class StudentOpt {  
  
    private List<String> listStudentInfo = new ArrayList<String>();  
  
    private StudentDAO studentDAO;  
  
    public StudentOpt() {  
        System.out.println("studentDAO is init");  
        studentDAO = new StudentDAOImpl();  
  
    }  
  
    private StudentDAO getStudentDAO() {  
        return studentDAO;  
    }  
  
    private void setStudentDAO(StudentDAO studentDAO) {  
        this.studentDAO = studentDAO;  
    }  
  
    public boolean addStudent(String name, String sex, String birthday) {  
        // 调用studentDAO.addStudent 方法入库  
        System.out.println("Now put student into DB!");  
        studentDAO.addStudent(name, sex, birthday);  
        return true;  
  
    }  
  
    public String queryStudent(String studentName) {  
        System.out.println("queryStudent->"+studentName);  
        if (studentDAO.queryStudent(studentName) == null) {  
            return "null";  
        } else {  
            return studentDAO.queryStudent(studentName).to_string();  
        }  
  
    }  
  
  
}  

```

其他的文件， Student.java 是 实体类，StudentDao是实体操作接口，StudentDaoImpl 是实体操作实现类。 StudentSimlationDB是一个模拟数据库类，就是记录一下addStudent操作的结果用的。这些代码如下：

`StudentDAO.java`

```
package com.study.dao;

import com.study.entity.Student;  

public interface StudentDAO {  
      
    //学生操作，新增学生  
    boolean addStudent(String name, String sex, String birthday);  
      
    //学生操作，删除学生  
    boolean delStudent(Student  student);  
      
    //学生操作，修改学生信息  
    boolean modifyStudent(Student  student);  
      
    //学生操作，查询学生信息，查询到返回学生对象，否则返回null  
    Student queryStudent( String  StudentName);  
      
}  
```

`StudentDAOImpl.java`

```
package com.study.dao.impl;

import com.study.dao.StudentDAO;  
import com.study.entity.Student;  
  
  
  
public class StudentDAOImpl implements StudentDAO {  
      
    @Override  
    public boolean addStudent(String name, String sex, String birthday) {  
        // TODO Auto-generated method stub  
        System.out.println("addStudent begin!");  
          
        Student  tmpStudent = new Student();  
        tmpStudent.setName(name);  
        tmpStudent.setSex(sex);  
        tmpStudent.setBirthday(birthday);  
          
        StudentSimlationDB.getInstance().listStudent.add( tmpStudent);    
          
        for(Student  stu:StudentSimlationDB.getInstance().listStudent){  
             System.out.println(stu.to_string());  
        }  
          
        return false;  
    }  
  
    @Override  
    public boolean delStudent(Student student) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
  
    @Override  
    public boolean modifyStudent(Student student) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
  
    @Override  
    public Student queryStudent(String StudentName) {  
        // TODO Auto-generated method stub  
        //模拟从数据库中查询学生名  
        System.out.println("queryStudent begin!");  
        for(Student  aStudent : StudentSimlationDB.getInstance().listStudent )  
        {  
            if(aStudent.getName().equals(StudentName) ){  
                System.out.println("queryStudent Infomation successfully !");  
                return  aStudent;  
            }  
            else{  
                System.out.println("queryStudent Infomation failture !");  
            }  
        }  
          
        return null;  
    }  
  
}

```

`StudentSimlationDB.java`

```
package com.study.dao.impl;

import java.util.ArrayList;  
import java.util.List;  
  
import com.study.entity.Student;  
  
//模拟数据库，目的是对student操作的时候，可以记录操作的内容  
public class StudentSimlationDB {     
      
    public List<Student > listStudent=new ArrayList<Student>();  
      
    private static StudentSimlationDB instance = null;    
    private StudentSimlationDB(){}    
      
    public static StudentSimlationDB getInstance() {// 实例化引用  
        if (instance == null) {  
            instance = new StudentSimlationDB();  
        }  
        return instance;  
    }  
      
      
      
  
}  

```

`Student.java`

```
package com.study.entity;

import java.util.List;  

public class Student {  
      
    String Name;  
    String Sex;   
    String Birthday;      
      
    public String getName() {  
        return Name;  
    }  
    public void setName(String name) {  
        Name = name;  
    }  
    public String getSex() {  
        return Sex;  
    }  
    public void setSex(String sex) {  
        Sex = sex;  
    }  
    public String getBirthday() {  
        return Birthday;  
    }  
    public void setBirthday(String birthday) {  
        Birthday = birthday;  
    }  
      
    public String to_string(){  
        String  str ="Name="+this.getName()+";Sex="+this.getSex()+";Birthday="+this.getBirthday();  
              
        return  str;  
    }  
  
      
  
      
  
}  
```

将编译后StudentOpt.class 复制到 tomcat目录下的\webapps\axis2\WEB-INF\pojo，一般情况下pojo目录是不存在的，需要自己手工建立。然后将其他类，上传到tomcat的lib 目录

![img](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\20170120171148473.png) 

因为POJO是需要调用 这些没有暴露的对象，因此需要找到这些依赖的对象。部署好了后，可以启动tomcat。（注意POJO类是热部署但是不是热更新）启动完成后，可以看到暴露的webservice接口服务了。URL如下：localhost:8080/axis2 

![img](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\20170120171527115.png) 



不编写客户端，我们可以通过 url方式来操作或者soapui工具来验证服务端是否正确，这里不细说了。

1）URL操作

增加学生：localhost:8080/axis2/services/StudentOpt/addStudent?name=Tom&sex=male&birthday=20010512



​        查询学生：localhost:8080/axis2/services/StudentOpt/queryStudent?studentName=Tom

### 2. web serverice打包成war发布到Tomcat

可以在webapps目录下以war方式部署服务，相关的内容部署在一个目录下，更加便于管理

首先我们看一下开发环境如何调试：

1）建立工程，编写业务逻辑代码

我们新建一个web project工程StudyWSAxis2Student2，在这个工程里面，Student，StudentDAO，StudentDAOImpl，StudentSimlationDB 这些类不变，我们将StudentWs 移到com.study.axis2ws 包下，确保工程都能编译无错误。

![](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets/20180505130105.png)

`StudentWs.java`

```
package com.study.axis2ws;

import java.util.ArrayList;  
import java.util.List;  
  
import com.study.dao.StudentDAO;  
import com.study.dao.impl.StudentDAOImpl;  
import com.study.entity.Student;  
  
public class StudentWs {  
  
    private List<String> listStudentInfo = new ArrayList<String>();  
  
    private StudentDAO studentDAO;  
  
    public StudentWs() {  
        System.out.println("studentDAO is init");  
        studentDAO = new StudentDAOImpl();  
  
    }  
  
    private StudentDAO getStudentDAO() {  
        return studentDAO;  
    }  
  
    private void setStudentDAO(StudentDAO studentDAO) {  
        this.studentDAO = studentDAO;  
    }  
  
    public boolean addStudent(String name, String sex, String birthday) {  
        // 调用studentDAO.addStudent 方法入库  
        System.out.println("Now put student into DB!");  
        studentDAO.addStudent(name, sex, birthday);  
        return true;  
  
    }  
  
    public String queryStudent(String studentName) {  
        System.out.println("queryStudent->"+studentName);  
        if (studentDAO.queryStudent(studentName) == null) {  
            return "null";  
        } else {  
            return studentDAO.queryStudent(studentName).to_string();  
        }  
  
    }  
  
    public static void main(String[] args) {  
  
         StudentWs  studentWs = new StudentWs();     
         studentWs.addStudent("Tom", "male", "20110314");  
         System.out.println(studentWs.queryStudent("Tom"));  
           
           
       
  
    }  
  
}  
```

2）axis2 环境嵌入到myeclipse里面

在myeclipse的WebRoot下，从之前安装axis的tomcat里面，webapps/axis2/WEB-INF 目录下，拷贝 conf、 modules、services、lib 目录都拷贝到工程的WebRoot/WEB-INF目录下，如下图所示：

```
D:.
│  .classpath
│  .project
│
├─.settings
│      .jsdtscope
│      org.eclipse.jdt.core.prefs
│      org.eclipse.wst.common.component
│      org.eclipse.wst.common.project.facet.core.xml
│      org.eclipse.wst.jsdt.ui.superType.container
│      org.eclipse.wst.jsdt.ui.superType.name
│
├─build
│  └─classes
│      └─com
│          └─study
│              ├─axis2ws
│              │      StudentWs.class
│              │
│              ├─dao
│              │  │  StudentDAO.class
│              │  │
│              │  └─impl
│              │          StudentDAOImpl.class
│              │          StudentSimlationDB.class
│              │
│              └─entity
│                      Student.class
│
├─src
│  └─com
│      └─study
│          ├─axis2ws
│          │      StudentWs.java
│          │
│          ├─dao
│          │  │  StudentDAO.java
│          │  │
│          │  └─impl
│          │          StudentDAOImpl.java
│          │          StudentSimlationDB.java
│          │
│          └─entity
│                  Student.java
│
└─WebContent
    ├─META-INF
    │      MANIFEST.MF
    │
    └─WEB-INF
        │  web.xml
        │
        ├─conf
        │      axis2.xml
        │
        ├─lib
        │      activation-1.1.jar
        │      activation-LICENSE.txt
        │      antlr-2-LICENSE.txt
        │      antlr-2.7.7.jar
        │      apache-mime4j-core-0.7.2.jar
        │      apache-mime4j-core-LICENSE.txt
        │      axiom-api-1.2.20.jar
        │      axiom-dom-1.2.20.jar
        │      axiom-impl-1.2.20.jar
        │      axiom-jaxb-1.2.20.jar
        │      axiom-LICENSE.txt
        │      axis2-adb-1.7.4.jar
        │      axis2-adb-codegen-1.7.4.jar
        │      axis2-clustering-1.7.4.jar
        │      axis2-codegen-1.7.4.jar
        │      axis2-corba-1.7.4.jar
        │      axis2-fastinfoset-1.7.4.jar
        │      axis2-java2wsdl-1.7.4.jar
        │      axis2-jaxbri-1.7.4.jar
        │      axis2-jaxws-1.7.4.jar
        │      axis2-jibx-1.7.4.jar
        │      axis2-json-1.7.4.jar
        │      axis2-kernel-1.7.4.jar
        │      axis2-LICENSE.txt
        │      axis2-metadata-1.7.4.jar
        │      axis2-mtompolicy-1.7.4.jar
        │      axis2-saaj-1.7.4.jar
        │      axis2-soapmonitor-servlet-1.7.4.jar
        │      axis2-spring-1.7.4.jar
        │      axis2-transport-http-1.7.4.jar
        │      axis2-transport-local-1.7.4.jar
        │      axis2-xmlbeans-1.7.4.jar
        │      bcel-findbugs-6.0.jar
        │      bcel-LICENSE.txt
        │      commons-cli-1.2.jar
        │      commons-cli-LICENSE.txt
        │      commons-codec-1.3.jar
        │      commons-codec-LICENSE.txt
        │      commons-fileupload-1.3.1.jar
        │      commons-fileupload-LICENSE.txt
        │      commons-httpclient-3.1.jar
        │      commons-httpclient-LICENSE.txt
        │      commons-io-2.1.jar
        │      commons-io-LICENSE.txt
        │      commons-logging-1.1.1.jar
        │      commons-logging-LICENSE.txt
        │      geronimo-annotation-LICENSE.txt
        │      geronimo-annotation_1.0_spec-1.1.jar
        │      geronimo-jaxws-LICENSE.txt
        │      geronimo-jaxws_2.2_spec-1.0.jar
        │      geronimo-jta-LICENSE.txt
        │      geronimo-jta_1.1_spec-1.1.jar
        │      geronimo-saaj-LICENSE.txt
        │      geronimo-saaj_1.3_spec-1.0.1.jar
        │      geronimo-stax-api-LICENSE.txt
        │      geronimo-stax-api_1.0_spec-1.0.1.jar
        │      geronimo-ws-metadata-LICENSE.txt
        │      geronimo-ws-metadata_2.0_spec-1.1.2.jar
        │      gson-2.1.jar
        │      gson-LICENSE.txt
        │      httpclient-4.5.2.jar
        │      httpclient-LICENSE.txt
        │      httpcore-4.4.4.jar
        │      httpcore-LICENSE.txt
        │      jalopy-LICENSE.txt
        │      jaxb-api-2.2.6.jar
        │      jaxb-api-LICENSE.txt
        │      jaxb-impl-2.2.6.jar
        │      jaxb-impl-LICENSE.txt
        │      jaxb-xjc-2.2.6.jar
        │      jaxb-xjc-LICENSE.txt
        │      jaxen-1.1.6.jar
        │      jaxen-LICENSE.txt
        │      jaxws-tools-2.2.6.jar
        │      jaxws-tools-LICENSE.txt
        │      jettison-1.3.jar
        │      jettison-LICENSE.txt
        │      jibx-bind-1.2.jar
        │      jibx-bind-LICENSE.txt
        │      jibx-run-1.2.jar
        │      jibx-run-LICENSE.txt
        │      jsr311-api-1.1.1.jar
        │      jsr311-api-LICENSE.txt
        │      juli-6.0.16.jar
        │      juli-LICENSE.txt
        │      log4j-1.2.15.jar
        │      log4j-LICENSE.txt
        │      mail-1.4.jar
        │      mail-LICENSE.txt
        │      mex-1.7.4-impl.jar
        │      neethi-3.0.3.jar
        │      neethi-LICENSE.txt
        │      stax2-api-3.1.1.jar
        │      stax2-api.LICENSE.txt
        │      tribes-6.0.16.jar
        │      tribes-LICENSE.txt
        │      woden-core-1.0M10.jar
        │      woden-core-LICENSE.txt
        │      woodstox-core-asl-4.2.0.jar
        │      woodstox-core-asl-LICENSE.txt
        │      wsdl4j-1.6.2.jar
        │      wsdl4j-LICENSE.txt
        │      xalan-2.7.0.jar
        │      xalan-LICENSE.txt
        │      xml-resolver-1.2.jar
        │      xml-resolver-LICENSE.txt
        │      xmlbeans-2.5.0.jar
        │      xmlbeans-LICENSE.txt
        │      xmlschema-core-2.2.1.jar
        │      xmlschema-core-LICENSE.txt
        │
        ├─modules
        │      addressing-1.7.4.mar
        │      axis2-jaxws-mar-1.7.4.mar
        │      mex-1.7.4.mar
        │      modules.list
        │      mtompolicy-1.7.4.mar
        │      ping-1.7.4.mar
        │      scripting-1.7.4.mar
        │      soapmonitor-1.7.4.mar
        │
        └─services
            │  services.list
            │  version-1.7.4.aar
            │
            └─StudentWs
                └─META-INF
                        services.xml

```

  3） 修改配置文件web.xml （WebRoot/WEB-INF ） 

![1525496813858](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1525496813858.png)

```
<?xml version="1.0" encoding="UTF-8"?>  
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">  
  <display-name>StudyWSAxis2Student2</display-name>  
  <welcome-file-list>  
    <welcome-file>index.html</welcome-file>  
    <welcome-file>index.htm</welcome-file>  
    <welcome-file>index.jsp</welcome-file>  
    <welcome-file>default.html</welcome-file>  
    <welcome-file>default.htm</welcome-file>  
    <welcome-file>default.jsp</welcome-file>  
  </welcome-file-list>  
    
  <!-- 加载Axis -->    
  <servlet>    
    <servlet-name>AxisServlet</servlet-name>    
    <servlet-class>org.apache.axis2.transport.http.AxisServlet</servlet-class>    
    <load-on-startup>1</load-on-startup>    
  </servlet>    
  <servlet-mapping>    
    <servlet-name>AxisServlet</servlet-name>    
    <url-pattern>/services/*</url-pattern>    
  </servlet-mapping>
</web-app>
```

4)  修改 WebRoot/WEB-INF/services/StudentWs/META-INF/ 下的services.xml 

![1525496883509](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1525496883509.png)

```
<service name="StudentWs">      
    <description>      
        Student Web Service     
    </description>      
    <parameter name="ServiceClass">      
        com.study.axis2ws.StudentWs    
    </parameter>      
    <operation name="addStudent">      
        <messageReceiver class="org.apache.axis2.rpc.receivers.RPCMessageReceiver" />      
    </operation>      
    <operation name="queryStudent">      
        <messageReceiver class="org.apache.axis2.rpc.receivers.RPCMessageReceiver" />      
    </operation>     
      
</service>
```

 在项目上，右键，选运行 server Application 



![1525496965070](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1525496965070.png)

首先打war包：在工程右键菜单里面旋转 export 

选择保存war包的地方，一般我们就放置在工程下 

![1525497059327](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1525497059327.png)

测试

![1525497118570](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1525497118570.png)

## 四、客户端编写

```
package com.study.client;
import javax.xml.namespace.QName;  
import org.apache.axis2.addressing.EndpointReference;  
import org.apache.axis2.client.Options;  
import org.apache.axis2.rpc.client.RPCServiceClient;  
  
public class StudentCli {  
      
    public static void main(String[] args) {  
          
        String url = "http://127.0.0.1:8080/axis2/services/StudentOpt";  
        String result = "";  
          
        try {  
            // 使用RPC方式调用WebService  
            RPCServiceClient serviceClient = new RPCServiceClient();  
            Options options = serviceClient.getOptions();  
              
            // 指定调用WebService的URL  
            EndpointReference targetEPR = new EndpointReference(url);  
            options.setTo(targetEPR);  
              
            // 在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，也就是<wsdl:definitions>元素的targetNamespace属性值  
            // // 指定要调用的getWorld方法及WSDL文件的命名空间.....  
            QName opAddEntry = new QName("http://ws.apache.org/axis2", "addStudent");  
            //  
              
            // 指定getGreeting方法的参数值，如果有多个，继续往后面增加即可，不用指定参数的名称  
            Object[] opAddEntryArgs = new Object[] { "Jerry","female","20160323" };  
              
            // 返回参数类型，这个和axis1有点区别  
            // invokeBlocking方法有三个参数，其中第一个参数的类型是QName对象，表示要调用的方法名；  
            // 第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；  
            // 第三个参数表示WebService方法的返回值类型的Class对象，参数类型为Class[]。  
            // 当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}  
            // 如果被调用的WebService方法没有返回值，应使用RPCServiceClient类的invokeRobust方法，  
            // 该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同  
            // 指定getGreeting方法返回值的数据类型的Class对象.....  
              
            Class[] classes = new Class[] { String.class };  
            // 调用getGreeting方法并输出该方法的返回值.......  
            result = (String) serviceClient.invokeBlocking(opAddEntry,  
                    opAddEntryArgs, classes)[0];  
              
            System.out.println("call  addStudent");  
            System.out.println(result);  
              
              
            // 下面是调用querryStudent  
            opAddEntry = new QName("http://ws.apache.org/axis2", "queryStudent");  
            opAddEntryArgs = new Object[] { "Tom" };  
            System.out.println("call  queryStudent");  
            System.out.println(serviceClient.invokeBlocking(opAddEntry,  
                    opAddEntryArgs, classes)[0]);  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
```

![1526532678287](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1526532678287.png)![1526532713006](C:\Users\13441\Desktop\md\WebService\Web Service实战.assets\1526532713006.png)