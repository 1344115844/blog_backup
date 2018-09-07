# zookeeper集群搭建和kafka集群搭建

## 环境

1. linux 开发机（主）
2. 4台centos7虚拟机
   1. 192.168.0.201  
   2. 192.168.0.202（nginx-kafka）
   3. 192.168.0.203
   4. 192.168.0.204
3. 虚拟机配置 jdk1.8 jps

## zookeeper集群搭建

1. 安装java（略）

2. 安装zookeeper

   1. 下载（自己到官网下载新的版本）

   2. 解压后移到指定目录

      ```bash
      mv zookeeper-3.4.12 /usr/local/zookeeper
      ```

   3. 修改配置

      ```bash
      cd /usr/local/zookeeper/conf
      cp zoo_sample.cfg zoo.cfg
      ```

      zoo_sample.cfg 这个文件是官方给我们的zookeeper的样板文件，给他复制一份命名为zoo.cfg，zoo.cfg是官方指定的文件命名规则。

      ```
      # vim zoo.cfg
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/data/zookeeper/zkdata
      dataLogDir=/data/zookeeper/zkdatalog
      clientPort=2181
      server.1=192.168.0.201:2888:3888
      server.2=192.168.0.203:2888:3888
      server.3=192.168.0.204:2888:3888
      ```

      ```
      #tickTime：
      这个时间是作为 Zookeeper 服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个 tickTime 时间就会发送一个心跳。
      #initLimit：
      这个配置项是用来配置 Zookeeper 接受客户端（这里所说的客户端不是用户连接 Zookeeper 服务器的客户端，而是 Zookeeper 服务器集群中连接到 Leader 的 Follower 服务器）初始化连接时最长能忍受多少个心跳时间间隔数。当已经超过 5个心跳的时间（也就是 tickTime）长度后 Zookeeper 服务器还没有收到客户端的返回信息，那么表明这个客户端连接失败。总的时间长度就是 5*2000=10 秒
      #syncLimit：
      这个配置项标识 Leader 与Follower 之间发送消息，请求和应答时间长度，最长不能超过多少个 tickTime 的时间长度，总的时间长度就是5*2000=10秒
      #dataDir：
      快照日志的存储路径
      #dataLogDir：
      事物日志的存储路径，如果不配置这个那么事物日志会默认存储到dataDir制定的目录，这样会严重影响zk的性能，当zk吞吐量较大的时候，产生的事物日志、快照日志太多
      #clientPort：
      这个端口就是客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。修改他的端口改大点
      #server.1 这个1是服务器的标识也可以是其他的数字， 表示这个是第几号服务器，用来标识服务器，这个标识要写到快照目录下面myid文件里
      #192.168.7.107为集群里的IP地址，第一个端口是master和slave之间的通信端口，默认是2888，第二个端口是leader选举的端口，集群刚启动的时候选举或者leader挂掉之后进行新的选举的端口默认是3888
      ```

   4. 创建myid文件

      ```bash
      #server1（192.168.0.201）
      echo "1" > /data/zookeeper/zkdata/myid
      #server2（192.168.0.203）
      echo "2" > /data/zookeeper/zkdata/myid
      #server3（192.168.0.204）
      echo "3" > /data/zookeeper/zkdata/myid
      ```

   5. 创建环境变量

      ```bash
      # vim /etc/profile
      export ZOOKEEPER_HOME=/usr/local/zookeeper
      export PATH=$PATH:$ZOOKEEPER_HOME/bin
      
      # source /etc/profile
      ```

   6. 启动Zookeeper服务并查看

      ```bash
      zkServer.sh start #启动
      zkServer.sh status #查看
      jps #查看
      ```

## Kafka集群搭建

1. 下载

2. 解压移动到指定目录

   ```bash
   mv kafka_2.11-1.0.1 /usr/local/kafka
   ```

3. 修改配置文件(注意把注释去掉)

   ```properties
   broker.id=0  #当前机器在集群中的唯一标识，和zookeeper的myid性质一样,每台服务器的broker.id都不能相同
   port=19092 #当前kafka对外提供服务的端口默认是9092
   host.name=192.168.0.201 #这个参数默认是关闭的，在0.8.1有个bug，DNS解析问题，失败率的问题。
   num.network.threads=3 #这个是borker进行网络处理的线程数
   num.io.threads=8 #这个是borker进行I/O处理的线程数
   log.dirs=/data/kafka/kafkalogs/ #消息存放的目录，这个目录可以配置为“，”逗号分割的表达式，上面的num.io.threads要大于这个目录的个数，如果配置多个目录，新创建的topic将消息持久化的地方是，当前以逗号分割的目录中，哪个分区数最少就放那一个
   socket.send.buffer.bytes=102400 #发送缓冲区buffer大小，数据不是一下子就发送的，会先存储到缓冲区，到达一定的大小后在发送，能提高性能
   socket.receive.buffer.bytes=102400 #kafka接收缓冲区大小，当数据到达一定大小后在序列化到磁盘
   socket.request.max.bytes=104857600 #这个参数是向kafka请求消息或者向kafka发送消息的请求的最大数，这个值不能超过java的堆栈大小
   num.partitions=1 #默认的分区数，一个topic默认1个分区数
   log.retention.hours=168 #默认消息的最大持久化时间，168小时，7天
   message.max.byte=5242880  #消息保存的最大值5M
   default.replication.factor=2  #kafka保存消息的副本数，如果一个副本失效了，另一个还可以继续提供服务
   replica.fetch.max.bytes=5242880  #取消息的最大直接数
   log.segment.bytes=1073741824 #这个参数是：因为kafka的消息是以追加的形式落地到文件，当超过这个值的时候，kafka会新起一个文件
   log.retention.check.interval.ms=300000 #每隔300000毫秒去检查上面配置的log失效时间（log.retention.hours=168 ），到目录查看是否有过期的消息如果有，删除
   log.cleaner.enable=false #是否启用log压缩，一般不用启用，启用的话可以提高性能
   zookeeper.connect=192.168.0.201:2181,192.168.0.203:2181,192.168.0.204:2181 #设置zookeeper的连接端口
   ```

4. 配置环境变量

   ```bash
   # vim /etc/profile
   export KAFKA_HOME=/usr/local/kafka
   export PATH=$PATH:$KAFKA_HOME/bin
   
   # source /etc/profile
   ```

5. 启动

   ```bash
   #从后台启动Kafka集群（3台都需要启动）
   kafka-server-start.sh -daemon ../config/server.properties
   
   # 官方推荐启动方式：
   /usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties &
   ```

6. 查看状态

   ```bash
   jps
   看到Kafka的进程，说明Kafka已经启动
   ```

7. 测试kafka

   ```bash
   #创建Topic
   kafka-topics.sh --create --zookeeper 192.168.0.201:2181,192.168.0.203:2181,192.168.0.204:2181 --partitions 3 --replication-factor 3 --topic suveng
   #解释
   --partitions 3   #创建3个分区
   --replication-factor 3     #复制3份
   --topic     #主题为suveng
   
   #查看topic状态
   kafka-topics.sh --describe --zookeeper localhost:2181 --topic suveng
   
   
   
   状态说明：
   #suveng有三个分区分别为1、2、3;
   #分区0的leader是1（broker.id），分区0有三个副本，并且状态都为lsr（ln-sync，表示可以参加选举成为leader）。
   
   #删除topic
       在config/server.properties中加入delete.topic.enable=true并重启服务，在执行如下命令
   # kafka-topics.sh --delete --zookeeper localhost:2181 --topic suveng
   ```

   ```bash
   #在一台服务器上创建一个发布者-发送消息
   kafka-console-producer.sh --broker-list 192.168.0.201:9092 --topic suveng
   输入以下信息：
   　　This is a message
   　　This is another message
   
   #在另一台服务器上创建一个订阅者接收消息
   kafka-console-consumer.sh --zookeeper 192.168.0.203:2181 --topic suveng --from-beginning
   
   #--from-beginning 表示从开始第一个消息开始接收
   #测试（订阅者那里能正常收到发布者发布的消息，则说明已经搭建成功）
   ```

## nginx-kafka模块集成

安装nginx-kafka插件

nginx可以直接把数据写到kafka里面去。

1.安装git

```bash
	yum install -y git
```

2.切换到/usr/local/src目录，然后将kafka的c客户端源码clone到本地

```bash
	cd /usr/local/src
	git clone https://github.com/edenhill/librdkafka
```

3.进入到librdkafka，然后进行编译

```bash
	cd librdkafka
	yum install -y gcc gcc-c++ pcre-devel zlib-devel
	./configure
	make && make install
```

4.安装nginx整合kafka的插件，进入到/usr/local/src，clone nginx整合kafka的源码

```bash
	cd /usr/local/src
	git clone https://github.com/brg-liuwei/ngx_kafka_module
```

5.进入到nginx的源码包目录下	（编译nginx，然后将将插件同时编译）

```bash
	cd /usr/local/src/nginx-1.12.2
	./configure --add-module=/usr/local/src/ngx_kafka_module/
	make
	make install
```

6.修改nginx的配置文件，详情请查看当前目录的nginx.conf

```properties
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';
    #access_log  logs/access.log  main;
    sendfile        on;
    #tcp_nopush     on;
    #keepalive_timeout  0;
    keepalive_timeout  65;
    #gzip  on;
    
    kafka;
    kafka_broker_list node-1.xiaoniu.com:9092 node-2.xiaoniu.com:9092 node-3.xiaoniu.com:9092; 	
    
    server {
        listen       80;
        server_name  node-6.xiaoniu.com;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;

    	location = /kafka/track {
                kafka_topic track;
        }

    	location = /kafka/user {
                kafka_topic user;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }

}
```

主要是添加kafka 和 location，在liuwei的git仓库里面的用法说明有提到。

7.启动zk和kafka集群(创建topic)

```bash
	/bigdata/zookeeper-3.4.9/bin/zkServer.sh start
	/bigdata/kafka_2.11-0.10.2.1/bin/kafka-server-start.sh -daemon /bigdata/kafka_2.11-0.10.2.1/config/server.properties
```

8.启动nginx，报错，找不到kafka.so.1的文件

```bash
	error while loading shared libraries: librdkafka.so.1: cannot open shared object file: No such file or directory
```

原因是没有加载库编译

9.加载so库

```bash
	echo "/usr/local/lib" >> /etc/ld.so.conf
	ldconfig
```

10.测试前把nginx开启，记得要ping通才能测试，而且开启相应的端口，开始测试：向nginx中写入数据，然后观察kafka的消费者能不能消费到数据

```bash
	curl localhost/kafka/track -d "message send to kafka topic"
```







