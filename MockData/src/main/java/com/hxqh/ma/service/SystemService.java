package com.hxqh.ma.service;

import com.hxqh.ma.model.Task;
import com.hxqh.ma.model.User;
import com.hxqh.ma.model.assist.Show;
import com.hxqh.ma.model.assist.StatusDto;
import com.hxqh.ma.model.assist.TaskDto;
import org.springframework.data.domain.Pageable;

/**
 * Created by Ocean lin on 2017/7/1.
 */

public interface SystemService {

    User findUserById(String name);

    void addTask(Task task);

    TaskDto taskData(Task task, Pageable page);

    void saveTask(Task task);

    Show showData(Long taskid);

    StatusDto statusData(Pageable pageable);
}
