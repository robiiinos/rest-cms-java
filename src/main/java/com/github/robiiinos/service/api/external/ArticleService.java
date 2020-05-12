package com.github.robiiinos.service.api.external;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dto.ArticleDto;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.List;

public class ArticleService {
    private static final String PATH = "articles";

    private static final ArticleDao dao = new ArticleDao();

    public ArticleService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path(PATH, () -> {

            // List of all articles based on the default Language.
            // Accept a queryParam (locale) to specify the Language.
            apiService.get("", (final Request request, final Response response) -> {
                String language = request.queryParamOrDefault("locale", "en");

                List<ArticleDto> articles = dao.findAllByLanguage(language);

                return articles;
            });

            // List of a slug-specific article based on the default Language.
            // Accept a queryParam (locale) to specify the Language.
            apiService.get("/:slug", (final Request request, final Response response) -> {
                String slug = request.params(":slug");
                String language = request.queryParamOrDefault("locale", "en");

                ArticleDto article = dao.findBySlugAndLanguage(slug, language);

                return article;
            });

        });
    }
}
