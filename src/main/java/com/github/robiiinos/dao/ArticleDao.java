package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.ReadDataSource;
import com.github.robiiinos.datasource.WriteDataSource;
import com.github.robiiinos.model.Article;
import com.github.robiiinos.model.ArticleTranslation;
import com.github.robiiinos.model.Language;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
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

    public final List<Article> findAll() {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.PARENT_ID.eq(ARTICLES.ID))
                .where(ARTICLE_TRANSLATIONS.LOCALE.eq("en"))
                .orderBy(ARTICLES.ID)
                .fetch();

        return map(articles);
    }

    public final List<Article> findAllByLanguage(String language) {
        Result<Record> articles = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.PARENT_ID.eq(ARTICLES.ID))
                .where(ARTICLE_TRANSLATIONS.LOCALE.eq(language))
                .orderBy(ARTICLES.ID)
                .fetch();

        return map(articles);
    }

    public final Article findBySlug(String slug) {
        Record article = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.PARENT_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.eq(slug))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq("en"))
                .orderBy(ARTICLES.ID)
                .fetchOne();

        return map(article);
    }

    public final Article findBySlugAndLanguage(String slug, String language) {
        Record article = readContext.select()
                .from(ARTICLES)
                .join(ARTICLE_TRANSLATIONS)
                .on(ARTICLE_TRANSLATIONS.PARENT_ID.eq(ARTICLES.ID))
                .where(ARTICLES.SLUG.eq(slug))
                .and(ARTICLE_TRANSLATIONS.LOCALE.eq(language))
                .orderBy(ARTICLES.ID)
                .fetchOne();

        return map(article);
    }

    private Article map(Record article) {
        return article.map(r -> {
            Language language = Language.builder()
                    .locale(r.getValue(ARTICLE_TRANSLATIONS.LOCALE))
                    .build();

            ArticleTranslation translation = ArticleTranslation.builder()
                    .title(r.getValue(ARTICLE_TRANSLATIONS.TITLE))
                    .content(r.getValue(ARTICLE_TRANSLATIONS.CONTENT))
                    .locale(language)
                    .build();

            return Article.builder()
                    .id(r.getValue(ARTICLES.ID))
                    .slug(r.getValue(ARTICLES.SLUG))
                    .translation(translation)
                    .build();
        });
    }

    private List<Article> map(Result<Record> articles) {
        return articles.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
