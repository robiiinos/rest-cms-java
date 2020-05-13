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
public class UserRegisterRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 255)
    private String lastName;
    @NotNull
    @Size(min = 6, max = 64)
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
}
