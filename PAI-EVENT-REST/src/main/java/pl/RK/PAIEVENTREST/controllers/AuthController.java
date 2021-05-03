package pl.RK.PAIEVENTREST.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.payload.request.LoginRequest;
import pl.RK.PAIEVENTREST.models.payload.request.SignUpRequest;
import pl.RK.PAIEVENTREST.services.implementations.AuthServiceImp;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 7200)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthServiceImp authenticationManager;

    public AuthController(AuthServiceImp authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("singin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationManager.authenticate(loginRequest);
    }

    @PostMapping("singup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticationManager.register(signUpRequest);
    }

    @PostMapping("facebook")
    public ResponseEntity<?> signInWithFacebook(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticationManager.singInByFacebook(signUpRequest);
    }

}
