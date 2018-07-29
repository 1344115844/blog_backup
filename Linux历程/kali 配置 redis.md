# kali 配置 redis

Redis安装与配置 

root@kali:~# wget http://download.redis.io/releases/redis-2.8.3.tar.gz

root@kali:~# tar xzf redis-2.8.3.tar.gz 

root@kali:~# cd redis-2.8.3

root@kali:~/redis-2.8.3# make 

到这里安装完成,直接运行redis-2.8.3里面的redis-server就行,但是一般我们用redis.conf作为配置文件

所以还要配置一下redis.conf,让redis-server后台运行

为了能够让服务进程在后台执行，对配置文件redis.conf做如下修改： 

root@kali:/usr/redis# vim redis.conf  

daemonize yes  

启动Redis服务

root@kali:/usr/redis# redis-server redis.conf 