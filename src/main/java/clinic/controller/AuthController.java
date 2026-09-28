package clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinic.dto.LoginRequest;
import clinic.dto.LoginResponse;
import clinic.dto.RegisterRequest;
import clinic.entity.User;
import clinic.service.AuthService;

@RestController 
@RequestMapping("api/v1/auth") 
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/register")
    public ResponseEntity<User>register(@RequestBody RegisterRequest request){
        User user =authService.register(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    } 
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    } 
    
}
