package com.github.robiiinos.request.external;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@Data
public class SlugRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private String slug;
}
