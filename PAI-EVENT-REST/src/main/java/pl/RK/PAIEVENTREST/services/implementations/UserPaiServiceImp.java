package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.ParticipationRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.UserPaiServiceIF;

import java.util.Optional;
import java.util.Set;

@Service
public class UserPaiServiceImp implements UserPaiServiceIF {

    UserPaiRepository userPaiRepository;
    EventPaiRepository eventPaiRepository;
    ParticipationRepository participationRepository;
    PasswordEncoder encoder;


    @Autowired
    public UserPaiServiceImp(UserPaiRepository userPaiRepository
            ,EventPaiRepository eventPaiRepository
            ,ParticipationRepository participationRepository, PasswordEncoder encoder) {
        this.userPaiRepository = userPaiRepository;
        this.eventPaiRepository=eventPaiRepository;
        this.participationRepository=participationRepository;
        this.encoder = encoder;
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


    @Override
    public boolean changePassword(String key, String newPassword) {
        Optional<UserPAI>optionalUserPAI = userPaiRepository.findByUserKey(key);
        if(optionalUserPAI.isPresent()){
            UserPAI userPAI = optionalUserPAI.get();
            userPAI.getNewKey();
            userPAI.setPassword(encoder.encode(newPassword));
            userPaiRepository.save(userPAI);
            return  true;
        }
        return false;
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

    @Override
    public UserPAI set(String email, String password, String nick) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if(!optionalUserPAI.isPresent()){
         return  userPaiRepository.save(new UserPAI(email,password,nick));
        }
        return null;
    }

    @Override
    public boolean requestToJoinEvent(String email, int eventId) {
        Optional<EventPAI>optionalEventPAI =eventPaiRepository.findById(eventId);
        Optional<UserPAI>optionalUserPAI = userPaiRepository.findById(email);

        if(optionalEventPAI.isPresent()&&optionalUserPAI.isPresent()){
            EventPAI eventPAI = optionalEventPAI.get();
            UserPAI userPAI = optionalUserPAI.get();
                //sprawdzam czy nie ma takiego zaproszenia
            if(!participationRepository.findByUserPAIAndEventPAI(userPAI,eventPAI).isPresent()){

                participationRepository.save(new Participation(RequestFrom.User,userPAI,eventPAI));
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean acceptParticipation(int participationId, String email) {
        Optional<Participation>optionalParticipation =participationRepository.findById(participationId);
        if(optionalParticipation.isPresent()){
            Participation participation = optionalParticipation.get();

            if(participation.getRequest().equals(RequestFrom.Event)){
                //sprawdzam bo konto lub wydarzenie  mogło zostac usunięte
                if (userPaiRepository.findById(participation.getUserPAI().getEmail()).isPresent()
                        &&eventPaiRepository.findById(participation.getEventPAI().getEventID()).isPresent()){
                    EventPAI eventPAI = participation.getEventPAI();
                    //na koniec sprawdzam kontrolnie czy z wydarzenie się gadza
                    if(participation.getUserPAI().getEmail().equals(email)) {
                        Set<UserPAI> userPAISet = eventPAI.getUserSet();
                        userPAISet.add(participation.getUserPAI());
                        eventPAI.setUserSet(userPAISet);
                        eventPaiRepository.save(eventPAI);
                        participationRepository.delete(participation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public UserPAI get(String email) {
     Optional<UserPAI> optionalUserPAI= userPaiRepository.findById(email);
        return optionalUserPAI.orElse(null);
    }




}
