package com.github.robiiinos.service.api.internal;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.request.CreateArticleRequest;
import com.github.robiiinos.request.UpdateArticleRequest;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Service;

public class ArticleService {
    private static final String PATH = "articles";

    private static final ArticleDao dao = new ArticleDao();

    public ArticleService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path(PATH, () -> {

            apiService.post("", (final Request request, final Response response) -> {
                CreateArticleRequest articleRequest = new Gson().fromJson(request.body(), CreateArticleRequest.class);

                return dao.create(articleRequest);
            });

            apiService.put("/:slug", (final Request request, final Response response) -> {
                UpdateArticleRequest articleRequest = new Gson().fromJson(request.body(), UpdateArticleRequest.class);

                return dao.update(articleRequest, request.params(":slug"));
            });

            apiService.delete("/:slug", (final Request request, final Response response) -> {
                return dao.delete(request.params(":slug"));
            });

        });
    }
}
