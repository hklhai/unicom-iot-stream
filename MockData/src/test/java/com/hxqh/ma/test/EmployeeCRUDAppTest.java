package com.hxqh.ma.test;

import com.hxqh.ma.common.Constants;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ocean lin on 2017/10/16.
 */
public class EmployeeCRUDAppTest {

    private TransportClient client;

//    @Before
//    public void init() throws Exception {
//        Settings settings = Settings.builder()
//                .put("cluster.name", "elasticsearch")
//                .build();
//
//        client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(
//                        InetAddress.getByName(Constants.HOST_SPARK3), Constants.ES_PORT));

//		createEmployee(client);
//		getEmployee(client);
//		updateEmployee(client);
//		deleteEmployee(client);

//        client.close();
//    }




    //    /**
//     * 创建员工信息（创建一个document）
//     */
//    @Test
//    public void createEmployee() throws Exception {
//        IndexResponse response = client.prepareIndex("company", "employee", "1")
//                .setSource(XContentFactory.jsonBuilder()
//                        .startObject()
//                        .field("name", "jack")
//                        .field("age", 27)
//                        .field("position", "technique")
//                        .field("country", "china")
//                        .field("join_date", "2017-01-01")
//                        .field("salary", 10000)
//                        .endObject())
//                .get();
//        System.out.println(response.getResult());
//        client.close();
//    }
//
//    /**
//     * 获取员工信息
//     *
//     * @throws Exception
//     */
//    @Test
//    public void getEmployee() throws Exception {
//        GetResponse response = client.prepareGet("company", "employee", "1").get();
//        System.out.println(response.getSourceAsString());
//    }
//
//
//    /**
//     * 修改员工信息
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updateEmployee() throws Exception {
//        UpdateResponse response = client.prepareUpdate("company", "employee", "1")
//                .setDoc(XContentFactory.jsonBuilder()
//                        .startObject()
//                        .field("position", "technique manager")
//                        .endObject())
//                .get();
//        System.out.println(response.getResult());
//    }
//
//
//    /**
//     * 删除 员工信息
//     *
//     * @throws Exception
//     */
//    @Test
//    public void deleteEmployee() throws Exception {
//        DeleteResponse response = client.prepareDelete("company", "employee", "1").get();
//        System.out.println(response.getResult());
//    }

}
