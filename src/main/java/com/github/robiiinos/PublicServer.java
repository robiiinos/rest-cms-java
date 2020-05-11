package com.github.robiiinos;

import spark.Service;

public class PublicServer extends Server {
    public PublicServer(final int port) {
        super(port);
    }

    @Override
    protected void registerRoutes(final Service apiService) {
        //

        logger.info("Public routes have been registered.");
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        //

        logger.info("Public exceptions have been registered.");
    }
}
