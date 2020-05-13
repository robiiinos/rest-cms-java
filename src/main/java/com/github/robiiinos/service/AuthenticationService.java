package com.github.robiiinos.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.robiiinos.model.User;

import java.util.Date;
import java.util.Properties;

public class AuthenticationService {
    private static final Properties authProperties;

    private static final String AUTH_PROPERTIES = "auth.properties";

    private static final JWTVerifier verifier;

    private static final String algorithm;
    private static final String issuer = "rest-cms-java";

    static {
        authProperties = PropertyService.loadProperties(AUTH_PROPERTIES);

        algorithm = authProperties.getProperty("algorithm.secret");

        verifier = JWT.require(Algorithm.HMAC512(algorithm))
                .withIssuer(issuer)
                .acceptLeeway(5)
                .build();
    }

    public AuthenticationService() {
    }

    public String getBearerToken(String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    public String createToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000L);

        return JWT.create()
                .withIssuedAt(now)
                .withNotBefore(now)
                .withIssuer(issuer)
                .withSubject(String.valueOf(user.getId()))
                .withClaim("name", user.getLastName() + " " + user.getFirstName())
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC512(algorithm));
    }

    public boolean isValid(String bearerToken) {
        boolean valid;
        try {
            verifier.verify(bearerToken);

            valid = true;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            valid = false;
        }

        return valid;
    }
}
