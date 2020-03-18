package com.zte.dianping.config;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ProjectName: dianping-com.zte.dianping.config
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 12:36 2020/3/12
 * @Description:
 */
@Configuration
public class ElasticsearchRestClient {

    @Value("${elasticsearch.ip}")
    private String esAddress;

    @Bean(name = "highLevelClient")
    public RestHighLevelClient restHighLevelClient(){
        String[] address = esAddress.split(":");
        String ip = address[0];
        int port = Integer.valueOf(address[1]);
        HttpHost http = new HttpHost(ip, port, "http");
        return new RestHighLevelClient(RestClient.builder(new HttpHost[]{http}));
    }

}
