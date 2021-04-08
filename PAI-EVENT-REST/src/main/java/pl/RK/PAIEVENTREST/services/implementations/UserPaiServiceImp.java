package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.UserPaiServiceIF;

import java.util.Optional;

@Service
public class UserPaiServiceImp implements UserPaiServiceIF {

    UserPaiRepository userPaiRepository;

    @Autowired
    public UserPaiServiceImp(UserPaiRepository userPaiRepository) {
        this.userPaiRepository = userPaiRepository;
    }

    //potwierdzenie
    @Override
    public boolean confirmation(String key) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findByUserKey(key);
        if (optionalUserPAI.isPresent()) {
            UserPAI userPAI = optionalUserPAI.get();
            userPAI.setActivated(true);
            userPaiRepository.save(userPAI);
            return true;
        }
        return false;
    }


    //Dostajesz dostęp do zmiany hasła (do uzytkownika)
    @Override
    public UserPAI resetPassword(String key) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findByUserKey(key);
        return optionalUserPAI.orElse(null);
    }

    //usuwanie po kluczu
    @Override
    public boolean deleteWithKey(String key) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findByUserKey(key);
        if (optionalUserPAI.isPresent()) {
            userPaiRepository.delete(optionalUserPAI.get());
            return true;
        }
        return false;
    }


}
