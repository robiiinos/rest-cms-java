package com.github.robiiinos.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@Data
public class CreateArticleTranslationRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private String title;
    @NotNull
    @Size(min = 1, max = 255)
    private String content;
    @NotNull
    @Size(min = 2, max = 2)
    private String locale;
}
