package com.github.robiiinos.service.api.internal;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.request.CreateArticleRequest;
import com.github.robiiinos.request.UpdateArticleRequest;
import com.google.gson.Gson;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class ArticleService {
    private static final String PATH = "articles";

    private static final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    private static final ArticleDao dao = new ArticleDao();

    public ArticleService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path(PATH, () -> {

            apiService.post("", (final Request request, final Response response) -> {
                CreateArticleRequest articleRequest = new Gson().fromJson(request.body(), CreateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                return dao.create(articleRequest);
            });

            apiService.put("/:slug", (final Request request, final Response response) -> {
                UpdateArticleRequest articleRequest = new Gson().fromJson(request.body(), UpdateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                return dao.update(articleRequest, request.params(":slug"));
            });

            apiService.delete("/:slug", (final Request request, final Response response) -> {
                return dao.delete(request.params(":slug"));
            });

        });
    }
}
