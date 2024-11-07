package com.oea.online_exam_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oea.online_exam_app.Requests.Auth.LoginRequest;
import com.oea.online_exam_app.Responses.Auth.LoginResponse;
import com.oea.online_exam_app.Services.AuthService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@ModelAttribute LoginRequest loginRequest) {
        try {
            int userId = authService.loginUser(loginRequest.getEmail(),loginRequest.getPassword());
            if (userId > 0) {
                LoginResponse loginResponse = new LoginResponse("success",userId,"login successful");
                return ResponseEntity.ok(loginResponse); 
            } else {
                LoginResponse loginResponse = new LoginResponse("failed",userId,"incorrect email or password");
                return ResponseEntity.status(401).body(loginResponse); 
            }
        } catch (Exception e) {
            LoginResponse loginResponse = new LoginResponse("failed",0,"internal server error");
            return ResponseEntity.status(500).body(loginResponse);
        }
    }
}
