# Kali Linux上安装maven

1. 下载maven的源码,自己到官方网站下载包

2. 配置jdk 必须配置,不过kali好像自带了open jdk 所以可以运行,建议自己重新配置jdk

3. 配置maven mvn 命令,根据自己的包路径配置

   ```
   update-alternatives --install /usr/bin/mvn mvn /usr/local/apache-maven/apache-maven-3.3.3/bin/mvn 1
   ```

4. 配置m2_home 以及jdk 有的话就不用配置了,这里可以是用户的.bashrc ,也可以是全局的/etc/profile

   ```
   export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
   export MAVEN_OPTS="-Xms256m -Xmx512m" # Very important to put the "m" on the end
   ```

