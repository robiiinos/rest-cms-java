package com.github.robiiinos.datasource;

import com.github.robiiinos.service.PropertyService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public final class ReadDataSource {
    private static final HikariDataSource dataSource;
    private static final HikariConfig dataSourceConfig;

    private static final String DATASOURCE_PROPERTIES = "datasource.properties";
    private static final String HIKARI_READ_PROPERTIES = "hikari.read.properties";

    static {
        Properties dataSourceProperties = PropertyService.loadProperties(DATASOURCE_PROPERTIES);
        Properties hikariProperties = PropertyService.loadProperties(HIKARI_READ_PROPERTIES);

        Properties properties = new Properties();
        properties.putAll(dataSourceProperties);
        properties.putAll(hikariProperties);

        dataSourceConfig = new HikariConfig(properties);

        dataSource = new HikariDataSource(dataSourceConfig);
    }

    private ReadDataSource() {
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
