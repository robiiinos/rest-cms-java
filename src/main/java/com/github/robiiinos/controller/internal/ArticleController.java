package com.github.robiiinos.controller.internal;

import com.github.robiiinos.request.internal.CreateArticleRequest;
import com.github.robiiinos.request.internal.UpdateArticleRequest;
import com.github.robiiinos.service.internal.ArticleService;
import com.google.gson.Gson;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class ArticleController {
    private static final String PATH = "articles";

    private static final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    private static final ArticleService articleService = new ArticleService();

    public ArticleController(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        // The following API routes accept a QueryParam called locale to localize results.
        apiService.path("/p/" + PATH, () -> {

            apiService.post("", (final Request request, final Response response) -> {
                CreateArticleRequest articleRequest = new Gson().fromJson(request.body(), CreateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                articleService.createArticleWithTranslations(articleRequest);

                response.status(201);
                return articleRequest;
            });

            apiService.put("/:slug", (final Request request, final Response response) -> {
                UpdateArticleRequest articleRequest = new Gson().fromJson(request.body(), UpdateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                articleService.updateArticleWithTranslations(articleRequest, request.params(":slug"));

                response.status(201);
                return articleRequest;
            });

            apiService.delete("/:slug", (final Request request, final Response response) -> {
                return articleService.deleteArticleWithTranslations(request.params(":slug"));
            });

        });
    }
}
