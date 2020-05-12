package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Language {
    private int id;
    private String name;
    private String locale;
}
