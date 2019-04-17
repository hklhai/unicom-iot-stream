package com.hxqh.ma.model.assist;

import com.hxqh.ma.model.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Ocean lin on 2018/4/9.
 *
 * @author Ocean lin
 */
public class TaskDto {

    private List<Task> users;
    private Pageable page;
    private Integer totalPages;

    public TaskDto() {
    }

    public TaskDto(List<Task> users, Pageable page, Integer totalPages) {
        this.users = users;
        this.page = page;
        this.totalPages = totalPages;
    }

    public List<Task> getUsers() {
        return users;
    }

    public void setUsers(List<Task> users) {
        this.users = users;
    }

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
