package com.github.robiiinos.service.api.external;

import spark.Request;
import spark.Response;
import spark.Service;

public class ArticleService {
    private static final String PATH = "articles";

    public ArticleService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path(PATH, () -> {

            apiService.get("", (final Request request, final Response response) -> {
                return null;
            });

            apiService.get("/:slug", (final Request request, final Response response) -> {
                return null;
            });

            apiService.get("/:slug/:locale", (final Request request, final Response response) -> {
                return null;
            });

        });
    }
}
