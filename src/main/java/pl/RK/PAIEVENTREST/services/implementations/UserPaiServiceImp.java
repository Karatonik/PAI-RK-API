package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;
import pl.RK.PAIEVENTREST.repositorys.CommentRepository;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.ParticipationRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.UserPaiServiceIF;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserPaiServiceImp implements UserPaiServiceIF {

    UserPaiRepository userPaiRepository;
    EventPaiRepository eventPaiRepository;
    ParticipationRepository participationRepository;
    CommentRepository commentRepository;
    PasswordEncoder encoder;


    @Autowired
    public UserPaiServiceImp(UserPaiRepository userPaiRepository
            , EventPaiRepository eventPaiRepository
            , ParticipationRepository participationRepository,CommentRepository commentRepository, PasswordEncoder encoder) {
        this.userPaiRepository = userPaiRepository;
        this.eventPaiRepository = eventPaiRepository;
        this.participationRepository = participationRepository;
        this.commentRepository=commentRepository;
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
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findByUserKey(key);
        if (optionalUserPAI.isPresent()) {
            UserPAI userPAI = optionalUserPAI.get();
            userPAI.getNewKey();
            userPAI.setPassword(encoder.encode(newPassword));
            userPaiRepository.save(userPAI);
            return true;
        }
        return false;
    }


    //usuwanie po kluczu
    @Override
    public boolean deleteWithKey(String key) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findByUserKey(key);
        if (optionalUserPAI.isPresent()) {
            UserPAI userPAI =optionalUserPAI.get();
            //comment
            commentRepository.deleteAll(commentRepository.findByUserPai(userPAI));
            //partic
            participationRepository.findByUserPAI(userPAI).forEach(v->participationRepository.delete(v));

            eventPaiRepository.getAllEventWhereIMAdmin(userPAI).forEach(v->{
            //i am admin
                if(v.getOrganizerSet().size()>=2){
                 Set<UserPAI>userPAISet=   v.getOrganizerSet();
                 userPAISet.remove(userPAI);
                 v.setOrganizerSet(userPAISet);
                 eventPaiRepository.save(v);
                }else{
                    eventPaiRepository.delete(v);
                }
            });
            //i am user
            eventPaiRepository.getAllEventWhereIMUser(userPAI).forEach(v->{
                Set<UserPAI> userPAISet = v.getUserSet();
                userPAISet.remove(userPAI);
                v.setUserSet(userPAISet);
                eventPaiRepository.save(v);
            });
            userPaiRepository.delete(optionalUserPAI.get());
            return true;
        }
        return false;
    }

    @Override
    public UserPAI set(String email, String password, String nick) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if (!optionalUserPAI.isPresent()) {
            return userPaiRepository.save(new UserPAI(email, password, nick));
        }
        return null;
    }

    @Override
    public Participation requestToJoinEvent(String email, int eventId) {
        Optional<EventPAI> optionalEventPAI = eventPaiRepository.findById(eventId);
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);

        if (optionalEventPAI.isPresent() && optionalUserPAI.isPresent()) {
            EventPAI eventPAI = optionalEventPAI.get();
            UserPAI userPAI = optionalUserPAI.get();

            if (eventPAI.getAccess().equals(AccessPAI.Open)) {//Open
                Set<UserPAI> userPAISet = eventPAI.getUserSet();
                userPAISet.add(userPAI);
                eventPAI.setUserSet(userPAISet);

                eventPaiRepository.save(eventPAI);
                return null;

            } else {//close
                //sprawdzam czy nie ma takiego zaproszenia
                if (!participationRepository.findByUserPAIAndEventPAI(userPAI, eventPAI).isPresent()) {

                    return participationRepository.save(new Participation(RequestFrom.User, userPAI, eventPAI));

                }

            }
        }

        return null;
    }

    @Override
    public boolean acceptParticipation(int participationId, String email) {
        Optional<Participation> optionalParticipation = participationRepository.findById(participationId);
        if (optionalParticipation.isPresent()) {
            Participation participation = optionalParticipation.get();

            if (participation.getRequest().equals(RequestFrom.Event)) {
                //sprawdzam bo konto lub wydarzenie  mogło zostac usunięte
                if (userPaiRepository.findById(participation.getUserPAI().getEmail()).isPresent()
                        && eventPaiRepository.findById(participation.getEventPAI().getEventID()).isPresent()) {
                    EventPAI eventPAI = participation.getEventPAI();
                    //na koniec sprawdzam kontrolnie czy z wydarzenie się gadza
                    if (participation.getUserPAI().getEmail().equals(email)) {
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
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        return optionalUserPAI.orElse(null);
    }

    @Override
    public List<EventPAI> getAllMyEventWhereIMAdmin(String email) {
        List<EventPAI> eventPAIList = new ArrayList<>();
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if (optionalUserPAI.isPresent()) {
            return eventPaiRepository.getAllEventWhereIMAdmin(optionalUserPAI.get());
        }
        return eventPAIList;
    }

    @Override
    public List<EventPAI> getAllMyEventWhereIMUser(String email) {
        List<EventPAI> eventPAIList = new ArrayList<>();
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if (optionalUserPAI.isPresent()) {
            return eventPaiRepository.getAllEventWhereIMUser(optionalUserPAI.get());
        }
        return eventPAIList;
    }


}
