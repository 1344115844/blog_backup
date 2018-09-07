启动zookeeper

```
./zkServer.sh start
```

启动kafka

```bash
/usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties &
```

启动消费者

```bash
/usr/local/kafka/bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.201:9092,192.168.0.203:9092,192.168.0.204:9092 --topic track --from-beginning 

```

nginx-kafka 写入数据

```
curl localhost/kafka/track -d "suvengddddddddddd"
```

