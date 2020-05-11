package com.github.robiiinos;

import spark.Service;

public class PrivateServer extends Server {
    public PrivateServer(final int port) {
        super(port);
    }

    @Override
    protected void registerRoutes(final Service apiService) {
        //

        logger.info("Private routes have been registered.");
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        //

        logger.info("Private exceptions have been registered.");
    }
}
