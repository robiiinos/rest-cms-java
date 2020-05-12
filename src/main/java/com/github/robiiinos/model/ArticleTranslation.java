package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ArticleTranslation {
    private int id;
    private String title;
    private String content;
    private Language locale;
}
