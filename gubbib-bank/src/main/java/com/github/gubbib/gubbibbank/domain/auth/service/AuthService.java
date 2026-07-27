package com.github.gubbib.gubbibbank.domain.auth.service;

import com.github.gubbib.gubbibbank.domain.auth.dto.LoginRequest;
import com.github.gubbib.gubbibbank.domain.auth.dto.LoginResponse;
import com.github.gubbib.gubbibbank.domain.auth.dto.SignupRequest;

public interface AuthService {

    LoginResponse signup(SignupRequest request);
    LoginResponse login(LoginRequest request);
}
