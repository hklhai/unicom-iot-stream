package com.hxqh.ma.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ocean lin on 2019/3/18.
 *
 * @author Ocean lin
 */
public class EmailTest {
    @Test
    public void getEmailList() {
//
//        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(5000)   //设置连接超时时间
//                .setConnectionRequestTimeout(5000) // 设置请求超时时间
//                .setSocketTimeout(5000)
//                .setRedirectsEnabled(true)//默认允许自动重定向
//                .build();
//        HttpGet httpGet2 = new HttpGet("http://192.168.31.211:8282/jeesite/htjk/apphttpjk/getMailList");
//        httpGet2.setConfig(requestConfig);
//        String srtResult = "";
//        try {
//            HttpResponse httpResponse = httpCilent2.execute(httpGet2);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                //获得返回的结果
//                srtResult = EntityUtils.toString(httpResponse.getEntity());
//                System.out.println(srtResult);
//                String[] split = srtResult.replace("[", "").replace("]", "").replace("\"", "").split(",");
//                for (int i = 0; i < split.length; i++) {
//                    System.out.println(split[i]);
//                }
//            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
//                //..........
//            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
//                //.............
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpCilent2.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
