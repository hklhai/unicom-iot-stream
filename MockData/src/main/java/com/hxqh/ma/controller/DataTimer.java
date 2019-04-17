package com.hxqh.ma.controller;

import com.hxqh.ma.util.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.hxqh.ma.common.Constants.*;

/**
 * Created by Ocean lin on 2019/3/18.
 *
 * @author Ocean lin
 */
@Configuration
@EnableScheduling
public class DataTimer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${system.ip}")
    private String ip;

    @Value(value = "${system.port}")
    private String port;


    private static Map<String, String> filmMap = new HashMap<String, String>() {{
        put("film_playnum", "电影-播放量Top10");
        put("film_label_pie", "电影-分类占比");
        put("film_tit1e_score", "电影-电影评分Top10");
        put("film_tit1e_company", "电影-出品公司Top10");
        put("film_actor_playnum", "电影-播放量最多演员Top10");
        put("film_actor_score", "电影-评分最高演员Top10");
        put("film_director_playnum", "电影-播放量最多导演Top10");
        put("film_director_score", "电影-评分最高导演Top10");

        put("variety_playnum", "电影-播放量Top10");
        put("variety_label_pie", "综艺-分类占比");
        put("variety_playnum_label_pie", "综艺-播放量前10的分类占比");
        put("variety_guest_playnum", "综艺-播放量最多嘉宾Top10");

        put("soap_playnum", "综艺-播放量Top10");
        put("soap_label_pie", "电视剧-分类占比");
        put("soap_guest_playnum", "电视剧-播放量最多演员Top10");
        put("soap_director_playnum", "电视剧-播放量最多导演Top10");

        put("movie_sum_split_box", "网络文学-累计分账票房Top10");
        put("movie_sum_box", "网络文学-累计综合票房Top10");
        put("movie_realtime_split_box", "网络文学-实时分账票房Top10");
        put("movie_realtime_box", "上映电影-实时综合票房Top10");
        put("movie_sum_box_pie", "上映电影-累计综合票房占比");
        put("movie_realtime_box_pie", "上映电影-实时综合票房占比");
        put("movie_showInfo", "上映电影-排片场次Top10");
        put("movie_showInfo_pie", "上映电影-排片场次占比");

    }};

    private static Map<String, String> bookMap = new HashMap<String, String>() {{
        put("books_comment", "电视剧-累计评论量排名Top10");
        put("books_label", "图书-各类别占比情况");
        put("books_press", "图书-累计评论量最多出版社排名Top10");

        put("literature_title_clicknum", "图书-点击量排名Top10");
        put("literature_label_pie", "图书-各标签占比情况");
        put("literature_label_clicknum_pie", "图书-各标签点击量占比");
        put("literature_comment_title", "网络文学-累计评论量最多作品排名Top10");
        put("literature_comment_author", "网络文学-累计评论量最多作者排名Top10");
        put("literature_clicknum_author", "网络文学-累计点击量最多作者排名Top10");
        put("literature_clicknum_subclass", "网络文学-二级分类点击量占比");
        put("literature_subclass_pie", "网络文学-子类占比情况");

    }};

    private static Map<String, String> exceptMap = new HashMap<String, String>() {{
        put("soap_score_title", "电视剧-评分Top10");
        put("soap_guest_comment", "电视剧-评论量最多演员Top10");
        put("soap_director_comment", "电视剧-评论量最高导演Top10");
    }};

    @Autowired
    private TransportClient client;

    @Scheduled(cron = "0 0 12 * * ?")
    public void frontFilm() throws Exception {
        logger.info("frontFilm定时任务启动");
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("addTime")
                .from(DateUtils.getTodayDate()).to(DateUtils.getTomorrowDate());
        //生成DSL查询语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(rangequerybuilder);

        SearchRequestBuilder responsebuilder = client
                .prepareSearch(FRONT_FILM)
                .setTypes(FRONT_FILM_TYPE);
        SearchResponse response = responsebuilder
                .setQuery(QueryBuilders.boolQuery()
                        .must(rangequerybuilder))
                .addAggregation(AggregationBuilders.terms("userAgg").size(50).field("category")
                        //求和要放到最内层的分组语句里面
                        .subAggregation(AggregationBuilders.sum("sumAgg").field("numvalue")))
                .setFrom(0).setSize(50)
                .setExplain(true)
                .execute()
                .actionGet();

        Terms userAgg = response.getAggregations().get("userAgg");
        Map<String, Object> dataMap = new HashMap<>(50);
        Map<String, Object> zeroMap = new HashMap<>(50);
        for (Terms.Bucket entry : userAgg.getBuckets()) {
            Sum sum = entry.getAggregations().get("sumAgg");
            dataMap.put(entry.getKey().toString(), sum.getValue());
        }

        for (Map.Entry entry : filmMap.entrySet()) {
            if (dataMap.containsKey(entry.getKey()) && !dataMap.get(entry.getKey()).toString().equals("0.0")) {
                continue;
            } else {
                zeroMap.put(entry.getKey().toString(), null);
            }
        }

        // 1. 过滤失效项目
        if (zeroMap.size() > 0) {
            for (Map.Entry entry : zeroMap.entrySet()) {
                if (exceptMap.containsKey(entry.getKey())) {
                    zeroMap.remove(entry.getKey());
                }
            }

            // 2. 映射
            for (Map.Entry entry : zeroMap.entrySet()) {
                if (filmMap.containsKey(entry.getKey())) {
                    zeroMap.put(entry.getKey().toString(), filmMap.get(entry.getKey()));
                }
            }
        }

        zeroSendEmail(zeroMap);

    }


    @Scheduled(cron = "0 30 11 * * ?")
    public void book() throws Exception {
        logger.info("book定时任务启动");
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("addTime")
                .from(DateUtils.getTodayDate()).to(DateUtils.getTomorrowDate());
        //生成DSL查询语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(rangequerybuilder);

        SearchRequestBuilder responsebuilder = client
                .prepareSearch(FRONT_BOOK_INDEX)
                .setTypes(FRONT_BOOK_TYPE);
        SearchResponse response = responsebuilder
                .setQuery(QueryBuilders.boolQuery()
                        .must(rangequerybuilder))
                .addAggregation(AggregationBuilders.terms("userAgg").size(50).field("category")
                        //求和要放到最内层的分组语句里面
                        .subAggregation(AggregationBuilders.sum("sumAgg").field("numvalue")))
                .setFrom(0).setSize(50)
                .setExplain(true)
                .execute()
                .actionGet();

        Terms userAgg = response.getAggregations().get("userAgg");
        Map<String, Object> dataMap = new HashMap<>(50);
        Map<String, Object> zeroMap = new HashMap<>(50);
        for (Terms.Bucket entry : userAgg.getBuckets()) {
            Sum sum = entry.getAggregations().get("sumAgg");
            dataMap.put(entry.getKey().toString(), sum.getValue());
        }

        for (Map.Entry entry : bookMap.entrySet()) {
            if (dataMap.containsKey(entry.getKey()) && !dataMap.get(entry.getKey()).toString().equals("0.0")) {
                continue;
            } else {
                zeroMap.put(entry.getKey().toString(), null);
            }
        }

        // 1. 过滤失效项目
        if (zeroMap.size() > 0) {
            for (Map.Entry entry : zeroMap.entrySet()) {
                if (exceptMap.containsKey(entry.getKey())) {
                    zeroMap.remove(entry.getKey());
                }
            }

            // 2. 映射
            for (Map.Entry entry : zeroMap.entrySet()) {
                if (bookMap.containsKey(entry.getKey())) {
                    zeroMap.put(entry.getKey().toString(), bookMap.get(entry.getKey()));
                }
            }
        }

        zeroSendEmail(zeroMap);

    }

    private void zeroSendEmail(Map<String, Object> zeroMap) throws MessagingException {
        if (zeroMap.size() > 0) {
            // 3. 拼接
            StringBuilder builder = new StringBuilder(200);
            for (Map.Entry entry : zeroMap.entrySet()) {
                String string = entry.getValue().toString();
                builder.append(string).append(",");
            }
            String content = builder.toString();
            content = content.substring(0, content.length() - 1);
            content += "数据采集存在问题，请运维人员检查！";
            logger.info(content);

            // 4. 调用接口获取email 列表
            String[] emails = getEmailFromSystem();
            for (int i = 0; i < emails.length; i++) {
                System.out.println(emails[i]);
                // 5. 循环发送存在问题邮件
                Message message = initEmail();
                // 设置发送方式与接收者
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(emails[i]));
                message.setSubject("数据采集异常");
                message.setContent(content, "text/html;charset=utf-8");
                // 创建 Transport用于将邮件发送
                Transport.send(message);
            }

        }
    }


    private Message initEmail() throws MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.163.com");
        // 指定验证为true是否需要身份验证
        props.setProperty("mail.smtp.auth", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                // 密码验证
                return new PasswordAuthentication("15030268371", "bqcenter123456");
            }
        };

        Session session = Session.getInstance(props, auth);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        // 设置发送者
        message.setFrom(new InternetAddress("15030268371@163.com"));
        return message;
    }

    public String[] getEmailFromSystem() {
        String[] split = new String[10];
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接超时时间
                .setConnectTimeout(5000)
                // 设置请求超时时间
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                //默认允许自动重定向
                .setRedirectsEnabled(true)
                .build();

        String url = "http://" + ip + ":" + port + "/jeesite/htjk/apphttpjk/getMailList";
        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        try {
            HttpResponse httpResponse = httpCilent2.execute(httpGet2);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //获得返回的结果
                srtResult = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(srtResult);
                split = srtResult.replace("[", "").replace("]", "").replace("\"", "").split(",");

            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                //..........
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                //.............
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpCilent2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return split;

    }

}
