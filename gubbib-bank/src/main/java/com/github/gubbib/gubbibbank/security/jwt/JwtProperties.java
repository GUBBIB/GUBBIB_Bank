package com.github.gubbib.gubbibbank.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties (
        String secret,
        long accessTokenExpiration
){
}
