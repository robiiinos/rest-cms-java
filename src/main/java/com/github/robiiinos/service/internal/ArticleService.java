package com.github.robiiinos.service.internal;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dao.ArticleTranslationDao;
import com.github.robiiinos.request.internal.CreateArticleRequest;
import com.github.robiiinos.request.internal.CreateArticleTranslationRequest;
import com.github.robiiinos.request.internal.UpdateArticleRequest;
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

    private static final ArticleDao articleDao = new ArticleDao();
    private static final ArticleTranslationDao translationDao = new ArticleTranslationDao();

    private static final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    public ArticleService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path("/p/" + PATH, () -> {

            apiService.post("", (final Request request, final Response response) -> {
                CreateArticleRequest articleRequest = new Gson().fromJson(request.body(), CreateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                int articleId = articleDao.create(articleRequest);
                articleRequest.getTranslations()
                        .parallelStream()
                        .forEach(t -> translationDao.create(t, articleId));

                return articleId;
            });

            apiService.put("/:slug", (final Request request, final Response response) -> {
                UpdateArticleRequest articleRequest = new Gson().fromJson(request.body(), UpdateArticleRequest.class);

                if (!validator.validate(articleRequest).isEmpty()) {
                    throw new ValidationException();
                }

                int result = articleDao.update(articleRequest, request.params(":slug"));
                articleRequest.getTranslations()
                        .parallelStream()
                        .forEach(t -> translationDao.update(t, articleRequest.getId()));

                return result;
            });

            apiService.delete("/:slug", (final Request request, final Response response) -> {
                return articleDao.delete(request.params(":slug"));
            });

        });
    }
}
