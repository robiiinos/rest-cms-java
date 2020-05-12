package com.github.robiiinos;

import com.github.robiiinos.service.api.external.ArticleService;
import org.jooq.exception.NoDataFoundException;
import spark.Service;

public class PublicServer extends Server {
    public PublicServer(final int port) {
        super(port);
    }

    @Override
    protected void registerRoutes(final Service apiService) {
        new ArticleService(apiService);

        logger.info("Public routes have been registered.");
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        apiService.exception(NoDataFoundException.class, (exception, request, response) -> {
            response.status(404);

            response.body(buildErrorPayload("Not Found"));
        });

        logger.info("Public exceptions have been registered.");
    }
}
