package com.hxqh.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ocean Lin on 2018/10/11.
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

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    public String service(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF8"));
        StringBuffer buff = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buff.append(line);
        }
        String string = buff.toString();
        System.out.println(string);
        return "user/index";
    }

}
