package com.hxqh.iot.hxqhiot.map;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019/3/22
 *
 * @author Lin
 */
public class DataModel {

    private Integer uid;
    private Integer val;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    private Integer count;

    public DataModel() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
