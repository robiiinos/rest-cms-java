package com.github.robiiinos.request;

import com.github.robiiinos.model.ArticleTranslation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
@Data
public class UpdateArticleRequest {
    private String slug;
    private List<ArticleTranslation> translations;
}
