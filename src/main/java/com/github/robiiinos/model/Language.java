package com.github.robiiinos.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Language {
    ENGLISH("en"),
    CHINESE("cn"),
    FRENCH("fr"),
    GERMAN("de"),
    SPANISH("es"),
    ITALIAN("it");

    String locale;
}
