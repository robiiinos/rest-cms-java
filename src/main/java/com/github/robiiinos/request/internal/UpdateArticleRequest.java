package com.github.robiiinos.request.internal;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
@Builder
@Data
public class UpdateArticleRequest {
    @NotNull
    private int id;
    @NotNull
    @Size(min = 1, max = 255)
    private String slug;
    @NotEmpty
    private List<UpdateArticleTranslationRequest> translations;
}
