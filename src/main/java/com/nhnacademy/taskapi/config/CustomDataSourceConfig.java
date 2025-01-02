package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.keyManager.exception.KeyManagerException;
import com.nhnacademy.taskapi.keyManager.service.KeyFactoryManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class CustomDataSourceConfig {

    @Autowired
    private KeyFactoryManager keyFactoryManager;

    public DataSource ServerDataSource() {
        Map<String, String> secretMap;
        try {
            secretMap = keyFactoryManager.keyInit().getHeaders().toSingleValueMap();
        } catch (KeyManagerException e) {
            throw new RuntimeException(e);
        }

        // 데이터 베이스 연결 정보 추출
        String ip = secretMap.get("ip");
        String port = secretMap.get("port");
        String dbName = secretMap.get("dbName");
        String userName = secretMap.get("userName");
        String password = secretMap.get("password");

        // DataSource 설정 (Apache Commons DBCP2 사용)
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        System.out.println("jdbc:mysql://%s:%s/%s".formatted(ip, port, dbName));
        dataSource.setUrl("jdbc:mysql://%s:%s/%s".formatted(ip, port, dbName));
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        /*
        dataSource.setInitialSize(5); // 초기 커넥션 수; default=0
        dataSource.setMaxTotal(10); // 최대 커넥션 수; default=8
        dataSource.setMaxIdle(5); // 최대 유휴 커넥션 수; default=8
        dataSource.setMinIdle(2); // 최소 유휴 커넥션 수; default=0
         */

        return dataSource;
    }

    @Bean
    public DataSource DevDataSource() {
        String ip = "133.186.241.167";
        String port = "3306";
        String dbName = "nhn_academy_9";
        String userName = "nhn_academy_9";
        String password = "iRz&E!F&XxG4d?kP";

        // DataSource 설정 (Apache Commons DBCP2 사용)
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://%s:%s/%s".formatted(ip, port, dbName));
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        /*
        dataSource.setInitialSize(5); // 초기 커넥션 수; default=0
        dataSource.setMaxTotal(10); // 최대 커넥션 수; default=8
        dataSource.setMaxIdle(5); // 최대 유휴 커넥션 수; default=8
        dataSource.setMinIdle(2); // 최소 유휴 커넥션 수; default=0
         */

        return dataSource;
    }
}
