package com.github.robiiinos;

import com.github.robiiinos.controller.internal.ArticleController;
import com.github.robiiinos.controller.internal.UserController;
import com.github.robiiinos.service.AuthenticationService;
import com.google.gson.JsonSyntaxException;
import org.jooq.exception.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.validation.ValidationException;

public class PrivateServer extends Server {
    public PrivateServer(final int port) {
        super(port);
    }

    @Override
    protected void registerRoutes(final Service apiService) {
        new UserController(apiService);

        new ArticleController(apiService);

        logger.info("Private routes have been registered.");
    }

    @Override
    protected void registerFilters(final Service apiService) {
        apiService.before("/p/*", (final Request request, final Response response) -> {
            boolean authenticated;

            // Check the Authorization header (bearerToken - JWT) for authorization process.
            final AuthenticationService authenticationService = new AuthenticationService();
            String bearerToken = authenticationService.getBearerToken(request.headers("Authorization"));
            authenticated = authenticationService.isValid(bearerToken);

            if (!authenticated) {
                logger.warn("Someone tried to access the Private API from {}", request.ip());

                apiService.halt(401, buildErrorPayload("Unauthorized"));
            }
        });
    }

    @Override
    protected void registerExceptions(final Service apiService) {
        apiService.exception(JsonSyntaxException.class, (exception, request, response) -> {
            response.status(400);

            response.body(buildErrorPayload("Bad Request"));
        });

        apiService.exception(ValidationException.class, (exception, request, response) -> {
            response.status(400);

            response.body(buildErrorPayload("Bad Request"));
        });

        apiService.exception(DataAccessException.class, (exception, request, response) -> {
            response.status(400);

            response.body(buildErrorPayload("Bad Request"));
        });

        logger.info("Private exceptions have been registered.");
    }
}
