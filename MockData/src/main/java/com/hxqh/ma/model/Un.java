package com.hxqh.ma.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ocean lin on 2019/3/21.
 *
 * @author Ocean lin
 */
@Entity
@Table(name = "un")
@DynamicUpdate
public class Un implements Serializable {

    @Id
    @GeneratedValue
    private Integer uid;
    private String thingkey;
    private Integer val;
    private Date createdate;

    public Un() {
    }

    public Un(String thingkey, Integer val, Date createdate) {
        this.thingkey = thingkey;
        this.val = val;
        this.createdate = createdate;
    }

    public String getThingkey() {
        return thingkey;
    }

    public void setThingkey(String thingkey) {
        this.thingkey = thingkey;
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
