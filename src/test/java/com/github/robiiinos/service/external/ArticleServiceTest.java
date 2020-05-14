package com.github.robiiinos.service.external;

import com.github.robiiinos.dto.ArticleDto;
import com.github.robiiinos.request.external.LocaleRequest;
import com.github.robiiinos.request.external.SlugRequest;
import com.google.common.collect.ImmutableList;
import org.jooq.exception.NoDataFoundException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleService articleService;

    private List<ArticleDto> articlesDto;

    @Before
    public void setUp() {
        articlesDto = ImmutableList.of(
                ArticleDto.builder().id(1).slug("what-is-bitcoin").title("Bitcoin is a cryptocurrency")
                        .content("Content.").locale("en").build(),
                ArticleDto.builder().id(4).slug("what-is-ethereum").title("Ethereum is a cryptocurrency")
                        .content("Content.").locale("en").build(),
                ArticleDto.builder().id(6).slug("what-is-litecoin").title("Litecoin is a cryptocurrency")
                        .content("Content.").locale("en").build(),
                ArticleDto.builder().id(7).slug("what-is-tether").title("Tether is a cryptocurrency")
                        .content("Content.").locale("en").build(),
                ArticleDto.builder().id(10).slug("what-is-ripple").title("Ripple is a cryptocurrency")
                        .content("Content.").locale("en").build()
        );
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void searchArticleBySlug_ValidSlug_ENLocale_ReturnsArticlesList_OneResult() {
        SlugRequest slugRequest = SlugRequest.builder().slug("bitcoin").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();
        List<ArticleDto> results = articleService.searchArticlesBySlugAndLanguageWithTranslations(slugRequest, localeRequest);

        assertThat(results, hasSize(1));

        assertThat(results.get(0).getId(), equalTo(articlesDto.get(0).getId()));
        assertThat(results.get(0).getSlug(), equalTo(articlesDto.get(0).getSlug()));
        assertThat(results.get(0).getTitle(), equalTo(articlesDto.get(0).getTitle()));
        assertThat(results.get(0).getContent(), equalTo(articlesDto.get(0).getContent()));
        assertThat(results.get(0).getLocale(), equalTo(articlesDto.get(0).getLocale()));
    }

    @Test
    public void searchArticleBySlug_ValidSlug_ENLocale_ReturnsArticlesList_MultipleResults() {
        SlugRequest slugRequest = SlugRequest.builder().slug("what").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();
        List<ArticleDto> results = articleService.searchArticlesBySlugAndLanguageWithTranslations(slugRequest, localeRequest);

        assertThat(results, hasSize(articlesDto.size()));

        assertThat(results.get(1).getId(), equalTo(articlesDto.get(3).getId()));
        assertThat(results.get(1).getSlug(), equalTo(articlesDto.get(3).getSlug()));
        assertThat(results.get(1).getTitle(), equalTo(articlesDto.get(3).getTitle()));
        assertThat(results.get(1).getContent(), equalTo(articlesDto.get(3).getContent()));
        assertThat(results.get(1).getLocale(), equalTo(articlesDto.get(3).getLocale()));
    }

    @Test
    public void searchArticleBySlug_InvalidSlug_ENLocale_ReturnsEmpty() {
        SlugRequest slugRequest = SlugRequest.builder().slug("neo").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();
        List<ArticleDto> results = articleService.searchArticlesBySlugAndLanguageWithTranslations(slugRequest, localeRequest);

        assertThat(results, empty());
    }

    @Test
    public void searchArticleBySlug_ValidSlug_InvalidLocale_ReturnsEmpty() {
        SlugRequest slugRequest = SlugRequest.builder().slug("bitcoin").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("cn").build();
        List<ArticleDto> results = articleService.searchArticlesBySlugAndLanguageWithTranslations(slugRequest, localeRequest);

        assertThat(results, empty());
    }

    @Test
    public void listArticles_ENLocale_ReturnsArticlesList() {
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();
        List<ArticleDto> results = articleService.listArticlesWithTranslations(localeRequest);

        assertThat(results, hasSize(articlesDto.size()));

        assertThat(results.get(0).getId(), equalTo(articlesDto.get(4).getId()));
        assertThat(results.get(0).getSlug(), equalTo(articlesDto.get(4).getSlug()));
        assertThat(results.get(0).getTitle(), equalTo(articlesDto.get(4).getTitle()));
        assertThat(results.get(0).getContent(), equalTo(articlesDto.get(4).getContent()));
        assertThat(results.get(0).getLocale(), equalTo(articlesDto.get(4).getLocale()));
    }

    @Test
    public void listArticles_InvalidLocale_ReturnsEmpty() {
        LocaleRequest localeRequest = LocaleRequest.builder().locale("cn").build();
        List<ArticleDto> results = articleService.listArticlesWithTranslations(localeRequest);

        assertThat(results, empty());
    }

    @Test
    public void findArticleBySlug_ValidSlug_ENLocale_ReturnsArticle() {
        SlugRequest slugRequest = SlugRequest.builder().slug("what-is-bitcoin").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();

        ArticleDto result = articleService.findArticleBySlugAndLanguageWithTranslations(slugRequest, localeRequest);

        assertThat(result.getId(), equalTo(articlesDto.get(0).getId()));
        assertThat(result.getSlug(), equalTo(articlesDto.get(0).getSlug()));
        assertThat(result.getTitle(), equalTo(articlesDto.get(0).getTitle()));
        assertThat(result.getContent(), equalTo(articlesDto.get(0).getContent()));
        assertThat(result.getLocale(), equalTo(articlesDto.get(0).getLocale()));
    }

    @Test(expected = NoDataFoundException.class)
    public void findArticleBySlug_ValidSLug_InvalidLocale_ThrowsNoDataException() {
        SlugRequest slugRequest = SlugRequest.builder().slug("what-is-bitcoin").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("cn").build();

        articleService.findArticleBySlugAndLanguageWithTranslations(slugRequest, localeRequest);
    }

    @Test(expected = NoDataFoundException.class)
    public void findArticleBySlug_InvalidSlug_ENLocale_ThrowsNoDataException() {
        SlugRequest slugRequest = SlugRequest.builder().slug("bitcoin").build();
        LocaleRequest localeRequest = LocaleRequest.builder().locale("en").build();

        articleService.findArticleBySlugAndLanguageWithTranslations(slugRequest, localeRequest);
    }
}