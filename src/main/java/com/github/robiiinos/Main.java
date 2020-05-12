package com.github.robiiinos;

import com.github.robiiinos.service.FlywayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Run migrations on start.
        // Note: This is to ensure database state.
        FlywayService.runMigrations();
        logger.info("Database has been migrated.");

        // Start the server for the public API.
        final PublicServer publicServer = new PublicServer(8080);
        publicServer.start();
        logger.info("API Public Server has started.");

        // Start the server for the private API.
        final PrivateServer privateServer = new PrivateServer(8084);
        privateServer.start();
        logger.info("API Private Server has started.");
    }
}
