package com.github.robiiinos.dao;

import com.github.robiiinos.datasource.WriteDataSource;
import com.github.robiiinos.request.internal.CreateArticleTranslationRequest;
import com.github.robiiinos.request.internal.UpdateArticleTranslationRequest;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static jooq.generated.Tables.ARTICLE_TRANSLATIONS;

public class ArticleTranslationDao {
    private static final SQLDialect dialect = SQLDialect.MYSQL;

    private static final DSLContext writeContext = DSL.using(WriteDataSource.getDataSource(), dialect);

    public ArticleTranslationDao() {
    }

    public final void create(CreateArticleTranslationRequest articleRequest, int articleId) {
        writeContext.insertInto(ARTICLE_TRANSLATIONS)
                .set(ARTICLE_TRANSLATIONS.ARTICLE_ID, articleId)
                .set(ARTICLE_TRANSLATIONS.TITLE, articleRequest.getTitle())
                .set(ARTICLE_TRANSLATIONS.CONTENT, articleRequest.getContent())
                .set(ARTICLE_TRANSLATIONS.LOCALE, articleRequest.getLocale())
                .execute();
    }

    public final void update(UpdateArticleTranslationRequest articleRequest, int articleId) {
        writeContext.insertInto(ARTICLE_TRANSLATIONS)
                .set(ARTICLE_TRANSLATIONS.ARTICLE_ID, articleId)
                .set(ARTICLE_TRANSLATIONS.TITLE, articleRequest.getTitle())
                .set(ARTICLE_TRANSLATIONS.CONTENT, articleRequest.getContent())
                .set(ARTICLE_TRANSLATIONS.LOCALE, articleRequest.getLocale())
                .onDuplicateKeyUpdate()
                .set(ARTICLE_TRANSLATIONS.TITLE, articleRequest.getTitle())
                .set(ARTICLE_TRANSLATIONS.CONTENT, articleRequest.getContent())
                .execute();
    }

    // There's no need for a delete method because the action is cascaded by the Article relationship.
}
