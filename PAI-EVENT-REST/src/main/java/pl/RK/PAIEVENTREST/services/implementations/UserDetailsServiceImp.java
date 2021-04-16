package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.RK.PAIEVENTREST.models.UserDetailsImp;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
@Service
public class UserDetailsServiceImp implements UserDetailsService {


    UserPaiRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserPaiRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPAI userPAI =userRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
        return UserDetailsImp.build(userPAI);
    }
}
