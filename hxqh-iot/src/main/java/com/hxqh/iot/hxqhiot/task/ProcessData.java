package com.hxqh.iot.hxqhiot.task;


import com.hxqh.iot.hxqhiot.reduce.DataReduce;
import com.hxqh.iot.hxqhiot.transfer.MessageSplitter;
import com.hxqh.iot.hxqhiot.transfer.MessageWaterEmitter;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;


/**
 * Created by Ocean lin on 2019/3/21.
 *
 * @author Ocean lin
 */
public class ProcessData {
    private final static Integer NUM = 6;

    public static void main(String[] args) {
        args = new String[]{"--input-topic", "hk2", "--bootstrap.servers", "spark1:9092",
                "--zookeeper.connect", "spark1:2181,spark2:2181,spark3:2181", "--group.id", "hk2",
                "--windows.size", "5", "--windows.slide", "1"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < NUM) {
            System.out.println("Missing parameters!\n" +
                    "Usage: Kafka --input-topic <topic>" +
                    "--bootstrap.servers <kafka brokers> " +
                    "--zookeeper.connect <zk quorum> --group.id <some id>");
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        // create a checkpoint every 5 seconds 非常关键，一定要设置启动检查点！！
        env.enableCheckpointing(5000);
        env.getConfig().setGlobalJobParameters(parameterTool);
        // make parameters available in the web interface
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        FlinkKafkaConsumer010 flinkKafkaConsumer = new FlinkKafkaConsumer010<String>(
                parameterTool.getRequired("input-topic"), new SimpleStringSchema(), parameterTool.getProperties());
//        DataStream<String> input = env.addSource(flinkKafkaConsumer);

        FlinkKafkaConsumerBase kafkaConsumerBase = flinkKafkaConsumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());
        DataStream<String> input = env.addSource(kafkaConsumerBase);

        SingleOutputStreamOperator<Tuple2<String, Integer>> map = input.map(new MessageSplitter());
        SingleOutputStreamOperator<Tuple2<String, Integer>> reduce = map.keyBy(0).countWindow(Long.valueOf(parameterTool.getRequired("windows.size")),
                Long.valueOf(parameterTool.getRequired("windows.slide"))).reduce(new DataReduce());

        reduce.addSink(new SinkFunction<Tuple2<String, Integer>>() {
            @Override
            public void invoke(Tuple2<String, Integer> value, Context context) throws Exception {
                if (value.f1 > 30) {
                    System.out.println("设备异常！TingKey：" + value.f0 + " 读数：" + value.f1);
                }
            }
        }).name("ProcessData-Sink");

        try {
            env.execute("ProcessData");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
