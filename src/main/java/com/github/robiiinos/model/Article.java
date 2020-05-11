package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Article {
    public int id;
    public String slug;
}
