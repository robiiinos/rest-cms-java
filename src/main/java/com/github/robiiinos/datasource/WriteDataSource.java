package com.github.robiiinos.datasource;

import com.github.robiiinos.service.PropertyService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public final class WriteDataSource {
    private static final HikariDataSource dataSource;
    private static final HikariConfig dataSourceConfig;

    private static final String DATASOURCE_PROPERTIES = "datasource.properties";
    private static final String HIKARI_WRITE_PROPERTIES = "hikari.write.properties";

    static {
        Properties dataSourceProperties = PropertyService.loadProperties(DATASOURCE_PROPERTIES);
        Properties hikariProperties = PropertyService.loadProperties(HIKARI_WRITE_PROPERTIES);

        Properties properties = new Properties();
        properties.putAll(dataSourceProperties);
        properties.putAll(hikariProperties);

        dataSourceConfig = new HikariConfig(properties);

        dataSource = new HikariDataSource(dataSourceConfig);
    }

    private WriteDataSource() {
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
