package com.github.robiiinos;

import com.github.robiiinos.service.api.internal.ArticleService;
import spark.Service;

public class PrivateServer extends Server {
    public PrivateServer(final int port) {
        super(port);
    }

    @Override
    protected void registerRoutes(final Service apiService) {
        new ArticleService(apiService);

        logger.info("Private routes have been registered.");
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        //

        logger.info("Private exceptions have been registered.");
    }
}
