package com.github.robiiinos.service.api.external;

import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.model.Article;
import org.modelmapper.ModelMapper;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private ArticleDto toDto(Article article) {
        return new ModelMapper().map(article, ArticleDto.class);
    }

    private List<ArticleDto> toDto(List<Article> articles) {
        return articles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
