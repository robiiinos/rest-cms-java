package com.github.robiiinos.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleTranslationDto {
    private String slug;
    private String title;
    private String content;
    private String locale;
}
