package com.github.robiiinos.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDto {
    private int id;
    private String slug;
    private String title;
    private String content;
    private String locale;
    private List<ArticleTranslationDto> translations;
}
