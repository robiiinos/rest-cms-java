package com.github.robiiinos.service.api.external;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.model.Article;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                String language = request.queryParamOrDefault("locale", null);

                List<Article> articles;
                articles = language == null
                        ? dao.findAll()
                        : dao.findAllByLanguage(language);

                return toDto(articles);
            });

            // List of a slug-specific article based on the default Language.
            // Accept a queryParam (locale) to specify the Language.
            apiService.get("/:slug", (final Request request, final Response response) -> {
                String slug = request.params(":slug");
                String language = request.queryParamOrDefault("locale", null);

                Article article;
                article = language == null
                        ? dao.findBySlug(slug)
                        : dao.findBySlugAndLanguage(slug, language);

                return toDto(article);
            });

        });
    }

    private ArticleDto toDto(Article article) {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return mapper.map(article, ArticleDto.class);
    }

    private List<ArticleDto> toDto(List<Article> articles) {
        return articles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
