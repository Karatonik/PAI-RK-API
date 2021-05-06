package pl.RK.PAIEVENTREST.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.RK.PAIEVENTREST.models.payload.request.LoginRequest;
import pl.RK.PAIEVENTREST.models.payload.request.SignUpRequest;

public interface AuthServiceIF {
    ResponseEntity<?> authenticate(LoginRequest loginRequest);

    ResponseEntity<?> register(SignUpRequest signUpRequest);

    ResponseEntity<?> singInByFacebook(SignUpRequest signUpRequest);
    ResponseEntity<?> singInByGoogle(SignUpRequest signUpRequest);
}
