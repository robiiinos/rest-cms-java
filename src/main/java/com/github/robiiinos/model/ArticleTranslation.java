package com.github.robiiinos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTranslation {
    private int id;
    private int articleId;
    private String title;
    private String content;
    private String locale;
}
