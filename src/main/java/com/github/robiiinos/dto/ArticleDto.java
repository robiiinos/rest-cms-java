package com.github.robiiinos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ArticleDto {
    private int id;
    private String slug;

    private String title;
    private String content;

    private String locale;
}
