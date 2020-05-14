package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.ReadDataSource;
import com.github.robiiinos.datasource.WriteDataSource;
import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.dto.ArticleTranslationDto;
import com.github.robiiinos.request.internal.CreateArticleRequest;
import com.github.robiiinos.request.external.LocaleRequest;
import com.github.robiiinos.request.external.SlugRequest;
import com.github.robiiinos.request.internal.UpdateArticleRequest;
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

    public final List<ArticleDto> searchBySlugAndLanguage(SlugRequest slugRequest, LocaleRequest localeRequest) {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.likeIgnoreCase("%" + slugRequest.getSlug() + "%"))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID)
                .fetch();

        return mapToDto(articles);
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

    private List<ArticleTranslationDto> findAllTranslationsBySlug(String slug, String locale) {
        return readContext.select(
                ARTICLES.SLUG,
                ARTICLE_TRANSLATIONS.TITLE,
                ARTICLE_TRANSLATIONS.CONTENT,
                ARTICLE_TRANSLATIONS.LOCALE
        )
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.eq(slug))
                .and(ARTICLE_TRANSLATIONS.LOCALE.notEqual(locale))
                .orderBy(ARTICLES.ID)
                .fetchInto(ArticleTranslationDto.class);
    }

    public final int create(CreateArticleRequest articleRequest) {
        writeContext.insertInto(ARTICLES)
                .set(ARTICLES.SLUG, articleRequest.getSlug())
                .returning()
                .execute();

        return writeContext.lastID().intValue();
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
                .translations(findAllTranslationsBySlug(r.getValue(ARTICLES.SLUG), r.getValue(ARTICLE_TRANSLATIONS.LOCALE)))
                .build());
    }

    private List<ArticleDto> mapToDto(Result<Record> articles) {
        return articles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
