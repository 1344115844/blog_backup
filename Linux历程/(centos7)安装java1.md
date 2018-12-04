# (centos7)安装java1.8环境

## 背景：

​	开发需要java环境，centos内置openjdk，但是那种不受控制的感觉很糟糕，我个人比较喜欢自定义。

​	说回来，安装java环境很简单，可以通过yum命令直接安装，这里不详细说，我是个程序员，我比较喜欢像windows那种java环境放在一个文件夹内

	在bash下输入 `which java` 可以看到路径在 `/usr/bin` 下，`/usr/bin` 下有很多的可执行命令文件，这种方式的 java环境实在恶心。于是我就删除这个openjdk了，直接从官网下压缩文件安装。

## 步骤

1. 获取官网文件，进入oracle官网获取

2. 解压 tar -zxvf ****

3. 移动到自己指定的路径，个人喜欢安装到 `/usr/local` 

   ```bash
   mv ****  /usr/local/java
   ```

4. 配置环境变量

   ```properties
   #set java environment
   #JAVA_HOME自己的安装的路径
   JAVA_HOME=/usr/local/java
   JRE_HOME=$JAVA_HOME/jre
   PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
   CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib
   export JAVA_HOME JRE_HOME PATH CLASSPATH
   ```

5. 完成



