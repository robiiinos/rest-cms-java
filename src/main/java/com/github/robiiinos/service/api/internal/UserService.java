package com.github.robiiinos.service.api.internal;

import com.github.robiiinos.dao.UserDao;
import com.github.robiiinos.model.User;
import com.github.robiiinos.request.UserLoginRequest;
import com.github.robiiinos.request.UserRegisterRequest;
import com.github.robiiinos.service.AuthenticationService;
import com.google.gson.Gson;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class UserService {
    private static final String PATH = "users";

    private static final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    private static final UserDao dao = new UserDao();

    public UserService(final Service apiService) {
        registerRoutes(apiService);
    }

    private void registerRoutes(final Service apiService) {
        apiService.path(PATH, () -> {

            apiService.post("/register", (final Request request, final Response response) -> {
                UserRegisterRequest userRequest = new Gson().fromJson(request.body(), UserRegisterRequest.class);

                if (!validator.validate(userRequest).isEmpty()) {
                    throw new ValidationException();
                }

                return dao.create(userRequest);
            });

            apiService.post("/login", (final Request request, final Response response) -> {
                UserLoginRequest userRequest = new Gson().fromJson(request.body(), UserLoginRequest.class);

                if (!validator.validate(userRequest).isEmpty()) {
                    throw new ValidationException();
                }

                User user = dao.findByUsernameAndPassword(userRequest);

                return new AuthenticationService().createToken(user);
            });

        });
    }
}
