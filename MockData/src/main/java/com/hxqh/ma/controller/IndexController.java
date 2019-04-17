package com.hxqh.ma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ocean lin on 2017/7/1.
 *
 * @author Lin
 */
@Controller
public class IndexController {


    /**
     * index
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "user/index";
    }

}
