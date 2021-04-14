package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.payload.request.LoginRequest;
import pl.RK.PAIEVENTREST.payload.request.SignUpRequest;
import pl.RK.PAIEVENTREST.services.implementations.AuthServiceImp;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 7200)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthServiceImp authenticationManager;

    public AuthController(AuthServiceImp authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/singin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return  authenticationManager.authenticate(loginRequest);
    }
    @PostMapping("singup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return  authenticationManager.register(signUpRequest);
    }

}
