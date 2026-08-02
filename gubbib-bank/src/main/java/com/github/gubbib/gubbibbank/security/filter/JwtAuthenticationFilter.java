package com.github.gubbib.gubbibbank.security.filter;

import com.github.gubbib.gubbibbank.security.details.CustomUserDetails;
import com.github.gubbib.gubbibbank.security.jwt.JwtProvider;
import com.github.gubbib.gubbibbank.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("========JwtAuthenticationFilter 시작========");

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("Authorization Header={}", authorization == null ? "null" : "Bearer *****");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        log.debug("Authorization Header에서 JWT 추출 완료");

        if(!jwtProvider.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String email  = jwtProvider.getEmail(token);
        log.debug("JWT 이메일 추출 - email={}", email);

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);
        log.debug("회원 조회 완료 - email={}", email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        /*
            SecurityContextHolder는 내부적으로 ThreadLocal을 사용하여
            요청(Thread)마다 독립적인 SecurityContext를 관리한다.

            따라서 동시에 여러 사용자가 요청해도
            서로의 인증 정보(Authentication)가 덮어쓰이지 않는다.
        */
        log.debug("SecurityContext에 인증 정보 저장 - email={}", userDetails.getUsername());

        log.debug("========JwtAuthenticationFilter 종료========");
        filterChain.doFilter(request, response);
    }
}
