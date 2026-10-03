package clinic.service;

import clinic.dto.LoginRequest;
import clinic.dto.LoginResponse;
import clinic.dto.RegisterRequest;
import clinic.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    User getUserByEmail(String email);
    
}
