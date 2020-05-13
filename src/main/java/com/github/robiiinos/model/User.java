package com.github.robiiinos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
