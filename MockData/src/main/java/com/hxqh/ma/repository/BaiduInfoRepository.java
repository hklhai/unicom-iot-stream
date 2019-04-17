package com.hxqh.ma.repository;

import com.hxqh.ma.model.BaiduInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ocean lin on 2018/4/20.
 *
 * @author Ocean lin
 */
@Repository
public interface BaiduInfoRepository extends JpaRepository<BaiduInfo, Integer> {

    List<BaiduInfo> getByName(String name);
}
