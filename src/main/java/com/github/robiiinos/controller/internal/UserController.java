package com.github.robiiinos.controller.internal;

import com.github.robiiinos.request.internal.UserLoginRequest;
import com.github.robiiinos.request.internal.UserRegisterRequest;
import com.github.robiiinos.service.internal.UserService;
import com.google.gson.Gson;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class UserController {
    private static final String PATH = "users";

    private static final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    private static final UserService userService = new UserService();

    public UserController(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        // The following API routes accept a QueryParam called locale to localize results.
        apiService.path(PATH, () -> {

            apiService.post("/register", (final Request request, final Response response) -> {
                UserRegisterRequest userRequest = new Gson().fromJson(request.body(), UserRegisterRequest.class);

                if (!validator.validate(userRequest).isEmpty()) {
                    throw new ValidationException();
                }

                return userService.createUser(userRequest);
            });

            apiService.post("/login", (final Request request, final Response response) -> {
                UserLoginRequest userRequest = new Gson().fromJson(request.body(), UserLoginRequest.class);

                if (!validator.validate(userRequest).isEmpty()) {
                    throw new ValidationException();
                }

                return userService.loginUser(userRequest);
            });

        });
    }
}
