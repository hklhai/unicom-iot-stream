package com.hxqh.ma.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Ocean lin on 2017/7/1.
 *
 * @author Lin
 */
@RestController
public class DataController {

    @Autowired
    private TransportClient client;

    @RequestMapping("/demo")
    public String demo() {
        return "Live and Learn!";
    }


    @GetMapping("/get/book/novel")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        GetResponse result = this.client.prepareGet("book", "novel", id).get();
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }


    @PostMapping("/add/book/novel")
    @ResponseBody
    public ResponseEntity add(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "word_count") String wordcount,
            @RequestParam(name = "publish_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishdate) {
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject().
                    field("title", title).
                    field("author", author).
                    field("word_count", wordcount).
                    field("publish_date", publishdate.getTime())
                    .endObject();

            IndexResponse result = this.client.prepareIndex("book", "novel").setSource(content)
                    .get();
            return new ResponseEntity(result.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/book/novel")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(name = "id") String id) {
        DeleteResponse result = this.client.prepareDelete("book", "novel", id).get();
        return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
    }

    @PutMapping("/update/book/novel")
    @ResponseBody
    public ResponseEntity update(@RequestParam(name = "id") String id,
                                 @RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "author", required = false) String author,
                                 @RequestParam(name = "word_count", required = false) int wordcount,
                                 @RequestParam(name = "publish_date", required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishdate) {
        UpdateRequest update = new UpdateRequest("book", "novel", id);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (null != title) {
                builder.field("title", title);
            }
            if (null != author) {
                builder.field("author", author);
            }
            if (null != title) {
                builder.field("word_count", wordcount);
            }
            if (null != title) {
                builder.field("publish_date", publishdate);
            }
            builder.endObject();

            update.doc(builder);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            UpdateResponse result = this.client.update(update).get();
            return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/query/book/novel")
    @ResponseBody
    public ResponseEntity query(@RequestParam(name = "title", required = false) String title,
                                @RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "gt_word_count", defaultValue = "0") int gtwordcount,
                                @RequestParam(name = "lt_word_count", defaultValue = "0") Integer ltwordcount) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (author != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("author", author));
        }
        if (title != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count");
        rangeQueryBuilder.from(gtwordcount);
        if (ltwordcount != null && ltwordcount > 0) {
            rangeQueryBuilder.to(ltwordcount);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("book").setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder).setFrom(0).setSize(10);
        //数据量足够大时采用QUERY_THEN_FETCH
        System.out.println(searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.get();
        List<Map<String, Object>> result = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            result.add(hit.getSourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @PostMapping("/group/book")
    @ResponseBody
    public ResponseEntity book() {
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("addTime")
                .from("2019-3-18").to("2019-3-19");
        //生成DSL查询语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(rangequerybuilder);

        SearchRequestBuilder responsebuilder = client
                .prepareSearch("front_book_index")
                .setTypes("front_book_type");
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
        for (Terms.Bucket entry : userAgg.getBuckets()) {
            Sum sum = entry.getAggregations().get("sumAgg");
            System.out.println("user:" + entry.getKey() + "----------sum:" + sum.getValue());
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }



    @PostMapping("/group/film")
    @ResponseBody
    public ResponseEntity group() {
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("addTime")
                .from("2019-3-17 00:00:01").to("2019-3-18 00:00:03");
        //生成DSL查询语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(rangequerybuilder);

        SearchRequestBuilder responsebuilder = client
                .prepareSearch("front_film")
                .setTypes("film");
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
        for (Terms.Bucket entry : userAgg.getBuckets()) {
            Sum sum = entry.getAggregations().get("sumAgg");
            System.out.println("user:" + entry.getKey() + "----------sum:" + sum.getValue());
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }


}
