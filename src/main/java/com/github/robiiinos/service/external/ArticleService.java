package com.github.robiiinos.service.external;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.request.external.LocaleRequest;
import com.github.robiiinos.request.external.SlugRequest;
import com.github.robiiinos.request.internal.CreateArticleRequest;
import org.jooq.Record;
import org.jooq.Result;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.Tables.ARTICLES;
import static jooq.generated.Tables.ARTICLE_TRANSLATIONS;

public class ArticleService {
    private static final ArticleDao articleDao = new ArticleDao();

    public ArticleService() {
    }

    public List<ArticleDto> listArticlesWithTranslations(LocaleRequest localeRequest) {
        return toDto(
                articleDao.findAllByLanguage(localeRequest)
        );
    }

    public ArticleDto findArticleBySlugAndLanguageWithTranslations(SlugRequest slugRequest, LocaleRequest localeRequest) {
        return toDto(
                articleDao.findBySlugAndLanguage(slugRequest, localeRequest)
        );
    }

    public List<ArticleDto> searchArticlesBySlugAndLanguageWithTranslations(SlugRequest slugRequest, LocaleRequest localeRequest) {
        return toDto(
                articleDao.searchBySlugAndLanguage(slugRequest, localeRequest)
        );
    }

    private ArticleDto toDto(Record article) {
        return article.map(r -> ArticleDto.builder()
                .id(r.getValue(ARTICLE_TRANSLATIONS.ID))
                .slug(r.getValue(ARTICLES.SLUG))
                .title(r.getValue(ARTICLE_TRANSLATIONS.TITLE))
                .content(r.getValue(ARTICLE_TRANSLATIONS.CONTENT))
                .locale(r.getValue(ARTICLE_TRANSLATIONS.LOCALE))
                .translations(
                        articleDao.findAllTranslationsBySlug(r.getValue(ARTICLES.SLUG), r.getValue(ARTICLE_TRANSLATIONS.LOCALE))
                )
                .build());
    }

    private List<ArticleDto> toDto(Result<Record> articles) {
        return articles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
