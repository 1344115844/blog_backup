# (centos7)安装elasticsearch6.4.2

## 环境

centos7 虚拟机 

java 1.8

## 步骤

1. 下载
   https://www.elastic.co/downloads/elasticsearch 

2. 解压安装
   解压后，移动到 `/usr/local/elasticsearch` (个人习惯)

3. 修改配置
   在 `/usr/local/elaticsearch/config` 的 `elasticsearch.yml` 文件中 可配置 访问权限，默认是只能在本机访问，其他机子都不能访问，就算开了防火墙都不行，里面可配置 `port` 默认是 9200。 如果只有本地可以访问，尝试修改配置文件 `elasticsearch.yml` 中network.host(注意配置文件格式不是以 # 开头的要空一格， ： 后要空一格)
   为 `network.host: 0.0.0.0`

4. 启动服务
   我的虚拟机报了一系列的错误，首先是，默认不允许使用root用户去启动，这个你可以有两种解决办法：1. 老实用其他非root 用户去启动es  2.修改配置，是其能使用root 启动
   其中错误的问题，详细的错误问题，可以查看这篇博客https://www.jianshu.com/p/4c6f9361565b 
   基本上解决了我的问题。

   根据上面的博文，总结一下解决办法。
   `ERROR: bootstrap checks failed`

   1. ```bash
      vi /etc/security/limits.conf 
      添加如下内容:
       soft nofile 65536
       hard nofile 131072
       soft nproc 2048
       hard nproc 4096
      ```

   2. ```bash
      vi /etc/security/limits.d/90-nproc.conf 
      修改文件内容为
      soft nproc 2048
      ```

   3. ```bash
      vi /etc/sysctl.conf 
      添加下面配置：
      vm.max_map_count=655360
      ```

   4. 执行 `sysctl -p`

   5. 重启es

   `max file descriptors [65535] for elasticsearch process likely too low, increase to at least [65536]`

   1. 执行下面命令

      ```bash
      ulimit -n 65536
      ```

## 操作

### 操作语法

curl -X<VERB> '<PROTOCOL>://<HOST>:<PORT>/<PATH>?<QUERY_STRING>' -d '<BODY>'

> VERB HTTP方法： GET , POST , PUT , HEAD , DELETE
> PROTOCOL http或者https协议（只有在Elasticsearch前面有https代理的时候可用）
> HOST Elasticsearch集群中的任何一个节点的主机名，如果是在本地的节点，那么就叫
> localhost
> PORT Elasticsearch HTTP服务所在的端口，默认为9200
> PATH API路径（例如_count将返回集群中文档的数量），PATH可以包含多个组件，例如
> _cluster/stats或者_nodes/stats/jvm
> QUERY_STRING 一些可选的查询请求参数，例如 ?pretty 参数将使请求返回更加美观
> 易读的JSON数据
> BODY 一个JSON格式的请求主体（如果请求需要的话

### 创建文档

1. 为了计算集群中的文档数量

   ```bash
   curl -H "Content-Type: application/json" -XGET 'http://localhost:9200/_count?pretty' -d '{"query": {"match_all": {}}}'
   ```

2. 指定id创建文档，创建员工目录,创建三个员工

   ```bash
   //创建员工1
   curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/megacorp/employee/1' -d '{"first_name":"John","last_name":"Smith","age":25,"about":"I love to go rock climbing","interests":["sports","music"]}'
   
   //创建员工2
   curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/megacorp/employee/2' -d '{
   "first_name" : "Jane",
   "last_name" : "Smith",
   "age" : 32,
   "about" : "I like to collect rock albums",
   "interests": [ "music" ]
   }'
   //创建员工3
   curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/megacorp/employee/3' -d '{
   "first_name" : "Douglas",
   "last_name" : "Fir",
   "age" : 35,
   "about": "I like to build cabinets",
   "interests": [ "forestry" ]
   }'
   ```

3. 不指定id 创建 文档

   ```bash
   curl -H "Content-Type: application/json" -XPOST 'http://localhost:9200/megacorp/employee' -d '{"first_name":"su","last_name":"su","age":25,"about":"I love to go rock climbing","interests":["sports","music"]}'
   ```

4. 查询指定id文档

   ```bash
    
   ```

5. 创建索引

   ```bash
   curl -H "Content-Type: application/json" -XPUT 'http://localhost:9200/test_index' 
   ```

6. 查看索引

   ```bash
   curl -H "Content-Type: application/json" -XGET 'http://localhost:9200/_cat/indices' 
   ```

7. 删除索引

   ```bash
   curl -H "Content-Type: application/json" -XDELETE  'http://localhost:9200/test_index' 
   ```


### 检索文档

1. 检索单个员工的信息

   ```bash
   //查询员工1
   curl -H "Content-Type: application/json" -XGET 'http://localhost:9200/megacorp/employee/1'
   
   ```

2. 使用 _search 查询文档

   ```bash
   curl -H "Content-Type: application/json" -XGET 'http://localhost:9200/_search'
   ```



## 参考文献

https://www.jianshu.com/p/4c6f9361565b 

《Elasticsearch权威指南》

