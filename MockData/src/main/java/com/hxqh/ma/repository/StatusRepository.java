package com.hxqh.ma.repository;

import com.hxqh.ma.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ocean lin on 2018/9/13.
 *
 * @author Ocean lin
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
