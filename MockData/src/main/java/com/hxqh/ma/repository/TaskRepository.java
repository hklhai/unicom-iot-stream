package com.hxqh.ma.repository;

import com.hxqh.ma.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Ocean lin on 2018/4/8.
 *
 * @author Ocean lin
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
