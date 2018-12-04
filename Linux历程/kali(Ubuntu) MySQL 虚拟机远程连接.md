# kali(Ubuntu) MySQL 虚拟机远程连接

我使用的是官方kali提供的ova虚拟机镜像,它已经安装好了MySQL后，经常我们面临到的是想使用客户端进行远程连接，并且考虑到安全性问题，我们不会使用root用户。

因此，接下来整理下新建用户，并且给用户授权，允许客户端能够进行登录访问。

1、ubuntu连接到mysql数据库

1. mysql -u root -p  

输入安装时设置的root用户密码。

2、切换到mysql数据库

1. mysql> use mysql;  
2. mysql> select host,user,password from user;  
3. 5.7以上的版本 mysql> select host,user,authentication_string from user;

3、给新建的用户进行授权

1. grant all privileges on *.* to 'myuser'@'%' identified by 'root' with grant option; 
2. mysql> flush privileges;  



注意： （1）"%"欲连接到此Mysql

数据库的客户端的IP地址，根据需求进行修正即可。%表示全部ip均可连接

​           （2）password就是Mysql数据库test用户的password,根据实际情况需要修改

5、修改MySQL的配置文件

/etc/mysql/my.cnf文件，找到bind-address = 127.0.0.1

  将 bind-address = 127.0.0.1 这一行注释掉, 即修改为:

  #bind-address = 127.0.0.1

6、将MySQL服务进行重启

1. service mysql restart  

7、使用连接工具进行尝试连接即可。