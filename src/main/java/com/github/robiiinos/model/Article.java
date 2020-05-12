package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Article {
    private int id;
    private String slug;
    private ArticleTranslation translation;
}
