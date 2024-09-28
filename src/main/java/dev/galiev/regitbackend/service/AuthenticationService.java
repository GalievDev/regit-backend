package dev.galiev.regitbackend.service;

import dev.galiev.regitbackend.dto.AuthRequest;
import dev.galiev.regitbackend.dto.AuthResponse;
import dev.galiev.regitbackend.dto.RegisterRequest;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest request);
}
