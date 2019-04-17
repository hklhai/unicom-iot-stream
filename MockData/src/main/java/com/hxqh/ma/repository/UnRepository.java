package com.hxqh.ma.repository;

import com.hxqh.ma.model.Un;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ocean lin on 2019/3/21.
 *
 * @author Ocean lin
 */
@Repository
public interface UnRepository extends JpaRepository<Un, Integer> {
}
