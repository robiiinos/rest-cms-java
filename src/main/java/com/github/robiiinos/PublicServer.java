package com.github.robiiinos;

import com.github.robiiinos.service.external.ArticleService;
import org.jooq.exception.NoDataFoundException;
import spark.Service;

import javax.validation.ValidationException;

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
    protected void registerFilters(final Service apiService) {
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        apiService.exception(ValidationException.class, (exception, request, response) -> {
            response.status(400);

            response.body(buildErrorPayload("Invalid Request"));
        });

        apiService.exception(NoDataFoundException.class, (exception, request, response) -> {
            response.status(404);

            response.body(buildErrorPayload("Not Found"));
        });

        logger.info("Public exceptions have been registered.");
    }
}
