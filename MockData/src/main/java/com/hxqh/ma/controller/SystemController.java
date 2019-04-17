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
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * 电影页面跳转接口
     *
     * @return
     */
    @RequestMapping("/film")
    public String user() {
        return "market/film";
    }

    /**
     * 上映电影页面跳转接口
     *
     * @return
     */
    @RequestMapping("/filming")
    public String filming() {
        return "market/filming";
    }

    /**
     * 网络文学页面跳转接口
     *
     * @return
     */
    @RequestMapping("/netLiterature")
    public String netLiterature() {
        return "market/netLiterature";
    }


    /**
     * 电视剧页面跳转接口
     *
     * @return
     */
    @RequestMapping("/tvSeries")
    public String tvSeries() {
        return "market/tvSeries";
    }


    /**
     * 综艺页面跳转接口
     *
     * @return
     */
    @RequestMapping("/variety")
    public String variety() {
        return "market/variety";
    }

    /**
     * 图书页面跳转接口
     *
     * @return
     */
    @RequestMapping("/book")
    public String book() {
        return "market/book";
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

    /**
     * 数据接口
     * http://127.0.0.1:8090/system/userData?name=xdm
     *
     * @param name 用户名
     * @return
     */
    @RequestMapping("userData")
    @ResponseBody
    public User userData(@RequestParam(value = "name") String name) {
        return systemService.findUserById(name);
    }

    /**
     * 新增任务接口
     *
     * @param task 新增任务
     * @return
     */
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    @ResponseBody
    public Message addTask(Task task) {
        Message message = null;
        try {
            task.setCreateTime(new Date());
            task.setTaskType("analysis");
            task.setTaskStatus(Constants.UNSUBMIT);
            // 设置task_name
            String taskParam = task.getTaskParam();
            JSONObject jsonObject = JSON.parseObject(taskParam);
            String param = jsonObject.getString(Constants.PARAM_TITLE);
            task.setTaskName(param + " " + DateUtils.formatTime(new Date()));
            systemService.addTask(task);
            message = new Message(Constants.SUCCESS, Constants.ADDSUCCESS);
        } catch (Exception e) {
            message = new Message(Constants.FAIL, Constants.ADDFAIL);
            e.printStackTrace();
        } finally {
            return message;
        }
    }


    /**
     * 分页查询接口
     *
     * @param task 任务
     * @param page 页数
     * @param size 页容量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/taskData", method = RequestMethod.POST)
    public TaskDto taskData(Task task,
                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "size", defaultValue = "15") Integer size) {
        TaskDto listData = null;
        Sort sort = new Sort(Sort.Direction.DESC, "taskid");
        try {
            Pageable pageable = new PageRequest(page, size, sort);
            listData = systemService.taskData(task, pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


    /**
     * 提交作业接口
     *
     * @param task 任务实体类
     * @return
     */
    @RequestMapping(value = "/submitTask", method = RequestMethod.POST)
    @ResponseBody
    public Message submitTask(Task task) {
        Message message = null;
        try {
            Task taskDB = taskRepository.findOne(task.getTaskid());
            taskDB.setTaskStatus(Constants.UNDO);
            systemService.saveTask(taskDB);
            message = new Message(Constants.SUCCESS, Constants.ADDSUCCESS);
        } catch (Exception e) {
            message = new Message(Constants.FAIL, Constants.ADDFAIL);
            e.printStackTrace();
        } finally {
            return message;
        }
    }


    /**
     * 交互式查询数据接口
     *
     * @param task 任务
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/showData", method = RequestMethod.POST)
    public Show showData(Task task) {
        return systemService.showData(task.getTaskid());
    }


}
