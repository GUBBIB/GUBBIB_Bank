package com.github.gubbib.gubbibbank.security.jwt;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(Member member){
        log.debug("Access Token 생성 시작 - email={}", member.getEmail());

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(
                jwtProperties.accessTokenExpiration()
        );

        String accessToken = Jwts.builder()
                .subject(member.getEmail())
                .claim("role", member.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

        log.debug("Access Token 생성 완료 - email={}, expiration={}",
                member.getEmail(),
                expiration);

        return accessToken;
    }

    public long getAccessTokenExpiration(){
        return jwtProperties.accessTokenExpiration();
    }

    public boolean validateToken(String token){
        log.debug("JWT 검증 시작");

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            log.debug("JWT 검증 성공");

            return true;
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT입니다.", e);
        } catch (SecurityException e) {
            log.debug("JWT 서명이 유효하지 않습니다.", e);
        } catch(MalformedJwtException e) {
            log.debug("JWT 형식이 올바르지 않습니다.", e);
        } catch(UnsupportedJwtException e) {
            log.debug("지원하지 않는 JWT입니다.", e);
        } catch(IllegalArgumentException e) {
            log.debug("JWT가 비어 있습니다.", e);
        }
        return false;
    }

    public String getEmail(String token){
        log.debug("JWT에서 이메일 추출 시작");

        Claims claims = Jwts.parser()           // JWT 해석기를 만든다.
                .verifyWith(secretKey)          // 이 키로 검증하도록 설정한다.
                .build()                        // 해석기 완성
                .parseSignedClaims(token)       // 토큰을 해석 + 서명 검증 + 만료 확인
                .getPayload();                  // 검증된 Payload(Claims)를 가져온다.

        String email = claims.getSubject();

        log.debug("JWT에서 이메일 추출 완료 - email={}", email);

        return email;
    }
}