package com.hxqh.ma.controller;

import com.hxqh.ma.model.assist.StatusDto;
import com.hxqh.ma.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ocean lin on 2017/7/1.
 *
 * @author Lin
 */
@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private SystemService systemService;


    /**
     * 上映电影页面跳转接口
     *
     * @return
     */
    @RequestMapping("/filming")
    public String filming() {
        return "show/filming";
    }

    /**
     * 电影页面跳转接口
     *
     * @return
     */
    @RequestMapping("/film")
    public String user() {
        return "show/film";
    }

    /**
     * 网络文学页面跳转接口
     *
     * @return
     */
    @RequestMapping("/netLiterature")
    public String netLiterature() {
        return "show/netLiterature";
    }


    /**
     * 电视剧页面跳转接口
     *
     * @return
     */
    @RequestMapping("/tvSeries")
    public String tvSeries() {
        return "show/tvSeries";
    }


    /**
     * 综艺页面跳转接口
     *
     * @return
     */
    @RequestMapping("/variety")
    public String variety() {
        return "show/variety";
    }

    /**
     * 图书页面跳转接口
     *
     * @return
     */
    @RequestMapping("/book")
    public String book() {
        return "show/book";
    }


    @RequestMapping("/status")
    public String status() {
        return "show/status";
    }


    @ResponseBody
    @RequestMapping(value = "/statusData", method = RequestMethod.POST)
    public StatusDto statusData(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "15") Integer size) {
        StatusDto listData = null;
        Sort sort = new Sort(Sort.Direction.DESC, "sid");
        try {
            Pageable pageable = new PageRequest(page, size, sort);
            listData = systemService.statusData(pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


}
