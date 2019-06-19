package com.hxqh.iot.hxqhiot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ocean lin on 2019/4/19.
 *
 * @author Ocean lin
 * <p>
 * <p>
 * {"eventThingKey":3c970e5e132a,"eventThingName":auto:3c970e5e132a,"eventThingType":default,"eventPropKey":cpu_usage，
 * "eventPropName":CPU_usage，"eventPropTs":2019-04-19T10:27:20+08:00，"eventPropUnit":，"eventPropValue":50，}
 * <p>
 * {"eventThingKey":3c970e5e132a,"eventThingName":auto:3c970e5e132a,"eventThingType":default,"eventPropKey":cpu_usage，
 * "eventPropName":CPU_usage，"eventPropTs":2019-04-19T10:27:35+08:00，"eventPropUnit":，"eventPropValue":32，}
 */

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UnicomModel implements Serializable {
    private String eventThingKey;
    private String eventThingName;
    private String eventThingType;
    private String eventPropKey;
    private String eventPropName;
    private Date eventPropTs;

    private String eventPropUnit;
    private Float eventPropValue;

}
