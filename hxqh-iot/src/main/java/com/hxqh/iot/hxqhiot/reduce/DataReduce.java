package com.hxqh.iot.hxqhiot.reduce;

 import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by Ocean lin on 2019/3/21.
 *
 * @author Ocean lin
 */
public class DataReduce implements ReduceFunction<Tuple2<String, Integer>> {

    @Override
    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
        int i = value1.f1 + value2.f1;
        value1.f1 = i;
        return value1;
    }
}
