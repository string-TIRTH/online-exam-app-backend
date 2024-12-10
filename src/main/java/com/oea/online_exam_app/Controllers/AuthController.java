package com.oea.online_exam_app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oea.online_exam_app.Requests.Auth.LoginRequest;
import com.oea.online_exam_app.Responses.Auth.LoginResponse;
import com.oea.online_exam_app.Utils.EncryptData;
import com.oea.online_exam_app.Utils.JwtUtil;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
             Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority(); 

            String token = jwtUtil.generateToken(userDetails.getUsername(), role);
            EncryptData encryptData = new EncryptData();
            role = encryptData.encrypt(role.replace("ROLE_", ""));
            if (token != null) {
                LoginResponse loginResponse = new LoginResponse("success","login successful",token,role);
                return ResponseEntity.ok(loginResponse); 
            } else {
                LoginResponse loginResponse = new LoginResponse("failed","incorrect email or password",null,null);
                return ResponseEntity.status(401).body(loginResponse); 
            }
        } catch (Exception e) {
            LoginResponse loginResponse = new LoginResponse("failed","internal server error",null,null);
            return ResponseEntity.status(500).body(loginResponse);
        }
    }
}
