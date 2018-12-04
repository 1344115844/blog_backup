# Ant学习笔记

## 1.简介

Apache Ant是可以在命令行中执行一个操作系统构建和部署工具。

### Apache Ant功能

- Ant 是最完整的Java构建和部署工具。
- Ant是平台无关的，可以处理特定平台的属性，如文件分隔符。
- Ant 可以用于执行特定任务的平台，例如使用“触摸'命令修改文件的修改时间。
- Ant 脚本使用的是纯XML编写的。如果你已经熟悉XML，你可以学习Ant 很快。
- Ant擅长复杂的自动化重复的任务。
- Ant 自带的预定义任务的大名单。
- Ant提供了开发自定义任务的界面。
- Ant可以在命令行中很容易地调用，它可以与免费的和商业的IDE集成。 

## 2.安装

- 确保将JAVA_HOME环境变量设置到安装JDK的文件夹。
- 下载的二进制文件从[http://ant.apache.org](http://ant.apache.org/)
- 使用Winzip，WinRAR，7-zip或类似工具解压缩zip文件到一个方便的位置 c:folder.
- 创建一个名为ANT_HOME，一个新的环境变量指向Ant的安装文件夹，在 c:apache-ant-1.8.2-bin 文件夹。
- 附加的路径Apache Ant批处理文件添加到PATH环境变量中。在我们的例子是 c:apache-ant-1.8.2-binin文件夹。

## 3.构建文件

所有构建文件要求项目元素和至少一个目标元素。

XML元素的项目有三个属性：

| 属性    | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| name    | The Name of the project. (Optional)                          |
| default | The default target for the build script. A project may contain any number of targets. This attribute specifies which target should be considered as the default. (Mandatory) |
| basedir | The base directory (or) the root folder for the project. (Optional) |

一个目标是要作为一个单元运行的任务的集合。在我们的例子中，我们有一个简单的目标消息给用户。 

目标元素具有以下属性：

| 属性        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| name        | The name of the target (Required)                            |
| depends     | Comma separated list of all targets that this target depends on. (Optional) |
| description | A short description of the target. (optional)                |
| if          | Allows the execution of a target based on the trueness of a conditional attribute. (optional) |
| unless      | Adds the target to the dependency list of the specified Extension Point. An Extension Point is similar to a target, but it does not have any tasks. (Optional) |

 



## 4.属性任务

Ant使用属性元素，它允许你指定的属性。这允许属性从一个版本改变为另一个。或者从一个环境到另一个。

默认情况下，Ant提供了可以在构建文件中使用下列预定义的属性

| 属性                        | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| ant.file                    | The full location of the build file.                         |
| ant.version                 | The version of the Apache Ant installation.                  |
| basedir                     | The basedir of the build, as specified in the **basedir** attribute of the**project** element. |
| ant.java.version            | The version of the JDK that is used by Ant.                  |
| ant.project.name            | The name of the project, as specified in the **name** atrribute of the**project** element |
| ant.project.default-target  | The default target of the current project                    |
| ant.project.invoked-targets | Comma separated list of the targets that were invoked in the current project |
| ant.core.lib                | The full location of the ant jar file                        |
| ant.home                    | The home directory of Ant installation                       |
| ant.library.dir             | The home directory for Ant library files - typically ANT_HOME/lib folder. |

 

## 5.属性文件

直接在构建文件中设置属性是好的，如果你使用的是少数属性。然而，对于一个大型项目，是要存储在一个单独的属性文件中。

存储在一个单独的文件中的属性可以让你重复使用相同的编译文件，针对不同的执行环境不同的属性设置。例如，生成属性文件可以单独维持DEV，TEST和PROD环境。

指定在一个单独的文件属性是有用的，当你不知道一个属性（在一个特定的环境中）前面的值。这使您可以在属性值是已知的其他环境进行构建。

没有硬性规定，但一般属性文件名为build.properties文件，并放在沿一侧的build.xml文件。如build.properties.dev和build.properties.test - 你可以根据部署环境中创建多个生成属性文件

构建属性文件的内容类似于普通的Java属性文件。他们每行包含一个属性。每个属性由一个名称和一个值对来表示。名称和值对由等号分开。强烈建议属性标注了正确的注释。注释列出所使用的哈希字符。

## 6.数据类型

nt提供了一些预定义的数据类型。不要混淆，也可在编程语言中的数据类型，而是考虑数据类型的设置被内置到产品中服务。

下面是一个由Apache Ant的提供的数据类型的列表

### 文件集合

该文件集的数据类型表示文件的集合。该文件集的数据类型通常是作为一个过滤器，以包括和排除匹配特定模式的文件。

例如：

```
<fileset dir="${src}" casesensitive="yes">
  <include name="**/*.java"/>
  <exclude name="**/*Stub*"/>
</fileset>
```

在上面的例子中的src属性指向项目的源文件夹。

在上面的例子中，文件集的选择，除了那些包含在其中单词“Stub”源文件夹中的所有java文件。在大小写敏感的过滤器应用到文件集这意味着名为Samplestub.java一个文件不会被排除在文件集

### 模式集

一个模式集是一个模式，可以非常方便地筛选基于某种模式的文件或文件夹。可以使用下面的元字符来创建模式。

- ? - 只匹配一个字符
- * - 匹配零个或多个字符
- ** - 匹配零个或多个目录递归

下面的例子应该给一个模式集的用法的想法。

```
<patternset id="java.files.without.stubs">
  <include name="src/**/*.java"/>
  <exclude name="src/**/*Stub*"/>
</patternset>
```

patternset 可以用一个文件集重用如下：

```
<fileset dir="${src}" casesensitive="yes">
  <patternset refid="java.files.without.stubs"/>
 </fileset>
```

### 文件列表

在文件列表的数据类型类似设置，除了在文件列表中包含显式命名的文件列表，不支持通配符的文件

文件列表和文件组的数据类型之间的另一个主要区别是，在文件列表的数据类型可应用于可能会或可能还不存在的文件。

以下是文件列表的数据类型的一个例子

```
<filelist id="config.files" dir="${webapp.src.folder}">
  <file name="applicationConfig.xml"/>
  <file name="faces-config.xml"/>
  <file name="web.xml"/>
  <file name="portlet.xml"/>
</filelist>
```

在上面的例子中webapp.src.folder属性指向该项目的Web应用程序的源文件夹。

### 过滤器集

使用与复制任务筛选器集的数据类型，你可以匹配一个替代值的模式，所有的文件替换一定的文本。

一个常见的例子是附加版本号的发行说明文件，如下面的示例所示

```
<copy todir="${output.dir}">
  <fileset dir="${releasenotes.dir}" includes="**/*.txt"/>
  <filterset>
    <filter token="VERSION" value="${current.version}"/>
  </filterset>
</copy>
```

在上面的例子中output.dir属性指向项目的输出文件夹。

在上面的例子点releasenotes.dir属性的发行说明的项目文件夹中。

在上面的例子中current.version属性指向的项目的当前版本中的文件夹。

副本任务，顾名思义是用来从一个位置复制到另一个文件。

### 路径

path 数据类型通常用来代表一个类路径。在路径项用分号或冒号隔开。然而，这些字符会被正在运行的系统的路径分隔符替换一个运行时间。

最常见的类路径设置为项目中的jar文件和类的列表，如下面的例子：

```
<path id="build.classpath.jar">
  <pathelement path="${env.J2EE_HOME}/${j2ee.jar}"/>
  <fileset dir="lib">
      <include name="**/*.jar"/>
  </fileset>
</path>
```

在上面的例子中env.J2EE_HOME属性指向环境变量J2EE_HOME。

在上面的例子中的j2ee.jar属性指向在J2EE基础文件夹J2EE的jar文件的名称。



## 7.打包和部署

根据项目需求来自己定义一个自动部署的构建工具。先用构建文件编译后，打包成war包，然后配置一个容器，完成自动部署。以后就可以用ant 自定义的命令重新部署自己的项目。

## 8.Ant执行java代码

 

您可以使用Ant来执行java代码。在下面这个例子中，java类中取一个参数（管理员的电子邮件地址），并发送了一封电子邮件。

```
public class NotifyAdministrator
{
  public static void main(String[] args)
  {
    String email = args[0];
    notifyAdministratorviaEmail(email);
    System.out.println("Administrator "+email+" has been notified");
  }
  public static void notifyAdministratorviaEmail(String email)
  {
      //......
  }
}
```

下面是执行这个java类简单的构建。

```
<?xml version="1.0"?>
<project name="sample" basedir="." default="notify">
  <target name="notify">
    <java fork="true" failonerror="yes" classname="NotifyAdministrator">
      <arg line="admin@test.com"/>
    </java>
  </target>
</project>
```

当执行构建时，它会产生以下结果：

```
C:>ant
Buildfile: C:uild.xml

notify:
     [java] Administrator admin@test.com has been notified

BUILD SUCCESSFUL
Total time: 1 second
```

在这个例子中，java代码做一个简单的事情 - 发送电子邮件。我们也可以使用内置的Ant任务来做到这一点。不过，现在你已经得到了你的想法可以扩展你的构建文件来调用java代码执行复杂的东西，例如：加密你的源代码。

自由度极大

## 9.Ant Junit集成

¥ 我要打赏 作者：李嘉图 Java技术QQ群：227270512 / Linux QQ群：479429477

JUnit 是基于Java常用的单元测试框架进行开发。它是易于使用和易于延伸。有许多[JUnit](http://www.yiibai.com/junit/)扩展可用。如果你不熟悉Junit的，你应该从www.junit.org下载JUnit和阅读JUnit的使用手册。

本教程讨论了关于执行使用Ant 的JUnit测试。Ant 通过这个简单Junit 的任务变得简单。

以下展示的是JUnit 任务的属性。

| Properties      | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| dir             | Where to invoke the VM from. This is ignored when **fork** is disabled. |
| jvm             | Command used to invoke the JVM. This is ignored when **fork** is disabled. |
| fork            | Runs the test in a separate JVM                              |
| errorproperty   | The name of the property to set if there is a Junit error    |
| failureproperty | The name of the property to set if there is a Junit failure  |
| haltonerror     | Stops execution when a test error occurs                     |
| haltonfailure   | Stops execution when a failure occurs                        |
| printsummary    | Advices Ant to display simple statistics for each test       |
| showoutput      | Adivces Ant tosend the output to its logs and formatters     |
| tempdir         | Path to the temporary file that Ant will use                 |
| timeout         | Exits the tests that take longer to run than this setting (in milliseconds). |

让我们继续的Hello World fax web应用程序的主题，并添加一个JUnit目标。

下面的例子展示了一个简单的JUnit测试执行

```
<target name="unittest">
  <junit haltonfailure="true" printsummary="true">
    <test name="com.yiibai.UtilsTest"/>
  </junit>
</target>
```

上面的例子显示的Junit对com.yiibai.UtilsTest JUnit类执行。运行上面会产生下面的输出

```
test:
[echo] Testing the application
[junit] Running com.yiibai.UtilsTest
[junit] Tests run: 12, Failures: 0, Errors: 0, Time elapsed: 16.2 sec
BUILD PASSED
```

## 10.参考资料

[易佰教程](https://www.yiibai.com/ant/ant_introduction.html)