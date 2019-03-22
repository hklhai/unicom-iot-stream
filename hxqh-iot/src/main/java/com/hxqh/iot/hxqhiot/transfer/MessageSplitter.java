package com.hxqh.iot.hxqhiot.transfer;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by Ocean lin on 2019/3/22.
 *
 * @author Ocean lin
 */
public class MessageSplitter extends RichMapFunction<String, Tuple2<String, Integer>> {
    private final String COMMA = ",";

    @Override
    public Tuple2<String, Integer> map(String value) throws Exception {
        value = value.replace("\"", "");
        String[] parts = value.split(COMMA);
        Tuple2<String, Integer> tuple2 = new Tuple2<>(parts[1], Integer.valueOf(parts[2]));
        return tuple2;
    }
}
