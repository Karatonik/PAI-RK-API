package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.RK.PAIEVENTREST.jwt.JwtUtils;
import pl.RK.PAIEVENTREST.models.UserDetailsImp;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.payload.request.LoginRequest;
import pl.RK.PAIEVENTREST.models.payload.request.SignUpRequest;
import pl.RK.PAIEVENTREST.models.payload.response.JwtResponse;
import pl.RK.PAIEVENTREST.models.payload.response.MessageResponse;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.AuthServiceIF;

@Service
public class AuthServiceImp implements AuthServiceIF {

    AuthenticationManager authenticationManager;

    UserPaiRepository userPaiRepository;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, UserPaiRepository userPaiRepository
            , PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userPaiRepository = userPaiRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        System.out.println(authentication.getName());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        /*
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
*/
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getNick(),userDetails.getEmail()));
    }


    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        if (userPaiRepository.existsById(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        // Create new user's account
        UserPAI userPAI = new UserPAI(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNick());
        userPaiRepository.save(userPAI);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> singInByFacebook(SignUpRequest signUpRequest) {
        UserPAI userPAI = new UserPAI(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNick());
        userPaiRepository.save(userPAI);
        return authenticate(new LoginRequest(signUpRequest.getEmail(), signUpRequest.getPassword()));
    }

    @Override
    public ResponseEntity<?> singInByGoogle(SignUpRequest signUpRequest) {
        UserPAI userPAI = new UserPAI(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getNick());
        userPaiRepository.save(userPAI);
        return authenticate(new LoginRequest(signUpRequest.getEmail(), signUpRequest.getPassword()));
    }
}
