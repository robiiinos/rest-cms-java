package com.github.robiiinos.service.external;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.request.external.LocaleRequest;
import com.github.robiiinos.request.external.SlugRequest;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;

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
        // The following API routes accept a QueryParam called locale to localize results.
        apiService.path(PATH, () -> {

            apiService.get("", (final Request request, final Response response) -> {
                LocaleRequest localeRequest = LocaleRequest.builder().locale(
                        request.queryParamOrDefault("locale", "en")
                ).build();

                if (!validator.validate(localeRequest).isEmpty()) {
                    throw new ValidationException();
                }

                List<ArticleDto> articles = dao.findAllByLanguage(localeRequest);

                return articles;
            });

            apiService.get("/:slug", (final Request request, final Response response) -> {
                SlugRequest slugRequest = SlugRequest.builder().slug(request.params(":slug")).build();
                LocaleRequest localeRequest = LocaleRequest.builder().locale(
                        request.queryParamOrDefault("locale", "en")
                ).build();

                if (!validator.validate(slugRequest).isEmpty() || !validator.validate(localeRequest).isEmpty()) {
                    throw new ValidationException();
                }

                ArticleDto article = dao.findBySlugAndLanguage(slugRequest, localeRequest);

                return article;
            });

            apiService.get("/search/:slug", (final Request request, final Response response) -> {
                SlugRequest slugRequest = SlugRequest.builder().slug(request.params(":slug")).build();
                LocaleRequest localeRequest = LocaleRequest.builder().locale(
                        request.queryParamOrDefault("locale", "en")
                ).build();

                if (!validator.validate(slugRequest).isEmpty() || !validator.validate(localeRequest).isEmpty()) {
                    throw new ValidationException();
                }

                List<ArticleDto> articles = dao.searchBySlugAndLanguage(slugRequest, localeRequest);

                return articles;
            });

        });
    }
}
