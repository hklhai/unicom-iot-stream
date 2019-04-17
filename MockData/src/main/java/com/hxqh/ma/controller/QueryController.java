package com.hxqh.ma.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxqh.ma.common.Constants;
import com.hxqh.ma.model.Task;
import com.hxqh.ma.model.User;
import com.hxqh.ma.model.assist.Show;
import com.hxqh.ma.model.assist.TaskDto;
import com.hxqh.ma.model.base.Message;
import com.hxqh.ma.repository.TaskRepository;
import com.hxqh.ma.service.SystemService;
import com.hxqh.ma.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Ocean lin on 2017/7/1.
 *
 * @author Lin
 */
@Controller
public class QueryController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * 报告跳转接口
     *
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String status() {
        return "market/bigData";
    }

    /**
     * 报告跳转接口
     *
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report() {
        return "reports/report";
    }

    /**
     * 交互式查询页面跳转接口
     *
     * @return
     */
    @RequestMapping("/query")
    public String query() {
        return "reports/query";
    }

}
