//package com.petsup.api.database;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfig {
//    private final DatabaseProperties databaseProperties;
//
//    @Autowired
//    public DatabaseConfig(DatabaseProperties databaseProperties) {
//        this.databaseProperties = databaseProperties;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        dataSource.setUrl(databaseProperties.getUrl());
//        dataSource.setUsername(databaseProperties.getUsername());
//        dataSource.setPassword(databaseProperties.getPassword());
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}