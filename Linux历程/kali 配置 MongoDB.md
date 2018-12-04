# kali 配置 MongoDB

1. 下载：wgethttps://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian92-3.6.5.tgz （mongodb.tgz 为Debian 7 Linux 64-bit v3.2 ，Kali 是基于 debian

2. 解压：tar -zxvf mongodb.tgz /usr/local/mongodb 

3. 创建数据库运行时需要的数据存储和日志：mkdir -p /usr/local/mongodb/data  mkdir -p /usr/local/mongodb/logs 

4. 配置mongodb启动参数 vi /etc/mongodb.conf

   ​            dbpath=/usr/local/mongodb/data

   ​            master=true

   ​            logpath=/usr/local/mongodb/logs/mongodb.log

   ​            logappend=true

5. 进入mongodb安装目录 cd /usr/local/mongodb/bin  运行mongodb： ./mongod -f /etc/mongodb.conf  &    通过**ps -def | grep mongod** 可以查看到已经启动的服务进程。 

6. 让mongodb在系统启动时就自启动：在/etc/rc.local  /usr/local/mongodb/bin/mongod -f /etc/mongodb.conf & 

7. 添加环境变量，profile在最后一行添加下面的代码：export PATH=$PATH:/usr/local/mongodb/bin 

8. 编辑命令：vi /etc/profile      然后使用下面命令：source /etc/profile  #使配置立即生效 

9. 然后重启服务器，使用：mongo #进入MongoDB控制台 show dbs #查看默认数据库 exit #退出MongoDB控制台 