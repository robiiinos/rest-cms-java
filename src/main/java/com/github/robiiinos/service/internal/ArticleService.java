package com.github.robiiinos.service.internal;

import com.github.robiiinos.dao.ArticleDao;
import com.github.robiiinos.dao.ArticleTranslationDao;
import com.github.robiiinos.request.internal.CreateArticleRequest;
import com.github.robiiinos.request.internal.UpdateArticleRequest;

public class ArticleService {
    private static final ArticleDao articleDao = new ArticleDao();
    private static final ArticleTranslationDao translationDao = new ArticleTranslationDao();

    public ArticleService() {
    }

    public void createArticleWithTranslations(CreateArticleRequest articleRequest) {
        int articleId = articleDao.create(articleRequest);

        articleRequest.getTranslations()
                .parallelStream()
                .forEach(t -> translationDao.create(t, articleId));
    }

    public void updateArticleWithTranslations(UpdateArticleRequest articleRequest, String oldSlug) {
        articleDao.update(articleRequest, oldSlug);

        articleRequest.getTranslations()
                .parallelStream()
                .forEach(t -> translationDao.update(t, articleRequest.getId()));
    }

    // Note: Translations are deleted by the database with an on cascade statement.
    public int deleteArticleWithTranslations(String slug) {
        return articleDao.delete(slug);
    }
}
