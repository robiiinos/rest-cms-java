package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ArticleTranslation {
    public int id;
    public String title;
    public String content;
    public Language locale;
}
