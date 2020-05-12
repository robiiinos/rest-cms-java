package com.github.robiiinos.service;

import com.github.robiiinos.datasource.WriteDataSource;
import org.flywaydb.core.Flyway;

public final class FlywayService {
    private static final Flyway flyway;

    static {
        flyway = Flyway.configure()
                .dataSource(WriteDataSource.getDataSource())
                .load();
    }

    private FlywayService() {
    }

    public static void runMigrations()
    {
        flyway.migrate();
    }

    public static void resetDatabase() {
        flyway.clean();

        runMigrations();
    }
}
