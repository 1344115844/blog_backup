##创建一个普通用户suveng
`useradd suveng`
###为suveng用户添加密码：
`echo 123456 | passwd --stdin suveng`
###将suveng添加到sudoers
`echo "suveng ALL = (root) NOPASSWD:ALL" | tee /etc/sudoers.d/suveng`
`chmod 0440 /etc/sudoers.d/suveng`
###解决sudo: sorry, you must have a tty to run sudo问题，在/etc/sudoer注释掉 Default requiretty 一行
`sudo sed -i 's/Defaults    requiretty/Defaults:suveng !requiretty/' /etc/sudoers`

## 配置mongo的yum源

`sudu vi /etc/yum.repos.d/mongodb-org-3.4.repo`

```
[mongodb-org-3.4]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/\$releasever/mongodb-org/3.4/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.4.asc
```



###关闭selinux
`vi /etc/sysconfig/selinux` 
`SELINUX=disabled`

###重新启动
`reboot`

## mongo的安装和基本使用

###在机器上使用suveng用户登录
sudo yum install -y mongodb-org

###修改mongo的配置文件
sudo vi /etc/mongod.conf 

###注释掉bindIp或者修改成当前机器的某一个ip地址

###启动mongo
sudo service mongod start

##连接到mongo
###如果注释掉了bindIp，那么连接时用
mongo
###指定了ip地址
mongo --host 192.168.100.101 --port 27017

###使用或创建database
use xiaoniu

###创建集合（表）
db.createCollection("bike")

###插入数据
db.bike.insert({"_id": 100001, "status": 1, "desc": "test"})
db.bike.insert({"_id": 100002, "status": 1, "desc": "test"})

###查找数据(所有)
db.bine.find()

###退出
exit

###关闭mongo服务
sudu service mongod stop

###设置服务开机启动
sudo checkconfig mongod on

###设置mongo服务开机不启动
sudo chkconfig mongod off