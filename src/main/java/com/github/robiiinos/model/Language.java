package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Language {
    public int id;
    public String name;
    public String locale;
}
