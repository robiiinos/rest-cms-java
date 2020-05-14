package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.ReadDataSource;
import com.github.robiiinos.datasource.WriteDataSource;
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

import static jooq.generated.Tables.*;

public class ArticleDao {
    private static final SQLDialect dialect = SQLDialect.MYSQL;

    private static final DSLContext readContext = DSL.using(ReadDataSource.getDataSource(), dialect);
    private static final DSLContext writeContext = DSL.using(WriteDataSource.getDataSource(), dialect);

    public ArticleDao() {
    }

    public final Result<Record> searchBySlugAndLanguage(SlugRequest slugRequest, LocaleRequest localeRequest) {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.likeIgnoreCase("%" + slugRequest.getSlug() + "%"))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID.desc())
                .fetch();

        return articles;
    }

    public final Result<Record> findAllByLanguage(LocaleRequest localeRequest) {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID.desc())
                .fetch();

        return articles;
    }

    public final Record findBySlugAndLanguage(SlugRequest slugRequest, LocaleRequest localeRequest) {
        Record article = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.ARTICLE_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.eq(slugRequest.getSlug()))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq(localeRequest.getLocale()))
                .orderBy(ARTICLES.ID.desc())
                .fetchOne();

        if (article == null) {
            throw new NoDataFoundException();
        }

        return article;
    }

    public List<ArticleTranslationDto> findAllTranslationsBySlug(String slug, String locale) {
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
                .orderBy(ARTICLE_TRANSLATIONS.LOCALE.asc())
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
}
