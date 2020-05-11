package com.github.robiiinos;

import com.github.robiiinos.transformer.JsonTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Service;

public abstract class Server {
    protected static final Logger logger = LogManager.getLogger(Server.class);

    private final Service apiService;

    private static final int minThreads = 4;
    private static final int maxThreads = 8;
    private static final int maxTimeOut = 5000;

    private static final String contentTypeHeader = "application/json;charset=UTF-8";
    private static final String serverHeader = "";

    public Server (final int port) {
        // Ignite Spark Service with a given Port number.
        apiService = Service.ignite();
        apiService.port(port);

        // Note: Service parameters are set below, before
        // the Route mapping, to avoid inappropriate state.

        // Set the min/max threads and timeOut for Jetty Server.
        apiService.threadPool(maxThreads, minThreads, maxTimeOut);

        // Set the default Response Transformer to custom class with Gson.
        apiService.defaultResponseTransformer(new JsonTransformer());
    }

    protected final void start() {
        apiService.init();
        logger.info("Service is being initialized.");

        registerRoutes(apiService);

        registerExceptions(apiService);

        apiService.after((Request request, Response response) -> {
            response.header("Content-Type", contentTypeHeader);

            // Note: Useful for obfuscation if not running behind a reverse proxy.
            response.header("Server", serverHeader);
        });

        apiService.afterAfter("*", (Request request, Response response) -> {
            logger.debug("Service::{} => [{}] HTTP {} - {} {}",
                    apiService.port(),
                    request.ip(),
                    response.status(),
                    request.requestMethod(),
                    request.raw().getRequestURI()
            );
        });

        apiService.awaitInitialization();
        logger.info("Service has been initialized.");
    }

    protected abstract void registerRoutes(final Service apiService);

    protected abstract void registerExceptions(final Service apiService);
}
