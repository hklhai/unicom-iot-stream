# Unicom IOT Stream
 Unicom IOT Stream Project
 
---
> HK  
> linh@bjhxqh.com

 
组件 | 机器 | 位置
---|---|---
flume | spark1 | /home/hadoop/app/apache-flume
kafka | spark1 | /home/hadoop/app/kafka_2.10-0.10.2.1
flink | spark1 | /home/hadoop/app/flink-1.4.1


### flume启动
bin/flume-ng agent --conf-file conf/sql-kafka-conf.properties --name a1 -Dflume.root.logger=INFO,console


## kafka
###  新建topic
bin/kafka-topics.sh --zookeeper spark1:2181,spark2:2181,spark3:2181 --topic hk2 --replication-factor 1 --partitions 1 --create
    
###  产生
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic hk2

### 消费 deprecated
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic hk2 --from-beginning
  
### 消费
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hk2 --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hk2 
