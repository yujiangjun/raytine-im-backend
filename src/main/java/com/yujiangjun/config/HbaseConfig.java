//package com.yujiangjun.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration
//@Slf4j
//public class HbaseConfig {
//
//    @Value("${hbase.zookeeper.quorum}")
//    private String quorum;
//
//    @Value("${hbase.zookeeper.property.clientPort}")
//    private Integer port;
//    @Bean
//    public Connection connection(){
//org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
//        config.set("hbase.zookeeper.quorum",quorum);
//        config.set("hbase.zookeeper.property.clientPort",String.valueOf(port));
//        try {
//            return ConnectionFactory.createConnection(config);
//        } catch (IOException e) {
//          log.error("hbase connect error:",e);
//        }
//        return null;
//    }
//}
