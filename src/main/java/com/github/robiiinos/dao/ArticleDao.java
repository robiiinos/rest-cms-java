package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.ReadDataSource;
import com.github.robiiinos.datasource.WriteDataSource;
import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.request.CreateArticleRequest;
import com.github.robiiinos.request.LocaleRequest;
import com.github.robiiinos.request.SlugRequest;
import com.github.robiiinos.request.UpdateArticleRequest;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.NoDataFoundException;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.Tables.*;

public class ArticleDao {
    private static final SQLDialect dialect = SQLDialect.MYSQL;

    private static final DSLContext readContext = DSL.using(ReadDataSource.getDataSource(), dialect);
    private static final DSLContext writeContext = DSL.using(WriteDataSource.getDataSource(), dialect);

    public ArticleDao() {
    }

    public final List<ArticleDto> findAllByLanguage(LocaleRequest localeRequest) {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID)
                .fetch();

        return mapToDto(articles);
    }

    public final ArticleDto findBySlugAndLanguage(SlugRequest slugRequest, LocaleRequest localeRequest) {
        Record article = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.eq(slugRequest.getSlug()))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID)
                .fetchOne();

        if (article == null) {
            throw new NoDataFoundException();
        }

        return mapToDto(article);
    }

    public final int create(CreateArticleRequest articleRequest) {
        return writeContext.insertInto(ARTICLES)
                .set(ARTICLES.SLUG, articleRequest.getSlug())
                .execute();
    }

    public final int update(UpdateArticleRequest articleRequest, String slug) {
        return writeContext.update(ARTICLES)
                .set(ARTICLES.SLUG, articleRequest.getSlug())
                .where(ARTICLES.SLUG.eq(slug))
                .execute();
    }

    public final int delete(String slug) {
        return writeContext.deleteFrom(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .execute();
    }

    private ArticleDto mapToDto(Record article) {
        return article.map(r -> ArticleDto.builder()
                .id(r.getValue(ARTICLE_TRANSLATIONS.ID))
                .slug(r.getValue(ARTICLES.SLUG))
                .title(r.getValue(ARTICLE_TRANSLATIONS.TITLE))
                .content(r.getValue(ARTICLE_TRANSLATIONS.CONTENT))
                .locale(r.getValue(ARTICLE_TRANSLATIONS.LOCALE))
                .build());
    }

    private List<ArticleDto> mapToDto(Result<Record> articles) {
        return articles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
