package pl.RK.PAIEVENTREST.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.RK.PAIEVENTREST.payload.request.LoginRequest;
import pl.RK.PAIEVENTREST.payload.request.SignUpRequest;

public interface AuthServiceIF {
    public ResponseEntity<?> authenticate(LoginRequest loginRequest);

    public ResponseEntity<?> register(SignUpRequest signUpRequest);
}
