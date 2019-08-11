package com.example.mongodb.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MongDbUtil
 * @Description MongDb工具类
 * @Author Liyihe
 * @Date 19-8-11 下午1:18
 * @Version 1.0
 */
@Configuration
public class MongDbUtil {

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private int port;
    @Value("${spring.data.mongodb.database}")
    private String dbname;
    @Bean
    public MongoDatabase db(){
        return  new MongoClient(host, port).getDatabase(dbname);
    }

}
