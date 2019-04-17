package com.hxqh.ma.service;

import com.hxqh.ma.model.*;
import com.hxqh.ma.model.assist.Show;
import com.hxqh.ma.model.assist.StatusDto;
import com.hxqh.ma.model.assist.TaskDto;
import com.hxqh.ma.repository.*;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ocean lin on 2018/4/8.
 *
 * @author Ocean lin
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserRepository userDao;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BaiduInfoRepository baiduInfoRepository;
    @Autowired
    private CrawlerDoubanSocreRepository crawlerDoubanSocreRepository;
    @Autowired
    private TransportClient client;
    @Autowired
    private StatusRepository statusRepository;


    @Override
    public User findUserById(String name) {
        return userDao.findUserById(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public TaskDto taskData(Task task, Pageable page) {
        Page<Task> taskList = taskRepository.findAll(page);
        //获取结果集
        List<Task> users = taskList.getContent();
        Integer totalPages = taskList.getTotalPages();
        TaskDto taskDto = new TaskDto(users, page, totalPages);
        return taskDto;
    }

    @Override
    public StatusDto statusData(Pageable pageable) {
        Page<Status> statuses = statusRepository.findAll(pageable);
        //获取结果集
        List<Status> statusList = statuses.getContent();
        Integer totalPages = statuses.getTotalPages();
        StatusDto statusDto = new StatusDto(statusList, pageable, totalPages);
        return statusDto;
    }


    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Show showData(Long taskid) {
        Task task = taskRepository.findOne(taskid);
        String title = task.getTaskName().split(" ")[0];

        // 查询MySQL 百度数据
        BaiduInfo baiduInfo = null;
        List<BaiduInfo> baiduInfoList = baiduInfoRepository.getByName(title);
        if (baiduInfoList.size() > 0) {
            baiduInfo = baiduInfoList.get(0);
        }

        // 查询MySQL 豆瓣数据
        List<CrawlerDoubanScore> doubanScoreList = crawlerDoubanSocreRepository.getByTitle(title);
        CrawlerDoubanScore doubanScore = null;
        if (doubanScoreList.size() > 0) {
            doubanScore = doubanScoreList.get(0);
        }

        Show show = new Show(title, baiduInfo, doubanScore);
        return show;
    }


}
