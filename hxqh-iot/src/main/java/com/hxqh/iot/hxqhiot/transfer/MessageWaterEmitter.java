package com.hxqh.iot.hxqhiot.transfer;

import com.hxqh.iot.hxqhiot.utils.DateUtils;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import scala.tools.jline_embedded.internal.Nullable;

/**
 * Created by Ocean lin on 2019/3/22.
 *
 * @author Ocean lin
 */
public class MessageWaterEmitter implements AssignerWithPunctuatedWatermarks<String> {
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(String lastElement, long extractedTimestamp) {
        if (lastElement != null && lastElement.contains(",")) {
            lastElement = lastElement.replace("\"", "");
            String[] parts = lastElement.split(",");
            return new Watermark(DateUtils.timeToStamp(parts[3]));
        }
        return null;
    }

    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        if (element != null && element.contains(",")) {
            element = element.replace("\"", "");
            String[] parts = element.split(",");
            long timeStamp = DateUtils.timeToStamp(parts[3]);
            return timeStamp;
        }
        return 0L;
    }
}